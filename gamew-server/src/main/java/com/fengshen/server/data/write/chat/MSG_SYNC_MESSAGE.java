package com.fengshen.server.data.write.chat;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SYNC_MESSAGE extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		

		
		Vo_MESSAGE npcMessage = GameCommonUtil.npcMessage("崩而可危_1111", "测322111试", 35618, 6189, 1);
//		ByteBuf writeBuf = Unpooled.buffer();
//		GameWriteTool.writeShort(writeBuf, npcMessage.channel);
//		GameWriteTool.writeInt(writeBuf, npcMessage.id);
//		GameWriteTool.writeString(writeBuf, npcMessage.name);
//		GameWriteTool.writeString2(writeBuf, npcMessage.msg);
//		GameWriteTool.writeInt(writeBuf, npcMessage.time);
//		GameWriteTool.writeShort(writeBuf, npcMessage.privilege);
//		GameWriteTool.writeString(writeBuf, npcMessage.server_name);
//		GameWriteTool.writeShort(writeBuf, npcMessage.show_extra);
//		GameWriteTool.writeByte(writeBuf, npcMessage.show_time);
//		GameWriteTool.writeShort(writeBuf, npcMessage.icon);
		
		ByteBuf writeBuf = new MSG_MESSAGE().write(npcMessage);
		
		
		byte[] bytes = new byte[writeBuf.readableBytes()];
		GameWriteTool.writeInt(buff, bytes.length);
		writeBuf.readBytes(bytes);
		GameWriteTool.writeBytes(buff, bytes);
		
		
		
		GameWriteTool.writeShort(buff, 0x2FFF);
		
		
	}

	@Override
	public int cmd() {
		return 0xFDD1;
	}

}
