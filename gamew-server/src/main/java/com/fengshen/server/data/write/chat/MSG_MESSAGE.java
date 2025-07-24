package com.fengshen.server.data.write.chat;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_MESSAGE extends BaseWrite<Vo_MESSAGE> {
	@Override
	protected void writeO(final ByteBuf writeBuf,  Vo_MESSAGE object) {
		GameWriteTool.writeShort(writeBuf, object.channel);
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeString(writeBuf, object.name);
		GameWriteTool.writeString2(writeBuf, object.msg);
		GameWriteTool.writeInt(writeBuf, object.time);
		GameWriteTool.writeShort(writeBuf, object.privilege);
		GameWriteTool.writeString(writeBuf, object.server_name);
		GameWriteTool.writeShort(writeBuf, object.show_extra);
		GameWriteTool.writeByte(writeBuf, object.show_time);
		GameWriteTool.writeShort(writeBuf, object.icon);
	}

	@Override
	public int cmd() {
		return 0x2FFF;
	}
}
