package com.fengshen.server.data.vo.fight;

import java.util.HashMap;
import java.util.Map;

public class Vo_C_OPPONENT_INFO {

	private Integer id;
	
	private Map<String,Object> buildFields;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Map<String, Object> getBuildFields() {
		return buildFields;
	}

	public void setBuildFields(Map<String, Object> buildFields) {
		this.buildFields = buildFields;
	}

	public Vo_C_OPPONENT_INFO(Integer id) {
		this.id = id;
		this.buildFields = new HashMap<>();
	}
	
}