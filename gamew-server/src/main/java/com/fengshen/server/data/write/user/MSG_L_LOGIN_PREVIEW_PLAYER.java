package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.account.Vo_L_LOGIN_PREVIEW_PLAYER;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_L_LOGIN_PREVIEW_PLAYER extends BaseWrite<Vo_L_LOGIN_PREVIEW_PLAYER> {

	@Override
	protected void writeO(ByteBuf buff, Vo_L_LOGIN_PREVIEW_PLAYER object) {
		GameWriteTool.writeString(buff, object.getAccount());
		GameWriteTool.writeString(buff, object.getGid());
		GameWriteTool.writeInt(buff, object.getTime());
		GameWriteTool.writeString(buff, object.getCookie());
		GameWriteTool.writeString(buff, object.getServerName());
		GameWriteTool.writeString(buff, object.getIp());
		GameWriteTool.writeShort(buff, object.getPort());
	}

	@Override
	public int cmd() {
		return 0x5E31;
	}

}
