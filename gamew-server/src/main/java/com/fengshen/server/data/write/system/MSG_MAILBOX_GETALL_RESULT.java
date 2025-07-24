package com.fengshen.server.data.write.system;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_MAILBOX_GETALL_RESULT extends BaseWrite<Map<String,Object>> {

	@Override
	protected void writeO(ByteBuf buff, Map<String, Object> object) {
		GameWriteTool.writeString(buff, MapUtils.getString(object, "id"));
		GameWriteTool.writeByte(buff, MapUtils.getIntValue(object, "result"));
	}

	@Override
	public int cmd() {
		return 0xB37A;
	}


}
