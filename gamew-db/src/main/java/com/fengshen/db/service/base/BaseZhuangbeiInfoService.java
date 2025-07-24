package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.ZhuangbeiInfoMapper;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.domain.example.ZhuangbeiInfoExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseZhuangbeiInfoService {
	@Autowired
	protected ZhuangbeiInfoMapper mapper;

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", condition = "#result.deleted == 0")
	public ZhuangbeiInfo findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = { "ZhuangbeiInfo" })
	public void add(final ZhuangbeiInfo zhuangbeiInfo) {
		zhuangbeiInfo.setAddTime(LocalDateTime.now());
		zhuangbeiInfo.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(zhuangbeiInfo);
	}

	@CacheEvict(cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo updateById(final ZhuangbeiInfo zhuangbeiInfo) {
		zhuangbeiInfo.setUpdateTime(LocalDateTime.now());
		this.mapper.updateByPrimaryKeySelective(zhuangbeiInfo);

		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIdEqualTo(zhuangbeiInfo.getId());
		return this.mapper.selectOneByExample(example);
	}

	@CacheEvict(cacheNames = { "ZhuangbeiInfo" })
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public List<ZhuangbeiInfo> findByAttrib(final Integer attribs) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attribs);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public List<ZhuangbeiInfo> findByAmount(final Integer amounts) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAmountEqualTo(amounts);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public List<ZhuangbeiInfo> findByType(final Integer types) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(types);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public List<ZhuangbeiInfo> findByStr(final String strs) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andStrEqualTo(strs);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo findOneByAttrib(final Integer attrib) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAttribEqualTo(attrib);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo findOneByAmount(final Integer amount) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAmountEqualTo(amount);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo findOneByType(final Integer type) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andTypeEqualTo(type);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo findOneByStr(final String str) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andStrEqualTo(str);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo findOne(Integer amout, Integer attrib) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andAmountEqualTo(amout).andAttribEqualTo(attrib);
		return this.mapper.selectOneByExample(example);
	}

	public List<ZhuangbeiInfo> findAll(final int page, final int size, final String sort, final String order) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public List<ZhuangbeiInfo> findAll() {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(keyGenerator = "cacheAutoKey", cacheNames = "ZhuangbeiInfo")
	public ZhuangbeiInfo getOneZbInfo(int sex, int level, int amount) {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		final ZhuangbeiInfoExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andMasterEqualTo(sex).andAttribEqualTo(level).andAmountEqualTo(amount);
		return this.mapper.selectOneByExample(example);
	}

	public List<ZhuangbeiInfo> selectAll() {
		final ZhuangbeiInfoExample example = new ZhuangbeiInfoExample();
		return this.mapper.selectByExample(example);
	}

	// 刷新缓存
	@CacheEvict(cacheNames = "ZhuangbeiInfo", allEntries = true)
	public void refreshCache() {
	}
}
