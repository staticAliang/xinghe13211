package com.fengshen.server.data.write.jiehun;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_ALL_LIST.Item;
import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_LIST;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_WEDDING_LIST extends BaseWrite<Vo_WEDDING_LIST>{

	@Override
	protected void writeO(ByteBuf buff, Vo_WEDDING_LIST object) {
		
		GameWriteTool.writeInt(buff, object.getTime());
		GameWriteTool.writeString(buff, object.getMeleName());
		GameWriteTool.writeString(buff, object.getFeMaleName());
		GameWriteTool.writeShort(buff, object.getItems().size());
		for(Item item:object.getItems()) {
			GameWriteTool.writeString(buff, item.getName());
			GameWriteTool.writeInt(buff, item.getPrice());
		}
	}

	@Override
	public int cmd() {
		return 0xB071;
	}

}
