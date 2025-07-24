package com.fengshen.db.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.fengshen.db.auth.BlackListMapper;
import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.domain.BlackList;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class BlackListService implements BaseServiceSupport<BlackList> {

	@Autowired
	private BlackListMapper blackListMapper;
	
	@Override
	public BaseCustomMapper<BlackList> getBaseMapper() {
		return blackListMapper;
	}
	
	@CacheEvict(cacheNames = "BlackList", allEntries = true)
	public int getCountByDataType(String data) {
		Example example = new Example(BlackList.class);
		example.createCriteria().andEqualTo("data", data);
		return blackListMapper.selectCountByExample(example);
	}
	
	@CacheEvict(cacheNames = "BlackList", allEntries = true)
	public void refreshCache() {}
}