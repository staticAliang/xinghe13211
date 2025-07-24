package com.fengshen.server.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections.MapUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_TASK_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameShiDao.ShiDaoRank;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.game.GameZone;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 试道定时器
 * @author aaa
 *
 */
@Component
@Slf4j
@Async
public class ShiDaoTimer {
	/**
	 * 5秒钟执行一次试道通知
	 */
	@Scheduled(cron = "*/5 * * * * ?")
	public void shidaoGonggao() {
		String msg = "";
		boolean isOpen = GameUtilRenWu.dateToWeekDay();
		long time = System.currentTimeMillis();
		boolean isTimeOpen = GameUtilRenWu.belongCalendarshidao();
		if (isOpen && isTimeOpen) {
			String[] times = GameConfig.config.getShidao().getTimes();
			msg = "试道大会即将开始报名，请中洲各路英雄及时到试道申请人处报名参加，活动奖励丰富请积极参与，入口将于#R" + times[1] + "#n分关闭，请尽快组队参与。";
			GameUtil.sendSystemMessage(7, msg);
			GameShiDao.statzhuangtai = 1;
//			for (int level : GameShiDao.shidaolevel) {
//				//如果没有发送消息则初始化
//				GameMap shiDaoMap = GameLine.getShiDaoMap(level);
//				if (shiDaoMap == null)
//					return;
//				List<GameObjectChar> sessionList = shiDaoMap.sessionList;
//				for (GameObjectChar g : sessionList) {
//					Vo_SHIDAO_TASK_INFO vo_49177_0 = GameCommonUtil.shidaoTaskInfoNo1();
//					GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(), vo_49177_0, g.chara.id);
//				}
//			}
			GameShiDao.isSenMsg[0] = true;
			//初始化试道地图信息
			if(GameShiDao.maps == null || GameShiDao.maps.isEmpty()) {
				GameShiDao.maps = new ConcurrentHashMap<String,List<GameZone>>();
				//先创建基础地图
				int mapId = 38004;
				int line = 1;
				GameShiDao.maps.put("70-79", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("80-89", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("90-99",  Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("100-109", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("110-119", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("120-129", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("130-139", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("140-149", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("150-159", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("160-169", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
				GameShiDao.maps.put("170-179", Lists.newArrayList(GameLine.createShidaoZoneGameMap(line,mapId)));
			}
		}
		if (GameShiDao.statzhuangtai == 1 && GameShiDao.getStartTime() + GameShiDao.getJoinTime() < time) {
			//初始化
			GameShiDao.isOver = new Boolean[] { false, false, false, false, false, false, false, false, false, false, false, false};
			GameShiDao.isSenMsg = new Boolean[] { false, false, false};
			msg = "试道大会已经开始";
			GameUtil.sendSystemMessage(7, msg);
		}
	}
	
	//2分钟提醒试道
	@Scheduled(cron = "*/2 * * * * ?")
	public void notifyShiDaoMsg() {
		if (GameShiDao.statzhuangtai == 1) {
			String[] times = GameConfig.config.getShidao().getTimes();
			String msg = "试道大会即将开始报名，请中洲各路英雄及时到试道申请人处报名参加，活动奖励丰富请积极参与，入口将于#R" + times[1] + "#n分关闭，请尽快组队参与。";
			GameUtil.sendSystemMessage(7, msg);
		}
	}
	
	@Scheduled(cron = "*/3 * * * * ?")
	public void autofightshidao() {

		Integer[] shidaolevel = GameShiDao.shidaolevel;
		long time = System.currentTimeMillis();
		// 试道大会开始,准备开始刷怪
		if (GameShiDao.statzhuangtai == 1 && GameShiDao.getStartTime() + GameShiDao.getJoinTime() < time) {
			// 试道大会已经开始.
			for (int i = 0; i < shidaolevel.length; ++i) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(shidaolevel[i]);
				if (gameZone == null) {
					continue;
				}
				List<GameObjectChar> session = GameShiDao.getShiDaoMapSession(shidaolevel[i]);
				// 获取地图人数.
				if (session != null
						&& session.size() < GameConfig.config.getShidao().getTeamNumber()) {
					for (GameObjectChar g : session) {
						// 把当前地图的全部送回城，人数不足结束
						Chara ch = g.chara;
						// 全部带回城里
						ch.x = 131;
						ch.y = 54;
						Vo_20481_0 vo_20481_10 = new Vo_20481_0();
						vo_20481_10.msg = "当前阶段人数不足,已结束本场活动。";
						vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
						g.sendOne(new M20481_0(), vo_20481_10);
						GameLine.getGameMapname(ch.line, "天墉城").join(g);
						// 清空该阶段所有人员
						GameShiDao.getShiDaoSession(shidaolevel[i]).clear();
						GameShiDao.sortShiDao.remove(shidaolevel[i]);
						log.info("{}阶段人数不足已自动结束", shidaolevel[i]);
					}
					session.clear();
					gameZone.clear();
				}else {
					//刷新元魔
					for(GameMap gameMap:gameZone) {
						// 在刷新
						GameShiDao.refreShiDaoYuanMo(gameMap.gameShiDao, gameMap);
						gameMap.gameShiDao.shuaXingTime = System.currentTimeMillis();
						for (GameObjectChar goc : gameMap.sessionList) {
							// 获取到参与队伍的信息
							if (GameCommonUtil.isNotGameTeam(goc.gameTeam)) {
								List<Chara> list = goc.gameTeam.duiwu;
								if (list != null && !list.isEmpty()) {
									// 获取到某个队伍的信息,并计算队伍总积分.
									int teamYuanmoJifenCount = list.get(0).shidaodaguaijifen;
									// 遍历当前队伍信息
									for (Chara chara : list) {
										GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(),
												GameCommonUtil.shidaoTaskInfoNo2(teamYuanmoJifenCount, 0), chara.id);
									}
								}
							}
						}
					}
				}
			}
			GameShiDao.statTime = System.currentTimeMillis();
			GameShiDao.statzhuangtai = 2;
		}
		// 刷新元魔阶段
		if (GameShiDao.statzhuangtai == 2) {
			for (int i = 0; i < shidaolevel.length; ++i) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(shidaolevel[i]);
				if (gameZone == null)
					continue;
				for(GameMap gameMap:gameZone) {
					// 人数满足要求刷新
					if (gameMap != null && gameMap.sessionList != null
							&& gameMap.sessionList.size() >= GameConfig.config.getShidao().getTeamNumber()) {
						if (gameMap.gameShiDao.shuaXingTime + 180000L < time) {
							// 当数量小于50的时候才去刷新
							if (gameMap.gameShiDao.shidaoyuanmo.size() < 50) {
								GameShiDao.refreShiDaoYuanMo(gameMap.gameShiDao, gameMap);
							}
							gameMap.gameShiDao.shuaXingTime = System.currentTimeMillis();
						}
						//第一次则刷新，后面不刷新
						List<GameObjectChar> sessionList = gameMap.sessionList;
						for (GameObjectChar goc : sessionList) {
							// 获取到参与队伍的信息
							if (GameCommonUtil.isNotGameTeam(goc.gameTeam)) {
								List<Chara> list = goc.gameTeam.duiwu;
								if (list != null && !list.isEmpty()) {
									// 获取到某个队伍的信息,并计算队伍总积分.
									int teamYuanmoJifenCount = list.get(0).shidaodaguaijifen;
									// 遍历当前队伍信息
									for (Chara chara : list) {
										GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(),
												GameCommonUtil.shidaoTaskInfoNo2(teamYuanmoJifenCount, 0), chara.id);
									}
								}
							}
						}
					}
				}
			}
			GameShiDao.isSenMsg[1] = true;
		}
		// 第三阶段
		if (GameShiDao.statzhuangtai == 2 && GameShiDao.statTime + GameShiDao.getDurationTime() < time) {
			GameShiDao.statzhuangtai = 3;
			GameShiDao.statTime = System.currentTimeMillis();
			// 进入决赛
			for (int i = 0; i < shidaolevel.length; i++) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(shidaolevel[i]);
				if (gameZone == null)
					continue;
				for(GameMap gameMap:gameZone) {
					for (int j = 0; j < gameMap.gameShiDao.shidaoyuanmo.size(); ++j) {
						Vo_APPEAR v = gameMap.gameShiDao.shidaoyuanmo.get(j);
						gameMap.send(new MSG_DISAPPEAR(), v.id);
					}
					// 清除所有元魔
					gameMap.gameShiDao.shidaoyuanmo.clear();
					List<GameObjectChar> sessionList = gameMap.sessionList;
					for (GameObjectChar g : sessionList) {
						Chara chara = g.chara;
						// 排名后的map
						Map<String, Integer> rankMap = GameShiDao.rankMap.get(chara.getUuid());
						// 总分
						int pkValue = MapUtils.getIntValue(rankMap, "pkValue");
						// 遍历当前队伍信息
						GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(), GameCommonUtil.shidaoTaskInfoNo3(pkValue, 0, 0),
								chara.id);
						// 把所有正在击杀元魔的队伍强制退出战斗
						if (chara.isFight) {
							FightContainer fightContainer = FightManager.getFightContainer(chara.id);
							if (fightContainer != null) {
								FightManager.sendOver(fightContainer, true);
								FightManager.listFight.remove(fightContainer);
							}
						}
						// 初始化PK对决值和试道积分
						chara.shidaoPkSocre = 4;
						chara.shidaoScore = 0;
					}
				}
			}
			GameShiDao.gonggaoTime = System.currentTimeMillis();
		}
		// 巅峰阶段
		if (GameShiDao.statzhuangtai == 3) {
			int flag = 0;
			for (int i = 0; i < shidaolevel.length; ++i) {
				List<GameZone> gameZone = GameShiDao.getShiDaoMap(shidaolevel[i]);
				if (gameZone == null || gameZone.isEmpty()) {
					flag++;
					continue;
				}
				//子地图队伍人数
				List<GameZone> mapTeamInfos = new ArrayList<>();
				for(GameZone gameMap:gameZone) {
					if(gameMap != null && gameMap.sessionList.size()>=1) {
						HashMap<String,List<Chara>> mapTeamSize = new HashMap<>();
						for (GameObjectChar game : gameMap.getSessionList()) {
							if (GameCommonUtil.isNotGameTeam(game.gameTeam)) {
								mapTeamSize.put(game.gameTeam.duiwu.get(0).uuid,game.gameTeam.duiwu);
							}
						}
						if (mapTeamSize.size() <= 1) {
							mapTeamInfos.add(gameMap);
						}
					}
				}
				//把队伍人数少的人全部集中到一个地图
				for(GameZone gameMap:mapTeamInfos) {
					for(GameZone gameMap2:gameZone) {
						if(gameMap2.uid.equals(gameMap.uid)) {
							continue;
						}
						for(GameObjectChar joinChar:gameMap.getSessionList()) {
							gameMap2.join(joinChar);
							//把这支队伍加入到这个地图
							GameCommonUtil.sendTips("系统已自动为你切换到活动地图", joinChar);
						}
						break;
					}
				}
				
				//地图玩家缓存
				List<GameObjectChar> size = GameShiDao.getShiDaoMapSession(shidaolevel[i]);
				// 获取到试道阶段地图所有玩家
				List<GameObjectChar> sessionList = size;
				if (sessionList == null || sessionList.isEmpty()) {
					flag++;
					GameShiDao.isOver[i] = true;
					continue;
				}
				//这是总阶段队伍
				Set<String> allTeamSize = new HashSet<>();
				for (GameObjectChar game : sessionList) {
					if (GameCommonUtil.isNotGameTeam(game.gameTeam)) {
						allTeamSize.add(game.gameTeam.duiwu.get(0).uuid);
					}
				}
				// 表示该阶段只有一支队伍，则直接结束该阶段的试道
				if (allTeamSize.size() <= 1) {
					// 结算试道
//					if(!GameShiDao.isOver[i]) {
						log.info("{}阶段开始结算",shidaolevel[i]);
						//结算标识
						GameShiDao.isOver[i] = true;
						GameShiDao.shidaoJieSuanByLevel(shidaolevel[i]);
						gameZone.clear();
//					}else {
//						log.info("{}=阶段的结算标识={}",shidaolevel[i],GameShiDao.isOver[i]);
//					}
					log.info("该阶段只剩一支队伍,自动结束该阶段===={}", shidaolevel[i]);
					flag++;
				} else {
					//大于0表示开启自动匹配
					if(GameConfig.config.getShidao().getFreeTime()>0) {
						// 存放正在休息队长的UUID
						Set<String> restShiDaoLeaderUUIDSet = new HashSet<>();
						for (GameObjectChar game : sessionList) {
							if (GameCommonUtil.isNotGameTeam(game.gameTeam)) {
								// 只拿队长信息
								Chara leader = game.gameTeam.duiwu.get(0);
								String string = GameData.that.redisUtils.get("shiDaoFight-" + leader.id);
								// 如果休息时间已到
								if (string == null && !leader.isFight) {
									//必须在试道场内
									if(leader.mapName.equals("试道场")) {
										restShiDaoLeaderUUIDSet.add(leader.uuid);
									}
								}
							}
						}
						// 休息的队伍必须大于1.
						if (!restShiDaoLeaderUUIDSet.isEmpty() && restShiDaoLeaderUUIDSet.size() > 1) {
							// 随机取出两个队伍进行PK
							List<String> restShiDaoLeaderUUIDList = new ArrayList<>(restShiDaoLeaderUUIDSet);
							// 随机取出第二个队伍
							String no1LeaderUUID = restShiDaoLeaderUUIDList.get(new Random().nextInt(restShiDaoLeaderUUIDList.size()));
							//删除第一个取出的队伍
							restShiDaoLeaderUUIDList.remove(no1LeaderUUID);
							//第二个队伍
							String no2LeaderUUID = restShiDaoLeaderUUIDList
									.get(new Random().nextInt(restShiDaoLeaderUUIDList.size()));
							// 两个绝对不能相同不然会卡战斗，多个判断安心点，上面取随机数是绝对不会重复的
							if (!no1LeaderUUID.equals(no2LeaderUUID)) {
								GameObjectChar no1GameObject = GameObjectCharMng.getGameObjectCharByUUid(no1LeaderUUID);
								GameObjectChar no2GameObject = GameObjectCharMng.getGameObjectCharByUUid(no2LeaderUUID);
								if (no1GameObject != null && no2GameObject != null) {
									if((!no1GameObject.chara.isFight
										&& !no2GameObject.chara.isFight) || 
											(FightManager.getFightContainer(no1GameObject.chara.id) == null && 
										FightManager.getFightContainer(no2GameObject.chara.id) == null)) {
										// 进入战斗
										FightManager.goFight(no1GameObject.chara, no2GameObject.chara);
									}
								}
							}
						}
					}
				}
				log.info("{}:阶段队伍人数:{}", shidaolevel[i], size.size());
			}
			// 如果标识大于或者等于试道阶段,表示所有试道都结束了.
			if (flag >= shidaolevel.length) {
				//结束试道.
				GameShiDao.statzhuangtai = 0;
				GameUtil.sendSystemMessage(7, "本次试道大会已全面结束,感谢大家的参与");
				GameShiDao.maps = null;
				return;
			}
		}
		// 到时间的结算
		if (GameShiDao.statzhuangtai == 3 && GameShiDao.statTime + GameShiDao.getPkTime() < time) {
			GameShiDao.statzhuangtai = 0;
			for (int i = 0; i < shidaolevel.length; ++i) {
				List<ShiDaoRank> list = GameShiDao.sortShiDao.get(shidaolevel[i]);
				// 结算试道
				if (list != null && !list.isEmpty()) {
//					if(!GameShiDao.isOver[i]) {
						log.info("{}阶段开始结算",shidaolevel[i]);
						//结算标识
						GameShiDao.isOver[i] = true;
						GameShiDao.shidaoJieSuanByLevel(shidaolevel[i]);
						list.clear();
//					}else {
//						log.info("{}=阶段的结算标识={}",shidaolevel[i],GameShiDao.isOver[i]);
//					}
				}
			}
			// 清除所有信息
			GameShiDao.cleanShidaoSession();
			GameShiDao.maps = null;
			GameUtil.sendSystemMessage(7, "本次试道大会已全面结束,感谢大家的参与");
		}
	}
	
	//试道对决阶段
	@Scheduled(cron = "*/5 * * * * ?")
	public void notifyShiDaoInfo3() {
		GameShiDao.notifyShiDaoInfo3();
	}
}