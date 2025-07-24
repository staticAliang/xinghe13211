package com.fengshen.server.data.vo.other;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_ITEM_APPEAR {

	private Integer id;
	
	private Integer x;
	
	private Integer y;
	
	private Integer dir;
	
	private Integer icon;
	
	private Integer type;
	
	private Integer amout;
	
	private String name;
	
	private Integer itemType;
	
	private Map<String,Object> buildFields;
	
	private String banRule;
	
	private String titleBanRule;

	public Vo_ITEM_APPEAR() {
		this.buildFields = new HashMap<String, Object>();
	}
}