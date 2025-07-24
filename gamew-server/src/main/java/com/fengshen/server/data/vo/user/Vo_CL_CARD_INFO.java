package com.fengshen.server.data.vo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_CL_CARD_INFO {

	private int size;
	
	private int maxSize;
	
	private int topSize;

	public Vo_CL_CARD_INFO(int size) {
		super();
		this.size = size;
	}

	public Vo_CL_CARD_INFO() {
		super();
	}
}