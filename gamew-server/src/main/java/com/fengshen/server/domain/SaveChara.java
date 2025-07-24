package com.fengshen.server.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fengshen.server.data.vo.Vo_41480_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.data.vo.chat.Vo_DECORATION_LIST.Items;
import com.fengshen.server.data.vo.tongtianta.Vo_TONGTIANTA_INFO;
import com.fengshen.server.data.vo.user.DAILY_STATS_INFO;

import lombok.Getter;
import lombok.Setter;

/**
 * 这个是转换成data里面需要的数据
 */
@Getter
@Setter
public class SaveChara {

public int shenhunUpLevel;
	/** 拥有的积分 */
	public int integral;
	/** 累计的积分 */
	public int totalIntegral;

	public int curCheckpoint = 0;
	
	
	public int zhuxian_ceng = 1;
	public int zhuxian_cishu = 0;

	public int totalCheckpoint = 0;
	public int molongCount = 0;
	public int diyushenyuanNum = 0;
	public int cengshu = 1;
	public int molongIndex = 0;
	public int diyu_ceng = 1;
	public int diyu_cishu = 0;
	public int zhuan = 0;

	public int dir;// 朝向
	public int flyPetID = 0;
	// 挑战地图守护神的次数
	public int mapguardcishu = 0;
	// 挑战证道殿的次数
	public int zhengdaodiancishu = 0;
	// 挑战英雄会次数
	public int heropubcishu = 0;
	// 攻城BOSS刷的次数
	public int gongchengcishu = 0;
	// 角色已经刷了多少次战神
	public int zhanshencishu = 0;
	// 角色已经刷了多少次海盗
	public int haidaocishu = 0;
	// 角色已经刷了多少次上古
	public int shanggucishu = 0;
	// 角色已经开启了多少福袋
	public int fuDaiNumber = 0;
	// 角色已经刷了多少次万年
	public int wanniancishu = 0;
	// 今天的修法任务次数
	public int xiufacishu = 0; // 做完一次涨1，第四次做完等于4。
	// 修法NPC的名称
	public String xiufaNpcName;

	// 证道殿-护法留言
	public String zdd_Notice;
	// 英雄会-留言
	public String yxh_Notice;
	// 挑战掌门-掌门留言
	public String leaderNotice;
	// 首饰强化次数
	public int shoushiQianghuacishu;
	// 首饰精华数量
	public int jewelry_essence = 0;
	public int allId;

	public List<JiNeng> jiNengList;
	public ZbAttribute zbAttribute; // 角色的所有装备的属性

