package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_COMMAND_ACCEPTED extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		
		GameWriteTool.writeInt(buff, (Integer) object[0]);
		GameWriteTool.writeShort(buff, (Integer) object[1]);
	}

	@Override
	public int cmd() {
		return 0x2DD3;
	}

}
