package com.fengshen.db.service.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.WeddingListMapper;
import com.fengshen.db.domain.WeddingList;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class WeddingListService implements BaseServiceSupport<WeddingList> {

	@Autowired
	private WeddingListMapper wlm;
	
	@Override
	public BaseCustomMapper<WeddingList> getBaseMapper() {
		return wlm;
	}

}