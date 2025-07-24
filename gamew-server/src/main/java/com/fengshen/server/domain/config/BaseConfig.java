package com.fengshen.server.domain.config;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseConfig {

	public int qingtongChongzhi = 10;
	public int baiyinChongzhi = 10;
	public int huangjinChongzhi = 10;
	public int bojinChongzhi = 10;
	public int zuanshiChongzhi = 10;
	public int xingyaoChongzhi = 10;
	public int wangzheChongzhi = 10;

	private int xiLianLBaseNumber;
	private double xiLianL2Probability;

	private int petXinfaGold;
	private int petXinfaSilver;
	private int petXinfaPoint;
	private int petXinfaBaoDian;
	private int petXinfaMaxLevel;
	private double petXinfaRate;

	private int isChenghaodie = 1;

	public  String zhu1;
	public String zhu2;
	public  String zhu3;
	public  String zhu4;
	public String  zhu5;
	public   String zhu6;
	public  String zhu7;
	public  String zhu8;
	public  String zhu9;

	public String hao1;
	public String hao2;
	public String hao3;
	public String hao4;
	public String hao5;
	public String hao6;
	public String hao7;
	public String hao8;
	public String hao9;

	public int zhuxianCishu = 10;

	public int choujiangDaojuCount = 1;

	private int diyuZongcishu = 18;

	private int guidanCount = 3;
	private int guiwangneidanCount = 6;




	private int xiLianType;
	private double xiLianL1Probability;
	private double xiLianL3Probability;
	private double xiLianL4Probability;
	private Integer xilianOpen;
	private Integer xiLianPoint;
	private Integer xiLianMaxValue;
	//充值链接
	private String chargeLink;
	//后台链接
	private String manageLink;
	//掌门次数
	private Integer zmcs;
	//改造几率
	private Integer gzcgv;
	//强化成功记录
	private Integer qhcgjv;
	//除暴每天次数
	private Integer chubaoNum;
	//师门每天次数
	private Integer shimenNum;
	//白帮忙每天次数
	private Integer baibangmangNum;
	//修行每天次数
	private Integer xiuxingcishuNum;
	//通天塔每天次数
	private Integer tongtiantaNum;
	//八仙每天次数
	private Integer baxianNum;
	//挑战战神次数
	private Integer zhanshenNum;
	//超级boos
	private Integer bossNum;
	//挑战天地星次数
	private Integer tiandixingNum;
	//证道殿
	private Integer zhengdaodianNum;
	//英雄会
	private Integer yingxionghuiNum;
	//地图守护神
	private Integer ditushouhuNum;
	//上古妖王
	private Integer shangguNum;
	//万年妖王
	private Integer wannianNum;
	//修行队伍人数
	private Integer xiuxingDuiwuNum;
	//游戏名字
	private String gameName;
	//真身上线等级
	private Integer realMaxLevel;
	//元血婴最高等级
	private Integer upgradeMaxLevel;
	//飞升仙魔消耗积分
	private Integer flyXianMo;
	//刷星配置
	private Map<String,Object> shuaxing;
	//充值比例
	private int chongzhibili;

	private int shenHunCostPoint;
    private int shenHunCostSilverCoin;
    private int shenHunUpMaxLevel;
    private int shenHunUpType;

	private int yaoqingbili = 50;
	//是否开启维护
	private int stopServer;
	//开口费开关
	private int isChargeSpeak;
	//超级boss次数
	private int superBossNum;
	//加速阈值次数
	private int addSpeedCount = 30;
	//加速阈值时间
	private int addSpeedTime;
	//自动断线时间
	private int autoDisConnectionTime;
	//帮派日常挑战任务次数
	private int partyFightNum;
	//帮派任务次数
	private int partyNum;
	//是否开局关闭动画
	private int closeStartAnimation;
	//万能加速器次数
	private int commonAddSpeedCount;
	//仙界悬赏,默认为2次
	private Integer xuanshangcishu = 2;
	//年兽次数
	private Integer newYearBeastNum = 50;
	//自动数据库备份
	private String authSaveDatabasePath = "C:/wdBackup/";
	//最低喊话等级
	private Integer minSpeakLevel = 51;
	//说话间隔
	private Integer speakIntervalTime = 1;
	//说话间隔次数
	private Integer speakIntervalCount = 5;
	//说话暂停时间
	private Integer speakPauseTime = 5;
	//回收神兽积分
	private int recoveryShenShouScore;
	//回收变异积分
	private int recoveryBianYiScore;
	//是否开启语言，默认为0关闭状态
	private int voiceStatus;
	//人物附灵最低等级
	private int charaFuLingLevel = 100;
	//宠物附灵最低等级
	private int petFuLingLevel = 100;
	//装备回收积分
	private int recoveryEquipScore = 100;
	//宠物继承积分
	private int petInheritScore = 3000;
	//七杀
	private int qishaCount = 3;
	//积分单次抽奖
	private int chargeNpcOnePrice = 2;
	//问道小子客户端地址
	private String xiaoziUrl = "";

	public int totalCheckpoint = 10;
	public int molongCount = 10;
	public int diyushenyuanNum = 10;

}