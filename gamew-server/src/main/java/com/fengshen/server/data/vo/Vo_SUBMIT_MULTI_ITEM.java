package com.fengshen.server.data.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SUBMIT_MULTI_ITEM {

	private int type;
	
	private int limitNum;
	
	private List<Integer> items;
	

	public Vo_SUBMIT_MULTI_ITEM(int type, int limitNum, List<Integer> items) {
		this.type = type;
		this.limitNum = limitNum;
		this.items = items;
	}
	
}