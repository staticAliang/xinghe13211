package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_CLIENT_DISCONNECTED extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
	
		GameWriteTool.writeByte(buff, 0);
	}

	@Override
	public int cmd() {
		return 0x1368;
	}

}
