package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M61677_0 extends BaseWrite {
	@Override
	protected void writeO(ByteBuf writeBuf, Object object) {
		Vo_61677_0 object2 = (Vo_61677_0) object;
		GameWriteTool.writeString(writeBuf, object2.store_type);
		GameWriteTool.writeInt(writeBuf, object2.npcID);
		List<Goods> list = object2.list;
		GameWriteTool.writeShort(writeBuf, object2.count);
		for (int j = 0; j < list.size(); ++j) {
			Goods goods = list.get(j);
			GameWriteTool.writeByte(writeBuf, object2.isGoon);
			GameWriteTool.writeShort(writeBuf, goods.pos);
			if (goods.goodsHunQi.zongShuxing != null && !goods.goodsHunQi.zongShuxing.isEmpty()) {
				GameWriteTool.writeShort(writeBuf, 11);
			} else {
				GameWriteTool.writeShort(writeBuf, 10);
			}
			Map<Object, Object> map = null;
			if (goods.goodsInfo.amount != 0) {
				if (goods.goodsInfo != null) {
					map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
					map.remove("groupNo");
					map.remove("groupType");
					GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupNo);
					GameWriteTool.writeByte(writeBuf, goods.goodsInfo.groupType);
					Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<Object, Object> entry = it.next();
						if(entry.getValue() == null) {
							it.remove();
						}
						else if (entry.getValue().equals(0) && entry.getKey().equals("silver_coin")) {
							it.remove();
						}
					}
					GameWriteTool.writeShort(writeBuf, map.size());
					for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
					Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<Object, Object> entry = it.next();
						if (entry.getValue().equals(0)) {
							it.remove();
						}
					}
					GameWriteTool.writeShort(writeBuf, map.size());
					for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
		for (int i = 201; i < 335; ++i) {
			if (this.weizhi(list, i)) {
				GameWriteTool.writeByte(writeBuf, object2.isGoon);
				GameWriteTool.writeShort(writeBuf, i);
				GameWriteTool.writeShort(writeBuf, 0);
			}
		}
	}

	@Override
	public int cmd() {
		return 61677;
	}

	public boolean weizhi(List<Goods> list, int j) {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		for (int i = 0; i < list.size(); ++i) {
			map.put(list.get(i).pos, list.get(i).pos);
		}
		return map.get(j) == null;
	}
}
