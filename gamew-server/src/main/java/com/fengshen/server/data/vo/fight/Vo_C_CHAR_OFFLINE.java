package com.fengshen.server.data.vo.fight;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_C_CHAR_OFFLINE {

	private Integer id;
	
	private Integer offline;

	public Vo_C_CHAR_OFFLINE(Integer id, Integer offline) {
		this.id = id;
		this.offline = offline;
	}
	
}