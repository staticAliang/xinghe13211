package com.fengshen.server.game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.apache.commons.collections.MapUtils;

import com.fengshen.core.util.DateUtil;
import com.fengshen.db.domain.ShidaoHistory;
import com.fengshen.db.domain.ShidaoHistoryteam;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.shidao.Vo_OPEN_SHIDWZDLG;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY.Vo_SHIDAO_HISTORY_TIMES;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY.Vo_SHIDAO_HISTORY_TIMES.Vo_SHIDAO_HISTORY_TIMES_MEMBERS;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.shidao.MSG_OPEN_SHIDWZDLG;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_GLORY_HISTORY;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_TASK_INFO;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.util.GameConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Slf4j
public class GameShiDao {
	public int shuaXingzhuangtai;
	public static int statzhuangtai;
	public long shuaXingTime;
	// 这是记录的开始时间
	public static Long statTime;
	public static long gonggaoTime;
	public List<Vo_APPEAR> shidaoyuanmo;
	public static Integer[] shidaolevel = {70, 80, 90, 100, 110, 120, 130, 140, 150, 160, 170 };
	public static Boolean[] isOver = {false, false, false, false, false, false, false, false, false, false, false, false };
	// 是否发送过消息
	public static Boolean[] isSenMsg = {false, false, false};
	// 存放试道人员的信息
//	private static List<List<Chara>> shidao60_69Session = new ArrayList<>();
	private static List<List<Chara>> shidao70_79Session = new ArrayList<>();
	private static List<List<Chara>> shidao80_89Session = new ArrayList<>();
	private static List<List<Chara>> shidao90_99Session = new ArrayList<>();
	private static List<List<Chara>> shidao100_109Session = new ArrayList<>();
	private static List<List<Chara>> shidao110_119Session = new ArrayList<>();
	private static List<List<Chara>> shidao120_129Session = new ArrayList<>();
	private static List<List<Chara>> shidao130_139Session = new ArrayList<>();
	private static List<List<Chara>> shidao140_149Session = new ArrayList<>();
	private static List<List<Chara>> shidao150_159Session = new ArrayList<>();
	private static List<List<Chara>> shidao160_169Session = new ArrayList<>();
	private static List<List<Chara>> shidao170_179Session = new ArrayList<>();
	// 试道排名--以队长key为准
	public static java.util.Map<String, java.util.Map<String, Integer>> rankMap = new LinkedHashMap<>();
	// 试道排名,已经按顺序排好 -- 以等级阶段为准
	public static java.util.Map<Integer, List<ShiDaoRank>> sortShiDao = new LinkedHashMap<>();
	// 试道人员
	public static java.util.Map<String, List<Chara>> shidaoMapChara = new LinkedHashMap<>();
	// 试道地图
	public static Map<String, List<GameZone>> maps;

	public GameShiDao() {
		this.shuaXingzhuangtai = 0;
		this.shuaXingTime = System.currentTimeMillis();
		this.shidaoyuanmo = new LinkedList<Vo_APPEAR>();
	}

	/**
	 * 刷新试道元魔，随机从数据库里面取出试道元魔数量
	 * 
	 * @param gameShiDao
	 * @param gameMap
	 */
	public static void refreShiDaoYuanMo(GameShiDao gameShiDao, GameMap gameMap) {
		int count = GameConfig.config.getShidao().getCount();
		Random random = new Random();
		for (int i = 0; i < count; ++i) {
			String name = "试道元魔";
			Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
			vo_65529_0.id = GameCommonUtil.generateBossId();
			vo_65529_0.name = name;
			vo_65529_0.type = 2;
			vo_65529_0.leixing = random.nextInt(5) + 1;
			vo_65529_0.mapid = gameMap.id;
			vo_65529_0.x = random.nextInt(123) + 1;
			vo_65529_0.y = random.nextInt(123) + 1;
			vo_65529_0.dir = 1;
			vo_65529_0.icon = 6049;
			vo_65529_0.org_icon = vo_65529_0.icon;
			vo_65529_0.portrait = vo_65529_0.icon;
			gameShiDao.shidaoyuanmo.add(vo_65529_0);
			gameMap.send(new MSG_APPEAR_MONSTER(), vo_65529_0);
		}
	}

	static {
		GameShiDao.statzhuangtai = 0;
		GameShiDao.statTime = System.currentTimeMillis();
		GameShiDao.gonggaoTime = System.currentTimeMillis();
	}

	/**
	 * 报名时间
	 * 
	 * @return
	 */
	public static long getJoinTime() {
		String[] times = GameConfig.config.getShidao().getTimes();
		long min = DateUtil.getMin(times[0], times[1]);
		return min;
	}

	/**
	 * 清除所有数据
	 */
	public static void cleanShidaoSession() {
		GameShiDao.shidao70_79Session.clear();
		GameShiDao.shidao80_89Session.clear();
		GameShiDao.shidao90_99Session.clear();
		GameShiDao.shidao100_109Session.clear();
		GameShiDao.shidao110_119Session.clear();
		GameShiDao.shidao120_129Session.clear();
		GameShiDao.shidao130_139Session.clear();
		GameShiDao.shidao140_149Session.clear();
		GameShiDao.shidao150_159Session.clear();
		GameShiDao.shidao160_169Session.clear();
		GameShiDao.shidao170_179Session.clear();
		GameShiDao.shidaoMapChara.clear();
		GameShiDao.sortShiDao.clear();
		GameShiDao.rankMap.clear();
	}

