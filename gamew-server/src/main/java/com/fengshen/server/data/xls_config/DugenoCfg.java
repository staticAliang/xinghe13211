package com.fengshen.server.data.xls_config;

import java.util.ArrayList;
import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

/**
 * 副本配置
 *
 */
public class DugenoCfg extends ArrayList<Object> {
	private static final long serialVersionUID = -1264337969368073944L;
	public static HashMap<String, DugenoItem> nameMap = new HashMap<>();

	
	
    public void init() {
    	for(int i=0;i<this.size();i++) {
    		JSONObject obj = (JSONObject) this.get(i);
    		DugenoItem item = JSONObject.parseObject(obj.toJSONString(), DugenoItem.class);
    		 item.init();
           nameMap.put(item.name, item);
    	}
    }

    public DugenoItem getByName(String name) {
        return nameMap.get(name);
    }

    public DugenoItem getByMapName(String name) {
        for (DugenoItem item : nameMap.values()) {
            if (item.map_name.equals(name)) {
                return item;
            }
        }
        return null;
    }
}