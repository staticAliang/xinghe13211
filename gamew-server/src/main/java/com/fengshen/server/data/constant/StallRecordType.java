package com.fengshen.server.data.constant;

/**
 * a商品交易记录类型
 * 
 *
 */
public enum StallRecordType {

	STALL("集市",0),
	GOLD_STALL("珍宝",1),
	;
	
	private String name;
	private int value;
	
	private StallRecordType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	private StallRecordType(Integer value) {
		this.value = value;
	}
	
	public static int getValue(String name) {
		StallRecordType[] values = StallRecordType.values();
		for(StallRecordType v:values) {
			if(v.name.equals(name)) {
				return v.value;
			}
		}
		return 0;
	}
}
