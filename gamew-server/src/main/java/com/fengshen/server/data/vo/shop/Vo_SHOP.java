package com.fengshen.server.data.vo.shop;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SHOP {

	private Integer refreshTime;
	
	private List<Item> items;
	
	
	
	public Vo_SHOP() {
		this.items = new ArrayList<>();
	}



	@Getter
	@Setter
	public static class Item {
		private String name;
		
		private Integer price;
		
		private Integer num;
		
		private Integer totalNum;
		
		private Integer limited;
	}
}