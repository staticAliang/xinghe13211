package com.fengshen.server.data.write.fight.c;

import java.util.List;

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
public class MSG_C_REFRESH_PET_LIST2 extends BaseWrite<List<Vo_64971_0>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, final List<Vo_64971_0> object) {
		GameWriteTool.writeShort(writeBuf, object.size());
		for(Vo_64971_0 v:object) {
			GameWriteTool.writeInt(writeBuf, v.id);
			GameWriteTool.writeByte(writeBuf, v.haveCalled);
		}
		
	}

	@Override
	public int cmd() {
		return 64971;
	}
}
