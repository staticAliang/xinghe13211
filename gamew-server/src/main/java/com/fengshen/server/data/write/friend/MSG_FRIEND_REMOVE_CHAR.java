package com.fengshen.server.data.write.friend;

import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 删除好友
 * 
 *
 */
public class MSG_FRIEND_REMOVE_CHAR extends BaseWrite<Map<String,String>> {

	@Override
	protected void writeO(ByteBuf buff, Map<String, String> object) {
		GameWriteTool.writeShort(buff, 1);
		GameWriteTool.writeString(buff, object.get("groupBuf"));
		GameWriteTool.writeString(buff, object.get("charBuf"));
		GameWriteTool.writeString(buff, object.get("gid"));
	}

	@Override
	public int cmd() {
		return 0x306B;
	}

}
