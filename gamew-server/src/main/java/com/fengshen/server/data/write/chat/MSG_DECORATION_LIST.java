package com.fengshen.server.data.write.chat;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.chat.Vo_DECORATION_LIST;
import com.fengshen.server.data.vo.chat.Vo_DECORATION_LIST.Items;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 聊天装饰列表
 * 
 *
 */
public class MSG_DECORATION_LIST extends BaseWrite<Vo_DECORATION_LIST> {

	@Override
	protected void writeO(ByteBuf buff, Vo_DECORATION_LIST object) {
		GameWriteTool.writeString(buff, object.getType());
		GameWriteTool.writeString(buff, object.getUsedName());
		GameWriteTool.writeByte(buff, object.getItems().size());
		for(Items item:object.getItems()) {
			GameWriteTool.writeString(buff, item.getName());
			GameWriteTool.writeInt(buff, item.getTime());
			GameWriteTool.writeInt(buff, item.getGetTime());
		}
	}

	@Override
	public int cmd() {
		return 0xA20C;
	}

}
