package com.fengshen.server.data.constant;

/**
 * 帮派类型
 * 
 *
 */
public enum PartyType {

	BANGZHU(700, "帮主"), CHUANWEI(200, "传位"), 
	FUBANGZHU(600, "副帮主"), 
	QINGLONGZHANGLAO(504, "青龙长老"), 
	BAOHUZHANGLAO(503, "白虎长老"), 
	ZHUQUEZHANGLAO(502, "朱雀长老"), 
	XUANWUZHANGLAO(501, "玄武长老"),
	CANGLANHUFA(405, "苍龙护法"),
	YUANLEIHUFA(404, "远雷护法"), 
	JIANFENGHUFA(403, "尖峰护法"), 
	YEFUHUFA(402, "夜伏护法"), 
	YUNHAIHUFA(401, "云海护法"),
	DEXINTANGZHU(308, "德馨堂主"), 
	SUXIATANGZHU(307, "素侠堂主"), 
	HANLONGTANGZHU(306, "暗龙堂主"), 
	HUWEITANGZHU(305, "虎威堂主"),
	ZIYUNTANGZHU(304, "紫云堂主"), 
	TINGXUETANGZHU(303, "听雪堂主"), 
	MENGXITANGZHU(302, "梦溪堂主"), 
	XUANFENGTANGZHU(301, "玄风堂主"),
	BANGPAIJINGYING(150, "帮派精英"),
	BANGZHONG(100, "帮众");

	private int type;
	private String name;

	private PartyType(Integer type, String name) {
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
