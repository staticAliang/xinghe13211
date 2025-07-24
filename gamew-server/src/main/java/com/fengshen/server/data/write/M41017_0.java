package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M41017_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        GameWriteTool.writeByte(writeBuf, 4);
        GameWriteTool.writeString(writeBuf, "xiux");
        GameWriteTool.writeString(writeBuf, "xiuxjz");
        GameWriteTool.writeString(writeBuf, "xiuxjz");
        GameWriteTool.writeString(writeBuf, "xiuxjz");
    }
    
    @Override
    public int cmd() {
        return 41017;
    }
}
