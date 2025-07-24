package com.fengshen.server.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.AESUtil;
import com.fengshen.core.util.DateUtil;
import com.fengshen.core.util.ExecutorsUtils;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.ChangeCard;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.CharaTrail;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.domain.Friend;
import com.fengshen.db.domain.FriendGroup;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.domain.LivenessRewards;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.db.domain.Party;
import com.fengshen.db.domain.PartyMember;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.db.domain.StallRecord;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.db.service.chara.LivenessRewardsService;
import com.fengshen.db.service.zhenbao.GoldStallNineGoodsService;
import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.constant.FlyType;
import com.fengshen.server.data.constant.SellOrBuyRecordType;
import com.fengshen.server.data.constant.StallRecordType;
import com.fengshen.server.data.constant.StallStatus;
import com.fengshen.server.data.constant.TransferItemType;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.ChangeCardAttr;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_11757_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_16383_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.data.vo.Vo_32855_0;
import com.fengshen.server.data.vo.Vo_32985_0;
import com.fengshen.server.data.vo.Vo_33055_0;
import com.fengshen.server.data.vo.Vo_3583_0;
import com.fengshen.server.data.vo.Vo_36871_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_40965_0;
import com.fengshen.server.data.vo.Vo_40981_0;
import com.fengshen.server.data.vo.Vo_4099_0;
import com.fengshen.server.data.vo.Vo_41009_0;
import com.fengshen.server.data.vo.Vo_41023_0;
import com.fengshen.server.data.vo.Vo_4163_0;
import com.fengshen.server.data.vo.Vo_4321_0;
import com.fengshen.server.data.vo.Vo_45277_0;
import com.fengshen.server.data.vo.Vo_49179_0;
import com.fengshen.server.data.vo.Vo_53399_0;
import com.fengshen.server.data.vo.Vo_53521_0;
import com.fengshen.server.data.vo.Vo_53925_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_61589_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.vo.Vo_62209_0;
import com.fengshen.server.data.vo.Vo_7653_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_8425_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.Vo_FIGHT_CMD_INFO;
import com.fengshen.server.data.vo.Vo_GODBOOK_EFFECT;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.vo.Vo_TITLE;
import com.fengshen.server.data.vo.achieve.Vo_ACHIEVE_CONFIG;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.data.vo.chat.Vo_DECORATION_LIST;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.data.vo.fight.Vo_ADD_FRIEND_OPPONENT;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_CHAR_DIED;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_COMBAT;
import com.fengshen.server.data.vo.fight.Vo_C_SET_CUSTOM_MSG;
import com.fengshen.server.data.vo.friend.Vo_ADD_FRIEND_OPER;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_GROUP;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_GROUP_LIST;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_UPDATE_LISTS;
import com.fengshen.server.data.vo.hunpo.Vo_REFRESH_NEIDAN_DATA;
import com.fengshen.server.data.vo.hunpo.Vo_REFRESH_SHENHUN_DATA;
import com.fengshen.server.data.vo.hunpo.Vo_REFRESH_SHENHUN_DATA.Vo_REFRESH_SHENHUN_DATA_ITEM;
import com.fengshen.server.data.vo.identity.Vo_FUZZY_IDENTITY;
import com.fengshen.server.data.vo.identity.Vo_UPDATE_ANTIADDICTION_STATUS;
import com.fengshen.server.data.vo.pet.Vo_PET_STORE;
import com.fengshen.server.data.vo.safelock.Vo_SAFE_LOCK_OPEN_UNLOCK;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_TASK_INFO;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.vo.user.Vo_ANIMATE_IN_CHAR;
import com.fengshen.server.data.vo.user.Vo_CHANGE_POLAR_DATA;
import com.fengshen.server.data.vo.user.Vo_CL_CARD_INFO;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_GOODS_LIST;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_MINE.Vo_GOLD_STALL_MINE_Items;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.data.write.M11757_0;
import com.fengshen.server.data.write.M12016_0;
import com.fengshen.server.data.write.M12023_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M32747_0;
import com.fengshen.server.data.write.M32855_0;
import com.fengshen.server.data.write.M32985_0;
import com.fengshen.server.data.write.M33055_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M40965_0;
import com.fengshen.server.data.write.M40981_0;
import com.fengshen.server.data.write.M4099_0;
import com.fengshen.server.data.write.M41009_0;
import com.fengshen.server.data.write.M41017_0;
import com.fengshen.server.data.write.M41023_0;
import com.fengshen.server.data.write.M4163_0;
import com.fengshen.server.data.write.M4321_0;
import com.fengshen.server.data.write.M45277_0;
import com.fengshen.server.data.write.M45388_0;
import com.fengshen.server.data.write.M53399_0;
import com.fengshen.server.data.write.M53521_0;
import com.fengshen.server.data.write.M53925_0;
import com.fengshen.server.data.write.M61589_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.M62209_0;
import com.fengshen.server.data.write.M64981_Fight_Blood;
import com.fengshen.server.data.write.M65511_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M65527_1;
import com.fengshen.server.data.write.M65527_4;
import com.fengshen.server.data.write.M7653_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.M8425_0;
import com.fengshen.server.data.write.MSG_FIGHT_CMD_INFO;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.data.write.MSG_REQUEST_SERVER_STATUS;
import com.fengshen.server.data.write.MSG_TITLE;
import com.fengshen.server.data.write.achieve.MSG_ACHIEVE_CONFIG;
import com.fengshen.server.data.write.animate.MSG_ANIMATE_IN_CHAR_LAYER;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.chat.MSG_DECORATION_LIST;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_CHAR_DIED;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_COMBAT;
import com.fengshen.server.data.write.fight.c.MSG_C_FRIENDS;
import com.fengshen.server.data.write.fight.c.MSG_C_OPPONENTS;
import com.fengshen.server.data.write.fight.c.MSG_C_START_COMBAT;
import com.fengshen.server.data.write.fight.lc.MSG_LC_UPDATE_STATUS;
import com.fengshen.server.data.write.friend.MSG_ADD_FRIEND_OPER;
import com.fengshen.server.data.write.friend.MSG_FRIEND_ADD_CHAR;
import com.fengshen.server.data.write.friend.MSG_FRIEND_GROUP_LIST;
import com.fengshen.server.data.write.friend.MSG_FRIEND_NOTIFICATION;
import com.fengshen.server.data.write.friend.MSG_FRIEND_UPDATE_LISTS;
import com.fengshen.server.data.write.hunpo.MSG_REFRESH_NEIDAN_DATA;
import com.fengshen.server.data.write.hunpo.MSG_REFRESH_SHENHUN_DATA;
import com.fengshen.server.data.write.identity.MSG_FUZZY_IDENTITY;
import com.fengshen.server.data.write.identity.MSG_UPDATE_ANTIADDICTION_STATUS;
import com.fengshen.server.data.write.look.MSG_LC_START_LOOKON;
import com.fengshen.server.data.write.market.M49179_0;
import com.fengshen.server.data.write.pet.MSG_GODBOOK_EFFECT_NORMAL;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_OPEN_SET;
import com.fengshen.server.data.write.safelock.MSG_SAFE_LOCK_OPEN_UNLOCK;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_TASK_INFO;
import com.fengshen.server.data.write.store.MSG_PET_STORE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.data.write.system.MSG_SET_SETTING;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_STOP_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.data.write.user.MSG_CHANGE_POLAR_DATA;
import com.fengshen.server.data.write.user.MSG_CL_CARD_INFO;
import com.fengshen.server.data.write.user.MSG_GHOSTDOM_INFO;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_GOODS_LIST;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_UPDATE_GOODS_INFO;
import com.fengshen.server.domain.BuildFields;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaBaseInfo;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsBasics;
import com.fengshen.server.domain.GoodsFenSe;
import com.fengshen.server.domain.GoodsGaiZao;
import com.fengshen.server.domain.GoodsGaiZaoGongMing;
import com.fengshen.server.domain.GoodsHuangSe;
import com.fengshen.server.domain.GoodsHunqi;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.GoodsLvSe;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.ShouHu;
import com.fengshen.server.domain.config.ForcePkConfig;
import com.fengshen.server.domain.config.LuckDrawNpcConfig;
import com.fengshen.server.domain.config.NeiDanConfig;
import com.fengshen.server.domain.config.NeiDanVo;
import com.fengshen.server.exception.PackOverflowException;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.job.SaveCharaTimes;
import com.fengshen.server.process.hunpo.CMD_SHENHUN_BREAK;
import com.fengshen.server.process.system.CMD_SELECT_MENU_ITEM;
import com.fengshen.server.util.BeanUtils;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.GameConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;
//import org.apache.commons.lang.StringUtils;

import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * 普通工具类
 */
@Slf4j
public class GameCommonUtil {

	// 在角色内部播放
	public int posInner = 1;
	// 在角色头顶
	public int posTop = 1;
	// 元魔开始时间
	public static long yuanmoStartTime;
	// pk开始时间
	public static long pkStartTime;
	// 版本号
	public static String gameVersion = "v1.2.2";

	public static String[] speedNo6 = { "岳麓剑", "古鹿", "北极熊", "筋斗云" };

	public static String[] speedNo8 = { "墨麒麟", "太极熊" };

	public static String[] fuzhan_name = { "齐天大圣", "后裔星神", "巨斧大帝", "盾甲仙君" };

	public static String[] chaoshenshou_name = { "绝世仙子", "御剑上仙", "孤独剑圣", "大罗金仙" };

	public static String[] shenshou_name = { "朱雀", "玄武", "九尾狐", "疆良" };

	public static String[] fabao_name = { "混元金斗", "番天印", "定海珠", "九龙神火罩" };

	public static String[] xiao_info = { "对方正忙！", "对方关闭了pk" };

	public static String[] shimen_tongzi = new String[] { "云霄童子", "碧玉童子", "水灵童子", "赤霞童子", "彩云童子" };

	public static String[] shimen_shizun = new String[] { "文殊天尊", "云中子", "龙吉公主", "太乙真人", "石矶娘娘" };

	public static String[] shimen_zhanglao = new String[] { "云霄长老", "玉柱长老", "斗阙长老", "金光长老", "白骨长老" };

	public static int[] shimen_shizun_icon = new int[] { 6052, 6053, 6054, 6055, 6056 };

	public static int[] shimen_tongzi_icon = new int[] { 6020, 6021, 6022, 6023, 6024 };

	public static int[] shimen_zhanglao_icon = new int[] { 20033, 20034, 20035, 20036, 20037 };
	// 五大派系外观
	public static int[] chara_icon = new int[] { 6001, 7002, 7003, 6004, 6005 };
	// 过滤的字符
	public static String filterStr = "[- _`~!@#$%^&*()--+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t▓";

	// 特殊变身卡
	public static String[] bianyiCard = { "超级伶俐鼠卡", "超级笨笨牛卡", "超级威威虎卡", "超级跳跳兔卡", "超级酷酷龙卡", "超级花花蛇卡", "超级溜溜马卡",
			"超级咩咩羊卡", "超级帅帅猴卡", "超级蛋蛋鸡卡", "超级乖乖狗卡", "超级招财猪卡", "超级九华卡", "超级陌玉卡", "超级馥汀卡", "超级幽雪卡" };
	// 神兽卡
	public static String[] shenShouCard = { "超级疆良卡", "超级东山神灵卡", "超级玄武卡", "超级朱雀卡", "超级九尾狐卡", "超级白矖卡" };
	// boss卡
	public static String[] bossCard = { "超级羊头怪卡", "超级牛头怪卡", "超级黑熊精卡", "超级狂狮怪卡", "超级刺猬精卡", "超级猪妖卡", "超级象精卡", "超级百花羞卡",
			"超级牛魔王卡", "超级夜叉王卡", "超级罗刹王卡", "超级白骨精卡" };

	// 天墉城擂台坐标点
	public static int[][] leitai_pt = new int[][] { { 186, 70 }, { 212, 86 }, { 185, 100 }, { 159, 86 } };
	// 安全密码盐值
	public static String safeLockKey = "superman";
	//角色存储
	public static String charaKey = "CHARA_DATA_";
	//boss自增id
	private static AtomicInteger bossAutoId = new AtomicInteger(999999);

	/**
	 * 在某个角色播放动画,一直循环播放
	 * 
	 * @param chara
	 * @param effectNo
	 * @param pos
	 * @param loops
	 * @param interval
	 */
	public static void charaPlayWhile(Chara chara, int effectNo, int pos) {
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("id", chara.id);
		map.put("effect_no", effectNo);
		map.put("order", chara.id);
		map.put("pos", (byte) pos);
		// 循环多少次
		map.put("loops", (long) 0);
		// 间隔
		map.put("interval", (long) 100000);
		// 在某个时刻
		map.put("during", (long) 0);
		GameObjectCharMng.getGameObjectChar(chara.id).gameMap.send(new CommonWrite(0xB073), map);
	}

	public static void resetShenHunData(Chara chara, GameObjectChar gameObject) {
		if (chara.shenHunDataSate < 1) {
			chara.shenHunDataSate = 1;
		}
		final int shenHunDataSate = chara.shenHunDataSate;
		final int shenHunDataLaye = chara.shenHunDataLaye;
		if (shenHunDataSate < 10 || shenHunDataLaye < 10) {
			chara.shenHunIsTop = 0;
		}
		chara.shenHunPhyPower = 0;
		chara.shenHunDef = 0;
		chara.shenHunmaxLife = 0;
		chara.shenHunMagPower = 0;
		chara.shenHunSpeed = 0;
		GameObjectChar.GAMEOBJECTCHAR_THREAD_LOCAL.set(gameObject);
		for (int i = 1; i <= shenHunDataSate; i++) {
			chara.shenHunDataSate = i;
			int laye = 10;
			if (i == shenHunDataSate) {
				laye = shenHunDataLaye;
			}
			for (int l = 1; l <= laye; l++) {
				chara.shenHunDataLaye = l;
				CMD_SHENHUN_BREAK.processShenHunAttr(chara); 
			}
		}
		GameCommonUtil.refreShenHun(chara);
	}

	 public static void refreshAppellAtion(final Chara chara) {

        final ChengweiService chengweiService = SpringBeanUtils.getBean(ChengweiService.class);
        List<Chengwei> chengweis = chengweiService.getAllReChargeChengwei();
        StringBuilder chenghaoShuoming = new StringBuilder();
        StringBuilder chenghaoStr = new StringBuilder();//当前称谓总加成：所以相性***
        Map<String, Integer> chengHaoMap = new HashMap<>();
        for (Chengwei chengwei : chengweis) {
            //拥有称号
            boolean hasChenhao = chara.chenghao.containsKey(chengwei.getName());
            if (hasChenhao) {
                chenghaoShuoming.append("#R").append(chengwei.getName()).append(":");
                chenghaoShuoming.append("#Y ");
            } else {
                chenghaoShuoming.append("#G").append(chengwei.getName()).append(":");
                chenghaoShuoming.append("#B ");
            }
            if (!StringUtils.isNullOrEmpty( chengwei.getAttr())) {
                JSONArray parse = JSONObject.parseArray(chengwei.getAttr());
                if (parse != null && !parse.isEmpty()) {
                    for (int i = 0; i < parse.size(); i++) {
                        com.alibaba.fastjson.JSONObject jsonObject = parse.getJSONObject(i);
                        String field = jsonObject.getString("field");
                        String value = jsonObject.getString("value");
                        chenghaoShuoming.append(field).append(" +").append(value);
                        if ("所有基础属性".equals(field)) {
                            chenghaoShuoming.append("%");
                        }
                        chenghaoShuoming.append(";");
                       //StrUtil.containsAny(field, "所有相性", "所有基础属性", "所有属性")
						boolean is1 = field.indexOf("所有相性") != -1 ;
						boolean is2 = field.indexOf("所有基础属性") != -1 ;
						boolean is3 = field.indexOf("所有属性") != -1 ;
						boolean is = is1 || is2 || is3;

						if (hasChenhao && is ) {
                            Integer integer = chengHaoMap.get(field);
                            if (integer == null) {
                                chengHaoMap.put(field, Integer.valueOf(value));
                            } else {
                                chengHaoMap.put(field, Integer.valueOf(value) + integer);
                            }
                        }
                    }
                    if (hasChenhao) {
                        chenghaoShuoming.append("#R(已获取)");
                    } else {
                    	String remake = chengwei.getRemake();
                        chenghaoShuoming.append("#W("+remake+")");
                    }
                }
            }
            chenghaoShuoming.append("\n");
        }
        for (Entry<String, Integer> entry : chengHaoMap.entrySet()) {
            chenghaoStr.append("#Y").append(entry.getKey()).append("#R+").append(entry.getValue());
            if ("所有基础属性".equals(entry.getKey())) {
                chenghaoStr.append("%");
            }
            chenghaoStr.append("#n");
        }
		 GameUtilRenWu.createTask("称号信息", 0, chenghaoStr.toString(), "称谓总增幅", chara,
				 "称号属性加成说明\n您在本服充值后，可以获取拥有属性增幅的称谓，您当前已激活 属性增幅如下，本属性是永久有效，不会过期。祝您和您的队友玩的开心愉快", chenghaoShuoming.toString());
    }

	/**
	 * 只播放一次
	 * 
	 * @param chara
	 * @param effectNo
	 * @param pos
	 */
	public static void charaPlay(GameObjectChar gameObjectChar, int effectNo, int pos) {
		Chara chara = gameObjectChar.chara;
		Vo_ANIMATE_IN_CHAR obj = new Vo_ANIMATE_IN_CHAR();
		obj.setEffectNo(effectNo);
		obj.setOrder(1);
		obj.setLocate(1);
		obj.setLoops(1);
		obj.setInterval(1);
		obj.setDuring(5);
		obj.setPos(pos);
		obj.setX(chara.x);
		obj.setY(chara.y);
		gameObjectChar.gameMap.send(new MSG_ANIMATE_IN_CHAR_LAYER(), obj);
	}

	/**
	 * 只播放一次
	 * 
	 * @param chara
	 * @param effectNo
	 * @param pos
	 */
	public static Vo_ANIMATE_IN_CHAR charaPlay(Chara chara, int effectNo, int pos) {
		Vo_ANIMATE_IN_CHAR obj = new Vo_ANIMATE_IN_CHAR();
		obj.setEffectNo(effectNo);
		obj.setOrder(1);
		obj.setLocate(1);
		obj.setLoops(1);
		obj.setInterval(1);
		obj.setDuring(5);
		obj.setPos(pos);
		obj.setX(chara.x);
		obj.setY(chara.y);
		return obj;
	}

	/**
	 * 自动按比例分配属性
	 * 
	 * @param tizhi   体制
	 * @param lingli  灵力
	 * @param liliang 力量
	 * @param minjie  敏捷
	 * @param count   总数
	 * @return
	 */
	public static Map<String, Integer> autoCalculationProportion(int tizhi, int lingli, int liliang, int minjie,
			int count) {
		int cTizhi = 0;
		int cLingli = 0;
		int cLiliang = 0;
		int cMinjie = 0;
		int index = 0;
		while (index < count) {
			cTizhi += tizhi;
			if ((cTizhi + cLingli + cLiliang + cMinjie) > count) {
				cTizhi -= tizhi;
			}
			cLingli += lingli;
			if ((cTizhi + cLingli + cLiliang + cMinjie) > count) {
				cLingli -= lingli;
			}
			cLiliang += liliang;
			if ((cTizhi + cLingli + cLiliang + cMinjie) > count) {
				cLiliang -= liliang;
			}
			cMinjie += minjie;
			if ((cTizhi + cLingli + cLiliang + cMinjie) > count) {
				cMinjie -= minjie;
			}
			index += (tizhi + lingli + liliang + minjie);
		}
		Map<String, Integer> map = new LinkedHashMap<>();
		map.put("tizhi", cTizhi);
		map.put("lingli", cLingli);
		map.put("liliang", cLiliang);
		map.put("minjie", cMinjie);
		return map;
	}

	public static void setOnline(Chara chara, int status) {
		// 设置登录状态
		Characters c = new Characters();
		c.setId(chara.getId());
		c.setOnline(status);
		GameData.that.baseCharactersService.updateById(c);
	}

	/**
	 * 设置角色战斗状态
	 * 
	 * @param session
	 */
	public static void setCharaFightStatus(GameObjectChar session) {
		if (session.chara.zhandouInfo != null) {
			GameConfig.canzhanBoos.remove(session.chara.zhandouInfo);
			session.chara.zhandouId = 0;
			session.chara.zhandouInfo = null;
			session.chara.isFight = false;
		}
	}

	public static int getZbLevel(int level) {
		int zbLevel = 1;
		if (level >= 10 && level <= 19) {
			zbLevel = 10;
		} else if (level >= 20 && level <= 29) {
			zbLevel = 20;
		} else if (level >= 30 && level <= 39) {
			zbLevel = 30;
		} else if (level >= 40 && level <= 49) {
			zbLevel = 40;
		} else if (level >= 50 && level <= 59) {
			zbLevel = 50;
		} else if (level >= 60 && level <= 69) {
			zbLevel = 60;
		} else if (level >= 70 && level <= 79) {
			zbLevel = 70;
		} else if (level >= 80 && level <= 89) {
			zbLevel = 80;
		} else if (level >= 90 && level <= 99) {
			zbLevel = 90;
		} else if (level >= 100 && level <= 109) {
			zbLevel = 100;
		} else if (level >= 110 && level <= 119) {
			zbLevel = 110;
		} else if (level >= 120 && level <= 129) {
			zbLevel = 120;
		} else if (level >= 130 && level <= 139) {
			zbLevel = 130;
		}else if (level >= 140 && level <= 149) {
			zbLevel = 140;
		}else if (level >= 150 && level <= 159) {
			zbLevel = 150;
		}else if (level >= 160 && level <= 169) {
			zbLevel = 160;
		}else if (level >= 170 && level <= 179) {
			zbLevel = 170;
		}else {
			zbLevel = 180;
		}
		return zbLevel;
	}

	/**
	 * 准备阶段
	 */
	public synchronized static Vo_SHIDAO_TASK_INFO shidaoTaskInfoNo1() {
		Vo_SHIDAO_TASK_INFO vo_49177_0 = new Vo_SHIDAO_TASK_INFO();
		vo_49177_0.isPK = 0;
		vo_49177_0.stageId = 0;
		vo_49177_0.monsterPoint = 0;
		vo_49177_0.pkValue = 0;
		vo_49177_0.totalScore = 0;
		Calendar cd = Calendar.getInstance();
		cd.setTime(new Date(GameShiDao.getStartTime()));
		cd.set(Calendar.MINUTE, cd.get(Calendar.MINUTE) + (int) GameShiDao.getJoinTime() / 1000 / 60);
		vo_49177_0.startTime = (int) (cd.getTime().getTime() / 1000L);
		vo_49177_0.rank = 0;
		yuanmoStartTime = cd.getTime().getTime();
		return vo_49177_0;

	}

	/**
	 * 第二阶段
	 * 
	 * @param monsterPoint
	 * @param totalScore
	 */
	public synchronized static Vo_SHIDAO_TASK_INFO shidaoTaskInfoNo2(int monsterPoint, int totalScore) {
		Vo_SHIDAO_TASK_INFO vo_49177_0 = new Vo_SHIDAO_TASK_INFO();
		vo_49177_0.isPK = 0;
		vo_49177_0.stageId = 1;
		vo_49177_0.monsterPoint = monsterPoint;
		vo_49177_0.pkValue = 0;
		vo_49177_0.totalScore = 0;
		vo_49177_0.startTime = 0;
		vo_49177_0.stage1_duration_time = (int) ((yuanmoStartTime + GameShiDao.getDurationTime()) / 1000L);
		vo_49177_0.stage2_duration_time = 0;
		vo_49177_0.rank = 0;
		// 设置当前时间
		pkStartTime = new Date().getTime();
		return vo_49177_0;
	}

	/**
	 * 第三阶段 决赛
	 * 
	 * @param pkValue
	 * @param totalScore
	 * @param rank
	 */
	public synchronized static Vo_SHIDAO_TASK_INFO shidaoTaskInfoNo3(int pkValue, int totalScore, int rank) {
		Vo_SHIDAO_TASK_INFO vo_49177_0 = new Vo_SHIDAO_TASK_INFO();
		vo_49177_0.isPK = 1;
		vo_49177_0.stageId = 3;
		vo_49177_0.monsterPoint = 0;
		vo_49177_0.pkValue = pkValue;
		vo_49177_0.totalScore = totalScore;
		vo_49177_0.startTime = 0;
		vo_49177_0.stage1_duration_time = 0;
		vo_49177_0.stage2_duration_time = (int) ((pkStartTime + GameShiDao.getPkTime()) / 1000L);
		vo_49177_0.rank = rank;
		return vo_49177_0;
	}

	/**
	 * 获取随机属性装备
	 * 
	 * @param chara
	 */
	public static void getRandomEquip(Chara chara) {
		Random random = new Random();
		int[] eqType = { 1, 2, 10, 3 };
		int leixing = eqType[random.nextInt(4)];
		String zhuangbname = GameUtil.zhuangbname(chara, leixing);
		ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils
				.appraisalEquipment(zhuangbeiInfo.getAmount(), zhuangbeiInfo.getAttrib(), 1);
		if (hashtables.size() > 0) {
			for (Hashtable<String, Integer> maps : hashtables) {
				if (maps.get("groupNo") == 2) {
					maps.put("groupType", 2);
					GoodsLanSe gooodsLanSe = (GoodsLanSe) JSONObject.parseObject(JSONObject.toJSONString(maps),
							GoodsLanSe.class);
					GameUtil.huodezhuangbei(chara, zhuangbeiInfo, 0, 1, gooodsLanSe);
				}
			}
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "恭喜你获得了#Y" + zhuangbname;
			vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
		}
	}

	/**
	 * 根据等级获取随机装备
	 * 
	 * @param chara 用户角色
	 * @param level 等级
	 */
	public static void getRandomEquipByLevel(Chara chara, int level) {
		String zhuangbname = GameUtil.getRandomZbNameByLevel(level);
		ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		if(zhuangbeiInfo != null) {
			List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils
					.appraisalEquipment(zhuangbeiInfo.getAmount(), zhuangbeiInfo.getAttrib(), 1);
			if (hashtables.size() > 0) {
				for (Hashtable<String, Integer> maps : hashtables) {
					if (maps.get("groupNo") == 2) {
						maps.put("groupType", 2);
						GoodsLanSe gooodsLanSe = JSONObject.parseObject(JSONObject.toJSONString(maps), GoodsLanSe.class);
						GameUtil.huodezhuangbei(chara, zhuangbeiInfo, 0, 1, gooodsLanSe);
					}
				}
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "恭喜你获得了#Y" + zhuangbname;
				vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			}
		}
	}

	/**
	 * 获取原始掌门
	 * 
	 * @return
	 */
	public static CharaStatue getOrginZhangmen(Chara chara, String name) {
		CharaStatue cs = new CharaStatue();
		cs.id = 0;
		cs.weapon_icon = 0;
		cs.sex = 1;
		cs.autofight_select = 1;
		cs.level = 70;
		cs.fashang = 70 * 200;
		cs.shengming = 70 * 800;
		cs.name = name;
		if ("金系掌门".equals(name)) {
			cs.polar = 1;
			cs.waiguan = 6001;
			cs.weapon_icon = 1141;
		} else if ("木系掌门".equals(name)) {
			cs.polar = 2;
			cs.waiguan = 7002;
			cs.weapon_icon = 1152;
		} else if ("水系掌门".equals(name)) {
			cs.polar = 3;
			cs.waiguan = 7003;
			cs.weapon_icon = 1130;
		} else if ("火系掌门".equals(name)) {
			cs.polar = 4;
			cs.waiguan = 6004;
			cs.weapon_icon = 1108;
		} else if ("土系掌门".equals(name)) {
			cs.polar = 5;
			cs.waiguan = 6005;
			cs.weapon_icon = 1119;
		}
		return cs;
	}

