package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_4275_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M4275_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_4275_0 object2 = (Vo_4275_0) object;
		GameWriteTool.writeInt(writeBuf, object2.a);
	}

	@Override
	public int cmd() {
		return 4275;
	}
}
