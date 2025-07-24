package com.fengshen.server.data.vo;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Vo_APPEAR {
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
	public String party;
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
	public int isHide; // 是否隐身
	public int moveSpeedPercent;
	public int score;
	public int opacity;
	public int masquerade;
	public int upgradestate;
	public int upgradetype;
	public int obstacle;
	public Map<String,Integer> effectIcons;
	public int share_mount_icon;
	public int share_mount_leader_id;
	public int gather_count;
	public int gather_name_num;
	public int portrait;
	public String customIcon;
	public int leixing;
	public int mapid;
	public String mapName;
	public int wanjiaid;
	public String uuid;
	//出现的时间
	public Long time;
	public int teamIcon;
	public int state;
	public int flyType;
	public int moveType;
	public Set<Integer> moveIds;
	public Vo_APPEAR() {
		this.wanjiaid = 0;
		this.effectIcons = new HashMap<>();
		this.time = System.currentTimeMillis();
		this.moveIds = new LinkedHashSet<>();
	}
}
