package com.fengshen.server.data.vo.pet;

import com.fengshen.server.domain.Petbeibao;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_PET_STORE {

	private Integer pos;
	
	private Petbeibao petbeibao;
	
	public Vo_PET_STORE(Integer pos, Petbeibao petbeibao) {
		super();
		this.pos = pos;
		this.petbeibao = petbeibao;
	}

	public Vo_PET_STORE() {
		super();
	}
}
