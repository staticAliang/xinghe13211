package com.fengshen.web.controller.sys.shop;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.RareShopItem;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/rareShopItem")
public class RareShopItemController extends BaseController {

	// 获取积分商城
	@PostMapping("/getRareShopItems")
	public ResponseView getRareShopItems(Page<Object> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<RareShopItem> selectAll = GameData.that.rareShopItemService.selectAll();
		PageInfo<RareShopItem> pageInfo = new PageInfo<RareShopItem>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));

	}

	/**
	 * 更新商城
	 * 
	 * @param goods
	 * @return
	 */
	@PostMapping("/updateRareShopItem")
	public ResponseView updateRareShopItem(RareShopItem goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		GameData.that.rareShopItemService.updateByPrimaryKeySelective(goods);
		return ResponseView.ok();
	}

	// 删除
	@PostMapping("/delRareShopItem")
	public ResponseView delRareShopItem(RareShopItem goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		GameData.that.rareShopItemService.deleteByPrimaryKey(goods.getId());
		return ResponseView.ok();
	}

	@PostMapping("/addRareShopItem")
	public ResponseView addRareShopItem(RareShopItem goods) {
		goods.setBarcode(GameCommonUtil.UUID());
		goods.setAddTime(new Date());
		GameData.that.rareShopItemService.insertSelective(goods);
		return ResponseView.ok();
	}

}
