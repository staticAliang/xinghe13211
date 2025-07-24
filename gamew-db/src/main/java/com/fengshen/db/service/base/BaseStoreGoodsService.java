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
public class BaseStoreGoodsService {
	@Autowired
	protected StoreGoodsMapper mapper;

	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public StoreGoods findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@CacheEvict(cacheNames = "StoreGoods", allEntries = true)
	public StoreGoods findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = "StoreGoods", allEntries = true)
	public void add(final StoreGoods storeGoods) {
		storeGoods.setAddTime(LocalDateTime.now());
		this.mapper.insertSelective(storeGoods);
	}

	@CacheEvict(cacheNames = "StoreGoods", allEntries = true)
	public int updateById(final StoreGoods storeGoods) {
		storeGoods.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(storeGoods);
	}

	@CacheEvict(cacheNames = "StoreGoods", allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public List<StoreGoods> findByName(final String names) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(names);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public List<StoreGoods> findByBarcode(final String barcodes) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andBarcodeEqualTo(barcodes);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public List<StoreGoods> findByType(final Integer types) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(types);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public List<StoreGoods> findByMustVip(final Integer mustVips) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMustVipEqualTo(mustVips);
		return this.mapper.selectByExample(example);
	}
	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public List<StoreGoods> findByIsGift(final Integer isGifts) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIsGiftEqualTo(isGifts);
		return this.mapper.selectByExample(example);
	}
	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public StoreGoods findOneByName(final String name) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}
	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public StoreGoods findOneByBarcode(final String barcode) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andBarcodeEqualTo(barcode);
		return this.mapper.selectOneByExample(example);
	}
	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public StoreGoods findOneByForSale(final Integer forSale) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andForSaleEqualTo(forSale);
		return this.mapper.selectOneByExample(example);
	}
	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public StoreGoods findOneByType(final Integer type) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public StoreGoods findOneByMustVip(final Integer mustVip) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMustVipEqualTo(mustVip);
		return this.mapper.selectOneByExample(example);
	}
	public List<StoreGoods> findAll(final int page, final int size, final String sort, final String order) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}
	@Cacheable(cacheNames = "StoreGoods", keyGenerator = "cacheAutoKey")
	public List<StoreGoods> findAll() {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	public List<StoreGoods> selectAll(StoreGoods goods) {
		final StoreGoodsExample example = new StoreGoodsExample();
		final StoreGoodsExample.Criteria criteria = example.createCriteria();
		if (goods.getName() != null) {
			criteria.andNameEqualTo(goods.getName());
		}
		return this.mapper.selectByExample(example);
	}
	//刷新缓存
  	@CacheEvict(cacheNames = "StoreGoods", allEntries = true)
  	public void refreshCache() {}
}
