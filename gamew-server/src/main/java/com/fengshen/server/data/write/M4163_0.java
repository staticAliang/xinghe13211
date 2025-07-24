package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
// MSG_SET_CURRENT_PET 设置当前宠物
@Service
public class M4163_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_4163_0 object2 = (Vo_4163_0)object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeShort(writeBuf, object2.b);
    }
    
    @Override
    public int cmd() {
        return 4163;
    }
}
