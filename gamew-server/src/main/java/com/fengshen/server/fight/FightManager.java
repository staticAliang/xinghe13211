package com.fengshen.server.fight;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.*;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.data.constant.RedisKeyConstant;
import com.fengshen.server.game.*;
import com.fengshen.server.process.dari.MSG_WORLD_BOSS_RESULT;
import com.fengshen.server.process.dari.rank_role;
import com.fengshen.server.process.dari.vo_boos_result;
import com.fengshen.server.process.jiutian.CMD_GHOSTDOM_CHALLENGE_INFO;
import com.fengshen.server.process.jiutian.CMD_JIUTIAN_ZHENJUN;
import com.fengshen.server.process.system.CMD_SELECT_MENU_ITEM;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fengshen.server.data.vo.Vo_11719_0;
import com.fengshen.server.data.vo.Vo_11757_0;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_32985_0;
import com.fengshen.server.data.vo.Vo_3583_0;
import com.fengshen.server.data.vo.Vo_4163_0;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_45141_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_64971_0;
import com.fengshen.server.data.vo.Vo_7653_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.Vo_GODBOOK_EFFECT;
import com.fengshen.server.data.vo.fight.Vo_ADD_FRIEND_OPPONENT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_CHAR_DIED;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_COMBAT;
import com.fengshen.server.data.vo.fight.Vo_C_LIFE_DELTA;
import com.fengshen.server.data.vo.fight.Vo_C_OPPONENT_INFO;
import com.fengshen.server.data.vo.fight.Vo_C_SANDGLASS;
import com.fengshen.server.data.vo.fight.Vo_C_WAIT_COMMAND;
import com.fengshen.server.data.vo.fight.Vo_SELECT_COMMAND;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.M11719_0;
import com.fengshen.server.data.write.M11757_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M32985_0;
import com.fengshen.server.data.write.M4163_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.M45141_0;
import com.fengshen.server.data.write.M64981_Fight_Blood;
import com.fengshen.server.data.write.M64981_Fight_Mana;
import com.fengshen.server.data.write.M64991_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M7653_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.fight.MSG_C_WAIT_COMMAND;
import com.fengshen.server.data.write.fight.MSG_SELECT_COMMAND;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_ADD_FRIEND;
import com.fengshen.server.data.write.fight.c.MSG_C_ADD_OPPONENT;
import com.fengshen.server.data.write.fight.c.MSG_C_CHAR_DIED;
import com.fengshen.server.data.write.fight.c.MSG_C_DIALOG_OK;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_COMBAT;
import com.fengshen.server.data.write.fight.c.MSG_C_FRIENDS;
import com.fengshen.server.data.write.fight.c.MSG_C_LEAVE_AT_ONCE;
import com.fengshen.server.data.write.fight.c.MSG_C_LIFE_DELTA;
import com.fengshen.server.data.write.fight.c.MSG_C_OPPONENTS;
import com.fengshen.server.data.write.fight.c.MSG_C_OPPONENT_INFO;
import com.fengshen.server.data.write.fight.c.MSG_C_REFRESH_PET_LIST;
import com.fengshen.server.data.write.fight.c.MSG_C_SANDGLASS;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_CUSTOM_MSG;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_FIGHT_PET;
import com.fengshen.server.data.write.fight.c.MSG_C_START_COMBAT;
import com.fengshen.server.data.write.fight.lc.MSG_LC_UPDATE_STATUS;
import com.fengshen.server.data.write.look.MSG_LC_END_LOOKON;
import com.fengshen.server.data.write.pet.MSG_GODBOOK_EFFECT_NORMAL;
import com.fengshen.server.data.write.pet.MSG_GODBOOK_EFFECT_SUMMON;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_TASK_INFO;
import com.fengshen.server.data.write.user.MSG_PLAY_INSTRUCTION;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.AutoTalkVo;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsBasics;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.ZbAttribute;
import com.fengshen.server.domain.config.Haidao;
import com.fengshen.server.exception.FightException;
import com.fengshen.server.netty.BaseWrite;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.HeroPubService;
import com.fengshen.server.service.ZhengDaoDianService;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;

import io.netty.util.internal.ThreadLocalRandom;

public class FightManager {
    private static Logger log;
    public static List<FightContainer> listFight; // 这个为全局战斗容器，全线共享的
    public static List<Integer> MONSTER_POS;
    public static List<Integer> PER_POS;
    public static List<Integer> PET_POS;
    public static List<Integer> PERSON_POS;
    public static java.util.Map<String, List<FightObject>> zmMap;
    String[] tongttXj;
    public static Random RANDOM;

    public FightManager() {

        this.tongttXj = new String[]{"玉衡星君", "天权星君", "天玑星君", "天璇星君", "天枢星君", "摇光星君", "开阳星君"};
    }

    // 当驱魔香关闭，并且在巡逻的时候会调用这里，进入战斗
    public static void goFight(Chara chara, String mapName) {
        // 这里是指定巡逻的怪物数量
        int monsterNum = 2;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        GameTeam gameTeam = session.gameTeam;
        if (GameCommonUtil.isNotGameTeam(gameTeam, chara)) {
            // 随机队伍加敌数
            int num = (gameTeam.duiwu.size() - 1) * 2;
            if (num == 0) {
                num = 1;
            }
            monsterNum += ThreadLocalRandom.current().nextInt(num) + 1;
            if (monsterNum >= 10) {
                monsterNum = 10;
            }
        }
        goFight(chara, mapName, monsterNum <= 0 ? 2 : monsterNum);
    }

    // 这里是巡逻的怪物战斗的逻辑
    public static void goFight(Chara chara, String mapName, int monsterNum) {
        // 查询巡逻的怪物，按照地图的怪物查询
        List<Pet> monsterList = GameData.that.basePetService.findByZoon(mapName);
        List<String> monsterNameList = new ArrayList<>();
        if (monsterList.size() == 0) {
            return;
        }
        // 随机的选择作战的怪物
        for (int i = 0; i < monsterNum; ++i) {
            Pet pet = monsterList.get(FightManager.RANDOM.nextInt(monsterList.size()));
            monsterNameList.add(pet.getName());
        }
        goFight(chara, monsterNameList);
    }

    // 判断是角色是否有法宝，如果有就加入到战斗对象中
    private static void addFabao(FightContainer fc, Chara chara, FightObject fightObject) {
        List<Object> zhandouisyoufabao = GameUtil.zhandouisyoufabao(chara);
        if (zhandouisyoufabao.size() >= 3) {
            String fabaoName = (String) zhandouisyoufabao.get(0);
            FightFabaoSkill fabaoSkill = FightSkill.getFabaoSkill(fabaoName);
            if (fabaoSkill != null) {
                int level = (int) zhandouisyoufabao.get(1);
                int qinmi = (int) zhandouisyoufabao.get(2);
                fabaoSkill.level = level;
                fabaoSkill.qinmi = qinmi;
                fabaoSkill.buffObject = fightObject;
                fabaoSkill.fightContainer = fc;
                fightObject.addSkill(fabaoSkill);
            }
        }
    }

