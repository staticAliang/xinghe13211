package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.db.domain.*;
import com.fengshen.server.data.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;

@Service
public class M65503_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final List object2 = (List)object;
        if (object2.get(0) instanceof MedicineShop) {
            GameWriteTool.writeInt(writeBuf, 15907);
            GameWriteTool.writeShort(writeBuf, 0);
        }
        else {
            GameWriteTool.writeInt(writeBuf, 15908);
            GameWriteTool.writeShort(writeBuf, 1);
        }
        GameWriteTool.writeShort(writeBuf, 100);
        GameWriteTool.writeShort(writeBuf, 0);
        GameWriteTool.writeShort(writeBuf, 6);
        GameWriteTool.writeShort(writeBuf, object2.size());
        for (int i = 0; i < object2.size(); ++i) {
            if (object2.get(i) instanceof MedicineShop) {
                final MedicineShop obj = (MedicineShop)object2.get(i);
                GameWriteTool.writeShort(writeBuf, obj.getGoodsNo());
                GameWriteTool.writeInt(writeBuf, obj.getPayType());
                GameWriteTool.writeShort(writeBuf, obj.getItemcount());
                GameWriteTool.writeString(writeBuf, obj.getName());
                GameWriteTool.writeInt(writeBuf, obj.getValue());
                GameWriteTool.writeShort(writeBuf, obj.getLevel());
                GameWriteTool.writeByte(writeBuf, obj.getType());
            }
            else {
                final GroceriesShop obj2 = (GroceriesShop)object2.get(i);
                GameWriteTool.writeShort(writeBuf, obj2.getGoodsNo());
                GameWriteTool.writeInt(writeBuf, obj2.getPayType());
                GameWriteTool.writeShort(writeBuf, obj2.getItemcount());
                GameWriteTool.writeString(writeBuf, obj2.getName());
                GameWriteTool.writeInt(writeBuf, obj2.getValue());
                GameWriteTool.writeShort(writeBuf, obj2.getLevel());
                GameWriteTool.writeByte(writeBuf, obj2.getType());
            }
        }
        GameWriteTool.writeShort(writeBuf, 0);
    }
    
    @Override
    public int cmd() {
        return 65503;
    }
}
