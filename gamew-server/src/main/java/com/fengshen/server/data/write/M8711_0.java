package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M8711_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_8711_0 object2 = (Vo_8711_0)object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeByte(writeBuf, object2.success);
        GameWriteTool.writeByte(writeBuf, object2.die);
    }
    
    @Override
    public int cmd() {
        return 8711;
    }
}
