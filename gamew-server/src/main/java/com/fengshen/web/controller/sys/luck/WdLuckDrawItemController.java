package com.fengshen.web.controller.sys.luck;

import com.fengshen.server.game.GameData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.web.controller.BaseController;
import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.LuckDrawItem;
import com.fengshen.db.service.system.LuckDrawItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 幸运抽奖大使
 * 
 *
 */
@RequestMapping("/sys/wd/luckDrawItem")
@RestController
public class WdLuckDrawItemController extends BaseController {

	@Autowired
	private LuckDrawItemService ls;
	
	
	/**
	 * 获取配置信息
	 * @return
	 */
	@PostMapping("/getLuckDrawItems")
	public ResponseView getLuckDrawItems(Page<Object> page, LuckDrawItem info) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("level,add_time desc");
		Example example = new Example(LuckDrawItem.class);
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isNullOrEmpty(info.getItem())) {
			createCriteria.andLike("item", "%"+info.getItem()+"%");
		}
		if(info.getLevel() != null) {
			createCriteria.andEqualTo("level", info.getLevel());
		}
		createCriteria.andEqualTo("type", info.getType());
		PageInfo<LuckDrawItem> pageInfo = new PageInfo<>(ls.selectByExample(example));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	/**
	 * 获取配置信息
	 * @return
	 */
	@PostMapping("/getFuDaiLuckDrawItems")
	public ResponseView getFuDaiLuckDrawItems(Page<Object> page, LuckDrawItem info) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize()).setOrderBy("level,add_time desc");
		Example example = new Example(LuckDrawItem.class);
		Criteria createCriteria = example.createCriteria();
		if(!StringUtils.isNullOrEmpty(info.getItem())) {
			createCriteria.andLike("item", "%"+info.getItem()+"%");
		}
		if(info.getLevel() != null) {
			createCriteria.andEqualTo("level", info.getLevel());
		}
		createCriteria.andEqualTo("type", info.getType());
		PageInfo<LuckDrawItem> pageInfo = new PageInfo<>(ls.selectByExample(example));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	/**
	 * 添加奖项
	 * @param c
	 * @return
	 */
	@PostMapping("/addLuckDrawItem")
	public ResponseView addLuckDrawItem(LuckDrawItem c) {
		ls.add(c);
		return ResponseView.ok();
	}
	
	/**
	 * 删除抽奖
	 * @param id
	 * @return
	 */
	@PostMapping("/delLuckDrawItem")
	public ResponseView delLuckDrawItem(int id) {
		ls.deleteById(id);
		return ResponseView.ok();
	}
	/**
	 * 删除抽奖
	 * @param id
	 * @return
	 */
	@PostMapping("/delFuDaiLuckDrawItem")
	public ResponseView delLuckDrawItem(int id,String type) {
		ls.deleteByIdAndType(id,type);
		return ResponseView.ok();
	}
	
	/**
	 * 修改抽奖
	 * @param id
	 * @return
	 */
	@PostMapping("/updateById")
	public ResponseView updateById(LuckDrawItem c) {
		ls.updateById(c);
		return ResponseView.ok();
	}
	
	/**
	 * 刷新缓存
	 * @return
	 */
	@RequestMapping("/refreshCache")
	public ResponseView refreshCache() {
		ls.refreshCache();
		return ResponseView.ok();
	}
}