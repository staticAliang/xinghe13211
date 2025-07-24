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
public class BaseCreepsStoreService
{
    @Autowired
    protected CreepsStoreMapper mapper;
    
    @Cacheable(cacheNames = { "CreepsStore" }, key = "#id")
    public CreepsStore findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    @Cacheable(cacheNames = { "CreepsStore" }, key = "#id", condition = "#result.deleted == 0")
    public CreepsStore findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    public void add(final CreepsStore creepsStore) {
        creepsStore.setAddTime(LocalDateTime.now());
        creepsStore.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(creepsStore);
    }
    
    @CachePut(cacheNames = { "CreepsStore" }, key = "#creepsStore.id")
    public int updateById(final CreepsStore creepsStore) {
        creepsStore.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(creepsStore);
    }
    
    @CacheEvict(cacheNames = { "CreepsStore" }, key = "#id")
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    public List<CreepsStore> findByName(final String name) {
        final CreepsStoreExample example = new CreepsStoreExample();
        final CreepsStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectByExample(example);
    }
    
    public List<CreepsStore> findByPrice(final Integer price) {
        final CreepsStoreExample example = new CreepsStoreExample();
        final CreepsStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andPriceEqualTo(price);
        return this.mapper.selectByExample(example);
    }
    
    public CreepsStore findOneByName(final String name) {
        final CreepsStoreExample example = new CreepsStoreExample();
        final CreepsStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectOneByExample(example);
    }
    
    public CreepsStore findOneByPrice(final Integer price) {
        final CreepsStoreExample example = new CreepsStoreExample();
        final CreepsStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andPriceEqualTo(price);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<CreepsStore> findAll(final int page, final int size, final String sort, final String order) {
        final CreepsStoreExample example = new CreepsStoreExample();
        final CreepsStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    
    public List<CreepsStore> findAll() {
        final CreepsStoreExample example = new CreepsStoreExample();
        final CreepsStoreExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
}
