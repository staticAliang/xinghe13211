package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45608_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_45608_0 object2 = (Vo_45608_0)object;
        GameWriteTool.writeShort(writeBuf, object2.count);
        GameWriteTool.writeString(writeBuf, object2.name0);
        GameWriteTool.writeInt(writeBuf, object2.goods_price0);
        GameWriteTool.writeString(writeBuf, object2.name1);
        GameWriteTool.writeInt(writeBuf, object2.goods_price1);
        GameWriteTool.writeString(writeBuf, object2.name2);
        GameWriteTool.writeInt(writeBuf, object2.goods_price2);
        GameWriteTool.writeString(writeBuf, object2.name3);
        GameWriteTool.writeInt(writeBuf, object2.goods_price3);
        GameWriteTool.writeString(writeBuf, object2.name4);
        GameWriteTool.writeInt(writeBuf, object2.goods_price4);
        GameWriteTool.writeString(writeBuf, object2.name5);
        GameWriteTool.writeInt(writeBuf, object2.goods_price5);
        GameWriteTool.writeString(writeBuf, object2.name6);
        GameWriteTool.writeInt(writeBuf, object2.goods_price6);
        GameWriteTool.writeString(writeBuf, object2.name7);
        GameWriteTool.writeInt(writeBuf, object2.goods_price7);
        GameWriteTool.writeString(writeBuf, object2.name8);
        GameWriteTool.writeInt(writeBuf, object2.goods_price8);
        GameWriteTool.writeString(writeBuf, object2.name9);
        GameWriteTool.writeInt(writeBuf, object2.goods_price9);
        GameWriteTool.writeString(writeBuf, object2.name10);
        GameWriteTool.writeInt(writeBuf, object2.goods_price10);
        GameWriteTool.writeString(writeBuf, object2.name11);
        GameWriteTool.writeInt(writeBuf, object2.goods_price11);
        GameWriteTool.writeString(writeBuf, object2.name12);
        GameWriteTool.writeInt(writeBuf, object2.goods_price12);
        GameWriteTool.writeShort(writeBuf, object2.count1);
    }
    
    @Override
    public int cmd() {
        return 45608;
    }
}
