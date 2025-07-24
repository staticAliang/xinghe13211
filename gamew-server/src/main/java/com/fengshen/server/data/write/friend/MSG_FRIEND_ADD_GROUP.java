package com.fengshen.server.data.write.friend;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_GROUP;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * 添加好友分组
 * 
 *
 */
@Slf4j
public class MSG_FRIEND_ADD_GROUP extends BaseWrite<Vo_FRIEND_ADD_GROUP>{

	@Override
	protected void writeO(ByteBuf buff, Vo_FRIEND_ADD_GROUP object) {
		GameWriteTool.writeString(buff, object.getGroupId());
		GameWriteTool.writeString(buff, object.getName());
		log.info("添加好友分组,名称:{}；id:{}",object.getName(),object.getGroupId());
	}

	@Override
	public int cmd() {
		return 0xB08D;
	}

}
