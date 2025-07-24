package com.fengshen.server.data.write.friend;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_AUTO_FIGHT_CONFIG;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FRIEND_AUTO_FIGHT_CONFIG extends BaseWrite<List<Vo_FRIEND_AUTO_FIGHT_CONFIG>>{

	@Override
	protected void writeO(ByteBuf buff, List<Vo_FRIEND_AUTO_FIGHT_CONFIG> object) {
		
		GameWriteTool.writeShort(buff, object.size());
		for(Vo_FRIEND_AUTO_FIGHT_CONFIG obj:object) {
			GameWriteTool.writeInt(buff, obj.getId());
			GameWriteTool.writeByte(buff, obj.getAuto_fight());
		}
	}

	@Override
	public int cmd() {
		return 0xD1DD;
	}

}
