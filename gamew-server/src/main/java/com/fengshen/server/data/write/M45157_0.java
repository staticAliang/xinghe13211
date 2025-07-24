package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45157_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * "MSG_CLEAR_ALL_CHAR", -- 清除所有的玩家
 */
@Service
public class M45157_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_45157_0 object2 = (Vo_45157_0) object;
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeInt(writeBuf, object2.mapId);
	}

	@Override
	public int cmd() {
		return 45157;
	}
}
