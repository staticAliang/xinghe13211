package com.fengshen.db.service.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ChangeCardMapper;
import com.fengshen.db.domain.ChangeCard;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class ChangeCardService implements BaseServiceSupport<ChangeCard>{

	@Autowired
	private ChangeCardMapper cm;
	
	@Override
	public BaseCustomMapper<ChangeCard> getBaseMapper() {
		return cm;
	}

	
}