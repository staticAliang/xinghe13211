package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M20912_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_20912_0 object2 = (Vo_20912_0)object;
        GameWriteTool.writeByte(writeBuf, object2.flag);
        GameWriteTool.writeInt(writeBuf, object2.open_time);
    }
    
    @Override
    public int cmd() {
        return 20912;
    }
}
