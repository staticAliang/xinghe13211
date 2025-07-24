package com.fengshen.server.data.vo.fight;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter	
public class Vo_COMBAT_LIGHT_EFFECT {

	private Integer charId;
	
	private Integer effectIcon;
	
	private Integer effectPos;

	public Vo_COMBAT_LIGHT_EFFECT(Integer charId, Integer effectIcon, Integer effectPos) {
		this.charId = charId;
		this.effectIcon = effectIcon;
		this.effectPos = effectPos;
	}

	public Vo_COMBAT_LIGHT_EFFECT(Integer charId, Integer effectIcon) {
		super();
		this.charId = charId;
		this.effectIcon = effectIcon;
	}
}