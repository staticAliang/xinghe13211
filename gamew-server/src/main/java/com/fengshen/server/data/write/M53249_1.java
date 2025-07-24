package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_53249_1;
import com.fengshen.server.data.vo.Vo_53249_1.Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M53249_1 extends BaseWrite<Vo_53249_1> {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Vo_53249_1 object) {
		GameWriteTool.writeByte(writeBuf, object.type);
		GameWriteTool.writeShort(writeBuf, object.items.size());
		for(Items object2:object.items) {
			GameWriteTool.writeString(writeBuf, object2.name);
			GameWriteTool.writeInt(writeBuf, object2.price);
		}
	}

	@Override
	public int cmd() {
		return 53249;
	}
}
