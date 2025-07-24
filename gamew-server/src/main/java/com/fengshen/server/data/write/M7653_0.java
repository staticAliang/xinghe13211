package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M7653_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_7653_0 object2 = (Vo_7653_0)object;
        GameWriteTool.writeInt(writeBuf, object2.id);
    }
    
    @Override
    public int cmd() {
        return 7653;
    }
}
