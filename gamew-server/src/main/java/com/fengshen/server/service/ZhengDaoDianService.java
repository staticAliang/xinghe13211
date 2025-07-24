package com.fengshen.server.service;

import static com.fengshen.server.data.constant.TitleConst.TITLE_ZHENGDAODIAN_100;
import static com.fengshen.server.data.constant.TitleConst.TITLE_ZHENGDAODIAN_110;
import static com.fengshen.server.data.constant.TitleConst.TITLE_ZHENGDAODIAN_120;
import static com.fengshen.server.data.constant.TitleConst.TITLE_ZHENGDAODIAN_70;
import static com.fengshen.server.data.constant.TitleConst.TITLE_ZHENGDAODIAN_80;
import static com.fengshen.server.data.constant.TitleConst.TITLE_ZHENGDAODIAN_90;
import static com.fengshen.server.util.MsgUtil.KONG_PA_SHI_LI_BU_GOU;
import static com.fengshen.server.util.MsgUtil.WO_YAO_YI_DU_HU_FA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Npc;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_20689_0;
import com.fengshen.server.data.vo.Vo_MENU_LIST;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.MSG_MENU_LIST;
import com.fengshen.server.data.write.MSG_OVERCOME_NPC_INFO;
import com.fengshen.server.data.write.system.M65529_npc;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.fengshen.server.util.MsgUtil;
import com.fengshen.server.util.NpcIds;

/**
 * 证道殿
 */
@Service
public class ZhengDaoDianService {
    public static final int MAP_ID = 29002;
    public static final String[] titles = new String[]{TITLE_ZHENGDAODIAN_70, TITLE_ZHENGDAODIAN_80, TITLE_ZHENGDAODIAN_90, TITLE_ZHENGDAODIAN_100, TITLE_ZHENGDAODIAN_110, TITLE_ZHENGDAODIAN_120};
    public static final String[] contents = new String[]{MsgUtil.WO_YAO_TIAO_ZHAN_70, MsgUtil.WO_YAO_TIAO_ZHAN_80, MsgUtil.WO_YAO_TIAO_ZHAN_90, MsgUtil.WO_YAO_TIAO_ZHAN_100,
            MsgUtil.WO_YAO_TIAO_ZHAN_110, MsgUtil.WO_YAO_TIAO_ZHAN_120};
    public static final Integer[][] manPos = {{61,29}, {55,26}, {49,23}, {43,20}, {37,17}, {31,14}};
    public static final Integer[][] womanPos = {{43,38}, {37,35}, {31,32}, {25,29}, {19,26}, {13,23}};
    public static final String DEFAULT_PET_NAME = "羸弱的新晋护法";
    public static final String NPC_NAME = "证道殿";

    public static void openMenu(Chara chara, int npcId){
        CharaStatue charaStatue = getCharStaure(chara.polar, npcId);

        Npc npc = new Npc();
        npc.setId(npcId);
        npc.setIcon(GameUtil.getWaiguan(chara.polar, isMan(npcId)?1:2, null));
        npc.setName(charaStatue.name);

        String content = "助本门弟子修心证道乃是吾等职责，但需功力深厚者方能担当证道之人。"+
                MsgUtil.getTalk(ZhengDaoDianService.getContent(npcId))+
                MsgUtil.getTalk(WO_YAO_YI_DU_HU_FA)+
                MsgUtil.getTalk(KONG_PA_SHI_LI_BU_GOU);
        Vo_MENU_LIST menu_list_vo = GameUtil.MSG_MENU_LIST(npc, content);
        GameObjectChar.send(new MSG_MENU_LIST(), menu_list_vo);
    }

    public static Npc createNpc(int npcId){
        Chara chara = GameObjectChar.getGameObjectChar().chara;
        boolean isMan = isMan(npcId);
        int index = getIndex(npcId);

        Npc npc = new Npc();
        npc.setId(npcId);
        npc.setName(NPC_NAME+(npcId- NpcIds.ZHEGN_DAO_NPC_ID_BEGIN));
        npc.setMapId(MAP_ID);
        npc.setIcon(GameUtil.getWaiguan(chara.polar, isMan?1:2, null));
        Integer[] pos = null;
        if(isMan){
            pos = manPos[index];
        }else{
            pos = womanPos[index];
        }

        npc.setX(pos[0]);
        npc.setY(pos[1]);
        return npc;
    }

