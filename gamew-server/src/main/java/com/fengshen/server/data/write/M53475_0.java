package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M53475_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_53475_0 object2 = (Vo_53475_0)object;
        GameWriteTool.writeString(writeBuf, object2.name0);
        GameWriteTool.writeShort(writeBuf, object2.count0);
        GameWriteTool.writeShort(writeBuf, object2.activeValue0);
        GameWriteTool.writeString(writeBuf, object2.timeStr0);
        GameWriteTool.writeString(writeBuf, object2.name1);
        GameWriteTool.writeShort(writeBuf, object2.count1);
        GameWriteTool.writeShort(writeBuf, object2.activeValue1);
        GameWriteTool.writeString(writeBuf, object2.timeStr1);
        GameWriteTool.writeString(writeBuf, object2.name2);
        GameWriteTool.writeShort(writeBuf, object2.count2);
        GameWriteTool.writeShort(writeBuf, object2.activeValue2);
        GameWriteTool.writeString(writeBuf, object2.timeStr2);
        GameWriteTool.writeString(writeBuf, object2.name3);
        GameWriteTool.writeShort(writeBuf, object2.count3);
        GameWriteTool.writeShort(writeBuf, object2.activeValue3);
        GameWriteTool.writeString(writeBuf, object2.timeStr3);
        GameWriteTool.writeString(writeBuf, object2.name4);
        GameWriteTool.writeShort(writeBuf, object2.count4);
        GameWriteTool.writeShort(writeBuf, object2.activeValue4);
        GameWriteTool.writeString(writeBuf, object2.timeStr4);
        GameWriteTool.writeString(writeBuf, object2.name5);
        GameWriteTool.writeShort(writeBuf, object2.count5);
        GameWriteTool.writeShort(writeBuf, object2.activeValue5);
        GameWriteTool.writeString(writeBuf, object2.timeStr5);
        GameWriteTool.writeString(writeBuf, object2.name6);
        GameWriteTool.writeShort(writeBuf, object2.count6);
        GameWriteTool.writeShort(writeBuf, object2.activeValue6);
        GameWriteTool.writeString(writeBuf, object2.timeStr6);
        GameWriteTool.writeString(writeBuf, object2.name7);
        GameWriteTool.writeShort(writeBuf, object2.count7);
        GameWriteTool.writeShort(writeBuf, object2.activeValue7);
        GameWriteTool.writeString(writeBuf, object2.timeStr7);
        GameWriteTool.writeString(writeBuf, object2.name8);
        GameWriteTool.writeShort(writeBuf, object2.count8);
        GameWriteTool.writeShort(writeBuf, object2.activeValue8);
        GameWriteTool.writeString(writeBuf, object2.timeStr8);
        GameWriteTool.writeString(writeBuf, object2.name9);
        GameWriteTool.writeShort(writeBuf, object2.count9);
        GameWriteTool.writeShort(writeBuf, object2.activeValue9);
        GameWriteTool.writeString(writeBuf, object2.timeStr9);
        GameWriteTool.writeString(writeBuf, object2.name10);
        GameWriteTool.writeShort(writeBuf, object2.count10);
        GameWriteTool.writeShort(writeBuf, object2.activeValue10);
        GameWriteTool.writeString(writeBuf, object2.timeStr10);
        GameWriteTool.writeString(writeBuf, object2.name11);
        GameWriteTool.writeShort(writeBuf, object2.count11);
        GameWriteTool.writeShort(writeBuf, object2.activeValue11);
        GameWriteTool.writeString(writeBuf, object2.timeStr11);
        GameWriteTool.writeString(writeBuf, object2.name12);
        GameWriteTool.writeShort(writeBuf, object2.count12);
        GameWriteTool.writeShort(writeBuf, object2.activeValue12);
        GameWriteTool.writeString(writeBuf, object2.timeStr12);
        GameWriteTool.writeString(writeBuf, object2.name13);
        GameWriteTool.writeShort(writeBuf, object2.count13);
        GameWriteTool.writeShort(writeBuf, object2.activeValue13);
        GameWriteTool.writeString(writeBuf, object2.timeStr13);
        GameWriteTool.writeString(writeBuf, object2.name14);
        GameWriteTool.writeShort(writeBuf, object2.count14);
        GameWriteTool.writeShort(writeBuf, object2.activeValue14);
        GameWriteTool.writeString(writeBuf, object2.timeStr14);
        GameWriteTool.writeString(writeBuf, object2.name15);
        GameWriteTool.writeShort(writeBuf, object2.count15);
        GameWriteTool.writeShort(writeBuf, object2.activeValue15);
        GameWriteTool.writeString(writeBuf, object2.timeStr15);
        GameWriteTool.writeString(writeBuf, object2.name16);
        GameWriteTool.writeShort(writeBuf, object2.count16);
        GameWriteTool.writeShort(writeBuf, object2.activeValue16);
        GameWriteTool.writeString(writeBuf, object2.timeStr16);
        GameWriteTool.writeString(writeBuf, object2.name17);
        GameWriteTool.writeShort(writeBuf, object2.count17);
        GameWriteTool.writeShort(writeBuf, object2.activeValue17);
        GameWriteTool.writeString(writeBuf, object2.timeStr17);
        GameWriteTool.writeString(writeBuf, object2.name18);
        GameWriteTool.writeShort(writeBuf, object2.count18);
        GameWriteTool.writeShort(writeBuf, object2.activeValue18);
        GameWriteTool.writeString(writeBuf, object2.timeStr18);
        GameWriteTool.writeString(writeBuf, object2.name19);
        GameWriteTool.writeShort(writeBuf, object2.count19);
        GameWriteTool.writeShort(writeBuf, object2.activeValue19);
        GameWriteTool.writeString(writeBuf, object2.timeStr19);
        GameWriteTool.writeString(writeBuf, object2.name20);
        GameWriteTool.writeShort(writeBuf, object2.count20);
        GameWriteTool.writeShort(writeBuf, object2.activeValue20);
        GameWriteTool.writeString(writeBuf, object2.timeStr20);
        GameWriteTool.writeString(writeBuf, object2.name21);
        GameWriteTool.writeShort(writeBuf, object2.count21);
        GameWriteTool.writeShort(writeBuf, object2.activeValue21);
        GameWriteTool.writeString(writeBuf, object2.timeStr21);
        GameWriteTool.writeString(writeBuf, object2.name22);
        GameWriteTool.writeShort(writeBuf, object2.count22);
        GameWriteTool.writeShort(writeBuf, object2.activeValue22);
        GameWriteTool.writeString(writeBuf, object2.timeStr22);
    }
    
    @Override
    public int cmd() {
        return 53475;
    }
}
