package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M32827_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_32827_0 object2 = (Vo_32827_0)object;
        GameWriteTool.writeByte(writeBuf, object2.has_pwd);
        GameWriteTool.writeByte(writeBuf, object2.isReleaseLock);
        GameWriteTool.writeInt(writeBuf, object2.reset_start);
        GameWriteTool.writeInt(writeBuf, object2.reset_end);
        GameWriteTool.writeInt(writeBuf, object2.reset_days);
    }
    
    @Override
    public int cmd() {
        return 32827;
    }
}
