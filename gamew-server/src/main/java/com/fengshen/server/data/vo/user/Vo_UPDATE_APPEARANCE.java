package com.fengshen.server.data.vo.user;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Vo_UPDATE_APPEARANCE {
	public int id;
	public int x;
	public int y;
	public int dir;
	public int icon;
	public int weapon_icon;
	public int type;
	public int sub_type;
	public int owner_id;
	public int leader_id;
	public String name;
	public int level;
	public String title;
	public String family;
	public String partyname;
	public int status;
	public int special_icon;
	public int org_icon;
	public int suit_icon;
	public int suit_light_effect;
	public int guard_icon;
	public int pet_icon;
	public int shadow_icon;
	public int shelter_icon;
	public int mount_icon;
	public String alicename;
	public String gid;
	public String camp;
	public int vip_type;
	public int isHide;
	public int moveSpeedPercent;
	public int score;
	public int opacity;
	public int masquerade;
	public int upgradestate;
	public int upgradetype;
	public int obstacle;
	public String partyTitle;
	public String customIcon;
	public Map<String,Integer> effect;
	public int teamIcon;
	public int flyType;
	public int moveType;
	public Set<Integer> moveIds;
	
	public Vo_UPDATE_APPEARANCE() {
		this.effect = new HashMap<>();
		this.moveIds = new LinkedHashSet<>();
	}
}
