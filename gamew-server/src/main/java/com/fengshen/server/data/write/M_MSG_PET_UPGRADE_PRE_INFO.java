package com.fengshen.server.data.write;

import io.netty.buffer.ByteBuf;

import org.springframework.stereotype.Component;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.VO_MSG_PET_UPGRADE_PRE_INFO;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;

import java.util.HashMap;
import java.util.Map;

@Component
public class M_MSG_PET_UPGRADE_PRE_INFO extends BaseWrite {

    @Override
    protected void writeO(ByteBuf writeBuf, Object paramObject) {
        VO_MSG_PET_UPGRADE_PRE_INFO vo = (VO_MSG_PET_UPGRADE_PRE_INFO) paramObject;
        GameWriteTool.writeInt(writeBuf, Integer.valueOf(vo.id));
        Map<Object, Object> map = new HashMap<>();
        // 气血
 		map.put("pet_life_shape", vo.pet_life_shape);
 		// 法力
 		map.put("pet_mana_shape", vo.pet_mana_shape);
 		// 速度
 		map.put("pet_speed_shape", vo.pet_speed_shape);
 		// 物攻
 		map.put("pet_phy_shape", vo.pet_mag_shape);
 		// 法攻
 		map.put("pet_mag_shape", vo.pet_phy_shape);
        
        GameWriteTool.writeShort(writeBuf, Integer.valueOf(map.size()));
        for (Map.Entry<Object, Object> objectEntry : map.entrySet()) {
            if (BuildFieldsNew.data.get((String)objectEntry.getKey()) != null) {
                BuildFieldsNew.get((String)objectEntry.getKey()).write(writeBuf, objectEntry.getValue());
            } else {
                System.out.println(objectEntry.getKey());
            }
        }
    }

    @Override
    public int cmd() {
        return 0xB0FC;
    }
}
