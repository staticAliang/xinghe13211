package com.fengshen.server.data.write.task;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 停止自动寻路
 * 
 *
 */
public class MSG_STOP_AUTO_WALK extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB067;
	}

}
