package com.fengshen.server.process.friend;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.SysMsgType;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 好友验证
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_VERIFY_FRIEND implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		String gid = GameReadTool.readString(buff);
		String message = GameReadTool.readString(buff);
		String[] gidArr = new String[2];
		if(gid.indexOf("|fenge|") != -1) {
			gidArr = gid.split("\\|fenge\\|");
		}
		//发送验证的用户
		GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(gidArr[0]);
		//显示的用户
		GameObjectChar showGameObject = GameObjectCharMng.getGameObjectCharByUUid(gidArr[1]);
		Chara chara = null;
		if(showGameObject == null) {
			// 去数据库查询
			Characters findOneByNameBold = GameData.that.baseCharactersService.findOneByGid2(gameObjectCharByUUid.chara.toVerifyFriendGid);
			chara = JSONObject.parseObject(findOneByNameBold.getData(), Chara.class);
		} else {
			chara = showGameObject.chara;
		}
		// 发送验证消息到对方
		final Vo_MAILBOX_REFRESH vo_40961_0 = new Vo_MAILBOX_REFRESH();
		vo_40961_0.id = chara.uuid;
		vo_40961_0.type = SysMsgType.FRIEND_CHECK.getValue();
		vo_40961_0.sender = chara.name;
		vo_40961_0.title = gameObjectCharByUUid.chara.uuid;
		vo_40961_0.msg = message;
		// lev, icon, party, isVip
		StringBuilder att = new StringBuilder();
		att.append(chara.level).append(";").append(chara.waiguan).append(";")
		.append(chara.getPartyName()).append(";").append(chara.vipType);
		vo_40961_0.attachment = att.toString();
		vo_40961_0.create_time = (int) (System.currentTimeMillis() / 1000L);
		vo_40961_0.expired_time = (int) (System.currentTimeMillis() / 1000L) + 1000000;
		vo_40961_0.status = 0;
		
		GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(vo_40961_0), gameObjectCharByUUid.chara.id);
		
		//查询是否存在此邮件
//		Example example = new Example(MailboxRefresh.class);
//		example.createCriteria().andEqualTo("title", vo_40961_0.title).andEqualTo("gid", vo_40961_0.id);
//		int count = GameData.that.mailboxRefreshService.selectCountByExample(example);
//		if(count == 0) {
//			//保存数据
			MailboxRefresh m =  new MailboxRefresh();
			m.setSender(vo_40961_0.sender);
			m.setAttachment(vo_40961_0.attachment);
			m.setCount(vo_40961_0.count);
			m.setCreateTime(vo_40961_0.create_time);
			m.setExpiredTime(vo_40961_0.expired_time);
			m.setStatus(vo_40961_0.status);
			m.setMsg(vo_40961_0.msg);
			m.setTitle(vo_40961_0.title);
			m.setGid(vo_40961_0.id);
			m.setType(vo_40961_0.type);
			m.setToGid(gameObjectCharByUUid.chara.uuid);
			GameData.that.mailboxRefreshService.insertSelective(m);
//		}
		log.info("好友验证:NAME={}",name);
	}

	@Override
	public int cmd() {
		return 0xA026;
	}

}
