package com.fengshen.server.fight;

import java.util.*;

public class FightMove
{
    public int id;
    public int curMove;
    public int nextMove;
    public static final Map<Integer, FightMove> MOVE_MAP;

    // 当角色关闭了驱魔香并且在地图巡逻走动的时候，碰到怪物的概率函数
    public static boolean move(final int id) {
        FightMove fightMove = FightMove.MOVE_MAP.get(id);
        if (fightMove == null) {
            fightMove = new FightMove();
            fightMove.curMove = 0;
            fightMove.nextMove = 5 + FightManager.RANDOM.nextInt(10);
            FightMove.MOVE_MAP.put(id, fightMove);
            return false;
        }
        final FightMove fightMove3 = fightMove;
        ++fightMove3.curMove;
        if (fightMove.curMove >= fightMove.nextMove) {
            fightMove.curMove = 0;
            fightMove.nextMove = 5 + FightManager.RANDOM.nextInt(10);
            return true;
        }
        return false;
    }
    
    static {
        MOVE_MAP = new HashMap<Integer, FightMove>();
    }
}
