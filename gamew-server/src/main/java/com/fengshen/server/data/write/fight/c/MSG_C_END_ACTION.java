package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_END_ACTION extends BaseWrite<Vo_C_END_ACTION> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_END_ACTION object) {
		GameWriteTool.writeInt(writeBuf, object.id);
	}

	@Override
	public int cmd() {
		return 7655;
	}
}
