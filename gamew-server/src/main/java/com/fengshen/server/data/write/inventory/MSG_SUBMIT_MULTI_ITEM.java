package com.fengshen.server.data.write.inventory;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_SUBMIT_MULTI_ITEM;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_SUBMIT_MULTI_ITEM extends BaseWrite<Vo_SUBMIT_MULTI_ITEM>{

	@Override
	protected void writeO(ByteBuf buff, Vo_SUBMIT_MULTI_ITEM object) {
		
		GameWriteTool.writeByte(buff, object.getType());
		GameWriteTool.writeByte(buff, object.getLimitNum());
		GameWriteTool.writeByte(buff, object.getItems().size());
		for(Integer item:object.getItems()) {
			GameWriteTool.writeInt(buff, item);
		}
	}

	@Override
	public int cmd() {
		return 0xA051;
	}

}
