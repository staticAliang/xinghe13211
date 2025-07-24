package com.fengshen.server.job;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.DateUtil;
import com.fengshen.core.util.JSONUtils;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.CharaTrail;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.StallRecord;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsFenSe;
import com.fengshen.server.domain.GoodsHuangSe;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.GoodsLvSe;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.domain.rank.LuoBoTaoZiRankVo;
import com.fengshen.server.game.GameBossTianDiXing;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameGongCheng;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component
@Slf4j
public class GameUtilJob {

	//年兽
	public static ConfigInfo new_year_beast_time;
	
	/**
	 * 每天凌晨5点清除角色任务
	 */
	@Scheduled(cron = "0 0 5 * * ?")
	public void refreshRenwu() {
		int excuteItem = 0;
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		//数据库查询出更新用户的id
		Example example = new Example(Characters.class);
		example.selectProperties("id");
		example.createCriteria().andEqualTo("block", 0).andEqualTo("deleted",false).andEqualTo("xiaozi", 0);
		List<Characters> characters = GameData.that.baseCharactersService.selectByExample(example);
		List<Integer> cids = new ArrayList<Integer>();
		for(Characters ch:characters) {
			cids.add(ch.getId());
		}
		// 获取在线用户
		for (GameObjectChar g : all) {
			// 对他们进行重置任务
			GameUtil.resetRenwuByChara(g.chara);
			excuteItem++;
			cids.remove((Object)g.chara.id);
		}
		for(Integer id:cids) {
			Example rest = new Example(Characters.class);
			rest.selectProperties("data","gid","id");
			rest.createCriteria().andEqualTo("id", id);
			Characters c = GameData.that.characterService.selectOneByExample(rest);
			SaveChara chara = null;
			try {
				chara = JSONObject.parseObject(c.getData(), SaveChara.class);
			} catch (Exception e) {
				excuteItem--;
				log.error("执行失败重置任务失败一条,角色id为={}", c.getId());
				log.error("{}", e);
			}
			if (chara != null) {
				GameUtil.resetRenwuByChara(chara);
				// 更新任务信息
				Characters update = new Characters();
				update.setId(c.getId());
				update.setData(JSONUtils.toJSONString(chara));
				int updateById = GameData.that.baseCharactersService.updateById(update);
				excuteItem += updateById;
			}
		}
		
		log.error("执行清除角色任务成功,执行条数:{}", excuteItem);
		Calendar calendar = Calendar.getInstance();
		int monday = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		if (monday == 1) {
			log.error("开始设置帮派上周活力");
			// 如果是星期一更新帮派的上周信息
			List<PartyMember> partyMembers = GameData.that.partyMemberService.selectAll();
			for (PartyMember p : partyMembers) {
				p.setLastWeekActive(p.getCurrWeekActive());
				p.setCurrWeekActive(0);
				GameData.that.partyMemberService.updateByPrimaryKeySelective(p);
			}
		}
		// 帮派每天的消耗
		List<Party> partys = GameData.that.partyService.selectAll();
		for (Party p : partys) {
			if (p.getPartyLevel() > 1) {
				// 减去建设度和资金
				int[] partyToDaySub = GamePartyUtil.getPartyToDaySub(p.getPartyLevel());
				int newConstruct = p.getConstruct() - partyToDaySub[0];
				p.setConstruct(newConstruct < 0 ? 0 : newConstruct);
				int money = p.getMoney() - partyToDaySub[1];
				p.setMoney(money < 0 ? 0 : money);
				// 如果扣除之后的建设度少于当前级数最低要求的话,帮派则会降级
				if (p.getConstruct() < GamePartyUtil.getPartyRequirdConstruct(p.getPartyLevel())) {
					p.setPartyLevel(p.getPartyLevel() - 1);
				}
				GameData.that.partyService.updateByPrimaryKeySelective(p);
			}
		}
	}

	public void haidaoOpenTimes() {
		String[] times = GameConfig.config.getHaidao().getTimes();
		if (times != null && times.length > 0) {
			for (String t : times) {
				if (Utils.compareHourOfMinute(t)) {
					log.error("海盗定时刷新");
					GameGongCheng.sendHaidao(GameLine.gameGongCheng);
				}
			}
		}
		
	}
	
	/**
	 * 监听加速脚本
	 * 这里模拟客户端每10秒钟会对服务器发送一次心跳
	 * 每10秒钟进行清零
	 */
	@Scheduled(cron = "*/10 * * * * ?")
	public void listenerAccelerateScript() {
		for(GameObjectChar gameObject:GameObjectCharMng.getAll()) {
			gameObject.commonSpeedNum.set(0);
		}
	}
	
