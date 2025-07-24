package com.fengshen.server.data.vo.chat;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_DECORATION_LIST {

	private String type;
	
	private String usedName;
	
	
	private List<Items> items;
	
	@Getter
	@Setter
	public static class Items {
		
		private String name;
		
		private Integer time;
		
		private Integer getTime;

		public Items(String name, Integer time, Integer getTime) {
			super();
			this.name = name;
			this.time = time;
			this.getTime = getTime;
		}

		public Items() {
		}
		
	}

	public Vo_DECORATION_LIST(String type, String usedName, List<Items> items) {
		super();
		this.type = type;
		this.usedName = usedName;
		this.items = items;
	}

	public Vo_DECORATION_LIST() {
		super();
	}
	
}