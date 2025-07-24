package com.fengshen.server.data.vo;

import java.util.LinkedList;
import java.util.List;

import com.fengshen.server.domain.Goods;

public class Vo_61677_0 {
	public String store_type;
	public int npcID;
	public int count;
	public int isGoon;
	public List<Goods> list;
	public int pos;

	public Vo_61677_0() {
		this.store_type = "normal_store";
		this.npcID = 0;
		this.count = 125;
		this.isGoon = 1;
		this.list = new LinkedList<Goods>();
	}

	public Vo_61677_0(String storeType) {
		this.store_type = storeType;
		this.npcID = 0;
		this.count = 125;
		this.isGoon = 1;
		this.list = new LinkedList<Goods>();
	}

	public Vo_61677_0(String storeType, int pos) {
		this.store_type = storeType;
		this.npcID = 0;
		this.count = 125;
		this.isGoon = 1;
		this.pos = pos;
		this.list = new LinkedList<Goods>();
	}
}
