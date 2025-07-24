package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.auth.AccountsMapper;
import com.fengshen.db.domain.Accounts;
import com.mysql.jdbc.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
public class BaseAccountsService {
	@Autowired
	protected AccountsMapper mapper;

	public Accounts findById(final int id) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByPrimaryKey(id);
	}
	
	public Accounts findByIdContainsDelete(final int id) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByPrimaryKey(id);
	}
	
	public void add(final Accounts accounts) {
		accounts.setAddTime(LocalDateTime.now());
		accounts.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(accounts);
	}

	public int updateById(final Accounts accounts) {
		accounts.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(accounts);
	}

	public void deleteById(final int id) {
		this.mapper.deleteByPrimaryKey(id);
	}

	public List<Accounts> findByName(final String name) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name);
		return this.mapper.selectByExample(example);
	}
	public List<Accounts> findByIp(final String ip) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("registerIp", ip);
		return this.mapper.selectByExample(example);
	}

	public List<Accounts> findByToken(final String token) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("token", token);
		return this.mapper.selectByExample(example);
	}

	public List<Accounts> findByCode(final String code) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("registerCode", code);
		
		return this.mapper.selectByExample(example);
	}

	public Accounts findOneByName(final String name) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", name);
		return this.mapper.selectOneByExample(example);
	}
	public Accounts findOneByToken(final String token) {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("token", token);
		return this.mapper.selectOneByExample(example);
	}

	public List<Accounts> findAll() {
		Example example = new Example(Accounts.class);
		example.createCriteria().andEqualTo("deleted", false);
		return this.mapper.selectByExample(example);
	}
	
	public List<Accounts> findAllManage(String name, Integer privilege) {
		Example example = new Example(Accounts.class);
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isNullOrEmpty(name)) {
			createCriteria.andLike("name", "%"+name+"%");
		}
		createCriteria.andEqualTo("privilege", privilege);
		return this.mapper.selectByExample(example);
	}
}