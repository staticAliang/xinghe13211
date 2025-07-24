package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.SkillsMapper;
import com.fengshen.db.domain.Skills;
import com.fengshen.db.domain.example.SkillsExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseSkillsService {
	@Autowired
	protected SkillsMapper mapper;

	public Skills findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Skills findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Skills skills) {
		skills.setAddTime(LocalDateTime.now());
		skills.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(skills);
	}

	public int updateById(final Skills skills) {
		skills.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(skills);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Skills> findBySkillIdHex(final String skillIdHex) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillIdHexEqualTo(skillIdHex);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillName(final String skillName) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillReqpolar(final Integer skillReqpolar) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillReqpolarEqualTo(skillReqpolar);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillType(final Integer skillType) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillTypeEqualTo(skillType);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillTypeLevel(final Integer skillTypeLevel) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillTypeLevelEqualTo(skillTypeLevel);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillMagic(final Integer skillMagic) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMagicEqualTo(skillMagic);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillReqLevel(final Integer skillReqLevel) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillReqLevelEqualTo(skillReqLevel);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findBySkillContext(final String skillContext) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillContextEqualTo(skillContext);
		return this.mapper.selectByExample(example);
	}

	public Skills findOneBySkillIdHex(final String skillIdHex) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillIdHexEqualTo(skillIdHex);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillName(final String skillName) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillReqpolar(final Integer skillReqpolar) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillReqpolarEqualTo(skillReqpolar);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillType(final Integer skillType) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillTypeEqualTo(skillType);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillTypeLevel(final Integer skillTypeLevel) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillTypeLevelEqualTo(skillTypeLevel);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillMagic(final Integer skillMagic) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMagicEqualTo(skillMagic);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillReqLevel(final Integer skillReqLevel) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillReqLevelEqualTo(skillReqLevel);
		return this.mapper.selectOneByExample(example);
	}

	public Skills findOneBySkillContext(final String skillContext) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillContextEqualTo(skillContext);
		return this.mapper.selectOneByExample(example);
	}

	public List<Skills> findAll(final int page, final int size, final String sort, final String order) {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Skills> findAll() {
		final SkillsExample example = new SkillsExample();
		final SkillsExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
