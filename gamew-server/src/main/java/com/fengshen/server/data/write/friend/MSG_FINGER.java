package com.fengshen.server.data.write.friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 查找好友
 * 
 *
 */
public class MSG_FINGER extends BaseWrite<List<Vo_FRIEND_ADD_CHAR>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_FRIEND_ADD_CHAR> friends) {
		GameWriteTool.writeByte(buff, friends.size());
		for (final Vo_FRIEND_ADD_CHAR object2 : friends) {
			Map<Object,Object> map = new HashMap<>();
			map.put("gid", object2.iid_str);
			map.put("level", object2.skill);
			map.put("icon", object2.type);
			map.put("name", object2.charBuf);
			map.put("party/name", object2.party_contrib);
			map.put("insider_level", object2.insider_level);
			map.put("comeback_flag", 0);
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFieldsNew.data.get(entry.getKey()) != null) {
					BuildFieldsNew.get((String) entry.getKey()).write(buff, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0xF073;
	}

}
