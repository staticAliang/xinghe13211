package com.fengshen.server.domain;

import com.fengshen.server.domain.config.*;

import lombok.Getter;
import lombok.Setter;

/**
 * 游戏配置类
 *
 */
@Getter
@Setter
public class ConfigInit {

	//gm指令
	private Gm gm;
	//副本
	private Fb fb;
	//禁言
	private int allJinyan;
	//限制注册
	private int xianZhiZhuCe;
	//切磋
	private int pkLock;
	//海盗
	private Haidao haidao;
	//聊天过滤
	private Mingan mingan;
	//创建角色
	private NewChara newChara;
	//试道大会
	private ShiDao shidao;
	//基础配置
	private BaseConfig baseConfig;
	//wpe
	private WpeConfig wpeConfig;
	//集市配置
	private MarketConfig marketConfig;
	//幸运大使配置
	private LuckDrawNpcConfig luckDrawNpcConfig;

	private Dari dari;

	private TouDingChengHao touDingChengHao;
}