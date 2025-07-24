package com.fengshen.server.data.write.tongtianta;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.tongtianta.Vo_TONGTIANTA_JUMP;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通天塔飞升确认框
 * 
 *
 */
public class MSG_TONGTIANTA_JUMP extends BaseWrite<Vo_TONGTIANTA_JUMP> {

	@Override
	protected void writeO(ByteBuf buff, Vo_TONGTIANTA_JUMP object) {
		GameWriteTool.writeByte(buff, object.getCostType());
		GameWriteTool.writeInt(buff, object.getCostCount());
		GameWriteTool.writeInt(buff, object.getJumpCount());
	}

	@Override
	public int cmd() {
		return 0xB022;
	}

}
