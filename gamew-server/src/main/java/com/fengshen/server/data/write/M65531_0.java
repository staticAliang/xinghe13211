package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.NpcPoint;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * MSG_EXITS
 */
@Service
public class M65531_0 extends BaseWrite<List<NpcPoint>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<NpcPoint> list) {
		GameWriteTool.writeByte(writeBuf, 1);
		GameWriteTool.writeShort(writeBuf, list.size());
		for (NpcPoint npcPoint : list) {
			GameWriteTool.writeString(writeBuf, npcPoint.getDoorname());
			GameWriteTool.writeShort(writeBuf, npcPoint.getX());
			GameWriteTool.writeShort(writeBuf, npcPoint.getY());
			GameWriteTool.writeShort(writeBuf, npcPoint.getZ());
			GameWriteTool.writeByte(writeBuf, 0);
			GameWriteTool.writeByte(writeBuf, 0);
		}
	}

	@Override
	public int cmd() {
		return 65531;
	}
}
