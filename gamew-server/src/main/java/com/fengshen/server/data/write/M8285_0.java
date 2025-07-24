package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_8285_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M8285_0 extends BaseWrite<Vo_8285_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_8285_0 object) {
		GameWriteTool.writeString(writeBuf, object.gid);
		GameWriteTool.writeString(writeBuf, object.name);
	}

	@Override
	public int cmd() {
		return 8285;
	}
}
