package com.fengshen.web.controller.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.domain.StallRecord;
import com.fengshen.db.service.zhenbao.GoldStallNineGoodsService;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.constant.StallStatus;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.config.MarketConfig;
import com.fengshen.server.game.GameData;
import com.fengshen.server.util.GameConfig;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import tk.mybatis.mapper.entity.Example;

/**
 * 珍宝
 * 
 *
 */
@RestController
@RequestMapping("/sys/wd/stall_gold")
public class StallGoldGoodController extends BaseController {

	@Autowired
	private GoldStallNineGoodsService goldStallNineGoodsService;

	/**
	 *  获取珍宝货物列表
	 * @param page 分页
	 * @param good 货物
	 * @return
	 */
	@PostMapping("/getGoods")
	public ResponseView getGoods(Page<GoldStallNineGoods> page, GoldStallNineGoods good) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		PageInfo<GoldStallNineGoods> pageInfo = new PageInfo<>(goldStallNineGoodsService.select(good));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 设置珍宝信息
	 * @param marketConfig
	 * @return
	 */
	@PostMapping("/setMarketConfig")
	public ResponseView setMarketConfig(MarketConfig marketConfig) {
		if(marketConfig.getZhenbaoStatus() != null) {
			GameConfig.config.getMarketConfig().setZhenbaoStatus(marketConfig.getZhenbaoStatus());
		}
		if(marketConfig.getZhenbaoPublicTimes() != null) {
			GameConfig.config.getMarketConfig().setZhenbaoPublicTimes(marketConfig.getZhenbaoPublicTimes());
		}
		if(marketConfig.getZhenbaoDownGoodTimes() != null) {
			GameConfig.config.getMarketConfig().setZhenbaoDownGoodTimes(marketConfig.getZhenbaoDownGoodTimes());
		}
		flushConfig();
		return ResponseView.ok();
	}
	
	/**
	 * a审核
	 * @param id 珍宝货物id
	 * @param status 状态
	 * @return
	 */
	@PostMapping("/checkGoods")
	public ResponseView checkGoods(GoldStallNineGoods goods) {
		if(goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}else if(goods.getStatus() == null) {
			ResponseView.fail("状态不能为空");
		}
		if(goods.getStatus() == StallStatus.getValue("已审核")) {
			//审核通过
			goods.setStatus(StallStatus.getValue("出售中"));
			// 设置下架时间
			GameData.that.redisUtils.set(
					DefinedConst.GOLD_STALL_PREFIX + ";" + goods.getGoodsId() + ";" + 3, "",
					GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes() * 60);

			goods.setStartTime((int) (System.currentTimeMillis() / 1000L));
			goods.setEndTime((int) (System.currentTimeMillis() / 1000L)
					+ GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes() * 60);
		}
		//更新
		GameData.that.zhenbao.updateByPrimaryKeySelective(goods);
		//删除记录
		Example example = new Example(StallRecord.class);
		example.createCriteria().andEqualTo("goodsUuid", goods.getGoodsId());
		GameData.that.stallRecordService.deleteByExample(example);
		return ResponseView.ok("操作成功");
	}
	
	/**
	 * a一键修复宠物问题
	 * @return
	 */
	@PostMapping("/marktBtnInit")
	public ResponseView marktBtnInit() {
		//获取到当前所有的宠物信息
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("stallItemType", 2);
		List<GoldStallNineGoods> selectByExample = GameData.that.zhenbao.selectByExample(example);
		for(GoldStallNineGoods goods:selectByExample) {
			Petbeibao pet = JSONObject.parseObject(goods.getGoods(),Petbeibao.class);
			String originName = pet.petShuXing.get(0).suit_polar;
			GoldStallNineGoods update = new GoldStallNineGoods();
			update.setName(originName);
			update.setId(goods.getId());
			GameData.that.zhenbao.updateByPrimaryKeySelective(update);
		}
		return ResponseView.ok();
	}
}