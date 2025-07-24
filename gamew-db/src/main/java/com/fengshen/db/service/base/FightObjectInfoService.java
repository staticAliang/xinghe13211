package com.fengshen.db.service.base;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.FightObjectInfoMapper;
import com.fengshen.db.domain.FightObjectInfo;
import com.github.pagehelper.PageHelper;

import tk.mybatis.mapper.entity.Example;

@Service
public class FightObjectInfoService implements BaseServiceSupport<FightObjectInfo> {

	@Autowired
	protected FightObjectInfoMapper mapper;

	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public List<FightObjectInfo> findByName(String name) {
		Example example = new Example(FightObjectInfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted",false).andEqualTo("name",name);
		return this.mapper.selectByExample(example);
	}
	
	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public List<FightObjectInfo> findByNameForType(String name, String type) {
		Example example = new Example(FightObjectInfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted",false).andEqualTo("name",name).andEqualTo("type", type);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public FightObjectInfo findOneByName(String name) {
		List<FightObjectInfo> list = findByName(name);
		if (list.isEmpty()) {
			return null;
		}
		FightObjectInfo FightObjectInfo = list.get(new Random().nextInt(list.size()));
		return FightObjectInfo;
	}

	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public List<FightObjectInfo> findByID(Integer id) {
		Example example = new Example(FightObjectInfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted",false).andEqualTo("id",id);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public FightObjectInfo findOneByID(Integer id) {
		List<FightObjectInfo> list = findByID(id);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	public List<FightObjectInfo> findAll(int page, int size, String sort, String order) {
		Example example = new Example(FightObjectInfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted",false);
		if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
			example.setOrderByClause(sort + " " + order);
		}

		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public List<FightObjectInfo> findAll() {
		Example example = new Example(FightObjectInfo.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("deleted",false);
		return this.mapper.selectByExample(example);
	}

	@CacheEvict(cacheNames = "FightObjectInfo", allEntries = true)
	public int updateById(FightObjectInfo t) {
		return mapper.updateByPrimaryKeySelective(t);
	}

	public List<FightObjectInfo> selectAll(String name) {
		Example example = new Example(FightObjectInfo.class);
		if (name != null) {
			Example.Criteria criteria = example.createCriteria();
			criteria.andEqualTo("name",name);
		}
		return this.mapper.selectByExample(example);
	}

	public List<FightObjectInfo> selectLinkAll(String name) {
		Example example = new Example(FightObjectInfo.class);
		if (name != null) {
			Example.Criteria criteria = example.createCriteria();
			criteria.andLike("name","%" + name + "%");
		}
		return this.mapper.selectByExample(example);
	}
	
	@Cacheable(cacheNames = "FightObjectInfo", keyGenerator = "cacheAutoKey")
	public List<FightObjectInfo> findInByName(List<String> name) {
		List<FightObjectInfo> fightObjectInfos = null;
		if(name != null && !name.isEmpty()) {
			Example example = new Example(FightObjectInfo.class);
			example.createCriteria().andIn("name", name).andEqualTo("deleted", false);
			fightObjectInfos = this.mapper.selectByExample(example);
		}
		return fightObjectInfos;
	}
	

	// 刷新缓存
	@CacheEvict(cacheNames = "FightObjectInfo", allEntries = true)
	public void refreshCache() {
	}

	@Override
	public BaseCustomMapper<FightObjectInfo> getBaseMapper() {
		return mapper;
	}
}
