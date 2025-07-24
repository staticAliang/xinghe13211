package com.fengshen.server.fight;

/**
 * 战斗属性类型
 */
public enum FightAttribtueType {
    // 反震率
    FANZHEN_RATE,
    // 反震度
    FANZHEN_NUM,
    // 反击率
    REBACK_HIT_RATE,
    // 反击次数
    REBACK_HIT_NUM,
    /**
     * 复活率*
     */
    REVIVAL_RATE,
    /**
     * 剩余复活次数*
     */
    REVIVAL_NUM,
    /**
     * 物理连击率
     */
    DOUBLE_HIT_RATE,
    /**
     * 物理连击次数
     */
    DOUBLE_HIT,
    /**
     * 物理必杀率
     */
    STUNT_RATE,
    /**
     * 攻击力提升百分比*
     */
    ATTACK_RATE,
    /**
     * 防御力提升百分比*
     */
    DEFENCE_RATE,
    /**
     * 金抗性百分比/克金
     * 敌人用金系法术攻击自身时    免疫百分比 多少的伤害
     */
    RESIST_METAL,
    /**
     * 木抗性百分比
     */
    RESIST_WOOD,
    /**
     * 水抗性百分比
     */
    RESIST_WATER,
    /**
     * 火抗性百分比
     */
    RESIST_FIRE,
    /**
     * 土抗性百分比
     */
    RESIST_EARTH,
    /**
     * 所有抗性百分比
     * 在敌人用所有五行技能法术攻击自身时 免疫百分比 多少的伤害
     */
    ALL_RESIST_POLAR,
    /**
     * 所有抗异常百分比
     * 在敌方障碍自己时 有百分比几率让敌方障碍失败
     */
    ALL_RESIST_EXCEPT,
    /**
     * 抗物理必杀率*
     * 有百分比几率让敌方物理必杀失效
     */
    RESIST_PHY_HIT_KILL_RATE,
    /**
     * 抗法术必杀率*
     * 有百分比几率让敌方法术必杀失效
     */
    RESIST_MANA_HIT_KILL_RATE,
    /**
     * 抗遗忘
     * 有百分比几率使金系障碍技能失败
     */
    RESIST_FORGOTTEN,
    /**
     * 忽视抗遗忘
     */
    IGNORE_RESIST_FORGOTTEN,
    /**
     * 抗中毒
     * 有百分比几率使木系障碍技能失败
     */
    RESIST_POISON,
    /**
     * 忽视抗中毒
     */
    IGNORE_RESIST_POISON,
    /**
     * 抗冰冻
     * 有百分比几率使水系障碍技能失败
     */
    RESIST_FROZEN,
    /**
     * 忽视抗冰冻
     */
    IGNORE_RESIST_FROZEN,
    /**
     * 抗昏睡
     * 有百分比几率使火系障碍技能失败
     */
    RESIST_SLEEP,
    /**
     * 忽视抗昏睡
     */
    IGNORE_RESIST_SLEEP,
    /**
     * 抗混乱
     * 有百分比几率使土系障碍技能失败
     */
    RESIST_CONFUSION,
    /**
     * 忽视抗混乱
     */
    IGNORE_RESIST_CONFUSION,
    /**
     * 忽视目标抗金
     * 对应忽视对方的抗性数值   超过对方抗性数值的百分比  每百分之10多造成法术伤害的十分之一
     */
    IGNORE_RESIST_METAL,
    /**
     * 忽视目标抗木
     */
    IGNORE_RESIST_WOOD,
    /**
     * 忽视目标抗水
     */
    IGNORE_RESIST_WATER,
    /**
     * 忽视目标抗火
     */
    IGNORE_RESIST_FIRE,
    /**
     * 忽视目标抗土
     */
    IGNORE_RESIST_EARTH,
    /**
     * 忽视所有抗性
     */
    IGNORE_ALL_RESIST_POLAR,
    /**
     * 忽视所有抗异常
     * 答应忽视对方的障碍抗性数值 超过对方障碍抗性数值的百分比 对应提高障碍几率
     */
    IGNORE_ALL_RESIST_EXCEPT,
    /**
     * 强力遗忘
     * 强制使自己的金系障碍技能提高对应百分比的成功率
     */
    SUPER_FORGOTTEN,
    /**
     * 强力中毒
     */
    SUPER_POISON,
    /**
     * 强力冰冻
     */
    SUPER_FROZEN,
    /**
     * 强力昏睡
     */
    SUPER_SLEEP,
    /**
     * 强力混乱
     */
    SUPER_CONFUSION,
    /**
     * 法攻技能消耗降低%X
     */
    B_SKILL_LOW_COST,
    /**
     * 障碍技能消耗降低%X
     */
    C_SKILL_LOW_COST,
    /**
     * 辅助技能消耗降低%X
     */
    D_SKILL_LOW_COST,
    /**
     * 降低相应数值的法力消耗*
     */
    SKILL_LOW_COST,
    /**
     * 破防百分比*
     * 如破防%20=忽视对方百分之十的防御数值
     */
    BREAK_DEF_PER,
    /**
     * 破防率*
     * 出现破防效果的几率
     */
    BREAK_DEF_RATE,
    //法术必杀率
    MSTUNT_RATE,
    //强物理伤害
    ENHANCED_PHY2,
    //强力克金
    super_excluse_metal,
    //强力克木
    super_excluse_wood,
    //强力克水
    super_excluse_water,
    //强力克火
    super_excluse_fire,
    //强力克土
    super_excluse_earth,
    //强金法
    enhanced_metal,
    //强木法
    enhanced_wood,
    //强水法
    enhanced_water,
    //强火法
    enhanced_fire,
    //强土法
    enhanced_earth,
    //有几率躲避攻击
    mag_dodge,
    //忽视闪避
    ignore_mag_dodge,
    ;
}
