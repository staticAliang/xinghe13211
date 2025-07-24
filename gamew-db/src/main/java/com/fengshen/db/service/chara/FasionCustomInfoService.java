package com.fengshen.db.service.chara;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.FasionCustomInfoMapper;
import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class FasionCustomInfoService implements BaseServiceSupport<FasionCustomInfo> {

	@Autowired
	private FasionCustomInfoMapper pcim;
	
	@Override
	public BaseCustomMapper<FasionCustomInfo> getBaseMapper() {
		return pcim;
	}

	
	/**
	 * 根据类型去获取自定义时装
	 * @param type
	 * @return
	 */
	@Cacheable(cacheNames = "FasionCustomInfo", keyGenerator = "cacheAutoKey")
	public List<FasionCustomInfo> getFasionCustomInfoByCategory(int type) {
		Example example = new Example(FasionCustomInfo.class);
		example.createCriteria().andEqualTo("category", type).andEqualTo("deleted", 0);
		return pcim.selectByExample(example);
	}
	
	/**
	 * 根据名字去获取自定义时装
	 * @param name
	 * @return
	 */
	@Cacheable(cacheNames = "FasionCustomInfo", keyGenerator = "cacheAutoKey")
	public FasionCustomInfo getOneFasionCustomInfoByName(String name) {
		Example example = new Example(FasionCustomInfo.class);
		example.createCriteria().andEqualTo("name", name).andEqualTo("deleted", 0);
		return pcim.selectOneByExample(example);
	}
	
	/**
	 * 根据名字批量查询
	 * @param names
	 * @return
	 */
	@Cacheable(cacheNames = "FasionCustomInfo", keyGenerator = "cacheAutoKey")
	public List<FasionCustomInfo> findByInStrs(List<String> names) {
		Example example = new Example(FasionCustomInfo.class);
		example.createCriteria().andIn("name", names).andEqualTo("deleted", 0);
		return pcim.selectByExample(example);
	}
	
	@CacheEvict(cacheNames = "FasionCustomInfo", allEntries = true)
	public int updateById(FasionCustomInfo fci) {
		fci.setUpdateTime(new Date());
		return pcim.updateByPrimaryKeySelective(fci);
	}
	
	@CacheEvict(cacheNames = "FasionCustomInfo", allEntries = true)
  	public void refreshCache() {}
	
}