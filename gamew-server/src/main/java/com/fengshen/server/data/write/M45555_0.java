package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45555_0;
import com.fengshen.server.netty.BaseWriteNotEnc;

import io.netty.buffer.ByteBuf;

@Service
public class M45555_0 extends BaseWriteNotEnc<Vo_45555_0> {
	@Override
	protected void writeO(ByteBuf writeBuf, Vo_45555_0 object) {
		GameWriteTool.writeString(writeBuf, object.type);
		GameWriteTool.writeString(writeBuf, object.cookie);
	}

	@Override
	public int cmd() {
		return 45555;
	}
}
