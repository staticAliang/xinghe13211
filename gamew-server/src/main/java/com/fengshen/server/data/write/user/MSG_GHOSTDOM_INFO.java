package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 地府系统
 * 
 *
 */
public class MSG_GHOSTDOM_INFO extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		
		GameWriteTool.writeByte(buff, (Integer) object[0]);
		GameWriteTool.writeByte(buff, (Integer) object[1]);
		GameWriteTool.writeByte(buff, (Integer) object[2]);
	}

	@Override
	public int cmd() {
		return 0xB2E5;
	}

}
