package com.fengshen.web.controller.sys.npc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.service.base.BaseNpcService;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.system.M65529_npc;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RequestMapping("/sys/wd/npc")
@RestController
public class WdNpcController extends BaseController {

	@Autowired
	private BaseNpcService npcService;
	
	/**
	 * 获取npc
	 * @return
	 */
	@PostMapping("/getNpcs")
	public ResponseView getNpcs(Page<Npc> page, String name) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("add_time desc");
		List<Npc> npcs = npcService.selectAll(name);
		PageInfo<Npc> pageInfo = new PageInfo<>(npcs);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 修改npc信息
	 * @param npc
	 * @return
	 */
	@PostMapping("/updateNpc")
	public ResponseView updateNpc(Npc npc) {
		if(npc.getId() == null) {
			ResponseView.fail("id 不能为空");
		}
		if(npc.getName() != null && npc.getName().equals("彻底删除")) {
			npcService.deleteById(npc.getId());
		}else {
			npcService.updateById(npc);
		}
		if(npc.getDeleted() != null && npc.getDeleted()) {
			//删除
			GameLine.getGameMap(1, npc.getMapId()).send(new MSG_DISAPPEAR(), npc.getId());
		}else {
			for (GameObjectChar gameSession : GameObjectCharMng.getAll()) {
				// 让当前地图的人都能实时看到实时刷新的npc
				if (gameSession.chara.mapid == npc.getMapId()) {
					gameSession.sendOne(new M65529_npc(), npc);
				}
			}
		}
		return ResponseView.ok();
	}
	
	/**
	 * 添加npc
	 * @param npc
	 * @return
	 */
	@PostMapping("/addNpc")
	public ResponseView addNpc(Npc npc) {
		npcService.add(npc);
		npc.setId(npc.getId());
		for (GameObjectChar gameSession : GameObjectCharMng.getGameObjectCharList()) {
			// 让当前地图的人都能实时看到刷新的掌门
			if (gameSession.gameMap.id == npc.getMapId())
				GameObjectChar.send(new M65529_npc(), npc, gameSession.chara.id);
		}
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		npcService.refreshCache();
		return ResponseView.ok();
	}
	
}
