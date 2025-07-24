package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M53267_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_53267_0 object2 = (Vo_53267_0)object;
        GameWriteTool.writeShort(writeBuf, object2.count);
        GameWriteTool.writeString(writeBuf, object2.barcode0);
        GameWriteTool.writeInt(writeBuf, object2.sale_quota0);
        GameWriteTool.writeInt(writeBuf, object2.toMoney0);
        GameWriteTool.writeInt(writeBuf, object2.costCoin0);
        GameWriteTool.writeString(writeBuf, object2.barcode1);
        GameWriteTool.writeInt(writeBuf, object2.sale_quota1);
        GameWriteTool.writeInt(writeBuf, object2.toMoney1);
        GameWriteTool.writeInt(writeBuf, object2.costCoin1);
        GameWriteTool.writeString(writeBuf, object2.barcode2);
        GameWriteTool.writeInt(writeBuf, object2.sale_quota2);
        GameWriteTool.writeInt(writeBuf, object2.toMoney2);
        GameWriteTool.writeInt(writeBuf, object2.costCoin2);
        GameWriteTool.writeString(writeBuf, object2.barcode3);
        GameWriteTool.writeInt(writeBuf, object2.sale_quota3);
        GameWriteTool.writeInt(writeBuf, object2.toMoney3);
        GameWriteTool.writeInt(writeBuf, object2.costCoin3);
        GameWriteTool.writeString(writeBuf, object2.barcode4);
        GameWriteTool.writeInt(writeBuf, object2.sale_quota4);
        GameWriteTool.writeInt(writeBuf, object2.toMoney4);
        GameWriteTool.writeInt(writeBuf, object2.costCoin4);
        GameWriteTool.writeString(writeBuf, object2.barcode5);
        GameWriteTool.writeInt(writeBuf, object2.sale_quota5);
        GameWriteTool.writeInt(writeBuf, object2.toMoney5);
        GameWriteTool.writeInt(writeBuf, object2.costCoin5);
    }
    
    @Override
    public int cmd() {
        return 53267;
    }
}
