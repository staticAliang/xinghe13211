package com.fengshen.db.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.DialogMapper;
import com.fengshen.db.domain.Dialog;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class DialogService implements BaseServiceSupport<Dialog> {

	@Autowired
	private DialogMapper dm;
	
	@Override
	public BaseCustomMapper<Dialog> getBaseMapper() {
		return dm;
	}

}
