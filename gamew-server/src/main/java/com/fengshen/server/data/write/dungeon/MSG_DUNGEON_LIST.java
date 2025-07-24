package com.fengshen.server.data.write.dungeon;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.dungeon.Vo_DUNGEON_LIST;
import com.fengshen.server.data.vo.dungeon.Vo_DUNGEON_LIST.DugeonsInfo;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_DUNGEON_LIST extends BaseWrite<Vo_DUNGEON_LIST> {

	@Override
	protected void writeO(ByteBuf buff, Vo_DUNGEON_LIST object) {
		
		GameWriteTool.writeShort(buff, object.getBonus());
		GameWriteTool.writeString(buff, object.getHardName());
		GameWriteTool.writeShort(buff, object.getDugeonsInfo().size());
		for(DugeonsInfo info:object.getDugeonsInfo()) {
			GameWriteTool.writeShort(buff, info.getLevel());
			GameWriteTool.writeString(buff, info.getDungeonName());
		}
	}

	@Override
	public int cmd() {
		return 0xB002;
	}

}
