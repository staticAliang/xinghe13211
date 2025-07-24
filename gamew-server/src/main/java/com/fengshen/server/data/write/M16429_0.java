package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_16429_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M16429_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_16429_0 object2 = (Vo_16429_0) object;
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeShort(writeBuf, object2.x);
		GameWriteTool.writeShort(writeBuf, object2.y);
		GameWriteTool.writeInt(writeBuf, object2.map_id);
	}

	@Override
	public int cmd() {
		return 16429;
	}
}
