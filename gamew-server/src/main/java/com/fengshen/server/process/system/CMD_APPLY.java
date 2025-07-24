package com.fengshen.server.process.system;

import java.util.List;
import java.util.Random;

import com.fengshen.server.exception.PackOverflowException;
import com.fengshen.server.game.*;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用背包里的物品对应的类
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_APPLY implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		int pos = GameReadTool.readByte(buff);
		int amount = GameReadTool.readShort(buff);
		if (pos < 0) {
			pos = 129 + pos + 127;
		}
		log.info("使用背包道具,amount={}",amount);
		Chara chara = gameObjectChar.chara;
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == pos) {
				Goods goods = chara.backpack.get(i);
				if (goods.goodsInfo.str.equals("喇叭")) {
					Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
					vo_9129_0.notify = 97;
					vo_9129_0.para = "HornDlg=喇叭";
					GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
				}
				if (goods.goodsInfo.str.equals("福袋")) {
					List<Integer> backpackAllPos = GameCommonUtil.getBackpackPos(chara);
					int avaliablePos = GameCommonUtil.getAvaliablePos(gameObjectChar.chara.backpack, backpackAllPos);
					if (avaliablePos == -1) {
						GameUtil.sendMeTips("背包已满！！");
						// 直接抛出异常
					}else{
						DrawApi.fuDai(gameObjectChar);
						ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
						GameObjectChar.send(new M65527_0(), listVo_65527_0);
						GameUtil.removemunber(chara, "福袋", 1);
						//增加使用福袋次数
						gameObjectChar.chara.fuDaiNumber +=1;
						DrawApi.fudaicishu(gameObjectChar.chara.fuDaiNumber,gameObjectChar);
					}
				}
				if (goods.goodsInfo.str.equals("血池")) {
					Chara chara2 = chara;
					chara2.extra_mana += 300000;
					if (chara.extra_mana > 90000000) {
						chara.extra_mana = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "血池", 1);
				}
				if (goods.goodsInfo.str.equals("中级血池")) {
					Chara chara3 = chara;
					chara3.extra_mana += 1500000;
					if (chara.extra_mana > 90000000) {
						chara.extra_mana = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "中级血池", 1);
				}
				if (goods.goodsInfo.str.equals("高级血池")) {
					Chara chara4 = chara;
					chara4.extra_mana += 7500000;
					if (chara.extra_mana > 90000000) {
						chara.extra_mana = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "高级血池", 1);
				}
				if (goods.goodsInfo.str.equals("灵池")) {
					Chara chara5 = chara;
					chara5.have_coin_pwd += 300000;
					if (chara.have_coin_pwd > 90000000) {
						chara.have_coin_pwd = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "灵池", 1);
				}
				if (goods.goodsInfo.str.equals("中级灵池")) {
					Chara chara6 = chara;
					chara6.have_coin_pwd += 1500000;
					if (chara.have_coin_pwd > 90000000) {
						chara.have_coin_pwd = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "中级灵池", 1);
				}
				if (goods.goodsInfo.str.equals("高级灵池")) {
					Chara chara7 = chara;
					chara7.have_coin_pwd += 7500000;
					if (chara.have_coin_pwd > 90000000) {
						chara.have_coin_pwd = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "高级灵池", 1);
				}
				if (goods.goodsInfo.str.equals("驯兽诀")) {
					Chara chara8 = chara;
					chara8.use_skill_d += 300;
					if (chara.use_skill_d > 90000000) {
						chara.use_skill_d = 90000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "驯兽诀", 1);
				}

				if (goods.goodsInfo.str.equals("超级仙风散")) {
					Chara chara9 = chara;
					chara9.enable_double_points += 200;
					if (chara.enable_double_points > 16000) {
						chara.enable_double_points = 16000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "超级仙风散", 1);

					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "你使用了道具#R超级仙风散#n，当前余额#R" + chara.enable_double_points + "点。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}

				if (goods.goodsInfo.str.equals("神木鼎")) {
					Chara chara10 = chara;
					chara10.enable_shenmu_points += 1000;
					if (chara.enable_shenmu_points > 8000) {
						chara.enable_shenmu_points = 8000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "神木鼎", 1);
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "你使用了道具#R神木鼎#n，当前余额#R" + chara.enable_shenmu_points + "点。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}
				if (goods.goodsInfo.str.equals("紫气鸿蒙")) {
					Chara chara11 = chara;
					chara11.ziqihongmeng += 200;
					if (chara.ziqihongmeng > 12000) {
						chara.ziqihongmeng = 12000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "紫气鸿蒙", 1);
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "你使用了道具#R紫气鸿蒙#n，当前余额#R" + chara.ziqihongmeng + "点。";
					vo_20481_0.time = 1562987118;
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}
				if (goods.goodsInfo.str.equals("如意刷到令")) {
					Chara chara12 = chara;
					chara12.chushi_ex += 200;
					if (chara.chushi_ex > 4000) {
						chara.chushi_ex = 4000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "如意刷到令", 1);
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "你使用了道具#R如意刷到令#n，当前余额#R" + chara.chushi_ex + "点。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}
				if (goods.goodsInfo.str.equals("急急如律令")) {
					Chara chara13 = chara;
					chara13.fetch_nice += 200;
					if (chara.fetch_nice > 4000) {
						chara.fetch_nice = 4000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "急急如律令", 1);
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "你使用了道具#R急急如律令#n，当前余额#R" + chara.fetch_nice + "点。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}
				if (goods.goodsInfo.str.equals("宠风散")) {
					Chara chara14 = chara;
					chara14.shuadaochongfeng_san += 200;
					if (chara.shuadaochongfeng_san > 12000) {
						chara.shuadaochongfeng_san = 12000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "宠风散", 1);
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "你使用了道具#R宠风散#n，当前余额#R" + chara.shuadaochongfeng_san + "点。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}
				if (goods.goodsInfo.str.equals("高级驯兽诀")) {
					Chara chara15 = chara;
					chara15.use_skill_d += 2500;
					if (chara.use_skill_d < 0) {
						chara.use_skill_d = 3000000;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "高级驯兽诀", 1);
				}
				if (goods.goodsInfo.str.equals("灵物囊")) {
					String name = callMounts();
					StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
					GameUtil.huodedaoju(gameObjectChar, info, 1);
					GameUtil.removemunber(chara, "灵物囊", 1);
					Vo_40964_0 vo_40964_0 = new Vo_40964_0();
					vo_40964_0.type = 1;
					vo_40964_0.name = name;
					vo_40964_0.param = info.getType().toString();
					vo_40964_0.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_0);
					Vo_20481_0 vo_20481_2 = new Vo_20481_0();
					vo_20481_2.msg = "你打开了灵物囊，获得了#R1#n个#R" + name + "#n。";
					vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_2);
				}
				//使用超级天书
				if (goods.goodsInfo.str.equals("超级天书")) {
					//获得随机属性天书
					String name = callMountsChaojitianshu();
					StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
					GameUtil.huodetianshu(gameObjectChar, info, 1,"suiji");
					String name2 = callMountsChaojitianshu();
					StoreInfo info2 = GameData.that.baseStoreInfoService.findOneByName(name2);
					GameUtil.huodetianshu(gameObjectChar, info2, 1,"suiji");
					GameUtil.removemunber(chara, goods, 1);
					Vo_40964_0 vo_40964_2 = new Vo_40964_0();
					vo_40964_2.type = 1;
					vo_40964_2.name = name;
					vo_40964_2.param = info.getType().toString();
					vo_40964_2.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_2);
					vo_40964_2 = new Vo_40964_0();
					vo_40964_2.type = 1;
					vo_40964_2.name = name2;
					vo_40964_2.param = info2.getType().toString();
					vo_40964_2.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_2);
					Vo_20481_0 vo_20481_3 = new Vo_20481_0();
					vo_20481_3.msg = "你使用了超级天书";
					vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_3);
				}
				if (goods.goodsInfo.str.equals("天书")) {
					String name = callMountstianshu();
					StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
					GameUtil.huodedaoju(gameObjectChar, info, 1);
					String name2 = callMountstianshu();
					StoreInfo info2 = GameData.that.baseStoreInfoService.findOneByName(name2);
					GameUtil.huodedaoju(gameObjectChar, info2, 1);
					GameUtil.removemunber(chara, goods, 1);
					Vo_40964_0 vo_40964_2 = new Vo_40964_0();
					vo_40964_2.type = 1;
					vo_40964_2.name = name;
					vo_40964_2.param = info.getType().toString();
					vo_40964_2.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_2);
					vo_40964_2 = new Vo_40964_0();
					vo_40964_2.type = 1;
					vo_40964_2.name = name;
					vo_40964_2.param = info2.getType().toString();
					vo_40964_2.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_2);
					Vo_20481_0 vo_20481_3 = new Vo_20481_0();
					vo_20481_3.msg = "你使用了天书";
					vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_3);
				}
				// 如果使用了超级藏宝图
				if (goods.goodsInfo.str.equals("超级藏宝图")) {
					Vo_61553_0 vo_61553_0 = chara.taskMap.get("超级宝藏");
					if (vo_61553_0 == null) {
						vo_61553_0 = new Vo_61553_0();
					}
					List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
							.findByType(8);
					RenwuMonster renwuMonster = renwuMonsters
							.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
					vo_61553_0.count = 1;
					vo_61553_0.task_type = "超级宝藏";
					vo_61553_0.task_desc = "在游戏中根据超级藏宝图进行寻宝。";
					vo_61553_0.task_prompt = "前往#Z" + renwuMonster.getMapName() + "|" + renwuMonster.getMapName() + "("
							+ renwuMonster.getX() + "," + renwuMonster.getY() + ")#Z寻宝";
					vo_61553_0.refresh = 1;
					vo_61553_0.task_end_time = 1567909190;
					vo_61553_0.attrib = 1;
					vo_61553_0.reward = "#I道行|道行#I#I潜能|潜能#I#I金钱|金钱#I#I物品|召唤令·十二生肖#I#I宠物|十二生肖=F#I";
					vo_61553_0.show_name = "超级宝藏";
					vo_61553_0.task_extra_para = "";
					vo_61553_0.task_state = "1";
					log.info("挖宝位置:{}", vo_61553_0.task_prompt);
					GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0);
					GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt, "挖宝"));
					GameUtil.removemunber(chara, goods, 1);
					chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
					return;
				}
				if("特级藏宝图".equals(goods.goodsInfo.str)) {
					Vo_61553_0 vo_61553_0 = chara.taskMap.get("特级宝藏");
					if (vo_61553_0 == null) {
						vo_61553_0 = new Vo_61553_0();
					}
					List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
							.findByType(8);
					RenwuMonster renwuMonster = renwuMonsters
							.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
					vo_61553_0.count = 1;
					vo_61553_0.task_type = "特级宝藏";
					vo_61553_0.task_desc = "在游戏中根据特级藏宝图进行寻宝。";
					vo_61553_0.task_prompt = "前往#Z" + renwuMonster.getMapName() + "|" + renwuMonster.getMapName() + "("
							+ renwuMonster.getX() + "," + renwuMonster.getY() + ")#Z寻宝";
					vo_61553_0.refresh = 1;
					vo_61553_0.task_end_time = 1567909190;
					vo_61553_0.attrib = 1;
					vo_61553_0.reward = "#I道行|道行#I#I潜能|潜能#I#I金钱|金钱#I#I物品|召唤令·十二生肖#I#I宠物|十二生肖=F#I";
					vo_61553_0.show_name = "特级宝藏";
					vo_61553_0.task_extra_para = "";
					vo_61553_0.task_state = "1";
					log.info("挖宝位置:{}", vo_61553_0.task_prompt);
					GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0);
					GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt, "挖宝"));
					GameUtil.removemunber(chara, "特级藏宝图", 1);
					chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
					return;
				}
				if (goods.goodsInfo.str.endsWith("头像框")) {
					gameObjectChar.confirmData = goods;
					GameUtil.confirm(chara, "确定使用#R" + goods.goodsInfo.str + "#n？", "useChatHead");
				}
				if (goods.goodsInfo.str.endsWith("聊天底框")) {
					gameObjectChar.confirmData = goods;
					GameUtil.confirm(chara, "确定使用#R" + goods.goodsInfo.str + "#n？", "useChatFloor");
				}
				if("结婚纪念册".equals(goods.goodsInfo.str)) {
					MarryUtil.openWeddingBook(gameObjectChar);
				}
				if("人气烟花·满天星雨".equals(goods.goodsInfo.str) || "人气烟花·绚丽彩焰".equals(goods.goodsInfo.str)) {
					gameObjectChar.confirmData = goods;
					GameUtil.confirm(chara, "是否确定使用烟花？", "useFireworks");
				}else if("大桃子".equals(goods.goodsInfo.str)) {
					
				}else if("桃子".equals(goods.goodsInfo.str)) {
					
				}else if("大萝卜".equals(goods.goodsInfo.str)) {
					
				}else if("萝卜".equals(goods.goodsInfo.str)) {
					
				}
				break;
			}
		}
	}

	@Override
	public int cmd() {
		return 8236;
	}

	private static String callMounts() {
		String[] mounts_name = { "控心玉", "定鞍石", "驱力刺", "困灵砂", "拘首环" };
		Random random = new Random();
		int randomInt = random.nextInt(5);
		String name = mounts_name[randomInt];
		return name;
	}

	private static String callMountstianshu() {
		String[] mounts_name = { "反击", "惊雷", "尽忠", "烈炎", "碎石", "仙风", "青木", "修罗术", "降魔斩", "狂暴", "寒冰", "云体", "怒击", "破天",
				"魔引" };
		Random random = new Random();
		int randomInt = random.nextInt(15);
		String name = mounts_name[randomInt];
		return name;
	}
	private static String callMountsChaojitianshu() {
		String[] mounts_name = { "超级反击", "超级惊雷", "超级尽忠", "超级烈炎", "超级碎石", "超级仙风", "超级青木", "超级修罗术", "超级降魔斩", "超级狂暴", "超级寒冰", "超级云体", "超级怒击", "超级破天",
				"超级魔引" };
		Random random = new Random();
		int randomInt = random.nextInt(15);
		String name = mounts_name[randomInt];
		return name;
	}
}