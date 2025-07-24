package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M20993_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_20993_0 object2 = (Vo_20993_0)object;
        GameWriteTool.writeByte(writeBuf, object2.is_startup);
        GameWriteTool.writeInt(writeBuf, object2.total_online);
        GameWriteTool.writeInt(writeBuf, object2.last_online);
        GameWriteTool.writeByte(writeBuf, object2.adult_status);
        GameWriteTool.writeByte(writeBuf, object2.switch3);
        GameWriteTool.writeByte(writeBuf, object2.switch5);
        GameWriteTool.writeByte(writeBuf, object2.second_enable);
        GameWriteTool.writeByte(writeBuf, object2.switch7);
        GameWriteTool.writeShort(writeBuf, object2.player_age);
        GameWriteTool.writeByte(writeBuf, object2.is_guest);
        GameWriteTool.writeShort(writeBuf, object2.small_age);
        GameWriteTool.writeInt(writeBuf, object2.young_coin_cost_limit);
        GameWriteTool.writeInt(writeBuf, object2.small_age_online);
        GameWriteTool.writeInt(writeBuf, object2.young_online);
    }
    
    @Override
    public int cmd() {
        return 20993;
    }
}
