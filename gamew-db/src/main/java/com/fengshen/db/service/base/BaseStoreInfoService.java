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
public class BaseStoreInfoService
{
    @Autowired
    protected StoreInfoMapper mapper;
    
    @Cacheable(cacheNames = { "StoreInfo" }, keyGenerator = "cacheAutoKey")
    public StoreInfo findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    @CacheEvict(cacheNames = "StoreInfo", allEntries = true)
    public StoreInfo findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    @CacheEvict(cacheNames = "StoreInfo", allEntries = true)
    public void add(final StoreInfo storeInfo) {
        storeInfo.setAddTime(LocalDateTime.now());
        storeInfo.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(storeInfo);
    }
    
    @CacheEvict(cacheNames = "StoreInfo", allEntries = true)
    public int updateById(final StoreInfo storeInfo) {
        storeInfo.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(storeInfo);
    }
    
    @CacheEvict(cacheNames = "StoreInfo", allEntries = true)
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public List<StoreInfo> findByQuality(final String qualitys) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andQualityEqualTo(qualitys);
        return this.mapper.selectByExample(example);
    }
    
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public List<StoreInfo> findByName(final String names) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(names);
        return this.mapper.selectByExample(example);
    }
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public List<StoreInfo> findByRecognizeRecognized(final Integer recognizeRecognizeds) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andRecognizeRecognizedEqualTo(recognizeRecognizeds);
        return this.mapper.selectByExample(example);
    }
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public List<StoreInfo> findByRebuildLevel(final Integer rebuildLevels) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andRebuildLevelEqualTo(rebuildLevels);
        return this.mapper.selectByExample(example);
    }
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public List<StoreInfo> findBySilverCoin(final Integer silverCoins) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andSilverCoinEqualTo(silverCoins);
        return this.mapper.selectByExample(example);
    }
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public StoreInfo findOneByQuality(final String quality) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andQualityEqualTo(quality);
        return this.mapper.selectOneByExample(example);
    }
    
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public StoreInfo findOneByName(final String name) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectOneByExample(example);
    }

    public List<StoreInfo> findAll(final int page, final int size, final String sort, final String order) {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    
    @Cacheable(cacheNames = "StoreInfo", keyGenerator = "cacheAutoKey")
    public List<StoreInfo> findAll() {
        final StoreInfoExample example = new StoreInfoExample();
        final StoreInfoExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
    
    public List<StoreInfo> selectAll(StoreInfo info) {
        final StoreInfoExample example = new StoreInfoExample();
        if(info.getName() != null) {
        	 final StoreInfoExample.Criteria criteria = example.createCriteria();
             criteria.andNameEqualTo(info.getName());
        }
        return this.mapper.selectByExample(example);
    }
    
    //刷新缓存
  	@CacheEvict(cacheNames = "StoreInfo", allEntries = true)
  	public void refreshCache() {}
}
