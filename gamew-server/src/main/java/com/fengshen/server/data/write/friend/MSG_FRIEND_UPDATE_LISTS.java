package com.fengshen.server.data.write.friend;

import java.util.List;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_UPDATE_LISTS;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 根据分组获取好友信息
 * 
 */
public class MSG_FRIEND_UPDATE_LISTS extends BaseWrite<List<Vo_FRIEND_UPDATE_LISTS>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_FRIEND_UPDATE_LISTS> list) {
		//分组数量
		GameWriteTool.writeShort(buff, list.size());
		for(Vo_FRIEND_UPDATE_LISTS v:list) {
			//分组id
			GameWriteTool.writeString(buff, v.getGroup().getGroupId());
			//该分组下全部好友
			List<Vo_FRIEND_ADD_CHAR> friends = v.getFriends();
			//该分组好友数量
			GameWriteTool.writeShort(buff, friends.size());
			for (final Vo_FRIEND_ADD_CHAR object2 : friends) {
				GameWriteTool.writeString(buff, object2.charBuf);
				GameWriteTool.writeByte(buff, object2.blocked);
				GameWriteTool.writeByte(buff, object2.online);
				GameWriteTool.writeString(buff, object2.server_name1);
				GameWriteTool.writeByte(buff, object2.insider_level);
				final Map<Object, Object> map = UtilObjMap.friend(object2);
				GameWriteTool.writeShort(buff, map.size());
				for (final Map.Entry<Object, Object> entry : map.entrySet()) {
					if (BuildFieldsNew.data.get(entry.getKey()) != null) {
						BuildFieldsNew.get((String) entry.getKey()).write(buff, entry.getValue());
					} else {
						System.out.println(entry.getKey());
					}
				}
			}
		}
		
	}

	@Override
	public int cmd() {
		return 0xF067;
	}

}
