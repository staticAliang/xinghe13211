package com.fengshen.server.data.write;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.vo.Vo_49153_0;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M49153_0 extends BaseWrite<Vo_49153_0> {
	@Override
	protected void writeO(ByteBuf buff, Vo_49153_0 object) {
		GameWriteTool.writeString(buff, object.name);
		GameWriteTool.writeShort(buff, object.level);
		GameWriteTool.writeInt(buff, object.icon);
		GameWriteTool.writeInt(buff, object.special_icon);
		GameWriteTool.writeInt(buff, object.weapon_icon);
		GameWriteTool.writeInt(buff, object.suit_icon);
		GameWriteTool.writeInt(buff, object.suit_effect);
		GameWriteTool.writeInt(buff, object.power);
		GameWriteTool.writeString(buff, object.partyName);
		GameWriteTool.writeInt(buff, object.fashionIcon);
		GameWriteTool.writeByte(buff, object.upgradetype);
		GameWriteTool.writeShort(buff, object.upgradelevel);
		List<Goods> list = object.backpack;
		GameWriteTool.writeByte(buff, list.size());// count
		for (int i = 0; i < list.size(); ++i) {
			Goods goods = list.get(i);
			GameWriteTool.writeShort(buff, goods.pos);
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
				GameWriteTool.writeShort(buff, map.size());
				for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
					if (BuildFields.data.get(entry2.getKey()) != null) {
						BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
					} else {
						System.out.println(entry2.getKey());
					}
				}
			}
			GameCommonUtil.goodsCreate(buff, goods);
		}
		if(object.upgradetype == 3) {
			object.effect.put("feishengEffect", 8043);
		}else if(object.upgradetype == 3) {
			object.effect.put("feishengEffect", 8045);
		}
		//特效
		if(object.effect == null) {
			GameWriteTool.writeShort(buff, 0);
		}else {
			GameWriteTool.writeShort(buff, object.effect.size());
			for(Entry<String, Integer> m:object.effect.entrySet()) {
				GameWriteTool.writeInt(buff, m.getValue());
			}
		}
		GameWriteTool.writeString(buff, object.customIcon);
	}

	@Override
	public int cmd() {
		return 49153;
	}
}
