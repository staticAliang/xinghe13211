package com.fengshen.server.data.write.jiehun;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_CLOSE_TIQIN_DLG extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
	}

	@Override
	public int cmd() {
		return 0xB06E;
	}

}
