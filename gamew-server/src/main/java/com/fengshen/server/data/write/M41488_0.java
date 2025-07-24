package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_41488_0;
import com.fengshen.server.data.vo.Vo_41488_0.Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M41488_0 extends BaseWrite<Vo_41488_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_41488_0 object) {
		final Vo_41488_0 object2 = (Vo_41488_0) object;
		GameWriteTool.writeByte(writeBuf, object2.flag);
		GameWriteTool.writeByte(writeBuf, object2.label);
		GameWriteTool.writeString(writeBuf, object2.para);
		GameWriteTool.writeShort(writeBuf, object2.items.size());
		for(Items item:object2.items) {
			GameWriteTool.writeString(writeBuf, item.getName());
			GameWriteTool.writeInt(writeBuf, item.getPrice());
		}
	}

	@Override
	public int cmd() {
		return 41488;
	}
}
