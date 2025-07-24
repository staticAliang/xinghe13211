package com.fengshen.server.domain;

import java.util.Hashtable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsHunqi{
	public int groupNo = 99;
    public int groupType = 2;
    public int hunqiState;
    public List<Hashtable<String, Object>> zongShuxing;

    public GoodsHunqi() {
		this.groupNo = 99;
		this.groupType = 2;
	}
}