package com.fengshen.server.data.write;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.domain.BuildFieldsNew;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class M65527_6 extends BaseWrite {
    @Override
    protected void writeO(final ByteBuf writeBuf, final Object object) {
//        final ListVo_65527_0 listVo_65527_8 = GameUtil.a65527(chara);
        final Chara object2 = (Chara) object;
        GameWriteTool.writeInt(writeBuf, object2.id);
        GameWriteTool.writeShort(writeBuf, 9);
       // BuildFieldsNew.get("shenhun_data/exp").write(writeBuf, object2.shenHunDataExp);
        BuildFieldsNew.get("cash").write(writeBuf, object2.cash);
      //  BuildFieldsNew.get("voucher").write(writeBuf, object2.voucher);
        BuildFieldsNew.get("pot").write(writeBuf, object2.pot);
        BuildFieldsNew.get("tao").write(writeBuf, object2.tao);
        BuildFieldsNew.get("silver_coin").write(writeBuf, object2.silverCoin);
        BuildFieldsNew.get("silverCoin").write(writeBuf, object2.goldCoin);
        BuildFieldsNew.get("shuadao/ruyi_point").write(writeBuf, object2.ruyishuadao);
        BuildFieldsNew.get("shuadao/chongfeng-san").write(writeBuf, object2.shuadaochongfeng_san);

    }

    @Override
    public int cmd() {
        return 65527;
    }
}
