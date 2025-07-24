package com.fengshen.server.data.write.pet;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PET_ICON_UPDATED extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		GameWriteTool.writeString(buff, (String) object[0]);
		GameWriteTool.writeByte(buff, (Integer) object[1]);
	}

	@Override
	public int cmd() {
		return 0x5132;
	}

}
