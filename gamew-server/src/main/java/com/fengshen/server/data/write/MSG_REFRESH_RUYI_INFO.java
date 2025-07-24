package com.fengshen.server.data.write;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_45319_0;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;


@Service
public class MSG_REFRESH_RUYI_INFO extends BaseWrite {
    @Override
    protected void writeO(ByteBuf writeBuf, Object object) {
        Vo_45319_0 object1=(Vo_45319_0)object;
        GameWriteTool.writeByte(writeBuf,object1.state);
    }

    @Override
    public int cmd() {
        return 45319;
    }
}

