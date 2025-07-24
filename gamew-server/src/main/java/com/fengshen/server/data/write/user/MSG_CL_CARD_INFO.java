package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_CL_CARD_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_CL_CARD_INFO extends BaseWrite<Vo_CL_CARD_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_CL_CARD_INFO object) {
		
		GameWriteTool.writeShort(buff, object.getSize());
		GameWriteTool.writeShort(buff, 10);
		GameWriteTool.writeShort(buff, 0);
		GameWriteTool.writeShort(buff, 2);
		GameWriteTool.writeInt(buff, 111);
		GameWriteTool.writeInt(buff, 111);
	}

	@Override
	public int cmd() {
		return 0x802B;
	}

}