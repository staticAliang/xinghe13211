package com.fengshen.server.data.vo.user;

import java.util.HashMap;
import java.util.Map;

public class Vo_UPDATE_COMBAT_INFO_DYNAMIC {

	public int id;
	
	public int isSet;
	
	public Map<String,Object> dataMap;

	public Vo_UPDATE_COMBAT_INFO_DYNAMIC(int id, int isSet,Map<String,Object> dataMap) {
		super();
		this.id = id;
		this.isSet = isSet;
		if(dataMap == null) {
			this.dataMap = new HashMap<String, Object>();
		}else {
			this.dataMap = dataMap;
		}
	}
	
	
}
