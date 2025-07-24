package com.fengshen.db.service.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.DaySignPrizeMapper;
import com.fengshen.db.domain.DaySignPrize;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class DaySignPrizeService implements BaseServiceSupport<DaySignPrize> {

	@Autowired
	private DaySignPrizeMapper dspm;
	
	
	@Override
	public BaseCustomMapper<DaySignPrize> getBaseMapper() {
		return dspm;
	}

	@Cacheable(cacheNames = { "DaySignPrize" }, keyGenerator = "cacheAutoKey")
	public DaySignPrize getDaySignPrizeByName(String name) {
		Example example = new Example(DaySignPrize.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name);
		return dspm.selectOneByExample(example);
	}
	
	@Cacheable(cacheNames = { "DaySignPrize" }, keyGenerator = "cacheAutoKey")
	public List<DaySignPrize> getAll() {
		Example example = new Example(DaySignPrize.class);
		example.orderBy("day").asc();
		example.createCriteria().andEqualTo("deleted", false);
		return dspm.selectByExample(example);
	}
	
	@Cacheable(cacheNames = { "DaySignPrize" }, keyGenerator = "cacheAutoKey")
	public DaySignPrize getDaySignPrizeByDay(int day) {
		Example example = new Example(DaySignPrize.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("day", day);
		return dspm.selectOneByExample(example);
	}
	
	//刷新缓存
  	@CacheEvict(cacheNames = "DaySignPrize", allEntries = true)
  	public void refreshCache() {}
}