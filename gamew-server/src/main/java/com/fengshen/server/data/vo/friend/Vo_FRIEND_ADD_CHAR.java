package com.fengshen.server.data.vo.friend;

public class Vo_FRIEND_ADD_CHAR {
	//分组
	public String groupBuf;
	public String charBuf;
	public int blocked;
	public Integer online;
	public String server_name1;
	public int insider_level;
	public int user_state;
	public int auto_reply;
	public String gid;
	public int placed_amount;
	public int tao_effect;
	public int skill;
	public int type;
	public String server_name;
	public int suit_icon;
	public String party_contrib;
	public String character_harmony;
	public int evolve_level;
	public String nice;
	public String req_str;
	public int org_icon;
	public String iid_str;
	public long balance;
	public int arena_rank;
	public Vo_FRIEND_ADD_CHAR() {
		super();
	}
	public Vo_FRIEND_ADD_CHAR(String groupBuf, String charBuf) {
		super();
		this.groupBuf = groupBuf;
		this.charBuf = charBuf;
	}
}