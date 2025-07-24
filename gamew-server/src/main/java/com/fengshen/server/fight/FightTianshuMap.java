package com.fengshen.server.fight;

import java.util.*;

public class FightTianshuMap {
	public static final Map<String, Integer> TIANSHU_EFFECT;

	public static void init() {
		FightTianshuMap.TIANSHU_EFFECT.put("修罗术", 7041);
		FightTianshuMap.TIANSHU_EFFECT.put("降魔斩", 7036);
		FightTianshuMap.TIANSHU_EFFECT.put("怒击", 7039);
		FightTianshuMap.TIANSHU_EFFECT.put("烈炎", 7034);
		FightTianshuMap.TIANSHU_EFFECT.put("仙风", 8050);
		FightTianshuMap.TIANSHU_EFFECT.put("破天", 7040);
		FightTianshuMap.TIANSHU_EFFECT.put("狂暴", 7037);
		FightTianshuMap.TIANSHU_EFFECT.put("惊雷", 7031);
		FightTianshuMap.TIANSHU_EFFECT.put("碎石", 7035);
		FightTianshuMap.TIANSHU_EFFECT.put("反击", 8049);
		FightTianshuMap.TIANSHU_EFFECT.put("青木", 7032);
		FightTianshuMap.TIANSHU_EFFECT.put("尽忠", 8240);
		FightTianshuMap.TIANSHU_EFFECT.put("寒冰", 7033);
		FightTianshuMap.TIANSHU_EFFECT.put("云体", 8051);
		FightTianshuMap.TIANSHU_EFFECT.put("魔引", 7038);
	}

	static {
		TIANSHU_EFFECT = new HashMap<String, Integer>();
	}
}