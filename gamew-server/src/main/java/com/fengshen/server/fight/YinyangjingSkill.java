package com.fengshen.server.fight;
// 阴阳镜
public class YinyangjingSkill extends FightFabaoSkill
{
    @Override
    public int getStateType() {
        return 8014;
    }
    
    @Override
    public int getTimes() {
        return (this.level + 3) / 4;
    }
}
