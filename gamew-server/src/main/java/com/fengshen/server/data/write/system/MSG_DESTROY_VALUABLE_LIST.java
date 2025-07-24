package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_DESTROY_VALUABLE_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_DESTROY_VALUABLE_LIST extends BaseWrite<Vo_DESTROY_VALUABLE_LIST> {

	@Override
	protected void writeO(ByteBuf buff, Vo_DESTROY_VALUABLE_LIST object) {
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeString(buff, object.getId_str());
	}

	@Override
	public int cmd() {
		return 0x8093;
	}

}
