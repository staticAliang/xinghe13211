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
public class BaseNpcDialogueService
{
    @Autowired
    protected NpcDialogueMapper mapper;
    
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public NpcDialogue findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
    public NpcDialogue findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    @CacheEvict(cacheNames = "NpcDialogue" , allEntries = true)
    public void add(final NpcDialogue npcDialogue) {
        npcDialogue.setAddTime(LocalDateTime.now());
        npcDialogue.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(npcDialogue);
    }
    
    @CacheEvict(cacheNames = "NpcDialogue" , allEntries = true)
    public int updateById(final NpcDialogue npcDialogue) {
        npcDialogue.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(npcDialogue);
    }
    
    @CacheEvict(cacheNames = "NpcDialogue" , allEntries = true)
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public List<NpcDialogue> findByName(final String names) {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(names);
        return this.mapper.selectByExample(example);
    }
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public List<NpcDialogue> findByIdname(final String idnames) {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andIdnameEqualTo(idnames);
        return this.mapper.selectByExample(example);
    }
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public NpcDialogue findOneByName(final String name) {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectOneByExample(example);
    }
    
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public NpcDialogue findOneByTaskType(final String taskType) {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andTaskTypeEqualTo(taskType);
        return this.mapper.selectOneByExample(example);
    }
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public NpcDialogue findOneByIdname(final String idname) {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andIdnameEqualTo(idname);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<NpcDialogue> findAll(final int page, final int size, final String sort, final String order) {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    @Cacheable(cacheNames = { "NpcDialogue" }, keyGenerator = "cacheAutoKey")
    public List<NpcDialogue> findAll() {
        final NpcDialogueExample example = new NpcDialogueExample();
        final NpcDialogueExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
    public List<NpcDialogue> selectAll() {
    	final NpcDialogueExample example = new NpcDialogueExample();
    	return this.mapper.selectByExample(example);
    }
    
    @CacheEvict(cacheNames = "NpcDialogue", allEntries = true)
	public void refreshCache() {}
}
