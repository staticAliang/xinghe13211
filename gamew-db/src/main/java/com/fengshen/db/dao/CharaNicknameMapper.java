package com.fengshen.db.dao;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.domain.CharaNickname;

public interface CharaNicknameMapper extends BaseCustomMapper<CharaNickname> {
	
	CharaNickname randomData(CharaNickname cn);
	
	CharaNickname randomData2(CharaNickname cn);
}