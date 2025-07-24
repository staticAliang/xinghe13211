package com.fengshen.server.data.vo.fight;

public class Vo_C_ACCEPT_HIT {
	public int id; // 被攻击者id
	public int hid; // 出手者id
	public int para_ex;
	public int missed;
	public int para;
	public int damage_type; // 目前只有两种值，1是普攻伤害，2是技能伤害
}
