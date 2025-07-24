package com.fengshen.db.service.shidao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ShidaoHistoryMapper;
import com.fengshen.db.domain.ShidaoHistory;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class ShidaoHistoryService implements BaseServiceSupport<ShidaoHistory> {

	@Autowired
	private ShidaoHistoryMapper shs;
	
	
	@Override
	public BaseCustomMapper<ShidaoHistory> getBaseMapper() {
		return shs;
	}

}
