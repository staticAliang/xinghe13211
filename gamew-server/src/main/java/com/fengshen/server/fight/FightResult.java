package com.fengshen.server.fight;

// 战斗结果
public class FightResult
{
    public int id; // 攻击方
    public int vid; // 被攻击方
    public int effect_no; // 10005为木的辅助效果，10005为复活
    public int damage_type; // 1是普攻伤害，2是技能伤害，0是辅助效果，4是障碍木
    public int point; // 伤害

    @Override
    public String toString() {
        return "FightResult{" +
                "id=" + id +
                ", vid=" + vid +
                ", effect_no=" + effect_no +
                ", damage_type=" + damage_type +
                ", point=" + point +
                '}';
    }
}
