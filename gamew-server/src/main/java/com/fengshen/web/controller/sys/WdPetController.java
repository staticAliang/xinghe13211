package com.fengshen.web.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.FightObjectInfo;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.service.base.BasePetService;
import com.fengshen.db.service.base.FightObjectInfoService;
import com.fengshen.server.game.GameData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/sys/wd/pet")
@RestController
public class WdPetController extends BaseController {

	@Autowired
	private BasePetService ps;
	
	@PostMapping("/getPets")
	public ResponseView getPets(Page<Pet> page, Pet pet) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		List<Pet> selectAll = ps.selectAll(pet);
		PageInfo<Pet> pageInfo = new PageInfo<Pet>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	@PostMapping("/updatePet")
	public ResponseView updateNpcStatue(Pet c) {
		if(c.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		ps.updateById(c);
		return ResponseView.ok();
	}
	
	@PostMapping("/getTpets")
	public ResponseView getTpets(Page<FightObjectInfo> page, String name) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		
		FightObjectInfoService b = GameData.that.baseFightObjectService;
		List<FightObjectInfo> selectAll = b.selectLinkAll(name);
		PageInfo<FightObjectInfo> pageInfo = new PageInfo<FightObjectInfo>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	@PostMapping("/updateTpet")
	public ResponseView updateTpet(FightObjectInfo c) {
		if(c.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		FightObjectInfoService b = GameData.that.baseFightObjectService;
		b.updateById(c);
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCachePet")
	public ResponseView refreshCachePet() {
		ps.refreshCache();
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCacheTpet")
	public ResponseView refreshCacheTpet() {
		GameData.that.baseFightObjectService.refreshCache();
		return ResponseView.ok();
	}
}
