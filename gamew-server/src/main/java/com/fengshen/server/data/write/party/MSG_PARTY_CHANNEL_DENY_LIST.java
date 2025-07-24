package com.fengshen.server.data.write.party;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
/**
 * 帮派频道黑名单
 * 
 *
 */
public class MSG_PARTY_CHANNEL_DENY_LIST extends BaseWrite<Object>{

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		GameWriteTool.writeByte(buff, 0);
		GameWriteTool.writeByte(buff, 0);
	}

	@Override
	public int cmd() {
		return 0x2E39;
	}

}
