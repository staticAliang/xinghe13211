package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_REQUEST_GIVING;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_REQUEST_GIVING extends BaseWrite<Vo_REQUEST_GIVING>{

	@Override
	protected void writeO(ByteBuf buff, Vo_REQUEST_GIVING object) {
		
		GameWriteTool.writeString(buff, object.getGiverName());
		GameWriteTool.writeShort(buff, object.getGiverLevel());
		GameWriteTool.writeInt(buff, object.getGiverIcon());
		
		
		GameWriteTool.writeString(buff, object.getReceiveName());
		GameWriteTool.writeShort(buff, object.getReceiveLevel());
		GameWriteTool.writeInt(buff, object.getReceiveIcon());
	}

	@Override
	public int cmd() {
		return 0xD083;
	}

}
