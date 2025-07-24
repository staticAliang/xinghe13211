package com.fengshen.server.data.vo.friend;

public class Vo_FRIEND_AUTO_FIGHT_CONFIG {

	private int id;
	
	private int auto_fight;
	

	public Vo_FRIEND_AUTO_FIGHT_CONFIG(int id, int auto_fight) {
		this.id = id;
		this.auto_fight = auto_fight;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAuto_fight() {
		return auto_fight;
	}

	public void setAuto_fight(int auto_fight) {
		this.auto_fight = auto_fight;
	}
	
}
