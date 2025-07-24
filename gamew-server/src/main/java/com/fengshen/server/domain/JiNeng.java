package com.fengshen.server.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JiNeng {
	public int id;
	public int skill_no;
	public int skill_attrib;
	public int skill_attrib1;
	public int skill_level; // 技能等级
	public int level_improved;
	public int skill_mana_cost;
	public int skill_nimbus;
	public int skill_disabled;
	public int range;
	public int max_range;
	public int count1;
	public String s1;
	public int s2;
	public List<SkillCost> skillCost;
	public int isTempSkill;
	public int skillRound;

	public JiNeng() {
		this.skillCost = new ArrayList<>();
	}
	
	@Override
	public String toString() {
		return "JiNeng{" + "id=" + id + ", skill_no=" + skill_no + ", skill_attrib=" + skill_attrib + ", skill_attrib1="
				+ skill_attrib1 + ", skill_level=" + skill_level + ", level_improved=" + level_improved
				+ ", skill_mana_cost=" + skill_mana_cost + ", skill_nimbus=" + skill_nimbus + ", skill_disabled="
				+ skill_disabled + ", range=" + range + ", max_range=" + max_range + ", count1=" + count1 + ", s1='"
				+ s1 + '\'' + ", s2=" + s2 + ", isTempSkill=" + isTempSkill + ", skillRound=" + skillRound + '}';
	}
	
	
}
