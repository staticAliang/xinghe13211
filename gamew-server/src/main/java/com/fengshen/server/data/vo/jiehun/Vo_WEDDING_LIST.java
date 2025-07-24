package com.fengshen.server.data.vo.jiehun;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_ALL_LIST.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_WEDDING_LIST {

	private Integer time;
	
	private String meleName;
	
	private String feMaleName;
	
	private List<Item> items;
	

	public Vo_WEDDING_LIST() {
		super();
		this.items = new ArrayList<>();
	}
}
