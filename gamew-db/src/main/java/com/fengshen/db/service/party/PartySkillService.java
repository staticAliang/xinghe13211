package com.fengshen.db.service.party;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.PartySkillMapper;
import com.fengshen.db.domain.PartySkill;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class PartySkillService implements BaseServiceSupport<PartySkill> {

	@Autowired
	private PartySkillMapper ps;
	
	@Override
	public BaseCustomMapper<PartySkill> getBaseMapper() {
		return ps;
	}

	
	public List<PartySkill> getPartySkillByPartyId(String party) {
		Example example = new Example(PartySkill.class);
		example.createCriteria().andEqualTo("partyId", party);
		return ps.selectByExample(example);
	}
}
