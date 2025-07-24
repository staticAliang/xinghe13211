package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_MOVE_CHAR;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 移动好友分组
 * 
 * 
 *
 */
public class MSG_FRIEND_MOVE_CHAR extends BaseWrite<Vo_FRIEND_MOVE_CHAR> {
	
	@Override
	protected void writeO(ByteBuf buff, Vo_FRIEND_MOVE_CHAR object) {
		
		GameWriteTool.writeString(buff, object.getFromId());
		GameWriteTool.writeString(buff, object.getToId());
		GameWriteTool.writeInt(buff, object.getGids().size());
		for(String gid:object.getGids()) {
			GameWriteTool.writeString(buff, gid);
		}
	}

	@Override
	public int cmd() {
		return 0xB08B;
	}

}
