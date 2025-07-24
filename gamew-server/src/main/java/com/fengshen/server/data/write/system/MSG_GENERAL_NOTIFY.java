package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GENERAL_NOTIFY extends BaseWrite<Vo_GENERAL_NOTIFY> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_GENERAL_NOTIFY object) {
		GameWriteTool.writeShort(writeBuf, object.notify);
		GameWriteTool.writeString(writeBuf, object.para);
	}

	@Override
	public int cmd() {
		return 9129;
	}
}
