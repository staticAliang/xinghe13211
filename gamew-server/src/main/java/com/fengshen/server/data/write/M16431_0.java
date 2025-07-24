package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_16431_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M16431_0 extends BaseWrite<Vo_16431_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_16431_0 object) {
		GameWriteTool.writeShort(writeBuf, object.x);
		GameWriteTool.writeShort(writeBuf, object.y);
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeByte(writeBuf, object.dir);
	}

	@Override
	public int cmd() {
		return 16431;
	}
}
