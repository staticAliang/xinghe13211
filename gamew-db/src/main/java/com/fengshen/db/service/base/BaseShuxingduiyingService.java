package com.fengshen.db.service.base;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import org.springframework.cache.annotation.*;
import java.util.*;

import org.springframework.util.*;

import com.fengshen.db.dao.*;
import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;
import com.github.pagehelper.*;

@Service
public class BaseShuxingduiyingService {
	@Autowired
	protected ShuxingduiyingMapper mapper;

	@Cacheable(cacheNames = { "Shuxingduiying" }, keyGenerator = "cacheAutoKey")
	public Shuxingduiying findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@Cacheable(cacheNames = { "Shuxingduiying" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
	public Shuxingduiying findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = { "Shuxingduiying" }, allEntries = true)
	public void add(final Shuxingduiying shuxingduiying) {
		shuxingduiying.setAddTime(LocalDateTime.now());
		shuxingduiying.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(shuxingduiying);
	}

	@CacheEvict(cacheNames = { "Shuxingduiying" }, allEntries = true)
	public int updateById(final Shuxingduiying shuxingduiying) {
		shuxingduiying.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(shuxingduiying);
	}

	@CacheEvict(cacheNames = { "Shuxingduiying" }, keyGenerator = "cacheAutoKey")
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "Shuxingduiying", keyGenerator = "cacheAutoKey")
	public List<Shuxingduiying> findByName(final String names) {
		final ShuxingduiyingExample example = new ShuxingduiyingExample();
		final ShuxingduiyingExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(names);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Shuxingduiying", keyGenerator = "cacheAutoKey")
	public List<Shuxingduiying> findByYingwen(final String yingwens) {
		final ShuxingduiyingExample example = new ShuxingduiyingExample();
		final ShuxingduiyingExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYingwenEqualTo(yingwens);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Shuxingduiying", keyGenerator = "cacheAutoKey")
	public Shuxingduiying findOneByName(final String names) {
		final ShuxingduiyingExample example = new ShuxingduiyingExample();
		final ShuxingduiyingExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(names);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Shuxingduiying", keyGenerator = "cacheAutoKey")
	public Shuxingduiying findOneByYingwen(final String yingwen) {
		final ShuxingduiyingExample example = new ShuxingduiyingExample();
		final ShuxingduiyingExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYingwenEqualTo(yingwen);
		return this.mapper.selectOneByExample(example);
	}

	public List<Shuxingduiying> findAll(final int page, final int size, final String sort, final String order) {
		final ShuxingduiyingExample example = new ShuxingduiyingExample();
		final ShuxingduiyingExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "Shuxingduiying" }, keyGenerator = "cacheAutoKey")
	public List<Shuxingduiying> findAll() {
		final ShuxingduiyingExample example = new ShuxingduiyingExample();
		final ShuxingduiyingExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
	//刷新缓存
  	@CacheEvict(cacheNames = "Shuxingduiying", allEntries = true)
  	public void refreshCache() {}
}
