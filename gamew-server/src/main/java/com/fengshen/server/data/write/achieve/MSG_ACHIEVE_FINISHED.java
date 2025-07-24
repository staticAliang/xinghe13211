package com.fengshen.server.data.write.achieve;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通知客户端完成成就
 * 
 *
 */
public class MSG_ACHIEVE_FINISHED extends BaseWrite<Object[]> {

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		GameWriteTool.writeInt(buff, (Integer) object[0]);
		GameWriteTool.writeString(buff, (String) object[1]);
	}

	@Override
	public int cmd() {
		return 0x80BB;
	}

}
