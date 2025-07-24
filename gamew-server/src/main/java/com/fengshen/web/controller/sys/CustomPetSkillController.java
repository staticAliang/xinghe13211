package com.fengshen.web.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.CustomPetSkill;
import com.fengshen.db.service.pet.CustomPetSkillService;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 宠物自定义技能
 * 
 *
 */
@RestController
@RequestMapping("/sys/wd/customPetSkill")
public class CustomPetSkillController extends BaseController {

	
	@Autowired
	private CustomPetSkillService cs;
	
	
	@PostMapping("/addCustomPetSkill")
	public ResponseView addCustomPetSkill(CustomPetSkill cps) {
		Example example = new Example(CustomPetSkill.class);
		example.createCriteria().andEqualTo("petName", cps.getPetName()).andEqualTo("skillName", cps.getSkillName());
		if(cs.selectCountByExample(example)>0) {
			ResponseView.fail("不允许重复");
		}
		//校验技能名是否存在
		List<org.json.JSONObject> nomelSkills = PetAndHelpSkillUtils.getSkills(cps.getSkillLevel(), cps.getSkillName());
		if(nomelSkills == null || nomelSkills.isEmpty()) {
			ResponseView.fail("不存在技能" + cps.getSkillName());
		}
		cs.addCustomPetSkill(cps);
		return ResponseView.ok();
	}
	
	/**
	 * 根据宠物名称获取自定义技能信息
	 * @param petName
	 * @return
	 */
	@PostMapping("/getCustomPetSkillByPetName")
	public ResponseView getCustomPetSkillByPetName(Page<Object> page, String petName) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		Example example = new Example(CustomPetSkill.class);
		if(!StringUtils.isNullOrEmpty(petName)) {
			example.createCriteria().andLike("petName", "%"+petName+"%");
    	}
		List<CustomPetSkill> selectAll = cs.selectByExample(example);
		PageInfo<CustomPetSkill> pageInfo = new PageInfo<CustomPetSkill>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	
	/**
	 * 删除某个配置
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteById")
	public ResponseView deleteById(int id) {
		cs.deleteById(id);
		return ResponseView.ok();
	}
	
	/**
	 * 更新某个技能信息
	 * @param cs
	 * @return
	 */
	@PostMapping("/updateCustomPetSkillById")
	public ResponseView updateCustomPetSkillById(CustomPetSkill cpet) {
		cs.updateCustomPetSkillById(cpet);
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		cs.refreshCache();
		return ResponseView.ok();
	}
	
}