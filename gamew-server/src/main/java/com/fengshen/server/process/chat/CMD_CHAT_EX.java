package com.fengshen.server.process.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Charge;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.fight.Vo_C_END_COMBAT;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.look.MSG_LC_END_LOOKON;
import com.fengshen.server.data.write.look.MSG_LC_START_LOOKON;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.Gm;
import com.fengshen.server.domain.config.Mingan;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.fengshen.server.util.SensitiveWordInit;
import com.fengshen.server.util.SensitivewordFilter;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_CHAT_EX implements GameHandler {
	private int xhjq;
	private int lbdj;

	public CMD_CHAT_EX() {
		this.xhjq = 10000;
		this.lbdj = 60;
	}

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int channel = GameReadTool.readShort(buff); // 1=当前，2=世界，4=组队，5=帮派, 30=喇叭
		int compress = GameReadTool.readShort(buff);
		int orgLength = GameReadTool.readShort(buff);
		String msg = "";
		if(compress == 9999) {
			msg = GameReadTool.readString(buff);
		}else {
			msg = GameReadTool.readString2(buff);
		}
		int cardCount = GameReadTool.readShort(buff);
		String cardParams = "";
		for (int i = 0; cardCount > i; ++i) {
			cardParams = GameReadTool.readString(buff);
		}
		int voiceTime = GameReadTool.readInt(buff);
		String token = GameReadTool.readString2(buff);
		log.info("玩家聊天: orgLength={}，cardParams：{}，voiceTime={}，token={}",orgLength,cardParams,voiceTime,token);
		String para = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if (msg.equals("结束卡战斗")) {
			if(gameObjectChar.isLook ==1) {
				return;
			}
			//试道场内必须投票才能退出战斗
			if(chara.mapName.equals("试道场")) {
				FightContainer fightContainer = FightManager.getFightContainer(chara.id);
				if(fightContainer != null) {
					if(!(System.currentTimeMillis()>fightContainer.roundTime+90000)) {
						return;
					}
				}
			}
			if("activeForcePk".equals(gameObjectChar.action)
					||"passiveForcePk".equals(gameObjectChar.action) || "jieyu_qiuqing".equals(gameObjectChar.flag)) {
				FightContainer fightContainer = FightManager.getFightContainer(chara.id);
				if(fightContainer != null) {
					FightTeam fightTeam = FightManager.getFightTeam(fightContainer, chara.id);
					//主动结束的人则死亡状态
					for(FightObject fightObject:fightTeam.fightObjectList) {
						fightObject.state.set(3);
					}
					//敌方设为复活状态
					FightTeam fightTeamDm = FightManager.getFightTeamDM(fightContainer, chara.id);
					for(FightObject fightObject:fightTeamDm.fightObjectList) {
						fightObject.state.set(1);
					}
					fightContainer.state.set(4);
					FightManager.nextRoundOrSendOver(fightContainer, gameObjectChar);
				}
				return;
			}
			
			if("ctPk".equals(gameObjectChar.action) && chara.mapid == 5000) {
				FightContainer fightContainer = FightManager.getFightContainer(chara.id);
				if(fightContainer != null) {
					FightTeam fightTeam = FightManager.getFightTeam(fightContainer, chara.id);
					//主动结束的人则死亡状态
					for(FightObject fightObject:fightTeam.fightObjectList) {
						fightObject.state.set(3);
					}
					//敌方设为复活状态
					FightTeam fightTeamDm = FightManager.getFightTeamDM(fightContainer, chara.id);
					for(FightObject fightObject:fightTeamDm.fightObjectList) {
						fightObject.state.set(1);
					}
					fightContainer.state.set(4);
					FightManager.nextRoundOrSendOver(fightContainer, gameObjectChar);
				}
				return;
			}
			
			//如果当前对象正在强制PK,或者被PK中
			FightContainer fightContainer = FightManager.getFightContainer(chara.id);
			List<GameObjectChar> charas = new ArrayList<>();
			if(fightContainer != null) {
				List<FightTeam> fightTeams = fightContainer.teamList;
				for(FightTeam team:fightTeams) {
					List<FightObject> fightObjectList = team.fightObjectList;
					for(FightObject fightObject:fightObjectList) {
						if(fightObject.type == 1) {
							GameObjectChar obj = GameObjectCharMng.getGameObjectChar(fightObject.id);
							if(obj != null) {
								charas.add(obj);
							}
						}
					}
				}
				//让观战人员也退出
				for(Map.Entry<Integer, GameObjectChar> lookGame:fightContainer.lookCharas.entrySet()) {
					GameObjectChar look = lookGame.getValue();
					//观战人数
					look.sendOne(new MSG_LC_START_LOOKON(), new Integer[] {1,1});
					look.sendOne(new MSG_LC_END_LOOKON(), new Vo_C_END_COMBAT(1));
					look.isLook = 0;
					look.lookCharId = 0;
					GameCommonUtil.setCharaTitleFlag(look.chara);
					Map<String, Object> dataMap = new HashMap<>();
					dataMap.put("auto_fight", look.chara.autofight_select);
					look.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(look.chara.id, dataMap));
				}
				log.error("{}。玩家输入结束卡战斗",chara.name);
			}else {
				if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam,chara)) {
					for(Chara team:gameObjectChar.gameTeam.duiwu) {
						//如果队伍里面有他
						charas.add(GameObjectCharMng.getGameObjectChar(team.id));
					}
				}else {
					charas.add(gameObjectChar);
				}
			}
			//如果组队了
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam,chara)) {
				if(chara.id != gameObjectChar.gameTeam.duiwu.get(0).id) {
					//组队状态下只允许队长使用结束指令
					GameUtil.sendMeTips("组队状态下只允许队长使用结束指令");
					return;
				}
			}
			GameCommonUtil.endCombat(charas, fightContainer, gameObjectChar);
			return;
		}
		if (msg.equals("结束观战")) {
			gameObjectChar.isLook = 0;
			FightContainer fightContainer = FightManager.getFightContainer(gameObjectChar.lookCharId);
			if(fightContainer != null && fightContainer.lookCharas != null) {
				fightContainer.lookCharas.remove(gameObjectChar.chara.id);
			}
			gameObjectChar.lookCharId = 0;
			Map<String, Object> dataMap = new HashMap<>();
			dataMap.put("auto_fight", chara.autofight_select);
			gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, dataMap));
			gameObjectChar.sendOne(new MSG_LC_START_LOOKON(), new Integer[] {1,1});
			gameObjectChar.sendOne(new MSG_LC_END_LOOKON(), new Vo_C_END_COMBAT(1));
			GameCommonUtil.setCharaTitleFlag(chara);
			return;
		}
		
		if (msg.equals("问道小子TO_NEXT_FIGHT")) {
			FightContainer fightContainer = FightManager.getFightContainer(chara.id);
			log.info("{}问道小子继续下一次",chara.name);
			if(fightContainer != null) {
				int mid = 0;
				if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
					//获取队长id
					mid = gameObjectChar.gameTeam.duiwu.get(0).id;
				}else {
					mid = gameObjectChar.chara.id;
				}
				//队伍
				FightTeam fightTeamOne = null;
				//如果存在战斗容易输入结卡则默认这个队伍是死亡
				List<FightTeam> teamList = fightContainer.teamList;
				for(FightTeam fightTeam:teamList) {
					List<FightObject> fightObjectList = fightTeam.fightObjectList;
					for(FightObject f:fightObjectList) {
						if(f.id == mid) {
							fightTeamOne = fightTeam;
							break;
						}
					}
				}
				if(fightTeamOne != null) {
					//设置为死亡状态
					for(FightObject fightObject:fightTeamOne.fightObjectList) {
						fightObject.state.set(1);
					}
				}
				FightManager.sendOver(fightContainer, true);
				FightManager.listFight.remove(fightContainer);
				log.info("问道小子战斗容器不为空,结束战斗，玩家为:{}",chara.name);
			}else {
				List<GameObjectChar> charas = new ArrayList<>();
				if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
					for (Chara game : GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu) {
						charas.add(GameObjectCharMng.getGameObjectChar(game.id));
					}
				} else {
					charas.add(GameObjectCharMng.getGameObjectChar(chara.id));
				}
				GameCommonUtil.endCombat(charas, fightContainer, gameObjectChar);
				log.info("问道小子战斗容器为空,结束战斗，玩家名称:{}",chara.name);
			}
			return;
		}
		if(GameData.that.redisUtils.get("speakPauseTime_"+chara.id) != null && gameObjectChar.privilege == 0) {
			GameUtil.sendMeTips("请不要频繁发言");
			return;
		}
		//如果在指定时间内不停的说话
		if (GameData.that.redisUtils.get("sendMsg_" + chara.uuid) != null && gameObjectChar.privilege == 0) {
			Integer speakIntervalCount = GameConfig.config.getBaseConfig().getSpeakIntervalCount();
			int count = GameData.that.redisUtils.getIncr2("speakIntervalCount"+ chara.uuid);
			if(count>speakIntervalCount) {
				GameUtil.sendMeTips("请不要频繁发言");
				GameData.that.redisUtils.set("speakPauseTime_" + chara.uuid, chara.uuid, GameConfig.config.getBaseConfig().getSpeakPauseTime());
				return;
			}
		}else {
			GameData.that.redisUtils.delete("speakIntervalCount"+ chara.uuid);
			//说话间隔
			GameData.that.redisUtils.set("sendMsg_" + chara.uuid, chara.uuid, GameConfig.config.getBaseConfig().getSpeakIntervalTime());
		}
		
		//gm指令
		if(GameCommonUtil.gmCmd(gameObjectChar, msg)) {
			return;
		}
		//内测指令不过滤
		Gm gm = GameConfig.config.getGm();
		log.info("msg:"+msg);
		log.info("gm.getChongzhi():"+gm.getChongzhi());
		if (msg.startsWith("坐骑(") && msg.endsWith(")") && gm.getZuoji() == 1) {
			String zuoji = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			GameUtil.huodezuoji(chara, zuoji, "GM指令");
			return;
		}
		if (msg.startsWith("潜能(") && msg.endsWith(")") && gm.getQianneng() == 1) {
			String qianNeng = (msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
			if (qianNeng.length() > 9) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "潜能值过大，操作失败 ！";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.addQianNeng(chara, Integer.valueOf(qianNeng));
			ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_5);
			return;
		}
		if (msg.startsWith("道具(") && msg.endsWith(")") && gm.getDaoju() == 1) {
			String daoju = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(daoju);
			if (info == null) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "不存在道具：" + daoju;
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.huodedaoju(gameObjectChar, info, 1);
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了#R" + daoju + "#n";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		}
		if (msg.startsWith("积分(") && msg.endsWith(")") && gm.getJifen() == 1) {
			long jifen = 0;
			try {
				jifen = Long.valueOf(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
			} catch (Exception e) {
				log.error("{}", e);
				GameUtil.sendMeTips("请输入数字。");
				return;
			}
			if (jifen > Integer.MAX_VALUE) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "积分值过大，操作失败 ！";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.addchargeScore(gameObjectChar, (int)jifen, "GM指令");
			return;
		}
		if (msg.startsWith("法宝(") && msg.endsWith(")") && gm.getFabao() == 1) {
			String fabao = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			GameUtil.huodefabao(chara, fabao, 24, "GM指令");
			return;
		}

		if (msg.startsWith("充值(") && msg.endsWith(")") && gm.getChongzhi() == 1) {
			String chongzhi = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			String accountName =gameObjectChar.account.getName();
			log.info("检测到充值口令,账号："+accountName+" 金额："+chongzhi);
			this.huodechongzhi(chara, chongzhi,accountName);
			return;
		}


		if (msg.startsWith("经验(") && msg.endsWith(")") && gm.getJingyan() == 1) {
			long jingyan = 0;
			try {
				jingyan = Long.valueOf(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
			} catch (NumberFormatException e) {
				log.error("{}", e);
				GameUtil.sendMeTips("请输入数字。");
				return;
			}
			if (jingyan > Integer.MAX_VALUE) {
				jingyan = 2000000000;
			}
			Chara chara1 = gameObjectChar.chara;
			GameUtil.huodejingyan(chara1, (int) jingyan, "GM指令");
			ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara1);
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M65527_0(), listVo_65527_2);
			return;
		}
		if (msg.startsWith("装备(") && msg.endsWith(")") && gm.getZhuangbei() == 1) {
			String zbName = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(zbName);
			if (oneByStr == null) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "请输入正确的装备名！";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			if (oneByStr.getAmount() == 4 || oneByStr.getAmount() == 5 || oneByStr.getAmount() == 6) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "请用首饰GM获取首饰！";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.huodezhuangbei(chara, oneByStr, 1, 1);
			return;
		}
		if (msg.startsWith("首饰(") && msg.endsWith(")") && gm.getShoushi() == 1) {
			String ssName = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(ssName);
			if (oneByStr == null) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "请输入正确的首饰名！";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.huodezhuangbei(chara, oneByStr, 0, 1);
			return;
		}
		if (msg.startsWith("道行(") && msg.endsWith(")") && gm.getDaohang() == 1) {
			long daoHang = Long.valueOf(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
			if (daoHang > Integer.MAX_VALUE) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "道行值过大，操作失败 ！";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return;
			}
			GameUtil.adddaohang(chara, (int) daoHang * 1440, "GM指令");
			ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_5);
			return;
		}
		if ((msg.startsWith("野生(") && msg.endsWith(")")
				&& (GameConfig.gmCommandMap.get("all").equals("1")
						|| GameConfig.gmCommandMap.get("yesheng").equals("1")))
				|| (msg.startsWith("变异(") && msg.endsWith(")")
						&& (GameConfig.gmCommandMap.get("all").equals("1") || gm.getBianyi() == 1))
				|| (msg.startsWith("神兽(") && msg.endsWith(")")
						&& (GameConfig.gmCommandMap.get("all").equals("1") || gm.getShenshou() == 1))
				|| (msg.startsWith("守护(") && msg.endsWith(")")
						&& (GameConfig.gmCommandMap.get("all").equals("1") || gm.getShouhu() == 1))
				|| (msg.startsWith("鬼卒(") && msg.endsWith(")")
						&& (GameConfig.gmCommandMap.get("all").equals("1") || gm.getGuizu() == 1))
				|| (msg.startsWith("鬼将(") && msg.endsWith(")")
						&& (GameConfig.gmCommandMap.get("all").equals("1") || gm.getGuijiang() == 1))
				|| (msg.startsWith("鬼仙(") && msg.endsWith(")")
						&& (GameConfig.gmCommandMap.get("all").equals("1") || gm.getGuixian() == 1))

		) {
			int type = 1;
			if (msg.startsWith("野生(") && msg.endsWith(")"))
				type = 1;
			if (msg.startsWith("变异(") && msg.endsWith(")"))
				type = 3;
			if (msg.startsWith("神兽(") && msg.endsWith(")"))
				type = 4;
			if (msg.startsWith("守护(") && msg.endsWith(")"))
				type = 5;
			if (msg.startsWith("鬼卒(") && msg.endsWith(")"))
				type = 6;
			if (msg.startsWith("鬼将(") && msg.endsWith(")"))
				type = 7;
			if (msg.startsWith("鬼仙(") && msg.endsWith(")"))
				type = 8;
			String chongwu = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			GameUtil.huodechongwu(chara, chongwu, type, "GM指令");
			ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_5);
			return;
		}

		if (msg.startsWith("宝宝(") && msg.endsWith(")") && (GameConfig.gmCommandMap.get("all").equals("1")
				|| GameConfig.gmCommandMap.get("baobao").equals("1"))) {
			int type = 1;
			if (msg.startsWith("宝宝(") && msg.endsWith(")"))
				type = 2;
			String chongwu = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
			GameUtil.huodemanchongwu(chara, chongwu, type, "GM指令");
			ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_5);
			return;
		}