	public static long getStartTime() {
		// 获取开始时间
		String[] times = GameConfig.config.getShidao().getTimes();

		SimpleDateFormat sdf = DateUtil.getSdf("yyyy-MM-dd");
		String h = sdf.format(new Date()) + " " + times[0] + ":" + "00";
		Date parse = DateUtil.parse(h, "yyyy-MM-dd HH:mm:ss");
		long startTime = parse.getTime();
		return startTime;
	}

	public static long getPkTime() {
		long pkTime = GameConfig.config.getShidao().getF2();
		return pkTime;
	}

	public static long getDurationTime() {
		long durationTime = GameConfig.config.getShidao().getF1();
		return durationTime;
	}

	/**
	 * 试道结算
	 * 
	 * @param level 阶段等级
	 */
	public static void shidaoJieSuanByLevel(int level) {
		//设置下出场时间
		long outTime = System.currentTimeMillis();
		List<GameObjectChar> sessionList = getShiDaoMapSession(level);
		for (GameObjectChar session : sessionList) {
			// 有队伍
			Chara chara = session.chara;
			if (chara.mapName.equals("试道场")) {
				FightContainer fightContainer = FightManager.getFightContainer(chara.id);
				if (fightContainer != null) {
					FightTeam fightTeam = FightManager.getFightTeam(fightContainer, chara.id);
					for (FightObject fightObject : fightTeam.fightObjectList) {
						fightObject.state.set(1);
					}
					FightTeam fightTeamDm = FightManager.getFightTeamDM(fightContainer, chara.id);
					for (FightObject fightObject : fightTeamDm.fightObjectList) {
						fightObject.state.set(1);
					}
					FightManager.sendOver(fightContainer, true);
				}
				chara.shidaoOutTime = outTime;
			}
		}
		// 做最后的结算
		comShiDaoRank(true);
		//排队信息
		List<ShiDaoRank> sortMap = GameShiDao.sortShiDao.get(level);
		if(sortMap == null || sortMap.isEmpty()) {
			return;
		}
		int rank = 1;
		for (ShiDaoRank obj : sortMap) {
			List<Chara> team = obj.getTeam();
			// 试道回城
			for (Chara c : team) {
				// 在试道场才会传送出去
				if (c.mapName.equals("试道场")) {
					c.x = 128;
					c.y = 52;
					GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(c.id);
					if (gameObjectChar != null) {
						GameLine.getGameMapname(c.line, "天墉城").join(gameObjectChar);
					}
				}
			}
		}
		for (ShiDaoRank obj : sortMap) {
			List<Chara> teams = obj.getTeam();
			if(teams.isEmpty()) {
				continue;
			}
			//未结算过才会计算
			boolean shiDaoGetReward = GameObjectCharMng.getGameObjectChar(teams.get(0).id).shiDaoGetReward;
			if(!shiDaoGetReward) {
				// 第一名
				if (rank == 1) {
					// 获取系统配置信息.
					java.util.Map<String, Object> no = GameConfig.config.getShidao().getNo1();
					readShiDaoConfig(no, "试道王者", "1", teams);
				} else if (rank == 2) {
					// 获取系统配置信息.
					java.util.Map<String, Object> no = GameConfig.config.getShidao().getNo2();
					readShiDaoConfig(no, "试道勇者", "2", teams);
				} else if (rank == 3) {
					// 获取系统配置信息.
					java.util.Map<String, Object> no = GameConfig.config.getShidao().getNo3();
					readShiDaoConfig(no, "试道勇者", "3", teams);
				}
			}
			// 试道主数据
			ShidaoHistory shidaoHistory = new ShidaoHistory();
			Date date = new Date();
			shidaoHistory.setCreateTime(date);
			shidaoHistory.setLeader(teams.get(0).getName());
			shidaoHistory.setLeaderUuid(teams.get(0).getUuid());
			int shiDaoJieDuanLevel = getShiDaoJieDuanLevel(teams.get(0).level);
			if(teams.get(0).level >= 60 && teams.get(0).level <= 79) {
				shiDaoJieDuanLevel = 60;
			}
			shidaoHistory.setLevel(shiDaoJieDuanLevel);
			shidaoHistory.setShidaoTime((int) (date.getTime() / 1000L));
			shidaoHistory.setRank(rank);
			shidaoHistory.setTotalTao(obj.getTao());
			shidaoHistory.setScore(teams.get(0).shidaoScore);
			GameData.that.shidaoHistoryService.insertSelective(shidaoHistory);
			// 试道队伍数据
			List<Vo_OPEN_SHIDWZDLG> wzlogs = new ArrayList<>();
			for (Chara team : teams) {
				ShidaoHistoryteam voTeam = new ShidaoHistoryteam();
				voTeam.setFamily(team.getFamily());
				voTeam.setGid(team.uuid);
				voTeam.setIcon(team.waiguan);
				voTeam.setLevel(team.level);
				voTeam.setName(team.name);
				voTeam.setShidaoHistoryId(shidaoHistory.getId());
				voTeam.setTao(team.tao + team.taoPoint);
				GameData.that.shidaoHistoryteamService.insertSelective(voTeam);
				// 试道王者弹窗信息
				if (rank == 1) {
					Vo_OPEN_SHIDWZDLG wzlog = new Vo_OPEN_SHIDWZDLG();
					wzlog.setGid(team.uuid);
					wzlog.setIcon(team.waiguan);
					wzlog.setLevel(team.level);
					wzlog.setName(team.name);
					wzlog.setPolar(team.polar);
					wzlogs.add(wzlog);
				}
			}
			// 通知玩家
			if (rank == 1) {
				for (Chara team : teams) {
					GameObjectChar.send(new MSG_OPEN_SHIDWZDLG(), wzlogs, team.id);
				}
			}
			rank++;
			if(rank > 3) {
				break;
			}
		}
		// 清除这个阶段的排名信息,因为已经结算完了
		sessionList.clear();
		if(GameShiDao.sortShiDao.get(level) != null) {
			GameShiDao.sortShiDao.get(level).clear();
		}
		if(maps != null) {
			maps.remove(getShiDaoJieDuan(level));
		}
		if(sortMap != null && !sortMap.isEmpty()) {
			sortMap.clear();
		}
	}

