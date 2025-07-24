package com.fengshen.db.service.chara;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.CharaTrailMapper;
import com.fengshen.db.domain.CharaTrail;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class CharaTrailService implements BaseServiceSupport<CharaTrail> {

	@Autowired
	private CharaTrailMapper ctm;
	
	@Override
	public BaseCustomMapper<CharaTrail> getBaseMapper() {
		return ctm;
	}

	/**
	 * 产生记录信息
	 * @param c
	 * @return
	 */
	public int addCharaTrail(CharaTrail c) {
		// c.setAddTime(new Date());
		// return ctm.insertSelective(c);
		return 1;
	}
	
	public Map<Object,Object> getCount(Map<String, Object> map) {
		return ctm.getCount(map);
	}
}