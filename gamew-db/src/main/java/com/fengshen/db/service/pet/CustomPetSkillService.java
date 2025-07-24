package com.fengshen.db.service.pet;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fengshen.db.base.BaseCustomMapper;
import com.fengshen.db.dao.CustomPetSkillMapper;
import com.fengshen.db.domain.CustomPetSkill;
import com.fengshen.db.service.base.BaseServiceSupport;

import tk.mybatis.mapper.entity.Example;

@Service
public class CustomPetSkillService implements BaseServiceSupport<CustomPetSkill> {

	@Autowired
	private CustomPetSkillMapper cpsm;

	@Override
	public BaseCustomMapper<CustomPetSkill> getBaseMapper() {
		return cpsm;
	}

	@CacheEvict(cacheNames = "CustomPetSkill", allEntries = true)
	public int addCustomPetSkill(CustomPetSkill cps) {
		cps.setAddTime(new Date());
		return cpsm.insertSelective(cps);
	}

	/**
	 * 根据宠物名称获取自定义技能信息
	 * 
	 * @param petName
	 * @return
	 */
	@Cacheable(cacheNames = "CustomPetSkill", keyGenerator = "cacheAutoKey")
	public List<CustomPetSkill> getCustomPetSkillByPetName(String petName) {
		Example example = new Example(CustomPetSkill.class);
		example.createCriteria().andEqualTo("petName", petName);
		return cpsm.selectByExample(example);
	}

	/**
	 * 删除某个配置
	 * 
	 * @param id
	 * @return
	 */
	@CacheEvict(cacheNames = "CustomPetSkill", allEntries = true)
	public int deleteById(int id) {
		return cpsm.deleteByPrimaryKey(id);
	}

	/**
	 * 更新某个技能信息
	 * 
	 * @param cs
	 * @return
	 */
	@CacheEvict(cacheNames = "CustomPetSkill", allEntries = true)
	public int updateCustomPetSkillById(CustomPetSkill cs) {
		return cpsm.updateByPrimaryKeySelective(cs);
	}

	// 刷新缓存
	@CacheEvict(cacheNames = "CustomPetSkill", allEntries = true)
	public void refreshCache() {}
}