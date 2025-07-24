package com.fengshen.server.data.write.fixedteam;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA.Member;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_FINISH_DATA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FIXED_TEAM_FINISH_DATA extends BaseWrite<Vo_FIXED_TEAM_FINISH_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FIXED_TEAM_FINISH_DATA object) {
		
		GameWriteTool.writeString(buff, object.getTeamName());
		GameWriteTool.writeByte(buff, object.getMembers().size());
		for(Member m:object.getMembers()) {
			GameWriteTool.writeString(buff, m.getGid());
			GameWriteTool.writeString(buff, m.getName());
			GameWriteTool.writeInt(buff, m.getIcon());
		}
	}

	@Override
	public int cmd() {
		return 0xD1FF;
	}

}
