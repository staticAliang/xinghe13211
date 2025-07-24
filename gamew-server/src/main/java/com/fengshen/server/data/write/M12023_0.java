package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;
// 宝宝天书的类
@Service
public class M12023_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final List<Vo_12023_0> object2 = (List<Vo_12023_0>)object;
        if (object2.size() > 0) {
            GameWriteTool.writeInt(writeBuf, object2.get(0).owner_id);
            GameWriteTool.writeInt(writeBuf, object2.get(0).id);
            GameWriteTool.writeShort(writeBuf, object2.size());
            for (int i = 0; i < object2.size(); ++i) {
                final Vo_12023_0 vo_12023_0 = object2.get(i);
                GameWriteTool.writeString(writeBuf, vo_12023_0.god_book_skill_name);
                GameWriteTool.writeShort(writeBuf, vo_12023_0.god_book_skill_level);
                GameWriteTool.writeShort(writeBuf, vo_12023_0.god_book_skill_power);
                GameWriteTool.writeByte(writeBuf, vo_12023_0.god_book_skill_disabled);
            }
        }
    }
    
    @Override
    public int cmd() {
        return 12023;
    }
}
