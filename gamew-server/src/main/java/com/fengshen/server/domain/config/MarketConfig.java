package com.fengshen.server.domain.config;

import lombok.Getter;
import lombok.Setter;

/**
 * 集市配置
 * 
 *
 */
@Getter
@Setter
public class MarketConfig {
	//集市配置
	private Integer status;
	private Integer downGoodTimes;
	//珍宝配置
	private Integer zhenbaoPublicTimes;
	private Integer zhenbaoStatus;
	private Integer zhenbaoDownGoodTimes;
}
