package com.fengshen.web.controller.sys.chara;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.ChargeConfig;
import com.fengshen.db.service.chara.ChargeConfigService;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;

/**
 * 充值奖励配置
 * 
 *
 */
@RequestMapping("/wd/charge")
@RestController
public class ChargeController extends BaseController {

	@Autowired
	private ChargeConfigService chargeConfigService;
	
	
	/**
	 * 获取所有配置
	 * @return
	 */
	@PostMapping("/getChargeConfigs")
	public ResponseView getChargeConfigs() {
		return ResponseView.ok(chargeConfigService.selectAll());
	}
	
	/**
	 * 添加配置
	 * @param chargeConfig
	 * @return
	 */
	@PostMapping("/addChargeConfig")
	public ResponseView addChargeConfig(ChargeConfig chargeConfig) {
		chargeConfig.setCreateTime(new Date());
		chargeConfigService.insertSelective(chargeConfig);
		return ResponseView.ok();
	}
	
	/**
	 * 删除配置
	 * @param id 
	 * @return
	 */
	@PostMapping("/delChargeConfig")
	public ResponseView delChargeConfig(int id) {
		chargeConfigService.deleteByPrimaryKey(id);
		return ResponseView.ok();
	}
	
	/**
	 * 更新配置
	 * @param chargeConfig
	 * @return
	 */
	@PostMapping("/updateChargeConfig")
	public ResponseView updateChargeConfig(ChargeConfig chargeConfig) {
		chargeConfigService.updateByPrimaryKeySelective(chargeConfig);
		return ResponseView.ok();
	}
	
	/**
	 * 停服维护
	 * @return
	 */
	@PostMapping("/setChargeStatus")
	public ResponseView setChargeStatus(Integer status) {
		//设置状态
		GameConfig.config.getBaseConfig().setIsChargeSpeak(status);
		flushConfig();
		return ResponseView.ok();
		
	}
}