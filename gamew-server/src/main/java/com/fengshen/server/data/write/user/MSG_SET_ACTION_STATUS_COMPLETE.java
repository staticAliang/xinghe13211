package com.fengshen.server.data.write.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_SET_ACTION_STATUS_COMPLETE extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeInt(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB22A;
	}

}
