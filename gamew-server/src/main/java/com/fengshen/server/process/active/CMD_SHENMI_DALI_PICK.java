package com.fengshen.server.process.active;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Experience;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.data.vo.Vo_41480_0;
import com.fengshen.server.data.vo.Vo_41482_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M32747_0;
import com.fengshen.server.data.write.M41480_0;
import com.fengshen.server.data.write.M41482_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 神秘大礼砸蛋给的奖励
 * 
 *
 */
@Service
@Slf4j
public class CMD_SHENMI_DALI_PICK implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("神秘大礼砸蛋给的奖励");
		int index = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (index == 0) {
			for (int w = 0; w < 8; ++w) {
				index = w + 1;
				int time = chara.shenmiliwu.get(w).time;
				if (chara.online_time / 1000L + (System.currentTimeMillis() - chara.uptime) / 1000L > time
						&& chara.shenmiliwu.get(index - 1).brate == 0) {
					String name = "";
					int potentialPoint = 0;
					Random random = new Random();
					int i = random.nextInt(3);
					if (i == 1) {
						name = "潜能";
						potentialPoint = chara.level * 810;
						Chara chara2 = chara;
						chara2.pot += potentialPoint;
						Vo_20480_0 vo_20480_0 = new Vo_20480_0();
						vo_20480_0.msg = "你获得了#R" + potentialPoint + "#n点" + name;
						vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20480_0(), vo_20480_0);
					}
					if (i == 2) { // 获得等级7倍的道行
						name = "道行";
						potentialPoint = chara.level * 7000;
						GameUtil.adddaohang(chara, potentialPoint);
					} else if (i == 0) {
						name = "经验";
						Experience experience1 = GameData.that.baseExperienceService.findOneByAttrib(chara.level);
						potentialPoint = experience1.getMaxLevel() * 2 / (chara.level + 9);
						GameUtil.huodejingyan(chara, 120000);
					}
					chara.shenmiliwu.get(index - 1).name = name;
					chara.shenmiliwu.get(index - 1).brate = 1;
					Vo_41482_0 vo_41482_0 = new Vo_41482_0();
					vo_41482_0.brate = 1;
					vo_41482_0.name = name;
					vo_41482_0.index = index;
					vo_41482_0.result = 0;
					GameObjectChar.send(new M41482_0(), vo_41482_0);
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(chara);
					GameObjectChar.send(new M32747_0(), vo_32747_0List);
//					GameUtil.a65511(gameObjectChar);
					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = "恭喜，你意外获得了#R" + name + "#n奖励";
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);
					GameUtil.MSG_OPEN_WELFARE(chara);
					List<Vo_41480_0> list = new LinkedList<Vo_41480_0>();
					for (int k = 0; k < chara.shenmiliwu.size(); ++k) {
						Vo_41480_0 vo_41480_0 = new Vo_41480_0();
						vo_41480_0.online_time = (int) (chara.online_time / 1000L
								+ (System.currentTimeMillis() - chara.uptime) / 1000L);
						vo_41480_0.time = chara.shenmiliwu.get(k).time;
						vo_41480_0.name = chara.shenmiliwu.get(k).name;
						vo_41480_0.index = chara.shenmiliwu.get(k).index;
						vo_41480_0.brate = chara.shenmiliwu.get(k).brate;
						list.add(vo_41480_0);
					}
					GameObjectChar.send(new M41480_0(), list);
				}
			}
			return;
		}
		int time2 = chara.shenmiliwu.get(index - 1).time;
		boolean istime = chara.online_time / 1000L + (System.currentTimeMillis() - chara.uptime) / 1000L > time2;
		if (istime && chara.shenmiliwu.get(index - 1).brate == 0) {
			String name2 = "";
			int potentialPoint2 = 0;
			Random random2 = new Random();
			int j = random2.nextInt(100) + 1;
			if (j > 10) {
				name2 = "潜能";
				potentialPoint2 = chara.level * 810;
				Chara chara3 = chara;
				chara3.pot += potentialPoint2;
				Vo_20480_0 vo_20480_2 = new Vo_20480_0();
				vo_20480_2.msg = "你获得了#R" + potentialPoint2 + "#n点" + name2;
				vo_20480_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20480_0(), vo_20480_2);
			} else if (j > 20) {
				name2 = "道行";
				potentialPoint2 = chara.level * 7;
				GameUtil.adddaohang(chara, potentialPoint2);
			} else if (j > 99.99) {
				name2 = "金元宝";
				chara.goldCoin += 100000;
				GameUtil.sendYaoYan("恭喜#Y" + chara.name + "#n在神秘大礼中砸出了#R10#n万金元宝。");
			} else {
				name2 = "经验";
				Experience experience2 = GameData.that.baseExperienceService.findOneByAttrib(chara.level);
				if(experience2 != null) {
					potentialPoint2 = experience2.getMaxLevel() * 2 / (chara.level + 9);
					GameUtil.huodejingyan(chara, potentialPoint2);
				}
			}
			chara.shenmiliwu.get(index - 1).name = name2;
			chara.shenmiliwu.get(index - 1).brate = 1;
			Vo_41482_0 vo_41482_2 = new Vo_41482_0();
			vo_41482_2.brate = 1;
			vo_41482_2.name = name2;
			vo_41482_2.index = index;
			vo_41482_2.result = 0;
			GameObjectChar.send(new M41482_0(), vo_41482_2);
			ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_2);
			List<Vo_32747_0> vo_32747_0List2 = GameUtil.a32747(chara);
			GameObjectChar.send(new M32747_0(), vo_32747_0List2);
//			GameUtil.a65511(chara);
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "恭喜，你意外获得了#R" + name2 + "#n奖励";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
			GameUtil.MSG_OPEN_WELFARE(chara);
			List<Vo_41480_0> list2 = new LinkedList<Vo_41480_0>();
			for (int l = 0; l < chara.shenmiliwu.size(); ++l) {
				Vo_41480_0 vo_41480_2 = new Vo_41480_0();
				vo_41480_2.online_time = (int) (chara.online_time / 1000L
						+ (System.currentTimeMillis() - chara.uptime) / 1000L);
				vo_41480_2.time = chara.shenmiliwu.get(l).time;
				vo_41480_2.name = chara.shenmiliwu.get(l).name;
				vo_41480_2.index = chara.shenmiliwu.get(l).index;
				vo_41480_2.brate = chara.shenmiliwu.get(l).brate;
				list2.add(vo_41480_2);
			}
			GameObjectChar.send(new M41480_0(), list2);
		}
	}

	@Override
	public int cmd() {
		return 41481;
	}
}