	public List<Vo_41480_0> shenmiliwu;
	public int chargeScore; // 积分数量
	public String chenhao; // 称号
	public int line;
	public String current_task;
	public int life;// 体质(面板)
	public int mag_power;// 灵力(面板)
	public int phy_power; // 力量(面板)
	public int speed;// 敏捷(面板)
	public int accurate; // 物伤
	public int def; // 气血
	public int dex; // 法力
	public int wiz; // 防御
	public int mana; // 法伤
	public int parry; // 速度
	public int exp; // 当前角色经验
	public int expToNextLevel; // 到下一级的需要多少经验
	public int extra_mana; // 气血储备
	public int have_coin_pwd; // 法力储备
	public int use_skill_d; // 宠物忠诚储备
	public int metal;// 金相性
	public int wood;// 金相性
	public int water;// 木相性
	public int fire;// 水
	public int earth;// 火
	public int attribPoint; // 剩余属性点
	public int polarPoint; // 剩余相性点
	public int max_life; // 最大气血
	public int max_mana; // 最大法力
	public int use_money_type; // 代金券
	public int shadow_self; // 抽奖数量
	public int weapon_icon; // 武器外观
	public int silverCoin; // 银元宝
	public int goldCoin; // 金元宝
	public int cash; // 金币
	public int jishou_coin;
	public int lock_exp; // 锁经验,0关1开
	public int chongwuchanzhanId; // 宠物参战id
	public int chongwuluezhenId;
	public int pot; // 角色潜能
	public long uptime;
	public long updatetime; // 角色上一次的更新时间，登陆和退出时会更新
	public long online_time; // 角色在线时长
	public int signDays;
	public int isCanSgin;
	public int canzhanshouhunumber;
	public int zuoqiwaiguan; // 这个是角色骑了坐骑的外观，没有骑则为0，骑了就非0
	public int zuoqiId; // 骑着的坐骑ID
	public int yidongsudu; // 坐骑带来的移动速度增益
	public int zuowaiguan; // 坐姿的外观
	public int special_icon;// 特殊图标
	public int genchong_icon;
	public int vipType;
	public int isGet;
	public int vipTime;
	public int vipTimeShengYu;
	public int suit_icon;
	public int suit_light_effect;
	public int wuxingBalance;
	public int enable_double_points;// 是否开启双倍 0:未开启 1:开启
	public int enable_shenmu_points;// 对于双倍点数
	public int chushi_ex;
	public int fetch_nice;
	public int shuadaochongfeng_san;
	public int[] xinshoulibao;
	public int[] levelUpReward;
	public Map<Integer,Vo_APPEAR> shudao;
	public int shuadao; // 刷道的次数，每个角色的初始值是1
	public int chubao; // 除暴完成次数(比实际多1次)
	public List<Vo_APPEAR> npcchubao; // 除暴任务列表
	public int baibangmang;
	public int shimencishu; // 师门完成次数(比实际多1次)，初始为0
	public int fabaorenwu; // 找龙王领取法宝任务
	public int xiuxingcishu; // 修行次数,初始值为1
	public String xiuxingNpcname; // 修行的NPC名称
	public int xuanshangcishu; // 悬赏次数
	public int autofight_select;
	public int autofight_skillaction;
	public int autofight_skillno;
	public int tao; // 道行的总天数。
	public int taoPoint; // 零碎的道行点。多少天道行，一天的道行点是1440
	public Map<String, String> chenghao;
	public List<CharaChengWei> charaChengWeis;
	public int qumoxiang; // 驱魔香开关
	public int charashuangbei;
	public int shenmoding;
	// 紫气鸿蒙数量
	public int ziqihongmeng;
	public int chongfengsan;
	// 紫气鸿蒙状态
	public int ziqihongmengState;
	// 急急如律令状态
	public int jijirulvlingState;
	// 急急如律令点数
	public int jijirulvling;
	// 如意刷道令
	public int ruyishuadaoState;
	// 宠风散金钱购买次数
	public int chongfengsanMoneyNum;
	// 紫气鸿蒙金钱购买次数
	public int ziqihongmengMoneyNum;
	public int ruyishuadao;
	public int fbjl;
	public int vipyuanbaolingqu;
	public int tongttcishu;
	public String zhangmenshijiantime;
	public int zhangmentiaozhan; // 挑战掌门次数
	public int baxiantiaozhan; // 挑战八仙次数
	public int fb_num; // 挑战副本的数量
	public String other;
	public Map<String, Vo_61553_0> taskMap;
	// 是否禁言 0未禁言1禁言
	public int shut;
	// 下一个剧本
	public int nextJuBen = 0;
	// 当前剧本
	public String[] currentJuBens = null;
	// 剧本队伍共享
	public boolean jubenAllTeam = false;
	// 是否在战斗.
	public boolean isFight = false;
	// 当前战斗id
	public int zhandouId;
	// 战斗信息
	public String zhandouInfo = "";
	// 当前确认框操作
	public String currentConfirmItem;
	// 变身卡信息
	public VoChangeCard changeCardInfo;
	// 帮派名称
	private String partyName;
	// 人物属性自动加点
	public Map<String, Object> userAutoAddPoint = new LinkedHashMap<String, Object>();
	// 宠物属性自动加点
	private Map<String, Object> petAutoAddPoint = new LinkedHashMap<String, Object>();
	// 通天塔内任务框
	public Vo_TONGTIANTA_INFO tongtiantaTask;
	// 普通任务,第二天清空
	public Map<String, Vo_61553_0> commonTaskMap = new HashMap<>(); // 当前的任务映射
	// 试道相关
	public int shidaodaguaijifen;
	// 试道对决值
	public int shidaoPkSocre;
	// 试道每一届的积分
	public int shidaoScore;
	// 系统设置
	private Map<String, Integer> settings;
	// 拒绝等级
	public int settingrefuse_stranger_level;
	// 自动回复消息
	public String settingauto_reply_msg;
	// 拒绝等级
	public int setting_refuse_be_add_level;
	// 添加对方好友验证的gid
	public String toVerifyFriendGid;
	// 邮件操作
	public int isMailBox;
	// 珍宝收入
	public int sellCash;
	// 是否完成飞升
	public int isFeisheng;
	// 元婴类型
	public int upgrade_type;
	// 元婴状态
	public int upgrade_state;
	// 元婴等级
	public int upgrade_level;
	// 元婴经验
	public int upgrade_exp;
	// 元婴下一级经验
	public int upgrade_exp_to_next_level;
	// 元婴最大经验
	public int upgrade_max_polar_extra;
	// 是否使用了坐骑 0 未使用 1使用
	public int isDownZuoQi;
	// 真身等级
	public int realLevel;
	// 元婴
	public CharaBaseInfo charaYuanyingInfo;
	// 真身
	public CharaBaseInfo charaRealInfo;
	// 仙道点
	public int upgrade_immortal;
	//魔到点
	public int upgrade_magic;
	// 仙魔道点
	public int upgrade_total;
	// 天劫
	public int tianjie;
	// 帮派职位
	public String partyJob;
	// 上个帮派名字
	public String upPartyName;
	// 帮贡
	public int contrib;
	// 帮派日常挑战
	public int partyFightNum;
	// 帮派任务
	public int partyNum;
	/* 以下是内丹神魄数据 */

