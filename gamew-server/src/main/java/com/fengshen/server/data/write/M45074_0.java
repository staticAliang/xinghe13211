package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;

@Service
public class M45074_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final List<Vo_45074_0> object2 = (List<Vo_45074_0>)object;
        GameWriteTool.writeByte(writeBuf, object2.size());
        for (int i = 0; i < object2.size(); ++i) {
            final Vo_45074_0 obj = object2.get(i);
            GameWriteTool.writeString(writeBuf, obj.guardName);
            GameWriteTool.writeShort(writeBuf, obj.guardLevel);
            GameWriteTool.writeShort(writeBuf, obj.guardIcon);
            GameWriteTool.writeShort(writeBuf, obj.guardOrder);
            GameWriteTool.writeInt(writeBuf, obj.guardId);
        }
    }
    
    @Override
    public int cmd() {
        return 45074;
    }
}
