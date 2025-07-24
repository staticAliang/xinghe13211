package com.fengshen.server.process.common;

import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.fengshen.server.data.write.*;
import com.fengshen.server.domain.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.DaySignPrize;
import com.fengshen.db.domain.LivenessRewards;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.domain.PetHelpType;
import com.fengshen.db.domain.Renwu;
import com.fengshen.db.domain.SaleClassifyGood;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.service.chara.LivenessRewardsService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.NoviceGiftBagUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_20568_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_41051_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.vo.Vo_4163_0;
import com.fengshen.server.data.vo.Vo_45074_0;
import com.fengshen.server.data.vo.Vo_45128_0;
import com.fengshen.server.data.vo.Vo_45319_0;
import com.fengshen.server.data.vo.Vo_49153_0;
import com.fengshen.server.data.vo.Vo_49169_0;
import com.fengshen.server.data.vo.Vo_49169_0.SignDaysItem;
import com.fengshen.server.data.vo.Vo_49179_0;
import com.fengshen.server.data.vo.Vo_49183;
import com.fengshen.server.data.vo.Vo_49183_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_61593_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.active.Vo_LIVENESS_INFO;
import com.fengshen.server.data.vo.cangbaotu.Vo_AUTO_CANGBAOTU_READY_SEARCH;
import com.fengshen.server.data.vo.fight.Vo_COMBAT_STATUS_INFO;
import com.fengshen.server.data.vo.party.Vo_PARTY_INFO.Leader;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.team.ListVo_TEAM_DATA;
import com.fengshen.server.data.vo.team.Vo_TEAM_DATA;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.active.MSG_LIVENESS_INFO;
import com.fengshen.server.data.write.active.MSG_LIVENESS_REWARDS;
import com.fengshen.server.data.write.cangbaotu.MSG_AUTO_CANGBAOTU_READY_SEARCH;
import com.fengshen.server.data.write.fight.MSG_COMBAT_STATUS_INFO;
import com.fengshen.server.data.write.market.M49179_0;
import com.fengshen.server.data.write.party.MSG_PARTY_INFO;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.rank.M61653_0;
import com.fengshen.server.data.write.rank.MSG_TOP_USER;
import com.fengshen.server.data.write.rank.M_MSG_RANK_CLIENT_INFO;
import com.fengshen.server.data.write.store.MSG_PET_STORE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.data.write.tongtianta.MSG_TONGTIANTA_INFO;
import com.fengshen.server.data.write.user.MSG_DAILY_SIGN;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.rank.Rank;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameShuaGuai;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.game.PetFlyMgr;
import com.fengshen.server.job.GameRankJob;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.GameConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

// 这个类会监控客户端所有的按钮事件
@Service
@Slf4j
public class CMD_GENERAL_NOTIFY implements GameHandler {
	
	@Autowired
	private GameRankJob gameRankJob;
	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		// type为前端操控的功能按钮
		int type = GameReadTool.readShort(buff);
		// para1 == 0为关闭，para1 == 1为打开
		String para1 = GameReadTool.readString(buff);
		String para2 = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info("客户端通知，type:{}、para1:{}、para2:{}", type, para1, para2);
		if(chara == null) {
			return;
		}
		if(20007 == type) {
			FightContainer fightContainer = FightManager.getFightContainer(chara.id);
			if(fightContainer != null) {
				int fid = Integer.valueOf(para1);
				FightObject fightObject = FightManager.getFightObject(fid);
				if(fightObject != null) {
					Vo_COMBAT_STATUS_INFO info = new Vo_COMBAT_STATUS_INFO();
					info.setObjId(fid);
					info.setIsCanUseHYJJ(1);
					info.setStatusType("FightOpponent");
					info.setZhenfaPolar(0);
					info.getBuildFields().put("life", fightObject.shengming);
					info.getBuildFields().put("max_life", fightObject.max_shengming);
					info.getBuildFields().put("status_show_opponent_life", fightContainer.hyjjRound);
					GameObjectChar.send(new MSG_COMBAT_STATUS_INFO(), info);
				}
			}
			return;
		}
		//摆摊价格
		if(40021 == type) {
			SaleClassifyGood classifyGood = GameData.that.baseSaleClassifyGoodService.findOneByStr(para1);
	        if (classifyGood != null) {
	        	Integer price = classifyGood.getPrice();
	        	String str = classifyGood.getName();
	        	//摆摊物品价格
	        	Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
	        	vo_9129_0.notify = ClientButtonIdConst.NOTIFY_STALL_ITEM_PRICE;
	        	vo_9129_0.para = "{150:" + (int)(price * 1.5) + ",140:" + (int)(price * 1.4) + ",130:" + (int)(price * 1.3) + ",120:" + (int)(price * 1.2) + ",110:" + (int)(price * 1.1) + ",100:" + price + ",90:" + (int)(price * 0.9) + ",80:" + (int)(price * 0.8) + ",70:" + (int)(price * 0.7) + ",60:" + (int)(price * 0.6) + ",50:" + (int)(price * 0.5) + ",\"name\":\"" + str + "\"}";
	        	GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
	        }
	        return;
		}
		
