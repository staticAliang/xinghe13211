package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_APPLY_FRIEND_ITEM_RESULT extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		
		GameWriteTool.writeByte(buff, (Integer) object[0]);
		GameWriteTool.writeString(buff, (String) object[1]);
	}

	@Override
	public int cmd() {
		return 0xB069;
	}

}
