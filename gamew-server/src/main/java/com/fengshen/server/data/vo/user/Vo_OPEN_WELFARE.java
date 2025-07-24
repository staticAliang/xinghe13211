package com.fengshen.server.data.vo.user;

public class Vo_OPEN_WELFARE {
	//神秘大礼下一次剩余时间
	public int leftTime;
	//神秘大礼当前可抽奖次数
	public int times;
	//神秘大礼当天剩余可抽奖次数
	public int leftTimes;
	//每日签到
	public int isCanSign;
	//0 未充值          1 已经充值未领取    2 已经充值已经领取
	public int isCanGetNewPalyerGift;
	//已弃用，服务器未删除该字段
	public int firstChargeState;
	//0 有礼包不可领取          1 有礼包可领取    2 全部领取
	public int cumulativeReward;
	//7日登录礼包
	public int loginGiftState;
	//-1 表示隐藏； 0 表示不可以抽奖， 1 表示允许抽奖
	public int activeCount;
	//-1 表示隐藏； 其他表示可领取的礼包数
	public int holidayCount;
	//1表示可以补签，0表示不可补签
	public int isCanReplenishSign;
	//-1表示活动结束，1表示活动期间
	public int chargePointFlag;
	//-1表示活动结束，1表示活动期间
	public int consumePointFlag;
	//是否显示老玩家回归
	public int isShowHuiGui;
	public int canGetZXQYHuoYue;
	public int canGetZXQYSevenLogin;
	public int canGetActive2020;
	public int returnHelpFlag;
	//是否显示召回道友
	public int isShowZhaohui;
	//是否显示活跃送会员
	public int activeVIPFlag;
	//五折改名卡
	public int rename_discount_time;
	//暑假送福
	public int summerSF2017;
	//暑假送福
	public int zaohua;
	/**
	 * 迎新抽奖活动是否开启，
	 * 其中 -1 = 未开启， 
	 * 1 = 可以抽奖， 0 = 不能抽奖， 2 = 已领奖
	 */
	public int welcomeDrawStatue;
	//-1 时，表示寻道赐福不开启； 为 非负数 时，表示奖励可以领取的次数
	public int activeLoginStatue;
	//-1 时，表示寻道赐福不开启； 为 非负数 时，表示奖励可以领取的次数
	public int xundcf;
	public int mergeLoginStatus;
	public int mergeLoginActiveStatus;
	//回归累充 -1 未开启 ， 1 = 可以领奖， 0 = 不能领奖， 2 = 已领奖
	public int reentryAsktaoRecharge;
	//0 未开启 1 开启
	public int expStoreStatus;
	//-1 看不见，1表示小红点
	public int isShowXYFL;
	//新服盛典
	public int isShowXFSD;
	//内测积分
	public int isShowNCJF;
	//全民PK抽奖 -1 未开启  0 开启没有抽奖次数 >0 开启且有领奖次数
	public int qmpkDrawTimes;
	//地府积分
	public int isDifuPoint;
	//充值双倍界面
	public int double_lottery;
	//新年祈福
	public int new_year_bless_flag;
	//阎罗秘宝活动信息。 	0表示未开启，1表示可领奖，2表示不可领奖
	public int isShowYLMB;
	//-1 未开启    0  不满足领取条件		1  未领取	2  已领取
	public int fixed_team_welfare_flag;
	
	
	//新服助力加成的百分比 -1 未开启
	public int newServeAddNum;
}
