package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_MENU_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_MENU_LIST extends BaseWrite<Vo_MENU_LIST> {
	@Override
	protected void writeO(final ByteBuf writeBuf,Vo_MENU_LIST object2) {
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeInt(writeBuf, object2.portrait);
		GameWriteTool.writeShort(writeBuf, object2.pic_no);
		GameWriteTool.writeString2(writeBuf, object2.content);
		GameWriteTool.writeString(writeBuf, object2.secret_key);
		GameWriteTool.writeString(writeBuf, object2.name);
		GameWriteTool.writeByte(writeBuf, object2.attrib);
		GameWriteTool.writeString(writeBuf, "");
	}

	@Override
	public int cmd() {
		return 8247;
	}
}
