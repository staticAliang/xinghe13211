package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_41041_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M41041_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_41041_0 object2 = (Vo_41041_0)object;
        GameWriteTool.writeByte(writeBuf, object2.type);
        GameWriteTool.writeByte(writeBuf, object2.limitNum);
        GameWriteTool.writeByte(writeBuf, object2.count);
    }
    
    @Override
    public int cmd() {
        return 41041;
    }
}
