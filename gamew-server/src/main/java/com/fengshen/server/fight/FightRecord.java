package com.fengshen.server.fight;

import lombok.Getter;
import lombok.Setter;

/**
 * 战斗记录
 * @author aaa
 *
 */
@Getter
@Setter
public class FightRecord {

	//名字
	private String name;
	//攻击人
	private Integer id;
	//攻击目标名字
	private String vname;
	//攻击目标
	private Integer vid;
	//动作
	private Object action;
	//参数
	private Object para;
	//开始时间
	private Long startTime;
	//当前回合
	private Integer roundNum;
	//是否死亡
	private String isDead;
	//是否复活
	private Boolean isRevive;
	//技能
	private Boolean isTalk;
	//pid
	private int pid;
}