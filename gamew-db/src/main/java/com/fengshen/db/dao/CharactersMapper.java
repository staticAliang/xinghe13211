package com.fengshen.db.dao;

import java.util.List;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.domain.Characters;

public interface CharactersMapper extends BaseCustomMapper<Characters> {
	
	 List<Characters> getRankData();
	 
	 List<Characters> getLastLoginTimeData(Characters ch);
}
