package com.fengshen.server.data.write.fixedteam;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_APPELLATION;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FIXED_TEAM_APPELLATION extends BaseWrite<Vo_FIXED_TEAM_APPELLATION> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FIXED_TEAM_APPELLATION object) {
		
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeString(buff, object.getTeamName());
		GameWriteTool.writeInt(buff, object.getCostGold());
	}

	@Override
	public int cmd() {
		return 0xD1FB;
	}

}