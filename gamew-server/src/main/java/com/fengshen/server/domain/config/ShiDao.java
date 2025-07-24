package com.fengshen.server.domain.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 试道大会配置类
 *
 */
@Getter
@Setter
public class ShiDao {

	// 开启星期
	private String[] week;
	//元魔数量
	private int count;
	// 开启时间
	private String[] times;
	// 队伍人数
	private int teamNumber;
	//阶段1时间
	private long f1;
	//阶段2时间
	private long f2;
	//阶段3时间
	private long f3;
	//第一名奖励
	private Map<String,Object> no1;
	//第二名奖励
	private Map<String,Object> no2;
	//第三名奖励
	private Map<String,Object> no3;
	//其他名次
	private Map<String,Object> no4;
	//开启配置信息
	private Map<String,Integer> openProject;
	//试道闲置时间(秒)
	private int freeTime;
	//试道单个队伍人数
	private String minOneTeamNum;
	//是否关闭主动PK,系统默认是关闭的
	private Integer isCloseActivePk;
	//最大回合数
	private int maxRound = 20;
	
}