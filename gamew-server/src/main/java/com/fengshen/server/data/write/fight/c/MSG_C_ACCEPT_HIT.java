package com.fengshen.server.data.write.fight.c;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_ACCEPT_HIT;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 手势动作
 * @author aaa
 *
 */
public class MSG_C_ACCEPT_HIT extends BaseWrite<Vo_C_ACCEPT_HIT> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_C_ACCEPT_HIT object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeInt(writeBuf, object.hid);
		GameWriteTool.writeInt(writeBuf, object.para_ex);
		GameWriteTool.writeShort(writeBuf, object.missed);
		GameWriteTool.writeShort(writeBuf, object.para);
		GameWriteTool.writeInt(writeBuf, object.damage_type);
	}

	@Override
	public int cmd() {
		return 19945;
	}
}
