package com.fengshen.db.service.base;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import org.springframework.cache.annotation.*;
import java.util.*;

import org.springframework.util.*;

import com.fengshen.db.dao.*;
import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;
import com.github.pagehelper.*;

@Service
public class BaseMedicineShopService
{
    @Autowired
    protected MedicineShopMapper mapper;
    
    @Cacheable(cacheNames = { "MedicineShop" }, key = "#id")
    public MedicineShop findById(final int id) {
        return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
    }
    
    public MedicineShop findByIdContainsDelete(final int id) {
        return this.mapper.selectByPrimaryKey(id);
    }
    
    public void add(final MedicineShop medicineShop) {
        medicineShop.setAddTime(LocalDateTime.now());
        medicineShop.setUpdateTime(LocalDateTime.now());
        this.mapper.insertSelective(medicineShop);
    }
    
    public int updateById(final MedicineShop medicineShop) {
        medicineShop.setUpdateTime(LocalDateTime.now());
        return this.mapper.updateByPrimaryKeySelective(medicineShop);
    }
    
    public void deleteById(final int id) {
        this.mapper.logicalDeleteByPrimaryKey(id);
    }
    
    public List<MedicineShop> findByGoodsNo(final Integer goodsNo) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andGoodsNoEqualTo(goodsNo);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findByPayType(final Integer payType) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andPayTypeEqualTo(payType);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findByName(final String name) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findByValue(final Integer value) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andValueEqualTo(value);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findByLevel(final Integer level) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findByType(final Integer type) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findByItemcount(final Integer itemcount) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andItemcountEqualTo(itemcount);
        return this.mapper.selectByExample(example);
    }
    
    public MedicineShop findOneByGoodsNo(final Integer goodsNo) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andGoodsNoEqualTo(goodsNo);
        return this.mapper.selectOneByExample(example);
    }
    
    public MedicineShop findOneByPayType(final Integer payType) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andPayTypeEqualTo(payType);
        return this.mapper.selectOneByExample(example);
    }
    
    public MedicineShop findOneByName(final String name) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andNameEqualTo(name);
        return this.mapper.selectOneByExample(example);
    }
    
    public MedicineShop findOneByValue(final Integer value) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andValueEqualTo(value);
        return this.mapper.selectOneByExample(example);
    }
    
    public MedicineShop findOneByLevel(final Integer level) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andLevelEqualTo(level);
        return this.mapper.selectOneByExample(example);
    }
    
    public MedicineShop findOneByType(final Integer type) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
        return this.mapper.selectOneByExample(example);
    }
    
    public MedicineShop findOneByItemcount(final Integer itemcount) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false).andItemcountEqualTo(itemcount);
        return this.mapper.selectOneByExample(example);
    }
    
    public List<MedicineShop> findAll(final int page, final int size, final String sort, final String order) {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        if (!StringUtils.isEmpty((Object)sort) && !StringUtils.isEmpty((Object)order)) {
            example.setOrderByClause(String.valueOf(sort) + " " + order);
        }
        PageHelper.startPage(page, size);
        return this.mapper.selectByExample(example);
    }
    
    public List<MedicineShop> findAll() {
        final MedicineShopExample example = new MedicineShopExample();
        final MedicineShopExample.Criteria criteria = example.createCriteria();
        criteria.andDeletedEqualTo(false);
        return this.mapper.selectByExample(example);
    }
}
