package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.SkilljinengMapper;
import com.fengshen.db.domain.Skilljineng;
import com.fengshen.db.domain.example.SkilljinengExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseSkilljinengService {
	@Autowired
	protected SkilljinengMapper mapper;

	public Skilljineng findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Skilljineng findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Skilljineng skilljineng) {
		skilljineng.setAddTime(LocalDateTime.now());
		skilljineng.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(skilljineng);
	}

	public int updateById(final Skilljineng skilljineng) {
		skilljineng.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(skilljineng);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Skilljineng> findByRid(final Integer rid) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRidEqualTo(rid);
		return this.mapper.selectByExample(example);
	}

	public List<Skilljineng> findByPid(final String pid) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPidEqualTo(pid);
		return this.mapper.selectByExample(example);
	}

	public List<Skilljineng> findBySkillName(final String skillName) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectByExample(example);
	}

	public List<Skilljineng> findBySkillLevel(final Integer skillLevel) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillLevelEqualTo(skillLevel);
		return this.mapper.selectByExample(example);
	}

	public List<Skilljineng> findBySkillMubiao(final Integer skillMubiao) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMubiaoEqualTo(skillMubiao);
		return this.mapper.selectByExample(example);
	}

	public List<Skilljineng> findBySkillMp(final Integer skillMp) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMpEqualTo(skillMp);
		return this.mapper.selectByExample(example);
	}

	public Skilljineng findOneByRid(final Integer rid) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRidEqualTo(rid);
		return this.mapper.selectOneByExample(example);
	}

	public Skilljineng findOneByPid(final String pid) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPidEqualTo(pid);
		return this.mapper.selectOneByExample(example);
	}

	public Skilljineng findOneBySkillName(final String skillName) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectOneByExample(example);
	}

	public Skilljineng findOneBySkillLevel(final Integer skillLevel) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillLevelEqualTo(skillLevel);
		return this.mapper.selectOneByExample(example);
	}

	public Skilljineng findOneBySkillMubiao(final Integer skillMubiao) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMubiaoEqualTo(skillMubiao);
		return this.mapper.selectOneByExample(example);
	}

	public Skilljineng findOneBySkillMp(final Integer skillMp) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMpEqualTo(skillMp);
		return this.mapper.selectOneByExample(example);
	}

	public List<Skilljineng> findAll(final int page, final int size, final String sort, final String order) {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Skilljineng> findAll() {
		final SkilljinengExample example = new SkilljinengExample();
		final SkilljinengExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
