package com.fengshen.web.controller.sys.npc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Chara_Statue;
import com.fengshen.db.service.base.BaseCharaStatueService;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/sys/npcStatue")
@RestController
public class WdNpcStatueController extends BaseController {

	@Autowired
	private BaseCharaStatueService cs;
	
	@PostMapping("/getAll")
	public ResponseView getAll(Page<CharaStatue> page, Chara_Statue c) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		List<Chara_Statue> selectAll = cs.selectAll(c);
		PageInfo<Chara_Statue> pageInfo = new PageInfo<Chara_Statue>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	@PostMapping("/updateNpcStatue")
	public ResponseView updateNpcStatue(Chara_Statue c) {
		if(c.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		if(c.getDeleted() != null && c.getDeleted()) {
			//删除
			cs.deletedById(c);
		}else {
			if(c.getNpcName() != null ) {
				CharaStatueService.cacheMap.put(c.getNpcName(), JSONObject.parseObject(c.getData(), CharaStatue.class));
			}
			cs.updateById(c);
		}
		return ResponseView.ok();
	}
	
	@PostMapping("/addNpcStatue")
	public ResponseView addNpcStatue(Chara_Statue c) {
		if(c.getNpcName() == null) {
			ResponseView.fail("名字不能为空");
		}
		cs.insert(c);
		CharaStatueService.init("1");
		return ResponseView.ok();
	}
}