	/**
	 * 每一秒钟检测活动是否开启.
	 */
	@Scheduled(cron = "*/1 * * * * ?")
	public void on1000Monster() {
		// 海盗开启时间
		haidaoOpenTimes();
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		//判断时间
		if(new_year_beast_time != null) {
			String times = new_year_beast_time.getData();
			String[] split = times.split("~");
			String startTimeStr = split[0].trim()+":00";
			if(Utils.compareHourOfMinute(startTimeStr)) {
				for (GameObjectChar gameSession : all) {
					if (gameSession.chara != null && gameSession.characters.getXiaozi() == 0) {
						//活动开始
						GameUtil.sendSystemMessage(7, "#Y烟花年兽#n活动已经开始了，请各位道友带上#Y烟花道具#n前往#R#Z北海沙滩#Z#n消灭年兽，活动时间为#R"+times+"#n时间有限请快快来，该活动奖励丰富,请积极参与！");
					}
				}
			}
		}
		//桃子萝卜收集
		ConfigInfo taoziLuobo = GameData.that.configInfoService.getOneByUuid("taozi_luobo");
		if(taoziLuobo != null) {
			String data = taoziLuobo.getData();
			JSONObject parseObject = JSONObject.parseObject(data);
			String time = parseObject.getString("time");
			String[] split = time.split("~");
			String startTimeStr = split[0].trim()+":00";
			String startTimeStr2 = split[1].trim()+":00";
			int state = parseObject.getIntValue("state");
			//如果活动开启了
			boolean dateToWeekDay = GameUtilRenWu.dateToWeekDay(parseObject.getString("week").split(","));
			boolean compareHourOfMinute = Utils.compareHourOfMinute(startTimeStr);

			// System.out.print("taozi_begintime:"+startTimeStr);
			// System.out.print("taozi_endtime:"+startTimeStr2);
			// System.out.print("state:"+state);
			// System.out.print("dateToWeekDay:"+dateToWeekDay);
			// System.out.print("compareHourOfMinute:"+compareHourOfMinute);

			if(compareHourOfMinute && GameConfig.taoziLuoboStatus == 0 &&
					dateToWeekDay && state == 1) {
				GameConfig.taoziLuoboStatus = 1;
				GameUtil.sendSystemMessage(19,"#R萝卜桃子收集#n已经开始了，请各位尽快到千面怪出参与活动，活动奖励丰厚哦请及时参与！");
				int npcCount = parseObject.getIntValue("npcCount");
				//桃柳林
				String xy = "36,50|46,47|52,41|51,32|48,23|52,15|57,32|48,30|40,26|32,22|38,11|34,19|25,20|22,13|18,6|9,21|6,30|7,39|16,41|26,42|32,36|25,33|32,36|27,43|36,40|42,39|47,43|53,46|";
				String[] xys = xy.split("\\|");
				//官道北
				String daobeiXy = "18,39|17,40|8,38|13,46|22,49|29,43|38,42|43,49|51,44|48,36|29,35|19,36|17,31|37,28|47,29|50,23|42,20|32,21|23,23|17,21|24,15|32,13|27,7|18,10|10,17|6,11|11,10|";
				String[] daobeiXys = daobeiXy.split("\\|");
				for (int i = 0; i < npcCount; i++) {
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.id = GameCommonUtil.generateBossId();
					npc.uuid = UUID.randomUUID().toString().replace("-", "");
					npc.name = "桃树";
					if(ThreadLocalRandom.current().nextBoolean()) {
						int index = ThreadLocalRandom.current().nextInt(xys.length);
						String[] xyvs = xys[index].split(",");
						npc.x = Integer.valueOf(xyvs[0]);
						npc.y = Integer.valueOf(xyvs[1]);
						npc.mapid = 6000;
						npc.mapName = "桃柳林";
					}else {
						//道北
						int index = ThreadLocalRandom.current().nextInt(daobeiXys.length);
						String[] xyvs = daobeiXys[index].split(",");
						npc.x = Integer.valueOf(xyvs[0]);
						npc.y = Integer.valueOf(xyvs[1]);
						npc.mapid = 24000;
						npc.mapName = "官道北";
					}
					npc.icon = 6106;
					npc.org_icon = npc.icon;
					npc.portrait = npc.icon;
					GameCore.otherBoosMonster.put(npc.id, npc);
					GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), npc, npc.mapid);
					//半小时后消失
					GameData.that.redisUtils.set("TAOZI_LUOBO_HIDE:"+npc.id, "", 1800);
				}
			}
			//活动已经开启了
			if(GameConfig.taoziLuoboStatus == 1) {
				String beforeDate = DateUtil.format(new Date(), "yyyy-MM-dd");
				Date endDate = DateUtil.parse(beforeDate+" "+startTimeStr2, "yyyy-MM-dd H:mm:ss");
				if((System.currentTimeMillis())>endDate.getTime() || state == 0) {
					//活动结束了
					GameConfig.taoziLuoboStatus = 0;
					GameUtil.sendSystemMessage(7,"#R萝卜桃子收集#n活动已结束，感谢各位的参与！");
					for (Entry<Integer, Vo_APPEAR> m : GameCore.otherBoosMonster.entrySet()) {
						GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), m.getValue().id, m.getValue().mapid);
						GameCore.fightObject.remove(m.getValue().id);
					}
					GameCore.otherBoosMonster.clear();
					//发放奖励
					if(!StringUtils.isNullOrEmpty(parseObject.getString("rankReward"))) {
						Iterator<Integer> ids = GameCore.luoboTaoziCids.iterator();
						List<LuoBoTaoZiRankVo> ranks = new ArrayList<>();
						//分数
						while(ids.hasNext()) {
							Integer next = ids.next();
							GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(next);
							if(gameObjectChar != null) {
								ranks.add(new LuoBoTaoZiRankVo(gameObjectChar.chara.name, gameObjectChar.lbtzTaskCount, gameObjectChar.lbtzTaskTime));
							}
						}
						//排序
						ranks = ranks.stream().sorted(Comparator.comparing(LuoBoTaoZiRankVo::getScore).reversed())
						.collect(Collectors.toList());
						int index = 0;
						String[] rankRewardArr = parseObject.getString("rankReward").split(",");
						for(LuoBoTaoZiRankVo rank:ranks) {
							String rewardName = rankRewardArr[index];
							String[] rewardArr = rewardName.split(":");
							if(rankRewardArr.length>0 && rewardArr.length>0) {
								GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(rank.getName());
								if(gameObjectChar != null) {
									if(GameActiveUtil.fightVictoryInfo(gameObjectChar.chara, rewardArr[0])) {
										gameObjectChar.sendTips("你获得了本次萝卜桃子第"+(index+1)+"名");
										if(rewardArr.length>1) {
											//有称号奖励
											GameUtil.chenghaoxiaoxi(gameObjectChar.chara, "萝卜桃子", rewardArr[1]);
										}
									}
								}
							}
							index++;
							if(index >=rankRewardArr.length) {
								break;
							}
						}
					}
					Iterator<Integer> iterator = GameCore.luoboTaoziCids.iterator();
					while(iterator.hasNext()) {
						Integer id = iterator.next();
						GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
						if(gameObjectChar != null) {
							//删除任务
							GameUtilRenWu.removeTask("萝卜桃子大收集", gameObjectChar.chara);
							gameObjectChar.lbtzTaskCount = 1;
						}else {
							//数据库查询
							Characters ch = GameData.that.baseCharactersService.findOneByIdSelectProperties(id, "id","data");
							Chara chara = JSONObject.parseObject(ch.getData(),Chara.class);
							chara.taskMap.remove("萝卜桃子大收集");
							ch.setData(JSONObject.toJSONString(chara));
							//保存
							GameData.that.baseCharactersService.updateByPrimaryKeySelective(ch);
						}
					}
					GameCore.luoboTaoziCids.clear();
				}else {
					//每10分钟刷新
					String xy = "36,50|46,47|52,41|51,32|48,23|52,15|57,32|48,30|40,26|32,22|38,11|34,19|25,20|22,13|18,6|9,21|6,30|7,39|16,41|26,42|32,36|25,33|32,36|27,43|36,40|42,39|47,43|53,46|";
					//官道北
					String daobeiXy = "18,39|17,40|8,38|13,46|22,49|29,43|38,42|43,49|51,44|48,36|29,35|19,36|17,31|37,28|47,29|50,23|42,20|32,21|23,23|17,21|24,15|32,13|27,7|18,10|10,17|6,11|11,10|";
					String[] daobeiXys = daobeiXy.split("\\|");
					Calendar calendar = Calendar.getInstance();
					if(calendar.get(Calendar.MINUTE) % 10 == 0 && calendar.get(Calendar.SECOND) == 0) {
						int lierenCount = parseObject.getIntValue("lierenCount");
						String[] xys = xy.split("\\|");
						for (int i = 0; i < lierenCount; i++) {
							Vo_APPEAR npc = new Vo_APPEAR();
							npc.id = GameCommonUtil.generateBossId();
							npc.uuid = UUID.randomUUID().toString().replace("-", "");
							npc.name = "猎人头领";
							if(ThreadLocalRandom.current().nextBoolean()) {
								npc.mapid = 6000;
								npc.mapName = "桃柳林";
								int index = ThreadLocalRandom.current().nextInt(xys.length);
								String[] xyvs = xys[index].split(",");
								npc.x = Integer.valueOf(xyvs[0]);
								npc.y = Integer.valueOf(xyvs[1]);
							}else {
								npc.mapid = 24000;
								npc.mapName = "官道北";
								int index = ThreadLocalRandom.current().nextInt(daobeiXys.length);
								String[] xyvs = daobeiXys[index].split(",");
								npc.x = Integer.valueOf(xyvs[0]);
								npc.y = Integer.valueOf(xyvs[1]);
							}
							GameUtil.sendSystemMessage(7,"听闻一大批抢夺萝卜桃子的猎人已登陆#Z"+npc.mapName+"#Z,请各位道友前去将其驱赶，驱赶成功会获得丰厚奖励哦！");
							npc.icon = 6201;
							npc.org_icon = npc.icon;
							npc.portrait = npc.icon;
							GameCore.otherBoosMonster.put(npc.id, npc);
							GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), npc, npc.mapid);
							//半小时后消失
							GameData.that.redisUtils.set("TAOZI_LUOBO_HIDE:"+npc.id, "", 1800);
						}
					}
					if(calendar.get(Calendar.MINUTE) % 3 == 0 && calendar.get(Calendar.SECOND) == 0) {
						int npcCount = parseObject.getIntValue("npcCount");
						String[] xys = xy.split("\\|");
						//官道北
						for (int i = 0; i < npcCount; i++) {
							Vo_APPEAR npc = new Vo_APPEAR();
							npc.id = GameCommonUtil.generateBossId();
							npc.uuid = UUID.randomUUID().toString().replace("-", "");
							npc.name = "桃树";
							if(ThreadLocalRandom.current().nextBoolean()) {
								int index = ThreadLocalRandom.current().nextInt(xys.length);
								String[] xyvs = xys[index].split(",");
								npc.x = Integer.valueOf(xyvs[0]);
								npc.y = Integer.valueOf(xyvs[1]);
								npc.mapid = 6000;
								npc.mapName = "桃柳林";
							}else {
								//道北
								int index = ThreadLocalRandom.current().nextInt(daobeiXys.length);
								String[] xyvs = xys[index].split(",");
								npc.x = Integer.valueOf(xyvs[0]);
								npc.y = Integer.valueOf(xyvs[1]);
								npc.mapid = 24000;
								npc.mapName = "官道北";
							}
							npc.icon = 6106;
							npc.org_icon = npc.icon;
							npc.portrait = npc.icon;
							GameCore.otherBoosMonster.put(npc.id, npc);
							GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), npc, npc.mapid);
							//半小时后消失
							GameData.that.redisUtils.set("TAOZI_LUOBO_HIDE:"+npc.id, "", 1800);
						}
					}
					
					if(calendar.get(Calendar.MINUTE) % 5 == 0 && calendar.get(Calendar.SECOND) == 0) {
						//发送排名公告
						Iterator<Integer> ids = GameCore.luoboTaoziCids.iterator();
						List<LuoBoTaoZiRankVo> ranks = new ArrayList<>();
						//分数
						while(ids.hasNext()) {
							Integer next = ids.next();
							GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(next);
							if(gameObjectChar != null) {
								ranks.add(new LuoBoTaoZiRankVo(gameObjectChar.chara.name, gameObjectChar.lbtzTaskCount, gameObjectChar.lbtzTaskTime));
							}
						}
						//排序
						ranks = ranks.stream().sorted(Comparator.comparing(LuoBoTaoZiRankVo::getScore).reversed())
						.collect(Collectors.toList());
						int index = 0;
						StringBuilder rankMsg = new StringBuilder();
						for(LuoBoTaoZiRankVo rank:ranks) {
							index++;
							rankMsg.append("\n第").append(index).append("名：#R").append(rank.getName()).append("#n：").append(rank.getScore()).append("次");
							if(index >=3) {
								break;
							}
						}
						GameUtil.sendSystemMessage(19,"#R萝卜桃子收集#n正如火如荼的进行中呢，还未参与的道友请到千面怪处报名参加吧！当前排名信息如下，" + rankMsg.toString());
					}
				}
			}
		}
	}

	/**
	 * 10分钟刷星
	 */
	@Scheduled(cron = "0 */10 * * * ?")
	public void shuaxing() {
		int count = 3;
		Map<String, Object> shuaxing = GameConfig.config.getBaseConfig().getShuaxing();
		if(shuaxing !=null && !shuaxing.isEmpty()) {
			Object object = shuaxing.get("count");
			if(object != null && object instanceof Integer) {
				count = (int) object;
			}
		}
		for (int i = 0; i < count; i++) {
			GameBossTianDiXing.shuaxing(null);
		}
		//
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			GameUtil.sendSystemMessage(7,"全局双倍活动正在进行中，请各位道友积极参与活动。");
		}
		log.info("刷星定时器....");
	}
	
	/**
	 * 清除问道小子背包
	 */
	@Scheduled(cron = "0 */60 * * * ?")
	public void clearWenDaoXiaoBackPack() {
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		for(GameObjectChar a:all) {
			if(a.characters.getXiaozi() != null && 
					a.characters.getXiaozi() == 1) {
				Iterator<Goods> iterator = a.chara.backpack.iterator();
				while(iterator.hasNext()) {
					Goods next = iterator.next();
					if(next.pos>21) {
						iterator.remove();
					}
				}
			}
		}
		log.info("清理问道小子背包....");
	}
	
	//一小时刷新一次攻城boss
