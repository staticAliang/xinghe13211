package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.RenwuMonsterMapper;
import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.db.domain.example.RenwuMonsterExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseRenwuMonsterService {
	@Autowired
	protected RenwuMonsterMapper mapper;

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public RenwuMonster findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public RenwuMonster findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final RenwuMonster renwuMonster) {
		renwuMonster.setAddTime(LocalDateTime.now());
		renwuMonster.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(renwuMonster);
	}

	public int updateById(final RenwuMonster renwuMonster) {
		renwuMonster.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(renwuMonster);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public List<RenwuMonster> findByMapName(final String mapName) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMapNameEqualTo(mapName);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public List<RenwuMonster> findByName(final String name) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public List<RenwuMonster> findByIcon(final Integer icon) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIconEqualTo(icon);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public List<RenwuMonster> findByType(final Integer type) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public RenwuMonster findOneByMapName(final String mapName) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMapNameEqualTo(mapName);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public RenwuMonster findOneByName(final String name) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public RenwuMonster findOneByType(final Integer type) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public List<RenwuMonster> findAll(final int page, final int size, final String sort, final String order) {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "RenwuMonster" }, keyGenerator = "cacheAutoKey")
	public List<RenwuMonster> findAll() {
		final RenwuMonsterExample example = new RenwuMonsterExample();
		final RenwuMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	public RenwuMonster getOneRandomRenwuMonster(int type) {
		return this.mapper.getOneRandomRenwuMonster(type);
	}

	public RenwuMonster getOneRandomRenwuMonsterByTypeNotReplace(RenwuMonster paramRenwuMonster) {
		return this.mapper.getOneRandomRenwuMonsterByTypeNotReplace(paramRenwuMonster);
	}
	
	//刷新缓存
  	@CacheEvict(cacheNames = "RenwuMonster", allEntries = true)
  	public void refreshCache() {}
}
