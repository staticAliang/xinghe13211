package com.fengshen.server.process.system;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;
import com.qiniu.util.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 邮件操作
 * 
 *READ    = 0,    -- 阅读
    GETACC  = 1,    -- 领取附件
    DEL     = 2,    -- 删除邮件
    GETACCS = 3,    -- 领取附件
 */
@Service
@Slf4j
public class CMD_MAILBOX_OPERATE implements GameHandler{

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		int type = GameReadTool.readShort(buff);
		String id = GameReadTool.readString2(buff);
		int operate = GameReadTool.readShort(buff);
		log.info("邮件操作,type={},id={},operate={}",type,id,operate);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Example example = new Example(MailboxRefresh.class);
		example.createCriteria().andEqualTo("gid", id).andEqualTo("toGid", gameObjectChar.chara.uuid);
		MailboxRefresh mail = GameData.that.mailboxRefreshService.selectOneByExample(example);
		if(mail == null) {
			GameUtil.sendMeTips("不存在该邮件");
			return;
		}
		if(operate == 0) {
			//阅读
			mail.setStatus(1);
			GameData.that.mailboxRefreshService.updateByPrimaryKeySelective(mail);
		}else if(operate == 1) {
			//领取附件
			String attachment = mail.getAttachment();
			if(StringUtils.isNullOrEmpty(attachment)) {
				GameUtil.sendMeTips("该邮件没有附件可领");
				return;
			}
			//更新为领取的状态
			mail.setStatus(2);
			if(mail.getIsGetReward() == 1) {
				GameUtil.sendMeTips("该邮件附件已领");
				//更新下状态
				GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(GameCommonUtil.convertMailVo(mail)));
				return;
			}
			mail.setIsGetReward(1);
			if(mail.getTitle().equals("大日金乌活动")){
				String jiangli = mail.getSender();
				List<String[]> results = GameCommonUtil.parseRewardStr(jiangli);
				for(String[] result:results) {
					GameCommonUtil.getReward(gameObjectChar, result, "邮件管理");
				}
			}else{
				List<String[]> results = GameCommonUtil.parseRewardStr(mail.getAttachment());
				for(String[] result:results) {
					GameCommonUtil.getReward(gameObjectChar, result, "邮件管理");
				}
			}
			GameData.that.mailboxRefreshService.updateByPrimaryKeySelective(mail);
		}else if(operate == 2) {
			mail.setStatus(3);
			//删除邮件
			GameData.that.mailboxRefreshService.deleteByExample(example);
		}else if(operate == 3) {
			//一键领取
			Example examples = new Example(MailboxRefresh.class);
			examples.createCriteria().andEqualTo("toGid", gameObjectChar.chara.uuid).andEqualTo("status", 0).orEqualTo("status", 1);
			List<MailboxRefresh> mails = GameData.that.mailboxRefreshService.selectByExample(examples);
			List<Vo_MAILBOX_REFRESH> vos = new ArrayList<>();
			for(MailboxRefresh m:mails) {
				//领取附件
				String attachment = m.getAttachment();
				if(StringUtils.isNullOrEmpty(attachment)) {
					continue;
				}
				//为0的时候才给奖励
				if(mail.getIsGetReward() == 0) {
					if(mail.getTitle().equals("大日金乌活动")){
						String jiangli = mail.getSender();
						List<String[]> results = GameCommonUtil.parseRewardStr(jiangli);
						for(String[] result:results) {
							GameCommonUtil.getReward(gameObjectChar, result, "邮件管理");
						}
					}else {
						List<String[]> results = GameCommonUtil.parseRewardStr(m.getAttachment());
						for (String[] result : results) {
							GameCommonUtil.getReward(gameObjectChar, result, "邮件管理");
						}
					}
				}
				//更新为领取的状态
				m.setStatus(2);
				m.setIsGetReward(1);
				GameData.that.mailboxRefreshService.updateByPrimaryKeySelective(m);
				vos.add(GameCommonUtil.convertMailVo(m));
			}
			if(!mails.isEmpty()) {
				GameUtil.sendMeTips("一键领取成功");
			}else {
				GameUtil.sendMeTips("当前没有可领取附件的邮件");
			}
			GameObjectChar.send(new MSG_MAILBOX_REFRESH(), vos);
			return;
		}
		GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(GameCommonUtil.convertMailVo(mail)));
 	}

	@Override
	public int cmd() {
		return 0xA000;
	}

}
