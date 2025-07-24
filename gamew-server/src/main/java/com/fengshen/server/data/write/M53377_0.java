package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M53377_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_53377_0 object2 = (Vo_53377_0)object;
        GameWriteTool.writeInt(writeBuf, object2.dsicountMonthPrice);
        GameWriteTool.writeInt(writeBuf, object2.dsicountQuaterPrice);
        GameWriteTool.writeInt(writeBuf, object2.dsicountYearPrice);
        GameWriteTool.writeInt(writeBuf, object2.startTime);
        GameWriteTool.writeInt(writeBuf, object2.endTime);
    }
    
    @Override
    public int cmd() {
        return 53377;
    }
}
