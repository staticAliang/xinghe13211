package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.SrenwuMapper;
import com.fengshen.db.domain.Srenwu;
import com.fengshen.db.domain.example.SrenwuExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseSrenwuService {
	@Autowired
	protected SrenwuMapper mapper;

	public Srenwu findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Srenwu findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Srenwu srenwu) {
		srenwu.setAddTime(LocalDateTime.now());
		srenwu.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(srenwu);
	}

	public int updateById(final Srenwu srenwu) {
		srenwu.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(srenwu);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Srenwu> findByPid(final String pid) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPidEqualTo(pid);
		return this.mapper.selectByExample(example);
	}

	public List<Srenwu> findByRid(final Integer rid) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRidEqualTo(rid);
		return this.mapper.selectByExample(example);
	}

	public List<Srenwu> findBySkillName(final String skillName) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectByExample(example);
	}

	public List<Srenwu> findBySkillJieshao(final String skillJieshao) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillJieshaoEqualTo(skillJieshao);
		return this.mapper.selectByExample(example);
	}

	public List<Srenwu> findBySkillDqti(final String skillDqti) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillDqtiEqualTo(skillDqti);
		return this.mapper.selectByExample(example);
	}

	public List<Srenwu> findBySkillXck(final String skillXck) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillXckEqualTo(skillXck);
		return this.mapper.selectByExample(example);
	}

	public Srenwu findOneByPid(final String pid) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPidEqualTo(pid);
		return this.mapper.selectOneByExample(example);
	}

	public Srenwu findOneByRid(final Integer rid) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andRidEqualTo(rid);
		return this.mapper.selectOneByExample(example);
	}

	public Srenwu findOneBySkillName(final String skillName) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillNameEqualTo(skillName);
		return this.mapper.selectOneByExample(example);
	}

	public Srenwu findOneBySkillJieshao(final String skillJieshao) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillJieshaoEqualTo(skillJieshao);
		return this.mapper.selectOneByExample(example);
	}

	public Srenwu findOneBySkillDqti(final String skillDqti) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillDqtiEqualTo(skillDqti);
		return this.mapper.selectOneByExample(example);
	}

	public Srenwu findOneBySkillXck(final String skillXck) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andSkillXckEqualTo(skillXck);
		return this.mapper.selectOneByExample(example);
	}

	public List<Srenwu> findAll(final int page, final int size, final String sort, final String order) {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Srenwu> findAll() {
		final SrenwuExample example = new SrenwuExample();
		final SrenwuExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
