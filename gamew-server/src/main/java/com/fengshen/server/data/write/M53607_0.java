package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M53607_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
    	GameWriteTool.writeByte(writeBuf, (Integer)object);
    }
    
    @Override
    public int cmd() {
        return 53607;
    }
}
