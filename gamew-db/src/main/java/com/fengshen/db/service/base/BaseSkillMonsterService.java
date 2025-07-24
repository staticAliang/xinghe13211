package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.SkillMonsterMapper;
import com.fengshen.db.domain.SkillMonster;
import com.fengshen.db.domain.example.SkillMonsterExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseSkillMonsterService {
	@Autowired
	protected SkillMonsterMapper mapper;

	public SkillMonster findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public SkillMonster findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final SkillMonster skillMonster) {
		skillMonster.setAddTime(LocalDateTime.now());
		skillMonster.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(skillMonster);
	}

	public int updateById(final SkillMonster skillMonster) {
		skillMonster.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(skillMonster);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<SkillMonster> findByName(final String name) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectByExample(example);
	}

	public List<SkillMonster> findBySkills(final String skills) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillsEqualTo(skills);
		return this.mapper.selectByExample(example);
	}

	public List<SkillMonster> findByType(final Integer type) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectByExample(example);
	}

	public SkillMonster findOneByName(final String name) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	public SkillMonster findOneBySkills(final String skills) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillsEqualTo(skills);
		return this.mapper.selectOneByExample(example);
	}

	public SkillMonster findOneByType(final Integer type) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	public List<SkillMonster> findAll(final int page, final int size, final String sort, final String order) {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<SkillMonster> findAll() {
		final SkillMonsterExample example = new SkillMonsterExample();
		final SkillMonsterExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
