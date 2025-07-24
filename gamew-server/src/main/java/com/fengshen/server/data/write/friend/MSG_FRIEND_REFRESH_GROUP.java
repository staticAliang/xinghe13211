package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FRIEND_REFRESH_GROUP extends BaseWrite<String[]> {

	@Override
	protected void writeO(ByteBuf buff, String[] object) {
		//groupId
		GameWriteTool.writeString(buff, object[0]);
		//name
		GameWriteTool.writeString(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0xB091;
	}

}
