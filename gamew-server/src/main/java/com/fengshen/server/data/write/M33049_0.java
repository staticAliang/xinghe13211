package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M33049_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_33049_0 object2 = (Vo_33049_0)object;
        GameWriteTool.writeString(writeBuf, object2.goods_gid);
        GameWriteTool.writeByte(writeBuf, object2.type);
        GameWriteTool.writeByte(writeBuf, object2.result);
        GameWriteTool.writeString(writeBuf, object2.tips);
    }
    
    @Override
    public int cmd() {
        return 33049;
    }
}
