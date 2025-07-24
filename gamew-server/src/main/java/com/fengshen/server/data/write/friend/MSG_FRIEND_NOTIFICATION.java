package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FRIEND_NOTIFICATION extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		//charbuf
		GameWriteTool.writeString(buff, String.valueOf(object[0]));
		//serverName
		GameWriteTool.writeString(buff, String.valueOf(object[1]));
		//online
		GameWriteTool.writeShort(buff, Integer.valueOf(String.valueOf(object[2])));
		//insider_level
		GameWriteTool.writeByte(buff, Integer.valueOf(String.valueOf(object[3])));
		
	}

	@Override
	public int cmd() {
		return 0x206D;
	}

}
