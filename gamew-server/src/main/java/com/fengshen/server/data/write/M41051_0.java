package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M41051_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_41051_0 object2 = (Vo_41051_0)object;
        GameWriteTool.writeByte(writeBuf, object2.count);
        GameWriteTool.writeString(writeBuf, object2.name0);
        GameWriteTool.writeShort(writeBuf, object2.amount0);
        GameWriteTool.writeInt(writeBuf, object2.startTime0);
        GameWriteTool.writeInt(writeBuf, object2.endTime0);
    }
    
    @Override
    public int cmd() {
        return 41051;
    }
}
