package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_CHANGE_POLAR_DATA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_CHANGE_POLAR_DATA extends BaseWrite<Vo_CHANGE_POLAR_DATA>{

	@Override
	protected void writeO(ByteBuf buff, Vo_CHANGE_POLAR_DATA object) {
		
		GameWriteTool.writeInt(buff, object.getTaskEndTime());
		GameWriteTool.writeByte(buff, object.getRawPolar());
		GameWriteTool.writeByte(buff, object.getNewPolar());
		GameWriteTool.writeByte(buff, object.getHasChange());
		GameWriteTool.writeByte(buff, object.getHasReturn());
	}

	@Override
	public int cmd() {
		return 0x5291;
	}

}
