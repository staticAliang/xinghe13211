package com.fengshen.server.data.write.party;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_VIEW_MEMBER_DESC;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PARTY_VIEW_MEMBER_DESC extends BaseWrite<Vo_PARTY_VIEW_MEMBER_DESC> {

	@Override
	protected void writeO(ByteBuf buff, Vo_PARTY_VIEW_MEMBER_DESC object) {
		
		GameWriteTool.writeString(buff, object.partyId);
		GameWriteTool.writeString(buff, object.memberGid);
		GameWriteTool.writeString(buff, object.desc);
	}

	@Override
	public int cmd() {
		return 0x8293;
	}

}
