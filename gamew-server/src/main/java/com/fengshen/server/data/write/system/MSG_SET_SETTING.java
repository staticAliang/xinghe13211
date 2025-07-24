package com.fengshen.server.data.write.system;

import java.util.Map;
import java.util.Map.Entry;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 系统设置消息
 * 
 *
 */
public class MSG_SET_SETTING extends BaseWrite<Map<String,Integer>>{

	@Override
	protected void writeO(ByteBuf buff, Map<String, Integer> object) {
		GameWriteTool.writeShort(buff, object.size());
		for(Entry<String, Integer> data:object.entrySet()) {
			GameWriteTool.writeString(buff, data.getKey());
			GameWriteTool.writeShort(buff, data.getValue());
		}
	}

	@Override
	public int cmd() {
		return 0xF095;
	}

}
