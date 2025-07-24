package com.fengshen.server.data.write.party;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.party.Vo_PARTY_QUERY_MEMBER;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PARTY_QUERY_MEMBER extends BaseWrite<Vo_PARTY_QUERY_MEMBER>{

	@Override
	protected void writeO(ByteBuf buff, Vo_PARTY_QUERY_MEMBER object) {
		
		GameWriteTool.writeString(buff, object.gid);
		GameWriteTool.writeString(buff, object.name);
		GameWriteTool.writeShort(buff, object.icon);
		GameWriteTool.writeShort(buff, object.level);
		GameWriteTool.writeString(buff, object.title);
		GameWriteTool.writeInt(buff, object.reputation);
		GameWriteTool.writeShort(buff, object.rights);
		GameWriteTool.writeString(buff, object.job);
		GameWriteTool.writeShort(buff, object.gender);
		GameWriteTool.writeInt(buff, object.contrib);
		GameWriteTool.writeInt(buff, object.joinTime);
		GameWriteTool.writeInt(buff, object.logoutTime);
		GameWriteTool.writeByte(buff, object.online);
		GameWriteTool.writeByte(buff, object.inTeam);
		GameWriteTool.writeString(buff, object.family);
		GameWriteTool.writeByte(buff, object.polar);
		GameWriteTool.writeString(buff, object.newJob);
		GameWriteTool.writeByte(buff, object.vipType);
		GameWriteTool.writeString(buff, object.serverId);
	}

	@Override
	public int cmd() {
		return 0xF0A5;
	}

}
