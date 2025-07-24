package com.fengshen.server.domain.rank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LuoBoTaoZiRankVo {

	private String name;
	
	private Integer score;
	
	private Long time;

	public LuoBoTaoZiRankVo(String name, Integer score, Long time) {
		this.name = name;
		this.score = score;
		this.time = time;
	}
}