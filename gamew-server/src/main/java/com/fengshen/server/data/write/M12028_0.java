package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_12028_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M12028_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_12028_0 object2 = (Vo_12028_0) object;
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeShort(writeBuf, object2.effect_no);
		GameWriteTool.writeInt(writeBuf, object2.type);
		if (object2.name != null) {
			GameWriteTool.writeString(writeBuf, object2.name);
		}
		GameWriteTool.writeByte(writeBuf, 0);
	}

	@Override
	public int cmd() {
		return 12028;
	}
}
