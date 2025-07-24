package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M16383_0 extends BaseWrite {
	@Override
	protected void writeO(ByteBuf writeBuf, Object object) {
		Vo_16383_0 object2 = (Vo_16383_0) object;
		GameWriteTool.writeShort(writeBuf, object2.channel);
		GameWriteTool.writeInt(writeBuf, object2.id);
		GameWriteTool.writeString(writeBuf, object2.name);
		GameWriteTool.writeString2(writeBuf, object2.msg);
		GameWriteTool.writeInt(writeBuf, object2.time);
		GameWriteTool.writeShort(writeBuf, object2.privilege);
		GameWriteTool.writeString(writeBuf, object2.server_name);
		GameWriteTool.writeShort(writeBuf, object2.show_extra);
		GameWriteTool.writeShort(writeBuf, object2.compress);
		GameWriteTool.writeShort(writeBuf, object2.orgLength);
		GameWriteTool.writeShort(writeBuf, object2.cardCount);
		if (object2.cardCount != 0) {
			GameWriteTool.writeString(writeBuf, object2.cardId);
		}
		GameWriteTool.writeInt(writeBuf, object2.voiceTime);
		GameWriteTool.writeString2(writeBuf, object2.token);
		GameWriteTool.writeInt(writeBuf, object2.checksum);
		Map<Object, Object> map = new HashMap<Object, Object>();
		map = UtilObjMap.Vo_16383_0(object2);
		map.remove("channel");
		map.remove("id");
		map.remove("name");
		map.remove("msg");
		map.remove("time");
		map.remove("privilege");
		map.remove("server_name");
		map.remove("show_extra");
		map.remove("compress");
		map.remove("cardCount");
		map.remove("orgLength");
		map.remove("voiceTime");
		map.remove("token");
		map.remove("checksum");

		Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Object, Object> entry = it.next();
			if (entry.getValue() != null && entry.getValue().equals(0)) {
				it.remove();
			}
		}
		GameWriteTool.writeShort(writeBuf, map.size());
		for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
			if (BuildFields.data.get(entry2.getKey()) != null) {
				BuildFieldsNew.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
			} else {
				System.out.println(entry2.getKey());
			}
		}
		GameWriteTool.writeShort(writeBuf, 0);
		GameWriteTool.writeShort(writeBuf, 1000);
	}

	@Override
	public int cmd() {
		return 16383;
	}
}
