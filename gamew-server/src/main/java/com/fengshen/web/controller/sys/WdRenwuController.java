package com.fengshen.web.controller.sys;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Renwu;
import com.fengshen.db.service.base.BaseRenwuService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/sys/wd/renwu")
@RestController
public class WdRenwuController extends BaseController {

	@Autowired
	private BaseRenwuService renwuService;
	
	/**
	 * 获取npc
	 * @return
	 */
	@PostMapping("/getNpcs")
	public ResponseView getNpcs(Page<Renwu> page) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("add_time desc");
		List<Renwu> renwus = renwuService.selectAll();
		PageInfo<Renwu> pageInfo = new PageInfo<>(renwus);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		renwuService.refreshCache();
		return ResponseView.ok();
	}
}
