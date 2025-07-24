package com.fengshen.db.service.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.PartyMapper;
import com.fengshen.db.domain.Party;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class PartyService implements BaseServiceSupport<Party>{

	@Autowired
	private PartyMapper pm;
	
	@Override
	public BaseCustomMapper<Party> getBaseMapper() {
		return pm;
	}

	/**
	 * 根据id查询帮派
	 * @param id
	 * @return
	 */
	public Party findByPartyId(String id) {
		Example example = new Example(Party.class);
		example.createCriteria().andEqualTo("partyId", id);
		return pm.selectOneByExample(example);
	}
	/**
	 * 查询帮派
	 * @param name
	 * @return
	 */
	public Party findByPartyName(String name) {
		Example example = new Example(Party.class);
		example.createCriteria().andEqualTo("partyName", name);
		return pm.selectOneByExample(example);
	}
}
