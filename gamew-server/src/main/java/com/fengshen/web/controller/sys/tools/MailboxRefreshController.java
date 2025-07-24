package com.fengshen.web.controller.sys.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.db.service.system.MailboxRefreshService;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * 邮件管理
 * @author aaa
 *
 */
@RestController
@RequestMapping("/sys/wd/mail")
public class MailboxRefreshController extends BaseController {

	@Autowired
	private MailboxRefreshService ms;
	
	/**
	 * 获取所有邮件
	 * @param page
	 * @param title
	 * @return
	 */
	@PostMapping("/getMailboxRefreshs")
	public ResponseView getMailboxRefreshs(Page<MailboxRefresh> page, String title) {
		PageHelper.startPage(page.getPageNum(),page.getPageSize());
		Example example = new Example(MailboxRefresh.class);
		example.orderBy("createTime").desc();
		if(!StringUtils.isNullOrEmpty(title)) {
			example.createCriteria().andLike("title", "%"+title+"%");
		}
		PageInfo<MailboxRefresh> pageInfo = new PageInfo<>(ms.selectByExample(example));
		return ResponseView.ok(settingsPage(pageInfo));
	}
	
	/**
	 * 发送邮件
	 * @param mail 邮件
	 * @return
	 */
	@PostMapping("/sendMail")
	public ResponseView sendMail(@RequestParam Map<String,Object> data) {
		
		if(StringUtils.isNullOrEmpty((String) data.get("title"))) {
			ResponseView.fail("标题不能为空");
		}
		if(StringUtils.isNullOrEmpty((String) data.get("type"))) {
			ResponseView.fail("类型不能为空");
		}
		if(StringUtils.isNullOrEmpty((String) data.get("online"))) {
			ResponseView.fail("状态不能为空");
		}
		String msg = "";
		if(data.get("msg") != null) {
			msg = (String) data.get("msg");
		}
		String attachment = "";
		if(data.get("attachment") != null) {
			attachment = (String) data.get("attachment");
		}
		//标题
		String title = (String) data.get("title");
		//类型
		String type = (String) data.get("type");
		//状态
		String online = (String) data.get("online");
		if("0".equals(type)) {
			if(StringUtils.isNullOrEmpty((String) data.get("toName"))) {
				ResponseView.fail("收件人不能为空");
			}
		}
		if(StringUtils.isNullOrEmpty((String) data.get("online"))) {
			ResponseView.fail("状态不能为空");
		}
		//指定地图
		String map = (String) data.get("maps");
		Map<String,String> maps = new HashMap<>();
		List<String> mapList = new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(map)) {
			for(String m:map.split("#I")) {
				maps.put(m, m);
				mapList.add(m);
			}
		}
		List<Integer> ids = new ArrayList<>();
		try {
			//指定人
			if("0".equals(type)) {
				String toName = (String) data.get("toName");
				String[] toNames = toName.split("#I");
				//指定人的情况下不管在不在线都发送
				for(String name:toNames) {
					GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(name);
					Vo_MAILBOX_REFRESH sendDeposit = new Vo_MAILBOX_REFRESH();
					sendDeposit.attachment = attachment;
					sendDeposit.create_time = (int) (System.currentTimeMillis() / 1000L);
					sendDeposit.expired_time = (int) (System.currentTimeMillis() / 1000L + 36 * 60 * 60);
					sendDeposit.id = GameCommonUtil.UUID();
					sendDeposit.msg = msg;
					sendDeposit.status = 0;
					sendDeposit.title = title;
					sendDeposit.type = 0;
					sendDeposit.sender = "后台";
					sendDeposit.attachment = attachment;
					
					if(gameObjectChar != null) {
						//指定了地图
						if(!maps.isEmpty()) {
							//如果该人不在这个地图这不会有奖励
							if(maps.get(gameObjectChar.chara.getMapName()) == null) {
								continue;
							}
						}
						sendDeposit.toGid = gameObjectChar.chara.uuid;
						//通知
						gameObjectChar.sendOne(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(sendDeposit));
						GameCommonUtil.sendTips("有新的邮件,请注意查收", gameObjectChar);
					}else {
						//查询该人的gid
						Characters ch = GameData.that.baseCharactersService.findOneByNameSelectProperties(name, "gid","mapName");
						if(ch == null) {
							continue;
						}
						//指定了地图
						if(!maps.isEmpty()) {
							//如果该人不在这个地图这不会有奖励
							if(maps.get(ch.getMapName()) == null) {
								continue;
							}
						}
						sendDeposit.toGid = ch.getGid();
					}
					MailboxRefresh mail = GameCommonUtil.convertMail(sendDeposit);
					GameData.that.mailboxRefreshService.addMail(mail);
					ids.add(mail.getId());
				}
				
			}else {//全服发送福利
				Example example = new Example(Characters.class);
				example.selectProperties("gid");
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("block", 0).andEqualTo("xiaozi", 0).andEqualTo("deleted", 0);
				//状态
				if(online.equals("1")) { //在线
					createCriteria.andEqualTo("online", 1);
				}else if(online.equals("2")){//只给人离线
					createCriteria.andEqualTo("online", 0);
				}
				if(!mapList.isEmpty()) {
					createCriteria.andIn("mapName", mapList);
				}
				List<Characters> chs = GameData.that.baseCharactersService.selectByExample(example);
				for(Characters c:chs) {
					Vo_MAILBOX_REFRESH sendDeposit = new Vo_MAILBOX_REFRESH();
					sendDeposit.attachment = attachment;
					sendDeposit.create_time = (int) (System.currentTimeMillis() / 1000L);
					sendDeposit.expired_time = (int) (System.currentTimeMillis() / 1000L + 36 * 60 * 60);
					sendDeposit.id = GameCommonUtil.UUID();
					sendDeposit.msg = msg;
					sendDeposit.status = 0;
					sendDeposit.title = title;
					sendDeposit.type = 0;
					sendDeposit.sender = "后台";
					sendDeposit.attachment = attachment;
					sendDeposit.toGid = c.getGid();
					MailboxRefresh mail = GameCommonUtil.convertMail(sendDeposit);
					GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(c.getGid());
					if(gameObjectChar != null) {
						//通知
						gameObjectChar.sendOne(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(sendDeposit));
						GameCommonUtil.sendTips("有新的邮件,请注意查收", gameObjectChar);
					}
					GameData.that.mailboxRefreshService.addMail(mail);
					ids.add(mail.getId());
				}
			}
		} catch (Exception e) {
			if(!ids.isEmpty()) {
				Example example = new Example(MailboxRefresh.class);
				example.createCriteria().andIn("id", ids);
				GameData.that.mailboxRefreshService.deleteByExample(example);
			}
			e.printStackTrace();
			ResponseView.fail("发送失败");
		}
		return ResponseView.ok("发送成功");
	}
	
}