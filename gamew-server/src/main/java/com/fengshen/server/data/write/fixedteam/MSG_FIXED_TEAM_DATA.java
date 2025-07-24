package com.fengshen.server.data.write.fixedteam;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_DATA;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_DATA.Member;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FIXED_TEAM_DATA extends BaseWrite<Vo_FIXED_TEAM_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FIXED_TEAM_DATA object) {
		
		GameWriteTool.writeString(buff, object.getName());
		GameWriteTool.writeByte(buff, object.getLevel());
		GameWriteTool.writeInt(buff, object.getIntimacy());
		GameWriteTool.writeInt(buff, object.getMaxIntimacy());
		GameWriteTool.writeShort(buff, object.getMembers().size());
		for(Member m:object.getMembers()) {
			GameWriteTool.writeString(buff, m.getGid());
			GameWriteTool.writeString(buff, m.getName());
			GameWriteTool.writeShort(buff, m.getLevel());
			GameWriteTool.writeInt(buff, m.getIcon());
			GameWriteTool.writeInt(buff, m.getTao());
			GameWriteTool.writeInt(buff, m.getLastLogoutTime());
			GameWriteTool.writeInt(buff, m.getJoinTime());
		}
	}

	@Override
	public int cmd() {
		return 0xD203;
	}

}
