package com.fengshen.server.data.write.fight.lc;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT;
import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_LC_ACCEPT_MAGIC_HIT extends BaseWrite<Vo_ACCEPT_MAGIC_HIT> {

	protected void writeO(ByteBuf buff, Vo_ACCEPT_MAGIC_HIT object) {
		GameWriteTool.writeInt(buff, object.hid);
		GameWriteTool.writeInt(buff, object.damageType);
		GameWriteTool.writeShort(buff, object.infos.size());
		for (int i = 0; i < object.infos.size(); ++i) {
			Info info = object.infos.get(i);
			GameWriteTool.writeInt(buff, info.id);

		}
		for (int i = 0; i < object.infos.size(); ++i) {
			Info info = object.infos.get(i);
			GameWriteTool.writeShort(buff, info.missed);
		}
	}

	@Override
	public int cmd() {
		return 0xF9DB;
	}
}
