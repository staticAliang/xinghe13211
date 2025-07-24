package com.fengshen.server.data.vo;

import java.util.List;

public class Vo_53249_1 {
	
	public int type;
	
	public List<Items> items;
	
	public class Items{
		public String name;
		public int price;
		public Items(String name, int price) {
			super();
			this.name = name;
			this.price = price;
		}
	}
	
}
