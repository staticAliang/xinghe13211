package com.fengshen.web.controller.market;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.SaleClassifyGood;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.db.service.SaleGoodService;
import com.fengshen.db.service.base.BaseSaleClassifyGoodService;
import com.fengshen.server.domain.config.MarketConfig;
import com.fengshen.server.util.GameConfig;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 集市管理控制器
 * 
 *
 */
@RestController
@RequestMapping("/sys/wd/saleGood")
public class SaleGoodController extends BaseController {
	
	@Autowired
	private SaleGoodService saleGoodService;
	
	@Autowired
	private BaseSaleClassifyGoodService saleClassifyGoodService;

	/**
	 * 获取集市货物分类
	 * @param page 分页
	 * @param good 货物
	 * @return
	 */
	@PostMapping("/getSaleClassifyGoods")
	public ResponseView getSaleClassifyGoods(Page<SaleClassifyGood> page, SaleClassifyGood good) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		PageInfo<SaleClassifyGood> pageInfo = new PageInfo<>(saleClassifyGoodService.select(good));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 获取集市货物
	 * @param page 分页
	 * @param good 货物
	 * @return
	 */
	@PostMapping("/getSaleGoods")
	public ResponseView getSaleGoods(Page<SaleGood> page, SaleGood good) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		PageInfo<SaleGood> pageInfo = new PageInfo<>(saleGoodService.select(good));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 更新货物分类
	 * 
	 * @param goods
	 * @return
	 */
	@PostMapping("/updateSaleClassifyGood")
	public ResponseView updateSaleClassifyGood(SaleClassifyGood goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		saleClassifyGoodService.updateByPrimaryKeySelective(goods);
		return ResponseView.ok();
	}
	
	/**
	 * 更新货物列表
	 * @param goods
	 * @return
	 */
	@PostMapping("/updateSaleGood")
	public ResponseView updateSaleGood(SaleGood goods) {
		if (goods.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		saleGoodService.updateByPrimaryKeySelective(goods);
		return ResponseView.ok();
	}
	
	/**
	 * 添加货物
	 * @param good
	 * @return
	 */
	@PostMapping("/addSaleClassifyGood")
	public ResponseView addSaleClassifyGood(SaleClassifyGood good) {
		good.setAddTime(new Date());
		return ResponseView.ok(saleClassifyGoodService.insertSelective(good));
	}
	
	/**
	 * 添加货物
	 * @param good
	 * @return
	 */
	@PostMapping("/addSaleGood")
	public ResponseView addSaleGood(SaleGood good) {
		good.setAddTime(new Date());
		return ResponseView.ok(saleGoodService.insertSelective(good));
	}
	
	/**
	 * 设置集市信息
	 * @param marketConfig
	 * @return
	 */
	@PostMapping("/setMarketConfig")
	public ResponseView setMarketConfig(MarketConfig marketConfig) {
		if(marketConfig.getStatus() != null) {
			GameConfig.config.getMarketConfig().setStatus(marketConfig.getStatus());;
		}
		if(marketConfig.getDownGoodTimes() != null) {
			GameConfig.config.getMarketConfig().setDownGoodTimes(marketConfig.getDownGoodTimes());
		}
		flushConfig();
		return ResponseView.ok();
	}

}