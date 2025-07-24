package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M8165_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_8165_0 object2 = (Vo_8165_0)object;
        GameWriteTool.writeString2(writeBuf, object2.msg);
        GameWriteTool.writeShort(writeBuf, object2.active);
    }
    
    @Override
    public int cmd() {
        return 8165;
    }
}
