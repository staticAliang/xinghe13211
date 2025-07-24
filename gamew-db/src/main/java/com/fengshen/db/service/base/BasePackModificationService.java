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
public class BasePackModificationService {
	@Autowired
	protected PackModificationMapper mapper;

	public PackModification findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public PackModification findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final PackModification packModification) {
		packModification.setAddTime(LocalDateTime.now());
		packModification.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(packModification);
	}

	public int updateById(final PackModification packModification) {
		packModification.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(packModification);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<PackModification> findByAlias(final String alias) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAliasEqualTo(alias);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByFasionType(final String fasionType) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFasionTypeEqualTo(fasionType);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByStr(final String str) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andStrEqualTo(str);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByType(final String type) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByFoodNum(final Integer foodNum) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFoodNumEqualTo(foodNum);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByGoodsPrice(final Integer goodsPrice) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andGoodsPriceEqualTo(goodsPrice);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findBySex(final Integer sex) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSexEqualTo(sex);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByPosition(final Integer position) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPositionEqualTo(position);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findByCategory(final Integer category) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCategoryEqualTo(category);
		return this.mapper.selectByExample(example);
	}

	public PackModification findOneByAlias(final String alias) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAliasEqualTo(alias);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByFasionType(final String fasionType) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFasionTypeEqualTo(fasionType);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByStr(final String str) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andStrEqualTo(str);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByType(final String type) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByFoodNum(final Integer foodNum) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andFoodNumEqualTo(foodNum);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByGoodsPrice(final Integer goodsPrice) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andGoodsPriceEqualTo(goodsPrice);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneBySex(final Integer sex) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSexEqualTo(sex);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByPosition(final Integer position) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPositionEqualTo(position);
		return this.mapper.selectOneByExample(example);
	}

	public PackModification findOneByCategory(final Integer category) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCategoryEqualTo(category);
		return this.mapper.selectOneByExample(example);
	}

	public List<PackModification> findAll(final int page, final int size, final String sort, final String order) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<PackModification> findAll() {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	public PackModification findOneBySexForType(final Integer position, int sex) {
		final PackModificationExample example = new PackModificationExample();
		final PackModificationExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPositionEqualTo(position).andSexEqualTo(sex);
		return this.mapper.selectOneByExample(example);
	}
}
