package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

import java.util.*;

@Service
public class M61677_SHIZHUANG extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_61677_0 object2 = (Vo_61677_0)object;
        GameWriteTool.writeString(writeBuf, object2.store_type);
        GameWriteTool.writeInt(writeBuf, object2.npcID);
        GameWriteTool.writeShort(writeBuf, 0);
    }
    
    @Override
    public int cmd() {
        return 61677;
    }
    
    public boolean weizhi(final List<Goods> list, final int j) {
        final HashMap<Object, Object> map = new HashMap<Object, Object>();
        for (int i = 0; i < list.size(); ++i) {
            map.put(list.get(i).pos, list.get(i).pos);
        }
        return map.get(j) == null;
    }
}
