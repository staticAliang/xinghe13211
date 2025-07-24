package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.MapMapper;
import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.example.MapExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseMapService {
	
	@Autowired
	protected MapMapper mapper;

	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey")
	public Map findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
	public Map findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}
	@CacheEvict(cacheNames = { "Map" }, allEntries = true)
	public void add(final Map map) {
		map.setAddTime(LocalDateTime.now());
		map.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(map);
	}

	@CacheEvict(cacheNames = { "Map" }, allEntries = true)
	public int updateById(final Map map) {
		map.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(map);
	}

	@CacheEvict(cacheNames = { "Map" }, allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey")
	public List<Map> findByName(final String name) {
		final MapExample example = new MapExample();
		final MapExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}
	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey")
	public List<Map> findByMapId(final Integer mapId) {
		final MapExample example = new MapExample();
		final MapExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMapIdEqualTo(mapId);
		return this.mapper.selectByExample(example);
	}
	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey")
	public Map findOneByName(final String name) {
		final MapExample example = new MapExample();
		final MapExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}
	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey")
	public Map findOneByMapId(final Integer mapId) {
		final MapExample example = new MapExample();
		final MapExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMapIdEqualTo(mapId);
		return this.mapper.selectOneByExample(example);
	}

	public List<Map> findAll(final int page, final int size, final String sort, final String order) {
		final MapExample example = new MapExample();
		final MapExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}
	@Cacheable(cacheNames = { "Map" }, keyGenerator = "cacheAutoKey")
	public List<Map> findAll() {
		final MapExample example = new MapExample();
		final MapExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
	
	@CacheEvict(cacheNames = "Map", allEntries = true)
  	public void refreshCache() {}
}
