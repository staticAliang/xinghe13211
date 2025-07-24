package com.fengshen.server.process.zhenbao;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_MINE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;

/**
 * 金元宝交易重新上架
 * 
 */
@Service
public class CMD_GOLD_STALL_RESTART_GOODS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String goodsId = GameReadTool.readString(buff);
		int price = GameReadTool.readInt(buff);
		GameReadTool.readByte(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		
		if(GameConfig.config.getMarketConfig().getZhenbaoStatus() == 1) {
			GameCommonUtil.dialogOk("gm关闭了珍宝。");
			return;
		}
		//把商品查询出来
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("status", 3).andEqualTo("goodsId", goodsId).andEqualTo("deleted", false);
		GoldStallNineGoods gold = GameData.that.zhenbao.selectOneByExample(example);
		if(gold == null) {
			GameCommonUtil.dialogOk("商品已失效。");
			return;
		}
		//公示
		JSONObject extra = JSONObject.parseObject(gold.getExtra());
		extra.put("deposit_state", 0);
		gold.setExtra(extra.toJSONString());
		Integer zhenbaoPublicTimes = GameConfig.config.getMarketConfig().getZhenbaoPublicTimes();
		gold.setStartTime((int) (System.currentTimeMillis()/1000L));
		if(zhenbaoPublicTimes>0) {
			gold.setStatus(1);
			gold.setEndTime((int) (System.currentTimeMillis()/1000L+(zhenbaoPublicTimes*60)));
			//保存信息
			GameData.that.redisUtils.set(DefinedConst.GOLD_STALL_PREFIX+";"+gold.getGoodsId()+";"+1, 
					"", GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes()*60);
		}else {
			//无需公式
			gold.setStatus(2);
			//设置下架时间
			GameData.that.redisUtils.set(DefinedConst.GOLD_STALL_PREFIX+";"+goodsId+";"+2, 
					"", GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes()*60);
			gold.setEndTime((int) (System.currentTimeMillis()/1000L)+GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes()*60);
		}
		gold.setPrice(price);
		gold.setUpdateTime(new Date());
		GameData.that.zhenbao.updateByPrimaryKeySelective(gold);
		
		//计算摊位费
		int stallMoney = price*100;
		if(stallMoney > 5000000) {
			stallMoney = 5000000;
		}else if(stallMoney < 500000 && gold.getStallItemType() != 3) {
			stallMoney =  500000;
		}
		chara.cash -= stallMoney;
		final ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0);
		GameUtil.sendMeTips("摆摊成功花费了摊位费" + GameCommonUtil.getMoneyDes(stallMoney) + "#n文钱#n");
		GameObjectChar.send(new MSG_GOLD_STALL_MINE(), GameCommonUtil.refreshMarketGold(chara));
	}

	@Override
	public int cmd() {
		return 0x8106;
	}

}
