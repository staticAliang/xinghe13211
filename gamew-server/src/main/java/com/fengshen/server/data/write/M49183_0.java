package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_49183;
import com.fengshen.server.data.vo.Vo_49183_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M49183_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_49183_0 object2 = (Vo_49183_0) object;
		GameWriteTool.writeShort(writeBuf, object2.totalPage);
		GameWriteTool.writeShort(writeBuf, object2.cur_page);
		GameWriteTool.writeShort(writeBuf, object2.vo_49183s.size());
		for (int i = 0; i < object2.vo_49183s.size(); ++i) {
			final Vo_49183 vo_49183 = object2.vo_49183s.get(i);
			GameWriteTool.writeString(writeBuf, vo_49183.name);
			GameWriteTool.writeByte(writeBuf, vo_49183.is_my_goods);
			GameWriteTool.writeString(writeBuf, vo_49183.id);
			GameWriteTool.writeInt(writeBuf, vo_49183.price);
			GameWriteTool.writeShort(writeBuf, vo_49183.status);
			GameWriteTool.writeInt(writeBuf, vo_49183.startTime);
			GameWriteTool.writeInt(writeBuf, vo_49183.endTime);
			GameWriteTool.writeShort(writeBuf, vo_49183.level);
			GameWriteTool.writeByte(writeBuf, vo_49183.unidentified);
			GameWriteTool.writeShort(writeBuf, vo_49183.amount);
			GameWriteTool.writeShort(writeBuf, vo_49183.req_level);
			GameWriteTool.writeString(writeBuf, vo_49183.extra);
			GameWriteTool.writeByte(writeBuf, vo_49183.item_polar);
			GameWriteTool.writeInt(writeBuf, vo_49183.icon);
		}
		GameWriteTool.writeString(writeBuf, object2.path_str);
		GameWriteTool.writeString(writeBuf, object2.select_gid);
		GameWriteTool.writeByte(writeBuf, object2.sell_stage);
		GameWriteTool.writeString(writeBuf, object2.sort_key);
		GameWriteTool.writeByte(writeBuf, object2.is_descending);
	}

	@Override
	public int cmd() {
		return 49183;
	}
}
