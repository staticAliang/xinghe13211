package com.fengshen.db.service.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ChargeConfigMapper;
import com.fengshen.db.domain.ChargeConfig;
import com.fengshen.db.service.base.BaseServiceSupport;

@Service
public class ChargeConfigService implements BaseServiceSupport<ChargeConfig> {

	@Autowired
	private ChargeConfigMapper cm;
	
	
	@Override
	public BaseCustomMapper<ChargeConfig> getBaseMapper() {
		return cm;
	}

}
