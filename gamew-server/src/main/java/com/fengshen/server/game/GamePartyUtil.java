package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.domain.PartySkill;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.constant.PartyType;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.data.vo.party.VO_PARTY_ICON;
import com.fengshen.server.data.vo.party.Vo_PARTY_QUERY_MEMBER;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.party.MSG_PARTY_ICON;
import com.fengshen.server.data.write.party.MSG_PARTY_QUERY_MEMBER;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.util.GameConfig;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 帮派工具类
 * 
 * 
 *
 */
public class GamePartyUtil {

	public static final List<Npc> npcs = new ArrayList<>();

	/**
	 * 帮派图标
	 * 
	 * @param chara
	 */
	public static void partyIcon(Chara chara) {
		for (GameObjectChar g : GameObjectCharMng.getGameObjectChar(chara.id).gameMap.sessionList) {
			if (g.chara.getSettings().get("refuse_party_image") != null
					&& g.chara.getSettings().get("refuse_party_image") == 1) {
				continue;
			}
			if (!StringUtils.isNullOrEmpty(g.chara.getPartyName())) {
				if( GameCore.partyMap != null) {
					Party party = GameCore.partyMap.get(g.chara.getPartyName());
					if(party != null) {
						// 只有当帮派图标显示的时候才设置帮派图标
						VO_PARTY_ICON icon = new VO_PARTY_ICON();
						icon.setId(g.chara.id);
						icon.setMd5Value(party.getIconMd5());
						GameObjectCharMng.sendAllmap(new MSG_PARTY_ICON(), icon, chara.mapid);
					}
				}
			}
		}
	}

