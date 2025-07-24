package com.fengshen.server.data.vo.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_RELOCATE {

	private Integer id;
	
	private Integer x;
	
	private Integer y;
	
	private Integer dir;

	public Vo_RELOCATE(Integer id, Integer x, Integer y, Integer dir) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public Vo_RELOCATE() {
		super();
	}
	
	
}
