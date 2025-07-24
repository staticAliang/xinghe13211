package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.GroceriesShopMapper;
import com.fengshen.db.domain.GroceriesShop;
import com.fengshen.db.domain.example.GroceriesShopExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseGroceriesShopService {
	@Autowired
	protected GroceriesShopMapper mapper;

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final GroceriesShop groceriesShop) {
		groceriesShop.setAddTime(LocalDateTime.now());
		groceriesShop.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(groceriesShop);
	}

	@CacheEvict(cacheNames = { "Map" }, allEntries = true)
	public int updateById(final GroceriesShop groceriesShop) {
		groceriesShop.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(groceriesShop);
	}

	@CacheEvict(cacheNames = { "Map" }, allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public List<GroceriesShop> findByGoodsNo(final Integer goodsNo) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andGoodsNoEqualTo(goodsNo);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public List<GroceriesShop> findByPayType(final Integer payType) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPayTypeEqualTo(payType);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public List<GroceriesShop> findByName(final String name) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public List<GroceriesShop> findByLevel(final Integer level) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findOneByGoodsNo(final Integer goodsNo) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andGoodsNoEqualTo(goodsNo);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findOneByPayType(final Integer payType) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPayTypeEqualTo(payType);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findOneByName(final String name) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findOneByLevel(final Integer level) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = { "GroceriesShop" }, keyGenerator = "cacheAutoKey")
	public GroceriesShop findOneByType(final Integer type) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public List<GroceriesShop> findAll(final int page, final int size, final String sort, final String order) {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<GroceriesShop> findAll() {
		final GroceriesShopExample example = new GroceriesShopExample();
		final GroceriesShopExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
	
	@CacheEvict(cacheNames = "GroceriesShop", allEntries = true)
  	public void refreshCache() {}
}
