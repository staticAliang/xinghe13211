package com.fengshen.server.data.vo.tongtianta;

import lombok.Getter;
import lombok.Setter;

/**
 * 通天塔任务信息框
 * 
 *
 */
@Getter
@Setter
public class Vo_TONGTIANTA_INFO {

	//当前层
	private int curLayer;
	//目标层
	private int breakLayer;
	//当前状态 1未完成 2完成
	private int curType;
	//最高层
	private int topLayer;
	//挑战的npc名字
	private String npc;
	//自我突破数量
	private int challengeCount;
	//奖励类型
	private String bonusType;
	//是否完成飞升
	private int hasNotCompletedSmfj;
	//死亡剩余挑战次数
	private int dieNumber;
	//飞升消耗的钱或者元宝
	private int feishengMoney;
	//飞升层数
	private int feishengNumber;
	

	public Vo_TONGTIANTA_INFO() {
		//顶层
		this.topLayer = 200;
		this.dieNumber = 3;
	}
}
