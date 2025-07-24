package com.fengshen.server.data.write.market;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_49179;
import com.fengshen.server.data.vo.Vo_49179_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 集市摆摊
 * 
 *
 */
@Service
public class M49179_0 extends BaseWrite<Vo_49179_0> {
	@Override
	protected void writeO(final ByteBuf writeBuf, Vo_49179_0 object) {
		GameWriteTool.writeShort(writeBuf, object.dealNum);
		GameWriteTool.writeString(writeBuf, object.sellCash);
		GameWriteTool.writeShort(writeBuf, object.stallTotalNum);
		GameWriteTool.writeShort(writeBuf, object.record_count_max);
		final List<Vo_49179> vo_49179 = object.vo_49179s;
		GameWriteTool.writeShort(writeBuf, vo_49179.size());
		for (int i = 0; i < vo_49179.size(); ++i) {
			final Vo_49179 goods = vo_49179.get(i);
			// name
			GameWriteTool.writeString(writeBuf, goods.name);
			// id
			GameWriteTool.writeString(writeBuf, goods.id);
			// price
			GameWriteTool.writeInt(writeBuf, goods.price);
			// status
			GameWriteTool.writeShort(writeBuf, goods.status);
			// startTime
			GameWriteTool.writeInt(writeBuf, goods.startTime);
			// endTime
			GameWriteTool.writeInt(writeBuf, goods.endTime);
			// level 等级
			GameWriteTool.writeShort(writeBuf, goods.level);
			// unidentified 未鉴定 0:已鉴定 1未鉴定
			GameWriteTool.writeByte(writeBuf, goods.unidentified);
			// amount --数量
			GameWriteTool.writeShort(writeBuf, goods.amount);
			// req_level
			GameWriteTool.writeShort(writeBuf, goods.req_level);
			// extra 其他字段
			GameWriteTool.writeString(writeBuf, goods.extra);
			// 相性 0是无相性
			GameWriteTool.writeByte(writeBuf, goods.item_polar);
			// cg_price_count
			GameWriteTool.writeByte(writeBuf, goods.cg_price_count);
			// init_price
			GameWriteTool.writeInt(writeBuf, goods.init_price);
			// icon
			GameWriteTool.writeInt(writeBuf, goods.icon);
		}

	}

	@Override
	public int cmd() {
		return 49179;
	}
}
