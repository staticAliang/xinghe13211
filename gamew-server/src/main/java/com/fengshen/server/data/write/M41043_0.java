package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M41043_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_41043_0 object2 = (Vo_41043_0)object;
        GameWriteTool.writeByte(writeBuf, object2.flag);
        GameWriteTool.writeString(writeBuf, object2.name);
    }
    
    @Override
    public int cmd() {
        return 41043;
    }
}
