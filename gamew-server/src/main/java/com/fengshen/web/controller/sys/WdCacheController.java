package com.fengshen.web.controller.sys;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.web.controller.BaseController;

/**
 * 缓存管理器
 * 
 *
 */
@RestController
@RequestMapping("/sys/wd/wdCache")
public class WdCacheController extends BaseController{

	@PostMapping("/refreshCache")
	public ResponseView refreshCache(String name) {
		if("mapCache".equals(name)) {
			GameData.that.baseMapService.refreshCache();
		}else if("charaExpCache".equals(name)) {
			GameData.that.baseExperienceService.refreshCache();
		}else if("daofaExpCache".equals(name)) {
			GameData.that.baseExperienceTreasureService.refreshCache();
		}else if("npc".equals(name)) {
//			GameData.that.baseNpcService.refreshCache();
		}else if("npc_dialogue".equals(name)) {
			GameData.that.baseNpcDialogueService.refreshCache();
		}else if("npc_dialogue_frame".equals(name)) {
			GameData.that.baseNpcDialogueFrameService.refreshCache();
		}else if("npc_point".equals(name)) {
			GameData.that.baseNpcPointService.refreshCache();
		}else if("pet".equals(name)) {
			GameData.that.basePetService.refreshCache();
		}else if("t_fight_object".equals(name)) {
			GameData.that.baseFightObjectService.refreshCache();
		}else if("custom_pet_skill".equals(name)) {
			GameData.that.customPetSkillService.refreshCache();
		}else if("choujiang".equals(name)) {
			GameData.that.baseChoujiangService.refreshCache();
		}else if("chengwei".equals(name)) {
			GameData.that.chengweiService.refreshCache();
		}else if("shuxingduiying".equals(name)) {
			GameData.that.baseShuxingduiyingService.refreshCache();
		}else if("victory_die_reward".equals(name)) {
			GameData.that.victoryDieRewardService.refreshCache();
		}else if("zhuangbei_info".equals(name)) {
			GameData.that.baseZhuangbeiInfoService.refreshCache();
		}else if("store_goods".equals(name)) {
			GameData.that.baseStoreGoodsService.refreshCache();
		}else if("store_info".equals(name)) {
			GameData.that.baseStoreInfoService.refreshCache();
		}else if("notice".equals(name)) {
			GameData.that.baseNoticeService.refreshCache();
		}else if("renwu".equals(name)) {
			GameData.that.baseRenwuService.refreshCache();
		}else if("renwu_monster".equals(name)) {
			GameData.that.baseRenwuMonsterService.refreshCache();
		}else if("config_info".equals(name)) {
			GameData.that.configInfoService.refreshCache();
		}else if("fudaichoujiang".equals(name)){
			GameData.that.luckDrawItemService.refreshCache();
		}

		return ResponseView.ok();
	}
	
	/**
	 *  清除所有缓存
	 * @return
	 */
	@PostMapping("/refreshCacheAll")
	public ResponseView refreshCacheAll() {
		GameCommonUtil.refreshCacheAll();
		return ResponseView.ok();
	}
}