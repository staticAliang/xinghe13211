package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M12285_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final int id = (int) object;
		GameWriteTool.writeInt(writeBuf, id);
		GameWriteTool.writeByte(writeBuf, 32768);
	}

	@Override
	public int cmd() {
		return 12285;
	}
}
