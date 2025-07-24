package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.List;

public class FightTeam {
	public List<FightObject> fightObjectList;
	public int leader;
	public int type; // 1是友方，2是敌方

	public FightTeam() {
		this.fightObjectList = new ArrayList<FightObject>();
	}

	public void add(FightObject fo) {
		this.fightObjectList.add(fo);
	}

	public void remove(FightObject fo) {
		this.fightObjectList.remove(fo);
	}

}