package com.fengshen.server.data.write.luoshu;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.luoshu.Vo_33507_0;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;

import java.util.Map;

public class UpdatejingyanWrite extends BaseWrite {

    protected void writeO(ByteBuf writeBuf, Object object) {
        Vo_33507_0 object2 = (Vo_33507_0)object;
        GameWriteTool.writeShort(writeBuf, object2.getMap().size());
        for (Map.Entry<Object, Object> entry : object2.getMap().entrySet()) {
            if (BuildFieldsNew.data.containsKey(entry.getKey())) {
                BuildFieldsNew.get((String)entry.getKey()).write(writeBuf, entry.getValue());
            }
        }
    }
    public int cmd() {
        return 33507;
    }
}