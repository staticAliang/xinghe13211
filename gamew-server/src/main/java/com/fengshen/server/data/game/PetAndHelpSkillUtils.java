package com.fengshen.server.data.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class PetAndHelpSkillUtils {
	public static String skillJson;
	private static ResourceLoader resourceLoader;
	private static Logger log;

	public static int getMaxSkill(int attrib) {
		int maxSkill = (int) (attrib * 1.6);
		return maxSkill;
	}

	public static List<JSONObject> getNomelSkills(int pet, int pMetal, int attrib, boolean isMagic)
			throws JSONException {
		return getNomelSkills(pet, pMetal, attrib, isMagic, "");
	}

	public static List<JSONObject> getNomelSkills(int pet, int pMetal, int attrib, boolean isMagic, String skill_value)
			throws JSONException {
		if (PetAndHelpSkillUtils.skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach(f -> strb.append(f));
			PetAndHelpSkillUtils.skillJson = strb.toString();
		}
		JSONArray jsonArray = new JSONArray(PetAndHelpSkillUtils.skillJson);
		int[] sh_gj = { 1, 19, 32, 50, 100 };
		int sh_gj_count = 0;
		for (int i = sh_gj.length - 1; i >= 0; --i) {
			if (attrib >= sh_gj[i]) {
				sh_gj_count = i + 1;
				break;
			}
		}
		List<JSONObject> sh_gj_list = new ArrayList<JSONObject>();
		int[] sh_fz = { 1, 1, 1, 50, 100 };
		int sh_fz_count = 0;
		for (int j = sh_fz.length - 1; j >= 0; --j) {
			if (attrib >= sh_fz[j]) {
				sh_fz_count = j + 1;
				break;
			}
		}
		List<JSONObject> sh_fz_list = new ArrayList<JSONObject>();
		List<JSONObject> pet_gj_list = new ArrayList<JSONObject>();
		int[] pet_gj = { 20, 40, 60 };
		List<Integer> pet_gj_counts = new ArrayList<Integer>();
		int k = pet_gj.length - 1;
		while (k >= 0) {
			if (attrib >= pet_gj[k]) {
				if (k == 2) {
					pet_gj_counts.add(1);
					pet_gj_counts.add(2);
					pet_gj_counts.add(4);
					break;
				}
				if (k == 1) {
					pet_gj_counts.add(1);
					pet_gj_counts.add(2);
					break;
				}
				pet_gj_counts.add(1);
				break;
			} else {
				--k;
			}
		}
		if (pet == 2 && (null == skill_value || skill_value.isEmpty())) {
			for (k = 0; k < jsonArray.length(); ++k) {
				JSONObject jsonObject = jsonArray.optJSONObject(k);
				int metal = jsonObject.optInt("metal");
				String skillType = jsonObject.optString("skillType");
				int skillIndex = jsonObject.optInt("skillIndex");
				if (skillType.contentEquals("FS") && pMetal == metal && (pMetal == 1 || pMetal == 2 || pMetal == 3)
						&& skillIndex <= sh_gj_count) {
					int[] skillNum_round = skillNum(jsonObject, getMaxSkill(attrib));
					jsonObject.put("skillNum", skillNum_round[0]);
					jsonObject.put("skillRound", skillNum_round[1]);
					jsonObject.put("skillLevel", getMaxSkill(attrib));
					jsonObject.remove("skillUse");
					jsonObject = appendBP(jsonObject, skillType, skillIndex, attrib);
					sh_gj_list.add(jsonObject);
				} else if (skillType.contentEquals("WS") && pMetal == metal && (pMetal == 4 || pMetal == 5)
						&& skillIndex <= sh_gj_count) {
					int[] skillNum_round = skillNum(jsonObject, getMaxSkill(attrib));
					jsonObject.put("skillNum", skillNum_round[0]);
					jsonObject.put("skillRound", skillNum_round[1]);
					jsonObject.put("skillLevel", getMaxSkill(attrib));
					jsonObject.remove("skillUse");
					jsonObject = appendBP(jsonObject, skillType, skillIndex, attrib);
					sh_gj_list.add(jsonObject);
				} else if (skillType.contentEquals("FZ") && pMetal == metal && skillIndex <= sh_fz_count) {
					int[] skillNum_round = skillNum(jsonObject, getMaxSkill(attrib));
					jsonObject.put("skillNum", skillNum_round[0]);
					jsonObject.put("skillRound", skillNum_round[1]);
					jsonObject.put("skillLevel", getMaxSkill(attrib));
					jsonObject.remove("skillUse");
					jsonObject = appendBP(jsonObject, skillType, skillIndex, attrib);
					sh_fz_list.add(jsonObject);
				}
			}
			sh_gj_list.addAll(sh_fz_list);
			return sh_gj_list;
		}
		if (pet == 1 && isMagic && (null == skill_value || skill_value.isEmpty())) {
			for (k = 0; k < jsonArray.length(); ++k) {
				JSONObject jsonObject = jsonArray.optJSONObject(k);
				int metal = jsonObject.optInt("metal");
				String skillType = jsonObject.optString("skillType");
				int skillIndex = jsonObject.optInt("skillIndex");
				if (pet_gj_counts.contains(skillIndex) && skillType.contentEquals("FS") && pMetal == metal) {
					int[] skillNum_round = skillNum(jsonObject, getMaxSkill(attrib));
					jsonObject.put("skillNum", skillNum_round[0]);
					jsonObject.put("skillRound", skillNum_round[1]);
					jsonObject.put("skillLevel", getMaxSkill(attrib));
					jsonObject.remove("skillUse");
					jsonObject.remove("skillRound");
					jsonObject = appendBP(jsonObject, skillType, skillIndex, attrib);
					pet_gj_list.add(jsonObject);
				}
			}
			return pet_gj_list;
		}
		if (null != skill_value && !skill_value.isEmpty()) {
			for (k = 0; k < jsonArray.length(); ++k) {
				JSONObject jsonObject = jsonArray.optJSONObject(k);
				int metal = jsonObject.optInt("metal");
				String skillType = jsonObject.optString("skillType");
				int skillIndex = jsonObject.optInt("skillIndex");
				String skillType_skillIndex = String.format("%s_%d", skillType, skillIndex);
				if (skill_value.contains(skillType_skillIndex) && pMetal == metal) {
					int[] skillNum_round2 = skillNum(jsonObject, getMaxSkill(attrib));
					jsonObject.put("skillNum", skillNum_round2[0]);
					jsonObject.put("skillRound", skillNum_round2[1]);
					jsonObject.put("skillLevel", getMaxSkill(attrib));
					jsonObject.remove("skillUse");
					jsonObject = appendBP(jsonObject, skillType, skillIndex, attrib);
					if (skillIndex == 5) {
						if (sh_gj_count >= 5) {
							pet_gj_list.add(jsonObject);
						}
					} else {
						pet_gj_list.add(jsonObject);
					}
				}
			}
			return pet_gj_list;
		}
		return sh_gj_list;
	}

	private static JSONObject appendBP(JSONObject jsonObject, String skillType, int skillIndex, int attrib)
			throws JSONException {
		int[] bp = getBlueAndPoints(skillType, skillIndex, attrib);
		jsonObject.put("skillBlue", bp[0]);
		jsonObject.put("skillPoint", bp[1]);
		return jsonObject;
	}

	private static int[] getBlueAndPoints(String skillType, int skillIndex, int attrib) {
		int[] bp = { 1, 1 };
		if (attrib == 1) {
			return bp;
		}
		if (skillType.contentEquals("WS")) {
			bp[0] = (int) (attrib * 17.5);
			bp[1] = attrib * attrib * 60;
		} else {
			Hashtable<String, Double> addHashtable = new Hashtable<String, Double>();
			addHashtable.put("FS", 0.0);
			addHashtable.put("ZA", 0.3);
			addHashtable.put("FZ", 0.4);
			addHashtable.put("BD", 0.5);
			Double add = addHashtable.get(skillType);
			if (null == add) {
				add = 0.0;
			}
			switch (skillIndex) {
			case 1: {
				bp[0] = (int) (attrib * (10.7 + add));
				bp[1] = (int) (attrib * attrib * (15.7 + add));
				break;
			}
			case 2: {
				bp[0] = (int) (attrib * (13.5 + add));
				bp[1] = (int) (attrib * attrib * (14.0 + add));
				break;
			}
			case 3: {
				bp[0] = (int) (attrib * (15.5 + add));
				bp[1] = (int) (attrib * attrib * (22.0 + add));
				break;
			}
			case 4: {
				bp[0] = (int) (attrib * (25.0 + add));
				bp[1] = (int) (attrib * attrib * (33.0 + add));
				break;
			}
			case 5: {
				bp[0] = (int) (attrib * (28.0 + add));
				bp[1] = (int) (attrib * attrib * (43.0 + add));
				break;
			}
			}
		}
		return bp;
	}

	public static int[] skillNum(JSONObject skillObject, int skill) {
		JSONArray jsonArray = skillObject.optJSONArray("skillUse");
		JSONArray jsonArrayRound = skillObject.optJSONArray("skillRound");
		int[] num_round = new int[2];
		if (null == jsonArray || jsonArray.length() == 0) {
			num_round[0] = 1;
		}
		if (null == jsonArrayRound || jsonArrayRound.length() == 0) {
			num_round[1] = 1;
		}
		if (num_round[0] == 0) {
			for (int i = 0; i < jsonArray.length(); ++i) {
				JSONObject jsonObject = jsonArray.optJSONObject(i);
				int skillLevelMin = jsonObject.optInt("skillLevelMin");
				int skillLevel = jsonObject.optInt("skillLevel");
				int skillNum = jsonObject.optInt("skillNum");
				if (skill >= skillLevelMin && skill <= skillLevel) {
					num_round[0] = skillNum;
				}
			}
		}
		if (num_round[0] == 0) {
			num_round[0] = 1;
		}
		if (num_round[1] == 0) {
			for (int i = 0; i < jsonArrayRound.length(); ++i) {
				JSONObject jsonObject = jsonArrayRound.optJSONObject(i);
				int skillLevelMin = jsonObject.optInt("skillLevelMin");
				int skillLevel = jsonObject.optInt("skillLevel");
				int skillRound = jsonObject.optInt("skillRound");
				if (skill >= skillLevelMin && skill <= skillLevel) {
					num_round[1] = skillRound;
				}
			}
		}
		if (num_round[1] == 0) {
			num_round[1] = 1;
		}
		return num_round;
	}

	public static BufferedReader getResFile() {
		Resource resource = PetAndHelpSkillUtils.resourceLoader.getResource("classpath:static/user_skill.json");
		BufferedReader br = null;
		try {
			InputStream inputStream = resource.getInputStream();
			InputStreamReader fr = new InputStreamReader(inputStream, "UTF-8");
			br = new BufferedReader(fr);
		} catch (IOException e) {
			PetAndHelpSkillUtils.log.error("{}", (Throwable) e);
		}
		return br;
	}

	public static JSONObject jsonArray(int skillNo) {
		if (PetAndHelpSkillUtils.skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach(f -> strb.append(f));
			PetAndHelpSkillUtils.skillJson = strb.toString();
		}
		JSONArray jsonArray = new JSONArray(PetAndHelpSkillUtils.skillJson);
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			int no = jsonObject.optInt("skillNo");
			if (no == skillNo) {
				return jsonObject;
			}
		}
		return null;
	}
	
	/**
	 * 根据门派获取技能
	 * @return
	 */
	public static JSONArray getSkillAll() {
		if (PetAndHelpSkillUtils.skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach(f -> strb.append(f));
			PetAndHelpSkillUtils.skillJson = strb.toString();
		}
		JSONArray jsonArray = new JSONArray(PetAndHelpSkillUtils.skillJson);
		return jsonArray;
	}
	
	/**
	 * 获取技能，根据门派、技能类型和阶数
	 * @param polar 门派
	 * @param index 阶数
	 * @param skillType 类型 ，例如FS或者FZ 
	 * @return
	 */
	public static JSONObject getSkill(int polar, int index, String skillType) {
		if (PetAndHelpSkillUtils.skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach(f -> strb.append(f));
			PetAndHelpSkillUtils.skillJson = strb.toString();
		}
		JSONArray jsonArray = new JSONArray(PetAndHelpSkillUtils.skillJson);
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			int metal = jsonObject.optInt("metal");
			String skillTypeStr = jsonObject.optString("skillType");
			if (metal == polar && skillTypeStr.equals(skillType) && 
					jsonObject.optInt("skillIndex") == index) {
				return jsonObject;
			}
		}
		return null;
	}

	public static int skillNummax(int skillNo, int skill) {
		JSONObject skillObject = jsonArray(skillNo);
		JSONArray jsonArray = skillObject.optJSONArray("skillUse");
		if (null == jsonArray || jsonArray.length() == 0) {
			return 1;
		}
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			int skillLevelMin = jsonObject.optInt("skillLevelMin");
			int skillLevel = jsonObject.optInt("skillLevel");
			int skillNum = jsonObject.optInt("skillNum");
			if (skill >= skillLevelMin && skill <= skillLevel) {
				return skillNum;
			}
		}
		return 1;
	}

	public static int[] getBlueAndPointsLan(int skillNo, int attrib) {
		if (PetAndHelpSkillUtils.skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach(f -> strb.append(f));
			PetAndHelpSkillUtils.skillJson = strb.toString();
		}
		JSONArray jsonArray = new JSONArray(PetAndHelpSkillUtils.skillJson);
		String leixing = null;
		int skillIndex = 0;
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			int no = jsonObject.optInt("skillNo");
			if (no == skillNo) {
				leixing = jsonObject.optString("skillType");
				skillIndex = jsonObject.optInt("skillIndex");
				break;
			}
		}
		int[] bp = { 1, 1 };
		if (attrib == 1) {
			return bp;
		}
		if (leixing != null) {
			if (leixing.contentEquals("WS")) {
				bp[0] = (int) (attrib * 17.5);
				bp[1] = attrib * attrib * 60;
			} else {
				Hashtable<String, Double> addHashtable = new Hashtable<String, Double>();
				addHashtable.put("FS", 0.0);
				addHashtable.put("ZA", 0.3);
				addHashtable.put("FZ", 0.4);
				addHashtable.put("BD", 0.5);
				Double add = addHashtable.get(leixing);
				if (null == add) {
					add = 0.0;
				}
				switch (skillIndex) {
				case 1: {
					bp[0] = (int) (attrib * (10.7 + add));
					bp[1] = (int) (attrib * attrib * (15.7 + add));
					break;
				}
				case 2: {
					bp[0] = (int) (attrib * (13.5 + add));
					bp[1] = (int) (attrib * attrib * (14.0 + add));
					break;
				}
				case 3: {
					bp[0] = (int) (attrib * (15.5 + add));
					bp[1] = (int) (attrib * attrib * (22.0 + add));
					break;
				}
				case 4: {
					bp[0] = (int) (attrib * (25.0 + add));
					bp[1] = (int) (attrib * attrib * (33.0 + add));
					break;
				}
				case 5: {
					bp[0] = (int) (attrib * (28.0 + add));
					bp[1] = (int) (attrib * attrib * (43.0 + add));
					break;
				}
				}
				if (leixing.contentEquals("BD")) {
					bp[1] = attrib * 70000 + 140000;
				}
			}
		}
		return bp;
	}

	static {
		PetAndHelpSkillUtils.skillJson = null;
		PetAndHelpSkillUtils.resourceLoader = (ResourceLoader) new DefaultResourceLoader();
		log = LoggerFactory.getLogger(PetAndHelpSkillUtils.class);
	}

	public static List<JSONObject> getSkills(int pMetal, int level, String skill_value) throws JSONException {
		if (skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach((f) -> {
				strb.append(f);
			});
			skillJson = strb.toString();
		}

		JSONArray jsonArray = new JSONArray(skillJson);
		List<JSONObject> result = new ArrayList<>();
		if (null == skill_value || skill_value.isEmpty()) {
			return result;

		}
		List<String> skillNameList = new ArrayList<>();
		for (String name : skill_value.split(",")) {
			skillNameList.add(name);
		}
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			int metal = jsonObject.optInt("metal");
			String skillType = jsonObject.optString("skillType");
			String skillName = jsonObject.optString("skillName");
			int skillIndex = jsonObject.optInt("skillIndex");
			if (pMetal == metal && skill_value.contains(skillName)) {
				int[] skillNum_round = skillNum(jsonObject, getMaxSkill(level));
				jsonObject.put("skillNum", skillNum_round[0]);
				jsonObject.put("skillRound", skillNum_round[1]);
				jsonObject.put("skillLevel", getMaxSkill(level));
				jsonObject.remove("skillUse");
				jsonObject = appendBP(jsonObject, skillType, skillIndex, level);

				result.add(jsonObject);
				skillNameList.remove(skillName);
			}
		}
		return result;
	}

	public static List<JSONObject> getSkills(int level, String skill_value) throws JSONException {
		if (skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach((f) -> {
				strb.append(f);
			});
			skillJson = strb.toString();
		}

		JSONArray jsonArray = new JSONArray(skillJson);
		List<JSONObject> result = new ArrayList<>();
		if (null == skill_value || skill_value.isEmpty()) {
			return result;

		}
		List<String> skillNameList = new ArrayList<>();
		for (String name : skill_value.split(",")) {
			skillNameList.add(name);
		}
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			String skillType = jsonObject.optString("skillType");
			String skillName = jsonObject.optString("skillName");
			int skillIndex = jsonObject.optInt("skillIndex");
			if (skill_value.contains(skillName)) {
				int[] skillNum_round = skillNum(jsonObject, getMaxSkill(level));
				jsonObject.put("skillNum", skillNum_round[0]);
				jsonObject.put("skillRound", skillNum_round[1]);
				jsonObject.put("skillLevel", getMaxSkill(level));
				jsonObject.remove("skillUse");
				jsonObject = appendBP(jsonObject, skillType, skillIndex, level);
				result.add(jsonObject);
				skillNameList.remove(skillName);
			}
		}
		return result;
	}
	
	/**
	 * 获取战斗怪物技能信息
	 * @param level 等级
	 * @param skill_value 技能信息
	 * @return
	 */
	public static List<JSONObject> getFightObjectSkills(int level, String skill_value) {
		if (skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach((f) -> {
				strb.append(f);
			});
			skillJson = strb.toString();
		}

		JSONArray jsonArray = new JSONArray(skillJson);
		List<JSONObject> result = new ArrayList<>();
		if (null == skill_value || skill_value.isEmpty()) {
			return result;

		}
		List<String> skillNameList = new ArrayList<>();
		for (String name : skill_value.split(",")) {
			skillNameList.add(name);
		}
		for (int i = 0; i < jsonArray.length(); ++i) {
			JSONObject jsonObject = jsonArray.optJSONObject(i);
			String skillType = jsonObject.optString("skillType");
			String skillName = jsonObject.optString("skillName");
			int skillIndex = jsonObject.optInt("skillIndex");
			if (skill_value.contains(skillName)) {
				//技能信息
				JSONObject skillUse = jsonObject.getJSONArray("skillUse").getJSONObject(0);
				int skillNum = skillUse.getInt("skillNum");
				jsonObject.put("skillNum", skillNum);
				if(!jsonObject.isNull("skillRound")) {
					//回合数
					JSONArray skillRounds = jsonObject.getJSONArray("skillRound");
					for (int j = 0; j < skillRounds.length(); j++) {
						JSONObject skillRound = skillRounds.getJSONObject(j);
						if(level>=skillRound.getInt("skillLevelMin") && level<=skillRound.getInt("skillLevel")) {
							jsonObject.put("skillRound", skillRound.getInt("skillRound"));
							jsonObject.put("skillNum", skillRound.getInt("skillRound"));
							break;
						}
					}
					jsonObject.put("skillLevel", level);
				}else {
					int[] skillNum_round = skillNum(jsonObject, getMaxSkill(level));
					jsonObject.put("skillNum", skillNum_round[0]);
					jsonObject.put("skillRound", skillNum_round[1]);
					jsonObject.put("skillLevel", getMaxSkill(level));
				}
				jsonObject.remove("skillUse");
				jsonObject = appendBP(jsonObject, skillType, skillIndex, level);
				result.add(jsonObject);
				skillNameList.remove(skillName);
			}
		}
		return result;
	}

	static {
		if (PetAndHelpSkillUtils.skillJson == null) {
			BufferedReader br = getResFile();
			StringBuilder strb = new StringBuilder();
			br.lines().forEach(f -> strb.append(f));
			PetAndHelpSkillUtils.skillJson = strb.toString();
		}
	}
}
