package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M32985_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_32985_0 object2 = (Vo_32985_0)object;
        GameWriteTool.writeByte(writeBuf, object2.user_is_multi);
        GameWriteTool.writeByte(writeBuf, object2.user_round);
        GameWriteTool.writeByte(writeBuf, object2.user_action);
        GameWriteTool.writeByte(writeBuf, object2.user_next_action);
        GameWriteTool.writeInt(writeBuf, object2.user_para);
        GameWriteTool.writeInt(writeBuf, object2.user_next_para);
        GameWriteTool.writeByte(writeBuf, object2.pet_is_multi);
        GameWriteTool.writeByte(writeBuf, object2.pet_round);
        GameWriteTool.writeByte(writeBuf, object2.pet_action);
        GameWriteTool.writeByte(writeBuf, object2.pet_next_action);
        GameWriteTool.writeInt(writeBuf, object2.pet_para);
        GameWriteTool.writeInt(writeBuf, object2.pet_next_para);
    }
    
    @Override
    public int cmd() {
        return 32985;
    }
}
