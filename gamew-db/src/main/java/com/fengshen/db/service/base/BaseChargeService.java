package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.ChargeMapper;
import com.fengshen.db.domain.Charge;
import com.fengshen.db.domain.example.ChargeExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseChargeService {
	@Autowired
	protected ChargeMapper mapper;

	public Charge findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Charge findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	public void add(final Charge charge) {
		charge.setAddTime(LocalDateTime.now());
		charge.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(charge);
	}

	public int updateById(final Charge charge) {
		charge.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(charge);
	}

	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	public List<Charge> findByAccountname(final String accountname) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAccountnameEqualTo(accountname);
		return this.mapper.selectByExample(example);
	}
	
	public List<Charge> findByAccountname(String accountname, int state) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAccountnameEqualTo(accountname).andStateEqualTo(state);
		return this.mapper.selectByExample(example);
	}

	public List<Charge> findByCoin(final Integer coin) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCoinEqualTo(coin);
		return this.mapper.selectByExample(example);
	}

	public List<Charge> findByState(final Integer state) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andStateEqualTo(state);
		return this.mapper.selectByExample(example);
	}

	public List<Charge> findByMoney(final Integer money) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMoneyEqualTo(money);
		return this.mapper.selectByExample(example);
	}

	public List<Charge> findByCode(final String code) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCodeEqualTo(code);
		return this.mapper.selectByExample(example);
	}

	public Charge findOneByAccountname(final String accountname) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAccountnameEqualTo(accountname);
		return this.mapper.selectOneByExample(example);
	}

	public Charge findOneByCoin(final Integer coin) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCoinEqualTo(coin);
		return this.mapper.selectOneByExample(example);
	}

	public Charge findOneByState(final Integer state) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andStateEqualTo(state);
		return this.mapper.selectOneByExample(example);
	}

	public Charge findOneByMoney(final Integer money) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMoneyEqualTo(money);
		return this.mapper.selectOneByExample(example);
	}

	public Charge findOneByAccountMoney(String accountName,final Integer money) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAccountnameEqualTo(accountName).andMoneyEqualTo(money).andStatusEqualTo(0);
		return this.mapper.selectOneByExample(example);
	}

	public Charge findOneByCode(final String code) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andCodeEqualTo(code);
		return this.mapper.selectOneByExample(example);
	}

	public List<Charge> findAll(final int page, final int size, final String sort, final String order) {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	public List<Charge> findAll() {
		final ChargeExample example = new ChargeExample();
		final ChargeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
}