    public static void notifyPanel(Chara chara, int npcId){
        Vo_20689_0 vo_20689_0 = new Vo_20689_0();
        CharaStatue charaStatue = getCharStaure(chara.polar, npcId);
        int index = getIndex(npcId);

        vo_20689_0.npcId = npcId;
        vo_20689_0.isLeader = charaStatue.id == chara.id?1:0;
        vo_20689_0.name = charaStatue.name;
        vo_20689_0.title = titles[index];
        vo_20689_0.level = ""+charaStatue.level;
        vo_20689_0.party_name = charaStatue.partyName;
        /**
         * 套装icon
         */
        vo_20689_0.suit_icon = charaStatue.suit_icon;
        vo_20689_0.weapon_icon = charaStatue.weapon_icon;
        vo_20689_0.icon = charaStatue.waiguan;
        //仙魔光效
        vo_20689_0.xianmo = 0;
        //留言
        GameObjectChar leader = GameObjectCharMng.getGameObjectChar(charaStatue.id);
        if(null!=leader&&null!=leader.chara.zdd_Notice){
            vo_20689_0.signature = leader.chara.zdd_Notice;
        }else{
            vo_20689_0.signature = String.format("大家好，我是新晋护法");
        }
        vo_20689_0.vipLevel = 0;
        vo_20689_0.gender = charaStatue.sex;
        GameObjectChar.send(new MSG_OVERCOME_NPC_INFO(), vo_20689_0);
    }

    public  static int getNpcId(int sex, int level) {
        if (sex == 1)
            return NpcIds.ZHEGN_DAO_NPC_ID_BEGIN + (level - 70) / 10;
        else
            return NpcIds.ZHEGN_DAO_NPC_ID_BEGIN + 6 + (level - 70) / 10;
    }

    public static void challenge(Chara chara, int npcId){
        if (chara.zhengdaodiancishu >= GameConfig.config.getBaseConfig().getZhengdaodianNum()) {
            final Vo_20481_0 vo_20481_10 = new Vo_20481_0();
            vo_20481_10.msg = "您今日已挑战过证道殿！";
            vo_20481_10.time = (int)(System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_10);
            return;
        }
        int index = getIndex(npcId);
        CharaStatue charaStatue = getCharStaure(chara.polar, npcId);

        Npc npc = new Npc();
        npc.setId(npcId);
        npc.setIcon(GameUtil.getWaiguan(chara.polar, isMan(npcId)?1:2, null));
        npc.setName(charaStatue.name);

        if(chara.level<70+index*10){
            GameUtil.notifyOpenMenu(npc, MsgUtil.WU_XUE_SHANG_QIQN);
            return;
        }
        if(chara.level>70+index*10+9){
            GameUtil.notifyOpenMenu(npc, MsgUtil.DAO_LI_GAO_SHEN);
            return;
        }
        int npcSex = isMan(npcId)?1:2;
        if(npcSex!=chara.sex){
            GameUtil.notifyOpenMenu(npc, MsgUtil.NAN_NV_YOU_BIE);
            return;
        }
        // 添加证道殿战斗的对象
        final List<String> list2 = new ArrayList<>();
        list2.add("金系掌门");
        list2.add(GameUtil.tongttcw[new Random().nextInt(GameUtil.tongttcw.length)]);
        FightManager.goFightZhengdaodian(chara, list2, DEFAULT_PET_NAME);
        ++chara.zhengdaodiancishu;
    }

    public static boolean isMan(int npcId){
        return (npcId- NpcIds.ZHEGN_DAO_NPC_ID_BEGIN)<manPos.length;
    }
    private static int getIndex(int npcId){
        return (npcId- NpcIds.ZHEGN_DAO_NPC_ID_BEGIN) % manPos.length;
    }
    public static String getContent(int npcId){
        return contents[getIndex(npcId)];
    }


    public static String getNpcName(int polar, int sex, int level){
        return NPC_NAME+"_"+polar + "_" + sex +"_"+(level - 70) / 10;
    }

    public static String getNpcName(int polar, int npcId){
        return NPC_NAME+"_"+polar+"_"+(npcId- NpcIds.ZHEGN_DAO_NPC_ID_BEGIN);
    }