    // 挑战证道殿的角色
    public static void goFightZhengdaodian(Chara chara, List<String> monsterList, String type) {
        for (FightContainer fc = getFightContainer(chara.id); fc != null; fc = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fc);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        if (GameCommonUtil.isNotGameTeam(session.gameTeam)) {
            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
            vo_20481_0.msg = "我只能单挑不接受群殴";
            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_0);
        } else if (chara.chongwuchanzhanId == 0) {
            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
            vo_20481_0.msg = "你还没有准备好参战的宠物，这样打败你会说我胜之不武。";
            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_0);
        } else {
            FightObject fightObject = new FightObject(chara);
            fightObject.pos = FightManager.PERSON_POS.get(num);
            fightObject.fid = chara.id;
            fightObject.leader = 1;
            fightObject.id = chara.id;
            fightObject.special_icon = chara.special_icon;
            ft.add(fightObject);
            addFabao(fc, chara, fightObject);

            // add tzhang 初始化雕像
            CharaStatue charaStatue = new CharaStatue();
            fc.charaStatue = charaStatue;
            charaStatue.copyFrom(chara);
            charaStatue.copyJiNengList(fightObject.skillsList);
            // add:e

            List<Petbeibao> pets = chara.pets;
            for (int j = 0; j < pets.size(); ++j) {
                if (pets.get(j).id == chara.chongwuchanzhanId) {
                    fightObject = new FightObject(pets.get(j), chara);
                    fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject.fid = pets.get(j).id;
                    fightObject.id = pets.get(j).id;
                    fightObject.cid = chara.id;
                    if (pets.get(j).tianshu.size() != 0) {
                        addFightTianShu(pets.get(j), fightObject, fc);
                    }
                    ft.add(fightObject);

                    charaStatue.copyPet(pets.get(j));
                    charaStatue.copyPetJiNengList(fightObject.skillsList);
                    break;
                }
            }
            // 添加敌方队伍
            FightTeam monsterTeam = new FightTeam();

            // 从快照中读取英雄状态
            String zddJuese = GameUtil.getZddJuese(chara.polar, chara.sex, chara.level);
            CharaStatue defCharaStatue = CharaStatueService.getCharStaure(zddJuese);
            monsterTeam.type = 2;
//            if (FightManager.zmMap.get(type) == null) {
            if (defCharaStatue == null) {
                fightObject = new FightObject(chara, monsterList.get(0), 1);
                // 这里要更新默认的证道殿名字，否则会是星君的名字
                fightObject.str = ZhengDaoDianService.DEFAULT_PET_NAME;
                fightObject.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, null);
                // add:e
                fightObject.pos = FightManager.PER_POS.get(0);
                fightObject.fid = fc.id++;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                FightObject fightObject2 = new FightObject(chara, monsterList.get(1), 0);
                fightObject2.pos = FightManager.PET_POS.get(0);
                fightObject2.fid = fc.id++;
                fightObject2.fightType = "TTT_TYPE";
                fightObject2.cid = fightObject.fid;
                monsterTeam.add(fightObject2);
            } else {
                // 玩家快照
                fightObject = new FightObject(defCharaStatue);
                fightObject.pos = FightManager.PER_POS.get(0);
                // 战斗显示的名字是：证道殿-名字
                fightObject.str = ZhengDaoDianService.NPC_NAME + "-" + fightObject.str;
                fightObject.fid = fc.id++;
                fightObject.leader = 1;
                fightObject.id = fightObject.fid;
                fightObject.type = 2;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                // 宠物
                if (null != defCharaStatue.petbeibao) {
                    FightObject fightObject3 = new FightObject(defCharaStatue.petbeibao, null);
                    fightObject3.pos = FightManager.PET_POS.get(0);
                    fightObject3.fid = fc.id++;
                    fightObject3.type = 2;
                    fightObject3.fightType = "TTT_TYPE";
                    fightObject3.cid = fightObject.fid;
                    fightObject3.skillsList = defCharaStatue.petJiNengList;
                    monsterTeam.add(fightObject3);
                }
            }
            fc.teamList.add(ft);
            fc.teamList.add(monsterTeam);
            FightManager.listFight.add(fc);
            if (chara.autofight_select != 0) {
                Vo_32985_0 vo_32985_0 = new Vo_32985_0();
                vo_32985_0.user_is_multi = 0;
                vo_32985_0.user_round = chara.autofight_select;
                vo_32985_0.user_action = chara.autofight_skillaction;
                vo_32985_0.user_next_action = chara.autofight_skillaction;
                vo_32985_0.user_para = chara.autofight_skillno;
                vo_32985_0.user_next_para = chara.autofight_skillno;
                vo_32985_0.pet_is_multi = 0;
                vo_32985_0.pet_round = 0;
                vo_32985_0.pet_action = 0;
                vo_32985_0.pet_next_action = 0;
                vo_32985_0.pet_para = 0;
                vo_32985_0.pet_next_para = 0;
                GameObjectChar.send(new M32985_0(), vo_32985_0);
            }
            // 加载战斗信息
            GameCommonUtil.fightCmdInfo(session);
            // 开始战斗
            Vo_3583_0 vo_3583_0 = new Vo_3583_0();
            vo_3583_0.flag = 1;
            vo_3583_0.mode = 3;
            send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
            FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
            List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
            for (FightObject object : fightObjectList1) {
                if (object.type == 2) {
                    Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                    vo_64971_0.count = 1;
                    vo_64971_0.id = object.id;
                    vo_64971_0.haveCalled = 1;
                    GameObjectCharMng.getGameObjectChar(object.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
                }
            }
            List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
            List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
            for (FightObject object2 : fightObjectList2) {
                Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
                vo_65019_0.id = object2.fid;
                vo_65019_0.leader = object2.leader;
                vo_65019_0.weapon_icon = object2.weapon_icon;
                vo_65019_0.pos = object2.pos;
                vo_65019_0.rank = object2.rank;
                vo_65019_0.vip_type = object2.vipType;
                vo_65019_0.str = object2.str;
                vo_65019_0.type = object2.org_icon;
                vo_65019_0.durability = object2.durability;
                vo_65019_0.req_level = 0;
                vo_65019_0.upgrade_level = object2.upgrade_level;
                vo_65019_0.upgrade_type = object2.upgrade_type;
                vo_65019_0.dex = object2.max_mofa;
                vo_65019_0.max_mana = object2.max_mofa;
                vo_65019_0.max_life = object2.max_shengming;
                vo_65019_0.def = object2.max_shengming;
                vo_65019_0.org_icon = object2.org_icon;
                vo_65019_0.suit_icon = object2.suit_icon;
                vo_65019_0.suit_light_effect = object2.suit_light_effect;
                vo_65019_0.special_icon = object2.special_icon;
                vo_65019_0.portrait = object2.org_icon;
                vo_65019_0.customIcon = object2.customIcon;
                vo_65019_0.zhenlingLevel = object2.zhenlingLevel;
                vo_65019_0.zhenlingType = object2.zhenlingType;
                list65019.add(vo_65019_0);
            }
            send(fc, new MSG_C_FRIENDS(), list65019);
            List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
            fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
            for (FightObject object3 : fightObjectList2) {
                Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
                vo_65017_0.id = object3.fid;
                vo_65017_0.leader = object3.leader;
                vo_65017_0.weapon_icon = object3.weapon_icon;
                vo_65017_0.pos = object3.pos;
                vo_65017_0.rank = 0;
                vo_65017_0.vip_type = object3.vipType;
                vo_65017_0.str = object3.str;
                vo_65017_0.type = object3.org_icon;
                vo_65017_0.durability = 2;
                vo_65017_0.req_level = 0;
                vo_65017_0.upgrade_level = object3.upgrade_level;
                vo_65017_0.upgrade_type = object3.upgrade_type;
                vo_65017_0.dex = object3.max_mofa;
                vo_65017_0.max_mana = object3.max_mofa;
                vo_65017_0.max_life = object3.max_shengming;
                vo_65017_0.def = object3.max_shengming;
                vo_65017_0.org_icon = object3.org_icon;
                vo_65017_0.suit_icon = object3.suit_icon;
                vo_65017_0.suit_light_effect = object3.suit_light_effect;
                vo_65017_0.portrait = object3.org_icon;
                vo_65017_0.special_icon = object3.special_icon;
                vo_65017_0.customIcon = object3.customIcon;
                vo_65017_0.zhenlingLevel = object3.zhenlingLevel;
                vo_65017_0.zhenlingType = object3.zhenlingType;
                list65020.add(vo_65017_0);
            }
            send(fc, new MSG_C_OPPONENTS(), list65020);
            fightObjectList2 = getAllFightObject(fc);
            // 天书底部效果
            getRandomGodbookEffect(fightObjectList2, fc);
            round(fc);
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
    }

    // 挑战英雄会的英雄
    public static void goFightHero(Chara chara, List<String> monsterList, String type) {
        for (FightContainer fc = getFightContainer(chara.id); fc != null; fc = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fc);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        if (GameCommonUtil.isNotGameTeam(session.gameTeam)) {
            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
            vo_20481_0.msg = "我只能单挑不接受群殴";
            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_0);
        } else if (chara.chongwuchanzhanId == 0) {
            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
            vo_20481_0.msg = "你还没有准备好参战的宠物，这样打败你会说我胜之不武。";
            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_0);
        } else {
            FightObject fightObject = new FightObject(chara);
            fightObject.pos = FightManager.PERSON_POS.get(num);
            fightObject.fid = chara.id;
            fightObject.leader = 1;
            fightObject.id = chara.id;
            fightObject.special_icon = chara.special_icon;
            ft.add(fightObject);
            addFabao(fc, chara, fightObject);

            // add tzhang 初始化雕像
            CharaStatue charaStatue = new CharaStatue();
            fc.charaStatue = charaStatue;
            charaStatue.copyFrom(chara);
            charaStatue.copyJiNengList(fightObject.skillsList);
            // add:e

            List<Petbeibao> pets = chara.pets;
            for (int j = 0; j < pets.size(); ++j) {
                if (pets.get(j).id == chara.chongwuchanzhanId) {
                    fightObject = new FightObject(pets.get(j), chara);
                    fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject.fid = pets.get(j).id;
                    fightObject.id = pets.get(j).id;
                    fightObject.cid = chara.id;
                    if (pets.get(j).tianshu.size() != 0) {
                        addFightTianShu(pets.get(j), fightObject, fc);
                    }
                    ft.add(fightObject);

                    charaStatue.copyPet(pets.get(j));
                    charaStatue.copyPetJiNengList(fightObject.skillsList);
                    break;
                }
            }
            // 添加敌方队伍
            FightTeam monsterTeam = new FightTeam();

            // 从快照中读取英雄状态
            String yingxiong = GameUtil.getYingxiong(chara.level);
            CharaStatue defCharaStatue = CharaStatueService.getCharStaure(yingxiong);
            monsterTeam.type = 2;
            if (defCharaStatue == null) {
                fightObject = new FightObject(chara, monsterList.get(0), 1);
                // 这里要更新默认的英雄评议员名字，否则会是星君的名字
                fightObject.str = yingxiong;
                // add:e
                fightObject.pos = FightManager.PER_POS.get(0);
                fightObject.fid = fc.id++;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                FightObject fightObject2 = new FightObject(chara, monsterList.get(1), 1);
                fightObject2.pos = FightManager.PET_POS.get(0);
                fightObject2.fid = fc.id++;
                fightObject2.fightType = "TTT_TYPE";
                fightObject2.cid = fightObject.fid;
                monsterTeam.add(fightObject2);
            } else {
                // 玩家快照
                fightObject = new FightObject(defCharaStatue);
                fightObject.pos = FightManager.PER_POS.get(0);
                // 战斗显示的名字是：称号-名字
                fightObject.str = defCharaStatue.chengHao + "-" + fightObject.str;
                fightObject.fid = fc.id++;
                fightObject.leader = 1;
                fightObject.id = fightObject.fid;
                fightObject.type = 2;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                // 宠物
                if (null != defCharaStatue.petbeibao) {
                    FightObject fightObject3 = new FightObject(defCharaStatue.petbeibao, chara);
                    fightObject3.pos = FightManager.PET_POS.get(0);
                    fightObject3.fid = fc.id++;
                    fightObject3.type = 2;
                    fightObject3.skillsList = defCharaStatue.petJiNengList;
                    fightObject3.fightType = "TTT_TYPE";
                    fightObject3.cid = fightObject.fid;
                    monsterTeam.add(fightObject3);
                }
            }
            fc.teamList.add(ft);
            fc.teamList.add(monsterTeam);
            FightManager.listFight.add(fc);
            if (chara.autofight_select != 0) {
                Vo_32985_0 vo_32985_0 = new Vo_32985_0();
                vo_32985_0.user_is_multi = 0;
                vo_32985_0.user_round = chara.autofight_select;
                vo_32985_0.user_action = chara.autofight_skillaction;
                vo_32985_0.user_next_action = chara.autofight_skillaction;
                vo_32985_0.user_para = chara.autofight_skillno;
                vo_32985_0.user_next_para = chara.autofight_skillno;
                vo_32985_0.pet_is_multi = 0;
                vo_32985_0.pet_round = 0;
                vo_32985_0.pet_action = 0;
                vo_32985_0.pet_next_action = 0;
                vo_32985_0.pet_para = 0;
                vo_32985_0.pet_next_para = 0;
                GameObjectChar.send(new M32985_0(), vo_32985_0);
            }
            // 加载战斗信息
            GameCommonUtil.fightCmdInfo(session);
            // 开始战斗
            Vo_3583_0 vo_3583_0 = new Vo_3583_0();
            vo_3583_0.flag = 1;
            vo_3583_0.mode = 3;
            send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
            FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
            List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
            for (FightObject object : fightObjectList1) {
                if (object.type == 2) {
                    Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                    vo_64971_0.count = 1;
                    vo_64971_0.id = object.id;
                    vo_64971_0.haveCalled = 1;
                    GameObjectCharMng.getGameObjectChar(object.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
                }
            }
            List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
            List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
            for (FightObject object2 : fightObjectList2) {
                Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
                vo_65019_0.id = object2.fid;
                vo_65019_0.leader = object2.leader;
                vo_65019_0.weapon_icon = object2.weapon_icon;
                vo_65019_0.pos = object2.pos;
                vo_65019_0.rank = object2.rank;
                vo_65019_0.vip_type = object2.vipType;
                vo_65019_0.str = object2.str;
                vo_65019_0.type = object2.org_icon;
                vo_65019_0.durability = object2.durability;
                vo_65019_0.req_level = 0;
                vo_65019_0.upgrade_level = object2.upgrade_level;
                vo_65019_0.upgrade_type = object2.upgrade_type;
                vo_65019_0.dex = object2.max_mofa;
                vo_65019_0.max_mana = object2.max_mofa;
                vo_65019_0.max_life = object2.max_shengming;
                vo_65019_0.def = object2.max_shengming;
                vo_65019_0.org_icon = object2.org_icon;
                vo_65019_0.suit_icon = object2.suit_icon;
                vo_65019_0.suit_light_effect = object2.suit_light_effect;
                vo_65019_0.special_icon = object2.special_icon;
                vo_65019_0.portrait = object2.org_icon;
                vo_65019_0.customIcon = object2.customIcon;
                vo_65019_0.zhenlingLevel = object2.zhenlingLevel;
                vo_65019_0.zhenlingType = object2.zhenlingType;
                list65019.add(vo_65019_0);
            }
            send(fc, new MSG_C_FRIENDS(), list65019);
            List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
            fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
            for (FightObject object3 : fightObjectList2) {
                Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
                vo_65017_0.id = object3.fid;
                vo_65017_0.leader = object3.leader;
                vo_65017_0.weapon_icon = object3.weapon_icon;
                vo_65017_0.pos = object3.pos;
                vo_65017_0.rank = 0;
                vo_65017_0.vip_type = object3.vipType;
                vo_65017_0.str = object3.str;
                vo_65017_0.type = object3.org_icon;
                vo_65017_0.durability = 2;
                vo_65017_0.req_level = 0;
                vo_65017_0.upgrade_level = object3.upgrade_level;
                vo_65017_0.upgrade_type = object3.upgrade_type;
                vo_65017_0.dex = object3.max_mofa;
                vo_65017_0.max_mana = object3.max_mofa;
                vo_65017_0.max_life = object3.max_shengming;
                vo_65017_0.def = object3.max_shengming;
                vo_65017_0.org_icon = object3.org_icon;
                vo_65017_0.suit_icon = object3.suit_icon;
                vo_65017_0.suit_light_effect = object3.suit_light_effect;
                vo_65017_0.portrait = object3.org_icon;
                vo_65017_0.special_icon = object3.special_icon;
                vo_65017_0.customIcon = object3.customIcon;
                vo_65017_0.zhenlingLevel = object3.zhenlingLevel;
                vo_65017_0.zhenlingType = object3.zhenlingType;
                list65020.add(vo_65017_0);
            }
            send(fc, new MSG_C_OPPONENTS(), list65020);
            fightObjectList2 = getAllFightObject(fc);
            // 天书底部效果
            getRandomGodbookEffect(fightObjectList2, fc);
            round(fc);
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
    }

    // 挑战掌门
    public static void goFightZhangMen(Chara chara, List<String> monsterList, String type, int id) {
        for (FightContainer fc = getFightContainer(chara.id); fc != null; fc = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fc);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        if (GameCommonUtil.isNotGameTeam(session.gameTeam)) {
            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
            vo_20481_0.msg = "掌门挑战只能单挑不接受群殴";
            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_0);
            GameConfig.canzhanBoos.remove("挑战掌门[" + type + "]_" + id);
        } else if (chara.chongwuchanzhanId == 0) {
            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
            vo_20481_0.msg = "你还没有准备好参战的宠物，这样打败你会说我胜之不武。";
            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
            GameObjectChar.getGameObjectChar();
            GameObjectChar.send(new M20481_0(), vo_20481_0);
            GameConfig.canzhanBoos.remove("挑战掌门[" + type + "]_" + id);
        } else {
            chara.isFight = true;
            ++chara.zhangmentiaozhan; // 更新挑战掌门次数
            FightObject fightObject = new FightObject(chara);
            fightObject.pos = FightManager.PERSON_POS.get(num);
            fightObject.fid = chara.id;
            fightObject.leader = 1;
            fightObject.id = chara.id;
            fightObject.special_icon = chara.special_icon;
            ft.add(fightObject);
            addFabao(fc, chara, fightObject);

            // add tzhang 初始化雕像
            CharaStatue charaStatue = new CharaStatue();
            fc.charaStatue = charaStatue;
            charaStatue.copyFrom(chara);
            charaStatue.copyJiNengList(fightObject.skillsList);
            // add:e

            List<Petbeibao> pets = chara.pets;
            for (int j = 0; j < pets.size(); ++j) {
                if (pets.get(j).id == chara.chongwuchanzhanId) {
                    fightObject = new FightObject(pets.get(j), chara);
                    fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject.fid = pets.get(j).id;
                    fightObject.id = pets.get(j).id;
                    fightObject.cid = chara.id;
                    if (pets.get(j).tianshu.size() != 0) {
                        addFightTianShu(pets.get(j), fightObject, fc);
                    }
                    ft.add(fightObject);

                    charaStatue.copyPet(pets.get(j));
                    charaStatue.copyPetJiNengList(fightObject.skillsList);
                    break;
                }
            }
            // 添加敌方队伍
            FightTeam monsterTeam = new FightTeam();

            // 从快照中读取掌门状态
            String zhangMen = GameUtil.getZhangMenName(chara.polar);
            CharaStatue defCharaStatue = CharaStatueService.getCharStaure(zhangMen);
            monsterTeam.type = 2;
            if (defCharaStatue == null) {
                fightObject = new FightObject(chara, monsterList.get(0), 1);
                fightObject.pos = FightManager.PER_POS.get(0);
                fightObject.fid = fc.id++;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                FightObject fightObject2 = new FightObject(chara, monsterList.get(1), 2);
                fightObject2.pos = FightManager.PET_POS.get(0);
                fightObject2.fid = fc.id++;
                fightObject2.cid = fightObject.fid;
                fightObject2.fightType = "TTT_TYPE";
                monsterTeam.add(fightObject2);
            } else {
                // 玩家快照
                fightObject = new FightObject(defCharaStatue);
                // 这里要更新一下从数据库读出来的掌门名称
                fightObject.str = zhangMen;
                // add:e
                fightObject.pos = FightManager.PER_POS.get(0);
                fightObject.fid = fc.id++;
                fightObject.leader = 1;
                fightObject.id = fightObject.fid;
                fightObject.type = 2;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                // 宠物
                if (null != defCharaStatue.petbeibao) {
                    FightObject fightObject3 = new FightObject(defCharaStatue.petbeibao, null);
                    fightObject3.pos = FightManager.PET_POS.get(0);
                    fightObject3.fid = fc.id++;
                    fightObject3.type = 2;
                    fightObject3.fightType = "TTT_TYPE";
                    fightObject3.cid = fightObject.fid;
                    fightObject3.skillsList = defCharaStatue.petJiNengList;
                    monsterTeam.add(fightObject3);
                }
            }
            fc.teamList.add(ft);
            fc.teamList.add(monsterTeam);
            FightManager.listFight.add(fc);
            if (chara.autofight_select != 0) {
                Vo_32985_0 vo_32985_0 = new Vo_32985_0();
                vo_32985_0.user_is_multi = 0;
                vo_32985_0.user_round = chara.autofight_select;
                vo_32985_0.user_action = chara.autofight_skillaction;
                vo_32985_0.user_next_action = chara.autofight_skillaction;
                vo_32985_0.user_para = chara.autofight_skillno;
                vo_32985_0.user_next_para = chara.autofight_skillno;
                vo_32985_0.pet_is_multi = 0;
                vo_32985_0.pet_round = 0;
                vo_32985_0.pet_action = 0;
                vo_32985_0.pet_next_action = 0;
                vo_32985_0.pet_para = 0;
                vo_32985_0.pet_next_para = 0;
                GameObjectChar.send(new M32985_0(), vo_32985_0);
            }
            // 加载战斗信息
            GameCommonUtil.fightCmdInfo(session);
            // 开始战斗
            Vo_3583_0 vo_3583_0 = new Vo_3583_0();
            vo_3583_0.flag = 1;
            vo_3583_0.mode = 3;
            send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
            FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
            List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
            for (FightObject object : fightObjectList1) {
                if (object.type == 2) {
                    Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                    vo_64971_0.count = 1;
                    vo_64971_0.id = object.id;
                    vo_64971_0.haveCalled = 1;
                    GameObjectCharMng.getGameObjectChar(object.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
                }
            }
            List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
            List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
            for (FightObject object2 : fightObjectList2) {
                Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
                vo_65019_0.id = object2.fid;
                vo_65019_0.leader = object2.leader;
                vo_65019_0.weapon_icon = object2.weapon_icon;
                vo_65019_0.pos = object2.pos;
                vo_65019_0.rank = object2.rank;
                vo_65019_0.vip_type = object2.vipType;
                vo_65019_0.str = object2.str;
                vo_65019_0.type = object2.org_icon;
                vo_65019_0.durability = object2.durability;
                vo_65019_0.req_level = 0;
                vo_65019_0.upgrade_level = object2.upgrade_level;
                vo_65019_0.upgrade_type = object2.upgrade_type;
                vo_65019_0.dex = object2.max_mofa;
                vo_65019_0.max_mana = object2.max_mofa;
                vo_65019_0.max_life = object2.max_shengming;
                vo_65019_0.def = object2.max_shengming;
                vo_65019_0.org_icon = object2.org_icon;
                vo_65019_0.suit_icon = object2.suit_icon;
                vo_65019_0.suit_light_effect = object2.suit_light_effect;
                vo_65019_0.special_icon = object2.special_icon;
                vo_65019_0.portrait = object2.org_icon;
                vo_65019_0.customIcon = object2.customIcon;
                vo_65019_0.zhenlingLevel = object2.zhenlingLevel;
                vo_65019_0.zhenlingType = object2.zhenlingType;
                list65019.add(vo_65019_0);
            }
            send(fc, new MSG_C_FRIENDS(), list65019);
            List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
            fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
            for (FightObject object3 : fightObjectList2) {
                Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
                vo_65017_0.id = object3.fid;
                vo_65017_0.leader = object3.leader;
                vo_65017_0.weapon_icon = object3.weapon_icon;
                vo_65017_0.pos = object3.pos;
                vo_65017_0.rank = 0;
                vo_65017_0.vip_type = object3.vipType;
                vo_65017_0.str = object3.str;
                vo_65017_0.type = object3.org_icon;
                vo_65017_0.durability = 2;
                vo_65017_0.req_level = 0;
                vo_65017_0.upgrade_level = object3.upgrade_level;
                vo_65017_0.upgrade_type = object3.upgrade_type;
                vo_65017_0.dex = object3.max_mofa;
                vo_65017_0.max_mana = object3.max_mofa;
                vo_65017_0.max_life = object3.max_shengming;
                vo_65017_0.def = object3.max_shengming;
                vo_65017_0.org_icon = object3.org_icon;
                vo_65017_0.suit_icon = object3.suit_icon;
                vo_65017_0.suit_light_effect = object3.suit_light_effect;
                vo_65017_0.portrait = object3.org_icon;
                vo_65017_0.special_icon = object3.special_icon;
                vo_65017_0.customIcon = object3.customIcon;
                vo_65017_0.zhenlingLevel = object3.zhenlingLevel;
                vo_65017_0.zhenlingType = object3.zhenlingType;
                list65020.add(vo_65017_0);
            }
            send(fc, new MSG_C_OPPONENTS(), list65020);
            fightObjectList2 = getAllFightObject(fc);
            // 天书底部效果
            getRandomGodbookEffect(fightObjectList2, fc);
            round(fc);
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
    }

    // 通天塔战斗的函数，monsterPerList
    public static void goFightTtt(Chara chara, List<String> monsterPerList, List<String> monsterPetList) {
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        FightContainer fc = new FightContainer();
        // 我方战斗的队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        int num = 0;
        GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
        chara.isFight = true;
        FightObject fightObject = new FightObject(chara);
        fightObject.pos = FightManager.PER_POS.get(num);
        fightObject.fid = chara.id;
        fightObject.leader = 1;
        fightObject.id = chara.id;
        ft.add(fightObject);
        addFabao(fc, chara, fightObject);
        List<Petbeibao> pets = chara.pets;
        for (int j = 0; j < pets.size(); ++j) {
            if (pets.get(j).id == chara.chongwuchanzhanId) {
                fightObject = new FightObject(pets.get(j), chara);
                fightObject.pos = FightManager.PET_POS.get(num);
                fightObject.fid = pets.get(j).id;
                fightObject.id = pets.get(j).id;
                fightObject.cid = chara.id;
                if (pets.get(j).tianshu.size() != 0) {
                    addFightTianShu(pets.get(j), fightObject, fc);
                }
                ft.add(fightObject);
                break;
            }
        }
        ++num;
        // 星君那方的队伍
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        for (int i = 0; i < monsterPerList.size(); ++i) {
            FightObject fightObject2 = new FightObject(chara, monsterPerList.get(i), "chara");
            fightObject2.pos = FightManager.PER_POS.get(monsterIndex);
            fightObject2.fid = fc.id++;
            if (monsterIndex == 1) {
                fightObject2.leader = 1;
            }
            monsterTeam.add(fightObject2);

            FightObject petFightObject = new FightObject(chara, monsterPetList.get(i), "pet");
            petFightObject.pos = FightManager.PET_POS.get(monsterIndex);
            petFightObject.fid = fc.id++;
            petFightObject.cid = fightObject2.fid;
            if (monsterIndex == 1) {
                petFightObject.leader = 1;
            }
            monsterTeam.add(petFightObject);
        }
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);
        FightManager.listFight.add(fc);
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select;
            vo_32985_0.user_action = chara.autofight_skillaction;
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno;
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(gameObjectChar);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject3 : fightObjectList1) {
            if (fightObject3.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject3.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject3.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject4 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject4.fid;
            vo_65019_0.leader = fightObject4.leader;
            vo_65019_0.weapon_icon = fightObject4.weapon_icon;
            vo_65019_0.pos = fightObject4.pos;
            vo_65019_0.rank = fightObject4.rank;
            vo_65019_0.vip_type = fightObject4.vipType;
            vo_65019_0.str = fightObject4.str;
            vo_65019_0.type = fightObject4.org_icon;
            vo_65019_0.durability = fightObject4.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject4.upgrade_level;
            vo_65019_0.upgrade_type = fightObject4.upgrade_type;
            vo_65019_0.dex = fightObject4.max_mofa;
            vo_65019_0.max_mana = fightObject4.max_mofa;
            vo_65019_0.max_life = fightObject4.max_shengming;
            vo_65019_0.def = fightObject4.max_shengming;
            vo_65019_0.org_icon = fightObject4.org_icon;
            vo_65019_0.suit_icon = fightObject4.suit_icon;
            vo_65019_0.suit_light_effect = fightObject4.suit_light_effect;
            vo_65019_0.special_icon = fightObject4.special_icon;
            vo_65019_0.portrait = fightObject4.org_icon;
            vo_65019_0.customIcon = fightObject4.customIcon;
            vo_65019_0.zhenlingLevel = fightObject4.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject4.zhenlingType;
            list65019.add(vo_65019_0);
        }
        send(fc, new MSG_C_FRIENDS(), list65019);
        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject5.fid;
            vo_65017_0.leader = fightObject5.leader;
            vo_65017_0.weapon_icon = fightObject5.weapon_icon;
            vo_65017_0.pos = fightObject5.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject5.vipType;
            vo_65017_0.str = fightObject5.str;
            vo_65017_0.type = fightObject5.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject5.upgrade_level;
            vo_65017_0.upgrade_type = fightObject5.upgrade_type;
            vo_65017_0.dex = fightObject5.max_mofa;
            vo_65017_0.max_mana = fightObject5.max_mofa;
            vo_65017_0.max_life = fightObject5.max_shengming;
            vo_65017_0.def = fightObject5.max_shengming;
            vo_65017_0.org_icon = fightObject5.org_icon;
            vo_65017_0.suit_icon = fightObject5.suit_icon;
            vo_65017_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65017_0.portrait = fightObject5.org_icon;
            vo_65017_0.special_icon = fightObject5.special_icon;
            vo_65017_0.customIcon = fightObject5.customIcon;
            vo_65017_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject5.zhenlingType;
            list65020.add(vo_65017_0);
        }
        send(fc, new MSG_C_OPPONENTS(), list65020);
        fightObjectList2 = getAllFightObject(fc);
        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);
        round(fc);
        //设置头顶标识
        GameCommonUtil.setCharaTitleFlag(chara);
    }

    // 怪物攻城、海盗、战神的战斗会调用这里
    public static void goFightBaseRenwu(Chara chara, List<String> monsterPerList, int boosId) {
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                if (i == 0) {
                    fightObject.leader = 1;
                }
                addFabao(fc, duiwu.get(i), fightObject);
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(pets.get(j), duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = pets.get(j).id;
                        fightObject.id = pets.get(j).id;
                        fightObject.cid = duiwu.get(i).id;
                        if (pets.get(j).tianshu.size() != 0) {
                            addFightTianShu(pets.get(j), fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        } else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PER_POS.get(num);
            fightObject2.fid = chara.id;
            fightObject2.leader = 1;
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            ft.add(fightObject2);
            addFabao(fc, chara, fightObject2);
            List<Petbeibao> pets2 = chara.pets;
            for (int k = 0; k < pets2.size(); ++k) {
                if (pets2.get(k).id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(pets2.get(k), chara);
                    fightObject2.pos = FightManager.PET_POS.get(num);
                    fightObject2.fid = pets2.get(k).id;
                    fightObject2.id = pets2.get(k).id;
                    fightObject2.cid = chara.id;
                    if (pets2.get(k).tianshu.size() != 0) {
                        addFightTianShu(pets2.get(k), fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            chara.isFight = true;
            ++num;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        for (int l = 0; l < monsterPerList.size(); ++l) {
            FightObject fightObject3 = new FightObject(monsterPerList.get(l), chara);
            int id = (int) (Math.random() * 100000.0);
            fightObject3.pos = FightManager.MONSTER_POS.get(l);
            fightObject3.id = id;
            fightObject3.fid = id;
            fightObject3.bossid = boosId;
            monsterTeam.add(fightObject3);
        }
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);
        FightManager.listFight.add(fc);
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select;
            vo_32985_0.user_action = chara.autofight_skillaction;
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno;
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.portrait = fightObject5.org_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        send(fc, new MSG_C_FRIENDS(), list65019);
        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = fightObject6.weapon_icon;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.portrait = fightObject6.org_icon;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        send(fc, new MSG_C_OPPONENTS(), list65020);
        fightObjectList2 = getAllFightObject(fc);
        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);
        round(fc);
    }

    // 这里宠物飞升的怪物
    public static void goFightfssc(Chara chara, List<String> monsterList) {
        // 如果全局战斗容器中已经有当前这个战斗容器了，则移除
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        // 新建战斗容器
        FightContainer fc = new FightContainer();
        // 建立角色方战斗队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        // 如果是团队作战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                addFabao(fc, duiwu.get(i), fightObject);
                if (i == 0) {
                    fightObject.leader = 1;
                }
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    Petbeibao petbeibao = pets.get(j);
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(petbeibao, duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = petbeibao.id;
                        fightObject.id = petbeibao.id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }
        // 如果是单人作战
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id; // 角色的战斗id就是角色id
            fightObject2.leader = 1; // 是战斗队长
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            // 将角色的法宝添加到战斗中
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2); // 角色加入到友方队伍
            List<Petbeibao> pets2 = chara.pets;
            // 将参战的宠物ID加入到友方队伍中
            for (int k = 0; k < pets2.size(); ++k) {
                Petbeibao petbeibao2 = pets2.get(k);
                if (petbeibao2.id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(petbeibao2, chara);
                    // 让宠物站在角色的正前方
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = petbeibao2.id; // 宠物的战斗id是其本身id
                    fightObject2.id = petbeibao2.id;
                    fightObject2.cid = chara.id;
                    fightObject2.shape = petbeibao2.petShuXing.get(0).shape;
                    fightObject2.petType = petbeibao2.petShuXing.get(0).penetrate;
                    // 如果宠物有天书
                    if (petbeibao2.tianshu != null && petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(petbeibao2, fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num; // 一个角色及其宠物的战斗已经初始化完成
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }

        // 初始化怪物方的队伍， 队伍类型为2
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        for (String monsterName : monsterList) {
            FightObject fightObject3 = new FightObject(chara, monsterName, 4, 81);
            fightObject3.pos = FightManager.MONSTER_POS.get(monsterIndex); // 设置怪物的战斗位置
            fightObject3.fid = fc.id++; // 战斗id为fc的id递增加1
            if (monsterIndex == 1) { // 将第二个怪物设置为队长
                fightObject3.leader = 1;
            }
            monsterTeam.add(fightObject3);
            ++monsterIndex;
        }
        // 将两个战斗队伍加入到战斗容器中
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);

        // 再将战斗容器加入到全局战斗列表中,定时任务会读取判断战斗列表是否为空
        FightManager.listFight.add(fc);

        // 如果角色开启了自动战斗，自动战斗的值为1
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select; // 设置为第1回合
            vo_32985_0.user_action = chara.autofight_skillaction; // 自动战斗时的技能类型
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno; // 自动技能编号
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 进入战斗背景
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        // 获取友方队伍
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            // 战斗对象的type==2，表示对象召回？
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }

        // 这里还是获取友方的战斗集合
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        // 将友方战斗对象包装好发送
        send(fc, new MSG_C_FRIENDS(), list65019);

        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = 0;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        // 将敌方战斗对象包装好发送
        send(fc, new MSG_C_OPPONENTS(), list65020);
        // 获取所有的参战对象，不同于goFight只获取友方对象
        fightObjectList2 = getAllFightObject(fc);
        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);
        // 这里是发送战斗容器的战斗回合发送到前端
        round(fc);
    }

    // 挑战地图守护
    public static void goFightMapGuard(Chara chara, List<String> monsterList, List<CharaStatue> defCharaStatueList,
                                       String type) {
        for (FightContainer fc = getFightContainer(chara.id); fc != null; fc = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fc);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;

        int i;
        FightObject fightObject;
        List<CharaStatue> attCharaStatueList = new ArrayList<>();
        // 添加挑战方的队伍
        List<Chara> duiwu = session.gameTeam.duiwu;
        for (i = 0; i < duiwu.size(); ++i) {
            fightObject = new FightObject((Chara) duiwu.get(i));
            fightObject.pos = FightManager.PERSON_POS.get(num);
            fightObject.fid = ((Chara) duiwu.get(i)).id;
            addFabao(fc, (Chara) duiwu.get(i), fightObject);
            if (i == 0) {
                fightObject.leader = 1;
            }

            CharaStatue charaStatue = new CharaStatue();
            charaStatue.copyFrom(duiwu.get(i));
            charaStatue.copyJiNengList(fightObject.skillsList);
            attCharaStatueList.add(charaStatue);

            ft.add(fightObject);
            List<Petbeibao> pets = ((Chara) duiwu.get(i)).pets;

            for (int j = 0; j < pets.size(); ++j) {
                Petbeibao petbeibao = (Petbeibao) pets.get(j);
                if (((Petbeibao) pets.get(j)).id == ((Chara) duiwu.get(i)).chongwuchanzhanId) {
                    fightObject = new FightObject(petbeibao, duiwu.get(i));
                    fightObject.pos = (Integer) PERSON_POS.get(num) + 5;
                    fightObject.fid = petbeibao.id;
                    fightObject.id = petbeibao.id;
                    fightObject.cid = ((Chara) duiwu.get(i)).id;
                    if (pets.get(j).tianshu.size() != 0) {
                        addFightTianShu(pets.get(j), fightObject, fc);
                    }
                    ft.add(fightObject);

                    charaStatue.copyPet(petbeibao);
                    charaStatue.copyPetJiNengList(fightObject.skillsList);
                    break;
                }
            }
            ++num;
            duiwu.get(i).isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
        }

        // 添加我方队伍角色charastatue到战斗容器
        fc.attCharaStatueList = attCharaStatueList;

        // 添加敌方队伍
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        num = 0;
        // 如果被挑战的守护神列表为空，就按照角色所在的地图生成对应等级的守护神和怪物
        if (defCharaStatueList == null) {
            int pos = 0;
            for (int k = 0; k < monsterList.size(); k += 2, pos++) {
                fightObject = new FightObject(chara, monsterList.get(k), false);
                fightObject.pos = FightManager.PER_POS.get(pos);
                fightObject.fid = fc.id++;
                fightObject.isGuaiWuHide = 1;
                monsterTeam.add(fightObject);
                FightObject fightObject2 = new FightObject(chara, monsterList.get(k + 1), true);
                fightObject2.pos = FightManager.PET_POS.get(pos);
                fightObject2.fid = fc.id++;
                fightObject2.fightType = "地图守护神";
                fightObject2.cid = fightObject.fid;
                monsterTeam.add(fightObject2);
            }
        }
        // 如果被挑战的守护神列表不为空，就从数据库快照中读取守护神信息
        else {
            for (CharaStatue defCharaStatue : defCharaStatueList) {
                // 快照
                fightObject = new FightObject(defCharaStatue);
                fightObject.str = chara.mapName + "守护神";
                fightObject.pos = MONSTER_POS.get(num);
                fightObject.isGuaiWuHide = 1;
                fightObject.fid = fc.id++;
                if (num == 0) {
                    fightObject.leader = 1;
                }
                fightObject.id = fightObject.fid;
                fightObject.type = 3;
                monsterTeam.add(fightObject);

                // 宠物
                if (null != defCharaStatue.petbeibao) {
                    FightObject fightObjectPet = new FightObject(defCharaStatue.petbeibao, null);
                    fightObjectPet.pos = MONSTER_POS.get(num) + 5;
                    fightObjectPet.fid = fc.id++;
                    fightObjectPet.type = 4;
                    fightObjectPet.skillsList = defCharaStatue.petJiNengList;
                    fightObjectPet.fightType = "地图守护神";
                    fightObjectPet.cid = fightObject.fid;
                    monsterTeam.add(fightObjectPet);
                }
                num++;
            }
        }

        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);
        FightManager.listFight.add(fc);
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select;
            vo_32985_0.user_action = chara.autofight_skillaction;
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno;
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject object : fightObjectList1) {
            if (object.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = object.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(object.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject object2 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = object2.fid;
            vo_65019_0.leader = object2.leader;
            vo_65019_0.weapon_icon = object2.weapon_icon;
            vo_65019_0.pos = object2.pos;
            vo_65019_0.rank = object2.rank;
            vo_65019_0.vip_type = object2.vipType;
            vo_65019_0.str = object2.str;
            vo_65019_0.type = object2.org_icon;
            vo_65019_0.durability = object2.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = object2.upgrade_level;
            vo_65019_0.upgrade_type = object2.upgrade_type;
            vo_65019_0.dex = object2.max_mofa;
            vo_65019_0.max_mana = object2.max_mofa;
            vo_65019_0.max_life = object2.max_shengming;
            vo_65019_0.def = object2.max_shengming;
            vo_65019_0.org_icon = object2.org_icon;
            vo_65019_0.suit_icon = object2.suit_icon;
            vo_65019_0.suit_light_effect = object2.suit_light_effect;
            vo_65019_0.special_icon = object2.special_icon;
            vo_65019_0.portrait = object2.org_icon;
            vo_65019_0.customIcon = object2.customIcon;
            vo_65019_0.zhenlingLevel = object2.zhenlingLevel;
            vo_65019_0.zhenlingType = object2.zhenlingType;
            list65019.add(vo_65019_0);
        }
        send(fc, new MSG_C_FRIENDS(), list65019);
        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject object3 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = object3.fid;
            vo_65017_0.leader = object3.leader;
            vo_65017_0.weapon_icon = object3.weapon_icon;
            vo_65017_0.pos = object3.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = object3.vipType;
            vo_65017_0.str = object3.str;
            vo_65017_0.type = object3.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = object3.upgrade_level;
            vo_65017_0.upgrade_type = object3.upgrade_type;
            vo_65017_0.dex = object3.max_mofa;
            vo_65017_0.max_mana = object3.max_mofa;
            vo_65017_0.max_life = object3.max_shengming;
            vo_65017_0.def = object3.max_shengming;
            vo_65017_0.org_icon = object3.org_icon;
            vo_65017_0.suit_icon = object3.suit_icon;
            vo_65017_0.suit_light_effect = object3.suit_light_effect;
            vo_65017_0.portrait = object3.org_icon;
            vo_65017_0.special_icon = object3.special_icon;
            vo_65017_0.customIcon = object3.customIcon;
            vo_65017_0.zhenlingLevel = object3.zhenlingLevel;
            vo_65017_0.zhenlingType = object3.zhenlingType;
            list65020.add(vo_65017_0);
        }
        send(fc, new MSG_C_OPPONENTS(), list65020);
        fightObjectList2 = getAllFightObject(fc);
        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);
        round(fc);
    }

    public static void goFightShouhu(Chara chara, List<String> monsterList) {
        // 如果全局战斗容器中已经有当前这个战斗容器了，则移除
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        // 新建战斗容器
        FightContainer fc = new FightContainer();
        // 建立角色方战斗队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        // 如果是团队作战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                addFabao(fc, duiwu.get(i), fightObject);
                if (i == 0) {
                    fightObject.leader = 1;
                }
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    Petbeibao petbeibao = pets.get(j);
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(petbeibao, duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = petbeibao.id;
                        fightObject.id = petbeibao.id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }
        // 如果是单人作战
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id; // 角色的战斗id就是角色id
            fightObject2.leader = 1; // 是战斗队长
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            // 将角色的法宝添加到战斗中
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2); // 角色加入到友方队伍
            List<Petbeibao> pets2 = chara.pets;
            // 将参战的宠物ID加入到友方队伍中
            for (int k = 0; k < pets2.size(); ++k) {
                Petbeibao petbeibao2 = pets2.get(k);
                if (petbeibao2.id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(petbeibao2, chara);
                    // 让宠物站在角色的正前方
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = petbeibao2.id; // 宠物的战斗id是其本身id
                    fightObject2.id = petbeibao2.id;
                    fightObject2.cid = chara.id;
                    fightObject2.shape = petbeibao2.petShuXing.get(0).shape;
                    fightObject2.petType = petbeibao2.petShuXing.get(0).penetrate;
                    // 如果宠物有天书
                    if (petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(petbeibao2, fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num; // 一个角色及其宠物的战斗已经初始化完成
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }

        // 初始化怪物方的队伍， 队伍类型为2
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        for (String monsterName : monsterList) {
            FightObject fightObject3 = new FightObject(chara, monsterName, 1); // new
            fightObject3.pos = FightManager.MONSTER_POS.get(monsterIndex); // 设置怪物的战斗位置
            fightObject3.fid = fc.id++; // 战斗id为fc的id递增加1
            if (monsterIndex == 1) { // 将第二个怪物设置为队长
                fightObject3.leader = 1;
            }
            monsterTeam.add(fightObject3);
            ++monsterIndex;
        }
        // 将两个战斗队伍加入到战斗容器中
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);

        // 再将战斗容器加入到全局战斗列表中,定时任务会读取判断战斗列表是否为空
        FightManager.listFight.add(fc);

        // 如果角色开启了自动战斗，自动战斗的值为1
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select; // 设置为第1回合
            vo_32985_0.user_action = chara.autofight_skillaction; // 自动战斗时的技能类型
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno; // 自动技能编号
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        // 获取友方队伍
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            // 战斗对象的type==2，表示对象召回？
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }

        // 这里还是获取友方的战斗集合
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        // 将友方战斗对象包装好发送
        send(fc, new MSG_C_FRIENDS(), list65019);

        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = 0;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        // 将敌方战斗对象包装好发送
        send(fc, new MSG_C_OPPONENTS(), list65020);

        fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;

        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);

        // 这里是发送战斗容器的战斗回合发送到前端
        round(fc);
    }

    // 单个角色和一群怪物进行战斗（巡逻会调用这里，巡逻可以召唤守护）
    public static void goFight(Chara chara, List<String> monsterList, boolean... isShouHu) {
        // 如果全局战斗容器中已经有当前这个战斗容器了，则移除
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        // 新建战斗容器
        FightContainer fc = new FightContainer();
        // 建立角色方战斗队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        if (session == null) {
            return;
        }
        int num = 0;
        // 如果是团队作战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                // 设置队伍战斗状态
                duiwu.get(i).isFight = true;
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                addFabao(fc, duiwu.get(i), fightObject);
                if (i == 0) {
                    fightObject.leader = 1;
                }
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    Petbeibao petbeibao = pets.get(j);
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(petbeibao, duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = petbeibao.id;
                        fightObject.id = petbeibao.id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }
        // 如果是单人作战
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id; // 角色的战斗id就是角色id
            fightObject2.leader = 1; // 是战斗队长
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            // 将角色的法宝添加到战斗中
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2); // 角色加入到友方队伍
            List<Petbeibao> pets2 = chara.pets;
            // 将参战的宠物ID加入到友方队伍中
            for (int k = 0; k < pets2.size(); ++k) {
                Petbeibao petbeibao2 = pets2.get(k);
                if (petbeibao2.id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(petbeibao2, chara);
                    // 让宠物站在角色的正前方
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = petbeibao2.id; // 宠物的战斗id是其本身id
                    fightObject2.id = petbeibao2.id;
                    fightObject2.cid = chara.id;
                    fightObject2.shape = petbeibao2.petShuXing.get(0).shape;
                    fightObject2.petType = petbeibao2.petShuXing.get(0).penetrate;
                    // 如果宠物有天书
                    if (petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(petbeibao2, fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num; // 一个角色及其宠物的战斗已经初始化完成
            // 设置单人作战状态
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }

        if (isShouHu == null || isShouHu.length == 0) {
            // 添加守护
            for (int i = 0; i < chara.listshouhu.size() && num < 5; ++i) {
                if (chara.listshouhu.get(i).listShouHuShuXing.get(0).nil != 0) {
                    FightObject fightObject = new FightObject(chara.listshouhu.get(i));
                    fightObject.pos = FightManager.PERSON_POS.get(num);
                    fightObject.fid = fc.id++; // 守护的id是fc的id递增加1
                    ft.add(fightObject); // 将守护加入到战斗队伍中
                    ++num; // 新增到下一个对象
                }
            }
        }

        // 初始化怪物方的队伍， 队伍类型为2
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        GameMap gameMap = GameObjectCharMng.getGameObjectChar(chara.id).gameMap;
        for (String monsterName : monsterList) {
            FightObject fightObject3 = gameMap.isDugeno()
                    ? new FightObject(GameData.that.baseFightObjectService.findOneByName(monsterName))
                    : new FightObject(chara, monsterName);
            if (monsterIndex >= FightManager.MONSTER_POS.size()) {
                log.info("数组越界了.....");
                break;
            }
            fightObject3.pos = FightManager.MONSTER_POS.get(monsterIndex); // 设置怪物的战斗位置
            fightObject3.fid = fc.id++; // 战斗id为fc的id递增加1
            if (monsterIndex == 1) { // 将第二个怪物设置为队长
                fightObject3.leader = 1;
            }
            monsterTeam.add(fightObject3);
            ++monsterIndex;
        }
        // 将两个战斗队伍加入到战斗容器中
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);

        // 再将战斗容器加入到全局战斗列表中,定时任务会读取判断战斗列表是否为空
        FightManager.listFight.add(fc);

        // 如果角色开启了自动战斗，自动战斗的值为1
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = 1; // 设置为第1回合
            vo_32985_0.user_action = chara.autofight_skillaction; // 自动战斗时的技能类型
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno; // 自动技能编号
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 进入战斗背景
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        // 获取友方队伍
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            // 战斗对象的type==2，表示对象召回？
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }

        // 这里还是获取友方的战斗集合
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        // 将友方战斗对象包装好发送
        send(fc, new MSG_C_FRIENDS(), list65019);

        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = 0;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        // 将敌方战斗对象包装好发送
        send(fc, new MSG_C_OPPONENTS(), list65020);

        fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;

        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);

        // 这里是发送战斗容器的战斗回合发送到前端
        round(fc);
    }

    // 挑战上古妖王、万年妖王
    public static void goFightYaowang(Chara chara, List<String> monsterList, Vo_APPEAR vo_65529_0) {
        for (FightContainer fc = getFightContainer(chara.id); fc != null; fc = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fc);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        if (session.gameTeam != null) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                if (i == 0) {
                    fightObject.leader = 1;
                }
                addFabao(fc, duiwu.get(i), fightObject);
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(pets.get(j), duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = pets.get(j).id;
                        fightObject.id = pets.get(j).id;
                        fightObject.cid = duiwu.get(i).id;
                        if (pets.get(j).tianshu.size() != 0) {
                            addFightTianShu(pets.get(j), fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        } else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id;
            fightObject2.leader = 1;
            fightObject2.id = chara.id;
            ft.add(fightObject2);
            addFabao(fc, chara, fightObject2);
            List<Petbeibao> pets2 = chara.pets;
            for (int k = 0; k < pets2.size(); ++k) {
                if (pets2.get(k).id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(pets2.get(k), chara);
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = pets2.get(k).id;
                    fightObject2.id = pets2.get(k).id;
                    fightObject2.cid = chara.id;
                    if (pets2.get(k).tianshu.size() != 0) {
                        addFightTianShu(pets2.get(k), fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
            ++num;
        }
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        num = 0;

        for (String monsterName : monsterList) {
            FightObject fightObject3 = new FightObject(chara, monsterName, vo_65529_0, null);
            fightObject3.id = vo_65529_0.id;
            fightObject3.pos = FightManager.MONSTER_POS.get(num);
            fightObject3.fid = fc.id++;
            fightObject3.bossid = vo_65529_0.id;
            if (num == 1) {
                fightObject3.leader = 1;
            }
            monsterTeam.add(fightObject3);
            ++num;
        }
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);
        FightManager.listFight.add(fc);
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select;
            vo_32985_0.user_action = chara.autofight_skillaction;
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno;
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.portrait = fightObject5.org_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        send(fc, new MSG_C_FRIENDS(), list65019);
        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = fightObject6.weapon_icon;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.portrait = fightObject6.org_icon;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        send(fc, new MSG_C_OPPONENTS(), list65020);
        fightObjectList2 = getAllFightObject(fc);
        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);
        round(fc);
    }

    // 当战斗容器中的友方成员初始化战斗请求之后，就会调用这里
    public static void addRequest(FightContainer fightContainer, FightRequest fightRequest) {
        log.info("当战斗容器中的友方成员初始化战斗请求之后，就会调用这里"+(fightContainer == null)+"当前状态："+fightContainer.state.get()+"战斗ID "+fightContainer.id);
        if (fightContainer == null) {
            return;
        }
       // log.info("fightContainer.state.get():"+fightContainer.state.get());
        // 战斗容器状态为1是正常状态
        if (fightContainer.state.get() != 1) {
            return;
        }

        if (fightRequest != null) {
            FightObject fightObject = getFightObject(fightContainer, fightRequest.id);
            if (fightObject.fightRequest == null) {
                fightObject.fightRequest = fightRequest;
            }
            if (fightObject.isDead()) {
                return;
            }
        }
        boolean doAction = false;
        try{
             doAction = isAllRequested(fightContainer);
        }catch(Exception e){

        }
       log.info("这里检查战斗是否初始化了："+doAction+" 战斗ID "+fightContainer.id);
        // 检查所有友方的战斗请求是否都初始化了，是则为true

       // log.info("检查所有友方的战斗请求是否都初始化了，是则为true"+doAction);
        if (doAction) {
            List<FightObject> doActionList = getAllFightObject(fightContainer);
            sortActions(doActionList);
            // 设置战斗容器的活动列表为排序后的战斗对象
            fightContainer.doActionList = doActionList;
            // 如果战斗没结束的逻辑

            if (!fabao(fightContainer)) {
            //    log.info("如果战斗没结束的逻辑:");
                doAction(fightContainer);
            }
            endaction(fightContainer);
        }
    }

    // 定时任务会调用这里的自动战斗
    /**
     * 自动战斗
     */
    public static void doAutoSkill(final FightContainer fightContainer) {
        final List<FightObject> allFightObject = getAllFightObject(fightContainer);
        for (final FightObject fightObject : allFightObject) {
            if ((fightObject.type == 1 || fightObject.type == 2) && fightObject.autofight_select != 0) {
                if (fightObject.fightRequest != null) {
                    continue;
                }
                boolean flag = fightObject.type != 2 || !fightObject.isDead();
                if (flag) {
                    fightObject.fightRequest = new FightRequest();
                    fightObject.fightRequest.id = fightObject.fid;
                    fightObject.fightRequest.action = fightObject.autofight_skillaction;
                    fightObject.fightRequest.para = fightObject.autofight_skillno;
                    generateActionDM(fightContainer, fightObject, fightObject.fightRequest);
                }
                //                    // 宠物
                if (fightObject.type == 2) {
                    // 如果人物都没自动宠物也无法点击自动
                    FightObject ownerFight = getFightObject(fightObject.cid);
                    if (ownerFight != null && ownerFight.autofight_select == 0) {
                        fightObject.autofight_select = 0;
                        continue;
                    }
                    FightManager.sendTeam(fightContainer, fightObject.cid, new MSG_C_SANDGLASS(),
                            new Vo_C_SANDGLASS(fightObject.cid, 0));
                }
                if (fightObject.type == 1) {
                    //MSG_C_SANDGLASS
                    //如果宠物没有自动的话
                    FightObject fightObjectPet = getFightObjectPet(fightContainer, fightObject);
                    if (fightObjectPet != null) {
                        fightObjectPet.autofight_select = 1;
                        if (fightObject.autofight_skillaction == 0) {
                            fightObject.autofight_skillaction = 1;
                        }
                    }
                    FightManager.sendTeam(fightContainer, fightObject.fid, new MSG_C_SANDGLASS(),
                            new Vo_C_SANDGLASS(fightObject.fid, 0));
                }
                    fightObject.fightRequest = new FightRequest();
                    fightObject.fightRequest.id = fightObject.fid;
                    fightObject.fightRequest.action = fightObject.autofight_skillaction;
                    fightObject.fightRequest.para = fightObject.autofight_skillno;

                    //如果是防御的话.
                    if (fightObject.autofight_skillaction == 1) {
                        fightObject.fightRequest.vid = fightObject.fid;
                    } else if (!FightSkill.isOpSkill(fightObject.autofight_skillno)) { //如果是辅助技能
                        generateActionVt(fightContainer, fightObject, fightObject.fightRequest);
                    } else {
                        generateActionDM(fightContainer, fightObject, fightObject.fightRequest);
                    }
                    // 选择技能
                    Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
                    vo_53715_0.attacker_id = fightObject.fightRequest.id;
                    vo_53715_0.victim_id = fightObject.fightRequest.vid;
                    vo_53715_0.action = fightObject.fightRequest.action;
                    if (vo_53715_0.action != 2) {
                        vo_53715_0.no = fightObject.fightRequest.para;
                    }
                    if (fightObject.type == 1) {
                        GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0, fightObject.fid);
                    } else {
                        GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0, fightObject.cid);
                    }
                    // 设置自动喊话
                    FightManager.setAutoTalkMsg(fightObject, fightObject.fightRequest);
            }
        }
        addRequest(fightContainer, null);
    }
//    public static void doAutoSkill(FightContainer fightContainer) {
//        try {
//            List<FightObject> allFightObject = getAllFightObject(fightContainer);
//            // 由于怪物的自动战斗选项为0，所以下面是处理友方的战斗请求
//            for (FightObject fightObject : allFightObject) {
//                // 如果是角色和宠物，就设置他们的自动出手技能
//                if ((fightObject.type == 1 || fightObject.type == 2) && fightObject.autofight_select != 0) {
//                    if (fightObject.fightRequest != null) {
//                        continue;
//                    }
//                    if (fightObject.type == 2 && fightObject.isDead()) {
//                        continue;
//                    }
//                    // 宠物
//                    if (fightObject.type == 2) {
//                        // 如果人物都没自动宠物也无法点击自动
//                        FightObject ownerFight = getFightObject(fightObject.cid);
//                        if (ownerFight != null && ownerFight.autofight_select == 0) {
//                            fightObject.autofight_select = 0;
//                            continue;
//                        }
//                        FightManager.sendTeam(fightContainer, fightObject.cid, new MSG_C_SANDGLASS(),
//                                new Vo_C_SANDGLASS(fightObject.cid, 0));
//                    }
//                    if (fightObject.type == 1) {
//                        //如果宠物没有自动的话
//                        FightObject fightObjectPet = getFightObjectPet(fightContainer, fightObject);
//                        if (fightObjectPet != null) {
//                            fightObjectPet.autofight_select = 1;
//                            if (fightObject.autofight_skillaction == 0) {
//                                fightObject.autofight_skillaction = 1;
//                            }
//                        }
//                        FightManager.sendTeam(fightContainer, fightObject.fid, new MSG_C_SANDGLASS(),
//                                new Vo_C_SANDGLASS(fightObject.fid, 0));
//                    }
//                    fightObject.fightRequest = new FightRequest();
//                    fightObject.fightRequest.id = fightObject.fid;
//                    fightObject.fightRequest.action = fightObject.autofight_skillaction;
//                    fightObject.fightRequest.para = fightObject.autofight_skillno;
//
//                    //如果是防御的话.
//                    if (fightObject.autofight_skillaction == 1) {
//                        fightObject.fightRequest.vid = fightObject.fid;
//                    } else if (!FightSkill.isOpSkill(fightObject.autofight_skillno)) { //如果是辅助技能
//                        generateActionVt(fightContainer, fightObject, fightObject.fightRequest);
//                    } else {
//                        generateActionDM(fightContainer, fightObject, fightObject.fightRequest);
//                    }
//                    // 选择技能
//                    Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
//                    vo_53715_0.attacker_id = fightObject.fightRequest.id;
//                    vo_53715_0.victim_id = fightObject.fightRequest.vid;
//                    vo_53715_0.action = fightObject.fightRequest.action;
//                    if (vo_53715_0.action != 2) {
//                        vo_53715_0.no = fightObject.fightRequest.para;
//                    }
//                    if (fightObject.type == 1) {
//                        GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0, fightObject.fid);
//                    } else {
//                        GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0, fightObject.cid);
//                    }
//                    // 设置自动喊话
//                    FightManager.setAutoTalkMsg(fightObject, fightObject.fightRequest);
//                }
//            }
//            addRequest(fightContainer, null);
//        } catch (Exception e) {
//            // 这里最好捕获下异常
//            log.error("{}", e);
//        }
//    }

    // 时间到了强制触发战斗
    public static void doTimeupSkill(FightContainer fightContainer) {
        try {
            List<FightObject> allFightObject = getAllFightObject(fightContainer);
            for (final FightObject fightObject : allFightObject) {
                if (fightObject.type == 1 || fightObject.type == 2) {
                    if (fightObject.type == 2 && fightObject.isDead()) {
                        continue;
                    }
                    if (fightObject.isGuaiWuHide == 1) {
                        continue;
                    }
                    if (fightObject.type == 1 && fightObject.autofight_select != 1) {
                        fightObject.autofight_select = 1;
                        FightObject fightObjectPet = getFightObjectPet(fightContainer, fightObject);
                        if (fightObjectPet != null) {
                            fightObjectPet.autofight_select = 1;
                        }
                    }
                    if (fightObject.fightRequest == null) {
                        fightObject.fightRequest = new FightRequest();
                        fightObject.fightRequest.id = fightObject.fid;
                        fightObject.fightRequest.action = fightObject.autofight_skillaction;
                        fightObject.fightRequest.para = fightObject.autofight_skillno;
                        generateActionDM(fightContainer, fightObject, fightObject.fightRequest);
                    }
                }
            }
//            for (FightObject fightObject : allFightObject) {
//                if (fightObject != null) {
//                    if (fightObject.type == 1 || (fightObject.type == 2)) {
//                        if (fightObject.type == 2 && fightObject.isDead()) {
//                            continue;
//                        }
//                        fightObject.autofight_select = 1;
//                        if (fightObject.autofight_skillaction == 0) {
//                            fightObject.autofight_skillaction = 0;
//                            fightObject.autofight_skillno = 0;
//                        }
//                        // 如果对象为1并且请求为null
//                        if (fightObject.type == 1) {
//                            // 把对象设置为自动
//                            GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightObject.fid);
//                            if (gameObjectChar != null) {
//                                Map<String, Object> dataMap = new HashMap<>();
//                                dataMap.put("auto_fight", 1);
//                                gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(),
//                                        new Vo_UPDATE_DYNAMIC(gameObjectChar.chara.id, dataMap));
//                            }
//                        }
//                        fightObject.fightRequest = new FightRequest();
//                        fightObject.fightRequest.id = fightObject.fid;
//                        // 如果角色设置过自动战斗技能
//                        fightObject.fightRequest.action = fightObject.autofight_skillaction;
//                        fightObject.fightRequest.para = fightObject.autofight_skillno;
//                        //如果是防御的话.
//                        if (fightObject.autofight_skillaction == 1) {
//                            fightObject.fightRequest.vid = fightObject.fid;
//                        } else if (!FightSkill.isOpSkill(fightObject.autofight_skillno)) { //如果是辅助技能
//                            generateActionVt(fightContainer, fightObject, fightObject.fightRequest);
//                        } else {
//                            generateActionDM(fightContainer, fightObject, fightObject.fightRequest);
//                        }
//                        // 选择技能
//                        Vo_SELECT_COMMAND vo_53715_0 = new Vo_SELECT_COMMAND();
//                        vo_53715_0.attacker_id = fightObject.fightRequest.id;
//                        vo_53715_0.victim_id = fightObject.fightRequest.vid;
//                        vo_53715_0.action = fightObject.fightRequest.action;
//                        if (vo_53715_0.action != 2) {
//                            vo_53715_0.no = fightObject.fightRequest.para;
//                        }
//                        if (fightObject.type == 1) {
//                            GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0, fightObject.fid);
//                        } else {
//                            GameObjectChar.send(new MSG_SELECT_COMMAND(), vo_53715_0, fightObject.cid);
//                        }
//                        // 设置自动喊话
//                        FightManager.setAutoTalkMsg(fightObject, fightObject.fightRequest);
//                    }
//                }
//            }
        } catch (Exception e) {
            log.error("{}", e);
        } finally {
            // 这个请求无论如何都要发出去
            addRequest(fightContainer, null);
        }
    }

    // 将战斗对象按照出手速度进行排序
    public static void sortActions(List<FightObject> doActionList) {
        doActionList.sort((ob1, ob2) -> ob2.parry + ob2.parry_ext - ob1.parry - ob1.parry_ext);
    }

    // 生成战斗请求，填充到战斗对象中
    private static FightRequest generateAction(FightContainer fightContainer, FightObject fightObject) {
        FightRequest fightRequest = new FightRequest();
        List<JiNeng> skillsList = fightObject.skillsList;
        // 如果该对象没有技能，则设置普攻
        if (skillsList == null || skillsList.size() == 0) {
            fightRequest.para = 2;
            fightRequest.action = 2;
            fightRequest.id = fightObject.fid;
            generateActionDM(fightContainer, fightObject, fightRequest);
        }
        // 如果该角色有技能
        else {
            // 从怪物的技能列表中随机的生成一个技能随机选择一个技能？
            JiNeng jiNeng = skillsList.get(FightManager.RANDOM.nextInt(skillsList.size()));
            fightRequest.para = jiNeng.skill_no;
            fightRequest.action = 3; // 表示为技能
            fightRequest.id = fightObject.fid;
            if (fightObject.autofight_skillaction == 1) {
                fightRequest.vid = fightObject.fid;
            } else if (FightSkill.isOpSkill(jiNeng.skill_no)) {
                generateActionDM(fightContainer, fightObject, fightRequest);
            }
            // 如果是辅助技能
            else {
                generateActionVt(fightContainer, fightObject, fightRequest);
            }
        }
        return fightRequest;
    }

    // 自动战斗时会调用这个函数，随机从对面选择一个攻击对象，填入到战斗请求的vid
    public static FightRequest generateActionDM(FightContainer fightContainer, FightObject fightObject,
                                                FightRequest fightRequest) {
        // 先获取地方参战对象
        ArrayList<FightObject> fightObjects = new ArrayList<>();
        // 获取敌方队伍，这里为怪物
        FightTeam opponentsFightTeam = getFightTeamDM(fightContainer, fightObject.fid);
        for (FightObject object : opponentsFightTeam.fightObjectList) {
            if (object.canbeVictim()) {
                fightObjects.add(object);
            }
        }

        // 如果对面的全部死亡了，则返回空
        FightObject target = null;
        if (fightObjects.size() == 0) {
            fightRequest.vid = 0;
            return null;
        }
        // 对面只有1个对象
        if (fightObjects.size() == 1) {
            target = fightObjects.get(0);
        }
        // 随机从对面选择一个
        else {
            int index = new Random().nextInt(fightObjects.size());
            target = fightObjects.get(index);
        }
        // 设置战斗请求的被攻击者id
        fightRequest.vid = target.fid;
        return fightRequest;
    }

    public static FightRequest generateActionVt(FightContainer fightContainer, FightObject fightObject,
                                                FightRequest fightRequest) {
        ArrayList<FightObject> fightObjects = new ArrayList<FightObject>();
        FightTeam friendsFightTeam = getFightTeam(fightContainer, fightObject.fid);
        for (FightObject object : friendsFightTeam.fightObjectList) {
            if (object.canbeVictim()) {
                fightObjects.add(object);
            }
        }
        FightObject target = null;
        if (fightObjects.size() == 0) {
            // add tzhang
            fightRequest.vid = 0;
            return null;
        }
        if (fightObjects.size() == 1) {
            target = fightObjects.get(0);
        } else {
            int index = new Random().nextInt(fightObjects.size());
            target = fightObjects.get(index);
        }
        fightRequest.vid = target.fid;
        return fightRequest;
    }

    public static FightRequest generateActionHunluan(FightContainer fightContainer, FightObject fightObject,
                                                     FightRequest fightRequest) {
        List<FightObject> allFightObject = getAlive(getAllFightObject(fightContainer));
        Iterator<FightObject> iterator = allFightObject.iterator();
        while (iterator.hasNext()) {
            FightObject next = iterator.next();
            if (next.fid == fightObject.fid) {
                iterator.remove();
            }
        }
        FightObject target = allFightObject.get(new Random().nextInt(allFightObject.size()));
        fightRequest.vid = target.fid;
        return fightRequest;
    }

    /**
     * 玩家点击操作完毕后调用
     *
     * @param fightContainer
     */
    public static void doAction(FightContainer fightContainer) {
       // log.info("玩家点击操作完毕后调用_______这里给state赋值为3 是什么意思");
        fightContainer.state.set(3); // fightContainer.state 默认为1，是自动打
        List<FightRecord> fightRecords = new ArrayList<>();
        while (fightContainer.doActionList != null && !fightContainer.doActionList.isEmpty()) {
            FightObject fightObject = fightContainer.doActionList.remove(0);
          //  log.info(" FightObject fightObject是不是等于空："+ (fightObject == null));
            try {
                if (fightObject == null) {
                    continue;
                }
                FightRequest fightRequest = fightObject.fightRequest;
                if (fightRequest == null) {
                    fightRequest = generateAction(fightContainer, fightObject);
                }
                //怪物
                if (fightObject.type != 1) {
                    switch (fightObject.fightType) {
                        case "TTT_TYPE": //如果人物死亡是需要主动拉人的
                            //如果自己的血量小于百分之30就优先加自己的
                            if (fightObject.getShengming() < (fightObject.getMax_shengming() * 0.3)) {
                                fightObject.fightRequest = new FightRequest();
                                fightRequest.id = fightObject.fid;
                                fightRequest.vid = fightObject.fid;
                                fightRequest.action = 4;
                                fightRequest.para = 8888;
                                fightRequest.item_type = 8888;
                            } else {
                                FightObject fightObjectMaster = FightManager.getFightObject(fightObject.cid);
                                if (fightObjectMaster != null && fightObjectMaster.type != 1) {
                                    //如果死亡了或者是血量小于百分之10则自动加血
                                    if (fightObjectMaster.isDead() ||
                                            fightObjectMaster.getShengming() < (fightObjectMaster.getMax_shengming() * 0.1)) {
                                        fightObject.fightRequest = new FightRequest();
                                        fightRequest.id = fightObject.fid;
                                        fightRequest.vid = fightObjectMaster.fid;
                                        fightRequest.action = 4;
                                        fightRequest.para = 8888;
                                        fightRequest.item_type = 8888;
                                    }
                                }
                            }
                            break;
                        case "天地星":
                            //如果有队友死亡则随机拉一个人
                            List<FightObject> deads = FightManager.getDeads(FightManager.getFightTeam(fightContainer, fightObject.fid).
                                    fightObjectList);
                            if (!deads.isEmpty()) {
                                if (ThreadLocalRandom.current().nextBoolean()) {
                                    int nextInt = ThreadLocalRandom.current().nextInt(deads.size());
                                    FightObject dead = deads.get(nextInt);
                                    if (dead.isDead()) {
                                        //给他拉血
                                        fightObject.fightRequest = new FightRequest();
                                        fightRequest.id = fightObject.fid;
                                        fightRequest.vid = dead.fid;
                                        fightRequest.action = 4;
                                        fightRequest.para = 88881;
                                        fightRequest.item_type = 8888;
                                    }
                                }
                            }
                            break;
                        case "地图守护神":
                            FightObject ditu = FightManager.getFightObject(fightObject.cid);
                            if (ditu != null && ditu.type != 1) {
                                //如果死亡了或者是血量小于百分之10则自动加血
                                if (ditu.isDead() ||
                                        ditu.getShengming() < (ditu.getMax_shengming() * 0.1)) {
                                    fightObject.fightRequest = new FightRequest();
                                    fightRequest.id = fightObject.fid;
                                    fightRequest.vid = ditu.fid;
                                    fightRequest.action = 4;
                                    fightRequest.para = 8888;
                                    fightRequest.item_type = 8888;
                                }
                            }
                            break;
                    }
                }
                if (fightRequest.vid == 0 && fightRequest.action != 7 && fightRequest.action != 4) {
                    continue;
                }
                // 如果角色已经死亡或者不能被攻击，则continue
                if ((fightObject.isDead() || !fightObject.canAtta()) && fightRequest.action != 7
                        && fightRequest.action != 4) {
                    continue;
                }

                if (fightObject.isYiwang() && fightRequest.action != 7 && fightRequest.action != 1) {
                    //如果不是
                    if (fightRequest.action != 2) {
                        continue;
                    }
                    //普通攻击有几率没法出手
                    else if (ThreadLocalRandom.current().nextBoolean()) {
                        continue;
                    }
                }
                // 如果角色中毒了
                if (fightObject.isZhongdu()) {
                    if (fightRequest.action == 3 && fightRequest.para == 501) {
                        continue;
                    }
                    if (fightRequest.action == 2 && fightRequest.para == 2) {
                        continue;
                    }
                }
                // 获取被攻击的怪物
                FightObject victimObject = getFightObject(fightContainer, fightRequest.vid);
                // 如果攻击对象已经死亡,则从敌方随机选一个目标
                if (victimObject != null && victimObject.isDead() && fightRequest.action != 4
                        && FightSkill.isOpSkill(fightRequest.para)) {
                    ArrayList<FightObject> fightObjects = new ArrayList<>();
                    // 获取敌方队伍，这里为怪物
                    FightTeam opponentsFightTeam = getFightTeamDM(fightContainer, fightObject.fid);
                    for (FightObject object : opponentsFightTeam.fightObjectList) {
                        if (object.canbeVictim()) {
                            fightObjects.add(object);
                        }
                    }
                    if (fightObjects.size() == 0) {
                        fightRequest.action = 1;
                    } else {
                        FightObject target = fightObjects.get(new Random().nextInt(fightObjects.size()));
                        if (target == null) {
                            fightRequest.action = 1;
                        } else {
                            fightRequest.vid = target.fid;
                        }
                    }
                }
                if (fightObject.isHunluan()) {
                    //只允许普通攻击
                    fightRequest.action = 2;
                    generateActionHunluan(fightContainer, fightObject, fightRequest);
                }
                // 如果怪不为空且不能被攻击且没有使用道具，则重新生成战斗请求,如果不是辅助技能
                else if (victimObject != null && !victimObject.canbeVictim() && fightRequest.action != 4
                        && FightSkill.isOpSkill(fightRequest.para)) {
                    if (FightSkill.isOpSkill(fightRequest.para)) {
                       // log.info("如果怪不为空且不能被攻击且没有使用道具，则重新生成战斗请求,如果不是辅助技能"+JSON.toJSON(fightRequest));
                        generateActionDM(fightContainer, fightObject, fightRequest);
                    } else {
                        generateActionVt(fightContainer, fightObject, fightRequest);
                    }
                }
                // 获取战斗的技能
                JiNeng jiNeng = null;
                // 如果是法术技能攻击
                if (fightRequest.action == 3) {
                    List<JiNeng> jiNengList = fightObject.skillsList;
                    for (JiNeng tjiNeng : jiNengList) {
                        if (tjiNeng.skill_no == fightRequest.para) {
                            jiNeng = tjiNeng;
                        }
                    }
                }
                if (fightObject.type == 2 || fightObject.type == 1) {
                    if (fightObject.isGuaiWuHide == 0 && fightRequest.para != 7 && fightRequest.para != 4
                            && fightRequest.para != 0) {
                        if (jiNeng != null) {
                            int skill_mana_cost = jiNeng.skill_mana_cost;
                            if (!costMofa(fightContainer, fightObject, skill_mana_cost)) {
                                //设置为null
                                fightRequest.skill_talk = null;
                                if (fightObject.autofight_supplement == 1) {
                                    // 设置为防御法力不足
                                    fightRequest.action = 1;
                                } else {
                                    // 设置为普通攻击
                                    fightRequest.action = 2;
                                    //重新设置喊话
                                    FightManager.setAutoTalkMsg(fightObject, fightRequest);
                                }
                            }
                        }
                    }
                }
                FightSkill skill = FightSkill.getFightSkill(fightRequest.action, fightRequest.para);
                if (skill == null) {
                    continue;
                }
                // 如果战斗请求为技能，但是却没有没有实际的技能则继续
                if (jiNeng == null && fightRequest.action == 3) {
                    continue;
                }
                // 这里是技能对目标的计算结果
                skill.doSkill(fightContainer, fightRequest, jiNeng);
                //记录
                FightRecord record = new FightRecord();
                record.setAction(fightRequest.action);
                record.setPara(fightRequest.para);
                record.setId(fightRequest.id);
                record.setName(fightObject.str);
                record.setVid(fightRequest.vid);
                FightObject opp = getFightObject(fightRequest.vid);
                if (opp != null) {
                    record.setVname(opp.str);
                }
                record.setIsDead(fightObject.isDead() ? "<font color='red'>死亡</font>" : "<font color='green'>正常</font>");
                record.setRoundNum(fightContainer.round);
                record.setStartTime(fightContainer.roundTime);
                record.setIsRevive(fightObject.isRevive);
                record.setIsTalk(fightObject.isTalk);
                record.setPid(fightContainer.round);
                fightRecords.add(record);
//				log.info("对象名字:{},对象ID：{},请求信息:{}",fightObject.str,fightObject.fid,JSONObject.toJSONString(fightRequest));
            } finally {
                fightObject.fightRequest = null;
            }
            boolean over = isOver(fightContainer);
          //  log.info(" boolean over："+ over);
            if (over) {
                doOver(fightContainer);
                FightRecord record = new FightRecord();
                record.setPid(-1);
                record.setName("------------------");
                record.setRoundNum(fightContainer.round);
                fightRecords.add(record);
                //战斗记录
                fightContainer.fightRecords.put(fightContainer.round, fightRecords);
                return;
            }
        }
        FightRecord record = new FightRecord();
        record.setPid(-1);
        record.setName("------------------");
        record.setRoundNum(fightContainer.round);
        fightRecords.add(record);
        //战斗记录
        fightContainer.fightRecords.put(fightContainer.round, fightRecords);
       // log.info("fightmanager.doAction   执行完毕，之后去执行什么了呢。。。");
    }

    // 这里是消耗战斗对象的蓝条
    public static boolean costMofa(FightContainer fightContainer, FightObject fightObject, int consumeMofa) {
        boolean flag = true;
        if (fightObject.mofa < consumeMofa) {
            // 如果开启了自动补充
            if (fightObject.autofight_supplement == 1) {
                // 判断背包是否有补充道具
                int cid = 0;
                if (fightObject.type == 1) {
                    cid = fightObject.fid;
                } else {
                    cid = fightObject.cid;
                }
                boolean isFind = false;
                GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(cid);
                if (gameObjectChar != null) {
                    for (Goods goods : gameObjectChar.chara.backpack) {
                        if (goods.goodsInfo.str.endsWith("法玲珑")) {
                            isFind = true;
                            int point = 0;
                            if (fightObject.guaiwulevel < 120) {
                                point += 26000 + (fightObject.max_mofa * 0.1);
                            } else {
                                point += 30000 + (fightObject.max_mofa * 0.2);
                            }
                            GoodsBasics goodsBasics = goods.goodsBasics;
                            GoodsInfo goodsInfo = goods.goodsInfo;
                            if (goodsBasics.max_mana < point) {
                                point = fightObject.max_mofa;
                            }
                            // 添加魔法
                            int mofa = fightObject.addMoFa(point);
                            // 如果当前的容量小于目标数,那就直接把point变成目标数
                            if (goodsBasics.max_mana <= point) {
                                // 删除这个道具
                                GameUtil.removemunber(gameObjectChar.chara, goods, 1);
                                GameCommonUtil.dialogOk(goods.goodsInfo.str + "已用尽", gameObjectChar.chara.id);
                            } else {
                                //当需要加的点数小于当前最大数，那就让点数变成需要加的点
                                if (point > mofa) {
                                    point = mofa;
                                }
                                goodsBasics.max_mana -= point;
                                goodsInfo.phy_rebuild_level = "剩余法力："
                                        + NumberFormat.getNumberInstance(Locale.CHINA).format(goodsBasics.max_mana);
                                // 刷新数据
                                gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(goods));
                            }
                            // 动画
                            Vo_11719_0 vo_11719_0 = new Vo_11719_0();
                            vo_11719_0.id = fightObject.fid;
                            vo_11719_0.owner_id = fightObject.fid;
                            vo_11719_0.no = 1003;
                            FightManager.send(fightContainer, new M11719_0(), vo_11719_0);
                            break;
                        }
                    }
                    flag = false;
                    if (!isFind) {
                        gameObjectChar.sendOne(new MSG_C_DIALOG_OK(), "法力不足，无法使用该技能");
                    }
                } else {
                    GameObjectChar game = GameObjectCharMng.getGameObjectChar(fightObject.fid);
                    if (game != null) {
                        game.sendOne(new MSG_C_DIALOG_OK(), "法力不足，无法使用该技能");
                    }
                    flag = false;
                }
            } else {
                // 魔法不足
//				GameObjectChar game = GameObjectCharMng.getGameObjectChar(fightObject.fid);
//				if (game != null) {
//					game.sendOne(new MSG_C_DIALOG_OK(), "法力不足，无法使用该技能");
//				}
                flag = false;
            }
        } else {
            //足够了才会扣
            fightObject.mofa -= consumeMofa;
        }

        if (fightObject.mofa < 0) {
            fightObject.mofa = 0;
        }
//		Vo_41027_0 vo_41027_0 = new Vo_41027_0();
//		vo_41027_0.id = fightObject.id;
//		vo_41027_0.mana = fightObject.mofa;
//		vo_41027_0.max_mana = fightObject.max_mofa;
//		send(fightContainer, new MSG_C_UPDATE_COMBAT_INFO(), vo_41027_0);

        ArrayList<Integer> objects2 = new ArrayList<Integer>();
        objects2.add(fightObject.fid);
        objects2.add(fightObject.mofa);
        FightManager.send(fightContainer, new M64981_Fight_Mana(), objects2);

        return flag;
    }

    public static void send_LIFE_DELTA(FightContainer fightContainer, FightResult fightResult) {
        Vo_C_LIFE_DELTA vo_15857_0 = new Vo_C_LIFE_DELTA();
        vo_15857_0.id = fightResult.vid;
        vo_15857_0.hitter_id = fightResult.id;
        vo_15857_0.point = fightResult.point;
        vo_15857_0.effect_no = fightResult.effect_no;
        vo_15857_0.damage_type = fightResult.damage_type;
        send(fightContainer, new MSG_C_LIFE_DELTA(), vo_15857_0);

        FightTeam friendsFightTeamDM = getFightTeam(fightContainer, fightResult.vid);
        if (friendsFightTeamDM != null && friendsFightTeamDM.type == 1) {
            FightObject updateBloodFightObject = getFightObject(fightContainer, fightResult.vid);
            updateBloodFightObject.update(fightContainer);
        }
        FightObject victimObject = getFightObject(fightContainer, fightResult.vid);
        if (victimObject != null) {
            //如果开了火眼金睛
            if (fightContainer.hyjjRound > 0) {
                List<FightObject> fightObjects = friendsFightTeamDM.fightObjectList;
                List<Vo_C_OPPONENT_INFO> showLifes = new ArrayList<>();
                for (FightObject fight : fightObjects) {
                    if (fight.fid == fightResult.vid) {
                        Vo_C_OPPONENT_INFO info = new Vo_C_OPPONENT_INFO(fight.fid);
                        info.getBuildFields().put("life", fight.shengming);
                        info.getBuildFields().put("max_life", fight.max_shengming);
                        showLifes.add(info);
                    }
                }
                if (!showLifes.isEmpty()) {
                    FightTeam fightTeam = FightManager.getFightTeam(fightContainer, fightContainer.hyjjUseCid);
                    List<FightObject> fightObjectList = new ArrayList<>();
                    if (fightTeam.fightObjectList != null) {
                        fightObjectList.addAll(fightTeam.fightObjectList);
                    }
                    FightManager.sendTeam(fightContainer, fightObjectList, new MSG_C_OPPONENT_INFO(), showLifes);
                }
            }
            if (victimObject.doDead()) {
                // 死亡动作
                Vo_C_CHAR_DIED vo_7669_0 = new Vo_C_CHAR_DIED();
                vo_7669_0.id = victimObject.fid;
                vo_7669_0.damage_type = 4098;
                send(fightContainer, new MSG_C_CHAR_DIED(), vo_7669_0);
                if (victimObject.state.get() == 3) {
                    if (victimObject.isGuaiWuHide == 0) {
                        // 死亡退出战斗
                        Vo_7653_0 vo_7653_0 = new Vo_7653_0();
                        vo_7653_0.id = victimObject.fid;
                        send(fightContainer, new M7653_0(), vo_7653_0);
                        //删除这个人所有状态
                        if (victimObject.hasBuffState(528128)) {
                            victimObject.removeBuffSK(fightContainer, 528128);
                        }
                        victimObject.getBuffState().clear();
                        victimObject.getRoundSkill().clear();
                    }
                    GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(victimObject.cid);
                    if (null != gameObjectChar && gameObjectChar.chara.chongwuluezhenId != 0) {
                        Petbeibao petbeibao = gameObjectChar.chara.getLueZhenPet();
                        if (null != petbeibao) {
                            lueZhen(fightContainer, victimObject, petbeibao);
                        }
                    }
                }
            }
        }
    }

    /**
     * 让宠物掠阵出来
     *
     * @param fc
     * @param victimObject
     */
    public static void lueZhen(FightContainer fc, FightObject victimObject, Petbeibao petbeibao) {
        // 为false的时候才允许掠阵出来
        if (victimObject.isLueZhen) {
            return;
        }
        if (victimObject.type == 2) {
            victimObject.isLueZhen = true;
            // 获取出战宠物信息
            GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(victimObject.cid);
            if (null != gameObjectChar && gameObjectChar.isOnline() && gameObjectChar.chara.chongwuluezhenId > 0) {
                FightObject fightObject = new FightObject(petbeibao, null);
                fightObject.isLueZhen = true;
                fightObject.pos = victimObject.pos;
                fightObject.fid = petbeibao.id;
                fightObject.id = petbeibao.id;
                fightObject.cid = gameObjectChar.chara.id;
                // 添加掠阵宠物天书
                if (petbeibao.tianshu.size() != 0) {
                    Vo_12023_0 vo_12023_0 = petbeibao.tianshu
                            .get(FightManager.RANDOM.nextInt(petbeibao.tianshu.size()));
                    fightObject.godbook = FightTianshuMap.TIANSHU_EFFECT.get(vo_12023_0.god_book_skill_name);
                    XiuluoshuSkill xiuluoshuSkill = new XiuluoshuSkill(vo_12023_0.god_book_skill_name);
                    xiuluoshuSkill.buffObject = fightObject;
                    xiuluoshuSkill.fightContainer = fc;
                    fightObject.addSkill(xiuluoshuSkill);
                }
                FightTeam friendsFightTeam = getFightTeam(fc, victimObject.fid);
                FightTeam enemyTeam = getFightTeamDM(fc, victimObject.fid);
                friendsFightTeam.fightObjectList.remove(victimObject);
                friendsFightTeam.fightObjectList.add(fightObject);
                //重新赋值
                fc.doActionList.remove(victimObject);
                // 新的队伍信息
                List<Vo_ADD_FRIEND_OPPONENT> newFightTeamInfo = builderFightObject(fc,
                        Lists.newArrayList(fightObject));
                // 添加队友
                sendTeam(fc, friendsFightTeam.fightObjectList, new MSG_C_ADD_FRIEND(), newFightTeamInfo);
                // 重新设置参战宠物
                gameObjectChar.sendOne(new MSG_C_SET_FIGHT_PET(), new Vo_64971_0(victimObject.id, 0));
                gameObjectChar.sendOne(new MSG_C_SET_FIGHT_PET(), new Vo_64971_0(fightObject.id, 1));
                sendTeam(fc, enemyTeam.fightObjectList, new MSG_C_ADD_OPPONENT(), newFightTeamInfo);
                //通知观战人员
                if (fc.lookCharas != null && !fc.lookCharas.isEmpty()) {
                    Iterator<Entry<Integer, GameObjectChar>> iterator = fc.lookCharas.entrySet().iterator();
                    while (iterator.hasNext()) {
                        GameObjectChar value = iterator.next().getValue();
                        if (value != null) {
                            boolean isFriend = false;
                            for (FightObject figObject : friendsFightTeam.fightObjectList) {
                                if (figObject.fid == value.lookCharId) {
                                    isFriend = true;
                                    break;
                                }
                            }
                            if (isFriend) {
                                value.sendOne(new MSG_C_ADD_FRIEND(), newFightTeamInfo);
                            } else {
                                value.sendOne(new MSG_C_ADD_OPPONENT(), newFightTeamInfo);
                            }
                        }
                    }
                }
                // 设置天书
                int addFightTianShuType = fightObject.getRandomTianshuType(fc);
                Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
                vo_12025_0.id = fightObject.id;
                vo_12025_0.effect_no = addFightTianShuType;
                send(fc, new MSG_GODBOOK_EFFECT_SUMMON(), vo_12025_0);
                // 刷新当前参战宠物的信息
                gameObjectChar.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(petbeibao));

                Vo_64971_0 vo_64971_2 = new Vo_64971_0();
                vo_64971_2.count = 1;
                vo_64971_2.id = fightObject.fid;
                vo_64971_2.haveCalled = 1;
                gameObjectChar.sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_2);
            }
        }
    }

    // 通知对手
    public static void notifyTeamEnemyList(FightContainer fightContainer, List<FightObject> notifyObjectList,
                                           List<FightObject> fightObjectList) {
        Iterator<FightObject> var43 = fightObjectList.iterator();
        List<Vo_ADD_FRIEND_OPPONENT> list65017 = new ArrayList<>();
        while (var43.hasNext()) {
            FightObject object1 = (FightObject) var43.next();
            // 如果是宠物的话.
            if (object1.type == 2) {
                // 只让存活的宠物出战
                if (object1.state.get() == 1) {
                    list65017.add(GameUtil.vo_65017_0(object1));
                }
            } else {
                list65017.add(GameUtil.vo_65017_0(object1));
            }
        }
        sendTeam(fightContainer, notifyObjectList, new MSG_C_OPPONENTS(), list65017);
    }

    /**
     * 构建战斗对象信息
     *
     * @param fightContainer  战斗容器
     * @param fightObjectList 战斗对象
     */
    public static List<Vo_ADD_FRIEND_OPPONENT> builderFightObject(FightContainer fightContainer,
                                                                  List<FightObject> fightObjectList) {
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        for (FightObject fightObject4 : fightObjectList) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject4.id;
            vo_65019_0.leader = fightObject4.leader;
            vo_65019_0.weapon_icon = fightObject4.weapon_icon;
            vo_65019_0.pos = fightObject4.pos;
            vo_65019_0.rank = fightObject4.rank;
            vo_65019_0.vip_type = fightObject4.vipType;
            vo_65019_0.str = fightObject4.str;
            vo_65019_0.type = fightObject4.org_icon;
            vo_65019_0.durability = fightObject4.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject4.upgrade_level;
            vo_65019_0.upgrade_type = fightObject4.upgrade_type;
            vo_65019_0.dex = fightObject4.max_mofa;
            vo_65019_0.max_mana = fightObject4.max_mofa;
            vo_65019_0.max_life = fightObject4.max_shengming;
            vo_65019_0.def = fightObject4.max_shengming;
            vo_65019_0.org_icon = fightObject4.org_icon;
            vo_65019_0.suit_icon = fightObject4.suit_icon;
            vo_65019_0.suit_light_effect = fightObject4.suit_light_effect;
            vo_65019_0.special_icon = fightObject4.special_icon;
            vo_65019_0.portrait = fightObject4.org_icon;
            vo_65019_0.zhenlingLevel = fightObject4.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject4.zhenlingType;
            // 如果是宠物的话.
            if (fightObject4.type == 2) {
                // 只让存活的宠物出战
                if (fightObject4.state.get() == 1) {
                    list65019.add(vo_65019_0);
                }
            } else {
                list65019.add(vo_65019_0);
            }
        }
        return list65019;
    }

    // 通知队友
    public static void notifyTeamFriendList(FightContainer fightContainer, List<FightObject> fightObjectList) {
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        for (FightObject fightObject4 : fightObjectList) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject4.id;
            vo_65019_0.leader = fightObject4.leader;
            vo_65019_0.weapon_icon = fightObject4.weapon_icon;
            vo_65019_0.pos = fightObject4.pos;
            vo_65019_0.rank = fightObject4.rank;
            vo_65019_0.vip_type = fightObject4.vipType;
            vo_65019_0.str = fightObject4.str;
            vo_65019_0.type = fightObject4.org_icon;
            vo_65019_0.durability = fightObject4.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject4.upgrade_level;
            vo_65019_0.upgrade_type = fightObject4.upgrade_type;
            vo_65019_0.dex = fightObject4.max_mofa;
            vo_65019_0.max_mana = fightObject4.max_mofa;
            vo_65019_0.max_life = fightObject4.max_shengming;
            vo_65019_0.def = fightObject4.max_shengming;
            vo_65019_0.org_icon = fightObject4.org_icon;
            vo_65019_0.suit_icon = fightObject4.suit_icon;
            vo_65019_0.suit_light_effect = fightObject4.suit_light_effect;
            vo_65019_0.special_icon = fightObject4.special_icon;
            vo_65019_0.portrait = fightObject4.org_icon;
            vo_65019_0.zhenlingLevel = fightObject4.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject4.zhenlingType;
            // 如果是宠物的话.
            if (fightObject4.type == 2) {
                // 只让存活的宠物出战
                if (fightObject4.state.get() == 1) {
                    list65019.add(vo_65019_0);
                }
            } else {
                list65019.add(vo_65019_0);
            }
        }
        sendTeam(fightContainer, fightObjectList, new MSG_C_FRIENDS(), list65019);
    }

    public static void doOver(FightContainer fightContainer) {
        fightContainer.state.set(4);
    }

    // 改变自动战斗的技能
    public static void changeAutoFightSkill(FightContainer fightContainer, FightObject fightObject, int action,
                                            int para) {
        if (action == 1 || action == 2 || action == 3) {
            fightObject.autofight_skillaction = action;
            fightObject.autofight_skillno = para;
            if (fightObject.type == 1) {
                GameObjectChar session = GameObjectCharMng.getGameObjectChar(fightObject.id);
                session.chara.autofight_skillaction = action;
                session.chara.autofight_skillno = para;
            } else if (fightObject.type == 2) {
                GameObjectChar session = GameObjectCharMng.getGameObjectChar(fightObject.cid);
                List<Petbeibao> pets = session.chara.pets;
                for (Petbeibao pet : pets) {
                    if (pet.id == fightObject.fid) {
                        pet.autofight_skillaction = action;
                        pet.autofight_skillno = para;
                        break;
                    }
                }
            }
        }
    }

    public static void nextRoundOrSendOver(FightContainer fightContainer) {
        if (fightContainer.state.compareAndSet(4, 5)) {
            FightManager.listFight.remove(fightContainer);
            sendOver(fightContainer, false);
        } else if (fightContainer.state.get() == 1) {
            ++fightContainer.round;
            nextRound(fightContainer);
        }
    }

    // 进行下一回合战斗或者发送战斗结束
    public static void nextRoundOrSendOver(FightContainer fightContainer, GameObjectChar gameObjectChar) {
      //  log.info("进行下一回合战斗或者发送战斗结束");
        //
        GameData.that.redisUtils.delete("fightTime_" + fightContainer.uid);
        GameData.that.redisUtils.delete("fightCallFail_" + fightContainer.uid);
        if (fightContainer.state.compareAndSet(4, 5)) {
            FightManager.listFight.remove(fightContainer);
            sendOver(fightContainer, false);
        } else if (fightContainer.state.get() == 1) {
            fightContainer.round += 1;
            if (gameObjectChar != null) {
                if (gameObjectChar.chara.mapName.equals("试道场")
                        && fightContainer.round > GameConfig.config.getShidao().getMaxRound()
                        && GameShiDao.statzhuangtai == 3) {
                    FightManager.listFight.remove(fightContainer);
                    sendOver(fightContainer, true);
                    return;
                }
            }
            nextRound(fightContainer);
        }
    }

    // 在战斗结束之后，移除buff效果并重置法宝次数
    public static void endaction(FightContainer fightContainer) {
        List<FightObject> allFightObject = getAllFightObject(fightContainer);
        for (FightObject fightObject : allFightObject) {
            List<FightRoundSkill> fightSkillList = fightObject.getRoundSkill();
            for (FightRoundSkill fightSkill : fightSkillList) {
                boolean remove = fightSkill.disappear(fightContainer);
                if (remove) {
                    fightObject.removeBuffSK(fightContainer, fightSkill.getStateType());
                }
            }
            FightFabaoSkill fabaoSkill = fightObject.getFabaoSkill();
            if (fabaoSkill != null) {
                fabaoSkill.resetTimes();
            }
        }
    }

    public static boolean fabao(FightContainer fightContainer) throws FightException {
        List<FightObject> allFightObject = getAllFightObject(fightContainer);
        for (FightObject fightObject : allFightObject) {
            // 获取参战对象的回合技能
            List<FightRoundSkill> fightSkillList = fightObject.getRoundSkill();
            for (FightRoundSkill fightSkill : fightSkillList) {
                fightSkill.doRoundSkill();
            }
            FightFabaoSkill fabaoSkill = fightObject.getFabaoSkill();
            if (fabaoSkill != null) {
                fabaoSkill.active();
            }
        }
		if (isOver(fightContainer)) {
		    log.info("战斗结束战斗ID是："+fightContainer.id);
			doOver(fightContainer);
			nextRoundOrSendOver(fightContainer, null);
			return true;
		}
        return false;
    }

    public static void send(FightContainer fightContainer, @SuppressWarnings("rawtypes") BaseWrite baseWrite,
                            Object obj) {
        if (fightContainer != null && obj != null) {
            List<FightObject> allFightObject = getAllFightObject(fightContainer);
            for (FightObject fightObject : allFightObject) {
                // 如果对象是角色
                if (fightObject.type == 1) {
                    GameObjectChar.send(baseWrite, obj, fightObject.id);
                }
            }
            // 通知观战人员
            if (fightContainer.lookCharas != null && !fightContainer.lookCharas.isEmpty()) {
                Iterator<Entry<Integer, GameObjectChar>> iterator = fightContainer.lookCharas.entrySet().iterator();
                while (iterator.hasNext()) {
                    GameObjectChar value = iterator.next().getValue();
                    if (value != null) {
                        if (value.chara != null && value.chara.isFight) {
                            // 这人在战斗有可能是异常导致没有移除，这里手动清理
                            iterator.remove();
                            continue;
                        }
                        value.sendOne(baseWrite, obj);
                    }
                }
            }
        }
    }

    public static void sendTeam(FightContainer fightContainer, List<FightObject> fightObjectList,
                                @SuppressWarnings("rawtypes") BaseWrite baseWrite, Object obj) {
        if (fightObjectList != null) {
            for (FightObject fightObject : fightObjectList) {
                if (fightObject.type == 1) {
                    GameObjectChar.send(baseWrite, obj, fightObject.fid);
                }
            }
        }
    }

    public static void sendTeam(FightContainer fightContainer, int id,
                                @SuppressWarnings("rawtypes") BaseWrite baseWrite, Object obj) {
        FightTeam fightTeam = getFightTeam(fightContainer, id);
        if (fightTeam != null && fightTeam.fightObjectList != null) {
            for (FightObject fightObject : fightTeam.fightObjectList) {
                if (fightObject.type == 1) {
                    GameObjectChar.send(baseWrite, obj, fightObject.fid);
                }
            }
        }
    }

    /**
     * 结束某个人的战斗.
     *
     * @param fightContainer
     */
    public static void sendOverById(int id, boolean isForaceOver) {
        FightContainer fightContainer = getFightContainer(id);
        if (fightContainer == null) {
            return;
        }
        sendOver(fightContainer, Lists.newArrayList(getFightObject(id)), isForaceOver);
    }

    /**
     * 结束战斗,是否强制结束
     *
     * @param fightContainer 战斗容器
     * @param isForaceOver   是否强制结束
     */
    public static void sendOver(FightContainer fightContainer, boolean isForaceOver) {
        List<FightObject> allFightObject = getAllFightObject(fightContainer);
        for (FightObject fightObject : allFightObject) {
            if (fightObject.type == 1) {
                ZbAttribute zbAttribute = new ZbAttribute();
                zbAttribute.id = fightObject.id;
                zbAttribute.accurate = 0;
                zbAttribute.mana = 0;
                zbAttribute.wiz = 0;
                zbAttribute.parry = 0;
                FightManager.send(fightContainer, new M64991_0(), zbAttribute);


            }
        }
        sendOver(fightContainer, allFightObject, isForaceOver);
    }

    /**
     * 结束战斗,是否强制结束
     *
     * @param fightContainer 战斗容器
     * @param fightObjects   战斗对象
     * @param isForaceOver   是否强制结束
     */
    private static void sendOver(FightContainer fightContainer, List<FightObject> fightObjects, boolean isForaceOver) {
     //   log.error("战斗结束，是否正常结束:{}", isForaceOver);
        for (FightObject fightObject : fightObjects) {
            if (fightObject.type == 1) {
                GameObjectChar session = GameObjectCharMng.getGameObjectChar(fightObject.id);
                // 这里有可能出现空指针
                if (session == null) {
                    continue;
                }
                // 刷新
                GameUtil.sendUpdate(session.chara);

                Vo_C_END_COMBAT vo_3581_0 = new Vo_C_END_COMBAT();
                vo_3581_0.a = 1;
                session.sendOne(new MSG_C_END_COMBAT(), vo_3581_0);

                fightObject.shengming = fightObject.shengming;
                // 设置宠物参战ID
                Vo_4163_0 vo_4163_0 = new Vo_4163_0();
                vo_4163_0.id = session.chara.chongwuchanzhanId;
                vo_4163_0.b = 1;
                session.sendOne(new M4163_0(), vo_4163_0);
                // 设置宠物掠阵
                if (session.chara.chongwuluezhenId != 0) {
                    vo_4163_0 = new Vo_4163_0();
                    vo_4163_0.id = session.chara.getChongwuluezhenId();
                    vo_4163_0.b = 2;
                    session.sendOne(new M4163_0(), vo_4163_0);
                }
                Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
                vo_19959_0.round = fightContainer.round;
                vo_19959_0.aid = fightObject.fid;
                vo_19959_0.action = 99;
                vo_19959_0.vid = fightObject.fid;
                vo_19959_0.para = 0;
                session.sendOne(new MSG_C_ACTION(), vo_19959_0);

                Vo_C_END_ACTION vo_7655_0 = new Vo_C_END_ACTION();
                vo_7655_0.id = fightObject.fid;
                session.sendOne(new MSG_C_END_ACTION(), vo_7655_0);

                fightObject.updateState(fightContainer, 0, 0);
            } else if (fightObject.type == 2) {
                if (fightObject.type != 2) {
                    continue;
                }
                GameObjectChar session = GameObjectCharMng.getGameObjectChar(fightObject.cid);
                // 刷新宠物
                if (session != null && session.chara != null && session.chara.pets != null
                        && !session.chara.pets.isEmpty()) {
                    for (Petbeibao pet : session.chara.pets) {
                        if (pet.id == session.chara.chongwuchanzhanId) {
                            session.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
                            break;
                        }
                    }
                }
            }
        }
        try {
            // 非强制结束才给奖励
            afterFight(fightContainer, isForaceOver);
        } catch (Exception e) {
            // 战斗报错.
            log.error("战斗报错:{}", e);
        } finally {
            for (FightObject fightObject : fightObjects) {
                if (fightObject.type == 1) {
                    GameObjectChar session = GameObjectCharMng.getGameObjectChar(fightObject.id);
                    // 这里有可能出现空指针
                    if (session == null) {
                        continue;
                    }
                    // 此处判断是否为空
                    session.chara.isFight = false;
                    // 删除正在战斗的boss
                    GameCore.fightObject.remove(session.chara.zhandouId);
                    // 清除战斗信息
                    String zhandouInfo = session.chara.zhandouInfo;
                    if (zhandouInfo != null) {
                        GameConfig.canzhanBoos.remove(zhandouInfo);
                    }
                    session.chara.zhandouInfo = null;
                    session.chara.zhandouId = 0;
                    session.action = "";
                    session.flag = "";
                    // 设置战斗标识
                    GameCommonUtil.setCharaTitleFlag(session.chara);
                }
            }
            // 观战人员退出
            for (Map.Entry<Integer, GameObjectChar> map : fightContainer.lookCharas.entrySet()) {
                GameObjectChar look = map.getValue();
                look.isLook = 0;
                look.lookCharId = 0;
                look.sendOne(new MSG_LC_END_LOOKON(), new Vo_C_END_COMBAT(1));
                // 更新状态
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("auto_fight", look.chara.autofight_select);
                look.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(look.chara.id, dataMap));
                GameCommonUtil.setCharaTitleFlag(look.chara);
            }
            //战斗记录输出到日志
//			log.error("战斗记录::::::版本号={},参战A={},参战B={},动作={}",GameCommonUtil.gameVersion,JSONObject.toJSONString(fightContainer.fightCharasA),JSONObject.toJSONString(fightContainer.fightCharasB),JSONObject.toJSONString(fightContainer.fightRecords));
            //删除观战信息
            fightContainer.lookCharas.clear();
            fightContainer = null;
        }
    }

    // 判断战斗是否结束
    public static boolean isOver(FightContainer fightContainer) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            List<FightObject> fightObjectList = fightTeam.fightObjectList;
            boolean over = true;
            int humanNum = 0; // 友方成员个数
            int runNum = 0;
            // 逃跑对象
            for (FightObject fightObject : fightObjectList) {

                // 如果角色没有死亡，而且没有逃跑，就没有结束
                if (!fightObject.isDead() && !fightObject.isRun()) {
                    over = false;
                }
                if (fightObject.type == 1) {
                    ++humanNum;
                    // 如果没逃跑则继续
                    if (!fightObject.isRun()) {
                        continue;
                    }
                    // 否则逃跑了就增加逃跑次数
                    ++runNum;
                }
            }

            // 如果存活一人且逃跑了，那么战斗结束
            if (humanNum == runNum && humanNum > 0) {
                log.info("战斗ID: "+fightContainer.id+"第 "+fightContainer.round+" 战斗结束");
                return true;
            }
            if (over) {
                log.info("战斗ID: "+fightContainer.id+"第 "+fightContainer.round+" 战斗结束");
                return true;
            }
        }
        log.info("战斗ID: "+fightContainer.id+"第 "+fightContainer.round+" 战斗没有结束");
        return false;
    }

    /**
     * 下一个
     *
     * @param fightContainer
     */
    public static void nextRound(FightContainer fightContainer) {
       // log.info("下一个回合：");
        fightContainer.endTime.set(System.currentTimeMillis());
        GameData.that.redisUtils.delete("fightTime_" + fightContainer.uid);
        GameData.that.redisUtils.delete("fightCallFail_" + fightContainer.uid);
        List<FightObject> allFightObject = getAllFightObject(fightContainer);
        //如果有火眼金睛
        if (fightContainer.hyjjRound > 0) {
            fightContainer.hyjjRound -= 1;
            if (fightContainer.hyjjRound <= 0) {
                //清楚火眼金睛的效果
                FightTeam fightTeamDM = FightManager.getFightTeamDM(fightContainer, fightContainer.hyjjUseCid);
                FightTeam fightTeam = FightManager.getFightTeam(fightContainer, fightContainer.hyjjUseCid);
                List<FightObject> fightObjects = fightTeamDM.fightObjectList;
                List<Vo_ADD_FRIEND_OPPONENT> opponents = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
                for (FightObject fight : fightObjects) {
                    if (!fight.isDead()) {
                        for (FightObject fightObject : fightTeam.fightObjectList) {
                            if (fightObject.type == 1) {
                                //先让他消失
                                GameObjectChar.send(new MSG_C_LEAVE_AT_ONCE(), fight.fid, fightObject.fid);
                            }
                        }
                        Vo_ADD_FRIEND_OPPONENT vo_65017_0 = GameUtil.vo_65017_0(fight);
                        opponents.add(vo_65017_0);
                    }
                }
                for (FightObject fightObject : fightTeam.fightObjectList) {
                    if (fightObject.type == 1) {
                        GameObjectChar.send(new MSG_C_OPPONENTS(), opponents, fightObject.fid);
                    }
                }
            }
        }
        long time = System.currentTimeMillis();
        for (FightObject fightObject : allFightObject) {
            fightObject.isSos = false;
            fightObject.isTalk = false;
            fightObject.isRevive = false;
            fightObject.fightRequest = null;
            // 如果是角色
            if (fightObject.type == 1) {
                GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightObject.id);
                if (gameObjectChar != null) {
                    gameObjectChar.isEndRound.set(false);
                    Vo_45141_0 vo_45141_0 = new Vo_45141_0();
                    vo_45141_0.round = fightContainer.round;
                    vo_45141_0.animate_done = 1;
                    gameObjectChar.sendOne(new M45141_0(), vo_45141_0);

                    Vo_C_WAIT_COMMAND vo_7659_0 = new Vo_C_WAIT_COMMAND();
                    vo_7659_0.menu = 0;
                    vo_7659_0.id = fightObject.fid;
                    vo_7659_0.time = 25;
                    vo_7659_0.question = 0;
                    vo_7659_0.round = fightContainer.round;
                    vo_7659_0.curTime = (int) (time / 1000L);
                    gameObjectChar.sendOne(new MSG_C_WAIT_COMMAND(), vo_7659_0);
                }
            }
        }
        // 设置当前回合的回合时间
        fightContainer.roundTime = time;

        // 通知观战人员
        if (fightContainer.lookCharas != null && !fightContainer.lookCharas.isEmpty()) {
            Iterator<Entry<Integer, GameObjectChar>> iterator = fightContainer.lookCharas.entrySet().iterator();
            while (iterator.hasNext()) {
                GameObjectChar value = iterator.next().getValue();
                if (value != null) {
                    if (value.chara != null && value.chara.isFight) {
                        // 这人在战斗有可能是异常导致没有移除，这里手动清理
                        iterator.remove();
                        continue;
                    }
                    Vo_C_WAIT_COMMAND vo_7659_0 = new Vo_C_WAIT_COMMAND();
                    vo_7659_0.menu = 0;
                    vo_7659_0.id = value.chara.id;
                    vo_7659_0.time = 25;
                    vo_7659_0.question = 0;
                    vo_7659_0.round = fightContainer.round;
                    vo_7659_0.curTime = (int) (time / 1000L);
                    value.sendOne(new MSG_C_WAIT_COMMAND(), vo_7659_0);
                }
            }
        }
    }


    // 这里没有实质性的作用，只是将战斗容器的回合等信息发送到前端，这里没有具体的战斗逻辑
    private static void round(FightContainer fightContainer) {
        fightContainer.endTime.set(System.currentTimeMillis());
        List<FightObject> allFightObject = getAllFightObject(fightContainer);
        for (FightObject fightObject : allFightObject) {
            // 如果是角色
            if (fightObject.type == 1) {
                GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightObject.id);
                if (gameObjectChar != null) {
                    Vo_45141_0 vo_45141_0 = new Vo_45141_0();
                    vo_45141_0.round = fightContainer.round;
                    vo_45141_0.animate_done = 1;
                    gameObjectChar.sendOne(new M45141_0(), vo_45141_0);
                    Vo_C_WAIT_COMMAND vo_7659_0 = new Vo_C_WAIT_COMMAND();
                    vo_7659_0.menu = 0;
                    vo_7659_0.id = fightObject.fid;
                    vo_7659_0.time = 25;
                    vo_7659_0.question = 0;
                    vo_7659_0.round = fightContainer.round;
                    vo_7659_0.curTime = (int) (System.currentTimeMillis() / 1000L);
                    gameObjectChar.sendOne(new MSG_C_WAIT_COMMAND(), vo_7659_0);
                    if ("newCombatFightS4".equals(gameObjectChar.flag) && fightContainer.round == 2) {
                        // 新手战斗兔子第二个回合.
                        gameObjectChar.sendOne(new MSG_PLAY_INSTRUCTION(), 51);
                    }
                }
            }
        }

        fightContainer.startTime = System.currentTimeMillis();
        // 设置当前回合的回合时间
        fightContainer.roundTime = System.currentTimeMillis();
        //记录战斗人员信息
        List<FightTeam> teamList = fightContainer.teamList;
        FightTeam fightTeamA = teamList.get(0);
        for (FightObject a : fightTeamA.fightObjectList) {
            Map<String, Object> info = new HashMap<>();
            info.put("name", a.str);
            info.put("id", a.fid);
            info.put("type", a.type);
            fightContainer.fightCharasA.add(info);
        }
        FightTeam fightTeamB = teamList.get(1);
        for (FightObject b : fightTeamB.fightObjectList) {
            Map<String, Object> info = new HashMap<>();
            info.put("name", b.str);
            info.put("id", b.fid);
            info.put("type", b.type);
            fightContainer.fightCharasB.add(info);
        }
    }


    // 这是检查是否所有的友方成员的战斗请求都已初始化，如果都初始化了，则返回true，反之false
    public static boolean isAllRequested(FightContainer fightContainer) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            if (fightTeam.type == 2) {
                continue;
            }
            List<FightObject> fightObjectList = fightTeam.fightObjectList;
            for (FightObject fightObject : fightObjectList) {
                // 如果已经死亡了那就没必要判断是否初始化了,必须是宠物
                if (fightObject.fightRequest == null) {
                    if (fightObject.type == 2 && !fightObject.isDead()) {
                        return false;
                    }
                    if (fightObject.type == 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static FightContainer getFightContainer() {
        int id = GameObjectChar.getGameObjectChar().chara.id;
        for (FightContainer fightContainer : FightManager.listFight) {
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.fid == id) {
                        return fightContainer;
                    }
                }
            }
        }
        return null;
    }

    // 通过战斗对象返回它所在的战斗容器
    public static FightContainer getFightContainer(int id) {
        for (FightContainer fightContainer : FightManager.listFight) {
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.fid == id) {
                        return fightContainer;
                    }
                }
            }
        }
        return null;
    }

    public static FightContainer getFightContainer(String name) {
        for (FightContainer fightContainer : FightManager.listFight) {
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.str.equals(name)) {
                        return fightContainer;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据uid获取战斗容器
     *
     * @param uid
     * @return
     */
    public static FightContainer getFightContainerByUid(String uid) {
        for (FightContainer fightContainer : FightManager.listFight) {
            if (fightContainer.uid.equals(uid)) {
                return fightContainer;
            }
        }
        return null;
    }

    // 根据传进来的id，获取它所在的队伍
    public static FightTeam getFightTeam(FightContainer fightContainer, int id) {
        if (fightContainer != null) {
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.fid == id) {
                        return fightTeam;
                    }
                }
            }
        }
        return null;
    }

    // 获取敌方队伍
    public static FightTeam getFightTeamDM(FightContainer fightContainer, int id) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            List<FightObject> fightObjectList = fightTeam.fightObjectList;
            for (FightObject fightObject : fightObjectList) {
                if (fightObject.fid == id) {
                    return (teamList.get(0) == fightTeam) ? teamList.get(1) : teamList.get(0);
                }
            }
        }
        return null;
    }

    public static List<FightObject> getAllFightObject(FightContainer fightContainer) {
        List<FightObject> list = new ArrayList<FightObject>();
        for (FightTeam fightTeam : fightContainer.teamList) {
            list.addAll(fightTeam.fightObjectList);
        }
        return list;
    }

    public static FightObject getFightObject(int id) {
        for (FightContainer fightContainer : FightManager.listFight) {
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.fid == id) {
                        return fightObject;
                    }
                }
            }
        }
        return null;
    }

    public static FightObject getFightObject(String name) {
        for (FightContainer fightContainer : FightManager.listFight) {
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.str.equals(name)) {
                        return fightObject;
                    }
                }
            }
        }
        return null;
    }

    public static FightObject getFightObject(FightContainer fightContainer, int id) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            List<FightObject> fightObjectList = fightTeam.fightObjectList;
            for (FightObject fightObject : fightObjectList) {
                if (fightObject.fid == id) {
                    return fightObject;
                }
            }
        }
        return null;
    }

    public static FightObject getFightObject(FightContainer fightContainer, FightRequest fightRequest, int id) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            List<FightObject> fightObjectList = fightTeam.fightObjectList;
            for (FightObject fightObject : fightObjectList) {
                if (fightObject.fid == id) {
                    return fightObject;
                }
            }
        }
        return null;
    }

    public static FightObject getFightObjectPet(FightContainer fightContainer, FightObject fightObject) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            List<FightObject> fightObjectList = fightTeam.fightObjectList;
            for (FightObject tfightObject : fightObjectList) {
                if (tfightObject.cid == fightObject.id) {
                    return tfightObject;
                }
            }
        }
        return null;
    }

    public static List<FightObject> getAlive(List<FightObject> list) {
        List<FightObject> rlist = new ArrayList<FightObject>();
        for (FightObject fightObject : list) {
            // 判断战斗对象存活状态
            if (!fightObject.isDead()) {
                rlist.add(fightObject);
            }
        }
        return rlist;
    }

    //获取已经死亡的人
    public static List<FightObject> getDeads(List<FightObject> list) {
        List<FightObject> rlist = new ArrayList<FightObject>();
        for (FightObject fightObject : list) {
            if (fightObject.isDead()) {
                rlist.add(fightObject);
            }
        }
        return rlist;
    }

    /**
     * 获取活着或者死亡的人
     *
     * @param list  对象
     * @param state 状态 0:死亡 1:活着 其他:死亡和活着
     * @return
     */
    public static List<FightObject> getAlive(List<FightObject> list, int state) {
        List<FightObject> rlist = new ArrayList<FightObject>();
        for (FightObject fightObject : list) {
            // 判断战斗对象存活状态
            if (state == 0) {
                if (fightObject.isDead()) {
                    rlist.add(fightObject);
                }
            } else if (state == 1) {
                if (!fightObject.isDead()) {
                    rlist.add(fightObject);
                }
            } else {
                rlist.add(fightObject);
            }
        }
        return rlist;
    }

    /**
     * 获取死亡和未死亡的人
     *
     * @param list
     * @return
     */
    public static List<FightObject> getAliveAndDead(List<FightObject> list) {
        List<FightObject> rlist = new ArrayList<FightObject>();
        for (FightObject fightObject : list) {
            //如果type为1不管死活都要加进去
            if (fightObject.type == 1) {
                rlist.add(fightObject);
            }
            //如果是宠物的话
            if (fightObject.type == 2 || (fightObject.type == 4 && fightObject.isGuaiWuHide != 1)) {
                //判断是否死亡
                if (!fightObject.isDead()) {
                    rlist.add(fightObject);
                }
            }
            //如果是不消失的怪物也得加进去
            if (fightObject.isGuaiWuHide == 1) {
                rlist.add(fightObject);
            }
        }

        return rlist;
    }

    /**
     * 随机寻找攻击目标
     *
     * @param fightContainer 战斗容器
     * @param fightRequest   请求
     * @param type           类型
     * @param num            攻击数量
     * @return
     */
    public static List<FightObject> findTarget(FightContainer fightContainer, FightRequest fightRequest, int type,
                                               int num) {
        List<FightObject> fightObjects = new ArrayList<>();
        // 所有障碍技能
        if (type == 1) {
            // 判断技能的攻击范围，如果攻击对象是一个人
            if (num == 1) {
                // 获取攻击对象
                FightObject fightObject = getFightObject(fightContainer, fightRequest.vid);
                if (null == fightObject || fightObject.isDead()) {
                    FightTeam opponentsFightTeam = getFightTeamDM(fightContainer, fightRequest.id);
                    List<FightObject> alive = getAlive(opponentsFightTeam.fightObjectList);
                    if (alive.size() == 0) {
                        fightObjects.add(null);
                    } else { // 如果对面还有存活的就随机加入一个进去
                        FightObject newTarget = alive.get(FightManager.RANDOM.nextInt(alive.size()));
                        fightObjects.add(newTarget);
                    }
                } else {
                    fightObjects.add(fightObject);
                }
            }
            // 如果攻击对象为多个人
            else {
                FightTeam opponentsFightTeam2 = getFightTeamDM(fightContainer, fightRequest.id);
                List<FightObject> alive2 = getAlive(opponentsFightTeam2.fightObjectList);
                Iterator<FightObject> iterator = alive2.iterator();
                // 这里只能选择一个攻击对象，因为next.fid等于fightRequest.vid只会满足一次
                while (iterator.hasNext()) {
                    FightObject next = iterator.next();
                    if (next.fid == fightRequest.vid) {
                        --num;
                        fightObjects.add(next);
                        iterator.remove();
                    }
                }
                // 如果还有存活的对象，而且当前攻击的对象数小于技能的最大攻击数，则继续在存活的对象中选择
                for (int i = 0; i < num && alive2.size() != 0; ++i) {
                    FightObject newTarget = alive2.remove(FightManager.RANDOM.nextInt(alive2.size()));
                    fightObjects.add(newTarget);
                }
            }
        } else if (type == 2) {
            FightTeam friendsFightTeam = getFightTeam(fightContainer, fightRequest.id);
            List<FightObject> alive2 = getAlive(friendsFightTeam.fightObjectList);
            Iterator<FightObject> iterator = alive2.iterator();
            while (iterator.hasNext()) {
                FightObject next = iterator.next();
                if (next.fid == fightRequest.vid) {
                    --num;
                    fightObjects.add(next);
                    iterator.remove();
                }
            }
            for (int i = 0; i < num && alive2.size() != 0; ++i) {
                FightObject newTarget = alive2.remove(FightManager.RANDOM.nextInt(alive2.size()));
                fightObjects.add(newTarget);
            }
        } else if (type == 3) {
            FightTeam friendsFightTeam = getFightTeam(fightContainer, fightRequest.id);
            List<FightObject> alive2 = getAlive(friendsFightTeam.fightObjectList);
            for (int i = 0; i < num && alive2.size() != 0; ++i) {
                FightObject newTarget = alive2.remove(FightManager.RANDOM.nextInt(alive2.size()));
                fightObjects.add(newTarget);
            }
        } else if (type == 20224 || type == 528128 || type == 265984 || type == 12032 || type == 134912) {// 所有的辅助技能目标
            FightTeam friendsFightTeam = getFightTeam(fightContainer, fightRequest.id);
            // 活着的人
            List<FightObject> alive = null;
            if (type == 528128) {
                alive = getAliveAndDead(friendsFightTeam.fightObjectList);
            } else {
                alive = getAlive(friendsFightTeam.fightObjectList);
            }
            if (type == 20224) {
                // 按伤害计算排序，如果伤害一样的话就按按照速度排序\金
                alive = alive.stream()
                        .sorted(Comparator.comparing(FightObject::getAccurate).reversed()
                                .thenComparing(Comparator.comparing(FightObject::getParry).reversed()))
                        .collect(Collectors.toList());
            } else if (type == 528128) {
                // 按生命值最低的人来添加、木
                alive = alive.stream()
                        .sorted(Comparator.comparing(FightObject::getShengming)
                                .thenComparing(Comparator.comparing(FightObject::getParry).reversed()))
                        .collect(Collectors.toList());
                //测试 木 心 是怎么回事


            } else if (type == 265984) {
                // 队伍按速度从快到慢来分配，速度快者能优先得到水的辅助
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            } else if (type == 12032) {
                // 队伍按速度从快到慢来分配，速度快者能优先得到火的辅助，跟水一样
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            } else if (type == 134912) {
                // 队伍按速度从快到慢来分配，速度快者能优先得到火土的辅助，跟水一样
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            }
            // 如果点击的人是自己的话。
            Iterator<FightObject> iterator = alive.iterator();
            while (iterator.hasNext()) {
                FightObject next = iterator.next();
                if (next.fid == fightRequest.vid) {
                    --num;
                    fightObjects.add(next);
                    iterator.remove();
                    break;
                }
            }
            fightObjects.addAll(alive.subList(0, num > alive.size() ? alive.size() : num));
        } else if (type == 0) {// 攻击目标
            FightTeam friendsFightTeam = getFightTeamDM(fightContainer, fightRequest.id);
            // 活着的人
            List<FightObject> alive = getAlive(friendsFightTeam.fightObjectList);
            if (alive.size() > num) {
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getPos)).collect(Collectors.toList());
                // 找出攻击主目标的索引
                int index = 0;
                for (FightObject a : alive) {
                    if (a.fid == fightRequest.vid) {
                        break;
                    }
                    index++;
                }
                List<FightObject> sortFightObject = new ArrayList<>();
                for (int i = 0; i < num; i++) {
                    if (index > alive.size() - 1) {
                        index = 0;
                    }
                    sortFightObject.add(alive.get(index));
                    index++;
                }
                alive = sortFightObject;
            }
            // 如果点击的人是自己的话。
            Iterator<FightObject> iterator = alive.iterator();
            while (iterator.hasNext()) {
                FightObject next = iterator.next();
                if (next.fid == fightRequest.vid) {
                    --num;
                    fightObjects.add(next);
                    iterator.remove();
                    break;
                }
            }
            fightObjects.addAll(alive.subList(0, num > alive.size() ? alive.size() : num));
        } else if (type == -1) {
            FightTeam friendsFightTeam = getFightTeamDM(fightContainer, fightRequest.id);
            // 活着的人
            List<FightObject> alive = getAlive(friendsFightTeam.fightObjectList);
            // 谁速度快，谁就先挨揍吧！
            alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                    .collect(Collectors.toList());
            // 如果点击的人是自己的话。
            Iterator<FightObject> iterator = alive.iterator();
            while (iterator.hasNext()) {
                FightObject next = iterator.next();
                if (next.fid == fightRequest.vid) {
                    --num;
                    fightObjects.add(next);
                    iterator.remove();
                    break;
                }
            }
            fightObjects.addAll(alive.subList(0, num > alive.size() ? alive.size() : num));
        } else if (type == 3848 || type == 3842 || type == 3856 || type == 3844 || type == 3872) { // 障碍目标
            FightTeam friendsFightTeam = getFightTeamDM(fightContainer, fightRequest.id);
            // 活着的人
            List<FightObject> alive = getAlive(friendsFightTeam.fightObjectList);
            if (type == 3848) {
                // 按伤害计算排序，如果伤害一样的话就按按照速度排序、金
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            } else if (type == 3842) {
                // 按生命值最低的人来添加、木
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getAccurate).reversed())
                        .collect(Collectors.toList());
            } else if (type == 3856) {
                // 队伍按速度从快到慢来分配，速度快者能优先得到水的辅助
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            } else if (type == 3844) {
                // 队伍按速度从快到慢来分配，速度快者能优先得到火的辅助，跟水一样
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            } else if (type == 3872) {
                // 队伍按速度从快到慢来分配，速度快者能优先得到火土的辅助，跟水一样
                alive = alive.stream().sorted(Comparator.comparing(FightObject::getParry).reversed())
                        .collect(Collectors.toList());
            }
            // 如果点击的人是自己的话。
            Iterator<FightObject> iterator = alive.iterator();
            while (iterator.hasNext()) {
                FightObject next = iterator.next();
                if (next.fid == fightRequest.vid) {
                    --num;
                    fightObjects.add(next);
                    iterator.remove();
                    break;
                }
            }
            fightObjects.addAll(alive.subList(0, num > alive.size() ? alive.size() : num));
        }
        return fightObjects;
    }

    // 任何战斗结束之后会在此进行清算，包括分配战斗的奖励

    private static void afterFight(FightContainer fightContainer, boolean isForaceOver) {
        try {
            if (GameObjectChar.getGameObjectChar() == null)
                return;
            List<FightTeam> teamList = fightContainer.teamList;
            for (FightTeam fightTeam : teamList) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.type == 1) {
                        int fid = fightObject.fid;
                        GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fid);
                        if (gameObjectChar == null) {
                            continue;
                        }
                        if (isForaceOver) {
                            GameCommonUtil.sendTips("回合数超过最大限制，你已被系统强制结束战斗", gameObjectChar);
                        }
                        break;
                    }
                }
            }
            // 强制回合结束
            if (isForaceOver) {
                return;
            }
            GameObjectChar gameObjectChar = getGameObjectChar(fightContainer);
            if (gameObjectChar == null) {
                return;
            }
            List<Chara> team = new ArrayList<>();
            //如果有队伍则去下重复
            if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                team = gameObjectChar.gameTeam.duiwu.stream().distinct().collect(Collectors.toList());
            }
            // 获取角色打的怪物
            List<FightObject> guaiwu = guaiwu(fightContainer);
            // 如果是在试道的话.
            if (gameObjectChar.chara.mapid == 38004 && GameShiDao.statzhuangtai == 3
                    && guaiwu == null) {
                GameShiDao.gameShiDaoPk(fightContainer);
                return;
            }
            // 强制切磋
            if (gameObjectChar.action.equals("activeForcePk")
                    || gameObjectChar.action.equals("passiveForcePk")) {
                GameCommonUtil.forcePk(fightContainer);
                return;
            }

            //擂台结算
            if (gameObjectChar.action.equals("ctPk")
                    && gameObjectChar.chara.mapid == 5000) {
                try {
                    GameActiveUtil.ctPkOver(fightContainer);
                } finally {
                    gameObjectChar.action = "";
                }
                return;
            }

            // 标志怪物是否全部死亡
            // 注意isDead==true表示还有存活的怪物。角色被打败
            // isDead==false表示怪物已经全部死亡。角色胜利
            boolean isDead = false;
            if (guaiwu != null) {
                for (FightObject fightObject2 : guaiwu) {
                    if (!fightObject2.isDead()) {
                        isDead = true;
                    }
                }
            }

            Chara chara = gameObjectChar.chara;
            if (chara == null) {
                return;
            }

            // 主线任务不管有没有胜利
            if ("主线—拜入师门s8".equals(chara.current_task) && chara.taskMap.get("主线—拜入师门") != null
                    && "新手蒙面".equals(chara.zhandouInfo) && guaiwu.get(0).str.equals("神秘蒙面人")) {
                // 创建任务
                chara.taskMap.get("主线—拜入师门").task_extra_para = "1";
                chara.taskMap.get("主线—拜入师门").task_state = "5";
                Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哼，看不出来，倒有两把刷子。", "主线—拜入师门", 6213, "神秘蒙面人");
                GameObjectChar.send(new M45056_0(), vo_45056_2);
                return;
            }
            // 固定队伍
            GameTeamUtil.updateFixedTeamData(gameObjectChar);
            if (!isDead) {
                if (guaiwu == null || guaiwu.isEmpty()) {
                    return;
                }
                String bossName = guaiwu.get(0).str;
                // 测试海盗o
                if (guaiwu != null && "测试海盗".equals(gameObjectChar.flag)) {
                    log.info("队伍解散测试....");
                    if (!team.isEmpty()) {
                        for (Chara teamChara : team) {
                            GameActiveUtil.fightVictoryInfo(teamChara, "测试海盗");
                        }
                    } else {
                        GameActiveUtil.fightVictoryInfo(chara, "测试海盗");
                    }
                    return;
                }

                if ("猎人头领".equals(guaiwu.get(0).str)) {
                    try {
                        if (!team.isEmpty()) {
                            for (Chara teamChara : team) {
                                if (ThreadLocalRandom.current().nextBoolean()) {
                                    GameUtil.huodedaoju(teamChara, "大桃子", 1);
                                    GameCommonUtil.sendTips("你获得了1个大桃子", teamChara.id);
                                } else {
                                    GameUtil.huodedaoju(teamChara, "大萝卜", 1);
                                    GameCommonUtil.sendTips("你获得了1个大萝卜", teamChara.id);
                                }
                            }
                        } else {
                            if (ThreadLocalRandom.current().nextBoolean()) {
                                GameUtil.huodedaoju(chara, "大桃子", 1);
                                GameCommonUtil.sendTips("你获得了1个大桃子", chara.id);
                            } else {
                                GameUtil.huodedaoju(chara, "大萝卜", 1);
                                GameCommonUtil.sendTips("你获得了1个大萝卜", chara.id);
                            }
                        }
                    } finally {
                        Vo_APPEAR lieren = GameCore.otherBoosMonster.get(chara.zhandouId);
                        if (lieren != null) {
                            GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), chara.zhandouId, 6000);
                        }
                        GameCore.fightObject.remove(chara.zhandouId);
                    }
                    return;
                }

                //七杀试炼
                if ("七杀试炼".equals(guaiwu.get(0).uid)) {
                    for (Chara teamChara : team) {
                        if (teamChara.qishaCount  > GameConfig.config.getBaseConfig().getQishaCount()) {
                            GameCommonUtil.sendTips("你今日已无奖励次数！", teamChara.id);
                            continue;
                        }
                        GameActiveUtil.fightVictoryInfo(teamChara, "七杀试炼");
                        CMD_SELECT_MENU_ITEM.refreshTask(teamChara);
                    }
                    return;
                }

                // 劫狱求情
                if (gameObjectChar.flag.equals("jieyu_qiuqing") && guaiwu.get(0).uid.equals("jieyu_qiuqing")) {
                    gameObjectChar.flag = "";
                    GameActiveUtil.fightVictoryInfo(gameObjectChar.chara, "劫狱");
                    GameActiveUtil.zuolaoJieYuSuccess(gameObjectChar, chara.zhandouId);
                    return;
                }

                // 年兽活动
                if ("年兽".equals(guaiwu.get(0).str) && chara.zhandouId == 99999) {
                    GameActiveUtil.victoryNewYearBeast(gameObjectChar);

                    return;
                }
                // 主线任务黄仨儿和宠物战斗
                if ("兔子".equals(guaiwu.get(0).str) && chara.zhandouId == 8888888) {
                    Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
                    task.task_state = "3";
                    task.task_extra_para = "1";
                    Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                            GameData.that.baseNpcDialogueService.findById(408));
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                // 揽仙镇外寻找玉佩
                if (chara.mapName.equals("揽仙镇外") && chara.taskMap.get("主线—浮生若梦") != null
                        && "主线—浮生若梦_s8".equals(chara.taskMap.get("主线—浮生若梦").currentTask)) {
                    if (guaiwu.get(0).str.equals("青蛙") || guaiwu.get(0).str.equals("松鼠")) {
                        Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
                        task.task_state = "1";
                        task.task_extra_para = "1";
                        Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
                                GameData.that.baseNpcDialogueService.findById(422).getContent(), "主线—浮生若梦",
                                chara.waiguan, chara.name);
                        GameObjectChar.send(new M45056_0(), vo_45056_2);
                        return;
                    }
                }
                // 师门试炼
                if (chara.taskMap.get("主线—拜入师门") != null &&
                        "主线—拜入师门s3".equals(chara.taskMap.get("主线—拜入师门").currentTask) && "新手木桩".equals(chara.zhandouInfo) && guaiwu.get(0).str.equals("木桩")) {
                    // 创建任务
                    chara.current_task = "主线—拜入师门s4";
                    GameUtil.renwujiangli(chara);
                    Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("主线—拜入师门s4");
                    renwu.setTaskPrompt(
                            "询问#P" + GameCommonUtil.shimen_tongzi[chara.polar - 1] + "|E=【主线】师父接下来有何安排#P有何安排");
                    GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
                    return;
                }
                if (chara.taskMap.get("主线—拜入师门") != null && guaiwu.get(0).fid == 88888888) {
                    if ("主线—拜入师门s15".equals(chara.taskMap.get("主线—拜入师门").currentTask)
                            && "3".equals(chara.taskMap.get("主线—拜入师门").task_state)) {
                        chara.taskMap.get("主线—拜入师门").task_extra_para = "1";
                        chara.taskMap.get("主线—拜入师门").task_state = "4";
                        Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "呜呜呜，他不给我饭吃......", "主线—拜入师门", 6018, "走失的孩子");
                        GameObjectChar.send(new M45056_0(), vo_45056_2);
                        GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
                    } else if ("主线—拜入师门s20".equals(chara.taskMap.get("主线—拜入师门").currentTask)
                            && "3".equals(chara.taskMap.get("主线—拜入师门").task_state)) {
                        chara.taskMap.get("主线—拜入师门").task_extra_para = "1";
                        chara.taskMap.get("主线—拜入师门").task_state = "4";
                        Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "别杀我，别杀我，我从未害人啊！", "主线—拜入师门", 6106, "桃精");
                        GameObjectChar.send(new M45056_0(), vo_45056_2);
                    }
                    return;
                }
                if (chara.taskMap.get("主线—拜入师门") != null && "主线—拜入师门s22".equals(chara.taskMap.get("主线—拜入师门").currentTask)
                        && "主线赤羽鸟怪".equals(gameObjectChar.flag) && "赤羽鸟怪".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—拜入师门").task_extra_para = "1";
                    chara.taskMap.get("主线—拜入师门").task_state = "5";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道士有些本事，此处阵法未成，我才糟了你的道，这桃柳林深处有一完整阵法，你可敢来闯！",
                            "主线—拜入师门", 6211, "赤羽鸟怪");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—拜入师门") != null && "主线—拜入师门s23".equals(chara.taskMap.get("主线—拜入师门").currentTask)
                        && "主线赤羽鸟怪s23".equals(gameObjectChar.flag) && "赤羽鸟怪".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—拜入师门").task_extra_para = "1";
                    chara.taskMap.get("主线—拜入师门").task_state = "4";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "说！谁派你来的！为何在此布置阵法！", "主线—拜入师门");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—拜入师门") != null && "主线—拜入师门s28".equals(chara.taskMap.get("主线—拜入师门").currentTask)
                        && "主线蟒精s28".equals(gameObjectChar.flag) && "蟒精".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—拜入师门").task_extra_para = "1";
                    chara.taskMap.get("主线—拜入师门").task_state = "4";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "快老实交代，为何要在桃柳林聚集妖孽，兴风作浪！", "主线—拜入师门");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s6".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s6".equals(gameObjectChar.flag) && "恶霸".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "4";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哼，看你以后还敢作恶！！", "主线—山雨欲来");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s7".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s7".equals(gameObjectChar.flag) && "妖风".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "4";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小道士的确挺厉害的嘛，姐姐下次再跟你玩吧。", "主线—山雨欲来", 6140, "妖风");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s9".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s9".equals(gameObjectChar.flag) && "琵琶精".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "5";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "可恶，这是什么妖法，为什么，为什么我没有力气了。", "主线—山雨欲来");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s15".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s15".equals(gameObjectChar.flag) && "恶霸怨魂".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "6";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我不甘心！！为什么又是你！", "主线—山雨欲来", 6141, "恶霸怨魂");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s18".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s18".equals(gameObjectChar.flag) && "北海乌龙".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "5";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
                            "定魂珠乃老龙飞升的依仗，怎能轻易予人。况且区区凡人魂魄，根本无法承受它的力量，道士你若执迷不悟，休怪老龙大开杀戒了！", "主线—山雨欲来", 6117, "北海乌龙");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s19".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s19".equals(gameObjectChar.flag) && "雉鸡精".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "7";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "此番差点上当，还好最后除掉了雉鸡精。", "主线—山雨欲来");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                if (chara.taskMap.get("主线—山雨欲来") != null && "主线—山雨欲来s22".equals(chara.taskMap.get("主线—山雨欲来").currentTask)
                        && "主线—山雨欲来s22".equals(gameObjectChar.flag) && "虎妖".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("主线—山雨欲来").task_extra_para = "1";
                    chara.taskMap.get("主线—山雨欲来").task_state = "3";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "还好及时擒住了这虎妖，才没有生什么事端。", "主线—山雨欲来");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                // 助人为乐
                if (chara.taskMap.get("助人为乐—打抱不平") != null && "助人为乐—打抱不平s1".equals(gameObjectChar.flag) && "醉酒暴徒".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("助人为乐—打抱不平").task_extra_para = "1";
                    chara.taskMap.get("助人为乐—打抱不平").task_state = "7";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "看你今后还敢不敢闹事。", "助人为乐—打抱不平");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                if (chara.taskMap.get("助人为乐—打抱不平") != null && "助人为乐—打抱不平s2".equals(gameObjectChar.flag) && "无名剑客".equals(guaiwu.get(0).getStr())) {
                    chara.taskMap.get("助人为乐—打抱不平").task_extra_para = "1";
                    chara.taskMap.get("助人为乐—打抱不平").task_state = "4";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "别打了，你到底为何来找我？", "助人为乐—打抱不平", 6231, "无名剑客");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                if (chara.taskMap.get("助人为乐—扶危救困") != null && "助人为乐—扶危救困s1".equals(gameObjectChar.flag)
                        && guaiwu.get(0).getStr().equals("百变飞贼")) {
                    chara.taskMap.get("助人为乐—扶危救困").task_extra_para = "1";
                    chara.taskMap.get("助人为乐—扶危救困").task_state = "2";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "没想到这个#Y衙门口的乞丐#n居然是骗子乔装，我还是再看看有没有别的乞丐需要帮助吧。",
                            "助人为乐—扶危救困");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                } else if (chara.taskMap.get("助人为乐—扶危救困") != null && "助人为乐—扶危救困s2".equals(gameObjectChar.flag)
                        && guaiwu.get(0).getStr().equals("百变飞贼")) {
                    chara.taskMap.get("助人为乐—扶危救困").task_extra_para = "1";
                    chara.taskMap.get("助人为乐—扶危救困").task_state = "2";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "没想到这个#Y药铺旁的乞丐#n居然是骗子乔装，我还是再看看有没有别的乞丐需要帮助吧。",
                            "助人为乐—扶危救困");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                } else if (chara.taskMap.get("助人为乐—扶危救困") != null && "助人为乐—扶危救困s3".equals(gameObjectChar.flag)
                        && guaiwu.get(0).getStr().equals("劫匪")) {
                    chara.taskMap.get("助人为乐—扶危救困").task_extra_para = "1";
                    chara.taskMap.get("助人为乐—扶危救困").task_state = "2";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "没想到这个#Y城北的乞丐#n居然是劫匪乔装，不过这下也算是为民除害了。", "助人为乐—扶危救困");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                // 击败超级boss
                if ("赤血魔猿".equals(guaiwu.get(0).str) || "魅影蝎后".equals(guaiwu.get(0).str)
                        || "黑熊妖皇".equals(guaiwu.get(0).str) || "血炼魔猪".equals(guaiwu.get(0).str)) {
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                        for (Chara duiyuan : team) {
                            GameActiveUtil.fightVictoryInfo(duiyuan, "超级BOSS");
                            duiyuan.superBossNum++;
                            CMD_SELECT_MENU_ITEM.refreshTask(duiyuan);
                        }
                    }
                    log.info("击败超级Boss");
                    return;
                }
                if ("超级魔化朱雀".equals(guaiwu.get(0).str) || "超级魔化玄武".equals(guaiwu.get(0).str)
                        || "超级魔化青龙".equals(guaiwu.get(0).str) || "超级魔化疆良".equals(guaiwu.get(0).str)) {
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                        for (Chara duiyuan : team) {
                            GameActiveUtil.fightVictoryInfo(duiyuan, "二阶超级BOSS");
                            duiyuan.superBossNum++;
                            CMD_SELECT_MENU_ITEM.refreshTask(duiyuan);
                        }
                    }
                    log.info("击败超级Boss");
                    return;
                }
                // 帮派日常挑战
                if ("帮派日常陪练".equals(guaiwu.get(0).str)) {
                    log.info("帮派日常挑战胜利");
                    Vo_61553_0 task = chara.taskMap.get("帮派日常挑战");
                    if (task != null) {
                        if ("partyTiaozhanEnd".equals(task.task_state)) {
                            // 移除任务.
                            GameUtilRenWu.removeTask("帮派日常挑战", chara);
                            // 整个任务流程结束
                            GamePartyUtil.endPartyTask(chara, 100);
                        } else {
                            GamePartyUtil.nextPartyTiaozhanTask(chara);
                        }
                    }
                    return;
                }
                // 处理帮派任务
                if ("花纹蛇".equals(guaiwu.get(0).str) || "灵睛鼠".equals(guaiwu.get(0).str)) {
                    Vo_61553_0 task = chara.getTaskMap().get("帮派任务");
                    if (task != null) {
                        // 0:战斗1:当前数量2:总数3:怪物名称
                        String[] split = task.task_extra_para.split(":");
                        int beatNum = 0;
                        for (FightObject f : guaiwu) {
                            if (split[3].equals(f.str)) {
                                beatNum++;
                            }
                        }
                        // 如果当前已经完成了数量就开启下一个任务哦。
                        beatNum += Integer.valueOf(split[1]);
                        if (beatNum >= Integer.valueOf(split[2])) {
                            // 任务已完成.
                            GameCommonUtil.dialogOk("任务已完成，回复#Y帮派总管#n。");
                            GamePartyUtil.partyTaskFinish(chara);
                            log.info("帮派任务完成");
                            return;
                        } else {
                            // 没有完成则在当前任务加上数量
                            split[1] = String.valueOf(beatNum);
                            // 更新任务
                            StringBuffer extb = new StringBuffer();
                            for (int i = 0; i < split.length; i++) {
                                extb.append(split[i]);
                                if (i < split.length - 1) {
                                    extb.append(":");
                                }
                            }
                            task.task_extra_para = extb.toString();
                            task.task_prompt = org.apache.commons.lang3.StringUtils.join("前往#Z帮派总坛|$1#Z，消灭#R#n",
                                    beatNum, "/", split[2], "只#R", split[3]);
                            GameUtilRenWu.createTask(task, chara);
                        }
                        log.info("帮派任务击杀{},完成数量:{}", split[3], beatNum);
                        return;
                    }
                }

                // 人物飞升挑战胜利
                if ("真人灵兽".equals(bossName)) {
                    log.info("飞升引路人1");
                    CharaFlyHandler.flyStepNo2(chara);
                    return;
                }
                if ("冰晶龙鳞兽王".equals(bossName)) {
                    log.info("飞升引路人2");
                    CharaFlyHandler.flyStepNo3(chara);
                    return;
                }
                if ("雪狐王".equals(bossName)) {
                    log.info("飞升引入路--找南华真人去飞升");
                    CharaFlyHandler.flyStepNo4(chara);
                    return;
                }
                // 处理副本.
                GameMap gameMap = gameObjectChar.gameMap;
                if (guaiwu != null && gameMap.isDugeno()) {
                    // 如果地图是副本的话.
                    GameDugeon gameDugeon = ((GameZone) gameMap).gameDugeon;
                    gameDugeon.fightWin(chara);
                    return;
                }
                // 处理除暴任务
                if (chara.npcchubao.size() > 0 && guaiwu != null
                        && chara.npcchubao.get(0).name.equals(guaiwu.get(0).str)) {
                    if (gameObjectChar.gameTeam != null && team != null) {
                        GameUtil.chubaorenwu(chara, team);
                    } else {
                        GameUtil.chubaorenwu(chara, Lists.newArrayList(chara));
                    }
                    return;
                }
                Vo_APPEAR shudao = chara.shudao.get(chara.zhandouId);
                // 处理刷道任务
                final List<Chara> fteam = team;
                if (shudao != null && guaiwu != null && shudao.name.equals(guaiwu.get(0).str)) {
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                        GameUtil.singleshuadao(chara, fteam, guaiwu.size());
                    } else {
                        GameUtil.singleshuadao(chara, Lists.newArrayList(chara), guaiwu.size());
                    }
                    return;
                }

                // 处理悬赏任务
                if (guaiwu != null && GameShuaGuai.xuanshang.get(chara.zhandouId) != null) {
                    if (gameObjectChar.gameTeam != null && team != null) {
                        for (int i = 0; i < team.size(); ++i) {
                            GameUtil.nextxuanshang(chara, team.get(i), chara.zhandouId);
                        }
                    } else {
                        GameUtil.nextxuanshang(chara, chara, chara.zhandouId);
                    }
                    return;
                }

                // 修法任务
                if (guaiwu != null && guaiwu.size() == 5 && chara.xiufaNpcName != null && !chara.xiufaNpcName.equals("")
                        && chara.xiufacishu < 4 && "青龙白虎朱雀玄武".contains(chara.xiufaNpcName)
                        && "青龙白虎朱雀玄武".contains(guaiwu.get(0).str) && guaiwu.get(1).str.contains("分身")) {
                    if (gameObjectChar.gameTeam != null && team != null) {
                        for (int i = 0; i < team.size(); ++i) {
                            GameUtil.nextxiufa(chara, team.get(i));
                        }
                    } else {
                        GameUtil.nextxiufa(chara, chara);
                    }
                    return;
                }

                // 十绝阵
                if (guaiwu != null && chara.xiuxingNpcname.equals((guaiwu.get(0)).str)
                        && chara.xiuxingNpcname.contains("阵主")) {
                    if (gameObjectChar.gameTeam != null && team != null) {
                        GameUtil.nextzhenzhu(chara, team);

                    } else {
                        GameUtil.nextzhenzhu(chara, Lists.newArrayList(chara));
                    }
                    return;
                }

                // 修行任务
                if (guaiwu != null && chara.xiuxingNpcname.equals(guaiwu.get(0).str)
                        && guaiwu.get(0).str.endsWith("神")) {
                    if (gameObjectChar.gameTeam != null && team != null) {
                        GameUtil.nextxiuxing(chara, team);
                    } else {
                        GameUtil.nextxiuxing(chara, Lists.newArrayList(chara));
                    }
                    return;
                }

                // 和攻城BOSS战斗结束
                if (guaiwu != null && "攻城BOSS".equals(guaiwu.get(0).uid)) {
                    Vo_APPEAR vo_65529_0 = GameLine.gameGongCheng.gongchengBoss.get(chara.zhandouId);
                    if (vo_65529_0 != null) {
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), vo_65529_0.id, vo_65529_0.mapid);
                        GameLine.gameGongCheng.gongchengBoss.remove(vo_65529_0.id);
                    }
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                        for (int k = 0; k < team.size(); ++k) {
                            Chara duiwuChara = team.get(k);
                            GameObjectChar duiwuGameObjectChar = GameObjectCharMng.getGameObjectChar(duiwuChara.id);
                            // 更新攻城次数
                            duiwuChara.gongchengcishu++;
                            // 等级差超过29级的队员无法领取奖励

                            int llevel = guaiwu.get(0).guaiwulevel;
                            if (llevel < 120) {
                                llevel = 120;
                            }

                            if (duiwuChara.level - llevel >= 129) {
                                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                                vo_20481_0.msg = "您的等级比BOSS高29级，暂无法领取奖励！";
                                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                                GameObjectCharMng.getGameObjectChar(duiwuChara.id).sendOne(new M20481_0(), vo_20481_0);
                            } else if (duiwuChara.gongchengcishu > GameConfig.config.getBaseConfig().getBossNum()) {
                                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                                vo_20481_0.msg = org.apache.commons.lang3.StringUtils.join("您今日的挑战攻城BOSS次数已达到上限",
                                        GameConfig.config.getBaseConfig().getBossNum(), "次，暂无法领取奖励！");
                                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                                GameObjectCharMng.getGameObjectChar(duiwuChara.id).sendOne(new M20481_0(), vo_20481_0);
                            } else {
                                if (!GameActiveUtil.fightVictoryInfo(duiwuChara, "攻城BOSS")) {
                                    String[] wupin1 = new String[]{"超级神兽丹", "物品"};
                                    GameUtil.huodechoujiang(wupin1, duiwuGameObjectChar, "攻城BOSS");
                                    GameUtil.adddaohang(duiwuChara, duiwuChara.level * 100 * 1440, "攻城BOSS");
                                    GameUtil.addQianNeng(duiwuChara, 6840 * duiwuChara.level, "攻城BOSS");
                                    GameUtil.huodejingyan(duiwuChara, duiwuChara.level * 2700, "攻城BOSS");
                                    GameUtil.addJinbi(duiwuChara, 1000000, "攻城BOSS");
                                    GameUtil.addJinYuanBao(duiwuGameObjectChar, 100000, "攻城BOSS");
                                    GameUtil.weijianding(duiwuChara);
                                    // 下面给妖石，首饰，神兽
                                    int jilv = new Random().nextInt(100);
                                    if (jilv < 50) {
                                        String[] wupin = new String[]{"召唤令·上古神兽", "物品"};
                                        GameUtil.huodechoujiang(wupin, duiwuGameObjectChar, "攻城BOSS");
                                    }
                                }
                                CMD_SELECT_MENU_ITEM.refreshTask(duiwuChara);
                                // 获得妖石是保证有的
                                int pos = 2 + new Random().nextInt(6);
                                int jilv = new Random().nextInt(100);
                                int level = 6;
                                if (jilv < 5)
                                    level = 8;
                                else if (jilv < 40)
                                    level = 7;
                                else if (jilv < 100)
                                    level = 6;
                                GameUtil.huodeyaoshi(duiwuGameObjectChar, pos, 1, level);

                                // 给首饰
                                jilv = new Random().nextInt(100);
                                if (jilv < 5)
                                    GameUtil.dengjishoushi(duiwuChara, GameUtil.SHOU_SHI_70);
                                else if (jilv < 40)
                                    GameUtil.dengjishoushi(duiwuChara, GameUtil.SHOU_SHI_60);
                                else
                                    GameUtil.dengjishoushi(duiwuChara, GameUtil.SHOU_SHI_50);
                                duiwuChara.gongchengcishu++;
                            }
                        }
                    }
                    return;
                }
                ConcurrentHashMap<Integer, Vo_APPEAR> haidaos = GameLine.gameGongCheng.haidaoGuaiwu;
                // 和海盗结束战斗
                if (guaiwu != null && haidaos != null && !haidaos.isEmpty() && guaiwu.get(0).str.equals("海盗")) {
                    // 获取当前战斗对象id
                    Vo_APPEAR vo_65529_0 = haidaos.get(chara.zhandouId);
                    if (vo_65529_0 != null) {
                        vo_65529_0.isHide = 1;
                        GameObjectCharMng.sendAllmap(new MSG_APPEAR_MONSTER(), vo_65529_0, vo_65529_0.mapid);
                        haidaos.remove(chara.zhandouId);
                        // 删除参战
                        GameConfig.canzhanBoos.remove("海盗_" + vo_65529_0.id);
                    }
                    // 提示剩余数量
                    if (GameLine.gameGongCheng.haidaoGuaiwu.size() != 0) {
                        GameUtil.sendYaoYan(
                                org.apache.commons.lang3.StringUtils.join("#n刚刚又有#R1#W名海盗被我们的英雄消灭了！真是了不起啊！尚有#R",
                                        GameLine.gameGongCheng.haidaoGuaiwu.size(), "#W名海盗任在#Z东海渔村#Z#n作乱，请各位英雄继续加油啊！"));
                    } else {
                        GameUtil.sendYaoYan("真是了不起啊，#R海盗#n已全部被消灭了!!!");
                    }
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                        for (int k = 0; k < team.size(); ++k) {
                            int haidaocishu = team.get(k).haidaocishu;
                            Chara duiwuChara = team.get(k);
                            GameObjectChar duiwuGameObjectChar = GameObjectCharMng.getGameObjectChar(duiwuChara.id);
                            if (haidaocishu < GameConfig.config.getHaidao().getCount()) {
                                if (!GameActiveUtil.fightVictoryInfo(duiwuChara, "海盗")) {
                                    // 获取设置的奖励.
                                    Haidao haidao = GameConfig.config.getHaidao();
                                    if (haidao.getQianneng() == 1) {
                                        GameUtil.addQianNeng(duiwuChara, 12500 * duiwuChara.level, "海盗");
                                    }
                                    // 金币
                                    if (haidao.getJinbi() == 1) {
                                        GameUtil.addJinbi(duiwuChara, 500000, "海盗");
                                    }
                                    // 未鉴定
                                    if (haidao.getWeijianding() == 1) {
                                        GameUtil.weijianding(duiwuChara);
                                    }
                                    // 道行
                                    if (haidao.getDaohang() == 1) {
                                        GameUtil.adddaohang(duiwuChara, 800000, "海盗");
                                    }
                                    // 经验
                                    if (haidao.getJingyan() == 1) {
                                        GameUtil.huodejingyan(duiwuChara, 102500, "海盗");
                                    }
                                    if (haidao.getDaoju() != null && haidao.getDaoju().length > 0) {
                                        // 随机获得哪些道具
                                        int count = (int) (Math.random() * haidao.getDaoju().length);
                                        if (count >= haidao.getDaoju().length) {
                                            count = haidao.getDaoju().length - 1;
                                        }
                                        List<String> d = new ArrayList<>();
                                        d.add(haidao.getDaoju()[count]);
                                        d.add("物品");
                                        String[] newJiangli = new String[d.size()];
                                        GameUtil.huodechoujiang(d.toArray(newJiangli), duiwuGameObjectChar, "海盗");
                                    }
                                }
                                duiwuChara.haidaocishu++;
                                CMD_SELECT_MENU_ITEM.refreshTask(chara);
                            } else {
                                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                                vo_20481_0.msg = org.apache.commons.lang3.StringUtils.join("你今日已经领取了",
                                        GameConfig.config.getHaidao().getCount(), "次海盗奖励,无法获得奖励");
                                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                                GameObjectCharMng.getGameObjectChar(duiwuChara.id).sendOne(new M20481_0(), vo_20481_0);
                            }
                        }
                    }
                    return;
                }
                // 和战神结束战斗
                if (guaiwu != null && guaiwu.get(0).str.equals("战神")) {
                    Vo_APPEAR vo_65529_0 = GameLine.gameGongCheng.zhanshenGuaiwu.get(guaiwu.get(0).bossid);
                    if (vo_65529_0 != null) {
                        log.info("击杀后的战神ID==={}", guaiwu.get(0).bossid);
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), vo_65529_0.id, vo_65529_0.mapid);
                        GameLine.gameGongCheng.zhanshenGuaiwu.remove(guaiwu.get(0).bossid);
                    }
                    if (gameObjectChar.gameTeam != null && team != null) {
                        for (int k = 0; k < team.size(); ++k) {
                            Chara duiwuChara = team.get(k);
                            GameObjectChar duiwuGameObjectChar = GameObjectCharMng.getGameObjectChar(duiwuChara.id);
                            if (duiwuChara.level - guaiwu.get(0).guaiwulevel > 35) {
                                GameCommonUtil.sendTips("你的等级大于怪物35级无法获取奖励", duiwuGameObjectChar);
                            } else if (duiwuChara.zhanshencishu < GameConfig.config.getBaseConfig().getZhanshenNum()) {
                                if (!GameActiveUtil.fightVictoryInfo(duiwuChara, "战神")) {
                                    String[] wupin1 = new String[]{"超级神兽丹", "物品"};
                                    GameUtil.huodechoujiang(wupin1, duiwuGameObjectChar, "战神");
                                    GameUtil.addQianNeng(duiwuChara, 8000 * duiwuChara.level);
                                    GameUtil.addJinbi(duiwuChara, 500000);
                                    if (new Random().nextInt(100) < 40)
                                        GameUtil.weijianding(duiwuChara);
                                    GameUtil.huodejingyan(duiwuChara, 2890 * duiwuChara.level + 100000);
                                    GameUtil.adddaohang(duiwuChara, 60 * duiwuChara.level * 1440);
                                }
                                duiwuChara.zhanshencishu++;
                                CMD_SELECT_MENU_ITEM.refreshTask(chara);
                            } else {
                                GameCommonUtil.sendTips(
                                        org.apache.commons.lang3.StringUtils.join("你今日已经领取了",
                                                GameConfig.config.getBaseConfig().getZhanshenNum(), "次战神奖励！暂无法领取奖励！"),
                                        duiwuGameObjectChar);
                            }
                        }
                    } else {
                        if (chara.zhanshencishu < GameConfig.config.getBaseConfig().getZhanshenNum()) {
                            if (!GameActiveUtil.fightVictoryInfo(chara, "战神")) {
                                String[] wupin1 = new String[]{"超级神兽丹", "物品"};
                                GameUtil.huodechoujiang(wupin1, gameObjectChar, "战神");
                                GameUtil.addQianNeng(chara, 8000 * chara.level);
                                GameUtil.addJinbi(chara, 500000);
                                if (new Random().nextInt(100) < 40)
                                    GameUtil.weijianding(chara);
                                GameUtil.huodejingyan(chara, 2890 * chara.level + 100000);
                                GameUtil.adddaohang(chara, 60 * chara.level * 1440);
                            }
                            chara.zhanshencishu++;
                            CMD_SELECT_MENU_ITEM.refreshTask(chara);
                        } else {
                            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                            vo_20481_0.msg = org.apache.commons.lang3.StringUtils.join("你今日已经领取了",
                                    GameConfig.config.getBaseConfig().getZhanshenNum(), "次战神奖励！暂无法领取奖励！");
                            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                            gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
                        }
                    }
                    return;
                }
                // 和上古战斗结束
                if (guaiwu != null && ("上古妖王".equals(guaiwu.get(0).str))) {
                    Vo_APPEAR shanggu = GameShuaGuai.shanggu.get(guaiwu.get(0).bossid);
                    // 表示已经自动消失了
                    if (shanggu != null) {
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), shanggu.id, shanggu.mapid);
                        GameShuaGuai.shanggu.remove(shanggu.id);
                    }

                    if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
                        boolean flag = false;
                        for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                            if (duiwu.level >= 70) {
                                //GameConfig.config.getBaseConfig().getTiandixingNum()
                                if (GameConfig.config.getBaseConfig().getShangguNum() < duiwu.shanggucishu) {
                                    GameCommonUtil.sendTips("上古妖王奖励次数已满!", duiwu.id);
                                    return;
                                }
                                int t = (int) Math.floor(duiwu.level / 10);
                                String name = "上古妖王(" + t * 10 + ")";
                               // GameUtil.sendMeTips(name);
                                GameActiveUtil.fightVictoryInfo(duiwu, name);
                            }
                        }
                    } else {
                        if (chara.level >= 70) {
                            if (GameConfig.config.getBaseConfig().getShangguNum() < chara.shanggucishu) {
                                GameCommonUtil.sendTips("上古妖王奖励次数已满!", chara.id);
                                return;
                            }
                            int t = (int) Math.floor(chara.level / 10);
                            String name = "上古妖王(" + t * 10 + ")";
                            //GameUtil.sendMeTips(name);
                            GameActiveUtil.fightVictoryInfo(chara, name);
                        }
                        // GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                    }
                    return;
                }
                // 和万年战斗结束
                if (guaiwu != null && guaiwu.get(0).str.indexOf("万年妖王") != -1) {


                    Vo_APPEAR wannian = GameShuaGuai.wannian.get(guaiwu.get(0).bossid);
                    if (wannian != null) {
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), wannian.id, wannian.mapid);
                        GameShuaGuai.wannian.remove(wannian.id);


                    }


                    if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {

                        boolean flag = false;
                        for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                            if (duiwu.level >= 70) {
                                //if (GameConfig.config.getBaseConfig().getWannianNum() < chara.wanniancishu)
                                if (GameConfig.config.getBaseConfig().getWannianNum() < duiwu.wanniancishu) {
                                    GameCommonUtil.sendTips("万年妖王奖励次数已满!", duiwu.id);
                                    return;
                                }
                                int t = (int) Math.floor(duiwu.level / 10);
                                String name = "万年妖王(" + t * 10 + ")";

                                GameActiveUtil.fightVictoryInfo(duiwu, name);
                            }

                        }

							} else {
								if (chara.level >= 70) {
									if (GameConfig.config.getBaseConfig().getWannianNum() < chara.wanniancishu) {
										GameCommonUtil.sendTips("万年妖王奖励次数已满!", chara.id);
                                return;
                            }
                            int t = (int) Math.floor(chara.level / 10);
                            String name = "万年妖王(" + t * 10 + ")";
                            GameActiveUtil.fightVictoryInfo(chara, name);
                        }
                        // GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                    }

                    return;
                }

                    if (guaiwu != null && ("火焰之灵".equals(guaiwu.get(0).str)) || guaiwu != null && ("金乌之灵".equals(guaiwu.get(0).str)) || guaiwu != null && ("火狮兽".equals(guaiwu.get(0).str)) || guaiwu != null && ("大日金乌".equals(guaiwu.get(0).str))) {
                        RedisUtils redisUtils = GameData.that.redisUtils;
                    //boos血量单独处理
                    String dari_life_str = redisUtils.get("dari_life_str");//当前血量
                    long dqlife = 0;
                    vo_boos_result result = new vo_boos_result();
                    String rankString = redisUtils.get(RedisKeyConstant.RANK_LEFT);
                    List<rank_role> rankList = Lists.newArrayList();
                    Map<String, JSONObject> mapRank = (Map) JSON.parseObject(rankString, Map.class);
                    if (mapRank == null) {
                        mapRank = com.google.common.collect.Maps.newHashMap();
                    }
                    Iterator var65 = ((Map) mapRank).values().iterator();

                    while (var65.hasNext()) {
                        com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) var65.next();
                        rank_role rankRole = (rank_role) JSON.parseObject(jsonObject.toJSONString(), rank_role.class);
                        rankList.add(rankRole);
                    }

                    Map<String, rank_role> map = JSON.parseObject(rankString, Map.class);
                    rank_role rankfile = new rank_role();
                    if (rankList.size() > 0) {
                        rankList.sort(Comparator.comparingLong(rank_role::getDamage).reversed());
                        for (int i = 0; i < rankList.size(); i++) {
                            if (rankList.get(i).getName().equals(chara.getName())) {
                                long left = rankList.get(i).getDamage();
                                //寻找历史排名
                                result.setOld_rank(rankList.get(i).getRank());
                                result.setNew_rank((short) (i + 1));
                                result.setInside_rank((short) (i + 1));
                                if (guaiwu.get(0).str.equals("火焰之灵")) {
                                    result.setAdd_damage(GameConfig.config.getDari().getHuoyanzhiling_left());
                                    left += GameConfig.config.getDari().getHuoyanzhiling_left();
                                    rankfile.setDamage((int) left);
                                    dqlife = Long.parseLong(dari_life_str) - GameConfig.config.getDari().getHuoyanzhiling_left() <= 0 ? 0 : Long.parseLong(dari_life_str) - GameConfig.config.getDari().getHuoyanzhiling_left();
                                    redisUtils.set("dari_life_str", dqlife);
                                } else if (guaiwu.get(0).str.equals("金乌之灵")) {
                                    result.setAdd_damage(GameConfig.config.getDari().getJingwuzhiling_left());
                                    left += GameConfig.config.getDari().getJingwuzhiling_left();
                                    rankfile.setDamage((int) left);
                                    dqlife = Long.parseLong(dari_life_str) - GameConfig.config.getDari().getJingwuzhiling_left() <= 0 ? 0 : Long.parseLong(dari_life_str) - GameConfig.config.getDari().getJingwuzhiling_left();
                                    redisUtils.set("dari_life_str", dqlife);
                                } else if (guaiwu.get(0).str.equals("火狮兽")) {
                                    result.setAdd_damage(GameConfig.config.getDari().getHuoshishou_left());
                                    left += GameConfig.config.getDari().getHuoshishou_left();
                                    rankfile.setDamage((int) left);
                                    dqlife = Long.parseLong(dari_life_str) - GameConfig.config.getDari().getHuoshishou_left() <= 0 ? 0 : Long.parseLong(dari_life_str) - GameConfig.config.getDari().getHuoshishou_left();
                                    redisUtils.set("dari_life_str", dqlife);
                                } else if (guaiwu.get(0).str.equals("大日金乌")) {
                                    result.setAdd_damage(GameConfig.config.getDari().getDarijinwu_left());
                                    left += GameConfig.config.getDari().getDarijinwu_left();
                                    rankfile.setDamage((int) left);
                                    dqlife = Long.parseLong(dari_life_str) - GameConfig.config.getDari().getDarijinwu_left() <= 0 ? 0 : Long.parseLong(dari_life_str) - GameConfig.config.getDari().getDarijinwu_left();
                                    redisUtils.set("dari_life_str", dqlife);
                                }
                                result.setNew_damage((int) left);
                                rankfile.setName(chara.getName());
                                rankfile.setRank(((short) (i + 1)));
                                gameObjectChar.sendOne(new MSG_WORLD_BOSS_RESULT(), result);
                                //更新redis
                                map.put(chara.getName(), rankfile);
                                redisUtils.set(RedisKeyConstant.RANK_LEFT, map);
                            }
                        }

                    }

                    String rankpm = redisUtils.get(RedisKeyConstant.RANK_LEFT);
                    List<rank_role> ranklists = Lists.newArrayList();
                    Map<String, JSONObject> mapRankpm = (Map) JSON.parseObject(rankpm, Map.class);
                    if (mapRankpm == null) {
                        mapRankpm = Maps.newHashMap();
                    }
                    Iterator var99 = ((Map) mapRankpm).values().iterator();
                    while (var99.hasNext()) {
                        com.alibaba.fastjson.JSONObject jsonObject = (com.alibaba.fastjson.JSONObject) var99.next();
                        rank_role rankRole = (rank_role) JSON.parseObject(jsonObject.toJSONString(), rank_role.class);
                        ranklists.add(rankRole);
                    }

                    if (ranklists.size() > 0) {
                        ranklists.sort(Comparator.comparingLong(rank_role::getDamage).reversed());
                        for (int i = 0; i < ranklists.size(); i++) {
                            rank_role ranks = new rank_role();
                            ranks.setName(ranklists.get(i).getName());
                            ranks.setRank((short) (i + 1));
                            ranks.setDamage(ranklists.get(i).getDamage());

                            Map<String, rank_role> mappm = JSON.parseObject(rankpm, Map.class);
                            mappm.put(ranklists.get(i).getName(), ranks);
                            redisUtils.set(RedisKeyConstant.RANK_LEFT, mappm);
                        }
                    }
                    return;
                }


                if (guaiwu != null && ("僵尸王".equals(guaiwu.get(0).str))) {
                    Vo_APPEAR guiguai = GameShuaGuai.guiguai.get(guaiwu.get(0).bossid);
                    if (guiguai != null) {
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), guiguai.id, guiguai.mapid);
                        GameShuaGuai.wannian.remove(guiguai.id);
                    }
                    if (gameObjectChar.gameTeam != null && team != null) {
                        List<Chara> duiwu = team;
                        for (Chara duiwuChara : duiwu) {
                            GameObjectChar duiwuGameObjectChar = GameObjectCharMng.getGameObjectChar(duiwuChara.id);
                            if (duiwuChara.level - guaiwu.get(0).guaiwulevel > 35) {
                                GameCommonUtil.sendTips("你的等级大于怪物35级无法获取奖励", duiwuGameObjectChar);
                            } else if (duiwuChara.wanniancishu < GameConfig.config.getBaseConfig().getWannianNum()) {
                                int pos = 2 + new Random().nextInt(6);
                                int jilv = new Random().nextInt(100);
                                int level = 6;
                                if (jilv < 10) {
                                    level = 8;
                                } else if (jilv < 70) {
                                    level = 7;
                                } else if (jilv < 100) {
                                    level = 6;
                                }
                                GameUtil.huodeyaoshi(duiwuGameObjectChar, pos, 1, level);
                                if (!GameActiveUtil.fightVictoryInfo(duiwuChara, "宝图鬼怪")) {
                                    jilv = new Random().nextInt(100);
                                    if (jilv < 10) {
                                        String[] wupin = new String[]{"召唤令·上古神兽", "物品"};
                                        GameUtil.huodechoujiang(wupin, duiwuGameObjectChar, "");
                                    }
                                    if (jilv < 3) {
                                        String[] wupin = new String[]{"超级女娲石", "物品"};
                                        GameUtil.huodechoujiang(wupin, duiwuGameObjectChar, "挖宝");
                                    }
                                }
                                // 给首饰
                                jilv = new Random().nextInt(100);
                                if (jilv < 30) {
                                    GameUtil.dengjishoushi(duiwuChara, GameUtil.SHOU_SHI_50);
                                } else if (jilv < 100) {
                                    GameUtil.dengjishoushi(duiwuChara, GameUtil.SHOU_SHI_35);
                                }
                                duiwuChara.wanniancishu++;
                            } else {
                                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                                vo_20481_0.msg = org.apache.commons.lang3.StringUtils.join("你今日已经领取了",
                                        GameConfig.config.getBaseConfig().getWannianNum(), "次鬼怪奖励！暂无法领取奖励！");
                                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                                GameObjectCharMng.getGameObjectChar(duiwuChara.id).sendOne(new M20481_0(), vo_20481_0);
                            }
                        }
                    }
                    return;
                }
                //log.info("\"天地星\".equals(guaiwu.get(0).uid)："+"天地星".equals(guaiwu.get(0).uid));
                // 计算刷星的奖励
                if (guaiwu != null && "天地星".equals(guaiwu.get(0).uid)) {
                    Vo_APPEAR xing = GameBossTianDiXing.xing.get(guaiwu.get(0).bossid);
                    String replace = "";
                    if (guaiwu.get(0).str.length() > 1) {
                        String substring = guaiwu.get(0).str.substring(1, 2);
                        replace = guaiwu.get(0).str.replace(substring, "");
                    }
                    if (gameObjectChar.gameTeam != null && team != null) {
                        for (int k = 0; k < team.size(); ++k) {
                            GameObjectChar teamGameObjectChar = GameObjectCharMng
                                    .getGameObjectChar(team.get(k).id);
                            // 等级差超过29级的队员无法领取奖励
                            if (teamGameObjectChar.chara.level - guaiwu.get(0).guaiwulevel >= 29) {
                                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                                vo_20481_0.msg = "您的等级大于星29级，暂无法领取奖励！";
                                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                                teamGameObjectChar.sendOne(new M20481_0(), vo_20481_0);
                            } else if (teamGameObjectChar.chara.getTiandixingNum() + 1 > GameConfig.config
                                    .getBaseConfig().getTiandixingNum()) {
                                GameCommonUtil.sendTips("你今日已无奖励次数！", teamGameObjectChar);
                            } else {
                                GameUtil.addQianNeng(teamGameObjectChar.chara, teamGameObjectChar.chara.level * 3700,
                                        "刷星");
                                // 获取装备
                                GameCommonUtil.getRandomEquipByLevel(teamGameObjectChar.chara,
                                        teamGameObjectChar.chara.level);
                                //不是变异星君就刷新
                                if(xing.score==11){
                                    if (GameActiveUtil.fightVictoryInfo2(teamGameObjectChar.chara, guaiwu.get(0).str,xing)) {
                                        teamGameObjectChar.chara.tiandixingNum++;
                                        CMD_SELECT_MENU_ITEM.refreshTask(chara);
                                    } else {
                                        GameUtil.shuaxingOver(teamGameObjectChar.chara,
                                                team.get(k), guaiwu.get(0).guaiwulevel, replace);
                                    }
                                }else{
                                    if (GameActiveUtil.fightVictoryInfo(teamGameObjectChar.chara, guaiwu.get(0).str)) {
                                        teamGameObjectChar.chara.tiandixingNum++;
                                        CMD_SELECT_MENU_ITEM.refreshTask(chara);
                                    } else {
                                        GameUtil.shuaxingOver(teamGameObjectChar.chara,
                                                team.get(k), guaiwu.get(0).guaiwulevel, replace);
                                    }
                                }

                            }
                        }
                    } else {
                        if (chara.level - guaiwu.get(0).guaiwulevel >= 29) {
                            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                            vo_20481_0.msg = "您的等级大于星29级，暂无法领取奖励！";
                            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                            gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
                        } else if (gameObjectChar.chara.getTiandixingNum() + 1 > GameConfig.config.getBaseConfig()
                                .getTiandixingNum()) {
                            GameCommonUtil.sendTips("你今日已无奖励次数！", gameObjectChar);
                        } else {
                            GameUtil.addQianNeng(chara, chara.level * 3700, "天星");
                            // 获取装备
                            GameCommonUtil.getRandomEquipByLevel(chara, chara.level);
                            if(xing.score==11){
                                if (GameActiveUtil.fightVictoryInfo2(chara, guaiwu.get(0).str,xing)) {
                                    chara.tiandixingNum++;
                                    CMD_SELECT_MENU_ITEM.refreshTask(chara);
                                } else {
                                    GameUtil.shuaxingOver(chara, chara, guaiwu.get(0).guaiwulevel, replace);
                                }
                            }else{
                                if (GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str)) {
                                    chara.tiandixingNum++;
                                    CMD_SELECT_MENU_ITEM.refreshTask(chara);
                                } else {
                                    GameUtil.shuaxingOver(chara, chara, guaiwu.get(0).guaiwulevel, replace);
                                }
                            }

                           // GameUtil.shuaxingOver(chara, chara, guaiwu.get(0).guaiwulevel, replace);
                        }
                    }
                    if (xing != null) {
                        Random random = new Random();
                        int a = random.nextInt(10);
                        //百分之30概率出现变异
                        if(a<2){
                            //不是变异星君就刷新
                            if(!(xing.score==11)){
                                if(xing.name.contains("天")){
                                    GameBossTianDiXing.shuaxingxiu(null,"0",String.valueOf(xing.level),xing.name);
                                }else if(xing.name.contains("地")){
                                    GameBossTianDiXing.shuaxingxiu(null,"1",String.valueOf(xing.level),xing.name);
                                }

                            }
                        }
                        //删除星星
                        GameData.that.redisUtils.delete(org.apache.commons.lang3.StringUtils.join("XINGXING_REFRESH:", xing.id, ":",
                                xing.mapid));
                        // 通知所有人.
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), xing.id, xing.mapid);
                        GameBossTianDiXing.xing.remove(xing.id);
                    }
                    return;
                }
            //    log.info("\"鬼差\".guaiwu.get(0).str.contains(\"鬼差\")："+guaiwu.get(0).str.contains("鬼差"));
                //鬼差
                if (guaiwu != null && guaiwu.get(0).str.contains("鬼差")) {
                    String replace = "";
                    if (guaiwu.get(0).str.length() > 1) {
                        String substring = guaiwu.get(0).str.substring(1, 2);
                        replace = guaiwu.get(0).str.replace(substring, "");
                    }
                    int bossid = chara.zhandouId;
                    Vo_APPEAR xing = GameBossTianDiXing.xing.get(bossid);
                    if (gameObjectChar.gameTeam != null && team != null) {
                        for (int k = 0; k < team.size(); ++k) {
                            GameObjectChar teamGameObjectChar = GameObjectCharMng
                                    .getGameObjectChar(team.get(k).id);
                            // 等级差超过29级的队员无法领取奖励
                            if (teamGameObjectChar.chara.level - xing.level >= 29) {
                                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                                vo_20481_0.msg = "您的等级大于鬼差29级，暂无法领取奖励！";
                                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                                teamGameObjectChar.sendOne(new M20481_0(), vo_20481_0);
                            } else if (teamGameObjectChar.chara.getTiandixingNum() + 1 > GameConfig.config
                                    .getBaseConfig().getTiandixingNum()) {
                                GameCommonUtil.sendTips("你今日已无奖励次数！", teamGameObjectChar);
                            } else {
                                GameUtil.addQianNeng(teamGameObjectChar.chara, teamGameObjectChar.chara.level * 3700,
                                        "鬼差");
                                // 获取装备
                                GameCommonUtil.getRandomEquipByLevel(teamGameObjectChar.chara,
                                        teamGameObjectChar.chara.level);
                                if (GameActiveUtil.fightVictoryInfo2(teamGameObjectChar.chara, guaiwu.get(0).str,xing)) {
                                    teamGameObjectChar.chara.tiandixingNum++;
                                    CMD_SELECT_MENU_ITEM.refreshTask(chara);
                                } else {
                                    GameUtil.shuaxingOver(teamGameObjectChar.chara,
                                            team.get(k), guaiwu.get(0).guaiwulevel, replace);
                                }
                            }
                        }
                    } else {
                        if (chara.level - xing.level >= 29) {
                            Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                            vo_20481_0.msg = "您的等级大于鬼差29级，暂无法领取奖励！";
                            vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                            gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
                        } else if (gameObjectChar.chara.getTiandixingNum() + 1 > GameConfig.config.getBaseConfig()
                                .getTiandixingNum()) {
                            GameCommonUtil.sendTips("你今日已无奖励次数！", gameObjectChar);
                        } else {
                            GameUtil.addQianNeng(chara, chara.level * 3700, "鬼差");
                            // 获取装备
                            GameCommonUtil.getRandomEquipByLevel(chara, chara.level);
                            if (GameActiveUtil.fightVictoryInfo2(chara, guaiwu.get(0).str,xing)) {
                                chara.tiandixingNum++;
                                CMD_SELECT_MENU_ITEM.refreshTask(chara);
                            } else {
                                GameUtil.shuaxingOver(chara, chara, guaiwu.get(0).guaiwulevel, replace);
                            }
                            GameUtil.shuaxingOver(chara, chara, guaiwu.get(0).guaiwulevel, replace);
                        }
                    }
                    if (xing != null) {
                        //删除星星
                        GameData.that.redisUtils.delete(org.apache.commons.lang3.StringUtils.join("XINGXING_REFRESH:", xing.id, ":",
                                xing.mapid));
                        // 通知所有人.
                        GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), xing.id, xing.mapid);
                        GameBossTianDiXing.xing.remove(xing.id);
                    }
                    return;
                }

                // 击杀试道元魔
                if (guaiwu != null && "试道元魔".equals(guaiwu.get(0).str)) {
                    if (gameObjectChar.gameTeam != null && team != null) {
                        // 试道加积分.
                        gameObjectChar.chara.shidaodaguaijifen += 1;
                        for (int i = 0; i < team.size(); ++i) {
                            Chara duiyuan = team.get(i);
                            GameActiveUtil.fightVictoryInfo(duiyuan, "试道元魔");
                            GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(),
                                    GameCommonUtil.shidaoTaskInfoNo2(gameObjectChar.chara.shidaodaguaijifen, 0),
                                    chara.id);
                        }

                    }
                    return;
                }

                // 宠物飞升挑战
                if (null != guaiwu && PetFlyMgr.isPetFeiSheng(guaiwu.get(0).str)) {
                    PetFlyMgr.onFightSuccess(chara);
                    return;
                }

                // 战胜了通天塔的星君
                if (guaiwu != null && "北斗神将玉衡星君天权星君天玑星君天璇星君天枢星君摇光星君开阳星君".indexOf(guaiwu.get(0).str) != -1) {
                    GameActiveUtil.tongtiantaFightEnd(chara);
                    return;
                }

                //转世
                if (guaiwu != null && "转世灵兽".indexOf(guaiwu.get(0).str) != -1) {
                    chara.zhuan = chara.zhuan + 1;
                    String chenghao = "";
                    if (chara.zhuan == 1) {
                        chenghao = GameConfig.config.getBaseConfig().hao1;
                    } else if (chara.zhuan == 2) {
                        chenghao = GameConfig.config.getBaseConfig().hao2;
                    } else if (chara.zhuan == 3) {
                        chenghao = GameConfig.config.getBaseConfig().hao3;
                    } else if (chara.zhuan == 4) {
                        chenghao = GameConfig.config.getBaseConfig().hao4;
                    } else if (chara.zhuan == 5) {
                        chenghao = GameConfig.config.getBaseConfig().hao5;
                    } else if (chara.zhuan == 6) {
                        chenghao = GameConfig.config.getBaseConfig().hao6;
                    } else if (chara.zhuan == 7) {
                        chenghao = GameConfig.config.getBaseConfig().hao7;
                    } else if (chara.zhuan == 8) {
                        chenghao = GameConfig.config.getBaseConfig().hao8;
                    } else if (chara.zhuan == 9) {
                        chenghao = GameConfig.config.getBaseConfig().hao9;
                    }

                    GameUtil.sendMeTips(String.valueOf(chara.zhuan));
                    GameUtil.sendMeTips(chenghao);
                    chara.chenhao = chenghao;
                    chara.realLevel = 175;
                    chara.level = 175;


                    ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
                    Chengwei newChengwei = cs.getChengweiByName(chenghao);
                    //重新计算角色信息
                    //GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
                    //重新计算伤害
                    chara.chenghao.put(chenghao, chenghao);

                    GameUtil.chenghaoxiaoxi(chara, newChengwei.getName(), newChengwei.getName());
                    GameUtil.a65511(gameObjectChar);
                    GameUtil.zhuangbeiValue(gameObjectChar);

                    GameUtil.sendMeTips(chara.zhuan + "转成功!");
                    GameUtil.sendUpdate(chara);
                    return;
                }


                //战胜九天真君
                if (guaiwu != null && "钧天君变天君玄天君幽天君成天君朱天君赤天君阳天君昊天君".indexOf(guaiwu.get(0).str) != -1) {

                    if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
                        boolean flag = false;
                        for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                            GameActiveUtil.fightVictoryInfo(duiwu, guaiwu.get(0).str);
                            CMD_JIUTIAN_ZHENJUN.FightEnd(duiwu);
                            duiwu.totalCheckpoint+=1;
                            CMD_SELECT_MENU_ITEM.refreshTask(duiwu);
                        }
                    } else {
                        GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                        CMD_JIUTIAN_ZHENJUN.FightEnd(chara);
                        chara.totalCheckpoint+=1;
                        CMD_SELECT_MENU_ITEM.refreshTask(chara);
                    }
                    return;
                }


                //诛仙台孔宣
                if (guaiwu != null && "孔宣雷震子哪咤九天玄女李靖孙悟空杨戬王母娘娘玉皇大帝".indexOf(guaiwu.get(0).str) != -1) {
                  //  String[] mList = {"孔宣", "雷震子", "哪咤", "九天玄女", "李靖", "孙悟空", "杨戬", "王母娘娘", "玉皇大帝"};
//                    int t = 1;
//                    for (int i = 0; i < mList.length; ++i) {
//                        String data = mList[i];
//                        if (guaiwu.get(0).str.equals(data)) {
//                            t = i + 2;
//                            if (i == mList.length - 1) {
//                                //GameGongCheng.sendDiyu(GameLine.gameGongCheng, chara.getName());
//                            }
//                            break;
//                        }
//                    }
//                    if (chara.zhuxian_ceng <= t) {
                    //                       chara.zhuxian_ceng = t;
                    String chenghao = "";
                    if (chara.zhuxian_ceng  == 1) {
                        chenghao = GameConfig.config.getBaseConfig().zhu1;
                    } else if (chara.zhuxian_ceng  == 2) {
                        chenghao = GameConfig.config.getBaseConfig().zhu2;
                    } else if (chara.zhuxian_ceng  == 3) {
                        chenghao = GameConfig.config.getBaseConfig().zhu3;
                    } else if (chara.zhuxian_ceng  == 4) {
                        chenghao = GameConfig.config.getBaseConfig().zhu4;
                    } else if (chara.zhuxian_ceng  == 5) {
                        chenghao = GameConfig.config.getBaseConfig().zhu5;
                    } else if (chara.zhuxian_ceng  == 6) {
                        chenghao = GameConfig.config.getBaseConfig().zhu6;
                    } else if (chara.zhuxian_ceng  == 7) {
                        chenghao = GameConfig.config.getBaseConfig().zhu7;
                    } else if (chara.zhuxian_ceng  == 8) {
                        chenghao = GameConfig.config.getBaseConfig().zhu8;
                    } else if (chara.zhuxian_ceng  == 9) {
                        chenghao = GameConfig.config.getBaseConfig().zhu9;
                    }
                    ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
                    Chengwei newChengwei = cs.getChengweiByName(chenghao);
                    //重新计算角色信息
                    //GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
                    //重新计算伤害.
                    //log.info("当前战斗层数：" + (chara.zhuxian_ceng ));
                    int currentZhuxianCeng = chara.zhuxian_ceng;
                    if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
                        boolean flag = false;
                        for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                            //log.info("队员: "+duiwu.name+" 当前战斗层数：" + (duiwu.zhuxian_ceng));
                            //GameConfig.config.getBaseConfig().zhuxianCishu;
                            int zhuxianCishu = GameConfig.config.getBaseConfig().zhuxianCishu;
                            if( duiwu.zhuxian_cishu < zhuxianCishu){
                                if (currentZhuxianCeng == duiwu.zhuxian_ceng ) {
                                    GameActiveUtil.fightVictoryInfo(duiwu, guaiwu.get(0).str);
                                    duiwu.zhuxian_ceng +=1;
                                    duiwu.zhuxian_cishu = duiwu.zhuxian_cishu + 1;
                                    CMD_SELECT_MENU_ITEM.refreshTask(duiwu);
                                }else{
                                    GameCommonUtil.sendTips("当前挑战仙君与您任务不符，无法获得奖励！", duiwu.id);
                                }
                            }else{
                                GameCommonUtil.sendTips("诛仙奖励次数已满！", duiwu.id);
                            }
                            duiwu.chenghao.put(chenghao, chenghao);
                            GameUtil.chenghaoxiaoxi(duiwu, newChengwei.getName(), newChengwei.getName());
                            GameObjectChar session = GameObjectCharMng.getGameObjectChar(duiwu.id);
                            GameUtil.a65511(session);
                            GameUtil.zhuangbeiValue(session);
                            GameUtil.sendUpdate(duiwu);
                        }
                    } else {
                        chara.zhuxian_cishu = chara.zhuxian_cishu + 1;
                        CMD_SELECT_MENU_ITEM.refreshTask(chara);
                        GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                        chara.zhuxian_ceng +=1;
                        chara.chenghao.put(chenghao, chenghao);
                        GameUtil.chenghaoxiaoxi(chara, newChengwei.getName(), newChengwei.getName());
                        GameUtil.a65511(gameObjectChar);
                        GameUtil.zhuangbeiValue(gameObjectChar);
                        GameUtil.sendUpdate(chara);
                    }

                //}
                    // chara.diyu_ceng = chara.diyu_ceng + 1;
                    // GameMap gameMapname = GameLine.getGameMapname(chara.line, "第"+chara.diyu_ceng+"层地狱");
                    // chara.x = gameMapname.x;
                    // chara.y = gameMapname.y;
                    // gameMapname.join(gameObjectChar);
                    return;
                }

                //战胜十八层地狱
                if (guaiwu != null && "游魂厉鬼达摩雪妖风灵炎魔炼狱魔阴阳师魅灵狱獒范无赦牛头狱卒谢必安马面罗刹冥炎之灵梦姑谛听阎魔".indexOf(guaiwu.get(0).str) != -1) {
                    GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                    String[] mList = {"游魂", "厉鬼", "达摩", "雪妖", "风灵", "炎魔", "炼狱魔", "阴阳师", "魅灵", "狱獒", "范无赦", "牛头狱卒", "谢必安", "马面罗刹", "冥炎之灵", "梦姑", "谛听", "阎魔"};
                    int t = 1;

                    for (int i = 0; i < mList.length; ++i) {
                        String data = mList[i];
                        if (guaiwu.get(0).str.equals(data)) {
                            t = i + 2;

                            if (i == mList.length - 1) {
                                //GameGongCheng.sendDiyu(GameLine.gameGongCheng, chara.getName());
                            }

                            break;
                        }
                    }

                    if (chara.diyu_ceng <= t) {
                        chara.diyu_ceng = t;
                    }
                    // chara.diyu_ceng = chara.diyu_ceng + 1;
                    // GameMap gameMapname = GameLine.getGameMapname(chara.line, "第"+chara.diyu_ceng+"层地狱");
                    // chara.x = gameMapname.x;
                    // chara.y = gameMapname.y;
                    // gameMapname.join(gameObjectChar);
                    return;
                }

                //地狱深渊
                if (guaiwu != null && "1层炼狱恶魔2层炼狱恶魔3层炼狱恶魔4层炼狱恶魔5层炼狱恶魔6层炼狱恶魔7层炼狱恶魔8层炼狱恶魔9层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                    if ("1层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 2;
                    }
                    if ("2层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 3;
                    }
                    if ("3层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 4;
                    }
                    if ("4层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 5;
                    }
                    if ("5层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 6;
                    }
                    if ("6层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 7;
                    }
                    if ("7层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 8;
                    }
                    if ("8层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 9;
                    }
                    if ("9层炼狱恶魔".indexOf(guaiwu.get(0).str) != -1) {
                        chara.cengshu = 9;
                    }
                    //CMD_GHOSTDOM_CHALLENGE_INFO.FightEnd(chara);
                    if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
                        boolean flag = false;
                        for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                            GameActiveUtil.fightVictoryInfo(duiwu, guaiwu.get(0).str);
                            duiwu.diyushenyuanNum += 1;
                            CMD_SELECT_MENU_ITEM.refreshTask(duiwu);
                        }
                    } else {
                        GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                        chara.diyushenyuanNum += 1;
                        CMD_SELECT_MENU_ITEM.refreshTask(chara);
                    }
                    return;
                }

                //魔龙
                if (guaiwu != null && "魔龙之尾魔龙之爪魔龙之首魔龙吞天魔龙吞天·怒".indexOf(guaiwu.get(0).str) != -1) {


                    if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
                        boolean flag = false;
                        for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                            GameActiveUtil.fightVictoryInfo(duiwu, guaiwu.get(0).str);
                            duiwu.molongCount+=1;
                            CMD_SELECT_MENU_ITEM.refreshTask(duiwu);
                            if ("魔龙之爪".indexOf(guaiwu.get(0).str) != -1) {
                                if (duiwu.molongIndex <= 1) {
                                    duiwu.molongIndex = 2;
                                }
                            } else if ("魔龙之尾".indexOf(guaiwu.get(0).str) != -1) {
                                if (duiwu.molongIndex <= 0) {
                                    duiwu.molongIndex = 1;
                                }
                            } else if ("魔龙之首".indexOf(guaiwu.get(0).str) != -1) {
                                if (duiwu.molongIndex <= 2) {
                                    duiwu.molongIndex = 3;
                                }
                            } else if ("魔龙吞天·怒".indexOf(guaiwu.get(0).str) != -1) {
                                if (duiwu.molongIndex <= 3) {
                                    duiwu.molongIndex = 3;
                                }
                            }
                        }
                    } else {
                        GameActiveUtil.fightVictoryInfo(chara, guaiwu.get(0).str);
                        chara.molongCount+=1;
                        CMD_SELECT_MENU_ITEM.refreshTask(chara);
                        if ("魔龙之爪".indexOf(guaiwu.get(0).str) != -1) {
                            if (chara.molongIndex <= 1) {
                                chara.molongIndex = 2;
                            }
                        } else if ("魔龙之尾".indexOf(guaiwu.get(0).str) != -1) {
                            if (chara.molongIndex <= 0) {
                                chara.molongIndex = 1;
                            }
                        } else if ("魔龙之首".indexOf(guaiwu.get(0).str) != -1) {
                            if (chara.molongIndex <= 2) {
                                chara.molongIndex = 3;
                            }
                        } else if ("魔龙吞天·怒".indexOf(guaiwu.get(0).str) != -1) {
                            if (chara.molongIndex <= 3) {
                                chara.molongIndex = 3;
                            }
                        }
                    }
                    return;
                }

                // 挑战了正道殿角色胜利后
                if (guaiwu != null
                        && (guaiwu.get(0).str.indexOf("证道殿") != -1 || guaiwu.get(0).str.indexOf("新晋护法") != -1)) {
                    GameUtil.zhengdaodian(chara, fightContainer);
                    return;
                }

                // 挑战了英雄会角色胜利后
                if (guaiwu != null && (guaiwu.get(0).str.indexOf("英雄会评议员") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[0] + "-") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[1] + "-") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[2] + "-") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[3] + "-") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[4] + "-") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[5] + "-") != -1
                        || guaiwu.get(0).str.indexOf(HeroPubService.titles[6] + "-") != -1)) {
                    GameUtil.yingxiong(chara, fightContainer);
                }

                // 打败了地图守护神之后
                if (guaiwu != null && (guaiwu.get(0).str.indexOf("守护神")) != -1) {
                    String chenhao = guaiwu.get(0).str;
                    GameUtil.mapguard(chara, fightContainer, chenhao);
                    return;
                }

                // 挑战原始掌门之后
                if (guaiwu != null && "金系掌门木系掌门水系掌门火系掌门土系掌门".indexOf(guaiwu.get(0).str) != -1) {
                    GameUtil.zhangmen(chara, fightContainer);
                    GameConfig.canzhanBoos.remove("挑战掌门[" + guaiwu.get(0).str + "]_" + guaiwu.get(0).id);
                }

                // 挑战其他角色做掌门的时候
                if (guaiwu != null) {
                    String subUtil = GameUtil.getSubUtil(guaiwu.get(0).str, "(.*?)掌门-");
                    if (subUtil.length() != 0 && "金系木系水系火系土系".indexOf(subUtil) != -1) {
                        GameUtil.zhangmen(chara, fightContainer);
                        GameConfig.canzhanBoos.remove("挑战掌门[" + guaiwu.get(0).str + "]_" + guaiwu.get(0).id);
                    }
                }

                // 在瑶池打瑶池仙子
                if (chara.mapid == 15002 && guaiwu != null && "瑶池仙子".equals(guaiwu.get(0).str)) {
                    GameUtil.jiBaiYaoChiXianZi(chara);
                }
                // 在瑶池打守职天兵
                if (chara.mapid == 15002 && guaiwu != null && "守值天兵".equals(guaiwu.get(0).str)) {
                    GameUtil.jiBaiShouZhiTianBing(chara);
                }
                // 八仙
                if (chara.mapid == 16003 && guaiwu != null && "穿山甲".equals(guaiwu.get(0).str)) {
                    GameUtil.jiBaiChuanShanJia(chara);
                }

                // 主线任务
                if (chara.mapid == 2000 && guaiwu != null && chara.current_task.equals("主线—浮生若梦_s8")) {
                    Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
                    task.task_state = "1";
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "功夫不负有心人，可算是找到了。", "主线—浮生若梦");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                if (chara.current_task.equals("主线—浮生若梦_s12") && chara.mapid == 4000 && guaiwu.get(0).str.equals("强盗")
                        && "新手强盗".equals(chara.zhandouInfo)) {
                    GameUtil.renwujiangli(chara);
                    Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
                    task.task_state = "4";
                    task.task_extra_para = "1";
                    Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                            GameData.that.baseNpcDialogueService.findById(292));
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }
                // 击杀强盗
                if (chara.current_task.equals("主线—浮生若梦_s16") && chara.mapid == 3000 && guaiwu.get(0).str.equals("强盗")
                        && "新手强盗".equals(chara.zhandouInfo)) {
                    Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
                    task.task_state = "5";
                    task.task_extra_para = "1";
                    chara.current_task = "主线—浮生若梦_s16";
                    Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                            GameData.that.baseNpcDialogueService.findById(315));
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                // 巡逻的时候战胜了野怪，在这里给巡逻奖励
                if (guaiwu != null) {
                    // 组队巡逻
                    if (gameObjectChar.gameTeam != null && team != null) {
                        for (int i = 0; i < team.size(); ++i) {
                            GameUtil.shuayeguai(chara, team.get(i), guaiwu.get(0).guaiwulevel);
                            GameCommonUtil.shenMuDingFightCard(gameObjectChar, guaiwu);
                        }
                    } else {
                        GameUtil.shuayeguai(chara, chara, guaiwu.get(0).guaiwulevel);
                        GameCommonUtil.shenMuDingFightCard(gameObjectChar, guaiwu);
                    }
                }
            } else if (guaiwu.get(0).type == 9) {

            }
            // 玩家在任何地方被打败，弹出战败信息
            if (isDead) {
                // 宠物飞升挑战失败
                if (null != guaiwu && PetFlyMgr.isPetFeiSheng(((FightObject) guaiwu.get(0)).str)) {
                    GameCommonUtil.addCharaTrail(chara, "死亡", "1", "宠物飞升");
                    return;
                } else if (chara.mapName.indexOf("通天塔") != -1) {
                    // 通天塔死亡
                    if (guaiwu.get(0).str.indexOf("星君") != -1) {
                        GameActiveUtil.tongtiantaFightFail();
                        GameCommonUtil.addCharaTrail(chara, "死亡", "1", "通天塔");
                        return;
                    }
                } else {
                    if (gameObjectChar.flag.equals("jieyu_qiuqing") && "jieyu_qiuqing".equals(guaiwu.get(0).uid)) {
                        gameObjectChar.flag = "";
                        if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                            for (Chara duiyuan : team) {
                                GameActiveUtil.fightDieInfo(duiyuan, "劫狱求情");
                            }
                        } else {
                            GameActiveUtil.fightDieInfo(chara, "劫狱求情");
                        }
                        return;
                    }
                    // 死亡惩罚
                    VictoryDieReward vdr = GameData.that.victoryDieRewardService.victoryOrDieInfo(guaiwu.get(0).str, 0);
                    if (vdr != null) {
                        if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
                            for (Chara duiyuan : team) {
                                GameActiveUtil.fightDieInfo(duiyuan, guaiwu.get(0).str);
                            }
                        } else {
                            GameActiveUtil.fightDieInfo(chara, guaiwu.get(0).str);
                        }
                    } else {
                        // 死亡记录
                        GameCommonUtil.addCharaTrail(chara, "死亡", "1", guaiwu.get(0).str);
                    }
                }
            }
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    // 获取战斗容器中的怪物
    private static List<FightObject> guaiwu(FightContainer fightContainer) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            if (fightTeam.type == 2) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                return fightObjectList;
            }
        }
        return null;
    }

    /**
     * 根据战斗容器，获取当前游戏对象
     *
     * @param fightContainer
     * @return
     */
    private static GameObjectChar getGameObjectChar(FightContainer fightContainer) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            if (fightTeam.type == 1) {
                List<FightObject> fightObjectList = fightTeam.fightObjectList;
                for (FightObject fightObject : fightObjectList) {
                    if (fightObject.leader == 1) {
                        int fid = fightObject.fid;
                        GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fid);
                        if (gameObjectChar != null) {
                            return gameObjectChar;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 两个角色进行PK,试道场也才采用这个切磋
     *
     * @param chara        发起人
     * @param charaduishou 对手
     */
    public static void goFight(Chara chara, Chara charaduishou) {
        FightContainer fightContainer = getFightContainer(chara.id);
        if (fightContainer != null) {
            FightManager.listFight.remove(fightContainer);
        }
        fightContainer = getFightContainer(charaduishou.id);
        if (fightContainer != null) {
            FightManager.listFight.remove(fightContainer);
        }

        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        // 如果pk发起者处于组队状态（包括单人队长状态和多人组队状态），就团队参战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                fightObject.durability = 1;
                if (i == 0) {
                    fightObject.leader = 1;
                }
                addFabao(fc, duiwu.get(i), fightObject);
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        Petbeibao petbeibao = pets.get(j);
                        fightObject = new FightObject(pets.get(j), duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = pets.get(j).id;
                        fightObject.id = pets.get(j).id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                // 设置状态
                duiwu.get(i).setFight(true);
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }

        // 如果pk发起者处于单人状态
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id;
            fightObject2.leader = 1;
            fightObject2.id = chara.id;
            fightObject2.durability = 1;
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2);
            List<Petbeibao> pets2 = chara.pets;

            for (int k = 0; k < pets2.size(); ++k) {
                if (pets2.get(k).id == chara.chongwuchanzhanId) {
                    Petbeibao petbeibao2 = pets2.get(k);
                    fightObject2 = new FightObject(pets2.get(k), chara);
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = pets2.get(k).id;
                    fightObject2.id = pets2.get(k).id;
                    fightObject2.cid = chara.id;
                    if (petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(pets2.get(k), fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num;
            // 设置状态
            chara.setFight(true);
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }

        FightTeam ftother = new FightTeam();
        ftother.type = 1;
        GameObjectChar session2 = GameObjectCharMng.getGameObjectChar(charaduishou.id);
        int num2 = 0;

        if (GameCommonUtil.isNotGameTeam(session2.gameTeam, session2.chara)) {
            List<Chara> duiwu = session2.gameTeam.duiwu.stream().distinct().collect(Collectors.toList());
            for (int l = 0; l < duiwu.size(); ++l) {
                FightObject fightObject3 = new FightObject(duiwu.get(l));
                fightObject3.pos = FightManager.PERSON_POS.get(num2);
                fightObject3.fid = duiwu.get(l).id;
                fightObject3.id = duiwu.get(l).id;
                fightObject3.durability = 1;
                if (l == 0) {
                    fightObject3.leader = 1;
                }
                addFabao(fc, duiwu.get(l), fightObject3);
                ftother.add(fightObject3);
                List<Petbeibao> pets3 = duiwu.get(l).pets;
                for (int m = 0; m < pets3.size(); ++m) {
                    if (pets3.get(m).id == duiwu.get(l).chongwuchanzhanId) {
                        Petbeibao petbeibao3 = pets3.get(m);
                        fightObject3 = new FightObject(pets3.get(m), duiwu.get(l));
                        fightObject3.pos = FightManager.PERSON_POS.get(num2) + 5;
                        fightObject3.fid = pets3.get(m).id;
                        fightObject3.id = pets3.get(m).id;
                        fightObject3.cid = duiwu.get(l).id;
                        if (petbeibao3.tianshu.size() != 0) {
                            addFightTianShu(petbeibao3, fightObject3, fc);
                        }
                        ftother.add(fightObject3);
                        break;
                    }
                }
                ++num2;
                // 设置状态
                duiwu.get(l).setFight(true);
            }
        }

        // 如果被pk者处于独立个体状态
        else {
            FightObject fightObject = new FightObject(charaduishou);
            fightObject.pos = FightManager.PERSON_POS.get(num2);
            fightObject.fid = charaduishou.id;
            fightObject.leader = 1;
            fightObject.id = charaduishou.id;
            fightObject.durability = 1;
            addFabao(fc, charaduishou, fightObject);
            ftother.add(fightObject);
            List<Petbeibao> pets = charaduishou.pets;
            for (int j = 0; j < pets.size(); ++j) {
                if (pets.get(j).id == charaduishou.chongwuchanzhanId) {
                    Petbeibao petbeibao = pets.get(j);
                    fightObject = new FightObject(petbeibao, charaduishou);
                    fightObject.pos = FightManager.PERSON_POS.get(num2) + 5;
                    fightObject.fid = pets.get(j).id;
                    fightObject.id = pets.get(j).id;
                    fightObject.cid = charaduishou.id;
                    fightObject.special_icon = charaduishou.special_icon;
                    if (petbeibao.tianshu.size() != 0) {
                        addFightTianShu(petbeibao, fightObject, fc);
                    }
                    ftother.add(fightObject);
                    break;
                }
            }
            ++num2;
            charaduishou.setFight(true);
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
        fc.teamList.add(ft);
        fc.teamList.add(ftother);
        FightManager.listFight.add(fc);

        List<FightObject> fightObjectListAll = getAllFightObject(fc);
        Iterator<FightObject> iterator2 = fightObjectListAll.iterator();
        while (iterator2.hasNext()) {
            FightObject fightObject = iterator2.next();
            // 如果是角色
            if (fightObject.type == 1) {
                GameObjectChar session3 = GameObjectCharMng.getGameObjectChar(fightObject.id);
                Chara tchar = session3.chara;
                if (chara.autofight_select != 0) {
                    Vo_32985_0 vo_32985_0 = new Vo_32985_0();
                    vo_32985_0.user_is_multi = 0;
                    vo_32985_0.user_round = tchar.autofight_select;
                    vo_32985_0.user_action = tchar.autofight_skillaction;
                    vo_32985_0.user_next_action = tchar.autofight_skillaction;
                    vo_32985_0.user_para = tchar.autofight_skillno;
                    vo_32985_0.user_next_para = tchar.autofight_skillno;
                    vo_32985_0.pet_is_multi = 0;
                    vo_32985_0.pet_round = 0;
                    vo_32985_0.pet_action = 0;
                    vo_32985_0.pet_next_action = 0;
                    vo_32985_0.pet_para = 0;
                    vo_32985_0.pet_next_para = 0;
                    GameObjectChar.send(new M32985_0(), vo_32985_0, fightObject.id);
                }
            }
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);

        Iterator<FightObject> iterator3 = fightObjectListAll.iterator();
        while (iterator3.hasNext()) {
            FightObject fightObject3 = iterator3.next();
            if (fightObject3.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject3.id;
                vo_64971_0.haveCalled = 1;
                GameObjectChar.send(new MSG_C_REFRESH_PET_LIST(), vo_64971_0, fightObject3.cid);
            }
        }
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        // 通知队友
        List<FightObject> fightObjectList = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject4 : fightObjectList) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject4.fid;
            vo_65019_0.leader = fightObject4.leader;
            vo_65019_0.weapon_icon = fightObject4.weapon_icon;
            vo_65019_0.pos = fightObject4.pos;
            vo_65019_0.rank = fightObject4.rank;
            vo_65019_0.vip_type = fightObject4.vipType;
            vo_65019_0.str = fightObject4.str;
            vo_65019_0.type = fightObject4.org_icon;
            vo_65019_0.durability = fightObject4.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject4.upgrade_level;
            vo_65019_0.upgrade_type = fightObject4.upgrade_type;
            vo_65019_0.dex = fightObject4.max_mofa;
            vo_65019_0.max_mana = fightObject4.max_mofa;
            vo_65019_0.max_life = fightObject4.max_shengming;
            vo_65019_0.def = fightObject4.max_shengming;
            vo_65019_0.org_icon = fightObject4.org_icon;
            vo_65019_0.suit_icon = fightObject4.suit_icon;
            vo_65019_0.suit_light_effect = fightObject4.suit_light_effect;
            // 如果是角色的话
            if (fightObject4.type == 1) {
                vo_65019_0.special_icon = fightObject4.special_icon;
            } else if (fightObject4.type == 2) {
                vo_65019_0.special_icon = fightObject4.petCustomIcon;
            }
            vo_65019_0.portrait = fightObject4.org_icon;
            vo_65019_0.customIcon = fightObject4.customIcon;
            vo_65019_0.zhenlingLevel = fightObject4.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject4.zhenlingType;
            list65019.add(vo_65019_0);
        }

        // 通知对手
        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectListOther = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectListOther) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject5.fid;
            vo_65017_0.leader = fightObject5.leader;
            vo_65017_0.weapon_icon = fightObject5.weapon_icon;
            vo_65017_0.pos = fightObject5.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject5.vipType;
            vo_65017_0.str = fightObject5.str;
            vo_65017_0.type = fightObject5.org_icon;
            vo_65017_0.durability = fightObject5.durability;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject5.upgrade_level;
            vo_65017_0.upgrade_type = fightObject5.upgrade_type;
            vo_65017_0.dex = fightObject5.max_mofa;
            vo_65017_0.max_mana = fightObject5.max_mofa;
            vo_65017_0.max_life = fightObject5.max_shengming;
            vo_65017_0.def = fightObject5.max_shengming;
            vo_65017_0.org_icon = fightObject5.org_icon;
            vo_65017_0.suit_icon = fightObject5.suit_icon;
            vo_65017_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65017_0.portrait = fightObject5.org_icon;
            // 如果是角色的话
            if (fightObject5.type == 1) {
                vo_65017_0.special_icon = fightObject5.special_icon;
            } else if (fightObject5.type == 2) {
                vo_65017_0.special_icon = fightObject5.petCustomIcon;
            }
            vo_65017_0.customIcon = fightObject5.customIcon;
            vo_65017_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject5.zhenlingType;
            list65020.add(vo_65017_0);
        }
        GameCommonUtil.setCharaTitleFlag(charaduishou);
        sendTeam(fc, fightObjectList, new MSG_C_FRIENDS(), list65019);
        sendTeam(fc, fightObjectList, new MSG_C_OPPONENTS(), list65020);
        sendTeam(fc, fightObjectListOther, new MSG_C_FRIENDS(), list65020);
        sendTeam(fc, fightObjectListOther, new MSG_C_OPPONENTS(), list65019);
        fightObjectList = getAllFightObject(fc);
        // 天书
        getRandomGodbookEffect(fightObjectList, fc);
        round(fc);
    }

    // 这里是刷星的战斗逻辑
    public static void goFightTianDiXing(Chara chara, List<String> monsterList, Vo_APPEAR vo_65529_0) {
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        FightContainer fc = new FightContainer();
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                if (i == 0) {
                    fightObject.leader = 1;
                }
                addFabao(fc, duiwu.get(i), fightObject);
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(pets.get(j), duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = pets.get(j).id;
                        fightObject.id = pets.get(j).id;
                        fightObject.cid = duiwu.get(i).id;
                        if (pets.get(j).tianshu.size() != 0) {
                            addFightTianShu(pets.get(j), fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
                ++num;
            }
        } else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id;
            fightObject2.leader = 1;
            fightObject2.id = chara.id;
            ft.add(fightObject2);
            addFabao(fc, chara, fightObject2);
            List<Petbeibao> pets2 = chara.pets;
            for (int k = 0; k < pets2.size(); ++k) {
                if (pets2.get(k).id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(pets2.get(k), chara);
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = pets2.get(k).id;
                    fightObject2.id = pets2.get(k).id;
                    fightObject2.cid = chara.id;
                    if (pets2.get(k).tianshu.size() != 0) {
                        addFightTianShu(pets2.get(k), fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
            ++num;
        }
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        // 添加敌方boss
        for (String monsterName : monsterList) {
            FightObject fightObject3 = new FightObject(chara, monsterName, vo_65529_0);
            fightObject3.pos = FightManager.MONSTER_POS.get(monsterIndex);
            fightObject3.fid = fc.id++;
            fightObject3.bossid = vo_65529_0.id;
            if (monsterIndex == 1) {
                fightObject3.leader = 1;
            }
            monsterTeam.add(fightObject3);
            ++monsterIndex;
        }
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);
        FightManager.listFight.add(fc);
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select;
            vo_32985_0.user_action = chara.autofight_skillaction;
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno;
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.portrait = fightObject5.org_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        send(fc, new MSG_C_FRIENDS(), list65019);
        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = fightObject6.weapon_icon;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.portrait = fightObject6.org_icon;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        send(fc, new MSG_C_OPPONENTS(), list65020);
        fightObjectList2 = getAllFightObject(fc);
        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);
        round(fc);
    }

    public static FightObject getRandomObject(FightContainer fightContainer, List<FightObject> exclude) {
        List<FightObject> allFightObject = getAllFightObject(fightContainer);
        for (FightObject fightObject : allFightObject) {
            if (!fightObject.isDead() && !exclude.contains(fightObject)) {
                return fightObject;
            }
        }
        return null;
    }

    public static void remove(FightContainer fightContainer, FightObject fightObject) {
        List<FightTeam> teamList = fightContainer.teamList;
        for (FightTeam fightTeam : teamList) {
            Iterator<FightObject> iterator = fightTeam.fightObjectList.iterator();
            while (iterator.hasNext()) {
                FightObject next = iterator.next();
                if (next.fid == fightObject.fid) {
                    iterator.remove();
                    break;
                }
            }
        }
        Iterator<FightObject> iterator2 = fightContainer.doActionList.iterator();
        while (iterator2.hasNext()) {
            FightObject next2 = iterator2.next();
            if (next2.fid == fightObject.fid) {
                iterator2.remove();
                break;
            }
        }
    }

    public static void reconnect(Chara chara) {
        int id = chara.id;
        GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
        FightContainer fc = getFightContainer(id);
        if (fc == null) {
            return;
        }
        List<FightObject> fightTeam = getFightTeam(fc, id).fightObjectList;
        List<FightObject> fightTeamDM = getFightTeamDM(fc, id).fightObjectList;
        List<Vo_ADD_FRIEND_OPPONENT> friends = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        for (FightObject fightObject : fightTeam) {
            if (fightObject.isDead()) {
                if (fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
                    continue;
                }
            }
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject.fid;
            vo_65019_0.leader = fightObject.leader;
            vo_65019_0.weapon_icon = fightObject.weapon_icon;
            vo_65019_0.pos = fightObject.pos;
            vo_65019_0.rank = fightObject.rank;
            vo_65019_0.vip_type = fightObject.vipType;
            vo_65019_0.str = fightObject.str;
            vo_65019_0.type = fightObject.org_icon;
            vo_65019_0.durability = fightObject.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject.upgrade_level;
            vo_65019_0.upgrade_type = fightObject.upgrade_type;
            vo_65019_0.dex = fightObject.max_mofa;
            vo_65019_0.max_mana = fightObject.max_mofa;
            vo_65019_0.max_life = fightObject.max_shengming;
            vo_65019_0.def = fightObject.shengming;
            vo_65019_0.org_icon = fightObject.org_icon;
            vo_65019_0.suit_icon = fightObject.suit_icon;
            vo_65019_0.suit_light_effect = fightObject.suit_light_effect;
            vo_65019_0.special_icon = fightObject.special_icon;
            vo_65019_0.customIcon = fightObject.customIcon;
            vo_65019_0.zhenlingLevel = fightObject.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject.zhenlingType;
            friends.add(vo_65019_0);
        }

        List<Vo_ADD_FRIEND_OPPONENT> opponents = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        for (FightObject fightObject : fightTeamDM) {
            if (fightObject.isDead()) {
                if (fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
                    continue;
                }
            }
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject.fid;
            vo_65017_0.leader = fightObject.leader;
            vo_65017_0.weapon_icon = fightObject.weapon_icon;
            vo_65017_0.pos = fightObject.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject.vipType;
            vo_65017_0.str = fightObject.str;
            vo_65017_0.type = fightObject.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject.upgrade_level;
            vo_65017_0.upgrade_type = fightObject.upgrade_type;
            vo_65017_0.dex = fightObject.mofa;
            vo_65017_0.max_mana = fightObject.max_mofa;
            vo_65017_0.max_life = fightObject.max_shengming;
            vo_65017_0.def = fightObject.shengming;
            vo_65017_0.org_icon = fightObject.org_icon;
            vo_65017_0.suit_icon = fightObject.suit_icon;
            vo_65017_0.suit_light_effect = fightObject.suit_light_effect;
            vo_65017_0.special_icon = fightObject.special_icon;
            vo_65017_0.customIcon = fightObject.customIcon;
            vo_65017_0.zhenlingLevel = fightObject.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject.zhenlingType;
            opponents.add(vo_65017_0);
        }
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        gameObjectChar.sendOne(new MSG_C_START_COMBAT(), vo_3583_0);

        // 发送怪物信息
        gameObjectChar.sendOne(new MSG_C_FRIENDS(), friends);
        gameObjectChar.sendOne(new MSG_C_OPPONENTS(), opponents);

        // 友方状态
        for (FightObject fightObject : fightTeam) {
            // 如果死亡了
            if (fightObject.isDead()) {
                // 除了type为1,其余死亡之后都要消失
                if (fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
                    ArrayList<Integer> objects = new ArrayList<Integer>();
                    objects.add(fightObject.fid);
                    objects.add(0);
                    gameObjectChar.sendOne(new M64981_Fight_Blood(), objects);

                    Vo_7653_0 vo_7653_0 = new Vo_7653_0();
                    vo_7653_0.id = fightObject.fid;
                    gameObjectChar.sendOne(new M7653_0(), vo_7653_0);
                } else {
                    ArrayList<Integer> objects = new ArrayList<Integer>();
                    objects.add(fightObject.fid);
                    objects.add(0);
                    gameObjectChar.sendOne(new M64981_Fight_Blood(), objects);

                    Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
                    vo_19959_0.round = fc.round;
                    vo_19959_0.aid = fightObject.fid;
                    vo_19959_0.action = 40;
                    vo_19959_0.vid = 0;
                    vo_19959_0.para = 0;
                    gameObjectChar.sendOne(new MSG_C_ACTION(), vo_19959_0);

                    Vo_C_CHAR_DIED vo_7669_0 = new Vo_C_CHAR_DIED();
                    vo_7669_0.id = fightObject.fid;
                    vo_7669_0.damage_type = 40;
                    gameObjectChar.sendOne(new MSG_C_CHAR_DIED(), vo_7669_0);
                    Vo_C_END_ACTION vo_7655_0 = new Vo_C_END_ACTION();
                    vo_7655_0.id = fightObject.fid;
                    gameObjectChar.sendOne(new MSG_C_END_ACTION(), vo_7655_0);
                }
            }
        }
        // 敌方状态
        for (FightObject fightObject : fightTeamDM) {
            // 如果死亡了
            if (fightObject.isDead()) {
                // 除了type为1,其余死亡之后都要消失
                if (fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
                    ArrayList<Integer> objects = new ArrayList<Integer>();
                    objects.add(fightObject.fid);
                    objects.add(0);
                    gameObjectChar.sendOne(new M64981_Fight_Blood(), objects);

                    Vo_7653_0 vo_7653_0 = new Vo_7653_0();
                    vo_7653_0.id = fightObject.fid;
                    gameObjectChar.sendOne(new M7653_0(), vo_7653_0);
                } else {
                    ArrayList<Integer> objects = new ArrayList<Integer>();
                    objects.add(fightObject.fid);
                    objects.add(0);
                    gameObjectChar.sendOne(new M64981_Fight_Blood(), objects);

                    Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
                    vo_19959_0.round = fc.round;
                    vo_19959_0.aid = fightObject.fid;
                    vo_19959_0.action = 40;
                    vo_19959_0.vid = 0;
                    vo_19959_0.para = 0;
                    gameObjectChar.sendOne(new MSG_C_ACTION(), vo_19959_0);

                    Vo_C_CHAR_DIED vo_7669_0 = new Vo_C_CHAR_DIED();
                    vo_7669_0.id = fightObject.fid;
                    vo_7669_0.damage_type = 40;
                    gameObjectChar.sendOne(new MSG_C_CHAR_DIED(), vo_7669_0);
                    Vo_C_END_ACTION vo_7655_0 = new Vo_C_END_ACTION();
                    vo_7655_0.id = fightObject.fid;
                    gameObjectChar.sendOne(new MSG_C_END_ACTION(), vo_7655_0);
                }
            }
        }
        // 设置宠物
        FightObject fightObjectPet = getFightObjectPet(fc, getFightObject(chara.id));
        if (fightObjectPet != null) {
            Vo_4163_0 vo_4163_0 = new Vo_4163_0();
            vo_4163_0.id = fightObjectPet.fid;
            vo_4163_0.b = 1;
            gameObjectChar.sendOne(new M4163_0(), vo_4163_0);
            if (fightObjectPet.isDead()) {
                gameObjectChar.sendOne(new MSG_C_SET_FIGHT_PET(), new Vo_64971_0(fightObjectPet.fid, 2));
            }
        }
        for (FightObject fightObject : fightTeam) {
            Vo_11757_0 friendStatus = new Vo_11757_0();
            friendStatus.id = fightObject.fid;
            List<Integer> buffState = fightObject.getBuffState();
            int value = 0;
            for (Integer i : buffState) {
                value += i;
            }
            friendStatus.list.add(value);
            friendStatus.list.add(32);
            // 队友状态
            gameObjectChar.sendOne(new M11757_0(), friendStatus);
        }
        for (FightObject fightObject2 : fightTeamDM) {
            Vo_11757_0 friendStatus = new Vo_11757_0();
            friendStatus.id = fightObject2.fid;
            List<Integer> buffState = fightObject2.getBuffState();
            int value = 0;
            for (Integer i : buffState) {
                value += i;
            }
            friendStatus.list.add(value);
            friendStatus.list.add(32);
            // 队友状态
            gameObjectChar.sendOne(new MSG_LC_UPDATE_STATUS(), friendStatus);
        }
        // 友方
        for (FightObject fightObject3 : fightTeam) {
            if (fightObject3.godbook != 0) {
                Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
                vo_12025_0.id = fightObject3.fid;
                vo_12025_0.effect_no = fightObject3.godbook;
                gameObjectChar.sendOne(new MSG_GODBOOK_EFFECT_NORMAL(), vo_12025_0);
            }
        }
        // 敌方
        for (FightObject fightObject3 : fightTeamDM) {
            if (fightObject3.godbook != 0) {
                Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
                vo_12025_0.id = fightObject3.fid;
                vo_12025_0.effect_no = fightObject3.godbook;
                gameObjectChar.sendOne(new MSG_GODBOOK_EFFECT_NORMAL(), vo_12025_0);
            }
        }
        if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
            //队伍就它一个人那就直接下一个回合
            if (gameObjectChar.gameTeam.duiwu.size() == 1) {
                fc.round += 1;
                fc.roundTime = System.currentTimeMillis();
                fc.state.set(1);
                FightManager.nextRound(fc);
                return;
            } else {
                //队伍人数大于1
                GameData.that.redisUtils.set("fightCallFail_" + fc.uid, "战斗重连", gameObjectChar.gameTeam.duiwu.size() * 10);
            }
        } else {
            //没组队的话也是一样
            fc.round += 1;
            fc.roundTime = System.currentTimeMillis();
            fc.state.set(1);
            FightManager.nextRound(fc);
            return;
        }

    }

    /**
     * 一般活动战斗
     *
     * @param chara
     * @param monsterList
     * @param isShouhu
     * @param npcId
     */
    public static void activeBoosGoFight(Chara chara, List<String> monsterList, boolean isShouhu, int... npcId) {
        // 如果全局战斗容器中已经有当前这个战斗容器了，则移除
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        // 新建战斗容器
        FightContainer fc = new FightContainer();
        // 建立角色方战斗队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        // 设置战斗对象
        if (npcId != null && npcId.length > 0) {
            chara.zhandouId = npcId[0];
            GameCore.fightObject.put(npcId[0], npcId[0]);
        }
        // 如果是团队作战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                addFabao(fc, duiwu.get(i), fightObject);
                if (i == 0) {
                    fightObject.leader = 1;
                }
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    Petbeibao petbeibao = pets.get(j);
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(petbeibao, duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = petbeibao.id;
                        fightObject.id = petbeibao.id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }
        // 如果是单人作战
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id; // 角色的战斗id就是角色id
            fightObject2.leader = 1; // 是战斗队长
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            // 将角色的法宝添加到战斗中
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2); // 角色加入到友方队伍
            List<Petbeibao> pets2 = chara.pets;
            // 将参战的宠物ID加入到友方队伍中
            for (int k = 0; k < pets2.size(); ++k) {
                Petbeibao petbeibao2 = pets2.get(k);
                if (petbeibao2.id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(petbeibao2, chara);
                    // 让宠物站在角色的正前方
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = petbeibao2.id; // 宠物的战斗id是其本身id
                    fightObject2.id = petbeibao2.id;
                    fightObject2.cid = chara.id;
                    fightObject2.shape = petbeibao2.petShuXing.get(0).shape;
                    fightObject2.petType = petbeibao2.petShuXing.get(0).penetrate;
                    // 如果宠物有天书
                    if (petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(petbeibao2, fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num; // 一个角色及其宠物的战斗已经初始化完成
            // 设置单人作战状态
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
        // 为true允许守护出战
        if (isShouhu) {
            // 添加守护
            for (int i = 0; i < chara.listshouhu.size() && num < 5; ++i) {
                if (chara.listshouhu.get(i).listShouHuShuXing.get(0).nil != 0) {
                    FightObject fightObject = new FightObject(chara.listshouhu.get(i));
                    fightObject.pos = FightManager.PERSON_POS.get(num);
                    fightObject.fid = fc.id++; // 守护的id是fc的id递增加1
                    ft.add(fightObject); // 将守护加入到战斗队伍中
                    ++num; // 新增到下一个对象
                }
            }
        }
        // 初始化怪物方的队伍， 队伍类型为2
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        int lv = 0;
        try{
            String guaiwuName = monsterList.get(0);
            if(guaiwuName.contains("鬼差")){
                lv = Integer.parseInt(guaiwuName.split("#")[1]);
            }
        }catch(Exception e){
            log.info("鬼差出错，{}",e);
        }
        for (String monsterName : monsterList) {
            if(monsterName.contains("鬼差")){
                monsterName = monsterName.split("#")[0];
            }
            List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByName(monsterName);

            // 如果地方为空,直接结束战斗
            if (fightObjectInfos != null && !fightObjectInfos.isEmpty()) {
                int randomIndex = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                FightObject fightObject3 = new FightObject(fightObjectInfos.get(randomIndex), true,
                        new Random().nextInt(5) + 1);
                String type = fightObjectInfos.get(0).getType();
                if(monsterName.contains("鬼差") || type.contains("鬼差")){
                    //这里是鬼差。 所有的属性 以70级  为基准， 每级提高百分之5
                    try{
                        if(lv>0){
                            Double lvcha = (lv -70)*0.05;
                            fightObject3.shengming = (int)Math.round(fightObject3.shengming*(1+lvcha));
                            fightObject3.max_shengming = fightObject3.shengming;
                            fightObject3.fangyu = (int)Math.round(fightObject3.fangyu*(1+lvcha));
                            fightObject3.accurate = (int)Math.round(fightObject3.accurate*(1+lvcha));
                            fightObject3.fashang = (int)Math.round(fightObject3.fashang*(1+lvcha));
                            fightObject3.fangyu_ext = (int)Math.round(fightObject3.fangyu_ext*(1+lvcha));
                            fightObject3.accurate_ext = (int)Math.round(fightObject3.accurate_ext*(1+lvcha));
                            fightObject3.fashang_ext = (int)Math.round(fightObject3.fashang_ext*(1+lvcha));
                            //fightObject3.parry_ext = (int)Math.round(fightObject3.parry_ext*(1+lvcha));
                        }
                    }catch(Exception e){
                        log.info("鬼差出错，{}",e);
                    }
                }
                // 设置位置
                fightObject3.pos = FightManager.MONSTER_POS.get(monsterIndex);
                fightObject3.fid = fc.id++;
                //添加到这里 bossid 鬼差

                if (session.victimId == 0) {
                    session.victimId = fightObject3.fid;
                }
                if (monsterIndex == 1) {
                    fightObject3.leader = 1;
                }
                monsterTeam.add(fightObject3);
                ++monsterIndex;
                chara.zhandouInfo = monsterName;
            }

        }

        if (monsterTeam.fightObjectList == null || monsterTeam.fightObjectList.isEmpty()) {
            FightManager.listFight.remove(fc);
            log.error("boss怪物队伍为空");
            return;
        }

        // 将两个战斗队伍加入到战斗容器中
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);

        // 再将战斗容器加入到全局战斗列表中,定时任务会读取判断战斗列表是否为空
        FightManager.listFight.add(fc);

        // 如果角色开启了自动战斗，自动战斗的值为1
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select; // 设置为第1回合
            vo_32985_0.user_action = chara.autofight_skillaction; // 自动战斗时的技能类型
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno; // 自动技能编号
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 1;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        // 获取友方队伍
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        // 这里还是获取友方的战斗集合
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);

        }
        // 将友方战斗对象包装好发送
        send(fc, new MSG_C_FRIENDS(), list65019);

        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = fightObject6.weapon_icon;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        // 将敌方战斗对象包装好发送
        send(fc, new MSG_C_OPPONENTS(), list65020);

        fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;


        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);

        // 这里是发送战斗容器的战斗回合发送到前端
        round(fc);

    }

    /**
     * 活动战斗
     *
     * @param chara       玩家
     * @param monsterList 怪物
     * @param fuid        唯一标识
     * @param isShouhu    是否守护出战
     * @param npcId
     */
    public static void activeBoosGoFight(Chara chara, List<String> monsterList, String fuid, boolean isShouhu,
                                         int... npcId) {
        // 如果全局战斗容器中已经有当前这个战斗容器了，则移除
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        // 新建战斗容器
        FightContainer fc = new FightContainer();
        // 建立角色方战斗队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        // 设置战斗对象
        if (npcId != null && npcId.length > 0) {
            chara.zhandouId = npcId[0];
            GameCore.fightObject.put(npcId[0], npcId[0]);
        }
        // 如果是团队作战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                addFabao(fc, duiwu.get(i), fightObject);
                if (i == 0) {
                    fightObject.leader = 1;
                }
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    Petbeibao petbeibao = pets.get(j);
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(petbeibao, duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = petbeibao.id;
                        fightObject.id = petbeibao.id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }
        // 如果是单人作战
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id; // 角色的战斗id就是角色id
            fightObject2.leader = 1; // 是战斗队长
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            // 将角色的法宝添加到战斗中
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2); // 角色加入到友方队伍
            List<Petbeibao> pets2 = chara.pets;
            // 将参战的宠物ID加入到友方队伍中
            for (int k = 0; k < pets2.size(); ++k) {
                Petbeibao petbeibao2 = pets2.get(k);
                if (petbeibao2.id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(petbeibao2, chara);
                    // 让宠物站在角色的正前方
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = petbeibao2.id; // 宠物的战斗id是其本身id
                    fightObject2.id = petbeibao2.id;
                    fightObject2.cid = chara.id;
                    fightObject2.shape = petbeibao2.petShuXing.get(0).shape;
                    fightObject2.petType = petbeibao2.petShuXing.get(0).penetrate;
                    // 如果宠物有天书
                    if (petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(petbeibao2, fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num; // 一个角色及其宠物的战斗已经初始化完成
            // 设置单人作战状态
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }
        // 为true允许守护出战
        if (isShouhu) {
            // 添加守护
            for (int i = 0; i < chara.listshouhu.size() && num < 5; ++i) {
                if (chara.listshouhu.get(i).listShouHuShuXing.get(0).nil != 0) {
                    FightObject fightObject = new FightObject(chara.listshouhu.get(i));
                    fightObject.pos = FightManager.PERSON_POS.get(num);
                    fightObject.fid = fc.id++; // 守护的id是fc的id递增加1
                    ft.add(fightObject); // 将守护加入到战斗队伍中
                    ++num; // 新增到下一个对象
                }
            }
        }
        // 初始化怪物方的队伍， 队伍类型为2
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;
        int monsterIndex = 0;
        for (String monsterName : monsterList) {
            List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByName(monsterName);
            // 如果地方为空,直接结束战斗
            if (fightObjectInfos != null && !fightObjectInfos.isEmpty()) {
                int randomIndex = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                FightObject fightObject3 = new FightObject(fightObjectInfos.get(randomIndex), true,
                        new Random().nextInt(5) + 1);
                // 设置位置
                fightObject3.pos = FightManager.MONSTER_POS.get(monsterIndex);
                fightObject3.fid = fc.id++;
                fightObject3.uid = fuid == null ? "" : fuid;
                if (session.victimId == 0) {
                    session.victimId = fightObject3.fid;
                }
                if (monsterIndex == 1) {
                    fightObject3.leader = 1;
                }
                monsterTeam.add(fightObject3);
                ++monsterIndex;
                chara.zhandouInfo = monsterName;
            }

        }

        if (monsterTeam.fightObjectList == null || monsterTeam.fightObjectList.isEmpty()) {
            FightManager.listFight.remove(fc);
            log.error("boss怪物队伍为空");
            return;
        }

        // 将两个战斗队伍加入到战斗容器中
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);

        // 再将战斗容器加入到全局战斗列表中,定时任务会读取判断战斗列表是否为空
        FightManager.listFight.add(fc);

        // 如果角色开启了自动战斗，自动战斗的值为1
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select; // 设置为第1回合
            vo_32985_0.user_action = chara.autofight_skillaction; // 自动战斗时的技能类型
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno; // 自动技能编号
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }
        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 1;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        // 获取友方队伍
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }
        // 这里还是获取友方的战斗集合
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);

        }
        // 将友方战斗对象包装好发送
        send(fc, new MSG_C_FRIENDS(), list65019);

        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = fightObject6.weapon_icon;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        // 将敌方战斗对象包装好发送
        send(fc, new MSG_C_OPPONENTS(), list65020);

        fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;

        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);

        // 这里是发送战斗容器的战斗回合发送到前端
        round(fc);
    }

    /**
     * 根据指定等级生成战斗
     *
     * @param chara      玩家
     * @param fightLevel 怪物等级
     * @param fightNames 怪物名字
     * @param isShouhu   是否守护
     * @param fightIds   战斗id
     */
    public static void goFightDynamicLevel(Chara chara, int fightLevel, List<String> fightNames, boolean isShouhu,
                                           int... fightIds) {
        List<String> fights = new ArrayList<>();
        List<FightObjectInfo> fights2 = new ArrayList<>();
        if (fightNames != null && !fightNames.isEmpty()) {
            // 获取队长阶段
            int level = GameCommonUtil.getZbLevel(fightLevel);
            // 给所有的对象加上等级
            for (String f : fightNames) {
                String name = f + "(" + level + ")";
                fights.add(name);
                List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByName(name);
                if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
                    log.error("找不到该怪物的配置:{}", name);
                    continue;
                }
                int random = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                fights2.add(fightObjectInfos.get(random));
            }
        }
        // 把参战的id加入到战斗序列去。
        for (int fid : fightIds) {
            GameCore.fightObject.put(fid, fid);
            chara.zhandouId = fid;
            break;
        }

        if (fights2 != null && !fights2.isEmpty()) {
            List<FightObject> fightObjects = new ArrayList<>();
            for (FightObjectInfo fi : fights2) {
                int randomPolar = ThreadLocalRandom.current().nextInt(5) + 1;
                if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(fi.getPolar())) {
                    randomPolar = GameCommonUtil.getPolarByCn(fi.getPolar());
                }
                FightObject fightObject = new FightObject(fi, true, randomPolar);
                if (fightIds != null && fightIds.length > 0) {
                    fightObject.bossid = fightIds[0];
                }
                fightObjects.add(fightObject);
            }
            activeBoosGoFight(chara, fightObjects, isShouhu);
        }
    }

    /**
     * a根据玩家等级阶段找寻怪物等级
     *
     * @param chara      玩家
     * @param fightNames 怪物列表
     * @param isShouhu   是否让守护出来
     */
    public static void goFightDynamicLevel(Chara chara, List<String> fightNames, boolean isShouhu, int... fightIds) {
        List<String> fights = new ArrayList<>();
        List<FightObjectInfo> fights2 = new ArrayList<>();
        if (fightNames != null && !fightNames.isEmpty()) {
            // 获取队长阶段
            int level = GameCommonUtil.getZbLevel(chara.level);
            // 给所有的对象加上等级
            for (String f : fightNames) {
                String name = f + "(" + level + ")";
                fights.add(name);
                List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByName(name);
                if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
                    log.error("找不到该怪物的配置:{}", name);
                    continue;
                }
                int random = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                fights2.add(fightObjectInfos.get(random));
            }
        }
        // 把参战的id加入到战斗序列去。
        for (int fid : fightIds) {
            GameCore.fightObject.put(fid, fid);
            chara.zhandouId = fid;
            break;
        }

        if (fights2 != null && !fights2.isEmpty()) {
            List<FightObject> fightObjects = new ArrayList<>();
            for (FightObjectInfo fi : fights2) {
                int randomPolar = ThreadLocalRandom.current().nextInt(5) + 1;
                if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(fi.getPolar())) {
                    randomPolar = GameCommonUtil.getPolarByCn(fi.getPolar());
                }
                FightObject fightObject = new FightObject(fi, true, randomPolar);
                if (fightIds != null && fightIds.length > 0) {
                    fightObject.bossid = fightIds[0];
                }
                fightObjects.add(fightObject);
            }
            activeBoosGoFight(chara, fightObjects, isShouhu);
        }
    }

    /**
     * a根据玩家等级阶段找寻怪物等级
     *
     * @param chara      玩家
     * @param fightNames 怪物列表
     * @param isShouhu   是否让守护出来
     */
    public static void goFightDynamicLevelByType(Chara chara, List<String> fightNames, String type, int... fightIds) {
        List<String> fights = new ArrayList<>();
        List<FightObjectInfo> fights2 = new ArrayList<>();
        if (fightNames != null && !fightNames.isEmpty()) {
            // 获取队长阶段
            int level = GameCommonUtil.getZbLevel(chara.level);
            // 给所有的对象加上等级
            for (String f : fightNames) {
                String name = f + "(" + level + ")";
                fights.add(name);
                List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByNameForType(name,
                        type);
                if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
                    log.error("找不到该怪物的配置:{}", name);
                    continue;
                }
                int random = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                fights2.add(fightObjectInfos.get(random));
            }
        }
        // 把参战的id加入到战斗序列去。
        for (int fid : fightIds) {
            GameCore.fightObject.put(fid, fid);
            chara.zhandouId = fid;
            break;
        }

        if (fights2 != null && !fights2.isEmpty()) {
            List<FightObject> fightObjects = new ArrayList<>();
            for (FightObjectInfo fi : fights2) {
                int randomPolar = ThreadLocalRandom.current().nextInt(5) + 1;
                if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(fi.getPolar())) {
                    randomPolar = GameCommonUtil.getPolarByCn(fi.getPolar());
                }
                FightObject fightObject = new FightObject(fi, true, randomPolar);
                if (fightIds != null && fightIds.length > 0) {
                    fightObject.bossid = fightIds[0];
                }
                fightObjects.add(fightObject);
            }
            activeBoosGoFight(chara, fightObjects, false);
        }
    }

    /**
     * a根据玩家等级阶段找寻怪物等级
     *
     * @param chara      玩家
     * @param fightNames 怪物列表
     * @param isShouhu   是否让守护出来
     */
    public static List<FightObject> getFightDynamicLevelByType(Chara chara, List<String> fightNames, String type,
                                                               int... fightIds) {
        List<String> fights = new ArrayList<>();
        List<FightObjectInfo> fights2 = new ArrayList<>();
        if (fightNames != null && !fightNames.isEmpty()) {
            // 获取队长阶段
            int level = GameCommonUtil.getZbLevel(chara.level);
            // 给所有的对象加上等级
            for (String f : fightNames) {
                String name = f + "(" + level + ")";
                fights.add(name);
                List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByNameForType(name,
                        type);
                if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
                    log.error("找不到该怪物的配置:{}", name);
                    continue;
                }
                int random = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                fights2.add(fightObjectInfos.get(random));
            }
        }
        // 把参战的id加入到战斗序列去。
        for (int fid : fightIds) {
            GameCore.fightObject.put(fid, fid);
            chara.zhandouId = fid;
            break;
        }

        List<FightObject> fightObjects = new ArrayList<>();
        if (fights2 != null && !fights2.isEmpty()) {
            for (FightObjectInfo fi : fights2) {
                int randomPolar = ThreadLocalRandom.current().nextInt(5) + 1;
                if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(fi.getPolar())) {
                    randomPolar = GameCommonUtil.getPolarByCn(fi.getPolar());
                }
                FightObject fightObject = new FightObject(fi, true, randomPolar);
                if (fightIds != null && fightIds.length > 0) {
                    fightObject.bossid = fightIds[0];
                }
                fightObjects.add(fightObject);
            }
        }
        return fightObjects;
    }


    /**
     * a根据玩家等级阶段找寻怪物等级
     *
     * @param chara      玩家
     * @param fightNames 怪物列表
     * @param isShouhu   是否让守护出来
     */
    public static List<FightObject> getFightDynamicLevelByType(Chara chara, int level, List<String> fightNames, String type,
                                                               int... fightIds) {
        List<String> fights = new ArrayList<>();
        List<FightObjectInfo> fights2 = new ArrayList<>();
        if (fightNames != null && !fightNames.isEmpty()) {
            // 获取队长阶段
            int mlevel = GameCommonUtil.getZbLevel(level);
            // 给所有的对象加上等级
            for (String f : fightNames) {
                String name = f + "(" + mlevel + ")";
                fights.add(name);
                List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByNameForType(name,
                        type);
                if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
                    log.error("找不到该怪物的配置:{}", name);
                    continue;
                }
                int random = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());
                fights2.add(fightObjectInfos.get(random));
            }
        }
        // 把参战的id加入到战斗序列去。
        for (int fid : fightIds) {
            GameCore.fightObject.put(fid, fid);
            chara.zhandouId = fid;
            break;
        }

        List<FightObject> fightObjects = new ArrayList<>();
        if (fights2 != null && !fights2.isEmpty()) {
            for (FightObjectInfo fi : fights2) {
                int randomPolar = ThreadLocalRandom.current().nextInt(5) + 1;
                if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(fi.getPolar())) {
                    randomPolar = GameCommonUtil.getPolarByCn(fi.getPolar());
                }
                FightObject fightObject = new FightObject(fi, true, randomPolar);
                if (fightIds != null && fightIds.length > 0) {
                    fightObject.bossid = fightIds[0];
                }
                fightObjects.add(fightObject);
            }
        }
        return fightObjects;
    }

    /**
     * 一般互动战斗
     *
     * @param chara          玩家
     * @param attFightObject 战斗对象
     * @param fightNum       战斗数量
     * @param isShouhu
     */
    public static void activeBoosGoFight(Chara chara, List<FightObject> attFightObjects, boolean isShouhu) {
        if (attFightObjects == null || attFightObjects.isEmpty()) {
            return;
        }
        // 如果全局战斗容器中已经有当前这个战斗容器了，则移除
        for (FightContainer fightContainer = getFightContainer(
                chara.id); fightContainer != null; fightContainer = getFightContainer(chara.id)) {
            FightManager.listFight.remove(fightContainer);
        }
        // 新建战斗容器
        FightContainer fc = new FightContainer();
        // 建立角色方战斗队伍
        FightTeam ft = new FightTeam();
        ft.type = 1;
        GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara.id);
        int num = 0;
        // 如果是团队作战
        if (GameCommonUtil.isNotGameTeam(session.gameTeam, session.chara)) {
            List<Chara> duiwu = session.gameTeam.duiwu;
            for (int i = 0; i < duiwu.size(); ++i) {
                FightObject fightObject = new FightObject(duiwu.get(i));
                fightObject.pos = FightManager.PERSON_POS.get(num);
                fightObject.fid = duiwu.get(i).id;
                fightObject.id = duiwu.get(i).id;
                addFabao(fc, duiwu.get(i), fightObject);
                if (i == 0) {
                    fightObject.leader = 1;
                }
                ft.add(fightObject);
                List<Petbeibao> pets = duiwu.get(i).pets;
                for (int j = 0; j < pets.size(); ++j) {
                    Petbeibao petbeibao = pets.get(j);
                    if (pets.get(j).id == duiwu.get(i).chongwuchanzhanId) {
                        fightObject = new FightObject(petbeibao, duiwu.get(i));
                        fightObject.pos = FightManager.PERSON_POS.get(num) + 5;
                        fightObject.fid = petbeibao.id;
                        fightObject.id = petbeibao.id;
                        fightObject.cid = duiwu.get(i).id;
                        if (petbeibao.tianshu.size() != 0) {
                            addFightTianShu(petbeibao, fightObject, fc);
                        }
                        ft.add(fightObject);
                        break;
                    }
                }
                ++num;
                duiwu.get(i).isFight = true;
                //设置头顶标识
                GameCommonUtil.setCharaTitleFlag(duiwu.get(i));
            }
        }
        // 如果是单人作战
        else {
            FightObject fightObject2 = new FightObject(chara);
            fightObject2.pos = FightManager.PERSON_POS.get(num);
            fightObject2.fid = chara.id; // 角色的战斗id就是角色id
            fightObject2.leader = 1; // 是战斗队长
            fightObject2.id = chara.id;
            fightObject2.str = chara.name;
            // 将角色的法宝添加到战斗中
            addFabao(fc, chara, fightObject2);
            ft.add(fightObject2); // 角色加入到友方队伍
            List<Petbeibao> pets2 = chara.pets;
            // 将参战的宠物ID加入到友方队伍中
            for (int k = 0; k < pets2.size(); ++k) {
                Petbeibao petbeibao2 = pets2.get(k);
                if (petbeibao2.id == chara.chongwuchanzhanId) {
                    fightObject2 = new FightObject(petbeibao2, chara);
                    // 让宠物站在角色的正前方
                    fightObject2.pos = FightManager.PERSON_POS.get(num) + 5;
                    fightObject2.fid = petbeibao2.id; // 宠物的战斗id是其本身id
                    fightObject2.id = petbeibao2.id;
                    fightObject2.cid = chara.id;
                    fightObject2.shape = petbeibao2.petShuXing.get(0).shape;
                    fightObject2.petType = petbeibao2.petShuXing.get(0).penetrate;
                    // 如果宠物有天书
                    if (petbeibao2.tianshu.size() != 0) {
                        addFightTianShu(petbeibao2, fightObject2, fc);
                    }
                    ft.add(fightObject2);
                    break;
                }
            }
            ++num; // 一个角色及其宠物的战斗已经初始化完成
            // 设置单人作战状态
            chara.isFight = true;
            //设置头顶标识
            GameCommonUtil.setCharaTitleFlag(chara);
        }

        // 为true允许守护出战
        if (isShouhu) {
            // 添加守护
            for (int i = 0; i < chara.listshouhu.size() && num < 5; ++i) {
                if (chara.listshouhu.get(i).listShouHuShuXing.get(0).nil != 0) {
                    FightObject fightObject = new FightObject(chara.listshouhu.get(i));
                    fightObject.pos = FightManager.PERSON_POS.get(num);
                    fightObject.fid = fc.id++; // 守护的id是fc的id递增加1
                    ft.add(fightObject); // 将守护加入到战斗队伍中
                    ++num;
                }
            }
        }

        // 初始化怪物方的队伍， 队伍类型为2
        FightTeam monsterTeam = new FightTeam();
        monsterTeam.type = 2;

        for (int i = 0; i < attFightObjects.size(); i++) {
            FightObject attFightObject = attFightObjects.get(i);
            // 设置怪物随机相性
            attFightObject.pos = FightManager.MONSTER_POS.get(i); // 设置怪物的战斗位置
            if (i == 1) { // 将第二个怪物设置为队长
                attFightObject.leader = 1;
            }
            if (attFightObject.fid == 0) {
                attFightObject.fid = fc.id++; // 战斗id为fc的id递增加1
            }
            monsterTeam.add(attFightObject);
        }
        if (monsterTeam.fightObjectList == null || monsterTeam.fightObjectList.isEmpty()) {
            FightManager.listFight.remove(fc);
            log.error("boss怪物队伍为空");
            return;
        }
        // 将两个战斗队伍加入到战斗容器中
        fc.teamList.add(ft);
        fc.teamList.add(monsterTeam);
        // 再将战斗容器加入到全局战斗列表中,定时任务会读取判断战斗列表是否为空
        FightManager.listFight.add(fc);

        // 如果角色开启了自动战斗，自动战斗的值为1
        if (chara.autofight_select != 0) {
            Vo_32985_0 vo_32985_0 = new Vo_32985_0();
            vo_32985_0.user_is_multi = 0;
            vo_32985_0.user_round = chara.autofight_select; // 设置为第1回合
            vo_32985_0.user_action = chara.autofight_skillaction; // 自动战斗时的技能类型
            vo_32985_0.user_next_action = chara.autofight_skillaction;
            vo_32985_0.user_para = chara.autofight_skillno; // 自动技能编号
            vo_32985_0.user_next_para = chara.autofight_skillno;
            vo_32985_0.pet_is_multi = 0;
            vo_32985_0.pet_round = 0;
            vo_32985_0.pet_action = 0;
            vo_32985_0.pet_next_action = 0;
            vo_32985_0.pet_para = 0;
            vo_32985_0.pet_next_para = 0;
            GameObjectChar.send(new M32985_0(), vo_32985_0);
        }

        // 加载战斗信息
        GameCommonUtil.fightCmdInfo(session);
        // 开始战斗
        Vo_3583_0 vo_3583_0 = new Vo_3583_0();
        vo_3583_0.flag = 1;
        vo_3583_0.mode = 3;
        send(fc, new MSG_C_START_COMBAT(), vo_3583_0);
        // 获取友方队伍
        FightTeam friendsFightTeam = getFightTeam(fc, chara.id);
        List<FightObject> fightObjectList1 = friendsFightTeam.fightObjectList;
        for (FightObject fightObject4 : fightObjectList1) {
            if (fightObject4.type == 2) {
                Vo_64971_0 vo_64971_0 = new Vo_64971_0();
                vo_64971_0.count = 1;
                vo_64971_0.id = fightObject4.id;
                vo_64971_0.haveCalled = 1;
                GameObjectCharMng.getGameObjectChar(fightObject4.cid).sendOne(new MSG_C_REFRESH_PET_LIST(), vo_64971_0);
            }
        }

        // 这里还是获取友方的战斗集合
        List<Vo_ADD_FRIEND_OPPONENT> list65019 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        List<FightObject> fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;
        for (FightObject fightObject5 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65019_0.id = fightObject5.fid;
            vo_65019_0.leader = fightObject5.leader;
            vo_65019_0.weapon_icon = fightObject5.weapon_icon;
            vo_65019_0.pos = fightObject5.pos;
            vo_65019_0.rank = fightObject5.rank;
            vo_65019_0.vip_type = fightObject5.vipType;
            vo_65019_0.str = fightObject5.str;
            vo_65019_0.type = fightObject5.org_icon;
            vo_65019_0.durability = fightObject5.durability;
            vo_65019_0.req_level = 0;
            vo_65019_0.upgrade_level = fightObject5.upgrade_level;
            vo_65019_0.upgrade_type = fightObject5.upgrade_type;
            vo_65019_0.dex = fightObject5.max_mofa;
            vo_65019_0.max_mana = fightObject5.max_mofa;
            vo_65019_0.max_life = fightObject5.max_shengming;
            vo_65019_0.def = fightObject5.max_shengming;
            vo_65019_0.org_icon = fightObject5.org_icon;
            vo_65019_0.suit_icon = fightObject5.suit_icon;
            vo_65019_0.suit_light_effect = fightObject5.suit_light_effect;
            vo_65019_0.special_icon = fightObject5.special_icon;
            vo_65019_0.customIcon = fightObject5.customIcon;
            vo_65019_0.zhenlingLevel = fightObject5.zhenlingLevel;
            vo_65019_0.zhenlingType = fightObject5.zhenlingType;
            list65019.add(vo_65019_0);
        }
        // 将友方战斗对象包装好发送
        send(fc, new MSG_C_FRIENDS(), list65019);

        List<Vo_ADD_FRIEND_OPPONENT> list65020 = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
        fightObjectList2 = getFightTeamDM(fc, chara.id).fightObjectList;
        for (FightObject fightObject6 : fightObjectList2) {
            Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
            vo_65017_0.id = fightObject6.fid;
            vo_65017_0.leader = fightObject6.leader;
            vo_65017_0.weapon_icon = fightObject6.weapon_icon;
            vo_65017_0.pos = fightObject6.pos;
            vo_65017_0.rank = 0;
            vo_65017_0.vip_type = fightObject6.vipType;
            vo_65017_0.str = fightObject6.str;
            vo_65017_0.type = fightObject6.org_icon;
            vo_65017_0.durability = 2;
            vo_65017_0.req_level = 0;
            vo_65017_0.upgrade_level = fightObject6.upgrade_level;
            vo_65017_0.upgrade_type = fightObject6.upgrade_type;
            vo_65017_0.dex = fightObject6.max_mofa;
            vo_65017_0.max_mana = fightObject6.max_mofa;
            vo_65017_0.max_life = fightObject6.max_shengming;
            vo_65017_0.def = fightObject6.max_shengming;
            vo_65017_0.org_icon = fightObject6.org_icon;
            vo_65017_0.suit_icon = fightObject6.suit_icon;
            vo_65017_0.suit_light_effect = fightObject6.suit_light_effect;
            vo_65017_0.special_icon = fightObject6.special_icon;
            vo_65017_0.customIcon = fightObject6.customIcon;
            vo_65017_0.zhenlingLevel = fightObject6.zhenlingLevel;
            vo_65017_0.zhenlingType = fightObject6.zhenlingType;
            list65020.add(vo_65017_0);
        }
        // 将敌方战斗对象包装好发送
        send(fc, new MSG_C_OPPONENTS(), list65020);

        fightObjectList2 = getFightTeam(fc, chara.id).fightObjectList;

        // 天书底部效果
        getRandomGodbookEffect(fightObjectList2, fc);


        // 这里是发送战斗容器的战斗回合发送到前端
        round(fc);

    }

    // 飞升战斗
    public static void goFightFeiSheng(Chara chara, List<String> monsterList) {
        activeBoosGoFight(chara, monsterList, false);
    }

    /**
     * 添加天书
     *
     * @param petbeibao   宝宝
     * @param fightObject 战斗对象
     * @param fc          战斗容器
     */
    public static void addFightTianShu(Petbeibao petbeibao, FightObject fightObject, FightContainer fc) {
        if (fightObject == null || fc == null) {
            return;
        }
        if (petbeibao != null && petbeibao.tianshu != null && !petbeibao.tianshu.isEmpty()) {
            for (Vo_12023_0 tianshu : petbeibao.tianshu) {
                Integer godbook = FightTianshuMap.TIANSHU_EFFECT.get(tianshu.god_book_skill_name);
                if (godbook != null) {
                    fightObject.godbook = godbook;
                    XiuluoshuSkill xiuluoshuSkill2 = new XiuluoshuSkill(tianshu.god_book_skill_name);
                    xiuluoshuSkill2.buffObject = fightObject;
                    xiuluoshuSkill2.fightContainer = fc;
                    fightObject.addSkill(xiuluoshuSkill2);
                }
            }
        }
    }

    /**
     * 为战斗对象随机生成一个天书底部效果
     *
     * @param fightObjectList
     */
    public static void getRandomGodbookEffect(List<FightObject> fightObjectList, FightContainer fc) {
        for (FightObject fightObject5 : fightObjectList) {
            if (fightObject5.godbook != 0) {
                int addFightTianShuType = fightObject5.getRandomTianshuType(fc);
                Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
                vo_12025_0.id = fightObject5.fid;
                vo_12025_0.effect_no = addFightTianShuType;
                send(fc, new MSG_GODBOOK_EFFECT_NORMAL(), vo_12025_0);
            }
        }
    }

    /**
     * 中途加入战斗
     *
     * @param fc         战斗容器
     * @param joinObject 需要加入的对象
     */
    public static void joinFightObject(FightContainer fc, FightObject joinObject) {
        if (fc == null || joinObject == null) {
            return;
        }
        FightTeam friendsFightTeam = getFightTeam(fc, joinObject.fid);
        FightTeam enemyTeam = getFightTeamDM(fc, joinObject.fid);
        friendsFightTeam.fightObjectList.add(joinObject);
        // 新的队伍信息
        List<Vo_ADD_FRIEND_OPPONENT> newFightTeamInfo = builderFightObject(fc,
                Lists.newArrayList(joinObject));
        // 添加队友
        sendTeam(fc, friendsFightTeam.fightObjectList, new MSG_C_ADD_FRIEND(), newFightTeamInfo);
        sendTeam(fc, enemyTeam.fightObjectList, new MSG_C_ADD_OPPONENT(), newFightTeamInfo);
    }

    /**
     * 设置自动喊话
     *
     * @param fightObject 战斗对象
     * @param fr          战斗请求
     */
    public static void setAutoTalkMsg(FightObject fightObject, FightRequest fr) {
        //根据para找出技能对话
        if (fightObject.autoTalk != null && fightObject.combatAutoTalk == 1) {
            for (AutoTalkVo vo : fightObject.autoTalk) {
                int action = fr.action;
                //防御
                if (action == 1) {
                    if (vo.getType().equals(2)) {
                        fr.skill_talk = vo.getMsg();
                        break;
                    }
                } else if (action == 2) { //物理攻击
                    if (vo.getType().equals(1)) {
                        fr.skill_talk = vo.getMsg();
                        break;
                    }
                } else if (action == 8) { //召唤
                    if (vo.getType().equals(7)) {
                        fr.skill_talk = vo.getMsg();
                        break;
                    }
                } else {
                    if (vo.getPara().equals(fr.para)) {
                        fr.skill_talk = vo.getMsg();
                        break;
                    }
                }
            }
        }
    }

    /**
     * 默认操作
     *
     * @param fightContainer
     * @param fightRequest
     */
    public static void defenseAction(FightContainer fightContainer, FightRequest fightRequest) {
        if (fightContainer != null) {
            Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
            vo_19959_0.round = fightContainer.round;
            vo_19959_0.aid = fightRequest.id;
            vo_19959_0.action = 0;
            vo_19959_0.vid = 0;
            vo_19959_0.para = 0;
            FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
            FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(fightRequest.id));
        }
    }

    /**
     * 自动喊话动作
     *
     * @param fightContainer 战斗容器
     * @param fightRequest   战斗请求
     */
    public static void autoTalkAction(FightContainer fightContainer, FightRequest fightRequest) {
        if (fightContainer != null && fightRequest != null && !com.mysql.jdbc.StringUtils.isNullOrEmpty(fightRequest.skill_talk)) {
            FightManager.send(fightContainer, new MSG_C_SET_CUSTOM_MSG(), GameCommonUtil.getAutoTalkObj(fightRequest.id, fightRequest.skill_talk, 17));
            FightObject fightObject = getFightObject(fightRequest.id);
            if (fightObject != null) {
                //触发了喊话
                fightObject.isTalk = true;
            }
        }
    }


    static {
        log = LoggerFactory.getLogger(FightManager.class);
        listFight = new CopyOnWriteArrayList<FightContainer>();
        MONSTER_POS = new CopyOnWriteArrayList<Integer>(new Integer[]{3, 2, 4, 1, 5, 8, 7, 9, 6, 10});
        PER_POS = new CopyOnWriteArrayList<Integer>(new Integer[]{3, 2, 4, 1, 5});
        PET_POS = new CopyOnWriteArrayList<Integer>(new Integer[]{8, 7, 9, 6, 10});
        PERSON_POS = new CopyOnWriteArrayList<Integer>(new Integer[]{3, 2, 4, 1, 5});
        FightManager.zmMap = new HashMap<String, List<FightObject>>();
        RANDOM = new Random();
    }
}