package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45072_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M45072_0 extends BaseWrite<Vo_45072_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_45072_0 object2) {
		GameWriteTool.writeString(writeBuf, object2.new_name);
	}

	@Override
	public int cmd() {
		return 45072;
	}
}
