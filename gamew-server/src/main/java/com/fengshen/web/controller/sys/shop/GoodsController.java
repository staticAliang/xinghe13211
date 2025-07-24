package com.fengshen.web.controller.sys.shop;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.ChargePoint;
import com.fengshen.db.domain.StoreGoods;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.service.base.BaseChargePointService;
import com.fengshen.db.service.base.BaseStoreGoodsService;
import com.fengshen.db.service.base.BaseStoreInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 商品管理
 *
 */
@RequestMapping("/sys/wd/goods")
@RestController
public class GoodsController extends BaseController {

	@Autowired
	private BaseStoreGoodsService storeGoodsService;

	@Autowired
	private BaseStoreInfoService storeInfoService;

	@Autowired
	private BaseChargePointService chargePoint;

	// 获取元宝商城
	@PostMapping("/getStoreGoods")
	public ResponseView getStoreGoods(Page<Object> page, StoreGoods goods) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		List<StoreGoods> selectAll = storeGoodsService.selectAll(goods);
		PageInfo<StoreGoods> pageInfo = new PageInfo<StoreGoods>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));

	}

	/**
	 * 更新元宝商城数据
	 * 
	 * @param goods
	 * @return
	 */
	@PostMapping("/updateStoreGoods")
	public ResponseView updateStoreGoods(StoreGoods goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		storeGoodsService.updateById(goods);
		return ResponseView.ok();
	}
	@PostMapping("/addStoreGoods")
	public ResponseView addStoreGoods(StoreGoods goods) {
		goods.setShowPos(0);
		goods.setSaleQuota(65535);
		goods.setDiscount(11);
		goods.setQuotaLimit(65535);
		goods.setIsGift(0);
		goods.setFollowPetType(0);
		storeGoodsService.add(goods);
		return ResponseView.ok();
	}

	// 获取商品库存数据
	@PostMapping("/getStoreInfoGoods")
	public ResponseView getStoreInfoGoods(Page<Object> page, StoreInfo goods) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		List<StoreInfo> selectAll = storeInfoService.selectAll(goods);
		PageInfo<StoreInfo> pageInfo = new PageInfo<StoreInfo>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));

	}

	/**
	 * 更新商品库存数据
	 * 
	 * @param goods
	 * @return
	 */
	@PostMapping("/updateStoreInfo")
	public ResponseView updateStoreInfo(StoreInfo goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		storeInfoService.updateById(goods);
		return ResponseView.ok();
	}
	
	@PostMapping("/addStoreInfo")
	public ResponseView addStoreInfo(StoreInfo goods) {
		storeInfoService.add(goods);
		return ResponseView.ok();
	}

	// 获取积分商城
	@PostMapping("/getChargePoints")
	public ResponseView getChargePoints(Page<Object> page) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("no desc");
		List<ChargePoint> selectAll = chargePoint.findAll();
		PageInfo<ChargePoint> pageInfo = new PageInfo<ChargePoint>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));

	}

	/**
	 * 更新积分商城
	 * 
	 * @param goods
	 * @return
	 */
	@PostMapping("/updateChargePoint")
	public ResponseView updateChargePoint(ChargePoint goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		chargePoint.updateById(goods);
		return ResponseView.ok();
	}
	//删除
	@PostMapping("/delChargePoint")
	public ResponseView delChargePoint(ChargePoint goods) {
		chargePoint.deleteById(goods);
		return ResponseView.ok();
	}
	
	@PostMapping("/addChargePoint")
	public ResponseView addChargePoint(ChargePoint goods) {
		BaseChargePointService bean = SpringBeanUtils.getBean(BaseChargePointService.class);
		if(bean.findByNo(goods.getNo()) != null) {
			ResponseView.fail("该序号已存在请重新填写");
		}
		chargePoint.add(goods);
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCacheStoreGoods")
	public ResponseView refreshCacheStoreGoods() {
		storeGoodsService.refreshCache();
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCacheStoreInfo")
	public ResponseView refreshCacheStoreInfo() {
		storeInfoService.refreshCache();
		return ResponseView.ok();
	}
}