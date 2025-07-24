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
public class BaseNpcPointService
{
    @Autowired
    protected NpcPointMapper mapper;
    
    @Cacheable(cacheNames = { "NpcPoint" }, keyGenerator = "cacheAutoKey")
    public NpcPoint findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    @Cacheable(cacheNames = { "NpcPoint" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
    public NpcPoint findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    @CacheEvict(cacheNames = { "NpcPoint" }, allEntries = true)
    public void add(final NpcPoint npcPoint) {
        npcPoint.setAddTime(LocalDateTime.now());
        npcPoint.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(npcPoint);
    }
    
    @CachePut(cacheNames = { "NpcPoint" }, keyGenerator = "cacheAutoKey")
    public int updateById(final NpcPoint npcPoint) {
        npcPoint.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(npcPoint);
    }
    
    @CacheEvict(cacheNames = { "NpcPoint" }, allEntries = true)
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    @Cacheable(cacheNames = "NpcPoint", keyGenerator = "cacheAutoKey")
    public List<NpcPoint> findByMapname(final String mapnames) {
        final NpcPointExample example = new NpcPointExample();
        final NpcPointExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andMapnameEqualTo(mapnames);
        return this.mapper.selectByExample(example);
    }
    @Cacheable(cacheNames = "NpcPoint", keyGenerator = "cacheAutoKey")
    public List<NpcPoint> findByDoorname(final String doornames) {
        final NpcPointExample example = new NpcPointExample();
        final NpcPointExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andDoornameEqualTo(doornames);
        return this.mapper.selectByExample(example);
    }
    @Cacheable(cacheNames = "NpcPoint", keyGenerator = "cacheAutoKey")
    public NpcPoint findOneByMapname(final String mapname) {
        final NpcPointExample example = new NpcPointExample();
        final NpcPointExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andMapnameEqualTo(mapname);
        return this.mapper.selectOneByExample(example);
    }
    @Cacheable(cacheNames = "NpcPoint", keyGenerator = "cacheAutoKey")
    public NpcPoint findOneByDoorname(final String doorname) {
        final NpcPointExample example = new NpcPointExample();
        final NpcPointExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andDoornameEqualTo(doorname);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<NpcPoint> findAll(final int page, final int size, final String sort, final String order) {
        final NpcPointExample example = new NpcPointExample();
        final NpcPointExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    @Cacheable(cacheNames = { "NpcPoint" }, keyGenerator = "cacheAutoKey")
    public List<NpcPoint> findAll() {
        final NpcPointExample example = new NpcPointExample();
        final NpcPointExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
    
    @CacheEvict(cacheNames = "NpcPoint", allEntries = true)
  	public void refreshCache() {}
}
