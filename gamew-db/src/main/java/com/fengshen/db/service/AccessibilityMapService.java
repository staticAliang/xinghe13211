package com.fengshen.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.AccessibilityMapMapper;
import com.fengshen.db.domain.AccessibilityMap;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class AccessibilityMapService implements BaseServiceSupport<AccessibilityMap> {

	@Autowired
	private AccessibilityMapMapper am;

	@Override
	public BaseCustomMapper<AccessibilityMap> getBaseMapper() {
		return am;
	}
	
	public List<AccessibilityMap> getRandomData(AccessibilityMap map) {
		if(map.getX() == null || map.getX() == 0) {
			map.setX(1);
		}
		return am.getRandomData(map);
	}
	
	public List<AccessibilityMap> getRandomData(Integer size) {
		return am.randomData(size);
	}
	
	
	public List<AccessibilityMap> findByMapName(String mapName) {
		Example example = new Example(AccessibilityMap.class);
		example.excludeProperties("delete");
		example.createCriteria().andEqualTo("mapName", mapName);
		return am.selectByExample(example);
	}
	
}
