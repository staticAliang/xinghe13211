package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M45104_0 extends BaseWrite {
	@Override
	protected void writeO(final ByteBuf writeBuf, final Object object) {
		final Vo_45104_0 object2 = (Vo_45104_0) object;
		GameWriteTool.writeString(writeBuf, object2.id);
		GameWriteTool.writeByte(writeBuf, object2.status);
		GameWriteTool.writeInt(writeBuf, object2.endTime);
		final Goods goods = object2.goods;
		GameWriteTool.writeShort(writeBuf, 10);
		Map<Object, Object> map = null;
		if (goods.goodsInfo.amount != 0) {
			if (goods.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupType);
				final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					final Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0) && entry.getKey().equals("silver_coin")) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(writeBuf, map.size());
				for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			GameCommonUtil.goodsCreate(writeBuf, goods);
		} else {
			if (goods.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupType);
				final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					final Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(writeBuf, map.size());
				for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(writeBuf, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			GameCommonUtil.goodsCreate(writeBuf, goods);
		}
	}

	@Override
	public int cmd() {
		return 45104;
	}
}
