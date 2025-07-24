package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45141_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M45141_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_45141_0 object2 = (Vo_45141_0) object;
		GameWriteTool.writeShort(writeBuf, object2.round);
		GameWriteTool.writeByte(writeBuf, object2.animate_done);
	}

	@Override
	public int cmd() {
		return 45141;
	}
}
