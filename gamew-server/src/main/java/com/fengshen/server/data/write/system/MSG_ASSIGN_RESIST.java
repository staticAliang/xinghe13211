package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_ASSIGN_RESIST extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeInt(buff, object);
	}

	@Override
	public int cmd() {
		return 0x108F;
	}

}
