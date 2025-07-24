package com.fengshen.web.controller.sys;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.server.domain.config.ForcePkConfig;
import com.fengshen.server.util.GameConfig;
import com.google.common.io.Files;

@RestController
@RequestMapping("/wdForcePk")
public class WdForcePkController extends BaseController {

	
	/**
	 * 获取配置信息
	 * @return
	 */
	@PostMapping("/getForce")
	public ResponseView getForceConfig() {
		
		return ResponseView.ok(GameConfig.forcePkConfig);
	}
	
	/**
	 * 修改配置信息
	 * @param config
	 * @return
	 */
	@PostMapping("/updateForce")
	public ResponseView updateForce(ForcePkConfig config) {
		GameConfig.forcePkConfig = config;
		//刷新配置
		String json = JSONObject.toJSONString(GameConfig.forcePkConfig);
		File resFile = Utils.getResFile("force_pk_config.json");
		try {
			Files.write(json.getBytes("utf-8"), resFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseView.ok();
	}
}