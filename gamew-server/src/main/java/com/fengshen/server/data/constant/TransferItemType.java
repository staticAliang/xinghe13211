package com.fengshen.server.data.constant;

/**
 * a商品类型
 * 
 *
 */
public enum TransferItemType {

	OTHER("无",0),
	CASH("金钱",1),
	PET("宠物",2),
	CHARGE("收费道具",3),
	NOT_COMBINE("不可叠加道具",4),
	COMBINE("可叠加道具",5),
	;
	
	private String name;
	private int value;
	
	private TransferItemType(String name, int value) {
		this.name = name;
		this.value = value;
	}
	private TransferItemType(Integer value) {
		this.value = value;
	}
	
	public static int getValue(String name) {
		TransferItemType[] values = TransferItemType.values();
		for(TransferItemType v:values) {
			if(v.name.equals(name)) {
				return v.value;
			}
		}
		return 7;
	}
}
