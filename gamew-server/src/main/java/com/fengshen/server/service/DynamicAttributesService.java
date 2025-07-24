package com.fengshen.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FightObjectInfo;
import com.fengshen.server.data.game.ChangeCardAttr;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.fight.FightAttribtueType;
import com.fengshen.server.fight.FightAttribute;

/**
 * 动态属性管理器
 */
@Service
public class DynamicAttributesService {

    public static FightAttribute fightAttribute(Chara chara) {
        FightAttribute fightAttribute = new FightAttribute();
        fightAttribute.setAttribute(FightAttribtueType.FANZHEN_RATE, chara.zbAttribute.portrait);//反震率
        fightAttribute.setAttribute(FightAttribtueType.FANZHEN_NUM, chara.zbAttribute.family);//反震度
        fightAttribute.setAttribute(FightAttribtueType.REBACK_HIT_RATE, chara.zbAttribute.double_hit_rate);//反击率
        fightAttribute.setAttribute(FightAttribtueType.REBACK_HIT_NUM, chara.zbAttribute.life_recover);//反击次数
 
        fightAttribute.setAttribute(FightAttribtueType.ALL_RESIST_POLAR, chara.zbAttribute.all_resist_except);//所有抗性百分比
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_METAL, chara.zbAttribute.ignore_resist_wood);//忽视目标抗金
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_WOOD, chara.zbAttribute.ignore_resist_water);
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_WATER, chara.zbAttribute.ignore_resist_fire);
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_FIRE, chara.zbAttribute.ignore_resist_earth);
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_EARTH, chara.zbAttribute.ignore_resist_forgotten);
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_ALL_RESIST_EXCEPT, chara.zbAttribute.release_forgotten);
        fightAttribute.setAttribute(FightAttribtueType.B_SKILL_LOW_COST, chara.zbAttribute.C_skill_low_cost);
        fightAttribute.setAttribute(FightAttribtueType.C_SKILL_LOW_COST, chara.zbAttribute.D_skill_low_cost);
        fightAttribute.setAttribute(FightAttribtueType.D_SKILL_LOW_COST, chara.zbAttribute.super_poison);
        //强物理伤害
        fightAttribute.setAttribute(FightAttribtueType.ENHANCED_PHY2, chara.zbAttribute.ignore_mag_dodge);
        
        //五大抗性
        fightAttribute.setAttribute(FightAttribtueType.RESIST_METAL, chara.zbAttribute.resist_wood);//金
        fightAttribute.setAttribute(FightAttribtueType.RESIST_WOOD, chara.zbAttribute.resist_water);//木
        fightAttribute.setAttribute(FightAttribtueType.RESIST_WATER, chara.zbAttribute.resist_fire);//水
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FIRE, chara.zbAttribute.resist_earth);//火
        fightAttribute.setAttribute(FightAttribtueType.RESIST_EARTH, chara.zbAttribute.exp_to_next_level);//土
        //忽视所有抗性
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_ALL_RESIST_POLAR, chara.zbAttribute.ignore_all_resist_except);
        
        //五大强力克制
        fightAttribute.setAttribute(FightAttribtueType.super_excluse_metal, chara.zbAttribute.super_excluse_wood);//金
        fightAttribute.setAttribute(FightAttribtueType.super_excluse_wood, chara.zbAttribute.super_excluse_water);//木
        fightAttribute.setAttribute(FightAttribtueType.super_excluse_water, chara.zbAttribute.super_excluse_fire);//水
        fightAttribute.setAttribute(FightAttribtueType.super_excluse_fire, chara.zbAttribute.super_excluse_earth);//火
        fightAttribute.setAttribute(FightAttribtueType.super_excluse_earth, chara.zbAttribute.B_skill_low_cost);//土
        //五大强法
        fightAttribute.setAttribute(FightAttribtueType.enhanced_metal, chara.zbAttribute.enhanced_wood);//金
        fightAttribute.setAttribute(FightAttribtueType.enhanced_wood, chara.zbAttribute.enhanced_water);//木
        fightAttribute.setAttribute(FightAttribtueType.enhanced_water, chara.zbAttribute.enhanced_fire);//水
        fightAttribute.setAttribute(FightAttribtueType.enhanced_fire, chara.zbAttribute.enhanced_earth);//火
        fightAttribute.setAttribute(FightAttribtueType.enhanced_earth, chara.zbAttribute.mag_dodge);//土
        //有几率躲避攻击
        fightAttribute.setAttribute(FightAttribtueType.mag_dodge, chara.zbAttribute.jinguang_zhaxian_counter_att_rate);
        //忽视闪避
        fightAttribute.setAttribute(FightAttribtueType.ignore_mag_dodge, chara.zbAttribute.ignore_mag_dodge2);
        //五大抗异常
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FORGOTTEN, chara.zbAttribute.resist_confusion);//抗遗忘
        fightAttribute.setAttribute(FightAttribtueType.RESIST_POISON, chara.zbAttribute.resist_frozen);//抗中毒
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FROZEN, chara.zbAttribute.resist_sleep);//抗冰冻
        fightAttribute.setAttribute(FightAttribtueType.RESIST_SLEEP, chara.zbAttribute.resist_forgotten);//抗昏睡
        fightAttribute.setAttribute(FightAttribtueType.RESIST_CONFUSION, chara.zbAttribute.longevity);//抗混乱
        //五大忽视抗异常
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_FORGOTTEN, chara.zbAttribute.ignore_resist_poison);//忽视抗遗忘
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_POISON, chara.zbAttribute.ignore_resist_frozen);//忽视抗中毒
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_FROZEN, chara.zbAttribute.ignore_resist_sleep);//忽视抗冰冻
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_SLEEP, chara.zbAttribute.ignore_resist_confusion);//忽视抗昏睡
        fightAttribute.setAttribute(FightAttribtueType.IGNORE_RESIST_CONFUSION, chara.zbAttribute.super_excluse_metal);//忽视抗混乱
        //五大强力异常
        //强力遗忘
        fightAttribute.setAttribute(FightAttribtueType.SUPER_FORGOTTEN, chara.zbAttribute.super_confusion);
        //强力中毒
        fightAttribute.setAttribute(FightAttribtueType.SUPER_POISON, chara.zbAttribute.super_sleep);
        //强力冰冻
        fightAttribute.setAttribute(FightAttribtueType.SUPER_FROZEN, chara.zbAttribute.enhanced_metal);
        //强力睡眠
        fightAttribute.setAttribute(FightAttribtueType.SUPER_SLEEP, chara.zbAttribute.super_forgotten);
        //强力混乱
        fightAttribute.setAttribute(FightAttribtueType.SUPER_CONFUSION, chara.zbAttribute.super_frozen);
        //所有抗异常
        fightAttribute.setAttribute(FightAttribtueType.ALL_RESIST_EXCEPT, chara.zbAttribute.all_skill);
        int stunt_rate = chara.zbAttribute.stunt_rate;
        int stunt = chara.zbAttribute.stunt;
        int damage_sel = chara.zbAttribute.damage_sel;
        int mstunt_rate = chara.zbAttribute.mstunt_rate2;
        VoChangeCard changeCardInfo = chara.getChangeCardInfo();
        if(changeCardInfo != null) {
        	List<ChangeCardAttr> attrs = changeCardInfo.getAttr();
        	if(attrs != null && !attrs.isEmpty()) {
        		for(ChangeCardAttr a:attrs) {
        			switch (a.getField()) {
					case "stunt_rate":
						damage_sel+=(damage_sel*a.getValue()/100);
						break;
					case "double_hit":
						stunt+=a.getValue();
						break;
					case "mstunt_rate":
						mstunt_rate+=(mstunt_rate*a.getValue()/100);
						break;
					}
        		}
        	}
        }
        fightAttribute.setAttribute(FightAttribtueType.DOUBLE_HIT_RATE, stunt_rate);//物理连击率
        fightAttribute.setAttribute(FightAttribtueType.DOUBLE_HIT, stunt);//物理连击次数
        fightAttribute.setAttribute(FightAttribtueType.STUNT_RATE, damage_sel);//物理必杀率
        //v2.0.0新增法术必杀率
        fightAttribute.setAttribute(FightAttribtueType.MSTUNT_RATE, mstunt_rate);
        return fightAttribute;
    }
    
    /**
     * 怪物抗性
     * @param info
     * @return
     */
    public static FightAttribute fightAttribute(FightObjectInfo info) {
        FightAttribute fightAttribute = new FightAttribute();
        //所有抗性百分比
        fightAttribute.setAttribute(FightAttribtueType.ALL_RESIST_POLAR, info.getAllResistPolar());
        //五大抗性
        fightAttribute.setAttribute(FightAttribtueType.RESIST_METAL, info.getResistMetal());//金
        fightAttribute.setAttribute(FightAttribtueType.RESIST_WOOD, info.getResistWood());//木
        fightAttribute.setAttribute(FightAttribtueType.RESIST_WATER, info.getResistWater());//水
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FIRE, info.getResistFire());//火
        fightAttribute.setAttribute(FightAttribtueType.RESIST_EARTH, info.getResistEarth());//土
        
        fightAttribute.setAttribute(FightAttribtueType.DOUBLE_HIT_RATE, info.getDoubleHitRate());//物理连击率
        fightAttribute.setAttribute(FightAttribtueType.DOUBLE_HIT, info.getDoubleHit());//物理连击次数
        //v2.0.0新增法术必杀率
        fightAttribute.setAttribute(FightAttribtueType.MSTUNT_RATE, info.getMstuntRate());
        return fightAttribute;
    }
    
    /**
     * 宠物抗性点
     * @param info
     * @return
     */
    public static FightAttribute fightAttribute(PetShuXing info) {
        FightAttribute fightAttribute = new FightAttribute();
        //五大抗性
        fightAttribute.setAttribute(FightAttribtueType.RESIST_METAL, info.resist_metal);//金
        fightAttribute.setAttribute(FightAttribtueType.RESIST_WOOD, info.resist_wood);//木
        fightAttribute.setAttribute(FightAttribtueType.RESIST_WATER, info.resist_water);//水
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FIRE, info.resist_fire);//火
        fightAttribute.setAttribute(FightAttribtueType.RESIST_EARTH, info.resist_earth);//土
        
        //五大抗异常
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FORGOTTEN, info.resist_forgotten);//抗遗忘
        fightAttribute.setAttribute(FightAttribtueType.RESIST_POISON, info.resist_poison);//抗中毒
        fightAttribute.setAttribute(FightAttribtueType.RESIST_FROZEN, info.resist_frozen);//抗冰冻
        fightAttribute.setAttribute(FightAttribtueType.RESIST_SLEEP, info.resist_sleep);//抗昏睡
        fightAttribute.setAttribute(FightAttribtueType.RESIST_CONFUSION, info.resist_confusion);//抗混乱
        
        return fightAttribute;
    }
}