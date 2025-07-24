package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45075_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_45075_0 object2 = (Vo_45075_0)object;
        GameWriteTool.writeShort(writeBuf, object2.teams);
        GameWriteTool.writeShort(writeBuf, object2.members);
    }
    
    @Override
    public int cmd() {
        return 45075;
    }
}