//	@Scheduled(cron = "0 */60 * * * ?")
//	public void gongcheng() {
//		GameGongCheng.sendShuaGuai();
//		log.info("清理问道小子背包....");
//	}
	
	@Scheduled(fixedDelay = 2000L)
	public void autofightshuaguai() {
		 long time = System.currentTimeMillis();
		if (GameLine.gameShuaGuai.shuaXingzhuangtai == 2 && GameLine.gameShuaGuai.shuaXingTime + 180000L < time) {
			GameLine.gameShuaGuai.shuaXingzhuangtai = 0;
			GameLine.gameShuaGuai.shuaXingTime = System.currentTimeMillis();
			for (int i = 0; i < GameLine.gameShuaGuai.shuaXing.size(); ++i) {
				GameLine.gameShuaGuai.shuaXing.get(i).wanjiaid = 0;
			}
		}
	}
	

	
	/**
	 * 每月一号0:10:00清除30天前记录
	 */
	@Scheduled(cron = "0 10 0 1 * ?")
	public void clearRecord() {
		Example example = new Example(StallRecord.class);
		String past30 = DateUtil.format(DateUtil.getPastDate(30), "yyyy-MM-dd");
		example.createCriteria().andCondition("DATE_FORMAT(add_time,\"%Y-%m-%d\")<=", past30);
		int num = GameData.that.stallRecordService.deleteByExample(example);
		
		
//		List<GameObjectChar> all = GameObjectCharMng.getAll();
//		for(GameObjectChar a:all) {
//			a.chara.monthTao = 0;
//		}

		Example example2 = new Example(Characters.class);
		example2.createCriteria().andEqualTo("xiaozi",0).andEqualTo("deleted", false);
		Characters characters = new Characters();
		characters.setMonthTao(0);
		GameData.that.baseCharactersService.updateByExampleSelective(characters, example2);
		log.error("定时删除交易记录，交易记录条数：{}", num);
		
		
		Example example3 = new Example(CharaTrail.class);
		example3.createCriteria().andNotEqualTo("remarks", "改名").andNotEqualTo("remarks", "WPE");
		int num2 = GameData.that.charaTrailService.deleteByExample(example3);
		log.error("执行清除今日统计任务成功,执行条数:{}", num2);
	}
	
	
	/**
	 * 每300中检测连接是否有效
	 */
