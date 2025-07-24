package com.fengshen.server.job;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.server.process.chat.CMD_CHAT_EX;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Notice;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaBaseInfo;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameBossTianDiXing;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameGongCheng;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.util.GameConfig;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

// 定时任务
@Component
@Slf4j
@Async
public class SaveCharaTimes {

	private long gonggaotim;
	public int[] coins;
	public int[] jiage;
	

	public static AtomicBoolean lock = new AtomicBoolean(false);

	public SaveCharaTimes() {
		this.coins = new int[] { 18000, 90000, 360000, 750000, 1284000, 1800000, 2844000, 3900000, 9000000, 14400000,
				25500000 };
		this.jiage = new int[] { 6, 30, 100, 200, 328, 500, 648, 1000, 2000, 3000, 5000 };
		this.gonggaotim = System.currentTimeMillis();
	}

	/**
	 * 5分钟自动存档一次
	 */
	@Scheduled(cron = "0 */5 * * * ?")
	public void autoTimeSaveCharaInfo() {
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		for (GameObjectChar gameSession : all) {
			if (gameSession.chara != null) {
				saveCharaInfo(gameSession);
			}
		}
		log.error("执行存档操作.....");
	}

	/**
	 * 保存对象信息
	 * 
	 * @param gameSession
	 */
	public static void saveCharaInfo(GameObjectChar gameSession) {
		try {
			Chara chara = gameSession.chara;
			Characters update = new Characters();
			update.setId(gameSession.characters.getId());
			// 如果是元婴
			CharaBaseInfo setInfo = setInfo(chara);
			if (chara.upgrade_state != 0) {
				chara.charaYuanyingInfo = setInfo;
				chara.level = chara.upgrade_level;
				update.setLevel(chara.upgrade_level);
			} else {
				chara.charaRealInfo = setInfo;
				chara.realLevel = chara.level;
				update.setLevel(chara.level);
			}
			//4.8.1开始
			update.setGoldCoin(chara.goldCoin);
			update.setPolar(chara.polar);
			update.setMapId(chara.mapid);
			update.setMapName(chara.mapName);
			update.setX(chara.x);
			update.setY(chara.y);
			update.setSex(chara.sex);
			update.setChargeScore(chara.chargeScore);
			update.setPortrait(chara.waiguan);
			//4.8.1开始
			// 把角色需要的信息复制到这个对象中
			SaveChara saveChara = com.fengshen.server.util.BeanUtils.clone(chara, SaveChara.class);
			String jsonString = JSONObject.toJSONString(saveChara);
			update.setData(jsonString);
			
			
			/*于v6.3.5优化重量级数据*/
			List<Goods> cloneTexiaos = com.fengshen.server.util.BeanUtils.cloneList(chara.texiao);
			GameCommonUtil.setGoodsDefaultValue(cloneTexiaos, false);
			update.setTexiao(JSONObject.toJSONString(cloneTexiaos));
			
			//时装
			List<Goods> cloneShizhuang = com.fengshen.server.util.BeanUtils.cloneList(chara.shizhuang);
			GameCommonUtil.setGoodsDefaultValue(cloneShizhuang, false);
			update.setShizhuang(JSONObject.toJSONString(cloneShizhuang));
			
			//跟宠
			List<Goods> cloneGenchong = com.fengshen.server.util.BeanUtils.cloneList(chara.genchong);
			GameCommonUtil.setGoodsDefaultValue(cloneGenchong, false);
			update.setGenchong(JSONObject.toJSONString(cloneGenchong));
			
			//背包
			List<Goods> goodsAll = new ArrayList<>();
			goodsAll.addAll(chara.backpack);
			goodsAll.addAll(chara.getOtherGoods());
			List<Goods> cloneBackpack = com.fengshen.server.util.BeanUtils.cloneList(goodsAll);
			GameCommonUtil.setGoodsDefaultValue(cloneBackpack, false);
			update.setBackpack(JSONObject.toJSONString(cloneBackpack));
			
			//卡套
			List<Goods> cloneCardStore = com.fengshen.server.util.BeanUtils.cloneList(chara.cardStore);
			GameCommonUtil.setGoodsDefaultValue(cloneCardStore, false);
			update.setCardStore(JSONObject.toJSONString(cloneCardStore));

			//仓库
			List<Goods> cloneCangku = com.fengshen.server.util.BeanUtils.cloneList(chara.cangku);
			GameCommonUtil.setGoodsDefaultValue(cloneCangku, false);
			update.setCangku(JSONObject.toJSONString(cloneCangku));
			
			//自定义时装
			List<Goods> cloneCustomShizhuang = com.fengshen.server.util.BeanUtils.cloneList(chara.customShizhuang);
			GameCommonUtil.setGoodsDefaultValue(cloneCustomShizhuang, false);
			update.setCustomShizhuang(JSONObject.toJSONString(cloneCustomShizhuang));
			
			//太阴之气-魂窍
			List<Goods> cloneTyzqStore = com.fengshen.server.util.BeanUtils.cloneList(chara.tyzqStore);
			GameCommonUtil.setGoodsDefaultValue(cloneTyzqStore, false);
			update.setTyzqStore(JSONObject.toJSONString(cloneTyzqStore));
			/*于v6.3.5优化重量级数据*/
			
			//宠物仓库
			update.setPetStore(JSONObject.toJSONString(chara.petStores));
			//守护
			update.setListshouhu(JSONObject.toJSONString(chara.listshouhu));
			
			
			// 设置宠物信息
			for (Petbeibao p : chara.pets) {
				CharaPet cp = new CharaPet();
				cp.setId(p.id);
				cp.setPet(JSONObject.toJSONString(p));
				cp.setUpdateTime(new Date());
				cp.setOwnerName(chara.name);
				GameData.that.charaPetService.updateByPrimaryKeySelective(cp);
			}
			// 以下设置其他信息
			GameData.that.baseCharactersService.updateById(update);
		} catch (Exception e) {
			log.error("执行存档操作出现一次异常--角色名为：{}", gameSession.chara.name);
			log.error("{}", e);
		}
	}