	public static void readShiDaoConfig(java.util.Map<String, Object> no, String defaultChengwei, String noRank,
			List<Chara> team, boolean... shiDaoFlag) {
		StringBuilder message = new StringBuilder();
		message.append("热烈恭喜恭喜[#Y");
		StringBuilder names = new StringBuilder();
		StringBuilder goods = new StringBuilder();
		int flag = 0;
		// 奖励队伍
		for (Chara chara : team) {
			GameObjectChar gc = GameObjectCharMng.getGameObjectChar(chara.id);
			// 为空或者是标识为false
			if (gc == null || !gc.shiDaoFlag.get()) {
				continue;
			}
			gc.shiDaoFlag.set(shiDaoFlag==null||shiDaoFlag.length==0?false:shiDaoFlag[0]);
			names.append(gc.chara.name).append(",");
			Vo_20481_0 vo_20481_4 = new Vo_20481_0();
			// 获取系统配置信息.
			String chengwei = defaultChengwei;
			if (no != null && !no.isEmpty()) {
				// 金元宝
				Object jinyuanbao = no.get("jinyuanbao");
				// 银元宝
				Object yinyuanbao = no.get("yinyuanbao");
				// 积分
				Object jifen = no.get("jifen");
				// 道具
				Object daoju = no.get("daoju");
				// 宠物
				Object chongwu = no.get("chongwu");
				// 称谓
				Object chengweiO = no.get("chengwei");
				if (chengweiO != null && !StringUtils.isNullOrEmpty((String) chengweiO)) {
					chengwei = (String) chengweiO;
				}
				if (jinyuanbao != null && (int) jinyuanbao > 0) {
					GameUtil.addJinYuanBao(gc, (int) jinyuanbao, "试道");
					if (flag == 0) {
						goods.append(jinyuanbao).append("金元宝,");
					}
				}
				// 银元宝
				if (yinyuanbao != null && (int) yinyuanbao > 0) {
					GameUtil.addYinYuanBao(gc, (int) yinyuanbao, "试道");
					if (flag == 0) {
						goods.append(yinyuanbao).append("银元宝,");
					}
				}
				// 道具奖励
				if (daoju != null && !StringUtils.isNullOrEmpty((String) daoju)) {
					String d = (String) daoju;
					String[] daojuArr = d.split(",");
					if (daojuArr != null && daojuArr.length > 0) {
						for (String arr : daojuArr) {
							GameUtil.huodedaoju(gc.chara, arr, 1);
							// 发送消息
							vo_20481_4 = new Vo_20481_0();
							vo_20481_4.msg = "你获得了" + arr + "道具奖励。";
							vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
							gc.sendOne(new M20481_0(), vo_20481_4);
							if (flag == 0) {
								goods.append(arr).append(",");
							}
						}
					}
				}
				// 积分奖励
				if (jifen != null && (int) jifen > 0) {
					GameUtil.addchargeScore(gc, (int) jifen, "试道");
					// 发送消息到app端
					if (flag == 0) {
						goods.append(jifen).append("充值积分,");
					}
				}
				try {
					// 宠物奖励
					if (chongwu != null && !StringUtils.isNullOrEmpty((String) chongwu)) {
						String d = (String) chongwu;
						String[] chongwuArr = d.split(",");
						if (chongwuArr != null && chongwuArr.length > 0) {
							for (String s : chongwuArr) {
								// 获取宠物
								String[] split = s.split("\\|");
								if (split != null && split.length >= 2) {
									// 宠物名称
									String name = split[0];
									// 宠物类型
									String type = split[1];
									if ("shenshou".equals(type)) {
										// 神兽
										GameUtil.huodechongwu(chara, name, 4, "试道");
									} else if ("zuoqi".equals(type)) {
										// 坐骑
										GameUtil.huodezuoji(chara, name, "试道");
									} else if ("bianyi".equals(type)) {
										// 变异
										GameUtil.huodechongwu(chara, name, 3, "试道");
									} else {
										// 普通宠物
										GameUtil.huodemanchongwu(chara, name, 1, "试道");
									}
									// 发送消息
									vo_20481_4 = new Vo_20481_0();
									vo_20481_4.msg = "你获得了" + name + "宠物奖励";
									vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
									gc.sendOne(new M20481_0(), vo_20481_4);
									if (flag == 0) {
										goods.append(name);
									}
								}
							}
						}
					}
				} catch (Exception e) {
					log.error("{}", e);
				}
				flag++;
			}
			GameUtil.chenghaoxiaoxi(chara, chengwei, chengwei);
			vo_20481_4.msg = "你获得了#R" + chengwei + "#n的称谓。";
			vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
			gc.sendOne(new M20481_0(), vo_20481_4);
			// 给用户装备称谓
			GameCommonUtil.changeTitle(gc, chengwei);
			chara.chenhao = chengwei;
			//刷新称谓消息
			Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
			gc.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		}
		String charaNames = names.toString();
		if(!charaNames.isEmpty()) {
			charaNames = charaNames.substring(0, charaNames.length() - 1);
			message.append("#Y").append(charaNames).append("#n]").append("#n获得了").append(getShiDaoJieDuan(team.get(0).level))
			.append("级别试道大会#R第").append(noRank).append("名#n得到了#R").append(goods.toString()).append("#n等奖励");
			// 发送系统消息
			GameUtil.sendSystemMessage(7, message.toString());
		}
	}

