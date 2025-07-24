package com.fengshen.server.data.vo.luck;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_NEW_LOTTERY_INFO {

	private int start_time;
	
	private int end_time;
	
	private List<Item> items;
	

	@Getter
	@Setter
	public static class Item {
		
		private Integer no;
		
		private String name;
		
		private String desc;
		
		private Integer level;

		public Item(Integer no, String name, String desc, Integer level) {
			super();
			this.no = no;
			this.name = name;
			this.desc = desc;
			this.level = level;
		}
		
		public Item() {
		
		}
	}
}
