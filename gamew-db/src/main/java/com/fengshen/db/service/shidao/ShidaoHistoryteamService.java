package com.fengshen.db.service.shidao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ShidaoHistoryteamMapper;
import com.fengshen.db.domain.ShidaoHistoryteam;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class ShidaoHistoryteamService implements BaseServiceSupport<ShidaoHistoryteam> {

	@Autowired
	private ShidaoHistoryteamMapper sm;
	
	@Override
	public BaseCustomMapper<ShidaoHistoryteam> getBaseMapper() {
		return sm;
	}

}
