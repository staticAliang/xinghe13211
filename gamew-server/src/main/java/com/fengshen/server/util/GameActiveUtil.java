package com.fengshen.server.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.DateUtil;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.VictoryDieReward;
import com.fengshen.server.data.constant.BonusType;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_MENU_LIST;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.vo.tongtianta.Vo_TONGTIANTA_BONUS_DLG;
import com.fengshen.server.data.vo.tongtianta.Vo_TONGTIANTA_INFO;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.MSG_MENU_LIST;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.animate.MSG_ANIMATE_IN_CHAR_LAYER;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.data.write.tongtianta.MSG_TONGTIANTA_BONUS_DLG;
import com.fengshen.server.data.write.tongtianta.MSG_TONGTIANTA_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.config.CtConfig;
import com.fengshen.server.domain.config.ForcePkConfig;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameTeamUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.process.system.CMD_SELECT_MENU_ITEM;
import com.qcloud.cos.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 游戏活动任务工具类
 *
 */
@Slf4j
public class GameActiveUtil {
	
	public static void pointTtt(Chara chara1) {
		Map map = GameData.that.baseMapService.findOneByName("通天塔");
		if (map == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "未找到通天塔地图，不符合条件";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			return;
		}
		chara1.y = map.getY();
		chara1.x = map.getX();
		GameLine.getGameMapname(chara1.line, map.getName()).join(GameObjectChar.getGameObjectChar());
	}

