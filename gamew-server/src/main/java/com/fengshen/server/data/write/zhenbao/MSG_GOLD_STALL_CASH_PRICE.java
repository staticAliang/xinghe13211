package com.fengshen.server.data.write.zhenbao;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_GOLD_STALL_CASH_PRICE extends BaseWrite<Map<String,Object>> {

	@Override
	protected void writeO(ByteBuf buff, Map<String, Object> object) {
		GameWriteTool.writeInt(buff, MapUtils.getIntValue(object, "name"));
		GameWriteTool.writeString(buff, MapUtils.getString(object, "class_str"));
	}

	@Override
	public int cmd() {
		return 0x8121;
	}

}
