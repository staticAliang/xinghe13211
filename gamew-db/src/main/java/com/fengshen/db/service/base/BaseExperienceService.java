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
public class BaseExperienceService {
	@Autowired
	protected ExperienceMapper mapper;

	@Cacheable(cacheNames = { "Experience" }, keyGenerator = "cacheAutoKey")
	public Experience findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@Cacheable(cacheNames = { "Experience" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
	public Experience findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = { "ExperienceTreasure" }, allEntries = true)
	public void add(final Experience experience) {
		experience.setAddTime(LocalDateTime.now());
		experience.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(experience);
	}

	@CacheEvict(cacheNames = { "ExperienceTreasure" }, allEntries = true)
	public int updateById(final Experience experience) {
		experience.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(experience);
	}

	@CacheEvict(cacheNames = { "ExperienceTreasure" }, allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "Experience", keyGenerator = "cacheAutoKey")
	public List<Experience> findByAttrib(final Integer attrib) {
		final ExperienceExample example = new ExperienceExample();
		final ExperienceExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Experience", keyGenerator = "cacheAutoKey")
	public List<Experience> findByMaxLevel(final Integer maxLevel) {
		final ExperienceExample example = new ExperienceExample();
		final ExperienceExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMaxLevelEqualTo(maxLevel);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Experience", keyGenerator = "cacheAutoKey")
	public Experience findOneByAttrib(final Integer attrib) {
		final ExperienceExample example = new ExperienceExample();
		final ExperienceExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Experience", keyGenerator = "cacheAutoKey")
	public Experience findOneByMaxLevel(final Integer maxLevel) {
		final ExperienceExample example = new ExperienceExample();
		final ExperienceExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMaxLevelEqualTo(maxLevel);
		return this.mapper.selectOneByExample(example);
	}

	public List<Experience> findAll(final int page, final int size, final String sort, final String order) {
		final ExperienceExample example = new ExperienceExample();
		final ExperienceExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Experience", keyGenerator = "cacheAutoKey")
	public List<Experience> findAll() {
		final ExperienceExample example = new ExperienceExample();
		final ExperienceExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	// 刷新缓存
	@CacheEvict(cacheNames = "Experience", allEntries = true)
	public void refreshCache() {}
}
