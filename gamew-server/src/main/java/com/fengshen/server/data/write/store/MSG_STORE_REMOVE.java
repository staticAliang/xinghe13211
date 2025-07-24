package com.fengshen.server.data.write.store;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_STORE_REMOVE extends BaseWrite<Vo_61677_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_61677_0 object) {
		GameWriteTool.writeString(writeBuf, object.store_type);
		GameWriteTool.writeInt(writeBuf, object.npcID);
		GameWriteTool.writeShort(writeBuf, 1);
		GameWriteTool.writeByte(writeBuf, object.isGoon);
		GameWriteTool.writeShort(writeBuf, object.pos);
	}

	@Override
	public int cmd() {
		return 61677;
	}
}