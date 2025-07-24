package com.fengshen.db.service.base;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.RenwuMapper;
import com.fengshen.db.domain.Renwu;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class BaseRenwuService {

	@Autowired
	protected RenwuMapper mapper;

	@Cacheable(cacheNames = { "Renwu" }, keyGenerator = "cacheAutoKey")
	public Renwu findById(int id) {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("id", id);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "Renwu" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
	public Renwu findByIdContainsDelete(int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	
	public void add(Renwu renwu) {
		renwu.setAddTime(new Date());
		renwu.setUpdateTime(new Date());
		this.mapper.insertSelective(renwu);
	}

	@CacheEvict(cacheNames = { "Renwu" }, allEntries = true)
	public int updateById(Renwu renwu) {
		renwu.setUpdateTime(new Date());
		return this.mapper.updateByPrimaryKeySelective(renwu);
	}

	@CacheEvict(cacheNames = { "Renwu" }, allEntries = true)
	public void deleteById(int id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "Renwu", keyGenerator = "cacheAutoKey")
	public List<Renwu> findByNpcName(String npcNames) {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("npcName", npcNames);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Renwu", keyGenerator = "cacheAutoKey")
	public List<Renwu> findByShowName(String showNames) {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("showName", showNames);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Renwu", keyGenerator = "cacheAutoKey")
	public Renwu findOneByNpcName(String npcName) {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("npcName", npcName);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Renwu", keyGenerator = "cacheAutoKey")
	public Renwu findOneByCurrentTask(String currentTask) {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("currentTask", currentTask);
		return this.mapper.selectOneByExample(example);
	}

	public List<Renwu> findAll(int page, int size, String sort, String order) {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Renwu", keyGenerator = "cacheAutoKey")
	public List<Renwu> findAll() {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}

	public List<Renwu> selectAll() {
		Example example = new Example(Renwu.class);
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}

	// 刷新缓存
	@CacheEvict(cacheNames = "Renwu", allEntries = true)
	public void refreshCache() {
	}
}