//	@Scheduled(cron = "*/300 * * * * ?")
//	public void validateChannel() {
//		for(Entry<String, ChannelHandlerContext> ctx:ServerHandler.clientMap.entrySet()) {
//			String id = ctx.getValue().channel().id().asLongText();
//			if(GameObjectCharMng.getGameObjectCharByChannelId(id) == null) {
//				InetSocketAddress ipSocket = (InetSocketAddress) ctx.getValue().channel().remoteAddress();
//				String clientIp = ipSocket.getAddress().getHostAddress();
//				ctx.getValue().close();
//				ServerHandler.clientMap.remove(id);
//				log.info("客户端非法连接,系统强制断开...ip={}",clientIp);
//			}
//		}
//	}
	
	/**
	 * 每个星期天0点20分执行
	 */
//	@Scheduled(cron = "0 20 0 * * 7")
//	public void deleteCharaTrail() {
//		Example example2 = new Example(CharaTrail.class);
//		example2.createCriteria().andNotEqualTo("remarks", "改名").andNotEqualTo("remarks", "WPE");
//		int num2 = GameData.that.charaTrailService.deleteByExample(example2);
//		log.error("执行清除今日统计任务成功,执行条数:{}", num2);
		//月道行清空
//		List<GameObjectChar> all = GameObjectCharMng.getAll();
//		for(GameObjectChar a:all) {
//			a.chara.monthTao = 0;
//		}
//		Example example = new Example(Characters.class);
//		
//		Characters characters = new Characters();
//		characters.setMonthTao(0);
//		GameData.that.baseCharactersService.updateByExampleSelective(characters, example);
//	}
	
	//强制PK坐牢倒计时
	@Scheduled(cron = "*/1 * * * * ?")
	public void forcePk() {
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		for (GameObjectChar gameSession : all) {
			Chara chara = gameSession.chara;
			if (chara != null && chara.crimeTime>0) {
				if(chara.taskMap.get("坐牢") != null && chara.mapName.equals("监狱")) {
					chara.crimeTime--;
					if(chara.crimeTime <= 0) {
						chara.crimeTime = 0;
						//释放该犯人
						chara.isNameRed = 0;
						//删除任务
						GameUtilRenWu.removeTask("坐牢", chara);
						chara.x = 26;
						chara.y = 30;
						GameLine.getGameMap(chara.line, "监狱").join(gameSession);
						GameCommonUtil.sendTips("坐牢时间到了，你被释放了", gameSession);
					}
				}
			}
		}
	}
	
	//每月1号0点0分执行
	@Scheduled(cron = "0 0 0 1 * ?")
	public void timerLastMonthExecute() {
		log.info("执行最后一天任务.....");
		//所有在线角色
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		for (GameObjectChar gameSession : all) {
			Chara chara = gameSession.chara;
			chara.isCanSgin = 1;
			chara.signDays = 0;
		}
		//未上线人员
		Example example = new Example(Characters.class);
		example.selectProperties("id");
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("online", 0);
		createCriteria.andEqualTo("block", 0).andEqualTo("xiaozi", 0).andEqualTo("deleted", false);
		List<Characters> selectByExample = GameData.that.baseCharactersService.selectByExample(example);
		for(Characters ch:selectByExample) {
			Example query = new Example(Characters.class);
			query.selectProperties("data");
			query.createCriteria().andEqualTo("id", ch.getId());
			Characters selectOneByExample = GameData.that.baseCharactersService.selectOneByExample(query);
			if(selectByExample != null) {
				try {
					Chara chara = JSONObject.parseObject(selectOneByExample.getData(),Chara.class);
					chara.isCanSgin = 1;
					chara.signDays = 0;
					//保存
					selectOneByExample.setData(JSONObject.toJSONString(chara));
					GameData.that.baseCharactersService.updateByPrimaryKeySelective(selectOneByExample);
				} catch (Exception e) {
					log.error("{}",e);
				}
			}
		}
	}


	//	@Scheduled(cron = "0 */3 * * * ?")
