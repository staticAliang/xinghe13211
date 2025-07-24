package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.NpcMapper;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.example.NpcExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseNpcService
{
    @Autowired
    protected NpcMapper mapper;
    
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc findById(final Integer id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
    public Npc findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
//    @CacheEvict(allEntries = true, cacheNames = "Npc")
    public void add(final Npc npc) {
        npc.setAddTime(LocalDateTime.now());
        npc.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(npc);
    }
    
//    @CacheEvict(allEntries = true, cacheNames = "Npc")
    public int updateById(final Npc npc) {
        npc.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(npc);
    }
    
//    @CacheEvict(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public List<Npc> findByIcon(final Integer icons) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andIconEqualTo(icons);
        return this.mapper.selectByExample(example);
    }
    
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public List<Npc> findByX(final Integer xs) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andXEqualTo(xs);
        return this.mapper.selectByExample(example);
    }
    
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public List<Npc> findByY(final Integer ys) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andYEqualTo(ys);
        return this.mapper.selectByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public List<Npc> findByName(final String names) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(names);
        return this.mapper.selectByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public List<Npc> findByMapId(final Integer mapIds) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andMapIdEqualTo(mapIds);
        return this.mapper.selectByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc findOneByIcon(final Integer icon) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andIconEqualTo(icon);
        return this.mapper.selectOneByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc findOneByX(final Integer x) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andXEqualTo(x);
        return this.mapper.selectOneByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc findOneByY(final Integer y) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andYEqualTo(y);
        return this.mapper.selectOneByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc findOneByName(final String name) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectOneByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc findOneByMapId(final Integer mapId) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andMapIdEqualTo(mapId);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<Npc> findAll(final int page, final int size, final String sort, final String order) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public List<Npc> findAll() {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
    
    public List<Npc> selectAll(String name) {
    	final NpcExample example = new NpcExample();
    	final NpcExample.Criteria criteria = example.createCriteria();
    	if(!StringUtils.isEmpty(name)) {
    		criteria.andNameLike("%"+name+"%");
    	}
    	return this.mapper.selectByExample(example);
    }
    
//    @Cacheable(cacheNames = { "Npc" }, keyGenerator = "cacheAutoKey")
    public Npc selectById(Integer id) {
        final NpcExample example = new NpcExample();
        final NpcExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return this.mapper.selectOneByExample(example);
    }
    
    /**
     * 随机获取一个Npc
     * @return
     */
    public Npc randomData() {
    	return this.mapper.randomData();
    }
    
  //刷新缓存
  	@CacheEvict(cacheNames = "Npc", allEntries = true)
  	public void refreshCache() {}
}
