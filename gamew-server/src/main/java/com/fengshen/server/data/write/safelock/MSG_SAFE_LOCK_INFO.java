package com.fengshen.server.data.write.safelock;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SAFE_LOCK_INFO extends BaseWrite<Vo_SAFE_LOCK_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_SAFE_LOCK_INFO object) {
		
		GameWriteTool.writeByte(buff, object.getHasPwd());
		GameWriteTool.writeByte(buff, object.getIsRelleaseLock());
		GameWriteTool.writeInt(buff, object.getResetStart());
		GameWriteTool.writeInt(buff, object.getResetEnd());
		GameWriteTool.writeInt(buff, object.getResetDays());
	}

	@Override
	public int cmd() {
		return 0x803B;
	}

}
