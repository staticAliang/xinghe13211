package com.fengshen.server.service;

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

import static com.fengshen.server.util.MsgUtil.KONG_PA_SHI_LI_BU_GOU;
import static com.fengshen.server.util.MsgUtil.WO_YAO_YI_DU_YING_XIONG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 英雄会
 */
@Service
public class HeroPubService {
    public static final int MAP_ID = 5004;
    public static final String[] titles = new String[]{"初出江湖","初显锋芒", "声名鹊起", "锋芒毕露", "声名显赫", "如雷贯耳", "威风八面", "德高望重", "威震九州","常胜将军","战无不胜","中洲战神"};
    public static final String[] contents = new String[]{MsgUtil.WO_XIANG_SHI_70, MsgUtil.WO_XIANG_SHI_80, MsgUtil.WO_XIANG_SHI_90, MsgUtil.WO_XIANG_SHI_100,
            MsgUtil.WO_XIANG_SHI_110, MsgUtil.WO_XIANG_SHI_120, MsgUtil.WO_XIANG_SHI_130};
    public static final Integer[][] POS = {{37,30}, {41,28}, {45,26}, {18,22}, {25,19}, {31,16}};
    public static final String DEFAULT_PET_NAME = "英雄会评议员";
    public static final int ICON = 6223;
    public static final String NPC_NAME = DEFAULT_PET_NAME;

    public static void openMenu(Chara chara, int npcId) {
        CharaStatue charaStatue = getCharStaure(npcId);

        Npc npc = new Npc();
        npc.setId(npcId);
        npc.setIcon(ICON);
        npc.setName(charaStatue == null ? DEFAULT_PET_NAME : charaStatue.name);

        String content = "英雄会高手如云，必须拥有过人本领方能有一席之地。"+
                MsgUtil.getTalk(HeroPubService.getContent(npcId))+
                MsgUtil.getTalk(WO_YAO_YI_DU_YING_XIONG)+
                MsgUtil.getTalk(KONG_PA_SHI_LI_BU_GOU);
        Vo_MENU_LIST menu_list_vo = GameUtil.MSG_MENU_LIST(npc, content);
        GameObjectChar.send(new MSG_MENU_LIST(), menu_list_vo);
    }

    public static void notifyPanel(Chara chara, int npcId) {
        Vo_20689_0 vo_20689_0 = new Vo_20689_0();
        CharaStatue charaStatue = getCharStaure(npcId);
        int index = getIndex(npcId);
        int level = (index == 0) ? 79 : (index == 1) ? 89 : (index == 2) ? 99 : (index == 3) ? 109 :
                (index == 4) ? 119 : 129;

        vo_20689_0.npcId = npcId;
        vo_20689_0.isLeader = (charaStatue == null) ? 0 : charaStatue.id == chara.id?1:0;
        vo_20689_0.name = (charaStatue == null) ? getNpcName(npcId) : charaStatue.name;
        vo_20689_0.title = titles[index];
        vo_20689_0.level = ""+ ((charaStatue == null) ? level : charaStatue.level);
        vo_20689_0.party_name = (charaStatue == null) ? "无" :charaStatue.partyName;
        /**
         * 套装icon
         */
        vo_20689_0.suit_icon = (charaStatue == null) ? 0 : charaStatue.suit_icon;
        vo_20689_0.weapon_icon = (charaStatue == null) ? 0 :charaStatue.weapon_icon;
        vo_20689_0.icon = (charaStatue == null) ? ICON : charaStatue.waiguan;
        //仙魔光效
        vo_20689_0.xianmo = 0;
        if (charaStatue != null) {
            //留言
            GameObjectChar leader = GameObjectCharMng.getGameObjectChar(charaStatue.id);
            if (null != leader && null != leader.chara.yxh_Notice) {
                vo_20689_0.signature = leader.chara.yxh_Notice;
            } else {
                vo_20689_0.signature = String.format("大家好，我是新晋英雄#R" + charaStatue.name + "#n!\n大家努力修炼，来挑战我吧！");
            }
        } else {
            vo_20689_0.signature = "我是" + level + "级的#R" + getNpcName(npcId) + "#n,\n大家努力修炼，来挑战我吧！";
        }
        vo_20689_0.vipLevel = 0;
        vo_20689_0.gender = (charaStatue == null) ? 1 : charaStatue.sex;
        GameObjectChar.send(new MSG_OVERCOME_NPC_INFO(), vo_20689_0);
    }

