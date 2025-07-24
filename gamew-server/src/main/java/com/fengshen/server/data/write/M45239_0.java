package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45239_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_45239_0 object2 = (Vo_45239_0)object;
        GameWriteTool.writeInt(writeBuf, object2.ti);
        GameWriteTool.writeByte(writeBuf, object2.state);
        GameWriteTool.writeString(writeBuf, object2.task_name);
        GameWriteTool.writeByte(writeBuf, object2.is_smart);
    }
    
    @Override
    public int cmd() {
        return 45239;
    }
}
