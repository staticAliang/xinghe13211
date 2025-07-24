package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M32896_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_32896_0 object2 = (Vo_32896_0)object;
        GameWriteTool.writeString(writeBuf, object2.alias0);
        GameWriteTool.writeString(writeBuf, object2.alias1);
        GameWriteTool.writeString(writeBuf, object2.alias2);
        GameWriteTool.writeString(writeBuf, object2.alias3);
        GameWriteTool.writeString(writeBuf, object2.alias4);
        GameWriteTool.writeString(writeBuf, object2.alias5);
        GameWriteTool.writeString(writeBuf, object2.alias6);
    }
    
    @Override
    public int cmd() {
        return 32896;
    }
}
