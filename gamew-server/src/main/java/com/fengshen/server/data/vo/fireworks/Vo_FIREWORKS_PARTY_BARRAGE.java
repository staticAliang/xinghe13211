package com.fengshen.server.data.vo.fireworks;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FIREWORKS_PARTY_BARRAGE {

	private String gid;
	
	private String msg;
	
	private Integer type;

	public Vo_FIREWORKS_PARTY_BARRAGE(String gid, String msg, Integer type) {
		this.gid = gid;
		this.msg = msg;
		this.type = type;
	}
	
}
