package com.fengshen.server.process.luoshu;

import com.fengshen.server.data.vo.luoshu.Vo_33507_0;
import com.fengshen.server.data.write.luoshu.UpdatejingyanWrite;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CMD_LUOSHU_UPDATE  implements GameHandler {

    public static int[] luoshudata = new int[] {
            0, 13, 88, 203, 578, 1073, 1688, 2423, 3278, 4253,
            5578, 7381, 8782, 10302, 11323, 12383, 13483, 14994, 16584, 18255,
            20220, 22286, 27122, 29501, 31980, 34559, 37238, 40017, 42896, 45875,
            49584, 53433, 57423, 63512, 67838, 72304, 76910, 81655, 93849, 99343,
            107274, 115469, 123928, 132651, 148876, 158286, 167960, 177898, 188100, 198566,
            212695, 227220, 242141, 257458, 283963, 300267, 329559, 347399, 365654, 384323,
            407650, 431528, 455959, 489899, 515573, 541798, 568575, 595905, 623786, 652219,
            687783, 724082, 771326, 809236, 847882, 887264, 927383, 968237, 1009827, 1052153,
            1102714, 1212347, 1267122, 1322857, 1379552, 1437208, 1495823, 1555398, 1615933, 1677428,
            1753174, 1827834, 1903695, 1980755, 2059015, 2138476, 2219136, 2300996, 2384057, 2468317 };

    @Override
    public void process(ChannelHandlerContext chx, ByteBuf byteBuf) {
        // 获取用户信息
        GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
        Chara chara = gameObjectChar.chara;
        int level = chara.getLuoshuLevel();
        int needexp = luoshudata[level];
        if (chara.getLuoshuExp() < needexp)
            return;
        int exp = chara.getLuoshuExp() - needexp;
        if ((level + 1) % 5 == 0) {
            int index = (level + 1) / 5 % 3;
            if (index == 1) {
                chara.setLuoshuDefense(chara.getLuoshuDefense() + 3800);
//                chara.setWiz(chara.getWiz() + 2880);
            } else if (index == 2) {
                chara.setLuoshuSpeed(chara.getLuoshuSpeed() + 300);
//                chara.setParry(chara.getSpeed() + 167);
            } else {
                chara.setLuoshuMagpower(chara.getLuoshuMagpower() + 5190);
//                chara.setMana(chara.getMana() + 3650);
                chara.setLuoshumPhypower(chara.getLuoshumPhypower() + 6200);
//                chara.setAccurate(chara.getAccurate() + 4500);
            }
        }
        Vo_33507_0 vo_33507_0 = new Vo_33507_0();
        Map<Object,Object> map = new HashMap<>();
        map.put("luoshu_exp",exp);
        map.put("luoshu_level",level + 1);
        vo_33507_0.setMap(map);
        chara.setLuoshuExp(exp);
        chara.setLuoshuLevel((short)(chara.getLuoshuLevel() + 1));
        GameObjectCharMng.save(gameObjectChar);
        GameObjectChar.send((BaseWrite)new UpdatejingyanWrite(), vo_33507_0);
        GameUtil.a65511(gameObjectChar);
        for (int j = 0; j < chara.pets.size(); j++) {
            Petbeibao petbeibao = chara.pets.get(j);
//            List<PetShuXing> petShuXing = petbeibao.petShuXing;
            List<Petbeibao> list = new ArrayList<>();
            list.add(petbeibao);
            GameObjectChar.send((BaseWrite) new MSG_UPDATE_PETS(), list);
        }

    }

    @Override
    public int cmd() {
        return 33508;
    }
}
