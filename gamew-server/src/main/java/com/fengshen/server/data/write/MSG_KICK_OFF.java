package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通知客户端本次断开连接不需要重新连接
 *
 */
@Service
public class MSG_KICK_OFF extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf writeBuf, String object) {
		
		GameWriteTool.writeString(writeBuf, object);
	}

	@Override
	public int cmd() {
		return 0xD09D;
	}

}
