package com.fengshen.server.process;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class CommonCmd extends BaseWrite<Map<String, Object>> {

	public int cmd;

	public CommonCmd(int cmd) {
		this.cmd = cmd;
	}

	@Override
	protected void writeO(ByteBuf buff, Map<String, Object> map) {
		if(map != null) {
			for (Map.Entry<String, Object> m : map.entrySet()) {
				if (m.getKey().contains("int") || m.getKey().contains("Integer")) {
					GameWriteTool.writeInt(buff, Integer.valueOf(String.valueOf(m.getValue())));
				}
				if (m.getKey().contains("str") || m.getKey().contains("String")) {
					GameWriteTool.writeString(buff, String.valueOf(m.getValue()));
				}
				if (m.getKey().contains("byte") || m.getKey().contains("Byte")) {
					GameWriteTool.writeByte(buff, MapUtils.getIntValue(map, m.getKey()));
				}
				if (m.getKey().contains("short") || m.getKey().contains("Short")) {
					GameWriteTool.writeShort(buff, Integer.valueOf(String.valueOf(m.getValue())));
				}
				if (m.getKey().contains("long") || m.getKey().contains("long")) {
					GameWriteTool.writeLong(buff, (long)m.getValue());
				}
				if (m.getKey().contains("str2") || m.getKey().contains("String2")) {
					GameWriteTool.writeString2(buff, String.valueOf(m.getValue()));
				}
			}
		}
	}

	@Override
	public int cmd() {
		return cmd;
	}
}
