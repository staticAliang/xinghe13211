package com.fengshen.server.data.write.safelock;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_OPEN_BAN;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SAFE_LOCK_OPEN_BAN extends BaseWrite<Vo_SAFE_LOCK_OPEN_BAN> {

	@Override
	protected void writeO(ByteBuf buff, Vo_SAFE_LOCK_OPEN_BAN object) {
		GameWriteTool.writeInt(buff, object.getBanTime());
		GameWriteTool.writeShort(buff, object.getErrorCountMax());
		GameWriteTool.writeShort(buff, object.getErrorCount());
	}

	@Override
	public int cmd() {
		return 0x8043;
	}

}