    public static void challenge(Chara chara, int npcId){
        if (chara.heropubcishu >= GameConfig.config.getBaseConfig().getYingxionghuiNum()) {
            final Vo_20481_0 vo_20481_10 = new Vo_20481_0();
            vo_20481_10.msg = "您今日已挑战过英雄会！";
            vo_20481_10.time = (int)(System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_10);
            return;
        }
        int index = getIndex(npcId);
        CharaStatue charaStatue = getCharStaure(npcId);
        Npc npc = new Npc();
        npc.setId(npcId);
        npc.setIcon(ICON);
        npc.setName(charaStatue == null ? DEFAULT_PET_NAME : charaStatue.name);
        if(chara.level < 70 + index * 10){
            GameUtil.notifyOpenMenu(npc, MsgUtil.WU_XUE_SHANG_QIQN);
            return;
        }
        if(chara.level > 70 + index * 10 + 9){
            GameUtil.notifyOpenMenu(npc, MsgUtil.DAO_LI_GAO_SHEN);
            return;
        }
        // 添加英雄会战斗的对象
        List<String> list2 = new ArrayList<>();
        list2.add("玉衡星君");
        list2.add(GameUtil.tongttcw[new Random().nextInt(GameUtil.tongttcw.length)]);
        FightManager.goFightHero(chara, list2, DEFAULT_PET_NAME);
        ++chara.heropubcishu; // 更新英雄会挑战次数

    }

    public static int getIndex(int npcId){
        return Arrays.binarySearch(NpcIds.HERO_PUB_NPC_ID, npcId);
    }
    public static String getContent(int npcId){
        return contents[getIndex(npcId)];
    }

    public static String getNpcName(int npcId){
        return NPC_NAME+"_"+getIndex(npcId);
    }

    public static void onEnterMap(GameObjectChar gameObjectChar){
        Chara chara = gameObjectChar.chara;

        for(int i = 0; i< POS.length; ++i){
            Npc npc = new Npc();
            npc.setId(NpcIds.HERO_PUB_NPC_ID_BEGIN+i);
            npc.setName(getNpcName(npc.getId()));
            npc.setMapId(MAP_ID);
            npc.setIcon(ICON);
            Integer[] pos = POS[i];
            npc.setX(pos[0]);
            npc.setY(pos[1]);

            checkInitCharStatue(chara.polar, npc, titles[i]);

            gameObjectChar.sendOne(new M65529_npc(), npc);
        }
    }

    public static void checkInitCharStatue(int polar, Npc npc, String title){
        CharaStatue charaStatue = getCharStaure(npc.getId());
        if(null == charaStatue){
            charaStatue = new CharaStatue();
            charaStatue.name = DEFAULT_PET_NAME;
            charaStatue.waiguan = npc.getIcon();
            charaStatue.chengHao = title;
            charaStatue.level = 50;

            charaStatue.fangyu = 1000;
            charaStatue.fashang = 1000;
            charaStatue.phy_power = 1000;

            putCharStaure(npc.getId(), charaStatue);
        }
    }

    public static CharaStatue getCharStaure(int npcId){
        return CharaStatueService.getCharStaure(getNpcName(npcId));
    }
    public static void putCharStaure(int npcId, CharaStatue charaStatue){
        CharaStatueService.putCache(getNpcName(npcId), charaStatue);
    }

    public static void changeNotice(int id, String msg){
        GameObjectChar.getGameObjectChar().chara.yxh_Notice = msg;
    }

}
