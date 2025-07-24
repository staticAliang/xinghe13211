package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_BE_ADD_FRIEND;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 添加好友弹框
 * 
 *
 */
public class MSG_BE_ADD_FRIEND extends BaseWrite<Vo_BE_ADD_FRIEND> {

	@Override
	protected void writeO(ByteBuf buff, Vo_BE_ADD_FRIEND object) {
		
		GameWriteTool.writeString(buff, object.getName());
		GameWriteTool.writeString(buff, object.getGid());
		GameWriteTool.writeInt(buff, object.getSetting());
	}

	@Override
	public int cmd() {
		return 0xB061;
	}

}