    public static void onEnterMap(GameObjectChar gameObjectChar){
        Chara chara = gameObjectChar.chara;
        //  加载男性队列
        for (int i = 0; i < 6; i ++) {
            String tmpName = (NPC_NAME + "_" + chara.polar + "_" + 1 + "_" + i);
            CharaStatue charaStatue = getCharStaure(tmpName);
            if (charaStatue == null) {
                Npc npc = new Npc();
                npc.setId(NpcIds.ZHEGN_DAO_NPC_ID_BEGIN+i);
                npc.setName(getNpcName(chara.polar, npc.getId()));
//                npc.setName(tmpName);
                npc.setMapId(MAP_ID);
                npc.setIcon(GameUtil.getWaiguan(chara.polar, 1, null));
                Integer[] pos = manPos[i];
                npc.setX(pos[0]);
                npc.setY(pos[1]);
                checkInitCharStatue(chara.polar, npc, titles[i]);
                gameObjectChar.sendOne(new M65529_npc(), npc);
            }
            else if (charaStatue != null && charaStatue.sex == 1) {
                Npc npc = new Npc();
                npc.setId(NpcIds.ZHEGN_DAO_NPC_ID_BEGIN+i);
                npc.setName(tmpName);
                npc.setMapId(MAP_ID);
                npc.setIcon(charaStatue.waiguan);
                Integer[] pos = manPos[i];
                npc.setX(pos[0]);
                npc.setY(pos[1]);
                checkInitCharStatue(chara.polar, npc, titles[i]);
                gameObjectChar.sendOne(new M65529_npc(), npc);
            }
        }

        // 加载女性队列
        for (int i = 0; i < 6; i ++) {
            String tmpName = (NPC_NAME + "_" + chara.polar + "_" + 2 + "_" + i);
            CharaStatue charaStatue = getCharStaure(tmpName);
            if (charaStatue == null ) {
                Npc npc = new Npc();
                npc.setId(NpcIds.ZHEGN_DAO_NPC_ID_BEGIN + i + manPos.length);
                npc.setName(getNpcName(chara.polar, npc.getId()));
//                npc.setName(tmpName);
                npc.setMapId(MAP_ID);
                npc.setIcon(GameUtil.getWaiguan(chara.polar, 2, null));
                Integer[] pos = womanPos[i];
                npc.setX(pos[0]);
                npc.setY(pos[1]);
                checkInitCharStatue(chara.polar, npc, titles[i]);
                gameObjectChar.sendOne(new M65529_npc(), npc);
            } else if (charaStatue != null && charaStatue.sex == 2) {
                    Npc npc = new Npc();
                    npc.setId(NpcIds.ZHEGN_DAO_NPC_ID_BEGIN + i + manPos.length);
                    npc.setName(tmpName);
                    npc.setMapId(MAP_ID);
                    npc.setIcon(charaStatue.waiguan);
                    Integer[] pos = womanPos[i];
                    npc.setX(pos[0]);
                    npc.setY(pos[1]);
                    checkInitCharStatue(chara.polar, npc, titles[i]);
                    gameObjectChar.sendOne(new M65529_npc(), npc);
                }
            }

//        //男
//        for(int i=0;i<manPos.length;++i){
//            Npc npc = new Npc();
//            npc.setId(NpcIds.ZHEGN_DAO_NPC_ID_BEGIN+i);
//            npc.setName(getNpcName(chara.polar, npc.getId()));
//            npc.setMapId(MAP_ID);
//            npc.setIcon(GameUtil.getWaiguan(chara.polar, 1));
//            Integer[] pos = manPos[i];
//            npc.setX(pos[0]);
//            npc.setY(pos[1]);
//            checkInitCharStatue(chara.polar, npc, titles[i]);
//            gameObjectChar.sendOne(new M65529_npc(), npc);
//        }
//        //女
//        for(int i=0;i<womanPos.length;++i){
//            Npc npc = new Npc();
//            npc.setId(NpcIds.ZHEGN_DAO_NPC_ID_BEGIN+i+manPos.length);
//            npc.setName(getNpcName(chara.polar, npc.getId()));
//            npc.setMapId(MAP_ID);
//            npc.setIcon(GameUtil.getWaiguan(chara.polar, 2));
//            Integer[] pos = womanPos[i];
//            npc.setX(pos[0]);
//            npc.setY(pos[1]);
//
//            checkInitCharStatue(chara.polar, npc, titles[i]);
//
//            gameObjectChar.sendOne(new M65529_npc(), npc);
//        }
    }

    private static void checkInitCharStatue(int polar, Npc npc, String title){
        CharaStatue charaStatue = getCharStaure(polar, npc.getId());
        if(null == charaStatue){
            charaStatue = new CharaStatue();
            charaStatue.name = DEFAULT_PET_NAME;
            charaStatue.waiguan = npc.getIcon();
            charaStatue.chengHao = title;
            charaStatue.level = 50;
            charaStatue.sex = isMan(npc.getId())?1:2;

            charaStatue.fangyu = 1000;
            charaStatue.fashang = 1000;
            charaStatue.phy_power = 1000;

            putCharStaure(polar, npc.getId(), charaStatue);
        }
    }

    private static CharaStatue getCharStaure(String name){
        return CharaStatueService.getCharStaure(name);
    }

    private static CharaStatue getCharStaure(int polar, int npcId){
        return CharaStatueService.getCharStaure(getNpcName(polar, npcId));
    }
    private static void putCharStaure(int polar, int npcId, CharaStatue charaStatue){
        CharaStatueService.putCache(getNpcName(polar, npcId), charaStatue);
    }

    public static void changeNotice(int id, String msg){
        GameObjectChar.getGameObjectChar().chara.zdd_Notice = msg;
    }

}
