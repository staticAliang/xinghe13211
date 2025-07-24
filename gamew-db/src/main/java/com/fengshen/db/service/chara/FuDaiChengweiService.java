package com.fengshen.db.service.chara;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ChengweiMapper;
import com.fengshen.db.dao.FuDaiChengweiMapper;
import com.fengshen.db.domain.FuDaiChengwei;
import com.fengshen.db.service.base.BaseServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class FuDaiChengweiService implements BaseServiceSupport<FuDaiChengwei>{

	@Autowired
	private FuDaiChengweiMapper cm;
	
	
	@Override
	public BaseCustomMapper<FuDaiChengwei> getBaseMapper() {
		return cm;
	}
	
	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "FuDaiChengwei")
	public FuDaiChengwei getChengweiByName(String name) {
		if(name == null) {
			return null;
		}
		Example example = new Example(FuDaiChengwei.class);
		example.createCriteria().andEqualTo("name", name);
		return cm.selectOneByExample(example);
	}
	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "FuDaiChengwei")
	public List<FuDaiChengwei> getAllReChargeChengwei() {
		Example example = new Example(FuDaiChengwei.class);
		example.orderBy("cishu").asc();
		example.createCriteria().andGreaterThan("cishu", 0);
		return cm.selectByExample(example);
	}

	/**
	 * 获取充值称谓奖励
	 * @param money
	 * @return
	 */
	public List<FuDaiChengwei> getChengweiMoney(int money) {
		Example example = new Example(FuDaiChengwei.class);
		example.createCriteria().andLessThanOrEqualTo("cishu", money);
		return cm.selectByExample(example);
	}

	public List<FuDaiChengwei> getChengweiMoneyDanBi(int money) {
		Example example = new Example(FuDaiChengwei.class);
		example.createCriteria().andEqualTo("cishu", money);
		return cm.selectByExample(example);
	}

	@CacheEvict(cacheNames = "FuDaiChengwei", allEntries = true)
	public int addChengwei(FuDaiChengwei chengwei) {
		return cm.insertSelective(chengwei);
	}

	@CacheEvict(cacheNames = "FuDaiChengwei", allEntries = true)
	public int delChengweiById(int id) {
		return cm.deleteByPrimaryKey(id);
	}
	@CacheEvict(cacheNames = "FuDaiChengwei", allEntries = true)
	public int updateChengweiById(FuDaiChengwei f) {
		return cm.updateByPrimaryKeySelective(f);
	}

	@CacheEvict(cacheNames = "FuDaiChengwei", allEntries = true)
	public void refreshCache() {}
}