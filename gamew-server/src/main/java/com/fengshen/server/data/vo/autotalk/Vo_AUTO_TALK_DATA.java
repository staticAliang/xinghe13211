package com.fengshen.server.data.vo.autotalk;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_AUTO_TALK_DATA {

	private Integer id;
	
	private String content;

	public Vo_AUTO_TALK_DATA(Integer id, String content) {
		this.id = id;
		this.content = content;
	}
}