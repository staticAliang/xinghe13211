package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45124_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M45124_0 extends BaseWrite {
	@Override
	protected void writeO(ByteBuf writeBuf, Object object) {
		Vo_45124_0 object2 = (Vo_45124_0) object;
		GameWriteTool.writeString(writeBuf, object2.reason);
	}

	@Override
	public int cmd() {
		return 45124;
	}
}
