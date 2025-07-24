package com.fengshen.server.process.shop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.LuckDrawUtils;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_45382_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.M41240_0;
import com.fengshen.server.data.write.M45382_0;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 充值好礼
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_NEW_LOTTERY_DRAW implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		log.info("充值好礼抽奖");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (chara.shadow_self < 0) {
			Vo_20481_0 vo2048101 = new Vo_20481_0();
			vo2048101.msg = "系统检测到你数据不正常，给予删号处罚！";
			vo2048101.time = (int) (System.currentTimeMillis() / 1000L);
		}
		if (1 == type) { // 小额单次
			if(chara.shadow_self-1 < 0) {
				GameUtil.sendMeTips("次数不足！");
				return;
			}
			chara.shadow_self -= 1;
			Map<String, Object> data = new HashMap<>();
			data.put("lottery_times", chara.shadow_self);
			GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
			String[] strings = LuckDrawUtils.luckDraw(false);
			if(strings != null) {
				Vo_45382_0 vo_45382_0 = new Vo_45382_0();
				vo_45382_0.reward_str = strings[2];
				vo_45382_0.level = Integer.valueOf(strings[3]);
				GameObjectChar.send(new M45382_0(), vo_45382_0);
				LuckDrawUtils.huodechoujiang(strings, gameObjectChar, "充值好礼");
			}
		}
		List<String[]> superLuckInfos = new ArrayList<>();
		if (3 == type) { // 小额 10次
			if(chara.shadow_self-10 < 0) {
				GameUtil.sendMeTips("次数不足！");
				return;
			}
			chara.shadow_self -= 10;
			Map<String, Object> data = new HashMap<>();
			data.put("lottery_times", chara.shadow_self);
			GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
			for (int i = 0; i < 10; ++i) {
				String[] strings2 = LuckDrawUtils.luckDraw(false);
				System.out.println(Arrays.toString(strings2));
				if(strings2 != null) {
					superLuckInfos.add(strings2);
					Vo_45382_0 vo_45382_0 = new Vo_45382_0();
					vo_45382_0.reward_str = strings2[2];
					vo_45382_0.level = Integer.valueOf(strings2[3]);
					GameObjectChar.send(new M45382_0(), vo_45382_0);
				}
			}
		}
		if (2 == type) { // 大额，单次抽奖
			if(chara.shadow_self-10 < 0) {
				GameUtil.sendMeTips("次数不足！");
				return;
			}
			chara.shadow_self -= 10;
			Map<String, Object> data = new HashMap<>();
			data.put("lottery_times", chara.shadow_self);
			GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
			String[] strings = LuckDrawUtils.luckDraw(true);
			if(strings != null) {
				Vo_45382_0 vo_45382_0 = new Vo_45382_0();
				vo_45382_0.reward_str = strings[2];
				vo_45382_0.level = Integer.valueOf(strings[3]);
				GameObjectChar.send(new M45382_0(), vo_45382_0);
				LuckDrawUtils.huodechoujiang(strings, gameObjectChar, "充值好礼");
			}
		}
		if (4 == type) { // 大额，10次抽奖
			Chara chara5 = chara;
			if(chara.shadow_self-100 < 0) {
				GameUtil.sendMeTips("次数不足！");
				return;
			}
			chara5.shadow_self -= 100;
			Map<String, Object> data = new HashMap<>();
			data.put("lottery_times", chara.shadow_self);
			GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
			for (int i = 0; i < 10; ++i) {
				String[] strings2 = LuckDrawUtils.luckDraw(true);
				if(strings2 != null) {
					superLuckInfos.add(strings2);
					Vo_45382_0 vo_45382_0 = new Vo_45382_0();
					vo_45382_0.reward_str = strings2[2];
					vo_45382_0.level = Integer.valueOf(strings2[3]);
					GameObjectChar.send(new M45382_0(), vo_45382_0);
				}
			}
		}
		GameObjectChar.send(new M41240_0(), null);
		for (String[] luckInfo : superLuckInfos) {
			LuckDrawUtils.huodechoujiang(luckInfo, gameObjectChar, "充值好礼");
		}
	}

	@Override
	public int cmd() {
		return 45385;
	}

}