	/**
	 * 进入通天塔
	 * 
	 * @param chara1    当前角色
	 * @param bonusType 奖励类型, exp:经验 tao:道行
	 * @param isFirst   是否第一次进入
	 */
	public static void enterTongtianta(Chara chara1, String bonusType, boolean isFirst) {
		Random random = new Random();
		String name = GameUtil.tongttXj[random.nextInt(GameUtil.tongttXj.length)];
		Vo_61553_0 vo_61553_0 = chara1.commonTaskMap.get("通天塔");
		// 加载任务信息,如果为空的话.则创建
		if (chara1.commonTaskMap.get("通天塔") == null) {
			vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = "通天塔";
			vo_61553_0.task_desc = "凡诚心向道者，皆可在通天塔内潜心修炼，领悟天道。\n当前正在进行的是#R通天塔修炼挑战阶段。\n#n挑战目标：在通天塔内完成第#R" + chara1.level
					+ "层#n的挑战\n" + "当前挑战情况：在#R通天塔" + (chara1.level - 10) + "层#n挑战#Y" + name + "#n。";
			vo_61553_0.task_prompt = "挑战#P" + name + "|M=麻烦星君多多指教@P北斗星使|M=【进塔】进入通天塔#P";
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = (int) (System.currentTimeMillis() / 1000L);
			vo_61553_0.attrib = 1;
			vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I";
			vo_61553_0.show_name = "通天塔";
			vo_61553_0.task_extra_para = "";
			vo_61553_0.task_state = "1";
			// 第一次进入,次数+1
			chara1.tongttcishu++;
		}
		//创建任务
//		GameUtilRenWu.createTaskTeam(vo_61553_0, chara1);
		chara1.commonTaskMap.put("通天塔", vo_61553_0);
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara1.id);
		// 创建通天塔任务模板
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		Vo_TONGTIANTA_INFO info = chara.tongtiantaTask;
		if (info == null) {
			info = new Vo_TONGTIANTA_INFO();
			info.setCurLayer(chara.level - 10);
			info.setBreakLayer(chara.level);
			info.setCurType(1);
			info.setNpc(name);
			info.setChallengeCount(0);
			info.setBonusType(bonusType);
			info.setHasNotCompletedSmfj(0);
		}
		GameObjectChar.send(new MSG_TONGTIANTA_INFO(), info);
		chara.tongtiantaTask = info;
		if (!isFirst) {
			// 自动行走
			GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
		}
	}

	// 通天塔战斗结束后，传送更高层
	public static void tongtiantaFightEnd(Chara chara) {
		if (chara != null) {
			// 通天塔最顶层
			if (chara.tongtiantaTask.getCurLayer() >= chara.tongtiantaTask.getTopLayer()) {
				// 清除任务
				chara.commonTaskMap.get("通天塔").show_name = "";
				chara.commonTaskMap.get("通天塔").task_prompt = "";
				GameObjectChar.send(new MSG_TASK_PROMPT(), chara.commonTaskMap.get("通天塔"));
				chara.commonTaskMap.remove("通天塔");
				tongtiantaReward(chara);
				chara.tongtiantaTask = null;
				// 到达最顶层结束任务.
				Map map = GameData.that.baseMapService.findOneByName("天墉城");
				// 将人物传送到北斗星使处
				chara.x = 114;
				chara.y = 16;
				GameLine.getGameMapname(chara.line, map.getName()).join(GameObjectChar.getGameObjectChar());
				return;
			}
			if (chara.tongtiantaTask.getCurLayer() == chara.level) {
				// 完成挑战给经验--并弹出框询问
				Vo_TONGTIANTA_BONUS_DLG vo_TONGTIANTA_BONUS_DLG = new Vo_TONGTIANTA_BONUS_DLG();
				vo_TONGTIANTA_BONUS_DLG.setBonusType(chara.tongtiantaTask.getBonusType());
				vo_TONGTIANTA_BONUS_DLG.setDlgType(1);
				// 设置奖励数值
				if (chara.tongtiantaTask.getBonusType() == BonusType.EXP.type) {
					// 经验
					int getExp = 1800000 - (chara.level * 1000);
					GameUtil.huodejingyan(chara, getExp, "通天塔");
					vo_TONGTIANTA_BONUS_DLG.setBonusValue(getExp);
				} else {
					int getTao = chara.level * 60 * 1440;
					int residue = getTao % 525600; // 一年为525600，一天为1440
					int days = residue / 1440;
					int years = (getTao - residue) / 525600;
					GameUtil.adddaohang(chara, getTao, "通天塔");
					GameCommonUtil.addWuXue(chara, getTao / 4000, "通天塔");
					vo_TONGTIANTA_BONUS_DLG.setBonusValue(years * 360 + days);
				}
				GameObjectChar.send(new MSG_TONGTIANTA_BONUS_DLG(), vo_TONGTIANTA_BONUS_DLG);
				chara.tongtiantaTask.setCurType(2);
				GameObjectChar.send(new MSG_TONGTIANTA_INFO(), chara.tongtiantaTask);
				//突破完成，自动下一层任务
				tongtiantaGoNextLayer(false);
				return;
			}
			// 自我突破层数
			int attrib = 0;
			if (chara.tongtiantaTask.getCurLayer() > chara.level) {
				chara.tongtiantaTask.setChallengeCount(chara.tongtiantaTask.getChallengeCount() + 1);
				attrib = 3;
			}
			if (chara.tongtiantaTask.getChallengeCount() > 0) {
				attrib = 3;
				tongtiantaReward(chara);
			}
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = "通天塔";
			vo_61553_0.task_desc = "请前往#Y北斗神将#n进入更高层挑战。";
			vo_61553_0.task_prompt = "请前往#P北斗神将|M=将我传送至更高层@P北斗星使|M=【进塔】进入通天塔#P处,进入更高层挑战";
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = (int) (System.currentTimeMillis() / 1000L);
			vo_61553_0.attrib = attrib;
			vo_61553_0.reward = "";
			vo_61553_0.show_name = "通天塔";
			vo_61553_0.task_extra_para = "";
			vo_61553_0.task_state = "1";
			GameObjectChar.sendduiwu(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);

			// 自动行走
			GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));

			// 更新通天塔任务信息
			Vo_TONGTIANTA_INFO tongtiantaTask = chara.tongtiantaTask;
			// 设置挑战成功
			tongtiantaTask.setCurType(2);
			GameObjectChar.send(new MSG_TONGTIANTA_INFO(), tongtiantaTask);
		}
	}

	/**
	 * 通天塔结算奖励
	 * 
	 * @param chara
	 */
	private static void tongtiantaReward(Chara chara) {
		if (chara.tongtiantaTask.getBonusType().equals(BonusType.EXP.type)) {
			// 经验
			int getExp = (int) (chara.level / 10 * 500 * (1.0 + 0.2 * chara.tongtiantaTask.getCurLayer()));
			if (getExp < 1) {
				getExp = 1;
			}
			if (chara.tongtiantaTask.getHasNotCompletedSmfj() == 1 && chara.tongtiantaTask.getFeishengNumber() > 0) {
				// 完成过飞升.基础翻倍
				getExp = getExp * chara.tongtiantaTask.getFeishengNumber();
				// 恢复初始化
				chara.tongtiantaTask.setHasNotCompletedSmfj(0);
				chara.tongtiantaTask.setFeishengNumber(0);
			}
			GameUtil.huodejingyan(chara, getExp, "通天塔");
		} else {
			// 1496000-----374000
			int getTao = (int) ((1.0 + 0.2 * (chara.tongtiantaTask.getCurLayer() - (chara.level - 24)))
					* GameConfig.tongtiantaTao);
			getTao = Math.abs(getTao);
			if (chara.tongtiantaTask.getHasNotCompletedSmfj() == 1 && chara.tongtiantaTask.getFeishengNumber() > 0) {
				// 完成过飞升.基础翻倍
				getTao = getTao * chara.tongtiantaTask.getFeishengNumber();
				// 恢复初始化
				chara.tongtiantaTask.setHasNotCompletedSmfj(0);
				chara.tongtiantaTask.setFeishengNumber(0);
			}
			GameUtil.adddaohang(chara, getTao, "通天塔");
			GameCommonUtil.addWuXue(chara, getTao / 4000, "通天塔");
		}
	}

	/**
	 * 通天塔战斗胜利传送至下一层
	 */
	public static void tongtiantaGoNextLayer(boolean... isAutoWalk) {
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		if (chara.tongtiantaTask.getCurType() == 1) {
			GameUtil.sendMeTips("请完成本层挑战！");
			return;
		} else if (chara.tongtiantaTask.getCurLayer() + 1 > chara.tongtiantaTask.getTopLayer()) {
			GameUtil.sendMeTips("已到达最顶层,无法传送到下一层！");
			return;
		}
		Random random = new Random();
		String name = GameUtil.tongttXj[random.nextInt(GameUtil.tongttXj.length)];
		String typeStr = "修炼挑战阶段";
		if (chara.tongtiantaTask.getCurLayer() > chara.level) {
			typeStr = "突破挑战阶段";
		}
		// 设置通天塔模板
		chara.tongtiantaTask.setNpc(name);
		chara.tongtiantaTask.setCurType(1);
		chara.tongtiantaTask.setCurLayer(chara.tongtiantaTask.getCurLayer() + 1);
		GameObjectChar.send(new MSG_TONGTIANTA_INFO(), chara.tongtiantaTask);

		// 更新任务信息
		Vo_61553_0 vo_61553_0 = chara.commonTaskMap.get("通天塔");
		vo_61553_0.task_desc = org.apache.commons.lang3.StringUtils.join("凡诚心向道者，皆可在通天塔内潜心修炼，领悟天道。\n当前正在进行的是#R通天塔" , typeStr , "。\n#n挑战目标：在通天塔内完成第#R"
				, chara.level , "层#n的挑战\n" , "当前挑战情况：在#R通天塔" , chara.tongtiantaTask.getCurLayer() , "层#n挑战#Y" , name
				, "#n。");
		vo_61553_0.task_prompt = org.apache.commons.lang3.StringUtils.join("挑战#P" , name , "|M=麻烦星君多多指教@P北斗星使|M=【进塔】进入通天塔#P");
		chara.commonTaskMap.put("通天塔", vo_61553_0);
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
		if(isAutoWalk.length==0) {
			// 自动行走
			GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
		}
	}

	/**
	 * 通天塔挑战失败
	 */
	public static void tongtiantaFightFail() {
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		// 开始自我突破的修炼
		Vo_TONGTIANTA_INFO tongtiantaTask = chara.tongtiantaTask;
		if (tongtiantaTask.getChallengeCount() > 0) {
			tongtiantaTask.setDieNumber(tongtiantaTask.getDieNumber() - 1);
			// 如果死亡超过三次则直接传送到天墉城北斗星使
			if (tongtiantaTask.getDieNumber() <= 0) {
				Map map = GameData.that.baseMapService.findOneByName("天墉城");
				// 将人物传送到北斗星使处
				chara.x = 114;
				chara.y = 16;
				GameLine.getGameMapname(chara.line, map.getName()).join(GameObjectChar.getGameObjectChar());
				// 清除任务
				chara.commonTaskMap.get("通天塔").show_name = "";
				chara.commonTaskMap.get("通天塔").task_prompt = "";
				GameObjectChar.send(new MSG_TASK_PROMPT(), chara.commonTaskMap.get("通天塔"));
				chara.tongtiantaTask = null;
				chara.commonTaskMap.remove("通天塔");

			} else {
				// 更新任务
				tongtiantaTask
						.setNpc(tongtiantaTask.getNpc().substring(0,
								tongtiantaTask.getNpc().indexOf("(") == -1 ? tongtiantaTask.getNpc().length()
										: tongtiantaTask.getNpc().indexOf("("))
								+ "(剩余" + tongtiantaTask.getDieNumber() + "次)");
				GameObjectChar.send(new MSG_TONGTIANTA_INFO(), tongtiantaTask);
			}
		}
	}

	/**
	 * 挑战超级boss
	 * 
	 * @param chara
	 */
	public static void gotoSuperBossFight(Chara chara, boolean isSuper) {
		String[] boss = null;
		if(isSuper) {
			//
			boss = new String[]{ "超级魔化朱雀:魔化小朱雀", "超级魔化玄武:魔化小玄武", "超级魔化青龙:魔化小青龙", "超级魔化疆良:魔化小疆良" };
		}else {
			boss = new String[]{ "赤血魔猿:赤血幼猿", "魅影蝎后:魅影毒蝎", "血炼魔猪:血幻豪猪", "黑熊妖皇:黑熊小弟" };
		}
		// 如果队长完成了就没必要继续下面的逻辑了
		if ((chara.superBossNum + 1) > GameConfig.config.getBaseConfig().getSuperBossNum()) {
			GameUtil.sendMeTips("你已完成今日的挑战！");
			return;
		}
		StringBuilder msg = new StringBuilder();
		msg.append("队伍中[");
		boolean flag = false;
		if (GameCommonUtil.isNotGameTeam(GameObjectChar.getGameObjectChar().gameTeam)) {
			// 判断队伍是否有人已经超限了
			for (Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
				if ((duiwu.superBossNum + 1) > GameConfig.config.getBaseConfig().getSuperBossNum()
						&& duiwu.id != chara.id) {
					msg.append("#Y").append(duiwu.name).append(",");
					flag = true;
				}
				if (duiwu.level < 100) {
					GameUtil.sendMeTips("队伍中有低于100级的队员");
					return;
				}
			}
		} else {
			GameUtil.sendMeTips("挑战较难请组队进行。");
			return;
		}
		if (flag) {
			msg.append("#n]已完成今日的挑战！");
			msg.replace(msg.lastIndexOf(","), msg.lastIndexOf(",") + 1, "");
			GameUtil.sendMeTips(msg.toString());
			return;
		}
		// 增加次数
//		for (Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
//			duiwu.superBossNum++;
//		}
		List<String> monsters = new ArrayList<>();
		String bossNameArr = boss[new Random().nextInt(boss.length)];
		String superBossName = bossNameArr.split(":")[0];
		String lowBossName = bossNameArr.split(":")[1];
		monsters.add(superBossName);
		for (int i = 0; i < 9; i++) {
			monsters.add(lowBossName);
		}
		// 随机选出一个boss进行战斗
		FightManager.activeBoosGoFight(chara, monsters, false);

	}

	/**
	 * 胜利之后给奖励
	 * 
	 * @param chara
	 * @param name
	 * @return
	 */
	public static boolean fightVictoryInfo(Chara chara, String name) {
		return fightVictoryOrDieInfo(chara, name, 0);
	}
	/**
	 * 胜利之后给奖励
	 *
	 * @param chara
	 * @param name
	 * @return
	 */
	public static boolean fightVictoryInfo2(Chara chara, String name,Vo_APPEAR xing) {
		return fightVictoryOrDieInfo2(chara, name, 0,xing);
	}

	/**
	 * 失败之后的惩罚
	 * 
	 * @param chara
	 * @param name
	 * @return
	 */
	public static boolean fightDieInfo(Chara chara, String name) {
		return fightVictoryOrDieInfo(chara, name, 1);
	}

	/**
	 * 胜利和失败之后的奖励
	 * 
	 * @param charas
	 */
	private static boolean fightVictoryOrDieInfo(Chara chara, String name, int type) {
		if (chara != null) {
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
			VictoryDieReward vdr = GameData.that.victoryDieRewardService.victoryOrDieInfo(name, type);
			if (vdr == null) {
				return false;
			}
			/* 胜利奖励 */
			if (vdr.getType() == 0) {
				// 积分
				if (vdr.getScore() != null && vdr.getScore() > 0) {
					GameUtil.addchargeScore(gameObjectChar, vdr.getScore(), name);
				}
				// 经验
				if (vdr.getExp() != null && vdr.getExp() > 0) {
					// 如果角色在试道场,则不会获取经验.
					GameUtil.huodejingyan(chara, vdr.getExp(), name);
				}
				// 道行
				if (vdr.getTao() != null && vdr.getTao() > 0) {
					// 如果角色在试道场,则不会获取道行.
					int tao = vdr.getTao() * 1440;
					GameUtil.adddaohang(chara, tao, name);
				}
				// 武学
				if (vdr.getWuxue() != null && vdr.getWuxue() > 0) {
					// 如果角色在试道场,不会获取武学奖励.
					GameCommonUtil.addWuXue(chara, vdr.getWuxue(), name);
				}
				// 金元宝
				if (vdr.getGoldCoin() != null && vdr.getGoldCoin() > 0) {
					GameUtil.addJinYuanBao(gameObjectChar, vdr.getGoldCoin(), name);
				}
				// 银元宝
				if (vdr.getSilverCoin() != null && vdr.getSilverCoin() > 0) {
					GameUtil.addYinYuanBao(gameObjectChar, vdr.getSilverCoin(), name);
				}

				// 道具
				if (!StringUtils.isNullOrEmpty(vdr.getDaoju())) {
					String[] daojuArr = vdr.getDaoju().split(";");
					if (daojuArr != null && daojuArr.length > 1) {
						String[] daojuNameArr = daojuArr[0].split(",");
						String types = daojuArr[1];
						// 如果是随机
						if ("随机".equals(types)) {
							String string = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
							if("太阴之气".equals(string)) {
								GameUtil.getTyzqRandomAttr(gameObjectChar);
							}else {
								GameUtil.huodedaoju(chara, string, 1);
							}
							GameCommonUtil.sendTips("你获得了#R" + string, chara.id);
						} else if ("随机2".equals(types)) {
							if (ThreadLocalRandom.current().nextBoolean()) {
								String string = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
								if("太阴之气".equals(string)) {
									GameUtil.getTyzqRandomAttr(gameObjectChar);
								}else {
									GameUtil.huodedaoju(chara, string, 1);
								}
								GameCommonUtil.sendTips("你获得了#R" + string, gameObjectChar);
							}
						} else if ("全部".equals(types)) {
							for (String d : daojuNameArr) {
								if("太阴之气".equals(d)) {
									GameUtil.getTyzqRandomAttr(gameObjectChar);
								}else {
									GameUtil.huodedaoju(chara, d, 1);
								}
								GameCommonUtil.sendTips("你获得了#R" + d, gameObjectChar);
							}
						}
					}
				}
				
				//宠物 	朱雀(神兽)$满,太极熊(坐骑)$8;随机
				if (!StringUtils.isNullOrEmpty(vdr.getPet())) {
					try {
						String[] arr = vdr.getPet().split(";");
						if (arr != null && arr.length > 1) {
							String[] nameArr = arr[0].split(",");
							//类型
							String types = arr[1];
							if ("随机".equals(types)) {
								//内容
								String content = nameArr[new Random().nextInt(nameArr.length)];
								//宠物信息
								String[] petInfo = content.split("\\$");
								if(petInfo.length>0) {
									String petName = petInfo[0].substring(0, petInfo[0].indexOf("("));
									if(petInfo[0].contains("神兽")) {
										GameUtil.huodeshenshou(chara, petName, name);
									}else if(petInfo[0].contains("变异")) {
										GameUtil.huodebianyi(chara, petName, name);
									}else if(petInfo[0].contains("宝宝")) {
										if(petInfo[0].contains("满")) {
											GameUtil.huodemanchongwu(chara, petName, 2, name);
										}else {
											GameUtil.huodechongwu(chara, petName, 2, name);
										}
									}
								}
							}else if ("全部".equals(types)) {
								for (String content : nameArr) {
									//宠物信息
									String[] petInfo = content.split("\\$");
									String petName = petInfo[0].substring(0, petInfo[0].indexOf(")"));
									if(petInfo[0].contains("神兽")) {
										GameUtil.huodeshenshou(chara, petName, name);
									}else if(petInfo[0].contains("变异")) {
										GameUtil.huodebianyi(chara, petName, name);
									}else if(petInfo[0].contains("宝宝")) {
										if(petInfo[0].contains("满")) {
											GameUtil.huodemanchongwu(chara, petName, 2, name);
										}else {
											GameUtil.huodechongwu(chara, petName, 2, name);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log.error(name, e);
					}
				}
				GameUtil.sendUpdate(chara);
			} else if (vdr.getType() == 1) {
				// 失败
				StringBuilder msg = new StringBuilder();
				msg.append("你死亡了因此损失了");
				if (vdr.getScore() != null && vdr.getScore() > 0) {
					chara.chargeScore -= vdr.getScore();
			GameUtilRenWu.refshPointTask(chara);

					if (chara.chargeScore < 0) {
						chara.chargeScore = 0;
					}
					msg.append(",#R").append(vdr.getScore()).append("#n点充值积分");
				}
				// 经验
				if (vdr.getExp() != null && vdr.getExp() > 0) {
					chara.exp -= vdr.getExp();
					msg.append(",#R").append(vdr.getExp()).append("#n经验");
				}
				// 道行
				if (vdr.getTao() != null && vdr.getTao() > 0) {
					chara.tao -= vdr.getTao();
					if (chara.tao < 0) {
						chara.tao = 0;
					}
					msg.append(",#R").append(GameUtil.fmtDh(vdr.getTao() * 1440)).append("#n道行");
				}
				// 金元宝
				if (vdr.getGoldCoin() != null && vdr.getGoldCoin() > 0) {
					chara.goldCoin -= vdr.getGoldCoin();
					if (chara.goldCoin < 0) {
						chara.goldCoin = 0;
					}
					msg.append(",#R").append(vdr.getGoldCoin()).append("#n金元宝");
				}
				// 银元宝
				if (vdr.getSilverCoin() != null && vdr.getSilverCoin() > 0) {
					chara.silverCoin -= vdr.getSilverCoin();
					if (chara.silverCoin < 0) {
						chara.silverCoin = 0;
					}
					msg.append(",#R").append(vdr.getSilverCoin()).append("#n银元宝");
				}
				// 道具
				if (!StringUtils.isNullOrEmpty(vdr.getDaoju())) {
					String[] daojuArr = vdr.getDaoju().split(";");
					if (daojuArr != null && daojuArr.length > 1) {
						String[] daojuNameArr = daojuArr[0].split(",");
						String types = daojuArr[1];
						// 如果是随机
						if ("随机".equals(types)) {
							String removeDaoju = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
							GameUtil.removemunber(chara, removeDaoju, 1);
							msg.append(",#R").append(removeDaoju).append("#n");
						} else if ("全部".equals(types)) {
							for (String d : daojuNameArr) {
								GameUtil.removemunber(chara, d, 1);
								msg.append(",#R").append(d).append("#n");
							}
						}
					}
				}
				GameCommonUtil.sendTips(msg.toString(), chara.id);
				GameUtil.sendUpdate(chara);
				// 死亡弹窗
				Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
				vo_9129_2.notify = ClientButtonIdConst.NOTIFY_OPEN_DLG;
				vo_9129_2.para = "DeadRemindDlg";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2, chara.id);
				// 死亡记录
				GameCommonUtil.addCharaTrail(chara, "死亡", "1", name);
				// PK值减一
				if (chara.forcePk > 0) {
					chara.forcePk -= 1;
					// 如果==0
					Vo_61553_0 task = chara.taskMap.get("孽债血海");
					if (chara.forcePk == 0) {
						// 删除任务
						if (task != null) {
							GameUtilRenWu.removeTask("孽债血海", chara);
						}
					} else {
						// 更新任务
						if (task != null) {
							String des = "你当前拥有#R%d#n点PK值，受到大家排斥，在各大药店，杂货店以及便捷购买入口购买物品时，价格会变高。参与有死亡惩罚的战斗死亡一次可以减少1点PK值。";
							task.task_desc = String.format(des, chara.forcePk);
							task.task_prompt = "当前拥有#R" + chara.forcePk + "#n点PK值，可前往#P无念僧#P消除";
							// 更新任务
							GameUtilRenWu.createTask(task, chara);
						}
					}
				}
			}
		}
		return true;
	}
	private static boolean fightVictoryOrDieInfo2(Chara chara, String name, int type,Vo_APPEAR xing) {
		if (chara != null) {
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
			VictoryDieReward vdr = GameData.that.victoryDieRewardService.victoryOrDieInfo(name, type);
			if (vdr == null) {
				return false;
			}
			/* 胜利奖励 */
			if (vdr.getType() == 0) {
				int lv = xing.level;
				Double lvcha = (lv -70)*0.03;
				// 积分
				if (vdr.getScore() != null && vdr.getScore() > 0) {
					int score = (int)Math.round(vdr.getScore()*(1+lvcha));
					GameUtil.addchargeScore(gameObjectChar, score, name);
				}
				// 经验
				if (vdr.getExp() != null && vdr.getExp() > 0) {
					int exp = (int)Math.round(vdr.getExp()*(1+lvcha));
					// 如果角色在试道场,则不会获取经验.
					GameUtil.huodejingyan(chara, exp, name);
				}
				// 道行
				if (vdr.getTao() != null && vdr.getTao() > 0) {
					// 如果角色在试道场,则不会获取道行.
					int tao = vdr.getTao() * 1440;
					int tao2 = (int)Math.round(tao*(1+lvcha));
					GameUtil.adddaohang(chara, tao2, name);
				}
				// 武学
				if (vdr.getWuxue() != null && vdr.getWuxue() > 0) {
					// 如果角色在试道场,不会获取武学奖励.
					int wuxue = (int)Math.round(vdr.getWuxue()*(1+lvcha));
					GameCommonUtil.addWuXue(chara, wuxue, name);
				}
				// 金元宝
				if (vdr.getGoldCoin() != null && vdr.getGoldCoin() > 0) {
					int jin = (int)Math.round(vdr.getGoldCoin()*(1+lvcha));
					GameUtil.addJinYuanBao(gameObjectChar, jin, name);
				}
				// 银元宝
				if (vdr.getSilverCoin() != null && vdr.getSilverCoin() > 0) {
					int yin = (int)Math.round(vdr.getGoldCoin()*(1+lvcha));
					GameUtil.addYinYuanBao(gameObjectChar, yin, name);
				}

				// 道具
				if (!StringUtils.isNullOrEmpty(vdr.getDaoju())) {
					String[] daojuArr = vdr.getDaoju().split(";");
					if (daojuArr != null && daojuArr.length > 1) {
						String[] daojuNameArr = daojuArr[0].split(",");
						String types = daojuArr[1];
						// 如果是随机
						if ("随机".equals(types)) {
							String string = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
							if("太阴之气".equals(string)) {
								GameUtil.getTyzqRandomAttr(gameObjectChar);
							}else {
								GameUtil.huodedaoju(chara, string, 1);
							}
							GameCommonUtil.sendTips("你获得了#R" + string, chara.id);
						} else if ("随机2".equals(types)) {
							if (ThreadLocalRandom.current().nextBoolean()) {
								String string = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
								if("太阴之气".equals(string)) {
									GameUtil.getTyzqRandomAttr(gameObjectChar);
								}else {
									GameUtil.huodedaoju(chara, string, 1);
								}
								GameCommonUtil.sendTips("你获得了#R" + string, gameObjectChar);
							}
						} else if ("全部".equals(types)) {
							for (String d : daojuNameArr) {
								if("太阴之气".equals(d)) {
									GameUtil.getTyzqRandomAttr(gameObjectChar);
								}else {
									GameUtil.huodedaoju(chara, d, 1);
								}
								GameCommonUtil.sendTips("你获得了#R" + d, gameObjectChar);
							}
						}
					}
				}

				//宠物 	朱雀(神兽)$满,太极熊(坐骑)$8;随机
				if (!StringUtils.isNullOrEmpty(vdr.getPet())) {
					try {
						String[] arr = vdr.getPet().split(";");
						if (arr != null && arr.length > 1) {
							String[] nameArr = arr[0].split(",");
							//类型
							String types = arr[1];
							if ("随机".equals(types)) {
								//内容
								String content = nameArr[new Random().nextInt(nameArr.length)];
								//宠物信息
								String[] petInfo = content.split("\\$");
								if(petInfo.length>0) {
									String petName = petInfo[0].substring(0, petInfo[0].indexOf("("));
									if(petInfo[0].contains("神兽")) {
										GameUtil.huodeshenshou(chara, petName, name);
									}else if(petInfo[0].contains("变异")) {
										GameUtil.huodebianyi(chara, petName, name);
									}else if(petInfo[0].contains("宝宝")) {
										if(petInfo[0].contains("满")) {
											GameUtil.huodemanchongwu(chara, petName, 2, name);
										}else {
											GameUtil.huodechongwu(chara, petName, 2, name);
										}
									}
								}
							}else if ("全部".equals(types)) {
								for (String content : nameArr) {
									//宠物信息
									String[] petInfo = content.split("\\$");
									String petName = petInfo[0].substring(0, petInfo[0].indexOf(")"));
									if(petInfo[0].contains("神兽")) {
										GameUtil.huodeshenshou(chara, petName, name);
									}else if(petInfo[0].contains("变异")) {
										GameUtil.huodebianyi(chara, petName, name);
									}else if(petInfo[0].contains("宝宝")) {
										if(petInfo[0].contains("满")) {
											GameUtil.huodemanchongwu(chara, petName, 2, name);
										}else {
											GameUtil.huodechongwu(chara, petName, 2, name);
										}
									}
								}
							}
						}
					} catch (Exception e) {
						log.error(name, e);
					}
				}
				GameUtil.sendUpdate(chara);
			} else if (vdr.getType() == 1) {
				// 失败
				StringBuilder msg = new StringBuilder();
				msg.append("你死亡了因此损失了");
				if (vdr.getScore() != null && vdr.getScore() > 0) {
					chara.chargeScore -= vdr.getScore();
					GameUtilRenWu.refshPointTask(chara);

					if (chara.chargeScore < 0) {
						chara.chargeScore = 0;
					}
					msg.append(",#R").append(vdr.getScore()).append("#n点充值积分");
				}
				// 经验
				if (vdr.getExp() != null && vdr.getExp() > 0) {
					chara.exp -= vdr.getExp();
					msg.append(",#R").append(vdr.getExp()).append("#n经验");
				}
				// 道行
				if (vdr.getTao() != null && vdr.getTao() > 0) {
					chara.tao -= vdr.getTao();
					if (chara.tao < 0) {
						chara.tao = 0;
					}
					msg.append(",#R").append(GameUtil.fmtDh(vdr.getTao() * 1440)).append("#n道行");
				}
				// 金元宝
				if (vdr.getGoldCoin() != null && vdr.getGoldCoin() > 0) {
					chara.goldCoin -= vdr.getGoldCoin();
					if (chara.goldCoin < 0) {
						chara.goldCoin = 0;
					}
					msg.append(",#R").append(vdr.getGoldCoin()).append("#n金元宝");
				}
				// 银元宝
				if (vdr.getSilverCoin() != null && vdr.getSilverCoin() > 0) {
					chara.silverCoin -= vdr.getSilverCoin();
					if (chara.silverCoin < 0) {
						chara.silverCoin = 0;
					}
					msg.append(",#R").append(vdr.getSilverCoin()).append("#n银元宝");
				}
				// 道具
				if (!StringUtils.isNullOrEmpty(vdr.getDaoju())) {
					String[] daojuArr = vdr.getDaoju().split(";");
					if (daojuArr != null && daojuArr.length > 1) {
						String[] daojuNameArr = daojuArr[0].split(",");
						String types = daojuArr[1];
						// 如果是随机
						if ("随机".equals(types)) {
							String removeDaoju = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
							GameUtil.removemunber(chara, removeDaoju, 1);
							msg.append(",#R").append(removeDaoju).append("#n");
						} else if ("全部".equals(types)) {
							for (String d : daojuNameArr) {
								GameUtil.removemunber(chara, d, 1);
								msg.append(",#R").append(d).append("#n");
							}
						}
					}
				}
				GameCommonUtil.sendTips(msg.toString(), chara.id);
				GameUtil.sendUpdate(chara);
				// 死亡弹窗
				Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
				vo_9129_2.notify = ClientButtonIdConst.NOTIFY_OPEN_DLG;
				vo_9129_2.para = "DeadRemindDlg";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2, chara.id);
				// 死亡记录
				GameCommonUtil.addCharaTrail(chara, "死亡", "1", name);
				// PK值减一
				if (chara.forcePk > 0) {
					chara.forcePk -= 1;
					// 如果==0
					Vo_61553_0 task = chara.taskMap.get("孽债血海");
					if (chara.forcePk == 0) {
						// 删除任务
						if (task != null) {
							GameUtilRenWu.removeTask("孽债血海", chara);
						}
					} else {
						// 更新任务
						if (task != null) {
							String des = "你当前拥有#R%d#n点PK值，受到大家排斥，在各大药店，杂货店以及便捷购买入口购买物品时，价格会变高。参与有死亡惩罚的战斗死亡一次可以减少1点PK值。";
							task.task_desc = String.format(des, chara.forcePk);
							task.task_prompt = "当前拥有#R" + chara.forcePk + "#n点PK值，可前往#P无念僧#P消除";
							// 更新任务
							GameUtilRenWu.createTask(task, chara);
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * a使用烟花
	 * 
	 * @param gameObjectChar 玩家
	 * @param goods          烟花
	 * @param type           类型
	 */
	public static void useFireworks(GameObjectChar gameObjectChar, Goods goods, String type) {
		if (gameObjectChar != null) {
			Chara chara = gameObjectChar.chara;
			if (GameCommonUtil.getGoodsNum(chara, goods.goodsInfo.str) <= 0) {
				GameUtil.sendMeTips("道具不足无法触发战斗！");
				return;
			}
			List<Chara> charas = new ArrayList<>();
			// 组队了
			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				// 队员消耗
				ConfigInfo teamCost = GameData.that.configInfoService
						.getOneByKeyName("new_year_beast_team_cost_status");
				// 如果为空null,则默认消耗队长
				if (teamCost == null || teamCost.getData().equals("关闭")) {
					charas.add(chara);
				} else if (teamCost.getData().equals("开启")) {
					StringBuilder teamMsg = new StringBuilder("队伍中《");
					boolean isNotAvailableCost = false;
					// 校验队员是否有道具
					for (Chara team : gameObjectChar.gameTeam.duiwu) {
						// 排除队长
						if (team.id == chara.id) {
							continue;
						}
						if (GameCommonUtil.getGoodsNum(team, goods.goodsInfo.str) <= 0) {
							isNotAvailableCost = true;
							teamMsg.append("#Y").append(team.name).append("#n,");
							// 发出警告信息
							GameCommonUtil.sendTips("你背包中#R" + goods.goodsInfo.str + "#n不足，无法触发战斗，请及时补充！", team.id);
						}
					}
					String msg = teamMsg.substring(0, teamMsg.length() - 1);
					msg += "》等人道具不足无法触发战斗！";
					// 队伍中有成员道具不够,无法触发战斗
					if (isNotAvailableCost) {
						GameUtil.sendMeTips(msg);
						return;
					}
					charas.addAll(gameObjectChar.gameTeam.duiwu);
				}
			} else {
				charas.add(chara);
			}
			for (Chara c : charas) {
				if (c.id == chara.id) {
					GameUtil.removemunber(c, goods, 1);
					GameUtil.sendMeTips("你使用了#R" + goods.goodsInfo.str);
				} else {
					GameUtil.removemunber(c, goods.goodsInfo.str, 1);
					GameCommonUtil.sendTips("你消耗了一个#R" + goods.goodsInfo.str, c.id);
				}
			}
			if ("人气烟花·满天星雨".equals(type)) {
				gameObjectChar.gameMap.send(new MSG_ANIMATE_IN_CHAR_LAYER(), GameCommonUtil.charaPlay(chara, 1289, 3));
			} else if ("人气烟花·绚丽彩焰".equals(type)) {
				GameObjectChar.sendduiwu(new MSG_ANIMATE_IN_CHAR_LAYER(), GameCommonUtil.charaPlay(chara, 2036, 3),
						chara.id);
			}
			// 星期
			ConfigInfo new_year_beast_week = GameData.that.configInfoService.getOneByKeyName("new_year_beast_week");
			if (new_year_beast_week == null) {
				return;
			}
			if (!GameUtilRenWu.dateToWeekDay(new_year_beast_week.getData().split(","))) {
				log.info("活动星期不满足");
				return;
			}
			// 判断时间
			ConfigInfo new_year_beast_time = GameData.that.configInfoService.getOneByKeyName("new_year_beast_time");
			if (new_year_beast_time == null) {
				return;
			}
			String times = new_year_beast_time.getData();
			String[] split = times.split("~");
			String startTimeStr = split[0].trim();
			String endTimeStr = split[1].trim();
			if (!GameUtilRenWu.belongCalendarTime(startTimeStr, endTimeStr)) {
				log.info("活动时间不满足");
				return;
			}

			// 地图
			ConfigInfo newYearBeastMaps = GameData.that.configInfoService.getOneByKeyName("new_year_beast_maps");
			String maps = "北海沙滩";
			if (newYearBeastMaps != null) {
				maps = newYearBeastMaps.getData();
			}
			boolean isFind = false;
			// 玩家必须在指定地点
			for (String map : maps.split("、")) {
				if (chara.mapName.equals(map)) {
					isFind = true;
					break;
				}
			}
			// 必须是指定地图并且等级大于39
			if (isFind && chara.level >= 40) {
				// 活动开启状态
				ConfigInfo newYearBeastStatus = GameData.that.configInfoService
						.getOneByKeyName("new_year_beast_status");
				if (newYearBeastStatus != null && "开启".equals(newYearBeastStatus.getData())) {
					Vo_MENU_LIST vo_8247_0 = new Vo_MENU_LIST();
					vo_8247_0.id = 99999;
					vo_8247_0.portrait = 6162;
					vo_8247_0.pic_no = 1;
					vo_8247_0.content = "都给我让开，我要开始捣乱啦。[休要嚣张(经验奖励)/exp][休要嚣张(道行奖励)/tao][开玩笑的]";
					vo_8247_0.secret_key = "";
					vo_8247_0.name = "捣乱的夕";
					vo_8247_0.attrib = 1;
					GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_0);
				}
			}
		}
	}

	/**
	 * a战胜年兽
	 * 
	 * @param gameObjectChar
	 */
	public static void victoryNewYearBeast(GameObjectChar gameObjectChar) {
		if (gameObjectChar != null) {
			Chara chara = gameObjectChar.chara;
			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				// 有队伍
				for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
					if (teamChara.newYearBeastNum + 1 > GameConfig.config.getBaseConfig().getNewYearBeastNum()) {
						GameCommonUtil.sendTips("你今日已无奖励次数！", teamChara.id);
						continue;
					}
					teamChara.newYearBeastNum++;
					// 经验奖励
					if ("newYearBeastExp".equals(gameObjectChar.flag)) {
						GameActiveUtil.fightVictoryInfoForFilter(teamChara, "年兽活动", "道行", "武学");
					} else if ("newYearBeastTao".equals(gameObjectChar.flag)) { // 道行奖励
						GameActiveUtil.fightVictoryInfoForFilter(teamChara, "年兽活动", "经验");
					}
					CMD_SELECT_MENU_ITEM.refreshTask(teamChara);
				}
			} else {
				if (chara.newYearBeastNum + 1 > GameConfig.config.getBaseConfig().getNewYearBeastNum()) {
					GameCommonUtil.sendTips("你今日已无奖励次数！", gameObjectChar);
					return;
				}
				chara.newYearBeastNum++;
				CMD_SELECT_MENU_ITEM.refreshTask(chara);
				// 经验奖励
				if ("newYearBeastExp".equals(gameObjectChar.flag)) {
					GameActiveUtil.fightVictoryInfoForFilter(chara, "年兽活动", "道行", "武学");
				} else if ("newYearBeastTao".equals(gameObjectChar.flag)) { // 道行奖励
					GameActiveUtil.fightVictoryInfoForFilter(chara, "年兽活动", "经验");
				}
			}
		}
	}

	/**
	 * 胜利之后给奖励
	 * 
	 * @param chara
	 * @param name
	 * @param filterRewards 过滤哪些类目
	 * @return
	 */
	public static boolean fightVictoryInfoForFilter(Chara chara, String name, String... filterRewards) {
		return fightVictoryOrDieInfoForFilter(chara, name, 0, filterRewards);
	}

	/**
	 * 失败之后的惩罚
	 * 
	 * @param chara
	 * @param name
	 * @param filterRewards 过滤哪些
	 * @return
	 */
	public static boolean fightDieInfoForFilter(Chara chara, String name, String... filterRewards) {
		return fightVictoryOrDieInfoForFilter(chara, name, 1, filterRewards);
	}

	/**
	 * a自选过滤哪些奖励或是惩罚类目
	 * 
	 * @param chara         玩家
	 * @param name          名字
	 * @param type          类型
	 * @param filterRewards 过滤哪些类目
	 * @return
	 */
	private static boolean fightVictoryOrDieInfoForFilter(Chara chara, String name, int type, String... filterRewards) {
		if (chara != null) {
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
			VictoryDieReward vdr = GameData.that.victoryDieRewardService.victoryOrDieInfo(name, type);
			if (vdr == null) {
				return false;
			}
			java.util.Map<String, String> switchTypesMap = new HashMap<>();
			for (String switchType : filterRewards) {
				switchTypesMap.put(switchType, switchType);
			}
			/* 胜利奖励 */
			if (vdr.getType() == 0) {
				// 积分
				if (vdr.getScore() != null && vdr.getScore() > 0 && switchTypesMap.get("积分") == null) {
					GameUtil.addchargeScore(gameObjectChar, vdr.getScore(),vdr.getName());
				}
				// 经验
				if (vdr.getExp() != null && vdr.getExp() > 0 && switchTypesMap.get("经验") == null) {
					GameUtil.huodejingyan(chara, vdr.getExp(), vdr.getName());
				}
				// 道行
				if (vdr.getTao() != null && vdr.getTao() > 0 && switchTypesMap.get("道行") == null) {
					GameUtil.adddaohang(chara, (vdr.getTao() * 1440), vdr.getName());
				}
				// 金元宝
				if (vdr.getGoldCoin() != null && vdr.getGoldCoin() > 0 && switchTypesMap.get("金元宝") == null) {
					GameUtil.addJinYuanBao(gameObjectChar, vdr.getGoldCoin(),vdr.getName());
				}
				// 银元宝
				if (vdr.getSilverCoin() != null && vdr.getSilverCoin() > 0 && switchTypesMap.get("银元宝") == null) {
					GameUtil.addYinYuanBao(gameObjectChar, vdr.getSilverCoin(), vdr.getName());
				}
				// 武学
				if (vdr.getWuxue() != null && vdr.getWuxue() > 0 && switchTypesMap.get("武学") == null) {
					GameCommonUtil.addWuXue(chara, vdr.getWuxue(), vdr.getName());
				}
				// 道具
				if (!StringUtils.isNullOrEmpty(vdr.getDaoju()) && switchTypesMap.get("道具") == null) {
					String[] daojuArr = vdr.getDaoju().split(";");
					if (daojuArr != null && daojuArr.length > 1) {
						String[] daojuNameArr = daojuArr[0].split(",");
						String types = daojuArr[1];
						// 如果是随机
						if ("随机".equals(types)) {
							String string = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
							GameUtil.huodedaoju(chara, string, 1);
							GameCommonUtil.sendTips("你获得了#R" + string, chara.id);
						} else if ("随机2".equals(types)) {
							if (ThreadLocalRandom.current().nextBoolean()) {
								String string = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
								GameUtil.huodedaoju(chara, string, 1);
								GameCommonUtil.sendTips("你获得了#R" + string, chara.id);
							}
						} else if ("全部".equals(types)) {
							for (String d : daojuNameArr) {
								GameUtil.huodedaoju(chara, d, 1);
								GameCommonUtil.sendTips("你获得了#R" + d, chara.id);
							}
						}
					}
				}
				GameUtil.sendUpdate(chara);
			} else if (vdr.getType() == 1) {
				// 失败
				StringBuilder msg = new StringBuilder();
				msg.append("你死亡了因此损失了");
				if (vdr.getScore() != null && vdr.getScore() > 0 && switchTypesMap.get("积分") == null) {
					chara.chargeScore -= vdr.getScore();
			GameUtilRenWu.refshPointTask(chara);

					if (chara.chargeScore < 0) {
						chara.chargeScore = 0;
					}
					msg.append(",#R").append(vdr.getScore()).append("#n点充值积分");
				}
				// 经验
				if (vdr.getExp() != null && vdr.getExp() > 0 && switchTypesMap.get("经验") == null) {
					chara.exp -= vdr.getExp();
					msg.append(",#R").append(vdr.getExp()).append("#n经验");
				}
				// 道行
				if (vdr.getTao() != null && vdr.getTao() > 0 && switchTypesMap.get("道行") == null) {
					chara.tao -= vdr.getTao();
					if (chara.tao < 0) {
						chara.tao = 0;
					}
					msg.append(",#R").append(GameUtil.fmtDh(vdr.getTao() * 1440)).append("#n道行");
				}
				// 金元宝
				if (vdr.getGoldCoin() != null && vdr.getGoldCoin() > 0 && switchTypesMap.get("金元宝") == null) {
					chara.goldCoin -= vdr.getGoldCoin();
					if (chara.goldCoin < 0) {
						chara.goldCoin = 0;
					}
					msg.append(",#R").append(vdr.getGoldCoin()).append("#n金元宝");
				}
				// 银元宝
				if (vdr.getSilverCoin() != null && vdr.getSilverCoin() > 0 && switchTypesMap.get("银元宝") == null) {
					chara.silverCoin -= vdr.getSilverCoin();
					if (chara.silverCoin < 0) {
						chara.silverCoin = 0;
					}
					msg.append(",#R").append(vdr.getSilverCoin()).append("#n银元宝");
				}
				// 道具
				if (!StringUtils.isNullOrEmpty(vdr.getDaoju()) && switchTypesMap.get("道具") == null) {
					String[] daojuArr = vdr.getDaoju().split(";");
					if (daojuArr != null && daojuArr.length > 1) {
						String[] daojuNameArr = daojuArr[0].split(",");
						String types = daojuArr[1];
						// 如果是随机
						if ("随机".equals(types)) {
							String removeDaoju = daojuNameArr[new Random().nextInt(daojuNameArr.length)];
							GameUtil.removemunber(chara, removeDaoju, 1);
							msg.append(",#R").append(removeDaoju).append("#n");
						} else if ("全部".equals(types)) {
							for (String d : daojuNameArr) {
								GameUtil.removemunber(chara, d, 1);
								msg.append(",#R").append(d).append("#n");
							}
						}
					}
				}
				GameCommonUtil.sendTips(msg.toString(), chara.id);
				GameUtil.sendUpdate(chara);
				// 死亡弹窗
				Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
				vo_9129_2.notify = ClientButtonIdConst.NOTIFY_OPEN_DLG;
				vo_9129_2.para = "DeadRemindDlg";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2, chara.id);
			}
		}
		return true;
	}
	
	/**
	 * 检测当前玩家是否杀人是否进入安全区域
	 * @param gameObjectChar
	 * @return
	 */
	public static boolean isEnterSafeArea(GameObjectChar gameObjectChar) {
		boolean flag = false;
		if(gameObjectChar == null) {
			return flag;
		}
		//监狱地点
		int[][] point = {{17,25},{30,19},{48,24}};
		Chara chara = gameObjectChar.chara;
		//判断这个人本次是否进入安全区
		ForcePkConfig config = GameConfig.forcePkConfig;
		// 判断当前是否为安全区，那就把他拉进天牢并创建任务
		String securityMap = config.getSecurityMap();
		String name = gameObjectChar.gameMap.name;
		Vo_45056_0 vo_45056_0 = new Vo_45056_0();
		vo_45056_0.id = chara.id;
		vo_45056_0.name = "狱卒";
		vo_45056_0.portrait = 6050;
		vo_45056_0.pic_no = 6050;
		vo_45056_0.content = "你犯罪了，被关进监狱了";
		vo_45056_0.isComplete = 0;
		vo_45056_0.isInCombat = 0;
		vo_45056_0.playTime = 3;
		vo_45056_0.task_type = "坐牢";
		if (securityMap.contains(name)) {
			Vo_61553_0 vo_61553_2 = new Vo_61553_0();
			vo_61553_2.count = 1;
			vo_61553_2.task_type = "坐牢";
			vo_61553_2.task_desc = "你犯罪了！你需要在线待足#RTIME_LEFT#n才可以离开这里，也可以让好友到官府的监狱里向牢头支付赎金离开。如果你认为自己是无辜的，请联系GM。";
			vo_61553_2.task_prompt = "你现在正在监狱反省中，需要#RTIME_LEFT#n才能获释。";
			vo_61553_2.refresh = 1;
			vo_61553_2.task_end_time = (int) (System.currentTimeMillis()/1000L)+(int)chara.crimeTime;
			vo_61553_2.attrib = 0;
			vo_61553_2.reward = "";
			vo_61553_2.show_name = "坐牢";
			vo_61553_2.task_extra_para = "";
			vo_61553_2.task_state = "0";
			//判断是否为组队状态
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				//循环这个队伍
				Iterator<Vo_4121_0> iterator = gameObjectChar.gameTeam.zhanliduiyuan.iterator();
				List<GameObjectChar> teamGameObjectChars = new ArrayList<>();
				while(iterator.hasNext()) {
					Vo_4121_0 team = iterator.next();
					GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(team.id);
					teamGameObjectChars.add(teamGameObjectChar);
				}
				for(GameObjectChar teamGameObjectChar:teamGameObjectChars) {
					//经过审核此人有犯罪记录
					if(teamGameObjectChar.chara.crimeTime>0) {
						Chara teamChara = teamGameObjectChar.chara;
						//让他离开队伍并移送到监狱去
						GameTeamUtil.quitTeam(teamGameObjectChar);
						//随机选出一个监狱
						int nextInt = ThreadLocalRandom.current().nextInt(point.length);
						int[] pointArr = point[nextInt];
						//单人的话那就直接进监狱
						teamChara.x = pointArr[0];
						teamChara.y = pointArr[1];
						GameLine.getGameMap(teamChara.line, "监狱").join(teamGameObjectChar);
						vo_61553_2.task_end_time = (int) (System.currentTimeMillis()/1000L)+(int)teamChara.crimeTime;
						teamChara.taskMap.put("坐牢", vo_61553_2);
						//创建任务坐牢任务
						teamGameObjectChar.sendOne(new MSG_TASK_PROMPT(), vo_61553_2);
						GameCommonUtil.sendTips("你已被关进监狱！", teamGameObjectChar);
//						teamGameObjectChar.sendOne(new M45056_0(), vo_45056_0);
						flag = true;
					}
				}
			}else {
				if(chara.crimeTime>0) {
					//随机选出一个监狱
					int nextInt = ThreadLocalRandom.current().nextInt(point.length);
					int[] pointArr = point[nextInt];
					//单人的话那就直接进监狱
					chara.x = pointArr[0];
					chara.y = pointArr[1];
					GameLine.getGameMap(chara.line, "监狱").join(gameObjectChar);
					chara.taskMap.put("坐牢", vo_61553_2);
					//创建任务坐牢任务
					GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_2);
					GameCommonUtil.sendTips("你已被关进监狱！", gameObjectChar);
//					GameObjectChar.send(new M45056_0(), vo_45056_0);
					flag = true;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 劫狱成功
	 * @param gameObjectChar
	 */
	public static void zuolaoJieYuSuccess(GameObjectChar gameObjectChar, int id) {
		Chara chara = gameObjectChar.chara;
		Vo_61553_0 task = chara.taskMap.get("将功补过");
		if(task == null) {
			GameUtil.sendMeTips("求情失败，任务不存在");
			return;
		}
		String toUUID = task.task_extra_para;
		//被救的玩家
		GameObjectChar toGameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(toUUID);
		if(toGameObjectChar == null) {
			GameUtil.sendMeTips("求情失败，被救的玩家不在线");
			return;
		}
		if(toGameObjectChar.chara.crimeTime>0 && toGameObjectChar.chara.taskMap.get("坐牢") != null) {
			toGameObjectChar.chara.crimeTime = 0;
			//释放该犯人
			toGameObjectChar.chara.isNameRed = 0;
			//删除任务
			GameUtilRenWu.removeTask("坐牢", toGameObjectChar.chara);
			toGameObjectChar.chara.x = 26;
			toGameObjectChar.chara.y = 30;
			GameLine.getGameMap(chara.line, "监狱").join(toGameObjectChar);
			GameCommonUtil.sendTips("你被#Y"+gameObjectChar.chara.name+"#n救了", toGameObjectChar);
		}
		Vo_APPEAR vo_APPEAR = GameCore.jieyuMonster.get(id);
		if(vo_APPEAR != null) {
			GameCore.jieyuMonster.remove(id);
			GameLine.getGameMap(chara.line, vo_APPEAR.mapid).send(new MSG_DISAPPEAR(), id);
		}
		//删除缓存
		GameData.that.redisUtils.delete("jieyu_tufei:"+chara.uuid+":"+id);
		//删除任务
		GameUtilRenWu.removeTask("将功补过", chara);
	}
	
	/**
	 * 擂台PK结算
	 * @param fightContainer
	 */
	public static void ctPkOver(FightContainer fightContainer) {
		List<FightTeam> teamList = fightContainer.teamList;
		// 队伍信息
		java.util.Map<String, FightTeam> fightTeamInfo = GameCommonUtil.getFightTeamInfo(teamList);
		// PK胜利的队伍
		FightTeam victoryTeam = fightTeamInfo.get("victoryTeam");
		// PK失败的队伍
		FightTeam deadTeam = fightTeamInfo.get("deadTeam");
		//擂台配置信息
		CtConfig ctConfig = GameCore.ctConfig;
		if(ctConfig == null) {
			//未开通这个功能
			return;
		}
		//活动关闭了
		if(ctConfig.getState() == 0) {
			return;
		}
		String times = ctConfig.getTime();
		String[] split = times.split("~");
		String startTimeStr = split[0].trim();
		String endTimeStr = split[1].trim();
		//活动时间不满足
		if (!GameUtilRenWu.belongCalendarTime(startTimeStr, endTimeStr)) {
			return;
		}
		//队伍
		List<GameObjectChar> victoryTeams = new ArrayList<>();
		List<GameObjectChar> deadTeams = new ArrayList<>();
		//队伍所有等级，用于等级排序
		List<Integer> victoryLevel = new ArrayList<>();
		List<Integer> deadTeamsLevel = new ArrayList<>();
		// 胜利的队伍信息
		if (victoryTeam != null) {
			if (victoryTeam.fightObjectList != null && !victoryTeam.fightObjectList.isEmpty()) {
				List<FightObject> teamLeader = victoryTeam.fightObjectList;
				if(teamLeader != null && !teamLeader.isEmpty()) {
					for (FightObject fig : teamLeader) {
						if(fig.type == 1) {
							GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fig.getId());
							if (gameObjectChar != null) {
								victoryTeams.add(gameObjectChar);
								victoryLevel.add(gameObjectChar.chara.level);
								gameObjectChar.action = "";
							}
						}
					}
				}
			}
		}
		// 失败的队伍信息
		if (deadTeam != null) {
			if (deadTeam.fightObjectList != null && !deadTeam.fightObjectList.isEmpty()) {
				List<FightObject> teamLeader = deadTeam.fightObjectList;
				if(teamLeader != null && !teamLeader.isEmpty()) {
					for(FightObject fightObject:teamLeader) {
						if(fightObject.type == 1) {
							GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightObject.getId());
							if (gameObjectChar != null) {
								deadTeams.add(gameObjectChar);
								deadTeamsLevel.add(gameObjectChar.chara.level);
								gameObjectChar.action = "";
							}
						}
					}
				}
			}
		}
		
		if(!victoryTeams.isEmpty() && !deadTeams.isEmpty()) {
			//胜利者最大等级
			Integer victoryLevelMax = Collections.max(victoryLevel);
			//失败者最大等级
			Integer deadTeamsLevelMax = Collections.max(deadTeamsLevel);
			//两个队伍最高等级级差
			int disparityLevel = Math.abs(victoryLevelMax-deadTeamsLevelMax);
			//奖励差
			int value = ctConfig.getRewardValue()/5;
			if(value <= 0) {
				value = 1;
			}
			int reward = value*deadTeams.size();
			//失败
			for(GameObjectChar gameObjectChar:deadTeams) {
				Chara chara = gameObjectChar.chara;
				if(disparityLevel>ctConfig.getDisparityLevel()) {
					GameCommonUtil.sendTips("进行等级差积分修正，你被扣除了#R0#n点擂台积分", gameObjectChar);
					continue;
				}
				//如果有某人小于这个奖励数值的，那胜利者获得修正后的奖励
				if(chara.ctDataScore<=-200) {
					reward-=value;
					GameCommonUtil.sendTips("擂台切磋失败，你被扣除了#R0#n点擂台积分", gameObjectChar);
					continue;
				}
				chara.ctDataScore-=value;
				chara.ctDataScoreCost-=value;
				GameCommonUtil.sendTips("擂台切磋失败，你被扣除了#R"+value+"#n点擂台积分", gameObjectChar);
			}
			if(reward<0) {
				reward = 0;
			}
			//胜利
			for(GameObjectChar gameObjectChar:victoryTeams) {
				Chara chara = gameObjectChar.chara;
				if(disparityLevel>ctConfig.getDisparityLevel()) {
					GameCommonUtil.sendTips("进行等级差积分修正，你获得了#R0#n点擂台积分", gameObjectChar);
					continue;
				}
				//判断次数是否满足
				if(chara.getCtCount()+1>ctConfig.getRewardNum()) {
					GameCommonUtil.sendTips("今日擂台挑战已无奖励次数，你获得了#R0#n点擂台积分", gameObjectChar);
					continue;
				}
				chara.ctCount+=1;
				chara.ctDataScore+=reward;
				chara.ctDataScoreCost+=reward;
				GameCommonUtil.sendTips("积分修正后，你获得了#R"+reward+"#n点擂台积分", gameObjectChar);
			}
		}
		
	}
	
//yaota
	public static String isOpenYaota() {
		ConfigInfo taoziLuobo = GameData.that.configInfoService.getOneByUuid("yaota");
		StringBuilder obj = new StringBuilder();
		if(taoziLuobo != null) {
			String data = taoziLuobo.getData();
			JSONObject parseObject = JSONObject.parseObject(data);
			String time = parseObject.getString("time");
			String[] split = time.split("~");
			String startTimeStr = split[0].trim()+":00";
			String startTimeStr2 = split[1].trim()+":00";
			int state = parseObject.getIntValue("state");
			if(state == 1) {
				String format = "HH:mm:ss";
				Date startTime = DateUtil.parse(startTimeStr,format);
				Date endTime = DateUtil.parse(startTimeStr2,format);
				if(DateUtil.isEffectiveDate(startTime, endTime)) {
					obj.append(parseObject.getString("names"));
					obj.append(",").append("刷道");
				}
			}
		}
		return obj.toString().isEmpty()?null:obj.toString();
	}

	/**
	 * 是否开启全局双倍
	 * @param value
	 * @return
	 */
	public static String isOpenGlobalDouble() {
		ConfigInfo taoziLuobo = GameData.that.configInfoService.getOneByUuid("global_double");
		StringBuilder obj = new StringBuilder();
		if(taoziLuobo != null) {
			String data = taoziLuobo.getData();
			JSONObject parseObject = JSONObject.parseObject(data);
			String time = parseObject.getString("time");
			String[] split = time.split("~");
			String startTimeStr = split[0].trim()+":00";
			String startTimeStr2 = split[1].trim()+":00";
			int state = parseObject.getIntValue("state");
			if(state == 1) {
				String format = "HH:mm:ss";
				Date startTime = DateUtil.parse(startTimeStr,format);
				Date endTime = DateUtil.parse(startTimeStr2,format);
				if(DateUtil.isEffectiveDate(startTime, endTime)) {
					obj.append(parseObject.getString("names"));
					obj.append(",").append("刷道");
				}
			}
		}
		return obj.toString().isEmpty()?null:obj.toString();
	}
}