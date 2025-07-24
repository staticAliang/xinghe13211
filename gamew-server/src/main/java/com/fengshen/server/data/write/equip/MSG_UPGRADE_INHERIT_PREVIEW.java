package com.fengshen.server.data.write.equip;

import java.util.Iterator;
import java.util.Map;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.vo.equip.Vo_UPGRADE_INHERIT_PREVIEW;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 装备继承预览
 * 
 *
 */
public class MSG_UPGRADE_INHERIT_PREVIEW extends BaseWrite<Vo_UPGRADE_INHERIT_PREVIEW> {

	@Override
	protected void writeO(ByteBuf buff, Vo_UPGRADE_INHERIT_PREVIEW object) {
		
		GameWriteTool.writeShort(buff, object.getPos());
		GameWriteTool.writeString(buff, object.getPara());
		GameWriteTool.writeByte(buff, object.getFlag());
		GameWriteTool.writeInt(buff, object.getMoney());
		GameWriteTool.writeInt(buff, object.getCoin());
		
		
		//主装备预览数据
		Goods mEquip = object.getMEquip();
		Map<Object,Object> map = null;
		if(mEquip != null) {
			GameWriteTool.writeShort(buff, 10);
			if (mEquip.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(mEquip.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, mEquip.goodsInfo.groupNo);
				GameWriteTool.writeByte(buff, mEquip.goodsInfo.groupType);
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
			GameCommonUtil.goodsCreate(buff, mEquip);
		}
		//副装备预览数据
		Goods oEquip = object.getOEquip();
		if(oEquip != null) {
			GameWriteTool.writeShort(buff, 10);
			if (mEquip.goodsInfo != null) {
				map = UtilObjMapshuxing.GoodsInfo(oEquip.goodsInfo);
				map.remove("groupNo");
				map.remove("groupType");
				GameWriteTool.writeByte(buff, oEquip.goodsInfo.groupNo);
				GameWriteTool.writeByte(buff, oEquip.goodsInfo.groupType);
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
			GameCommonUtil.goodsCreate(buff, oEquip);
		}
	}

	@Override
	public int cmd() {
		return 0xA117;
	}

}
