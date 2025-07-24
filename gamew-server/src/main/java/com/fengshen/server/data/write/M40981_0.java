package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_40981_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

@Service
public class M40981_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_40981_0 object2 = (Vo_40981_0)object;
        GameWriteTool.writeInt(writeBuf, object2.start_time);
        GameWriteTool.writeInt(writeBuf, object2.end_time);
        GameWriteTool.writeShort(writeBuf, object2.icon);
        GameWriteTool.writeString(writeBuf, object2.word);
        GameWriteTool.writeString(writeBuf, object2.gather_style);
    }
    
    @Override
    public int cmd() {
        return 40981;
    }
}
