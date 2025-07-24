package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 元血婴升级
 * 
 *
 */
public class MSG_UPGRADE_LEVEL_UP extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		GameWriteTool.writeInt(buff, (Integer) object[0]);
		GameWriteTool.writeShort(buff, (Integer) object[1]);
	}

	@Override
	public int cmd() {
		return 0x5052;
	}

}
