package com.fengshen.server.data.write.fly;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FLY_ARTIFACT_MAKE extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		GameWriteTool.writeByte(buff, 1);
	}

	@Override
	public int cmd() {
		return 33575;
	}

}
