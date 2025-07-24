package com.fengshen.server.data.write.safelock;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_OPEN_UNLOCK;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SAFE_LOCK_OPEN_UNLOCK extends BaseWrite<Vo_SAFE_LOCK_OPEN_UNLOCK> {

	@Override
	protected void writeO(ByteBuf buff, Vo_SAFE_LOCK_OPEN_UNLOCK object) {
		
		GameWriteTool.writeString(buff, object.getKey());
		GameWriteTool.writeShort(buff, object.getErrorCountMax());
		GameWriteTool.writeShort(buff, object.getErrorCount());
	}

	@Override
	public int cmd() {
		return 0x8041;
	}

}
