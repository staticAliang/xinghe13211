package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.ReportsMapper;
import com.fengshen.db.domain.Reports;
import com.fengshen.db.domain.example.ReportsExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseReportsService
{
    @Autowired
    protected ReportsMapper mapper;
    
    public Reports findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    public Reports findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    public void add(final Reports reports) {
        reports.setAddTime(LocalDateTime.now());
        reports.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(reports);
    }
    
    public int updateById(final Reports reports) {
        reports.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(reports);
    }
    
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    public List<Reports> findByZhanghao(final String zhanghao) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andZhanghaoEqualTo(zhanghao);
        return this.mapper.selectByExample(example);
    }
    
    public List<Reports> findByYuanbaoshu(final Integer yuanbaoshu) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andYuanbaoshuEqualTo(yuanbaoshu);
        return this.mapper.selectByExample(example);
    }
    
    public List<Reports> findByShifouchongzhi(final String shifouchongzhi) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andShifouchongzhiEqualTo(shifouchongzhi);
        return this.mapper.selectByExample(example);
    }
    
    public Reports findOneByZhanghao(final String zhanghao) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andZhanghaoEqualTo(zhanghao);
        return this.mapper.selectOneByExample(example);
    }
    
    public Reports findOneByYuanbaoshu(final Integer yuanbaoshu) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andYuanbaoshuEqualTo(yuanbaoshu);
        return this.mapper.selectOneByExample(example);
    }
    
    public Reports findOneByShifouchongzhi(final String shifouchongzhi) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andShifouchongzhiEqualTo(shifouchongzhi);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<Reports> findAll(final int page, final int size, final String sort, final String order) {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    
    public List<Reports> findAll() {
        final ReportsExample example = new ReportsExample();
        final ReportsExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
}
