package com.fengshen.server.domain;

import java.util.LinkedList;
import java.util.List;

public class ShouHu {
	public int id;
	public List<ShouHuShuXing> listShouHuShuXing;

	public ShouHu() {
		this.id = 123456;
		this.listShouHuShuXing = new LinkedList<ShouHuShuXing>();
	}
}
