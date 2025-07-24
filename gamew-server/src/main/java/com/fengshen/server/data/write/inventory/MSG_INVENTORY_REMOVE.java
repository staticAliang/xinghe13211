package com.fengshen.server.data.write.inventory;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a删除某个物品
 * 
 * @author weilian
 *
 */
public class MSG_INVENTORY_REMOVE extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf writeBuf, Integer pos) {
		GameWriteTool.writeShort(writeBuf, 1);
		GameWriteTool.writeByte(writeBuf, pos);
	}

	@Override
	public int cmd() {
		return 65525;
	}

}
