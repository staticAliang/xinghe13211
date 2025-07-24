package com.fengshen.server.service;

import static com.fengshen.server.util.MsgUtil.ZHU_WEI_XIN_KU;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Npc;
import com.fengshen.server.data.vo.Vo_MENU_LIST;
import com.fengshen.server.data.write.MSG_MENU_LIST;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameTeam;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.MsgUtil;
import com.fengshen.server.util.NpcIds;

/**
 * 地图守护神
 */
@Service
public class MapGuardianService {
    private static final Logger logger = LoggerFactory.getLogger(MapGuardianService.class);
    public static final Map<String, Template> configMap = new LinkedHashMap<>();

    public static void init(){
        register("五龙窟四层守护神", 45, 55);
        register("蓬莱岛守护神", 45, 56);
        register("五龙窟五层守护神", 45, 58);
        register("幽冥涧守护神", 48, 62);
        register("百花谷一守护神", 53, 67);
        register("百花谷二守护神", 56, 70);
        register("百花谷三守护神", 59, 73);
        register("百花谷四守护神", 62, 76);
        register("百花谷五守护神", 65, 79);
        register("百花谷六守护神", 68, 82);
        register("百花谷七守护神", 71, 85);
        register("东昆仑守护神", 73, 87);
        register("绝人阵守护神", 73, 87);
        register("绝仙阵守护神", 78, 92);
        register("地绝阵守护神", 83, 97);
        register("天绝阵守护神", 88, 102);
        register("海底迷宫守护神", 93, 107);
        register("昆仑云海守护神", 97, 112);
        register("雪域冰原守护神", 102, 117);
        register("迷境花树守护神", 107, 122);
        register("水云间守护神", 112, 127);
        register("热砂荒漠守护神", 117, 132);
        register("方丈岛守护神", 122, 140);
        register("断魂窟守护神", 130, 140);
        register("弑神殿守护神", 132, 147);
    }

    public static void register(String npcName, int minLevel, int maxLevel){
        Npc npc = GameData.that.baseNpcService.findOneByName(npcName);
        int npcIdBegin = NpcIds.MAP_GUARDIAN_NPC_ID_BEGIN+configMap.size()*5;
        int npcIdEnd = npcIdBegin + 4;
        Template template = new Template(npc, minLevel, maxLevel, npcIdBegin, npcIdEnd);
        configMap.put(npcName, template);
    }

    public static Template getTemplate(int charaStatueId){
        for(Template template:configMap.values()){
            if(charaStatueId>=template.npcIdBegin && charaStatueId<=template.npcIdEnd){
                return template;
            }
        }
        return null;
    }

    public static class Template{
        public final Npc npc;
        public final int minLevel;
        public final int maxLevel;
        public final int npcIdBegin;
        public final int npcIdEnd;

        public Template(Npc npc, int minLevel, int maxLevel, int npcIdBegin, int npcIdEnd) {
            this.npc = npc;
            this.minLevel = minLevel;
            this.maxLevel = maxLevel;
            this.npcIdBegin = npcIdBegin;
            this.npcIdEnd = npcIdEnd;
        }
    }
    /**
     * 是否是守护神
     * @param npcName
     * @return
     */
    public static boolean isProtector(String npcName){
        return npcName.endsWith("守护神");
    }

    public static void openMenu(Chara chara, Npc npc){
        Template template = configMap.get(npc.getName());
        if(null == template){
            logger.error("not found config:"+npc.getName());
            return;
        }
        String content = "我们就是传说中美貌与智慧并存、英雄与侠义的化身——人见人爱的"+npc.getName()+"!我们守护着这片土地的一草一木。"+
                MsgUtil.getTalk("看看你们的实力（"+template.minLevel+"-"+template.maxLevel+"级可挑战）")+
                MsgUtil.getTalk(ZHU_WEI_XIN_KU);
        Vo_MENU_LIST menu_list_vo = GameUtil.MSG_MENU_LIST(npc, content);
        GameObjectChar.send(new MSG_MENU_LIST(), menu_list_vo);
    }
    public static void openMenu(Chara chara, int charaStatueId){
        Template template = getTemplate(charaStatueId);
        if (template == null) {
            Npc npc = GameData.that.baseNpcService.findById(charaStatueId);
            openMenu(chara, npc);
        } else {
            Npc npc = new Npc();
            npc.setId(charaStatueId);
            npc.setName(template.npc.getName());
            npc.setIcon(template.npc.getIcon());
            openMenu(chara, npc);
        }
    }

