package com.fengshen.server.data.vo.equip;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_PLAY_LYFH_ANIMAATE {

	private Integer pos;
	
	private Integer index;
	
	private Integer yangPercent;
	
	private Integer yinPercent;

	public Vo_PLAY_LYFH_ANIMAATE(Integer pos, Integer index) {
		super();
		this.pos = pos;
		this.index = index;
	}

	public Vo_PLAY_LYFH_ANIMAATE() {
		super();
	}
	
}