	public static String getShiDaoJieDuan(int level) {
		String jieduan = "";
		if (level >= 60 && level <= 69) {
			jieduan = "60-79";
		}else if (level >= 70 && level <= 79) {
			jieduan = "70-79";
		} else if (level >= 80 && level <= 89) {
			jieduan = "80-89";
		} else if (level >= 90 && level <= 99) {
			jieduan = "90-99";
		} else if (level >= 100 && level <= 109) {
			jieduan = "100-109";
		} else if (level >= 110 && level <= 119) {
			jieduan = "110-119";
		} else if (level >= 120 && level <= 129) {
			jieduan = "120-129";
		} else if (level >= 130 && level <= 139) {
			jieduan = "130-139";
		} else if (level >= 140 && level <= 149) {
			jieduan = "140-149";
		} else if (level >= 150 && level <= 159) {
			jieduan = "150-159";
		} else if (level >= 160 && level <= 169) {
			jieduan = "160-169";
		} else if (level >= 170 && level <= 179) {
			jieduan = "170-179";
		}

		return jieduan;
	}

	/**
	 * 获取试道阶段等级列表
	 * 
	 * @param level
	 * @return
	 */
	public static int getShiDaoJieDuanLevel(int level) {
		int zbLevel = 1;
		if (level >= 60 && level <= 69) {
			zbLevel = 60;
		}else if (level >= 70 && level <= 79) {
			zbLevel = 70;
		} else if (level >= 80 && level <= 89) {
			zbLevel = 80;
		} else if (level >= 90 && level <= 99) {
			zbLevel = 90;
		} else if (level >= 100 && level <= 109) {
			zbLevel = 100;
		} else if (level >= 110 && level <= 119) {
			zbLevel = 110;
		} else if (level >= 120 && level <= 129) {
			zbLevel = 120;
		} else if (level >= 130 && level <= 139) {
			zbLevel = 130;
		} else if (level >= 140 && level <= 149) {
			zbLevel = 140;
		} else if (level >= 150 && level <= 159) {
			zbLevel = 150;
		} else if (level >= 160 && level <= 169) {
			zbLevel = 160;
		} else if (level >= 170 && level <= 179) {
			zbLevel = 170;
		}

		return zbLevel;
	}

	/**
	 * 根据阶段等级获取试道人员
	 * 
	 * @param level 等级
	 * @return
	 */
	public static List<List<Chara>> getShiDaoSession(int level) {
		int jieduan = GameCommonUtil.getZbLevel(level);
		List<List<Chara>> charas = null;
		switch (jieduan) {
		case 70:
			charas = GameShiDao.shidao70_79Session;
			break;
		case 80:
			charas = GameShiDao.shidao80_89Session;
			break;
		case 90:
			charas = GameShiDao.shidao90_99Session;
			break;
		case 100:
			charas = GameShiDao.shidao100_109Session;
			break;
		case 110:
			charas = GameShiDao.shidao110_119Session;
			break;
		case 120:
			charas = GameShiDao.shidao120_129Session;
			break;
		case 130:
			charas = GameShiDao.shidao130_139Session;
			break;
		case 140:
			charas = GameShiDao.shidao140_149Session;
			break;
		case 150:
			charas = GameShiDao.shidao150_159Session;
			break;
		case 160:
			charas = GameShiDao.shidao160_169Session;
			break;
		case 170:
			charas = GameShiDao.shidao170_179Session;
			break;
		}
		return charas;
	}

