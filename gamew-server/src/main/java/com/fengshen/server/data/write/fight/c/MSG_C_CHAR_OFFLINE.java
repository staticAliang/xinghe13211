package com.fengshen.server.data.write.fight.c;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.fight.Vo_C_CHAR_OFFLINE;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_C_CHAR_OFFLINE extends BaseWrite<Vo_C_CHAR_OFFLINE>{

	@Override
	protected void writeO(ByteBuf buff, Vo_C_CHAR_OFFLINE object) {
		GameWriteTool.writeInt(buff, object.getId());
		GameWriteTool.writeShort(buff, object.getOffline());
	}

	@Override
	public int cmd() {
		return 0x2DBF;
	}

}