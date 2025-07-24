package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_11757_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M11757_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_11757_0 object2 = (Vo_11757_0) object;
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeShort(writeBuf, object2.list.size());
		for (Integer integer : object2.list) {
			GameWriteTool.writeInt(writeBuf, integer);
		}
	}

	@Override
	public int cmd() {
		return 11757;
	}
}
