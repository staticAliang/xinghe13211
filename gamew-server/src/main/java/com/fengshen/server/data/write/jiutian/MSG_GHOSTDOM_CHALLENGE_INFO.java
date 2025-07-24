package com.fengshen.server.data.write.jiutian;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;

public class MSG_GHOSTDOM_CHALLENGE_INFO extends BaseWrite<Object> {
    @Override
    protected void writeO(final ByteBuf buff, final Object object) {
        Vo_53951 vo_33321 = (Vo_53951) object;
        GameWriteTool.writeByte(buff, vo_33321.heightestLevel);
        GameWriteTool.writeByte(buff, vo_33321.cengshu);
        GameWriteTool.writeInt(buff, vo_33321.icon);
        GameWriteTool.writeShort(buff, vo_33321.rewardInfo.size());
        for (String str : vo_33321.rewardInfo) {
            GameWriteTool.writeString(buff, str);
        }
        GameWriteTool.writeByte(buff, vo_33321.bonus_flag);
    }

    @Override
    public int cmd() {
        return 53951;
    }
}
