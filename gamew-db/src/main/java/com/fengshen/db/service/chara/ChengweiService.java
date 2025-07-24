package com.fengshen.db.service.chara;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.ChengweiMapper;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class ChengweiService implements BaseServiceSupport<Chengwei>{

	@Autowired
	private ChengweiMapper cm;
	
	
	@Override
	public BaseCustomMapper<Chengwei> getBaseMapper() {
		return cm;
	}
	
	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "Chengwei")
	public Chengwei getChengweiByName(String name) {
		if(name == null) {
			return null;
		}
		Example example = new Example(Chengwei.class);
		example.createCriteria().andEqualTo("name", name);
		return cm.selectOneByExample(example);
	}

	public List<Chengwei> getAllReChargeChengwei() {
		Example example = new Example(Chengwei.class);
		example.orderBy("id").asc();
		example.createCriteria().andGreaterThan("money", 0);
		return cm.selectByExample(example);
	}

	/**
	 * 获取充值称谓奖励
	 * @param money
	 * @return
	 */
	public List<Chengwei> getChengweiMoney(int money) {
		Example example = new Example(Chengwei.class);
		example.createCriteria().andLessThanOrEqualTo("money", money);
		return cm.selectByExample(example);
	}

	/**一叶知秋  --只能获得累计充值的称号。
	 * 获取充值称谓奖励
	 * @param money
	 * @return
	 */
	public List<Chengwei> getChengweiMoney(int money,String chtypee) {
		Example example = new Example(Chengwei.class);
		example.createCriteria().andLessThanOrEqualTo("money", money).andEqualTo("chtype", chtypee);
		return cm.selectByExample(example);
	}

	public List<Chengwei> getChengweiMoneyDanBi(int money) {
		Example example = new Example(Chengwei.class);
		example.createCriteria().andEqualTo("money", money);
		return cm.selectByExample(example);
	}
	
	@CacheEvict(cacheNames = "Chengwei", allEntries = true)
	public int addChengwei(Chengwei chengwei) {
		chengwei.setAddTime(new Date());
		return cm.insertSelective(chengwei);
	}
	
	@CacheEvict(cacheNames = "Chengwei", allEntries = true)
	public int delChengweiById(int id) {
		return cm.deleteByPrimaryKey(id);
	}
	
	@CacheEvict(cacheNames = "Chengwei", allEntries = true)
	public void refreshCache() {}
}