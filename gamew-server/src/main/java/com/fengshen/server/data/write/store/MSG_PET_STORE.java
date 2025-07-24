package com.fengshen.server.data.write.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.pet.Vo_PET_STORE;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.SkillCost;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MSG_PET_STORE extends BaseWrite<List<Vo_PET_STORE>>{

	@Override
	protected void writeO(ByteBuf buff, List<Vo_PET_STORE> object) {
		GameWriteTool.writeString(buff, "pet_store");
		GameWriteTool.writeInt(buff, 0);
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_PET_STORE store:object) {
			Petbeibao pet = store.getPetbeibao();
			GameWriteTool.writeByte(buff, 1);
			GameWriteTool.writeShort(buff, store.getPos());
			GameWriteTool.writeShort(buff,1);
			PetShuXing petShuXing = pet.petShuXing.get(0);
			GameWriteTool.writeByte(buff, store.getPos());
			GameWriteTool.writeByte(buff, pet.petShuXing.get(0).type1);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map = UtilObjMapshuxing.PetShuXing(petShuXing, GameObjectChar.getGameObjectChar().chara.name);
			map.remove("no");
			map.remove("type1");
			
			
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
				} else {
					log.info((String)entry.getKey());
				}
			}
			//宠物技能信息
			List<JiNeng> jiNengList = new ArrayList<JiNeng>();
			List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getNomelSkills(1, petShuXing.metal, petShuXing.skill, true);
			List<Integer> defaultFsId = new ArrayList<>();
			for (int i = 0; i < nomelSkills.size(); ++i) {
				JiNeng jiNeng = new JiNeng();
				JSONObject jsonObject = nomelSkills.get(i);
				jiNeng.id = pet.id;
				jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
				JSONObject jsonObject2 = PetAndHelpSkillUtils.jsonArray(jiNeng.skill_no);
				jiNeng.skill_attrib1 = Integer.parseInt((String) jsonObject2.get("skill_attrib"));
				jiNeng.skill_attrib = (int) jsonObject.get("skillLevel");
				jiNeng.skill_level = (int) jsonObject.get("skillLevel");
				jiNeng.skillRound = jsonObject.optInt("skillRound");
				jiNeng.level_improved = 1;
				jiNeng.skill_mana_cost = (int) jsonObject.get("skillBlue");
				jiNeng.skill_nimbus = 42949672;
				jiNeng.skill_disabled = 0;
				jiNeng.range = (int) jsonObject.get("skillNum");
				jiNeng.max_range = (int) jsonObject.get("skillNum");
				jiNengList.add(jiNeng);
				if(jsonObject2.getString("skillType").equals("FS")) {
					defaultFsId.add(i);
				}
			}
			//如果不等于空
			if(pet != null && pet.tianji != null && !pet.tianji.isEmpty()) {
				int customFsNum = 0;
				for(JiNeng tinaji:pet.tianji) {
					JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(tinaji.skill_no);
					if(jsonObject.getString("skillType").equals("FS")) {
						customFsNum++;
					}
				}
				if(defaultFsId.size()>=3) {
					if(customFsNum>3) {
						//直接清除
						jiNengList.clear();
					}else {
						//表示法伤技能已经超限了，则删除默认技能
						for(int i=0;i<customFsNum;i++) {
							jiNengList.remove(0);
						}
					}
				}
				jiNengList.addAll(pet.tianji);
			}
			GameWriteTool.writeShort(buff, jiNengList.size());
			for(JiNeng jineng:jiNengList) {
				GameWriteTool.writeShort(buff, jineng.skill_no);
	            GameWriteTool.writeShort(buff, jineng.skill_attrib1);
	            GameWriteTool.writeShort(buff, jineng.skill_level+jineng.level_improved);
	            GameWriteTool.writeShort(buff, jineng.level_improved);
	            GameWriteTool.writeShort(buff, jineng.skill_mana_cost);
	            GameWriteTool.writeInt(buff, jineng.skill_nimbus);
	            GameWriteTool.writeByte(buff, jineng.skill_disabled);
	            GameWriteTool.writeShort(buff, jineng.range);
	            GameWriteTool.writeShort(buff, jineng.max_range);
	            if(jineng.count1>0) {
	            	GameWriteTool.writeShort(buff, jineng.count1);
	            	GameWriteTool.writeString(buff, jineng.s1);
	        		GameWriteTool.writeInt(buff, jineng.s2);
	            }else {
	            	 GameWriteTool.writeShort(buff, jineng.skillCost.size());
	                 for(SkillCost cost:jineng.skillCost) {
	             		GameWriteTool.writeString(buff, cost.s1);
	             		GameWriteTool.writeInt(buff, cost.s2);
	                 }
	            }
	            GameWriteTool.writeByte(buff, 0);
			}
			//宠物天书信息
			GameWriteTool.writeShort(buff, pet.tianshu.size());
			for(Vo_12023_0 tianshu:pet.tianshu) {
				GameWriteTool.writeString(buff, tianshu.god_book_skill_name);
                GameWriteTool.writeShort(buff, tianshu.god_book_skill_level);
                GameWriteTool.writeShort(buff, tianshu.god_book_skill_power);
                GameWriteTool.writeByte(buff, tianshu.god_book_skill_disabled);
			}
			//其他属性
			GameWriteTool.writeShort(buff, 0);
		}
	}

	@Override
	public int cmd() {
		return 0xF0ED;
	}

}
