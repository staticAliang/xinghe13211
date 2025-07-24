package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;
/**
 * MSG_NOTIFY_MISC_EX
 */
@Service
public class M20481_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_20481_0 object2 = (Vo_20481_0)object;
        GameWriteTool.writeString2(writeBuf, object2.msg);
        GameWriteTool.writeInt(writeBuf, object2.time);
    }
    
    @Override
    public int cmd() {
        return 20481;
    }
}
