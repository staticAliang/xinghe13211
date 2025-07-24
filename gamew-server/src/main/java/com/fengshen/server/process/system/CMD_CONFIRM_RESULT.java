package com.fengshen.server.process.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.domain.FixedTeam;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.domain.PartySkill;
import com.fengshen.db.domain.Renwu;
import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.LuckDrawUtils;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_61591_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.chat.Vo_DECORATION_LIST.Items;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.vo.user.Vo_CL_CARD_INFO;
import com.fengshen.server.data.vo.user.Vo_OTHER_LOGIN;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.M61591_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.data.write.tongtianta.MSG_TONGTIANTA_INFO;
import com.fengshen.server.data.write.user.MSG_CHANGE_POLAR_SUCC;
import com.fengshen.server.data.write.user.MSG_CHAR_UPGRADE_COAGULATION;
import com.fengshen.server.data.write.user.MSG_CL_CARD_INFO;
import com.fengshen.server.data.write.user.MSG_OTHER_LOGIN;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.zuolao.MSG_RELEASE_SUCC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaBaseInfo;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SkillCost;
import com.fengshen.server.domain.config.ForcePkConfig;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightRequest;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameTeamUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.game.MarryUtil;
import com.fengshen.server.job.SaveCharaTimes;
import com.fengshen.server.process.CommonCmd;
import com.fengshen.server.process.pet.CMD_SELECT_CURRENT_MOUNT;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 
 * 自定义确认框
 *
 */
