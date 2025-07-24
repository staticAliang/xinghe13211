package com.fengshen.server.data.write;

import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.Vo_20467_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M20467_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_20467_0 object2 = (Vo_20467_0) object;
		GameWriteTool.writeString(writeBuf, object2.caption);
		GameWriteTool.writeString(writeBuf, object2.content);
		GameWriteTool.writeString(writeBuf, object2.peer_name);
		GameWriteTool.writeString(writeBuf, object2.ask_type);
		GameWriteTool.writeShort(writeBuf, 1);
		GameWriteTool.writeInt(writeBuf, object2.org_icon);
		final Map<Object, Object> map = UtilObjMap.Vo_20467_0(object2);
		map.remove("caption");
		map.remove("content");
		map.remove("org_icon");
		map.remove("peer_name");
		map.remove("ask_type");
		map.remove("teamMembersCount");
		map.remove("comeback_flag");
		final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			final Map.Entry<Object, Object> entry = it.next();
			if (entry.getValue() instanceof Integer) {
				if (!entry.getValue().equals(0)) {
					continue;
				}
				it.remove();
			} else {
				if (entry.getValue() != null) {
					continue;
				}
				it.remove();
			}
		}
		GameWriteTool.writeShort(writeBuf, map.size());
		for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
			if (BuildFields.data.get(entry2.getKey()) != null) {
				BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
			} else {
				System.out.println(entry2.getKey());
			}
		}
		GameWriteTool.writeByte(writeBuf, object2.teamMembersCount);
		GameWriteTool.writeByte(writeBuf, object2.comeback_flag);
	}

	@Override
	public int cmd() {
		return 20467;
	}
}
