package com.fengshen.db.service.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.CharaNicknameMapper;
import com.fengshen.db.domain.CharaNickname;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class CharaNicknameService implements BaseServiceSupport<CharaNickname> {

	@Autowired
	private CharaNicknameMapper cm;
	
	@Override
	public BaseCustomMapper<CharaNickname> getBaseMapper() {
		return cm;
	}
	
	public CharaNickname randomData(CharaNickname cn) {
		return cm.randomData(cn);
	}
	
	public CharaNickname randomData2(CharaNickname cn) {
		return cm.randomData2(cn);
	}
}
