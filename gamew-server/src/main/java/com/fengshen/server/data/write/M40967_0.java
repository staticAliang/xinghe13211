package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.db.domain.*;
import com.fengshen.server.data.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;

@Service
public class M40967_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final List<CreepsStore> list = (List<CreepsStore>)object;
        GameWriteTool.writeByte(writeBuf, 1);
        GameWriteTool.writeShort(writeBuf, list.size());
        for (int i = 0; i < list.size(); ++i) {
            GameWriteTool.writeString(writeBuf, list.get(i).getName());
            GameWriteTool.writeInt(writeBuf, list.get(i).getPrice());
            GameWriteTool.writeString(writeBuf, "cash");
        }
    }
    
    @Override
    public int cmd() {
        return 40967;
    }
}
