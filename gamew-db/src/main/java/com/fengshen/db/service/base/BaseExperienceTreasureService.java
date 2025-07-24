package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.ExperienceTreasureMapper;
import com.fengshen.db.domain.ExperienceTreasure;
import com.fengshen.db.domain.example.ExperienceTreasureExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseExperienceTreasureService {
	@Autowired
	protected ExperienceTreasureMapper mapper;

	@Cacheable(cacheNames = { "ExperienceTreasure" },keyGenerator = "cacheAutoKey")
	public ExperienceTreasure findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@Cacheable(cacheNames = { "ExperienceTreasure" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
	public ExperienceTreasure findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}
	@CacheEvict(cacheNames = { "ExperienceTreasure" }, allEntries = true)
	public void add(final ExperienceTreasure experienceTreasure) {
		experienceTreasure.setAddTime(LocalDateTime.now());
		experienceTreasure.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(experienceTreasure);
	}

	@CacheEvict(cacheNames = { "ExperienceTreasure" }, allEntries = true)
	public int updateById(final ExperienceTreasure experienceTreasure) {
		experienceTreasure.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(experienceTreasure);
	}

	@CacheEvict(cacheNames = { "ExperienceTreasure" }, allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "ExperienceTreasure", keyGenerator = "cacheAutoKey")
	public List<ExperienceTreasure> findByAttrib(final Integer attrib) {
		final ExperienceTreasureExample example = new ExperienceTreasureExample();
		final ExperienceTreasureExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectByExample(example);
	}
	
	@Cacheable(cacheNames = "ExperienceTreasure", keyGenerator = "cacheAutoKey")
	public List<ExperienceTreasure> findByMaxLevel(final Integer maxLevel) {
		final ExperienceTreasureExample example = new ExperienceTreasureExample();
		final ExperienceTreasureExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMaxLevelEqualTo(maxLevel);
		return this.mapper.selectByExample(example);
	}
	
	@Cacheable(cacheNames = "ExperienceTreasure", keyGenerator = "cacheAutoKey")
	public ExperienceTreasure findOneByAttrib(final Integer attrib) {
		final ExperienceTreasureExample example = new ExperienceTreasureExample();
		final ExperienceTreasureExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectOneByExample(example);
	}
	
	@Cacheable(cacheNames = "ExperienceTreasure", keyGenerator = "cacheAutoKey")
	public ExperienceTreasure findOneByMaxLevel(final Integer maxLevel) {
		final ExperienceTreasureExample example = new ExperienceTreasureExample();
		final ExperienceTreasureExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMaxLevelEqualTo(maxLevel);
		return this.mapper.selectOneByExample(example);
	}

	public List<ExperienceTreasure> findAll(final int page, final int size, final String sort, final String order) {
		final ExperienceTreasureExample example = new ExperienceTreasureExample();
		final ExperienceTreasureExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}
	
	@Cacheable(cacheNames = "ExperienceTreasure", keyGenerator = "cacheAutoKey")
	public List<ExperienceTreasure> findAll() {
		final ExperienceTreasureExample example = new ExperienceTreasureExample();
		final ExperienceTreasureExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
	
	//刷新缓存
  	@CacheEvict(cacheNames = "ExperienceTreasure", allEntries = true)
  	public void refreshCache() {}
}
