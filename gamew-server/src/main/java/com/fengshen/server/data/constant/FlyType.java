package com.fengshen.server.data.constant;

public enum FlyType {

	MENG_HE(1, "梦荷"),
	YU_TIAN_SUO(2, "御天梭"),
	MO_YAN_FEI_JIA(3, "魔炎飞甲"),
	MO_WU_QING_YUN(4, "墨舞青云"),
	LIE_HAI_LONG_JING(5, "裂海龙鲸");
	
	private int type;
	private String name;
	
	private FlyType(int type, String name) {
		this.type = type;
		this.name = name;
	}
	
	public static String getValueByKey(int type) {
		for (FlyType p : FlyType.values()) {
			if (p.getType() == type) {
				return p.getName();
			}
		}
		return "";
	}
	
	// 根据匹配value的值获取key
	public static int getKeyByValue(String name) {
		for (FlyType s : FlyType.values()) {
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