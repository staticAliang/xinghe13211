package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.SkilldataMapper;
import com.fengshen.db.domain.Skilldata;
import com.fengshen.db.domain.example.SkilldataExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseSkilldataService {
	@Autowired
	protected SkilldataMapper mapper;

	public Skilldata findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Skilldata findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Skilldata skilldata) {
		skilldata.setAddTime(LocalDateTime.now());
		skilldata.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(skilldata);
	}

	public int updateById(final Skilldata skilldata) {
		skilldata.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(skilldata);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Skilldata> findByPid(final String pid) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPidEqualTo(pid);
		return this.mapper.selectByExample(example);
	}

	public List<Skilldata> findBySkillName(final String skillName) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectByExample(example);
	}

	public List<Skilldata> findBySkillLevel(final Integer skillLevel) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillLevelEqualTo(skillLevel);
		return this.mapper.selectByExample(example);
	}

	public List<Skilldata> findBySkillMubiao(final Integer skillMubiao) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMubiaoEqualTo(skillMubiao);
		return this.mapper.selectByExample(example);
	}

	public Skilldata findOneByPid(final String pid) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPidEqualTo(pid);
		return this.mapper.selectOneByExample(example);
	}

	public Skilldata findOneBySkillName(final String skillName) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectOneByExample(example);
	}

	public Skilldata findOneBySkillLevel(final Integer skillLevel) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillLevelEqualTo(skillLevel);
		return this.mapper.selectOneByExample(example);
	}

	public Skilldata findOneBySkillMubiao(final Integer skillMubiao) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMubiaoEqualTo(skillMubiao);
		return this.mapper.selectOneByExample(example);
	}

	public List<Skilldata> findAll(final int page, final int size, final String sort, final String order) {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Skilldata> findAll() {
		final SkilldataExample example = new SkilldataExample();
		final SkilldataExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
