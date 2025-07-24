package com.fengshen.server.data.vo.fight;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_C_MESSAGE {

	private Integer channel;
	
	private String name;
	
	private String msg;

	public Vo_C_MESSAGE(Integer channel, String name, String msg) {
		this.channel = channel;
		this.name = name;
		this.msg = msg;
	}

	public Vo_C_MESSAGE(Integer channel) {
		this.channel = channel;
	}
	
}