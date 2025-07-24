package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M61593_0 extends BaseWrite<Vo_61593_0>
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Vo_61593_0 object2) {
        GameWriteTool.writeString(writeBuf, object2.ask_type);
    }
    
    @Override
    public int cmd() {
        return 61593;
    }
}
