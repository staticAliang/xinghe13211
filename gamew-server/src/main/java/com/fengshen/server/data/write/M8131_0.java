package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_8131_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M8131_0 extends BaseWrite<Vo_8131_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_8131_0 object) {
		GameWriteTool.writeString2(writeBuf, object.buf);
		GameWriteTool.writeInt(writeBuf, object.cookie);
	}

	@Override
	public int cmd() {
		return 8131;
	}
}
