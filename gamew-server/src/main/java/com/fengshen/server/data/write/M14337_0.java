package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M14337_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_14337_0 object2 = (Vo_14337_0)object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeInt(writeBuf, object2.type);
        GameWriteTool.writeInt(writeBuf, object2.life_plus);
        GameWriteTool.writeInt(writeBuf, object2.max_life_plus);
        GameWriteTool.writeInt(writeBuf, object2.mana_plus);
        GameWriteTool.writeInt(writeBuf, object2.max_mana_plus);
        GameWriteTool.writeInt(writeBuf, object2.phy_power_plus);
        GameWriteTool.writeInt(writeBuf, object2.mag_power_plus);
        GameWriteTool.writeInt(writeBuf, object2.speed_plus);
        GameWriteTool.writeInt(writeBuf, object2.def_plus);
        GameWriteTool.writeByte(writeBuf, object2.free);
    }
    
    @Override
    public int cmd() {
        return 14337;
    }
}
