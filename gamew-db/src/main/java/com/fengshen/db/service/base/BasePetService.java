package com.fengshen.db.service.base;

import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;

import java.time.*;
import org.springframework.cache.annotation.*;
import java.util.*;

import org.springframework.util.*;

import com.fengshen.db.dao.*;
import com.fengshen.db.domain.*;
import com.fengshen.db.domain.example.*;
import com.github.pagehelper.*;

@Service
public class BasePetService {
	@Autowired
	protected PetMapper mapper;

	@Cacheable(cacheNames = { "Pet" }, keyGenerator = "cacheAutoKey")
	public Pet findById(final int id) {
		return this.mapper.selectByPrimaryKeyWithLogicalDelete(id, false);
	}

	@CacheEvict(cacheNames = "Pet", allEntries = true)
	public Pet findByIdContainsDelete(final int id) {
		return this.mapper.selectByPrimaryKey(id);
	}

	@CacheEvict(cacheNames = "Pet", allEntries = true)
	public void add(final Pet pet) {
		pet.setAddTime(LocalDateTime.now());
		pet.setUpdateTime(LocalDateTime.now());
		this.mapper.insertSelective(pet);
	}

	@CacheEvict(cacheNames = "Pet", allEntries = true)
	public int updateById(final Pet pet) {
		pet.setUpdateTime(LocalDateTime.now());
		return this.mapper.updateByPrimaryKeySelective(pet);
	}

	@CacheEvict(cacheNames = "Pet", allEntries = true)
	public void deleteById(final int id) {
		this.mapper.logicalDeleteByPrimaryKey(id);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public List<Pet> findByPolar(final String polars) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPolarEqualTo(polars);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public List<Pet> findByZoon(final String zoons) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andZoonEqualTo(zoons);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public List<Pet> findByIcon(final Integer icons) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIconEqualTo(icons);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public List<Pet> findByName(final String names) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(names);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public Pet findOneByLevelReq(final Integer levelReq) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andLevelReqEqualTo(levelReq);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public Pet findOneByPolar(final String polar) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andPolarEqualTo(polar);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public Pet findOneByZoon(final String zoon) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andZoonEqualTo(zoon);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public Pet findOneByIcon(final Integer icon) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andIconEqualTo(icon);
		return this.mapper.selectOneByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public Pet findOneByName(final String name) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false).andNameEqualTo(name);
		return this.mapper.selectOneByExample(example);
	}

	public List<Pet> findAll(final int page, final int size, final String sort, final String order) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		if (!StringUtils.isEmpty((Object) sort) && !StringUtils.isEmpty((Object) order)) {
			example.setOrderByClause(String.valueOf(sort) + " " + order);
		}
		PageHelper.startPage(page, size);
		return this.mapper.selectByExample(example);
	}

	@Cacheable(cacheNames = "Pet", keyGenerator = "cacheAutoKey")
	public List<Pet> findAll() {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		criteria.andDeletedEqualTo(false);
		return this.mapper.selectByExample(example);
	}

	public List<Pet> selectAll(Pet pet) {
		final PetExample example = new PetExample();
		final PetExample.Criteria criteria = example.createCriteria();
		if (pet.getName() != null) {
			criteria.andNameEqualTo(pet.getName());
		}
		return this.mapper.selectByExample(example);
	}
	
	//刷新缓存
  	@CacheEvict(cacheNames = "Pet", allEntries = true)
  	public void refreshCache() {}
}
