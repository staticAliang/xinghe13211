package com.fengshen.server.data.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fengshen.server.domain.Goods;

// 查看别人的角色面板
public class Vo_49153_0 {
	public String name;
	public int level;
	public int icon;
	public int special_icon;
	public int weapon_icon;
	public int suit_icon;
	public int suit_effect;
	public int power;
	public String partyName;
	public int fashionIcon;
	public int upgradetype;
	public int upgradelevel;
	public List<Goods> backpack;
	public String customIcon;
	public Map<String,Integer> effect;

	public Vo_49153_0() {
		this.backpack = new ArrayList<Goods>();
		this.customIcon = "";
		effect = new HashMap<>();
	}
}
