package com.fengshen.server.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 魂器阳属性
 * 
 *
 */
@Getter
@Setter
public class HunqiYang {
    //物伤
    private int phy_power;
    //法伤
    private int mag_power;
    //力量
    private int str;
    //速度
    private int speed;
    //灵力
    private int wiz;
    //敏捷
    private int dex;
    //忽视所有抗异常
    private int ignore_all_resist_except;
    //破防率
    private int penetrate_rate;
    //破防
    private int penetrate;
    //物理连击数
    private int double_hit;
    //物理连击率
    private int double_hit_rate;
    //忽视所有抗性
    private int ignore_all_resist_polar;
    //忽视目标抗金
    private int ignore_resist_metal;
    //忽视目标抗木
    private int ignore_resist_wood;
    //忽视目标抗水
    private int ignore_resist_water;
    //忽视目标抗火
    private int ignore_resist_fire;
    //忽视目标抗土
    private int ignore_resist_earth;
    //忽视目标抗遗忘
    private int ignore_resist_forgotten;
    //忽视目标抗中毒
    private int ignore_resist_poison;
    //忽视目标抗冰冻
    private int ignore_resist_frozen;
    //忽视目标抗昏睡
    private int ignore_resist_sleep;
    //忽视目标抗混乱
    private int ignore_resist_confusion;

}
