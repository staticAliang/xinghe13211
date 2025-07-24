package com.fengshen.server.data.write.jiehun;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_WEDDING_CHECK_MUSIC extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		
		GameWriteTool.writeByte(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB0D4;
	}

}
