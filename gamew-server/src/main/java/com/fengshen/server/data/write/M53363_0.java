package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M53363_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		GameWriteTool.writeShort(writeBuf, 2);
		GameWriteTool.writeString(writeBuf, "这是文本");
		GameWriteTool.writeString(writeBuf, "这是文本2");
	}

	@Override
	public int cmd() {
		return 53363;
	}
}