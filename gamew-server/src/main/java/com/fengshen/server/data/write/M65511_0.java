package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.ZbAttribute;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M65511_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final ZbAttribute object2 = (ZbAttribute) object;
		Map<Object, Object> map = new HashMap<Object, Object>();
		if (object2 != null) {
			map = UtilObjMapshuxing.ZbAttribute(object2);
			final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			map.remove("id");
			GameWriteTool.writeInt(writeBuf, object2.id);
			GameWriteTool.writeShort(writeBuf, map.size());
			for (final Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
				} else {
					System.out.println(
							"M65511_0 中获取角色的装备属性 BuildFields.data.get(entry.getKey()) == null " + entry.getKey());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 65511;
	}
}
