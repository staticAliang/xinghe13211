package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_64971_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 刷新参战宠物列表
 * 
 *
 */
@Service
public class MSG_C_REFRESH_PET_LIST extends BaseWrite<Vo_64971_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Vo_64971_0 object) {
		GameWriteTool.writeShort(writeBuf, object.count);
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeByte(writeBuf, object.haveCalled);
	}

	@Override
	public int cmd() {
		return 64971;
	}
}
