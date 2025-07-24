package com.fengshen.server.data.write.friend;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * a刷新赠送礼物
 * 
 *
 */
public class MSG_UPDATE_GIVING_ITEM extends BaseWrite<Object[]>{

	@Override
	protected void writeO(ByteBuf buff, Object[] object) {
		GameWriteTool.writeByte(buff, (Integer) object[0]);
		if(object[0].equals(0)) {
			//暂时只能道具
			Goods goods = (Goods) object[1];
			if (goods.goodsHunQi.zongShuxing != null && !goods.goodsHunQi.zongShuxing.isEmpty()) {
				GameWriteTool.writeShort(buff, 11);
			} else {
				GameWriteTool.writeShort(buff, 10);
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
			if (goods.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, goods.goodsInfo.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsInfo.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0) && entry.getKey().equals("silver_coin")) {
						it.remove();
					}
					if (entry.getValue().equals(0) && entry.getKey().equals("pot")) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			// 魂器
			if (goods.goodsHunQi.zongShuxing != null && !goods.goodsHunQi.zongShuxing.isEmpty()) {
				// groupNo
				GameWriteTool.writeByte(buff, 1);
				GameWriteTool.writeByte(buff, 4);
				GameWriteTool.writeByte(buff, 5);
				Iterator<Hashtable<String, Object>> xh = goods.goodsHunQi.zongShuxing.iterator();
				while (xh.hasNext()) {
					Hashtable<String, Object> ls = (Hashtable<String, Object>) xh.next();
					GameWriteTool.writeByte(buff, (Integer) ls.get("chaos_value"));
					GameWriteTool.writeByte(buff, (Integer) ls.get("yang_percent"));
					GameWriteTool.writeString(buff, (String) ls.get("yang_prop"));
					GameWriteTool.writeShort(buff, (Integer) ls.get("yang_prop_value"));
					GameWriteTool.writeString(buff, (String) ls.get("yin_prop"));
					GameWriteTool.writeShort(buff, (Integer) ls.get("yin_prop_value"));
				}
			}
			if (goods.goodsBasics != null) {
				map = UtilObjMapshuxing.GoodsBasics(goods.goodsBasics);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, goods.goodsBasics.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsBasics.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			if (goods.goodsFenSe != null) {
				map = UtilObjMapshuxing.GoodsFenSe(goods.goodsFenSe);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, goods.goodsFenSe.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsFenSe.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			if (goods.goodsHuangSe != null) {
				map = UtilObjMapshuxing.GoodsHuangSe(goods.goodsHuangSe);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, goods.goodsHuangSe.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsHuangSe.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			if (goods.goodsLvSe != null) {
				map = UtilObjMapshuxing.GoodsLvSe(goods.goodsLvSe);
				map.remove("groupNo");
				map.remove("groupType");
				map.remove("speed");
				GameWriteTool.writeByte(buff, goods.goodsLvSe.groupNo);
				GameWriteTool.writeByte(buff, goods.goodsLvSe.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
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
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if (entry.getValue().equals(0)) {
						it.remove();
					}
				}
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0xD087;
	}

}