	//设置个人属性
	public static CharaBaseInfo setInfo(Chara chara) {
		CharaBaseInfo info = new CharaBaseInfo();
		info.phy_power = chara.phy_power;
		info.life = chara.life;
		info.speed = chara.speed;
		info.mag_power = chara.mag_power;
		info.accurate = chara.accurate;
		info.attribPoint = chara.attribPoint;
		info.def = chara.def;
		info.dex = chara.dex;
		info.wiz = chara.wiz;
		info.mana = chara.mana;
		info.parry = chara.parry;
		info.max_life = chara.max_life;
		info.max_mana = chara.max_mana;
		info.metal = chara.metal;
		info.wood = chara.wood;
		info.water = chara.water;
		info.fire = chara.fire;
		info.earth = chara.earth;
		info.polarPoint = chara.polarPoint;
		info.chongwuchanzhanId = chara.chongwuchanzhanId;
		info.chongwuluezhenId = chara.chongwuluezhenId;
		info.zuoqiId = chara.zuoqiId;
		info.yidongsudu = chara.yidongsudu;
		info.zuowaiguan = chara.zuowaiguan;
		info.zuoqiwaiguan = chara.zuoqiwaiguan;
		info.tao = chara.tao;
		info.taoPoint = chara.taoPoint;
		info.autofight_select = chara.autofight_select;
		info.autofight_skillaction = chara.autofight_skillaction;
		info.autofight_skillno = chara.autofight_skillno;
		info.userAutoAddPoint = chara.userAutoAddPoint;
		info.jiNengList = chara.jiNengList;
		info.equipPage = chara.equipPage;
		for (Goods g : chara.otherGoods) {
			if (g.pos > 0 && g.pos < 21 || g.pos == 40) {
				if (info.equip == null) {
					info.equip = new HashMap<Integer, Goods>();
				}
				info.equip.put(g.pos, g);
			}
		}
		return info;
	}
	@Async("taskThreadPool")
	@Scheduled(fixedDelay = 1000L)
	public void time1s() {
		final List<FightContainer> listFight = FightManager.listFight;
		final long time = System.currentTimeMillis();
		for (final FightContainer fightContainer : listFight) {
			if (fightContainer == null) {
				continue;
			}
			try {
				if (fightContainer.state.get() == 1 && fightContainer.roundTime + 2000L < time) {
					log.info("回合开始："+fightContainer.state.get()+"当前回合："+fightContainer.round+"战斗容器："+fightContainer.uid);
					ExecutorsUtils.getExecutorPools().execute(() -> {
						synchronized (fightContainer) {
							FightManager.doAutoSkill(fightContainer);
						}
					});
				}
				//1准备战斗等待操作,3战斗中,4战斗结束
				if (fightContainer.state.get() != 1 || fightContainer.roundTime + 24000L >= time) {
					log.info("回合开始："+fightContainer.state.get()+"当前回合："+fightContainer.round+"战斗容器："+fightContainer.uid);
					int roundTimeOut =5 * 25;
					if (fightContainer.state.get() >= 3 && fightContainer.roundTime + roundTimeOut * 1000L < time) {
						log.warn("战斗回合超时,下一回合或结束战斗 战斗状态:{}", fightContainer.state.get());
						if (fightContainer.state.compareAndSet(3, 1) || fightContainer.state.get() == 4) {
							FightManager.nextRoundOrSendOver(fightContainer);
						}
					}
					continue;
				}
				if (fightContainer.beginTime + 15 * 60 * 1000 < time) {
					log.debug("战斗开始时间:{}", DateUtil.formatDateTime(new Date(fightContainer.beginTime)));
					log.debug("战斗超时,结束战斗");
					CMD_CHAT_EX.endCombat(fightContainer);
					continue;
				}
				ExecutorsUtils.getExecutorPools().execute(() -> {
					synchronized (fightContainer) {
						FightManager.doTimeupSkill(fightContainer);
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
				log.error("定时器自动战斗异常,出错: {}", e);
			}
		}
	}
	
//	@Scheduled(fixedDelay = 1000L)
//	public void autoCheckFightTimeout() {
//		// 从战斗管理器中读取战斗容器
//		List<FightContainer> listFight = FightManager.listFight;
//		for (FightContainer fightContainer : listFight) {
//			if (fightContainer == null) {
//				continue;
//			}
//			long time = System.currentTimeMillis();
//			long pre = time-fightContainer.endTime.get();
//			if(fightContainer.endTime.get()>0 && pre > 5000) {
//				//如果战斗结束了
//				fightContainer.endTime.set(0);
//				if (FightManager.isOver(fightContainer)) {
//					FightManager.listFight.remove(fightContainer);
//					FightManager.sendOver(fightContainer, false);
//				}else {
//					if (fightContainer.state.compareAndSet(3, 1) || fightContainer.state.get() == 4) {
//						fightContainer.round+=1;
//						fightContainer.roundTime = System.currentTimeMillis();
//						fightContainer.state.set(1);
//						FightManager.nextRound(fightContainer);
//					}
//				}
//				log.info("战斗超时--------------------{}",fightContainer.uid);
//			}
//		}
//	}
	

	//每一小时刷新一次战神
	@Scheduled(cron = "0 0 0/1 * * ?")
	public void onFlushHaidaoAndZhanshen() {
		GameGongCheng.sendZhanshen(GameLine.gameGongCheng);
	}

	@Scheduled(cron = "*/10 * * * * ?")
	public void autofightgonggao() {
		long time = System.currentTimeMillis();
		List<Notice> all = (List<Notice>) GameData.that.baseNoticeService.findAll();
		for (int i = 0; i < all.size(); ++i) {
			if (this.gonggaotim + all.get(i).getTime() * 60000 < time) {
				Vo_16383_0 vo_16383_0 = new Vo_16383_0();
				vo_16383_0.channel = 19;
				vo_16383_0.id = 0;
				vo_16383_0.name = "";
				vo_16383_0.msg = all.get(i).getMessage().replaceAll("\r|\n", " ");
				long times = System.currentTimeMillis() / 1000L;
				vo_16383_0.time = (int) times;
				vo_16383_0.privilege = 0;
				vo_16383_0.server_name = GameConfig.lineName;
				vo_16383_0.show_extra = 2;
				vo_16383_0.compress = 0;
				vo_16383_0.orgLength = 65535;
				vo_16383_0.cardCount = 0;
				vo_16383_0.voiceTime = 0;
				vo_16383_0.token = "";
				vo_16383_0.checksum = 0;
				vo_16383_0.iid_str = "";
				vo_16383_0.has_break_lv_limit = 0;
				vo_16383_0.skill = 1;
				vo_16383_0.type = 1;
				GameObjectCharMng.sendAll(new M16383_0(), vo_16383_0);
				this.gonggaotim = System.currentTimeMillis();
			}
		}
	}

	public static List<GameObjectChar> insertionSort(List<GameObjectChar> sessionList) {
		for (int i = 0; i < sessionList.size() - 1; ++i) {
			for (int j = i + 1; j > 0 && sessionList.get(j - 1).chara.shidaodaguaijifen < sessionList
					.get(j).chara.shidaodaguaijifen; --j) {
				GameObjectChar temp = sessionList.get(j);
				sessionList.set(j, sessionList.get(j - 1));
				sessionList.set(j - 1, temp);
			}
		}
		return sessionList;
	}

	@Scheduled(cron = "*/2 * * * * ?")
	public void autofightromve() {
		List<GameObjectChar> sessionList = GameObjectCharMng.getGameObjectCharList();
		long time = System.currentTimeMillis();
		for (int i = 0; i < sessionList.size(); ++i) {
			try {
				GameObjectChar gameObjectChar = sessionList.get(i);
				if (gameObjectChar != null) {
					//试道如果队伍解散的话，传送回天墉城
					if (gameObjectChar.gameMap.id == 38004 && gameObjectChar.gameTeam == null) {
						// 如果是gm的话不传送
						if (gameObjectChar.privilege != 1000) {
							//设置标识
							gameObjectChar.shiDaoFlag.set(false);
							GameUtilRenWu.shidaohuicheng(gameObjectChar.chara);
						}
					}
					// 秒数
					int echoTime = (int) ((time - gameObjectChar.heartEcho) / 1000);
					int autoDsiConnectionTime = GameConfig.config.getBaseConfig().getAutoDisConnectionTime();
					if(echoTime > 20) {
						//20秒还未回应
						gameObjectChar.isBack.compareAndSet(false, true);
					}
					// 表示不开启自动断线
					if (autoDsiConnectionTime == 0) {
						continue;
					}
					// 自动断线时间5分钟
					if (gameObjectChar.heartEcho > 0 && echoTime > autoDsiConnectionTime) {
						if (gameObjectChar.chara != null) {
							gameObjectChar.offline();
							gameObjectChar.heartEcho = 0;
						}	
					}
				}
			} catch (Exception e) {
				SaveCharaTimes.log.error("", e);
			}
		}
	}

	

	public void fenghao(Chara chara) {
		// 此人属性异常直接做封号处理
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_KICK_OFF(),
				"对不起，系统检测到您的角色异常,该账号已被停用，请端正您的行为.(有任何问题请联系GM)");
		GameObjectCharMng.getGameObjectChar(chara.id).characters.setBlock(1);
		GameObjectCharMng.getGameObjectChar(chara.id).offline();
	}
	
	
	@Scheduled(fixedDelay = 1000L)
	public void timer1s() {
		Iterator<Entry<Integer, Vo_APPEAR>> iterator = GameBossTianDiXing.xing.entrySet().iterator();
		//星星刷新
		while(iterator.hasNext()) {
			if (iterator == null)
			{
				break;
			}
			Entry<Integer, Vo_APPEAR> next = iterator.next();
			Vo_APPEAR xingxing = next.getValue();
			if(xingxing != null) {
				//星星3分钟出现
				//这里从3分钟改成30S 记得改回来++++++
				if(System.currentTimeMillis() > (xingxing.time+180000) && xingxing.state == 0) {
					xingxing.state = 1;
					xingxing.isHide = 0;
					xingxing.time = System.currentTimeMillis();
					GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), xingxing, xingxing.mapid);
					//刷新谣言
					GameUtil.sendYaoYan(org.apache.commons.lang3.StringUtils.join("#Y" , xingxing.name , "#R(" , xingxing.level ,"级)#n已经出现在#Z"
							, xingxing.mapName , "|1线" , "#Z，各位道友可前往挑战！超过10分钟#Y" , xingxing.name , "#R("
							,xingxing.level , "级)" , "#n将会消失！若星君被击败也将离去，请各位道友可千万别错过。"));
					
					//从地图选中一个人
					GameMap map = GameLine.getGameMap(1, xingxing.mapName);
					if(map != null) {
						List<GameObjectChar> sessionList = map.getSessionList();
						// 过滤后的人选
						List<GameObjectChar> filterSession = new ArrayList<>();
						for (GameObjectChar session : sessionList) {
							String string = GameData.that.redisUtils.get(org.apache.commons.lang3.StringUtils.join("randomSwitchChara_",xingxing.id));
							if (com.mysql.jdbc.StringUtils.isNullOrEmpty(string)) {
								// 为空的人才会成为选中人
								filterSession.add(session);
							}
						}
						if (filterSession != null && !filterSession.isEmpty()) {
							// 随机选中的人
							GameObjectChar randomGameObject = filterSession.get(ThreadLocalRandom.current().nextInt(sessionList.size()));
							if(randomGameObject.chara.level<xingxing.level-29) {
								continue;
							}
							GameData.that.redisUtils.set(org.apache.commons.lang3.StringUtils.join("randomSwitchChara_" , xingxing.id),
									org.apache.commons.lang3.StringUtils.join(System.currentTimeMillis() + 60 * 1000 * 3 ,":",randomGameObject.chara.name),180);
							// 发送消息
							GameCommonUtil.sendTips(org.apache.commons.lang3.StringUtils.join(
									"#R恭喜#Y" , randomGameObject.chara.name , "#n,我乃#R" ,xingxing.name , "("
											, xingxing.level , "级)#n。遵天命,今特邀你在#R" , GameConfig.lineName , "1线"
											, xingxing.mapName , "#n处挑战。我只等你3分钟,请速来挑战。如果挑战成功,将会获得丰厚的奖励。"),
									randomGameObject);
						}
					}
				}else if(System.currentTimeMillis() > (xingxing.time+600000)&& xingxing.state == 1) { //10分钟后消失
					xingxing.state = -1;
					iterator.remove();
					GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), xingxing.id, xingxing.mapid);
					GameCore.fightObject.remove(xingxing.id);
				}
			}
		}
	}
