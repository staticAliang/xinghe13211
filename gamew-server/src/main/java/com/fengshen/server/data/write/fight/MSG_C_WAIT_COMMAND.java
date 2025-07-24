package com.fengshen.server.data.write.fight;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_WAIT_COMMAND;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_WAIT_COMMAND extends BaseWrite<Vo_C_WAIT_COMMAND> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_WAIT_COMMAND object2) {
		GameWriteTool.writeShort(writeBuf, object2.menu);
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeShort(writeBuf, object2.time);
		GameWriteTool.writeInt(writeBuf, object2.question);
		GameWriteTool.writeShort(writeBuf, object2.round);
		GameWriteTool.writeInt(writeBuf, object2.curTime);
	}

	@Override
	public int cmd() {
		return 7659;
	}
}
