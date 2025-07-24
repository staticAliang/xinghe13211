package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M20480_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_20480_0 object2 = (Vo_20480_0)object;
        GameWriteTool.writeString2(writeBuf, object2.msg);
        GameWriteTool.writeInt(writeBuf, object2.time);
    }
    
    @Override
    public int cmd() {
        return 20480;
    }
}
