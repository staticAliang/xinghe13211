package com.fengshen.server.data.vo;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.domain.SkillCost;

public class Vo_32747_0 {
	public int id;
	public int count;
	public int skill_no;
	public int skill_attrib;
	public int skill_attrib1;
	public int skill_level;
	public int level_improved;
	public int skill_mana_cost;
	public int skill_nimbus;
	public int skill_disabled;
	public int range;
	public int max_range;
	public String s1;
	public int s2;
	public int count1;
	public List<SkillCost> skillCost;
	
	public Vo_32747_0() {
		this.skillCost = new ArrayList<>();
	}
	
	public int isTempSkill;
}