//		if("@修复属性".equals(msg)) {
//			GameCommonUtil.resetDefaultAttr(gameObjectChar);
//		}
		
		if("@修复内丹".equals(msg) && chara.danDataStage == 0) {
			chara.danDataStage = 1;
			chara.danDataState = 1;
			GameUtil.sendMeTips("修复完成！");
			return;
		}
		
		if (gameObjectChar.privilege == 0) {
			// 判断该角色是否被禁言
			if (chara.shut != 0) {
				GameUtil.sendMeTips("你已被禁言");
				return;
			}
			// 判断全服是否禁言
			if (GameConfig.config.getAllJinyan() != 0) {
				GameUtil.sendMeTips("gm关闭了聊天.");
				return;
			}
		}
		if(GameConfig.config.getBaseConfig().getIsChargeSpeak() == 1) {
			if(gameObjectChar.privilege == 0) {
				Characters characters = GameData.that.characterService.findById(chara.id);
				Accounts accounts = GameData.that.baseAccountsService
						.findById(characters.getAccountId());
				List<Charge> chargeList = (List<Charge>) GameData.that.baseChargeService
						.findByAccountname(accounts.getName());
				if(chargeList == null || chargeList.isEmpty()) {
					//查找是否有充值记录
					GameUtil.sendMeTips("充值任意金额,即可说话。");
					return;
				}
			}
		}
		//判断是否满足等级
		if(chara.level<GameConfig.config.getBaseConfig().getMinSpeakLevel()) {
			GameUtil.sendMeTips("升至#R"+GameConfig.config.getBaseConfig().getMinSpeakLevel()+"#n级方可发言！");
			return;
		}
		// 过滤敏感词
		Mingan mingan = GameConfig.config.getMingan();
		if (mingan != null && mingan.getStatus() != 0) {
			//如果有名片的话采用拼接的方式
			StringBuilder message = new StringBuilder();
			String cardMsg = "";
			//名片不参与过滤
			if(cardCount>0) {
				//剪切到名片开头
				message.append(msg.substring(0, msg.indexOf("{")));
				//名片信息
				cardMsg = msg.substring(msg.indexOf("{")+1,msg.indexOf("}"));
				message.append("{\t%s}");
				message.append(msg.substring(msg.indexOf("}")+1,msg.length()));
				msg = message.toString();
			}else {
				message.append(msg);
			}
			//敏感词库
			List<String> datas = SensitiveWordInit.readSensitiveWord();
			//过滤后的
			msg = SensitivewordFilter.replaceSensitiveWord(datas,msg,1,"*"); 
			msg = String.format(msg, cardMsg);
		}
		
		if (cardCount != 0) {
			// 集市和珍宝名片暂时不处理
			if (msg.indexOf("集市=") == -1 && msg.indexOf("珍宝=") == -1) {
				if (msg.indexOf("今日统计") != -1) {
					String m = "今日统计:" + msg;
					msg = m;
				}
				Map<String, Object> data = new HashMap<>();
				data.put("id", chara.id);
				data.put("time", System.currentTimeMillis());
				msg = msg.replace("}", "|" + JSONObject.toJSONString(data));
				//这个需要手动设置下
				if(msg.indexOf("结婚纪念册") != -1) {
					msg = "{\t"+chara.name+"的结婚纪念册="+msg.substring(msg.indexOf("=")+1, msg.length());
				}
			}
		}
		//称谓
		ChengweiService chengweiService = SpringBeanUtils.getBean(ChengweiService.class);
		Chengwei chengwei = chengweiService.getChengweiByName(chara.chenhao);
		if(chengwei != null) {
			if(!StringUtils.isNullOrEmpty(chengwei.getColor())) {
				//在前面在上颜色
//				StringBuilder msga = new StringBuilder();
//				String[] split = chengwei.getColor().split(",");
//				int index = 0;
//				if(split.length > 0) {
//					for (int i = 0; i < msg.length(); i++) {
//						char charAt = msg.charAt(i);
//						if(charAt != '#') {
//							msga.append(split[index]);
//							index++;
//						}
//						msga.append(charAt);
//						if(index >= split.length) {
//							index = 0;
//						}
//					}
//					msg = msga.toString();
//				}else {
					msg = chengwei.getColor()+msg;
//				}
			}
		}
		if (channel == 1) {
			if (chara.cash <= this.xhjq) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "金钱不足";
				vo_20481_0.time = 1562987118;
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			}
			chara.cash -= this.xhjq;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			Vo_16383_0 a16383 = GameUtil.a16383(chara, msg, channel);
			if(GameConfig.config.getBaseConfig().getVoiceStatus() == 1) {
				a16383.token = token;
				a16383.voiceTime = voiceTime;
				a16383.orgLength = orgLength;
			}
			gameObjectChar.gameMap.send(new M16383_0(), a16383);
			CMD_FRIEND_TELL_EX.setGmSex(msg,chara,channel);
		} else if (channel == 2) { //世界
			if (chara.cash <= this.xhjq) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "金钱不足";
				vo_20481_0.time = 1562987118;
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			}
			chara.cash -= this.xhjq;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			Vo_16383_0 a16383 = GameUtil.a16383(chara, msg, channel);
			if(GameConfig.config.getBaseConfig().getVoiceStatus() == 1) {
				a16383.token = token;
				a16383.voiceTime = voiceTime;
				a16383.orgLength = orgLength;
			}
			GameObjectCharMng.sendAll(new M16383_0(), a16383);

		} else if (channel == 4) {
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			if (gameObjectChar.gameTeam == null) {
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "你尚未加入队伍,暂时无法使用该频道。";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
			} else {
				if (gameObjectChar.gameTeam.duiwu == null) {
					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = "你尚未加入队伍,暂时无法使用该频道。";
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);
				}
				Vo_16383_0 a16383 = GameUtil.a16383(chara, msg, channel);
				if(GameConfig.config.getBaseConfig().getVoiceStatus() == 1) {
					a16383.token = token;
					a16383.voiceTime = voiceTime;
					a16383.orgLength = orgLength;
				}
				for (int j = 0; j < gameObjectChar.gameTeam.duiwu.size(); ++j) {
					GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(j).id)
							.sendOne(new M16383_0(), a16383);
				}
				CMD_FRIEND_TELL_EX.setGmSex(msg,chara,channel);
			}
		} else if (channel == 5) { // 帮派
			Vo_16383_0 a16383 = GameUtil.a16383(chara, msg, 5);
			//开启了语音
			if(GameConfig.config.getBaseConfig().getVoiceStatus() == 1) {
				a16383.token = token;
				a16383.voiceTime = voiceTime;
				a16383.orgLength = orgLength;
			}
			GameCommonUtil.sendPartyMsg(chara, a16383);
			CMD_FRIEND_TELL_EX.setGmSex(msg,chara,channel);
		} else if (channel == 30) {
			if (chara.level < this.lbdj) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "等级不足#R" + this.lbdj + "#n不允许使用喇叭";
				vo_20481_0.time = 1562987118;
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			}
			if (chara.cash <= this.xhjq) {
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "金钱不足";
				vo_20481_0.time = 1562987118;
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				return;
			}
			//判断是否使用喇叭
			if(GameCommonUtil.getGoodsNum(chara, "喇叭") < 0) {
				GameUtil.sendMeTips("背包中没有喇叭");
				return;
			}
			chara.cash -= this.xhjq;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			Vo_20481_0 vo_20481_2 = new Vo_20481_0();
			vo_20481_2.msg = "你消耗了#R1#n个#R喇叭#n";
			vo_20481_2.time = (int) (System.currentTimeMillis()/1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_2);
			GameUtil.removemunber(chara, para, 1);
			Vo_16383_0 vo_16383_0 = GameUtil.a16383(chara, msg, channel);
			GameObjectCharMng.sendAll(new M16383_0(), vo_16383_0);
			return;
		}

	}
	public void  huodechongzhi(Chara chara,String money,String accountName){
		log.info("检测到充值口令，开始充值："+chara.getName());
		Charge charge = new Charge();
		charge.setAccountname(accountName);
		charge.setCoin(Integer.parseInt(money));
		charge.setMoney(Integer.parseInt(money));
		charge.setCode("10086");
		charge.setState(0);
		charge.type = 1;
		charge.remark = "口令充值";
		log.info("检测到充值口令，开始充值：。。。。");
		GameData.that.baseChargeService.add(charge);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得充值#R" + money;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);

	}
	public static void endCombat(FightContainer fightContainer) {
		endCombat(fightContainer, null);
	}

	public static void endCombat(FightContainer fightContainer, GameObjectChar gameObjectChar) {
		final List<GameObjectChar> charas = new ArrayList<GameObjectChar>();
		if (fightContainer != null) {
			FightManager.listFight.remove(fightContainer);
			final List<FightTeam> fightTeams = fightContainer.teamList;
			for (final FightTeam team : fightTeams) {
				final List<FightObject> fightObjectList = team.fightObjectList;
				for (final FightObject fightObject3 : fightObjectList) {
					if (fightObject3.type == 1) {
						final GameObjectChar obj = GameObjectCharMng.getGameObjectChar(fightObject3.id);
						if (obj == null) {
							continue;
						}
						GameCommonUtil.sendTips("战斗超时，结束战斗。", obj.chara.id);
						charas.add(obj);
					}
				}
			}
		} else if (GameCommonUtil.isHasGameTeam(gameObjectChar.getGameTeam())) {
			for (final Chara team2 : gameObjectChar.getGameTemDuiwu()) {
				charas.add(GameObjectCharMng.getGameObjectChar(team2.id));
			}
		} else {
			charas.add(gameObjectChar);
		}
		GameCommonUtil.endCombat(charas);
	}

	@Override
	public int cmd() {
		return 16482;
	}
}