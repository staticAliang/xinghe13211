package com.fengshen.server.game;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.server.util.GameConfig;

/**
 * a标准公示
 * @author aaa
 *
 */
public class Formula {

	//获取标准气血
	public static int getStdLife(int level) {
		JSONObject stdLife = GameConfig.stdValue.getJSONObject("stdLife");
		Integer stdValue = stdLife.getInteger(String.valueOf(level));
		if(stdValue != null) {
			return stdValue;
		}
		level = level-1;
		return (int) ((1.39*level*level)+85*level+100);
	}
	
	//标准法力
	public static int getStdMana(int level) {
		JSONObject stdLife = GameConfig.stdValue.getJSONObject("stdLife");
		Integer stdValue = stdLife.getInteger(String.valueOf(level));
		if(stdValue != null) {
			return stdValue;
		}
		level = level-1;
		return (int) ((1.39*level*level)+85*level+100);
	}
	
	//标准速度
	public static int getStdSpeed(int level) {
		JSONObject stdLife = GameConfig.stdValue.getJSONObject("stdLife");
		Integer stdValue = stdLife.getInteger(String.valueOf(level));
		if(stdValue != null) {
			return stdValue;
		}
		level = level-1;
		return (int) ((1.39*level*level)+85*level+100);
	}
}