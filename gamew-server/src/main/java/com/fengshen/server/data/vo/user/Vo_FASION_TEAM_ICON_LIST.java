package com.fengshen.server.data.vo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FASION_TEAM_ICON_LIST {

	private String name;
	
	private int goods_price;

	public Vo_FASION_TEAM_ICON_LIST(String name, int goods_price) {
		super();
		this.name = name;
		this.goods_price = goods_price;
	}
	
}
