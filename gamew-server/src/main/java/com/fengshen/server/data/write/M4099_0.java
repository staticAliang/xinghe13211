package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M4099_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_4099_0 object2 = (Vo_4099_0)object;
        GameWriteTool.writeString(writeBuf, object2.name);
        GameWriteTool.writeString(writeBuf, object2.para);
        GameWriteTool.writeString(writeBuf, object2.gid);
    }
    
    @Override
    public int cmd() {
        return 4099;
    }
}
