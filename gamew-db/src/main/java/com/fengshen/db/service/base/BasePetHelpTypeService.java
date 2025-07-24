package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.PetHelpTypeMapper;
import com.fengshen.db.domain.PetHelpType;
import com.fengshen.db.domain.example.PetHelpTypeExample;
import com.github.pagehelper.PageHelper;

@Service
public class BasePetHelpTypeService {
	@Autowired
	protected PetHelpTypeMapper mapper;

	public PetHelpType findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public PetHelpType findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final PetHelpType petHelpType) {
		petHelpType.setAddTime(LocalDateTime.now());
		petHelpType.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(petHelpType);
	}

	public int updateById(final PetHelpType petHelpType) {
		petHelpType.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(petHelpType);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<PetHelpType> findByType(final Integer type) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectByExample(example);
	}

	public List<PetHelpType> findByName(final String name) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}

	public List<PetHelpType> findByQuality(final Integer quality) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andQualityEqualTo(quality);
		return this.mapper.selectByExample(example);
	}

	public List<PetHelpType> findByMoney(final Integer money) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMoneyEqualTo(money);
		return this.mapper.selectByExample(example);
	}

	public List<PetHelpType> findByPolar(final Integer polar) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPolarEqualTo(polar);
		return this.mapper.selectByExample(example);
	}

	public PetHelpType findOneByType(final Integer type) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public PetHelpType findOneByName(final String name) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	public PetHelpType findOneByQuality(final Integer quality) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andQualityEqualTo(quality);
		return this.mapper.selectOneByExample(example);
	}

	public PetHelpType findOneByMoney(final Integer money) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMoneyEqualTo(money);
		return this.mapper.selectOneByExample(example);
	}

	public PetHelpType findOneByPolar(final Integer polar) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPolarEqualTo(polar);
		return this.mapper.selectOneByExample(example);
	}

	public List<PetHelpType> findAll(final int page, final int size, final String sort, final String order) {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<PetHelpType> findAll() {
		final PetHelpTypeExample example = new PetHelpTypeExample();
		final PetHelpTypeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
