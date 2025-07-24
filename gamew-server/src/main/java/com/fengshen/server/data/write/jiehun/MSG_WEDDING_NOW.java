package com.fengshen.server.data.write.jiehun;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a开始结婚流程
 * 
 *
 */
public class MSG_WEDDING_NOW extends BaseWrite<Integer[]> {

	@Override
	protected void writeO(ByteBuf buff, Integer[] object) {
		GameWriteTool.writeByte(buff, object[0]);
		GameWriteTool.writeByte(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0xB06F;
	}

}
