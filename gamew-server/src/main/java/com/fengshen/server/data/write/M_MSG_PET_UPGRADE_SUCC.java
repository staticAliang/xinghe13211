package com.fengshen.server.data.write;

import io.netty.buffer.ByteBuf;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_MSG_PET_UPGRADE_SUCC;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import java.util.HashMap;
import java.util.Map;

@Component
public class M_MSG_PET_UPGRADE_SUCC extends BaseWrite {
	@Override
	protected void writeO(ByteBuf writeBuf, Object paramObject) {
		Vo_MSG_PET_UPGRADE_SUCC vo = (Vo_MSG_PET_UPGRADE_SUCC) paramObject;
		GameWriteTool.writeInt(writeBuf, Integer.valueOf(vo.id));
		Map<Object, Object> map = new HashMap<>();
		// 气血
		map.put("pet_life_shape", vo.pet_life_shape[0]);
		// 法力
		map.put("pet_mana_shape", vo.pet_mana_shape[0]);
		// 速度
		map.put("pet_speed_shape", vo.pet_speed_shape[0]);
		// 物攻
		map.put("pet_phy_shape", vo.pet_mag_shape[0]);
		// 法攻
		map.put("pet_mag_shape", vo.pet_phy_shape[0]);
		System.out.println(JSONObject.toJSON(vo));
		GameWriteTool.writeShort(writeBuf, Integer.valueOf(5));
		for (Map.Entry<Object, Object> objectEntry : map.entrySet()) {
			if (BuildFieldsNew.data.get((String) objectEntry.getKey()) != null) {
				BuildFieldsNew.get((String) objectEntry.getKey()).write(writeBuf, objectEntry.getValue());
			} else {
				System.out.println(objectEntry.getKey());
			}
		}
		// 气血
		map.put("pet_life_shape", vo.pet_life_shape[1]);
		// 法力
		map.put("pet_mana_shape", vo.pet_mana_shape[1]);
		// 速度
		map.put("pet_speed_shape", vo.pet_speed_shape[1]);
		// 物攻
		map.put("pet_phy_shape", vo.pet_mag_shape[1]);
		// 法攻
		map.put("pet_mag_shape", vo.pet_phy_shape[1]);
		GameWriteTool.writeShort(writeBuf, Integer.valueOf(5));
		for (Map.Entry<Object, Object> objectEntry : map.entrySet()) {
			if (BuildFieldsNew.data.get((String) objectEntry.getKey()) != null) {
				BuildFieldsNew.get((String) objectEntry.getKey()).write(writeBuf, objectEntry.getValue());
			} else {
				System.out.println(objectEntry.getKey());
			}
		}
	}

	@Override
	public int cmd() {
		return 0xB0FD;
	}
}