	// 神魂阶数
	public int shenHunDataSate;
	// 当前阶数层级
	public int shenHunDataLaye;
	// 神魂是否到达等级
	public int shenHunIsTop;
	// 内丹状态 0未获得 1获得
	public int danDataState;
	// 内丹阶级
	public int danDataStage;
	// 内丹经验
	public int danDataExp;
	// 内丹下一级经验
	public int danDataExpToNextLevel;
	// 内丹属性点
	public int danDataAttribPoint;
	// 内丹剩余属性点
	public int danDataPolarPoint;
	// 内丹是否达到顶层
	public int isNeiDanTop;
	/* 神魄数据 */
	// 物伤
	public int shenHunPhyPower;
	// 防御
	public int shenHunDef;
	// 气血
	public int shenHunmaxLife;
	// 法伤
	public int shenHunMagPower;
	// 速度
	public int shenHunSpeed;
	// 变身卡
	public int cardSize;
	// 刷道积分
	public int shuadaoScore;
	// 刷道积分领取次数
	public int shuadaoFetchState;
	// 家园
	public int family;
	// 是否红名
	public int isNameRed;
	// 强制PK值
	public int forcePk;
	// 超级boss奖励次数
	public int superBossNum;
	// 当前是那个装备
	public int equipPage;
	// 自定义外观
	public String customIcon;
	// 特效
	public Map<String, Integer> effectIcons = new HashMap<>();
	// 是否完成百级拜师
	public int isFinish100Task;
	// 擂台分
	public int leitaiScore;
	// 聊天底框
	public String useChatFloor;
	// 聊天头像框
	public String useChatHead;
	// 聊天装饰
	public List<Items> chatFloors = new ArrayList<>();
	public List<Items> chatHeads = new ArrayList<>();
	public int teamIcon;
	// 伴侣id
	public int marriageMarryId;
	// 伴侣姓名
	public String marriageName;
	// 结婚时间
	public long marriageTime;
	// 年兽次数
	public int newYearBeastNum;
	// 天地星
	public int tiandixingNum;
	// 试道经验
	public int shidaoExp;
	// 试道道行
	public int shidaoTao;
	//
	public int shuaijianCishu;
	public int diffTime;
	public String npcName;
	// 试道武学
	public int shidaoMartial;
	// 月道行
	public int monthTao;
	// 共通宠物id
	public int awardSupplyPetId;
	// 附灵类型,四大神兽
	public int zhenlingType;
	// 附灵等级
	public int zhenlingLevel;
	// 附灵阶层
	public int zhenlingStage;
	// 附灵经验
	public int zhenlingExp;
	// 附灵物伤
	public int zhenlingPhy;
	// 附灵法伤
	public int zhenlingMag;
	// 附灵速度
	public int zhenlingSpeed;
	// 附灵防御
	public int zhenlingDef;
	// 青龙真灵
	public int qinglongZhenlingLevel;
	// 白虎真灵
	public int baihuhenlingLevel;
	// 朱雀真灵
	public int zhuqueZhenlingLevel;
	// 玄武真灵
	public int xuanwuZhenlingLevel;
	//仙魔道点自动配置类型
	public int upgradeAddType;
	//是否打开仙魔道点自动加点
	public int upgradeIsOpen;
	//五行竞猜次数
	public int wuxingCount;
	//自动补充
	public int autofight_supplement;
	//今日统计
	public DAILY_STATS_INFO dayInfo;
	//赠送次数
	public int sendGivingCount;
	//接收次数
	public int getGivingCount;
	//试道淘汰时间
	public long shidaoOutTime;
	//坐牢时间
	public long crimeTime;
	//固定队
	public String fixedTeamName;
	//是否领取充值福利
	public int isGetChargeFuLi;
	//上个门派
	public int oldPolar;
	//当天最大固定点
	public int fixedTeamPoint;
	//擂台奖励次数
	public int ctCount;
	//擂台积分--用于消耗
	public int ctDataScoreCost;
	//七杀
	public int qishaCount;
	//开启七杀次数
	public int openQiShaCount;
	//喊话
	public List<AutoTalkVo> autoTalk;
	// 洛书等级
	public int luoshuLevel;
	public boolean islingqufuyao;
	// 洛书经验
	public int luoshuExp;
	public int fuyaoExp;
	public int fuyaoXiaolv;
	public int fuyao;
	// 洛书加成防御
	public int luoshuDefense;
	// 洛书加成法伤
	public int luoshuMagpower;
	// 洛书加成速度
	public int luoshuSpeed;
	// 洛书加成物理
	public int luoshumPhypower;
}
