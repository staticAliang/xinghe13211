package com.fengshen.server.data.write.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_FIXED_TEAM_CHECK extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeByte(buff, object);
	}

	@Override
	public int cmd() {
		return 0x50FC;
	}

}
