package com.fengshen.server.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色独立信息
 * 
 * 
 *
 */
public class CharaBaseInfo {
	// 力量（面板的力量=角色力量+装备力量）
	public int phy_power;
	// 体质(面板)
	public int life;
	// 敏捷(面板)
	public int speed;
	// 灵力(面板)
	public int mag_power;
	// 物伤
	public int accurate;
	// 气血
	public int def;
	// 法力
	public int dex;
	// 防御
	public int wiz;
	// 法伤
	public int mana;
	// 速度
	public int parry;
	// 最大气血
	public int max_life;
	// 最大法力
	public int max_mana;

	public int metal;// 金相性
	public int wood;// 木相性
	public int water;// 水相性
	public int fire;// 火
	public int earth;// 土
	public int attribPoint; // 剩余属性点
	public int polarPoint; // 剩余相性点

	// 宠物参战id
	public int chongwuchanzhanId;
	// 添加宠物掠阵的id
	public int chongwuluezhenId;
	//坐外观
	public int zuowaiguan;
	//坐骑移动速度
	public int yidongsudu;
	//坐骑id
	public int zuoqiId;
	//坐骑外观
	public int zuoqiwaiguan;
	// 道行的总天数。
	public int tao;
	// 零碎的道行点。多少天道行，一天的道行点是1440
	public int taoPoint;
	//是否选择自动技能
	public int autofight_select;
	//自动技能动作
	public int autofight_skillaction;
	//自动技能编号
	public int autofight_skillno;
	//人物属性自动加点
	public Map<String, Object> userAutoAddPoint = new LinkedHashMap<String, Object>();
	//技能
	public List<JiNeng> jiNengList;
	//装备
	public Map<Integer,Goods> equip;
	//装备页码
	public int equipPage;
	
	public CharaBaseInfo() {
		this.phy_power = 1;
		this.life =1;
		this.speed=1;
		this.mag_power=1;
		this.equip = new HashMap<>();
		this.jiNengList = new ArrayList<>();
	}
	
}