	// 显示boos
	public static void showBoss(Chara chara, int mapid) {
		// 海盗检测、如果有用户掉线
		for (Map.Entry<Integer, Vo_APPEAR> m : GameLine.gameGongCheng.haidaoGuaiwu.entrySet()) {
			Vo_APPEAR v = m.getValue();
			if (chara.mapid == v.mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), v, chara.id);
			}
		}
		// 天地星
		if (GameBossTianDiXing.xing.size() > 0) {
			for (Map.Entry<Integer, Vo_APPEAR> v : GameBossTianDiXing.xing.entrySet()) {
				if (chara.mapid == v.getValue().mapid) {
					GameObjectChar.send(new MSG_APPEAR_MONSTER(), v.getValue(), chara.id);
				}
			}
		}
		// 战神
		for (Map.Entry<Integer, Vo_APPEAR> m : GameLine.gameGongCheng.zhanshenGuaiwu.entrySet()) {
			Vo_APPEAR v = m.getValue();
			if (chara.mapid == v.mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), v, chara.id);
			}
		}
		// 刷星
		for (int i = 0; i < GameLine.gameShuaGuai.shuaXing.size(); ++i) {
			if (GameLine.gameShuaGuai.shuaXing.get(i).mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), GameLine.gameShuaGuai.shuaXing.get(i), chara.id);
			}
		}
		// 攻城
		for (int i = 0; i < GameLine.gameGongCheng.shuaGuai.size(); ++i) {
			if (GameLine.gameGongCheng.shuaGuai.get(i).mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), GameLine.gameGongCheng.shuaGuai.get(i), chara.id);
			}
		}
		// 除暴
		for (int i = 0; i < chara.npcchubao.size(); ++i) {
			if (mapid == chara.npcchubao.get(i).mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), chara.npcchubao.get(i), chara.id);
			}
		}
		// 悬赏
		for (Entry<Integer, Vo_APPEAR> m : GameShuaGuai.xuanshang.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}
		// 这里是玩家使用藏宝图挖出上古之后，实时的在地图上添加一个上古的npc
		for (Entry<Integer, Vo_APPEAR> m : GameShuaGuai.shanggu.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}
		// 这里是玩家使用藏宝图挖出万年之后，实时的在地图上添加一个万年的npc
		for (Entry<Integer, Vo_APPEAR> m : GameShuaGuai.wannian.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}

		for (Entry<Integer, Vo_APPEAR> m : GameShuaGuai.guiguai.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}
		
		//劫狱土匪
		for (Entry<Integer, Vo_APPEAR> m : GameCore.jieyuMonster.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}
		
		// 刷道
		Map<Integer, Vo_APPEAR> shudao = null;
		//如果组了队这里只显示队长的
		if(GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
			shudao = GameObjectCharMng.getGameObjectChar(chara.id).gameTeam.duiwu.get(0).shudao;
		}else {
			shudao = chara.shudao;
		}
		if(shudao != null) {
			for (Entry<Integer, Vo_APPEAR> m : shudao.entrySet()) {
				if (m.getValue().mapid == mapid) {
					GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
				}
			}
		}
		//攻城boss
		for (Entry<Integer, Vo_APPEAR> m : GameLine.gameGongCheng.gongchengBoss.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}
		
		for (Entry<Integer, Vo_APPEAR> m : GameCore.otherBoosMonster.entrySet()) {
			if (m.getValue().mapid == mapid) {
				GameObjectChar.send(new MSG_APPEAR_MONSTER(), m.getValue(), chara.id);
			}
		}
	}

	/**
	 * npc消息
	 * 
	 * @param name    名字
	 * @param msg     内容
	 * @param id      id
	 * @param icon    头像
	 * @param channel 频道
	 */
	public static Vo_MESSAGE npcMessage(String name, String msg, Integer id, Integer icon, Integer channel) {
		Vo_MESSAGE v = new Vo_MESSAGE();
		v.channel = channel;
		v.id = id;
		v.name = name;
		v.msg = msg;
		v.time = (int) (System.currentTimeMillis() / 1000L);
		v.privilege = 0;
		v.server_name = GameConfig.lineName;
		v.show_extra = 1;
		v.show_time = 5;
		v.icon = icon;
		return v;
	}

	/**
	 * 构建好友
	 * 
	 * @param chara
	 * @return
	 */
	public static Vo_FRIEND_ADD_CHAR buildFriend(Chara chara, Vo_FRIEND_ADD_CHAR friend) {
		Vo_FRIEND_ADD_CHAR vo_61545_0 = new Vo_FRIEND_ADD_CHAR();
		vo_61545_0.groupBuf = friend.groupBuf;
		vo_61545_0.charBuf = chara.name;
		vo_61545_0.blocked = friend.blocked;
		vo_61545_0.online = friend.online;
		vo_61545_0.server_name1 = GameConfig.lineName;
		vo_61545_0.insider_level = chara.vipType;
		vo_61545_0.skill = chara.level;
		vo_61545_0.type = chara.waiguan;
		vo_61545_0.server_name = GameConfig.lineName;
		vo_61545_0.suit_icon = chara.weapon_icon;
		vo_61545_0.party_contrib = chara.getPartyName();
		vo_61545_0.iid_str = chara.uuid;
		// 暂时用作友好度
		vo_61545_0.arena_rank = friend.arena_rank;
		return vo_61545_0;
	}

	/**
	 * 根据分组创建好友列表
	 * 
	 * @param chara
	 * @param friendGroups
	 * @return
	 */
	public static List<Vo_FRIEND_UPDATE_LISTS> createFriends(Chara chara, List<FriendGroup> friendGroups,
			boolean... isOnlineTips) {
		// 传给客户端的数据集合
		List<Vo_FRIEND_UPDATE_LISTS> f = new ArrayList<>();
		// 根据分组获取好友列表
		for (FriendGroup fg : friendGroups) {
			// 传给客户端的好友列表
			List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = new ArrayList<Vo_FRIEND_ADD_CHAR>();
			List<Friend> friendByGroupName = GameData.that.friendService.getFriendByGroupName(fg.getGroupId(),
					chara.uuid);
			if (friendByGroupName == null || friendByGroupName.isEmpty()) {
				continue;
			}
			// 分组信息
			Vo_FRIEND_UPDATE_LISTS myFriendGroup = new Vo_FRIEND_UPDATE_LISTS();
			myFriendGroup.setGroup(new Vo_FRIEND_ADD_GROUP(fg.getGroupId(), fg.getName()));
			f.add(myFriendGroup);
			for (Friend fr : friendByGroupName) {
				// 好友信息
				GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectCharByUUid(fr.getFriendGid());
				Chara friendChara = null;
				Vo_FRIEND_ADD_CHAR friend = new Vo_FRIEND_ADD_CHAR();
				friend.groupBuf = fg.getGroupId();
				int blocked = 0;
				if (gameObjectChar != null) {
					friend.online = 1;
					friendChara = gameObjectChar.chara;
					blocked = gameObjectChar.characters.getBlock();
				} else {
					// 用户不在线
					friend.online = 2;
					Characters characters = GameData.that.baseCharactersService.findOneByGidSelectProperties(fr.getFriendGid(), "data","id","name","level","polar","portrait", "gid", "block");
					if (characters == null) {
						continue;
					}
					friendChara = JSONObject.parseObject(characters.getData(),Chara.class);
					friendChara.name = characters.getName();
					friendChara.id = characters.getId();
					friendChara.uuid = characters.getGid();
					friendChara.level = characters.getLevel();
					friendChara.waiguan = characters.getPortrait();
					friendChara.polar = characters.getPolar();
					blocked = characters.getBlock();
				}
				friend.arena_rank = fr.getFriendScore();
				// 添加好友信息
				Vo_FRIEND_ADD_CHAR buildFriend = GameCommonUtil.buildFriend(friendChara, friend);
				buildFriend.blocked = blocked;
				vo_61545_0List.add(buildFriend);
			}
			myFriendGroup.setFriends(vo_61545_0List);
		}
		return f;
	}

	public static Vo_16383_0 a16383(Chara chara, String msg, int channel, Vo_16383_0 vo) {
		Vo_16383_0 vo_16383_0 = new Vo_16383_0();
		vo_16383_0.channel = channel;
		vo_16383_0.id = chara.id;
		vo_16383_0.name = chara.name;
		vo_16383_0.msg = msg;
		long times = System.currentTimeMillis() / 1000L;
		int time = (int) times;
		vo_16383_0.time = time;
		vo_16383_0.privilege = 0;
		vo_16383_0.server_name = GameConfig.lineName;
		vo_16383_0.show_extra = 0;
		vo_16383_0.compress = vo.compress;
		vo_16383_0.orgLength = vo.orgLength;
		vo_16383_0.cardCount = vo.cardCount;
		vo_16383_0.voiceTime = vo.voiceTime;
		vo_16383_0.token = vo.token;
		vo_16383_0.checksum = 0;
		vo_16383_0.iid_str = chara.uuid;
		vo_16383_0.has_break_lv_limit = 0;
		vo_16383_0.skill = chara.level;
		vo_16383_0.type = chara.waiguan;
		vo_16383_0.cardId = vo.cardId;
		return vo_16383_0;
	}

	/**
	 * 产品创建
	 * 
	 * @param buff
	 * @param goods
	 */
	public static void goodsCreate(ByteBuf buff, Goods goods) {
		Map<Object, Object> map = new HashMap<>();
		if (goods.goodsBasics != null) {
			map = UtilObjMapshuxing.GoodsBasics(goods.goodsBasics);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsBasics.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsBasics.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		// 魂器
		if (goods.goodsHunQi.zongShuxing != null && !goods.goodsHunQi.zongShuxing.isEmpty()) {
			// groupNo
			GameWriteTool.writeByte(buff, 1);
			GameWriteTool.writeByte(buff, 4);
			GameWriteTool.writeByte(buff, 5);
			Iterator<Hashtable<String, Object>> xh = goods.goodsHunQi.zongShuxing.iterator();
			while (xh.hasNext()) {
				Hashtable<String, Object> ls = (Hashtable<String, Object>) xh.next();
				GameWriteTool.writeByte(buff, (Integer) ls.get("chaos_value"));
				GameWriteTool.writeByte(buff, (Integer) ls.get("yang_percent"));
				GameWriteTool.writeString(buff, (String) ls.get("yang_prop"));
				GameWriteTool.writeShort(buff, (Integer) ls.get("yang_prop_value"));
				GameWriteTool.writeString(buff, (String) ls.get("yin_prop"));
				GameWriteTool.writeShort(buff, (Integer) ls.get("yin_prop_value"));
			}
		}
		if (goods.goodsLanSe != null) {
			map = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsLanSe.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsLanSe.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsFenSe != null) {
			map = UtilObjMapshuxing.GoodsFenSe(goods.goodsFenSe);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsFenSe.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsFenSe.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsHuangSe != null) {
			map = UtilObjMapshuxing.GoodsHuangSe(goods.goodsHuangSe);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsHuangSe.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsHuangSe.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsLvSe != null) {
			map = UtilObjMapshuxing.GoodsLvSe(goods.goodsLvSe);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsLvSe.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsLvSe.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsGaiZao != null) {
			map = UtilObjMapshuxing.GoodsGaiZao(goods.goodsGaiZao);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsGaiZao.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsGaiZao.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsGaiZaoGongMing != null) {
			map = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMing.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMing.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsGaiZaoGongMingChengGong != null) {
			map = UtilObjMapshuxing.GoodsGaiZaoGongMingChengGong(goods.goodsGaiZaoGongMingChengGong);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMingChengGong.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsGaiZaoGongMingChengGong.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
		if (goods.goodsLvSeGongMing != null) {
			map = UtilObjMapshuxing.GoodsLvSeGongMing(goods.goodsLvSeGongMing);
			map.remove("groupNo");
			map.remove("groupType");
			GameWriteTool.writeByte(buff, goods.goodsLvSeGongMing.groupNo);
			GameWriteTool.writeByte(buff, goods.goodsLvSeGongMing.groupType);
			Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry<Object, Object> entry = it.next();
				if (entry.getValue().equals(0)) {
					it.remove();
				}
			}
			GameWriteTool.writeShort(buff, map.size());
			for (Map.Entry<Object, Object> entry2 : map.entrySet()) {
				if (BuildFields.data.get(entry2.getKey()) != null) {
					BuildFields.get((String) entry2.getKey()).write(buff, entry2.getValue());
				} else {
					System.out.println(entry2.getKey());
				}
			}
		}
	}

	/**
	 * 成功提示
	 * 
	 * @param msg 消息
	 */
	public static void dialogOk(String msg) {
		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = msg;
		vo_8165_0.active = 0;
		GameObjectChar.send(new M8165_0(), vo_8165_0);
	}

	/**
	 * 成功提示
	 * 
	 * @param msg 消息
	 * @param id  通道id
	 */
	public static void dialogOk(String msg, int id) {
		Vo_8165_0 vo_8165_0 = new Vo_8165_0();
		vo_8165_0.msg = msg;
		vo_8165_0.active = 1;
		GameObjectChar.send(new M8165_0(), vo_8165_0,id);
	}

	/**
	 * 杂项
	 * 
	 * @param msg 消息
	 * @param id  通道id
	 */
	public static void sendTips(String msg, int id) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = msg;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if (gameObjectChar != null) {
			gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
		}
	}
	
	/**
	 * 杂项
	 * 
	 * @param msg 消息
	 * @param id  通道id
	 */
	public static void sendTips(String msg) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = msg;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		GameObjectChar.send(new M20481_0(), vo_20481_0);
	}

	/**
	 * 杂项
	 * 
	 * @param msg 消息
	 * @param id  通道id
	 */
	public static void sendTips(String msg, GameObjectChar gameObjectChar) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = msg;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		if (gameObjectChar != null) {
			gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
		}
	}

	/**
	 * 添加加好友
	 * 
	 * @param chara   添加发起人
	 * @param toChara 被添加
	 */
	public static void addFriend(Chara chara, Chara toChara) {
		// 弹出好友添加成功窗口
		Vo_ADD_FRIEND_OPER oper = new Vo_ADD_FRIEND_OPER();
		oper.setGid(toChara.getUuid());
		oper.setName(toChara.getName());
		oper.setPartyName(toChara.getPartyName());
		oper.setIcon(toChara.getWaiguan());
		oper.setLevel(toChara.getLevel());
		GameObjectChar.send(new MSG_ADD_FRIEND_OPER(), oper);
		// 发送温馨提示
		GameCommonUtil
				.dialogOk(String.join("", "恭喜你在《", GameConfig.lineName, "》", "的世界又多了一位朋友#Y", toChara.getName(), "#W。"));
		// 发送消息到被添加方
		GameCommonUtil.dialogOk(String.join("", "#Y", chara.getName(), "#W已将你加为了好友！"), toChara.id);
		// 插入数据
		Friend friend = new Friend();
		friend.setAddTime(new Date());
		friend.setFriendGid(toChara.uuid);
		friend.setGid(chara.uuid);
		friend.setGroupId("1");
		friend.setGroupName("我的好友");
		friend.setFriendName(toChara.name);
		GameData.that.friendService.insertSelective(friend);
	}

	/**
	 * 刷新某个好友信息
	 * 
	 * @param vo    好友vo
	 * @param chara 好友
	 */
	public static void refreshFriend(Vo_FRIEND_ADD_CHAR vo, Chara chara) {
		refreshFriendPublic(vo, chara);
	}

	/**
	 * 刷新某个好友信息
	 * 
	 * @param vo    好友vo
	 * @param chara 好友
	 * @param id    要把这个消息发送给谁的id
	 */
	public static void refreshFriend(Vo_FRIEND_ADD_CHAR vo, Chara chara, int id) {
		refreshFriendPublic(vo, chara, id);
	}

	private static void refreshFriendPublic(Vo_FRIEND_ADD_CHAR vo, Chara chara, int... id) {
		List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = new ArrayList<Vo_FRIEND_ADD_CHAR>();
		Vo_FRIEND_ADD_CHAR vo_61545_0 = new Vo_FRIEND_ADD_CHAR();
		vo_61545_0.groupBuf = vo.groupBuf;
		vo_61545_0.charBuf = chara.name;
		vo_61545_0.blocked = 0;
		vo_61545_0.online = vo.online == null ? 1 : vo.online;
		vo_61545_0.server_name1 = GameConfig.lineName;
		vo_61545_0.insider_level = chara.vipType;
		vo_61545_0.iid_str = chara.uuid;
		vo_61545_0.skill = chara.level;
		vo_61545_0.party_contrib = chara.getPartyName();
		vo_61545_0.type = chara.waiguan;
		vo_61545_0.arena_rank = vo.arena_rank;
		vo_61545_0List.add(vo_61545_0);
		if (id != null && id.length > 0) {
			GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List, id[0]);
		} else {
			GameObjectChar.send(new MSG_FRIEND_ADD_CHAR(), vo_61545_0List);
		}
	}

	/**
	 * 好友提醒
	 * 
	 * @param chara
	 */
	public static void friendTips(Chara chara, int online) {
		Example friendExample = new Example(Friend.class);
		friendExample.createCriteria().andEqualTo("friendGid", chara.uuid);
		List<Friend> friends = GameData.that.friendService.selectByExample(friendExample);
		for (Friend f : friends) {
			String gid = f.getGid();
			// 获取好友在不在线
			GameObjectChar friendGameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
			if (friendGameObject != null) {
				String onlineStr = "上线";
				if (online == 2) {
					onlineStr = "下线";
				}
				Vo_16383_0 vo_16383_2 = GameUtil.a16383(chara,
						String.join("", "你的好友#Y", chara.name, "#W", onlineStr, "了。"), 0);
				GameObjectChar.send(new M16383_0(), vo_16383_2, friendGameObject.chara.id);
				if (online == 1) {
					// 这是刷新我在好友列表的信息
					GameObjectChar.send(new MSG_FRIEND_NOTIFICATION(), new Object[] { friendGameObject.chara.name,
							GameConfig.lineName, online, friendGameObject.chara.vipType }, chara.id);
					// 这是刷新好友在我列表的信息
					GameObjectChar.send(new MSG_FRIEND_NOTIFICATION(),
							new Object[] { chara.name, GameConfig.lineName, online, chara.vipType },
							friendGameObject.chara.id);
				} else {
					// 这是刷新好友在我列表的信息
					GameObjectChar.send(new MSG_FRIEND_NOTIFICATION(),
							new Object[] { chara.name, GameConfig.lineName, online, chara.vipType },
							friendGameObject.chara.id);
				}
			}
		}
	}

	/**
	 * 获取用户信息
	 * 
	 * @param chara
	 * @return
	 */
	public static Vo_36871_0 getCharaInfo(Chara chara) {
		Vo_36871_0 vo_36871_0 = new Vo_36871_0();
		vo_36871_0.msg_type = "asadas";
		vo_36871_0.icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
		vo_36871_0.id = chara.id;
		vo_36871_0.level = chara.level;
		vo_36871_0.gid = chara.uuid;
		vo_36871_0.name = chara.name;
		vo_36871_0.party = chara.getPartyName();
		vo_36871_0.friend_score = 0;
		vo_36871_0.setting_flag = 0;
		vo_36871_0.char_status = 1;
		GameObjectChar gameObject = GameObjectCharMng.getGameObjectChar(chara.id);
		if (gameObject != null && gameObject.gameTeam != null && gameObject.gameTeam.duiwu != null) {
			vo_36871_0.char_status = 3;
		}
		vo_36871_0.vip = chara.vipType;
		vo_36871_0.serverId = "1";
		vo_36871_0.account = chara.uuid;
		vo_36871_0.polar = chara.polar;
		vo_36871_0.isInThereFrend = 1;
		vo_36871_0.ringScore = 0;
		vo_36871_0.comeback_flag = 0;
		return vo_36871_0;
	}

	/**
	 * 根据等级获取随机首饰名称
	 * 
	 * @param level
	 * @return
	 */
	public static String randomGetShouShiName(int level) {
		String name = "";
		if (getZbLevel(level) == 50) {
			name = GameUtil.SHOU_SHI_50[(int) (Math.random() * GameUtil.SHOU_SHI_50.length)];
		} else if (getZbLevel(level) == 60) {
			name = GameUtil.SHOU_SHI_60[(int) (Math.random() * GameUtil.SHOU_SHI_60.length)];
		} else if (getZbLevel(level) == 70) {
			name = GameUtil.SHOU_SHI_70[(int) (Math.random() * GameUtil.SHOU_SHI_70.length)];
		} else if (getZbLevel(level) == 80) {
			name = GameUtil.SHOU_SHI_80[(int) (Math.random() * GameUtil.SHOU_SHI_80.length)];
		} else if (getZbLevel(level) == 90) {
			name = GameUtil.SHOU_SHI_90[(int) (Math.random() * GameUtil.SHOU_SHI_90.length)];
		} else if (getZbLevel(level) == 100) {
			name = GameUtil.SHOU_SHI_100[(int) (Math.random() * GameUtil.SHOU_SHI_100.length)];
		} else if (getZbLevel(level) == 110) {
			name = GameUtil.SHOU_SHI_110[(int) (Math.random() * GameUtil.SHOU_SHI_110.length)];
		} else if (getZbLevel(level) == 120) {
			name = GameUtil.SHOU_SHI_120[(int) (Math.random() * GameUtil.SHOU_SHI_120.length)];
		} else if (getZbLevel(level) == 130) {
			name = GameUtil.SHOU_SHI_130[(int) (Math.random() * GameUtil.SHOU_SHI_130.length)];
		} else if (getZbLevel(level) == 140) {
			name = GameUtil.SHOU_SHI_140[(int) (Math.random() * GameUtil.SHOU_SHI_140.length)];
		} else if (getZbLevel(level) == 150) {
			name = GameUtil.SHOU_SHI_150[(int) (Math.random() * GameUtil.SHOU_SHI_150.length)];
		} else if (getZbLevel(level) == 160) {
			name = GameUtil.SHOU_SHI_160[(int) (Math.random() * GameUtil.SHOU_SHI_160.length)];
		} else if (getZbLevel(level) == 170) {
			name = GameUtil.SHOU_SHI_170[(int) (Math.random() * GameUtil.SHOU_SHI_170.length)];
		} else {
			// 35级
			name = GameUtil.SHOU_SHI_35[(int) (Math.random() * GameUtil.SHOU_SHI_35.length)];
		}
		return name;
	}

	/**
	 * 随机Npc抽奖
	 * 
	 * @param sb
	 * @return
	 */
	public static int randomNpcLuck(Chara chara) {
		String msg = "#W喜从天降！恭喜#Y" + chara.name + "#W在天墉城#R抽奖大使#W处幸运的抽中了#R%s#W获得了#R%s#W！";
		double index = new Random().nextDouble() * 1000;
		LuckDrawNpcConfig luckDrawNpcConfig = GameConfig.config.getLuckDrawNpcConfig();
		if (index <= Double.valueOf(luckDrawNpcConfig.getNo1Jilv().replace("%", ""))) { // 0.3%随机服战神兽
			String name = fuzhan_name[new Random().nextInt(fuzhan_name.length)];
			GameUtil.huodeshenshou(chara, name, "npc抽奖");
			msg = String.format(msg, "服战神兽", name);
			GameUtil.sendYaoYan(msg);
		} else if (index <= Double.valueOf(luckDrawNpcConfig.getNo2Jilv().replace("%", ""))) { // 1%随机相5满属性首饰、随机超神兽
			int nextInt = new Random().nextInt(3) + 1;
			if (nextInt == 1) {
				// 随机相5满属性首饰
				String name = randomGetShouShiName(chara.level);
				GameUtil.jifendengjishoushi(chara, new String[] { name });
				msg = String.format(msg, "相5满属性首饰", name);
			} else if (nextInt == 2) {
				// 随机超神兽
				String name = chaoshenshou_name[new Random().nextInt(chaoshenshou_name.length)];
				GameUtil.huodeshenshou(chara, name, "npc抽奖");
				msg = String.format(msg, "超神兽", name);
			} else {
				String name = fabao_name[new Random().nextInt(fabao_name.length)];
				GameUtil.huodefabao(chara, name, 24, "npc抽奖", new Random().nextInt(5) + 1);
				msg = String.format(msg, "极品法宝", name);
			}
			GameUtil.sendYaoYan(msg);
		} else if (index <= Double.valueOf(luckDrawNpcConfig.getNo3Jilv().replace("%", ""))) { // 3%随机8速
			String name = speedNo8[new Random().nextInt(speedNo8.length)];
			GameUtil.huodezuoqi(chara, name, "npc抽奖");
			msg = String.format(msg, "八速坐骑", name);
			GameUtil.sendYaoYan(msg);
		} else if (index <= Double.valueOf(luckDrawNpcConfig.getNo4Jilv().replace("%", ""))) { // 5%随机神兽
			String name = shenshou_name[new Random().nextInt(shenshou_name.length)];
			GameUtil.huodeshenshou(chara, name, "npc抽奖");
			msg = String.format(msg, "神兽", name);
			GameUtil.sendYaoYan(msg);
		} else if (index <= Double.valueOf(luckDrawNpcConfig.getNo5Jilv().replace("%", ""))) {// 10%随机6速、随机变异
			boolean nextBoolean = new Random().nextBoolean();
			if (nextBoolean) {
				// 随机6速
				String name = speedNo6[new Random().nextInt(speedNo6.length)];
				GameUtil.huodezuoqi(chara, name, "npc抽奖");
				msg = String.format(msg, "六速坐骑", name);
			} else {
				// 随机变异
				String name = GameUtil.baobianyi(chara, "npc抽奖");
				msg = String.format(msg, "变异", name);
			}
			GameUtil.sendYaoYan(msg);
		} else if (index <= Double.valueOf(luckDrawNpcConfig.getNo6Jilv().replace("%", ""))) {// 20%道行 (自身等级*0.2*200)
			GameUtil.adddaohang(chara, (int) (chara.level * 0.2 * 300000));
		} else {
			// 80%经验 (自身等级*0.7*10000)
			GameUtil.huodejingyan(chara, (int) (chara.level * 0.7 * 10000) / 2);
			GameUtil.sendMeTips("你获得了#R" + (int) (chara.level * 0.7 * 10000) / 2 + "#W经验");
		}
		return 0;
	}

	public static boolean isInTeam(int charaId) {
		GameObjectChar oc = GameObjectCharMng.getGameObjectChar(charaId);
		if (oc.gameTeam == null)
			return false;
		if (oc.gameTeam.duiwu.size() > 0)
			return true;
		if (oc.gameTeam.zhanliduiyuan.size() > 0)
			return true;

		return false;
	}

	/**
	 * 根据宠物类型获取对应的类型名字
	 * 
	 * @param type
	 * @return
	 */
	public static String getPetTypeStr(int type) {
		String typeStr = "野生";
		switch (type) {
		case 2:
			typeStr = "宝宝";
			break;
		case 3:
			typeStr = "变异";
			break;
		case 4:
			typeStr = "神兽";
			break;
		case 5:
			typeStr = "守护";
			break;
		case 6:
			typeStr = "鬼卒";
			break;
		case 7:
			typeStr = "鬼将";
			break;
		case 8:
			typeStr = "鬼仙";
			break;
		}
		return typeStr;
	}

	public static String UUID() {
		return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
	}

	/**
	 * 自动递增全局唯一bossId
	 * @return
	 */
	public static int generateBossId() {
		int id = bossAutoId.getAndIncrement();
		log.info("生成bossAutoId={}", id);
		// 如果大于这个就恢复初始化
		if (id > 999999999) {
			bossAutoId.set(999999);
		}
		return id;
	}

	/**
	 * 设置队伍头顶状态
	 * 
	 * @param chara
	 */
	public static void setCharaTitleFlag(Chara chara) {
		List<GameObjectChar> charas = null;
		// 如果在帮派总坛的话.
		if ("帮派总坛".equals(chara.mapName)) {
			charas = GameLine.getZoneGameMapSessionList(chara.line, chara.getPartyName());
		} else if ("试道场".equals(chara.mapName)) {
			charas = GameShiDao.getShiDaoMapSession(chara.level);
		} else {
			charas = GameLine.getGameMap(chara.line, chara.mapid).sessionList;
		}
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
//		if(gameObjectChar == null) {
//			gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
//		}
		setCharaTitleFlag(charas, gameObjectChar);
	}

	/**
	 * 设置角色头顶标识
	 * @param charas
	 * @param gameObjectChar
	 */
	public static void setCharaTitleFlag(List<GameObjectChar> charas, GameObjectChar gameObjectChar) {
		//获取当前地图人员
		List<GameObjectChar> sessionList = gameObjectChar.gameMap.sessionList;
		for(GameObjectChar session:sessionList) {
			//通知地图所有玩家加载自己的标识
			session.sendOne(new MSG_TITLE(), getCharaVoTitle(gameObjectChar));
			//通知自己加载这个地图所有玩家的标识
			gameObjectChar.sendOne(new MSG_TITLE(), getCharaVoTitle(session));
		}
	}
	//获取用户头顶标识
	public static Vo_TITLE getCharaVoTitle(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		Vo_TITLE title = new Vo_TITLE();
		title.id = chara.id;
		//有队伍的情况
		GameTeam gameTeam = gameObjectChar.gameTeam;
		if (isNotGameTeam(gameTeam,chara)) {
			List<Chara> duiwu = gameTeam.duiwu;
			Chara leaderChara = duiwu.get(0);
			int leaderId = leaderChara.id;
			//自己身为队长
			if(chara.id == leaderId) {
				if (gameTeam.zhanliduiyuan.size() == 5) {
					title.list.add(4);
					if (leaderChara.isFight) {
						// 战斗状态
						title.list.add(1);
					} else if (gameObjectChar.isLook == 1) {
						// 观战
						title.list.add(6);
					}
				} else {
					title.list.add(3);
					if (leaderChara.isFight) {
						// 战斗状态
						title.list.add(1);
					} else if (gameObjectChar.isLook == 1) {
						// 观战
						title.list.add(6);
					}
				}
				if (chara.isNameRed == 1) {
					title.list.add(7);
				}
			}else {
				//自己身为队员
				if (leaderChara.isFight) {
					// 战斗状态
					title.list.add(1);
				} else if (gameObjectChar.isLook == 1) {
					// 观战
					title.list.add(6);
				} else {
					// 默认。无状态、禁止移动
					title.list.add(0);
					title.list.add(2);
					title.list.add(5);
				}
				if (chara.isNameRed == 1) {
					title.list.add(7);
				}
			}
		}else {
			//未组队状态
			if (chara.isFight) {
				// 战斗状态
				title.list.add(1);
			} else if (gameObjectChar.isLook == 1) {
				// 观战
				title.list.add(6);
			} else {
				title.list.add(0);
			}
			if (chara.isNameRed == 1) {
				title.list.add(7);
			}
		}
		return title;
	}
	
	/**
	 * 设置角色头顶的标识
	 * 
	 * @param charas
	 */
	@Deprecated
	public static void setCharaTitleFlag(List<GameObjectChar> charas) {
		ExecutorsUtils.getExecutorPools().execute(new Runnable() {
			@Override
			public void run() {
				Vo_TITLE vo_61671_0 = new Vo_TITLE();
				for (GameObjectChar teamGameObjectChar : charas) {
					if (teamGameObjectChar == null) {
						continue;
					}
					// 设置队伍头顶状态
					if (isNotGameTeam(teamGameObjectChar.gameTeam) && 
							teamGameObjectChar.gameTeam.duiwu.contains(teamGameObjectChar.chara)) {
						Chara duizhang = teamGameObjectChar.gameTeam.duiwu.get(0);
						GameObjectChar duizhangGame = GameObjectCharMng.getGameObjectChar(duizhang.id);
						// 如果有队伍的话设置队员
						for (Chara c : teamGameObjectChar.gameTeam.duiwu) {
							vo_61671_0 = new Vo_TITLE();
							vo_61671_0.id = c.id;
							if (c.isNameRed == 1) {
								vo_61671_0.list.add(7);
							}
							if (c.id == duizhang.id) {
								if (teamGameObjectChar.gameTeam.zhanliduiyuan.size() == 5) {
									vo_61671_0.list.add(4);
									if (duizhang.isFight) {
										// 战斗状态
										vo_61671_0.list.add(1);
									}else if(duizhangGame.isLook ==1) {
										//观战
										vo_61671_0.list.add(6);
									}
								} else {
									vo_61671_0.list.add(3);
									if (duizhang.isFight) {
										// 战斗状态
										vo_61671_0.list.add(1);
									}else if(duizhangGame.isLook ==1) {
										//观战
										vo_61671_0.list.add(6);
									}
								}
								teamGameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
								continue;
							}
							if (duizhang.isFight) {
								// 战斗状态
								vo_61671_0.list.add(1);
							}else if(duizhangGame.isLook ==1) {
								//观战
								vo_61671_0.list.add(6);
							} else {
								// 默认。无状态、禁止移动
								vo_61671_0.list.add(0);
								vo_61671_0.list.add(2);
								vo_61671_0.list.add(5);
							}
							teamGameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
						}
					} else {
						vo_61671_0 = new Vo_TITLE();
						vo_61671_0.id = teamGameObjectChar.chara.id;
						if (teamGameObjectChar.chara.isFight) {
							vo_61671_0.list.add(1);
						} else if(teamGameObjectChar.isLook ==1) {
							//观战
							vo_61671_0.list.add(6);
						}else {
							vo_61671_0.list.add(0);
						}
						if (teamGameObjectChar.chara.isNameRed == 1) {
							vo_61671_0.list.add(7);
						}
						teamGameObjectChar.gameMap.send(new MSG_TITLE(), vo_61671_0);
					}
				}
			}
		});
	}

	public static boolean isCollectionNullOrEmpty(Collection<?> con) {
		boolean flag = true;
		if (con == null || con.isEmpty() || con.size() == 0) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 判断是否有队伍,如果有队伍则返回true
	 * 
	 * @param gameTeam
	 * @return
	 */
	public static boolean isNotGameTeam(GameTeam gameTeam, Chara thisChara) {
		boolean flag = true;
		if (gameTeam == null || gameTeam.duiwu == null || gameTeam.duiwu.isEmpty() || gameTeam.duiwu.size() == 0) {
			flag = false;
			return flag;
		}
		if(thisChara != null) {
			// 去队伍查找这个人.如果不在的话.表示暂离
			for (Chara chara : gameTeam.duiwu) {
				//如果找到这个人接设置为true
				if (chara.id == thisChara.id) {
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
		}
		return flag;
	}

	/**
	 * 判断是否有队伍
	 * 
	 * @param gameTeam
	 * @return
	 */
	public static boolean isNotGameTeam(GameTeam gameTeam) {

		return isNotGameTeam(gameTeam, null);
	}

	/**
	 * 刷新珍宝摊位
	 * 
	 * @param chara
	 * @return
	 */
	public static Vo_GOLD_STALL_MINE refreshMarketGold(Chara chara) {
		ArrayList<Vo_GOLD_STALL_MINE_Items> items = new ArrayList<Vo_GOLD_STALL_MINE_Items>();
		GoldStallNineGoodsService zhenbao = GameData.that.zhenbao;
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("gid", chara.uuid).andEqualTo("deleted", false).andIn("status",
				Lists.newArrayList(1, 2, 3, 4));
		List<GoldStallNineGoods> selectByExample = zhenbao.selectByExample(example);
		int pos = 1;
		for (GoldStallNineGoods s : selectByExample) {
			Vo_GOLD_STALL_MINE_Items v = new Vo_GOLD_STALL_MINE().new Vo_GOLD_STALL_MINE_Items();
			v.setName(s.getName());
			v.setGoodsId(s.getGoodsId());
			v.setPrice(s.getPrice());
			v.setPos(pos);
			v.setStatus(s.getStatus());
			v.setStartTime(s.getStartTime());
			if (s.getEndTime() != null) {
				v.setEndTime(s.getEndTime());
			}
			v.setLevel(s.getLevel());
			v.setUnidentified(s.getUnidentified());
			v.setReq_level(s.getReqLevel());
			v.setExtra(s.getExtra());
			v.setItem_polar(s.getReqLevel());
			v.setCg_price_count(s.getCgPriceCount());
			v.setInit_price(s.getInitPrice());
			v.setFlag_num(s.getFlagNum());
			v.setStall_item_type(s.getStallItemType());
			v.setBuyout_price(s.getBuyoutPrice());
			v.setSell_type(s.getSellType());
			v.setAppointee_name(s.getAppointeeName());
			if (s.getAppointeeName() != null && !s.getAppointeeName().equals("")) {
				v.setAppointee_name(s.getAppointeeName().split(";")[0]);
			}
			pos++;
			items.add(v);
		}
		Vo_GOLD_STALL_MINE gold2 = new Vo_GOLD_STALL_MINE();
		gold2.setDealNum(1);
		gold2.setSellCash(String.valueOf(chara.sellCash));
		// 摊位
		int erwaiStallTotalNum = chara.vipType * 4;
		gold2.setStallTotalNum(4 + erwaiStallTotalNum);
		gold2.setItems(items);
		return gold2;
	}

	public static void openStallGold(Chara chara, String key, String pageStr) {
		String[] pageStrArr = pageStr.split(";");
		GoldStallNineGoodsService zhenbao = GameData.that.zhenbao;
		Example example = new Example(GoldStallNineGoods.class);
		Criteria createCriteria = example.createCriteria();
		createCriteria.andEqualTo("alias", key).andEqualTo("deleted", false).andEqualTo("status",
				Integer.valueOf(pageStrArr[1]));
		if ("start_time".equals(pageStrArr[3])) {
			pageStrArr[3] = "startTime";
		}
		if (pageStrArr[2].equals("1")) {
			example.orderBy(pageStrArr[3]).asc();
		} else {
			example.orderBy(pageStrArr[3]).desc();
		}
		PageHelper.startPage(Integer.valueOf(pageStrArr[0]), 15);
		PageInfo<GoldStallNineGoods> pageInfo = new PageInfo<GoldStallNineGoods>(zhenbao.selectByExample(example));
		Vo_GOLD_STALL_GOODS_LIST list = new Vo_GOLD_STALL_GOODS_LIST();
		list.setTotalPage(pageInfo.getPages());
		list.setCur_page(pageInfo.getPageNum());

		List<Vo_GOLD_STALL_MINE_Items> items = new ArrayList<>();
		for (GoldStallNineGoods s : pageInfo.getList()) {
			Vo_GOLD_STALL_MINE_Items v = new Vo_GOLD_STALL_MINE().new Vo_GOLD_STALL_MINE_Items();
			v.setName(s.getName());
			if (s.getGid().equals(chara.uuid)) {
				v.setIs_my_goods(1);
			} else {
				v.setIs_my_goods(0);
			}
			v.setGoodsId(s.getGoodsId());
			v.setPrice(s.getPrice());
			v.setStatus(s.getStatus());
			v.setStartTime(v.getStartTime());
			v.setEndTime(s.getEndTime());
			v.setLevel(s.getLevel());
			v.setUnidentified(s.getUnidentified());
			v.setReq_level(s.getReqLevel());
			v.setExtra(s.getExtra());
			v.setItem_polar(s.getReqLevel());
			v.setBuyout_price(s.getBuyoutPrice());
			v.setSell_type(s.getSellType());
			v.setAppointee_name(s.getAppointeeName());
			if (s.getAppointeeName() != null && !s.getAppointeeName().equals("")) {
				v.setAppointee_name(s.getAppointeeName().split(";")[0]);
			}
			items.add(v);
		}
		list.setItems(items);
		list.setPath_str(pageStr);
		list.setSelect_gid("");
		list.setSell_stage(Integer.valueOf(pageStrArr[1]));
		list.setSort_key(pageStrArr[3]);
		list.setIs_descending(0);
		GameObjectChar.send(new MSG_GOLD_STALL_GOODS_LIST(), list);
	}

	public static MailboxRefresh convertMail(Vo_MAILBOX_REFRESH mail) {
		MailboxRefresh m = new MailboxRefresh();
		m.setAttachment(mail.attachment);
		m.setCreateTime(mail.create_time);
		m.setExpiredTime(mail.expired_time);
		m.setGid(mail.id);
		m.setMsg(mail.msg);
		m.setStatus(mail.status);
		m.setToGid(mail.toGid);
		m.setSender(mail.sender);
		m.setTitle(mail.title);
		m.setType(mail.type);
		return m;
	}

	public static Vo_MAILBOX_REFRESH convertMailVo(MailboxRefresh mail) {
		Vo_MAILBOX_REFRESH m = new Vo_MAILBOX_REFRESH();
		m.attachment = mail.getAttachment();
		m.create_time = mail.getCreateTime();
		m.expired_time = mail.getExpiredTime();
		m.id = mail.getGid();
		m.msg = mail.getMsg();
		m.status = mail.getStatus();
		m.toGid = mail.getGid();
		m.sender = mail.getSender();
		m.title = mail.getTitle();
		m.type = mail.getType();
		return m;
	}

	/**
	 * 760034&31001 根据坐骑获取元血婴的坐骑外观
	 * 
	 * @param chara
	 */
	public static int getYuanYingZuoqiWaiguan(Chara chara, int icon) {
		int waiguan = 0;
		if (chara.upgrade_state != 0) {
			// 元血婴
			if (icon == 31006 || icon == 31010) {
				if(chara.upgrade_type == 1 || chara.upgrade_type == 3) {
					waiguan = 760010;
				}else {
					waiguan = 770010;
				}
			} else if (icon == 31011 || icon == 31013) {
				if(chara.upgrade_type == 1 || chara.upgrade_type == 3) {
					waiguan = 760020;
				}else {
					waiguan = 770020;
				}
			} else if (icon == 31001 || icon == 31003 || icon == 31019 || icon == 31029 || icon == 31020
					|| icon == 31021 || icon == 31023 || icon == 31004 || icon == 31052 || icon == 31017
					|| icon == 31018){
				//元婴
				if(chara.upgrade_type == 1 || chara.upgrade_type == 3) {
					waiguan = 760030;
				}else {
					waiguan = 770030;
				}
			}
		}
		return waiguan;
	}

//	/**
//	 * 刷新称号
//	 *
//	 * @param chara 角色
//	 */
//	 public static void refreshAppellAtion(Chara chara) {
//	 	List<Vo_62209_0> list = new LinkedList<Vo_62209_0>();
//	 	Vo_62209_0 vo_62209_0 = new Vo_62209_0();
//	 	vo_62209_0.stringformat = "无显示";
//	 	vo_62209_0.title = "";
//	 	vo_62209_0.titled_left_time = 0;
//	 	list.add(vo_62209_0);
//	 	for (java.util.Map.Entry<String, String> entry : chara.chenghao.entrySet()) {
//	 		vo_62209_0 = new Vo_62209_0();
//	 		vo_62209_0.stringformat = entry.getKey();
//	 		vo_62209_0.title = entry.getValue();
//	 		vo_62209_0.titled_left_time = 0;
//	 		list.add(vo_62209_0);
//	 	}
//	 	GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M62209_0(), list);
//	 }

	/**
	 * 添加武学
	 * 
	 * @param chara
	 */
	public static void addWuXue(Chara chara, int intimacy, String... source) {
		if (chara.mapName.equals("试道场")) {
			chara.shidaoMartial += intimacy;
		} else {
			Petbeibao pet = getCurrentPet(chara);
			if(pet != null) {
				PetShuXing petShuXing = pet.petShuXing.get(0);
				if (intimacy == 0) {
					int base_pet_dh = (int) (0.29 * petShuXing.skill * petShuXing.skill * petShuXing.skill);
					int newIntimacy = 33 * petShuXing.skill
							/ ((petShuXing.intimacy > base_pet_dh) ? (petShuXing.intimacy / base_pet_dh) : 1);
					String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
					if(openGlobalDouble != null) {
						if(source.length>0 && openGlobalDouble.indexOf(source[0]) != -1) {
							newIntimacy*=2;
						}
					}
					petShuXing.intimacy += newIntimacy;
				}else {
					String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
					if(openGlobalDouble != null) {
						if(source.length>0 && openGlobalDouble.indexOf(source[0]) != -1) {
							intimacy*=2;
						}
					}
					petShuXing.intimacy += intimacy;
				}
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "你的#Y" + petShuXing.str + "#n获得了#R" + intimacy + "#n武学。";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			}
		}
//		GameCommonUtil.addCharaTrail(chara, "武学", intimacy, source);
	}
	
	public static void addWuXue(Chara chara, int intimacy, Petbeibao pet, String... source) {
		if (chara.mapName.equals("试道场")) {
			chara.shidaoMartial += intimacy;
		} else {
			if(pet == null) {
				pet = getCurrentPet(chara);
			}
			if(pet != null) {
				PetShuXing petShuXing = pet.petShuXing.get(0);
				if (intimacy == 0) {
					int base_pet_dh = (int) (0.29 * petShuXing.skill * petShuXing.skill * petShuXing.skill);
					if(base_pet_dh<=0) {
						base_pet_dh = 1;
					}
					int baseNum = ((petShuXing.intimacy > base_pet_dh) ? (petShuXing.intimacy / base_pet_dh) : 1);
					int newIntimacy = 33 * petShuXing.skill
							/ baseNum<0?1:baseNum;
					petShuXing.intimacy += newIntimacy;
				}else {
					petShuXing.intimacy += intimacy;
				}
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "你的#Y" + petShuXing.str + "#n获得了#R" + intimacy + "#n武学。";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			}
		}
//		GameCommonUtil.addCharaTrail(chara, "武学", intimacy, source);
	}
	
	
	/**
	 * a获取当前pet
	 * @param chara
	 * @return
	 */
	public static Petbeibao getCurrentPet(Chara chara) {
		Petbeibao pet = null;
		for (Petbeibao p : chara.pets) {
			if(chara.awardSupplyPetId != 0) {
				if (p.id == chara.awardSupplyPetId) {
					pet = p;
					break;
				}
			}else {
				if (p.id == chara.chongwuchanzhanId) {
					pet = p;
					break;
				}
			}
		}
		return pet;
	}

	public static void integral_horcrux(Chara chara, String horcrux, int level, List<Hashtable<String, Object>> attr) {
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(horcrux);
		if (info == null) {
			sendTips("没有找到#Y" + horcrux, chara.id);
			return;
		} else {
			Goods goods = new Goods();
			goods.pos = GameUtil.packPoint(chara);
			goods.goodsInfo = new GoodsInfo();
			// 40图标
			goods.goodsInfo.type = info.getType();
			// 205等级+进化
			goods.goodsInfo.attrib = level;
			// 1
			goods.goodsInfo.str = info.getName();
			// 270限制交易
			goods.goodsInfo.recognize_recognized = info.getRecognizeRecognized();
			// 306标志
			goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
			// 74使用方式
			goods.goodsInfo.total_score = info.getTotalScore();
			// 207售价
			goods.goodsInfo.rebuild_level = 50000;
			goods.goodsInfo.value = info.getValue();
			// 197未鉴定
			goods.goodsInfo.degree_32 = 0;
			// 203叠加
			goods.goodsInfo.owner_id = 1;
			// 84发图
			goods.goodsInfo.damage_sel_rate = 400976;
			// 202装备位置 ..
			goods.goodsInfo.amount = 8;
			// 颜色
			goods.goodsInfo.quality = info.getQuality();
			// 270限制交易
			goods.goodsInfo.recognize_recognized = 1;
			// 268套装相性
			goods.goodsInfo.skill_level = 1;
			goods.goodsInfo.upgrade_degree = 0;

			goods.goodsHunQi = new GoodsHunqi();
			goods.goodsHunQi.hunqiState = 1;
			goods.goodsHunQi.zongShuxing = attr==null?HunqiUtils.chuShihua():attr;

			chara.backpack.add(goods);
			GameObjectChar.send(new M65525_0(), chara.backpack, chara.id);
			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 1;
			vo_40964_0.name = horcrux;
			vo_40964_0.param = "20691134";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0, chara.id);
		}
	}

	/**
	 * 创建魂器
	 * 
	 * @param chara 玩家
	 * @param name  魂器名字
	 */
	public static void createHunQi(Chara chara, Goods goods, String name) {
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
		if (info == null) {
			sendTips("没有找到#Y" + name, chara.id);
			return;
		} else {
			goods.pos = GameUtil.packPoint(chara);
			if (goods.goodsInfo == null) {
				goods.goodsInfo = new GoodsInfo();
				goods.goodsInfo.attrib = 75;
			}
			// 40图标
			goods.goodsInfo.type = info.getType();
			// 1
			goods.goodsInfo.str = info.getName();
			// 270限制交易
			goods.goodsInfo.recognize_recognized = info.getRecognizeRecognized();
			// 306标志
			goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
			// 74使用方式
			goods.goodsInfo.total_score = info.getTotalScore();
			// 207售价
			goods.goodsInfo.rebuild_level = 50000;
			goods.goodsInfo.value = info.getValue();
			// 197未鉴定
			goods.goodsInfo.degree_32 = 0;
			// 203叠加
			goods.goodsInfo.owner_id = 1;
			// 84发图
			goods.goodsInfo.damage_sel_rate = 400976;
			// 202装备位置 ..
			goods.goodsInfo.amount = 8;
			// 颜色
			goods.goodsInfo.quality = info.getQuality();
			// 270限制交易
			goods.goodsInfo.recognize_recognized = 1;
			// 268套装相性
			goods.goodsInfo.skill_level = 1;
			goods.goodsInfo.upgrade_degree = 0;

			goods.goodsHunQi = new GoodsHunqi();
			goods.goodsHunQi.hunqiState = 1;
			goods.goodsHunQi.zongShuxing = HunqiUtils.chuShihua();

			Vo_40964_0 vo_40964_0 = new Vo_40964_0();
			vo_40964_0.type = 1;
			vo_40964_0.name = name;
			vo_40964_0.param = "20691134";
			vo_40964_0.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_0);
		}
	}

	/**
	 * 发送系统邮件
	 * 
	 * @param chara  用户
	 * @param msg    消息
	 * @param title  标题
	 * @param sender 发件人
	 */
	public static Vo_MAILBOX_REFRESH sendSystemEmail(Chara chara, String msg, String title, String sender) {
		Vo_MAILBOX_REFRESH sendDeposit = new Vo_MAILBOX_REFRESH();
		sendDeposit.attachment = "";
		sendDeposit.toGid = chara.uuid;
		sendDeposit.create_time = (int) (System.currentTimeMillis() / 1000L);
		sendDeposit.expired_time = (int) (System.currentTimeMillis() / 1000L + 12 * 60 * 60);
		sendDeposit.id = GameCommonUtil.UUID();
		sendDeposit.msg = msg;
		sendDeposit.status = 0;
		sendDeposit.title = title;
		sendDeposit.type = 0;
		sendDeposit.sender = sender;
		MailboxRefresh mail2 = GameCommonUtil.convertMail(sendDeposit);
		GameData.that.mailboxRefreshService.insertSelective(mail2);
		
		return sendDeposit;
	}
	
	/**
	 * 发送系统邮件
	 * 
	 * @param chara  用户
	 * @param msg    消息
	 * @param title  标题
	 * @param sender 发件人
	 * @param attachment 附件
	 */
	public static Vo_MAILBOX_REFRESH sendSystemEmail(Chara chara, String msg, String title, String sender, String attachment) {
		Vo_MAILBOX_REFRESH sendDeposit = new Vo_MAILBOX_REFRESH();
		sendDeposit.attachment = "";
		sendDeposit.toGid = chara.uuid;
		sendDeposit.create_time = (int) (System.currentTimeMillis() / 1000L);
		sendDeposit.expired_time = (int) (System.currentTimeMillis() / 1000L + 36 * 60 * 60);
		sendDeposit.id = GameCommonUtil.UUID();
		sendDeposit.msg = msg;
		sendDeposit.status = 0;
		sendDeposit.title = title;
		sendDeposit.type = 0;
		sendDeposit.sender = sender;
		sendDeposit.attachment = attachment;
		MailboxRefresh mail2 = GameCommonUtil.convertMail(sendDeposit);
		GameData.that.mailboxRefreshService.insertSelective(mail2);
		return sendDeposit;
	}

	/**
	 * 刷新神魂
	 * 
	 * @param chara
	 */
	public static void refreShenHun(Chara chara) {
		Vo_REFRESH_SHENHUN_DATA data = new Vo_REFRESH_SHENHUN_DATA();
		data.setPhy_power(chara.shenHunPhyPower);
		data.setMag_power(chara.shenHunMagPower);
		data.setMax_life(chara.shenHunmaxLife);
		data.setDef(chara.shenHunDef);
		data.setSpeed(chara.shenHunSpeed);
		// 判断是否到达顶级
		data.setIsTop(1);
		data.setNextState(chara.shenHunDataSate);
		data.setNextLayer(chara.shenHunDataLaye);
		List<Vo_REFRESH_SHENHUN_DATA_ITEM> items = new ArrayList<>();
		Map<String, JSONObject> attri = GameConfig.shenHunConfig.getAttri();
		for (Map.Entry<String, JSONObject> a : attri.entrySet()) {
			JSONObject value = a.getValue();
			items.add(new Vo_REFRESH_SHENHUN_DATA_ITEM(value.getString("name"),
					value.getIntValue("value") * chara.shenHunDataSate));
		}
		data.setItems(items);
		for(Goods goods:chara.otherGoods) {
			if(goods.pos>=21 && goods.pos <= 25) {
				data.getHqPropData().add(goods);
			}
		}
		GameObjectChar.send(new MSG_REFRESH_SHENHUN_DATA(), data);
	}

	/**
	 * 刷新内丹
	 * @param chara
	 */
	public static void refreshNeidan(GameObjectChar gameObjectChar) {
		NeiDanConfig neiDanConfig = GameConfig.neiDanConfig;
		if(neiDanConfig != null) {
			Chara chara = gameObjectChar.chara;
			//级数
			String no = "no"+(chara.danDataState);
			//如果当前层是5阶段
			if(chara.danDataStage == 5) {
				//加一级
				no = "no"+(chara.danDataState+1);
			}
			List<NeiDanVo> nextNeiDanConfigs = neiDanConfig.getInfo().get(no);
			if(nextNeiDanConfigs != null) { 
				//当前阶段
				int stage = chara.danDataStage;
				if(stage >= 5) {
					stage = 0;
				}
				NeiDanVo nextNeiDanConfig = nextNeiDanConfigs.get(stage);
				Vo_REFRESH_NEIDAN_DATA neidan = new Vo_REFRESH_NEIDAN_DATA();
				neidan.setIsTop(chara.isNeiDanTop);
				//级数
				int state = chara.danDataState;
				//如果阶段==5
				if(chara.danDataStage == 5) {
					//加一阶段
					state +=1;
				}
				neidan.setNextState(state);
				//已达到最顶层
				if(chara.danDataState == 5 && chara.danDataStage == 5) {
					neidan.setNextStage(5);
					//下一级升级精气
					chara.danDataExpToNextLevel = neiDanConfig.getInfo().get("no5").get(4).getNextExp();
					Map<String,Object> data = new HashMap<>();
					data.put("dan_data/exp_to_next_level", chara.danDataExpToNextLevel);
					gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id,data));
				}else {
					neidan.setNextStage(stage+1);
					//下一级升级精气 no2 0
					chara.danDataExpToNextLevel = neiDanConfig.getInfo().get(no).get(stage).getNextExp();
					Map<String,Object> data = new HashMap<>();
					data.put("dan_data/exp_to_next_level", chara.danDataExpToNextLevel);
					gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id,data));
				}
				// 下一层属性点
				neidan.setNextAttributePoint(nextNeiDanConfig.getAttribPoint());
				// 下一层相性点
				neidan.setNextPolarPoint(nextNeiDanConfig.getPolarPoint());
				GameObjectChar.send(new MSG_REFRESH_NEIDAN_DATA(), neidan);
			}

			
		}
		
	}

	/**
	 * 添加变身卡 2001-2501；
	 * 
	 * @param chara
	 */
	public static int addCard(Goods goods, Chara chara, String... msg) {
		// 如果用户的卡套位置为0直接不处理以下的情况
		if (chara.cardSize == 0) {
			return -1;
		}
		List<Integer> original = new ArrayList<>();
		for (int i = 2001; i <= (2001 + chara.cardSize); i++) {
			original.add(i);
		}
		List<Integer> exits = new ArrayList<>();
		boolean isFind = false;
		if (chara.cardStore != null) {
			for (Goods g : chara.cardStore) {
				// 已经存在同类型变身卡，就直接增加数量.而不是增加栏位
				if (goods.goodsInfo.str.equals(g.goodsInfo.str)) {
					g.goodsInfo.owner_id += 1;
					isFind = true;
					log.info("找到同类型的:{}", g.pos);
				}
				exits.add(g.pos);
			}
			if (!isFind) {
				if (chara.cardStore.size() >= chara.cardSize) {
					// 位置已经满了无法再次使用
					GameUtil.sendMeTips("卡套位置已满,已自动放入到包裹中");
					return -1;
				}
				// 没有找到同类型变身卡则为这个商品创建位置
				for (Integer e : exits) {
					if (original.contains(e)) {
						original.remove(e);
					}
				}
				if (original != null && !original.isEmpty()) {
					goods.pos = original.get(0);
					log.info("创建位置:{}", goods.pos);
					chara.cardStore.add(goods);
				} else {
					GameUtil.sendMeTips("没有可用的卡套位置");
					return -1;
				}
			}
			if (msg == null || msg.length == 0) {
				GameUtil.sendMeTips("你已成功将#R" + goods.goodsInfo.str + "#n存入卡套。");
			} else {
				GameUtil.sendMeTips(msg[0]);
			}
		}
		return 0;
	}

	/**
	 * 添加亲密伤害
	 * 
	 * @param petShuXing
	 */
	public static void addQimMiShangHai(Petbeibao pet) {
		PetShuXing petShuXing = pet.petShuXing.get(0);
		if (petShuXing.shape >= 5000000) {
			++petShuXing.maxReviveTimes;
			petShuXing.qinmiAccurate = 10000;
			petShuXing.qinmiMana = 10000;
			petShuXing.qinmiWiz = 10000;
			petShuXing.qinmiParry = 2000;
			petShuXing.use500w = true;
		} else if (petShuXing.shape >= 2000000) {
			++petShuXing.maxReviveTimes;
			petShuXing.qinmiAccurate = 8000;
			petShuXing.qinmiMana = 8000;
			petShuXing.qinmiWiz = 8000;
			petShuXing.qinmiParry = 1500;
			petShuXing.use200w = true;
		} else if (petShuXing.shape >= 1000000) {
			++petShuXing.maxReviveTimes;
			petShuXing.qinmiAccurate = 6000;
			petShuXing.qinmiMana = 6000;
			petShuXing.qinmiWiz = 6000;
			petShuXing.qinmiParry = 1000;
			petShuXing.use100w = true;
		} else if (petShuXing.shape > 500000) {
			++petShuXing.maxReviveTimes;
			petShuXing.qinmiAccurate = 4000;
			petShuXing.qinmiMana = 4000;
			petShuXing.qinmiWiz = 4000;
			petShuXing.qinmiParry = 500;
			petShuXing.use50w = true;
		} else if (petShuXing.shape > 300000) {
			++petShuXing.maxReviveTimes;
			petShuXing.qinmiAccurate = 2000;
			petShuXing.qinmiMana = 2000;
			petShuXing.qinmiWiz = 2000;
			petShuXing.qinmiParry = 300;
			petShuXing.use30w = true;
		}
		BasicAttributesUtils.petshuxing(petShuXing,pet);
	}

	public synchronized static List<Object> removeRepeatFactor(List<Object> list1, List<Object> list2) {
		if (list1 != null && list2 != null) {
			if (list1.size() != 0 && list2.size() != 0) {
				Collection<Object> A = new ArrayList<Object>(list1);
				Collection<Object> B = new ArrayList<Object>(list2);
				A.retainAll(B);
				if (A.size() != 0) {
					B.removeAll(A);
				}
				return (List<Object>) B;
			}
		}
		return list2;
	}

	public static Object deepClone(Object object) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);
			ByteArrayInputStream bais = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Object) ois.readObject();
		} catch (Exception e) {
			log.error("{}", e);
		}
		throw new RuntimeException("克隆失败");
	}

	/**
	 * 使用变身卡
	 * 
	 * @param chara
	 * @param pos
	 */
	public static void applyCard(Chara chara, int pos) {
		List<Goods> backpack = chara.backpack;
		GoodsInfo goodsInfo = null;
		Goods goods = null;
		boolean isCardStore = false;
		if (pos >= 2001 && pos <= 2501) {
			// 变身卡仓库
			for (int i = 0; i < chara.cardStore.size(); i++) {
				Goods goods2 = chara.cardStore.get(i);
				if (goods2.pos == pos) {
					goods = goods2;
					goodsInfo = goods2.goodsInfo;
					isCardStore = true;
					break;
				}
			}
		} else {
			for (Goods g : backpack) {
				if (g.pos == pos) {
					// 获取背包商品信息
					goodsInfo = g.goodsInfo;
					goods = g;
					break;
				}
			}
		}
		if (goodsInfo == null) {
			GameUtil.sendMeTips("未找到#Y这个商品信息。");
			return;
		}
		Example example = new Example(ChangeCard.class);
		example.createCriteria().andEqualTo("name", goodsInfo.str);
		ChangeCard changeCard = GameData.that.changeCardService.selectOneByExample(example);
		if (changeCard == null) {
			GameUtil.sendMeTips("未找到#Y" + goodsInfo.str + "#n配置信息。");
			return;
		}
		// 先删除之前的定时器
		GameData.that.redisUtils.delete(DefinedConst.CHANGE_CARD + ";" + chara.uuid);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - changeCard.getTime());
		VoChangeCard voCard = new VoChangeCard();
		voCard.setIcon(changeCard.getIcon());
		voCard.setType(changeCard.getType());
		voCard.setLevel(changeCard.getLeve());
		String reward = "";
		try {
			String attr = changeCard.getAttr();
			List<ChangeCardAttr> parseArray = JSONObject.parseArray(attr, ChangeCardAttr.class);
			voCard.setAttr(parseArray);
			// #T附加效果#T#C变身效果#C#B物伤 +8%、#B物伤 +8%
			reward = "#T附加效果#T#C变身效果#C";
			StringBuilder app = new StringBuilder();
			int i = 1;
			for (ChangeCardAttr c : parseArray) {
				app.append("#B").append(c.getName()).append(" +").append(c.getValue()).append("%").append("      ");
				if (i % 3 == 0) {
					app.append("#C");
				}
				i++;
			}
			reward = app.toString();
		} catch (Exception e) {
			log.error("{}", e);
		}
		voCard.setName(changeCard.getName());
		// 开始时间
		voCard.setStartTime(System.currentTimeMillis());
		int endTime = (int) ((System.currentTimeMillis() / 1000L + changeCard.getTime() * 60 * 60) - 60);
		voCard.setEndTime(endTime);
		// 小时
		voCard.setHour(changeCard.getTime());
		chara.changeCardInfo = voCard;

		// 刷新地图数据--让所有人都能看到
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);

		// 播放使用变身卡声音.
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sound", "bianshen");
		GameObjectChar.getGameObjectChar().gameMap.send(new CommonWrite(0xD043), map);
		// 播放动画效果
		GameCommonUtil.charaPlay(GameObjectChar.getGameObjectChar(), 1261, 1);
		// 增加千变万化
		Vo_61553_0 vo_61553_2 = new Vo_61553_0();
		vo_61553_2.count = 1;
		vo_61553_2.task_type = "千变万化";
		vo_61553_2.task_desc = "你使用了#R" + goodsInfo.str + "#W，变身效果持续时间还剩余#RTIME_LEFT#n，此效果下线后不消失，但仍然计时。";
		vo_61553_2.task_prompt = goodsInfo.str + "还剩余#RTIME_LEFT";
		vo_61553_2.refresh = 1;
		vo_61553_2.task_end_time = endTime;
		vo_61553_2.attrib = 1;
		vo_61553_2.reward = reward;
		vo_61553_2.show_name = "千变万化";
		vo_61553_2.task_extra_para = "";
		vo_61553_2.task_state = "0";
		chara.taskMap.put("千变万化", vo_61553_2);
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_2);
		// 任务保存redis中
		GameData.that.redisUtils.set(DefinedConst.CHANGE_CARD + ";" + chara.uuid, goodsInfo.str,
				changeCard.getTime() * 60 * 60);
		GameUtil.sendUpdate(chara);
		if (isCardStore) {
			// 删除变身卡
			if (goods.goodsInfo.owner_id == 1) {
				chara.cardStore.remove(goods);
				// 刷新仓库
				Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
				vo_61677_0.pos = goods.pos;
				GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_0);
			} else {
				goods.goodsInfo.owner_id -= 1;
				// 变身卡套
				Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
				vo_61677_0.list = chara.cardStore;
				GameObjectChar.send(new M61677_0(), vo_61677_0);
			}
		} else {
			// 从背包使用
			GameUtil.removemunber(chara, goods, 1);
		}
		GameUtil.sendMeTips("你使用了一张#R" + goodsInfo.str + "#n。可在任务界面查看变身卡效果");
	}

	/**
	 * 神木鼎打卡
	 * 
	 * @param charas
	 * @param guaiwu
	 */
	public static void shenMuDingFightCard(GameObjectChar gameObjectChar, List<FightObject> guaiwu) {
		Chara chara = gameObjectChar.chara;
		// 判断是否开启神木鼎
		if (chara.shenmoding == 1) {
			if (new Random().nextBoolean()) {
				// 从怪物中随机取出一个怪物
				FightObject fightObject = guaiwu.get(new Random().nextInt(guaiwu.size()));
				String cardName = "超级" + fightObject.str + "卡";
				int defaultSubPoint = 30;
				if (new Random().nextInt(100) > 90) {
					// 百分只50的机会爆出特殊变身卡
					int switchCards = new Random().nextInt(3) + 1;
					if (switchCards == 1) {
						// 神兽卡
						defaultSubPoint = 300;
						cardName = shenShouCard[new Random().nextInt(shenShouCard.length)];
					} else if (switchCards == 2) {
						// 变异卡
						defaultSubPoint = 200;
						cardName = bianyiCard[new Random().nextInt(bianyiCard.length)];
					} else {
						// boss卡
						defaultSubPoint = 150;
						cardName = bossCard[new Random().nextInt(bossCard.length)];
					}
				}
				if ((chara.enable_shenmu_points - defaultSubPoint) < 0) {
					// 神木鼎点数不足
					GameCommonUtil.sendTips("你的神木鼎点数不足,无法获得卡片。请及时补充", chara.id);
					chara.shenmoding = 0;
					GameUtil.sendUpdate(chara);
				} else {
					StoreInfo findOneByName = GameData.that.baseStoreInfoService.findOneByName(cardName);
					if (findOneByName != null) {
						if (defaultSubPoint > 30) {
							GameCommonUtil.sendTips(
									"获得一张特殊变身卡，#R" + cardName + "#n消耗了#R" + defaultSubPoint + "#n点神木鼎点数。", chara.id);
						} else {
							GameCommonUtil.sendTips("获得一张#R" + cardName + "#n消耗了#R" + defaultSubPoint + "#n点神木鼎点数。",
									chara.id);
						}
						chara.enable_shenmu_points -= defaultSubPoint;
						GameUtil.sendUpdate(chara);
						log.info("获得变身卡。。。。。。");
						Vo_40964_0 vo_40964_0 = new Vo_40964_0();
						vo_40964_0.type = 1;
						vo_40964_0.name = cardName;
						vo_40964_0.param = "20691134";
						vo_40964_0.rightNow = 0;
						GameObjectChar.send(new M40964_0(), vo_40964_0, chara.id);
						// 获取变身卡
						GameUtil.huodedaoju(gameObjectChar, findOneByName, 1);
					}
				}
			}
		}
	}

	/**
	 * 拒绝请求时间
	 * 
	 * @param chara
	 * @param prefix 前缀
	 * @param msg    提示
	 * @param second 时间
	 * @return
	 */
	public static boolean rejectRequestTimeOut(Chara chara, String prefix, String msg, int second) {
		if (GameData.that.redisUtils.get(prefix + "_" + chara.uuid) != null) {
			if (msg != null && !msg.equals("")) {
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = msg;
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
			}
			return true;
		}
		GameData.that.redisUtils.set(prefix + "_" + chara.uuid, chara.uuid, second);
		return false;
	}

	/**
	 * 拒绝请求时间毫秒
	 * 
	 * @param chara
	 * @param prefix 前缀
	 * @param msg    提示
	 * @param second 时间
	 * @return
	 */
	public static boolean rejectRequestTimeOutForSecond(Chara chara, String prefix, String msg, long second) {
		if (GameData.that.redisUtils.get(prefix + "_" + chara.uuid) != null) {
			if (msg != null && !msg.equals("")) {
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = msg;
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
			}
			return true;
		}
		GameData.that.redisUtils.setSecond(prefix + "_" + chara.uuid, chara.uuid, second);
		return false;
	}

	/**
	 * 获取活跃度底板奖励信息
	 * 
	 * @param chara
	 * @return
	 */
	public static List<Integer[]> getLivenessRewards(Chara chara) {
		int activeNum = 0;
		activeNum += (chara.shimencishu - 1) * 2;
		activeNum += (chara.chubao - 1) * 2;
		activeNum += chara.partyNum * 1;
		activeNum += chara.fb_num * 10;
		activeNum += chara.xiuxingcishu * 0.5;
		activeNum += chara.baibangmang * 10;
		activeNum += chara.partyFightNum * 10;
		activeNum += chara.shuadao * 0.5;
		activeNum += chara.tongttcishu * 20;
		activeNum += chara.xiufacishu * 10;
		if (activeNum > 100) {
			activeNum = 100;
		}
		List<Integer[]> activeDatas = new ArrayList<>();
		LivenessRewardsService lrs = SpringBeanUtils.getBean(LivenessRewardsService.class);
		Map<Integer, List<LivenessRewards>> data = lrs.getLivenessRewardsByActiveToDay(chara.uuid);
		if (activeNum >= 20) {
			Integer[] liveness = new Integer[2];
			liveness[0] = 20;
			if (data.get(20) != null) {
				liveness[1] = 1;
			} else {
				// 未领取
				liveness[1] = 2;
			}
			activeDatas.add(liveness);
		}
		if (activeNum >= 40) {
			Integer[] liveness = new Integer[2];
			liveness[0] = 40;
			if (data.get(40) != null) {
				liveness[1] = 1;
			} else {
				// 未领取
				liveness[1] = 2;
			}
			activeDatas.add(liveness);
		}
		if (activeNum >= 60) {
			Integer[] liveness = new Integer[2];
			liveness[0] = 60;
			if (data.get(60) != null) {
				liveness[1] = 1;
			} else {
				// 未领取
				liveness[1] = 2;
			}
			activeDatas.add(liveness);
		}

		if (activeNum >= 80) {
			Integer[] liveness = new Integer[2];
			liveness[0] = 80;
			if (data.get(80) != null) {
				liveness[1] = 1;
			} else {
				// 未领取
				liveness[1] = 2;
			}
			activeDatas.add(liveness);
		}

		if (activeNum >= 100) {
			Integer[] liveness = new Integer[2];
			liveness[0] = 100;
			if (data.get(100) != null) {
				liveness[1] = 1;
			} else {
				// 未领取
				liveness[1] = 2;
			}
			activeDatas.add(liveness);
		}

		return activeDatas;
	}

	/**
	 * 设置gm状态
	 * 
	 * @param session
	 */
	public static void setGmStatus2(List<GameObjectChar> sessions) {
		for (GameObjectChar session : sessions) {
			// 设置gm状态
			if (session != null && session.privilege == 1000 && session.isHide == 1) {
				GameObjectChar gm = session;
				List<GameObjectChar> sessionList = gm.gameMap.sessionList;
				for (GameObjectChar g : sessionList) {
					// 通知地图所有人,GM大大隐身了
					Vo_UPDATE_APPEARANCE vo = GameUtil.a61661(g.chara);
					if (g.chara.id == gm.chara.id) {
						vo.isHide = 1;
					}
					g.gameMap.send(new MSG_UPDATE_APPEARANCE(), vo);
				}
				// 再次把自己设置状态
				Vo_UPDATE_APPEARANCE a61661 = GameUtil.a61661(gm.chara);
				a61661.isHide = 0;
				a61661.opacity = 30;
				gm.sendOne(new MSG_UPDATE_APPEARANCE(), a61661);
			}
		}
	}

	/**
	 * 进入动态地图
	 * 
	 * @param map
	 */
	public static void enterDynamicMap(String mapName, Chara chara) {
		com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName(mapName);
		if (map == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不符合条件";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			return;
		}
		GameZone gameZone = GameLine.createGameZone(chara.line, map.getMapId());
		chara.y = map.getY();
		chara.x = map.getX();
		gameZone.join(GameObjectCharMng.getGameObjectChar(chara.id));
	}

	/**
	 * 判断是否为gm
	 * 
	 * @param gameObject
	 * @return
	 */
	public static boolean isNotGameManage(GameObjectChar gameObject) {
		if (gameObject.privilege == 1000) {
			return false;
		}
		return true;
	}

	/**
	 * 结束战斗场景
	 * 
	 * @param chara
	 */
	public static void endCombat(List<GameObjectChar> charas, FightContainer fightContainer, GameObjectChar endGameChara) {
		//战斗记录输出到日志
//		log.error("战斗记录::::::版本号={},参战A={},参战B={},动作={}",GameCommonUtil.gameVersion,JSONObject.toJSONString(fightContainer.fightCharasA),JSONObject.toJSONString(fightContainer.fightCharasB),JSONObject.toJSONString(fightContainer.fightRecords));
		for (GameObjectChar game : charas) {
			if (game == null) {
				continue;
			}
			game.isEndRound.set(true);
			Chara chara = game.chara;
			
//			ZbAttribute zbAttribute = new ZbAttribute();
//			zbAttribute.id = game.chara.id;
//			zbAttribute.accurate = 0;
//			zbAttribute.mana =0;
//			zbAttribute.wiz = 0;
//			zbAttribute.parry = 0;
//			game.sendOne(new M64991_0(), zbAttribute);
			
			// 设置宠物参战ID
			Vo_4163_0 vo_4163_0 = new Vo_4163_0();
			vo_4163_0.id = chara.chongwuchanzhanId;
			vo_4163_0.b = 1;
			game.sendOne(new M4163_0(), vo_4163_0);
			// 设置宠物掠阵
			if (chara.chongwuluezhenId != 0) {
				vo_4163_0 = new Vo_4163_0();
				vo_4163_0.id = chara.getChongwuluezhenId();
				vo_4163_0.b = 2;
				game.sendOne(new M4163_0(), vo_4163_0);
			}
			Vo_3583_0 vo_3583_0 = new Vo_3583_0();
			vo_3583_0.flag = 1;
			vo_3583_0.mode = 3;
			game.sendOne(new MSG_C_START_COMBAT(), vo_3583_0);
			
			Vo_C_END_COMBAT vo_3581_0 = new Vo_C_END_COMBAT();
			vo_3581_0.a = 1;
			game.sendOne(new MSG_C_END_COMBAT(), vo_3581_0);
			// 设置战斗标识
			chara.setFight(false);
			// 删除正在战斗的boss
			GameCore.fightObject.remove(chara.zhandouId);
			// 清除战斗信息
			String zhandouInfo = chara.zhandouInfo;
			if (zhandouInfo != null) {
				GameConfig.canzhanBoos.remove(zhandouInfo);
			}
			chara.zhandouInfo = null;
			chara.zhandouId = 0;
			//刷新
			GameUtil.sendUpdate(game.chara);
			// 刷新宠物
			if (game != null && game.chara != null &&game.chara.pets != null
					&& !game.chara.pets.isEmpty()) {
				for (Petbeibao pet : game.chara.pets) {
					if (pet.id == game.chara.chongwuchanzhanId) {
						game.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
						break;
					}
				}
			}
			if(endGameChara == null) {
				GameCommonUtil.sendTips("你使用了强制结束战斗指令。", game);
			}else {
				GameCommonUtil.sendTips("#Y"+endGameChara.chara.name+"#n使用了强制结束卡战斗指令，结束了这场战斗！", game);
			}
			GameCommonUtil.setCharaTitleFlag(chara);
		}
		if(fightContainer != null) {
			GameData.that.redisUtils.delete("fightTime_"+fightContainer.uid);
			FightManager.listFight.remove(fightContainer);
		}
	}
	/**
	 * 结束战斗场景
	 *
	 * @param chara
	 */
	public static void endCombat(List<GameObjectChar> charas) {
		//战斗记录输出到日志
//		log.error("战斗记录::::::版本号={},参战A={},参战B={},动作={}",GameCommonUtil.gameVersion,JSONObject.toJSONString(fightContainer.fightCharasA),JSONObject.toJSONString(fightContainer.fightCharasB),JSONObject.toJSONString(fightContainer.fightRecords));
		for (GameObjectChar game : charas) {
			if (game == null) {
				continue;
			}
			game.isEndRound.set(true);
			Chara chara = game.chara;

//			ZbAttribute zbAttribute = new ZbAttribute();
//			zbAttribute.id = game.chara.id;
//			zbAttribute.accurate = 0;
//			zbAttribute.mana =0;
//			zbAttribute.wiz = 0;
//			zbAttribute.parry = 0;
//			game.sendOne(new M64991_0(), zbAttribute);

			// 设置宠物参战ID
			Vo_4163_0 vo_4163_0 = new Vo_4163_0();
			vo_4163_0.id = chara.chongwuchanzhanId;
			vo_4163_0.b = 1;
			game.sendOne(new M4163_0(), vo_4163_0);
			// 设置宠物掠阵
			if (chara.chongwuluezhenId != 0) {
				vo_4163_0 = new Vo_4163_0();
				vo_4163_0.id = chara.getChongwuluezhenId();
				vo_4163_0.b = 2;
				game.sendOne(new M4163_0(), vo_4163_0);
			}
			Vo_3583_0 vo_3583_0 = new Vo_3583_0();
			vo_3583_0.flag = 1;
			vo_3583_0.mode = 3;
			game.sendOne(new MSG_C_START_COMBAT(), vo_3583_0);

			Vo_C_END_COMBAT vo_3581_0 = new Vo_C_END_COMBAT();
			vo_3581_0.a = 1;
			game.sendOne(new MSG_C_END_COMBAT(), vo_3581_0);
			// 设置战斗标识
			chara.setFight(false);
			// 删除正在战斗的boss
			GameCore.fightObject.remove(chara.zhandouId);
			// 清除战斗信息
			String zhandouInfo = chara.zhandouInfo;
			if (zhandouInfo != null) {
				GameConfig.canzhanBoos.remove(zhandouInfo);
			}
			chara.zhandouInfo = null;
			chara.zhandouId = 0;
			//刷新
			GameUtil.sendUpdate(game.chara);
			// 刷新宠物
			if (game != null && game.chara != null &&game.chara.pets != null
					&& !game.chara.pets.isEmpty()) {
				for (Petbeibao pet : game.chara.pets) {
					if (pet.id == game.chara.chongwuchanzhanId) {
						game.sendOne(new MSG_UPDATE_PETS(), Lists.newArrayList(pet));
						break;
					}
				}
			}
			GameCommonUtil.setCharaTitleFlag(chara);
		}
	}

	/**
	 * 添加物品到仓库
	 * 
	 * @param goods 物品
	 * @param chara
	 */
	public static void cangkuaddwupin(Goods goods, Chara chara) {
		// 单个物品最大叠加数
		int maxSize = getMaxSuperPosition(goods);
		// 仓库所有位置.这里不校验是否已满
		List<Integer> storeAllPos = getStorePos(chara);
		boolean isFind = false;
		// 如果物品可以叠加的话.
		if (maxSize > 1) {
			// 去仓库查找是否有这个物品存在
			for (Goods store : chara.cangku) {
				// 找到这个物品.并且最大叠加
				if (store.goodsInfo.str.equals(goods.goodsInfo.str) && store.goodsInfo.owner_id < maxSize
						&& goods.goodsInfo.attrib == store.goodsInfo.attrib
						&& goods.goodsInfo.skill == store.goodsInfo.skill) {
					log.info("找到同样的仓库物品，name={}", goods.goodsInfo.str);
					// 计算这个物品叠加是否溢出,如果溢出的话.则在找一个位置.
					int availableSize = maxSize - store.goodsInfo.owner_id;
					/**
					 * 如果这个物品的叠加数量大于当前这个格子最大可用的叠加数量. 溢出的商品则另外找一个格子存放.
					 */
					if (goods.goodsInfo.owner_id > availableSize) {
						// 更新仓库物品的叠加数量
						store.goodsInfo.owner_id += availableSize;
						// 更新背包中剩余部分的叠加数量
						goods.goodsInfo.owner_id -= availableSize;
						// 更新这个商品信息
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
						// 再次调用方法,存放剩余的商品
						cangkuaddwupin(goods, chara);
					} else {
						// 更新仓库物品的叠加数量
						store.goodsInfo.owner_id += goods.goodsInfo.owner_id;
						// 没有溢出的话，表示这个格子够用。那就直接删除玩家背包中这个商品
						completelyDelete(goods, chara, 1);
					}
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				// 未找到可叠加位置
				log.info("未找到可叠加位置,创建一个位置");
				int avaliablePos = getAvaliablePos(chara.cangku, storeAllPos);
				if (avaliablePos == -1) {
					GameUtil.sendMeTips("仓库已满！！");
					return;
				}
				Goods newGoods = BeanUtils.clone(goods);
				newGoods.pos = avaliablePos;
				chara.cangku.add(newGoods);
				Vo_61677_0 vo_61677_4 = new Vo_61677_0();
				vo_61677_4.list = chara.cangku;
				GameObjectChar.send(new M61677_0(), vo_61677_4);
				// 删除这个物品
				completelyDelete(goods, chara, 1);
			}
		} else {
			// 不能叠加则直接创建位置.
			int avaliablePos = getAvaliablePos(chara.cangku, storeAllPos);
			if (avaliablePos == -1) {
				GameUtil.sendMeTips("仓库已满！！");
				return;
			}
			Goods newGoods = BeanUtils.clone(goods);
			newGoods.pos = avaliablePos;
			chara.cangku.add(newGoods);
			Vo_61677_0 vo_61677_4 = new Vo_61677_0();
			vo_61677_4.list = chara.cangku;
			GameObjectChar.send(new M61677_0(), vo_61677_4);
			// 删除这个物品
			completelyDelete(goods, chara, 1);
		}
	}

	/**
	 * 添加仓库物品到背包
	 * 
	 * @param goods 物品
	 * @param chara
	 */
	public static void addStoreGoodsToBackpack(Goods goods, Chara chara) {
		if (goods == null) {
			return;
		}
		// 单个物品最大叠加数
		int maxSize = getMaxSuperPosition(goods);
		// 背包所有位置.这里不校验是否已满
		List<Integer> backpackAllPos = getBackpackPos(chara);
		boolean isFind = false;
		// 如果物品可以叠加的话.
		if (maxSize > 1) {
			// 去背包查找是否有这个物品存在
			for (Goods store : chara.backpack) {
				// 找到这个物品.并且最大叠加
				if (store.goodsInfo.str.equals(goods.goodsInfo.str) && store.goodsInfo.owner_id < maxSize
						&& goods.goodsInfo.attrib == store.goodsInfo.attrib
						&& goods.goodsInfo.skill == store.goodsInfo.skill) {
					log.info("找到可以叠加的物品，name={}", goods.goodsInfo.str);
					// 计算这个物品叠加是否溢出,如果溢出的话.则在找一个位置.
					int availableSize = maxSize - store.goodsInfo.owner_id;
					/**
					 * 如果这个物品的叠加数量大于当前这个格子最大可用的叠加数量. 溢出的商品则另外找一个格子存放.
					 */
					if (goods.goodsInfo.owner_id > availableSize) {
						// 更新仓库物品的叠加数量
						store.goodsInfo.owner_id += availableSize;
						// 更新背包中剩余部分的叠加数量
						goods.goodsInfo.owner_id -= availableSize;
						// 更新这个商品信息
						if (GameObjectCharMng.getGameObjectChar(chara.id) == null) {
							return;
						}
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(store));
						// 再次调用方法,存放剩余的商品
						addStoreGoodsToBackpack(goods, chara);
					} else {
						// 更新仓库物品的叠加数量
						store.goodsInfo.owner_id += goods.goodsInfo.owner_id;
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(store));
						// 没有溢出的话，表示这个格子够用。那就直接删除玩家背包中这个商品
						completelyDelete(goods, chara, 0);
					}
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				// 未找到可叠加位置
				log.info("未找到可叠加位置,创建一个位置");
				int avaliablePos = getAvaliablePos(chara.backpack, backpackAllPos);
				if (avaliablePos == -1) {
					GameUtil.sendMeTips("背包已满！！");
					// 直接抛出异常
					throw new PackOverflowException();
				}
				Goods newGoods = BeanUtils.clone(goods);
				newGoods.pos = avaliablePos;
				chara.backpack.add(newGoods);

				Vo_61677_0 vo_61677_4 = new Vo_61677_0();
				vo_61677_4.list = chara.cangku;
				GameObjectChar.send(new M61677_0(), vo_61677_4);
				// 背包
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(newGoods));
				// 删除这个物品
				completelyDelete(goods, chara, 0);
			}
		} else {
			// 不能叠加则直接创建位置.
			int avaliablePos = getAvaliablePos(chara.backpack, backpackAllPos);
			if (avaliablePos == -1) {
				GameUtil.sendMeTips("背包已满！！");
				// 直接抛出异常
				throw new PackOverflowException();
			}
			Goods newGoods = BeanUtils.clone(goods);
			newGoods.pos = avaliablePos;
			chara.backpack.add(newGoods);
			// 刷新仓库
			Vo_61677_0 vo_61677_4 = new Vo_61677_0();
			vo_61677_4.list = chara.cangku;
			GameObjectChar.send(new M61677_0(), vo_61677_4);
			// 背包
			GameObjectChar.send(new M65525_0(), Lists.newArrayList(newGoods));
			// 删除这个物品
			completelyDelete(goods, chara, 0);
		}
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.list = chara.cangku;
		GameObjectChar.send(new M61677_0(), vo_61677_0);
	}

	/**
	 * 批量添加物品到背包
	 * 
	 * @param goods
	 * @param chara
	 * @return false 添加失败 true添加成功
	 */
	public static boolean addGoodsToBackpack(Goods goods, GameObjectChar gameObjectChar, int count) {
		goods.goodsInfo.owner_id = count;
		return addGoodsToBackpack(goods, gameObjectChar);
	}

	/**
	 * 添加物品到背包
	 * 
	 * @param goods 物品
	 * @param chara
	 * @return false 添加失败 true添加成功
	 */
	public static boolean addGoodsToBackpack(Goods goods, GameObjectChar gameObjectChar) {
		if (goods == null || gameObjectChar == null) {
			return false;
		}
		Chara chara = gameObjectChar.chara;
		// 单个物品最大叠加数
		int maxSize = getMaxSuperPosition(goods);
		// 背包所有位置.这里不校验是否已满
		List<Integer> backpackAllPos = getBackpackPos(chara);
		boolean isFind = false;
		// 如果物品可以叠加的话.
		if (maxSize > 1) {
			// 去背包查找是否有这个物品存在
			for (Goods store : chara.backpack) {
				// 找到这个物品.并且最大叠加
				if (store.goodsInfo.str.equals(goods.goodsInfo.str) && store.goodsInfo.owner_id < maxSize
						&& goods.goodsInfo.attrib == store.goodsInfo.attrib
						&& goods.goodsInfo.skill == store.goodsInfo.skill) {
					log.info("找到可以叠加的物品，name={}", goods.goodsInfo.str);
					// 计算这个物品叠加是否溢出,如果溢出的话.则在找一个位置.
					int availableSize = maxSize - store.goodsInfo.owner_id;
					/**
					 * 如果这个物品的叠加数量大于当前这个格子最大可用的叠加数量. 溢出的商品则另外找一个格子存放.
					 */
					if (goods.goodsInfo.owner_id > availableSize) {
						// 更新仓库物品的叠加数量
						store.goodsInfo.owner_id += availableSize;
						// 更新背包中剩余部分的叠加数量
						goods.goodsInfo.owner_id -= availableSize;
						// 更新这个商品信息
						if (GameObjectCharMng.getGameObjectChar(chara.id) == null) {
							return false;
						}
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(store), chara.id);
						// 再次调用方法,存放剩余的商品
						addGoodsToBackpack(goods, gameObjectChar);
					} else {
						// 更新仓库物品的叠加数量
						store.goodsInfo.owner_id += goods.goodsInfo.owner_id;
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(store), chara.id);
						// 没有溢出的话，表示这个格子够用。那就直接删除玩家背包中这个商品
						completelyDelete(goods, chara, 0);
					}
					isFind = true;
					break;
				}
			}
			if (!isFind) {
				// 未找到可叠加位置
				log.info("未找到可叠加位置,创建一个位置");
				int avaliablePos = getAvaliablePos(chara.backpack, backpackAllPos);
				if (avaliablePos == -1) {
					GameUtil.sendMeTips("背包已满！！");
					return false;
				}
				Goods newGoods = BeanUtils.clone(goods);
				newGoods.pos = avaliablePos;
				// 如果本次要添加的大于最大叠加数，那就让这个格子成为最大的格子
				if (goods.goodsInfo.owner_id > maxSize) {
					// 更新仓库物品的叠加数量
					newGoods.goodsInfo.owner_id = maxSize;
					// 更新背包中剩余部分的叠加数量
					goods.goodsInfo.owner_id -= maxSize;
					// 更新这个商品信息
					if (GameObjectCharMng.getGameObjectChar(chara.id) == null) {
						return false;
					}
					gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(newGoods));
					chara.backpack.add(newGoods);
					// 再次调用方法,存放剩余的商品
					addGoodsToBackpack(goods, gameObjectChar);
				} else {
					newGoods.goodsInfo.auto_fight = UUID();
					// 更新仓库物品的叠加数量
					newGoods.goodsInfo.owner_id = goods.goodsInfo.owner_id;
					gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(newGoods));
					chara.backpack.add(newGoods);
					// 没有溢出的话，表示这个格子够用。那就直接删除玩家背包中这个商品
					completelyDelete(goods, chara, 0);
				}

			}
		} else {
			List<Goods> newGoodss = new ArrayList<>();
			for (int i = 0; i < goods.goodsInfo.owner_id; i++) {
				// 不能叠加则直接创建位置.
				int avaliablePos = getAvaliablePos(chara.backpack, backpackAllPos);
				if (avaliablePos == -1) {
					GameUtil.sendMeTips("背包已满！！");
					return false;
				}
				Goods newGoods = BeanUtils.clone(goods);
				newGoods.goodsInfo.auto_fight = UUID();
				newGoods.pos = avaliablePos;
				newGoods.goodsInfo.owner_id = 1;
				chara.backpack.add(newGoods);
				newGoodss.add(newGoods);
			}
			// 背包
			gameObjectChar.sendOne(new M65525_0(), newGoodss);
			// 删除这个物品
			completelyDelete(goods, chara, 0);
		}
		
		return true;
	}

	/**
	 * 获取仓库的所有位置.
	 * 
	 * @param chara
	 * @return
	 */
	public static List<Integer> getStorePos(Chara chara) {
		List<Integer> original = new ArrayList<>();
		int maxStoreSize = 50;
		if (chara.vipType == 1 || chara.vipType == 2) {
			maxStoreSize += 25;
		}
		if (chara.vipType == 3) {
			maxStoreSize += 50;
		}
		for (int i = 201; i < (201 + maxStoreSize); i++) {
			original.add(i);
		}

		return original;
	}

	/**
	 * 获取背包的所有位置.
	 * 
	 * @param chara
	 * @return
	 */
	public static List<Integer> getBackpackPos(Chara chara) {
		List<Integer> original = new ArrayList<>();
		int maxBackpackSize = 50;
		if (chara.isDownZuoQi == 1 || chara.zuoqiId != 0) {
			maxBackpackSize += 25;
		}
		if (chara.vipType == 1 || chara.vipType == 2) {
			maxBackpackSize += 25;
		}
		if (chara.vipType == 3) {
			maxBackpackSize += 50;
		}
		for (int i = 41; i < (41 + maxBackpackSize); i++) {
			original.add(i);
		}
		return original;
	}

	/**
	 * 返回可用的位置
	 * 
	 * @param goods  商品集合
	 * @param allPos 所有的pos
	 * @return
	 */
	public static int getAvaliablePos(List<Goods> goods, List<Integer> allPos) {
		List<Integer> exits = new ArrayList<>();
		for (Goods store : goods) {
			exits.add(store.pos);
		}
		// 删除已经用过的位置.
		for (Integer i : exits) {
			if (allPos.contains(i)) {
				allPos.remove(i);
			}
		}
		return allPos == null || allPos.isEmpty() ? -1 : allPos.get(0);
	}

	/**
	 * 彻底删除一个物品
	 * 
	 * @param goods 商品信息
	 * @param type  0:仓库 1:背包
	 */
	public static void completelyDelete(Goods goods, Chara chara, int type) {
		// 删除
		if (type == 0) {
			chara.cangku.remove(goods);
		} else if (type == 1) {
			Goods removeGoods = new Goods();
			removeGoods.goodsInfo = null;
			removeGoods.goodsBasics = null;
			removeGoods.pos = goods.pos;
			GameObjectChar.send(new M65525_0(), Lists.newArrayList(removeGoods), chara.id);
			chara.backpack.remove(goods);
		}
	}

	/**
	 * 获取某个物品最大叠加数量
	 * 
	 * @param goods 商品
	 * @return 大小
	 */
	public static int getMaxSuperPosition(Goods goods) {
		if (goods == null || goods.goodsInfo == null) {
			return 1;
		}
		int maxSize = 99;
		if ("凝香幻彩#炫影霜星#风寂云清#枯月流魂#冰落残阳#雷极弧光#蝎后血精#魔猪血精#黑熊血精#鬼猿血精".contains(goods.goodsInfo.str)) {
			maxSize = 999;
		}
		StringBuilder app = new StringBuilder();
		app.append("血玲珑#法玲珑#通天令#番天印#混元金斗#九龙神火#定海珠#八角晶牌#魂器#骑宠灵魄#彩凤之魂#乾坤罩秘笈"
				+ "魔引 狂暴 怒击 破天 反击 降魔斩 修罗术 云体 仙风 尽忠 惊雷 青木 寒冰 烈炎 碎石 超级魔引 超级狂暴 超级怒击 超级破天 超级反击 超级降魔斩 超级修罗术 超级云体 超级仙风 超级尽忠 超级惊雷 超级青木 超级寒冰 超级烈炎 超级碎石" + "#四周年头像框#四周年聊天底框#星星头像框"
				+ "#星星聊天底框#星月空间头像框#名人头像框#名人聊天底框#百合#玫瑰#情缘盒#巧克力#结婚纪念册#龙凤呈祥#太阴之气#御天梭#墨舞青云#魔炎飞甲#裂海龙鲸");
		if (app.toString().contains(goods.goodsInfo.str)
				|| (goods.goodsInfo.str.startsWith("超级") && goods.goodsInfo.str.endsWith("卡"))) {
			maxSize = 1;
		}
		// 1-10的物品不允许叠加
		int amount = goods.goodsInfo.amount;
		if (amount > 0 && amount < 21) {
			maxSize = 1;
		} else if (goods.goodsInfo.degree_32 == 1) {
			// 如果是未鉴定.允许叠加99个
			maxSize = 99;
		} else if (goods.goodsInfo.str.indexOf("超级黑水晶") != -1 && goods.goodsInfo.attrib > 0) {
			maxSize = 1;
		}
		return maxSize;
	}

	/**
	 * 获取角色最大相性点.
	 * 
	 * @param level 等级
	 * @return
	 */
	public static int getMaxStamina(int level) {
		int maxStamina = 0;
		if (level > 60) {
			level -= 60;
			maxStamina = 30;
			maxStamina += level;
		} else {
			maxStamina = level / 2;
		}
		return maxStamina - 1;
	}

	/**
	 * 角色发起强制PK
	 * 
	 * @param fightContainer
	 */
	public static void forcePk(FightContainer fightContainer) {
		List<FightTeam> teamList = fightContainer.teamList;
		// 队伍信息
		Map<String, FightTeam> fightTeamInfo = getFightTeamInfo(teamList);
		// PK胜利的队伍
		FightTeam victoryTeam = fightTeamInfo.get("victoryTeam");
		// PK失败的队伍
		FightTeam deadTeam = fightTeamInfo.get("deadTeam");
		// 强制PK配置信息
		ForcePkConfig config = GameConfig.forcePkConfig;
		// 胜利队长
		Chara victoryLeader = null;
		// 胜利的队伍信息
		if (victoryTeam != null) {
			if (victoryTeam.fightObjectList != null && !victoryTeam.fightObjectList.isEmpty()) {
				List<FightObject> teamLeader = victoryTeam.fightObjectList;
				if(teamLeader != null && !teamLeader.isEmpty()) {
					victoryLeader = GameObjectCharMng.getGameObjectChar(teamLeader.get(0).getId()).chara;
					for (FightObject fig : teamLeader) {
						GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fig.getId());
						if (gameObjectChar != null) {
							if (gameObjectChar.action.equals("activeForcePk")) {
								// 名字变红色
								gameObjectChar.chara.isNameRed = 1;
								if(deadTeam != null && deadTeam.fightObjectList.isEmpty()) {
									//杀多少个人就增加多少个小时
									gameObjectChar.chara.forcePk += deadTeam.fightObjectList.size();
								}else {
									gameObjectChar.chara.forcePk += 1;
								}
								//坐牢时间增加
								gameObjectChar.chara.crimeTime+=config.getZuolaoTime()*60;
								Vo_61553_0 vo_61553_0 = gameObjectChar.chara.taskMap.get("孽债血海");
								String des = "你当前拥有#R%d#n点PK值，受到大家排斥，在各大药店，杂货店以及便捷购买入口购买物品时，价格会变高。参与有死亡惩罚的战斗死亡一次可以减少1点PK值。";
								if (vo_61553_0 != null) {
									vo_61553_0.task_desc = String.format(des, gameObjectChar.chara.forcePk);
									vo_61553_0.task_prompt = "当前拥有#R" + gameObjectChar.chara.forcePk
											+ "#n点PK值，可前往#P无念僧#P消除";
									gameObjectChar.chara.taskMap.get("孽债血海");
									// 更新任务
									GameUtilRenWu.createTask(vo_61553_0, gameObjectChar.chara);
								} else {
									// 创建任务
									vo_61553_0 = new Vo_61553_0();
									vo_61553_0.count = 1;
									vo_61553_0.task_type = "孽债血海";
									vo_61553_0.task_desc = String.format(des, gameObjectChar.chara.forcePk);
									vo_61553_0.task_prompt = "当前拥有#R" + gameObjectChar.chara.forcePk
											+ "#n点PK值，可前往#P无念僧#P消除";
									vo_61553_0.refresh = 1;
									vo_61553_0.task_end_time = 1567909190;
									vo_61553_0.attrib = 0;
									vo_61553_0.reward = "";
									vo_61553_0.show_name = "孽债血海";
									vo_61553_0.task_extra_para = "";
									vo_61553_0.task_state = "";
									GameUtilRenWu.createTask(vo_61553_0, gameObjectChar.chara);
								}
								if (deadTeam != null && deadTeam.fightObjectList != null
										&& deadTeam.fightObjectList.size() > 0) {
									int id = deadTeam.fightObjectList.get(0).id;
									GameObjectChar deadLeader = GameObjectCharMng.getGameObjectChar(id);
									if (deadLeader != null) {
										// 发出击杀别人的提示
										sendTips("你使用强制PK,成功的击杀了#Y" + deadLeader.chara.name, gameObjectChar.chara.id);
									}
								}
							}
							gameObjectChar.action = "";
						}
					}
				}
			}
		}
		// 失败的队伍信息
		if (deadTeam != null) {
			if (deadTeam.fightObjectList != null && !deadTeam.fightObjectList.isEmpty()) {
				List<FightObject> teamLeader = deadTeam.fightObjectList;
				if(teamLeader != null && !teamLeader.isEmpty()) {
					List<Chara> teams = new ArrayList<>();
					for(FightObject fightObject:teamLeader) {
						GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(fightObject.getId());
						if (gameObjectChar != null) {
							teams.add(gameObjectChar.chara);
						}
					}
					parseForcePkInfo(teams, 0, victoryLeader);
				}
			}
		}
	}

	/**
	 * 根据原始队伍信息,获取到胜利队伍和死亡队伍
	 * 
	 * @param origin 原始队伍信息
	 * @return
	 */
	public static Map<String, FightTeam> getFightTeamInfo(List<FightTeam> origin) {
		// 队伍信息
		Map<String, FightTeam> fightTeamMap = new HashMap<>();
		// PK胜利的队伍
		FightTeam victoryTeam = null;
		// PK失败的队伍
		FightTeam deadTeam = null;
		boolean isDead = false;
		// 两只队伍进行强制切磋
		if (origin.size() > 1) {
			FightTeam no1Team = origin.get(0);
			for (FightObject fightObject : no1Team.fightObjectList) {
				if (fightObject.isDead()) {
					isDead = true;
				} else {
					isDead = false;
				}
			}
			if (isDead) {
				deadTeam = no1Team;
			} else {
				victoryTeam = no1Team;
			}
			FightTeam no2Team = origin.get(1);
			for (FightObject fightObject : no2Team.fightObjectList) {
				if (fightObject.isDead()) {
					isDead = true;
				} else {
					isDead = false;
				}
			}
			if (isDead) {
				deadTeam = no2Team;
			} else {
				victoryTeam = no2Team;
			}
			fightTeamMap.put("deadTeam", deadTeam);
			fightTeamMap.put("victoryTeam", victoryTeam);
		}
		return fightTeamMap;
	}

	/**
	 * 解析强制PK信息
	 * 
	 * @param charas          失败队伍信息
	 * @param maxForcePkValue 最大PK值
	 * @param victoryLeader   胜利队伍的队长
	 */
	private static void parseForcePkInfo(List<Chara> charas, int maxForcePkValue, Chara victoryLeader) {
		ForcePkConfig config = GameConfig.forcePkConfig;
		String lastDeadMapName = victoryLeader.mapName;
		StringBuilder systemInfo = null;
		for (Chara chara : charas) {
			if (!StringUtils.isNullOrEmpty(config.getReviveMap())) {
				// 分割
				String[] split = config.getReviveMap().split(",");
				if (split.length > 2) {
					if (Utils.isNumber(split[1]) && Utils.isNumber(split[2])) {
						// 指定坐标
						chara.y = Integer.valueOf(split[1]);
						chara.x = Integer.valueOf(split[2]);
						GameLine.getGameMapname(chara.line, split[0])
								.join(GameObjectCharMng.getGameObjectChar(chara.id));
					}

				} else {
					// 复活地
					com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName(config.getReviveMap());
					if (map != null) {
						chara.y = map.getY();
						chara.x = map.getX();
						GameLine.getGameMapname(chara.line, map.getName())
								.join(GameObjectCharMng.getGameObjectChar(chara.id));
					}
				}
			}
			StringBuilder msg = new StringBuilder();
			systemInfo = new StringBuilder();
			systemInfo.append("#Y").append(chara.name).append("#n在#R").append(lastDeadMapName).append("#n被")
					.append("#Y").append(victoryLeader.name).append("#n击杀，丢失了");
			msg.append("你死亡了因此损失了");
			// 随机掉1000-1万元宝
			int n = new Random().nextInt(9000) + 1000;
			// 如果要扣的金额大于剩余金额,那就让扣除金额等于剩余金额
			if (n > chara.goldCoin) {
				n = chara.goldCoin;
			}
			chara.goldCoin -= n;
			msg.append("#R").append(n).append("#n金元宝");
			systemInfo.append("#R").append(n).append("#n金元宝");
			// 解析惩罚信息
			if (!StringUtils.isNullOrEmpty(config.getDieSubInfo())) {
				try {
					String[] split = config.getDieSubInfo().split(";");
					for (String in : split) {
						String[] split2 = in.split(":");
						if (split2.length > 1) {
							String type = split2[0];
							String value = split2[1];
							if ("道具".equals(type)) {
								if (!StringUtils.isNullOrEmpty(value)) {
									msg.append(",#R").append(value).append("#n道具");
									systemInfo.append(",#R").append(value).append("#n道具");
									GameUtil.removemunber(chara, value, 1);
								}
							} else if ("积分".equals(type)) {
								if (!StringUtils.isNullOrEmpty(value)) {
									int val = Integer.valueOf(value);
									if (val > 0) {
										msg.append(",#R").append(value).append("#n积分");
										systemInfo.append(",#R").append(value).append("#n积分");
										chara.chargeScore -= Integer.valueOf(value);
										if (chara.chargeScore < 0) {
											chara.chargeScore = 0;
										}
									}
								}
							} else if ("经验".equals(type)) {
								if (!StringUtils.isNullOrEmpty(value)) {
									int val = Integer.valueOf(value);
									if (val > 0) {
										msg.append(",#R").append(value).append("#n经验");
										systemInfo.append(",#R").append(value).append("#n经验");
										chara.exp -= val;
									}
								}
							} else if ("潜能".equals(type)) {
								if (!StringUtils.isNullOrEmpty(value)) {
									int val = Integer.valueOf(value);
									if (val > 0) {
										msg.append(",#R").append(value).append("#n潜能");
										systemInfo.append(",#R").append(value).append("#n潜能");
										chara.pot -= Integer.valueOf(value);
									}
								}
							} else if ("道行".equals(type)) {
								if (!StringUtils.isNullOrEmpty(value)) {
									int val = Integer.valueOf(value);
									if (val > 0) {
										msg.append(",#R").append(GameUtil.fmtDh(val)).append("#n道行");
										systemInfo.append(",#R").append(GameUtil.fmtDh(Integer.valueOf(value) * 1440))
												.append("#n道行");
										chara.tao -= val;
										if (chara.tao < 0) {
											chara.tao = 0;
										}
									}
								}
							}
						}
					}
					GameCommonUtil.sendTips(msg.toString(), chara.id);
				} catch (Exception e) {
					log.error("强制PK惩罚信息,解析失败");
					log.error("{}", e);
				}
			}
			// PK值减一
			if (chara.forcePk > 0) {
				chara.forcePk -= 1;
			}
			// 如果==0
			Vo_61553_0 task = chara.taskMap.get("孽债血海");
			if (chara.forcePk == 0) {
				// 删除任务
				if (task != null) {
					GameUtilRenWu.removeTask("孽债血海", chara);
				}
			} else {
				// 更新任务
				if (task != null) {
					String des = "你当前拥有#R%d#n点PK值，受到大家排斥，在各大药店，杂货店以及便捷购买入口购买物品时，价格会变高。参与有死亡惩罚的战斗死亡一次可以减少1点PK值。";
					task.task_desc = String.format(des, chara.forcePk);
					task.task_prompt = "当前拥有#R" + chara.forcePk + "#n点PK值，可前往#P无念僧#P消除";
					// 更新任务
					GameUtilRenWu.createTask(task, chara);
				}
			}
			Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
			vo_9129_2.notify = ClientButtonIdConst.NOTIFY_OPEN_DLG;
			vo_9129_2.para = "DeadRemindDlg";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2, chara.id);
			GameObjectCharMng.getGameObjectChar(chara.id).action = "";
			GameUtil.sendUpdate(chara);
			GameUtilRenWu.refshPointTask(chara);

			// 发送消息到系统频道
			GameUtil.sendSystemMessage(7, systemInfo.toString() + "请大家前往缉拿归案。");
			// 发送消息到这个人的帮派，当然了前提的要有帮派
			if (!StringUtils.isNullOrEmpty(chara.getPartyName())) {
				Example examplePartyMember = new Example(PartyMember.class);
				examplePartyMember.createCriteria().andEqualTo("partyId",
						GameCore.partyMap.get(chara.getPartyName()).getPartyId());
				List<PartyMember> partyMemerbs = GameData.that.partyMemberService.selectByExample(examplePartyMember);
				Vo_MESSAGE npcMessage = npcMessage("帮派总管", "本帮成员" + systemInfo.toString() + "，请帮中兄弟前往救援。", 0, 6036, 5);
				for (PartyMember p : partyMemerbs) {
					GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(p.getCharaGid());
					if (gameObjectCharByUUid != null) {
						gameObjectCharByUUid.sendOne(new MSG_MESSAGE(), npcMessage);
					}
				}
			}
		}

	}

	/**
	 * 发消息到帮派
	 * 
	 * @param chara
	 * @param msg
	 */
	public static void sendPartyMsg(Chara chara, Vo_16383_0 vo_16383_0) {
		if (!StringUtils.isNullOrEmpty(chara.getPartyName())) {
			// 查询该帮派是否被封停
			Party party = GameCore.partyMap.get(chara.getPartyName());
			if (party != null && party.getState() != 0) {
				GameCommonUtil.dialogOk("对不起该帮派因违反规定，被封停。");
				return;
			}
			// 获取玩家帮派所有在线成员
			Example example = new Example(PartyMember.class);
			example.createCriteria().andEqualTo("partyId", party.getPartyId());
			List<PartyMember> partyMemerbs = GameData.that.partyMemberService.selectByExample(example);
			for (PartyMember p : partyMemerbs) {
				GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(p.getCharaGid());
				if (gameObjectCharByUUid != null) {
					gameObjectCharByUUid.sendOne(new M16383_0(), vo_16383_0);
				}
			}
		}
	}

	/**
	 * 帮派总管Npc消息
	 * 
	 * @param channel
	 * @param msg
	 * @return
	 */
	public static Vo_MESSAGE getPartyNpc(Integer channel, String msg) {
		Vo_MESSAGE v = new Vo_MESSAGE();
		v.channel = channel;
		v.id = 0;
		v.name = "帮派总管";
		v.msg = msg;
		v.time = (int) (System.currentTimeMillis() / 1000L);
		v.privilege = 0;
		v.server_name = GameConfig.lineName;
		v.show_extra = 1;
		v.show_time = 1;
		v.icon = 6036;
		return v;
	}

	/**
	 * 计算基础道行增加
	 * 
	 * @param chara
	 * @param num
	 * @return
	 */
	public static int getBaseAddTao(Chara chara, int num) {
		int base_dh = (int) (0.29 * chara.level * chara.level * chara.level);
		int tao = (int) (39 * chara.level * (1.0 + 0.2 * num)
				/ ((chara.tao > base_dh) ? (chara.tao / base_dh) : 1));

		return tao;
	}

	/**
	 * 获取商品数量
	 * 
	 * @param name
	 * @return
	 */
	public static int getGoodsNum(Chara chara, String name) {
		int num = 0;
		for (Goods goods : chara.backpack) {
			if (name.equals(goods.goodsInfo.str)) {
				num += goods.goodsInfo.owner_id;
			}
		}
		return num;
	}

	/**
	 * 打入天牢
	 * 
	 * @param chara
	 */
	public static void beInJail(Chara chara) {

	}

	/**
	 * 获取金钱格式
	 * 
	 * @param money
	 * @return
	 */
	public static String getMoneyDes(int money) {
		String moneyDes = "";
		DecimalFormat df = new DecimalFormat("#,###");
		String format = df.format(money);
		if (money >= 100000 && money <= 999999) {
			moneyDes = "#G" + format + "#n";
		} else if (money >= 1000000 && money <= 9999999) {
			moneyDes = "#O" + format + "#n";
		} else if (money >= 10000000 && money <= 99999999) {
			moneyDes = "#Y" + format + "#n";
		} else if (money >= 100000000 && money <= 999999999) {
			moneyDes = "#R" + format + "#n";
		} else {
			moneyDes = "#W" + format + "#n";
		}
		return moneyDes;
	}

	/**
	 * 学习帮派技能帮贡消耗和金钱消耗
	 * 
	 * @param skillLevel 技能等级
	 * @return
	 */
	public static int[] getPetPartySkillCost(int skillLevel) {
		int partyContrib = 0;
		int cash = 0;
		if (skillLevel >= 0 && skillLevel <= 30) {
			partyContrib = 30;
			cash = 350000;
		} else if (skillLevel >= 31 && skillLevel <= 50) {
			partyContrib = 50;
			cash = 360000;
		} else if (skillLevel >= 51 && skillLevel <= 70) {
			partyContrib = 70;
			cash = 400000;
		} else if (skillLevel >= 71 && skillLevel <= 90) {
			partyContrib = 90;
			cash = 420000;
		} else if (skillLevel >= 91 && skillLevel <= 110) {
			partyContrib = 110;
			cash = 430000;
		} else if (skillLevel >= 111 && skillLevel <= 130) {
			partyContrib = 130;
			cash = 450000;
		} else if (skillLevel >= 131 && skillLevel <= 150) {
			partyContrib = 150;
			cash = 460000;
		} else if (skillLevel >= 151 && skillLevel <= 170) {
			partyContrib = 170;
			cash = 470000;
		} else if (skillLevel >= 171 && skillLevel <= 190) {
			partyContrib = 190;
			cash = 480000;
		} else if (skillLevel >= 191 && skillLevel <= 206) {
			partyContrib = 200;
			cash = 490000;
		} else if (skillLevel > 206) {
			partyContrib = 206;
			cash = 500000;
		}
		return new int[] { cash * skillLevel, partyContrib * skillLevel };

	}

	/**
	 * 计算称谓加成信息
	 * 
	 * @param chengwei
	 */
	public static void computeDeltaChengwei(Chara chara, Chengwei chengwei, boolean isSub) {
		if (chengwei != null && !StringUtils.isNullOrEmpty(chengwei.getAttr())) {
			// 属性
			String attrStr = chengwei.getAttr();
			// 解析属性
			JSONArray parseArray = JSONObject.parseArray(attrStr);
			for (int i = 0; i < parseArray.size(); i++) {
				JSONObject attri = parseArray.getJSONObject(i);
				String key = attri.getString("field");
				int value = attri.getIntValue("value");
				if (isSub) {
					value = value - (value + value);
				}
				if ("所有相性".equals(key)) {
					chara.zbAttribute.wood += value;
					chara.zbAttribute.water += value;
					chara.zbAttribute.fire += value;
					chara.zbAttribute.earth += value;
					chara.zbAttribute.resist_metal += value;
				} else if ("金相性".equals(key)) {
					chara.zbAttribute.wood += value;
				} else if ("木相性".equals(key)) {
					chara.zbAttribute.water += value;
				} else if ("水相性".equals(key)) {
					chara.zbAttribute.fire += value;
				} else if ("火相性".equals(key)) {
					chara.zbAttribute.earth += value;
				} else if ("土相性".equals(key)) {
					chara.zbAttribute.resist_metal += value;
				} else if ("所有属性".equals(key)) {
					chara.zbAttribute.life += value;
					chara.zbAttribute.mag_power += value;
					chara.zbAttribute.phy_power += value;
					chara.zbAttribute.speed += value;
				} else if ("体质".equals(key)) {
					chara.zbAttribute.life += value;
				} else if ("灵力".equals(key)) {
					chara.zbAttribute.mag_power += value;
				} else if ("力量".equals(key)) {
					chara.zbAttribute.phy_power += value;
				} else if ("敏捷".equals(key)) {
					chara.zbAttribute.speed += value;
				}
			}
		}
	}

	/**
	 * 指定或随机首饰属性
	 * 
	 * @param chara 玩家
	 * @param name  首饰名字
	 */
	public static String randomShouShiAttri(Chara chara, String... name) {
		return randomShouShiAttriP(chara, false, name);
	}

	/**
	 * 指定或随机首饰满属性
	 * 
	 * @param chara 玩家
	 * @param name  首饰名字
	 */
	public static String randomShouShiAllAttri(Chara chara, String... name) {
		return randomShouShiAttriP(chara, true, name);
	}

	/**
	 * 指定或随机首饰满属性
	 * 
	 * @param chara 玩家
	 * @param isAll 是否满属性
	 * @param name  首饰名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static String randomShouShiAttriP(Chara chara, boolean isAll, String... name) {
		if (name == null || name.length == 0) {
			// 根据当前人物等级随机取出一个首饰
			name = new String[] { randomGetShouShiName(chara.level) };
		}
		Random r = new Random();
		String shoushiname = name[r.nextInt(name.length)];
		ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		if (zhuangbeiInfo2 == null) {
			GameUtil.sendMeTips("未找到该物品！");
			return "";
		}
		// 小于等于70级，没有蓝属性S
		if (zhuangbeiInfo2.getAttrib() <= 70) {
			GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1);
		} else {
			// 80级、120级定制首饰，80级直接给所有相性5
			List<Hashtable<String, Integer>> hashtables2 = null;
			GoodsLanSe gooodsLanSe2 = new GoodsLanSe();
			// 90级到120级逐次加一条蓝属性
			java.util.Map<Object, Object> goodsLanSe = null;
			for (int i = 80; i <= 170; i += 10) {
				@SuppressWarnings("rawtypes")
				Hashtable hashMap = new Hashtable();
				goodsLanSe = UtilObjMapshuxing.GoodsLanSe(gooodsLanSe2);
				for (java.util.Map.Entry<Object, Object> entry : goodsLanSe.entrySet()) {
					if (!entry.getKey().equals("groupNo")) {
						if (entry.getKey().equals("groupType")) {
							continue;
						}
						if (entry.getValue().toString().equals("0")) {
							continue;
						}
						hashMap.put(entry.getKey(), entry.getValue());
					}
				}
				if (isAll) {
					hashtables2 = ForgingEquipmentUtils.appraisalMaxALLEquipment(zhuangbeiInfo2.getAmount(), i,
							hashMap);
				} else {
					hashtables2 = ForgingEquipmentUtils.shoushiRandomEquipment(zhuangbeiInfo2.getAmount(), i, hashMap);
				}
				if (hashtables2.size() >= 0) {
					for (Hashtable<String, Integer> maps2 : hashtables2) {
						if (maps2.get("groupNo") == 2) {
							maps2.put("groupType", 2);
							gooodsLanSe2 = (GoodsLanSe) com.alibaba.fastjson.JSONObject
									.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps2), GoodsLanSe.class);
							if (zhuangbeiInfo2.getAttrib() == i) {
								GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, gooodsLanSe2);
								break;
							}
						}
					}
				}
			}
		}
		return shoushiname;
	}

	/**
	 * 获取某个商品可用剩余的数量
	 * 
	 * @param goods     商品集合
	 * @param goodsName 名字
	 * @return
	 */
	public static int getGoodsAvaliableNum(List<Goods> goods, String goodsName) {
		int number = 0;
		for (Goods g : goods) {
			if (goodsName.equals(g.goodsInfo.str)) {
				number += g.goodsInfo.owner_id;
			}
		}
		return number;
	}

	/**
	 * 添加装备到背包
	 * 
	 * @param chara     玩家
	 * @param zhuangb   装备
	 * @param degree_32 未鉴定
	 * @param count     数量
	 */
	public static void addEquipToBackpack(GameObjectChar gameObjectChar, ZhuangbeiInfo zhuangb, int degree_32,
			int count) {
		Goods goods = new Goods();
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = degree_32;
		addGoodsToBackpack(goods, gameObjectChar, count);
		gameObjectChar.sendOne(new M65525_0(), gameObjectChar.chara.backpack);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得了#R" + goods.goodsInfo.str + "";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
	}

	/**
	 * 清除所有缓存
	 */
	public static void refreshCacheAll() {
		GameData.that.baseMapService.refreshCache();
		GameData.that.baseExperienceService.refreshCache();
		GameData.that.baseExperienceTreasureService.refreshCache();
		GameData.that.baseNpcService.refreshCache();
		GameData.that.baseNpcDialogueService.refreshCache();
		GameData.that.baseNpcDialogueFrameService.refreshCache();
		GameData.that.baseNpcPointService.refreshCache();
		GameData.that.basePetService.refreshCache();
		GameData.that.baseFightObjectService.refreshCache();
		GameData.that.customPetSkillService.refreshCache();
		GameData.that.baseChoujiangService.refreshCache();
		GameData.that.chengweiService.refreshCache();
		GameData.that.baseShuxingduiyingService.refreshCache();
		GameData.that.victoryDieRewardService.refreshCache();
		GameData.that.baseZhuangbeiInfoService.refreshCache();
		GameData.that.baseStoreGoodsService.refreshCache();
		GameData.that.baseStoreInfoService.refreshCache();
		GameData.that.baseNoticeService.refreshCache();
		GameData.that.baseRenwuService.refreshCache();
		GameData.that.baseRenwuMonsterService.refreshCache();
		GameData.that.configInfoService.refreshCache();
		GameData.that.baseGroceriesShopService.refreshCache();
		GameData.that.daySignPrizeService.refreshCache();
		GameData.that.luckDrawItemService.refreshCache();

	}

	/**
	 * 添加宠物到数据库
	 * 
	 * @param chara
	 * @param petbeibao
	 */
	public static void addCharaPet(CharaPet charaPet, Chara chara, Petbeibao petbeibao) {
		charaPet.setCid(chara.id);
		charaPet.setOwnerName(chara.name);
		charaPet.setPet(JSONObject.toJSONString(petbeibao));
		charaPet.setPetName(petbeibao.petShuXing.get(0).str);
		charaPet.setAddTime(new Date());
		charaPet.setUuid(chara.uuid);
		GameData.that.charaPetService.insertSelective(charaPet);
	}

	/**
	 * 解析自动寻路信息
	 * 
	 * @param dest
	 * @return
	 */
	public static Map<String, Object> getAutoWalkDest(String dest) {
		Pattern compile = Pattern.compile("(#[PZ].+#[PZ])");
		Matcher m = compile.matcher(dest);
		Map<String, Object> dataMap = new HashMap<>();
		if (m.find()) {
			String group = m.group();
			String[] split = group.split("@");
			if (split.length == 1) {
				// 查找npc #P逍遥仙|$0#P
				if (group.startsWith("#P") && group.endsWith("#P")) {
					group = group.replace("#P", "");
				} else if (group.startsWith("#Z") && group.endsWith("#Z")) {
					group = group.replace("#Z", "");
				}
				group = group.replace("#P", "");
				String[] infoArr = group.split("\\|");
				String name = infoArr[0];
				dataMap.put("name", name);
				if (infoArr != null) {
					if (infoArr.length >= 2) {
						String mapInfo = infoArr[1];
						String mapName = mapInfo;
						if (mapInfo.indexOf("(") != -1 && mapInfo.lastIndexOf(")") != -1) {
							String str = mapInfo.substring(mapInfo.indexOf("(") + 1, mapInfo.indexOf(")"));
							dataMap.put("xy", str);
							mapName = mapInfo.substring(0, mapInfo.indexOf("("));
						}
						dataMap.put("mapName", mapName);
					}
					if (infoArr.length >= 3) {
						String menuItem = infoArr[2];
						if (menuItem.startsWith("M=")) {
							// M点击菜单开头
							menuItem = menuItem.replace("M=", "");
							dataMap.put("menuItem", menuItem);
						}
					}
					// 获取到最后一个信息
					String lastInfo = infoArr[infoArr.length - 1];
					dataMap.put("type", lastInfo);
				}
			}
		}
		return dataMap;
	}

	/**
	 * 获取极品土套武器
	 * 
	 * @param chara 玩家
	 * @param level 等级
	 * @param color 改造
	 * @return
	 */
	public static Goods getJpEquipEarthWuQi(Chara chara, int level, int color) {
		String zhuangbname = GameUtil.getZbNameByLevel(chara, level, 1);
		ZhuangbeiInfo zhuangbei = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		Goods goods = new Goods();
		int pos2 = GameUtil.packPoint(chara);
		if (pos2 == -1) {
			return null;
		}
		goods.pos = pos2;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsBasics = new GoodsBasics();
		goods.goodsCreate(zhuangbei);
		goods.goodsInfo.quality = "绿色";
		// 绿色属性
		goods.goodsInfo.suit_enabled = 5;
		// 改造信息
		goods.goodsInfo.color = color;
		List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment(zhuangbname,
				zhuangbei.getAmount(), zhuangbei.getAttrib(), color);
		for (Hashtable<String, Integer> maps2 : hashtables2) {
			if (maps2.get("groupNo") == 10) {
				maps2.put("groupType", 2);
				GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZao.class);
				goods.goodsGaiZao = goodsGaiZao;
			}
		}
		// 蓝色
		int skill_low_cost = ForgingEquipmentUtils.getMaxValueByChineseName("伤害_最低伤害", goods.goodsInfo.attrib, false,
				false);
		goods.goodsLanSe = new GoodsLanSe();
		goods.goodsLanSe.all_resist_polar = 5;
		goods.goodsLanSe.skill_low_cost = skill_low_cost;
		goods.goodsLanSe.resist_metal = 5;
		// 粉色属性
		goods.goodsFenSe = new GoodsFenSe();
		goods.goodsFenSe.all_resist_polar = 5;

		// 黄色
		goods.goodsHuangSe = new GoodsHuangSe();
		goods.goodsHuangSe.skill_low_cost = skill_low_cost;

		goods.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
		if(goods.goodsInfo.color>=4) {
			goods.goodsGaiZaoGongMing.phy_power = ForgingEquipmentUtils.getMaxValueByChineseName("力量",
					goods.goodsInfo.attrib, goods.goodsInfo.amount == 3, true) * color / 4;
		}

		// 绿色属性
		GameUtil.getRandomGreenAttr(goods, true);
		goods.goodsLvSe = new GoodsLvSe();
		goods.goodsLvSe.ignore_mag_dodge = ForgingEquipmentUtils.getMaxValueByChineseName("强物理伤害",
				goods.goodsInfo.attrib, false, true);
		return goods;
	}

	/**
	 * 获取极品衣服、鞋子、帽子
	 * 
	 * @param chara 玩家
	 * @param level 等级
	 * @param color 改造
	 * @return
	 */
	public static Goods getJpEquipEarthOther(Chara chara, int level, int color, int leixing) {
		String zhuangbname = GameUtil.getZbNameByLevel(chara, level, leixing);
		ZhuangbeiInfo zhuangbei = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		Goods goods = new Goods();
		int pos2 = GameUtil.packPoint(chara);
		if (pos2 == -1) {
			return null;
		}
		goods.pos = pos2;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsBasics = new GoodsBasics();
		goods.goodsCreate(zhuangbei);
		goods.goodsInfo.quality = "绿色";
		// 绿色属性
		goods.goodsInfo.suit_enabled = 5;
		// 改造信息
		goods.goodsInfo.color = color;
		List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment(zhuangbname,
				zhuangbei.getAmount(), zhuangbei.getAttrib(), color);
		for (Hashtable<String, Integer> maps2 : hashtables2) {
			if (maps2.get("groupNo") == 10) {
				maps2.put("groupType", 2);
				GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZao.class);
				goods.goodsGaiZao = goodsGaiZao;
			}
		}
		// 蓝色
		goods.goodsLanSe = new GoodsLanSe();
		int all_polar = ForgingEquipmentUtils.getMaxValueByChineseName("所有属性", goods.goodsInfo.attrib, false, false);
		int parry = ForgingEquipmentUtils.getMaxValueByChineseName("速度", goods.goodsInfo.attrib, false, false);
		int phyPower = ForgingEquipmentUtils.getMaxValueByChineseName("力量", goods.goodsInfo.attrib, false, false);
		goods.goodsLanSe.all_polar = all_polar;
		goods.goodsLanSe.speed = ForgingEquipmentUtils.getMaxValueByChineseName("敏捷", goods.goodsInfo.attrib, false,
				false);
		if (leixing == 10) {
			// 鞋子
			goods.goodsLanSe.parry = parry;
		} else {
			goods.goodsLanSe.phy_power = phyPower;
		}
		// 粉色属性
		goods.goodsFenSe = new GoodsFenSe();
		goods.goodsFenSe.all_polar = all_polar;

		// 黄色
		goods.goodsHuangSe = new GoodsHuangSe();
		if (leixing == 10) {
			// 鞋子
			goods.goodsHuangSe.parry = parry;
		} else {
			goods.goodsHuangSe.phy_power = phyPower;
		}
		//改造共鸣
		goods.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
		if(goods.goodsInfo.color>=4) {
			goods.goodsGaiZaoGongMing.phy_power = phyPower * color / 4;
		}

		// 绿色属性
		GameUtil.getRandomGreenAttr(goods, true);
		return goods;
	}

	/**
	 * 获取极品金套武器
	 * 
	 * @param chara 玩家
	 * @param level 等级
	 * @param color 改造
	 * @return
	 */
	public static Goods getJpEquipMetalWuQi(Chara chara, int level, int color) {
		String zhuangbname = GameUtil.getZbNameByLevel(chara, level, 1);
		ZhuangbeiInfo zhuangbei = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		Goods goods = new Goods();
		int pos2 = GameUtil.packPoint(chara);
		if (pos2 == -1) {
			return null;
		}
		goods.pos = pos2;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsBasics = new GoodsBasics();
		goods.goodsCreate(zhuangbei);
		goods.goodsInfo.quality = "绿色";
		// 绿色属性
		goods.goodsInfo.suit_enabled = 1;
		// 改造信息
		goods.goodsInfo.color = color;
		List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment(zhuangbname,
				zhuangbei.getAmount(), zhuangbei.getAttrib(), color);
		for (Hashtable<String, Integer> maps2 : hashtables2) {
			if (maps2.get("groupNo") == 10) {
				maps2.put("groupType", 2);
				GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZao.class);
				goods.goodsGaiZao = goodsGaiZao;
			}
		}
		// 蓝色
		int skill_low_cost = ForgingEquipmentUtils.getMaxValueByChineseName("伤害_最低伤害", goods.goodsInfo.attrib, false,
				false);
		goods.goodsLanSe = new GoodsLanSe();
		goods.goodsLanSe.all_resist_polar = 5;
		goods.goodsLanSe.skill_low_cost = skill_low_cost;
		goods.goodsLanSe.wood = 5;
		// 粉色属性
		goods.goodsFenSe = new GoodsFenSe();
		goods.goodsFenSe.all_resist_polar = 5;

		// 黄色
		goods.goodsHuangSe = new GoodsHuangSe();
		goods.goodsHuangSe.skill_low_cost = skill_low_cost;

		// 绿色共鸣
		goods.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
		if(goods.goodsInfo.color>=4) {
			goods.goodsGaiZaoGongMing.mag_power = ForgingEquipmentUtils.getMaxValueByChineseName("灵力",
					goods.goodsInfo.attrib, goods.goodsInfo.amount == 3, true) * color / 4;
		}

		// 绿色属性
		GameUtil.getRandomGreenAttr(goods, true);
		goods.goodsLvSe = new GoodsLvSe();
		goods.goodsLvSe.enhanced_wood = ForgingEquipmentUtils.getMaxValueByChineseName("强金法伤害", goods.goodsInfo.attrib,
				false, true);
		return goods;
	}

	/**
	 * 获取极品衣服、鞋子、帽子
	 * 
	 * @param chara 玩家
	 * @param level 等级
	 * @param color 改造
	 * @return
	 */
	public static Goods getJpEquipMetalOther(Chara chara, int level, int color, int leixing) {
		String zhuangbname = GameUtil.getZbNameByLevel(chara, level, leixing);
		ZhuangbeiInfo zhuangbei = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		Goods goods = new Goods();
		int pos2 = GameUtil.packPoint(chara);
		if (pos2 == -1) {
			return null;
		}
		goods.pos = pos2;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsBasics = new GoodsBasics();
		goods.goodsCreate(zhuangbei);
		goods.goodsInfo.quality = "绿色";
		// 绿色属性
		goods.goodsInfo.suit_enabled = 1;
		// 改造信息
		goods.goodsInfo.color = color;
		List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment(zhuangbname,
				zhuangbei.getAmount(), zhuangbei.getAttrib(), color);
		for (Hashtable<String, Integer> maps2 : hashtables2) {
			if (maps2.get("groupNo") == 10) {
				maps2.put("groupType", 2);
				GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZao.class);
				goods.goodsGaiZao = goodsGaiZao;
			}
		}
		// 蓝色
		int all_polar = ForgingEquipmentUtils.getMaxValueByChineseName("所有属性", goods.goodsInfo.attrib, false, false);
		int speed = ForgingEquipmentUtils.getMaxValueByChineseName("敏捷", goods.goodsInfo.attrib, false, false);
		int parry = ForgingEquipmentUtils.getMaxValueByChineseName("速度", goods.goodsInfo.attrib, false, false);
		int mag_power = ForgingEquipmentUtils.getMaxValueByChineseName("灵力", goods.goodsInfo.attrib, false, false);
		goods.goodsLanSe = new GoodsLanSe();
		goods.goodsLanSe.all_polar = all_polar;
		goods.goodsLanSe.speed = speed;
		if (leixing == 10) {
			// 鞋子
			goods.goodsLanSe.parry = parry;
		} else {
			goods.goodsLanSe.mag_power = mag_power;
		}
		// 粉色属性
		goods.goodsFenSe = new GoodsFenSe();
		goods.goodsFenSe.all_polar = all_polar;

		// 黄色
		goods.goodsHuangSe = new GoodsHuangSe();
		if (leixing == 10) {
			// 鞋子
			goods.goodsHuangSe.parry = parry;
		} else {
			goods.goodsHuangSe.mag_power = mag_power;
		}
		// 改造共鸣
		goods.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
		if(goods.goodsInfo.color>=4) {
			goods.goodsGaiZaoGongMing.mag_power = mag_power * color / 4;
		}

		// 绿色属性
		GameUtil.getRandomGreenAttr(goods, true);
		return goods;
	}

	/**
	 * 获取随机满属性魂器
	 * 
	 * @param chara
	 * @param name  名字
	 */
	public static void getRandomAllAttrHunQi(GameObjectChar gameObjectChar, Goods goods) {
		GameCommonUtil.createHunQi(gameObjectChar.chara, goods, goods.goodsInfo.str);
		List<Hashtable<String, Object>> list = goods.goodsHunQi.zongShuxing;
		// 设置满属性
		for (Hashtable<String, Object> hashtable : list) {
			// 阳属性
			int yang_percent = new Random().nextInt(100) + 1;
			String key = HunqiUtils.horcrux_yang();
			JSONObject jsonObject = GameCore.hunqiYang.get(String.valueOf(goods.goodsInfo.attrib));
			int value = jsonObject.getIntValue(key);
			// 阴属性
			String keyYin = HunqiUtils.horcrux_yin();
			jsonObject = GameCore.hunqiYin.get(String.valueOf(goods.goodsInfo.attrib));
			int valueYin = jsonObject.getIntValue(keyYin);
			hashtable.put("chaos_value", new Random().nextInt(100) + 1);
			hashtable.put("yang_percent", yang_percent);
			hashtable.put("yang_prop", key);
			hashtable.put("yang_prop_value", value);
			hashtable.put("yin_prop", keyYin);
			hashtable.put("yin_prop_value", valueYin);
		}
		addGoodsToBackpack(goods, gameObjectChar);
	}

	/**
	 * 加载已存在的角色
	 * 
	 * @param characters 角色
	 * @param session    游戏会话
	 * @param char_name  角色名
	 */
	public static void loadExistedChar(Characters characters, GameObjectChar session, String char_name) {
		// 开始设置信息
		Chara chara = session.chara;
		if (chara == null) {
			session.init(characters);
			chara = session.chara;
		}
		// 更新上线时间
		Characters c = new Characters();
		c.setId(characters.getId());
		int lastLoginTime = (int) (System.currentTimeMillis() / 1000L);
		c.setLastLoginTime(lastLoginTime);
		c.setOnline(1);
		InetSocketAddress ipSocket = (InetSocketAddress) session.ctx.channel().remoteAddress();
		String clientIp = ipSocket.getAddress().getHostAddress();
		c.setLastLoginIp(clientIp);
		characters.setLastLoginTime(lastLoginTime);
		GameData.that.baseCharactersService.updateById(c);

		GameObjectChar.send(new M16383_0(),
				GameUtil.a16383(chara, "你上次登录的时间#Y" + DateUtil.format(new Date(chara.uptime), "yyyy-MM-dd H:mm:ss")
						+ "。#R注意：如有疑问，请尽快设定安全码验证或更改密码，以免造成不必要的损失", 0));
		chara.uptime = System.currentTimeMillis();
		// 设置服务器类型
		Vo_45277_0 vo_45277_0 = new Vo_45277_0();
		vo_45277_0.server_type = 0;
		GameObjectChar.send(new M45277_0(), vo_45277_0);
		// 设置时区
		Vo_41009_0 vo_41009_0 = new Vo_41009_0();
		vo_41009_0.server_time = (int) (System.currentTimeMillis() / 1000L);
		vo_41009_0.time_zone = 8;
		GameObjectChar.send(new M41009_0(), vo_41009_0);

		// 登录记录
		Vo_4099_0 vo_4099_0 = new Vo_4099_0();
		vo_4099_0.name = char_name;
		vo_4099_0.para = char_name + "是第 1次登录";
		vo_4099_0.gid = chara.uuid;
		GameObjectChar.send(new M4099_0(), vo_4099_0);

		// 角色信息头像外观
		GameObjectChar.send(new M65527_1(), Lists.newArrayList(chara.id, chara.def, chara.waiguan));
		// 开启新充值好礼
		GameObjectChar.send(new M45388_0(), null);
		// 卡套
		GameObjectChar.send(new MSG_CL_CARD_INFO(), new Vo_CL_CARD_INFO(chara.cardSize));

		if (chara.level >= 100) {
			Vo_41023_0 vo_41023_0 = new Vo_41023_0();
			vo_41023_0.taskName = "拜师任务";
			vo_41023_0.status = 1;
			GameObjectChar.send(new M41023_0(), vo_41023_0);
		}
		// 初始化宠物列表
		for (int j = 0; j < chara.pets.size(); ++j) {
			Petbeibao petbeibao = chara.pets.get(j);
			List<PetShuXing> petShuXing = petbeibao.petShuXing;
			List<Petbeibao> list = new ArrayList<>();
			list.add(petbeibao);
			GameObjectChar.send(new MSG_UPDATE_PETS(), list);
			GameObjectChar.send(new M12023_0(), petbeibao.tianshu);
			boolean isfagong = petShuXing.get(0).rank > petShuXing.get(0).pet_mag_shape;
			new Thread() {
				public void run() {
					GameUtil.dujineng(1, petShuXing.get(0).metal, petShuXing.get(0).skill, isfagong, petbeibao.id,
							session.chara, petbeibao);
				}
			}.start();
		}
		// 设置宠物参战ID
		Vo_4163_0 vo_4163_0 = new Vo_4163_0();
		vo_4163_0.id = chara.chongwuchanzhanId;
		vo_4163_0.b = 1;
		GameObjectChar.send(new M4163_0(), vo_4163_0);

		// 设置宠物掠阵
		if (chara.chongwuluezhenId != 0) {
			vo_4163_0 = new Vo_4163_0();
			vo_4163_0.id = chara.getChongwuluezhenId();
			vo_4163_0.b = 2;
			GameObjectChar.send(new M4163_0(), vo_4163_0);
		}

		// 设置坐骑ID
		Vo_8425_0 vo_8425_0 = new Vo_8425_0();
		vo_8425_0.id = chara.zuoqiId;
		GameObjectChar.send(new M8425_0(), vo_8425_0);
		// 设置角色VIP
		GameUtil.addVip(chara);
		GameObjectChar.send(new M41017_0(), null);

		// 设置推送开关
		Vo_53399_0 vo_53399_0 = new Vo_53399_0();
		vo_53399_0.value = "10011011111";
		GameObjectChar.send(new M53399_0(), vo_53399_0);

		// 出师等级
		Vo_53521_0 vo_53521_0 = new Vo_53521_0();
		vo_53521_0.chushiLevel = 90;
		GameObjectChar.send(new M53521_0(), vo_53521_0);

		// 通知客户端珍宝系统的配置信息
		Vo_33055_0 vo_33055_0 = new Vo_33055_0();
		vo_33055_0.is_enable = 1;
		vo_33055_0.enable_gold_stall_cash = 0;
		vo_33055_0.sell_cash_aft_days = 0;
		vo_33055_0.start_gold_stall_cash = 0;
		vo_33055_0.enable_appoint = 1;
		vo_33055_0.enable_autcion = 0;
		vo_33055_0.close_time = (int) (System.currentTimeMillis() / 1000L + 30000);
		GameObjectChar.send(new M33055_0(), vo_33055_0);

		// 奇宝斋
		Vo_32855_0 vo_32855_0 = new Vo_32855_0();
		vo_32855_0.enable = 1;
		vo_32855_0.url = "";
		GameObjectChar.send(new M32855_0(), vo_32855_0);

		Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 61001;
		vo_9129_0.para = "1";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);

		vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 50017;
		vo_9129_0.para = "0";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);

		vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 20002;
		/**
		 * 0:关 F:开
		 * 
		 * 1字节:暂未发现作用 2字节:暂未发现作用 3字节:暂未发现作用 4字节:角色属性-魂魄 5字节:装备炼化右侧tab 6字节:暂未发现作用
		 * 7字节:宠物右侧菜单 8字节:角色属性-技能 9字节:社区按钮 10字节:居所按钮 11:字节:周年庆和合作按钮 12字节:刷道和活动 13字节:巡逻
		 * 14字节:排行榜 15字节:守护 16字节:帮派和打造 17字节:暂未发现作用 18字节:暂未发现作用 19字节:暂未发现作用 20字节:暂未发现作用
		 * 21字节:许愿、权益、名人堂用 22字节:许愿、权益、名人堂用 23字节:魂器、行囊、地宫 24字节:好声音
		 */
		vo_9129_0.para = "FFFFFFFFFF0FFFFFFFFFF0F0";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);

		vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 10012;
		vo_9129_0.para = "";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);

		vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 20010;
		vo_9129_0.para = String.valueOf(chara.qumoxiang);
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);

		// 人物属性
		GameUtil.zhuangbeiValue(session);

		chara.zbAttribute.id = chara.id;
		GameObjectChar.send(new M65511_0(), chara.zbAttribute, chara.id);
		ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), vo_65527_0, chara.id);

		// 通知客户端角色数据加载完成.
		vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 39;
		vo_9129_0.para = "";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
		//如果计时没有的话就消除红名
		if (session.chara.crimeTime <= 0) {
			session.chara.isNameRed = 0;
		}
		// 如果当前角色在帮派总坛
		if (chara.mapid == 26000) {
			// 帮派地图
			String partyName = chara.getPartyName();
			GameLine.enterPartyMap(chara.mapid, partyName).join(GameObjectChar.getGameObjectChar());
		} else if (chara.mapName.equals("试道场")) {
			if (GameShiDao.statzhuangtai != 0) {
				// 获取队长在的地图
				GameMap gameMap = session.gameMap;
				if (gameMap == null) {
					GameUtil.sendMeTips("未找到地图信息！");
					return;
				}
				session.shiDaoFlag.set(true);
				gameMap.join(session);
				// 如果是报名期间
				if (GameShiDao.statzhuangtai == 1) {
					Vo_SHIDAO_TASK_INFO vo_49177_0 = GameCommonUtil.shidaoTaskInfoNo1();
					GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(), vo_49177_0);
				}
			} else {
				// 放到天墉城
				// 设置城里地图
				chara.x = 132;
				chara.y = 51;
				chara.mapid = 5000;
				chara.mapName = "天墉城";
				GameLine.getGameMap(1, 5000).join(session);
			}
		} else {
			session.gameMap.join(session);
		}
		// 这里在发送一次，因为客户端问题
		GameObjectChar.send(new M65527_0(), vo_65527_0, chara.id);
		// 异步加载其他信息
		new Thread() {
			public void run() {
				// 加载守护信息.
				GameObjectChar.send(new M12016_0(), session.chara.listshouhu, session.chara.id);
				// 设置守护
				for (int k = 0; k < session.chara.listshouhu.size(); ++k) {
					ShouHu shouHu = session.chara.listshouhu.get(k);
					GameUtil.dujineng(2, shouHu.listShouHuShuXing.get(0).metal, shouHu.listShouHuShuXing.get(0).skill,
							true, shouHu.id, session.chara, null);
				}
				// 福利
				GameUtil.MSG_OPEN_WELFARE(session.chara);
				// 加载背包数据
				session.sendOne(new M65525_0(), session.chara.backpack);
			}
		}.start();
		List<SaleGood> saleGoodList = GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
		Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
		GameObjectChar.send(new M49179_0(), vo_49179_0);

		Vo_12269_0 vo_12269_0 = new Vo_12269_0();
		vo_12269_0.id = chara.id;
		vo_12269_0.owner_id = 96780;
		GameObjectChar.send(new M12269_0(), vo_12269_0);

		Vo_61589_0 vo_61589_0 = GameUtil.a61589();
		GameObjectChar.send(new M61589_0(), vo_61589_0);

		Vo_40965_0 vo_40965_0 = new Vo_40965_0();
		vo_40965_0.guideId = 3;
		GameObjectChar.send(new M40965_0(), vo_40965_0);

		Vo_53925_0 vo_53925_0 = new Vo_53925_0();
		vo_53925_0.isOffical = 1;
		GameObjectChar.send(new M53925_0(), vo_53925_0);

		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);

		// 加载技能信息
		List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(chara);
		GameObjectChar.send(new M32747_0(), vo_32747_0List);

		Vo_32985_0 vo_32985_0 = new Vo_32985_0();
		vo_32985_0.user_is_multi = 0;
		vo_32985_0.user_round = chara.autofight_select;
		vo_32985_0.user_action = chara.autofight_skillaction;
		vo_32985_0.user_next_action = chara.autofight_skillaction;
		vo_32985_0.user_para = chara.autofight_skillno;
		vo_32985_0.user_next_para = chara.autofight_skillno;
		vo_32985_0.pet_is_multi = 0;
		vo_32985_0.pet_round = 0;
		vo_32985_0.pet_action = 0;
		vo_32985_0.pet_next_action = 0;
		vo_32985_0.pet_para = 0;
		vo_32985_0.pet_next_para = 0;
		GameObjectChar.send(new M32985_0(), vo_32985_0);
		GameUtil.genchongfei(session);
		// 初始化线路信息
		GameObjectChar.send(new MSG_REQUEST_SERVER_STATUS(), GameCore.that.getGameLineAll());

		// 设置称号
		//GameUtil.chenghaoxiaoxi(chara, null, null);
		//GameUtil.refreshChengHao(chara);
		// 如果角色处于组队状态
		if (session.gameTeam != null && session.gameTeam.duiwu != null && session.gameTeam.duiwu.size() > 0) {
			Vo_TITLE vo_61671_0 = new Vo_TITLE();
			vo_61671_0.id = session.gameTeam.duiwu.get(0).id;
			vo_61671_0.count = 2;
			vo_61671_0.list.add(2);
			vo_61671_0.list.add(3);
			GameObjectChar.send(new MSG_TITLE(), vo_61671_0);
			for (int l = 0; l < session.gameTeam.duiwu.size(); ++l) {
				if (session.gameTeam.duiwu.get(l).id == chara.id && session.gameTeam.duiwu.get(0).id != chara.id) {
					vo_61671_0 = new Vo_TITLE();
					vo_61671_0.id = session.chara.id;
					vo_61671_0.count = 2;
					vo_61671_0.list.add(2);
					vo_61671_0.list.add(5);
					GameObjectChar.send(new MSG_TITLE(), vo_61671_0);
				}
			}
			List<Chara> charas = GameObjectChar.getGameObjectChar().gameTeam.duiwu;
			GameUtil.a4119(charas);
			GameUtil.a4121(GameObjectChar.getGameObjectChar().gameTeam.zhanliduiyuan);
		}

		// 进入游戏信息
		Vo_4321_0 vo_4321_0 = new Vo_4321_0();
		vo_4321_0.dist = GameConfig.lineName;
		vo_4321_0.corss_server_dist = 0;
		vo_4321_0.flag = 0;
		vo_4321_0.lineNum = GameCore.getGameLine(chara.line).lineNum;
		vo_4321_0.name = GameCore.getGameLine(chara.line).lineName + GameCore.getGameLine(chara.line).lineNum + "线";
		vo_4321_0.time = (int) (System.currentTimeMillis() / 1000L);
		vo_4321_0.time_zone = 8;
		vo_4321_0.forbid_redhand = 1;
		GameObjectChar.send(new M4321_0(), vo_4321_0);
		// 重连判断战斗情况
		FightContainer fc = FightManager.getFightContainer(chara.id);
		if (fc != null) {
			chara.isFight = true;
			FightManager.reconnect(chara);
			setCharaTitleFlag(chara);
		}
		// 加载boss信息
		GameCommonUtil.showBoss(chara, chara.mapid);
		// 加载任务信息
		if (chara.taskMap != null) {
			for (Map.Entry<String, Vo_61553_0> task : chara.taskMap.entrySet()) {
				Vo_61553_0 v = task.getValue();
				GameObjectChar.send(new MSG_TASK_PROMPT(), v);
				if (v.task_type.equals("帮派任务") || v.task_type.equals("帮派日常挑战")) {
					GameObjectChar.send(new MSG_STOP_AUTO_WALK(), v.task_type);
				}
			}
		}
		// 加载普通任务,第二天会清空
		if (chara.commonTaskMap != null) {
			for (Map.Entry<String, Vo_61553_0> task : chara.commonTaskMap.entrySet()) {
				Vo_61553_0 v = task.getValue();
				GameObjectChar.send(new MSG_TASK_PROMPT(), v);
			}
		}
		new Thread() {
			public void run() {
				// 帮派图标
				GamePartyUtil.partyIcon(session.chara);
				// 设置登录状态
				GameCommonUtil.setOnline(session.chara, 1);
				// 实名认证
				Vo_FUZZY_IDENTITY identity = new Vo_FUZZY_IDENTITY();
				identity.setIsBindPhone(1);
				identity.setIsBindName(1);
				identity.setBindName("王老板");
				identity.setBindId("111111111111111111");
				identity.setBindPhone("130****6666");
				GameObjectChar.send(new MSG_FUZZY_IDENTITY(), identity, session.chara.id);
				// 门派转换信息2.57以上版本
				Vo_CHANGE_POLAR_DATA vo = new Vo_CHANGE_POLAR_DATA();
				vo.setHasChange(0);
				vo.setRawPolar(session.chara.oldPolar);
				vo.setNewPolar(session.chara.polar);
				vo.setHasReturn(0);
				vo.setTaskEndTime((int) (System.currentTimeMillis() / 1000L) + 999999999);
				session.sendOne(new MSG_CHANGE_POLAR_DATA(), vo);
				// 防成谜
				GameObjectChar.send(new MSG_UPDATE_ANTIADDICTION_STATUS(), new Vo_UPDATE_ANTIADDICTION_STATUS(),
						session.chara.id);

				// 获取好友分组列表
				List<FriendGroup> friendGroups = new ArrayList<>();
				// 默认分组
				friendGroups.add(new FriendGroup("我的好友", "1"));
				// 数据库查询出的分组
				List<FriendGroup> selectAll = GameData.that.friendGroupService.getFriendGroupsByGid(session.chara.uuid);
				friendGroups.addAll(selectAll);
				Vo_FRIEND_GROUP_LIST f = new Vo_FRIEND_GROUP_LIST();
				List<Vo_FRIEND_ADD_GROUP> groups = new ArrayList<>();
				for (FriendGroup fg : friendGroups) {
					groups.add(new Vo_FRIEND_ADD_GROUP(fg.getGroupId(), fg.getName()));
				}
				f.setFriendGroups(groups);
				GameObjectChar.send(new MSG_FRIEND_GROUP_LIST(), f, session.chara.id);
				GameObjectChar.send(new MSG_FRIEND_UPDATE_LISTS(),
						GameCommonUtil.createFriends(session.chara, friendGroups, true), session.chara.id);
				// 获取对方加自己的所有玩家，向他们发出通知
				GameCommonUtil.friendTips(session.chara, 1);
				// 查询别人发给我的邮件消息
				Example example = new Example(MailboxRefresh.class);
				example.createCriteria().andEqualTo("toGid", session.chara.uuid);
				List<MailboxRefresh> ms = GameData.that.mailboxRefreshService.selectByExample(example);
				List<Vo_MAILBOX_REFRESH> mails = new ArrayList<>();
				List<Integer> expiredIds = new ArrayList<>();
				for (MailboxRefresh m : ms) {
					if((System.currentTimeMillis()/1000L)<m.getExpiredTime()) {
						Vo_MAILBOX_REFRESH vo_40961_0 = new Vo_MAILBOX_REFRESH();
						vo_40961_0.count = m.getCount();
						vo_40961_0.id = m.getGid();
						vo_40961_0.type = m.getType();
						vo_40961_0.sender = m.getSender();
						vo_40961_0.title = m.getTitle();
						vo_40961_0.msg = m.getMsg();
						vo_40961_0.create_time = m.getCreateTime();
						vo_40961_0.expired_time = m.getExpiredTime();
						vo_40961_0.status = m.getStatus();
						vo_40961_0.attachment = m.getAttachment();
						mails.add(vo_40961_0);
					}else {
						expiredIds.add(m.getId());
					}
				}
				//删除过期邮件
				if(!expiredIds.isEmpty()) {
					Example expiredExample = new Example(MailboxRefresh.class);
					expiredExample.createCriteria().andIn("id", expiredIds);
					GameData.that.mailboxRefreshService.deleteByExample(expiredExample);
				}
				GameObjectChar.send(new MSG_MAILBOX_REFRESH(), mails, session.chara.id);
				// 发送系统设置消息
				GameObjectChar.send(new MSG_SET_SETTING(), session.chara.getSettings(), session.chara.id);
				// 开启地府系统
				GameObjectChar.send(new MSG_GHOSTDOM_INFO(), new Object[] { 1, 1, 1 }, session.chara.id);
				// 在这里检测变身卡是否过期
				if (session.chara.getTaskMap().get("千变万化") != null) {
					if (GameData.that.redisUtils.get(DefinedConst.CHANGE_CARD + ";" + session.chara.uuid) == null) {
						GameUtilRenWu.removeTask("千变万化", session.chara);
						session.chara.changeCardInfo = null;
						// 刷新
						GameObjectCharMng.getGameObjectChar(session.chara.id).gameMap.send(new MSG_UPDATE_APPEARANCE(),
								GameUtil.a61661(session.chara));
						GameUtil.sendUpdate(session.chara);
						GameCommonUtil.sendTips("你的变身卡效果已过期", session.chara.id);
					}
				}
				if (session.chara.getTaskMap().get("将功补过") != null) {
					if (System.currentTimeMillis() / 1000L > session.chara.getTaskMap().get("将功补过").task_end_time) {
						// 删除这个任务
						GameUtilRenWu.removeTask("将功补过", session.chara);
					}
				}
				if (session.chara.getTaskMap().get("坐牢") != null) {
					if (session.chara.crimeTime <= 0) {
						session.chara.crimeTime = 0;
						// 删除任务
						GameUtilRenWu.removeTask("坐牢", session.chara);
						session.chara.x = 26;
						session.chara.y = 30;
						GameLine.getGameMap(session.chara.line, "监狱").join(session);
					} else {
						// 更新为当前时间，因为下线后不计时
						session.chara.getTaskMap().get("坐牢").task_end_time = (int) (System.currentTimeMillis() / 1000L)
								+ (int) session.chara.crimeTime;
					}
				}
				//如果不是永久禁言的话
				if(session.chara.shut == 1) {
					if( GameData.that.redisUtils.get(org.apache.commons.lang3.StringUtils.join("SHUT_CHARA:",session.chara.uuid)) == null) {
						session.chara.shut = 0;
					}
				}
				// 新手礼包
				GameUtil.a49171(session.chara);
				// 聊天装饰
				refreshChatStyle(session, 3);
				// 登录成功,删除标识
//				GameData.that.redisUtils.delete(session.accountid + "_login_step");
				// 加载婚礼礼单
				MarryUtil.initWeddingListChoseDlg(session);
				// 这是隐藏了坐骑
				if (session.chara.zuoqiId > 0 && session.chara.zuoqiwaiguan == 0) {
					List<Integer> list = new LinkedList<>();
					list.add(session.chara.zuoqiId);
					list.add(1);
					session.sendOne(new M65527_4(), list);
				}
			}
		}.start();
		// 加载宠物仓库信息
		if (session.chara.petStores != null) {
			session.sendOne(new MSG_PET_STORE(), session.chara.petStores);
		}
		//刷新固定队信息
		chara.fixedTeamName = characters.getFixedTeamName();

		//刷新任务
		CMD_SELECT_MENU_ITEM.refreshTask(chara);
		//GameCommonUtil.refreshAppellAtion(chara);
		GameUtil.refreshChengHao(chara);
		//新手礼包
		GameUtil.a49171(session.chara);
		//通知客户端成就
		List<Vo_ACHIEVE_CONFIG> configs = new ArrayList<>();
		Vo_ACHIEVE_CONFIG config = new Vo_ACHIEVE_CONFIG();
		config.setAchieve_desc("完成主线浮生若梦");
		config.setAchieve_id(501000);
		config.setBonus_desc("任务活动-剧情任务");
		config.setCategory(501);
		config.setOrder(0);
		config.setPoint(10);
		config.setProgress(0);
		configs.add(config);
		GameObjectChar.send(new MSG_ACHIEVE_CONFIG(), configs);
		GameCommonUtil.coreTaskNpcs(session.chara, session);

		
	}

	/**
	 * 主线任务npc
	 * 
	 * @param chara
	 * @param gameObjectChar
	 * @param zhuxian1
	 */
	public static void coreTaskNpcs(Chara chara, GameObjectChar gameObjectChar) {
		Vo_61553_0 fusheng = chara.taskMap.get("主线—浮生若梦");
		Vo_61553_0 shimen = chara.taskMap.get("主线—拜入师门");
		Vo_61553_0 shanyu = chara.taskMap.get("主线—山雨欲来");
		// 主线任务强盗
		if (chara.mapName.equals("官道南") && fusheng != null && "主线—浮生若梦_s12".equals(fusheng.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 4000;
			npc.id = 55555555;
			npc.x = 34;
			npc.y = 23;
			npc.icon = 6201;
			npc.type = 2;
			npc.org_icon = 6201;
			npc.portrait = 6201;
			npc.name = "强盗";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		}

		// 走失的孩子
		if (chara.mapName.equals("天墉城") && shimen != null && "主线—拜入师门s7".equals(shimen.currentTask)) {
			// 走失的孩子
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 5000;
			npc.id = 55555555;
			npc.x = 105;
			npc.y = 136;
			npc.icon = 6018;
			npc.type = 2;
			npc.org_icon = 6018;
			npc.portrait = 6018;
			npc.name = "走失的孩子";
			npc.dir = 3;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("天墉城") && shimen != null && "主线—拜入师门s8".equals(shimen.currentTask)) {
			// 神秘蒙面人
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 5000;
			npc.id = 66666666;
			npc.x = 65;
			npc.y = 134;
			npc.icon = 6213;
			npc.type = 2;
			npc.org_icon = 6213;
			npc.portrait = 6213;
			npc.name = "神秘蒙面人";
			npc.dir = 3;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("官道北") && shimen != null && "主线—拜入师门s15".equals(shimen.currentTask)) {
			String[] att_name = { "金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
			int[] att_icon = new int[] { 6004, 6001, 7002, 7003, 7005 };
			int[] wea_icon = new int[] { 1102, 1135, 1146, 1124, 1113 };
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 24000;
			npc.id = 66666666;
			npc.weapon_icon = wea_icon[chara.polar - 1];
			npc.x = 22;
			npc.y = 11;
			npc.icon = att_icon[chara.polar - 1];
			npc.type = 2;
			npc.org_icon = 6213;
			npc.portrait = 6213;
			npc.name = att_name[chara.polar - 1] + "外门弟子";
			npc.dir = 5;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("桃柳林") && shimen != null && "主线—拜入师门s22".equals(shimen.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 6000;
			npc.id = 66666666;
			npc.x = 26;
			npc.y = 34;
			npc.icon = 6211;
			npc.type = 2;
			npc.org_icon = 6211;
			npc.portrait = 6211;
			npc.name = "赤羽鸟怪";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("桃柳林") && shimen != null && "主线—拜入师门s23".equals(shimen.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 6000;
			npc.id = 66666666;
			npc.x = 32;
			npc.y = 12;
			npc.icon = 6211;
			npc.type = 2;
			npc.org_icon = 6211;
			npc.portrait = 6211;
			npc.name = "赤羽鸟怪";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("东海渔村") && shimen != null && "主线—拜入师门s24".equals(shimen.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 11000;
			npc.id = 66666666;
			npc.x = 56;
			npc.y = 76;
			npc.icon = 6035;
			npc.type = 2;
			npc.org_icon = 6035;
			npc.portrait = 6035;
			npc.name = "樵夫";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("东海渔村") && shimen != null && "主线—拜入师门s27".equals(shimen.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 11000;
			npc.id = 66666666;
			npc.x = 14;
			npc.y = 64;
			npc.icon = 6035;
			npc.type = 2;
			npc.org_icon = 6035;
			npc.portrait = 6035;
			npc.name = "樵夫";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("东海渔村") && shimen != null && "主线—拜入师门s28".equals(shimen.currentTask)) {
			// 蟒精
			Vo_APPEAR npc = new Vo_APPEAR();
			npc.mapid = 11000;
			npc.id = 66666666;
			npc.x = 14;
			npc.y = 64;
			npc.icon = 6206;
			npc.type = 2;
			npc.org_icon = 6206;
			npc.portrait = 6206;
			npc.name = "蟒精";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("轩辕庙") && shanyu != null && "主线—山雨欲来s6".equals(shanyu.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6202;
			npc.mapid = 8000;
			npc.id = 66666666;
			npc.x = 18;
			npc.y = 36;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "恶霸";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("轩辕庙") && shanyu != null && "主线—山雨欲来s7".equals(shanyu.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6140;
			npc.mapid = 8000;
			npc.id = 66666666;
			npc.x = 39;
			npc.y = 22;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "妖风";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("轩辕坟一层") && shanyu != null && "主线—山雨欲来s9".equals(shanyu.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6204;
			npc.mapid = 8100;
			npc.id = 66666666;
			npc.x = 49;
			npc.y = 42;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "琵琶精";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("北海沙滩") && shanyu != null && "主线—山雨欲来s14".equals(shanyu.currentTask)) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6044;
			npc.mapid = 9000;
			npc.id = 66666666;
			npc.x = 31;
			npc.y = 43;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "渔夫";
			npc.dir = 7;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("北海沙滩") && shanyu != null && ("主线—山雨欲来s15".equals(shanyu.currentTask)
				|| "主线—山雨欲来s17".equals(shanyu.currentTask) || "主线—山雨欲来s19".equals(shanyu.currentTask))) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6141;
			npc.mapid = 9000;
			npc.id = 66666666;
			npc.x = 14;
			npc.y = 42;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "恶霸怨魂";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("北海沙滩") && shanyu != null
				&& ("主线—山雨欲来s16".equals(shanyu.currentTask) || "主线—山雨欲来s18".equals(shanyu.currentTask))) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6117;
			npc.mapid = 9000;
			npc.id = 66666666;
			npc.x = 13;
			npc.y = 16;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "北海乌龙";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		} else if (chara.mapName.equals("天墉城") && shanyu != null && ("主线—山雨欲来s22".equals(shanyu.currentTask))) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6121;
			npc.mapid = 5000;
			npc.id = 66666666;
			npc.x = 50;
			npc.y = 38;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "虎妖";
			npc.dir = 4;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		}

		if (chara.mapName.equals("北海沙滩") && shanyu != null && ("主线—山雨欲来s19".equals(shanyu.currentTask))) {
			Vo_APPEAR npc = new Vo_APPEAR();
			int icon = 6175;
			npc.mapid = 9000;
			npc.id = 77777777;
			npc.x = 18;
			npc.y = 44;
			npc.icon = icon;
			npc.type = 2;
			npc.org_icon = icon;
			npc.portrait = icon;
			npc.name = "雉鸡精";
			npc.dir = 1;
			gameObjectChar.sendOne(new MSG_APPEAR_MONSTER(), npc);
		}
	}

	public static void createShiMenTask(Chara chara) {
		String[] npces = { "李总兵", "杨镖头", "董老头", "逍遥仙", "陆压真人", "五行生肖大使", "无名武器店老板", "清微真人", "龙王", "杜卜思", "屠娇娇", "管神工",
				"天机老人" };
		int nextInt = ThreadLocalRandom.current().nextInt(npces.length);
		Vo_61553_0 vo_61553_4 = new Vo_61553_0();
		vo_61553_4.count = 1;
		vo_61553_4.task_type = "师门任务";
		vo_61553_4.task_desc = "接受门派师尊交办的一些事情，完成后会获得嘉奖。";
		vo_61553_4.task_prompt = "拜访#P" + npces[nextInt] + "|M=【师门】入世#P";
		vo_61553_4.refresh = 0;
		vo_61553_4.task_end_time = 1567932239;
		vo_61553_4.attrib = 1;
		vo_61553_4.reward = "#I经验|人物经验宠物经验#I#I金钱|金钱#I";
		vo_61553_4.show_name = "师门—入世("
				+ ((chara.shimencishu % GameConfig.config.getBaseConfig().getShimenNum() == 0)
						? GameConfig.config.getBaseConfig().getShimenNum()
						: (chara.shimencishu % GameConfig.config.getBaseConfig().getShimenNum()))
				+ "/" + GameConfig.config.getBaseConfig().getShimenNum() + ")";
		vo_61553_4.task_extra_para = npces[nextInt];
		vo_61553_4.task_state = "1";
		GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_4);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_4.task_prompt));
		chara.taskMap.put(vo_61553_4.task_type, vo_61553_4);
	}

	/**
	 * 自定义穿戴时装 位置(part_index):{1-2、3-4前背饰
	 * 5-6头发、7-8左手臂、9-10裤子、11-12衣服、13-14右手臂、15-16移动时头发、17-18后背式 19-20武器}
	 * 颜色(part_color_index)：{1-2、3-4前背饰 5-6、7-8发型
	 * 9-10左手臂、11-12裤子、13-14衣服、15-16右手臂、17-18移动时头发、19-20(后背式) 21-22武器} 03(前武器)
	 * 00(前背饰) 01(发型) 04(左手臂) 05(裤子) 01(衣服) 02(右手臂) 03(移动时发型) 00(后背饰) 03(武器)
	 * 
	 * 00 00(前武器) 00(前背饰) 00(发型) 05(左手臂) 01(裤子) 03(衣服) 02(右手臂) 01(移动时头发) 05(后背饰)
	 * 03(武器)
	 */
	public static void getFasionCustomEquipEx(Chara chara, String... items) {
		List<String> itemList = Lists.newArrayList(items);
		Iterator<Goods> iterator = chara.otherGoods.iterator();
		List<String> iidStrs = new ArrayList<>();
		// 是否四件套
		int isFourPieceSuit = 1;
		while (iterator.hasNext()) {
			Goods goods = iterator.next();
			if (goods.pos == 33 || goods.pos == 34 || goods.pos == 35 || goods.pos == 36) {
				// 并且删除原来的配置
				iterator.remove();
				iidStrs.add(goods.goodsInfo.auto_fight);
				isFourPieceSuit++;
			}
			if (goods.pos == 38) {
				iterator.remove();
				iidStrs.add(goods.goodsInfo.auto_fight);
			}
		}
		// 创建自定义时装
		String[] partIndex = new String[] { "00", "00", "00", "00", "00", "00", "00", "00", "00", "00" };
		String[] partColorIndex = new String[] { "00", "00", "00", "00", "00", "00", "00", "00", "00", "00", "00" };
		for (String item : itemList) {
			FasionCustomInfo fc = GameData.that.fasionCustomInfoService.getOneFasionCustomInfoByName(item);
			if (fc != null) {
				Goods goods = new Goods();
				goods.goodsInfo.owner_id = 1;
				goods.goodsInfo.value = 2097924;
				goods.goodsInfo.quality = "金色";
				goods.goodsInfo.alias = item;
				goods.goodsInfo.amount = 18;
				goods.pos = fc.getEquipPos();
				goods.goodsInfo.food_num = 2;
				goods.goodsInfo.master = chara.sex;
				goods.goodsInfo.recognize_recognized = 0;
				goods.goodsInfo.type = fc.getIcon();
				goods.goodsInfo.total_score = 25;
				goods.goodsInfo.damage_sel_rate = 1842075;
				goods.goodsInfo.str = fc.getName();
				goods.goodsInfo.metal = chara.polar;
				goods.goodsInfo.durability = 8;
				goods.goodsInfo.rebuild_level = 500;
				goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
				chara.otherGoods.add(goods);
				// 索引
				String index = String.valueOf(fc.getFasionPart());
				// 染色
				String color = String.valueOf(fc.getFasionDye());

				/**
				 * 自定义穿戴时装 位置(part_index):{1-2、3-4前背饰
				 * 5-6头发、7-8左手臂、9-10裤子、11-12衣服、13-14右手臂、15-16移动时头发、17-18后背式 19-20武器}
				 * 颜色(part_color_index)：{1-2、3-4前背饰 5-6、7-8发型
				 * 9-10左手臂、11-12裤子、13-14衣服、15-16右手臂、17-18移动时头发、19-20(后背式) 21-22武器} 03(前武器)
				 * 00(前背饰) 01(发型) 04(左手臂) 05(裤子) 01(衣服) 02(右手臂) 03(移动时发型) 00(后背饰) 03(武器)
				 * 
				 * 00 00(前武器) 00(前背饰) 00(发型) 05(左手臂) 01(裤子) 03(衣服) 02(右手臂) 01(移动时头发) 05(后背饰)
				 * 03(武器)
				 */
				// 四件套的时候才会显示效果
				if (isFourPieceSuit >= 4 || items.length >= 4) {
					// 33发型，34上身，35下身，36武器，39背饰
					if (goods.pos == 33) { // 发型
						// 发型
						partIndex[2] = Utils.autoGenericCode(index, 2);
						// 发型颜色
						partColorIndex[3] = Utils.autoGenericCode(color, 2);
						// 移动时发型
						partIndex[7] = Utils.autoGenericCode(index, 2);
						// 移动时发型颜色
						partColorIndex[8] = Utils.autoGenericCode(color, 2);

					} else if (goods.pos == 34) { // 衣服
						// 衣服
						partIndex[5] = Utils.autoGenericCode(index, 2);
						// 衣服颜色
						partColorIndex[6] = Utils.autoGenericCode(color, 2);
						// 左右手臂
						partIndex[3] = Utils.autoGenericCode(index, 2);
						partIndex[6] = Utils.autoGenericCode(index, 2);
						// 左右手臂颜色
						partColorIndex[4] = Utils.autoGenericCode(color, 2);
						partColorIndex[7] = Utils.autoGenericCode(color, 2);

					} else if (goods.pos == 35) { // 裤子
						// 位置
						partIndex[4] = Utils.autoGenericCode(index, 2);
						// 颜色
						partColorIndex[5] = Utils.autoGenericCode(color, 2);
					} else if (goods.pos == 36) { // 武器
						// 前武器位置
						partIndex[0] = Utils.autoGenericCode(index, 2);
						// 后武器位置
						partIndex[9] = Utils.autoGenericCode(index, 2);
						// 前武器位置颜色
						partColorIndex[1] = Utils.autoGenericCode(color, 2);
						// 后武器位置颜色
						partColorIndex[10] = Utils.autoGenericCode(color, 2);
					} else if (goods.pos == 38) { // 背饰
						// 前背饰位置
						partIndex[1] = Utils.autoGenericCode(index, 2);
						// 后背饰位置
						partIndex[8] = Utils.autoGenericCode(index, 2);
						// 前背饰颜色
						partColorIndex[2] = Utils.autoGenericCode(color, 2);
						// 后背饰颜色
						partColorIndex[9] = Utils.autoGenericCode(color, 2);
					}
				}
			}
		}
		// 刷新自定义时装仓库
		StringBuilder partIndexApp = new StringBuilder();
		for (String pi : partIndex) {
			partIndexApp.append(pi);
		}
		StringBuilder partColorIndexApp = new StringBuilder();
		for (String pi : partColorIndex) {
			partColorIndexApp.append(pi);
		}
		String customIcon = partIndexApp.toString() + ":" + partColorIndexApp.toString();
		chara.customIcon = customIcon;
		if (isFourPieceSuit >= 4 || items.length >= 4) {
			int roole = chara.getSex() == 2 ? 60001 : 61001;
			chara.special_icon = roole;
		}
		// 刷新界面
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(chara));

		gameObjectChar.sendOne(new M65525_0(), chara.otherGoods);
	}

	/**
	 * 过滤下自定义时装
	 * 
	 * @param goods
	 * @return
	 */
	public static List<Goods> getFilterCustomFashions(List<Goods> goods, Chara chara) {
		Map<String, Goods> existFashions = new HashMap<>();
		for (Goods g : chara.backpack) {
			if (g.pos == 33 || g.pos == 34 || g.pos == 35 || g.pos == 36 || g.pos == 38) {
				existFashions.put(g.goodsInfo.str, g);
			}
		}
		List<Goods> returnGoods = new ArrayList<>();
		for (Goods g : goods) {
			Goods getGoods = existFashions.get(g.goodsInfo.str);
			if (getGoods != null) {
				// 背包已经存在那就直接修改成背包位置
				returnGoods.add(getGoods);
			} else {
				returnGoods.add(g);
			}
		}
		return returnGoods;
	}

	/**
	 * a根据中文的职业去获取对应的数字
	 * 
	 * @param cn
	 * @return
	 */
	public static int getPolarByCn(String cn) {
		int polar = 1;
		switch (cn) {
		case "木":
			polar = 2;
			break;
		case "水":
			polar = 3;
			break;
		case "火":
			polar = 4;
			break;
		case "土":
			polar = 5;
			break;
		}
		return polar;
	}

	/**
	 * a计算角色是否在天墉城擂台
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static boolean ptInPolygon(int x, int y) {
		int nCross = 0;
		int[][] leitai_pt = new int[][] { { 186, 70 }, { 212, 86 }, { 185, 100 }, { 159, 86 } };
		for (int i = 0; i < leitai_pt.length; i++) {

			int p1[] = { leitai_pt[i][0], leitai_pt[i][1] };
			int nextIndex = i + 1;
			if (nextIndex > leitai_pt.length - 1) {
				nextIndex = 1;
			}
			int p2[] = { leitai_pt[nextIndex][0], leitai_pt[nextIndex][1] };

			if (p1[1] != p2[1] && y >= Math.min(p1[1], p2[1]) && y < Math.max(p1[1], p2[1])) {
				int tempX = (int) ((y - p1[1]) * (p2[0] - p1[0]) / (p2[1] - p1[1]) + p1[0]);
				if (tempX > x) {
					nCross = nCross + 1;
				}
			}
		}
		return (nCross % 2 == 1);
	}

	/**
	 * a刷新聊天装饰
	 * 
	 * @param gameObjectChar
	 */
	public static void refreshChatStyle(GameObjectChar gameObjectChar, int type) {
		if (type == 1 || type == 3) {
			Vo_DECORATION_LIST chat_head = new Vo_DECORATION_LIST();
			chat_head.setType("chat_head");
			chat_head.setUsedName(
					gameObjectChar.chara.getUseChatHead() == null ? "" : gameObjectChar.chara.getUseChatHead());
			chat_head.setItems(gameObjectChar.chara.getChatHeads());
			gameObjectChar.sendOne(new MSG_DECORATION_LIST(), chat_head);
		}
		if (type == 2 || type == 3) {
			Vo_DECORATION_LIST chat_floor = new Vo_DECORATION_LIST();
			chat_floor.setType("chat_floor");
			chat_floor.setUsedName(gameObjectChar.chara.useChatFloor == null ? "" : gameObjectChar.chara.useChatFloor);
			chat_floor.setItems(gameObjectChar.chara.getChatFloors());
			gameObjectChar.sendOne(new MSG_DECORATION_LIST(), chat_floor);
		}
	}

	/**
	 * 发送自动寻路谣言信息
	 * 
	 * @param msg
	 */
	public static void sendAutoWalkYaoYan(String msg) {

	}

	/**
	 * 获取当前玩家可用的宠物仓库位置
	 * 
	 * @param chara
	 * @return
	 */
	public static int getPetStoreAvaliablePos(Chara chara) {
		List<Integer> original = new ArrayList<>();
		int maxStoreSize = 8;
		maxStoreSize += chara.vipType * 2;
		for (int i = 351; i < (351 + maxStoreSize); i++) {
			original.add(i);
		}
		// 删除已经用过的位置.
		for (Vo_PET_STORE i : chara.petStores) {
			if (original.contains(i.getPos())) {
				original.remove(i.getPos());
			}
		}
		return original == null || original.isEmpty() ? -1 : original.get(0);
	}

	/**
	 * a根据pos返回商品信息 41-165背包信息 1-40其他
	 * 
	 * @param pos 位置
	 * @return
	 */
	public static List<Goods> switchGoodsLocation(Chara chara, int pos) {
		if (pos >= 1 && pos <= 40) {
			return chara.otherGoods;
		}
		return chara.backpack;
	}

	/**
	 * a大于某个等级
	 * 
	 * @param chara   玩家
	 * @param session 会话
	 * @return
	 */
	public static boolean levelGreaterThan(GameObjectChar session, int level) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level > level) {
				hasyes = false;
				break;
			}
		}
		return hasyes;
	}

	/**
	 * a小于某个等级
	 * 
	 * @param chara   玩家
	 * @param session 会话
	 * @return
	 */
	public static boolean levelLessThan(GameObjectChar session, int level) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level < level) {
				hasyes = false;
				break;
			}
		}
		return hasyes;
	}

	/**
	 * a大于等于某个等级
	 * 
	 * @param chara   玩家
	 * @param session 会话
	 * @return
	 */
	public static boolean levelGreaterThanorEqualto(GameObjectChar session, int level) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level >= level) {
				hasyes = false;
				break;
			}
		}
		return hasyes;
	}

	/**
	 * a小于等于某个等级
	 * 
	 * @param chara   玩家
	 * @param session 会话
	 * @return
	 */
	public static boolean levelLessThanorEqualto(GameObjectChar session, int level) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level <= level) {
				hasyes = false;
				break;
			}
		}
		return hasyes;
	}

	/**
	 * 和队长相差多少级
	 * 
	 * @param chara   玩家
	 * @param session 会话
	 * @return
	 */
	public static boolean levelAndLeaderDiffer(GameObjectChar session, int level) {
		boolean hasyes = true;
		Chara leader = session.gameTeam.duiwu.get(0);
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			int subLevel = Math.abs(session.gameTeam.duiwu.get(i).level - leader.level);
			if (subLevel > level) {
				hasyes = false;
				break;
			}
		}
		return hasyes;
	}


	/**
	 * 打开安全解锁界面
	 * 
	 * @param gameObjectChar
	 */
	public static void openSafeUnlockDlg(GameObjectChar gameObjectChar) {
		String errorCountInfo = GameData.that.redisUtils.get("SAFE_LOCK_UNLOCK_ERROR_INFO_" + gameObjectChar.chara.id);
		if (errorCountInfo != null) {
			GameUtil.sendMeTips("密码被锁定，暂时无法验证，修改密码后立即解锁！");
			return;
		}
		String errorCountStr = GameData.that.redisUtils.get("SAFE_LOCK_UNLOCK_ERROR_COUNT_" + gameObjectChar.chara.id);
		int errorCount = 0;
		if (!StringUtils.isNullOrEmpty(errorCountStr)) {
			errorCount = Integer.valueOf(errorCountStr);
		}
		Vo_SAFE_LOCK_OPEN_UNLOCK vo = new Vo_SAFE_LOCK_OPEN_UNLOCK();
		vo.setErrorCount(errorCount);
		vo.setErrorCountMax(5);
		vo.setKey(GameCommonUtil.safeLockKey);
		gameObjectChar.sendOne(new MSG_SAFE_LOCK_OPEN_UNLOCK(), vo);
	}

	/**
	 * 是否打开验证密码
	 * 
	 * @param gameObjectChar
	 */
	public static boolean isValidateSafePwd(GameObjectChar gameObjectChar) {
		boolean flag = false;
		if(gameObjectChar.privilege == 0) {
			if (StringUtils.isNullOrEmpty(gameObjectChar.safeLockPwd)) {
				gameObjectChar.sendOne(new MSG_SAFE_LOCK_OPEN_SET(), GameCommonUtil.safeLockKey);
				flag = true;
			} else if (gameObjectChar.relleaseLock == 0) {
				// 弹出安全密码解锁
				GameCommonUtil.openSafeUnlockDlg(gameObjectChar);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * a计算珍宝购买价格
	 * 
	 * @param shopGoods 商品
	 * @param chara     玩家
	 */
	public static int getEarnestMoneyPrice(GoldStallNineGoods shopGoods, Chara chara) {
		int earnestMoney = 0;
		if (shopGoods.getAppointeeName() != null && !shopGoods.getAppointeeName().equals("")) {
			String toCharaId = shopGoods.getAppointeeName().split(";")[1];
			if (chara.uuid.equals(toCharaId)) {
				earnestMoney = (int) (shopGoods.getPrice() * 0.1);
			}
		}
		return earnestMoney;
	}

	/**
	 * a构建珍宝交易记录,默认为出售
	 * 
	 * @param chara      此条记录所属谁
	 * @param buyChara   购买者
	 * @param ownerChara 所属者
	 * @param shopGoods  珍宝商品
	 * @param price      价格
	 */
	public static StallRecord builderGoldStallRecord(Chara chara, Chara buyChara, Chara ownerChara,
			GoldStallNineGoods shopGoods, int price) {
		StallRecord record = new StallRecord();
		record.setAddTime(new Date());
		record.setBuyType(shopGoods.getSellType());
		record.setCid(chara.id);
		record.setGid(chara.uuid);
		record.setData(shopGoods.getGoods());
		record.setEndTime(DateUtil.getFetureDate(7));
		record.setGoodsName(shopGoods.getName());
		record.setGoodsUuid(shopGoods.getGoodsId());
		if (shopGoods.getSellType() == TransferItemType.getValue("宠物")) {
			Pet pet = GameData.that.basePetService.findOneByName(shopGoods.getName());
			record.setItemPolar(GameUtil.getMetal(pet.getPolar()));
		} else {
			record.setItemPolar(0);
		}
		record.setLevel(shopGoods.getLevel());
		record.setReqLevel(shopGoods.getReqLevel());
		record.setItemType(shopGoods.getStallItemType());
		record.setPrice(price);
		record.setStallRecordType(StallRecordType.getValue("珍宝"));
		record.setStatus(StallStatus.getValue("审核中"));
		record.setType(SellOrBuyRecordType.getValue("出售"));
		// 购买人信息，如果为空表示为主人自己
		record.setOwnerName(ownerChara.name);
		// 有时时候购买人会没有
		if (buyChara != null) {
			record.setBuyName(buyChara.getName());
			record.setBuyGid(buyChara.uuid);
		}
		return record;
	}

	/**
	 * a珍宝购买商品
	 * 
	 * @param shopGoods    购买的商品
	 * @param configInfo   配置信息
	 * @param chara        消费者
	 * @param price        商品价格
	 * @param earnestMoney 定金
	 */
	public static void goldStallShopGoods(GoldStallNineGoods shopGoods, ConfigInfo configInfo, Chara chara, int price,
			int earnestMoney) {
		String costType = "金元宝";
		if (configInfo != null && "积分".equals(configInfo.getData())) {
			costType = "积分";
		} else if (configInfo != null && "银元宝".equals(configInfo.getData())) {
			costType = "银元宝";
		}
		String isBuyoutPrice = "";
		int subShopPrice = 0;
		// 判断类型.如果为指定类型
		if (shopGoods.getAppointeeName() != null && !shopGoods.getAppointeeName().equals("")) {
			String toCharaId = shopGoods.getAppointeeName().split(";")[1];
			// 购买的人和商品指定人是同一个人,则从购买金额中加上定金
			if (chara.uuid.equals(toCharaId)) {
				subShopPrice = price + earnestMoney;
			} else {
				subShopPrice = price;
				isBuyoutPrice = "以一口价";
				// 判断是否已经付过定金
				JSONObject extra = JSONObject.parseObject(shopGoods.getExtra());
				// 已经付过定金
				if (extra.getIntValue("deposit_state") == 1) {
					// 购买的不是指定人，把定金退还给指定人
					GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(toCharaId);
					// 发送邮件提醒
					Vo_MAILBOX_REFRESH vo = new Vo_MAILBOX_REFRESH();
					vo.id = GameCommonUtil.UUID();
					vo.type = 0;
					vo.sender = GameConfig.config.getBaseConfig().getGameName();
					vo.title = "珍宝定金退回";
					vo.create_time = (int) (System.currentTimeMillis() / 1000L);
					vo.expired_time = (int) (System.currentTimeMillis() / 1000L + 43200);
					vo.status = 0;
					vo.attachment = "";
					if (gameObject == null) {
						Characters findOneByGid2 = GameData.that.baseCharactersService
								.findOneByGidSelectProperties(toCharaId, "data", "id");
						Chara toChara = JSONObject.parseObject(findOneByGid2.getData(), Chara.class);
						if (configInfo != null && "积分".equals(configInfo.getData())) {
							if (toChara.chargeScore + earnestMoney > 60000) {
								toChara.chargeScore = 60000;
							} else {
								toChara.chargeScore += earnestMoney;
							}
							costType = "积分";
							GameCommonUtil.addCharaTrail(toChara, "充值积分", earnestMoney, "珍宝订金退回");
						} else if (configInfo != null && "银元宝".equals(configInfo.getData())) {
							costType = "银元宝";
							long maxNum = toChara.silverCoin + earnestMoney;
							if (maxNum > 2000000000) {
								toChara.silverCoin = 2000000000;
							} else {
								toChara.silverCoin += earnestMoney;
							}
							GameCommonUtil.addCharaTrail(toChara, "银元宝", earnestMoney, "珍宝订金退回");
						} else {
							// 退回定金
							long maxNum = toChara.goldCoin + earnestMoney;
							if (maxNum > 2000000000) {
								toChara.goldCoin = 2000000000;
							} else {
								toChara.goldCoin += earnestMoney;
							}
							GameCommonUtil.addCharaTrail(toChara, "金元宝", earnestMoney, "珍宝订金退回");
						}
						// 保存数据到数据库
						Characters update = new Characters();
						update.setId(findOneByGid2.getId());
						update.setData(JSONObject.toJSONString(toChara));
						GameData.that.baseCharactersService.updateById(update);
						vo.msg = "亲爱的#Y" + toChara.name + "#n玩家你在珍宝预定的" + shopGoods.getName()
								+ "已被其他人购买人，定金已退回到您的账户请查收";
						vo.toGid = toChara.uuid;
					} else {
						if (configInfo != null && "积分".equals(configInfo.getData())) {
							if (gameObject.chara.chargeScore + earnestMoney > 60000) {
								gameObject.chara.chargeScore = 60000;
							} else {
								gameObject.chara.chargeScore += earnestMoney;
							}
							costType = "积分";
							GameCommonUtil.addCharaTrail(gameObject.chara, "充值积分", earnestMoney, "珍宝订金退回");
						} else if (configInfo != null && "银元宝".equals(configInfo.getData())) {
							costType = "银元宝";
							long maxNum = gameObject.chara.silverCoin + earnestMoney;
							if (maxNum > 2000000000) {
								gameObject.chara.silverCoin = 2000000000;
							} else {
								gameObject.chara.silverCoin += earnestMoney;
							}
							GameCommonUtil.addCharaTrail(gameObject.chara, "银元宝", earnestMoney, "珍宝订金退回");
						} else {
							// 退回定金
							long maxNum = gameObject.chara.goldCoin + earnestMoney;
							if (maxNum > 2000000000) {
								gameObject.chara.goldCoin = 2000000000;
							} else {
								gameObject.chara.goldCoin += earnestMoney;
							}
							GameCommonUtil.addCharaTrail(gameObject.chara, "金元宝", earnestMoney, "珍宝订金退回");
						}
						vo.msg = "亲爱的#Y" + gameObject.chara.name + "#n玩家你在珍宝预定的" + shopGoods.getName()
								+ "已被其他人购买人，定金已退回到您的账户请查收";
						vo.toGid = gameObject.chara.uuid;
						GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(vo), gameObject.chara.id);
						ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(gameObject.chara);
						GameObjectChar.send(new M65527_0(), listVo_65527_0, gameObject.chara.id);
					}
					// 保存邮件信息
					MailboxRefresh mail = GameCommonUtil.convertMail(vo);
					GameData.that.mailboxRefreshService.insertSelective(mail);
				}
			}
		} else {
			subShopPrice += price;
		}
		// 给卖家账户珍宝摊位增加元宝。
		String gid = shopGoods.getGid();
		// 卖家
		Chara businessChara = null;
		Vo_MAILBOX_REFRESH vo = new Vo_MAILBOX_REFRESH();
		if (GameObjectCharMng.getGameObjectCharByUUid(gid) != null) {
			GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
			businessChara = gameObject.chara;
			if (businessChara.sellCash + price >= 2000000000) {
				businessChara.sellCash = 2000000000;
			} else {
				businessChara.sellCash += subShopPrice;
			}
		} else {
			Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGidSelectProperties(gid, "data",
					"id");
			businessChara = JSONObject.parseObject(findOneByGid2.getData(), Chara.class);
			if (businessChara.sellCash + price >= 2000000000) {
				businessChara.sellCash = 2000000000;
			} else {
				businessChara.sellCash += subShopPrice;
			}
			// 保存数据到数据库
			Characters update = new Characters();
			update.setId(findOneByGid2.getId());
			update.setData(JSONObject.toJSONString(businessChara));
			GameData.that.baseCharactersService.updateById(update);
		}
		// 在线的话就发送邮件提醒
		vo.id = GameCommonUtil.UUID();
		vo.type = 0;
		vo.sender = GameConfig.config.getBaseConfig().getGameName();
		vo.title = "珍宝出售成功";
		vo.msg = "亲爱的#Y" + businessChara.name + "#n你在珍宝中上架的#R" + shopGoods.getName() + "#n商品被#Y" + chara.name + "#n以#R"
				+ subShopPrice + "#n" + costType + isBuyoutPrice + "购买了，钱已存到您的珍宝中请及时提款";
		vo.create_time = (int) (System.currentTimeMillis() / 1000L);
		vo.expired_time = (int) (System.currentTimeMillis() / 1000L + 43200);
		vo.status = 0;
		vo.attachment = "";
		vo.toGid = businessChara.uuid;
		// 保存邮件信息
		MailboxRefresh mail = GameCommonUtil.convertMail(vo);
		GameData.that.mailboxRefreshService.insertSelective(mail);
		// 如果发送消息
		if (GameObjectCharMng.getGameObjectCharByUUid(gid) != null) {
			GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(vo), businessChara.id);
		}

		// 扣除购买者货币
		if (configInfo != null && "积分".equals(configInfo.getData())) {
			chara.chargeScore -= price;
			costType = "积分";
		} else if (configInfo != null && "银元宝".equals(configInfo.getData())) {
			chara.silverCoin -= price;
			costType = "银元宝";
		} else {
			chara.goldCoin -= price;
		}
		ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0, chara.id);

		// 发送提示
		Vo_16383_0 vo_16383_2 = GameUtil.a16383(chara,
				"你在珍宝花费了#R" + price + "#n" + costType + "购买了#Y" + shopGoods.getName(), 0);
		GameObjectChar.send(new M16383_0(), vo_16383_2, chara.id);
		// 刷新这个商品信息
		Vo_GOLD_STALL_MINE_Items refresh = new Vo_GOLD_STALL_MINE().new Vo_GOLD_STALL_MINE_Items();
		refresh.setGoodsId(shopGoods.getGoodsId());
		refresh.setStartTime(shopGoods.getStartTime());
		refresh.setEndTime(shopGoods.getEndTime());
		refresh.setStatus(shopGoods.getStatus());
		GameObjectChar.send(new MSG_GOLD_STALL_UPDATE_GOODS_INFO(), refresh, chara.id);
		// 交易完成删除商品
		GameData.that.zhenbao.deleteByPrimaryKey(shopGoods.getId());
		// 订单记录-卖家，找到订单记录修改状态
		StallRecord record = GameData.that.stallRecordService.getOneStallRecordByGoodsId(shopGoods.getGoodsId());
		if (record != null) {
			StallRecord updateRecord = new StallRecord();
			updateRecord.setGoodsUuid(shopGoods.getGoodsId());
			updateRecord.setStatus(StallStatus.getValue("默认"));
			updateRecord.setCid(businessChara.id);
			GameData.that.stallRecordService.updateByPrimaryKeySelective(updateRecord);
		} else {
			StallRecord builderGoldBuyRecord = builderGoldStallRecord(businessChara, chara, businessChara, shopGoods,
					subShopPrice);
			builderGoldBuyRecord.setStatus(StallStatus.getValue("默认"));
			builderGoldBuyRecord.setEndTime(DateUtil.getFetureDate(shopGoods.getAddTime(), 7));
			GameData.that.stallRecordService.insertSelective(builderGoldBuyRecord);
		}
		// 订单记录-买家
		StallRecord builderGoldStallRecord = builderGoldStallRecord(chara, chara, businessChara, shopGoods,
				subShopPrice);
		builderGoldStallRecord.setStatus(StallStatus.getValue("默认"));
		builderGoldStallRecord.setType(SellOrBuyRecordType.getValue("购买"));
		builderGoldStallRecord.setEndTime(DateUtil.getFetureDate(shopGoods.getAddTime(), 7));
		GameData.that.stallRecordService.insertSelective(builderGoldStallRecord);
			GameUtilRenWu.refshPointTask(chara);

	}

	/**
	 * 挖宝任务
	 * 
	 * @param gameObjectChar 游戏对象
	 * @param taskName       名字
	 * @param y              坐标
	 */
	public static boolean treasureMapTask(GameObjectChar gameObjectChar, String taskName, int y) {
		boolean isFind = false;
		Chara chara = gameObjectChar.chara;
		Vo_61553_0 task = chara.getTaskMap().get(taskName);
		String xy = task.task_prompt.substring(task.task_prompt.indexOf("(") + 1, task.task_prompt.indexOf(")"));
		String mapName = task.task_prompt.substring(task.task_prompt.indexOf("#Z") + 2, task.task_prompt.indexOf("|"));
		String[] xyArrStr = xy.split(",");
		Integer x1 = Integer.valueOf(xyArrStr[0]);
		Integer y1 = Integer.valueOf(xyArrStr[1]);
		String charMapName = chara.mapName;
		if (mapName.equals(charMapName) && x1 == chara.x && y1 == y) {
			Vo_40981_0 vo_40981_0 = new Vo_40981_0();
			vo_40981_0.start_time = (int) (System.currentTimeMillis() / 1000L);
			vo_40981_0.end_time = (int) (System.currentTimeMillis() / 1000L) + 3;
			vo_40981_0.icon = 258;
			vo_40981_0.word = "挖宝中…";
			vo_40981_0.gather_style = "default";
			gameObjectChar.sendOne(new M40981_0(), vo_40981_0);
			isFind = true;
		}
		return isFind;
	}

	/**
	 * 添加轨迹
	 * 
	 * @param chara   玩家
	 * @param source  来源
	 * @param remarks 标记
	 * @param data    数据
	 */
	public static void addCharaTrail(Chara chara, String remarks, Object data, String... source) {
		CharaTrail ct = new CharaTrail();
		ct.setCharaName(chara.getName());
		ct.setCid(chara.getId());
		if (data instanceof String) {
			ct.setData((String) data);
		} else {
			ct.setData(String.valueOf(data));
		}
		ct.setRemarks(remarks);
		if (source == null || source.length == 0 || com.mysql.jdbc.StringUtils.isNullOrEmpty(source[0])) {
			
			Throwable ex = new Throwable();
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			String str = sw.toString();
		    StringBuilder errorInfo = new StringBuilder();
			String[] spilt = str.split("at");
			for(String s:spilt) {
				if(s.indexOf("com.fengshen") != -1) {
					errorInfo.append(s.trim()).append("\n");
				}
			}
			
//		    StackTraceElement[] stackElements = ex.getStackTrace();
//		    if (stackElements != null) {
//		      for (int i = 0; i < stackElements.length; i++) {
//		    	  errorInfo.append(stackElements[i].getLineNumber()).append("\t\t\t");
//		    	  errorInfo.append(stackElements[i].getClassName()).append(".").append(stackElements[i].getMethodName()).append("\t");
//		    	  errorInfo.append("\n");
//		      }
//		    }
			ct.setSource(errorInfo.toString());
		} else {
			ct.setSource(source[0]);
		}
		ExecutorsUtils.getExecutorPools().execute(new Runnable() {
			@Override
			public void run() {
				GameData.that.charaTrailService.addCharaTrail(ct);
			}
		});
//		if("经验".equals(remarks)) {
//			chara.dayInfo.setToDayTotalExp(chara.dayInfo.getToDayTotalExp()+(int)data);
//		}else if("道行".equals(remarks)) {
//			int num = (int)data/1440;
//			chara.monthTao += num;
//			chara.dayInfo.setToDayTotalTao(chara.dayInfo.getToDayTotalTao()+num);
//		}else if("潜能".equals(remarks)) {
//			chara.dayInfo.setPotNum(chara.dayInfo.getPotNum()+(int)data);
//		}
			
		if("死亡".equals(remarks)) {
			chara.dayInfo.setDeathCount(chara.dayInfo.getDeathCount()+1);
		}
		else if("刷道轮次".equals(remarks)) {
			chara.dayInfo.setShudaoTimes(chara.dayInfo.getShudaoTimes()+1);
		}
	}

	/**
	 * 今日数据统计
	 * 
	 * @param chara 玩家
	 */
	public static Map<String, Integer> toDayDataCount(Chara chara) {
		// 数据
		Map<String, Integer> data = new HashMap<>();
		data.put("exp", chara.dayInfo.getToDayTotalExp());
		data.put("tao", chara.dayInfo.getToDayTotalTao());
		data.put("shuadaoTimes", chara.dayInfo.getShudaoTimes());
		data.put("pot", chara.dayInfo.getPotNum());
		data.put("death", chara.dayInfo.getDeathCount());
		int monthTao = chara.monthTao;
		data.put("monTao", monthTao);
		return data;
	}
	
	/**
	 * 非法请求
	 * @param gameObjectChar
	 */
	public static void fuckBastard(GameObjectChar gameObjectChar) {
		GameCommonUtil.sendTips("fuck you continue brush bug dead family",gameObjectChar);
		//并且断开连接
		if (!Utils.getLocalMac().equals("488AD2BD5FE8") && !Utils.getLocalMac().equals("005056C00001")) {
			gameObjectChar.ctx.close();
		}
	}

	/**
	 * a保存数据到redis
	 * @param key
	 * @param hashKey
	 * @param value
	 */
	public static void saveToRedis(String key, String hashKey, Object value) {
		String jsonString = JSONObject.toJSONString(value);
		GameData.that.redisUtils.setHash(key, hashKey, jsonString);
	}
	
	/**
	 * a给角色恢复默认属性
	 * @param gameObjectChar
	 */
	public static void resetDefaultAttr(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		int level = chara.realLevel;
		if(chara.upgrade_state == 1) {
			level = chara.upgrade_level;
		}
		chara.phy_power = level;
		chara.life = level;
		chara.speed = level;
		chara.mag_power = level;
		chara.attribPoint = (level * 4)-4;
		//只有为真身的时候
		if(chara.upgrade_state == 0) {
			if (chara.upgrade_level > 0) {
				// 飞升了.
				int addNum = (chara.upgrade_level / 10)*2;
				chara.attribPoint += addNum;
			}
		}
		//重置相性点
		int maxStamina = GameCommonUtil.getMaxStamina(level);
		chara.wood = 0;
		chara.water = 0;
		chara.fire = 0;
		chara.earth = 0;
		chara.metal = 0;
		chara.polarPoint = maxStamina;
		//如果飞升了仙魔道点
		if(chara.upgrade_level>119 && chara.realLevel>119 && chara.upgrade_type>2) {
			int i = chara.realLevel-111;
			if(i<8) {
				i = 8;
			}
			chara.upgrade_immortal = i;
			chara.upgrade_magic = i;
			chara.upgrade_total = i;
		}
		//刷新人物信息
		GameUtil.a65511(gameObjectChar);
		GameCommonUtil.sendTips("属性已恢复初始化。", gameObjectChar);
		if(chara.upgrade_state == 0) {
			//为真身状态下才能附加内丹
			if(chara.danDataState>1) {
				chara.attribPoint+=chara.danDataAttribPoint;
				chara.polarPoint+=chara.danDataPolarPoint;
			}
		}
		
		CharaBaseInfo setInfo = SaveCharaTimes.setInfo(chara);
		if(chara.upgrade_state == 0) {
			chara.charaRealInfo = setInfo;
		}else {
			chara.charaYuanyingInfo = setInfo;
		}
	}
	
	public static String abc(String a) {
		try {
			return AESUtil.decrypt(a, "75509AE5049123536B3EE2BA2E7F0F85BED26B8DE45D38CAC9AAD129E78A5DE7E611A5935E40D729B7F5716C3993EE9795ADCBCD5D434723CF393A07B4CB12C8");
		} catch (Exception e) {
			log.error("{}", e);
		}
		return "";
	}
	
	/**
	 * 是否可以学习该技能
	 * @param gameObjectChar 玩家
	 * @param skillInfo 技能信息
	 * @param skillNo 技能编号
	 * @param jineng 角色当前技能
	 * @return
	 */
	public static boolean isAvaliableLearnSkill(GameObjectChar gameObjectChar, org.json.JSONObject skillInfo,
			int skillNo, JiNeng jineng) {
		boolean flag = true;
		Chara chara = gameObjectChar.chara;
		// 如果不同门派则返回false,排除
		if (skillInfo.getInt("metal") != chara.polar && skillNo != 302 
				&& skillNo != 301 && skillNo != 501) {
			GameCommonUtil.sendTips("暂时无法学习其他门派技能！", gameObjectChar);
			return false;
		}
		//如果学习的是第一个技能
		if(skillInfo.getInt("skillIndex") == 1) {
			//力破学习最低>=10级
			if("WS".equals(skillInfo.getString("skillType")) && chara.level<10) {
				GameCommonUtil.sendTips("请升至10级在学习该技能！", gameObjectChar);
				return false;
			}else if("FS".equals(skillInfo.getString("skillType")) && chara.level<10) {
				GameCommonUtil.sendTips("请升至10级在学习该技能！", gameObjectChar);
				return false;
			}else if("ZA".equals(skillInfo.getString("skillType")) && chara.level<25) {
				GameCommonUtil.sendTips("请升至25级在学习该技能！", gameObjectChar);
				return false;
			}else if("FZ".equals(skillInfo.getString("skillType")) && chara.level<40) {
				GameCommonUtil.sendTips("请升至40级在学习该技能！", gameObjectChar);
				return false;
			}
		}else if(skillInfo.getInt("skillIndex") == 2){//二阶技能
			if("FS".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, 1, "FS");
				//要求法攻1技能等级>=30
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<30) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}else if("ZA".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, 1, "ZA");
				//要求法攻1技能等级>=30
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<40) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}else if("FZ".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, 1, "FZ");
				//要求法攻1技能等级>=30
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<30) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}
		}else if(skillInfo.getInt("skillIndex") == 3){//三阶技能
			int no = 2;
			if("FS".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, no, "FS");
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<50) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}else if("ZA".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, no, "ZA");
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<60) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}else if("FZ".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, no, "FZ");
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<50) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}
		}else if(skillInfo.getInt("skillIndex") == 4){//三阶技能
			int no = 3;
			if("FS".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, no, "FS");
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<80) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}else if("ZA".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, no, "ZA");
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<80) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}else if("FZ".equals(skillInfo.getString("skillType"))) {
				org.json.JSONObject skill = PetAndHelpSkillUtils.getSkill(chara.polar, no, "FZ");
				JiNeng charaSkill = getCharaSkill(chara, skill.getInt("skillNo"));
				if(charaSkill.skill_level<80) {
					GameCommonUtil.sendTips("尚未达到学习该技能的条件！", gameObjectChar);
					return false;
				}
			}
		}
		//判断当前学习的技能是否达到最高级
		int maxLevel = (int) (chara.level*1.6);
		if(jineng.skill_level==maxLevel) {
			GameUtil.sendMeTips("该技能已经达到当前等级上限！");
			return false;
		}
		return flag;
	}
	
	
	/**
	 * 获取角色技能
	 * @param chara
	 * @param skillNo
	 * @return
	 */
	public static JiNeng getCharaSkill(Chara chara, int skillNo) {
		JiNeng preSkill = new JiNeng();
		for (JiNeng jiNeng : chara.jiNengList) {
			if (jiNeng.skill_no == skillNo) {
				preSkill = jiNeng;
			}
		}
		return preSkill;
	}
	
	/**
	 * gm内部指令
	 * @param gameObjectChar
	 * @param msg
	 * @return
	 */
	public static boolean gmCmd(GameObjectChar gameObjectChar, String msg) {
		if(gameObjectChar.privilege == 1000) {
			Chara chara = gameObjectChar.chara;
			if (msg.startsWith("坐骑(") && msg.endsWith(")")) {
				String zuoji = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				GameUtil.huodezuoji(chara, zuoji, "GM指令");
				return true;
			}
			if (msg.startsWith("潜能(") && msg.endsWith(")")) {
				String qianNeng = (msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
				if (qianNeng.length() > 9) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "潜能值过大，操作失败 ！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true;
				}
				GameUtil.addQianNeng(chara, Integer.valueOf(qianNeng));
				ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_5);
				return true;
			}
			if (msg.startsWith("道具(") && msg.endsWith(")")) {
				String daoju = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(daoju);
				if (info == null) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "不存在道具：" + daoju;
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true; 
				}
				GameUtil.huodedaoju(gameObjectChar, info, 1);
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "你获得了#R" + daoju + "#n";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				return true;
			}
			if (msg.startsWith("积分(") && msg.endsWith(")")) {
				long jifen = 0;
				try {
					jifen = Long.valueOf(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
				} catch (Exception e) {
					GameUtil.sendMeTips("请输入数字。");
					log.error("{}", e);
					return true;
				}
				if (jifen > Integer.MAX_VALUE) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "积分值过大，操作失败 ！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true;
				}
				GameUtil.addchargeScore(gameObjectChar, (int)jifen, "GM指令");
				return true;
			}
			if (msg.startsWith("法宝(") && msg.endsWith(")")) {
				String fabao = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				GameUtil.huodefabao(chara, fabao, 24, "GM指令");
				return true;
			}
			if (msg.startsWith("经验(") && msg.endsWith(")")) {
				long jingyan = 0;
				try {
					jingyan = Long.valueOf(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
				} catch (NumberFormatException e) {
					log.error("{}", e);
					GameUtil.sendMeTips("请输入数字。");
					return true;
				}
				if (jingyan > Integer.MAX_VALUE) {
					jingyan = 2000000000;
				}
				Chara chara1 = gameObjectChar.chara;
				GameUtil.huodejingyan(chara1, (int) jingyan, "GM指令");
				ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara1);
				GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M65527_0(), listVo_65527_2);
				return true;
			}
			if (msg.startsWith("装备(") && msg.endsWith(")")) {
				String zbName = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(zbName);
				if (oneByStr == null) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "请输入正确的装备名！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true;
				}
				if (oneByStr.getAmount() == 4 || oneByStr.getAmount() == 5 || oneByStr.getAmount() == 6) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "请用首饰GM获取首饰！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true;
				}
				GameUtil.huodezhuangbei(chara, oneByStr, 1, 1);
				return true;
			}
			if (msg.startsWith("首饰(") && msg.endsWith(")")) {
				String ssName = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(ssName);
				if (oneByStr == null) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "请输入正确的首饰名！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true;
				}
				GameUtil.huodezhuangbei(chara, oneByStr, 0, 1);
				return true;
			}
			if (msg.startsWith("道行(") && msg.endsWith(")")) {
				long daoHang = Long.valueOf(msg.substring(msg.indexOf("(") + 1, msg.indexOf(")")));
				if (daoHang > Integer.MAX_VALUE) {
					Vo_20481_0 vo_20481_0 = new Vo_20481_0();
					vo_20481_0.msg = "道行值过大，操作失败 ！";
					vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
					return true;
				}
				GameUtil.adddaohang(chara, (int) daoHang * 1440, "GM指令");
				ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_5);
				return true;
			}
			if (msg.startsWith("野生(") && msg.endsWith(")") || msg.startsWith("变异(") && msg.endsWith(")")
					|| (msg.startsWith("神兽(") && msg.endsWith(")")) || (msg.startsWith("守护(") && msg.endsWith(")"))
					|| (msg.startsWith("鬼卒(") && msg.endsWith(")")) || (msg.startsWith("鬼将(") && msg.endsWith(")"))) {
				int type = 1;
				if (msg.startsWith("野生(") && msg.endsWith(")"))
					type = 1;
				if (msg.startsWith("变异(") && msg.endsWith(")"))
					type = 3;
				if (msg.startsWith("神兽(") && msg.endsWith(")"))
					type = 4;
				if (msg.startsWith("守护(") && msg.endsWith(")"))
					type = 5;
				if (msg.startsWith("鬼卒(") && msg.endsWith(")"))
					type = 6;
				if (msg.startsWith("鬼将(") && msg.endsWith(")"))
					type = 7;
				if (msg.startsWith("鬼仙(") && msg.endsWith(")"))
					type = 8;
				String chongwu = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				GameUtil.huodechongwu(chara, chongwu, type, "GM指令");
				ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_5);
				return true;
			}

			if (msg.startsWith("宝宝(") && msg.endsWith(")")) {
				String chongwu = msg.substring(msg.indexOf("(") + 1, msg.indexOf(")"));
				GameUtil.huodemanchongwu(chara, chongwu, 2, "GM指令");
				ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_5);
				return true;
			}
		}
		return false;
	}

	public static boolean addSpeedHandler(GameObjectChar gameObjectChar) {
		int addSpeedCount = GameConfig.config.getBaseConfig().getAddSpeedCount(); 
		// 大于0的情况才是开启加速器检测
		if (addSpeedCount > 0) {
			// 累计次数
			int commonSpeedNum = gameObjectChar.commonSpeedNum.getAndIncrement();
			if (commonSpeedNum > addSpeedCount) {
				GameObjectChar.send(new MSG_KICK_OFF(), "系统检测到你开了#R加速器#n被强制下线！#R如若检测次数超限,将面临永久封号！");
				GameObjectChar.getGameObjectChar().offline();
				// 恢复初始化
				gameObjectChar.commonSpeedNum.set(0);
//				int incr = GameData.that.redisUtils.getIncr2("SPEED_BLACK_" + gameObjectChar.chara.id);
//				// 如果被检测次数超过3次直接封号处理
//				if (incr != -1 && incr > 5 && gameObjectChar.privilege == 0) {
//					BlackListService blackListService = GameData.that.blackListService;
//					// 查询账号信息
//					Accounts account = GameData.that.baseAccountsService
//							.findById(gameObjectChar.characters.getAccountId());
//					if (account != null) {
//						// 设备拉黑
//						BlackList b = new BlackList();
//						if (account.getMac() != null) {
//							b.setData(account.getMac());
//							b.setAddTime(new Date());
//							blackListService.insertSelective(b);
//						}
//						// ip拉黑
//						if (account.getLastLoginIp() != null) {
//							b = new BlackList();
//							b.setData(account.getLastLoginIp());
//							b.setAddTime(new Date());
//							blackListService.insertSelective(b);
//						}
//						// 注册ip
//						if (account.getRegisterIp() != null) {
//							b = new BlackList();
//							b.setData(account.getRegisterIp());
//							b.setAddTime(new Date());
//							blackListService.insertSelective(b);
//						}
//						Characters characters = new Characters();
//						characters.setBlock(1);
//						characters.setUpdateTime(new Date());
//						characters.setId(gameObjectChar.characters.getId());
//						GameData.that.baseCharactersService.updateById(characters);
//						GameData.that.redisUtils.delete("SPEED_BLACK_" + gameObjectChar.chara.id);
//					}
//				}
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 设置战斗信息
	 * @param gameObjectChar
	 */
	public static void fightCmdInfo(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		List<Vo_FIGHT_CMD_INFO> info = new ArrayList<>();
		//人物战斗信息
		Vo_FIGHT_CMD_INFO vo_36889_0 = new Vo_FIGHT_CMD_INFO();
		vo_36889_0.id = chara.id;
		vo_36889_0.auto_select = chara.autofight_supplement;
		vo_36889_0.multi_index = 0;
		vo_36889_0.action = chara.autofight_skillaction;
		vo_36889_0.para = chara.autofight_skillno;
		vo_36889_0.multi_count = 0;
		//参战宠物信息
		Vo_FIGHT_CMD_INFO petFightInfo = new Vo_FIGHT_CMD_INFO();
		for(Petbeibao pet:chara.pets) {
			if(pet.id == chara.chongwuchanzhanId) {
				petFightInfo.id = pet.id;
				petFightInfo.auto_select = pet.autofight_supplement;
				petFightInfo.action = pet.autofight_skillaction;
				petFightInfo.para = pet.autofight_skillno;
			}
		}
		info.add(petFightInfo);
		info.add(vo_36889_0);
		gameObjectChar.sendOne(new MSG_FIGHT_CMD_INFO(), info);
	}
	
	/**
	 * 设置战斗信息
	 * @param gameObjectChar 玩家
	 * @param pet 宠物
	 */
	public static void fightCmdInfo(GameObjectChar gameObjectChar, Petbeibao pet) {
		Chara chara = gameObjectChar.chara;
		List<Vo_FIGHT_CMD_INFO> info = new ArrayList<>();
		//人物战斗信息
		Vo_FIGHT_CMD_INFO vo_36889_0 = new Vo_FIGHT_CMD_INFO();
		vo_36889_0.id = chara.id;
		vo_36889_0.auto_select = chara.autofight_supplement;
		vo_36889_0.multi_index = 0;
		vo_36889_0.action = chara.autofight_skillaction;
		vo_36889_0.para = chara.autofight_skillno;
		vo_36889_0.multi_count = 0;
		//参战宠物信息
		Vo_FIGHT_CMD_INFO petFightInfo = new Vo_FIGHT_CMD_INFO();
		petFightInfo.id = pet.id;
		petFightInfo.auto_select = pet.autofight_supplement;
		petFightInfo.action = pet.autofight_skillaction;
		petFightInfo.para = pet.autofight_skillno;
		info.add(petFightInfo);
		info.add(vo_36889_0);
		gameObjectChar.sendOne(new MSG_FIGHT_CMD_INFO(), info);
	}
	
	/**
	 * 是否可以赠送
	 * @param chara
	 * @return
	 */
	public static JSONObject isGivingItem(Chara chara, Goods goods) {
		ConfigInfo config = GameData.that.configInfoService.getOneByKeyName("giving_config");
		String data = config.getData();
		JSONObject givingConfig = JSONObject.parseObject(data);
		int status = givingConfig.getIntValue("status");
		//判断是否开启
		if(status == 0) {
			GameUtil.sendMeTips("赠送暂未开启");
			return null;
		}
		//该物品是否允许赠送
		if(goods != null) {
			String rejectText = givingConfig.getString("rejectText");
			if(!StringUtils.isNullOrEmpty(rejectText)) {
				for(String re:rejectText.split("、")) {
					if(re.equals(goods.goodsInfo.str)) {
						GameUtil.sendMeTips("该物品不允许赠送");
						return null;
					}
				}
			}
		}
		return givingConfig;
	}
	
	/**
	 * 更换称谓
	 * @param chara
	 * @param selectTitle
	 * @param isReplace 是否覆盖 
	 */
	public static void changeTitle(GameObjectChar gameObjectChar, String selectTitle) {
		Chara chara = gameObjectChar.chara;
		String string = chara.chenghao.get(selectTitle);
		// 如果相同就直接返回
		if (chara.chenhao.equals(selectTitle)) {
			return;
		}
		// // 获取称号属性.
		// ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
		// // 上个称谓属性
		// Chengwei prefixChengwei = cs.getChengweiByName(chara.chenhao);
		// GameCommonUtil.computeDeltaChengwei(chara, prefixChengwei, true);
		if (string != null) {
			chara.chenhao = string;
		} else {
			chara.chenhao = "";
		}
		// //新更换后的属性称谓你
		// Chengwei newChengwei = cs.getChengweiByName(string);
		// //重新计算角色信息
		// GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
		// //重新计算伤害
		 GameUtil.a65511(gameObjectChar);
	}
	
	/**
	 * 观战
	 * @param charas 观战玩家
	 * @param fc 要观战玩家的战斗容器
	 * @param lookCharaId 要观战玩家的id
	 */
	public static void lookFight(List<GameObjectChar> charas, FightContainer fc, int lookCharaId) {
		if(fc == null) {
			//为空设置为不观战状态
			for(GameObjectChar gameObjectChar:charas) {
				gameObjectChar.isLook = 0;
				gameObjectChar.lookCharId = 0;
			}
			return;
		}
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("auto_fight", 1);
		for(GameObjectChar gameObjectChar:charas) {
			gameObjectChar.isLook = 1;
			gameObjectChar.lookCharId = lookCharaId;
			fc.lookCharas.put(gameObjectChar.chara.id,gameObjectChar);
			//让他变成自动状态
			gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(gameObjectChar.chara.id, dataMap));
			//开始观战
			gameObjectChar.sendOne(new MSG_LC_START_LOOKON(), new Integer[] {1,1});
			List<FightObject> fightTeam = FightManager.getFightTeam(fc, lookCharaId).fightObjectList;
			List<FightObject> fightTeamDM = FightManager.getFightTeamDM(fc, lookCharaId).fightObjectList;
			List<Vo_ADD_FRIEND_OPPONENT> friends = new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
			for (FightObject fightObject : fightTeam) {
				if(fightObject.isDead()) {
					if(fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
						continue;
					}
				}
				Vo_ADD_FRIEND_OPPONENT vo_65019_0 = new Vo_ADD_FRIEND_OPPONENT();
				vo_65019_0.id = fightObject.fid;
				vo_65019_0.leader = fightObject.leader;
				vo_65019_0.weapon_icon = fightObject.weapon_icon;
				vo_65019_0.pos = fightObject.pos;
				vo_65019_0.rank = fightObject.rank;
				vo_65019_0.vip_type = 0;
				vo_65019_0.str = fightObject.str;
				vo_65019_0.type = fightObject.org_icon;
				vo_65019_0.durability = fightObject.durability;
				vo_65019_0.req_level = 0;
				vo_65019_0.upgrade_level = fightObject.upgrade_level;
				vo_65019_0.upgrade_type = fightObject.upgrade_type;
				vo_65019_0.dex = fightObject.max_mofa;
				vo_65019_0.max_mana = fightObject.max_mofa;
				vo_65019_0.max_life = fightObject.max_shengming;
				vo_65019_0.def = fightObject.shengming;
				vo_65019_0.org_icon = fightObject.org_icon;
				vo_65019_0.suit_icon = fightObject.suit_icon;
				vo_65019_0.suit_light_effect = fightObject.suit_light_effect;
				vo_65019_0.special_icon = fightObject.special_icon;
				vo_65019_0.customIcon = fightObject.customIcon;
				vo_65019_0.zhenlingLevel = fightObject.zhenlingLevel;
				vo_65019_0.zhenlingType = fightObject.zhenlingType;
				friends.add(vo_65019_0);
			}
			
			List<Vo_ADD_FRIEND_OPPONENT> opponents= new ArrayList<Vo_ADD_FRIEND_OPPONENT>();
			for (FightObject fightObject : fightTeamDM) {
				if(fightObject.isDead()) {
					if(fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
						continue;
					}
				}
				Vo_ADD_FRIEND_OPPONENT vo_65017_0 = new Vo_ADD_FRIEND_OPPONENT();
				vo_65017_0.id = fightObject.fid;
				vo_65017_0.leader = fightObject.leader;
				vo_65017_0.weapon_icon = fightObject.weapon_icon;
				vo_65017_0.pos = fightObject.pos;
				vo_65017_0.rank = 0;
				vo_65017_0.vip_type = 0;
				vo_65017_0.str = fightObject.str;
				vo_65017_0.type = fightObject.org_icon;
				vo_65017_0.durability = 2;
				vo_65017_0.req_level = 0;
				vo_65017_0.upgrade_level = fightObject.upgrade_level;
				vo_65017_0.upgrade_type = fightObject.upgrade_type;
				vo_65017_0.dex = fightObject.mofa;
				vo_65017_0.max_mana = fightObject.max_mofa;
				vo_65017_0.max_life = fightObject.max_shengming;
				vo_65017_0.def = fightObject.shengming;
				vo_65017_0.org_icon = fightObject.org_icon;
				vo_65017_0.suit_icon = fightObject.suit_icon;
				vo_65017_0.suit_light_effect = fightObject.suit_light_effect;
				vo_65017_0.special_icon = fightObject.special_icon;
				vo_65017_0.customIcon = fightObject.customIcon;
				vo_65017_0.zhenlingLevel = fightObject.zhenlingLevel;
				vo_65017_0.zhenlingType = fightObject.zhenlingType;
				opponents.add(vo_65017_0);
			}
			gameObjectChar.sendOne(new MSG_C_FRIENDS(), friends);
			gameObjectChar.sendOne(new MSG_C_OPPONENTS(), opponents);
			//友方状态
			for (FightObject fightObject : fightTeam) {
				//如果死亡了
				if(fightObject.isDead()) {
					//除了type为1,其余死亡之后都要消失
					if(fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
						ArrayList<Integer> objects = new ArrayList<Integer>();
						objects.add(fightObject.fid);
						objects.add(0);
						GameObjectChar.send(new M64981_Fight_Blood(), objects);
						
						Vo_7653_0 vo_7653_0 = new Vo_7653_0();
						vo_7653_0.id = fightObject.fid;
						GameObjectChar.send(new M7653_0(), vo_7653_0);
					}else {
						Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
						vo_19959_0.round = fc.round;
						vo_19959_0.aid = fightObject.fid;
						vo_19959_0.action = 40;
						vo_19959_0.vid = 0;
						vo_19959_0.para = 0;
						GameObjectChar.send(new MSG_C_ACTION(), vo_19959_0);
						
						Vo_C_CHAR_DIED vo_7669_0 = new Vo_C_CHAR_DIED();
						vo_7669_0.id = fightObject.fid;
						vo_7669_0.damage_type = 40;
						GameObjectChar.send(new MSG_C_CHAR_DIED(), vo_7669_0);
						Vo_C_END_ACTION vo_7655_0 = new Vo_C_END_ACTION();
						vo_7655_0.id = fightObject.fid;
						GameObjectChar.send(new MSG_C_END_ACTION(), vo_7655_0);
					}
				}
			}
			//敌方状态
			for (FightObject fightObject : fightTeamDM) {
				//如果死亡了
				if(fightObject.isDead()) {
					//除了type为1,其余死亡之后都要消失
					if(fightObject.type > 1 && fightObject.isGuaiWuHide == 0) {
						ArrayList<Integer> objects = new ArrayList<Integer>();
						objects.add(fightObject.fid);
						objects.add(0);
						GameObjectChar.send(new M64981_Fight_Blood(), objects);
						
						Vo_7653_0 vo_7653_0 = new Vo_7653_0();
						vo_7653_0.id = fightObject.fid;
						GameObjectChar.send(new M7653_0(), vo_7653_0);
					}else {
						Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
						vo_19959_0.round = fc.round;
						vo_19959_0.aid = fightObject.fid;
						vo_19959_0.action = 40;
						vo_19959_0.vid = 0;
						vo_19959_0.para = 0;
						GameObjectChar.send(new MSG_C_ACTION(), vo_19959_0);
						
						Vo_C_CHAR_DIED vo_7669_0 = new Vo_C_CHAR_DIED();
						vo_7669_0.id = fightObject.fid;
						vo_7669_0.damage_type = 40;
						GameObjectChar.send(new MSG_C_CHAR_DIED(), vo_7669_0);
						Vo_C_END_ACTION vo_7655_0 = new Vo_C_END_ACTION();
						vo_7655_0.id = fightObject.fid;
						GameObjectChar.send(new MSG_C_END_ACTION(), vo_7655_0);
					}
				}
			}
			for (FightObject fightObject : fightTeam) {
				//如果是怪物已经死亡的话则不添加
				if(fightObject.type == 4 && fightObject.isDead()) {
					//如果是怪物死亡并且要消失的才继续
					if(fightObject.isGuaiWuHide == 0) {
						continue;
					}
				}else if(fightObject.type == 1 && fightObject.isGuaiWuHide == 1 && fightObject.isDead()) {
					continue;
				}else if(fightObject.type == 2 && fightObject.isDead()) {
					continue;
				}
				Vo_11757_0 friendStatus = new Vo_11757_0();
				friendStatus.id = fightObject.fid;
				List<Integer> buffState = fightObject.getBuffState();
				int value = 0;
				for(Integer i:buffState) {
					value+=i;
				}
				friendStatus.list.add(value);
				friendStatus.list.add(32);
				//队友状态
				gameObjectChar.sendOne(new M11757_0(), friendStatus);
			}
		
			for (FightObject fightObject2 : fightTeamDM) {
				//如果是怪物已经死亡的话则不添加
				if(fightObject2.type == 4 && fightObject2.isDead()) {
					if(fightObject2.isGuaiWuHide == 0) {
						continue;
					}
				}else if(fightObject2.type == 1 && fightObject2.isGuaiWuHide == 1 && fightObject2.isDead()) {
					continue;
				}else if(fightObject2.type == 2 && fightObject2.isDead()) {
					continue;
				}
				Vo_11757_0 friendStatus = new Vo_11757_0();
				friendStatus.id = fightObject2.fid;
				List<Integer> buffState = fightObject2.getBuffState();
				int value = 0;
				for(Integer i:buffState) {
					value+=i;
				}
				friendStatus.list.add(value);
				friendStatus.list.add(32);
				//队友状态
				gameObjectChar.sendOne(new MSG_LC_UPDATE_STATUS(), friendStatus);
			}
			for (FightObject fightObject3 : fightTeam) {
				if (fightObject3.godbook != 0) {
					Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
					vo_12025_0.id = fightObject3.fid;
					vo_12025_0.effect_no = fightObject3.godbook;
					gameObjectChar.sendOne(new MSG_GODBOOK_EFFECT_NORMAL(), vo_12025_0);
				}
			}
			
			for (FightObject fightObject3 : fightTeamDM) {
				if (fightObject3.godbook != 0) {
					Vo_GODBOOK_EFFECT vo_12025_0 = new Vo_GODBOOK_EFFECT();
					vo_12025_0.id = fightObject3.fid;
					vo_12025_0.effect_no = fightObject3.godbook;
					gameObjectChar.sendOne(new MSG_GODBOOK_EFFECT_NORMAL(), vo_12025_0);
				}
			}
		}
	}
	
	/**
	 * 解析成固定格式
	 * @param rewardStr 奖励信息
	 * @return
	 */
	public static List<String[]> parseRewardStr(String rewardStr) {
		String[] reward = rewardStr.split("#I");
		List<String[]> results = new ArrayList<>();
		for(String re:reward) {
			if(!re.isEmpty()) {
				try {
					String[] reArr = re.split("\\|");
					String type = reArr[0];
					if ("物品".equals(type)) {
						results.add(new String[] { type, reArr[1].split("#r")[0], reArr[1].split("#r")[1]});
					} else if ("宠物".equals(type)) {
						String nameAndType = reArr[1].split("\\$")[0];
						String[] str = nameAndType.split("\\(");
						String name = str[0]; // 宠物名字
						String petType = str[1].replace(")", ""); // 宠物名字
						results.add(new String[] {petType, name});
					} else if ("首饰".equals(type)) {
						// #I首饰|七星手链$指定$35#I
						results.add(new String[] { "首饰", reArr[1].split("\\$")[0],  reArr[1]});
					} else if ("装备".equals(type)) {
						String[] equipType = reArr[1].split("\\$");
						List<String> lists = new ArrayList<>();
						lists.add(type);
						for(String s:equipType) {
							lists.add(s);
						}
						results.add(lists.toArray(new String[lists.size()]));
					}else if("经验".equals(type) || "潜能".equals(type) || "道行".equals(type) || "积分".equals(type) || "金元宝".equals(type)
							|| "银元宝".equals(type) || "抽奖".equals(type)) {
						results.add(new String[] {type, reArr[1]});
					}else if("法宝".equals(type)) {
						String[] fabaoStr = reArr[1].split("\\$");
						results.add(new String[] {type, fabaoStr[0],fabaoStr[1],fabaoStr[2]});
					}
				} catch (Exception e) {
					log.error("解析奖励信息出错{}",e);
				}
			}
		}
		return results;
	}
	
	/**
	 * 获得抽奖
	 * @param gameObjectChar
	 * @param strings 解析后的
	 * @param origin 原始
	 * @param typeName 类型
	 */
	public static void getReward(GameObjectChar gameObjectChar, String[] strings, String typeName) {
		if(strings != null && strings.length>1) {
			Chara chara = gameObjectChar.chara;
			if (strings[0].equals("变异")) {
				try {
					Pet pet = GameData.that.basePetService.findOneByName(strings[1]);
					if(pet != null) {
						Petbeibao petbeibao = new Petbeibao();
						petbeibao.PetCreate(pet, chara, 0, 3, typeName);
						List<Petbeibao> list = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao);
						list.add(petbeibao);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					}
				} catch (Exception e) {
					log.error("{}",e);
				}
			}
			else if (strings[0].equals("神兽")) {
				try {
					Pet pet = GameData.that.basePetService.findOneByName(strings[1]);
					if(pet != null) {
						Petbeibao petbeibao = new Petbeibao();
						petbeibao.PetCreate(pet, chara, 0, 4, typeName);
						List<Petbeibao> list = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao);
						list.add(petbeibao);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list);
					}
				} catch (Exception e) {
					log.error("{}",e);
				}
			}
			else if (strings[0].equals("精怪")) {
				try {
					int jieshu = GameUtil.stageMounts(strings[0]);
					Pet pet2 = GameData.that.basePetService.findOneByName(strings[1]);
					if(pet2 != null) {
						Petbeibao petbeibao2 = new Petbeibao();
						petbeibao2.PetCreate(pet2, chara, 0, 2, typeName);
						List<Petbeibao> list2 = new ArrayList<Petbeibao>();
						chara.pets.add(petbeibao2);
						list2.add(petbeibao2);
						petbeibao2.petShuXing.get(0).enchant_nimbus = 0;
						petbeibao2.petShuXing.get(0).max_enchant_nimbus = 0;
						petbeibao2.petShuXing.get(0).suit_light_effect = 1;
						petbeibao2.petShuXing.get(0).hide_mount = jieshu;
						PetShuXing shuXing = new PetShuXing();
						shuXing.no = 23;
						shuXing.type1 = 2;
						shuXing.accurate = 4 * (jieshu - 1);
						shuXing.mana = 4 * (jieshu - 1);
						shuXing.wiz = 3 * (jieshu - 1);
						shuXing.all_polar = 0;
						shuXing.upgrade_magic = 0;
						shuXing.upgrade_total = 0;
						petbeibao2.petShuXing.add(shuXing);
						GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
					}
				} catch (Exception e) {
					log.error("{}",e);
				}
			}
			else if (strings[0].equals("物品")) {
				StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(strings[1]);
				GameUtil.huodedaoju(gameObjectChar, info, Integer.valueOf(strings[2]));
				GameUtil.sendMeTips("获得物品#R"+strings[1]);
				Vo_40964_0 vo_40964_0 = new Vo_40964_0();
				vo_40964_0.type = 1;
				vo_40964_0.name = strings[1];
				vo_40964_0.param = "20691134";
				vo_40964_0.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_0, chara.id);
			}
			else if (strings[0].equals("首饰")) {
				/**
				 * #I首饰|随机35级首饰$随机$35#I #I首饰|随机满属性$随机满属性$35#I #I首饰|随机满属性$随机满属性#I
				 * #I首饰|七星手链$指定$35#I #I首饰|随机首饰$指定$35#I
				 */
				String[] split = strings[2].split("\\$");
				String name = split[0];
				String type = "";
				if (split.length > 1) {
					type = split[1];
					if (Utils.isNumber(type)) {
						// 不是数字的时候表示随机
						type = "";
					}
				}
				// 随机首饰
				if (StringUtils.isNullOrEmpty(type) || type.startsWith("随机属性")) {
					// 解析出指定什么
					GameCommonUtil.randomShouShiAttri(chara, name);
					GameCommonUtil.sendTips("获得首饰#R"+name, gameObjectChar);
				} else if (type.startsWith("满属性")) {
					// 解析出指定什么
					GameCommonUtil.randomShouShiAllAttri(chara, name);
					GameCommonUtil.sendTips("获得首饰#R"+name, gameObjectChar);
				} else if (type.equals("所有相五")) {
					GameUtil.jifendengjishoushi(chara, new String[] { name });
					GameCommonUtil.sendTips("获得首饰#R"+name, gameObjectChar);
				}
			}
			else if (strings[0].equals("装备")) {
				//[装备, 暴力70整套, 整套, 力, 70, 3]
				if(strings.length>4) {
					// 指定装备类型,是整套还是特定
					String equipType = strings[2];
					// 指定装备属性,是力还是法的
					String polar = strings[3];
					if (!Utils.isNumber(strings[4])) {
						GameUtil.sendMeTips("积分装备格式错误，请联系GM！");
						return;
					}
					// 等级
					Integer level = Integer.valueOf(strings[4]);
					// 改造等级
					Integer rebuildLevel = Integer.valueOf(strings[5]);
					if ("整套".equals(equipType)) {
						// 获取整套
						if ("力".equals(polar)) {
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipEarthWuQi(chara, level, rebuildLevel),
									gameObjectChar);
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 2),
									gameObjectChar);
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 3),
									gameObjectChar);
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 10),
									gameObjectChar);
							GameUtil.sendMeTips("恭喜你获得整套#R" + level + "#n级极品力套");
						} else if ("法".equals(polar)) {
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipMetalWuQi(chara, level, rebuildLevel),
									gameObjectChar);
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 2),
									gameObjectChar);
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 3),
									gameObjectChar);
							GameCommonUtil.addGoodsToBackpack(
									GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 10),
									gameObjectChar);
							GameUtil.sendMeTips("恭喜你获得整套#R" + level + "#n级极品法套");
						}
					} else {
						if ("武器".equals(equipType)) {
							if ("力".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipEarthWuQi(chara, level, rebuildLevel),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力武器");
							} else if ("法".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipMetalWuQi(chara, level, rebuildLevel),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法武器");
							}
						} else if ("帽子".equals(equipType)) {
							if ("力".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 2),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力帽子");
							} else if ("法".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 2),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法帽子");
							}
						} else if ("衣服".equals(equipType)) {
							if ("力".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 3),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力衣服");
							} else if ("法".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 3),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法衣服");
							}
						} else if ("鞋子".equals(equipType)) {
							if ("力".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 10),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力鞋子");
							} else if ("法".equals(polar)) {
								GameCommonUtil.addGoodsToBackpack(
										GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 10),
										gameObjectChar);
								GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法鞋子");
							}
						}
					}
				
				}
			}
			else if("经验".equals(strings[0])) {
				GameUtil.huodejingyan(chara, Integer.valueOf(strings[1]),typeName);
			}
			else if("道行".equals(strings[0])) {
				GameUtil.adddaohang(chara, Integer.valueOf(strings[1])*1440,typeName);
			}
			else if("潜能".equals(strings[0])) {
				GameUtil.addQianNeng(chara, Integer.valueOf(strings[1]), typeName);
			}
			else if("积分".equals(strings[0])) {
				GameUtil.addchargeScore(gameObjectChar, Integer.valueOf(strings[1]), typeName);
			}else if("金元宝".equals(strings[0])) {
				GameUtil.addJinYuanBao(gameObjectChar, Integer.valueOf(strings[1]), typeName);
			}else if("银元宝".equals(strings[0])) {
				GameUtil.addYinYuanBao(gameObjectChar, Integer.valueOf(strings[1]), typeName);
			}else if("抽奖".equals(strings[0])) {
				GameUtil.addLotteryTimes(gameObjectChar, Integer.valueOf(strings[1]), typeName);
			}else if("法宝".equals(strings[0])) {
				String name = strings[1];
				int fabaoLevel = Integer.valueOf(strings[2]);
				if(fabaoLevel<=0) {
					fabaoLevel = 1;
				}else if(fabaoLevel>24) {
					fabaoLevel = 24;
				}
				//相性
				int polar = Integer.valueOf(strings[3]);
				if(polar <= 0 || polar>5) {
					//随机相性
					polar = ThreadLocalRandom.current().nextInt(4)+1;
				}
				GameUtil.jifenhuodefabao(chara, name, fabaoLevel, typeName, polar);
			} else if ("魂器".indexOf(strings[0]) != -1) {
				ZhuangbeiInfo findByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(strings[1]);
				if (findByStr != null) {
					GameCommonUtil.integral_horcrux(chara, strings[1], 75, null);
					GameUtil.notifyPrompt(chara.id, "获得#Y" + strings[1]);
				}
			} 
		}
	}
	
	public static String getEquipTypeName(int equipType) {
		String type = null;
		
		switch (equipType) {
		case 0:
			type = "道具";
			break;
		case 1: case 2: case 3: case 10:
			type = "装备";
			break;
		case 4: case 5: case 6:
			type = "首饰";
			break;
		case 8:
			type = "魂器";
			break;
		case 9:
			type = "法宝";
			break;
		case 12:
			type = "妖石";
			break;
		case 16:
			type = "时装套装";
			break;
		case 17:
			type = "时装首饰";
			break;
		case 18:
			type = "自定义部件";
			break;
		case 20:
			type = "队标";
			break;
		case 37:
			type = "跟宠";
			break;
		}
		return type;
	}
	
	
	
	public static void setGoodsDefaultValue(Chara chara, boolean isOnline) {
		if(isOnline) {
//			for(Goods goods:chara.cangku) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.cardStore) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.backpack) {
//				if(goods.pos == 48) {
//					System.out.println();
//				}
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.otherGoods) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.texiao) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.genchong) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.customShizhuang) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
//			for(Goods goods:chara.tyzqStore) {
//				GameCommonUtil.setGoodsDefaultValue(goods, true);
//			}
		}else {
			for(Goods goods:chara.cangku) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.cardStore) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.backpack) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.otherGoods) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.texiao) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.genchong) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.customShizhuang) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
			for(Goods goods:chara.tyzqStore) {
				GameCommonUtil.setGoodsDefaultValue(goods, false);
			}
		}
	}
	
	/**
	 * 批量设置商品默认值
	 * @param goodsList
	 * @param isOnline
	 */
	public static void setGoodsDefaultValue(List<Goods> goodsList, boolean isOnline) {
		for(Goods goods:goodsList) {
			setGoodsDefaultValue(goods, isOnline);
		}
	}
	
	/**
	 * 设置商品默认值
	 * @param goods 
	 * @param isOnline
	 */
	public static void setGoodsDefaultValue(Goods goods, boolean isOnline) {
		//上线
		if(isOnline) {
			charaOnline(goods.goodsInfo);
			charaOnline(goods.goodsLanSe);
			charaOnline(goods.goodsFenSe);
			charaOnline(goods.goodsHuangSe);
			charaOnline(goods.goodsLvSe);
			charaOnline(goods.goodsGaiZao);
			charaOnline(goods.goodsGaiZaoGongMing);
			charaOnline(goods.goodsGaiZaoGongMingChengGong);
			charaOnline(goods.goodsLvSeGongMing);
		}else {
			charaOffline(goods.goodsInfo);
			charaOffline(goods.goodsLanSe);
			charaOffline(goods.goodsFenSe);
			charaOffline(goods.goodsHuangSe);
			charaOffline(goods.goodsLvSe);
			charaOffline(goods.goodsGaiZao);
			charaOffline(goods.goodsGaiZaoGongMing);
			charaOffline(goods.goodsGaiZaoGongMingChengGong);
			charaOffline(goods.goodsLvSeGongMing);
		}
	}
	
	/**
	 * 设置字段值
	 * @param object
	 */
	private static void charaOnline(Object object) {
		try {
			if(object!= null) {
				for(Field f:object.getClass().getFields()) {
					//如果为空直接设置为0
					Object v = f.get(object);
					if(v == null) {
						if(f.getType() == Integer.class) {
							f.set(object, 0);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("{}",e);
		}
	}
	
	/**
	 * 角色下线
	 * @param object
	 */
	private static void charaOffline(Object object) {
		try {
			if(object!= null) {
				for(Field f:object.getClass().getFields()) {
					Object v = f.get(object);
					if(v != null) {
						if(f.getType() == Integer.class) {
							if(v.equals(0)) {
								f.set(object, null);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("{}",e);
		}
	}
	
	/**
	 * 自动喊话
	 * @param id id
	 * @param content 内容
	 * @param channel 频道
	 * @return
	 */
	public static Vo_C_SET_CUSTOM_MSG getAutoTalkObj(int id, String content, int channel) {
		Vo_C_SET_CUSTOM_MSG msg = new Vo_C_SET_CUSTOM_MSG();
		msg.setId(id);
		msg.setMsg(content);
		msg.setServerName(GameConfig.lineName);
		msg.setShowTime(2);
		msg.setChannel(channel);
		msg.setVipType(0);
		return msg;
	}
	
	/**
	 * 初始化飞行器
	 * @param gameObjectChar 玩家
	 * @param goodss 飞行器
	 */
	public static void flyInit(GameObjectChar gameObjectChar, Goods... goodss) {
		Chara oChara = gameObjectChar.chara;
		if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, gameObjectChar.chara)) {
			oChara = gameObjectChar.gameTeam.duiwu.get(0);
			for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
				//这个必须清空
				teamChara.genchong_icon = 0;
				GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
				if(teamGameObjectChar != null) {
					//初始化清空一次
					teamGameObjectChar.moveIds.clear();
				}
			}
		} 
		Goods flyGoods = null;
		if(goodss != null && goodss.length>0) {
			flyGoods =  goodss[0];
		}else {
			for (Goods goods : oChara.otherGoods) {
				if (goods.pos == 40) {
					flyGoods = goods;
					break;
				}
			}
		}
		if(flyGoods != null && flyGoods.pos == 40) {
			int flyType = FlyType.getKeyByValue(flyGoods.goodsInfo.str);
			gameObjectChar.flyType = flyType;
			gameObjectChar.moveType = 1;
			// 多人飞行器
			if (flyType > 2) {
				// fly_type FLY_TYPE = {FLY_TYPE_LOTUS = 1, FLY_TYPE_YUTIANSUO = 2}
				if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, gameObjectChar.chara)) {
					for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
						GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
						teamGameObjectChar.moveType = 1;
						//跟随队长飞行类型
						teamGameObjectChar.flyType = flyType;
						for(Chara teamC:teamGameObjectChar.gameTeam.duiwu) {
							teamGameObjectChar.moveIds.add(teamC.id);
						}
					}
				} else {
					gameObjectChar.moveIds.clear();
					gameObjectChar.moveIds.add(oChara.id);
				}
			}else {
				//如果是单人话则默认使用自己的飞行器
				if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, gameObjectChar.chara)) {
					for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
						GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
						teamGameObjectChar.moveIds.clear();
						teamGameObjectChar.moveType = 1;
						//其他队员
						if(teamChara.id != oChara.id) {
							Goods teamFlyGoods = null;
							for (Goods goods : teamChara.otherGoods) {
								//如果带了飞行器
								if (goods.pos == 40) {
									teamFlyGoods =  goods;
									break;
								}
							}
							//自己带了飞行器那就使用自己的
							if(teamFlyGoods != null) {
								int teamFlyType = FlyType.getKeyByValue(teamFlyGoods.goodsInfo.str);
								//自己携带的是单人飞行
								if (teamFlyType <=2) {
									teamGameObjectChar.flyType = teamFlyType;
								}else {
									//使用队长的
									teamGameObjectChar.flyType = flyType;
								}
							}else {
								//使用队长的
								teamGameObjectChar.flyType = flyType;
							}
						}
					}
				}
			}
		}else {
			gameObjectChar.moveType = 0;
			gameObjectChar.flyType = 0;
			gameObjectChar.moveIds.clear();
		}
	}
	/**
	 * 是否有队伍,false没有队伍,true有队伍
	 */
	public static boolean isHasGameTeam(final GameTeam gameTeam) {
		return isHasGameTeam(gameTeam, null);
	}

	public static boolean isHasGameTeam(final GameTeam gameTeam, final Chara thisChara) {
		boolean flag = gameTeam != null && gameTeam.duiwu != null && !gameTeam.duiwu.isEmpty() && gameTeam.duiwu.size() != 0;
		//如果没有队伍
		//如果有队伍
		if (flag) {
			//判断自己是否在队伍中
			for (final Chara chara : gameTeam.duiwu) {
				if (thisChara == null) {
					continue;
				}
				if (chara.id == thisChara.id) {
					flag = true;
					break;
				}
				flag = false;
			}
		}
		return flag;
	}
	/**
	 * 根据位置获取背包商品
	 * @param chara 玩家
	 * @param pos 位置
	 * @return
	 */
	public static Goods getBackpackGoodsByPos(Chara chara, int pos) {
		for(Goods goods:chara.backpack) {
			if(goods.pos == pos) {
				return goods;
			}
		}
		return null;
	}
	
	/**
	 * 根据名称获取背包商品
	 * @param chara 玩家
	 * @param name 名称
	 * @return
	 */
	public static Goods getBackpackGoodsByName(Chara chara, String name) {
		for(Goods goods:chara.backpack) {
			if(goods.goodsInfo.str.equals(name)) {
				return goods;
			}
		}
		return null;
	}
	
	public static void main(String[] args) {

		System.out.println(DateUtil.format(DateUtil.getFetureDate(30), "yyyy-MM-dd"));
		System.out.println(DateUtil.format(DateUtil.getPastDate(30), "yyyy-MM-dd"));

	}
}