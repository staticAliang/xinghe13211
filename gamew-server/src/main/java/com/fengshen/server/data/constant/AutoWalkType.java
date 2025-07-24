package com.fengshen.server.data.constant;

public enum AutoWalkType {

	CHUYAO(1, "降妖"),
	FUMO(2, "伏魔"),
	FEIXIANDUXIE(3, "飞仙渡邪"),
	WEIMINGCHUBAO(4, "为民除暴"),
	XIUXING(5, "修行"),
	SHIJUE(6, "十绝阵"),
	巡逻(7, "巡逻"),
	ERJIECHUYAO(8, "二阶降妖"),
	ERJIEFUMO(9, "二阶伏魔"),
	ERJIEFEIXIANDUXIE(10, "二阶飞仙渡邪"),
	;
	
	private int type;
	private String name;

	private AutoWalkType(Integer type, String name) {
		this.name = name;
		this.type = type;
	}

	public static String getValueByKey(int type) {
		for (PartyType p : PartyType.values()) {
			if (p.getType() == type) {
				return p.getName();
			}
		}
		return "";
	}
	

	// 根据匹配value的值获取key
	public static int getKeyByValue(String name) {
		for (PartyType s : PartyType.values()) {
			if (s.getName().equals(name)) {
				return s.getType();
			}
		}
		return 0;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
