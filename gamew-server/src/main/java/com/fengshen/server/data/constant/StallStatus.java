package com.fengshen.server.data.constant;

/**
 * a珍宝状态
 * 
 *
 */
public enum StallStatus {

	STALL_GS_NONE2("默认",0),
	STALL_GS_SHOWING("公示中",1),
	STALL_GS_SELLING("出售中",2),
	STALL_GS_OUT_SELLING("已下架",3),
	STALL_GS_FROZEN("冻结中",4),
	STALL_GS_AUDIT("审核中",5),
	STALL_GS_AUDITED("已审核",6),
	STALL_GS_NO_PASS("审核不通过",7),
	;
	
	private String name;
	private int value;
	
	private StallStatus(String name, int value) {
		this.name = name;
		this.value = value;
	}
	private StallStatus(Integer value) {
		this.value = value;
	}
	
	public static int getValue(String name) {
		StallStatus[] values = StallStatus.values();
		for(StallStatus v:values) {
			if(v.name.equals(name)) {
				return v.value;
			}
		}
		return 7;
	}
}