		//领取宝宝
		if(20005 == type) {
			String[] info = para1.split("\\|");
			String name = info[1].split("\\$")[0];
			String petName = name.substring(0,name.indexOf("("));
			String petType = name.substring(name.indexOf("(")+1,name.indexOf(")"));
			if(chara.taskMap.get("主线—浮生若梦") != null && "主线—浮生若梦_s4".equals(chara.taskMap.get("主线—浮生若梦").currentTask)) {
				if(chara.taskMap.get("主线—浮生若梦").task_state.equals("3")) {
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					String[] split = renwu.getReward().split("#");
					if(!petName.equals(split[0])) {
						GameUtil.sendMeTips("请提交正确数据");
						return;
					}else if(!petType.equals(split[1])) {
						GameUtil.sendMeTips("宠物类型不对");
						return;
					}
					//#I1|白果儿(宝宝)$1#I
					int id = 0;
					if("宝宝".equals(petType)) {
						id = GameUtil.huodechongwu(chara, petName, 2, "主线");
					}else if("神兽".equals(petType)) {
						id = GameUtil.huodeshenshou(chara, petName, "主线");
					}else if("变异".equals(petType)) {
						id = GameUtil.huodebianyi(chara, petName, "主线");
					}else if("精怪".equals(petType)) {
						id = GameUtil.huodezuoqi(chara, petName, "主线");
					}
					chara.current_task = "主线—浮生若梦_s5";
					//创建主线任务
					GameUtilRenWu.createZhuXianFuShengRuoMengTask(chara, 
							GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task));
					//设置参战宠物
					chara.chongwuchanzhanId = id;
					Vo_4163_0 vo_4163_0 = new Vo_4163_0();
					vo_4163_0.id = id;
					vo_4163_0.b = 1;
					GameObjectChar.send(new M4163_0(), vo_4163_0);
				}
			}
			if (chara.taskMap.get("主线—拜入师门") != null && "主线—拜入师门s30_2".equals(chara.taskMap.get("主线—拜入师门").currentTask)) {
				StringBuilder rawds = new StringBuilder();
				String[] split = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task).getReward().split(",");
				for(String sp:split) {
					if(sp.indexOf("宝宝") != -1 || sp.indexOf("神兽") != -1 || sp.indexOf("神兽") != -1) {
						continue;
					}
					rawds.append(sp).append(",");
				}
				rawds.deleteCharAt(rawds.length()-1);
				String[] finalRawd = rawds.toString().split(",");
				for(String fr:finalRawd) {
					String[] jiangli = fr.split("\\#");
					GameUtil.huodechoujiang(jiangli, gameObjectChar, "主线");
				}
				if("宝宝".equals(petType)) {
					GameUtil.huodechongwu(chara, petName, 2, "主线");
				}else if("神兽".equals(petType)) {
					GameUtil.huodeshenshou(chara, petName, "主线");
				}else if("变异".equals(petType)) {
					GameUtil.huodebianyi(chara, petName, "主线");
				}
				chara.current_task = "主线—山雨欲来s1";
				GameUtilRenWu.removeTask("主线—拜入师门", chara);
				//创建主线任务
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				GameUtilRenWu.createZhuXianShanYuYuLaiTask(chara, renwu);
			}
			return;
		}
		
		// 帮派升级
		if (41 == type) {
			if (chara.getPartyJob().equals("帮主") || chara.getPartyJob().equals("副帮主")) {
				Party party = GameData.that.partyService.findByPartyName(chara.getPartyName());
				if (party.getPartyLevel() == 4) {
					GameUtil.sendMeTips("贵帮已是顶级帮派无法在升级");
					return;
				} else if (party.getConstruct() < GamePartyUtil.getPartyRequirdConstruct(party.getPartyLevel() + 1)) {
					GameUtil.sendMeTips("建设度不够无法升级");
					return;
				} else {
					// 开始升级
					party.setPartyLevel(party.getPartyLevel() + 1);
					GameData.that.partyService.updateByPrimaryKeySelective(party);
					// 刷新帮派信息
					GameObjectChar.send(new MSG_PARTY_INFO(), party);
					GameCore.partyMap.put(party.getPartyName(), party);
					GameUtil.sendMeTips("升级成功");
				}
			}
			return;
		}
		// 活跃度领取奖励
		if (33 == type) {
			// 查询是否领取
			LivenessRewardsService bean = SpringBeanUtils.getBean(LivenessRewardsService.class);
			if (bean.getLivenessRewardsIsGet(chara.uuid, Integer.valueOf(para1)) > 0) {
				GameUtil.sendMeTips("该奖励已领取");
				return;
			}
			String name = "";
			switch (para1) {
			case "20":
				name = "血池";
				GameUtil.huodedaoju(chara, name, 1);
				break;
			case "40":
				name = "灵池";
				GameUtil.huodedaoju(chara, "灵池", 1);
				break;
			case "60":
				name = "超级仙风散";
				GameUtil.huodedaoju(chara, "超级仙风散", 1);
				break;
			case "80":
				name = "天星石";
				GameUtil.huodedaoju(chara, "天星石", 1);
				break;
			case "100":
				name = "银元宝";
				chara.silverCoin += 200;
				GameUtil.sendUpdate(chara);
				break;
			}
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 1;
			vo_40964_0.name = name;
			vo_40964_0.param = "200";
			vo_40964_0.rightNow = 2;
			GameObjectChar.send(new M40964_0(), vo_40964_0);

			// 插入领取记录
			LivenessRewards record = new LivenessRewards();
			record.setActivity(Integer.parseInt(para1));
			record.setGid(chara.uuid);
			record.setName(chara.name);
			record.setAddTime(new Date());
			bean.insertSelective(record);
			List<Integer[]> livenessRewards = GameCommonUtil.getLivenessRewards(chara);
			GameObjectChar.send(new MSG_LIVENESS_REWARDS(), livenessRewards);
			return;
		}
		// 获取活跃度信息
		if (32 == type) {
			List<Vo_LIVENESS_INFO> infos = new ArrayList<>();
			int shimencishu = chara.shimencishu>=10?2000:(chara.shimencishu - 1) * 200;
			infos.add(new Vo_LIVENESS_INFO("师门任务", chara.shimencishu-1, shimencishu, ""));
			infos.add(new Vo_LIVENESS_INFO("除暴任务", chara.chubao - 1, (chara.chubao - 1) * 200, ""));
			int partyNum = chara.partyNum - 1<0?0:chara.partyNum -1;
			infos.add(new Vo_LIVENESS_INFO("帮派任务", partyNum, partyNum * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("副本", chara.fb_num, chara.fb_num * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("【修炼】修行", chara.xiuxingcishu - 1, (chara.xiuxingcishu - 1) * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("【修炼】十绝阵", chara.xiuxingcishu - 1, (chara.xiuxingcishu - 1) * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("竞技场", 0, 0, ""));
			infos.add(new Vo_LIVENESS_INFO("助人为乐", chara.baibangmang, chara.baibangmang * 100, ""));
			int partyFightNum = chara.partyFightNum - 1<0?0:chara.partyFightNum;
			infos.add(new Vo_LIVENESS_INFO("帮派日常挑战", chara.partyFightNum, partyFightNum * 1000, ""));
			infos.add(new Vo_LIVENESS_INFO("刷道", chara.shuadao, (chara.shuadao - 1) * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("【周】武学历练", 0, 0, ""));
			infos.add(new Vo_LIVENESS_INFO("通天塔", chara.tongttcishu, chara.tongttcishu * 2000, ""));
			infos.add(new Vo_LIVENESS_INFO("斗宠大会", 0, 0, ""));
			infos.add(new Vo_LIVENESS_INFO("【周】八仙梦境", chara.baxiantiaozhan, 0, ""));
			infos.add(new Vo_LIVENESS_INFO("修法任务", chara.xiufacishu, chara.xiufacishu * 1000, ""));
			infos.add(new Vo_LIVENESS_INFO("【地府】引魂入殿", 0, 0, ""));
			infos.add(new Vo_LIVENESS_INFO("【周】地宫关卡", 0, 0, ""));
			infos.add(new Vo_LIVENESS_INFO("【周】萝卜桃子", 0, 0, ""));
			// 限时活动
			infos.add(new Vo_LIVENESS_INFO("镖行万里", 0, 0, ""));
			int xuanshang = chara.xuanshangcishu>0?1000:0;
			infos.add(new Vo_LIVENESS_INFO("悬赏任务", chara.xuanshangcishu, xuanshang, ""));
			infos.add(new Vo_LIVENESS_INFO("海盗入侵", chara.haidaocishu, chara.haidaocishu * 200, ""));

			// 其他活动
			infos.add(new Vo_LIVENESS_INFO("上古妖王", chara.shanggucishu, chara.shanggucishu * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("挑战掌门", chara.zhangmentiaozhan, chara.zhangmentiaozhan * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("证道殿", chara.zhengdaodiancishu, chara.zhengdaodiancishu * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("英雄会", chara.heropubcishu, chara.heropubcishu * 100, ""));
			infos.add(new Vo_LIVENESS_INFO("地图守护神", chara.mapguardcishu, chara.mapguardcishu * 100, ""));
			List<Integer[]> infoArr = GameCommonUtil.getLivenessRewards(chara);
			GameObjectChar.send(new MSG_LIVENESS_INFO(), new Object[] { infos, infoArr });

		}
		// 排行榜宠物名片
		if (34 == type) {
			CharaPet pet = GameData.that.charaPetService.getPetById(Integer.valueOf(para1));
			if (pet != null && !StringUtils.isNullOrEmpty(pet.getPet())) {
				Petbeibao petBeibao = JSONObject.parseObject(pet.getPet(), Petbeibao.class);
				GameObjectChar.send(new MSG_PET_CARD(), new Object[] { petBeibao, pet.getOwnerName() });
			}
			return;
		}
		// 排行榜装备名片
		if (30019 == type) {
			// 查看装备名片 0:uuid，1:装备id
			if (!StringUtils.isNullOrEmpty(para1)) {
				String[] dataArr = para1.split("\\|");
				String gid = dataArr[0];
				String iid = dataArr[1];
				GameObjectChar showGame = GameObjectCharMng.getGameObjectCharByUUid(gid);
				Goods goods = null;
				if(showGame != null) {
					List<Goods> otherGoods = showGame.chara.otherGoods;
					for(Goods g:otherGoods) {
						if(g.goodsInfo.auto_fight.equals(iid)) {
							goods = g;
							break;
						}
					}
				}else {
					//不在线
					Characters characters = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "backpack");
					List<Goods> backpack = JSONObject.parseArray(characters.getBackpack(), Goods.class);
					for(Goods g:backpack) {
						if(g.goodsInfo.auto_fight.equals(iid)) {
							goods = g;
							break;
						}
					}
				}
				if(goods != null) {
					GameObjectChar.send(new MSG_EQUIP_CARD(), new Object[] {goods});
				}
			}
			return;
		}
		//启动自动断线
		if (20011 == type) {
			return;
		}
		//断线
		if(16 == type) {
			//进入游戏
			if ("1".equals(para1)) {
//				gameObjectChar.sendOne(new MSG_PERFORMANCE(), null);
				//如果有队伍
				if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
					// 我的队伍
					GameUtil.a4119(gameObjectChar.gameTeam.duiwu);
					// 更新右侧组队信息
					GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
				}else {
					//没有队伍了
					gameObjectChar.sendOne(new M4121_0(), new ArrayList<Vo_4121_0>());
					gameObjectChar.sendOne(new M4119_0(), new ArrayList<Vo_4121_0>());
				}
				log.info("重连:{}",gameObjectChar.isBack.get());
				GameCommonUtil.setCharaTitleFlag(chara);
				if(gameObjectChar.isBack.compareAndSet(true, false)) {
					if(chara.isFight) {
						gameObjectChar.isEndRound.set(true);
						new Timer().schedule(new TimerTask() {
							@Override
							public void run() {
								FightManager.reconnect(chara);
								Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
								//如果有队伍
								if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
									// 我的队伍
									GameUtil.a4119(gameObjectChar.gameTeam.duiwu);
									// 更新右侧组队信息
									GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
								}
								gameObjectChar.isBack.set(false);
								vo_9129_2 = new Vo_GENERAL_NOTIFY();
								vo_9129_2.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
								vo_9129_2.para = "WaitDlg";
								gameObjectChar.sendOne(new MSG_GENERAL_NOTIFY(), vo_9129_2);
							}
						}, 3000);
					}
				}else if(gameObjectChar.isLook == 1) {
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							GameCommonUtil.lookFight(Lists.newArrayList(gameObjectChar), FightManager.getFightContainer(gameObjectChar.lookCharId), gameObjectChar.lookCharId);
							Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
							vo_9129_2.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
							vo_9129_2.para = "WaitDlg";
							gameObjectChar.sendOne(new MSG_GENERAL_NOTIFY(), vo_9129_2);
						}
					}, 3000);
				}
			}else if("2".equals(para1)) { //长时间在游戏未激活状态
				GameUtil.sendMeTips("你已进入挂机状态");
			}else if("3".equals(para1)) { //切入后台
				gameObjectChar.isBack.set(true);
			}
			return;
		}
		if (20001 == type) {
			// 点击聊天--不处理珍宝和集市
			if (para1.indexOf("珍宝=") == -1 && para1.indexOf("集市=") == -1) {
				GameObjectChar.send(new MSG_CARD_INFO(), para1);
			}
			return;
		}
		// 放弃任务
		if (type == ClientButtonIdConst.DROP_TASK) {
			if ("超级宝藏".equals(para1)) {
				GameUtilRenWu.removeTask("超级宝藏", chara);
				GameUtil.sendMeTips("你已放弃超级宝藏任务...");
			} else if (para1.indexOf("通天塔") != -1) {
				if (chara.commonTaskMap.get("通天塔") != null) {
					// 判断是否在通天塔
					if (chara.mapName.equals("通天塔")) {
						GameCommonUtil.dialogOk("在通天塔内无法放弃");
					} else if (chara.tongtiantaTask.getCurLayer() > chara.level) {
						// 如果是突破阶段放弃的话,将无法再次挑战
						GameUtil.confirm(chara, "通天塔#R突破阶段#n放弃的话，今日将无法再次挑战，是否确认放弃?", "restTttTp");
					} else {
						// 放弃该任务
						GameUtil.confirm(chara, "放弃该任务，今日再次挑战将从初始层开始，是否确认放弃?", "restTtt");
					}
				}
			} else if ("千变万化".equals(para1)) {
				Map<String, Vo_61553_0> taskMap = chara.taskMap;
				Vo_61553_0 vo_61553_0 = taskMap.get("千变万化");
				// 变身卡过期
				vo_61553_0.task_prompt = "";
				chara.changeCardInfo = null;
				// 刷新地图数据
				Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
				gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
				// 刷新任务
				GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_0, chara.id);
				GameUtil.sendMeTips("已清除变身效果");
				GameUtilRenWu.removeTask("千变万化", chara);
				GameUtil.sendUpdate(chara);
			} else if ("为民除暴".equals(para1)) {
				List<Vo_APPEAR> npcchubao = chara.npcchubao;
				for (Vo_APPEAR v : npcchubao) {
					v.isHide = 1;
					GameLine.getGameMap(chara.line, v.mapid).send(new M65529_0(), v);
				}
				npcchubao.clear();
				GameUtilRenWu.removeTask("为民除暴", chara);
				GameUtil.sendMeTips("你已放弃除暴任务。");
			}else if("师门任务".equals(para1)) {
				GameUtilRenWu.removeTask("师门任务", chara);
				GameUtil.sendMeTips("你已放弃师门任务。");
			}else if("悬赏任务".equals(para1)) {
				GameUtilRenWu.removeTask("悬赏任务", chara);
				GameUtil.sendMeTips("你已放弃悬赏任务。");
			}else if("八仙梦境".equals(para1)) {
				GameUtilRenWu.removeTask("八仙梦境", chara);
				GameUtil.sendMeTips("你已放弃八仙梦境任务。");
				//当前用户在八仙地图里面
				switch (chara.mapName) {
				case "瑶池":
				case "桐柏山":
					gameObjectChar.chara.x = 48;
					gameObjectChar.chara.y = 53;
					GameLine.getGameMap(gameObjectChar.chara.line, 17000).join(gameObjectChar);
					break;
				}
			}else if("修炼".equals(para1)) {
				GameUtilRenWu.removeTask("修炼", chara);
				GameUtil.sendMeTips("你已放弃修行任务。");
			}else if("伏魔".equals(para1)) {
				GameUtilRenWu.removeTask("伏魔", chara);
				GameUtil.sendMeTips("你已放弃伏魔任务。");
				Iterator<Entry<Integer, Vo_APPEAR>> iterator = chara.shudao.entrySet().iterator();
				if(iterator.hasNext()) {
					Entry<Integer, Vo_APPEAR> next = iterator.next();
					if(next.getValue().leixing == 3) {
						iterator.remove();
					}
				}
			}else if("降妖".equals(para1)) {
				GameUtilRenWu.removeTask("降妖", chara);
				GameUtil.sendMeTips("你已放弃降妖任务。");
				Iterator<Entry<Integer, Vo_APPEAR>> iterator = chara.shudao.entrySet().iterator();
				if(iterator.hasNext()) {
					Entry<Integer, Vo_APPEAR> next = iterator.next();
					if(next.getValue().leixing == 2) {
						iterator.remove();
					}
				}
			}else if("飞仙渡邪".equals(para1)) {
				GameUtilRenWu.removeTask("飞仙渡邪", chara);
				GameUtil.sendMeTips("你已放弃飞仙渡邪任务。");
				Iterator<Entry<Integer, Vo_APPEAR>> iterator = chara.shudao.entrySet().iterator();
				if(iterator.hasNext()) {
					Entry<Integer, Vo_APPEAR> next = iterator.next();
					if(next.getValue().leixing == 4) {
						iterator.remove();
					}
				}
			}else if("十绝阵".equals(para1)) {
				GameUtilRenWu.removeTask("十绝阵", chara);
				GameUtil.sendMeTips("你已放弃十绝阵任务。");
			}else if("提亲".equals(para1)) {
				GameUtil.confirm(chara, "你确定放弃#R提亲#n任务？", "delTiQi");
			}else if("宠物飞升".equals(para1)) {
				GameUtilRenWu.removeTask("宠物飞升", chara);
				chara.flyPetID = 0;
				GameUtil.sendMeTips("你已放弃宠物飞升任务。");
			}else if("门派转换".equals(para1)) {
				GameUtilRenWu.removeTask("门派转换", chara);
				GameUtil.sendMeTips("你已放弃门派转换任务。");
			}else if("修法".equals(para1)) {
				GameUtilRenWu.removeTask("修法", chara);
				GameUtil.sendMeTips("你已放弃修法任务。");
			}else if("萝卜桃子大收集".equals(para1)) {
				GameUtilRenWu.removeTask("萝卜桃子大收集", chara);
				GameUtil.sendMeTips("你已放弃萝卜桃子大收集任务。");
			}
			return;
		}

		// 查询试道大会信息
		if (type == ClientButtonIdConst.NOTIFY_QUERY_SHIDAO_INFO) {
			GameShiDao.notifyShiDaoInfo3();
		}
		// 通天塔挑战下一层
		if (type == ClientButtonIdConst.NOTIFY_TTT_GO_NEXT_LAYER) {
			GameActiveUtil.tongtiantaGoNextLayer();
		}
		// 通天塔快速飞升 金钱
		if (type == ClientButtonIdConst.NOTIFY_TTT_KUAISU_FEISHENG) {
			int jumpCount = Integer.valueOf(para1);
			int costCount = 800000 * (jumpCount - 1);
			if (jumpCount > 4) {
				// 320万文钱
				costCount = 3200000;
			}
			if(jumpCount>5) {
				//金钱最多飞升5层
				GameUtil.sendMeTips("金钱最多一次性飞升5层！");
				return;
			}
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
//			Vo_TONGTIANTA_JUMP jump = new Vo_TONGTIANTA_JUMP();
//			jump.setCostType(2);
//			jump.setCostCount(costCount);
//			jump.setJumpCount(jumpCount);
//			GameObjectChar.send(new MSG_TONGTIANTA_JUMP(), jump);
			// 设置飞升消耗的金钱
			chara.tongtiantaTask.setFeishengMoney(costCount);
			// 飞升层数
			chara.tongtiantaTask.setFeishengNumber(jumpCount);
			gameObjectChar.confirmData = 2;
			GameUtil.confirm(chara, "是否消耗"+GameCommonUtil.getMoneyDes(costCount)+"#n文钱，飞升至#R"+(chara.tongtiantaTask.getCurLayer()+jumpCount)+"#n层？", "NOTIFY_TTT_JISU_FEISHENG", 30);
		}
		// 通天塔急速飞升 元宝
		if (type == ClientButtonIdConst.NOTIFY_TTT_JISU_FEISHENG) {
			// 计算飞升元宝数量
			int jumpCount = Integer.valueOf(para1);
			int costCount = 90;
			if (jumpCount > 5) {
				costCount = 180;
			}
			if(jumpCount>10) {
				//元宝最多飞升5层
				GameUtil.sendMeTips("元宝最多一次性飞升10层！");
				return;
			}
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
//			Vo_TONGTIANTA_JUMP jump = new Vo_TONGTIANTA_JUMP();
//			jump.setCostType(1);
//			jump.setCostCount(costCount);
//			jump.setJumpCount(jumpCount);
//			GameObjectChar.send(new MSG_TONGTIANTA_JUMP(), jump);
			// 设置飞升消耗的元宝
			chara.tongtiantaTask.setFeishengMoney(costCount);
			// 飞升层数
			chara.tongtiantaTask.setFeishengNumber(jumpCount);
			gameObjectChar.confirmData = 1;
			GameUtil.confirm(chara, "是否消耗#R"+costCount+"#n元宝，飞升至#R"+(chara.tongtiantaTask.getCurLayer()+jumpCount)+"#n层？", "NOTIFY_TTT_JISU_FEISHENG", 30);
		}
		// 通天塔飞升确认
		if (type == ClientButtonIdConst.NOTIFY_TTT_JUMP_ASSURE) {
			// 1元宝 2金钱
			int feishengType = Integer.valueOf(para1);
			String feishengTypeName = "";
			if (feishengType == 1) {
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
			GameUtil.sendMeTips("你消耗了#R" + chara.tongtiantaTask.getFeishengMoney() + "#n" + feishengTypeName + "，飞升至#R"
					+ (chara.tongtiantaTask.getCurLayer() + 1) + "#n层");
			// 只有当突破的时候
			if (chara.tongtiantaTask.getChallengeCount() > 0) {
				chara.tongtiantaTask.setHasNotCompletedSmfj(1);
			}
			chara.tongtiantaTask.setFeishengMoney(0);
			// 开始分配任务
			GameActiveUtil.tongtiantaGoNextLayer();
			return;
		}
		// 离开通天塔
		if (type == ClientButtonIdConst.NOTIFY_TTT_LEAVE_TOWER) {
			com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName("天墉城");
			// 将人物传送到北斗星使处
			chara.x = 114;
			chara.y = 16;
			GameLine.getGameMapname(chara.line, map.getName()).join(gameObjectChar);
			return;
		}
		// 自动加点
		if (26 == type) {
			Map<String, Object> userAutoAddPoint = null;
			if ("".equals(para1)) {
				// 请求打开人物自动加点
				GameUtil.openDlg("UserAutoAddPointDlg");
				userAutoAddPoint = chara.getUserAutoAddPoint();
			} else {
				userAutoAddPoint = chara.getPetAutoAddPoint();
			}
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			byte con = MapUtils.getByteValue(userAutoAddPoint, "con");
			byte wiz = MapUtils.getByteValue(userAutoAddPoint, "wiz");
			byte str = MapUtils.getByteValue(userAutoAddPoint, "str");
			byte dex = MapUtils.getByteValue(userAutoAddPoint, "dex");
			byte autoAdd = MapUtils.getByteValue(userAutoAddPoint, "auto_add");
			byte plan = MapUtils.getByteValue(userAutoAddPoint, "plan");
			map.put("id", chara.id);
			map.put("con", con);
			map.put("wiz", wiz);
			map.put("str", str);
			map.put("dex", dex);
			map.put("auto_add", autoAdd);
			map.put("plan", plan);
			GameObjectChar.send(new CommonWrite(0x2295), map);
		}
		// 自动相性加点
		if (44 == type) {
			if ("".equals(para1)) {
//				GameUtil.openDlg("PolarAutoAddPointDlg");
				GameUtil.sendTips("正在开发...");
				return;
			}
		}
		// 购买双倍
		if (ClientButtonIdConst.NOTIFY_BUY_DOUBLE_POINTS == type) {
			chara.enable_double_points += Integer.valueOf(para1) * 200;
			GameUtil.sendMeTips(
					"花费了#R" + Integer.valueOf(para1) * 108 + "#W元宝购买了#R" + Integer.valueOf(para1) * 200 + "#W点双倍点数。");
			chara.silverCoin -= Integer.valueOf(para1) * 108;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
		}
		// 购买神木鼎
		if (10010 == type) {
			chara.enable_shenmu_points += Integer.valueOf(para1) * 1000;
			GameUtil.sendMeTips(
					"花费了#R" + Integer.valueOf(para1) * 328 + "#W元宝购买了#R" + Integer.valueOf(para1) * 1000 + "#W点双倍点数。");
			chara.goldCoin -= Integer.valueOf(para1) * 328;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
		}
		// 副本
		if (30000 == type) {
			String para = "";
			Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
			if (para1.matches("黑风洞")) {
				// 校验等级
				if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 30)) {
					GameUtil.sendMeTips("队伍中有低于30级的成员,无法挑战");
					return;
				}
				para = "黑风洞一层";
			} else if (para1.equals("兰若寺")) {
				// 校验等级
				if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 75)) {
					GameUtil.sendMeTips("队伍中有低于75级的成员,无法挑战");
					return;
				}
				para = "兰若寺后山1";
			} else if (para1.equals("烈火涧")) {
				// 校验等级
				if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 90)) {
					GameUtil.sendMeTips("队伍中有低于90级的成员,无法挑战");
					return;
				}
				para = "烈火涧1";
			} else if (para1.equals("飘渺仙府")) {
				if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 110)) {
					GameUtil.sendMeTips("队伍中有低于110级的成员,无法挑战");
					return;
				}
				para = "飘渺仙府";
			}
			// 判断人数
			if (gameObjectChar.gameTeam.duiwu.size() < 1) {
				GameUtil.sendMeTips("#R" + para1 + "#W中妖怪众多，少于3人的队伍进去是非常危险的。");
				return;
			}
			for (Chara c : gameObjectChar.gameTeam.duiwu) {
				if (c.fb_num >= GameConfig.config.getFb().getFbNumber()) {
					GameUtil.sendMeTips("队伍中#Y" + c.name + "#n今日已完成了副本挑战");
					return;
				}
			}
			vo_9129_0.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
			vo_9129_0.para = "DugeonCreateDlg";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
			GameUtil.enterDugeno(chara, para);
		}

		if (30020 == type) {
			if (PetFlyMgr.isTongGuoKaoYan(chara)) {
				PetFlyMgr.sendPetUpgradedInfo(chara);
			} else {
				if (chara.taskMap.get("宠物飞升") == null) {
					PetFlyMgr.onChosePetFly(chara, Integer.valueOf(para1).intValue());
					chara.current_task = "宠物飞升";
				} else {
					GameUtil.changeNpcSession(1181, 6041, "灵兽异人", "当前已有宠物飞升任务,请去完成[离开]");
				}
			}
		}

		// 30024 角色锁经验
		if (type == 30024) {
			if (Integer.valueOf(para1) != 0) {
				// 宠物锁定经验
			} else {
				if (chara.lock_exp == 0) {
					chara.lock_exp = 1;
					Vo_8165_0 vo_8165_2 = new Vo_8165_0();
					vo_8165_2.msg = "成功#R锁定#n经验";
					vo_8165_2.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_2);
					Vo_20480_0 vo_20480_0 = new Vo_20480_0();
					vo_20480_0.msg = "成功#R锁定#n经验";
					vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20480_0(), vo_20480_0);
					GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
				} else {
					chara.lock_exp = 0;
					Vo_8165_0 vo_8165_2 = new Vo_8165_0();
					vo_8165_2.msg = "成功#R解除#n经验锁定";
					vo_8165_2.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_2);
					Vo_20480_0 vo_20480_0 = new Vo_20480_0();
					vo_20480_0.msg = "成功#R解除#n经验锁定";
					vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20480_0(), vo_20480_0);
					GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
				}
				return;
			}
		}
		// type=30011 为清空队伍的邀请列表
		if (type == 30011) {
			gameObjectChar.invitationCharas = null;
		}

		// 30015为当用户在商城点击充值的时候触发，para1为第几个购买套餐
		if (type == 30015) {
			int[] money = {0,6,30,98,198,328,648};
			GameObjectChar.send(new M32853(), new Object[] {gameObjectChar.account.getName(),money[Integer.parseInt(para1)]});
			return;
		}
		//采集
		if (type == 20023) {
			// 使用一张藏宝图
			Vo_AUTO_CANGBAOTU_READY_SEARCH cangbao = new Vo_AUTO_CANGBAOTU_READY_SEARCH();
			cangbao.setPara(gameObjectChar.getGatherType());
			cangbao.setTips("是否在次使用藏宝图？");
			cangbao.setHasSgyw(0);
			if ("chaoji_goon".equals(gameObjectChar.getGatherType()) && chara.taskMap.get("超级宝藏") != null) {
				gameObjectChar.setGatherType("");
				GameUtilRenWu.removeTask("超级宝藏", chara);
				// 随机选择藏宝图的奖励
				String[] strings = GameUtilRenWu.luckFindDraw(chara);
				if (strings[1].equals("上古")) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "大事不好了，放出了上古妖王。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					String msg = GameShuaGuai.shangguWithLevel(chara.name, chara.level);
					cangbao.setHasSgyw(1);
					cangbao.setShangguAutoDesc(msg);
					cangbao.setTips(msg);
					gameObjectChar.confirmData = msg;
					GameUtil.confirm(chara, "大事不好了，放出了上古妖王。", "前去讨伐", "继续挖宝", "wabao");
					return;
				} else if (strings[1].equals("万年")) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "大事不好了，放出了万年妖王。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					String msg = GameShuaGuai.wannianWithLevel(chara.name, chara.level);
					cangbao.setHasSgyw(1);
					cangbao.setTips(msg);
					cangbao.setShangguAutoDesc(msg);
					gameObjectChar.confirmData = msg;
					GameUtil.confirm(chara, "大事不好了，放出了万年妖王。", "前去讨伐", "继续挖宝", "wabao");
					return;
				} else if (strings[1].equals("鬼怪")) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "大事不好了，放出了鬼怪。";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_0);
					String msg = GameShuaGuai.guiguaiWithLevel(chara.name, chara.level);
					cangbao.setHasSgyw(1);
					cangbao.setTips(msg);
					cangbao.setShangguAutoDesc(msg);
					gameObjectChar.confirmData = msg;
					GameUtil.confirm(chara, "大事不好了，放出了鬼怪。", "前去讨伐", "继续挖宝", "wabao");
					return;
				} else {
					GameUtil.huodechoujiang(strings, gameObjectChar, "挖宝");
					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = org.apache.commons.lang3.StringUtils.join("喜从天降,恭喜#Y", chara.name, "#n在高级挖宝中获得#R",
							strings[1], "#n ");
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);

					Vo_20480_0 vo_20480_0 = new Vo_20480_0();
					vo_20480_0.msg = org.apache.commons.lang3.StringUtils.join("喜从天降,恭喜#Y", chara.name, "#n在高级挖宝中获得#R",
							strings[1], "#n ");
					vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20480_0(), vo_20480_0);
					if (!strings[1].equals("金币")) {
						GameUtil.sendSystemMessage(6, org.apache.commons.lang3.StringUtils.join("喜从天降,恭喜#Y", chara.name,
								"#n在高级挖宝中获得#R", strings[1], "#n "));
					}
				}
			} else if ("teji_goon".equals(gameObjectChar.getGatherType()) && chara.taskMap.get("特级宝藏") != null) {
				gameObjectChar.setGatherType("");
				// 特级藏宝图
				GameUtilRenWu.removeTask("特级宝藏", chara);
				// 读取配置文件
			} else {
				GameCommonUtil.fuckBastard(gameObjectChar);
			}
			GameObjectChar.send(new MSG_AUTO_CANGBAOTU_READY_SEARCH(), cangbao);
			log.info("客户端请求采集结果");
		}
		// 宠风散
		if (type == 30046) {
			int state = Integer.valueOf(para1);
			if (state == 1) {
				if (chara.shuadaochongfeng_san <= 0) {
					state = 0;
					GameCommonUtil.dialogOk("当前宠风散点数不足。");
				} else {
					GameCommonUtil.dialogOk("成功开启宠风散功能。");
				}
			} else {
				GameCommonUtil.dialogOk("你已关闭宠风散功能。");
			}
			chara.chongfengsan = state;
			GameUtil.a45060(chara);
			return;
		}
		// 购买宠风散
		if (type == 30047) {

			// 购买类型
			int shoppingType = Integer.valueOf(para1);
			// 购买数量
			int num = Integer.valueOf(para2);
			int money = num * 216;
			if (shoppingType == 2) {
				// 元宝购买
				chara.silverCoin -= money;
			} else {
				if (chara.chongfengsanMoneyNum >= 3) {
					GameCommonUtil.dialogOk("今日金钱购买次数已使用完。");
					return;
				}
				// 金钱购买
				chara.cash -= 2160000;
				chara.chongfengsanMoneyNum++;
			}
			chara.shuadaochongfeng_san += num * 200;
			GameUtil.a45060(chara);
			GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
			GameCommonUtil.dialogOk("成功购买#R" + num * 200 + "#W点宠风散");
		}

		// 紫气鸿蒙
		if (type == 30048) {
			int state = Integer.valueOf(para1);
			if (state == 1) {
				if (chara.ziqihongmeng <= 0) {
					state = 0;
					GameCommonUtil.dialogOk("当前紫气鸿蒙点数不足。");
				} else {
					GameCommonUtil.dialogOk("成功开启紫气鸿蒙功能。");
				}
			} else {
				GameCommonUtil.dialogOk("你已关闭紫气鸿蒙功能。");
			}
			chara.ziqihongmengState = state;
			GameUtil.a45060(chara);
			return;
		}
		// 购买紫气鸿蒙点数
		if (type == 30049) {
			// 购买类型
			int shoppingType = Integer.valueOf(para1);
			// 购买数量
			int num = Integer.valueOf(para2);
			int money = num * 418;
			if (shoppingType == 2) {
				// 元宝购买
				chara.silverCoin -= money;
			} else {
				// 金钱购买
				chara.cash -= 4180000;
				chara.ziqihongmengMoneyNum++;
			}
			chara.ziqihongmeng += num * 200;
			GameUtil.a45060(chara);
			GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
			GameCommonUtil.dialogOk("成功购买#R" + num * 200 + "#W点紫气鸿蒙");
		}
		// 急急如律令开关
		if (ClientButtonIdConst.NOTIFY_SHUADAO_SET_JIJI == type) {
			int state = Integer.parseInt(para1);
			if (state == 1) {
				if (chara.jijirulvling <= 0) {
					state = 0;
					GameCommonUtil.dialogOk("当前急急如律令点数不足。");
				} else {
					GameCommonUtil.dialogOk("成功开启急急如律令。");
				}
			} else {
				GameCommonUtil.dialogOk("你已关闭急急如律令。");
			}
			chara.jijirulvlingState = state;
			GameUtil.a45060(chara);
		}

		// 购买急急如律令
		if (30045 == type) {
			int num = Integer.parseInt(para1);
			if (chara.silverCoin < num * 328) {
				GameCommonUtil.dialogOk("元宝不足无法购买。");
				return;
			}
			chara.silverCoin -= num * 328;
			chara.jijirulvling += num * 200;
			GameCommonUtil.dialogOk("你已成功购买#R" + num * 200 + "#W点急急如律令点数");
			GameObjectChar.send(new M65527_0(), GameUtil.a65527(chara));
		}

		// 打开刷道界面
		if (type == 30002) {
			// 如意刷道令
			Vo_45319_0 vo_45319_0 = new Vo_45319_0();
			vo_45319_0.state = chara.ruyishuadaoState;
			GameObjectChar.send(new MSG_REFRESH_RUYI_INFO(), vo_45319_0);
			// 刷道信息
			GameUtil.a45060(chara);
			// 刷道积分
			GameObjectChar.send(new M45217_0(), new Integer[] { chara.shuadaoScore, chara.shuadaoFetchState, 1 });
			return;
		}

		// 使用了双倍
		if (type == 52) {
			int state = Integer.valueOf(para1);
			if (state == 1) {
				if (chara.enable_double_points <= 0) {
					state = 0;
					GameCommonUtil.dialogOk("当前双倍点数不足。");
				} else {
					GameCommonUtil.dialogOk("成功开启双倍点数。");
				}
			} else {
				GameCommonUtil.dialogOk("成功关闭双倍点数，双倍点数将不再消耗。");
			}
			chara.charashuangbei = state;
			ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), vo_65527_0);
			return;
		}
		if (type == 10009) {
			int state = Integer.valueOf(para1);
			if (state == 1) {
				if (chara.enable_shenmu_points <= 0) {
					state = 0;
					GameCommonUtil.dialogOk("当前神木鼎点数不足。");
				} else {
					GameCommonUtil.dialogOk("成功开启神木鼎。");
				}
			} else {
				GameCommonUtil.dialogOk("成功关闭神木鼎。");
			}
			chara.shenmoding = state;
			ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), vo_65527_0);
			return;
		}
		// 驱魔香开关
		if (type == 20009) {
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "成功关闭驱魔香，在练功区走动时将会遇怪。";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
			Vo_GENERAL_NOTIFY vo_9129_53 = new Vo_GENERAL_NOTIFY();
			vo_9129_53.notify = 20010;
			vo_9129_53.para = "0";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_53);
			chara.qumoxiang = 0;
		}
		if (type == 20008) {
			Vo_8165_0 vo_8165_2 = new Vo_8165_0();
			vo_8165_2.msg = "成功开启驱魔香，在练功区走动时将无法遇怪。";
			vo_8165_2.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_2);
			Vo_GENERAL_NOTIFY vo_9129_53 = new Vo_GENERAL_NOTIFY();
			vo_9129_53.notify = 20010;
			vo_9129_53.para = "1";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_53);
			chara.qumoxiang = 1;
		}
		// 使用改名卡
		if (type == 1) {
			if(para1.contains("�")) {
				GameCommonUtil.dialogOk("昵称只允许数字、中文、字母");
				return;
			}
			if (GameData.that.baseCharactersService.findOneByName(para1) != null) {
				Vo_8165_0 vo_8165_2 = new Vo_8165_0();
				vo_8165_2.msg = "该名字已有人使用";
				vo_8165_2.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_2);
				return;
			}
			Pattern p = Pattern.compile(GameCommonUtil.filterStr);
			Matcher m = p.matcher(para1);
			if (m.find()) {
				GameCommonUtil.dialogOk("昵称只允许数字、中文、字母");
				return;
			} else if (para1.length() < 2) {
				GameCommonUtil.dialogOk("昵称应在2-12个字符");
				return;
			}else {
				for(char ch:para1.toCharArray()) {
					if(org.apache.commons.lang3.StringUtils.isBlank(ch+"")) {
						GameCommonUtil.dialogOk("昵称只允许数字、中文、字母");
						return;
					}
				}
			}
			//判断是否为gm
			if(para1.toUpperCase().indexOf("GM")!=-1) {
				if(gameObjectChar.privilege == 0) {
					GameCommonUtil.dialogOk("违规昵称！");
					return;
				}
			}
			String filterText = GameConfig.config.getMingan().getSettings().getFilterNickText();
			if(filterText.isEmpty()) {
				for(String ft:filterText.split("、")) {
					String name = para1.toUpperCase();
					Pattern p2 = Pattern.compile(".*"+ft+".*");
					Matcher m2 = p2.matcher(name);
					boolean isValid = m2.matches();
					if(isValid) {
						GameCommonUtil.dialogOk("该昵称被系统禁用");
						return;
					}
				}
			}
			int removemunber = GameUtil.removemunber(chara, "改头换面卡", 1);
			if(removemunber<0) {
				GameUtil.sendMeTips("改名卡不足");
				return;
			}
			//新增改名记录
			GameCommonUtil.addCharaTrail(chara, "改名", chara.name+"->"+para1, "改名卡");
			//发送系统通知
			GameUtil.sendSystemMessage(7, org.apache.commons.lang3.StringUtils.join("#Y",chara.name+"#n成功改名为#Y",para1));
			chara.name = para1;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			Vo_8165_0 vo_8165_3 = new Vo_8165_0();
			vo_8165_3.msg = "修改成功";
			vo_8165_3.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_3);
			// 更新信息
			Characters updateCharacters = new Characters();
			updateCharacters.setId(chara.id);
			updateCharacters.setName(para1);
			gameObjectChar.characters.setName(para1);
			GameData.that.baseCharactersService.updateById(updateCharacters);
			//如果有帮派的话
			if(!StringUtils.isNullOrEmpty(chara.getPartyName())) {
				//更新他在帮派的信息
				PartyMember pm = new PartyMember();
				pm.setName(para1);
				Example example = new Example(PartyMember.class);
				example.createCriteria().andEqualTo("charaId", chara.id);
				GameData.that.partyMemberService.updateByExampleSelective(pm, example);
				//如果是职位是帮主或者副帮主
				if(!chara.getPartyJob().equals("帮众") && !chara.getPartyJob().equals("帮派精英")
						&& !chara.getPartyJob().equals("传位")) {
					//更新帮派信息.
					Party party = GameData.that.partyService.findByPartyName(chara.getPartyName());
					//防止帮主解散帮派出现空指针
					if(party != null) {
						String leader = party.getLeader();
						List<Leader> jsonArray = JSONObject.parseArray(leader, Leader.class);
						for(Leader l:jsonArray) {
							//如果当前职位和gid相同则修改
							if(l.getJob().equals(chara.partyJob) && l.getGid().equals(chara.uuid)) {
								l.setName(para1);
								break;
							}
						}
						//更新帮派信息
						Party update = new Party();
						update.setId(party.getId());
						update.setLeader(JSONObject.toJSONString(jsonArray));
						GameData.that.partyService.updateByPrimaryKeySelective(update);
					}
				}
			}
			//如果有队伍的话
			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
					if(teamChara.id == chara.id) {
						teamChara.name =  para1;
					}
				}
				for(Vo_4121_0 v:gameObjectChar.gameTeam.zhanliduiyuan) {
					if(v.id == chara.id) {
						v.str = para1;
					}
				}
				// 队伍信息
				GameUtil.a4119(gameObjectChar.gameTeam.duiwu);
				// 右侧队伍列表信息
				GameUtil.a4121(gameObjectChar.gameTeam.zhanliduiyuan);
			}
			return;
		}
		// 改性别
		if (type == ClientButtonIdConst.NOTIFY_CHAR_CHANGE_SEX) {
			List<Goods> goods = chara.otherGoods;
			int sex = chara.sex == 1 ? 2 : 1;
			// 判断是否有穿戴时装.如若有则提示先卸下时装.
			if (chara.special_icon != 0) {
				GameUtil.sendMeTips("请先卸下时装,在更改性别.");
				return;
			}
			//如果已经结婚了
			if(chara.marriageMarryId != 0) {
				GameUtil.sendMeTips("请先离婚在变性");
				return;
			}
			for (Goods g : goods) {
				if (g.pos == 2) {
					// 对帽子进行性别转换
					ZhuangbeiInfo zb = GameData.that.baseZhuangbeiInfoService.getOneZbInfo(sex,
							GameCommonUtil.getZbLevel(g.goodsInfo.attrib), g.pos);
					g.goodsInfo.master = sex;
					g.goodsInfo.str = zb.getStr();
					g.goodsInfo.type = zb.getType();
				} else if (g.pos == 3) {
					// 带有衣服进行性别转换
					ZhuangbeiInfo zb = GameData.that.baseZhuangbeiInfoService.getOneZbInfo(sex,
							GameCommonUtil.getZbLevel(g.goodsInfo.attrib), g.pos);
					g.goodsInfo.master = sex;
					g.goodsInfo.str = zb.getStr();
					g.goodsInfo.type = zb.getType();
				}
			}
			// 更改玩家性别
			chara.sex = sex;
			chara.waiguan = GameUtil.getWaiguan(chara.polar, sex, chara);
			// 移除改名卡
			GameUtil.removemunber(chara, "改头换面卡", 1);
			// 更新角色头像状态
			GameObjectChar.send(new M65529_0(), GameUtil.a65529(chara));
			GameUtil.a65511(gameObjectChar);

			Vo_8165_0 vo_8165_3 = new Vo_8165_0();
			vo_8165_3.msg = "修改成功";
			vo_8165_3.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_3);
			return;
		}
		// 当查看别人面板的时候调用这里
		if (type == 40005) {
			Chara showChara = null;
			GameObjectChar showGameObject = GameObjectCharMng.getGameObjectCharByUUid(para1);
			// 用户不在线去数据库查询.
			List<Goods> showEqiup = new ArrayList<>();
			if (showGameObject == null) {
				Characters characters = GameData.that.baseCharactersService.findOneByGidSelectProperties(para1, "data", "level","portrait","backpack");
				if (characters != null) {
					String data = characters.getData();
					String backpack = characters.getBackpack();
					if(backpack != null) {
						showChara = JSONObject.parseObject(data, Chara.class);
						showChara.level = characters.getLevel();
						showChara.waiguan = characters.getPortrait();
						List<Goods> parseArray = JSONArray.parseArray(backpack, Goods.class);
						for(Goods g:parseArray) {
							if(g.pos>1 && g.pos<=40) {
								showEqiup.add(g);
							}
						}
					}
				}
			} else {
				showChara = showGameObject.chara;
				showEqiup = showChara.otherGoods;
			}
			if (showChara != null) {
				if (showChara.getSettings().get("refuse_look_equip") != null
						&& showChara.getSettings().get("refuse_look_equip") == 1
						&& gameObjectChar.privilege != 1000) {
					// 关闭装备查看
					GameCommonUtil.dialogOk("目标玩家已关闭装备查看。");
					return;
				}
				Vo_49153_0 vo_49153_0 = new Vo_49153_0();
				vo_49153_0.name = showChara.name;
				vo_49153_0.level = showChara.level;
				if (showChara.upgrade_state != 0) {
					vo_49153_0.level = showChara.realLevel;
				}
				vo_49153_0.icon = showChara.waiguan;
				vo_49153_0.special_icon = showChara.special_icon;
				vo_49153_0.weapon_icon = showChara.weapon_icon;
				vo_49153_0.suit_icon = showChara.suit_icon;
				vo_49153_0.suit_effect = showChara.suit_light_effect;
				vo_49153_0.power = 0;
				vo_49153_0.partyName = showChara.getPartyName();
				vo_49153_0.fashionIcon = 0;
				vo_49153_0.upgradetype = showChara.upgrade_type;
				vo_49153_0.upgradelevel = showChara.upgrade_level;
				vo_49153_0.effect = showChara.effectIcons;
				vo_49153_0.customIcon = showChara.customIcon;
				for (int i = 0; i < showEqiup.size(); ++i) {
					if (showEqiup.get(i).pos <= 10 || showEqiup.get(i).pos == 40) {
						vo_49153_0.backpack.add(showEqiup.get(i));
					}
				}
				GameObjectChar.send(new M49153_0(), vo_49153_0);
			} else {
				GameCommonUtil.dialogOk("玩家不存在");
			}
		}
		// 移除妖石
		if (4 == type) {
			for (int j = 0; j < chara.pets.size(); ++j) {
				if (chara.pets.get(j).no == Integer.valueOf(para1)) {
					Petbeibao petbeibao = chara.pets.get(j);
					int wiz = 0;
					int parry = 0;
					int def = 0;
					int dex = 0;
					int mana = 0;
					int accurate = 0;
					for (int k = 0; k < petbeibao.petShuXing.size(); ++k) {
						if (petbeibao.petShuXing.get(k).str.equals(para2)) {
							PetShuXing petShuXing = petbeibao.petShuXing.get(k);
							wiz = petShuXing.wiz;
							parry = petShuXing.parry;
							def = petShuXing.def;
							dex = petShuXing.dex;
							mana = petShuXing.mana;
							accurate = petShuXing.accurate;
							petbeibao.petShuXing.remove(petbeibao.petShuXing.get(k));
						}
					}
					for (int k = 0; k < petbeibao.petShuXing.size(); ++k) {
						// 在宠物的基础信息里面操作
						if (petbeibao.petShuXing.get(k).no == 0) {
							PetShuXing petShuXing = petbeibao.petShuXing.get(k);
							petShuXing.wiz -= wiz;
							petShuXing.parry -= parry;
							petShuXing.def -= def;
							petShuXing.dex -= dex;
							petShuXing.mana -= mana;
							petShuXing.accurate -= accurate;
						}
					}

					List<Petbeibao> list = new ArrayList<>();
					list.add(chara.pets.get(j));
					GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					Vo_8165_0 vo_8165_4 = new Vo_8165_0();

					vo_8165_4.msg = "移除妖石成功！";
					vo_8165_4.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_4);
				}
			}
		}
		if (5 == type) {
			for (int j = 0; j < chara.pets.size(); ++j) {
				if (chara.pets.get(j).no == Integer.valueOf(para1)) {
					for (int m = 0; m < chara.pets.get(j).tianshu.size(); ++m) {
						if (chara.pets.get(j).tianshu.get(m).god_book_skill_name.equals(para2)) {
							Vo_12023_0 vo_12023_0 = chara.pets.get(j).tianshu.get(m);
							chara.pets.get(j).tianshu.remove(chara.pets.get(j).tianshu.get(m));
							List<Petbeibao> list2 = new ArrayList<>();
							list2.add(chara.pets.get(j));
							GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
							boolean isfagong = chara.pets.get(j).petShuXing.get(0).rank > chara.pets.get(j).petShuXing
									.get(0).pet_mag_shape;
							GameUtil.dujineng(1, chara.pets.get(j).petShuXing.get(0).metal,
									chara.pets.get(j).petShuXing.get(0).skill, isfagong, chara.pets.get(j).id, chara, chara.pets.get(j));
							if (chara.pets.get(j).tianshu.size() == 0) {
								Vo_12023_0 vo_12023_02 = new Vo_12023_0();
								vo_12023_02.owner_id = chara.id;
								vo_12023_02.id = chara.pets.get(j).id;
								GameObjectChar.send(new M12023_1(), vo_12023_02);
							} else {
								GameObjectChar.send(new M12023_0(), chara.pets.get(j).tianshu);
							}
							StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(para2);
							//取出天书 天书放回书包
							Petbeibao petbeibao = chara.pets.get(j);
							if (vo_12023_0.name.contains("超级")) {
								//获取宠物属性list中 no等于 取出超级天书 type的那本天书的加成
								int wiz = vo_12023_0.wiz;
								int parry = vo_12023_0.parry;
								int def = vo_12023_0.def;
								int dex = vo_12023_0.dex;
								int mana = vo_12023_0.mana;
								int accurate = vo_12023_0.accurate;
								StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName(vo_12023_0.name);
								Goods goods = new Goods();
								goods.pos = 0;
								goods.goodsInfo = new GoodsInfo();
								goods.goodsDaoju(storeInfo);
								goods.goodsInfo.degree_32 = 0;
								goods.goodsInfo.skill = 1;
								goods.goodsInfo.owner_id = 1;
								goods.goodsInfo.damage_sel_rate = 400976;
								goods.goodsInfo.silver_coin = 6000;
								goods.goodsInfo.degree_32 = 0; // 【重要】妖石也是已鉴定
								goods.goodsInfo.amount = 0;
								goods.goodsLanSe = new GoodsLanSe();
								goods.goodsLanSe.def = def;
								goods.goodsLanSe.dex = dex;
								goods.goodsLanSe.accurate = accurate;
								goods.goodsLanSe.mana = mana;
								goods.goodsLanSe.parry = parry;
								goods.goodsLanSe.wiz = wiz;

								GameUtil.addwupin(goods, chara);
								GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
								//移除天书加成
								for (int i = 0; i < petbeibao.petShuXing.size(); i++) {
									PetShuXing p  = petbeibao.petShuXing.get(i);
									if(p.no == vo_12023_0.type){
										petbeibao.petShuXing.remove(i);
										BasicAttributesUtils.petshuxing(petbeibao.petShuXing.get(0),petbeibao);
										List<Petbeibao> list = new ArrayList<>();
										list.add(petbeibao);
										GameObjectChar.send(new MSG_UPDATE_PETS(), list);
									}
								}
							}else{
								GameUtil.huodedaoju(gameObjectChar, info, 1);
							}
							Vo_20481_0 vo_20481_4 = new Vo_20481_0();
							vo_20481_4.msg = "你的宠物#Y" + chara.pets.get(j).petShuXing.get(0).str + "#n成功取出了天书散卷#R"
									+ para2 + "#n。";
							vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
							GameObjectChar.send(new M20481_0(), vo_20481_4);
							break;
						}
					}
				}
			}
			return;
		}
		if (40013 == type) {
			if(chara.xinshoulibao[Integer.parseInt(para1)]  == 1) {
				GameUtil.sendTips("你已领取该奖励,请勿重复领取");
				return;
			}
			int attrib = (Integer.parseInt(para1) + 1) * 10;
			String[] strings2 = NoviceGiftBagUtils.giftBags(attrib, chara.sex, chara.polar);
			chara.xinshoulibao[Integer.parseInt(para1)] = 1;
			GameUtil.a49171(chara);
			for (int l = 0; l < strings2.length; ++l) {
				String[] split = strings2[l].split("\\#");
				Vo_8165_0 vo_8165_5 = new Vo_8165_0();
				vo_8165_5.msg = "你获得了#R" + split[0];
				vo_8165_5.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_5);
				Vo_20480_0 vo_20480_2 = new Vo_20480_0();
				vo_20480_2.msg = "你获得了#R" + split[0];
				vo_20480_2.time = (int) System.currentTimeMillis();
				GameObjectChar.send(new M20480_0(), vo_20480_2);
				GameUtil.huodechoujiang(split, gameObjectChar, "新手礼包");
			}
		}
		if (40014 == type) {
			GameUtil.a49171(chara);
		}
		// 自动战斗的开关，只要点击自动或者关闭自动都会触发
		if (37 == type) {
			gameObjectChar.isBack.set(false);
			chara.autofight_select = Integer.valueOf(para1);
			for(Petbeibao pet:chara.pets) {
				FightObject fightObject = FightManager.getFightObject(pet.id);
				if(fightObject != null) {
					//如果是自动的话
					if(chara.autofight_select == 1) {
						//判断是否自动过技能
						if(fightObject.autofight_skillaction == 0) {
							//设置为防御
							fightObject.autofight_skillaction = 1;
							//为0
							fightObject.autofight_skillno = 0;
						}
					}
					fightObject.autofight_select = chara.autofight_select;
				}
				//如果是自动的话
				if(chara.autofight_select == 1) {
					//判断是否自动过技能
					if(pet.autofight_skillaction == 0) {
						//设置为防御
						pet.autofight_skillaction = 1;
						//为0
						pet.autofight_skillno = 0;
					}
				}
				pet.autofight_select = chara.autofight_select;
			}
			if(chara.autofight_skillaction == 0) {
				//设置为防御
				chara.autofight_skillaction = 1;
				//为0
				chara.autofight_skillno = 0;
			}
			//
//			if (chara.autofight_select == 0) {
//				return;
//			}
//			FightContainer fightContainer = FightManager.getFightContainer();
//			if(fightContainer == null) {
//				return;
//			}
			FightObject fightObject = FightManager.getFightObject(chara.id);
			if (fightObject == null) {
				return;
			}
			fightObject.autofight_select = chara.autofight_select;
			//
//			if (fightContainer.state.get()==3 || fightContainer.state.get()==4) {
//				return;
//			}
			//加载战斗信息
			GameCommonUtil.fightCmdInfo(gameObjectChar);
			//为空的时候才允许战斗
//			FightManager.doAutoSkill(fightContainer);
		}
		if (10007 == type) {
			if (para1.equals("1")) {
				Chara chara2 = chara;
				chara2.extra_mana += 300000;
				if (chara.extra_mana > 90000000) {
					chara.extra_mana = 90000000;
				}
				GameUtil.removemoney(chara, 120000);
			}
			if (para1.equals("2")) {
				Chara chara3 = chara;
				chara3.have_coin_pwd += 300000;
				if (chara.have_coin_pwd > 90000000) {
					chara.have_coin_pwd = 90000000;
				}
				GameUtil.removemoney(chara, 360000);
			}
			if (para1.equals("3")) {
				Chara chara4 = chara;
				chara4.use_skill_d += 300000;
				if (chara.use_skill_d > 3000000) {
					chara.use_skill_d = 3000000;
				}
				GameUtil.removemoney(chara, 1800000);
			}
		}
		if (50007 == type) {
			int viptype = Integer.valueOf(para1);
			if (viptype == 1 && chara.vipyuanbaolingqu < 1) {
				++chara.vipyuanbaolingqu;
				chara.silverCoin += 100;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
			} else if (viptype == 2 && chara.vipyuanbaolingqu < 1) {
				++chara.vipyuanbaolingqu;
				chara.silverCoin += 120;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
			} else if (viptype == 3 && chara.vipyuanbaolingqu < 1) {
				++chara.vipyuanbaolingqu;
				chara.silverCoin += 150;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
			} else {
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "#R本服有防WPE系统，禁止使用WPE，违者封号！";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_2);
				return;
			}
			chara.isGet = 1;
			Vo_40964_0 vo_40964_18 = new Vo_40964_0();
			vo_40964_18.type = 4;
			vo_40964_18.name = "银元宝";
			vo_40964_18.param = "100";
			vo_40964_18.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_18);
			GameUtil.addVip(chara);
			return;
		}
		if (50006 == type) {
			if (chara.vipTimeShengYu <= 0) {
				chara.vipTimeShengYu = (int) (System.currentTimeMillis() / 1000L);
			}
			int viptype = Integer.valueOf(para1);
			if (viptype == 1) {
				chara.goldCoin -= 3000;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
				chara.vipTimeShengYu += 2592000;
			}
			if (viptype == 2) {
				chara.goldCoin -= 9000;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
				chara.vipTimeShengYu += 7776000;
			}
			if (viptype == 3) {
				chara.goldCoin -= 36000;
				ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_0);
				chara.vipTimeShengYu += 31104000;
			}
			if (chara.vipType == 1) {
				GameUtil.chenghaoxiaoxi(chara, "月卡", "位列仙班·灵识初开");
				Vo_20481_0 vo_20481_3 = new Vo_20481_0();
				vo_20481_3.msg = "你获得了#R位列仙班·灵识初开#n的称谓。";
				vo_20481_3.time = (int) (System.currentTimeMillis()/1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_3);
			}
			if (chara.vipType == 2) {
				GameUtil.chenghaoxiaoxi(chara, "季卡", "位列仙班·道法自然");
				Vo_20481_0 vo_20481_3 = new Vo_20481_0();
				vo_20481_3.msg = "你获得了#R位列仙班·道法自然#n的称谓。";
				vo_20481_3.time = (int) (System.currentTimeMillis()/1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_3);
			}
			if (chara.vipType == 3) {
				GameUtil.chenghaoxiaoxi(chara, "年卡", "位列仙班·大道无穷");
				Vo_20481_0 vo_20481_3 = new Vo_20481_0();
				vo_20481_3.msg = "你获得了#R位列仙班·大道无穷#n的称谓。";
				vo_20481_3.time = (int) (System.currentTimeMillis()/1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_3);
			}
			if(chara.vipType<viptype) {
				chara.vipType = viptype;
			}
			GameUtil.addVip(chara);
			gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(chara));
		}

		if (30010 == type) {
			for (int j = 0; j < chara.listshouhu.size(); ++j) {
				if (chara.listshouhu.get(j).id == Integer.parseInt(para2)) {
					Chara chara8 = chara;
					--chara8.canzhanshouhunumber;
					chara.listshouhu.get(j).listShouHuShuXing.get(0).salary = 0;
					if (chara.listshouhu.get(j).listShouHuShuXing.get(0).nil == 0) {
						chara.listshouhu.get(j).listShouHuShuXing.get(0).nil = 1;
					} else {
						chara.listshouhu.get(j).listShouHuShuXing.get(0).nil = 0;
					}
					List<ShouHu> list3 = new ArrayList<>();
					list3.add(chara.listshouhu.get(j));
					GameObjectChar.send(new M12016_0(), list3);
				}
				if (chara.listshouhu.get(j).id == Integer.parseInt(para1)) {
					if (chara.canzhanshouhunumber == 0) {
						chara.listshouhu.get(j).listShouHuShuXing.get(0).salary = 5;
						Chara chara9 = chara;
						++chara9.canzhanshouhunumber;
					} else {
						chara.listshouhu.get(j).listShouHuShuXing.get(0).salary = chara.canzhanshouhunumber;
						Chara chara10 = chara;
						++chara10.canzhanshouhunumber;
					}
					if (chara.listshouhu.get(j).listShouHuShuXing.get(0).nil == 0) {
						chara.listshouhu.get(j).listShouHuShuXing.get(0).nil = 1;
					} else {
						chara.listshouhu.get(j).listShouHuShuXing.get(0).nil = 0;
					}
					List<ShouHu> list3 = new ArrayList<>();
					list3.add(chara.listshouhu.get(j));
					GameObjectChar.send(new M12016_0(), list3);
				}
			}
			GameObjectChar.send(new M12016_0(), chara.listshouhu);
			List<Vo_45074_0> list4 = new ArrayList<Vo_45074_0>();
			for (int i2 = 0; i2 < chara.listshouhu.size(); ++i2) {
				if (chara.listshouhu.get(i2).listShouHuShuXing.get(0).nil != 0) {
					Vo_45074_0 vo_45074_0 = new Vo_45074_0();
					vo_45074_0.guardName = chara.listshouhu.get(i2).listShouHuShuXing.get(0).str;
					vo_45074_0.guardLevel = chara.level;
					vo_45074_0.guardIcon = chara.listshouhu.get(i2).listShouHuShuXing.get(0).type;
					vo_45074_0.guardOrder = chara.listshouhu.get(i2).listShouHuShuXing.get(0).salary;
					vo_45074_0.guardId = chara.listshouhu.get(i2).id;
					list4.add(vo_45074_0);
				}
			}
			GameObjectChar.sendduiwu(new M45074_0(), list4, chara.id);
			if (gameObjectChar.gameTeam != null
					&& gameObjectChar.gameTeam.duiwu != null) {
				for (int i2 = 0; i2 < gameObjectChar.gameTeam.duiwu.size(); ++i2) {
					GameObjectCharMng.getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(i2).id)
							.sendOne(new M45074_0(), list4);
				}
			}
		}
		if (8 == type) {
			for (int j = 0; j < chara.listshouhu.size(); ++j) {
				if (chara.listshouhu.get(j).id == Integer.parseInt(para1)) {
					chara.listshouhu.get(j).listShouHuShuXing.get(0).max_degree = Integer.parseInt(para2);
					List<ShouHu> list3 = new ArrayList<>();
					list3.add(chara.listshouhu.get(j));
					GameObjectChar.send(new M12016_0(), list3);
				}
			}
		}
		if (30006 == type) {
			if (Integer.parseInt(para1) >= 2001 && Integer.parseInt(para1) <= 2501) {
				// 删除卡套
				for (int i = 0; i < chara.cardStore.size(); i++) {
					Goods goods = chara.cardStore.get(i);
					if (goods.pos == Integer.parseInt(para1)) {
						if (goods.goodsInfo.owner_id == 1) {
							chara.cardStore.remove(goods);
							// 刷新仓库
							Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
							vo_61677_0.pos = goods.pos;
							GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_0);
						} else {
							goods.goodsInfo.owner_id -= 1;
							// 变身卡套
							Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
							vo_61677_0.list = chara.cardStore;
							GameObjectChar.send(new M61677_0(), vo_61677_0);
						}
						chara.use_money_type += goods.goodsInfo.rebuild_level / 5 * Integer.valueOf(para2);
						ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
						GameObjectChar.send(new M65527_0(), listVo_65527_2);
						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
						vo_20481_0.msg = "你成功出售" + goods.goodsInfo.str + "#n获得代金券#n。";
						vo_20481_0.time = (int) (System.currentTimeMillis()/1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_0);
						break;
					}
				}
			} else {
				for (int j = 0; j < chara.backpack.size(); ++j) {
					Goods goods = chara.backpack.get(j);
					if (goods.pos == Integer.parseInt(para1)) {
						GameUtil.removemunber(chara, goods, Integer.valueOf(para2), true);
						chara.use_money_type += goods.goodsInfo.rebuild_level / 5 * Integer.valueOf(para2);
						ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
						GameObjectChar.send(new M65527_0(), listVo_65527_2);
						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
						vo_20481_0.msg = "你成功出售" + goods.goodsInfo.str + "#n获得代金券#n。";
						vo_20481_0.time = (int) (System.currentTimeMillis()/1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_0);
						break;
					}
				}
			}
		}
		if (10002 == type) {
			//普通仓库
			if("1".equals(para1)) {
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.list = chara.cangku;
				GameObjectChar.send(new M61677_0(), vo_61677_0);
			}else if("2".equals(para1)) {
				//宠物仓库
				GameObjectChar.send(new MSG_PET_STORE(), chara.petStores);
			}else if("9".equals(para1)) {
				//太阴之气仓库
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.list = chara.tyzqStore;
				vo_61677_0.store_type = "tyzq_store";
				GameObjectChar.send(new M61677_0(), vo_61677_0);
			}
		}
		if (40022 == type) {
			if(chara.jishou_coin<=0) {
				GameUtil.sendMeTips("可提款金额为0");
				return;
			}
			GameUtil.addCash(gameObjectChar, chara.jishou_coin);
			Vo_40964_0 vo_40964_19 = new Vo_40964_0();
			vo_40964_19.type = 3;
			vo_40964_19.name = "金币";
			vo_40964_19.param = String.valueOf(chara.jishou_coin);
			vo_40964_19.rightNow = 1;
			GameObjectChar.send(new M40964_0(), vo_40964_19);
			chara.jishou_coin = 0;
			ListVo_65527_0 listVo_65527_3 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_3);
			List<SaleGood> saleGoodList = (List<SaleGood>) GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
			Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
			GameObjectChar.send(new M49179_0(), vo_49179_0);
			Vo_8165_0 vo_8165_5 = new Vo_8165_0();
			vo_8165_5.msg = "你提款了钱";
			vo_8165_5.active = 0;
			GameObjectChar.send(new M8165_0(), vo_8165_5);
			Vo_20480_0 vo_20480_2 = new Vo_20480_0();
			vo_20480_2.msg = "你提款了钱";
			vo_20480_2.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_2);
		}
		if (40016 == type) {
			int pos = GameUtil.packPoint(chara);
			if (pos == -1) {
				return;
			}
			SaleGood saleGood = GameData.that.saleGoodService.findOneByGoodsId(para1);
			if(saleGood != null) {
				if (saleGood.getType() == 1) {
					String goods2 = saleGood.getGoods();
					Goods goods3 = JSONObject.parseObject(goods2, Goods.class);
					goods3.pos = goods3.goodsInfo.owner_id = 1;
					GameUtil.addwupin(goods3, chara);
					GameData.that.saleGoodService.deleteById((int) saleGood.getId());

					Vo_40964_0 vo_40964_20 = new Vo_40964_0();
					vo_40964_20.type = 1;
					vo_40964_20.name = saleGood.getName();
					vo_40964_20.param = "32271173";
					vo_40964_20.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_20);

					Vo_20481_0 vo_20481_4 = new Vo_20481_0();
					vo_20481_4.msg = "你成功将#R" + saleGood.getName() + "#n撤摊了";
					vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_4);
				} else {
					String goods2 = saleGood.getGoods();
					Petbeibao petbeibao2 = JSONObject.parseObject(goods2, Petbeibao.class);
					// 添加宠物
					CharaPet charaPet = new CharaPet();
					charaPet.setAddTime(new Date());
					charaPet.setCid(chara.id);
					charaPet.setUuid(chara.uuid);
					charaPet.setPet(goods2);
					charaPet.setOwnerName(chara.name);
					charaPet.setPetName(petbeibao2.petShuXing.get(0).str);
					GameData.that.charaPetService.insertSelective(charaPet);
					petbeibao2.id = charaPet.getId();
					Vo_12269_0 vo_12269_0 = new Vo_12269_0();
					vo_12269_0.id = petbeibao2.id;
					vo_12269_0.owner_id = chara.id;
					GameObjectChar.send(new M12269_0(), vo_12269_0);
					Vo_40964_0 vo_40964_21 = new Vo_40964_0();
					vo_40964_21.type = 2;
					vo_40964_21.name = "";
					vo_40964_21.param = String.valueOf(petbeibao2.petShuXing.get(0).type);
					vo_40964_21.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_21);
					Vo_20481_0 vo_20481_4 = new Vo_20481_0();
					vo_20481_4.msg = "你成功将#R" + saleGood.getName() + "#n撤摊了";
					vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_4);
					List<Petbeibao> list6 = new ArrayList<>();
					list6.add(petbeibao2);
					GameObjectChar.send(new MSG_UPDATE_PETS(), list6);
					boolean isfagong2 = petbeibao2.petShuXing.get(0).rank > petbeibao2.petShuXing.get(0).pet_mag_shape;
					GameUtil.dujineng(1, petbeibao2.petShuXing.get(0).metal, petbeibao2.petShuXing.get(0).skill, isfagong2,
							petbeibao2.id, chara, petbeibao2);
					chara.pets.add(petbeibao2);
					GameData.that.saleGoodService.deleteById((int) saleGood.getId());
				}
				List<SaleGood> saleGoodList = (List<SaleGood>) GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
				Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
				GameObjectChar.send(new M49179_0(), vo_49179_0);
			}
		}
		if (40018 == type) {
			// 1;1;1;price
			String[] split2 = para2.split("\\;");
			// 页码
			int pageNum = Integer.parseInt(split2[0]);
			// 出售阶段 1 公示 2 逛摊(正常出售)
			int sellStage = Integer.parseInt(split2[1]);
			SaleGood where = new SaleGood();
			where.setStatus(sellStage);
			where.setAlias(para1);
			PageInfo<SaleGood> pageInfo = GameData.that.saleGoodService
					.findBySaleGoodPage(new Page<SaleGood>(pageNum, 15), where);
			List<SaleGood> saleGoodList2 = pageInfo.getList();
			Vo_49183_0 vo_49183_0 = new Vo_49183_0();
			vo_49183_0.totalPage = pageInfo.getPages();
			vo_49183_0.cur_page = pageInfo.getPageNum();
			for (int i = 0; i < saleGoodList2.size(); ++i) {
				Vo_49183 vo_49183 = new Vo_49183();
				vo_49183.name = saleGoodList2.get(i).getName();
				if (saleGoodList2.get(i).getName().contains("超级黑水晶·")) {
					SaleGood saleGood2 = saleGoodList2.get(i);
					String goods4 = saleGood2.getGoods();
					Goods goods5 = JSONObject.parseObject(goods4, Goods.class);
					Map<Object, Object> goodsFenSe1 = UtilObjMapshuxing.GoodsLanSe(goods5.goodsLanSe);
					int value = 0;
					for (Map.Entry<Object, Object> entry : goodsFenSe1.entrySet()) {
						if (!entry.getKey().equals("groupNo") && !entry.getKey().equals("groupType")) {
							if (entry.getValue().toString().equals("0")) {
								continue;
							}
							value = (int) entry.getValue();
							break;
						}
					}
					vo_49183.name = saleGoodList2.get(i).getName() + "|" + value + "|1";
				}
				vo_49183.is_my_goods = chara.uuid.equals(saleGoodList2.get(i).getGid()) ? 1 : 0;
				vo_49183.id = saleGoodList2.get(i).getGoodsId();
				vo_49183.price = saleGoodList2.get(i).getPrice();
				vo_49183.status = saleGoodList2.get(i).getStatus();
				vo_49183.startTime = saleGoodList2.get(i).getStartTime();
				vo_49183.endTime = saleGoodList2.get(i).getEndTime();
				vo_49183.level = saleGoodList2.get(i).getLevel();
				vo_49183.unidentified = saleGoodList2.get(i).getUnidentified();
				vo_49183.amount = 0;
				vo_49183.req_level = saleGoodList2.get(i).getReqLevel();
				vo_49183.extra = saleGoodList2.get(i).getExtra();
				vo_49183.item_polar = saleGoodList2.get(i).getItemPolar();
				vo_49183.icon = saleGoodList2.get(i).getIcon();
				vo_49183_0.vo_49183s.add(vo_49183);
			}
			vo_49183_0.path_str = para1;
			vo_49183_0.select_gid = "";
			vo_49183_0.sell_stage = sellStage;
			vo_49183_0.is_descending = 1;
			vo_49183_0.sort_key = "price";
			GameObjectChar.send(new M49183_0(), vo_49183_0);
		}
		if (40012 == type) {
		}

		// 重新摆摊
		if (40017 == type) {
			// 获取该商品信息
			SaleGood saleGood = GameData.that.saleGoodService.findOneByGoodsId(para1);
			int downGoodTimes = GameConfig.config.getMarketConfig().getDownGoodTimes() * 60;
			if (saleGood != null) {
				Example example = new Example(SaleClassifyGood.class);
				example.createCriteria().andEqualTo("id", saleGood.getSgId());
				SaleClassifyGood saleClassifyGood = GameData.that.baseSaleClassifyGoodService
						.selectOneByExample(example);
				int endTime = 0;
				// 如果需要公示
				if (saleClassifyGood.getPublicityTime() != null && saleClassifyGood.getPublicityTime() > 0) {
					log.info("需要公示-------商品名称={},商品id={}", saleGood.getName(), para1);
					saleGood.setStatus(1);
					// 公示需要的秒数
					int publicEndTimes = saleClassifyGood.getPublicityTime() * 60;
					endTime = (int) (System.currentTimeMillis() / 1000L) + publicEndTimes;
					// 公示结束后调用此方法,把物品修改为出售状态.
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							// 设置为出售状态,并且初始化开始时间和到期时间
							SaleGood up = new SaleGood();
							up.setStatus(2);
							up.setStartTime((int) (System.currentTimeMillis() / 1000L));
							up.setEndTime((int) (System.currentTimeMillis() / 1000L) + downGoodTimes);
							Example example = new Example(SaleGood.class);
							example.createCriteria().andEqualTo("goodsId", para1);
							GameData.that.saleGoodService.updateByExampleSelective(up, example);
							log.info("公示到期更新为出售状态-------商品名称={},商品id={}", saleGood.getName(), para1);
							// 货物只保存12小时
							new Timer().schedule(new TimerTask() {
								@Override
								public void run() {
									// 货物到期设置为到期
									SaleGood up = new SaleGood();
									up.setStatus(3);
									Example example = new Example(SaleGood.class);
									example.createCriteria().andEqualTo("goodsId", para1);
									GameData.that.saleGoodService.updateByExampleSelective(up, example);
									log.info("货物到期，下架-------商品名称={},商品id={}", saleGood.getName(), para1);
								}
							}, downGoodTimes * 1000);
						}
					}, publicEndTimes * 1000);
				} else {
					// 无需公示直接上架
					saleGood.setStatus(2);
					endTime = (int) (System.currentTimeMillis() / 1000L) + downGoodTimes;
					log.info("无需公示-------商品名称={},商品id={}", saleGood.getName(), para1);
					// 货物只保存12小时
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							// 货物时间到期,设置状态为到期
							SaleGood up = new SaleGood();
							up.setStatus(3);
							Example example = new Example(SaleGood.class);
							example.createCriteria().andEqualTo("goodsId", para1);
							GameData.that.saleGoodService.updateByExampleSelective(up, example);
							log.info("货物到期下架-------商品名称={},商品id={}", saleGood.getName(), para1);
						}
					}, downGoodTimes * 1000);
				}
				GameData.that.saleGoodService.updateByPrimaryKey(saleGood);
				log.info("结束时间:{}", endTime);
				List<SaleGood> saleGoodList = GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
				Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
				GameObjectChar.send(new M49179_0(), vo_49179_0);
			}
		}

		// 集市摆摊
		if (40015 == type) {
			List<SaleGood> saleGoodList = (List<SaleGood>) GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
			Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
			GameObjectChar.send(new M49179_0(), vo_49179_0);
			// 变身卡套
			Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
			vo_61677_0.list = chara.cardStore;
			GameObjectChar.send(new M61677_0(), vo_61677_0);
		}
		if (40010 == type) {
			if(chara.isCanSgin == 2) {
				GameUtil.sendMeTips("你今日已签到请勿重复签到！");
				return;
			}
			//查询签到奖励
			DaySignPrize daySignPrizeByDay = GameData.that.daySignPrizeService.getDaySignPrizeByDay(chara.signDays + 1);
			if(daySignPrizeByDay == null) {
				GameUtil.sendMeTips("签到失败，设置有误！");
				return;
			}
			
			switch (daySignPrizeByDay.getType()) {
				case "道具":
					GameUtil.huodedaoju(chara, daySignPrizeByDay.getName(), daySignPrizeByDay.getNum());
					GameUtil.sendMeTips("你获得了"+daySignPrizeByDay.getName());
					break;
				case "变身卡":
					GameUtil.huodedaoju(chara, daySignPrizeByDay.getName(), daySignPrizeByDay.getNum());
					GameUtil.sendMeTips("你获得了"+daySignPrizeByDay.getName());
					break;
				case "金元宝":
					GameUtil.addJinYuanBao(gameObjectChar, daySignPrizeByDay.getNum(), "每日签到");
					break;
				case "银元宝":
					GameUtil.addYinYuanBao(gameObjectChar, daySignPrizeByDay.getNum(), "每日签到");
					break;
				case "道行":
					GameUtil.adddaohang(chara, daySignPrizeByDay.getNum(),"每日签到");
					break;
				case "潜能":
					GameUtil.addQianNeng(chara, daySignPrizeByDay.getNum(),"每日签到");
					break;
				case "经验":
					GameUtil.huodejingyan(chara, daySignPrizeByDay.getNum(), "每日签到");
					break;
				case "积分":
					GameUtil.addchargeScore(gameObjectChar, daySignPrizeByDay.getNum(), "每日签到");
					break;
			}
			
			Vo_20480_0 vo_20480_3 = new Vo_20480_0();
			vo_20480_3.msg = "你领取了签到奖励。";
			vo_20480_3.time = (int) (System.currentTimeMillis()/1000L);
			GameObjectChar.send(new M20480_0(), vo_20480_3);
			chara.isCanSgin = 2;
			++chara.signDays;
			Vo_41051_0 vo_41051_0 = new Vo_41051_0();
			vo_41051_0.count = 1;
			vo_41051_0.name0 = "month_charge_gift";
			vo_41051_0.amount0 = 0;
			vo_41051_0.startTime0 = 1577825999;
			vo_41051_0.endTime0 = 1577825999;
			GameObjectChar.send(new M41051_0(), vo_41051_0);
			Vo_49169_0 vo_49169_0 = new Vo_49169_0();
			vo_49169_0.monthDays = 31;
			vo_49169_0.signDays = chara.signDays;
			vo_49169_0.isCanSgin = chara.isCanSgin;
			vo_49169_0.isCanReplenishSign = 0;
			List<SignDaysItem> items = new ArrayList<>();
			List<DaySignPrize> all = GameData.that.daySignPrizeService.getAll();
			for(DaySignPrize dsp:all) {
				items.add(new SignDaysItem(dsp.getName(),dsp.getNum()));
			}
			vo_49169_0.items = items;
			GameObjectChar.send(new MSG_DAILY_SIGN(), vo_49169_0);
			GameUtil.sendUpdate(chara);
			return;
		}
		if (40009 == type) {
			Vo_49169_0 vo_49169_2 = new Vo_49169_0();
			vo_49169_2.monthDays = 31;
			vo_49169_2.signDays = chara.signDays;
			vo_49169_2.isCanSgin = chara.isCanSgin;
			vo_49169_2.isCanReplenishSign = 0;
			List<SignDaysItem> items = new ArrayList<>();
			List<DaySignPrize> all = GameData.that.daySignPrizeService.getAll();
			for(DaySignPrize dsp:all) {
				items.add(new SignDaysItem(dsp.getName(),dsp.getNum()));
			}
			vo_49169_2.items = items;
			GameObjectChar.send(new MSG_DAILY_SIGN(), vo_49169_2);
		}
		if (6 == type) {
			PetHelpType petHelpType = GameData.that.basePetHelpTypeService.findOneByName(para1);
			int coin = petHelpType.getMoney();
			if (petHelpType.getQuality() == 3) {
				if (chara.goldCoin < coin) {
					Vo_20481_0 vo_20481_5 = new Vo_20481_0();
					vo_20481_5.msg = "金元宝不足";
					vo_20481_5.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_5);
					return;
				}
				Chara chara13 = chara;
				chara13.goldCoin -= coin;
			} else {
				if (chara.cash < coin) {
					Vo_20481_0 vo_20481_5 = new Vo_20481_0();
					vo_20481_5.msg = "金币不足";
					vo_20481_5.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_5);
					return;
				}
				chara.cash -= coin;
			}
			ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_2);
			ShouHu shouHu = new ShouHu();
			shouHu.id = GameUtil.getCard(chara);
			ShouHuShuXing shouHuShuXing = new ShouHuShuXing();
			int pos5 = petHelpType.getPolar();
			int pos6 = petHelpType.getQuality();
			Hashtable<String, int[]> stringHashtable = PetAttributesUtils.helpPet(pos6, pos5, chara.level);
			int[] attributes = stringHashtable.get("attribute");
			int[] polars = stringHashtable.get("polars");
			shouHuShuXing.life = attributes[0];
			shouHuShuXing.mag_power = attributes[1];
			shouHuShuXing.phy_power = attributes[2];
			shouHuShuXing.speed = attributes[3];
			shouHuShuXing.wood = polars[0];
			shouHuShuXing.water = polars[1];
			shouHuShuXing.fire = polars[2];
			shouHuShuXing.earth = polars[3];
			shouHuShuXing.resist_metal = polars[4];
			shouHuShuXing.skill = chara.level;
			shouHuShuXing.str = para1;
			shouHuShuXing.shape = 0;
			shouHuShuXing.penetrate = pos6;
			shouHuShuXing.metal = pos5;
			shouHuShuXing.color = pos6;
			shouHuShuXing.suit_polar = para1;
			shouHuShuXing.type = petHelpType.getType();
			int[] ints = BasicAttributesUtils.calculationHelpAttributes(chara.level, attributes[0], attributes[1],
					attributes[2], attributes[3], polars[0], polars[1], polars[2], polars[3], polars[4], pos5);
			shouHuShuXing.max_life = ints[0];
			shouHuShuXing.def = ints[0];
			shouHuShuXing.accurate = ints[2];
			shouHuShuXing.mana = ints[3];
			shouHuShuXing.parry = ints[4];
			shouHuShuXing.wiz = ints[5];
			shouHuShuXing.salary = 0;
			shouHu.listShouHuShuXing.add(shouHuShuXing);
			chara.listshouhu.add(shouHu);
			//如果有主线任务,并且是指引过来的设置为参战
			if("主线—拜入师门s13".equals(chara.current_task) && chara.taskMap.get("主线—拜入师门") != null) {
				String step = chara.taskMap.get("主线—拜入师门").task_state;
				if("3".equals(step)) {
					shouHuShuXing.salary = chara.canzhanshouhunumber;
					++chara.canzhanshouhunumber;
					shouHuShuXing.nil = 1;
					GameUtil.closeDlg("GuardAttribDlg");
					GameUtil.openDlg("GuardAttribDlg");
					//设置下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s14";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_zhanglao[chara.polar-1]));
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				}
			}
			List<ShouHu> list7 = new ArrayList<>();
			list7.add(shouHu);
			GameObjectChar.send(new M12016_0(), list7);
			Vo_20481_0 vo_20481_6 = new Vo_20481_0();
			vo_20481_6.msg = "#n召唤守护#Y" + para1 + "#n";
			vo_20481_6.time = (int) (System.currentTimeMillis()/1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_6);
			GameUtil.dujineng(2, pos5, shouHuShuXing.skill, true, shouHu.id, chara, null);
			return;
		}
		// 查询组队队伍信息
		if (10008 == type) {
			GameObjectChar gm = gameObjectChar;
			// 这是未暂离队员
			if (GameCommonUtil.isNotGameTeam(gm.gameTeam)) {
				List<Chara> duiwu = gm.gameTeam.duiwu;
				Vo_TEAM_DATA team = new Vo_TEAM_DATA();
				team.setList(new ArrayList<ListVo_TEAM_DATA>());
				team.setIsTeam(1);
				for (Chara c : duiwu) {
					ListVo_TEAM_DATA list = new ListVo_TEAM_DATA();
					list.setIcon(c.waiguan);
					list.setId(c.id);
					list.setLevel(c.level);
					list.setName(c.name);
					list.setVip(c.vipType);
					list.setZanli(gm.gameTeam.zhanliduiyuan == null || gm.gameTeam.zhanliduiyuan.isEmpty() ? 0
							: gm.gameTeam.zhanliduiyuan.size());
				}
//				GameObjectChar.send(new MSG_TEAM_DATA(), team);
			}
			log.info("查询队伍信息....");
		}
		if (30013 == type) {
//			 Vo_45075_0 vo_45075_0 = new Vo_45075_0();
//			vo_45075_0.teams = 0;
//			vo_45075_0.members = 0;
//			if(GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
//				//有队伍
//				vo_45075_0.teams = 1;
//				vo_45075_0.members = GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu.size();
//			}
//			GameObjectChar.send(new M45075_0(), vo_45075_0);
//			GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
//			if (session.gameTeam != null) {
//                session.gameTeam.liebiao.clear();
//            }
//            Vo_61593_0 vo_61593_0 = new Vo_61593_0();
//            vo_61593_0.ask_type = "invite_join";
//            GameObjectChar.send(new M61593_0(), vo_61593_0);
//            Vo_20568_0 vo_20568_0 = new Vo_20568_0();
//            vo_20568_0.gid = "";
//            GameObjectChar.send(new M20568_0(), vo_20568_0);

			log.info("请求匹配队员与数量");
		}
		if (30011 == type) {
			GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
			if (session.gameTeam != null && session.gameTeam.duiwu != null && !session.gameTeam.duiwu.isEmpty()) {
				session.gameTeam.liebiao.clear();
			}
			Vo_61593_0 vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "invite_join";
			GameObjectChar.send(new M61593_0(), vo_61593_0);
			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar.send(new M20568_0(), vo_20568_0);
		}
		if (30012 == type) {
			GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
			if (session.gameTeam != null && session.gameTeam.duiwu != null && !session.gameTeam.duiwu.isEmpty()) {
				session.gameTeam.liebiao.clear();
			}
			Vo_61593_0 vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "request_join";
			GameObjectChar.send(new M61593_0(), vo_61593_0);
			Vo_20568_0 vo_20568_0 = new Vo_20568_0();
			vo_20568_0.gid = "";
			GameObjectChar.send(new M20568_0(), vo_20568_0);
		}
		if (26 == type) {
		}
		if (30038 == type) {
			PetHelpType petHelpType = GameData.that.basePetHelpTypeService.findOneByName(para1);
			String[] split3 = para2.split(";");
			int pos7 = Integer.parseInt(split3[0]);
			int pos8 = Integer.parseInt(split3[1]);
			Hashtable<String, int[]> stringHashtable2 = PetAttributesUtils.helpPet(pos8, pos7, chara.level);
			int[] attributes2 = stringHashtable2.get("attribute");
			int[] polars2 = stringHashtable2.get("polars");
			Vo_45128_0 vo_45128_2 = new Vo_45128_0();
			vo_45128_2.life = attributes2[0];
			vo_45128_2.mag_power = attributes2[1];
			vo_45128_2.phy_power = attributes2[2];
			vo_45128_2.speed = attributes2[3];
			vo_45128_2.wood = polars2[0];
			vo_45128_2.water = polars2[1];
			vo_45128_2.fire = polars2[2];
			vo_45128_2.earth = polars2[3];
			vo_45128_2.resist_metal = polars2[4];
			vo_45128_2.skill = chara.level;
			vo_45128_2.str = para1;
			vo_45128_2.shape = 0;
			vo_45128_2.penetrate = pos8;
			vo_45128_2.metal = pos7;
			vo_45128_2.color = pos8;
			vo_45128_2.suit_polar = para1;
			vo_45128_2.type = petHelpType.getType();
			int[] ints2 = BasicAttributesUtils.calculationHelpAttributes(chara.level, attributes2[0], attributes2[1],
					attributes2[2], attributes2[3], polars2[0], polars2[1], polars2[2], polars2[3], polars2[4], pos7);
			vo_45128_2.max_life = ints2[0];
			vo_45128_2.def = ints2[0];
			vo_45128_2.accurate = ints2[2];
			vo_45128_2.mana = ints2[3];
			vo_45128_2.parry = ints2[4];
			vo_45128_2.wiz = ints2[5];
			GameObjectChar.send(new M45128_0(), vo_45128_2);
		}
		//代金卷切换
		if (30023 == type) {
			chara.use_money_type = Integer.parseInt(para1);
			GameUtil.sendUpdate(chara);
		}
		if (type == 40008) {
			GameUtil.MSG_OPEN_WELFARE(chara);
		}
		if (type == 11002) {
			if (chara.baxiantiaozhan+1 > GameConfig.config.getBaseConfig().getBaxianNum()) {
				Vo_20481_0 vo_20481_7 = new Vo_20481_0();
				vo_20481_7.msg = "今日已经挑战完了八仙梦境，道友明天再来！";
				vo_20481_7.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_7);
				return;
			}
			GameCommonUtil.enterDynamicMap("桐柏山", chara);
			//当没有任务的时候才创建任务
			if(chara.taskMap.get("八仙梦境") == null) {
				Vo_61553_0 vo_61553_2 = new Vo_61553_0();
				vo_61553_2.count = 1;
				vo_61553_2.task_type = "八仙梦境";
				vo_61553_2.task_desc = "八仙梦境-吕洞宾";
				vo_61553_2.task_prompt = "前去#P吕洞宾1|E=【八仙】吕洞宾的苦恼|$0#P处打探一下情况";
				vo_61553_2.refresh = 1;
				vo_61553_2.task_end_time = (int) (System.currentTimeMillis() / 1000L);
				vo_61553_2.attrib = 1;
				vo_61553_2.reward = "";
				vo_61553_2.show_name = "八仙梦境-吕洞宾";
				vo_61553_2.task_extra_para = "";
				vo_61553_2.task_state = "0";
				GameUtilRenWu.createTask(vo_61553_2, chara);
			}
		}

		// 排行榜当前玩家显示的信息.
		if (type == 30017) {
			GameObjectChar.send(new M_MSG_RANK_CLIENT_INFO(), chara);
		}
		// 排行榜其他玩家列表
		if (type == 3) {
			processRank(para1, para2);
		}
	}

