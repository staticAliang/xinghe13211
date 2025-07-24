package com.fengshen.server.data.vo.active;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_LIVENESS_INFO {

	private String name;
	private Integer count;
	private Integer activeValue;
	private String timeStr;
	public Vo_LIVENESS_INFO() {
		super();
	}
	public Vo_LIVENESS_INFO(String name, Integer count, Integer activeValue, String timeStr) {
		super();
		this.name = name;
		this.count = count;
		this.activeValue = activeValue;
		this.timeStr = timeStr;
	}
}