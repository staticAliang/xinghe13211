package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M4323_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_4323_0 object2 = (Vo_4323_0)object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeShort(writeBuf, object2.level);
    }
    
    @Override
    public int cmd() {
        return 4323;
    }
}
