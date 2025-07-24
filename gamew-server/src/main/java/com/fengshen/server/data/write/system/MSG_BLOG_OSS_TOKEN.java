package com.fengshen.server.data.write.system;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.system.Vo_BLOG_OSS_TOKEN;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_BLOG_OSS_TOKEN extends BaseWrite<Vo_BLOG_OSS_TOKEN> {

	@Override
	protected void writeO(ByteBuf buff, Vo_BLOG_OSS_TOKEN object) {
		
		GameWriteTool.writeByte(buff, object.getFlag());
		GameWriteTool.writeString2(buff, object.getRet());
	}

	@Override
	public int cmd() {
		return 0x80D5;
	}

}
