package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_36871_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 玩家名片数据
 * 
 *
 */
public class MSG_CHAR_INFO_EX extends BaseWrite<Vo_36871_0> {

	@Override
	protected void writeO(ByteBuf buff, Vo_36871_0 object2) {
		GameWriteTool.writeString(buff, object2.msg_type);
		GameWriteTool.writeInt(buff, object2.icon);
		GameWriteTool.writeInt(buff, object2.id);
		GameWriteTool.writeShort(buff, object2.level);
		GameWriteTool.writeString(buff, object2.gid);
		GameWriteTool.writeString(buff, object2.name);
		GameWriteTool.writeString(buff, object2.party);
		GameWriteTool.writeInt(buff, object2.friend_score);
		GameWriteTool.writeInt(buff, object2.setting_flag);
		GameWriteTool.writeShort(buff, object2.char_status);
		GameWriteTool.writeByte(buff, object2.vip);
		GameWriteTool.writeString(buff, object2.serverId);
		GameWriteTool.writeString(buff, object2.account);
		GameWriteTool.writeByte(buff, object2.polar);
		GameWriteTool.writeByte(buff, object2.isInThereFrend);
		GameWriteTool.writeInt(buff, object2.ringScore);
		GameWriteTool.writeByte(buff, object2.comeback_flag);
	}

	@Override
	public int cmd() {
		return 0x5164;
	}

}
