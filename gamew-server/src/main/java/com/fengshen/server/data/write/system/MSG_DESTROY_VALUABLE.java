package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_DESTROY_VALUABLE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_DESTROY_VALUABLE extends BaseWrite<Vo_DESTROY_VALUABLE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_DESTROY_VALUABLE object) {
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeInt(buff, object.getLife());
	}

	@Override
	public int cmd() {
		return 0x8095;
	}

}
