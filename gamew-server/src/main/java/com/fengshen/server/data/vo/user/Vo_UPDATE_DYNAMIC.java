package com.fengshen.server.data.vo.user;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_UPDATE_DYNAMIC {

	private Integer id;
	
	private Map<String,Object> dataMap;

	public Vo_UPDATE_DYNAMIC(Integer id, Map<String, Object> dataMap) {
		super();
		this.id = id;
		this.dataMap = dataMap;
	}
}