//	@Scheduled(fixedDelay = 1000L)
//	public void nextRoundTimer() {
//		//获取
//		Map<Integer, GameObjectChar> endGame = FightManager.endGame;
//		Iterator<Entry<Integer, GameObjectChar>> iterator = endGame.entrySet().iterator();
//		while(iterator.hasNext()) {
//			Entry<Integer, GameObjectChar> info = iterator.next();
//			GameObjectChar gameObjectChar = info.getValue();
//			log.info("玩家={}",gameObjectChar.chara.name);
//			if (gameObjectChar != null && !gameObjectChar.isEndRound.get()) {
//				FightContainer fightContainer = FightManager.getFightContainer(gameObjectChar.chara.id);
//				if(fightContainer != null) {
//					ChannelFuture cf = gameObjectChar.ctx.write(new CommonCmd(9999).write(null));
//					cf.addListener(new ChannelFutureListener() {
//						@Override
//						public void operationComplete(ChannelFuture future) throws Exception {
//							// true标识通信成功
//							boolean success = future.isSuccess();
//							// 如果发送消息不成功，那标识此人有可能已经离线那就设置标识为true
//							if (!success) {
//								gameObjectChar.isBack.set(true);
//								gameObjectChar.isEndRound.set(true);
//								List<FightTeam> teamList = fightContainer.teamList;
//								List<GameObjectChar> gameObjectChars = new ArrayList<>();
//								for (FightTeam team : teamList) {
//									List<FightObject> fightObjectList = team.fightObjectList;
//									for (FightObject fightObject : fightObjectList) {
//										if (fightObject.type == 1) {
//											GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(fightObject.fid);
//											if (teamGame != null && teamGame.chara != null) {
//												gameObjectChars.add(teamGame);
//											}
//										}
//									}
//								}
//								AtomicBoolean flag = new AtomicBoolean(true);
//								// 循环队伍内的玩家
//								for (GameObjectChar teamGame : gameObjectChars) {
//									if (!teamGame.isEndRound.get()) {
//										log.info("战斗巡逻，不满足条件无法进行下一回合,{}", teamGame.chara.name);
//										flag.set(false);
//										break;
//									}
//								}
//								if (flag.get()) {
//									// 后台自动下一回合或者是结束
//									if (fightContainer.state.compareAndSet(3, 1) || fightContainer.state.get() == 4) {
//										FightManager.nextRoundOrSendOver(fightContainer, null);
//									}
//								}
//							}
//						}
//					});
//				}
//			}
//		}
//	}
}