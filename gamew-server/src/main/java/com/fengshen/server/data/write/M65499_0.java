package com.fengshen.server.data.write;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreGoods;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M65499_0 extends BaseWrite<List<StoreGoods>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<StoreGoods> list) {
		GameWriteTool.writeString(writeBuf, "");
		GameWriteTool.writeByte(writeBuf, 0);
		GameWriteTool.writeShort(writeBuf, list.size());
		for (int i = 0; i < list.size(); ++i) {
			GameWriteTool.writeString(writeBuf, list.get(i).getName());
			GameWriteTool.writeString(writeBuf, list.get(i).getBarcode());
			GameWriteTool.writeShort(writeBuf, list.get(i).getForSale());
			GameWriteTool.writeShort(writeBuf, list.get(i).getShowPos());
			GameWriteTool.writeShort(writeBuf, list.get(i).getRpos());
			GameWriteTool.writeShort(writeBuf, list.get(i).getSaleQuota());
			GameWriteTool.writeShort(writeBuf, list.get(i).getRecommend());
			GameWriteTool.writeInt(writeBuf, list.get(i).getCoin());
			GameWriteTool.writeByte(writeBuf, list.get(i).getDiscount());
			GameWriteTool.writeInt(writeBuf,(int) (System.currentTimeMillis()/1000L));
			GameWriteTool.writeByte(writeBuf, list.get(i).getType());
			GameWriteTool.writeShort(writeBuf, list.get(i).getQuotaLimit());
			GameWriteTool.writeByte(writeBuf, list.get(i).getMustVip());
			GameWriteTool.writeByte(writeBuf, list.get(i).getIsGift());
			GameWriteTool.writeByte(writeBuf, list.get(i).getFollowPetType());
			GameWriteTool.writeInt(writeBuf, 0);
			GameWriteTool.writeInt(writeBuf, 0);
		}
	}

	@Override
	public int cmd() {
		return 65499;
	}
}
