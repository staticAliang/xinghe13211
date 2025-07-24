package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M45128_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_45128_0 object2 = (Vo_45128_0)object;
        GameWriteTool.writeShort(writeBuf, 10);
        Map<Object, Object> map = new HashMap<Object, Object>();
        map = UtilObjMap.Vo_45128_0(object2);
        map.remove("no");
        map.remove("type1");
        GameWriteTool.writeByte(writeBuf, object2.no);
        GameWriteTool.writeByte(writeBuf, object2.type1);
        GameWriteTool.writeShort(writeBuf, map.size());
        for (final Map.Entry<Object, Object> entry : map.entrySet()) {
            if (BuildFields.data.get(entry.getKey()) != null) {
                BuildFields.get((String) entry.getKey()).write(writeBuf, entry.getValue());
            }
            else {
                System.out.println(entry.getKey());
            }
        }
    }
    
    @Override
    public int cmd() {
        return 45128;
    }
}
