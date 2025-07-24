package com.fengshen.server.process.zhenbao;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_BUY_RESUL;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_BUY_RESULT;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 珍宝支付定金
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_PAY_DEPOSIT implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String goodsGid = GameReadTool.readString(buff);
		int expectPrice = GameReadTool.readInt(buff);
		GameReadTool.readInt(buff);
		String pathStr = GameReadTool.readString(buff);
		String pageStr = GameReadTool.readString(buff);
		int type = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;

		// 查询商品信息
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("goodsId", goodsGid);
		GoldStallNineGoods saleGood = GameData.that.zhenbao.selectOneByExample(example);
		if (saleGood == null) {
			GameCommonUtil.dialogOk("商品不存在。");
			return;
		}
		String extra = saleGood.getExtra();
		JSONObject extraObject = JSONObject.parseObject(extra);
		if (extraObject.getIntValue("deposit_state") == 1) {
			GameCommonUtil.dialogOk("已支付定金，请勿重复支付。");
			return;
		}
		//查询出配置
		ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("zhenbao_cost_type");
		//默认为金元宝
		if(configInfo == null) {
			if(chara.goldCoin<expectPrice*0.1) {
				GameUtil.sendMeTips("金元宝不足。");
				return;
			}
		}else {
			if("积分".equals(configInfo.getData())) {
				if(chara.chargeScore<expectPrice*0.1) {
					GameUtil.sendMeTips("积分不足。");
					return;
				}
			}else if("银元宝".equals(configInfo.getData())) {
				if(chara.silverCoin<expectPrice*0.1) {
					GameUtil.sendMeTips("银元宝不足。");
					return;
				}
			}else if(chara.goldCoin<expectPrice*0.1){
				GameUtil.sendMeTips("金元宝不足。");
				return;
			}
		}
		
		// 设置支付状态
		extraObject.put("deposit_state", 1);
		//发送邮件通知商家，指定人邮件购买
		Vo_MAILBOX_REFRESH mail = new Vo_MAILBOX_REFRESH();
		mail.attachment = "";
		mail.create_time = (int) (System.currentTimeMillis()/1000L);
		mail.expired_time = (int) (System.currentTimeMillis()/1000L)+12*60*60;
		mail.msg = "#Y"+saleGood.getMaster()+"#n你在珍宝上架的#Y" + saleGood.getName() +"#n指定人已支付定金。";
		mail.sender = chara.uuid;
		mail.title = "珍宝定金支付";
		mail.status = 0;
		mail.toGid = saleGood.getGid();
		mail.id = GameCommonUtil.UUID();
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(saleGood.getGid());
		if(gameObject != null) {
			//在线的话就直接发送邮件
			GameObjectCharMng.getGameObjectChar(gameObject.chara.id).sendOne(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(mail));
		}
		MailboxRefresh m = GameCommonUtil.convertMail(mail);
		GameData.that.mailboxRefreshService.insertSelective(m);
		//扣除支付定金用户的元宝
		if(configInfo != null && "积分".equals(configInfo.getData())) {
			GameUtil.addchargeScore(gameObjectChar, (int) (expectPrice*0.1));
		}else if(configInfo != null && "银元宝".equals(configInfo.getData())) {
			GameUtil.addYinYuanBao(gameObjectChar, (int) (expectPrice*0.1));
		}else {
			GameUtil.addJinYuanBao(gameObjectChar,  (int) (expectPrice*0.1));
		}
		GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
		
		//重新保存信息
		saleGood.setExtra(extraObject.toJSONString());
		saleGood.setUpdateTime(new Date());
		GameData.that.zhenbao.updateByPrimaryKey(saleGood);
		
		//发送结果
		Vo_GOLD_STALL_BUY_RESUL vo = new Vo_GOLD_STALL_BUY_RESUL();
		vo.setGoods_gid(saleGood.getGid());
		vo.setResult(3);
		vo.setTips("支付定金成功。");
		vo.setType(type);
		GameObjectChar.send(new MSG_GOLD_STALL_BUY_RESULT(), vo);
		//刷新界面
		GameCommonUtil.openStallGold(chara, pathStr, pageStr);
		log.info("珍宝请求支付指定交易定金");
		
	}

	@Override
	public int cmd() {
		return 0x8130;
	}

}
