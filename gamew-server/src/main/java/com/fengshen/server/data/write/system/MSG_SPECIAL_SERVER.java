package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SPECIAL_SERVER extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
		GameWriteTool.writeShort(buff, 1);
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeShort(buff, 2);
		
	}

	@Override
	public int cmd() {
		return 0xB062;
	}

}
