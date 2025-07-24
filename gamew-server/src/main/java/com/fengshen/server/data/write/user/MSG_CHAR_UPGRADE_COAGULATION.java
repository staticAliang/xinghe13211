package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 角色凝结元婴
 * 
 *
 */
public class MSG_CHAR_UPGRADE_COAGULATION extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeByte(buff, object);
	}

	@Override
	public int cmd() {
		return 0xA08E;
	}

}
