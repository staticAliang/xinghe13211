package com.fengshen.server.data.write;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.Vo_4119_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M4119_0 extends BaseWrite<List<Vo_4119_0>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Vo_4119_0> object2) {
		GameWriteTool.writeShort(writeBuf, object2.size());
		for (Vo_4119_0 obj : object2) {
			GameWriteTool.writeInt(writeBuf, obj.id);
			GameWriteTool.writeString(writeBuf, obj.gid);
			GameWriteTool.writeInt(writeBuf, obj.suit_icon);
			GameWriteTool.writeShort(writeBuf, obj.weapon_icon);
			GameWriteTool.writeShort(writeBuf, obj.org_icon);
			Map<Object, Object> map = UtilObjMap.Vo_4119_0(obj);
			map.remove("id");
			map.remove("gid");
			map.remove("membercard_name");
			map.remove("memberlight_effect_count");
			map.remove("suit_icon");
			map.remove("weapon_icon");
			map.remove("conutnumber");
			map.remove("org_icon");
			GameWriteTool.writeShort(writeBuf, map.size());
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
			GameWriteTool.writeString(writeBuf, obj.membercard_name);
			GameWriteTool.writeInt(writeBuf, 0);
			GameWriteTool.writeByte(writeBuf, obj.memberlight_effect_count);
			if(obj.memberlight_effect_count > 0) {
				GameWriteTool.writeInt(writeBuf, 0);
			}
			//suit_light_effect
			GameWriteTool.writeInt(writeBuf, 0);
			//recent_raise_bad
			GameWriteTool.writeInt(writeBuf, 0);
		}
	}

	@Override
	public int cmd() {
		return 4119;
	}
}
