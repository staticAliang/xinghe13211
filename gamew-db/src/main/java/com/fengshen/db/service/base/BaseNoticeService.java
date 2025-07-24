package com.fengshen.db.service.base;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fengshen.db.dao.NoticeMapper;
import com.fengshen.db.domain.Notice;
import com.fengshen.db.domain.example.NoticeExample;
import com.github.pagehelper.PageHelper;

@Service
public class BaseNoticeService {
	@Autowired
	protected NoticeMapper mapper;

	public Notice findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	public Notice findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(allEntries = true, cacheNames = "Notice")
	public void add(final Notice notice) {
		notice.setAddTime(LocalDateTime.now());
		notice.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(notice);
	}

	@CacheEvict(allEntries = true, cacheNames = "Notice")
	public int updateById(final Notice notice) {
		notice.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(notice);
	}

	@CacheEvict(allEntries = true, cacheNames = "Notice")
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	
	public List<Notice> findAll(final int page, final int size, final String sort, final String order) {
		final NoticeExample example = new NoticeExample();
		final NoticeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Notice", keyGenerator = "cacheAutoKey")
	public List<Notice> findAll() {
		final NoticeExample example = new NoticeExample();
		final NoticeExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}
	
	//刷新缓存
  	@CacheEvict(cacheNames = "Notice", allEntries = true)
  	public void refreshCache() {}
}
