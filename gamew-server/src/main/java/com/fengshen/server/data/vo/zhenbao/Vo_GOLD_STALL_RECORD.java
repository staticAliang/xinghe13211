package com.fengshen.server.data.vo.zhenbao;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_GOLD_STALL_RECORD {

	private List<Vo_GOLD_STALL_RECORD_BASE> buyList;
	
	private List<Vo_GOLD_STALL_RECORD_BASE> sellCout;

	public Vo_GOLD_STALL_RECORD() {
		this.buyList = new ArrayList<>();
		this.sellCout = new ArrayList<>();
	}
	
	
}