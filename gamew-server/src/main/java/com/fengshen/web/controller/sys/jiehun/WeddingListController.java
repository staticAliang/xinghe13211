package com.fengshen.web.controller.sys.jiehun;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.WeddingList;
import com.fengshen.db.service.chara.WeddingListService;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.MarryUtil;
import com.fengshen.web.controller.BaseController;

@RestController
@RequestMapping("/sys/wd/weedingList")
public class WeddingListController extends BaseController {

	@Autowired
	private WeddingListService wls;
	
	
	/**
	 * a获取所有礼单
	 * @return
	 */
	@PostMapping("/getWeddingLists")
	public ResponseView getWeddingLists() {
		return ResponseView.ok(wls.selectAll());
	}
	
	
	/**
	 * a修改礼单信息
	 * @param wl 礼单
	 * @return
	 */
	@PostMapping("/updateWeddingList")
	public ResponseView updateWeddingList(WeddingList wl) {
		if(wl.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		//只允许修改价格
		wls.updateByPrimaryKeySelective(wl);
		//获取所有在线人员重新加载信息
		for(GameObjectChar gameObjectChar:GameObjectCharMng.getAll()) {
			MarryUtil.initWeddingListChoseDlg(gameObjectChar);
		}
		return ResponseView.ok();
	}
	
}