package com.fengshen.server.data.write.zuolao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_RELEASE_SUCC extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB0B0;
	}

}