	/**
	 * 试道pk计算
	 * 
	 * @param fightContainer
	 */
	public static void gameShiDaoPk(FightContainer fightContainer) {
		// 如果角色在试道场
		List<FightTeam> teamList = fightContainer.teamList;
		// 队伍信息
		Map<String, FightTeam> fightTeamInfo = GameCommonUtil.getFightTeamInfo(teamList);
		// PK胜利的队伍
		FightTeam victoryTeam = fightTeamInfo.get("victoryTeam");
		// PK失败的队伍
		FightTeam deadTeam = fightTeamInfo.get("deadTeam");
		// 空闲时间
		int freeTime = GameConfig.config.getShidao().getFreeTime();
		// 胜利的队伍信息
		if (victoryTeam != null) {
			if (victoryTeam.fightObjectList != null && !victoryTeam.fightObjectList.isEmpty()) {
				int teamLeaderId = victoryTeam.fightObjectList.get(0).id;
				GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(teamLeaderId);
				if (gameObjectChar != null) {
//					gameObjectChar.chara.shidaoPkSocre += 2;
					//这里只加试道积分
					gameObjectChar.chara.shidaoScore += 1;
					log.info("胜利队伍队长名字-------:{}", gameObjectChar.chara.name);
					// 设置闲置时间.
					if (freeTime > 0) {
						// 在原基础上随机时间
						int time = ThreadLocalRandom.current().nextInt(30) + freeTime;
						GameData.that.redisUtils.set("shiDaoFight-" + teamLeaderId, "战斗人员-teamLeaderId", time);
					}
				}
			}
		}
		// 失败的队伍信息
		if (deadTeam != null) {
			if (deadTeam.fightObjectList != null && !deadTeam.fightObjectList.isEmpty()) {
				int teamLeaderId = deadTeam.fightObjectList.get(0).id;
				GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(teamLeaderId);
				if (gameObjectChar != null) {
					gameObjectChar.chara.shidaoPkSocre -= 1;
					log.info("失败队伍队长名字-------:{}", gameObjectChar.chara.name);
					Chara deadChara = gameObjectChar.chara;
					if (deadChara.shidaoPkSocre <= 0) {
						// 如果分数小于等于0的话,则直接踢出去试道场
						java.util.Map<Integer, String> charaIds = new HashMap<>();
						List<String> outNames = new ArrayList<>();
						String leaderName = "";
						long outTime = System.currentTimeMillis();
						for (Chara c : gameObjectChar.gameTeam.duiwu) {
							if(c.mapName.equals("试道场")) {
								c.x = 128;
								c.y = 52;
								GameLine.getGameMapname(c.line, "天墉城").join(GameObjectCharMng.getGameObjectChar(c.id));
								charaIds.put(c.id, "1");
								GameCommonUtil.sendTips("你已被淘汰出场。", c.id);
								outNames.add(c.name);
								c.shidaoOutTime = outTime;
							}
						}
						if (!outNames.isEmpty()) {
							leaderName = outNames.get(0);
						}
						//如果试道场内还有两只以上的队伍则直接给他结算
//						int shiDaoJieDuanLevel = GameShiDao.getShiDaoJieDuanLevel(deadChara.level);
//						int teamSize = getTeamSize(shiDaoJieDuanLevel);
						//当里面只有两个队伍的时候才会提前结算
//						if(teamSize==2) {
//							gameObjectChar.shiDaoGetReward = true;
//							//计算一次排名
//							comShiDaoRank(false);
//							//给第三名结算
//							java.util.Map<String, Object> no = GameConfig.config.getShidao().getNo3();
//							readShiDaoConfig(no, "试道勇者", "3", gameObjectChar.gameTeam.duiwu, true);
//						}
						log.error("试道积分：{}，队长：{}，队员成员：{}，被赶出了试道场", gameObjectChar.chara.shidaoScore, leaderName,
								outNames);
					} else {
						if (freeTime > 0) {
							// 在原基础上随机时间
							int time = ThreadLocalRandom.current().nextInt(30) + freeTime;
							GameData.that.redisUtils.set("shiDaoFight-" + teamLeaderId, "战斗人员-teamLeaderId", time);
						}
					}
				}
			}
		}
		return;
	}

