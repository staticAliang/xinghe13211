package com.fengshen.server.data.write.safelock;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SAFE_LOCK_OPEN_CHANGE extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0x803F;
	}

}
