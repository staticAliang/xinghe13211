package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_ADD_FRIEND_OPER;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 弹出添加好友成功界面
 * 
 *
 */
public class MSG_ADD_FRIEND_OPER extends BaseWrite<Vo_ADD_FRIEND_OPER> {

	@Override
	protected void writeO(ByteBuf buff, Vo_ADD_FRIEND_OPER object) {
	
		GameWriteTool.writeString(buff, object.getGid());
		GameWriteTool.writeString(buff, object.getName());
		GameWriteTool.writeString(buff, object.getPartyName());
		GameWriteTool.writeInt(buff, object.getIcon());
		GameWriteTool.writeInt(buff, object.getLevel());
	}

	@Override
	public int cmd() {
		return 0xB0F8;
	}

}
