package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.MapsMapper;
import com.fengshen.db.domain.Maps;
import com.fengshen.db.domain.example.MapsExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseMapsService {
	@Autowired
	protected MapsMapper mapper;

	public Maps findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Maps findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Maps maps) {
		maps.setAddTime(LocalDateTime.now());
		maps.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(maps);
	}

	public int updateById(final Maps maps) {
		maps.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(maps);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Maps> findByName(final String name) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}

	public List<Maps> findByType(final Integer type) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectByExample(example);
	}

	public List<Maps> findByMap(final Integer map) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMapEqualTo(map);
		return this.mapper.selectByExample(example);
	}

	public List<Maps> findByDir(final Float dir) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDirEqualTo(dir);
		return this.mapper.selectByExample(example);
	}

	public List<Maps> findByX(final Float x) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andXEqualTo(x);
		return this.mapper.selectByExample(example);
	}

	public List<Maps> findByY(final Float y) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYEqualTo(y);
		return this.mapper.selectByExample(example);
	}

	public Maps findOneByName(final String name) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	public Maps findOneByType(final Integer type) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public Maps findOneByMap(final Integer map) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMapEqualTo(map);
		return this.mapper.selectOneByExample(example);
	}

	public Maps findOneByDir(final Float dir) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andDirEqualTo(dir);
		return this.mapper.selectOneByExample(example);
	}

	public Maps findOneByX(final Float x) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andXEqualTo(x);
		return this.mapper.selectOneByExample(example);
	}

	public Maps findOneByY(final Float y) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andYEqualTo(y);
		return this.mapper.selectOneByExample(example);
	}

	public List<Maps> findAll(final int page, final int size, final String sort, final String order) {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Maps> findAll() {
		final MapsExample example = new MapsExample();
		final MapsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
