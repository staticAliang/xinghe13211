package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M40964_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_40964_0 object2 = (Vo_40964_0) object;
		GameWriteTool.writeShort(writeBuf, object2.type);
		GameWriteTool.writeString(writeBuf, object2.name);
		GameWriteTool.writeString(writeBuf, object2.param);
		GameWriteTool.writeShort(writeBuf, object2.rightNow);
	}

	@Override
	public int cmd() {
		return 40964;
	}
}
