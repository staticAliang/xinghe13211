package com.fengshen.server.data.constant;

/**
 * a商品出售类型
 * 
 *
 */
public enum SellOrBuyRecordType {

	SELL("出售",0),
	BUY("购买",1),
	;
	
	private String name;
	private int value;
	
	private SellOrBuyRecordType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	private SellOrBuyRecordType(Integer value) {
		this.value = value;
	}
	
	public static int getValue(String name) {
		SellOrBuyRecordType[] values = SellOrBuyRecordType.values();
		for(SellOrBuyRecordType v:values) {
			if(v.name.equals(name)) {
				return v.value;
			}
		}
		return 7;
	}
}
