package com.fengshen.server.domain.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TyzqAttribConfig {

	public Map<String,TyzqAttribVo> tyzqArryibs;
	
	public Map<Integer,Integer> tyzqArryibCoefs;
	
	
	@Getter
	@Setter
	public static class TyzqAttribVo {
		
		public String propName;
		public Integer propMinValue;
		public Integer propMaxValue;
		public Integer affectByLevel;
		
	}
}