	/**
	 * 获取历届试道王者记录
	 */
	public static void getShiDaoWzHistorys() {
		PageHelper.startPage(1, 30);
		Example example = new Example(ShidaoHistory.class);
		example.orderBy("createTime").desc();
		example.createCriteria().andEqualTo("rank", 1);

		PageInfo<ShidaoHistory> pageInfo = new PageInfo<>(GameData.that.shidaoHistoryService.selectByExample(example));
		List<ShidaoHistory> list = pageInfo.getList();
		if (list != null && !list.isEmpty()) {
			List<Vo_SHIDAO_HISTORY> vos = new ArrayList<>();
			// 对List进行分组
			Map<Integer, List<ShidaoHistory>> collect = list.stream()
					.collect(Collectors.groupingBy(ShidaoHistory::getLevel));
			for (Map.Entry<Integer, List<ShidaoHistory>> map : collect.entrySet()) {
				List<Vo_SHIDAO_HISTORY_TIMES> items = new ArrayList<>();
				Vo_SHIDAO_HISTORY vo = new Vo_SHIDAO_HISTORY();
				vo.setLevelBuff(map.getKey());
				for (ShidaoHistory s : map.getValue()) {
					Example example2 = new Example(ShidaoHistoryteam.class);
					example2.createCriteria().andEqualTo("shidaoHistoryId", s.getId());
					// 列表
					Vo_SHIDAO_HISTORY_TIMES item = new Vo_SHIDAO_HISTORY().new Vo_SHIDAO_HISTORY_TIMES();
					item.setIsMonth(0);
					item.setTime(s.getShidaoTime());
					items.add(item);
					// 成员详情
					List<ShidaoHistoryteam> members = GameData.that.shidaoHistoryteamService.selectByExample(example2);
					List<Vo_SHIDAO_HISTORY_TIMES_MEMBERS> vo_members = new ArrayList<>();
					for (ShidaoHistoryteam member : members) {
						Vo_SHIDAO_HISTORY_TIMES_MEMBERS vo_member = new Vo_SHIDAO_HISTORY().new Vo_SHIDAO_HISTORY_TIMES().new Vo_SHIDAO_HISTORY_TIMES_MEMBERS();
						vo_member.setFamily(member.getFamily());
						vo_member.setGid("tao:" + member.getTao());
						vo_member.setIcon(member.getIcon());
						if (member.getGid().equals(s.getLeaderUuid())) {
							vo_member.setIsLeader(1);
						} else {
							vo_member.setIsLeader(0);
						}
						vo_member.setLevel(member.getLevel());
						vo_member.setMemberName(member.getName());
						vo_members.add(vo_member);
					}
					item.setMembers(vo_members);
				}
				vos.add(vo);
				vo.setItems(items);
			}
			GameObjectChar.send(new MSG_SHIDAO_GLORY_HISTORY(), vos);
		} else {
			GameUtil.sendMeTips("暂无数据");
		}
	}

