package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_ACTION extends BaseWrite<Vo_C_ACTION> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_C_ACTION object) {
		Vo_C_ACTION object2 = (Vo_C_ACTION) object;
		GameWriteTool.writeShort(writeBuf, object2.round);
		GameWriteTool.writeInt(writeBuf, object2.aid);
		GameWriteTool.writeShort(writeBuf, object2.action);
		GameWriteTool.writeInt(writeBuf, object2.vid);
		GameWriteTool.writeInt(writeBuf, object2.para);
	}

	@Override
	public int cmd() {
		return 19959;
	}
}
