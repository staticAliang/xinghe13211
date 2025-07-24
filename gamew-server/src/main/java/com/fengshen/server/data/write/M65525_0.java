package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

// 物品的详细目录
@Service
public class M65525_0 extends BaseWrite<List<Goods>> {
	@Override
	protected void writeO(ByteBuf writeBuf, List<Goods> list) {
		GameWriteTool.writeShort(writeBuf, list.size());
		for (int i = 0; i < list.size(); ++i) {
			Goods goods = list.get(i);
			GameWriteTool.writeByte(writeBuf, goods.pos);
			if (goods.goodsHunQi != null && 
					goods.goodsHunQi.zongShuxing != null && !goods.goodsHunQi.zongShuxing.isEmpty()) {
				GameWriteTool.writeShort(writeBuf, 11);
			} else {
				GameWriteTool.writeShort(writeBuf, 10);
			}
			Map<Object, Object> map = new HashMap<Object, Object>();
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
					else if (entry.getValue().equals(0) && entry.getKey().equals("pot") && goods.pos != 9) {
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
			// 魂器
			if (goods.goodsHunQi != null && 
					goods.goodsHunQi.zongShuxing != null && !goods.goodsHunQi.zongShuxing.isEmpty()) {
				// groupNo
				GameWriteTool.writeByte(writeBuf, 1);
				GameWriteTool.writeByte(writeBuf, 4);
				GameWriteTool.writeByte(writeBuf, 5);
				Iterator<Hashtable<String, Object>> xh = goods.goodsHunQi.zongShuxing.iterator();
				while (xh.hasNext()) {
					Hashtable<String, Object> ls = (Hashtable<String, Object>) xh.next();
					GameWriteTool.writeByte(writeBuf, (Integer) ls.get("chaos_value"));
					GameWriteTool.writeByte(writeBuf, (Integer) ls.get("yang_percent"));
					GameWriteTool.writeString(writeBuf, (String) ls.get("yang_prop"));
					GameWriteTool.writeShort(writeBuf, (Integer) ls.get("yang_prop_value"));
					GameWriteTool.writeString(writeBuf, (String) ls.get("yin_prop"));
					GameWriteTool.writeShort(writeBuf, (Integer) ls.get("yin_prop_value"));
				}
			}
			if (goods.goodsBasics != null) {
				map = UtilObjMapshuxing.GoodsBasics(goods.goodsBasics);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsBasics.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsBasics.groupType);
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
			if (goods.goodsLanSe != null) {
				map = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsLanSe.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsLanSe.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}
					else if (entry.getValue().equals(0)) {
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
			if (goods.goodsFenSe != null) {
				map = UtilObjMapshuxing.GoodsFenSe(goods.goodsFenSe);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsFenSe.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsFenSe.groupType);
				map = map.entrySet().stream().filter((e) -> e.getValue() != null).collect(Collectors.toMap(
			                (e) -> (String) e.getKey(),
			                (e) -> e.getValue()));
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					try {
						if(entry.getValue() == null) {
							it.remove();
						}
						if (entry.getValue().equals(0)) {
							it.remove();
						}
					} catch (Exception e) {
						System.out.println();
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
			if (goods.goodsHuangSe != null) {
				map = UtilObjMapshuxing.GoodsHuangSe(goods.goodsHuangSe);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsHuangSe.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsHuangSe.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}
					else if (entry.getValue().equals(0)) {
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
			if (goods.goodsLvSe != null) {
				map = UtilObjMapshuxing.GoodsLvSe(goods.goodsLvSe);
				map.remove("groupNo");
				map.remove("groupType");
				map.remove("speed");
				GameWriteTool.writeByte(writeBuf, goods.goodsLvSe.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsLvSe.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}
					else if (entry.getValue().equals(0)) {
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
			if (goods.goodsGaiZao != null) {
				map = UtilObjMapshuxing.GoodsGaiZao(goods.goodsGaiZao);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsGaiZao.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsGaiZao.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}else if (entry.getValue().equals(0)) {
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
			if (goods.goodsGaiZaoGongMing != null) {
				map = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMing.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMing.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}
					else if (entry.getValue().equals(0)) {
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
			if (goods.goodsGaiZaoGongMingChengGong != null) {
				map = UtilObjMapshuxing.GoodsGaiZaoGongMingChengGong(goods.goodsGaiZaoGongMingChengGong);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMingChengGong.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsGaiZaoGongMingChengGong.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}
					else if (entry.getValue().equals(0)) {
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
			if (goods.goodsLvSeGongMing != null) {
				map = UtilObjMapshuxing.GoodsLvSeGongMing(goods.goodsLvSeGongMing);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(writeBuf, goods.goodsLvSeGongMing.groupNo);
				GameWriteTool.writeByte(writeBuf, goods.goodsLvSeGongMing.groupType);
				Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry<Object, Object> entry = it.next();
					if(entry.getValue() == null) {
						it.remove();
					}
					else if (entry.getValue().equals(0)) {
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
		}
	}

	@Override
	public int cmd() {
		return 65525;
	}
}
