package com.fengshen.server.data.write.hunpo;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.hunpo.Vo_REFRESH_SHENHUN_DATA;
import com.fengshen.server.data.vo.hunpo.Vo_REFRESH_SHENHUN_DATA.Vo_REFRESH_SHENHUN_DATA_ITEM;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

public class MSG_REFRESH_SHENHUN_DATA extends BaseWrite<Vo_REFRESH_SHENHUN_DATA> {

	@Override
	protected void writeO(ByteBuf buff, Vo_REFRESH_SHENHUN_DATA object) {
		
		GameWriteTool.writeInt(buff, object.getPhy_power());
		GameWriteTool.writeInt(buff, object.getMag_power());
		GameWriteTool.writeInt(buff, object.getMax_life());
		GameWriteTool.writeInt(buff, object.getDef());
		GameWriteTool.writeInt(buff, object.getSpeed());
		
		if(object.getHqPropData().isEmpty()) {
			GameWriteTool.writeShort(buff, 0);
		}else {
			Map<String,Integer> datas = new HashMap<>();
			for(Goods goods:object.getHqPropData()) {
				//计算属性
				GoodsLanSe goodsLanSe = goods.goodsLanSe;
				Field[] lanseFields = goodsLanSe.getClass().getFields();
				for(Field f:lanseFields) {
					if(!f.getName().equals("groupNo") && !f.getName().equals("groupType")) {
						try {
							if(f.get(goodsLanSe) instanceof Integer) {
								int int1 = (int) f.get(goodsLanSe);
								if(int1 > 0) {
									datas.put(f.getName(), int1);
								}
							}
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
			GameWriteTool.writeShort(buff, datas.size());
			for(Entry<String, Integer> v:datas.entrySet()) {
				BuildFields.get(v.getKey()).write(buff, v.getValue());
			}
		}
		
		GameWriteTool.writeByte(buff, object.getIsTop());
		GameWriteTool.writeByte(buff, object.getNextState());
		GameWriteTool.writeByte(buff, object.getNextLayer());
		for(Vo_REFRESH_SHENHUN_DATA_ITEM item:object.getItems()) {
			GameWriteTool.writeString(buff, item.getAttrib());
			GameWriteTool.writeInt(buff, item.getValue());
		}
		GameWriteTool.writeByte(buff, 0);
		
	}

	@Override
	public int cmd() {
		return 21249;
	}

}
