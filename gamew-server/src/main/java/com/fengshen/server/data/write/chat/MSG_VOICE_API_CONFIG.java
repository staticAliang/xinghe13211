package com.fengshen.server.data.write.chat;


import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_VOICE_API_CONFIG extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		GameWriteTool.writeString2(buff, "||"+object);
	}

	@Override
	public int cmd() {
		return 0x521A;
	}

}
