package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M12023_1 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_12023_0 object2 = (Vo_12023_0)object;
        GameWriteTool.writeInt(writeBuf, object2.owner_id);
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeShort(writeBuf, 0);
    }
    
    @Override
    public int cmd() {
        return 12023;
    }
}