//	@Scheduled(cron = "*/10 * * * * ?")
//	public void checkRoleInfo() throws Exception {
//		// 检测属性点是否正常
//		List<GameObjectChar> all = GameObjectCharMng.getAll();
//		for (GameObjectChar game : all) {
//			// 只检测普通玩家
//			if (game.privilege == 0) {
//				Chara chara = game.chara;
//				// 当前加点的点数
//				int currentPoint = chara.phy_power + chara.life + chara.speed + chara.mag_power;
//				// 当前等级最大加点,这里允许多出10点
//				int charaMaxPoint = ((chara.level-1) * 4) + (chara.level * 4)+10;
//				if (chara.upgrade_state == 0) {
//					if (chara.upgrade_level > 0) {
//						// 飞升了.
//						int addNum = (int) (chara.upgrade_level / 10) * 2;
//						charaMaxPoint += addNum;
//					}
//				}
//				//升级了阶段
//				if(chara.danDataState>1) {
//					charaMaxPoint+=chara.danDataAttribPoint;
//				}
//				if (currentPoint > charaMaxPoint) {
//					//强制下线
//					// GameCommonUtil.sendTips("系统检测到你的属性点异常,已为您重新初始化属性点了！", game);
//					// GameCommonUtil.resetDefaultAttr(game);
//					// game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的属性点异常！");
//					// game.offline(game);
//					// continue;
//				}
//				//如果当前属性点小于最低限制
//				if(chara.phy_power<chara.level || chara.life<chara.level || chara.speed<chara.level || chara.mag_power<chara.level) {
//					GameCommonUtil.sendTips("系统检测到你的属性点异常,已为您重新初始化属性点了！", game);
//					GameCommonUtil.resetDefaultAttr(game);
//					continue;
//				}
//				//仙魔点检测
//				if(chara.upgrade_level>119 && chara.upgrade_type>2 && chara.realLevel>119) {
//					int upgradeMaxPoint = chara.realLevel-111;
//					if(upgradeMaxPoint<8) {
//						upgradeMaxPoint = 8;
//					}
//					if((chara.upgrade_immortal+chara.upgrade_magic)>(upgradeMaxPoint*3)) {
//						//封号处理
//						GameCommonUtil.sendTips("系统检测到你的属性点异常,已为您重新初始化属性点了！",game);
//						GameCommonUtil.resetDefaultAttr(game);
//						continue;
//					}
//				}
//				// 装备检测
//				Iterator<Goods> iterator = chara.otherGoods.iterator();
//				while (iterator.hasNext()) {
//					Goods goods = iterator.next();
//					boolean isEnd = false;
//					if (goods.pos > 10) {
//						continue;
//					} else if (goods.pos == 8) {
//						// 单独检测魂器
//						if (goods.pos == 8 && goods.goodsInfo.amount != 8) {
//							// 装备位置错误,强制下线。并删除装备
//							iterator.remove();
//							GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//							game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你佩戴的装备异常，请勿利用漏洞严重者永久封号");
//							game.offline(game);
//							break;
//						}
//						continue;
//					} else if (goods.pos == 9) {
//						// 法宝
//						if (goods.pos == 9 && goods.goodsInfo.amount != 9) {
//							// 装备位置错误,强制下线。并删除装备
//							iterator.remove();
//							GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//							game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你佩戴的装备异常，请勿利用漏洞严重者永久封号");
//							game.offline(game);
//							break;
//						}
//						continue;
//					}
//					// 只检测1-8-10
//					GoodsLanSe goodsLanSe = goods.getGoodsLanSe();
//					Field[] fieldsLanSe = goodsLanSe.getClass().getFields();
//					int blueAttrCount = 0;
//					for (Field f : fieldsLanSe) {
//						if (f.get(goodsLanSe) instanceof Integer) {
//							if (f.getName().equals("groupNo") || f.getName().equals("groupType")) {
//								continue;
//							}
//							int value = (Integer)f.get(goodsLanSe);
//							String chinese = ForgingEquipmentUtils.getEquipmentKeyByName(f.getName(), false);
//							int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chinese,
//									goods.goodsInfo.attrib, goods.pos == 3, false);
//							if (value > maxValue) {
//								// 属性值不正常大于
//								iterator.remove();
//								GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//								game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//								game.offline(game);
//								isEnd = true;
//								break;
//							}
//							if(value > 0) {
//								if(goods.pos == 1 || goods.pos == 2 || goods.pos == 3 || goods.pos == 10) {
//									blueAttrCount++;
//								}
//							}
//						}
//					}
//					if(blueAttrCount > 3) {
//						log.info("违规装备属性,封号处理");
//						GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//						GameData.that.redisUtils.getIncr2("CHECK_ATTR_ERROR"+ chara.id);
//						// 属性值不正常大于
//						iterator.remove();
//						game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//						game.offline(game);
//						return;
//					}
//
//					//粉色属性检测
//					int pinkAttrCount = 0;
//					GoodsFenSe goodsFense = goods.goodsFenSe;
//					Field[] fieldsFenSe = goodsFense.getClass().getFields();
//					for (Field f : fieldsFenSe) {
//						if (f.get(goodsFense) instanceof Integer) {
//							if (f.getName().equals("groupNo") || f.getName().equals("groupType")) {
//								continue;
//							}
//							int value = (Integer)f.get(goodsFense);
//							String chinese = ForgingEquipmentUtils.getEquipmentKeyByName(f.getName(), false);
//							int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chinese,
//									goods.goodsInfo.attrib, goods.pos == 3, false);
//							if (value > maxValue) {
//								// 属性值不正常大于
//								iterator.remove();
//								GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//								game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//								game.offline(game);
//								isEnd = true;
//								break;
//							}
//							if(value > 0) {
//								if(goods.pos == 1 || goods.pos == 2 || goods.pos == 3 || goods.pos == 10) {
//									pinkAttrCount++;
//								}
//							}
//						}
//					}
//					if(pinkAttrCount > 1) {
//						log.info("违规装备属性,封号处理");
//						GameData.that.redisUtils.getIncr2("CHECK_ATTR_ERROR"+ chara.id);
//						// 属性值不正常大于
//						iterator.remove();
//						GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//						game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//						game.offline(game);
//						return;
//					}
//
//					//金色属性检测
//					int yellowAttrCount = 0;
//					GoodsHuangSe goodsHuangSe = goods.goodsHuangSe;
//					Field[] fieldsHuangSe = goodsHuangSe.getClass().getFields();
//					for (Field f : fieldsHuangSe) {
//						if (f.get(goodsHuangSe) instanceof Integer) {
//							if (f.getName().equals("groupNo") || f.getName().equals("groupType")) {
//								continue;
//							}
//							int value = (Integer)f.get(goodsHuangSe);
//							String chinese = ForgingEquipmentUtils.getEquipmentKeyByName(f.getName(), false);
//							int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chinese,
//									goods.goodsInfo.attrib, goods.pos == 3, false);
//							if (value > maxValue) {
//								// 属性值不正常大于
//								iterator.remove();
//								GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//								game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//								game.offline(game);
//								isEnd = true;
//								break;
//							}
//							if(value > 0) {
//								if(goods.pos == 1 || goods.pos == 2 || goods.pos == 3 || goods.pos == 10) {
//									yellowAttrCount++;
//								}
//							}
//						}
//					}
//					if(yellowAttrCount > 1) {
//						log.info("违规装备属性,封号处理");
//						GameData.that.redisUtils.getIncr2("CHECK_ATTR_ERROR"+ chara.id);
//						// 属性值不正常大于
//						iterator.remove();
//						GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//						game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//						game.offline(game);
//						return;
//					}
//					GoodsLvSe goodsLvSe = goods.getGoodsLvSe();
//					Field[] fieldsLvSe = goodsLvSe.getClass().getFields();
//					for (Field f : fieldsLvSe) {
//						if (f.get(goodsLvSe) instanceof Integer) {
//							if (f.getName().equals("groupNo") || f.getName().equals("groupType")) {
//								continue;
//							}
//							int value = (Integer)f.get(goodsLvSe);
//							String chinese = ForgingEquipmentUtils.getEquipmentKeyByName(f.getName(), false);
//							int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chinese,
//									goods.goodsInfo.attrib, goods.pos == 3, true);
//							if (value > maxValue) {
//								// 属性值不正常大于
//								iterator.remove();
//								GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//								game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你的装备属性不正常，请勿利用漏洞严重者永久封号");
//								game.offline(game);
//								isEnd = true;
//								break;
//							}
//						}
//					}
//					// 如果位置和
//					boolean isPointError = false;
//					if (goods.pos == 1 && goods.goodsInfo.amount != 1) {
//						isPointError = true;
//					} else if (goods.pos == 2 && goods.goodsInfo.amount != 2) {
//						isPointError = true;
//					} else if (goods.pos == 3 && goods.goodsInfo.amount != 3) {
//						isPointError = true;
//					} else if (goods.pos == 4 && goods.goodsInfo.amount != 4) {
//						isPointError = true;
//					} else if (goods.pos == 5 && goods.goodsInfo.amount != 5) {
//						isPointError = true;
//					} else if (goods.pos == 6 && goods.goodsInfo.amount != 6) {
//						isPointError = true;
//					} else if (goods.pos == 7 && goods.goodsInfo.amount != 6) {
//						isPointError = true;
//					} else if (goods.pos == 9 && goods.goodsInfo.amount != 9) {
//						isPointError = true;
//					} else if (goods.pos == 10 && goods.goodsInfo.amount != 10) {
//						isPointError = true;
//					}
//					if (isEnd) {
//						break;
//					} else if (isPointError) {
//						// 装备位置错误,强制下线。并删除装备
//						iterator.remove();
//						GameUtil.sendSystemMessage(7, chara.name+"玩家被系统检测到装备属性异常被系统#R强制下线。");
//						game.sendOne(new MSG_KICK_OFF(), "你已被强制下线，系统检测到你佩戴的装备异常，请勿利用漏洞严重者永久封号");
//						game.offline(game);
//						break;
//					}
//				}
//
//				// 检测宠物
//				for (Petbeibao pet : chara.pets) {
//					if (pet.id == chara.chongwuchanzhanId) {
//						PetShuXing petShuXing = pet.petShuXing.get(0);
//						int maxPoint = petShuXing.skill * 5;
//						// 如果当前属性大于最大属性值,则认为该宠物异常
//						boolean error = false;
//						if (petShuXing.phy_power > maxPoint || petShuXing.phy_power < 0) {
//							error = true;
//						} else if (petShuXing.mag_power > maxPoint || petShuXing.mag_power < 0) {
//							error = true;
//						} else if (petShuXing.life > maxPoint || petShuXing.life < 0) {
//							error = true;
//						} else if (petShuXing.speed > maxPoint || petShuXing.speed < 0) {
//							error = true;
//						} else if (petShuXing.polar_point < 0) {
//							error = true;
//						}
//						if(petShuXing.mag_rebuild_level > 12) {
//							error = true;
//						}
//						//强化成长
//						Pet petInfo = GameData.that.basePetService.findOneByName(petShuXing.suit_polar);
//						if(petInfo != null) {
//							//如果基础成长超出则恢复
//							if(petShuXing.mana_effect>petInfo.getLife() || petShuXing.attack_effect>petInfo.getMana()
//									|| petShuXing.mag_effect>petInfo.getPhyAttack() || petShuXing.phy_absorb>petInfo.getMagAttack()
//									|| petShuXing.phy_effect>petInfo.getSpeed()) {
//								error = true;
//								petShuXing.mana_effect = petInfo.getLife()-40;
//								petShuXing.pet_mana_shape=petShuXing.mana_effect+40;
//
//								petShuXing.attack_effect = petInfo.getMana()-40;
//								petShuXing.pet_speed_shape=petShuXing.attack_effect+40;
//
//								petShuXing.mag_effect = petInfo.getPhyAttack()-40;
//								petShuXing.pet_mag_shape=petShuXing.mag_effect+40;
//
//								petShuXing.phy_absorb = petInfo.getMagAttack()-40;
//								petShuXing.rank=petShuXing.phy_absorb+40;
//
//								petShuXing.phy_effect = petInfo.getSpeed()-40;
//								petShuXing.pet_phy_shape=petShuXing.phy_effect+40;
//
//								//取消所有状态，强化、点化、羽化、幻化
//								petShuXing.pet_life_shape_temp = 0;
//								petShuXing.raw_name = 0;
//								petShuXing.life_add_temp = 0;
//
//								petShuXing.mag_rebuild_add = 0;
//								petShuXing.mag_rebuild_rate = 0;
//								petShuXing.mag_rebuild_level = 0;
//								//点化
//								petShuXing.enchant_nimbus = 0;
//								petShuXing.max_enchant_nimbus = 0;
//								//飞升
//								petShuXing.limit_use_time = 0;
//								//羽化
//								petShuXing.eclosion_nimbus = 0;
//								petShuXing.max_eclosion_nimbus = 0;
//								//幻化
//								petShuXing.morph_life_times = 0;
//								petShuXing.morph_life_stat = 0;
//								petShuXing.morph_mana_stat = 0;
//								petShuXing.morph_mana_times = 0;
//								petShuXing.morph_speed_stat = 0;
//								petShuXing.morph_speed_times = 0;
//								petShuXing.morph_phy_stat = 0;
//								petShuXing.morph_phy_times = 0;
//								petShuXing.morph_mag_stat = 0;
//								petShuXing.morph_mag_times = 0;
//							}
//						}
//						if (error) {
//							petShuXing.phy_power = petShuXing.skill;
//							petShuXing.mag_power = petShuXing.skill;
//							petShuXing.life = petShuXing.skill;
//							petShuXing.speed = petShuXing.skill;
//							petShuXing.polar_point = petShuXing.skill * 4;
//							BasicAttributesUtils.petshuxing(petShuXing, pet);
//							// 这里是计算妖石伤害
//							for (PetShuXing yaoshi : pet.petShuXing) {
//								// 在宠物的基础信息里面操作
//								if (yaoshi.no >= 12 && yaoshi.no <= 15) {
//									petShuXing.wiz += yaoshi.wiz;
//									petShuXing.parry += yaoshi.parry;
//									petShuXing.def += yaoshi.def;
//									petShuXing.dex += yaoshi.dex;
//									petShuXing.mana += yaoshi.mana;
//									petShuXing.accurate += yaoshi.accurate;
//								}
//							}
//							petShuXing.max_life = petShuXing.def;
//							petShuXing.max_mana = petShuXing.dex;
//							if (petShuXing.suit_light_effect != 0) {
//								if (petShuXing.no == 23) {
//									petShuXing.accurate = 4 * (petShuXing.hide_mount - 1) * petShuXing.skill;
//									petShuXing.mana = 4 * (petShuXing.hide_mount - 1) * petShuXing.skill;
//									petShuXing.wiz = 3 * (petShuXing.hide_mount - 1) * petShuXing.skill;
//								}
//							}
//							game.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
//							GameCommonUtil.sendTips("系统检测到你的宠物属性点异常,已恢复初始化！", game);
//							game.sendOne(new MSG_KICK_OFF(), "你已被强制下线,系统检测到你的宠物属性点异常,已恢复初始化！");
//							game.offline(game);
//							log.error("检测出宠物异常:{},角色:{}",petInfo.getName(),chara.name);
////						}
//						break;
//					}
//				}
//				/**
//				 * 检测相性点是否正常. 检测大于50级以上的
//	//			 */
//				// 当角色的总相性点大于最大上下,直接强制把他恢复初始化
//				ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
//				if (chara.level > 60) {
//					// 当前角色最大相信
//					int maxStamina = GameCommonUtil.getMaxStamina(chara.level);
//					//升级了阶段
//					if(chara.danDataState>1) {
//						maxStamina+=chara.danDataPolarPoint;
//					}
//					// 获取当前称号
//					Chengwei chengwei = cs.getChengweiByName(chara.chenhao);
//					if (chengwei != null && !StringUtils.isNullOrEmpty(chengwei.getAttr())) {
//						// 属性
//						String attrStr = chengwei.getAttr();
//						// 解析属性
//						JSONArray parseArray = JSONObject.parseArray(attrStr);
//						for (int i = 0; i < parseArray.size(); i++) {
//							JSONObject attri = parseArray.getJSONObject(i);
//							String key = attri.getString("field");
//							int value = attri.getIntValue("value");
//							if ("所有相性".equals(key)) {
//								maxStamina += (value * 5);
//							} else if ("金相性".equals(key)) {
//								maxStamina += value;
//							} else if ("木相性".equals(key)) {
//								maxStamina += value;
//							} else if ("水相性".equals(key)) {
//								maxStamina += value;
//							} else if ("火相性".equals(key)) {
//								maxStamina += value;
//							} else if ("土相性".equals(key)) {
//								maxStamina += value;
//							}
//						}
//					}
//					int thisStamina = (chara.wood + chara.water + chara.fire + chara.earth + chara.metal);
//					// if (thisStamina > maxStamina) {
//					// 	GameCommonUtil.sendTips("系统检测到你的相性点异常，已恢复#R初始化#n，多出的部分充公处理，#R请端正游戏态度否则角色将被封闭#n。",
//					// 			game.chara.id);
//					// 	chara.polarPoint = maxStamina;
//					// 	chara.wood = 0;
//					// 	chara.water = 0;
//					// 	chara.fire = 0;
//					// 	chara.earth = 0;
//					// 	chara.metal = 0;
//					// 	GameUtil.sendUpdate(chara);
//					// } else {
//						// 角色单个相性点最大上限
//						int charaSingle = 30 + chara.upgrade_max_polar_extra;
//						// 获取当前称号
//						if (chengwei != null && !StringUtils.isNullOrEmpty(chengwei.getAttr())) {
//							// 属性
//							String attrStr = chengwei.getAttr();
//							// 解析属性
//							JSONArray parseArray = JSONObject.parseArray(attrStr);
//							for (int i = 0; i < parseArray.size(); i++) {
//								JSONObject attri = parseArray.getJSONObject(i);
//	//								String key = attri.getString("field");
//								int value = attri.getIntValue("value");
//								if ("所有相性".equals(key)) {
//									charaSingle += (value * 5);
//								} else if ("金相性".equals(key)) {
//									charaSingle += value;
//								} else if ("木相性".equals(key)) {
//									charaSingle += value;
//								} else if ("水相性".equals(key)) {
//									charaSingle += value;
//								} else if ("火相性".equals(key)) {
//									charaSingle += value;
//								} else if ("土相性".equals(key)) {
//									charaSingle += value;
//								}
//							}
//						}
//						// 金相性超过单个最大上限
//						// if (chara.wood > charaSingle || chara.water > charaSingle || chara.fire > charaSingle
//						// 		|| chara.earth > charaSingle || chara.metal > charaSingle) {
//						// 	GameCommonUtil.sendTips("系统检测到你的相性点异常，已恢复#R初始化#n，多出的部分充公处理，#R请端正游戏态度否则角色将被封闭#n。",
//						// 			game.chara.id);
//						// 	chara.polarPoint = maxStamina;
//						// 	chara.wood = 0;
//						// 	chara.water = 0;
//						// 	chara.fire = 0;
//						// 	chara.earth = 0;
//						// 	chara.metal = 0;
//						// 	GameUtil.sendUpdate(chara);
//						// 	game.sendOne(new MSG_KICK_OFF(), "你已被强制下线,系统检测到你的宠物属性点异常,已恢复初始化！");
//						// 	game.offline(game);
//						// 	continue;
//						// }
//					//}
//				}
//			}
//		}
//	}
}