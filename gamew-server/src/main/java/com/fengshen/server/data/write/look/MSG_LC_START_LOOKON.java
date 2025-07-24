package com.fengshen.server.data.write.look;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_LC_START_LOOKON extends BaseWrite<Integer[]> {

	@Override
	protected void writeO(ByteBuf buff, Integer[] object) {
		//isBroadcast
		GameWriteTool.writeByte(buff, object[0]);
		//mode
		GameWriteTool.writeByte(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0x09FF;
	}

}
