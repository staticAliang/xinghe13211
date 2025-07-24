package com.fengshen.server.data.vo.achieve;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_ACHIEVE_CONFIG {

	private Integer achieve_id;
	
	private String name;
	
	private Integer point;
	
	private Integer progress;
	
	private String bonus_desc;
	
	private String achieve_desc;
	
	private Integer category;
	
	private Integer order;
	
	private List<Target> targets;
	
	@Getter
	@Setter
	public static class Target {
		
		private String des;
		
		private Integer process;
		
	}
}
