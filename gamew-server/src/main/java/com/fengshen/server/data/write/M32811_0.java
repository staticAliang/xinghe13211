package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M32811_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_32811_0 object2 = (Vo_32811_0)object;
        GameWriteTool.writeShort(writeBuf, object2.size);
        GameWriteTool.writeShort(writeBuf, object2.max_size);
        GameWriteTool.writeShort(writeBuf, object2.top_size);
    }
    
    @Override
    public int cmd() {
        return 32811;
    }
}
