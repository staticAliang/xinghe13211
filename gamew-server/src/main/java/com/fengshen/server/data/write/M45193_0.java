package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45193_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_45193_0 object2 = (Vo_45193_0)object;
        GameWriteTool.writeString(writeBuf, object2.groupId0);
        GameWriteTool.writeString(writeBuf, object2.name0);
    }
    
    @Override
    public int cmd() {
        return 45193;
    }
}
