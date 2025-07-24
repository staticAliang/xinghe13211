package com.fengshen.db.service.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.FixedTeamMapper;
import com.fengshen.db.domain.FixedTeam;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class FixedTeamService implements BaseServiceSupport<FixedTeam> {

	@Autowired
	private FixedTeamMapper ftm;
	
	@Override
	public BaseCustomMapper<FixedTeam> getBaseMapper() {
		return ftm;
	}

}
