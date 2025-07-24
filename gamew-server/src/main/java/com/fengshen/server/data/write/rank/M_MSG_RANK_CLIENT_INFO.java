package com.fengshen.server.data.write.rank;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.netty.BaseWrite;
import com.google.common.collect.Maps;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class M_MSG_RANK_CLIENT_INFO extends BaseWrite {

    @Override
    protected void writeO(ByteBuf paramByteBuf, Object paramObject) {
        Chara chara = (Chara) paramObject;
        Map<String, Object> map = Maps.newHashMap();
        map.put(BuildFields.IID_STR, chara.uuid);
        map.put(BuildFields.LEVEL, chara.level);
        map.put(BuildFields.POLAR, chara.polar);
        map.put(BuildFields.PHY_POWER, chara.phy_power);
        map.put(BuildFields.SPEED, chara.speed);
        map.put(BuildFields.TAO, chara.tao);
        GameWriteTool.writeShort(paramByteBuf, Integer.valueOf(map.size()));
        for (Map.Entry<String, Object> stringObjectEntry : map.entrySet()) {
            if (BuildFields.data.get(stringObjectEntry.getKey()) != null) {
                BuildFields buildFields = BuildFields.get(stringObjectEntry.getKey());
                buildFields.write(paramByteBuf, stringObjectEntry.getValue());
            } else {
                log.info("key:{}",stringObjectEntry.getKey());
            }
        }
    }

    @Override
    public int cmd() {
        return 45077;
    }
}
