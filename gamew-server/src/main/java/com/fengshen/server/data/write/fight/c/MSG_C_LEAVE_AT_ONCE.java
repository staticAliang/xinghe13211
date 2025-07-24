package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 马上离开战斗
 * 
 *
 */
public class MSG_C_LEAVE_AT_ONCE extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeInt(buff, object);
	}

	@Override
	public int cmd() {
		return 0x1DDB;
	}

}
