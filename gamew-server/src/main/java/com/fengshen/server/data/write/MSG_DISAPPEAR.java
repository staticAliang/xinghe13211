package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * MSG_DISAPPEAR 角色(NPC)不再视野内
 */
public class MSG_DISAPPEAR extends BaseWrite<Integer> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Integer object) {
		GameWriteTool.writeInt(writeBuf, object);
		GameWriteTool.writeByte(writeBuf, 1);
	}

	@Override
	public int cmd() {
		return 12285;
	}
}
