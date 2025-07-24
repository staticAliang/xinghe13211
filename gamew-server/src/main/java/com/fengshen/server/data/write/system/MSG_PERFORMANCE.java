package com.fengshen.server.data.write.system;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 性能统计
 * @author aaa
 *
 */
public class MSG_PERFORMANCE extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
	}

	@Override
	public int cmd() {
		return 0x8099;
	}

}
