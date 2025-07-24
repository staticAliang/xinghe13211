package com.fengshen.server.data.write;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 通用类
 *
 */
public class CommonWrite extends BaseWrite<Object> {

	public int cmd;
	
	public CommonWrite(int cmd) {
		super();
		this.cmd = cmd;
	}
	@Override
	protected void writeO(ByteBuf buff, Object object) {
		if (object instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) object;
			for (Map.Entry<String, Object> m : map.entrySet()) {
				if (m.getValue() instanceof Integer) {
					GameWriteTool.writeInt(buff, (int)m.getValue());
				}
				if (m.getValue() instanceof String) {
					GameWriteTool.writeString2(buff, String.valueOf(m.getValue()));
				}
				if (m.getValue() instanceof Byte) {
					GameWriteTool.writeByte(buff, MapUtils.getIntValue(map, m.getKey()));
				}
				if (m.getValue() instanceof Short) {
					GameWriteTool.writeShort(buff, Integer.valueOf(String.valueOf(m.getValue())));
				}
				if (m.getValue() instanceof Long) {
					GameWriteTool.writeLong(buff, (long)m.getValue());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return cmd;
	}

}
