package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.party.VO_PARTY_ICON;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SEND_ICON extends BaseWrite<VO_PARTY_ICON>{

	@Override
	protected void writeO(ByteBuf buff, VO_PARTY_ICON object) {
		
		GameWriteTool.writeString(buff, object.getMd5Value());
		if(object.getBuffData() != null) {
			GameWriteTool.writeLenBuffer2(buff, object.getBuffData());
		}
	}

	@Override
	public int cmd() {
		return 0x5038;
	}

}
