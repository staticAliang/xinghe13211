package com.fengshen.server.data.write.jiutian;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;

public class MSG_JIUTIAN_ZHENJUN extends BaseWrite<Object> {
    @Override
    protected void writeO(final ByteBuf buff, final Object object) {
        Vo_33321 vo_33321 = (Vo_33321) object;
        GameWriteTool.writeByte(buff, vo_33321.curCheckpoint);
        GameWriteTool.writeByte(buff, vo_33321.openMax);
        GameWriteTool.writeByte(buff, vo_33321.is_open);

    }

    @Override
    public int cmd() {
        return 33321;
    }
}
