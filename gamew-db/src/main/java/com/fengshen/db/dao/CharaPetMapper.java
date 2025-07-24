package com.fengshen.db.dao;

import java.util.List;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.domain.CharaPet;

public interface CharaPetMapper extends BaseCustomMapper<CharaPet> {
	
	int updateBatch(List<CharaPet> charaPet);
	
	int inserBatch(List<CharaPet> charaPet);
	
}