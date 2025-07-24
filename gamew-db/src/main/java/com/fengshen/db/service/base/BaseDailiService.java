package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.auth.DailiMapper;
import com.fengshen.db.domain.Daili;
import com.fengshen.db.domain.example.DailiExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseDailiService
{
    @Autowired
    protected DailiMapper mapper;
    
    public Daili findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    public Daili findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    public void add(final Daili daili) {
        daili.setAddTime(LocalDateTime.now());
        daili.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(daili);
    }
    
    public int updateById(final Daili daili) {
        daili.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(daili);
    }
    
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    public List<Daili> findByAccount(final String account) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andAccountEqualTo(account);
        return this.mapper.selectByExample(example);
    }
    
    public List<Daili> findByPasswd(final String passwd) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andPasswdEqualTo(passwd);
        return this.mapper.selectByExample(example);
    }
    
    public List<Daili> findByCode(final String code) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andCodeEqualTo(code);
        return this.mapper.selectByExample(example);
    }
    
    public List<Daili> findByToken(final String token) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andTokenEqualTo(token);
        return this.mapper.selectByExample(example);
    }
    
    public Daili findOneByAccount(final String account) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andAccountEqualTo(account);
        return this.mapper.selectOneByExample(example);
    }
    
    public Daili findOneByPasswd(final String passwd) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andPasswdEqualTo(passwd);
        return this.mapper.selectOneByExample(example);
    }
    
    public Daili findOneByCode(final String code) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andCodeEqualTo(code);
        return this.mapper.selectOneByExample(example);
    }
    
    public Daili findOneByToken(final String token) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andTokenEqualTo(token);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<Daili> findAll(final int page, final int size, final String sort, final String order) {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    
    public List<Daili> findAll() {
        final DailiExample example = new DailiExample();
        final DailiExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
}