    /**
     * 是否显示原始的npc
     * @param npc
     * @return
     */
    public static boolean isNpcAppear(Npc npc){
        return !CharaStatueService.containsCharStaure(getCharaStatueName(npc.getName(), 0));
    }

    public static void onEnterMap(int mapId, GameObjectChar gameObjectChar){
        for(Template template:configMap.values()){
        	if(template == null || template.npc == null) {
        		continue;
        	}
        	if(template.npc.getMapId() != mapId){
        		continue;
        	}

            List<CharaStatue> list = getCharaStatueList(template.npc.getName());
            if(list.isEmpty()){
                continue;
            }
            notifyNpcApprear(template, list, gameObjectChar.gameMap.sessionList);
        }
    }

    public static void notifyNpcApprear(Template template, List<CharaStatue> list, List<GameObjectChar> sessionList){
        for(int i=0;i<list.size();++i){
            CharaStatue charaStatue = list.get(i);
            Npc chara = new Npc();
            chara.setMapId(template.npc.getMapId());
            chara.setId(template.npcIdBegin+i);
            chara.setIcon(charaStatue.waiguan);
            if(i%2==0){//偶数
                chara.setX(template.npc.getX()-3*i/2);
                chara.setY(template.npc.getY()-3*i/2);
            }else{//奇数
                chara.setX(template.npc.getX()+3*(i+1)/2);
                chara.setY(template.npc.getY()-3*(i+1)/2);
            }

            chara.setName(getCharaStatueName(template.npc.getName(), i));
            GameUtil.notifyNpcAppear(chara, sessionList);
        }
    }

    public static void challenge(Chara chara, int charaStatue) {
        Npc npc = GameData.that.baseNpcService.findById(charaStatue);
        if (npc == null) {
            Template template = getTemplate(charaStatue);
            npc = new Npc();
            npc.setId(charaStatue);
            npc.setMapId(template.npc.getMapId());
            npc.setIcon(template.npc.getIcon());
            npc.setName(template.npc.getName());
        }

        GameTeam gameTeam= GameObjectChar.getGameObjectChar().gameTeam;
        if(!GameCommonUtil.isNotGameTeam(gameTeam) || gameTeam.duiwu.size()<5) {
            GameUtil.notifyOpenMenu(npc, "我们可不想以多欺少，你还是组满了5个人再来挑战吧。[离开]");
            return;
        }

        if (!GameUtil.judeMapGuardCishu(chara, GameObjectChar.getGameObjectChar())) {
            GameUtil.notifyOpenMenu(npc, "你的队伍中今日已有人挑战过守护神，暂时无法挑战！[离开]");
            return;
        }
        // 更新次数
        for (int i = 0; i < GameObjectChar.getGameObjectChar().gameTeam.duiwu.size(); ++i) {
            ++GameObjectChar.getGameObjectChar().gameTeam.duiwu.get(i).mapguardcishu;
        }
        Template template = configMap.get(npc.getName());
        if(chara.level<template.minLevel || chara.level>template.maxLevel) {
            GameUtil.notifyOpenMenu(npc, "等级不符合。[离开]");
            return;
        }

        List<String> monsterList = new ArrayList<>();
        String mapGuardName = chara.mapName+"守护神";
        monsterList.add(mapGuardName);
        monsterList.add(chara.mapName+"守护神宠物");
        monsterList.add(mapGuardName);
        monsterList.add(chara.mapName+"守护神宠物");
        monsterList.add(mapGuardName);
        monsterList.add(chara.mapName+"守护神宠物");
        monsterList.add(mapGuardName);
        monsterList.add(chara.mapName+"守护神宠物");
        monsterList.add(mapGuardName);
        monsterList.add(chara.mapName+"守护神宠物");

        CharaStatue statue = CharaStatueService.getCharStaure(getCharaStatueName(npc.getName(), 0));
        List<CharaStatue> defList = new ArrayList<>();
        if(null==statue) {
            FightManager.goFightMapGuard(chara, monsterList, null, "");
        }else{
            defList.add(statue);
            for(int i=1;i<=4;i++){
                String name = getCharaStatueName(npc.getName(), i);
                statue = CharaStatueService.getCharStaure(name);
                if(null==statue){
                    logger.error("charaStatue is null!"+name);
                    continue;
                }
                defList.add(statue);
            }
            FightManager.goFightMapGuard(chara, monsterList, defList, "");
        }
    }