	/**
	 * 刷新帮派某个成员
	 * 
	 * @param query
	 * @param toChara
	 */
	public static void queryPartyMember(String gid) {
		Chara toChara = null;
		Vo_PARTY_QUERY_MEMBER query = new Vo_PARTY_QUERY_MEMBER();
		query.inTeam = 0;
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if (gameObject == null) {
			// 数据库查询
			Characters ch = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid,"data");
			toChara = JSONObject.parseObject(ch.getData(), Chara.class);
			query.online = 0;
		} else {
			toChara = gameObject.chara;
			query.inTeam = GameCommonUtil.isNotGameTeam(gameObject.gameTeam) ? 1
					: 0;
			query.online = 1;
		}
		query.gid = toChara.uuid;
		query.name = toChara.name;
		query.icon = toChara.waiguan;
		query.level = toChara.level;
		query.title = "";
		query.reputation = 0;
		query.rights = 0;
		query.job = toChara.partyJob + ":" + PartyType.getKeyByValue(toChara.partyJob);
		query.gender = toChara.sex;
		query.contrib = query.contrib;
		query.joinTime = query.joinTime;
		query.logoutTime = query.logoutTime;
		query.family = query.family;
		query.polar = toChara.polar;
		query.newJob = toChara.partyJob;
		query.vipType = toChara.vipType;
		query.serverId = GameCore.getGameLine(toChara.line).lineNum + "线";
		GameObjectChar.send(new MSG_PARTY_QUERY_MEMBER(), query);
	}

	/**
	 * 1:消灭一些怪物 随机产生帮派任务 矿石:6220
	 * 
	 * @param chara
	 */
	public static Vo_61553_0 randomPartyTask(Chara chara) {
		int next = new Random().nextInt(2) + 1;
		chara.partyNum++;
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "帮派任务";
		vo_61553_0.task_desc = "为建设帮派而完成的任务。当前为#R" + chara.partyNum + "#n次任务,今日还可以领取#R" + (10 - chara.partyNum)
				+ "#n轮。";
		vo_61553_0.refresh = 0;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I#I帮贡|帮贡#I#I帮派活力值|帮派活力值#I#I物品|超级女娲石#I";
		vo_61553_0.show_name = "帮派任务(" + chara.partyNum + "/"+GameConfig.config.getBaseConfig().getPartyNum()+")";
		vo_61553_0.task_state = "1";
		if (next == 1) {
			int num = new Random().nextInt(3) + 3;
			String[] figStr = new String[] { "花纹蛇", "灵睛鼠" };
			String guaiwu = new Random().nextBoolean() ? figStr[0] : figStr[1];
			// 0:战斗1:当前数量2:总数3:怪物名称
			vo_61553_0.task_extra_para = "fight:" + 0 + ":" + num + ":" + guaiwu;
			vo_61553_0.task_prompt = "前往#Z帮派总坛|$1#Z，消灭#R#n0/" + num + "只#R" + guaiwu;
		} else if (next == 2) {
			// 送天山雪莲,随机一个npc
			Npc npc = GamePartyUtil.npcs.get(new Random().nextInt(GamePartyUtil.npcs.size()));
			String task_prompt = "将天山雪莲送给#Z" + npc.getName() + "|"
					+ GameLine.getGameMap(chara.line, npc.getMapId()).name + "(" + npc.getX() + "," + npc.getY() + ")::"
					+ npc.getName() + "|$0|M=【帮派任务】护送天山雪莲#Z";
			vo_61553_0.task_extra_para = "goGoods:天山雪莲:" + npc.getId();
			vo_61553_0.task_prompt = task_prompt;
			vo_61553_0.task_state = "tsxlStep1";
		}
		GameUtilRenWu.createTask(vo_61553_0, chara);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
		return vo_61553_0;
	}

	/**
	 * 帮派挑战生成任务
	 * 
	 * @param chara
	 * @return
	 */
	public static Vo_61553_0 randomPartyTiaozhanTask(Chara chara) {

		return null;
	}

	/**
	 * 是否在帮派总坛
	 * 
	 * @param chara
	 * @return
	 */
	public static boolean isPartyMap(Chara chara) {
		return chara.mapid == 26000 ? true : false;
	}

	/**
	 * 帮派日常挑战
	 * 
	 * @param chara
	 * @return
	 */
	public static void nextPartyTiaozhanTask(Chara chara) {
		// 挑战#P帮派书童|E【帮派日常挑战】我要挑战@P帮派书童|M=【帮派日常挑战】我要挑战#
		String showName = "挑战#P%s|E【日常挑战】帮派日常挑战@P%s|M=【日常挑战】帮派日常挑战#P";
		Vo_61553_0 task = chara.taskMap.get("帮派日常挑战");
		// 如果任务存在则进行下一个任务。
		if (task != null) {
			if (task.task_state.equals("partyTzNext_1")) {
				task.task_prompt = String.format(showName, "帮派军师", "帮派军师");
				task.task_state = "partyTzNext_2";
			} else if (task.task_state.equals("partyTzNext_2")) {
				task.task_prompt = String.format(showName, "帮派侍女", "帮派侍女");
				task.task_state = "partyTzNext_3";
			} else if (task.task_state.equals("partyTzNext_3")) {
				task.task_prompt = String.format(showName, "账房先生", "账房先生");
				task.task_state = "partyTzNext_4";
			} else if (task.task_state.equals("partyTzNext_4")) {
				task.task_prompt = String.format(showName, "帮派总教头", "帮派总教头");
				task.task_state = "partyTiaozhanEnd";
			}
		} else {
			task = new Vo_61553_0();
			task.count = 1;
			task.task_type = "帮派日常挑战";
			task.task_desc = "帮派组织的日常活动，各帮派成员应积极为帮派建设出力！";
			task.refresh = 0;
			task.task_end_time = 1567909190;
			task.attrib = 1;
			task.reward = "#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I#I帮贡|帮贡#I#I帮派活力值|帮派活力值#I#I物品|超级女娲石#I";
			task.task_state = "partyTzNext_1";
			task.task_prompt = String.format(showName, "帮派书童", "帮派书童");
		}
		task.show_name = "帮派日常挑战";
		GameUtilRenWu.createTask(task, chara);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task.task_prompt));
	}

	/**
	 * 帮派任务已完成回复帮派总管
	 * 
	 * @param chara
	 */
	public static void partyTaskFinish(Chara chara) {
		Vo_61553_0 task = chara.getTaskMap().get("帮派任务");
		task.task_prompt = "回复#P帮派总管|E【帮派任务】我要为帮派出力@P帮派总管|M=【帮派任务】我要为帮派出力#P";
		task.task_extra_para = "finish";
		GameUtilRenWu.createTask(task, chara);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task.task_prompt));
	}


	/**
	 * 更新帮派信息
	 */
	public static void updatePartyInfo(Chara chara) {

	}

	/**
	 * 当前是否在进行帮派任务对话
	 * 
	 * @param chara
	 * @return
	 */
	public static boolean isIngParty(Chara chara) {
		boolean flag = false;
		if(chara != null) {
			Vo_61553_0 task = chara.taskMap.get("帮派任务");
			if (task != null) {
				String ext = task.task_extra_para;
				// 解析参数
				if (ext.indexOf("goGoods:天山雪莲") != -1) {
					flag = true;
				}
			}
		}
		return flag;
	}

	static {
		npcs.add(new Npc(964, "钱大富", 89, 94, 5000, 6014));
		npcs.add(new Npc(965, "杜卜思", 139, 39, 5000, 6011));
		npcs.add(new Npc(966, "冯喜来", 107, 113, 5000, 6016));
		npcs.add(new Npc(967, "杂货店老板", 76, 26, 5000, 6015));
		npcs.add(new Npc(968, "管神工", 41, 72, 5000, 6039));
		npcs.add(new Npc(969, "厉巧手", 43, 81, 5000, 6040));
		npcs.add(new Npc(970, "晶晶儿", 9, 66, 5000, 6240));
		npcs.add(new Npc(971, "厉北七", 57, 79, 5000, 6058));
		npcs.add(new Npc(973, "屠娇娇", 55, 27, 5000, 6175));
		// 东海渔村
		npcs.add(new Npc(1175, "钱庄老板", 88, 15, 11000, 6014));
		npcs.add(new Npc(1176, "药店老板", 17, 28, 11000, 6011));
		npcs.add(new Npc(1177, "客栈老板", 62, 51, 11000, 6016));
		npcs.add(new Npc(1178, "杂货店老板", 46, 15, 11000, 6015));
		npcs.add(new Npc(1179, "宋辛哲", 89, 51, 11000, 6032));
		// 无名小镇
		npcs.add(new Npc(1186, "无名药铺老板", 47, 47, 23000, 6011));
		npcs.add(new Npc(1187, "无名武器店老板", 86, 63, 23000, 6012));
		npcs.add(new Npc(1188, "无名布庄老板", 78, 18, 23000, 6013));
		npcs.add(new Npc(1189, "无名客栈老板", 42, 19, 23000, 6016));
		npcs.add(new Npc(1190, "无名杂货店老板", 16, 44, 23000, 6015));
	}

	public static Npc getPartyJuBenCurrNpc(int id) {
		for (Npc npc : GamePartyUtil.npcs) {
			if (npc.getId() == id) {
				return npc;
			}
		}
		return null;
	}

	/**
	 * 帮派任务之天山雪莲剧情对话
	 * @param step 当前步数
	 * @param npc 
	 * @param chara 玩家
	 */
	public static void playTsxlScenariod(String step, Npc npc, Chara chara) {
		Vo_45056_0 juben = new Vo_45056_0();
		juben.id = npc.getId();
		juben.pic_no = 0;
		juben.isComplete = 0;
		juben.playTime = 20;
		juben.task_type = "帮派任务";
		if (step.equals("tsxlStep1")) {
			// 播放剧本
			juben.content = npc.getName() + ",我奉本帮总管之命，带来#R天山雪莲#n，请尽早服下，可马上减除病苦。";
			juben.name = chara.name;
			juben.portrait = chara.waiguan;
			chara.taskMap.get("帮派任务").task_state = "tsxlStep2";
		} else if (step.equals("tsxlStep2")) {
			// 播放第二个剧情
			juben.content = "真是太感谢你了，我正发愁找不到#R天山雪莲#n！";
			juben.name = npc.getName();
			juben.portrait = npc.getIcon();
			chara.taskMap.get("帮派任务").task_state = "tsxlStep3";
		} else if (step.equals("tsxlStep3")) {
			// 播放第三个剧情
			juben.content = "请你安心静养，不出三五天便可痊愈。我还有其他事要办，失陪了。";
			juben.name = chara.name;
			juben.portrait = chara.waiguan;
			chara.taskMap.get("帮派任务").task_state = "tsxlEnd";
		} else if (step.equals("tsxlEnd")) {
			Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
			vo_9129_0.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
			vo_9129_0.para = "DramaDlg";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0, chara.id);
			// 完成任务
			partyTaskFinish(chara);
			return;
		}
		GameObjectChar.send(new M45056_0(), juben);
	}

	/**
	 * 帮派任务结束奖励
	 * @param chara
	 */
	public static boolean endPartyTask(Chara chara, int n) {
		int jishu = chara.partyNum;
		if(n != 0) {
			jishu = n;
		}
		int contrib = 13 + (jishu * 3);
		int money = 10000*jishu;
		chara.contrib += contrib;
		int wuxue = 10 * jishu;
		int daohang = 50000 * jishu;
		int qiannneg = 10000 * jishu;
		GameUtil.sendMeTips("你获得了#R" + contrib + "#n点帮贡和#R" + contrib + "#n点活力值，帮派资金增加"+GameCommonUtil.getMoneyDes(money)+"#n。");
		GameCommonUtil.addWuXue(chara, wuxue, "帮派");
		GameUtil.adddaohang(chara, daohang, "帮派");
		GameUtil.addQianNeng(chara, qiannneg, "帮派");
		GameCommonUtil.dialogOk(String.join("", "你获得了#R", GameUtil.fmtDh(daohang), "#n道行、#R", String.valueOf(qiannneg),
				"#n点潜能、#R", String.valueOf(contrib), "#n点帮贡、#R", String.valueOf(contrib), "#n点活力值奖励，你的宠物获得了#R",
				String.valueOf(wuxue), "#n点武学奖励"));
		// 更新玩家在帮派的信息.
		Example example = new Example(PartyMember.class);
		example.createCriteria().andEqualTo("partyId", GameCore.partyMap.get(chara.getPartyName()).getPartyId())
				.andEqualTo("charaId", chara.id);
		List<PartyMember> partyMembers = GameData.that.partyMemberService.selectByExample(example);
		if(partyMembers != null && !partyMembers.isEmpty()) {
			PartyMember partyMember = partyMembers.get(0);
			partyMember.setActive(partyMember.getActive() + contrib);
			partyMember.setCurrWeekActive(partyMember.getCurrWeekActive() + contrib);
			GameData.that.partyMemberService.updateByPrimaryKeySelective(partyMember);
		}
		// 更新帮派建设度
		Party party = GameData.that.partyService.findByPartyName(chara.getPartyName());
		if(party != null) {
			party.setConstruct(party.getConstruct() + contrib);
			party.setMoney(party.getMoney()+money);
			GameData.that.partyService.updateByPrimaryKeySelective(party);
		}
		// 刷新
		GameUtil.sendUpdate(chara);
		return chara.partyNum >= 10 ? true : false;
	}
	
	/**
	 * 解散帮派
	 * @param chara
	 */
	public static void removeParty(Chara chara) {
		if(!StringUtils.isNullOrEmpty(chara.getPartyName())) {
			//删除帮派
			Example example = new Example(Party.class);
			example.createCriteria().andEqualTo("partyName", chara.getPartyName());
			GameData.that.partyService.deleteByExample(example);
			//通知所有帮派成员
			List<PartyMember> partyMembers = GameData.that.partyMemberService.getPartyMemberByPartyId(GameCore.partyMap.get(chara.getPartyName()).getPartyId());
			for(PartyMember p:partyMembers) {
				if(p.getCharaId() != chara.id) {
					GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(p.getCharaId());
					if(gameObjectChar != null) {
						if(gameObjectChar.chara.chenhao.indexOf(chara.getPartyName()) != -1) {
							gameObjectChar.chara.chenhao = "";
						}
						gameObjectChar.chara.setPartyName("");
						gameObjectChar.chara.setPartyJob("");
						//删除帮派称谓
						gameObjectChar.chara.chenghao.remove(chara.getPartyName()+"帮"+gameObjectChar.chara.getPartyJob());
						//GameCommonUtil.refreshAppellAtion(gameObjectChar.chara);
						GameUtil.refreshChengHao(gameObjectChar.chara);
						//发送消息通知
						GameCommonUtil.sendTips(chara.getPartyName() + "#n帮已被帮主#Y"+chara.name+"#n解散.", gameObjectChar.chara.id);
						//更新信息
						GameUtil.sendUpdate(gameObjectChar.chara);
						//如果当前这个人在帮派总坛
						if(gameObjectChar.chara.mapid == 26000) {
							gameObjectChar.chara.x = 95;
							gameObjectChar.chara.y = 64;
				            GameLine.getGameMapname(gameObjectChar.chara.line, "天墉城").join(gameObjectChar);
						}
						final Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(gameObjectChar.chara);
						GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
						//刷新图标
						VO_PARTY_ICON icon = new VO_PARTY_ICON();
						icon.setId(gameObjectChar.chara.id);
						icon.setMd5Value("");
						gameObjectChar.gameMap.send(new MSG_PARTY_ICON(), icon);
						//删除帮派任务
						GameUtilRenWu.removeTask("帮派任务", gameObjectChar.chara);
						GameUtilRenWu.removeTask("帮派日常挑战", gameObjectChar.chara);
					}else {
						//离线状态
						Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGidSelectProperties(p.getCharaGid(),"data", "id");
						if(findOneByGid2 != null) {
							Chara toChara = JSONObject.parseObject(findOneByGid2.getData(),Chara.class);
							if(toChara.chenhao.indexOf(chara.getPartyName()) != -1) {
								toChara.chenhao = "";
							}
							toChara.setPartyName("");
							toChara.setPartyJob("");
							//如果当前这个人在帮派总坛
							if(toChara.mapid == 26000) {
								toChara.x = 95;
								toChara.y = 64;
							}
							//删除帮派称谓
							toChara.chenghao.remove(chara.getPartyName()+"帮"+toChara.getPartyJob());
							//更新数据
							Characters update = new Characters();
							update.setId(findOneByGid2.getId());
							update.setData(JSONObject.toJSONString(toChara));
							GameData.that.baseCharactersService.updateById(update);
							//发送离线邮件
							GameCommonUtil.sendSystemEmail(toChara, "你所在的#Y"+chara.getPartyName()+"#n已被帮主解散", "帮派解散通知", "帮派");
							//删除帮派任务
							toChara.commonTaskMap.remove("帮派任务");
							toChara.commonTaskMap.remove("帮派日常挑战");
						}
					}
				}
			}
			//删除动态地图
			GameLine.deleteZoneGameMap(1, chara.getPartyName());
			//删除帮派称谓
			chara.chenghao.remove(chara.getPartyName()+"帮"+chara.getPartyJob());
			GameUtil.refreshChengHao(chara);
			if(chara.chenhao.indexOf(chara.getPartyName()) != -1) {
				chara.chenhao = "";
			}
			//刷新图标
			VO_PARTY_ICON icon = new VO_PARTY_ICON();
			icon.setId(chara.id);
			icon.setMd5Value("");
			GameObjectCharMng.getGameObjectChar(chara.id).gameMap.send(new MSG_PARTY_ICON(), icon);
			GameUtil.sendMeTips("你已成功解散帮派");
			//删除所有帮派成员
			Example example2 = new Example(PartyMember.class);
			example2.createCriteria().andEqualTo("partyId", GameCore.partyMap.get(chara.getPartyName()).getPartyId());
			GameData.that.partyMemberService.deleteByExample(example2);
			//以下是通知自己
			chara.setPartyJob("");
			chara.setPartyName("");
			//删除缓存的帮派
			GameCore.partyMap.remove(chara.getPartyName());
			//删除帮派任务
			GameUtilRenWu.removeTask("帮派任务", chara);
			GameUtilRenWu.removeTask("帮派日常挑战", chara);
			//更新信息
			GameUtil.sendUpdate(chara);
			final Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
			GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		}
	}
	
	/**
	 * 获取帮派人数上限
	 * @param level
	 */
	public static int getPartyMaxPopulation(int level) {
		int maxNum = 100;
		switch (level) {
		case 1:
			maxNum = 100;
			break;
		case 2:
			maxNum = 150;	
			break;
		case 3:
			maxNum = 200;
			break;
		case 4:
			maxNum = 300;
			break;
		}
		return maxNum;
	}
	
	/**
	 * 根据等级获取帮派日消耗建设度和资金
	 * @param level
	 * @return 0:建设度 1:资金
	 */
	public static int[] getPartyToDaySub(int level) {
		int[] data = new int[2];
		switch (level) {
		case 2:
			data[0] = 1027;
			data[1] = 350000;	
			break;
		case 3:
			data[0] = 2027;
			data[1] = 750000;
			break;
		case 4:
			data[0] = 4027;
			data[1] = 1500000;
			break;
		default:
			data[0] = 0;
			data[1] = 0;
			break;
		}
		return data;
	}
	
	/**
	 * 帮派基础的建设度要求
	 * @param level
	 * @return
	 */
	public static int getPartyRequirdConstruct(int level) {
		int num = 0;
		switch (level) {
		case 2:
			num = 100000;
			break;
		case 3:
			num = 500000;
			break;
		case 4:
			num = 1000000;
			break;
		}
		return num;
	}
	
	public static void main(String[] args) {
		
		System.out.println(100*1);
	}
	
	/**
	 * 帮派技能批量研发消耗
	 * @param currentScore 当前进度
	 * @param levelupScore 升级进度
	 * @param upLevel 升几级
	 * @return 0:消耗资金 1:建设度
	 */
	public static int[] getBatchPartySkillInfo(int currentLevel, int currentScore, int levelupScore, int upLevel) {
		int point = levelupScore-currentScore;
		for(int i=1;i<upLevel;i++) {
			point = getPartySkillCost(254,currentLevel+i)+point;
		}
		int costMoney= point*74;
		return new int[] {costMoney, point};
	}
	
	/**
	 * 计算帮派消耗
	 * @param level 等级
	 * @return
	 */
	private static int getPartySkillCost(int skillNo, int level) {
//		if(skillNo == 254 || skillNo == 259 || skillNo == 260) {
//			//天生技能
//			if(level<180) {
//				return 4*level*level+400*level;
//			}else if(level<=206) {
//				return (int) Math.floor(0.28*level*level*level-45*level*level+32000);
//			}else {
//				return 0;
//			}
//		}
		return 53 * level + 133;
	}
	
	/**
	 * 获取帮派可用资金
	 * @param level 帮派等级
	 * @param partyMoney 帮派资金
	 * @return
	 */
	public static int getCanUseMoneyByLevel(int level, int partyMoney) {
		int canUseMoney = 0;
		if(level == 1 || level == 2) {
			canUseMoney = 500000;
		}else if(level == 3) {
			canUseMoney = 1000000;
		}else if(level == 4) {
			canUseMoney = 3000000;
		}
		int canUse = partyMoney - canUseMoney;
		return canUse<0?0:canUse;
	}
	
	
	/**
	 *  获取帮派可用建设度
	 * @param level 帮派等级
	 * @param construct 帮派建设
	 * @return
	 */
	public static int getCanUseConstuByLevel(int level, int construct) {
		int canUseConstruct = 0;
		if(level == 1 || level == 2) {
			canUseConstruct = 100000;
		}else if(level == 3) {
			canUseConstruct = 500000;
		}else if(level == 4) {
			canUseConstruct = 1000000;
		}
		int canUse = construct - canUseConstruct;
		return canUse<0?0:canUse;
	}
	
	/**
	 * 获取帮派技能下一个等级分
	 * @param partySkill
	 * @return
	 */
	public static int getNextSkillUpLevelSocreBase(PartySkill partySkill) {
		int baseNum = 0;
		if(partySkill.getLevel()>=0 && partySkill.getLevel() <=30) {
			baseNum = 255;
		}else if(partySkill.getLevel()>=31 && partySkill.getLevel() <= 50) {
			baseNum = 500;
		}else if(partySkill.getLevel()>=51 && partySkill.getLevel() <= 70) {
			baseNum = 1000;
		}else if(partySkill.getLevel()>=71 && partySkill.getLevel() <= 90) {
			baseNum = 2000;
		}else if(partySkill.getLevel()>=91 && partySkill.getLevel() <= 110) {
			baseNum = 5000;
		}else if(partySkill.getLevel()>=111 && partySkill.getLevel() <= 130) {
			baseNum = 6000;
		}else if(partySkill.getLevel()>=131 && partySkill.getLevel() <= 150) {
			baseNum = 8000;
		}else if(partySkill.getLevel()>=151 && partySkill.getLevel() <= 170) {
			baseNum = 9000;
		}else if(partySkill.getLevel()>=171 && partySkill.getLevel() <= 190) {
			baseNum = 10000;
		}else if(partySkill.getLevel()>=191 && partySkill.getLevel() <= 206){
			baseNum = 12000;
		}else if(partySkill.getLevel()>206) {
			baseNum = 52000;
		}
		return baseNum*partySkill.getLevel();
	}
	
	/**
	 * a自动同意入帮
	 * @param party 要加入的帮派
	 * @param toGameObjectChar 申请人
	 */
	public static void autoAcceptAddParty(Party partyInfo, GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		Party party = GameCore.partyMap.get(partyInfo.getPartyName());
		if(party == null) {
			return;
		}
		// 为这个帮派添加成员
		PartyMember partyMember = new PartyMember();
		partyMember.setCharaId(chara.id);
		partyMember.setPartyId(party.getPartyId());
		partyMember.setJob("帮众" + ":" + PartyType.getKeyByValue("帮众"));
		partyMember.setName(chara.name);
		partyMember.setPolar(chara.polar);
		partyMember.setCharaGid(chara.uuid);
		partyMember.setCreateTime(new Date());
		GameData.that.partyMemberService.insertSelective(partyMember);
		//更新帮派信息
		partyInfo.setPopulation(partyInfo.getPopulation() + 1);
		GameData.that.partyService.updateByPrimaryKeySelective(partyInfo);
		// 刷新缓存信息
		GameCore.partyMap.put(chara.getPartyName(), partyInfo);
		chara.setPartyJob("帮众");
		chara.setPartyName(party.getPartyName());
		// 如果加入是上个帮派的话,帮贡就恢复
		//打个标记wangcong 这里空指针
		if (chara.getUpPartyName().equals(party.getPartyName())) {
			chara.contrib *= 2;
		}
		GameUtil.sendUpdate(chara);
		// 刷新图标
		GamePartyUtil.partyIcon(chara);
		GameUtil.chenghaoxiaoxi(chara, party.getPartyName() + "帮帮众", party.getPartyName() + "帮帮众");
		GameCommonUtil.sendTips("你已加入#Y" + party.getPartyName() + "#n帮派。", chara.id);
		GameCommonUtil.sendTips("你获得了#R" + party.getPartyName() + "帮众#n的称谓。", chara.id);
		// 清除上个帮派
		chara.upPartyName = "";
		//欢迎语
		GamePartyUtil.notifyPartyMsg(chara.getPartyName(), "热烈欢迎#Y"+chara.getName()+"#n加入帮派#50m");
	}
	
	/**
	 * a发送帮派信息
	 * @param partyName 帮派名称
	 * @param msg 消息
	 */
	public static void notifyPartyMsg(String partyName, String msg) {
		//发送这个任命消息到帮会
		Example examplePartyMember = new Example(PartyMember.class);
		examplePartyMember.createCriteria().andEqualTo("partyId", GameCore.partyMap.get(partyName).getPartyId());
		List<PartyMember> partyMemerbs = GameData.that.partyMemberService.selectByExample(examplePartyMember);
		Vo_MESSAGE npcMessage = GameCommonUtil.npcMessage("帮派总管", msg, 0, 6036, 5);
		for(PartyMember p:partyMemerbs) {
			GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(p.getCharaGid());
			if(gameObjectCharByUUid != null) {
				gameObjectCharByUUid.sendOne(new MSG_MESSAGE(), npcMessage);
			}
		}
	}
}
