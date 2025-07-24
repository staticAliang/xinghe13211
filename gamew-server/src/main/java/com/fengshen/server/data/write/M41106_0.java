package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M41106_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_41106_0 object2 = (Vo_41106_0)object;
        GameWriteTool.writeByte(writeBuf, object2.month);
        GameWriteTool.writeInt(writeBuf, object2.startTime);
        GameWriteTool.writeInt(writeBuf, object2.endTime);
        GameWriteTool.writeByte(writeBuf, object2.count);
        GameWriteTool.writeString(writeBuf, object2.item_name0);
        GameWriteTool.writeByte(writeBuf, object2.item_amount0);
        GameWriteTool.writeByte(writeBuf, object2.item_gift0);
        GameWriteTool.writeString(writeBuf, object2.item_icon0);
        GameWriteTool.writeString(writeBuf, object2.item_name1);
        GameWriteTool.writeByte(writeBuf, object2.item_amount1);
        GameWriteTool.writeByte(writeBuf, object2.item_gift1);
        GameWriteTool.writeString(writeBuf, object2.item_icon1);
        GameWriteTool.writeString(writeBuf, object2.item_name2);
        GameWriteTool.writeByte(writeBuf, object2.item_amount2);
        GameWriteTool.writeByte(writeBuf, object2.item_gift2);
        GameWriteTool.writeString(writeBuf, object2.item_icon2);
        GameWriteTool.writeString(writeBuf, object2.item_name3);
        GameWriteTool.writeByte(writeBuf, object2.item_amount3);
        GameWriteTool.writeByte(writeBuf, object2.item_gift3);
        GameWriteTool.writeString(writeBuf, object2.item_icon3);
    }
    
    @Override
    public int cmd() {
        return 41106;
    }
}
