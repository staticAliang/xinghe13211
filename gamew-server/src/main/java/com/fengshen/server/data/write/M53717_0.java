package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_53717_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M53717_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_53717_0 object2 = (Vo_53717_0) object;
		GameWriteTool.writeInt(writeBuf, object2.attacker_id);
		GameWriteTool.writeInt(writeBuf, object2.victim_id);
		GameWriteTool.writeByte(writeBuf, object2.type);
		GameWriteTool.writeByte(writeBuf, object2.result);
		GameWriteTool.writeString(writeBuf, object2.itemName);
	}

	@Override
	public int cmd() {
		return 53717;
	}
}
