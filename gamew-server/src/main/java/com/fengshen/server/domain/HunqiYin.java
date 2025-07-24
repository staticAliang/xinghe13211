package com.fengshen.server.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author：my
 * @describe：魂器.阴
 */
@Getter
@Setter
public class HunqiYin {
	//火抗性
    private int resist_fire;
    //防御
    private int def;
    //体质
    private int con;
    //反击次数
    private int counter_attack;
    //抗混乱
    private int resist_confusion;
    //所有抗性
    private int all_resist_polar;
    //最大气血
    private int max_life;
    //最大法力
    private int max_mana;
    //金抗性
    private int resist_metal;
    //反震度
    private int damage_sel;
    //反震率
    private int damage_sel_rate;
    //反击率
    private int counter_attack_rate;
    //所有抗异常
    private int all_resist_except;
    //木抗性
    private int resist_wood;
    //水抗性
    private int resist_water;
    //土抗性
    private int resist_earth;
    //抗遗忘
    private int resist_forgotten;
    //抗中毒
    private int resist_poison;
    //抗冰冻
    private int resist_frozen;
    //抗睡眠
    private int resist_sleep;

}
