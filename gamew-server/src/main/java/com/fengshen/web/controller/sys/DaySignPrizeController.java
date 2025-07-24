package com.fengshen.web.controller.sys;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.DaySignPrize;
import com.fengshen.db.service.game.DaySignPrizeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;

@RestController
@RequestMapping("/sys/wd/daySignPrize")
public class DaySignPrizeController extends BaseController {

	@Autowired
	private DaySignPrizeService dsps;
	
	
	/**
	 * a获取每日签到
	 * @param page
	 * @param name
	 * @return
	 */
	@PostMapping("/getDaySignPrizes")
	public ResponseView getDaySignPrizes(Page<DaySignPrize> page, String name) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("add_time desc");
		Example example = new Example(DaySignPrize.class);
		if(!StringUtils.isNullOrEmpty(name)) {
			example.createCriteria().andLike("name", "%"+name+"%");
		}
		List<DaySignPrize> daySignPrizes = dsps.selectByExample(example);
		PageInfo<DaySignPrize> pageInfo = new PageInfo<>(daySignPrizes);
		
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * a更新
	 * @param ps
	 * @return
	 */
	@PostMapping("/update")
	public ResponseView update(DaySignPrize psp) {
		if(psp.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		psp.setUpdateTime(new Date());
		dsps.updateByPrimaryKeySelective(psp);
		dsps.refreshCache();
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCacheStoreInfo() {
		dsps.refreshCache();
		return ResponseView.ok();
	}
}