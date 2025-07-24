package com.fengshen.web.controller.sys.chara;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.web.controller.BaseController;
import com.mysql.jdbc.StringUtils;

/**
 * 称谓系统
 * 
 *
 */
@RestController
@RequestMapping("/sys/wd/chengwei")
public class ChengweiController extends BaseController {

	@Autowired
	private ChengweiService cs;
	
	
	@PostMapping("/getChengweis")
	public ResponseView getChengweis() {
		return ResponseView.ok(cs.selectAll());
	}
	
	@PostMapping("/addChengwei")
	public ResponseView addChengwei(Chengwei chengwei) {
		cs.addChengwei(chengwei);
		return ResponseView.ok();
	}
	
	@PostMapping("/delChengwei")
	public ResponseView delChengwei(int id) {
		cs.delChengweiById(id);
		return ResponseView.ok();
	}
	
	/**
	 * 发送称谓
	 * @param chengwei 称谓对象
	 * @param charaName 角色名
	 * @return
	 */
	@PostMapping("/sendChengwei")
	public ResponseView sendChengwei(Chengwei chengwei, String charaName) {
		if(StringUtils.isNullOrEmpty(charaName)) {
			ResponseView.fail("请输入角色名");
		}
		Chara chara = getChara(charaName);
		if(chara == null) {
			ResponseView.fail(charaName+"不在线");
		}
		if(chara.chenghao.get(chengwei.getName()) != null) {
			ResponseView.fail(charaName+"已有"+chengwei.getName()+"这个称谓了！");
		}
		//开始发送
		GameUtil.chenghaoxiaoxi(chara, chengwei.getName(), chengwei.getName());
		GameCommonUtil.sendTips("GM给你发了一个#R"+chengwei.getName()+"#n称谓。", chara.id);
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