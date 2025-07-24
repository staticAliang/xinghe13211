package com.fengshen.server.data.write.chat;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.chat.Vo_NPC_CHAT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_NPC_CHAT extends BaseWrite<Vo_NPC_CHAT>{

	@Override
	protected void writeO(ByteBuf buff, Vo_NPC_CHAT object) {
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeString(buff, object.getText());
	}

	@Override
	public int cmd() {
		return 0xD33F;
	}

}
