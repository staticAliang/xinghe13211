package com.fengshen.server.data.write.wdrd;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class MSG_WD_RB_REMOVE_REDBAG extends BaseWrite<String> {

	@Override
	protected void writeO(ByteBuf buff, String object) {
		GameWriteTool.writeString(buff, object);
	}

	@Override
	public int cmd() {
		return 0x82C5;
	}

}
