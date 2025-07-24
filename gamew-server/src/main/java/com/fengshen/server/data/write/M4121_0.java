package com.fengshen.server.data.write;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M4121_0 extends BaseWrite<List<Vo_4121_0>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, List<Vo_4121_0> object2) {
		GameWriteTool.writeShort(writeBuf, object2.size());
		for (final Vo_4121_0 obj : object2) {
			GameWriteTool.writeInt(writeBuf, obj.id);
			GameWriteTool.writeString(writeBuf, obj.gid);
			GameWriteTool.writeInt(writeBuf, obj.suit_icon);
			GameWriteTool.writeShort(writeBuf, obj.weapon_icon);
			GameWriteTool.writeShort(writeBuf, obj.org_icon);
			final Map<Object, Object> map = UtilObjMap.Vo_4121_0(obj);
			map.remove("id");
            map.remove("gid");
            map.remove("suit_icon");
            map.remove("weapon_icon");
            map.remove("org_icon");
            map.remove("memberpos_x");
            map.remove("memberpos_y");
            map.remove("membermap_id");
            map.remove("memberteam_status");
            map.remove("membercard_name");
            map.remove("membercomeback_flag");
            map.remove("memberlight_effect_count");
			final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				final Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0) || entry.getKey().equals("")) {
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
			GameWriteTool.writeShort(writeBuf, obj.memberpos_x);
			GameWriteTool.writeShort(writeBuf, obj.memberpos_y);
			GameWriteTool.writeInt(writeBuf, obj.membermap_id);
			GameWriteTool.writeByte(writeBuf, obj.memberteam_status);
			GameWriteTool.writeString(writeBuf, obj.membercard_name);
			//card_end_time
			GameWriteTool.writeInt(writeBuf, 0);
			//comeback_flag
			GameWriteTool.writeByte(writeBuf, obj.membercomeback_flag);
			//light_effect_count
			GameWriteTool.writeByte(writeBuf, obj.memberlight_effect_count);
			if(obj.memberlight_effect_count>0) {
				//effect
				GameWriteTool.writeInt(writeBuf, 0);
			}
			//difu_flag
			GameWriteTool.writeByte(writeBuf, 0);
			//suit_light_effect
			GameWriteTool.writeInt(writeBuf, 0);
			//recent_raise_bad
			GameWriteTool.writeInt(writeBuf, 0);
		}
	}

	@Override
	public int cmd() {
		return 4121;
	}
}
