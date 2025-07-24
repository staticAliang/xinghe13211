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
public class BaseChoujiangService {
	@Autowired
	protected ChoujiangMapper mapper;

	@Cacheable(cacheNames = "Choujiang", key="#id")
	public Choujiang findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@CacheEvict(cacheNames = "Choujiang", allEntries = true)
	public Choujiang findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = "Choujiang", allEntries = true)
	public void add(final Choujiang choujiang) {
		choujiang.setAddTime(LocalDateTime.now());
		choujiang.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(choujiang);
	}

	@CacheEvict(cacheNames = "Choujiang", allEntries = true)
	public int updateById(final Choujiang choujiang) {
		choujiang.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(choujiang);
	}

	@CacheEvict(cacheNames = "Choujiang", allEntries = true)
	public void deleteById(final int id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public List<Choujiang> findByNo(final Integer nos) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNoEqualTo(nos);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public List<Choujiang> findByName(final String names) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(names);
		return this.mapper.selectByExample(example);
	}
	
	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public List<Choujiang> findByLevel(final Integer level) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public Choujiang findOneByNo(final Integer no) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNoEqualTo(no);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public Choujiang findOneByName(final String name) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public Choujiang findOneByLevel(final Integer level) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
		return this.mapper.selectOneByExample(example);
	}

	public List<Choujiang> findAll(final int page, final int size, final String sort, final String order) {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Choujiang", keyGenerator = "cacheAutoKey")
	public List<Choujiang> findAll() {
		final ChoujiangExample example = new ChoujiangExample();
		final ChoujiangExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
	
	public List<Choujiang> selectAll() {
		final ChoujiangExample example = new ChoujiangExample();
		return this.mapper.selectByExample(example);
	}
	
	public List<Choujiang> selectAll(Choujiang choujiang) {
		final ChoujiangExample example = new ChoujiangExample();
		ChoujiangExample.Criteria criteria = example.createCriteria();
		if(!com.mysql.jdbc.StringUtils.isNullOrEmpty(choujiang.getName())) {
			criteria.andNameLike("%"+choujiang.getName()+"%");
		}
		if(choujiang.getLevel() != null) {
			criteria.andLevelEqualTo(choujiang.getLevel());
		}
		return this.mapper.selectByExample(example);
	}
	
	//刷新缓存
	@CacheEvict(cacheNames = "Choujiang", allEntries = true)
	public void refreshCache() {}
}
