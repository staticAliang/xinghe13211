package com.fengshen.server.data.write.system;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 邮件消息
 * 
 * 
 * SystemMessageMgr.SYSMSG_TYPE = {
    SYSTEM          = 0,    -- 系统消息
    FRIEND_CHECK    = 1,    -- 好友验证
    ARENA           = 2,    -- 竞技场信息
    RED_DOT         = 3,    -- 小红点提示
    TYPE_MAIL_CHATGROUP = 4, -- 群组系统消息
    TYPE_MAIL_MATERIAL  = 5, -- 材料赠送
    TYPE_MAIL_ACTIVITY  = 6, -- 活动切磋战报
    TYPE_MAIL_FRIEND    = 7, -- 好友区域验证消息
}

-- 邮件操作
local SYSMSG_OPERATE = {
    READ    = 0,    -- 阅读
    GETACC  = 1,    -- 领取附件
    DEL     = 2,    -- 删除邮件
}

-- 邮件状态
SystemMessageMgr.SYSMSG_STATUS = {
    UNREAD  = 0,
    READ    = 1,
    GET     = 2,
    DEL     = 3,
}
 * 
 * 
 *
 */
@Service
public class MSG_MAILBOX_REFRESH extends BaseWrite<List<Vo_MAILBOX_REFRESH>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, List<Vo_MAILBOX_REFRESH> object) {
		GameWriteTool.writeShort(writeBuf, object.size());
		for(int i=0;i<object.size();i++) {
			Vo_MAILBOX_REFRESH object2 = object.get(i);
			GameWriteTool.writeString(writeBuf, object2.id);
			GameWriteTool.writeShort(writeBuf, object2.type);
			GameWriteTool.writeString(writeBuf, object2.sender);
			GameWriteTool.writeString(writeBuf, object2.title);
			GameWriteTool.writeString2(writeBuf, object2.msg);
			GameWriteTool.writeString2(writeBuf, object2.attachment);
			GameWriteTool.writeInt(writeBuf, object2.create_time);
			GameWriteTool.writeInt(writeBuf, object2.expired_time);
			GameWriteTool.writeShort(writeBuf, object2.status);
		}
	}

	@Override
	public int cmd() {
		return 0xA001;
	}
}
