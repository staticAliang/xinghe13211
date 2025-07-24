package com.fengshen.web.controller.sys.data;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.DateUtil;
import com.fengshen.core.util.ResponseView;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.CharaTrail;
import com.fengshen.server.game.GameData;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 今日统计
 * @author weilian
 *
 */
@RestController
@RequestMapping("/wd/sys/dailyStats")
public class WdDailyStatsController extends BaseController {

	
	/**
	 * 获取所有统计信息
	 * @param ct
	 * @return
	 * @throws IllegalAccessException 
	 */
	@PostMapping("/getDailyStats")
	public ResponseView getDailyStats(Page<CharaTrail> page, CharaTrail ct, String time) throws IllegalAccessException {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("id desc");
		PageHelper.getLocalPage().count(false);
		Map<String, Object> objectToMap = Utils.objectToMap(ct);
		Example example = new Example(CharaTrail.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo(ct);
		if(!StringUtils.isNullOrEmpty(time)) {
			String[] split = time.split("~");
			String startTime = split[0].trim();
			String endTime = split[1].trim();
			createCriteria.andCondition("DATE_FORMAT(add_time,\"%Y-%m-%d\")>=", startTime).andCondition("DATE_FORMAT(add_time,\"%Y-%m-%d\")<=", endTime);
			objectToMap.put("startTime", startTime);
			objectToMap.put("endTime", endTime);
		}else {
		}
		List<CharaTrail> selectByExample = GameData.that.charaTrailService.selectByExample(example);
		PageInfo<CharaTrail> pageInfo = new PageInfo<>(selectByExample);
		pageInfo.setTotal(10000000);
		return ResponseView.ok(settingsPage(pageInfo));
	}
}