package com.fengshen.db.service.party;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.PartyMemberMapper;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class PartyMemberService implements BaseServiceSupport<PartyMember>{

	@Autowired
	private PartyMemberMapper pm;
	
	@Override
	public BaseCustomMapper<PartyMember> getBaseMapper() {
		return pm;
	}

	/**
	 * 根据帮派id查询
	 * @param name
	 * @return
	 */
	public List<PartyMember> getPartyMemberByPartyId(String partyId) {
		
		Example example = new Example(PartyMember.class);
		example.createCriteria().andEqualTo("partyId", partyId);
		return pm.selectByExample(example);
	}
	
	/**
	 * 根据帮派名称查询这个人
	 * @param name
	 * @return
	 */
	public PartyMember getPartyMemberOnePartyName(String name) {
		Example example = new Example(PartyMember.class);
		example.createCriteria().andEqualTo("name", name);
		return pm.selectOneByExample(example);
	}
}
