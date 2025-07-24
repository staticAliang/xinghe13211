package com.fengshen.server.data.write.system;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_UPLOAD_COMBAT_MESSAGE extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
	}

	@Override
	public int cmd() {
		return 0x8227;
	}

}
