package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_4197_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M4197_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_4197_0 object2 = (Vo_4197_0) object;
		GameWriteTool.writeInt(writeBuf, object2.id);
	}

	@Override
	public int cmd() {
		return 4197;
	}
}
