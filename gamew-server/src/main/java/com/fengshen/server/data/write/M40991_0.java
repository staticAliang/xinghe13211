package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M40991_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_40991_0 object2 = (Vo_40991_0)object;
        GameWriteTool.writeByte(writeBuf, object2.result);
    }
    
    @Override
    public int cmd() {
        return 40991;
    }
}
