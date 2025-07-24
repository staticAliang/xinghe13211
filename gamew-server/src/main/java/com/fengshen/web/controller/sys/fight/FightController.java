package com.fengshen.web.controller.sys.fight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.DateUtil;
import com.fengshen.core.util.ResponseView;
import com.fengshen.server.data.constant.FightActionType;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightRecord;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.web.controller.BaseController;
import com.qcloud.cos.utils.StringUtils;

@RestController
@RequestMapping("/fight")
public class FightController extends BaseController {

	/**
	 * 具体详情
	 * @param name
	 * @return
	 */
	@RequestMapping("/getFightInfoDetails")
	public ResponseView getFightInfoDetails(String uid) {
		Map<String,Object> result = new HashMap<>();
		List<FightRecord> frs = new ArrayList<>();
		result.put("roundNum", 0);
		if(!StringUtils.isNullOrEmpty(uid)) {
			FightContainer fightContainer = FightManager.getFightContainerByUid(uid);
			if(fightContainer != null) {
				Map<Integer, List<FightRecord>> fightRecords = fightContainer.fightRecords;
				for(Map.Entry<Integer, List<FightRecord>> f:fightRecords.entrySet()) {
					for(FightRecord fr:f.getValue()) {
						if(fr.getPara() instanceof Integer) {
							int para = (int)fr.getPara();
							if(fr.getAction().equals(3)) {
								org.json.JSONObject skill = PetAndHelpSkillUtils.jsonArray(para);
								if(skill != null) {
									fr.setPara(skill.getString("skillName"));
								}
							}
						}
						if(fr.getAction() instanceof Integer) {
							String valueByKey = FightActionType.getValueByKey((int) fr.getAction());
							fr.setAction(valueByKey);
						}
					}
				}
				Collection<List<FightRecord>> values = fightContainer.fightRecords.values();
				for(List<FightRecord> v:values) {
					for(FightRecord fr:v) {
						frs.add(fr);
					}
				}
				result.put("roundNum", fightContainer.round);
			}
		}
		result.put("fightInfo", frs);
		return ResponseView.ok(result);
	}
	
	/**
	 * 获取战斗列表
	 * @param name
	 * @return
	 */
	@RequestMapping("/getFightLists")
	public ResponseView getFightLists(String name) {
		List<Map<String,Object>> fightInfos = new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(name)) {
			FightContainer fightContainer = FightManager.getFightContainer(name);
			if(fightContainer != null) {
				Map<String,Object> info = new HashMap<>();
				info.put("roundNum", fightContainer.round);
				info.put("startTime", fightContainer.startTime);
				info.put("fightNum", fightContainer.fightCharasA.size()+fightContainer.fightCharasB.size());
				info.put("isPauseFight", System.currentTimeMillis()>fightContainer.roundTime+90000?"<font color='red'>异常<font>":"<font color='green'>正常</font>");
				info.put("uid", fightContainer.uid);
				info.put("roundTime",DateUtil.format(new Date(fightContainer.roundTime), "H:mm:ss"));
				fightInfos.add(info);
			}
		}else {
			List<FightContainer> listFight = FightManager.listFight;
			for(FightContainer fightContainer:listFight) {
				Map<String,Object> info = new HashMap<>();
				info.put("roundNum", fightContainer.round);
				info.put("startTime", fightContainer.startTime);
				info.put("fightNum", fightContainer.fightCharasA.size()+fightContainer.fightCharasB.size());
				info.put("isPauseFight", System.currentTimeMillis()>fightContainer.roundTime+90000?"<font color='red'>异常<font>":"<font color='green'>正常</font>");
				info.put("uid", fightContainer.uid);
				info.put("roundTime",DateUtil.format(new Date(fightContainer.roundTime), "H:mm:ss"));
				fightInfos.add(info);
			}
		}
		return ResponseView.ok(fightInfos);
	}
	
	/**
	 * 强制结束战斗
	 * @param uid 战斗uid
	 * @return
	 */
	@PostMapping("/forceStopFight")
	public ResponseView forceStopFight(String uid) {
		
		FightContainer fightContainer = FightManager.getFightContainerByUid(uid);
		if(fightContainer == null) {
			ResponseView.fail("战斗不存在或已结束");
		}
		List<GameObjectChar> charas = new ArrayList<>();
		FightManager.listFight.remove(fightContainer);
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
		GameCommonUtil.endCombat(charas,fightContainer, null);
		return ResponseView.ok();
	}
	
	/**
	 * 导出
	 * @param uid
	 * @return
	 */
	@PostMapping("/exportJson")
	public ResponseView exportJson(String uid) {
		FightContainer fightContainer = FightManager.getFightContainerByUid(uid);
		if(fightContainer == null) {
			ResponseView.fail("战斗不存在或已结束");
		}
		ResponseView ok = ResponseView.ok();
		ok.put("gameVersion", GameCommonUtil.gameVersion);
		ok.put("fightCharasA", JSONObject.toJSONString(fightContainer.fightCharasA));
		ok.put("fightCharasB", JSONObject.toJSONString(fightContainer.fightCharasB));
		ok.put("fightRecords", JSONObject.toJSONString(fightContainer.fightRecords));
		return ok;
	}
}