package com.fengshen.server.data.write;

import com.fengshen.server.data.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

public class M53443 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        GameWriteTool.writeShort(writeBuf, 2);
        GameWriteTool.writeString(writeBuf, "这个是做装备");
        GameWriteTool.writeString(writeBuf, "我是在做装备");
    }

    @Override
    public int cmd() {
        return 53443;
    }
}