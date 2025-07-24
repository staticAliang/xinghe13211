package com.fengshen.web.controller.sys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.VictoryDieReward;
import com.fengshen.db.service.system.VictoryDieRewardService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 死亡惩罚奖励控制器
 * 
 *
 */
@RestController
@RequestMapping("/victoryDieReward")
public class VictoryDieRewardController extends BaseController {

	@Autowired
	private VictoryDieRewardService vs;
	
	/**
	 * 获取所有配置信息
	 * @return
	 */
	@PostMapping("/getVictoryDieReward")
	public ResponseView  getVictoryDieReward(Page<VictoryDieReward> page, String name) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		Example example = new Example(VictoryDieReward.class);
		
		if(name != null && !name.isEmpty()) {
			example.createCriteria().andLike("name", "%"+name+"%");
		}
		PageInfo<VictoryDieReward> pageInfo = new PageInfo<VictoryDieReward>(vs.selectByExample(example));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 修改配置信息
	 * @param config
	 * @return
	 */
	@RequestMapping("/updateVictoryDieReward")
	public ResponseView updateVictoryDieReward(VictoryDieReward config) {
		if(config.getId() == null) {
			ResponseView.fail("请输入id");
		}
		vs.updateByRecord(config);
		return ResponseView.ok();
	}
	
	/**
	 * 删除这个配置
	 * @param config
	 * @return
	 */
	@RequestMapping("/deleteVictoryDieReward")
	public ResponseView deleteVictoryDieReward(VictoryDieReward config) {
		if(config.getId() == null) {
			ResponseView.fail("请输入id");
		}
		vs.updateByRecord(config);
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		vs.refreshCache();
		return ResponseView.ok();
	}
}