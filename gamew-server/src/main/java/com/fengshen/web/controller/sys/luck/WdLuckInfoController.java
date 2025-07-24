package com.fengshen.web.controller.sys.luck;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Choujiang;
import com.fengshen.db.service.base.BaseChoujiangService;
import com.fengshen.server.domain.config.ChoujiangConfig;
import com.fengshen.server.util.GameConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/sys/wd/wdLuckInfo")
public class WdLuckInfoController extends BaseController {

	@Autowired
	private BaseChoujiangService choujiang;
	
	
	/**
	 * 获取所有抽奖
	 * @return
	 */
	@PostMapping("/getChoujiangs")
	public ResponseView getChoujiangs(Page<Object> page, Choujiang info) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("level desc");
		List<Choujiang> choujiangs = choujiang.selectAll(info);
		PageInfo<Choujiang> pageInfo = new PageInfo<>(choujiangs);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 获取抽奖配置信息
	 * @return
	 */
	@PostMapping("/getChoujiangConfig")
	public ResponseView getChoujiangConfig() {
		return ResponseView.ok(GameConfig.choujiangConfig);
	}
	
	/**
	 * 修改抽奖配置
	 * @param config
	 * @return
	 */
	@PostMapping("/updateChoujiangConfig")
	public ResponseView updateChoujiangConfig(ChoujiangConfig config) {
		GameConfig.choujiangConfig = config;
		flushConfig("choujiang_config.json", config);
		return ResponseView.ok();
	}
	
	/**
	 * 添加奖项
	 * @param c
	 * @return
	 */
	@PostMapping("/addChoujiang")
	public ResponseView addChoujiang(Choujiang c) {
		choujiang.add(c);
		return ResponseView.ok();
	}
	
	/**
	 * 删除抽奖
	 * @param id
	 * @return
	 */
	@PostMapping("/delChoujiang")
	public ResponseView delChoujiang(int id) {
		choujiang.deleteById(id);
		return ResponseView.ok();
	}
	
	/**
	 * 修改抽奖
	 * @param id
	 * @return
	 */
	@PostMapping("/updateChoujiang")
	public ResponseView updateChoujiang(Choujiang c) {
		choujiang.updateById(c);
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCache() {
		choujiang.refreshCache();
		return ResponseView.ok();
	}
}
