package com.fengshen.server.data.write.equip;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_PRE_UPGRADE_EQUIP extends BaseWrite<Object[]> {

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		Goods goods = (Goods) object[0];
		GameWriteTool.writeByte(buff, goods.pos);
		GameWriteTool.writeByte(buff, (Integer) object[1]);
		GameWriteTool.writeShort(buff, 10);

		Map<Object, Object> map = new HashMap<Object, Object>();
		if (goods.goodsInfo != null) {
			goods.goodsInfo.add_pet_exp = (Integer) object[1];
			map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
			GameWriteTool.writeShort(buff, map.size());
			for (final Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
				} else {
					System.out.println(entry.getKey());
				}
			}
		}
		if (goods.goodsBasics != null) {
			map = UtilObjMapshuxing.GoodsBasics(goods.goodsBasics);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsBasics.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsBasics.groupType);
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
		if (goods.goodsLanSe != null) {
			map = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsLanSe.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsLanSe.groupType);
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
		if (goods.goodsGaiZao != null) {
			map = UtilObjMapshuxing.GoodsGaiZao(goods.goodsGaiZao);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsGaiZao.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsGaiZao.groupType);
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
		if (goods.goodsGaiZaoGongMing != null) {
			map = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMing.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMing.groupType);
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
		if (goods.goodsGaiZaoGongMingChengGong != null) {
			map = UtilObjMapshuxing.GoodsGaiZaoGongMingChengGong(goods.goodsGaiZaoGongMingChengGong);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMingChengGong.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMingChengGong.groupType);
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
		if (goods.goodsLvSeGongMing != null) {
			map = UtilObjMapshuxing.GoodsLvSeGongMing(goods.goodsLvSeGongMing);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsLvSeGongMing.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsLvSeGongMing.groupType);
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
	}

	@Override
	public int cmd() {
		return 32775;
	}

}
