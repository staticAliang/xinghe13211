package com.fengshen.server.data.write.fireworks;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fireworks.Vo_FIREWORKS_PARTY_BARRAGE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FIREWORKS_PARTY_BARRAGE extends BaseWrite<Vo_FIREWORKS_PARTY_BARRAGE> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FIREWORKS_PARTY_BARRAGE object) {
		
		GameWriteTool.writeString(buff, object.getGid());
		GameWriteTool.writeString(buff, object.getMsg());
		GameWriteTool.writeByte(buff, object.getType());
	}

	@Override
	public int cmd() {
		return 0xD361;
	}

}