	public static void comShiDaoRank(boolean isOver) {
		Integer[] shidaolevel = GameShiDao.shidaolevel;
		for (Integer level : shidaolevel) {
			// 获取当前阶段地图信息
			List<List<Chara>> shiDaoSession = GameShiDao.getShiDaoSession(level);
			if (shiDaoSession == null || shiDaoSession.isEmpty()) {
				continue;
			}
			//获取试道地图里面的所有人.
			List<GameObjectChar> shiDaoMapSession = getShiDaoMapSession(level);
			if (shiDaoMapSession == null || shiDaoMapSession.isEmpty()) {
				continue;
			}
			//这里试道场内队伍
			Map<String,List<Chara>> shidaoMapTeams = new HashMap<>();
			for(GameObjectChar game:shiDaoMapSession) {
				if (GameCommonUtil.isNotGameTeam(game.gameTeam)) {
					shidaoMapTeams.put(game.gameTeam.duiwu.get(0).uuid, game.gameTeam.duiwu);
				}
			}
			//先计算试道场内排名
			List<ShiDaoRank> shidaoInnerRankList = new ArrayList<>();
			for (java.util.Map.Entry<String, List<Chara>> m : shidaoMapTeams.entrySet()) {
				List<Chara> duiwu = m.getValue();
				if (duiwu != null && !duiwu.isEmpty()) {
					// 道行
					int tao = 0;
					for (Chara c : duiwu) {
						tao += (c.tao + c.taoPoint);
					}
					// 只计算队长的信息
					int pkScore = duiwu.get(0).getShidaoPkSocre();
					ShiDaoRank shiDaoRank = new ShiDaoRank(pkScore, tao, duiwu.get(0).shidaoScore, duiwu.get(0).shidaoOutTime, duiwu.get(0).name,
							duiwu);
					shiDaoRank.setUuid(duiwu.get(0).uuid);
					shidaoInnerRankList.add(shiDaoRank);
				}
			}
			//1.试道总积分排名、2.队伍总道行
//			shidaoInnerRankList = shidaoInnerRankList.stream()
//					.sorted(Comparator.comparing(ShiDaoRank::getShidaoScore).thenComparing(ShiDaoRank::getTao).reversed())
//					.collect(Collectors.toList());
			
			/*------------------------------试道场内排名计算 end------------------------------*/
			//试道场外排名
			List<ShiDaoRank> shidaoOutRankList = new ArrayList<>();
			//如果试道场内场队伍小于三支
			int teamSize = 3-shidaoMapTeams.size();
			//试道场外队员
			List<List<Chara>> filterShiDaoOutSession = new ArrayList<>();
			//如果队伍数不足的话则从试道场外找寻试道积分最高的队伍
			if(teamSize>0) {
				//先过滤下队伍
				for (List<Chara> c : shiDaoSession) {
					//这人不在试道场的
					if(!c.isEmpty() && shidaoMapTeams.get(c.get(0).uuid) == null) {
						filterShiDaoOutSession.add(c);
					}
				}
			}
			// 存放队长队伍信息
			java.util.Map<String, List<Chara>> shidaoOutTeam = new HashMap<>();
			// 参与试道队伍的信息
			for (List<Chara> c : filterShiDaoOutSession) {
				List<Chara> filterTeams = new ArrayList<>();
				for (Chara fc : c) {
					// 只有参战过试道的人员才会有奖励
					GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fc.id);
					if (gameObjectChar != null && gameObjectChar.shiDaoFlag.get()) {
						filterTeams.add(fc);
					}
				}
				if (!filterTeams.isEmpty()) {
					shidaoOutTeam.put(filterTeams.get(0).uuid, filterTeams);
				}

			}
			for (java.util.Map.Entry<String, List<Chara>> m : shidaoOutTeam.entrySet()) {
				List<Chara> duiwu = m.getValue();
				if (duiwu != null && !duiwu.isEmpty()) {
					// 道行
					int tao = 0;
					for (Chara c : duiwu) {
						tao += (c.tao + c.taoPoint);
					}
					// 只计算队长的信息
					int pkScore = duiwu.get(0).getShidaoPkSocre();
					ShiDaoRank shiDaoRank = new ShiDaoRank(pkScore, tao, duiwu.get(0).shidaoScore, duiwu.get(0).shidaoOutTime, duiwu.get(0).name,
							duiwu);
					shiDaoRank.setUuid(duiwu.get(0).uuid);
					shidaoOutRankList.add(shiDaoRank);
				}
			}
			//先按PK值排序、试道总积分、道行
//			shidaoOutRankList = shidaoOutRankList.stream()
//					.sorted(Comparator.comparing(ShiDaoRank::getShidaoScore).thenComparing(ShiDaoRank::getTao).reversed())
//					.collect(Collectors.toList());
			
			//把两个排名组合在一起
			List<ShiDaoRank> shidaoAllRankList = new ArrayList<>();
			//优先添加场内数据
			shidaoAllRankList.addAll(shidaoInnerRankList);
			shidaoAllRankList.addAll(shidaoOutRankList);

			shidaoAllRankList = shidaoAllRankList.stream()
					.sorted(Comparator.comparing(ShiDaoRank::getShidaoScore).thenComparing(ShiDaoRank::getTao).reversed())
					.collect(Collectors.toList());

			GameShiDao.sortShiDao.put(level, shidaoAllRankList);
			int i = 1;
			// 再次取出已经排好序的list；
			for (ShiDaoRank rank : shidaoAllRankList) {
				// 分数
				java.util.Map<String, Integer> score = new LinkedHashMap<>();
				score.put("rank", i);
				score.put("pkValue", rank.getScore());
				score.put("totalScore", rank.getShidaoScore());
				GameShiDao.rankMap.put(rank.getUuid(), score);
				i++;
			}
		}
	}

	/**
	 * 通知试道巅峰对决信息
	 * 
	 * @param gameObjectChar 玩家
	 */
	public static void notifyShiDaoInfo3() {
		if (GameShiDao.statzhuangtai == 3) {
			log.info("试道对决刷新");
			GameShiDao.comShiDaoRank(false);
			Integer[] shidaolevel = GameShiDao.shidaolevel;
			// 刷新pk信息
			for (int i = 0; i < shidaolevel.length; i++) {
				List<ShiDaoRank> list = GameShiDao.sortShiDao.get(shidaolevel[i]);
				if (list != null && !list.isEmpty()) {
					int sortRank = 1;
					for (ShiDaoRank l : list) {
						int intValue = l.getScore();
						List<Chara> charas = l.getTeam();
						// 遍历当前队伍信息
						for (Chara chara2 : charas) {
							GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(),
									GameCommonUtil.shidaoTaskInfoNo3(intValue, 0, sortRank), chara2.id);
						}
						sortRank++;
					}
				}
				// 存放队长队伍信息
				Map<String, List<Chara>> commander = new HashMap<>();
				// 地图人员
				List<GameObjectChar> sessionList = getShiDaoMapSession(shidaolevel[i]);
				if (sessionList != null && !sessionList.isEmpty()) {
					for (GameObjectChar c : sessionList) {
						if (GameCommonUtil.isNotGameTeam(c.gameTeam)) {
							// 队伍信息
							List<Chara> duiwu = c.gameTeam.duiwu;
							// 这里在做判断
							Chara leader = duiwu.get(0);
							if (leader.shidaoPkSocre <= 0) {
								// 如果分数小于等于0的话,则直接踢出去试道场
								java.util.Map<Integer, String> charaIds = new HashMap<>();
								List<String> outNames = new ArrayList<>();
								String leaderName = "";
								for (Chara dead : duiwu) {
									// 当队伍数量大于3的时候才会移除排行榜信息
									dead.x = 128;
									dead.y = 52;
									GameLine.getGameMapname(dead.line, "天墉城")
											.join(GameObjectCharMng.getGameObjectChar(dead.id));
									charaIds.put(dead.id, "1");
									GameCommonUtil.sendTips("你已被淘汰出场。", dead.id);
									outNames.add(dead.name);
								}
								if (!outNames.isEmpty()) {
									leaderName = outNames.get(0);
								}
								log.error("试道积分：{}，队长：{}，队员成员：{}，被赶出了试道场", c.chara.shidaoScore, leaderName, outNames);
							}
							// 获取队伍队长,存放到map等待使用
							commander.put(duiwu.get(0).uuid, duiwu);
							for (Map.Entry<String, List<Chara>> m : commander.entrySet()) {
								// 队伍
								List<Chara> teams = m.getValue();
								// 排名后的map
								Map<String, Integer> rankMap = GameShiDao.rankMap.get(m.getKey());
								// 排名
								int sortRank = MapUtils.getIntValue(rankMap, "rank");
								// 对决值
								int pkValue = MapUtils.getIntValue(rankMap, "pkValue");
								// 试道积分
								int totalScore = MapUtils.getIntValue(rankMap, "totalScore");
								for (Chara chara : teams) {
									GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(),
											GameCommonUtil.shidaoTaskInfoNo3(pkValue, totalScore, sortRank), chara.id);
								}
							}
						}
					}
				}
			}
			log.info("刷新试道大会信息-----");
		}

	}

	/**
	 * 试道排行信息
	 * 
	 *
	 */
	@Getter
	@Setter
	@ToString
	public static class ShiDaoRank {
		private int score;
		private String uuid;
		private List<Chara> team;
		private int tao;
		private int shidaoScore;
		private long outTime;
		private String teamLeaderName;
		//是否结算过
		private boolean isGetReward;

		/**
		 * 试道排序对象
		 * @param score pk分值
		 * @param tao 道行
		 * @param shidaoScore 试道分
		 * @param outTime 淘汰时间
		 * @param teamLeaderName 队长名称
		 * @param team 队伍信息
		 */
		public ShiDaoRank(int score, int tao, int shidaoScore,long outTime, String teamLeaderName, List<Chara> team) {
			this.score = score;
			this.tao = tao;
			this.shidaoScore = shidaoScore;
			this.outTime = outTime;
			this.teamLeaderName = teamLeaderName;
			this.team = team;
		}

		public ShiDaoRank() {
		}

	}

	/**
	 * 获取该阶段所有试道人员信息
	 * 
	 * @param level 阶段等级
	 * @return
	 */
	public static List<GameObjectChar> getShiDaoMapSession(int level) {
		String uid = GameShiDao.getShiDaoJieDuan(level);
		if (maps != null) {
			// 得到当前阶段所有的地图
			List<GameZone> shiDaoMaps = maps.get(uid);
			List<GameObjectChar> gameObjectChars = new ArrayList<>();
			if (shiDaoMaps != null && !shiDaoMaps.isEmpty()) {
				for (GameZone gameZone : shiDaoMaps) {
					gameObjectChars.addAll(gameZone.getSessionList());
				}
			}
			return gameObjectChars;
		}
		return new ArrayList<>();
	}

	/**
	 * 获取该阶段所有地图信息
	 * 
	 * @param level 阶段等级
	 * @return
	 */
	public static List<GameZone> getShiDaoMap(int level) {
		String uid = GameShiDao.getShiDaoJieDuan(level);
		// 得到当前阶段所有的地图
		if (maps != null) {
			List<GameZone> shiDaoMaps = maps.get(uid);
			return shiDaoMaps;
		}
		return new ArrayList<>();
	}

	/**
	 * 进入试道地图
	 * 
	 * @param level
	 * @return
	 */
	public static GameZone enterShiDaoMap(int level) {
		String uid = GameShiDao.getShiDaoJieDuan(level);
		if (maps != null) {
			// 得到当前阶段所有的地图
			List<GameZone> shiDaoMaps = maps.get(uid);
			for (GameZone gameZone : shiDaoMaps) {
				// 如果大于15则直接返回
				if (gameZone.getSessionList().size() >= 55) {
					continue;
				}
				return gameZone;
			}
			// 创建新的地图
			int mapId = 38004;
			int line = 1;
			GameZone newGameZone = GameLine.createShidaoZoneGameMap(line, mapId);
			shiDaoMaps.add(newGameZone);
			return newGameZone;
		}
		return null;
	}
	
	
	/**
	 * 获取某阶段试道场内还有多少只队伍
	 * @param stageLevel 阶段等级
	 * @return
	 */
	public static int getTeamSize(int stageLevel) {
		//地图玩家缓存
		List<GameObjectChar> size = GameShiDao.getShiDaoMapSession(stageLevel);
		// 获取到试道阶段地图所有玩家
		List<GameObjectChar> sessionList = size;
		if (sessionList == null || sessionList.isEmpty()) {
			return 0;
		}
		//这是总阶段队伍
		Set<String> allTeamSize = new HashSet<>();
		for (GameObjectChar game : sessionList) {
			if (GameCommonUtil.isNotGameTeam(game.gameTeam)) {
				allTeamSize.add(game.gameTeam.duiwu.get(0).uuid);
			}
		}
		return allTeamSize.size();
	}
}