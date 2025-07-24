package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 新手指引
 * 
 *
 */
public class MSG_PLAY_INSTRUCTION extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeShort(buff, object);
	}

	@Override
	public int cmd() {
		return 0xA005;
	}

}
