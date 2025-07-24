package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 删除好友分组
 * 
 *
 */
public class MSG_FRINED_REMOVE_GROUP extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		//groupId
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB08E;
	}

}
