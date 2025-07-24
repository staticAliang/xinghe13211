package com.fengshen.server.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Chara_Statue;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameData;
import com.google.gson.Gson;

/**
 * 人物雕像管理器
 */
@Service
public class CharaStatueService {
	private static final Logger log = LoggerFactory.getLogger(FightManager.class);

	private static String serverId;
	/**
	 * key:npc名字
	 */
	public static final Map<String, CharaStatue> cacheMap = new HashMap<>();

	public static final void init(String serverId) {
		CharaStatueService.serverId = serverId;
		for (Chara_Statue chara_statue : GameData.that.baseCharaStatueService.findAll(serverId)) {
			cacheMap.put(chara_statue.getNpcName(), JSONObject.parseObject(chara_statue.getData(), CharaStatue.class));
		}
	}

	/**
	 * 保存人物雕像
	 * 
	 * @param npcName
	 */
	public static void saveCharaStature(String npcName, CharaStatue charaStatue) {
		cacheMap.put(npcName, charaStatue); // 放缓存
		Chara_Statue chara_statue = GameData.that.baseCharaStatueService.findByName(serverId, npcName);
		if (null == chara_statue) {
			chara_statue = new Chara_Statue();
			chara_statue.setNpcName(npcName);
			chara_statue.setServerid(serverId);
			chara_statue.setData(new Gson().toJson(charaStatue));
			GameData.that.baseCharaStatueService.insert(chara_statue);
			log.info("插入一条新雕像！" + npcName);
		} else {
			chara_statue.setData(new Gson().toJson(charaStatue));
			GameData.that.baseCharaStatueService.updateById(chara_statue);
			log.info("更新一条新雕像！" + npcName);
		}
	}

	public static CharaStatue getCharStaure(String npcName) {
		return cacheMap.get(npcName);
	}

	public static boolean containsCharStaure(String npcName) {
		return cacheMap.containsKey(npcName);
	}

	public static void putCache(String npcName, CharaStatue charaStatue) {
		assert !cacheMap.containsKey(npcName);
		cacheMap.put(npcName, charaStatue);
	}

}
