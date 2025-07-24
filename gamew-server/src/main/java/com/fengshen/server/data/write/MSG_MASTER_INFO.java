package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.Vo_61613_0;
import com.fengshen.server.data.vo.Vo_BuildField;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 掌门信息
 */
public class MSG_MASTER_INFO extends BaseWrite {
    private static M_BuildField m_buildField = new M_BuildField();
    @Override
    protected void writeO(ByteBuf writeBuf, Object object) {
        Vo_61613_0 vo_61613_0 = (Vo_61613_0) object;
        GameWriteTool.writeInt(writeBuf, vo_61613_0.polar);
        GameWriteTool.writeShort(writeBuf, vo_61613_0.isLeader);
        GameWriteTool.writeShort(writeBuf, 11);//个数


        m_buildField.writeO(writeBuf, Vo_BuildField.stringc(1, vo_61613_0.name));//name
        m_buildField.writeO(writeBuf, Vo_BuildField.stringc(36, vo_61613_0.title));//title
        m_buildField.writeO(writeBuf, Vo_BuildField.stringc(31, vo_61613_0.level));//level
        m_buildField.writeO(writeBuf, Vo_BuildField.stringc(196, vo_61613_0.party_name));//party_name
        m_buildField.writeO(writeBuf, Vo_BuildField.int32(291, vo_61613_0.suit_icon));//suit_icon
        m_buildField.writeO(writeBuf, Vo_BuildField.int32(290, vo_61613_0.weapon_icon));//weapon_icon
        m_buildField.writeO(writeBuf, Vo_BuildField.int32(40, vo_61613_0.icon));//icon
        m_buildField.writeO(writeBuf, Vo_BuildField.int32(341, vo_61613_0.xianmo));//upgrade/type
        m_buildField.writeO(writeBuf, Vo_BuildField.stringc(90, vo_61613_0.signature));//signature
        m_buildField.writeO(writeBuf, Vo_BuildField.int32(880, vo_61613_0.vipLevel));//insider_level
        m_buildField.writeO(writeBuf, Vo_BuildField.int32(29, vo_61613_0.gender));//gender
    }

    public int cmd() {
        return 61613;
    }
}