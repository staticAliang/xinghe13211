package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 角色门派转换成功
 * @author aaa
 *
 */
public class MSG_CHANGE_POLAR_SUCC extends BaseWrite<Integer[]> {

	@Override
	protected void writeO(ByteBuf buff, Integer[] object) {
		
		GameWriteTool.writeByte(buff, object[0]);
		GameWriteTool.writeByte(buff, object[1]);
	}

	@Override
	public int cmd() {
		return 0x5293;
	}

}
