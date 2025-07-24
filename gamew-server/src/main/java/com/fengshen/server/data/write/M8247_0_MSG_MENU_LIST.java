package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_8247_0;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class M8247_0_MSG_MENU_LIST extends BaseWrite {
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
        final Vo_8247_0 object2 = (Vo_8247_0) object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeInt(writeBuf, object2.portrait);
        GameWriteTool.writeShort(writeBuf, object2.pic_no);
        GameWriteTool.writeString2(writeBuf, object2.content);
        GameWriteTool.writeString(writeBuf, object2.secret_key);
        GameWriteTool.writeString(writeBuf, object2.name);
        GameWriteTool.writeByte(writeBuf, object2.attrib);
    }

    @Override
    public int cmd() {
        return 8247;
    }
}
