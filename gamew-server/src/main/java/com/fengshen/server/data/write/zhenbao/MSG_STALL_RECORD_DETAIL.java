package com.fengshen.server.data.write.zhenbao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.constant.TransferItemType;
import com.fengshen.server.data.vo.zhenbao.Vo_STALL_RECORD_DETAIL;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MSG_STALL_RECORD_DETAIL extends BaseWrite<Vo_STALL_RECORD_DETAIL> {

	@Override
	protected void writeO(ByteBuf buff, Vo_STALL_RECORD_DETAIL record) {
		GameWriteTool.writeString(buff, record.getRecordId());
		GameWriteTool.writeByte(buff, record.getGoodsType());
		if (record.getGoodsType() == TransferItemType.getValue("宠物")) {
			Petbeibao pet = JSONObject.parseObject(record.getData(), Petbeibao.class);
			GameWriteTool.writeShort(buff, pet.petShuXing.size());
			PetShuXing petShuXing = pet.petShuXing.get(0);
			GameWriteTool.writeByte(buff, petShuXing.no);
			GameWriteTool.writeByte(buff, petShuXing.type1);
			Map<Object, Object> map = new HashMap<Object, Object>();
			map = UtilObjMapshuxing.PetShuXing(petShuXing, record.getOwnerName());
			map.remove("no");
			map.remove("type1");
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry : map.entrySet()) {
				if (BuildFields.data.get(entry.getKey()) != null) {
					BuildFields.get((String) entry.getKey()).write(buff, entry.getValue());
				} else {
					log.info((String) entry.getKey());
				}
			}
		} else if (record.getGoodsType() == TransferItemType.getValue("收费道具")) {
			Goods goods = JSONObject.parseObject(record.getData(), Goods.class);
			Map<Object, Object> map = new HashMap<Object, Object>();
			GameWriteTool.writeShort(buff, 10);
			if (goods.goodsInfo.amount != 0) {
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
						if (entry.getValue().equals(0) && entry.getKey().equals("pot")) {
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
	}

	@Override
	public int cmd() {
		return 0x8129;
	}

}
