package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.StallRecord;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.constant.StallStatus;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M12285_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * redis过期通知
 * 
 * 
 *
 */
@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {

	public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		String key = message.toString();
		// 珍宝
		if (key.startsWith(DefinedConst.GOLD_STALL_PREFIX)) {
			// 通过key获取 GOLDSTALL;asdsadas;1---0:标识1:商品id;2:状态
			String[] goodsKeyInfo = key.split(";");
			Example example = new Example(GoldStallNineGoods.class);
			example.createCriteria().andEqualTo("deleted", false).andEqualTo("goodsId", goodsKeyInfo[1]);
			GoldStallNineGoods gold = new GoldStallNineGoods();
			log.info("监听到珍宝订单={}", goodsKeyInfo[1]);
			// 如果商品状态为,//1公示 2正常出售 3超时
			int nextStepStatus = 1;
			if (goodsKeyInfo[2].equals("1")) {
				//如果系统开启了审核
				ConfigInfo orderStatus = GameData.that.configInfoService.getOneByKeyName("zhenbao_cost_order_status");
				if(orderStatus != null && "开启".equals(orderStatus.getData())) {
					nextStepStatus = StallStatus.getValue("审核中");
					Vo_MAILBOX_REFRESH vo = new Vo_MAILBOX_REFRESH();
					vo.id = GameCommonUtil.UUID();
					vo.type = 0;
					vo.sender = GameConfig.config.getBaseConfig().getGameName();
					vo.title = "珍宝状态订单";
					vo.create_time = (int) (System.currentTimeMillis() / 1000L);
					vo.expired_time = (int) (System.currentTimeMillis() / 1000L + 43200);
					vo.status = 0;
					vo.attachment = "";
					String gid = gold.getGid();
					GameObjectChar toGameObjectChara = GameObjectCharMng.getGameObjectCharByUUid(gid);
					Chara toChara = null;
					if(toGameObjectChara == null) {
						//不在线
						Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "data","id");
						toChara = JSONObject.parseObject(findOneByGid2.getData(), Chara.class);
					}else {
						toChara = toGameObjectChara.chara;
					}
					vo.msg = StringUtils.join("亲爱的#Y" , toChara.name ,"#n玩家你在珍宝上架的#R" , gold.getName()
					, "#n商品待系统审核通过后即可上架");
					vo.toGid = toChara.uuid;
					if(toGameObjectChara != null) {
						toGameObjectChara.sendOne(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(vo));
					}
					//审核记录-主人
					StallRecord builderGoldStallRecord = GameCommonUtil.builderGoldStallRecord(toChara, null, toChara, gold, 
							gold.getPrice());
					GameData.that.stallRecordService.insertSelective(builderGoldStallRecord);
				}else {
					// 设置为正常出售
					nextStepStatus = StallStatus.getValue("出售中");
					// 设置下架时间
					GameData.that.redisUtils.set(StringUtils.join(DefinedConst.GOLD_STALL_PREFIX , ";" , goodsKeyInfo[1] , ";" , nextStepStatus, ""),
							GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes() * 60);

					gold.setStartTime((int) (System.currentTimeMillis() / 1000L));
					gold.setEndTime((int) (System.currentTimeMillis() / 1000L)
							+ GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes() * 60);
				}
				
			} else if (goodsKeyInfo[2].equals("2")) {
				// 下一阶段过期
				nextStepStatus = StallStatus.getValue("已下架");
				// 判断是否有指定人,如果有指定人是否已经支付了定金
				GoldStallNineGoods oneGold = GameData.that.zhenbao.selectOneByExample(example);
				if (oneGold != null && !StringUtil.isNullOrEmpty(oneGold.getAppointeeName())) {
					//如果是正常出售才会设置过期
					if(oneGold.getStatus() == 2) {
						String extra = oneGold.getExtra();
						JSONObject parseObject = JSONObject.parseObject(extra);
						Vo_MAILBOX_REFRESH vo = new Vo_MAILBOX_REFRESH();
						//查询出配置
						ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("zhenbao_cost_type");
						if (parseObject.getIntValue("deposit_state") == 1) {
							// 商品到期指定人定金已经支付了,但是还未支付、卖家则获得定金一半银元宝的奖励
							int overFlowMoney = (int) (oneGold.getPrice() * 0.1);
							GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(oneGold.getGid());
							if (gameObject == null) {
								Characters findOneByGid2 = GameData.that.baseCharactersService
										.findOneByGid2(oneGold.getGid());
								Chara chara = JSONObject.parseObject(findOneByGid2.getData(), Chara.class);
								if(configInfo == null) {
									// 获得一半银元宝的定金
									GameUtil.addYinYuanBao(gameObject, overFlowMoney / 2);
								}else if("积分".equals(configInfo.getData())) {
									GameUtil.addchargeScore(gameObject, overFlowMoney / 2);
								}
								vo.toGid = chara.uuid;
								// 保存数据到数据库
								Characters update = new Characters();
								update.setId(findOneByGid2.getId());
								update.setData(JSONObject.toJSONString(chara));
								GameData.that.baseCharactersService.updateById(update);

							} else {
								// 在线状态
								Chara chara = gameObject.chara;
								if(configInfo == null) {
									// 获得一半银元宝的定金
									GameUtil.addYinYuanBao(gameObject, overFlowMoney / 2);
								}else if("积分".equals(configInfo.getData())) {
									GameUtil.addchargeScore(gameObject, overFlowMoney / 2);
								}
								ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
								GameObjectChar.send(new M65527_0(), listVo_65527_0, chara.id);

								GameObjectChar.send(new M65527_0(), listVo_65527_0, gameObject.chara.id);
								vo.id = GameCommonUtil.UUID();
								vo.type = 0;
								vo.sender = "珍宝系统";
								vo.title = "珍宝定金补偿";
								vo.msg = StringUtils.join("亲爱的#Y" , gameObject.chara.name , "#n玩家你在珍宝上架的#R" , oneGold.getName()
										, "时间到期了，指定人付了定金但未付余款你将获得一半定金的银元宝作为补偿#n，#Y" , overFlowMoney / 2
										, "#n银元宝已退到到您的账户请查收");
								vo.create_time = (int) (System.currentTimeMillis() / 1000L);
								vo.expired_time = (int) (System.currentTimeMillis() / 1000L + 43200);
								vo.status = 0;
								vo.attachment = "";
								vo.toGid = chara.uuid;
								GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(vo), gameObject.chara.id);
							}
							// 没收定金
							parseObject.put("deposit_state", 4);
							gold.setExtra(parseObject.toJSONString());
							// 保存邮件信息
							MailboxRefresh mail = new MailboxRefresh();
							mail.setAttachment(vo.attachment);
							mail.setToGid(vo.toGid);
							mail.setCreateTime(vo.create_time);
							mail.setExpiredTime(vo.expired_time);
							mail.setStatus(vo.status);
							mail.setTitle(vo.title);
							mail.setSender(vo.sender);
							mail.setType(0);
							mail.setMsg(vo.msg);
							mail.setGid(vo.id);
							GameData.that.mailboxRefreshService.insertSelective(mail);
							// 发送邮件提醒指定人定金被没收
							String[] split = oneGold.getAppointeeName().split(";");
							Vo_MAILBOX_REFRESH sendDeposit = new Vo_MAILBOX_REFRESH();
							sendDeposit.attachment = "";
							sendDeposit.toGid = split[1];
							sendDeposit.create_time = (int) (System.currentTimeMillis() / 1000L);
							sendDeposit.expired_time = (int) (System.currentTimeMillis() / 1000L + 12 * 60 * 60);
							sendDeposit.id = GameCommonUtil.UUID();
							sendDeposit.msg = StringUtils.join("亲爱的玩家#Y" , split[0] ,"#n你在珍宝上支付过的" , oneGold.getName() ,"因超时未支付，#Y"
									, (int) (oneGold.getPrice() * 0.1) , "#n定金被没收了。");
							sendDeposit.status = 0;
							sendDeposit.title = "珍宝支付超时";
							sendDeposit.type = 0;
							sendDeposit.sender = "珍宝系统";
							if (GameObjectCharMng.getGameObjectCharByUUid(split[1]) != null) {
								GameObjectChar gameObject2 = GameObjectCharMng.getGameObjectCharByUUid(split[1]);
								gameObject2.sendOne(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(sendDeposit));
							}
							MailboxRefresh mail2 = GameCommonUtil.convertMail(sendDeposit);
							GameData.that.mailboxRefreshService.insertSelective(mail2);
						}
					}
				}
			}
			gold.setUpdateTime(new Date());
			gold.setStatus(nextStepStatus);
			GameData.that.zhenbao.updateByExampleSelective(gold, example);
		} else if (key.startsWith(DefinedConst.CHANGE_CARD)) {
			// 变身卡
			// 通过key获取0:标识1:玩家uuid
			String[] keyInfo = key.split(";");
			GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(keyInfo[1]);
			if (gameObjectCharByUUid != null) {
				// 用户在线
				GameUtilRenWu.removeTask("千变万化", gameObjectCharByUUid.chara);
				gameObjectCharByUUid.chara.changeCardInfo = null;
				// 刷新
				gameObjectCharByUUid.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(gameObjectCharByUUid.chara));
				GameUtil.sendUpdate(gameObjectCharByUUid.chara);
			} else {
				Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGid2(keyInfo[1]);
				Chara chara = JSONObject.parseObject(findOneByGid2.getData(), Chara.class);
				chara.taskMap.remove("千变万化");
				chara.changeCardInfo = null;
				findOneByGid2.setData(JSONObject.toJSONString(chara));
				GameData.that.baseCharactersService.updateById(findOneByGid2);
			}
			log.info("变身卡过期事件");
		} else if (key.startsWith("XUANSHANG")) {
			// 悬赏任务
			String[] keyInfo = key.split(";");
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(keyInfo[1]);
			if (gameObjectChar != null) {
				GameUtilRenWu.removeTask("悬赏任务", gameObjectChar.chara);
				GameCommonUtil.dialogOk("悬赏任务已过期", gameObjectChar.chara.id);
			} else {
				// 不在线
				Characters findOneByGidSelectProperties = GameData.that.baseCharactersService
						.findOneByGidSelectProperties(keyInfo[1], "data", "id");
				String data = findOneByGidSelectProperties.getData();
				Chara chara = JSONObject.parseObject(data, Chara.class);
				chara.taskMap.remove("悬赏任务");
				GameData.that.baseCharactersService.updateByPrimaryKeySelective(findOneByGidSelectProperties);
			}
			// 删除地图得npc
			Integer npcId = Integer.valueOf(keyInfo[2]);
			Vo_APPEAR vo_APPEAR = GameShuaGuai.xuanshang.get(npcId);
			if (vo_APPEAR != null) {
				GameShuaGuai.xuanshang.remove(npcId);
				Map findById = GameData.that.baseMapService.findOneByMapId(vo_APPEAR.mapid);
				GameLine.getGameMap(1, findById.getMapId()).send(new M12285_0(), npcId);
			}
			log.info("悬赏过期事件");
		} else if (key.startsWith("longFengTimeOut")) { // 龙凤呈祥效果过期
			String[] keyInfo = key.split("_");
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(Integer.valueOf(keyInfo[1]));
			if (gameObjectChar != null) {
				// 效果为龙凤呈祥才会去初始化
				if (gameObjectChar.chara.special_icon == 42101 || gameObjectChar.chara.special_icon == 42102) {
					gameObjectChar.chara.special_icon = 0;
					Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(gameObjectChar.chara);
					gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
					// 提示用户
					GameCommonUtil.sendTips("你的龙凤呈祥礼服效果已失效！", gameObjectChar);
				}
			} else {
				// 不在线
				Characters findOneByGidSelectProperties = GameData.that.baseCharactersService
						.findOneByGidSelectProperties(keyInfo[1], "data", "id");
				if(findOneByGidSelectProperties != null) {
					String data = findOneByGidSelectProperties.getData();
					Chara chara = JSONObject.parseObject(data, Chara.class);
					// 效果为龙凤呈祥才会去初始化
					if (chara.special_icon == 42101 || chara.special_icon == 42102) {
						chara.special_icon = 0;
						GameData.that.baseCharactersService.updateByPrimaryKeySelective(findOneByGidSelectProperties);
					}
				}
			}
		}else if(key.startsWith("jieyu_tufei")) {
			//劫狱土匪
			String[] keyInfo = key.split(":");
			String gid = keyInfo[1];
			String npcId = keyInfo[2];
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
			if(gameObjectChar != null) {
				//删除任务
				GameUtilRenWu.removeTask("将功补过", gameObjectChar.chara);
				GameCommonUtil.sendTips("将功补过任务已过期", gameObjectChar);
			}
			Vo_APPEAR vo_APPEAR = GameCore.jieyuMonster.get(Integer.valueOf(npcId));
			if(vo_APPEAR != null) {
				int mapid = vo_APPEAR.mapid;
				//让npc在地图上消失
				GameLine.getGameMap(1, mapid).send(new MSG_DISAPPEAR(), vo_APPEAR.id);
			}
			//删除这个
			GameCore.jieyuMonster.remove(Integer.valueOf(npcId));
			GameCore.fightObject.remove(Integer.valueOf(npcId));
		}else if(key.startsWith("SHUT_CHARA")) {
			//禁言玩家
			String[] keyInfo = key.split(":");
			String gid = keyInfo[1];
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(gid);
			if(gameObjectChar != null) {
				//解除禁言
				gameObjectChar.chara.shut = 0;
				GameCommonUtil.sendTips("你已被解除禁言", gameObjectChar);
			}
			Example example = new Example(Characters.class);
			example.createCriteria().andEqualTo("gid", gid);
			Characters update = new Characters();
			update.setShut(0);
			GameData.that.baseCharactersService.updateByExampleSelective(update, example);
			
		} else if(key.startsWith("REMOVE_SHANGGU")) {
			//上古
			String[] keyInfo = key.split(":");
			int id = Integer.valueOf(keyInfo[1]);
			int mapId = Integer.valueOf(keyInfo[2]);
			GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), id, mapId);
			GameShuaGuai.shanggu.remove(id);
			GameCore.fightObject.remove(id);
		} else if(key.startsWith("REMOVE_WANNIAN")) {
			//万年
			String[] keyInfo = key.split(":");
			int id = Integer.valueOf(keyInfo[1]);
			int mapId = Integer.valueOf(keyInfo[2]);
			GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), id, mapId);
			GameShuaGuai.wannian.remove(id);
			GameCore.fightObject.remove(id);
		} else if(key.startsWith("REMOVE_GUIGUAI")) {
			//鬼怪
			String[] keyInfo = key.split(":");
			int id = Integer.valueOf(keyInfo[1]);
			int mapId = Integer.valueOf(keyInfo[2]);
			GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), id, mapId);
			GameShuaGuai.guiguai.remove(id);
			GameCore.fightObject.remove(id);
		}
		
		else if(key.startsWith("XINGXING_REFRESH")) {
			//星星刷新
			String[] keyInfo = key.split(":");
			int id = Integer.valueOf(keyInfo[1]);
			Vo_APPEAR xingxing = GameBossTianDiXing.xing.get(id);	
			if(xingxing != null) {
				xingxing.isHide = 0;
				GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), xingxing, xingxing.mapid);
				//10分钟让星星自动消失
				GameData.that.redisUtils.set(StringUtils.join("REMOVE_XINGXING:",xingxing.id,":",xingxing.mapid), 60*10, 60*10);
				//刷新谣言
				GameUtil.sendYaoYan(StringUtils.join("#Y" , xingxing.name , "#R(" , xingxing.level ,"级)#n已经出现在#Z"
						, xingxing.mapName , "|1线" , "#Z，各位道友可前往挑战！超过10分钟#Y" , xingxing.name , "#R("
						,xingxing.level , "级)" , "#n将会消失！若星君被击败也将离去，请各位道友可千万别错过。"));
				
				//从地图选中一个人
				GameMap map = GameLine.getGameMap(1, xingxing.mapName);
				if(map != null) {
					List<GameObjectChar> sessionList = map.getSessionList();
					// 过滤后的人选
					List<GameObjectChar> filterSession = new ArrayList<>();
					for (GameObjectChar session : sessionList) {
						String string = GameData.that.redisUtils.get(StringUtils.join("randomSwitchChara_",id));
						if (com.mysql.jdbc.StringUtils.isNullOrEmpty(string)) {
							// 为空的人才会成为选中人
							filterSession.add(session);
						}
					}
					if (filterSession != null && !filterSession.isEmpty()) {
						// 随机选中的人
						GameObjectChar randomGameObject = filterSession.get(ThreadLocalRandom.current().nextInt(sessionList.size()));
						GameData.that.redisUtils.set(StringUtils.join("randomSwitchChara_" , id),
						StringUtils.join(String.valueOf(System.currentTimeMillis() + 60 * 1000 * 3) ,":",randomGameObject.chara.name),180);
						// 发送消息
						GameCommonUtil.sendTips(StringUtils.join(
								"#R恭喜#Y" , randomGameObject.chara.name , "#n,我乃#R" ,xingxing.name , "("
										, xingxing.level , "级)#n。遵天命,今特邀你在#R" , GameConfig.lineName , "1线"
										, xingxing.mapName , "#n处挑战。我只等你3分钟,请速来挑战。如果挑战成功,将会获得丰厚的奖励。"),
								randomGameObject.chara.id);
					}
				}
			}
		}
		else if(key.startsWith("REMOVE_XINGXING")) {
			String[] keyInfo = key.split(":");
			int id = Integer.valueOf(keyInfo[1]);
			int mapId = Integer.valueOf(keyInfo[2]);
			GameBossTianDiXing.xing.remove(id);
			GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), id, mapId);
			GameCore.fightObject.remove(id);
		}
		
		else if(key.equals("GONGCHENG_BOSS")) {
			//刷新攻城boss
			ConfigInfo oneByKeyName = GameData.that.configInfoService.getOneByKeyName("gongcheng_boss_config");
			if(oneByKeyName != null) {
				JSONObject parseObject = JSONObject.parseObject(oneByKeyName.getData());
				int count = parseObject.getIntValue("count");
				//分钟
				int time = parseObject.getIntValue("time");
				//并在下回合刷新
				GameData.that.redisUtils.set(key, "", time*60);
				GameGongCheng.sendShuaGuai(count);
			}
		}else if(key.startsWith("TIMEOUT_TIMER_ROUND")) { //超过一小时了还在战斗则直接强制结束
			String[] keyInfo = key.split(":");
			String uid = String.valueOf(keyInfo[1]);
			FightContainer fightContainer = FightContainer.getFightContainer(uid);
			if(fightContainer != null) {
				FightManager.sendOver(fightContainer, true);
			}
		}else if (key.startsWith("fightTime_")) {
			String[] keyInfo = key.split("_");
			String fcid = String.valueOf(keyInfo[1]);
			FightContainer fightContainer = FightContainer.getFightContainer(fcid);
			if (fightContainer != null) {
				log.info("战斗超时----------");
				fightContainer.round+=1;
				fightContainer.roundTime = System.currentTimeMillis();
				fightContainer.state.set(1);
				FightManager.nextRound(fightContainer);
			}
		}else if (key.startsWith("fightCallFail_")) {
			String[] keyInfo = key.split("_");
			String fcid = String.valueOf(keyInfo[1]);
			FightContainer fightContainer = FightContainer.getFightContainer(fcid);
			if (fightContainer != null) {
				log.info("战斗超时----------");
				fightContainer.round+=1;
				fightContainer.roundTime = System.currentTimeMillis();
				fightContainer.state.set(1);
				FightManager.nextRound(fightContainer);
			}
		}else if(key.startsWith("CLEAR_ZHANSHEN")) {
			String[] keyInfo = key.split("=");
			JSONArray parseArray = JSONObject.parseArray(keyInfo[1]);
			for (int i = 0; i < parseArray.size(); i++) {
				int id = parseArray.getIntValue(i);
				Vo_APPEAR zhanshen = GameLine.gameGongCheng.zhanshenGuaiwu.get(id);
				if(zhanshen != null) {
					GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), id, zhanshen.mapid);
					GameCore.fightObject.remove(id);
					GameLine.gameGongCheng.zhanshenGuaiwu.remove(id);
				}
			}
		}
	}

	
	public static void set(GameObjectChar gameObjectChar, FightContainer fightContainer) {
		if(gameObjectChar != null) {
//			gameObjectChar.isBack.set(true);
//			gameObjectChar.isEndRound.set(true);
		}
		if(fightContainer != null) {
//			List<FightTeam> teamList = fightContainer.teamList;
//			List<GameObjectChar> gameObjectChars = new ArrayList<>();
//			for (FightTeam team : teamList) {
//				List<FightObject> fightObjectList = team.fightObjectList;
//				for (FightObject fightObject : fightObjectList) {
//					if (fightObject.type == 1) {
//						GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(fightObject.fid);
//						if (teamGame != null && teamGame.chara != null) {
//							gameObjectChars.add(teamGame);
//						}
//					}
//				}
//			}
			//循环队伍内的玩家
//			for (GameObjectChar teamGame : gameObjectChars) {
//				if (!teamGame.isEndRound.get() && !teamGame.isBack.get()) {
//					log.info("战斗巡逻，不满足条件无法进行下一回合,{}", teamGame.chara.name);
//					return;
//				}
//			}
			//如果战斗结束了
			if (FightManager.isOver(fightContainer)) {
				FightManager.listFight.remove(fightContainer);
				FightManager.sendOver(fightContainer, false);
				return;
			}
			// 后台自动下一回合或者是结束
//			if (fightContainer.state.compareAndSet(3, 1) || fightContainer.state.get() == 4) {
//				FightManager.nextRoundOrSendOver(fightContainer, gameObjectChar);
//			}
			fightContainer.state.compareAndSet(3, 1);
			fightContainer.round+=1;
			Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
			vo_19959_0.round = fightContainer.round;
			vo_19959_0.aid = 0;
			vo_19959_0.action = 0;
			vo_19959_0.vid = 0;
			vo_19959_0.para = 0;
			FightManager.send(fightContainer,new MSG_C_ACTION(), vo_19959_0);
			FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(0));
			FightManager.nextRound(fightContainer);
		}
	}
}