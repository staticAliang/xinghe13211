package com.fengshen.server.game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;

import com.fengshen.server.domain.PetShuXing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.Renwu;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.util.GameConfig;
import com.mysql.jdbc.StringUtils;

public class GameUtilRenWu {

	private static Logger log = LoggerFactory.getLogger(GameUtilRenWu.class);


	public static void refshPointTask(Chara chara) {
		GameUtilRenWu.createTask("信息", "#Y当前剩余积分：#R" + chara.getChargeScore() + "#Y点", "积分动态", chara,
				"你的当前剩余积分：#R" + chara.getChargeScore() + "#n点", "积分数量");
	}

	public static void petXinfaTask(Chara chara, PetShuXing petShuXing) {
		if (petShuXing != null && petShuXing.getXinFa() != null) {
			java.util.Map<String, Integer> xinFa = petShuXing.getXinFa();
			StringBuilder sb = new StringBuilder();
//            double petXinfaRate = GameConfig.config.getBaseConfig().getPetXinfaRate();
//            petXinfaRate = petXinfaRate / 100.0;
			int totalLevl = 0;
			for (java.util.Map.Entry<String, Integer> entry : xinFa.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (value == null || value == 0) {
					continue;
				}
				totalLevl += value;
				if ("def".equals(key)) {
					sb.append("气血：#Y").append(value).append("层#n 当前气血#R").append(petShuXing.def).append("#n#G（心法已加成）#n\n");
				} else if ("dex".equals(key)) {
					sb.append("法力：#Y").append(value).append("层#n 当前法力#R").append(petShuXing.dex).append("#n#G（心法已加成）#n\n");
				} else if ("accurate".equals(key)) {
					sb.append("物攻：#Y").append(value).append("层#n 当前物攻#R").append(petShuXing.accurate).append("#n#G（心法已加成）#n\n");
				} else if ("mana".equals(key)) {
					sb.append("法伤：#Y").append(value).append("层#n 当前法伤#R").append(petShuXing.mana).append("#n#G（心法已加成）#n\n");
				} else if ("parry".equals(key)) {
					sb.append("速度：#Y").append(value).append("层#n 当前速度#R").append(petShuXing.parry).append("#n#G（心法已加成）#n\n");
				} else if ("wiz".equals(key)) {
					sb.append("防御：#Y").append(value).append("层#n 当前防御#R").append(petShuXing.wiz).append("#n#G（心法已加成）#n\n");
				}
			}
			GameUtilRenWu.createTask("宠物心法", "#Y" + petShuXing.str + "#n心法：" + (totalLevl > 0 ? "#Y增幅生效中" : "增幅未生效"), "宠物心法", chara,
					"天地初开，鸿蒙伊始，鸿钧老祖为使仙宠更强大，集道家绝学。为宠物打造了一本绝世心法。道友可以去“东海渔村”灵兽异人处给宠物进行心法参悟或升阶！", sb.toString());
		} else {
			GameUtilRenWu.removeTask("宠物心法", chara);
		}
	}

