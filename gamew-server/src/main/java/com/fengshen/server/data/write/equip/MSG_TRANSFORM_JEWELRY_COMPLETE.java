package com.fengshen.server.data.write.equip;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 首饰转换
 * 
 *
 */
public class MSG_TRANSFORM_JEWELRY_COMPLETE extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		GameWriteTool.writeShort(buff, object);
	}

	@Override
	public int cmd() {
		return 0xB1D2;
	}

}
