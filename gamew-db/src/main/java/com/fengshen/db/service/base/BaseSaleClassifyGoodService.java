package com.fengshen.db.service.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.SaleClassifyGoodMapper;
import com.fengshen.db.domain.SaleClassifyGood;

import tk.mybatis.mapper.entity.Example;

@Service
public class BaseSaleClassifyGoodService implements BaseServiceSupport<SaleClassifyGood> {
	@Autowired
	protected SaleClassifyGoodMapper mapper;


	public SaleClassifyGood findOneByCompose(final String compose) {
		Example example = new Example(SaleClassifyGood.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("compose",compose);
		return this.mapper.selectOneByExample(example);
	}
	
	public SaleClassifyGood findOneByStr(final String name) {
		Example example = new Example(SaleClassifyGood.class);
		example.createCriteria().andEqualTo("deleted", false).andEqualTo("name",name);
		return this.mapper.selectOneByExample(example);
	}

	@Override
	public BaseCustomMapper<SaleClassifyGood> getBaseMapper() {
		return mapper;
	}
	
	

}
