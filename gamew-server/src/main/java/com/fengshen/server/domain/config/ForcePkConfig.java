package com.fengshen.server.domain.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForcePkConfig {

	private Integer lowLevel;
	
	private String securityMap;
	
	private String reviveMap;
	
	private String DieSubInfo;
	
	//价格上涨多少个百分点
	private Integer priceRise = 0;
	
	//坐牢时间
	private Integer zuolaoTime = 60;
	
	//是否开启
	private Integer enableForcePk;
	
	//pk代价
	private String pkMoney;
	
	//保释单价，1/小时
	private int releasePrice = 100;
}
