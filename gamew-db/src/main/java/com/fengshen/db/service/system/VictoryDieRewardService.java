package com.fengshen.db.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.VictoryDieRewardMapper;
import com.fengshen.db.domain.VictoryDieReward;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

/**
 * 自定义死亡胜利奖励
 * 
 *
 */
@Service
public class VictoryDieRewardService implements BaseServiceSupport<VictoryDieReward> {

	@Autowired
	private VictoryDieRewardMapper vm;
	
	@Override
	public BaseCustomMapper<VictoryDieReward> getBaseMapper() {
		return vm;
	}

	
	/**
	 * 获取胜利后的奖励
	 * @param type
	 * @param name
	 * @return
	 */
	@Cacheable(cacheNames = "VictoryDieReward", key = "#name+'_'+#type")
	public VictoryDieReward victoryOrDieInfo(String name, int type) {
		Example example = new Example(VictoryDieReward.class);
		example.createCriteria().andEqualTo("name", name).andEqualTo("type", type).andEqualTo("deleted", 0);
		return vm.selectOneByExample(example);
	}
	
	@CacheEvict(cacheNames = "VictoryDieReward", allEntries = true)
	public int updateByRecord(VictoryDieReward record) {
		return vm.updateByPrimaryKeySelective(record);
	}
	
	@CacheEvict(cacheNames = "VictoryDieReward", allEntries = true)
	public void refreshCache() {}
}
