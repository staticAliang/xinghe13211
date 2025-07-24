package com.fengshen.server.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;

/**
 * 人物雕像信息
 */
public class CharaStatue {
    public int id;
    public String name;
    public int level;
    public int sex;
    public String partyName;
    public int weapon_icon;
    public int shengming;
    public int mofa;
    public int max_shengming;
    public int max_mofa;
    public int fashang;
    public int speed;
    public int phy_power;
    public int fangyu;
    //身穿特效
    public int suit_icon;
    //脚底下的特效
    public int suit_light_effect;
    public int waiguan;
    public int tao;
    public List<JiNeng> jiNengList = new LinkedList<>();
    public int autofight_skillno;
    public int autofight_select;
    public int autofight_skillaction;
    public int polar;
    public String chengHao;
    //朝向
    public String dir;

    /**
     * 宠物
     */
    public Petbeibao petbeibao;
    /**
     * 宠物技能
     */
    public List<JiNeng> petJiNengList = new LinkedList<>();
    
    public int mapId;

    public void copyFrom(Chara chara) {
        this.id = chara.id;
        this.sex = chara.sex;
        this.name = chara.name;
        this.polar = chara.polar;
        this.level = chara.level;
        this.weapon_icon = chara.weapon_icon;
        this.shengming = chara.max_life;

        this.mofa = chara.max_mana;
        this.max_shengming = chara.max_life;
        this.max_mofa = chara.max_mana;
        this.fashang = chara.mana + chara.zbAttribute.mana;
        this.speed = chara.parry + chara.zbAttribute.parry;
        this.phy_power = chara.accurate + chara.zbAttribute.accurate;
        this.fangyu = chara.wiz + chara.zbAttribute.wiz;

        this.suit_icon = chara.suit_icon;
        this.suit_light_effect = chara.suit_light_effect;
        this.waiguan = chara.waiguan;

        this.tao = chara.tao;

        this.chengHao = chara.chenhao;
        this.jiNengList = chara.jiNengList;
        this.autofight_skillno = chara.autofight_skillno;
        this.autofight_select = chara.autofight_select;
        this.autofight_skillaction = chara.autofight_skillaction;
        this.mapId = chara.mapid;
    }

    public void copyChengHao(String chengHao){
        this.chengHao = chengHao;
    }

    public void copyPet(Petbeibao petbeibao){
        Gson gson = new Gson();
        this.petbeibao = gson.fromJson(gson.toJson(petbeibao), Petbeibao.class);
//        this.petbeibao = petbeibao;
    }
    public void copyJiNengList(List<JiNeng> jnList){
        this.jiNengList = new ArrayList<>();
        for (int i = 0; i < jnList.size(); i++) {
            Gson gson = new Gson();
            this.jiNengList.add(gson.fromJson(gson.toJson(jnList.get(i)), JiNeng.class));
        }
//        this.jiNengList = jiNengList;
    }
    public void copyPetJiNengList(List<JiNeng> jnList){
//        this.petJiNengList = jiNengList;
        this.petJiNengList = new ArrayList<>();
        for (int i = 0; i < jnList.size(); i++) {
            Gson gson = new Gson();
            this.petJiNengList.add(gson.fromJson(gson.toJson(jnList.get(i)), JiNeng.class));
        }
    }
}
