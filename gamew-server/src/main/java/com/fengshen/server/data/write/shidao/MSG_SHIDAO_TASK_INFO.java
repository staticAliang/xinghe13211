package com.fengshen.server.data.write.shidao;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_TASK_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SHIDAO_TASK_INFO extends BaseWrite<Vo_SHIDAO_TASK_INFO> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_SHIDAO_TASK_INFO object) {
		GameWriteTool.writeByte(writeBuf, object.isPK);
		GameWriteTool.writeByte(writeBuf, object.stageId);
		GameWriteTool.writeShort(writeBuf, object.monsterPoint);
		GameWriteTool.writeShort(writeBuf, object.pkValue);
		GameWriteTool.writeShort(writeBuf, object.totalScore);
		GameWriteTool.writeInt(writeBuf, object.startTime);
		GameWriteTool.writeInt(writeBuf, object.stage1_duration_time);
		GameWriteTool.writeInt(writeBuf, object.stage2_duration_time);
		GameWriteTool.writeByte(writeBuf, object.rank);
		GameWriteTool.writeByte(writeBuf, 1);
	}

	@Override
	public int cmd() {
		return 49177;
	}
}
