package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M41482_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_41482_0 object2 = (Vo_41482_0)object;
        GameWriteTool.writeByte(writeBuf, object2.index);
        GameWriteTool.writeByte(writeBuf, object2.result);
        GameWriteTool.writeString(writeBuf, object2.name);
        GameWriteTool.writeByte(writeBuf, object2.brate);
    }
    
    @Override
    public int cmd() {
        return 41482;
    }
}
