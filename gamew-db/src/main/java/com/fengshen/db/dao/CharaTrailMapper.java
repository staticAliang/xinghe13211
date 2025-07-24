package com.fengshen.db.dao;

import java.util.Map;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.domain.CharaTrail;

public interface CharaTrailMapper extends BaseCustomMapper<CharaTrail> {
	
	Map<Object,Object> getCount(Map<String, Object> ct);
}