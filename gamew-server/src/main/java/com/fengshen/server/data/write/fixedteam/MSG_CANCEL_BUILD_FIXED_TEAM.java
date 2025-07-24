package com.fengshen.server.data.write.fixedteam;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_CANCEL_BUILD_FIXED_TEAM extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
	}

	@Override
	public int cmd() {
		return 0xD201;
	}

}
