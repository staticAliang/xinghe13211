package com.fengshen.server.data.write.zhenbao;

import java.util.Iterator;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.vo.Vo_45104_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 珍宝查看道具名片
 * 
 *
 */
public class MSG_GOLD_STALL_GOODS_INFO_ITEM extends BaseWrite<Vo_45104_0> {

	@Override
	protected void writeO(ByteBuf buff, Vo_45104_0 object2) {
        GameWriteTool.writeString(buff, object2.id);
        GameWriteTool.writeByte(buff, object2.status);
        GameWriteTool.writeInt(buff, object2.endTime);
        final Goods goods = object2.goods;
        GameWriteTool.writeShort(buff, 10);
        Map<Object, Object> map = null;
        if(goods.goodsInfo.amount != 0) {
			if (goods.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
				final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					final Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0) && entry.getKey().equals("silver_coin")) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			GameCommonUtil.goodsCreate(buff, goods);
		}else {
			if (goods.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
				final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					final Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (final Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			GameCommonUtil.goodsCreate(buff, goods);
		}
	}

	@Override
	public int cmd() {
		return 0x8115;
	}

}