    public static void challenge(int charaStatue){
        Template template = getTemplate(charaStatue);
        // 如果为空，说明是第一次挑战守护神
        Npc npc = new Npc();
        if (template == null) {
            npc = GameData.that.baseNpcService.findById(charaStatue);
        } else {
            npc.setId(charaStatue);
            npc.setMapId(template.npc.getMapId());
            npc.setIcon(template.npc.getIcon());
            npc.setName(template.npc.getName());
        }
        challenge(npc);
    }

    public static void challenge(Npc npc){
        GameTeam gameTeam= GameObjectChar.getGameObjectChar().gameTeam;
        if(null == gameTeam || gameTeam.duiwu.size()<5){
            GameUtil.notifyOpenMenu(npc, "我们可不想以多欺少，你还是组满了5个人再来挑战吧。[离开]");
            return;
        }

        Chara chara = GameObjectChar.getGameObjectChar().chara;
        Template template = configMap.get(npc.getName());
        if(chara.level<template.minLevel || chara.level>template.maxLevel){
            GameUtil.notifyOpenMenu(npc, "等级不符合。[离开]");
            return;
        }

        CharaStatue charaStatue = CharaStatueService.getCharStaure(getCharaStatueName(npc.getName(), 0));
        List<CharaStatue> defList = new ArrayList<>();
        if(null==charaStatue) {
            charaStatue = new CharaStatue();
            charaStatue.name = npc.getName();
            charaStatue.waiguan = npc.getIcon();
            charaStatue.level = 50;

            charaStatue.fangyu = 1000;
            charaStatue.fashang = 1000;
            charaStatue.phy_power = 1000;
            defList.add(charaStatue);
        }else{
            defList.add(charaStatue);
            for(int i=1;i<=4;i++){
                String name = getCharaStatueName(npc.getName(), i);
                charaStatue = CharaStatueService.getCharStaure(name);
                if(null==charaStatue){
                    logger.error("charaStatue is null!"+name);
                    continue;
                }
                defList.add(charaStatue);
            }
        }
    }

    public static List<CharaStatue> getCharaStatueList(String npcName){
        List<CharaStatue> list = new ArrayList<>();
        for(int i=0;i<=4;i++){
            String name = getCharaStatueName(npcName, i);
            CharaStatue charaStatue = CharaStatueService.getCharStaure(name);
            if(null==charaStatue){
                continue;
            }
            list.add(charaStatue);
        }
        return list;
    }

    public static String getCharaStatueName(String npcName, int index){
        return npcName+"_"+index;
    }

    public static void onChallengeSuccess(String npcName, List<CharaStatue> attCharaStatue, List<CharaStatue> defCharaStatueList){
        for(int i=0;i<attCharaStatue.size();++i){
            CharaStatue charaStatue = attCharaStatue.get(i);
            charaStatue.copyChengHao(npcName);
            CharaStatueService.saveCharaStature(getCharaStatueName(npcName, i), charaStatue);
        }

//        Template template = configMap.get(npcName);
//        GameUtil.notifyNpcDisappear(template.npc);
//        notifyNpcApprear(template, attCharaStatue);
    }

}
