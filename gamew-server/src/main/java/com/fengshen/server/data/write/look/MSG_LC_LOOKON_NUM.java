package com.fengshen.server.data.write.look;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_LC_LOOKON_NUM extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeShort(buff, object);
	}

	@Override
	public int cmd() {
		return 0x29C5;
	}

}