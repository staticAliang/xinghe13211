package com.fengshen.server.data.write.user;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_AAA_EXISTED_CHAR_LIST extends BaseWrite<Object> {

	@Override
	protected void writeO(ByteBuf buff, Object object) {
		
		GameWriteTool.writeString(buff, "钟山风雨");
		GameWriteTool.writeString(buff, "13701111111");
		GameWriteTool.writeByte(buff, 1);
		GameWriteTool.writeByte(buff, 1);
		
		GameWriteTool.writeString(buff, "AFAE3A64D9E14908BF83F1ED679D18D9");
		GameWriteTool.writeString(buff, "终是离去");
		GameWriteTool.writeString(buff, "13701111111");
	}

	@Override
	public int cmd() {
		return 0x82C7;
	}

}
