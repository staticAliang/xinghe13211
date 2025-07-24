package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameObjectChar;
// 战斗容器，包含所有的参战对象
public class FightContainer {
    public int id;
    public List<FightResult> resultList; // 战斗的结果集合，所有参战人员的战后数据
    public int round; // 回合
    public AtomicInteger state; // 战斗类型：自动、手动、倒计时。1是自动战斗。state=4表示战斗已结束
    public List<FightTeam> teamList; // 只有两队，一队是角色，一队是怪物
    public List<FightObject> doActionList; // 保存所有对象的战斗信息
    public Map<Integer,GameObjectChar> lookCharas;//观战玩家
    public long roundTime;
    //开始时间
    public long startTime;
    // 添加保存角色的状态信息，挑战的时候会用到
    public CharaStatue charaStatue;
    // 添加保存整个队伍的角色信息，打地图守护神的时候会用到
    public List<CharaStatue> attCharaStatueList;
    //战斗容器uid
    public String uid;
    //战斗类型
    public String type;
    //火眼金睛持续的回合
    public int hyjjRound;
    //火眼金睛使用者id
    public int hyjjUseCid;
    //战斗记录
    public Map<Integer,List<FightRecord>> fightRecords;
    //参战成员
    public List<Map<String,Object>> fightCharasA;
    public List<Map<String,Object>> fightCharasB;
    //每次结束时间
    public AtomicLong endTime;

    public long beginTime;
    
    public FightContainer() {
    	this.uid = GameCommonUtil.UUID();
        this.id = 100000000;
        this.resultList = new ArrayList<FightResult>();
        this.round = 1; // 初始化为第1回合
        this.state = new AtomicInteger(1); // 默认新建的战斗容器是自动战斗
        this.teamList = new ArrayList<FightTeam>();
        this.roundTime = System.currentTimeMillis();
        this.lookCharas = new HashMap<>();
        this.type = "";
        this.fightRecords = new ConcurrentHashMap<>();
        this.fightCharasA = new ArrayList<>();
        this.fightCharasB = new ArrayList<>();
        this.endTime = new AtomicLong(0);
        this.beginTime = System.currentTimeMillis();
    }
    
    public FightContainer(String type) {
    	this.uid = GameCommonUtil.UUID();
        this.id = 100000000;
        this.resultList = new ArrayList<FightResult>();
        this.round = 1; // 初始化为第1回合
        this.state = new AtomicInteger(1); // 默认新建的战斗容器是自动战斗
        this.teamList = new ArrayList<FightTeam>();
        this.roundTime = System.currentTimeMillis();
        this.lookCharas = new HashMap<>();
        this.type = type;
        this.fightRecords = new ConcurrentHashMap<>();
        this.fightCharasA = new ArrayList<>();
        this.fightCharasB = new ArrayList<>();
        this.endTime = new AtomicLong(0);
        this.beginTime = System.currentTimeMillis();
    }
    
    
    
    public static FightContainer getFightContainer(String uid) {
    	for (FightContainer fightContainer : FightManager.listFight) {
    		if(fightContainer.uid.equals(uid)) {
    			return fightContainer;
    		}
		}
    	return null;
    }
}
