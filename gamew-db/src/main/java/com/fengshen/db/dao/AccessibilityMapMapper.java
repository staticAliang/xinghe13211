package com.fengshen.db.dao;

import java.util.List;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.domain.AccessibilityMap;

public interface AccessibilityMapMapper extends BaseCustomMapper<AccessibilityMap>{
	
	/**
	 * 随机根据条件获取可用坐标
	 * @param map
	 * @return
	 */
	List<AccessibilityMap> getRandomData(AccessibilityMap map);
	
	/**
	 * 随机获取无障碍坐标
	 * @return
	 */
	List<AccessibilityMap> randomData(Integer size);
	
}