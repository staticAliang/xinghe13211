package com.fengshen.web.controller.sys;

import java.util.HashMap;
import java.util.Map;

import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.TianshuConfig;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.mysql.jdbc.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.server.domain.config.EquipGaiZaoConfig;
import com.fengshen.server.util.GameConfig;

@RequestMapping("/wd/config")
@RestController
public class WdConfigController extends BaseController {

	/**
	 * 获取改造配置
	 * @return
	 */
	@PostMapping("/getEquipGaizaoConfig")
	public ResponseView getEquipGaizaoConfig() {
		return ResponseView.ok(GameConfig.equipGaiZaoConfig);
	}
	/**
	 * 获取天书配置
	 * @return
	 */
	@PostMapping("/getTianshuConfig")
	public ResponseView getTianshuConfig() {
		return ResponseView.ok(GameConfig.tianshuConfig);
	}
	/**
	 * 发送满属性天书
	 * @return
	 */
	@PostMapping("/sendTianshu")
	public ResponseView sendTianshu(String charaName, String name) {
		if(StringUtils.isNullOrEmpty(charaName)) {
			ResponseView.fail("请输入角色名");
		}
		GameObjectChar  gameObjectChar = getGameObjectChar(charaName);
		if(gameObjectChar == null) {
			ResponseView.fail(charaName+"不在线");
		}
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
		GameUtil.huodetianshu(gameObjectChar,info,1,"man");
		return ResponseView.ok();
	}
	/**
	 * 修改天书配置
	 * @param config
	 * @return
	 */
	@PostMapping("/updateTianshuConfig")
	public ResponseView updateTianshuConfig(TianshuConfig config, @RequestParam Map<String,Integer> map) {
		String jsonString = JSONObject.toJSONString(map);
		GameConfig.tianshuConfig = JSONObject.parseObject(jsonString, TianshuConfig.class);
		flushConfig("tianshu.json", GameConfig.tianshuConfig);
		return ResponseView.ok();
	}
	/**
	 * 修改改造配置
	 * @param config
	 * @return
	 */
	@PostMapping("/updateEquipGaizaoConfig")
	public ResponseView updateEquipGaizaoConfig(EquipGaiZaoConfig config, @RequestParam Map<String,Integer> map) {
		String jsonString = JSONObject.toJSONString(map);
		GameConfig.equipGaiZaoConfig = JSONObject.parseObject(jsonString, EquipGaiZaoConfig.class);
		flushConfig("equip_gaizao.json", GameConfig.equipGaiZaoConfig);
		GameConfig.equipGaiZao = new HashMap<>();
		GameConfig.equipGaiZao.put("n1", GameConfig.equipGaiZaoConfig.n1Type);
		GameConfig.equipGaiZao.put("n2", GameConfig.equipGaiZaoConfig.n2Type);
		GameConfig.equipGaiZao.put("n3", GameConfig.equipGaiZaoConfig.n3Type);
		GameConfig.equipGaiZao.put("n4", GameConfig.equipGaiZaoConfig.n4Type);
		GameConfig.equipGaiZao.put("n5", GameConfig.equipGaiZaoConfig.n5Type);
		GameConfig.equipGaiZao.put("n6", GameConfig.equipGaiZaoConfig.n6Type);
		GameConfig.equipGaiZao.put("n7", GameConfig.equipGaiZaoConfig.n7Type);
		GameConfig.equipGaiZao.put("n8", GameConfig.equipGaiZaoConfig.n8Type);
		GameConfig.equipGaiZao.put("n9", GameConfig.equipGaiZaoConfig.n9Type);
		GameConfig.equipGaiZao.put("n10", GameConfig.equipGaiZaoConfig.n10Type);
		GameConfig.equipGaiZao.put("n11", GameConfig.equipGaiZaoConfig.n11Type);
		return ResponseView.ok();
	}

}