package com.fengshen.server.domain;

import java.io.Serializable;

public class GoodsBasics implements Cloneable, Serializable {
	private static final long serialVersionUID = 1L;
	public int groupNo;
	public int groupType;
	public int mana;
	public int accurate;
	public int def;
	public int dex;
	public int wiz;
	public int parry;
	public int max_life;
	public int max_mana;

	public GoodsBasics() {
		this.groupNo = 1;
		this.groupType = 2;
	}
}
