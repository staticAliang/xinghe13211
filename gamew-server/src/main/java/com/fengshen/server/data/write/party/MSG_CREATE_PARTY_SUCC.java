package com.fengshen.server.data.write.party;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 帮派创建成功消息
 * 
 *
 */
public class MSG_CREATE_PARTY_SUCC extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB045;
	}

}
