package com.fengshen.server.process.luoshu;

import com.fengshen.server.data.vo.luoshu.MonsterData;
import com.fengshen.server.data.vo.luoshu.ShenjiangData;
import com.fengshen.server.data.vo.luoshu.Vo_33507_0;
import com.fengshen.server.data.vo.luoshu.Vo_54145_0;
import com.fengshen.server.data.write.luoshu.MSG_LUOSHU_DATA;
import com.fengshen.server.data.write.luoshu.UpdatejingyanWrite;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.netty.BaseWrite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CMD_Luoshu_Data implements GameHandler {
    @Override
    public void process(ChannelHandlerContext paramChannelHandlerContext, ByteBuf byteBuf) {
        // 获取用户信息
        GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
//        int exp = 72000;
        int exp = 0;
        int xiaolv = 50;
        GameCommonUtil.dialogOk("伏妖经验每日限领一次！");
        Chara chara = gameObjectChar.chara;
        Vo_54145_0 vo_54145_0;
//        if (chara.getIslingqufuyao()) {
//            gameObjectChar.chara.setFuyao(0);
        vo_54145_0 = setLuoshuData( 0, xiaolv);
//        } else {
//            vo_54145_0 = setLuoshuData(exp, xiaolv);
//        }
        chara.setFuyaoExp(exp);
        chara.setFuyaoXiaolv(xiaolv);
        Vo_33507_0 vo_33507_0 = new Vo_33507_0();
        Map<Object,Object> map = new HashMap<>();
        map.put("luoshu_exp",chara.getLuoshuExp());
        map.put("luoshu_level",chara.getLuoshuLevel());
        vo_33507_0.setMap(map);
        GameObjectCharMng.save(gameObjectChar);
        GameObjectChar.send((BaseWrite)new UpdatejingyanWrite(), vo_33507_0);
        GameObjectChar.send((BaseWrite)new MSG_LUOSHU_DATA(), vo_54145_0);
    }

    @Override
    public int cmd() {
        return 54150;
    }

    public Vo_54145_0 setLuoshuData(int exp, int xiaoLv) {
        Vo_54145_0 respreqluoshu = new Vo_54145_0();
        respreqluoshu.setGuanka(1);
        respreqluoshu.setType(0);
        respreqluoshu.setCguanka(2);
        respreqluoshu.setMonsterData(getfuyaoMonster());
        respreqluoshu.setListshenjiang(getFuyaoShenjiang());
        respreqluoshu.setJingyan(exp);
        respreqluoshu.setFf1(-1);
        respreqluoshu.setFf2(-1);
        respreqluoshu.setDangqianxiaolv(xiaoLv);
        respreqluoshu.setTargetCount(0);
        respreqluoshu.setUnkonwnint(1601996942);
        respreqluoshu.setIsok(0);
        return respreqluoshu;
    }

    public List<MonsterData> getfuyaoMonster() {
        ArrayList<MonsterData> arrayList = new ArrayList<>();
        MonsterData monsterData1 = new MonsterData();
        MonsterData monsterData2 = new MonsterData();
        monsterData1.setName("魔猪精");
        monsterData1.setMagpower(6208);
        monsterData1.setPhypower(1080);
        monsterData1.setSpeed(960);
        monsterData1.setDefense(0);
        monsterData1.setShuxing5(300);
        monsterData1.setShuxing6(0);
        monsterData1.setShuxing7(0);
        monsterData1.setShuxing8(0);
        monsterData1.setShuxing9(0);
        monsterData1.setShuxing10(0);
        monsterData1.setShuxing11(0);
        monsterData1.setSkillname("物攻");
        monsterData2.setName("魔猪精");
        monsterData2.setMagpower(6208);
        monsterData2.setPhypower(1080);
        monsterData2.setSpeed(960);
        monsterData2.setDefense(0);
        monsterData2.setShuxing5(300);
        monsterData2.setShuxing6(0);
        monsterData2.setShuxing7(0);
        monsterData2.setShuxing8(0);
        monsterData2.setShuxing9(0);
        monsterData2.setShuxing10(0);
        monsterData2.setShuxing11(0);
        monsterData2.setSkillname("物攻");
        arrayList.add(monsterData1);
        arrayList.add(monsterData2);
        return arrayList;
    }

    public List<ShenjiangData> getFuyaoShenjiang() {
        ArrayList<ShenjiangData> arrayList = new ArrayList<>();
        ShenjiangData ShenjiangData = new ShenjiangData();
        ShenjiangData.setName("杨戬");
        ShenjiangData.setMagpower(6208);
        ShenjiangData.setPhypower(1080);
        ShenjiangData.setSpeed(960);
        ShenjiangData.setDefense(0);
        ShenjiangData.setShuxing5(300);
        ShenjiangData.setShuxing6(0);
        ShenjiangData.setShuxing7(0);
        ShenjiangData.setShuxing8(0);
        ShenjiangData.setShuxing9(0);
        ShenjiangData.setShuxing10(0);
        ShenjiangData.setSkillname("物攻");
        ShenjiangData.setIschuzhan((byte)1);
        ShenjiangData.setPos((byte)1);
        arrayList.add(ShenjiangData);
        return arrayList;
    }
}

