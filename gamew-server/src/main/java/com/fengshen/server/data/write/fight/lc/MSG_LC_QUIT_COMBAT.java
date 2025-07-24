package com.fengshen.server.data.write.fight.lc;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 敌方退出战斗
 * 
 *
 */
public class MSG_LC_QUIT_COMBAT extends BaseWrite<Integer> {

	@Override
	protected void writeO(ByteBuf buff, Integer object) {
		
		GameWriteTool.writeInt(buff, object);
	}

	@Override
	public int cmd() {
		return 0x19DF;
	}

}
