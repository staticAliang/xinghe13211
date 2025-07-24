package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_SANDGLASS;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_SANDGLASS extends BaseWrite<Vo_C_SANDGLASS> {
	
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_SANDGLASS object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeShort(writeBuf, object.show);
	}

	@Override
	public int cmd() {
		return 0x2DC1;
	}
}