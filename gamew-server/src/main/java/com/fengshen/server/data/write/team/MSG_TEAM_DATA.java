package com.fengshen.server.data.write.team;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.team.ListVo_TEAM_DATA;
import com.fengshen.server.data.vo.team.Vo_TEAM_DATA;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 查询队伍信息
 * 
 *
 */
public class MSG_TEAM_DATA extends BaseWrite<Vo_TEAM_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_TEAM_DATA object) {
		
		GameWriteTool.writeByte(buff, object.getIsTeam());
		GameWriteTool.writeByte(buff, object.getList().size());
		for(ListVo_TEAM_DATA o:object.getList()) {
			GameWriteTool.writeString(buff, o.getName());
			GameWriteTool.writeShort(buff, o.getLevel());
			GameWriteTool.writeInt(buff, o.getIcon());
			GameWriteTool.writeInt(buff, o.getId());
			GameWriteTool.writeByte(buff, o.getVip());
			GameWriteTool.writeByte(buff, o.getZanli());
		}
	}

	@Override
	public int cmd() {
		return 0xF105;
	}

}
