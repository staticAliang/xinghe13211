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
public class BaseNpcDialogueFrameService {
	@Autowired
	protected NpcDialogueFrameMapper mapper;

	@Cacheable(cacheNames = { "NpcDialogueFrame" }, keyGenerator = "cacheAutoKey")
	public NpcDialogueFrame findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@CacheEvict(cacheNames = "NpcDialogueFrame", allEntries = true)
	public NpcDialogueFrame findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = "NpcDialogueFrame", allEntries = true)
	public void add(final NpcDialogueFrame npcDialogueFrame) {
		npcDialogueFrame.setAddTime(LocalDateTime.now());
		npcDialogueFrame.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(npcDialogueFrame);
	}

	@CacheEvict(cacheNames = "NpcDialogueFrame", allEntries = true)
	public int updateById(final NpcDialogueFrame npcDialogueFrame) {
		npcDialogueFrame.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(npcDialogueFrame);
	}

	@CacheEvict(cacheNames = "NpcDialogueFrame", allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "NpcDialogueFrame", keyGenerator = "cacheAutoKey")
	public List<NpcDialogueFrame> findByPicNo(final Integer picNos) {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPicNoEqualTo(picNos);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "NpcDialogueFrame", keyGenerator = "cacheAutoKey")
	public List<NpcDialogueFrame> findByName(final String names) {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(names);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "NpcDialogueFrame", keyGenerator = "cacheAutoKey")
	public List<NpcDialogueFrame> findByIdname(final Integer idnames) {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIdnameEqualTo(idnames);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "NpcDialogueFrame", keyGenerator = "cacheAutoKey")
	public NpcDialogueFrame findOneByName(final String name) {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "NpcDialogueFrame", keyGenerator = "cacheAutoKey")
	public NpcDialogueFrame findOneByIdname(final Integer idname) {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIdnameEqualTo(idname);
		return this.mapper.selectOneByExample(example);
	}

	public List<NpcDialogueFrame> findAll(final int page, final int size, final String sort, final String order) {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "NpcDialogueFrame", keyGenerator = "cacheAutoKey")
	public List<NpcDialogueFrame> findAll() {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		final NpcDialogueFrameExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	public List<NpcDialogueFrame> selectAll() {
		final NpcDialogueFrameExample example = new NpcDialogueFrameExample();
		return this.mapper.selectByExample(example);
	}
	
	@CacheEvict(cacheNames = "NpcDialogueFrame", allEntries = true)
	public void refreshCache() {}
}
