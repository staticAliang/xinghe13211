package com.fengshen.web.controller.sys.chara;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.server.domain.config.ShenHunConfig;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;
import com.google.common.io.Files;

/**
 * 神魂
 * 
 *
 */
@RestController
@RequestMapping("/shenhun")
public class ShenHunController extends BaseController {

	
	@PostMapping("/updateShenHun")
	public ResponseView updateShenHun(@RequestParam Map<String,Object> data) {
		
		ShenHunConfig sh = GameConfig.shenHunConfig;
		//属性
		Map<String, JSONObject> attri = sh.getAttri();
		//积分配置
		Map<String, JSONObject> data2 = sh.getData();
		int d = 1;
		int n = 1;
		for(Map.Entry<String, Object> m:data.entrySet()) {
			//d开头伤害配置,
			if(m.getKey().indexOf("d") != -1) {
				JSONObject jsonObject = attri.get(String.valueOf(d));
				jsonObject.put("name", data.get("s"+d));
				jsonObject.put("value", m.getValue());
				attri.put(String.valueOf(d), jsonObject);
				d++;
			}
			//n开头积分
			if(m.getKey().indexOf("n") != -1) {
				JSONObject jsonObject = data2.get(String.valueOf(n));
				jsonObject.put("jifen", m.getValue());
				data2.put(String.valueOf(n), jsonObject);
				n++;
			}
		}
		String json = JSONObject.toJSONString(sh);
		File resFile = Utils.getResFile("shenhun.json");
		try {
			Files.write(json.getBytes("utf-8"), resFile);
		} catch (IOException e) {
		}
		return ResponseView.ok();
	}
	
	
	@PostMapping("/getShenHunConfig")
	public ResponseView getShenHunConfig() {
		ShenHunConfig shenHunConfig = GameConfig.shenHunConfig;
		Map<String, JSONObject> attri = shenHunConfig.attri;
		Map<String, JSONObject> data = shenHunConfig.data;
		Map<String,Object> data2 = new HashMap<String, Object>();
		data2.put("value", data);
		data2.put("attri", attri);
		return ResponseView.ok(data2);
	}
	
	
}