package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M20736_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_20736_0 object2 = (Vo_20736_0)object;
        GameWriteTool.writeString(writeBuf, object2.select);
    }
    
    @Override
    public int cmd() {
        return 20736;
    }
}
