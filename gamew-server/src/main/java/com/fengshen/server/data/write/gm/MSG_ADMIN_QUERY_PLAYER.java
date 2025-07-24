package com.fengshen.server.data.write.gm;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.gm.Vo_ADMIN_QUERY_PLAYER;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_ADMIN_QUERY_PLAYER extends BaseWrite<List<Vo_ADMIN_QUERY_PLAYER>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_ADMIN_QUERY_PLAYER> object) {
		
		GameWriteTool.writeInt(buff, object.size());
		for(Vo_ADMIN_QUERY_PLAYER v:object) {
			GameWriteTool.writeString(buff, v.getServer());
			GameWriteTool.writeString(buff, v.getAccount());
			GameWriteTool.writeString(buff, v.getName());
			GameWriteTool.writeString(buff, v.getGid());
			GameWriteTool.writeShort(buff, v.getLevel());
			GameWriteTool.writeShort(buff, v.getPolar());
			GameWriteTool.writeString(buff, v.getMac());
			GameWriteTool.writeString(buff, v.getIp());
		}
	}

	@Override
	public int cmd() {
		return 0xFA07;
	}

}
