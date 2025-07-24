package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;
import java.util.*;

@Service
public class M65527_4 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final List object2 = (List)object;
        GameWriteTool.writeInt(writeBuf, (Integer) object2.get(0));
        GameWriteTool.writeShort(writeBuf, 1);
        BuildFields.get("deadline").write(writeBuf, object2.get(1));
    }
    
    @Override
    public int cmd() {
        return 65527;
    }
}
