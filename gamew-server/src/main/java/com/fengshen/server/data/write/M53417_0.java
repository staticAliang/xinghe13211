package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M53417_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_53417_0 object2 = (Vo_53417_0)object;
        GameWriteTool.writeByte(writeBuf, object2.isBindName);
        GameWriteTool.writeByte(writeBuf, object2.isBindPhone);
        GameWriteTool.writeString(writeBuf, object2.bindName);
        GameWriteTool.writeString(writeBuf, object2.bindId);
        GameWriteTool.writeString(writeBuf, object2.bindPhone);
    }
    
    @Override
    public int cmd() {
        return 53417;
    }
}
