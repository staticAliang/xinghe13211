package com.fengshen.server.data.vo.hunpo;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.domain.Goods;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_REFRESH_SHENHUN_DATA {

	private int phy_power;
	
	private int mag_power;
	
	private int max_life;
	
	private int def;
	
	private int speed;
	
	private int isTop;
	
	private int nextState;
	
	private int nextLayer;
	
	//魂窍数据
	private List<Goods> hqPropData;
	
	private List<Vo_REFRESH_SHENHUN_DATA_ITEM> items;
	
	@Getter
	@Setter
	public static class Vo_REFRESH_SHENHUN_DATA_ITEM{
		
		private String attrib;
		
		private int value;

		public Vo_REFRESH_SHENHUN_DATA_ITEM(String attrib, int value) {
			this.attrib = attrib;
			this.value = value;
		}

		public Vo_REFRESH_SHENHUN_DATA_ITEM() {
		}
		
	}

	public Vo_REFRESH_SHENHUN_DATA() {
		this.hqPropData = new ArrayList<>();
	}
	
}