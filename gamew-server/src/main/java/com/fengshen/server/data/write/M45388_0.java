package com.fengshen.server.data.write;

import org.springframework.stereotype.*;

import com.fengshen.server.netty.*;

import io.netty.buffer.*;

@Service
public class M45388_0 extends BaseWrite
{
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
    }
    
    @Override
    public int cmd() {
        return 45388;
    }
}
