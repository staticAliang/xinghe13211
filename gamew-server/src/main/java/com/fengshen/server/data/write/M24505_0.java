package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M24505_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_24505_0 object2 = (Vo_24505_0)object;
        GameWriteTool.writeShort(writeBuf, object2.update_type);
        GameWriteTool.writeString(writeBuf, object2.groupBuf);
        GameWriteTool.writeString(writeBuf, object2.charBuf);
        final Map<Object, Object> map = UtilObjMap.Vo_24505_0(object2);
        map.remove("update_type");
        map.remove("groupBuf");
        map.remove("charBuf");
        final Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
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
        return 24505;
    }
}