@Service
@Slf4j
public class CMD_CONFIRM_RESULT implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		// 0取消 1确定
		String select = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		//角色强制顶号
		if("topLogin".equals(gameObjectChar.currentConfirmItem)) {
			if(!"0".equals(select)) {
				try {
					//开始等待
					GameUtil.openDlg("WaitDlg");
					if(gameObjectChar.confirmData != null && gameObjectChar.confirmData instanceof Map) {
						@SuppressWarnings("unchecked")
						Map<String,Object> confirmData = (Map<String, Object>) gameObjectChar.confirmData;
						GameObjectChar oldSession = (GameObjectChar) confirmData.get("gameObjectChara");
						if(oldSession != null) {
							//先存档
							SaveCharaTimes.saveCharaInfo(oldSession);
							String char_name = (String) confirmData.get("char_name");
							//被顶号的话就在查询一次.
							Characters characters = GameData.that.characterService.login(gameObjectChar.accountid, char_name);
							if(characters == null) {
								ctx.close();
								log.error("顶号请求为空...");
								return;
							}
							Vo_OTHER_LOGIN login = new Vo_OTHER_LOGIN();
							login.setCode(0);
							login.setResult(2);
							login.setMsg("你的账号已在其他设备登录,如非本人操作请尽快修改密码！");
							oldSession.sendOne(new MSG_OTHER_LOGIN(), login);
							oldSession.ctx.close();
							gameObjectChar.init(characters);
							//开始登录
							gameObjectChar.gameMap = oldSession.gameMap;
							gameObjectChar.tickCount = new AtomicInteger(0);
							GameCommonUtil.loadExistedChar(characters, gameObjectChar, char_name);
						}
					}
				} finally {
					GameUtil.closeDlg("WaitDlg");
					gameObjectChar.currentConfirmItem = "";
					gameObjectChar.confirmData = null;
				}
				return;
			}else {
			}
		}
		
		if(chara == null) {
			return;
		}
		//角色剧情
		if("主线—拜入师门s1".equals(chara.current_task)) {
			if("0".equals(select)) {
				return;
			}
			chara.current_task = "主线—拜入师门s2";
			int icon = GameCommonUtil.shimen_tongzi_icon[chara.polar-1];
			String[] skill = new String[] {"金光乍现","摘叶飞花","滴水穿石","举火焚天","落土飞岩"};
			//高物伤//高法伤
			if("1".equals(select)) { 
				Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既然你选择了物伤攻击偏向，那么接下来你按照我的指点学习#R力破千均#n。",
						"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
				GameObjectChar.send(new M45056_0(), vo_45056_2);
				//创建任务
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("主线—拜入师门s2");
				renwu.setTaskPrompt("找#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|M=【主线】学习道法#P将#R力破千钧#n提升至16级");
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
			}
			else if("2".equals(select)) { 
				Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既然你选择了法术攻击偏向，那么接下来你按照我的指点学习#R"+skill[chara.polar-1]+"#n。",
						"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
				GameObjectChar.send(new M45056_0(), vo_45056_2);
				//创建任务
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("主线—拜入师门s2");
				renwu.setTaskPrompt("找#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|M=【主线】学习道法#P将#R"+skill[chara.polar-1]+"#n提升至16级");
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
			}
			else if("3".equals(select)){
				//自定义
				Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既然你有自己的打算，那为师也不便强求，那么接下来你需要自行将#R力破千钧#n或#R"+skill[chara.polar-1]+"#n学习至#R16级#n来向为师展示你的能力吧。",
						"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
				GameObjectChar.send(new M45056_0(), vo_45056_2);
			}
			chara.taskMap.get("主线—拜入师门").task_state = select;
			return;
		}
		String para = chara.currentConfirmItem;
		if (!"0".equals(select)) {
			if ("switchFly".equals(para)) {
				// 人物选择元婴或者血婴
				chara.upgrade_level = 1;
				chara.upgrade_type = Integer.valueOf(select);
				chara.upgrade_exp_to_next_level = (int) (517 * 0.8);
				chara.upgrade_exp = 0;
				chara.upgrade_max_polar_extra = 60;
				chara.upgrade_state = 0;
				chara.isFeisheng = 1;
				// 创建一个基本信息
				chara.charaYuanyingInfo = new CharaBaseInfo();
				GameUtil.sendUpdate(chara);
				GameObjectChar.send(new MSG_CHAR_UPGRADE_COAGULATION(), chara.upgrade_type);
				GameUtilRenWu.removeTask("飞升—引路人", chara);
			}  else if ("reSwitchFly".equals(para)) {
				if(chara.upgrade_state != 0) {
					GameUtil.sendMeTips("请切换真身操作！");
					return;
				}
				// 重新选择元血婴
				chara.upgrade_type = Integer.valueOf(select);
				GameUtil.sendUpdate(chara);
				GameObjectChar.send(new MSG_CHAR_UPGRADE_COAGULATION(), Integer.valueOf(select));
			} else if ("openSwitchXianMo".equals(para)) {
				// 飞升仙魔
				String type = "遁入魔道";
				if (chara.upgrade_type == 1) {
					type = "踏上仙途";
				}
				GameUtil.confirm(chara, org.apache.commons.lang3.StringUtils.join("是否消耗#R" , GameConfig.config.getBaseConfig().getFlyXianMo() , "#n积分" , type),
						"switchXianMo");
			} else if ("switchXianMo".equals(para)) {
				if(chara.upgrade_state != 0) {
					GameUtil.sendMeTips("请切换真身操作！");
					return;
				}
				if (chara.upgrade_level <119) {
					GameUtil.sendMeTips("条件不满足");
					return;
				}
				// 判断积分是否充足
				Integer flyXianMo = GameConfig.config.getBaseConfig().getFlyXianMo();
				if (chara.chargeScore < flyXianMo) {
					GameCommonUtil.dialogOk("抱歉积分不足。");
					chara.currentConfirmItem = "openSwitchXianMo";
					return;
				}
				// 飞升仙魔
				int type = 3;
				if (chara.upgrade_type == 2) {
					type = 4;
				}
				chara.upgrade_type = type;
				
				//初始化仙魔道点
				int i = chara.realLevel-111;
				if(i<8) {
					i = 8;
				}
				chara.upgrade_immortal = i;
				chara.upgrade_magic = i;
				chara.upgrade_total = i;
				GameUtil.sendUpdate(chara);
				GameObjectChar.send(new MSG_CHAR_UPGRADE_COAGULATION(), chara.upgrade_type);
				chara.currentConfirmItem = "openSwitchXianMo";
				// 扣除积分
				chara.chargeScore -= flyXianMo;
			GameUtilRenWu.refshPointTask(chara);

			} else if ("reSwitchXianMo".equals(para)) {
				if(chara.upgrade_state != 0) {
					GameUtil.sendMeTips("请切换真身操作！");
					return;
				}
				if (chara.upgrade_level <119 && chara.realLevel<119) {
					GameUtil.sendMeTips("条件不满足");
					return;
				}
				// 重新选择仙魔
				chara.upgrade_type = Integer.valueOf(select);
				GameUtil.sendUpdate(chara);
				GameObjectChar.send(new MSG_CHAR_UPGRADE_COAGULATION(), chara.upgrade_type);
				GameUtil.removemunber(chara, "天星石", 3);
			}else if ("restTtt".equals(para)) {
				chara.tongttcishu--;
				chara.tongtiantaTask = null;
				chara.commonTaskMap.remove("通天塔");
				GameUtilRenWu.removeTask("通天塔", chara);
				GameUtil.sendMeTips("已放弃#R通天塔#n任务");
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			} else if ("restTttTp".equals(para)) {
				chara.tongtiantaTask = null;
				chara.commonTaskMap.remove("通天塔");
				GameUtilRenWu.removeTask("通天塔", chara);
				GameUtil.sendMeTips("已放弃#R通天塔#n任务");
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			} else if ("removeParty".equals(para)) {
				// 解散帮派
				GamePartyUtil.removeParty(chara);
				chara.currentConfirmItem = "";
			} else if (para.startsWith("recoveryShangGuLingPai-")) {
				// 神兽牌子兑换积分
				String num = para.split("-")[1];
				int numInt = Integer.valueOf(num);
				if(GameCommonUtil.getGoodsNum(chara, "召唤令·上古神兽")< numInt) {
					GameUtil.sendMeTips("你想干什么呢？");
					return;
				}
				GameUtil.addchargeScore(gameObjectChar, numInt * GameConfig.jifenhuishou, "兑换大使",org.apache.commons.lang3.StringUtils.join("成功兑换了#R" , numInt * GameConfig.jifenhuishou ,"#n积分"));
				GameUtil.removemunber(chara, "召唤令·上古神兽", numInt);
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			} else if (para.startsWith("addCardSize")) {
				// 购买卡套空间
				String num = para.split("-")[1];
				int numInt = Integer.valueOf(num);
				if (chara.goldCoin < (numInt * 10)) {
					GameUtil.sendMeTips("元宝不足，购买失败。");
					return;
				}
				chara.goldCoin -= numInt * 10;
				chara.cardSize += numInt;
				GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你花费了#R" , numInt * 12 , "#n元宝购买了#R" , numInt , "#n卡套空间"));
				GameObjectChar.send(new MSG_CL_CARD_INFO(), new Vo_CL_CARD_INFO(chara.cardSize));
			} else if (para.startsWith("applyCard")) {
				// 确定使用变身卡
				String num = para.split("-")[1];
				int pos = Integer.valueOf(num);
				GameCommonUtil.applyCard(chara, pos);
			} else if ("tttGoRun".equals(para)) {
				// 通天塔突破阶段取消逃跑
				FightContainer fightContainer = FightManager.getFightContainer();
				if (fightContainer != null) {
					FightRequest fr = new FightRequest();
					fr.id = chara.id;
					fr.action = 7;
					fr.vid = 0;
					FightManager.addRequest(fightContainer, fr);
				}
			} else if (para.startsWith("PKGM")) {
				// 对gm发起pk
				String toUUID = para.split("_")[1];
				// PK发起者
				GameObjectChar pkChara = GameObjectCharMng.getGameObjectCharByUUid(toUUID);
				if (pkChara != null) {
					if (pkChara.chara.mapid != chara.mapid) {
						GameUtil.sendMeTips("不在同一个地图");
						return;
					} else if (pkChara.chara.isFight) {
						GameUtil.sendMeTips("对方正忙！");
						return;
					} else {
						FightManager.goFight(pkChara.chara, chara);
					}
				}
			} else if (para.startsWith("forcePkGm")) {
				// 要PK人的uuid
				String toUUID = para.split("_")[1];
				// 要PK人的信息
				GameObjectChar pkChara = GameObjectCharMng.getGameObjectCharByUUid(toUUID);
				if (pkChara != null) {
					if (pkChara.chara.mapid != chara.mapid) {
						GameUtil.sendMeTips("不在同一个地图");
						return;
					} else if (pkChara.chara.isFight) {
						GameUtil.sendMeTips("对方正忙！");
						return;
					} else {
						FightManager.goFight(chara, pkChara.chara);
					}
				}
			} else if ("saodangxiufu".equals(para)) {
				if (chara.chargeScore < 10) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "积分不足，无法扫荡！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return;
				}
				chara.chargeScore -= 10;
			GameUtilRenWu.refshPointTask(chara);

				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65527_0(), listVo_65527_0);
				GameUtil.addFabaoQinmi(chara, 40000, "修法");
				GameUtil.addFabaoDaofa(chara, 40000, "修法");
				chara.xiufacishu += 1;
				GameUtil.sendMeTips("扫荡修法任务，消耗了#R10#n积分");
			} else if (para.startsWith("forcePkChara")) {
				GameUtil.sendMeTips("等待完善！");
				return;
			} else if (para.startsWith("notGetTaskForcePkChara")) {
				// 如果当前自己已经在战斗了,无法发起强P
				if (gameObjectChar.chara.isFight) {
					return;
				}
				ForcePkConfig config = GameConfig.forcePkConfig;
				// 是否开启
				if (config.getEnableForcePk() == 0) {
					GameUtil.sendMeTips("gm关闭了强P系统");
					return;
				}
				if ("天墉城、监狱、轩辕庙、无名小镇、帮派总坛、证道殿、帮战地图、试道场、南天门、揽仙镇、东海渔村、五龙山、终南山、乾元山、凤凰山、骷髅山".contains(gameObjectChar.gameMap.name)) {
					GameUtil.sendMeTips("当前地图为安全区,无法发起PK");
					return;
				}
				// 判断当前是否为安全区
				if (config.getSecurityMap().contains(gameObjectChar.gameMap.name)) {
					GameUtil.sendMeTips("当前地图为安全区,无法发起PK");
					return;
				}
				// 最等级
				if (chara.level < config.getLowLevel()) {
					GameUtil.sendMeTips("道友你还年轻还是提升下自我,在来开启强制PK");
					return;
				}
				// 要PK人的uuid
				String toUUID = para.split("_")[1];
				// 要PK人的信息
				GameObjectChar pkChara = GameObjectCharMng.getGameObjectCharByUUid(toUUID);
				if (pkChara.chara.level < config.getLowLevel()) {
					GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("对方等级低于#R" , config.getLowLevel() , "#n级无法发起强制PK"));
					return;
				}
				if (pkChara != null) {
					if (pkChara.chara.mapid != chara.mapid) {
						GameUtil.sendMeTips("不在同一个地图");
						return;
					} else if (pkChara.chara.isFight) {
						GameUtil.sendMeTips("对方正忙！");
						return;
					} else {
						if (GameObjectCharMng.getGameObjectChar(pkChara.chara.id) != null
								&& GameObjectCharMng.getGameObjectChar(pkChara.chara.id).gameTeam != null
								&& gameObjectChar.gameTeam != null
								&& GameObjectCharMng.getGameObjectChar(pkChara.chara.id).gameTeam.duiwu != null
								&& gameObjectChar.gameTeam.duiwu != null
								// add tzhang 添加空指针判断逻辑
								&& GameObjectCharMng.getGameObjectChar(pkChara.chara.id).gameTeam.duiwu.size() > 0
								&& gameObjectChar.gameTeam.duiwu.size() > 0
								// add:e
								&& (GameObjectCharMng.getGameObjectChar(pkChara.chara.id).gameTeam.duiwu
										.get(0).id == gameObjectChar.gameTeam.duiwu.get(0).id)) {
							GameCommonUtil.dialogOk("你不能和自己的队员切磋！");
							return;
						}
						// 解析参数
						if (!StringUtils.isNullOrEmpty(config.getPkMoney())) {
							// 解析参数
							String pkInfo = config.getPkMoney();
							String[] pkInfoArr = pkInfo.split(":");
							if (pkInfoArr.length > 1) {
								String type = pkInfoArr[0];
								if ("积分".equals(type)) {
									if (chara.chargeScore < Integer.valueOf(pkInfoArr[1])) {
										GameUtil.sendMeTips("积分不足无法发起#R强制PK");
										return;
									}
									// 扣除积分
									chara.chargeScore -= Integer.valueOf(pkInfoArr[1]);
			GameUtilRenWu.refshPointTask(chara);

								} else if ("金元宝".equals(type)) {
									if (chara.goldCoin < Integer.valueOf(pkInfoArr[1])) {
										GameUtil.sendMeTips("金元宝不足无法发起#R强制PK");
										return;
									}
									chara.goldCoin -= Integer.valueOf(pkInfoArr[1]);
								} else if ("银元宝".equals(type)) {
									if (chara.silverCoin < Integer.valueOf(pkInfoArr[1])) {
										GameUtil.sendMeTips("银元宝不足无法发起#R强制PK");
										return;
									}
									chara.silverCoin -= Integer.valueOf(pkInfoArr[1]);
								}
								GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join(
										"你对#Y" , pkChara.chara.name , "#n发起了强制PK消耗了#R" , pkInfoArr[1] , "#n" , type));
							}
						}
						if (GameCommonUtil.isNotGameTeam(pkChara.gameTeam)) {
							for (Chara game : pkChara.gameTeam.duiwu) {
								GameObjectCharMng.getGameObjectChar(game.id).action = "passiveForcePk";
								GameCommonUtil.sendTips("请注意这是一场#RPK#n赛,死亡后会扣除#R积分!", game.id);
							}
						} else {
							GameCommonUtil.sendTips("请注意这是一场#RPK#n赛,死亡后会扣除#R积分!", pkChara.chara.id);
							pkChara.action = "passiveForcePk";
						}
						// 主动方
						if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							for (Chara game : gameObjectChar.gameTeam.duiwu) {
								GameObjectCharMng.getGameObjectChar(game.id).action = "activeForcePk";
							}
						} else {
							gameObjectChar.action = "activeForcePk";
						}
						FightManager.goFight(chara, pkChara.chara);
					}
				}
				return;
			} else if (para.startsWith("bpstjk")) {
				if (StringUtils.isNullOrEmpty(chara.getPartyName())) {
					GameUtil.sendMeTips("你还未加入帮派！");
					return;
				}
				// 帮派捐款
				String[] paras = para.split("\\|");
				int money = Integer.valueOf(paras[1]);
				if (chara.cash < money) {
					GameUtil.sendMeTips("金钱不足！");
					return;
				}
				chara.cash -= money;
				String moneyDes = GameCommonUtil.getMoneyDes(money);
				GameUtil.sendTips(org.apache.commons.lang3.StringUtils.join("你缴纳了", moneyDes , "文钱，" , "增加了#R" , (money / 10000) , "点#n帮派活力值！"));
				// 查询帮派
				Party party = GameData.that.partyService.findByPartyName(chara.getPartyName());
				party.setMoney(party.getMoney() + money);
				GameData.that.partyService.updateByPrimaryKeySelective(party);
				// 成员
				PartyMember partyMemberOnePartyName = GameData.that.partyMemberService
						.getPartyMemberOnePartyName(chara.getName());
				partyMemberOnePartyName
						.setCurrWeekActive(partyMemberOnePartyName.getCurrWeekActive() + (money / 10000));
				GameData.that.partyMemberService.updateByPrimaryKeySelective(partyMemberOnePartyName);
				// 通知帮派信息
				// 获取玩家帮派所有在线成员
				Example example = new Example(PartyMember.class);
				example.createCriteria().andEqualTo("partyId",
						GameCore.partyMap.get(chara.getPartyName()).getPartyId());
				List<PartyMember> partyMemerbs = GameData.that.partyMemberService.selectByExample(example);
				Vo_MESSAGE msgVo = GameCommonUtil.getPartyNpc(5,org.apache.commons.lang3.StringUtils.join(
						"#Y" + chara.name + "#n为本帮贡献了" + moneyDes + "文钱，增加了#R" + money / 10000 + "点#n帮派活力值！"));
				for (PartyMember p : partyMemerbs) {
					GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(p.getCharaGid());
					if (gameObjectCharByUUid != null) {
						gameObjectCharByUUid.sendOne(new MSG_MESSAGE(), msgVo);
					}
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			} else if (para.startsWith("upPetPartySkillLevel")) {
				// 宠物技能升级, id, skill_no up_level costMoney costPartyContrib
				String[] paras = para.split("_");
				// 宠物id
				int id = Integer.valueOf(paras[1]);
				// 技能序号
				int skill_no = Integer.valueOf(paras[2]);
				// 升级等级
				int up_level = Integer.valueOf(paras[3]);
				// 消耗金钱
				int costMoney = Integer.valueOf(paras[4]);
				// 消耗帮贡
				int costPartyContrib = Integer.valueOf(paras[5]);
				for (Petbeibao pet : chara.pets) {
					if (pet.id == id) {
						String name = "如意圈";
						JiNeng tianji = null;
						if (pet.tianji == null) {
							pet.tianji = new ArrayList<>();
						}
						for (JiNeng jn : pet.tianji) {
							if (jn.getSkill_no() == skill_no) {
								// 找到该技能
								tianji = jn;
								break;
							}
						}
						if (tianji == null) {
							GameUtil.sendMeTips("宠物还尚未学习该技能！");
							return;
						} else if (StringUtils.isNullOrEmpty(chara.getPartyName())) {
							GameUtil.sendMeTips("你还未加入帮派！");
							return;
						}
						if (skill_no == 259) {
							name = "乾坤罩";
						} else if (skill_no == 260) {
							name = "神龙罩";
						}
						Example example = new Example(PartySkill.class);
						example.createCriteria()
								.andEqualTo("partyId", GameCore.partyMap.get(chara.getPartyName()).getPartyId())
								.andEqualTo("no", skill_no);
						PartySkill partySkill = GameData.that.partySkill.selectOneByExample(example);
						if (partySkill == null) {
							GameUtil.sendMeTips("帮派技能等级不足,无法升级！");
							return;
						} else if (tianji.skill_level + 1 > partySkill.getLevel()) {
							GameUtil.sendMeTips("帮派技能等级不足,无法升级！");
							return;
						}
						// 判断金钱和帮贡是否充足
						if (chara.contrib < costPartyContrib && gameObjectChar.privilege == 0) {
							GameUtil.sendMeTips("帮贡不足无法升级！");
							return;
						} else if (chara.cash < costMoney && gameObjectChar.privilege == 0) {
							GameUtil.sendMeTips("金钱不足无法升级！");
							return;
						}
						PetShuXing petShuXing = pet.petShuXing.get(0);
						for (JiNeng jn : pet.tianji) {
							if (jn.getSkill_no() == skill_no) {
								// 找到该技能
								tianji = jn;
								break;
							}
						}
						// 在原基础上进行升级
						JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(skill_no);
						tianji.skill_attrib1 = Integer.parseInt((String) jsonObject.get("skill_attrib"));
						tianji.skill_level += up_level;
						tianji.range = PetAndHelpSkillUtils.skillNummax(skill_no, tianji.skill_level);
						int[] ints2 = PetAndHelpSkillUtils.skillNum(jsonObject, tianji.skill_level);
						tianji.skillRound = ints2[1];
						int[] blueAndPointsLan2 = PetAndHelpSkillUtils.getBlueAndPointsLan(skill_no,
								tianji.skill_level);
						tianji.skill_mana_cost = blueAndPointsLan2[0];
						// 先删除原来的
						tianji.skillCost.clear();
						//下一级需要消耗的
						int[] nextPetPartySkillCost = GameCommonUtil.getPetPartySkillCost(tianji.skill_level);
						tianji.skillCost.add(new SkillCost("cash", nextPetPartySkillCost[0]));
						tianji.skillCost.add(new SkillCost("party/contrib", nextPetPartySkillCost[1]));
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join(name , "技能等级提升到了#R" , tianji.skill_level , "#n级！"));
						// 刷新技能信息
						boolean isfagong = petShuXing.rank > petShuXing.pet_mag_shape;
						GameUtil.dujineng(1, petShuXing.metal, petShuXing.skill, isfagong, pet.id, chara, pet);
						if(gameObjectChar.privilege == 0) {
							// 扣除帮贡和金钱
							chara.cash -= costMoney;
							chara.contrib -= costPartyContrib;
							GameUtil.sendUpdate(chara);
						}
						break;
					}
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			} else if("request_team_leader".equals(para)){
				//更换队长
				GameTeamUtil.changeTeamLeader(gameObjectChar, gameObjectChar.upduizhangid);
			}else if("shopCustomFasion".equals(para)) {
				@SuppressWarnings("unchecked")
				Map<String,Object> confirmData = (Map<String, Object>) gameObjectChar.confirmData;
				@SuppressWarnings("unchecked")
				List<FasionCustomInfo> fcs = (List<FasionCustomInfo>)confirmData.get("data");
				int sum = (int) confirmData.get("sum");
				String[] names = (String[]) confirmData.get("names");
				for(FasionCustomInfo fc:fcs) {
					Goods goods = new Goods();
					goods.goodsInfo.owner_id = 1;
					goods.goodsInfo.value = 2097924;
					goods.goodsInfo.quality = "金色";
					goods.goodsInfo.alias = fc.getName();
					goods.goodsInfo.amount = 18;
					goods.pos = fc.getPosition();
					goods.goodsInfo.food_num = fc.getGift();
					goods.goodsInfo.master = chara.sex;
					goods.goodsInfo.recognize_recognized = 0;
					goods.goodsInfo.type = fc.getIcon();
					goods.goodsInfo.total_score = 25;
					goods.goodsInfo.damage_sel_rate = 1842075;
					goods.goodsInfo.str = fc.getName();
					goods.goodsInfo.metal = chara.polar;
					goods.goodsInfo.durability = 8;
					goods.goodsInfo.rebuild_level = 500;
					goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
					chara.customShizhuang.add(goods);
				}
				chara.chargeScore -= sum;
			GameUtilRenWu.refshPointTask(chara);
				
				//穿戴自定义时装
				GameCommonUtil.getFasionCustomEquipEx(chara, names);
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.store_type = "custom_store";
				vo_61677_0.npcID = 0;
				vo_61677_0.list = chara.customShizhuang;
				vo_61677_0.count = chara.customShizhuang.size();
				GameObjectChar.send(new M61677_0(), vo_61677_0);
				
				Vo_41505_0 vo_41505_0 = new Vo_41505_0();
				vo_41505_0.type = "equip_fasion";
				GameObjectChar.send(new M41505_0(), vo_41505_0);
				GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你本次换装花费了#R" , sum , "#n点积分！"));
				GameUtil.closeDlg("CustomDressShowDlg");
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("useChatHead".equals(para)) {
				if(gameObjectChar.confirmData != null) {
					Goods goods = (Goods) gameObjectChar.confirmData;
					for(Items item:chara.chatHeads) {
						if(goods.goodsInfo.str.equals(item.getName())) {
							GameUtil.sendMeTips("请勿重复使用！");
							return;
						}
					}
					//使用聊天头像框
					int time = (int) (System.currentTimeMillis()/1000L)+10000000;
					int getTime = (int) (System.currentTimeMillis()/1000L);
					chara.chatHeads.add(new Items(goods.goodsInfo.str, time, getTime));
					GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你已成功使用了#R",goods.goodsInfo.str,"#n，请到聊天装饰中查看！"));
					//刷新聊天装饰
					GameCommonUtil.refreshChatStyle(gameObjectChar,1);
					GameUtil.removemunber(chara, goods, 1);
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("useChatFloor".equals(para)) {
				if(gameObjectChar.confirmData != null) {
					Goods goods = (Goods) gameObjectChar.confirmData;
					for(Items item:chara.chatFloors) {
						if(goods.goodsInfo.str.equals(item.getName())) {
							GameUtil.sendMeTips("请勿重复使用！");
							return;
						}
					}
					//使用聊天头像框
					int time = (int) (System.currentTimeMillis()/1000L)+10000000;
					int getTime = (int) (System.currentTimeMillis()/1000L);
					chara.chatFloors.add(new Items(goods.goodsInfo.str, time, getTime));
					GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你已成功使用了#R",goods.goodsInfo.str,"#n，请到聊天装饰中查看！"));
					//刷新聊天装饰
					GameCommonUtil.refreshChatStyle(gameObjectChar,2);
					GameUtil.removemunber(chara, goods, 1);
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("BUY_WEDDING_LIST".equals(para)) {
				//确定礼单
				if(gameObjectChar.confirmData != null) {
					@SuppressWarnings("unchecked")
					Map<String,Object> confirmData = (Map<String, Object>) gameObjectChar.confirmData;
					String type = (String) confirmData.get("type");
					String weddinglist = (String) confirmData.get("weddinglist");
					int sum = (int) confirmData.get("sum");
					String unit = "个";
					if("积分".equals(type)) {
						if(chara.chargeScore<sum) {
							GameUtil.sendMeTips("积分不足！");
							return;
						}
						chara.chargeScore-=sum;
			GameUtilRenWu.refshPointTask(chara);

						unit = "点";
					}else if("银元宝".equals(type)) {
						if(chara.silverCoin<sum) {
							GameUtil.sendMeTips("银元宝不足！");
							return;
						}
						chara.silverCoin-=sum;
					}else {
						//默认为金元宝
						if(chara.goldCoin<sum) {
							GameUtil.sendMeTips("金元宝不足！");
							return;
						}
						chara.goldCoin-=sum;
					}
					GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你花费了#R",sum,"#n",unit,type,"来支付此次的费用。"));
					//给龙凤呈祥礼服
					GameObjectChar womenGameObjectChar = GameObjectCharMng.getGameObjectChar(chara.marriageMarryId);
					if(womenGameObjectChar != null) {
						MarryUtil.getLongFengChengXiang(gameObjectChar);
						MarryUtil.getLongFengChengXiang(womenGameObjectChar);
						if(chara.marriageMarryId == 0) {
							//获取纪念册
							GameUtil.huodedaoju(gameObjectChar.chara, "结婚纪念册", 1);
							GameUtil.huodedaoju(womenGameObjectChar.chara, "结婚纪念册", 1);
							GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你获得了与#Y",gameObjectChar.chara.name,"#n的结婚纪念册"));
							GameCommonUtil.sendTips(org.apache.commons.lang3.StringUtils.join("你获得了与#Y",womenGameObjectChar.chara.name,"#n的结婚纪念册"), womenGameObjectChar.chara.id);
						}
					}
					//创建预定任务
					Vo_61553_0 vo_61553_0 = new Vo_61553_0();
					vo_61553_0.count = 1;
					vo_61553_0.task_type = "预定婚礼";
					vo_61553_0.task_desc = "前往#P月老#P出预定婚礼时间";
					vo_61553_0.task_prompt = "去#P月老#P处预定婚礼时间";
					vo_61553_0.refresh = 1;
					vo_61553_0.task_end_time = 1567909190;
					vo_61553_0.attrib = 0;
					vo_61553_0.reward = "";
					vo_61553_0.show_name = "预定婚礼时间";
					vo_61553_0.task_extra_para = gameObjectChar.chara.uuid;
					vo_61553_0.task_state = weddinglist;
					vo_61553_0.currentTask = "";
					GameUtilRenWu.createTask(vo_61553_0, gameObjectChar.chara);
				}
			}else if("delTiQi".equals(para)) {
				//放弃提亲任务
				GameUtilRenWu.removeTask("提亲", chara);
				GameUtil.sendMeTips("你已放弃#R提亲#n任务。");
			}else if("leaveShiDaoMap".equals(para)) {
				//设置试道状态为false
				if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
					//移除离开人员
					Iterator<List<Chara>> iterator = GameShiDao.getShiDaoSession(chara.level).iterator();
					for(Chara team:gameObjectChar.gameTeam.duiwu) {
						GameObjectChar teamGameObjetcTeam = GameObjectCharMng.getGameObjectChar(team.id);
						team.x = 132;
						team.y = 51;
						GameLine.getGameMapname(chara.line, "天墉城").join(teamGameObjetcTeam);
						teamGameObjetcTeam.shiDaoFlag.set(false);
						while(iterator.hasNext()) {
							List<Chara> next = iterator.next();
							if(next.get(0).uuid.equals(team.uuid)) {
								iterator.remove();
								break;
							}
						}
					}
					//移除这个队伍的信息
					GameShiDao.shidaoMapChara.remove(gameObjectChar.gameTeam.duiwu.get(0).uuid);
				}else if(gameObjectChar.privilege != 0) {
					chara.x = 132;
					chara.y = 51;
					GameLine.getGameMapname(chara.line, "天墉城").join(gameObjectChar);
					gameObjectChar.shiDaoFlag.set(false);
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("useFireworks".equals(para)) {
				//使用烟花
				Goods goods = (Goods) gameObjectChar.confirmData;
				GameActiveUtil.useFireworks(gameObjectChar, goods, goods.goodsInfo.str);
				//关闭背包页面
				GameUtil.closeDlg("BagDlg");
				//使用烟花
				gameObjectChar.flag = "fromUseFireworks";
			}else if("NOTIFY_TTT_JISU_FEISHENG".equals(para) && gameObjectChar.confirmData != null) {
				// 1元宝 2金钱
				int feishengType = (int) gameObjectChar.confirmData;
				String feishengTypeName = "";
				int jumpCount = chara.tongtiantaTask.getFeishengNumber() - 1;
				//判断当前层数是否不足
				if(chara.tongtiantaTask.getChallengeCount()>0){
					//突破阶段
					if(chara.tongtiantaTask.getCurLayer()+jumpCount>chara.tongtiantaTask.getTopLayer()) {
						GameUtil.sendMeTips("飞升层数超过最高上限！");
						return;
					}
				}else if(chara.tongtiantaTask.getCurLayer()+jumpCount>chara.level){
					//修炼阶段
					GameUtil.sendMeTips("当前层数不足#R"+jumpCount+"#n层！");
					return;
				}else if(chara.tongtiantaTask.getCurType() == 1) {
					GameUtil.sendMeTips("你尚未通过本层的挑战，无法进阶至更高的塔层。");
					return;
				}
				if (feishengType == 1) {
					if(jumpCount>10) {
						//元宝最多飞升5层
						GameUtil.sendMeTips("元宝最多一次性飞升10层！");
						return;
					}
					// 优先扣除银元宝.
					if (chara.silverCoin > 0) {
						chara.silverCoin -= chara.tongtiantaTask.getFeishengMoney();
					} else {
						// 金元宝
						if (chara.goldCoin <= 0) {
							GameUtil.sendMeTips("元宝不足..");
							return;
						}
					}
					feishengTypeName = "元宝";
				} else {
					if (chara.cash <= 0) {
						GameUtil.sendMeTips("金钱不足,无法飞升");
						return;
					}
					if(jumpCount>5) {
						//金钱最多飞升5层
						GameUtil.sendMeTips("金钱最多一次性飞升5层！");
						return;
					}
					// 扣除钱
					chara.cash -= chara.tongtiantaTask.getFeishengMoney();
					feishengTypeName = "文钱";
				}
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
				// 刷新任务信息，这里-1的目的是为了，领取任务的时候.不在重复计算.
				chara.tongtiantaTask
						.setCurLayer((chara.tongtiantaTask.getFeishengNumber() - 1) + chara.tongtiantaTask.getCurLayer());
				GameObjectChar.send(new MSG_TONGTIANTA_INFO(), chara.tongtiantaTask);
				Object showText = chara.tongtiantaTask.getFeishengMoney();
				if(feishengTypeName.equals("文钱")) {
					showText = GameCommonUtil.getMoneyDes((int)showText);
				}
				GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你消耗了#R" , showText , "#n" ,feishengTypeName , "，飞升至#R"
						, (chara.tongtiantaTask.getCurLayer() + 1) , "#n层"));
				// 只有当突破的时候
				if (chara.tongtiantaTask.getChallengeCount() > 0) {
					chara.tongtiantaTask.setHasNotCompletedSmfj(1);
				}
				chara.tongtiantaTask.setFeishengMoney(0);
				// 开始分配任务
				GameActiveUtil.tongtiantaGoNextLayer();
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("qzlh".equals(para)) {
				//强制离婚
				if(chara.marriageMarryId == 0) {
					GameUtil.sendMeTips("你还未婚呢？");
					return;
				}
				MarryUtil.lihun(chara, true);
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("lh".equals(para)) {
				if(chara.marriageMarryId == 0) {
					GameUtil.sendMeTips("你还未婚呢？");
					return;
				}
				else if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
						&& gameObjectChar.gameTeam.duiwu.size() != 2) {
					GameUtil.sendMeTips("离婚需要双方在场！？");
					return;
				} else if (gameObjectChar.gameTeam.duiwu.size() != 2
						|| gameObjectChar.gameTeam.duiwu.get(1).marriageMarryId != chara.id) {
					GameUtil.sendMeTips("请带上你的另一半");
					return;
				}
				MarryUtil.lihun(chara, false);
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("zuolao_release".equals(para)) {
				Object[] object = (Object[]) gameObjectChar.confirmData;
				//保释人员
				GameObjectChar releseGameObjectChar = null;
				for(GameObjectChar all:GameObjectCharMng.getAll()) {
					if(all.chara.crimeTime>0 && all.chara.taskMap.get("坐牢") != null && all.chara.uuid.equals(object[0])) {
						releseGameObjectChar = all;
						break;
					}
				}
				if(releseGameObjectChar == null) {
					GameUtil.sendMeTips("没找到该人信息");
					return;
				}
				//判断积分是否足够
				if(chara.chargeScore < (int)object[1]) {
					GameUtil.sendMeTips("积分不足");
					return;
				}
				//扣除积分
				releseGameObjectChar.chara.chargeScore-=(int)object[1];
			GameUtilRenWu.refshPointTask(chara);

				//开始释放该人员
				releseGameObjectChar.chara.crimeTime = 0;
				//释放该犯人
				releseGameObjectChar.chara.isNameRed = 0;
				//删除任务
				GameUtilRenWu.removeTask("坐牢", releseGameObjectChar.chara);
				releseGameObjectChar.chara.x = 26;
				releseGameObjectChar.chara.y = 30;
				GameLine.getGameMap(chara.line, "监狱").join(releseGameObjectChar);
				GameCommonUtil.sendTips(org.apache.commons.lang3.StringUtils.join("你被#Y",chara.name,"#n保释了"), releseGameObjectChar);
				gameObjectChar.sendOne(new MSG_RELEASE_SUCC(), releseGameObjectChar.chara.uuid);
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("zuolao_plead".equals(para)) {
				//如果这人有任务在身上那就无法领取
				if(chara.taskMap.get("将功补过") != null) {
					GameUtil.sendMeTips("你身上已有任务，请去完成");
					return;
				}
				//坐牢求情
				String gid = (String) gameObjectChar.confirmData;
				//保释人员
				GameObjectChar releseGameObjectChar = null;
				for(GameObjectChar all:GameObjectCharMng.getAll()) {
					if(all.chara.crimeTime>0 && all.chara.taskMap.get("坐牢") != null && all.chara.uuid.equals(gid)) {
						releseGameObjectChar = all;
						break;
					}
				}
				if(releseGameObjectChar == null) {
					GameUtil.sendMeTips("没找到该人信息");
					return;
				}
				//生成怪物信息
				List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
						.findByType(4);
				RenwuMonster renwuMonster = renwuMonsters.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
				com.fengshen.db.domain.Map map = GameData.that.baseMapService
						.findOneByName(renwuMonster.getMapName());
				if (map == null) {
					return;
				}
				//创建任务
				Vo_61553_0 vo_61553_2 = new Vo_61553_0();
				vo_61553_2.count = 1;
				vo_61553_2.task_type = "将功补过";
				vo_61553_2.task_desc = "擒回#Y作恶的土匪#n(剩余:#RTIME_LEFT#n)";
				vo_61553_2.task_prompt = org.apache.commons.lang3.StringUtils.join("擒回#Y作恶的土匪#P|",map.getName(),"(",renwuMonster.getX(),",",renwuMonster.getY(),")::",chara.name,"擒拿的土匪","#P#n(剩余:#RTIME_LEFT#n)");
				vo_61553_2.refresh = 1;
				vo_61553_2.task_end_time = (int) (System.currentTimeMillis()/1000L)+30*60;
				vo_61553_2.attrib = 0;
				vo_61553_2.reward = "";
				vo_61553_2.show_name = "将功补过";
				vo_61553_2.task_extra_para = releseGameObjectChar.chara.uuid;
				vo_61553_2.flag = chara.uuid;
				vo_61553_2.task_state = "0";
				GameUtilRenWu.createTask(vo_61553_2,chara);
				
				Vo_APPEAR npc = new Vo_APPEAR();
				npc.mapid = map.getMapId();
				npc.id = GameCommonUtil.generateBossId();
				npc.x = renwuMonster.getX();
				npc.y = renwuMonster.getY();
				npc.icon = 6202;
				npc.type = 2;
				npc.org_icon = 6202;
				npc.portrait = 6202;
				npc.name = chara.name+"擒拿的土匪";
				npc.level = chara.level;
				npc.leixing = 4;
				GameCore.jieyuMonster.put(npc.id, npc);
				//30分钟后过期
				GameData.that.redisUtils.set("jieyu_tufei:"+chara.uuid+":"+npc.id, "", 30*59);
				GameUtil.sendMeTips("成功领取求情任务，快去完成吧");
				GameUtil.closeDlg("PrisonDlg");
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("quitFixedTeam".equals(para)) {
				if(!StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
					//确定退出固定队,找出原来的固定队扣除200
					FixedTeam fixedTeam = (FixedTeam) gameObjectChar.confirmData;
					if(fixedTeam != null) {
//						if(fixedTeam.getIntimacy()-200 < fixedTeam.getLevel()*1000) {
//							//降级处理
//							fixedTeam.setLevel(fixedTeam.getLevel()-1);
//						}
//						fixedTeam.setIntimacy(fixedTeam.getIntimacy()-200);
//						//最小数为0
//						if(fixedTeam.getIntimacy()<0 ) {
//							fixedTeam.setIntimacy(0);
//						}
						//把他移除固定队伍
						JSONArray parseArray = com.alibaba.fastjson.JSONObject.parseArray(fixedTeam.getMembers());
						Iterator<Object> iterator = parseArray.iterator();
						List<String> gids = new ArrayList<>();
						while(iterator.hasNext()) {
							com.alibaba.fastjson.JSONObject next = (com.alibaba.fastjson.JSONObject) iterator.next();
							gids.add(next.getString("gid"));
							if(next.getString("gid").equals(chara.uuid)) {
								iterator.remove();
							}
						}
						fixedTeam.setMembers(parseArray.toJSONString());
						//同时保存到数据库
						Characters characters = GameData.that.baseCharactersService.findOneByGid2(chara.uuid);
						characters.setFixedTeamName("");
						GameData.that.baseCharactersService.updateById(characters);
						//更新固定队伍信息
						GameData.that.fixedTeamService.updateByPrimaryKeySelective(fixedTeam);
						//通知队友
						gids.remove(chara.uuid);
						for(String gid:gids) {
							GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gid);
							if(gameObjectCharByUUid != null) {
								GameCommonUtil.sendTips(org.apache.commons.lang3.StringUtils.join("#Y",chara.name,"#n已退出固定队"), gameObjectCharByUUid);
							}else {
								//以邮件的方式通知
								Chara toChara = new Chara();
								toChara.uuid = gid;
								GameCommonUtil.sendSystemEmail(toChara, org.apache.commons.lang3.StringUtils.join("#Y",chara.name,"#n已退出固定队"), "固定通知", "固定队");
							}
						}
						//退出固定队
						chara.fixedTeamName = "";
						//设置恢复默认
						chara.getSettings().remove("ft_dun_yb");
						chara.getSettings().remove("ft_change_look");
						chara.getSettings().remove("ft_req_team");
						chara.getSettings().remove("ft_inv_team");
						chara.getSettings().remove("ft_recruit");
						chara.getSettings().remove("ft_lead_team");
						chara.getSettings().remove("ft_use_item");
						chara.getSettings().remove("ft_change_team_seq");
						GameUtil.sendMeTips("你已成功退出固定队");
					}
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("leaderQuitFixedTeam".equals(para)) { //队长退出固定队
				if(!StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
					FixedTeam fixedTeam = (FixedTeam) gameObjectChar.confirmData;
					if(fixedTeam != null && fixedTeam.getLeaderUid().equals(chara.uuid)) {
						//把他移除固定队伍
						JSONArray parseArray = com.alibaba.fastjson.JSONObject.parseArray(fixedTeam.getMembers());
						Iterator<Object> iterator = parseArray.iterator();
						while(iterator.hasNext()) {
							com.alibaba.fastjson.JSONObject next = (com.alibaba.fastjson.JSONObject) iterator.next();
							String gid = next.getString("gid");
							GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gid);
							if(gameObjectCharByUUid != null) {
								//退出固定队
								gameObjectCharByUUid.chara.fixedTeamName = "";
								//设置恢复默认
								gameObjectCharByUUid.chara.getSettings().remove("ft_dun_yb");
								gameObjectCharByUUid.chara.getSettings().remove("ft_change_look");
								gameObjectCharByUUid.chara.getSettings().remove("ft_req_team");
								gameObjectCharByUUid.chara.getSettings().remove("ft_inv_team");
								gameObjectCharByUUid.chara.getSettings().remove("ft_recruit");
								gameObjectCharByUUid.chara.getSettings().remove("ft_lead_team");
								gameObjectCharByUUid.chara.getSettings().remove("ft_use_item");
								gameObjectCharByUUid.chara.getSettings().remove("ft_change_team_seq");
								//同时保存到数据库
								Characters characters = GameData.that.baseCharactersService.findOneByGid2(chara.uuid);
								characters.setFixedTeamName("");
								GameData.that.baseCharactersService.updateById(characters);
								//自己的话不通知
								if(gameObjectCharByUUid.chara.id == chara.id) {
									continue;
								}
								GameCommonUtil.sendTips(org.apache.commons.lang3.StringUtils.join("队长#Y",chara.name,"#n已解散固定队伍"), gameObjectCharByUUid);
							}else {
								Characters ch = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "id","data");
								com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(ch.getData());
								com.alibaba.fastjson.JSONObject sets = json.getJSONObject("settings");
								sets.remove("ft_dun_yb");
								sets.remove("ft_change_look");
								sets.remove("ft_req_team");
								sets.remove("ft_inv_team");
								sets.remove("ft_recruit");
								sets.remove("ft_lead_team");
								sets.remove("ft_use_item");
								sets.remove("ft_change_team_seq");
								json.put("settings", sets);
								//不在线的话
								Example chExample = new Example(Characters.class);
								chExample.createCriteria().andEqualTo("gid", gid);
								Characters update = new Characters();
								update.setFixedTeamName("");
								update.setData(json.toJSONString());
								GameData.that.baseCharactersService.updateByExampleSelective(update, chExample);
								//以邮件的方式通知
								Chara toChara = new Chara();
								toChara.uuid = gid;
								GameCommonUtil.sendSystemEmail(toChara, org.apache.commons.lang3.StringUtils.join("队长#Y",chara.name,"#n已解散固定队伍"), "固定通知", "固定队");
							}
						}
						//更新固定队伍信息
						GameData.that.fixedTeamService.deleteByPrimaryKey(fixedTeam.getId());
						GameUtil.sendMeTips("你已成功解散固定队");
					}
				}
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("wabao".equals(para)) {
				//前去讨伐
				gameObjectChar.sendOne(new MSG_AUTO_WALK(), new Vo_AUTO_WALK((String)gameObjectChar.confirmData));
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("acceptChangePolar".equals(para)) { //确定门派转换
				if(chara.upgrade_state != 0) {
					GameUtil.sendMeTips("请切换真身在转门派");
					return;
				}
				if(chara.level<70) {
					GameUtil.sendMeTips("等级低于70无法转换");
					return;
				}
				if(chara.taskMap.get("门派转换") == null) {
					GameUtil.sendMeTips("你还未领取门派转换任务");
					return;
				}
				//判断是否脱下武器
				for(Goods goods:chara.getOtherGoods()) {
					if(goods.pos == 1) {
						GameUtil.sendMeTips("门派转换不允许携带武器");
						return;
					}
				}
				//判断娃娃是否携带武器
				if(chara.charaYuanyingInfo.equip.get(1) != null 
						|| chara.charaYuanyingInfo.equip.get(1) != null) {
					GameUtil.sendMeTips("门派转换不允许娃娃携带武器");
					return;
				}
				int oldPolar = chara.polar;
				//新的门派
				int newPolar = (int) gameObjectChar.confirmData;
				if(newPolar == oldPolar) {
					GameUtil.sendMeTips("不能选择原门派");
					return;
				}
				//判断是否有改名卡
				int num = GameCommonUtil.getGoodsNum(chara, "改头换面卡");
				if(num < 2) {
					GameUtil.sendMeTips("改头换面卡不足，无法转换");
					return;
				}
				GameUtil.removemunber(chara, "改头换面卡", 2);
				//把这人法术、障碍都清零需重新学习
//				List<JiNeng> skills = chara.jiNengList;
//				Iterator<JiNeng> iterator = skills.iterator();
//				while(iterator.hasNext()) {
//					JiNeng next = iterator.next();
//					next.skill_disabled = 1;
//					next.skill_level = 0;
//					next.level_improved = 0;
//				}
//				//刷新技能信息
//				gameObjectChar.sendOne(new MSG_UPDATE_SKILLS(), GameUtil.a32747(chara));
				Iterator<JiNeng> cjineng = chara.jiNengList.iterator();
				while(cjineng.hasNext()) {
					JiNeng next = cjineng.next();
					if(next.skill_no == 301 || next.skill_no == 302 || next.skill_no == 501) {
						continue;
					}
					cjineng.remove();
				}
				//删除元婴技能
				Iterator<JiNeng> yjineng = chara.charaYuanyingInfo.jiNengList.iterator();
				while(yjineng.hasNext()) {
					JiNeng next = yjineng.next();
					if(next.skill_no == 301 || next.skill_no == 302 || next.skill_no == 501) {
						continue;
					}
					yjineng.remove();
				}
				chara.polar = newPolar;
				chara.waiguan = GameUtil.getCharWaiGuan2(newPolar, chara.sex);
				//设置坐骑
				if(chara.zuoqiId>0 && chara.zuoqiwaiguan>0) {
					for (int i = 0; i < chara.pets.size(); ++i) {
						if (chara.pets.get(i).id == chara.zuoqiId) {
							// 如果注入了彩凤之魂，就变换角色外观
							if (chara.pets.get(i).petShuXing.get(0).zhuruCaifeng == 1 && chara.upgrade_state == 0) {
								// 彩凤坐着的外观
								chara.zuoqiwaiguan = 31501; // 彩凤之魂特效
								// 如果是元血婴的状态
								if (chara.upgrade_state != 0) {
									chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara, chara.zuoqiwaiguan);
								} else {
									int zuowaiguan = CMD_SELECT_CURRENT_MOUNT.typeMounts(chara.pets.get(i).petShuXing.get(0).type + 1000, chara.polar,
											chara.sex - 1);
									chara.zuowaiguan = zuowaiguan;
								}
							}
							// 如果没有彩凤之魂特效，就默认的
							else {
								int zuoqiwaiguan = chara.pets.get(i).petShuXing.get(0).type + 1000;
								chara.zuoqiwaiguan = zuoqiwaiguan;
								if (chara.upgrade_state != 0) {
									chara.zuowaiguan = GameCommonUtil.getYuanYingZuoqiWaiguan(chara, chara.zuoqiwaiguan);
								} else {
									int zuowaiguan = CMD_SELECT_CURRENT_MOUNT.typeMounts(chara.pets.get(i).petShuXing.get(0).type + 1000, chara.polar,
											chara.sex - 1);
									chara.zuowaiguan = zuowaiguan;
								}
							}
						}
					}
				}
				//更新外观
				Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(chara);
				gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), a61661);
				//更新全部数据
				GameUtil.sendUpdate(chara);
				//转换门派成功
				gameObjectChar.sendOne(new MSG_CHANGE_POLAR_SUCC(), new Integer[] {oldPolar,newPolar});
				//赠送潜能已供学习技能
				chara.pot = 2000000000;
				//移除任务
				GameUtilRenWu.removeTask("门派转换", chara);
				chara.oldPolar = oldPolar;
				//如果开了自动就设置为普攻
				if(chara.autofight_select == 1) {
					chara.autofight_skillaction = 2;
					chara.autofight_skillno = 0;
					GameCommonUtil.fightCmdInfo(gameObjectChar);
				}
				//门派转换数据
				GameUtil.sendMeTips("转换门派成功，你消耗了两张#R改头换面卡,#n3秒钟后自动断线重连！");
				//下线
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						gameObjectChar.offline();
					}
				}, 5000);
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("pet_inherit".equals(para) && gameObjectChar.confirmData != null) {
				if(chara.chargeScore<GameConfig.config.getBaseConfig().getPetInheritScore()) {
					GameUtil.sendMeTips("积分不足");
					return;
				}
				Object[] objs = (Object[]) gameObjectChar.confirmData;
				Petbeibao mpet = (Petbeibao) objs[0];
				Petbeibao opet = (Petbeibao) objs[1];
				//对换两个宠物的武学
				int mpetWuXue = mpet.petShuXing.get(0).intimacy;
				int opetWuXue = opet.petShuXing.get(0).intimacy;
				mpet.petShuXing.get(0).intimacy = opetWuXue;
				opet.petShuXing.get(0).intimacy = mpetWuXue;
				GameObjectChar.send(new MSG_UPDATE_PETS(), Lists.newArrayList(mpet,opet));
				GameUtil.sendMeTips("宠物继承成功，武学已调换");
				chara.chargeScore-=GameConfig.config.getBaseConfig().getPetInheritScore();
			GameUtilRenWu.refshPointTask(chara);

				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
			}else if("luobo".equals(para)) {
				int daluozi = GameCommonUtil.getGoodsNum(chara, "大萝卜");
				int xluozi = GameCommonUtil.getGoodsNum(chara, "萝卜");
				int count = (daluozi*2)+xluozi;
				if(count>0) {
					ConfigInfo taoziLuobo = GameData.that.configInfoService.getOneByUuid("taozi_luobo");
					if(taoziLuobo != null) {
						com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(taoziLuobo.getData());
						int luobo = parseObject.getIntValue("luobo");
						GameUtil.huodejingyan(chara, luobo*count, "桃子萝卜大收集");
						GameUtil.removemunber(chara, "大萝卜", daluozi);
						GameUtil.removemunber(chara, "萝卜", xluozi);
					}
				}
			}else if("taozi".equals(para)) {
				int dataozi = GameCommonUtil.getGoodsNum(chara, "大桃子");
				int xtaozi = GameCommonUtil.getGoodsNum(chara, "桃子");
				int count = (dataozi*2)+xtaozi;
				if(count>0) {
					ConfigInfo taoziLuobo = GameData.that.configInfoService.getOneByUuid("taozi_luobo");
					if(taoziLuobo != null) {
						com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(taoziLuobo.getData());
						int taozi = parseObject.getIntValue("taozi");
						GameUtil.adddaohang(chara, taozi*count*1440, "桃子萝卜大收集");
						GameUtil.removemunber(chara, "大桃子", dataozi);
						GameUtil.removemunber(chara, "桃子", xtaozi);
					}
				}
			}else if("chargeLuckOne".equals(para) ) {
				int chargeNpcOnePrice = GameConfig.config.getBaseConfig().getChargeNpcOnePrice();
				if (chara.chargeScore < chargeNpcOnePrice) {
					GameUtil.sendMeTips("积分不足");
					return;
				}

				int choujiangDaojuCount = GameConfig.config.getBaseConfig().choujiangDaojuCount;
				int count = GameCommonUtil.getGoodsNum(chara, "好运牌");
				if (count < choujiangDaojuCount)
				{
					GameUtil.sendMeTips("需要#R"+choujiangDaojuCount+"#W个#Y好运牌#W才能抽奖");
					return;
				}

				GameUtil.removemunber(chara, "好运牌", choujiangDaojuCount);
				chara.chargeScore -= chargeNpcOnePrice;
			GameUtilRenWu.refshPointTask(chara);

				String[] luckInfo = LuckDrawUtils.npcLuckDraw();
				if(luckInfo != null && luckInfo.length>0) {
					LuckDrawUtils.huodechoujiang(luckInfo, gameObjectChar, "抽奖大使");
					if(luckInfo[1].equals("道行")) {
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[3],"#n天道行"));
					}else if(luckInfo[1].equals("潜能")) {
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[3],"#n点潜能"));
					}else if(luckInfo[1].equals("经验")){
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[3],"#n点经验"));
					}else if(luckInfo[1].equals("积分")){
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[3],"#n点积分"));
					}else {
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R",luckInfo[0]));
					}
					log.info("抽奖物品:{}", Arrays.toString(luckInfo));
				}
				GameUtil.sendMeTips("你消耗#R"+chargeNpcOnePrice+"#n积分开始了一次抽奖");
			}else if("chargeLuckMany".equals(para) ) {
				int chargeNpcOnePrice = GameConfig.config.getBaseConfig().getChargeNpcOnePrice();
				if (chara.chargeScore < chargeNpcOnePrice*10) {
					GameUtil.sendMeTips("积分不足");
					return;
				}


				int choujiangDaojuCount = GameConfig.config.getBaseConfig().choujiangDaojuCount;
				int count = GameCommonUtil.getGoodsNum(chara, "好运牌");
				if (count < choujiangDaojuCount*10)
				{
					GameUtil.sendMeTips("需要#R"+choujiangDaojuCount*10+"#W个#Y好运牌#W才能抽奖");
					return;
				}

				GameUtil.removemunber(chara, "好运牌", choujiangDaojuCount*10);

				for (int i = 0; i < 10; i++) {
					String[] luckInfo = LuckDrawUtils.npcLuckDraw();
					if(luckInfo == null) {
						continue;
					}
					chara.chargeScore -= chargeNpcOnePrice;
					GameUtilRenWu.refshPointTask(chara);

					LuckDrawUtils.huodechoujiang(luckInfo, gameObjectChar, "抽奖大使");
					if(luckInfo[1].equals("道行")) {
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[0],"#n天道行"));
					}else if(luckInfo[1].equals("潜能")) {
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[0],"#n点潜能"));
					}else if(luckInfo[1].equals("经验")){
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[0],"#n点经验"));
					}else if(luckInfo[1].equals("积分")){
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R" , luckInfo[0],"#n点积分"));
					}else {
						GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你抽中了#R",luckInfo[0]));
					}
				}
				GameUtil.sendMeTips("你消耗#R"+chargeNpcOnePrice*10+"#n积分开始了10次抽奖");
			}
		} 
		
		//点击取消
		else {
			if ("switchXianMo".equals(para)) {
				// 如果点击取消就要回第一步
				chara.currentConfirmItem = "openSwitchXianMo";
			} else if ("tttGoRun".equals(para)) {
				// 通天塔突破阶段取消逃跑
				FightContainer fightContainer = FightManager.getFightContainer();
				if (fightContainer != null) {
					Map<String,Object> obj = new LinkedHashMap<>();
					obj.put("int:id", chara.id);
					obj.put("short:result", 0);
					GameObjectChar.send(new CommonCmd(0x2DD3), obj);
				}
			}else if("wabao".equals(para)) {
				gameObjectChar.currentConfirmItem = "";
				gameObjectChar.confirmData = null;
				//继续挖宝
				for (int i = 0; i < chara.backpack.size(); ++i) {
					if (chara.backpack.get(i).goodsInfo.str.equals("超级藏宝图")) {
						Goods goods = chara.backpack.get(i);
						// 如果使用了超级藏宝图
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
						vo_61553_0.task_prompt = org.apache.commons.lang3.StringUtils.join("前往#Z" , renwuMonster.getMapName() , "|" , renwuMonster.getMapName() , "("
								, renwuMonster.getX() , "," , renwuMonster.getY() , ")#Z寻宝");
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
				}
				GameUtil.sendMeTips("道具不足无法继续挖宝");
				return;
			}else {
				GameObjectChar toGameObject = GameObjectCharMng
						.getGameObjectChar(gameObjectChar.upduizhangid);
				if (toGameObject == null)
					return;
				Vo_61591_0 vo_61591_0 = new Vo_61591_0();
				vo_61591_0.ask_type = "request_team_leader";
				vo_61591_0.name = toGameObject.chara.name;
				GameObjectChar.send(new M61591_0(), vo_61591_0);
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "队长拒绝了你的带队申请。";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0, toGameObject.chara.id);
			}
		}
	}

	@Override
	public int cmd() {
		return 20736;
	}
}