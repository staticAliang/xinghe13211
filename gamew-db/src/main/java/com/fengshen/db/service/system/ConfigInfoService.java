package com.fengshen.db.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ConfigInfoMapper;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class ConfigInfoService implements BaseServiceSupport<ConfigInfo> {

	@Autowired
	private ConfigInfoMapper cm;
	
	@Override
	public BaseCustomMapper<ConfigInfo> getBaseMapper() {
		return cm;
	}
	
	public List<ConfigInfo> getAll(ConfigInfo configInfo) {
		return cm.selectAll();
	}
	
	@Cacheable(cacheNames = "ConfigInfo", keyGenerator = "cacheAutoKey")
	public ConfigInfo getOneByKeyName(String keyName) {
		Example example = new Example(ConfigInfo.class);
		example.selectProperties("data");
		example.createCriteria().andEqualTo("keyName", keyName);
		ConfigInfo ci = cm.selectOneByExample(example);
		return ci;
	}
	
	@Cacheable(cacheNames = "ConfigInfo", keyGenerator = "cacheAutoKey")
	public ConfigInfo getOneByUuid(String uuid) {
		Example example = new Example(ConfigInfo.class);
		example.createCriteria().andEqualTo("uuid", uuid);
		ConfigInfo ci = cm.selectOneByExample(example);
		return ci;
	}
	
	// 刷新缓存
	@CacheEvict(cacheNames = "ConfigInfo", allEntries = true)
	public void refreshCache() {}
}