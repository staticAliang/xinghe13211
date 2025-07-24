package com.fengshen.server.data.write.shop;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shop.Vo_RARE_SHOP_ITEMS_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_RARE_SHOP_ONE_ITEM_INFO extends BaseWrite<Vo_RARE_SHOP_ITEMS_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_RARE_SHOP_ITEMS_INFO object) {
		
		GameWriteTool.writeString(buff, object.getBarcode());
		GameWriteTool.writeString(buff, object.getName());
		GameWriteTool.writeInt(buff, object.getCost());
		GameWriteTool.writeInt(buff, object.getNum());
	}

	@Override
	public int cmd() {
		return 0xB100;
	}

}
