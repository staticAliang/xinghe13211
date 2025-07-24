package com.fengshen.server.fight;

// 战斗请求信息
public class FightRequest {
	public int id; // 战斗发起者的id
	public int vid; // 战斗受攻击的id
	// 1为防御，2为普攻，3为技能，4为使用了道具技能等
	// 7是逃跑
	public int action;
	public int para;
	public String para1;
	public String para2;
	public String para3;
	public String skill_talk;
	public int item_type;

	@Override
	public String toString() {
		return "FightRequest{" + "id=" + id + ", vid=" + vid + ", action=" + action + ", para=" + para + ", para1='"
				+ para1 + '\'' + ", para2='" + para2 + '\'' + ", para3='" + para3 + '\'' + ", skill_talk='" + skill_talk
				+ '\'' + ", item_type=" + item_type + '}';
	}
}
