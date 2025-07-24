package com.fengshen.server.data.constant;

public enum BonusType {

	EXP("exp"),
	TAO("tao");
	public String type;
	
	private BonusType(String type) {
		this.type = type;
	}
	
}
