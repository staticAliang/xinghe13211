package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_SET_CUSTOM_MSG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_C_SET_CUSTOM_MSG extends BaseWrite<Vo_C_SET_CUSTOM_MSG>{

	@Override
	protected void writeO(ByteBuf buff, Vo_C_SET_CUSTOM_MSG object) {
		
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeShort(buff, object.getChannel());
		GameWriteTool.writeString(buff, object.getServerName());
		GameWriteTool.writeString(buff, object.getMsg());
		GameWriteTool.writeByte(buff, object.getShowTime());
		GameWriteTool.writeByte(buff, object.getVipType());
	}

	@Override
	public int cmd() {
		return 0xFDB3;
	}

}
