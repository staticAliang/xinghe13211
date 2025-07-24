package com.fengshen.server.fight;
// 定海珠
public class DinghaizhuSkill extends FightFabaoSkill
{
    @Override
    public int getStateType() {
        return 8015;
    }
    
    @Override
    public int getTimes() {
        return (this.level + 3) / 4;
    }
}
