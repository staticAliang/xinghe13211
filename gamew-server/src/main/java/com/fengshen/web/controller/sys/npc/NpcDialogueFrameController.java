package com.fengshen.web.controller.sys.npc;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.NpcDialogueFrame;
import com.fengshen.server.game.GameData;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/sys/npcDialogueFrame")
public class NpcDialogueFrameController extends BaseController {

	
	@PostMapping("/getNpcDialogueFrames")
	public ResponseView getZhuangbeiInfos(Page<Object> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		List<NpcDialogueFrame> selectAll = GameData.that.baseNpcDialogueFrameService.selectAll();
		PageInfo<NpcDialogueFrame> pageInfo = new PageInfo<NpcDialogueFrame>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		GameData.that.baseNpcDialogueFrameService.refreshCache();
		return ResponseView.ok();
	}
}
