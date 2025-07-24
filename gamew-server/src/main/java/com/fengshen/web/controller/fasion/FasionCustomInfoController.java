package com.fengshen.web.controller.fasion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.service.chara.FasionCustomInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;

@RequestMapping("/fasionCustomInfo")
@RestController
public class FasionCustomInfoController extends BaseController {

	@Autowired
	private FasionCustomInfoService fcis;
	
	
	/**
	 *  获取自定义时装
	 * @param name
	 * @return
	 */
	@PostMapping("/getFasionCustomInfos")
	public ResponseView getFasionCustomInfos(Page<Object> page, String name) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		Example example = new Example(FasionCustomInfo.class);
		if(!StringUtils.isNullOrEmpty(name)) {
			example.createCriteria().andLike("name", "%"+name+"%");
		}
		List<FasionCustomInfo> selectAll = fcis.selectByExample(example);
		PageInfo<FasionCustomInfo> pageInfo = new PageInfo<FasionCustomInfo>(selectAll);
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 *  获取自定义时装
	 * @param name
	 * @return
	 */
	@PostMapping("/update")
	public ResponseView update(FasionCustomInfo fc) {
		if(fc.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		return ResponseView.ok(fcis.updateById(fc));
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCache() {
		fcis.refreshCache();
		return ResponseView.ok();
	}
}