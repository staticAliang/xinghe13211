package com.fengshen.server.data.write.luoshu;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.luoshu.MonsterData;
import com.fengshen.server.data.vo.luoshu.ShenjiangData;
import com.fengshen.server.data.vo.luoshu.Vo_54145_0;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;

public class MSG_LUOSHU_DATA extends BaseWrite {
    @Override
    protected void writeO(ByteBuf writeBuf, Object paramT) {
        Vo_54145_0 vo_54145_0 = (Vo_54145_0) paramT;
        GameWriteTool.writeShort(writeBuf, vo_54145_0.getGuanka());
        GameWriteTool.writeByte(writeBuf,vo_54145_0.getType());
        GameWriteTool.writeShort(writeBuf, vo_54145_0.getCguanka());
        GameWriteTool.writeShort(writeBuf, vo_54145_0.getMonsterData().size());
        for (MonsterData monsterDatum : vo_54145_0.getMonsterData()) {
            GameWriteTool.writeString(writeBuf,monsterDatum.getName());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getMagpower());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getPhypower());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getSpeed());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getDefense());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing5());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing6());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing7());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing8());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing9());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing10());
            GameWriteTool.writeInt(writeBuf,monsterDatum.getShuxing11());
            GameWriteTool.writeString(writeBuf,monsterDatum.getSkillname());
        }
        GameWriteTool.writeShort(writeBuf, vo_54145_0.getListshenjiang().size());
        for (ShenjiangData shenjiangData : vo_54145_0.getListshenjiang()) {
            GameWriteTool.writeString(writeBuf,shenjiangData.getName());
            GameWriteTool.writeShort(writeBuf,shenjiangData.getType());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getMagpower());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getPhypower());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getSpeed());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getDefense());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getShuxing5());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getShuxing6());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getShuxing7());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getShuxing8());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getShuxing9());
            GameWriteTool.writeInt(writeBuf,shenjiangData.getShuxing10());
            GameWriteTool.writeString(writeBuf,shenjiangData.getSkillname());
            GameWriteTool.writeByte(writeBuf,shenjiangData.getIschuzhan());
            GameWriteTool.writeByte(writeBuf,shenjiangData.getPos());
        }
        GameWriteTool.writeInt(writeBuf,vo_54145_0.getJingyan());
        GameWriteTool.writeInt(writeBuf,vo_54145_0.getFf1());
        GameWriteTool.writeInt(writeBuf,vo_54145_0.getFf2());
        GameWriteTool.writeInt(writeBuf,vo_54145_0.getDangqianxiaolv());
        GameWriteTool.writeByte(writeBuf,vo_54145_0.getTargetCount());
        GameWriteTool.writeInt(writeBuf,vo_54145_0.getUnkonwnint());
        GameWriteTool.writeByte(writeBuf,vo_54145_0.getIsok());

    }

    @Override
    public int cmd() {
        return 54145;
    }
}
