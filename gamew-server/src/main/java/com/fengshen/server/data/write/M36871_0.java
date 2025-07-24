package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_36871_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M36871_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_36871_0 object2 = (Vo_36871_0) object;
		GameWriteTool.writeString(writeBuf, object2.msg_type);
		GameWriteTool.writeInt(writeBuf, object2.icon);
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeShort(writeBuf, object2.level);
		GameWriteTool.writeString(writeBuf, object2.gid);
		GameWriteTool.writeString(writeBuf, object2.name);
		GameWriteTool.writeString(writeBuf, object2.party);
		GameWriteTool.writeInt(writeBuf, object2.friend_score);
		GameWriteTool.writeInt(writeBuf, object2.setting_flag);
		GameWriteTool.writeShort(writeBuf, object2.char_status);
		GameWriteTool.writeByte(writeBuf, object2.vip);
		GameWriteTool.writeString(writeBuf, object2.serverId);
		GameWriteTool.writeString(writeBuf, object2.account);
		GameWriteTool.writeByte(writeBuf, object2.polar);
		GameWriteTool.writeByte(writeBuf, object2.isInThereFrend);
		GameWriteTool.writeInt(writeBuf, object2.ringScore);
		GameWriteTool.writeByte(writeBuf, object2.comeback_flag);
	}

	@Override
	public int cmd() {
		return 0x9007;
	}
}
