package com.fengshen.db.service.shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.RareShopItemMapper;
import com.fengshen.db.domain.RareShopItem;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class RareShopItemService implements BaseServiceSupport<RareShopItem> {

	@Autowired
	private RareShopItemMapper rsim; 
	
	@Override
	public BaseCustomMapper<RareShopItem> getBaseMapper() {
		return rsim;
	}
	
}