package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.ZbAttribute;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M64991_0 extends BaseWrite<ZbAttribute> {
	@Override
	protected void writeO(final ByteBuf writeBuf, ZbAttribute object) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (object != null) {
			map = UtilObjMapshuxing.ZbAttribute(object);
			map.remove("id");
			GameWriteTool.writeInt(writeBuf, object.id);
			GameWriteTool.writeShort(writeBuf, map.size());
			for (final Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 64991;
	}
}
