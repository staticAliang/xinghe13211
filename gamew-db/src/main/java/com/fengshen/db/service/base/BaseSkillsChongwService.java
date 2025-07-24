package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.SkillsChongwMapper;
import com.fengshen.db.domain.SkillsChongw;
import com.fengshen.db.domain.example.SkillsChongwExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseSkillsChongwService {
	@Autowired
	protected SkillsChongwMapper mapper;

	public SkillsChongw findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public SkillsChongw findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final SkillsChongw skillsChongw) {
		skillsChongw.setAddTime(LocalDateTime.now());
		skillsChongw.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(skillsChongw);
	}

	public int updateById(final SkillsChongw skillsChongw) {
		skillsChongw.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(skillsChongw);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<SkillsChongw> findByOwnerid(final String ownerid) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andOwneridEqualTo(ownerid);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findBySkllCwid(final String skllCwid) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkllCwidEqualTo(skllCwid);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findBySkillIdHex(final String skillIdHex) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillIdHexEqualTo(skillIdHex);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findBySkillName(final String skillName) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findBySkillReqpolar(final Integer skillReqpolar) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillReqpolarEqualTo(skillReqpolar);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findBySkillLevel(final Integer skillLevel) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillLevelEqualTo(skillLevel);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findBySkillMubiao(final Integer skillMubiao) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMubiaoEqualTo(skillMubiao);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findByTianshuId(final String tianshuId) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTianshuIdEqualTo(tianshuId);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findByTianshuName(final String tianshuName) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTianshuNameEqualTo(tianshuName);
		return this.mapper.selectByExample(example);
	}

	public SkillsChongw findOneByOwnerid(final String ownerid) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andOwneridEqualTo(ownerid);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneBySkllCwid(final String skllCwid) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkllCwidEqualTo(skllCwid);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneBySkillIdHex(final String skillIdHex) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillIdHexEqualTo(skillIdHex);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneBySkillName(final String skillName) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneBySkillReqpolar(final Integer skillReqpolar) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillReqpolarEqualTo(skillReqpolar);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneBySkillLevel(final Integer skillLevel) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillLevelEqualTo(skillLevel);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneBySkillMubiao(final Integer skillMubiao) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillMubiaoEqualTo(skillMubiao);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneByTianshuId(final String tianshuId) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTianshuIdEqualTo(tianshuId);
		return this.mapper.selectOneByExample(example);
	}

	public SkillsChongw findOneByTianshuName(final String tianshuName) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTianshuNameEqualTo(tianshuName);
		return this.mapper.selectOneByExample(example);
	}

	public List<SkillsChongw> findAll(final int page, final int size, final String sort, final String order) {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<SkillsChongw> findAll() {
		final SkillsChongwExample example = new SkillsChongwExample();
		final SkillsChongwExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
