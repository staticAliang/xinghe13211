package com.fengshen.web.controller.sys.equip;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.game.GameData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/sys/zhuangbeiInfo")
public class ZhuangbeiInfoController extends BaseController {

	// 获取元宝商城
	@PostMapping("/getZhuangbeiInfos")
	public ResponseView getZhuangbeiInfos(Page<Object> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		List<ZhuangbeiInfo> selectAll = GameData.that.baseZhuangbeiInfoService.selectAll();
		PageInfo<ZhuangbeiInfo> pageInfo = new PageInfo<ZhuangbeiInfo>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		GameData.that.baseZhuangbeiInfoService.refreshCache();
		return ResponseView.ok();
	}
}
