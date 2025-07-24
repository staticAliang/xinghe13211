package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_GROUP;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_GROUP_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_FRIEND_GROUP_LIST extends BaseWrite<Vo_FRIEND_GROUP_LIST> {

	@Override
	protected void writeO(ByteBuf buff, Vo_FRIEND_GROUP_LIST object) {
		GameWriteTool.writeInt(buff, object.getFriendGroups().size());
		for(Vo_FRIEND_ADD_GROUP v:object.getFriendGroups()) {
			GameWriteTool.writeString(buff, v.getGroupId());
			GameWriteTool.writeString(buff, v.getName());
		}
	}
	
	@Override
	public int cmd() {
		return 0xB089;
	}

}
