package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M12269_0 extends BaseWrite<Vo_12269_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_12269_0 object) {
		GameWriteTool.writeInt(writeBuf, object.id);
		GameWriteTool.writeInt(writeBuf, object.owner_id);
	}

	@Override
	public int cmd() {
		return 12269;
	}
}
