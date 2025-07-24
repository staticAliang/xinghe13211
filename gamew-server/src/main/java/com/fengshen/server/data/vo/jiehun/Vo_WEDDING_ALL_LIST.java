package com.fengshen.server.data.vo.jiehun;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_WEDDING_ALL_LIST {

	private String costType;
	
	private Integer discount;
	
	private List<Item> items;
	
	@Getter
	@Setter
	public static class Item {
		
		private String name;
		
		private Integer price;

		public Item(String name, Integer price) {
			super();
			this.name = name;
			this.price = price;
		}
	}

	public Vo_WEDDING_ALL_LIST(String costType, Integer discount) {
		super();
		this.costType = costType;
		this.discount = discount;
		this.items = new ArrayList<>();
	}
}
