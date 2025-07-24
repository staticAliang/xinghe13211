package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;

@Service
public class M41480_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final List<Vo_41480_0> object2 = (List<Vo_41480_0>)object;
        GameWriteTool.writeInt(writeBuf, object2.get(0).online_time);
        GameWriteTool.writeByte(writeBuf, object2.size());
        for (int i = 0; i < object2.size(); ++i) {
            final Vo_41480_0 object3 = object2.get(i);
            GameWriteTool.writeByte(writeBuf, object3.index);
            GameWriteTool.writeShort(writeBuf, object3.time);
            GameWriteTool.writeString(writeBuf, object3.name);
            GameWriteTool.writeByte(writeBuf, object3.brate);
        }
    }
    
    @Override
    public int cmd() {
        return 41480;
    }
}