	public static void xiLianTask(Chara chara) {
		if (chara.getXiLianInfoMap() != null) {
			int xilianOpen = GameConfig.config.getBaseConfig().getXilianOpen();
			int maxValue = GameConfig.config.getBaseConfig().getXiLianMaxValue();
			java.util.Map<String, Integer> xiLianInfoMap = chara.getXiLianInfoMap();
			int xiLianType = GameConfig.config.getBaseConfig().getXiLianType();
			String str1 = "所有基础属性#R+";
			String str2 = "%#n\n";

			if (xiLianType == 1) {
				str1 = "所有相性属性#R+";
				str2 = "#n\n";
			}
			StringBuilder sb = new StringBuilder();
			StringBuilder str = new StringBuilder();
			int total = 0;
			boolean shiZhaungXiLian = false, faBaoXiLian = false, peiShiXiLian = false, zuoQiXiLian = false;
			for (java.util.Map.Entry<String, Integer> entry : xiLianInfoMap.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (value == null || value == 0) {
					continue;
				}
				if (xilianOpen == 1) {
					if (xiLianType == 0) {
						value = (int) (value * 100.0 / (4 * maxValue));
					}
				} else {
					value = 0;
				}
				total += value;
				if ("shiZhaungXiLian".equals(key)) {
					shiZhaungXiLian = true;
					sb.append("时装洗炼：#Y").append(str1).append(value).append(str2);
				} else if ("faBaoXiLian".equals(key)) {
					faBaoXiLian = true;
					sb.append("法宝洗炼：#Y").append(str1).append(value).append(str2);
				} else if ("peiShiXiLian".equals(key)) {
					peiShiXiLian = true;
					sb.append("配饰洗炼：#Y").append(str1).append(value).append(str2);
				} else if ("zuoQiXiLian".equals(key)) {
					zuoQiXiLian = true;
					sb.append("坐骑洗炼：#Y").append(str1).append(value).append(str2);
				}
			}
			(shiZhaungXiLian ? str.append("#G") : str.append("#B")).append("时装#n ");
			(faBaoXiLian ? str.append("#G") : str.append("#B")).append("法宝#n ");
			(peiShiXiLian ? str.append("#G") : str.append("#B")).append("配饰#n ");
			(zuoQiXiLian ? str.append("#G") : str.append("#B")).append("坐骑#n ");

			GameUtilRenWu.createTask("洗练信息",  "#Y当前洗练已激活：" + str, "洗练信息", chara,
					"天地混沌，盘古开天，其经脉化山川，沐浴众生。 其灵魂已浸入道门各大仙器之中。道友可以通过洗练获取配饰中隐藏属性加成，让你在问道的世界中独领风骚！", sb.toString());
		} else {
			GameUtilRenWu.removeTask("洗练信息", chara);
		}
	}
	/**
	 * 创建任务
	 * 
	 * @param task_type
	 * @param task_prompt
	 * @param show_name
	 * @param chara
	 * @param extar
	 */
	public static void createTask(String task_type, String task_prompt, String show_name, Chara chara,
			String... extar) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = task_type;
		vo_61553_0.task_desc = extar != null && extar.length > 0 ? extar[0] : "";
		vo_61553_0.task_prompt = task_prompt;
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = extar != null && extar.length > 1 ? extar[1]
				: "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
		vo_61553_0.show_name = show_name;
		vo_61553_0.task_extra_para = extar != null && extar.length > 2 ? extar[2] : "";
		vo_61553_0.task_state = "tsxlStep1";
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
		chara.taskMap.put(task_type, vo_61553_0);
	}
	/**
	 * 创建不能放弃任务
	 */
	public static void createTask(final String task_type, int attrib, final String task_prompt, final String show_name, final Chara chara, final String... extar) {
		final Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = task_type;
		vo_61553_0.task_desc = ((extar != null && extar.length > 0) ? extar[0] : "");
		vo_61553_0.task_prompt = task_prompt;
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = attrib;
		vo_61553_0.reward = ((extar != null && extar.length > 1) ? extar[1] : "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I");
		vo_61553_0.show_name = show_name;
		vo_61553_0.task_extra_para = ((extar != null && extar.length > 2) ? extar[2] : "");
		vo_61553_0.task_state = "tsxlStep1";
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
		chara.taskMap.put(task_type, vo_61553_0);
	}

	
	/**
	 * 创建任务
	 * @param chara
	 * @param renwu
	 */
	public static void createTask(Chara chara, Renwu renwu) {
		if(renwu != null) {
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = renwu.getShowName();
			vo_61553_0.task_desc = renwu.getTaskDesc();
			vo_61553_0.task_prompt = renwu.getTaskPrompt();
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = renwu.getTaskEndTime();
			vo_61553_0.attrib = renwu.getAttrib();
			vo_61553_0.reward = renwu.getShowReward();
			vo_61553_0.show_name = renwu.getShowName();
			vo_61553_0.task_extra_para = renwu.getCurrentTask();
			vo_61553_0.task_state = renwu.getTaskState();
			vo_61553_0.currentTask = renwu.getCurrentTask();
			GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
			chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		}
	}
	
	/**
	 * 创建任务
	 * @param chara 玩家
	 * @param taskName 任务名称
	 */
	public static void createTask(Chara chara, String taskName) {
		createTask(chara, GameData.that.baseRenwuService.findOneByCurrentTask(taskName));
	}
	
	
	/**
	 * 创建任务
	 * 
	 * @param vo_61553_0
	 * @param chara
	 */
	public static void createTask(Vo_61553_0 vo_61553_0, Chara chara) {
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
		chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
	}

	/**
	 * 创建主线任务
	 * 
	 * @param vo_61553_0
	 * @param chara
	 */
	public static void createZhuXianFuShengRuoMengTask(Chara chara, Renwu renwu) {
		if(renwu != null) {
			if (renwu != null && renwu.getCurrentTask().equals("主线—浮生若梦_s21")) {
				String[] str = { "前往#Z五龙山#Z拜师", "前往#Z终南山#Z拜师", "前往#Z凤凰山#Z拜师", "前往#Z乾元山#Z拜师", "前往#Z骷髅山#Z拜师" };
				renwu.setTaskPrompt(str[chara.polar - 1]);
			}
			if (renwu != null && renwu.getCurrentTask().equals("主线—浮生若梦_s22")) {
				String[] str = { "向#P云霄童子|E=【主线】慕名而来#P拜师", "向#P碧玉童子|E=【主线】慕名而来#P拜师", "向#P水灵童子|E=【主线】慕名而来#P拜师",
						"向#P赤霞童子|E=【主线】慕名而来#P拜师", "向#P彩云童子|E=【主线】慕名而来#P拜师" };
				renwu.setTaskPrompt(str[chara.polar - 1]);
			}
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = "主线—浮生若梦";
			vo_61553_0.task_desc = "1-9级主线任务，该等级段任务不可组队同步完成。";
			vo_61553_0.task_prompt = renwu.getTaskPrompt();
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = 1563252508;
			vo_61553_0.attrib = 0;
			vo_61553_0.reward = renwu.getShowReward();
			vo_61553_0.show_name = renwu.getShowName();
			vo_61553_0.task_extra_para = renwu.getCurrentTask();
			vo_61553_0.task_state = "0";
			vo_61553_0.currentTask = renwu.getCurrentTask();
			GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
			chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		}
	
	}
	
	/**
	 * 创建主线任务
	 * 
	 * @param vo_61553_0
	 * @param chara
	 */
	public static void createZhuXianBaiRuShiMenTask(Chara chara, Renwu renwu) {
		if(renwu != null) {
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = "主线—拜入师门";
			vo_61553_0.task_desc = "10-19级主线任务，该等级段任务不可组队同步完成。";
			vo_61553_0.task_prompt = renwu.getTaskPrompt();
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = 1563252508;
			vo_61553_0.attrib = 0;
			vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I代金券|代金券#I";
			vo_61553_0.show_name = renwu.getShowName();
			vo_61553_0.task_extra_para = renwu.getCurrentTask();
			vo_61553_0.task_state = "0";
			vo_61553_0.currentTask = renwu.getCurrentTask();
			GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
			chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		}
	}
	
	//主线山雨欲来
	public static void createZhuXianShanYuYuLaiTask(Chara chara, Renwu renwu) {
		if(renwu != null) {
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = "主线—山雨欲来";
			vo_61553_0.task_desc = "20-29级主线任务，该等级段任务不可组队同步完成。";
			vo_61553_0.task_prompt = renwu.getTaskPrompt();
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = 1563252508;
			vo_61553_0.attrib = 0;
			vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I代金券|代金券#I";
			vo_61553_0.show_name = renwu.getShowName();
			vo_61553_0.task_extra_para = renwu.getCurrentTask();
			vo_61553_0.task_state = "0";
			vo_61553_0.currentTask = renwu.getCurrentTask();
			GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
			chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		}
	} 
	
	/**
	 * a创建妖魔道
	 * @param chara
	 * @param renwu
	 */
	public static void createYaoMoDaoYongQingYingYuGuaiTask(Chara chara, String type, Renwu renwu) {
		if(renwu != null) {
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			vo_61553_0.task_type = "妖魔道";
			vo_61553_0.task_desc = renwu.getTaskDesc();
			vo_61553_0.task_prompt = renwu.getTaskPrompt();
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = renwu.getTaskEndTime();
			vo_61553_0.attrib = renwu.getAttrib();
			vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I代金券|代金券#I";
			vo_61553_0.show_name = renwu.getShowName();
			vo_61553_0.task_extra_para = renwu.getCurrentTask();
			vo_61553_0.task_state = "0";
			vo_61553_0.currentTask = renwu.getCurrentTask();
			GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
			chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		}
	}
	
	/**
	 * 创建队伍任务
	 * 
	 * @param vo_61553_0
	 * @param chara
	 */
	public static void createTaskTeam(Vo_61553_0 vo_61553_0, Chara chara) {
		if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
			for (Chara charas : GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu) {
				GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, charas.id);
				charas.taskMap.put(vo_61553_0.task_type, vo_61553_0);
			}
		} else {
			GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
			chara.taskMap.put(vo_61553_0.task_type, vo_61553_0);
		}
	}

	/**
	 * 创建队伍任务
	 * 
	 * @param task_type
	 * @param task_prompt
	 * @param show_name
	 * @param chara
	 * @param extar
	 */
	public static void createTaskTeam(String task_type, String task_prompt, String show_name, Chara chara,
			String... extar) {
		if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
			for (Chara c : GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu) {
				createTask(task_type, task_prompt, show_name, c, extar);
			}
		} else {
			createTask(task_type, task_prompt, show_name, chara, extar);
		}
	}

	/**
	 * 清除某个任务
	 * 
	 * @param task_type
	 * @param chara
	 */
	public static void removeTask(String task_type, Chara chara) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = task_type;
		vo_61553_0.task_desc = "";
		vo_61553_0.task_prompt = "";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "";
		vo_61553_0.show_name = "";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "1";
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
		chara.taskMap.remove(task_type);
	}

	public static void removeTeamTask(String task_type, Chara chara) {
		if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
			for (Chara c : GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu) {
				removeTask(task_type, c);
			}
		} else {
			removeTask(task_type, chara);
		}
	}

	public static String shidaolevel(Chara chara) {
		if (!belongCalendarshidao()) {
			return "notStart";
		}
		GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
		String state = "";
		if (session.gameTeam == null || session.gameTeam.duiwu == null) {
			state = "wzd";
			return state;
		}
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			Chara chara2 = session.gameTeam.duiwu.get(i);
			int leaderLevel = GameShiDao.getShiDaoJieDuanLevel(session.gameTeam.duiwu.get(0).level);
			int teamLevel = GameShiDao.getShiDaoJieDuanLevel(chara2.level);
			if (teamLevel != leaderLevel) {
				state = "djbt";
				break;
			}
		}
		return state;}

	public static void feiditu(int mapid, Chara chara) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		GameUtil.genchongfei(gameObjectChar);
	}

	// 这里对应打开超级藏宝图抽出来的物品
	public static String[] luckFindDraw(Chara chara) {
		String nameType = "";
		String[] yiDing = { "帅帅猴#变异", "蛋蛋鸡#变异", "乖乖狗#变异", "招财猪#变异", "岳麓剑#精怪", "筋斗云#精怪" };
		String[] erDing = { "召唤令·十二生肖#物品" };
		String[] siDing = { "超级女娲石#物品" };
		String[] sgDing = { "XXX#上古", "XXX#万年"};
		Random random = new Random();
		int r = random.nextInt(1000) + 1;
		if (r <= 3) {
			nameType = yiDing[random.nextInt(yiDing.length)];
		} else if (r < 20) {
			nameType = erDing[random.nextInt(erDing.length)];
		} else if (r < 50) {
			nameType = siDing[random.nextInt(siDing.length)];
		} else if (r < 500) {
			nameType = sgDing[random.nextInt(sgDing.length)];
			//如果超过限制直接就是金币
			if(nameType.indexOf("上古") != -1 && GameShuaGuai.shanggu.size()>400) {
				int money = 1000000 + random.nextInt(300000);
				nameType = String.format("%d#金币", money);
			}else if(nameType.indexOf("万年") != -1 && GameShuaGuai.wannian.size()>400) {
				int money = 1000000 + random.nextInt(300000);
				nameType = String.format("%d#金币", money);
			}
		} else {
			int money = 1000000 + random.nextInt(300000);
			nameType = String.format("%d#金币", money);
		}
		return nameType.split("#");
	}

	public static String dateToWeekday(Date changeDate) {
		String[] weekdays = GameConfig.config.getShidao().getWeek();
		if (changeDate == null) {
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(changeDate);
		int numOfWeek = cal.get(7) - 1;
		if (numOfWeek < weekdays.length) {
			return weekdays[numOfWeek];
		}
		return null;
	}

	public static boolean dateToWeekDay() {
		String[] weekdays = GameConfig.config.getShidao().getWeek();
		for (String w : weekdays) {
			if (Utils.getWeek().equals(w)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public static int dayForWeek(String pTime) throws Throwable {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date tmpDate = format.parse(pTime);
		Calendar cal = new GregorianCalendar();
		cal.set(tmpDate.getYear(), tmpDate.getMonth(), tmpDate.getDay());
		return cal.get(7);
	}

	// 将角色传送回天墉城
	public static void huicheng(Chara chara) {
		Map map = GameData.that.baseMapService.findOneByName("天墉城");
		chara.y = map.getY();
		chara.x = map.getX();
		GameLine.getGameMapname(chara.line, map.getName()).join(GameObjectCharMng.getGameObjectChar(chara.id));
	}

	public static void shidaohuicheng(Chara chara) {
		chara.shidaodaguaijifen = 0;
		huicheng(chara);
	}

	public static boolean belongCalendarshidao() {
		Date nowTime = null;
		Date beginTime = null;
		Date endTime = null;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		try {
			nowTime = df.parse(df.format(new Date()));
			String[] times = GameConfig.config.getShidao().getTimes();
			String beginTimeStr = "";
			String endTimeStr = "";
			// 读取配置文件中时间.
			if (times != null && times.length >= 2) {
				beginTimeStr = times[0];
				endTimeStr = times[1];
			}
			if (StringUtils.isNullOrEmpty(beginTimeStr) || StringUtils.isNullOrEmpty(endTimeStr)) {
				return false;
			}
			beginTime = df.parse(beginTimeStr);
			endTime = df.parse(endTimeStr);
		} catch (ParseException e) {
			log.error("试道解析时间错误:{}", e);
		}
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		return date.after(begin) && date.before(end);
	}
	
	/**
	 * 时间解析判断
	 * @param startTimeStr 开始时间
	 * @param endTimeStr 结束时间
	 * @return
	 */
	public static boolean belongCalendarTime(String startTimeStr, String endTimeStr) {
		Date nowTime = null;
		Date beginTime = null;
		Date endTime = null;
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		try {
			nowTime = df.parse(df.format(new Date()));
			if (StringUtils.isNullOrEmpty(startTimeStr) || StringUtils.isNullOrEmpty(endTimeStr)) {
				return false;
			}
			beginTime = df.parse(startTimeStr);
			endTime = df.parse(endTimeStr);
		} catch (ParseException e) {
			log.error("时间错误:{}", e);
		}
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		return date.after(begin) && date.before(end);
	}
	
	/**
	 * 判断星期是否满足
	 * @param weekdays
	 * @return
	 */
	public static boolean dateToWeekDay(String[] weekdays) {
		if(weekdays==null || weekdays.length==0) {
			return false;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		int weekday = c.get(Calendar.DAY_OF_WEEK);
		for (String w : weekdays) {
			if(Utils.isNumber(w)) {
				if (Integer.valueOf(w) == weekday) {
					return true;
				}
			}
		}
		return false;
	}
}