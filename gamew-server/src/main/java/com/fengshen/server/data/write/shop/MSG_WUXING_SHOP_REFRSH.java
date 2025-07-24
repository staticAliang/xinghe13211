package com.fengshen.server.data.write.shop;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shop.Vo_SHOP;
import com.fengshen.server.data.vo.shop.Vo_SHOP.Item;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_WUXING_SHOP_REFRSH extends BaseWrite<Vo_SHOP> {

	@Override
	protected void writeO(ByteBuf buff, Vo_SHOP object) {
		GameWriteTool.writeInt(buff, (int) (System.currentTimeMillis()/1000L+10000000));
		GameWriteTool.writeByte(buff, object.getItems().size());
		for(Item item:object.getItems()) {
			GameWriteTool.writeString(buff, item.getName());
			GameWriteTool.writeShort(buff, item.getPrice());
			GameWriteTool.writeShort(buff, item.getNum());
			GameWriteTool.writeShort(buff, item.getTotalNum());
			GameWriteTool.writeByte(buff, item.getLimited());
		}
	}

	@Override
	public int cmd() {
		return 0xA067;
	}

}
