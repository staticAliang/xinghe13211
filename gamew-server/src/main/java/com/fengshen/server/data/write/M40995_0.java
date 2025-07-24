package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M40995_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_40995_0 object2 = (Vo_40995_0)object;
        GameWriteTool.writeByte(writeBuf, object2.flag);
        GameWriteTool.writeInt(writeBuf, object2.money);
        GameWriteTool.writeString(writeBuf, object2.surlus);
        GameWriteTool.writeString(writeBuf, object2.overflow);
        GameWriteTool.writeInt(writeBuf, object2.amount);
        GameWriteTool.writeByte(writeBuf, object2.choice);
        GameWriteTool.writeByte(writeBuf, object2.prize);
        GameWriteTool.writeByte(writeBuf, object2.leftCount);
    }
    
    @Override
    public int cmd() {
        return 40995;
    }
}
