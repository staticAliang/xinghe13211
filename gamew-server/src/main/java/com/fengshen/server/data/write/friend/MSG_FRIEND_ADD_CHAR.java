package com.fengshen.server.data.write.friend;

import java.util.List;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
/**
 * 添加角色为好友
 * 
 *
 */
public class MSG_FRIEND_ADD_CHAR extends BaseWrite<List<Vo_FRIEND_ADD_CHAR>> {
	@Override
	protected void writeO(final ByteBuf writeBuf, List<Vo_FRIEND_ADD_CHAR> vo_61545_0List) {
		GameWriteTool.writeShort(writeBuf, vo_61545_0List.size());
		for (final Vo_FRIEND_ADD_CHAR object2 : vo_61545_0List) {
			GameWriteTool.writeString(writeBuf, object2.groupBuf);
			GameWriteTool.writeString(writeBuf, object2.charBuf);
			GameWriteTool.writeByte(writeBuf, object2.blocked);
			GameWriteTool.writeByte(writeBuf, object2.online);
			GameWriteTool.writeString(writeBuf, object2.server_name1);
			GameWriteTool.writeByte(writeBuf, object2.insider_level);
			Map<Object, Object> map = UtilObjMap.friend(object2);
			GameWriteTool.writeShort(writeBuf, map.size());
			for (final Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFieldsNew.data.get(entry.getKey()) != null) {
					BuildFieldsNew.get((String) entry.getKey()).write(writeBuf, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0xF069;
	}
}
