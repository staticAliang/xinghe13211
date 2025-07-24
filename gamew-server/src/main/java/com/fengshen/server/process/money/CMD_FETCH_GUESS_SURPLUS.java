package com.fengshen.server.process.money;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_40995_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M40995_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 五行提款
 * 
 *
 */
@Service
@Slf4j
public class CMD_FETCH_GUESS_SURPLUS implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("五行提款");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.wuxingBalance<=0) {
			GameUtil.sendMeTips("余额不足，无法提款！");
			return;
		}
		ConfigInfo wuxingConfig = GameData.that.configInfoService.getOneByKeyName("wuxing_config");
		com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(wuxingConfig.getData());
		if(parseObject == null) {
			GameUtil.sendMeTips("未找到五行竞猜配置");
			return;
		}
		
		String type = parseObject.getString("type");
		
		//通知
		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = "你获得了#R" + chara.wuxingBalance +"#n"+type+"#n";
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20480_0(), vo_20480_0);
		
		
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 3;
		vo_40964_0.name = type;
		vo_40964_0.param = String.valueOf(chara.wuxingBalance);
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		
		if("积分".equals(type)) {
			GameUtil.addchargeScore(gameObjectChar, chara.wuxingBalance, "五行竞猜");
		}else if("金钱".equals(type)){
			GameUtil.addCash(gameObjectChar, chara.wuxingBalance,"五行竞猜");
		}else if("金元宝".equals(type)) {
			GameUtil.addJinYuanBao(gameObjectChar, chara.wuxingBalance, "五行竞猜");
		}
		chara.wuxingBalance = 0;
		
		ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0);
		
		Vo_40995_0 vo_40995_0 = new Vo_40995_0();
		vo_40995_0.flag = 0;
		vo_40995_0.money = 0;
		vo_40995_0.surlus = String.valueOf(chara.wuxingBalance);
		vo_40995_0.overflow = "0";
		vo_40995_0.amount = 0;
		vo_40995_0.choice = 0;
		vo_40995_0.prize = 0;
		vo_40995_0.leftCount = parseObject.getIntValue("maxCount")-chara.wuxingCount;
		GameObjectChar.send(new M40995_0(), vo_40995_0);
	}

	@Override
	public int cmd() {
		return 40996;
	}
}