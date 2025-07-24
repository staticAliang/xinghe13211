package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M33301_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_33301_0 object2 = (Vo_33301_0)object;
        GameWriteTool.writeByte(writeBuf, object2.enable);
        GameWriteTool.writeByte(writeBuf, object2.level);
    }
    
    @Override
    public int cmd() {
        return 33301;
    }
}
