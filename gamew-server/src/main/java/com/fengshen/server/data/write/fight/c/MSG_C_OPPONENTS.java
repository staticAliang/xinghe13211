package com.fengshen.server.data.write.fight.c;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.fight.Vo_ADD_FRIEND_OPPONENT;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * MSG_C_OPPONENTS 对手列表
 */
@Service
public class MSG_C_OPPONENTS extends BaseWrite<List<Vo_ADD_FRIEND_OPPONENT>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Vo_ADD_FRIEND_OPPONENT> obj) {
		GameWriteTool.writeByte(writeBuf, obj.size());
		for (int i = 0; i < obj.size(); ++i) {
			Vo_ADD_FRIEND_OPPONENT object2 = obj.get(i);
			GameWriteTool.writeInt(writeBuf, object2.id);
			GameWriteTool.writeShort(writeBuf, object2.leader);
			GameWriteTool.writeShort(writeBuf, object2.weapon_icon);
			GameWriteTool.writeShort(writeBuf, object2.pos);
			GameWriteTool.writeShort(writeBuf, object2.rank);
			GameWriteTool.writeShort(writeBuf, object2.vip_type);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map = UtilObjMap.Vo_65017_0(object2);
			map.remove("id");
			map.remove("leader");
			map.remove("weapon_icon");
			map.remove("pos");
			map.remove("rank");
			map.remove("vip_type");
			map.remove("org_icon");
			map.remove("suit_icon");
			map.remove("suit_light_effect");
			map.remove("special_icon");
			GameWriteTool.writeShort(writeBuf, map.size());
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
			GameWriteTool.writeShort(writeBuf, object2.org_icon);
			GameWriteTool.writeInt(writeBuf, object2.suit_icon);
			GameWriteTool.writeInt(writeBuf, object2.suit_light_effect);
			GameWriteTool.writeInt(writeBuf, object2.special_icon);
		}
		for (int i = 0; i < obj.size(); ++i) {
			Vo_ADD_FRIEND_OPPONENT object2 = obj.get(i);
			GameWriteTool.writeString(writeBuf, object2.customIcon == null ? "" : object2.customIcon);
		}
		// effectIcons
		GameWriteTool.writeByte(writeBuf, 0);
		// extra_scale
		GameWriteTool.writeShort(writeBuf, 0);
	}

	@Override
	public int cmd() {
		return 65017;
	}
}
