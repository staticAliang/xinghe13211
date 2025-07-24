package com.fengshen.web.controller.sys.chara;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.FuDaiChengwei;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.db.service.chara.FuDaiChengweiService;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.web.controller.BaseController;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 称谓系统
 * 
 *
 */
@RestController
@RequestMapping("/sys/wd/fudaichengwei")
public class FuDaiChengweiController extends BaseController {

	@Autowired
	private FuDaiChengweiService cs;
	
	
	@PostMapping("/getChengweis")
	public ResponseView getChengweis() {
		return ResponseView.ok(cs.selectAll());
	}
	
	@PostMapping("/addChengwei")
	public ResponseView addChengwei(FuDaiChengwei chengwei) {
		cs.addChengwei(chengwei);
		return ResponseView.ok();
	}
	
	@PostMapping("/delChengwei")
	public ResponseView delChengwei(int id) {
		cs.delChengweiById(id);
		return ResponseView.ok();
	}
	@PostMapping("/updateById")
	public ResponseView updateById(FuDaiChengwei f) {
		cs.updateChengweiById(f);
		return ResponseView.ok();
	}
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCache() {
		cs.refreshCache();
		return ResponseView.ok();
	}
}