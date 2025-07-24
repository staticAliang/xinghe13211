package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M8405 extends BaseWrite {

	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final String charaName = (String) object;

		GameWriteTool.writeShort(writeBuf, 1);
        GameWriteTool.writeString(writeBuf, "192.168.0.88"+ ",14721,3333111,1112222," + charaName);
//		GameWriteTool.writeString(writeBuf, "抱歉暂不支持换线");

	}

	@Override
	public int cmd() {
		return 8405;
	}
}
