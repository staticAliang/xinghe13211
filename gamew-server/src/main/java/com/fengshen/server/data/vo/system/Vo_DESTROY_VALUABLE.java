package com.fengshen.server.data.vo.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_DESTROY_VALUABLE {

	private Integer type;
	
	private Integer id;
	
	private Integer life;

	public Vo_DESTROY_VALUABLE(Integer type, Integer id, Integer life) {
		super();
		this.type = type;
		this.id = id;
		this.life = life;
	}
}