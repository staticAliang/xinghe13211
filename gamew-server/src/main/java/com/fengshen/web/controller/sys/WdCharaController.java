package com.fengshen.web.controller.sys;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.JSONUtils;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.BlackList;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.service.system.BlackListService;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaBaseInfo;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SaveChara;
import com.fengshen.server.domain.config.TyzqAttribConfig.TyzqAttribVo;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.game.HunqiUtils;
import com.fengshen.server.job.SaveCharaTimes;
import com.fengshen.server.util.BeanUtils;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/wd/chara")
public class WdCharaController extends BaseController {

	@Autowired
	private BlackListService blackListService;

	/**
	 * 获取某个玩家信 息
	 * @param gid 用户uuid
	 * @param type 类型
	 * @return
	 */
	@PostMapping("/getCharaValue")
	public ResponseView getCharaValue(String gid, String type) {
		if(StringUtils.isNullOrEmpty(gid)) {
			ResponseView.fail("gid不能为空");
		}
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(gameObject == null) {
			//数据库查询
			gameObject = new GameObjectChar();
			Characters findOne = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "sex","data","id","level","polar","gid","name");
			if(findOne == null) {
				return ResponseView.ok();
			}
			gameObject.chara = JSONObject.parseObject(findOne.getData(), Chara.class);
			gameObject.chara.setLevel(findOne.getLevel());
			gameObject.chara.setPolar(findOne.getPolar());
			gameObject.chara.setSex(findOne.getSex());
			gameObject.chara.setUuid(findOne.getGid());
			gameObject.chara.setName(findOne.getName());
		}
		Chara chara = gameObject.chara;
		if(StringUtils.isNullOrEmpty(type)) {
			ResponseView.fail("类型不能为空");
		}
		if("task".equals(type)) {
			Map<String,Vo_61553_0> task = new HashMap<>();
			task.putAll(chara.commonTaskMap);
			task.putAll(chara.taskMap);
			return ResponseView.ok(task);
		}else if("pets".equals(type)) {
			List<CharaPet> petsByCid = GameData.that.charaPetService.getPetsByCid(chara.id);
			return ResponseView.ok(petsByCid);
		}else if("back".equals(type)) {
			//背包
			Characters findById = GameData.that.baseCharactersService.findById(chara.id);
			String back = findById.getBackpack();
			List<Goods> backpack = JSONObject.parseArray(back,Goods.class);
			backpack.stream().sorted(Comparator.comparing(Goods::getPos));
			List<GoodsInfo> goodsInfo = new ArrayList<>();
			for(Goods g:backpack) {
				goodsInfo.add(g.goodsInfo);
			}
			return ResponseView.ok(goodsInfo);
		}else if("charaInfo".equals(type)) {
			//获取玩家信息
			Map<String,Object> data = new HashMap<>();
			data.put("afterChara", GameUtil.a65527(chara).vo_65527_0);
			Chara clone = BeanUtils.clone(chara);
			clone.backpack = null;
			clone.cangku = null;
			clone.customShizhuang = null;
			data.put("chara", clone);
			return ResponseView.ok(data);
		}else if("xiaoziCharaInfo".equals(type)) {
			//获取玩家信息
			Map<String,Object> data = new HashMap<>();

			//队伍信息
			if(gameObject != null && gameObject.gameTeam != null) {
				List<Map<String,Object>> teamList = new ArrayList<>();
				for(Chara team:gameObject.gameTeam.duiwu) {
					Map<String,Object> teamMap = new HashMap<>();
					teamMap.put("uuid", team.getUuid());
					teamMap.put("id", team.getId());
					teamMap.put("name", team.getName());
					teamList.add(teamMap);
				}
				data.put("teams", teamList);
				//队伍申请人员
			}
			Map<String,Object> charaMap = new HashMap<>();
			charaMap.put("x", chara.x);
			charaMap.put("y", chara.y);
			charaMap.put("name", chara.name);
			charaMap.put("action", gameObject.action);
			data.put("chara", charaMap);
			return ResponseView.ok(data);
		}
		return ResponseView.ok();
	}

	@PostMapping("/updateValue")
	public ResponseView updateValue(String type, @RequestParam Map<String,Object> data) {

		if(data.get("gid") == null) {
			ResponseView.fail("gid为空");
		}
		int online = 0;
		String gid = (String) data.get("gid");
		Chara chara = null;
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(gameObject == null) {
			Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGidSelectProperties((String) data.get("gid"),"data");
			chara = JSONObject.parseObject(findOneByGid2.getData(),Chara.class);
		}else {
			online = 1;
			chara = gameObject.chara;
		}
		//删除任务
		if("delTask".equals(type)) {
			//有些任务是要实时通知的
			switch ((String)data.get("value")) {
				case "千变万化":
					chara.changeCardInfo = null;
					if(online == 1) {
						gameObject.gameMap.send( new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(chara));
					}
					break;
				default:
					break;
			}
			if(online == 1) {
				GameUtilRenWu.removeTask((String)data.get("value"), gameObject.chara);
				GameCommonUtil.sendTips("GM已帮你清除【#Y"+data.get("value")+"#n】任务！", chara.id);
			}
			gameObject.chara.taskMap.remove(data.get("value"));
			gameObject.chara.commonTaskMap.remove(data.get("value"));
		}else if("delBack".equals(type)) {
			if(online == 1) {
				GameUtil.removemunber(chara, (String)data.get("value"), 1);
				GameCommonUtil.sendTips("GM删除了你背包中的【#Y"+data.get("value")+"#n】物品！", chara.id);
			}
		}else if("updateCharaInfo".equals(type)) {
			data.remove("type");
			data.remove("isFeisheng");
			data.remove("lock_exp");
			data.remove("gid");
			data.remove("chengwei");
			data.remove("xiangxingshangxian");
			data.remove("max_life");
			data.remove("dex");
			data.remove("accurate");
			data.remove("mana");
			data.remove("wiz");
			data.remove("parry");
			data.remove("qumoxiang");
			//修改数据
			for(Entry<String, Object> value:data.entrySet()) {
				try {
					Field field = chara.getClass().getField(value.getKey());
					if(field != null) {
						if(Utils.isNumber((String) value.getValue())) {
							field.set(chara, Integer.valueOf((String) value.getValue()));
						}else {
							field.set(chara, value.getValue());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					ResponseView.fail("系统异常");
				}
			}
			if(chara.upgrade_state == 0) {
				chara.level = chara.realLevel;
			}else {
				chara.level = chara.upgrade_level;
			}
			if(online == 1) {
				//这里重置属性.如果当前是元婴状态.
				GameCommonUtil.resetDefaultAttr(gameObject);
			}
		}else if("updateCharaInfoToFeisheng".equals(type)) {
			chara.setIsFeisheng(Integer.valueOf((String) data.get("isFeisheng")));
			if(online == 1) {
				//刷新人物信息
				GameUtil.sendUpdate(chara);
			}
		}else if("updateCharaInfoToQuMoXiang".equals(type)) {
			chara.setQumoxiang(Integer.valueOf((String) data.get("qumoxiang")));
			if(online == 1) {
				Vo_GENERAL_NOTIFY vo_9129_53 = new Vo_GENERAL_NOTIFY();
				vo_9129_53.notify = 20010;
				vo_9129_53.para = (String) data.get("qumoxiang");
				gameObject.sendOne(new MSG_GENERAL_NOTIFY(), vo_9129_53);
			}
		}else if("resetCharaAttrInfo".equals(type)) {
			if(online == 1) {
				GameCommonUtil.resetDefaultAttr(gameObject);
			}
		}
		if(online == 0) {
			Characters update = new Characters();
			update.setId(chara.getId());
			// 如果是元婴
			CharaBaseInfo setInfo = SaveCharaTimes.setInfo(chara);
			if (chara.upgrade_state != 0) {
				chara.charaYuanyingInfo = setInfo;
				chara.level = chara.upgrade_level;
			} else {
				chara.charaRealInfo = setInfo;
				chara.realLevel = chara.level;
			}
			// 把角色需要的信息复制到这个对象中
			SaveChara saveChara = com.fengshen.server.util.BeanUtils.clone(chara, SaveChara.class);
			String jsonString = JSONObject.toJSONString(saveChara);
			update.setData(jsonString);
			// 以下设置其他信息
			GameData.that.baseCharactersService.updateById(update);
		}else if("deleteChara".equals(type)) {
			//删除玩家角色,如果角色在线。先把他t下线
			if(gameObject.ctx != null) {
				gameObject.sendOne(new MSG_KICK_OFF(), "角色已被GM，强制注销！");
				gameObject.ctx.close();
			}
			GameData.that.baseCharactersService.deleteById(chara.id);
			//删除这人的所有宝宝
			Example example = new Example(CharaPet.class);
			example.createCriteria().andEqualTo("cid", chara.id);
			GameData.that.charaPetService.deleteByExample(example);
		}
		return ResponseView.ok();
	}

	/**
	 * 更新某个宠物.
	 * @param petShuXing
	 * @return
	 * @throws
	 * @throws Exception
	 */
	@PostMapping("/updatePet")
	public ResponseView updatePet(String gid, Integer id, PetShuXing petShuXing) throws Exception {

		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(gameObject == null) {
			ResponseView.fail("该用户不在线，无法操作");
		}
		Chara chara = gameObject.chara;
		Field[] fields = petShuXing.getClass().getFields();
		for(Petbeibao p:chara.pets) {
			if(p.id == id) {
				PetShuXing petShuXing2 = p.petShuXing.get(0);
				for(Field f:fields) {
					if(f.getName().equals("type1")) {
						continue;
					}
					if(f.get(petShuXing) instanceof String) {
						String s = (String) f.get(petShuXing);
						if(s != null && !s.equals("")) {
							petShuXing2.getClass().getField(f.getName()).set(petShuXing2, s);
						}
					}else if(f.get(petShuXing) instanceof Integer) {
						if(f.getInt(petShuXing) != 0) {
							petShuXing2.getClass().getField(f.getName()).setInt(petShuXing2, f.getInt(petShuXing));
							if(f.getName().equals("fasion_id")) {
								petShuXing2.fasion_visible = 0;
								petShuXing2.fasion_id = petShuXing2.getType();
							}
						}else if(f.getName().equals("skillRange")) {
							petShuXing2.getClass().getField(f.getName()).setInt(petShuXing2, f.getInt(petShuXing));
						}
					}
				}
				gameObject.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(p));
				break;
			}
		}

		return ResponseView.ok();
	}

	/**
	 * 删除玩家某个宠物
	 * @param data
	 * @return
	 */
	@PostMapping("/delPet")
	public ResponseView delPet(String gid, Integer id) throws Exception {
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if(gameObject != null) {
			Chara chara = gameObject.chara;
			Iterator<Petbeibao> iterator = chara.pets.iterator();
			while(iterator.hasNext()) {
				Petbeibao next = iterator.next();
				if(next.id == id) {
					Vo_12269_0 vo_12269_0 = new Vo_12269_0();
					vo_12269_0.id = next.id;
					vo_12269_0.owner_id = 0;
					GameObjectChar.send(new M12269_0(), vo_12269_0, gameObject.chara.id);
					//数据库删除
					GameData.that.charaPetService.deleteByPrimaryKey(next.id);
					iterator.remove();
					break;
				}
			}
		}else {
			GameData.that.charaPetService.deleteByPrimaryKey(id);
		}
		return ResponseView.ok();
	}

	@PostMapping("/customIcon")
	public ResponseView customIcon(@RequestParam Map<String,Object> data) {
		if(data.get("charaName") == null) {
			ResponseView.fail("gid为空");
		}
		String ic = (String) data.get("charaName");
		Chara chara = getChara(ic);
		if(chara == null) {
			ResponseView.fail("玩家不在线无法操作");
		}
		if(data.get("endTime") == null) {
			ResponseView.fail("结束时间不能为空");
		}
		if(data.get("name") == null) {
			ResponseView.fail("名字不能为空");
		}
		if(data.get("icon") == null) {
			ResponseView.fail("外观不能为空");
		}

		int endTime = Integer.valueOf(String.valueOf(data.get("endTime")));
		//先删除之前的定时器
		GameData.that.redisUtils.delete(DefinedConst.CHANGE_CARD+":"+chara.uuid);
		VoChangeCard voCard = new VoChangeCard();
		voCard.setIcon(Integer.valueOf(String.valueOf(data.get("icon"))));
		voCard.setType(0);
		voCard.setLevel(0);
		voCard.setName((String)data.get("name"));
		chara.changeCardInfo = voCard;
		int endTime2 =  (int) ((System.currentTimeMillis()/1000L + endTime*60*60)-60);;
		//播放动画效果
		GameCommonUtil.charaPlay(GameObjectCharMng.getGameObjectChar(chara.id), 1261, 1);
		//增加千变万化
		final Vo_61553_0 vo_61553_2 = new Vo_61553_0();
		vo_61553_2.count = 1;
		vo_61553_2.task_type = "千变万化";
		vo_61553_2.task_desc = "你使用了#R"+data.get("name")+"#W，变身效果持续时间还剩余#RTIME_LEFT#n，此效果下线后不消失，但仍然计时。";
		vo_61553_2.task_prompt = data.get("name")+"还剩余#RTIME_LEFT";
		vo_61553_2.refresh = 1;
		vo_61553_2.task_end_time = endTime2;
		vo_61553_2.attrib = 1;
		vo_61553_2.reward = "";
		vo_61553_2.show_name = "千变万化";
		vo_61553_2.task_extra_para = "";
		vo_61553_2.task_state = "0";
		GameUtilRenWu.createTask(vo_61553_2, chara);
		//任务保存redis中
		GameData.that.redisUtils.set(DefinedConst.CHANGE_CARD+";"+chara.uuid, data.get("name"), endTime*60*60);
		//刷新地图数据--让所有人都能看到
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectCharMng.getGameObjectChar(chara.id).gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		GameCommonUtil.sendTips("GM为你使用了#Y"+data.get("name"), chara.id);
		return ResponseView.ok();
	}

	/**
	 *  重置所有玩家日常任务
	 * @return
	 */
	@PostMapping("/resetAllToDayTask")
	public ResponseView resetAllToDayTask() {
		int excuteItem = 0;
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		// 在线用户ids
		List<String> isExistGids = new ArrayList<>();
		// 获取在线用户
		for (GameObjectChar g : all) {
			isExistGids.add(g.characters.getGid());
			// 对他们进行重置任务
			GameUtil.resetRenwuByChara(g.chara);
			excuteItem++;
		}
		List<Characters> selectAll = GameData.that.characterService.getResetRenwu(isExistGids);
		for (Characters c : selectAll) {
			SaveChara chara = null;
			try {
				chara = JSONObject.parseObject(c.getData(), SaveChara.class);
			} catch (Exception e) {
				excuteItem--;
				try {
					chara = JSONUtils.parseObject(c.getData(), SaveChara.class);
				} catch (Exception e2) {
				}
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
		return ResponseView.ok(excuteItem);
	}

	/**
	 * 重置日常任务
	 * @param name 角色名
	 * @param taskNames 日常任务
	 * @return
	 */
	@PostMapping("/resetToDayTask")
	public ResponseView resetToDayTask(String uuid, String taskNames) {
		GameObjectChar chara = getCharaByGid(uuid);
		int updateCount = 0;
		if(chara == null) {
			Characters characters = GameData.that.baseCharactersService.findOneByGidSelectProperties(uuid, "data","id");
			Chara c = JSONObject.parseObject(characters.getData(), Chara.class);
			resetTaskObject(c,taskNames);
			Characters update = new Characters();
			update.setId(c.getId());
			update.setData(JSONUtils.toJSONString(c));
			updateCount = GameData.that.baseCharactersService.updateById(update);
		}else {
			resetTaskObject(chara.chara, taskNames);
			updateCount = 1;
			GameCommonUtil.sendTips("GM已为你重置日常任务.",chara.chara.id);
		}
		return ResponseView.ok(updateCount);
	}

	/**
	 * 设置重置任务信息
	 * @param chara
	 * @param taskName
	 */
	private void resetTaskObject(Chara chara, String taskName) {
		String[] task = taskName.split(",");
		for(String t:task) {
			if("shuadao".equals(t)) {
				//刷道
				chara.shuadao = 1;
			}else if("chubao".equals(t)) {
				// 除暴
				chara.chubao = 1;
			}else if("baibangmang".equals(t)) {
				//白帮忙
				chara.baibangmang = 0;
			}else if("shimencishu".equals(t)) {
				// 师门
				chara.shimencishu = 1;
			}else if("fabaorenwu".equals(t)) {
				//法宝任务
				chara.fabaorenwu = 0;
			}else if("xiuxingcishu".equals(t)) {
				// 修行次数
				chara.xiuxingcishu = 1;
			}else if("xuanshangcishu".equals(t)) {
				// 悬赏
				chara.xuanshangcishu = 0;
			}else if("zhangmentiaozhan".equals(t)) {
				//掌门
				chara.zhangmentiaozhan = 0;
			}else if("baxiantiaozhan".equals(t)) {
				//八仙
				chara.baxiantiaozhan = 0;
			}else if("fb_num".equals(t)) {
				//副本
				chara.fb_num = 0;
			}else if("mapguardcishu".equals(t)) {
				//挑战地图守护神的次数
				chara.mapguardcishu = 0;
			}else if("zhengdaodiancishu".equals(t)) {
				// 挑战证道殿的次数
				chara.zhengdaodiancishu = 0;
			}else if("heropubcishu".equals(t)) {
				// 挑战英雄会次数
				chara.heropubcishu = 0;
			}else if("gongchengcishu".equals(t)) {
				// 攻城BOSS刷的次数
				chara.gongchengcishu = 0;
			}else if("zhanshencishu".equals(t)) {
				// 战神的次数
				chara.zhanshencishu = 0;
			}else if("haidaocishu".equals(t)) {
				// 海盗的次数
				chara.haidaocishu = 0;
			}else if("shanggucishu".equals(t)) {
				// 上古的次数
				chara.shanggucishu = 0;
			}else if("wanniancishu".equals(t)) {
				// 万年的次数
				chara.wanniancishu = 0;
			}else if("xiufacishu".equals(t)) {
				// 修法任务次数
				chara.xiufacishu = 0;
			}else if("tongttcishu".equals(t)) {
				// 通天塔
				chara.tongttcishu = 0;
			}else if("superBossNum".equals(t)) {
				//超级boss
				chara.superBossNum = 0;
			}else if("partyFightNum".equals(t)) {
				//帮派日常挑战
				chara.partyFightNum = 0;
			}else if("partyNum".equals(t)) {
				//帮派任务
				chara.partyNum = 0;
			}else if("newYearBeastNum".equals(t)) {
				chara.newYearBeastNum = 0;
			}
		}
	}

	/**
	 * 对玩家一键拉黑
	 * @param gid
	 * @return
	 */
	@PostMapping("/fastAddBlackList")
	public ResponseView fastAddBlackList(String gid) {
		GameObjectChar charaByGid = getCharaByGid(gid);
		Characters characters = null;
		if(charaByGid == null) {
			//不在线去数据库查询
			Example example = new Example(Characters.class);
			example.selectProperties("id","accountId","name","gid");
			example.createCriteria().andEqualTo("gid", gid);
			characters = GameData.that.baseCharactersService.selectOneByExample(example);
		}else {
			characters = charaByGid.characters;
		}
		//查询账号信息
		Accounts account = GameData.that.baseAccountsService.findById(characters.getAccountId());
		if(account != null) {
			account.setDeleted(true);
			account.setUpdateTime(LocalDateTime.now());
			GameData.that.baseAccountsService.updateById(account);
			//设备拉黑
			BlackList b = new BlackList();
			if(account.getMac() != null) {
				b.setData(account.getMac());
				b.setAddTime(new Date());
				blackListService.insertSelective(b);
			}
			//ip拉黑
			if(account.getLastLoginIp()!= null) {
				b = new BlackList();
				b.setData(account.getLastLoginIp());
				b.setAddTime(new Date());
				blackListService.insertSelective(b);
			}
			//注册码
			if(account.getRegisterIp() != null) {
				b = new BlackList();
				b.setData(account.getRegisterIp());
				b.setAddTime(new Date());
				blackListService.insertSelective(b);
			}
			if(charaByGid == null) {
				characters.setBlock(1);
				characters.setUpdateTime(new Date());
				GameData.that.baseCharactersService.updateById(characters);
			}
		}
		if(charaByGid != null) {
			//发送消息
			GameUtil.sendSystemMessage(7, "#Y"+charaByGid.chara.name+"#n玩家被GM封号");
			charaByGid.sendOne(new MSG_KICK_OFF(), "对不起您违反了游戏的公平,角色已被封。");
			charaByGid.characters.setBlock(1);
			charaByGid.offline();
			GameObjectCharMng.getGameObjectCharList().remove(charaByGid);

		}
		return ResponseView.ok();
	}

	/**
	 * 发送首饰
	 * @param data
	 * @return
	 */
	@PostMapping("/sendShouShi")
	public ResponseView sendShouShi(@RequestParam Map<String,Object> data) {
		//判断是否有重复数据
		List<String> names = new ArrayList<>();
		//Map attrNames = new HashMap<>();
		//List attrs = Arrays.asList("所有相性","力量","体质","敏捷","灵力","所有属性","所有技能上升");
		//List fieldList = new ArrayList();
		Map<String,Integer> fields = new HashMap<>();
		if(!data.get("ssAttr1").equals("")) {
			names.add((String)data.get("ssAttr1"));
			if(!data.get("ssVal1").equals("")) {
				fields.put((String)data.get("ssAttr1"), Integer.valueOf((String)data.get("ssVal1")));
			}
		}
		if(!data.get("ssAttr2").equals("")) {
			names.add((String)data.get("ssAttr2"));
			if(!data.get("ssVal2").equals("")) {
				fields.put((String)data.get("ssAttr2"), Integer.valueOf((String)data.get("ssVal2")));
			}
		}
		if(!data.get("ssAttr3").equals("")) {
			names.add((String)data.get("ssAttr3"));
			if(!data.get("ssVal3").equals("")) {
				fields.put((String)data.get("ssAttr3"), Integer.valueOf((String)data.get("ssVal3")));
			}
		}
		if(!data.get("ssAttr4").equals("")) {
			names.add((String)data.get("ssAttr4"));
			if(!data.get("ssVal4").equals("")) {
				fields.put((String)data.get("ssAttr4"), Integer.valueOf((String)data.get("ssVal4")));
			}
		}
		if(!data.get("ssAttr5").equals("")) {
			names.add((String)data.get("ssAttr5"));
			if(!data.get("ssVal5").equals("")) {
				fields.put((String)data.get("ssAttr5"), Integer.valueOf((String)data.get("ssVal5")));
			}
		}
		if(!data.get("ssAttr6").equals("")) {
			names.add((String)data.get("ssAttr6"));
			if(!data.get("ssVal6").equals("")) {
				fields.put((String)data.get("ssAttr6"), Integer.valueOf((String)data.get("ssVal6")));
			}
		}
		if(!data.get("ssAttr7").equals("")) {
			names.add((String)data.get("ssAttr7"));
			if(!data.get("ssVal7").equals("")) {
				fields.put((String)data.get("ssAttr7"), Integer.valueOf((String)data.get("ssVal7")));
			}
		}
		if(!data.get("ssAttr8").equals("")) {
			names.add((String)data.get("ssAttr8"));
			if(!data.get("ssVal8").equals("")) {
				fields.put((String)data.get("ssAttr8"), Integer.valueOf((String)data.get("ssVal8")));
			}
		}
		if(!data.get("ssAttr9").equals("")) {
			names.add((String)data.get("ssAttr9"));
			if(!data.get("ssVal9").equals("")) {
				fields.put((String)data.get("ssAttr9"), Integer.valueOf((String)data.get("ssVal9")));
			}
		}
		if(names.isEmpty()) {
			ResponseView.fail("至少选中一条属性");
		}

//		for (String name : names) {
//			if(attrNames.containsKey(name)){
//				if(attrs.contains(name)){
//					int num = Integer.parseInt(attrNames.get(name).toString());
//					if(num == 1){
//						attrNames.put(name,"2");
//					}else{
//						ResponseView.fail("相同属性最多存在两条");
//					}
//				}else{
//					ResponseView.fail("不允许有重复属性");
//				}
//			}else{
//				attrNames.put(name,"1");
//			}
//
//		}
		boolean isRepeat = names.size() != new HashSet<String>(names).size();
		if(isRepeat) {
			ResponseView.fail("不允许有重复属性");
		}
		if(data.get("ss_name").equals("")) {
			ResponseView.fail("请选择要发送的首饰");
		}
		//获取属性信息
		Chara chara = getChara((String)data.get("charaName"));
		ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr((String)data.get("ss_name"));
		if (zhuangbeiInfo2 == null) {
			ResponseView.fail("未找到该首饰");
		}
		//创建goods
		GoodsLanSe goods = new GoodsLanSe();
		for(Entry<String, Integer> d:fields.entrySet()) {
			//取值
			try {
				String fieldName = ForgingEquipmentUtils.getEquipmentKeyByName(d.getKey());
				//判断是否超过最大值
				int maxVal = ForgingEquipmentUtils.getMaxValueByChineseName(d.getKey(), zhuangbeiInfo2.getAttrib(), false);
				int val = d.getValue();
				if(d.getValue() > maxVal) {
					val = maxVal;
				}
				goods.getClass().getField(fieldName).set(goods, val);
			} catch (Exception e) {
				e.printStackTrace();
				ResponseView.fail("{}");
			}
		}
		GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, goods);
		GameCommonUtil.sendTips("GM给你发送了一个#R"+(String)data.get("ss_name"), chara.id);
		return ResponseView.ok();
	}

	/**
	 * 发送首饰
	 * @param data
	 * @return
	 */
	@PostMapping("/sendHunQi")
	public ResponseView sendHunQi(@RequestParam Map<String,Object> data) {

		String name = (String)data.get("name");
		if(name == null) {
			ResponseView.fail("请选择魂器");
		}
		if(!name.equals("魂器·锋芒") && !name.equals("魂器·魂灯") && !name.equals("魂器·鬼步") && !name.equals("魂器·润泽")
				&& !name.equals("魂器·薄暮") && !name.equals("魂器·轮回") && !name.equals("魂器·伏虎")
				&& !name.equals("魂器·双极") && !name.equals("魂器·灵咒") ) {
			ResponseView.fail("不存在该魂器");
		}
		//魂器等级能低于75或者是高于135
		if(data.get("level").equals("")) {
			ResponseView.fail("请输入正确的等级");
		}
		int level = Integer.valueOf((String)data.get("level"));
		if(level<75 || level>179) {
			ResponseView.fail("魂器等级最低不能低于75级，最高不能高于179级");
		}
		//判断属性是否重复
		List<String> yangList = new ArrayList<String>();
		for(Entry<String, Object> d:data.entrySet()) {
			if(d.getKey().startsWith("yangKey")) {
				//提取key里面的数字
				int index = Utils.findNumber(d.getKey());
				if(!data.get("yangVal"+index).equals("")) {
					yangList.add((String) d.getValue());
					//判断阴属性是否为空
					if(data.get("yinKey"+index).equals("") || data.get("yinVal"+index).equals("")) {
						ResponseView.fail("阳值和<font color='yellow'>阴</font>值必须成对！");
					}
				}
			}
		}
		Map<String, Long> yangCount = yangList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for(Entry<String, Long> yc:yangCount.entrySet()) {
			if(yc.getValue()>2) {
				ResponseView.fail("阳属性重复数最大为2条");
			}
		}
		//阴属性
		List<String> yinList = new ArrayList<>();
		for(Entry<String, Object> d:data.entrySet()) {
			if(d.getKey().startsWith("yinKey")) {
				//提取key里面的数字
				int index = Utils.findNumber(d.getKey());
				if(!data.get("yinVal"+index).equals("") && !d.getValue().equals("")) {
					yinList.add((String) d.getValue());
					//判断阳属性是否为空
					if(data.get("yangKey"+index).equals("") || data.get("yangVal"+index).equals("")) {
						ResponseView.fail("阳值和<font color='yellow'>阴</font>值必须成对！");
					}
				}
			}
		}
		Map<String, Long> yinCount = yinList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		for(Entry<String, Long> yc:yinCount.entrySet()) {
			if(yc.getValue()>2) {
				ResponseView.fail("<font color='yellow'>阴</font>属性最多只能2条重复属性");
			}
		}

		//获取属性信息
		Chara chara = getChara((String)data.get("charaName"));
		ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr(name);
		if (zhuangbeiInfo2 == null) {
			ResponseView.fail("未找到该魂器");
		}
		//开始创建魂器
		List<Hashtable<String, Object>> attrs = HunqiUtils.chuShihua();
		//阳
		JSONObject yangJsonObject = GameCore.hunqiYang.get(String.valueOf(level));
		JSONObject yinJsonObject = GameCore.hunqiYin.get(String.valueOf(level));
		//循环创建魂器阳属性
		for(Entry<String, Object> d:data.entrySet()) {
			if(d.getKey().startsWith("yangKey")) {
				//提取key里面的数字
				int index = Utils.findNumber(d.getKey());
				if(!d.getValue().equals("") && !data.get("yangVal"+index).equals("")) {
					Hashtable<String, Object> hashtable = attrs.get(index-1);
					int maxYangValue = yangJsonObject.getIntValue((String)d.getValue());
					int yangValue = Integer.valueOf((String) data.get("yangVal"+index));
					if(yangValue>maxYangValue) {
						yangValue = maxYangValue;
					}
					//根据序号获取阴值
					String yinKey = (String) data.get("yinKey"+index);
					int maxYinValue = yinJsonObject.getIntValue(yinKey);
					int yinValue = Integer.valueOf((String) data.get("yinVal"+index));
					if(yinValue>maxYinValue) {
						yinValue = maxYinValue;
					}
					if(hashtable != null) {
						hashtable.put("yang_percent", 90);
						hashtable.put("yang_prop", (String)d.getValue());
						hashtable.put("yang_prop_value", yangValue);
						hashtable.put("yin_prop", yinKey);
						hashtable.put("yin_prop_value", yinValue);
						hashtable.put("chaos_value", 90);
					}
				}
			}
		}
		GameCommonUtil.integral_horcrux(chara, name, level, attrs);
		GameCommonUtil.sendTips("GM给你发送了一个#R"+name, chara.id);
		return ResponseView.ok();
	}

	/**
	 * 获取定位信息
	 * @param name 玩家姓名
	 * @return
	 */
	@PostMapping("/getPointInfo")
	public ResponseView getPointInfo(String name) {
		GameObjectChar gameObjectChar = getGameObjectChar(name);
		Map<String,Object> map = new HashMap<>();
		map.put("x", gameObjectChar.chara.x);
		map.put("y", gameObjectChar.chara.y);
		map.put("map", gameObjectChar.chara.mapName);
		return ResponseView.ok(map);
	}

	/**
	 * 清理超时玩家
	 * @param level 等级
	 * @param day 天数
	 * @return
	 */
	@PostMapping("/clearTimeoutCharas")
	public ResponseView clearTimeoutCharas(Integer level, Integer day) {
		if(level == null) {
			ResponseView.fail("等级不能为空");
		}else if(day == null) {
			ResponseView.fail("天数不能为空");
		}
		List<Characters> characterss = GameData.that.baseCharactersService.getLastLoginTimeData(level, day);
		if(!characterss.isEmpty()) {
			//开始删除
			for(Characters ch:characterss) {
				GameData.that.baseCharactersService.deleteByPrimaryKey(ch.getId());
				//删除宠物信息
				Example petExample = new Example(CharaPet.class);
				petExample.createCriteria().andEqualTo("cid", ch.getId());
				GameData.that.charaPetService.deleteByExample(petExample);
			}
		}
		return ResponseView.ok(characterss.size());
	}

	/**
	 * 获取清离数量
	 * @param level
	 * @param day
	 * @return
	 */
	@PostMapping("/getClearTimeoutCharaCount")
	public ResponseView getClearTimeoutCharaCount(Integer level, Integer day) {
		if(level == null) {
			ResponseView.fail("等级不能为空");
		}else if(day == null) {
			ResponseView.fail("天数不能为空");
		}
		List<Characters> characterss = GameData.that.baseCharactersService.getLastLoginTimeData(level, day);
		return ResponseView.ok(characterss.size());
	}

	/**
	 * 对某个角色进行禁言
	 * @param gid 角色gid
	 * @param time 时间(分)
	 * @return
	 */
	@PostMapping("/shutChara")
	public ResponseView shutChara(String gid, Integer time) {
		GameObjectChar charaByGid = getCharaByGid(gid);
		Chara chara = charaByGid.chara;
		//如果不是永久
		if(time>0) {
			chara.shut = 1;
			GameData.that.redisUtils.set("SHUT_CHARA:"+gid, time, time*60);
			GameUtil.sendSystemMessage(7, "玩家#Y"+chara.name+"#n被GM#n禁言#R"+time+"#n分钟");
			GameCommonUtil.sendTips("你已被GM禁言#R"+time+"#n分钟",chara.id);
		}else {
			chara.shut = 2;
			GameUtil.sendSystemMessage(7, "玩家#Y"+chara.name+"#n被GM禁言");
			GameCommonUtil.sendTips("你已被GM永久禁言",chara.id);
		}
		Characters c = new Characters();
		c.setId(charaByGid.characters.getId());
		c.setShut(chara.shut);
		GameData.that.baseCharactersService.updateById(c);
		return ResponseView.ok();
	}


	/**
	 * 发送首饰
	 * @param data
	 * @return
	 */
	@PostMapping("/sendHunQiao")
	public ResponseView sendHunQiao(@RequestParam Map<String,Object> data) {
		String color = (String) data.get("char_zb_ss_type");
		if(color.equals("")) {
			ResponseView.fail("请选择颜色");
		}
		//判断是否有重复数据
		List<String> names = new ArrayList<>();
		Map<String,Integer> fields = new HashMap<>();
		if(!data.get("ssAttr1").equals("")) {
			names.add((String)data.get("ssAttr1"));
			if(!data.get("ssVal1").equals("")) {
				fields.put((String)data.get("ssAttr1"), Integer.valueOf((String)data.get("ssVal1")));
			}
		}
		if(!data.get("ssAttr2").equals("")) {
			names.add((String)data.get("ssAttr2"));
			if(!data.get("ssVal2").equals("")) {
				fields.put((String)data.get("ssAttr2"), Integer.valueOf((String)data.get("ssVal2")));
			}
		}
		if(!data.get("ssAttr3").equals("")) {
			names.add((String)data.get("ssAttr3"));
			if(!data.get("ssVal3").equals("")) {
				fields.put((String)data.get("ssAttr3"), Integer.valueOf((String)data.get("ssVal3")));
			}
		}
		if(names.isEmpty()) {
			ResponseView.fail("至少选中一条属性");
		}
		boolean isRepeat = names.size() != new HashSet<String>(names).size();
		if(isRepeat) {
			ResponseView.fail("不允许有重复属性");
		}
		//获取属性信息
		GameObjectChar gameObjectChar = getGameObjectChar((String)data.get("charaName"));
		Chara chara = gameObjectChar.chara;
		ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr("太阴之气");
		if (zhuangbeiInfo2 == null) {
			ResponseView.fail("未找到该物品");
		}
		//创建goods
		GoodsLanSe goodsLanSe = new GoodsLanSe();
		for(Entry<String, Integer> d:fields.entrySet()) {
			//取值
			try {
				String fieldName = ForgingEquipmentUtils.getErrorFieldByOriginField(d.getKey(),false);
				TyzqAttribVo tyzqAttribVo = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(d.getKey());
				//判断是否超过最大值
				int maxVal = tyzqAttribVo.getPropMaxValue();
				int val = d.getValue();
				if(d.getValue() > maxVal) {
					val = maxVal;
				}
				goodsLanSe.getClass().getField(fieldName).set(goodsLanSe, val);
			} catch (Exception e) {
				e.printStackTrace();
				ResponseView.fail("{}");
			}
		}
		ZhuangbeiInfo zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr("太阴之气");
		List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
		int pos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
		if (pos == -1) {
			return ResponseView.fail("玩家包裹不足");
		}
		Goods goods = new Goods();
		goods.pos = pos;
		goods.goodsLanSe = goodsLanSe;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.total_score = 31;
		goods.goodsInfo.quality = color;
		goods.goodsInfo.type = 2136;
		if(color.equals("粉色")) {
			goods.goodsInfo.type = 2137;
		}else if(color.equals("金色")){
			goods.goodsInfo.type = 2138;
		}
		//唯一码
		goods.goodsInfo.damage_sel_rate = pos;
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = 0;
		chara.tyzqStore.add(goods);
		//刷新仓库
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.list = chara.tyzqStore;
		vo_61677_0.store_type = "tyzq_store";
		gameObjectChar.sendOne(new M61677_0(), vo_61677_0);

		GameCommonUtil.sendTips("GM给你发送了一个#R太阴之气#n。", chara.id);
		return ResponseView.ok();
	}

	/**
	 * 获取角色队伍战斗状态
	 * @param name
	 * @return
	 */
	@PostMapping("/getFightTeamStatus")
	public ResponseView getFightStatus(String name) {
		GameObjectChar charaByGid = getGameObjectChar(name);
		Chara chara = charaByGid.chara;
		FightContainer fightContainer = FightManager.getFightContainer(chara.id);
		List<Map<String,Object>> info = new ArrayList<>();
		Map<String,Object> data = new HashMap<>();
		if(fightContainer != null) {
			FightTeam fightTeam = FightManager.getFightTeam(fightContainer,chara.id);
			List<FightObject> fightObjectList = fightTeam.fightObjectList;
			for(FightObject fightObject:fightObjectList) {
				if(fightObject.type == 1 ) {
					GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightObject.fid);
					Map<String,Object> in = new HashMap<>();
					in.put("name", fightObject.getStr());
					in.put("dead", fightObject.isDead());
					in.put("status", gameObjectChar.isEndRound.get());
					in.put("back", gameObjectChar.isBack.get());
					info.add(in);
				}
			}
			data.put("fightObjects", info);
			data.put("round",fightContainer.round);
			data.put("time", fightContainer.startTime);
		}
		return ResponseView.ok(data);
	}
}