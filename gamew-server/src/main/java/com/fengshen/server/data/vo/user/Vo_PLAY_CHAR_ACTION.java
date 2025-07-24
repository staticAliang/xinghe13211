package com.fengshen.server.data.vo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_PLAY_CHAR_ACTION {

	private Integer id;
	
	private Integer action;
	
	private Integer loops;

	public Vo_PLAY_CHAR_ACTION(Integer id, Integer action, Integer loops) {
		this.id = id;
		this.action = action;
		this.loops = loops;
	}

	public Vo_PLAY_CHAR_ACTION() {
	}
}