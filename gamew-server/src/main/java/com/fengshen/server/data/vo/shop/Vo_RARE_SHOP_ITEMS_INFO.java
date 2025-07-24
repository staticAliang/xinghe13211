package com.fengshen.server.data.vo.shop;

import lombok.Getter;

@Getter
public class Vo_RARE_SHOP_ITEMS_INFO {

	private String barcode;
	
	private String name;
	
	private Integer cost;
	
	private Integer num;

	public Vo_RARE_SHOP_ITEMS_INFO(String barcode, String name, Integer cost, Integer num) {
		this.barcode = barcode;
		this.name = name;
		this.cost = cost;
		this.num = num;
	}
	
	
}