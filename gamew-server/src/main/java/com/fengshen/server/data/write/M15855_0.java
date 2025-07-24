package com.fengshen.server.data.write;

import io.netty.buffer.ByteBuf;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_15855_0;
import com.fengshen.server.netty.BaseWrite;

// 法力更新的对象
@Service
public class M15855_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_15855_0 object2 = (Vo_15855_0)object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeInt(writeBuf, object2.hitter_id);
        GameWriteTool.writeInt(writeBuf, object2.point);
        GameWriteTool.writeInt(writeBuf, object2.effect_no);
    }
    
    @Override
    public int cmd() {
        return 15855;
    }
}
