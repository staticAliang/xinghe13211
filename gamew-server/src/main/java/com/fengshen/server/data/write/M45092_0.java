package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45092_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_45092_0 object2 = (Vo_45092_0)object;
        GameWriteTool.writeString(writeBuf, object2.task_name);
        GameWriteTool.writeInt(writeBuf, object2.check_point);
    }
    
    @Override
    public int cmd() {
        return 45092;
    }
}
