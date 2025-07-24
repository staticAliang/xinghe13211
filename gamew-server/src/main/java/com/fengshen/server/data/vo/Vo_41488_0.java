package com.fengshen.server.data.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class Vo_41488_0 {
	public int flag;
	public int label;
	public String para;
	
	public List<Items> items;
	
	@Getter
	@Setter
	public class Items{
		private int price;
		private String name;
		public Items(String name, int price) {
			this.name = name;
			this.price = price;
		}
		public Items() {
			super();
		}
	}
}
