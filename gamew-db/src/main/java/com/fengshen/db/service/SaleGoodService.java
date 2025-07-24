package com.fengshen.db.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.SaleGoodMapper;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.db.service.base.BaseServiceSupport;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

@Service
public class SaleGoodService implements BaseServiceSupport<SaleGood> {
	
	@Autowired
	private SaleGoodMapper mapper;
	
	
	public SaleGood findOneByGoodsId(String goodsId) {
		Example example = new Example(SaleGood.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted", false).andEqualTo("goodsId", goodsId);
		return this.mapper.selectOneByExample(example);
	}

	public List<SaleGood> findByStr(String alias) {
		Example example = new Example(SaleGood.class);
		example.orderBy("addTime").desc();
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted", false).andEqualTo("alias", alias);
		return this.mapper.selectByExample(example);
	}
	
	public PageInfo<SaleGood> findBySaleGoodPage(Page<SaleGood> page,SaleGood good) {
		good.setDeleted(false);
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		PageInfo<SaleGood> pageInfo = new PageInfo<>(this.mapper.select(good));
		return pageInfo;
	}

	public List<SaleGood> findByOwnerUuid(String uuid) {
		Example example = new Example(SaleGood.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted", false).andEqualTo("gid", uuid);
		return this.mapper.selectByExample(example);
	}
	
	public int deleteById(int id) {
		
		return mapper.deleteByPrimaryKey(id); 
	}
	
	public int updateById(SaleGood s) {
		
		return mapper.updateByPrimaryKey(s);
	}

	public BaseCustomMapper<SaleGood> getBaseMapper() {
		return mapper;
	}
}
