package com.fengshen.server.data.write.tongtianta;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.tongtianta.Vo_TONGTIANTA_BONUS_DLG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_TONGTIANTA_BONUS_DLG extends BaseWrite<Vo_TONGTIANTA_BONUS_DLG>{

	@Override
	protected void writeO(ByteBuf buff, Vo_TONGTIANTA_BONUS_DLG object) {
		GameWriteTool.writeString(buff, object.getBonusType());
		GameWriteTool.writeByte(buff, object.getDlgType());
		GameWriteTool.writeInt(buff, object.getBonusValue());
		GameWriteTool.writeInt(buff, object.getBonusTaoPoint());
	}

	@Override
	public int cmd() {
		return 0xC005;
	}

	
}
