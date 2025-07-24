package com.fengshen.server.data.constant;

/**
 * npc朝向
 * 
 *
 */
public enum LocatePosition {

	LEFT_TOP("左上",1),
	RIGHT_TOP("右上",2),
	LEFT_BOTTOM("左下",3),
	RIGHT_BOTTOM("右下",4),
	CENTER("左偏上下居中",5),
	MID("中间",6),
	MID_TOP("中上",7),
	MID_BOTTOM("中下",8),
	;
	
	private String strPosition;
	private int intPosition;
	
	private LocatePosition(String strPosition, int intPosition) {
		this.strPosition = strPosition;
		this.intPosition = intPosition;
	}
	private LocatePosition(Integer intPosition) {
		this.intPosition = intPosition;
	}
	//获取朝向
	public static int getLocatePosition(String positionStr) {
		LocatePosition[] values = LocatePosition.values();
		for(LocatePosition v:values) {
			if(v.strPosition.equals(positionStr)) {
				return v.intPosition;
			}
		}
		return 7;
	}
}
