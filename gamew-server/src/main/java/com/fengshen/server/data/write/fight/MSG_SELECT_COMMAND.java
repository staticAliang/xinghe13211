package com.fengshen.server.data.write.fight;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_SELECT_COMMAND;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_SELECT_COMMAND extends BaseWrite<Vo_SELECT_COMMAND> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_SELECT_COMMAND object) {
		GameWriteTool.writeInt(writeBuf, object.attacker_id);
		GameWriteTool.writeInt(writeBuf, object.victim_id);
		GameWriteTool.writeByte(writeBuf, object.action);
		GameWriteTool.writeInt(writeBuf, object.no);
	}

	@Override
	public int cmd() {
		return 53715;
	}
}
