package com.fengshen.server.data.write.account;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.account.Vo_L_ACCOUNT_CHARS;
import com.fengshen.server.data.vo.account.Vo_L_ACCOUNT_CHARS.Role;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_L_ACCOUNT_CHARS extends BaseWrite<Vo_L_ACCOUNT_CHARS> {

	@Override
	protected void writeO(ByteBuf buff, Vo_L_ACCOUNT_CHARS object) {
		
		GameWriteTool.writeString(buff, object.getDistName());
		GameWriteTool.writeByte(buff, object.getRoleList().size());
		for(Role role:object.getRoleList()) {
			GameWriteTool.writeString(buff, role.getName());
			GameWriteTool.writeShort(buff, role.getIcon());
			GameWriteTool.writeShort(buff, role.getLevel());
			GameWriteTool.writeInt(buff, role.getDeleteTime());
		}
	}

	@Override
	public int cmd() {
		return 0xB035;
	}

}