//	public static void doCharaBlockAndBlankList(GameObjectChar gameObjectChar, String msg) {
//		gameObjectChar.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你你使用了WPE，请勿利用漏洞严重者永久封号");
//		gameObjectChar.offline();
//		final BlackListService blackListService = GameData.that.blackListService;
//		final Accounts account = GameData.that.baseAccountsService.findById(gameObjectChar.characters.getAccountId());
//		if (account != null) {
//			BlackList b = new BlackList();
//			if (account.getMac() != null) {
//				b.setData(account.getMac());
//				b.setType(2);
//				b.setAddTime(new Date());
//				blackListService.insertSelective(b);
//			}
//			if (account.getLastLoginIp() != null) {
//				b = new BlackList();
//				b.setType(2);
//				b.setData(account.getLastLoginIp());
//				b.setAddTime(new Date());
//				blackListService.insertSelective(b);
//			}
//			if (account.getRegisterIp() != null) {
//				b = new BlackList();
//				b.setType(2);
//				b.setData(account.getRegisterIp());
//				b.setAddTime(new Date());
//				blackListService.insertSelective(b);
//			}
//			final Characters characters = new Characters();
//			characters.setName(gameObjectChar.characters.getName() + msg);
//			characters.setBlock(Integer.valueOf(1));
//			characters.setUpdateTime(new Date());
//			characters.setId(gameObjectChar.characters.getId());
//			GameData.that.baseCharactersService.updateById(characters);
//		}
//	}

	/**
	 * 处理排行榜
	 * @param rankType
	 * @param cookie
	 */
	private void processRank(String rankType, String cookie) {

		if (rankType.equals("601") || rankType.equals("602") || rankType.equals("603") || rankType.equals("604")
				|| rankType.equals("605") || rankType.equals("606") || rankType.equals("607") || rankType.equals("608")
				|| rankType.equals("703")) {
			return;
		}
		int type = 0;
		int minLevel = 0;
		int maxLevel = 0;
		int requestType = 1;
		if (rankType.contains(":")) {
			String[] strings = rankType.split(":");
			type = Integer.parseInt(strings[0]);
			String[] levelStrings = strings[1].split("-");
			minLevel = Integer.parseInt(levelStrings[0]);
			maxLevel = Integer.parseInt(levelStrings[1]);
			requestType = 2;
		} else {
			type = Integer.parseInt(rankType);
		}
		if (rankType.startsWith("201") || rankType.startsWith("202") || rankType.startsWith("203")
				|| rankType.startsWith("204")) {
			GameObjectChar.send(new MSG_TOP_USER(), gameRankJob.getRankEquipVo(minLevel, maxLevel, requestType, type));
		} else if (rankType.equals("301") || rankType.equals("302") || rankType.equals("303") || rankType.equals("304")
				|| rankType.equals("305")) {
			GameObjectChar.send(new MSG_TOP_USER(), gameRankJob.getRankVo("RANK_PET_", requestType, type));
		} else {
			List<Rank> rankList = gameRankJob.getRankList(rankType);
			GameObjectChar.send(new M61653_0(type, requestType, minLevel, maxLevel,
					(int) (System.currentTimeMillis() / 1000L), rankList.size()), rankList);
		}
		log.info("请求类型:{}", type);
	}
	

	public static void main(String[] args) {
		String s = "前往#Z地图|地图(坐标X,坐标Y)#Z寻宝";
		System.out.println(s);
	}

	@Override
	public int cmd() {
		return 63752;
	}
}