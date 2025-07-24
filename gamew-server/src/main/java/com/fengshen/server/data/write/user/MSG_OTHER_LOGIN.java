package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.user.Vo_OTHER_LOGIN;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 其他登录
 * 
 *
 */
public class MSG_OTHER_LOGIN extends BaseWrite<Vo_OTHER_LOGIN> {

	@Override
	protected void writeO(ByteBuf buff, Vo_OTHER_LOGIN object) {
		GameWriteTool.writeShort(buff, object.getResult());
		GameWriteTool.writeShort(buff, object.getCode());
		GameWriteTool.writeString(buff, object.getMsg());
	}

	@Override
	public int cmd() {
		return 0xB054;
	}

}
