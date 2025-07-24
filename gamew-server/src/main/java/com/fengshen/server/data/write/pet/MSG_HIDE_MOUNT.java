package com.fengshen.server.data.write.pet;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_HIDE_MOUNT extends BaseWrite<Object[]> {

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		
		GameWriteTool.writeInt(buff, (Integer) object[0]);
		GameWriteTool.writeByte(buff, (Integer) object[1]);
	}

	@Override
	public int cmd() {
		return 0xD0A3;
	}

}
