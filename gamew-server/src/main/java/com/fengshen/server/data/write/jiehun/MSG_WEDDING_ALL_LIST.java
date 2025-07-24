package com.fengshen.server.data.write.jiehun;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_ALL_LIST;
import com.fengshen.server.data.vo.jiehun.Vo_WEDDING_ALL_LIST.Item;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_WEDDING_ALL_LIST extends BaseWrite<Vo_WEDDING_ALL_LIST> {

	@Override
	protected void writeO(ByteBuf buff, Vo_WEDDING_ALL_LIST object) {
		GameWriteTool.writeShort(buff, object.getItems().size());
		for(Item item:object.getItems()) {
			GameWriteTool.writeString(buff, item.getName());
			GameWriteTool.writeInt(buff, item.getPrice());
		}
		GameWriteTool.writeString(buff, object.getCostType());
		GameWriteTool.writeInt(buff, object.getDiscount());
	}

	@Override
	public int cmd() {
		return 0xB077;
	}

}
