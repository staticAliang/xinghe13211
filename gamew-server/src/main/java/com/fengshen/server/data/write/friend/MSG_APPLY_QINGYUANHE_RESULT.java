package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a使用情缘盒成功
 * 
 *
 */
public class MSG_APPLY_QINGYUANHE_RESULT extends BaseWrite<Integer>{

	@Override
	protected void writeO(ByteBuf buff, Integer result) {
		GameWriteTool.writeByte(buff, result);
	}

	@Override
	public int cmd() {
		return 0xB06B;
	}

}
