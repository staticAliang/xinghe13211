package com.fengshen.server.data.vo;

import java.util.ArrayList;
import java.util.List;

public class Vo_TITLE {
	public int id;
	public int count;
	public List<Integer> list;

	public Vo_TITLE() {
		this.list = new ArrayList<>();
	}

	public Vo_TITLE(int id) {
		this.id = id;
	}
}
