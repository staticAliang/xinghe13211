package com.fengshen.db.service.system;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.LuckDrawItemMapper;
import com.fengshen.db.domain.LuckDrawItem;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

/**
 * 积分抽奖大使
 * 
 *
 */
@Service
public class LuckDrawItemService implements BaseServiceSupport<LuckDrawItem> {

	@Autowired
	private LuckDrawItemMapper ldim;
	
	@Override
	public BaseCustomMapper<LuckDrawItem> getBaseMapper() {
		return ldim;
	}

	@Cacheable(cacheNames = "LuckDrawItem", keyGenerator = "cacheAutoKey")
	public List<LuckDrawItem> getAll() {
		return ldim.selectAll();
	}
	
	@Cacheable(cacheNames = "LuckDrawItem", keyGenerator = "cacheAutoKey")
	public LuckDrawItem getLuckOneByItem(String item) {
		Example example = new Example(LuckDrawItem.class);
		example.createCriteria().andEqualTo("item", item);
		return ldim.selectOneByExample(example);
	}
	
	@Cacheable(cacheNames = "LuckDrawItem", keyGenerator = "cacheAutoKey")
	public List<LuckDrawItem> getLuckByLevel(int level) {
		Example example = new Example(LuckDrawItem.class);
		example.createCriteria().andEqualTo("level", level).andEqualTo("type", "choujiangdashi");
		return ldim.selectByExample(example);
	}
	@Cacheable(cacheNames = "LuckDrawItem", keyGenerator = "cacheAutoKey")
	public List<LuckDrawItem> getLuckByLevel(int level,String type) {
		Example example = new Example(LuckDrawItem.class);
		example.createCriteria().andEqualTo("level", level).andEqualTo("type", type);
		return ldim.selectByExample(example);
	}
	
	@CacheEvict(cacheNames = "LuckDrawItem", allEntries = true)
	public int add(LuckDrawItem item) {
		item.setAddTime(new Date());
		return ldim.insertSelective(item);
	}
	
	@CacheEvict(cacheNames = "LuckDrawItem", allEntries = true)
	public int deleteById(int id) {
		return ldim.deleteByPrimaryKey(id);
	}
	@CacheEvict(cacheNames = "LuckDrawItem", allEntries = true)
	public int deleteByIdAndType(int id,String type) {
		Example example = new Example(LuckDrawItem.class);
		example.createCriteria().andEqualTo("id", id).andEqualTo("type",type);
		return ldim.deleteByExample(example);
	}
	
	@CacheEvict(cacheNames = "LuckDrawItem", allEntries = true)
	public int updateById(LuckDrawItem item) {
		return ldim.updateByPrimaryKeySelective(item);
	}
	
	// 刷新缓存
	@CacheEvict(cacheNames = "LuckDrawItem", allEntries = true)
	public void refreshCache() {}
}