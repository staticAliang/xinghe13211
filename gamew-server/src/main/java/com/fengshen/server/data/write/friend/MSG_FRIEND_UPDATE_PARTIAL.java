package com.fengshen.server.data.write.friend;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMap;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 *  刷新好友
 * 
 *
 */
public class MSG_FRIEND_UPDATE_PARTIAL extends BaseWrite<Vo_FRIEND_ADD_CHAR> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FRIEND_ADD_CHAR object) {
		GameWriteTool.writeShort(buff, 2);
		GameWriteTool.writeString(buff, object.groupBuf);
		GameWriteTool.writeString(buff, object.charBuf);
		final Map<Object, Object> map = UtilObjMap.friend(object);
		GameWriteTool.writeShort(buff, map.size());
		for (final Map.Entry<Object, Object> entry : map.entrySet()) {
			if (BuildFields.data.get(entry.getKey()) != null) {
				BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
			} else {
				System.out.println(entry.getKey());
			}
		}
	}

	@Override
	public int cmd() {
		return 0x5FB9;
	}

}
