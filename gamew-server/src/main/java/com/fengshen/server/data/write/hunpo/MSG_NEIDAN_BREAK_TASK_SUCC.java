package com.fengshen.server.data.write.hunpo;

import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 完成突破任务成功
 * @author weilian
 *
 */
public class MSG_NEIDAN_BREAK_TASK_SUCC extends BaseWrite<Object>{

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
	}

	@Override
	public int cmd() {
		return 0xB1840;
	}

}
