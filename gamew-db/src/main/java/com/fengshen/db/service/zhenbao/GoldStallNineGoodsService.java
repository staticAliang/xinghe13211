package com.fengshen.db.service.zhenbao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.GoldStallNineGoodsMapper;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class GoldStallNineGoodsService implements BaseServiceSupport<GoldStallNineGoods> {

	@Autowired
	private GoldStallNineGoodsMapper mapper;
	
	@Override
	public BaseCustomMapper<GoldStallNineGoods> getBaseMapper() {
		return mapper;
	}

}
