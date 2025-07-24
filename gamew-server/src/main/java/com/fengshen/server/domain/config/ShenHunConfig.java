package com.fengshen.server.domain.config;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShenHunConfig {

	public Map<String,JSONObject> data;
	
	public Map<String,JSONObject> attri;
	
}
