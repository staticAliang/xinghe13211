package com.fengshen.web.controller.sys;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.BlackList;
import com.fengshen.db.domain.Notice;
import com.fengshen.db.service.system.BlackListService;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qcloud.cos.utils.StringUtils;

import tk.mybatis.mapper.entity.Example;

/**
 * 系统控制器
 * 
 * 
 *
 */
@RestController
@RequestMapping("/system/")
public class SystemController extends BaseController {
	
	@Autowired
	private BlackListService blackListService;

	@RequestMapping("/getJavaThreads")
	public ResponseView getJavaThreads() {
		ThreadGroup group = Thread.currentThread().getThreadGroup();
		ThreadGroup topGroup = group;
		while (group != null) {
			topGroup = group;
			group = group.getParent();
		}
		int estimatedSize = topGroup.activeCount() * 2;
		Thread[] slackList = new Thread[estimatedSize];
		int actualSize = topGroup.enumerate(slackList);
		Thread[] list = new Thread[actualSize];
		System.arraycopy(slackList, 0, list, 0, actualSize);
		List<String> names = new ArrayList<>();
		for (Thread thread : list) {
			names.add(thread.getName());
		}
		return ResponseView.ok(names);
	}
	
	
	@RequestMapping("/flush")
	public ResponseView flush() {
		try {
			//进行存档一次
	    	List<GameObjectChar> all = GameObjectCharMng.getAll();
			for(GameObjectChar g:all) {
				Chara chara = g.chara;
				//如果在战斗则直接退出战斗
				if(chara.isFight) {
					FightContainer fightContainer = FightManager.getFightContainer(chara.id);
					if (fightContainer != null) {
						FightManager.listFight.remove(fightContainer);
						FightManager.sendOver(fightContainer, true);
					}
				}
				g.sendOne(new MSG_KICK_OFF(), "服务器维护");
	        	g.offline();
			}
		} finally {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					//关闭整个程序
					System.exit(0);
				}
			}, 3);
		}
		return ResponseView.ok();
	}
	
	
	@PostMapping("/getTimerPubliMssage")
	public ResponseView getTimerPubliMssage() {
		List<Notice> findAll = GameData.that.baseNoticeService.findAll();
		return ResponseView.ok(findAll);
	}
	
	/**
	 * 添加公告
	 * @param notice
	 * @return
	 */
	@PostMapping("/updateNotice")
	public ResponseView updateNotice(Notice notice) {
		
		if(notice.getId() == null) {
			ResponseView.fail("id不能为空");
		}
		if(notice.getTime() == null) {
			ResponseView.fail("时间不能为空");
		}
		GameData.that.baseNoticeService.updateById(notice);
		return ResponseView.ok();
	}
	
	/**
	 * 删除公告
	 * @param id
	 * @return
	 */
	@PostMapping("/delNotice")
	public ResponseView updateNotice(Integer id) {
		if(id == null) {
			ResponseView.fail("id不能为空");
		}
		GameData.that.baseNoticeService.deleteById(id);
		return ResponseView.ok();
	}
	
	/**
	 * 添加公告
	 * @param notice
	 * @return
	 */
	@PostMapping("/addNotice")
	public ResponseView addNotice(Notice notice) {
		if(notice.getTime() == null) {
			ResponseView.fail("时间不能为空");
		}
		GameData.that.baseNoticeService.add(notice);
		return ResponseView.ok();
	}
	
	@PostMapping("/getBlackLists")
	public ResponseView getRejectIps(Page<BlackList> page, String data) {
		PageHelper.startPage(page.getPageNum(), page.getPageSize()).setOrderBy("add_time desc");
		Example example = new Example(BlackList.class);
		if(!StringUtils.isNullOrEmpty(data)) {
			example.createCriteria().andEqualTo("data", data);
		}
		PageInfo<BlackList> pageInfo = new PageInfo<BlackList>(blackListService.selectByExample(example));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	@PostMapping("/delBlackList")
	public ResponseView delBlackList(Integer id) {
		if(id == null) {
			ResponseView.fail("id不能为空！");
		}
		int deleteByPrimaryKey = blackListService.deleteByPrimaryKey(id);
		blackListService.refreshCache();
		return ResponseView.ok(deleteByPrimaryKey);
	}
	
	@PostMapping("/addBlackList")
	public ResponseView addBlackList(String data) {
		BlackList b = new BlackList();
		b.setData(data);
		b.setAddTime(new Date());
		blackListService.insertSelective(b);
		return ResponseView.ok();
	}
	
	@PostMapping("/getClients")
	public ResponseView getClients(String data) {
		
		return ResponseView.ok();
	}
	
	/**
	 * 强制关闭客户端
	 * @param uuid
	 * @return
	 */
	@PostMapping("/forceShutdownClient")
	public ResponseView forceShutdownClient(String uuid) {
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByChannelId(uuid);
		if(gameObject != null) {
			gameObject.offline();
		}
		return ResponseView.ok();
	}
	
	@PostMapping("/addTestList")
	public ResponseView addTestList(String list) {
		GameData.that.redisUtils.set("testList", list, 1800);
		return ResponseView.ok();
	}
	
	@PostMapping("/getTestList")
	public ResponseView getTestList() {
		String string = GameData.that.redisUtils.get("testList");
		return ResponseView.ok((Object)string);
	}
}