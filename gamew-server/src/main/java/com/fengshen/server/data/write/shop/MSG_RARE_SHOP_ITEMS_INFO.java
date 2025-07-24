package com.fengshen.server.data.write.shop;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shop.Vo_RARE_SHOP_ITEMS_INFO;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_RARE_SHOP_ITEMS_INFO extends BaseWrite<List<Vo_RARE_SHOP_ITEMS_INFO>> {

	@Override
	protected void writeO(ByteBuf buff, List<Vo_RARE_SHOP_ITEMS_INFO> object) {
		GameWriteTool.writeInt(buff, object.size());
		for(Vo_RARE_SHOP_ITEMS_INFO vo:object) {
			GameWriteTool.writeString(buff, vo.getBarcode());
			GameWriteTool.writeString(buff, vo.getName());
			GameWriteTool.writeInt(buff, vo.getCost());
			GameWriteTool.writeInt(buff, vo.getNum());
		}
	}

	@Override
	public int cmd() {
		return 0xB0FF;
	}

}
