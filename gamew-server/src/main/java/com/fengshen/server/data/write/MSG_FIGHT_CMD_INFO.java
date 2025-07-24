package com.fengshen.server.data.write;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_FIGHT_CMD_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FIGHT_CMD_INFO extends BaseWrite<List<Vo_FIGHT_CMD_INFO>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Vo_FIGHT_CMD_INFO> object) {
		GameWriteTool.writeByte(writeBuf, object.size());
		for(Vo_FIGHT_CMD_INFO object2:object) {
			GameWriteTool.writeInt(writeBuf, object2.id);
			GameWriteTool.writeByte(writeBuf, object2.auto_select);
			GameWriteTool.writeByte(writeBuf, object2.multi_index);
			GameWriteTool.writeByte(writeBuf, object2.action);
			GameWriteTool.writeInt(writeBuf, object2.para);
			GameWriteTool.writeByte(writeBuf, object2.multi_count);
		}
		
	}

	@Override
	public int cmd() {
		return 36889;
	}
}
