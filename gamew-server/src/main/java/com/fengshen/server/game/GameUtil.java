package com.fengshen.server.game;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.data.write.*;
import com.fengshen.server.domain.*;
import com.fengshen.server.netty.BaseWrite;
import org.apache.commons.collections.MapUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.Chengwei;
import com.fengshen.db.domain.Experience;
import com.fengshen.db.domain.ExperienceTreasure;
import com.fengshen.db.domain.GroceriesShop;
import com.fengshen.db.domain.Map;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.NpcDialogue;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.Renwu;
import com.fengshen.db.domain.RenwuMonster;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.constant.TitleConst;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.ChangeCardAttr;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.game.NoviceGiftBagUtils;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.game.SuitEffectUtils;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.data.vo.chat.Vo_MESSAGE;
import com.fengshen.server.data.vo.fight.Vo_ADD_FRIEND_OPPONENT;
import com.fengshen.server.data.vo.friend.Vo_FRIEND_ADD_CHAR;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_TASK_INFO;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.vo.user.DAILY_STATS_INFO;
import com.fengshen.server.data.vo.user.Vo_OPEN_WELFARE;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.appear.MSG_APPEAR_MONSTER;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_TASK_INFO;
import com.fengshen.server.data.write.system.M65529_npc;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.user.MSG_OPEN_WELFARE;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.user.MSG_UPGRADE_LEVEL_UP;
import com.fengshen.server.data.xls_config.DugenoCfg;
import com.fengshen.server.data.xls_config.DugenoItem;
import com.fengshen.server.data.xls_config.XLSConfigMgr;
import com.fengshen.server.domain.config.TyzqAttribConfig.TyzqAttribVo;
import com.fengshen.server.fight.FightAttribtueType;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.HeroPubService;
import com.fengshen.server.service.MapGuardianService;
import com.fengshen.server.service.TitleService;
import com.fengshen.server.service.ZhengDaoDianService;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;

@Service
public class GameUtil {
	public static String[] tongttXj;
	public static String[] tongttcw;
	public static String[] originGuardPet;
	public static Logger log;
	public static String[] ZHANG_MEN = new String[] { "金系掌门", "木系掌门", "水系掌门", "火系掌门", "土系掌门" };
	public static String[] XIU_FA_NPC = new String[] { "青龙", "白虎", "朱雀", "玄武" };
	public static String[] SHOU_SHI_35 = new String[] { "温玉玦", "紫晶坠子", "七星手链" };
	public static String[] SHOU_SHI_50 = new String[] { "血心石", "三才项圈", "凤舞环" };
	public static String[] SHOU_SHI_60 = new String[] { "八角晶牌", "幻彩项链", "龙鳞手镯" };
	public static String[] SHOU_SHI_70 = new String[] { "蟠螭结", "雪魂丝链", "法文手轮" };
	public static String[] SHOU_SHI_80 = new String[] { "七龙珠", "天机锁链", "闭月双环" };
	public static String[] SHOU_SHI_90 = new String[] { "金蝉宝囊", "秘魔灵珠", "三清手镯" };
	public static String[] SHOU_SHI_100 = new String[] { "通灵宝玉", "金碧莲花", "天星奇光" };
	public static String[] SHOU_SHI_110 = new String[] { "寒玉龙勾", "流光绝影", "碎梦涵光" };
	public static String[] SHOU_SHI_120 = new String[] { "八宝如意", "五蕴悯光", "九天霜华" };
	public static String[] SHOU_SHI_130 = new String[] { "游火灵焰", "千彩流光", "岚金火链" };
	public static String[] SHOU_SHI_140 = new String[] { "炫元玲珑", "掠虹宝坠", "龙御七星" };
	public static String[] SHOU_SHI_150 = new String[] { "七杀固元", "破军捆灵", "贪狼破日" };
	public static String[] SHOU_SHI_160 = new String[] { "菩提镜明", "洛神回雪", "屠龙封魔" };
	public static String[] SHOU_SHI_170 = new String[] { "和光同尘", "景云烛天", "九霞朝真" };

	// 添加装备特有属性
	public static String[] WUQI_TEYOU = new String[] { "物伤", "法伤", "物理连击率", "反击率", "物理必杀率", "金相性", "木相性", "水相性", "火相性",
			"土相性", "所有技能上升", "所有相性", "忽视所有抗性", "忽视所有抗异常" };
	public static String[] YIFU_TEYOU = new String[] { "抗中毒", "抗冰冻", "抗昏睡", "抗遗忘", "抗混乱", "金抗性", "木抗性", "水抗性", "火抗性",
			"土抗性", "所有抗异常", "所有抗性" };
	public static String[] XIEZI_TEYOU = new String[] { "速度" };

	private static JSONArray randomSkills;

	/**
	 * 通知提示消息
	 */
	public static void notifyPrompt(int charaId, String msg) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = msg;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		GameObjectCharMng.getGameObjectChar(charaId).sendOne(new M20481_0(), vo_20481_0);
	}

	/**
	 * 检查某个物品是否够
	 **/
	public static boolean checkGoods(Chara chara, String str, int count) {
		for (int i = 0; i < chara.backpack.size(); i++) {
			Goods goods = chara.backpack.get(i);
			if ((goods.goodsInfo.str.equals(str))) {
				count -= goods.goodsInfo.owner_id;
			}
			if (count <= 0)
				return true;
		}
		return false;
	}

	/**
	 * 弹出 TIPS
	 *
	 * @param msg
	 */
	public static void sendTips(String msg) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = msg;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		GameObjectChar.send(new M20481_0(), vo_20481_0);
	}

	// 判断黑水晶是否适用于某类型的装备上
	public static boolean isTeyou(String name, String[] teyouList) {
		String type = name;
		if (name.split("·").length == 2)
			type = name.split("·")[1];
		for (String s : teyouList) {
			if (type.equals(s))
				return true;
		}
		return false;
	}

	// 重置传入chara的所有任务
	public static void resetRenwuByChara(Chara chara) {
		chara.isGet = 0;
		chara.isCanSgin = 1;
		chara.online_time = 0L;
		// add:e
		for (int i = 0; i < chara.shenmiliwu.size(); ++i) {
			chara.shenmiliwu.get(i).online_time = 0;
			chara.shenmiliwu.get(i).name = "";
			chara.shenmiliwu.get(i).brate = 0;
		}
		// 紫气鸿蒙金钱购买次数
		chara.ziqihongmengMoneyNum = 0;
		// 宠风散金钱购买次数
		chara.chongfengsanMoneyNum = 0;
		// 刷道
		chara.shuadao = 1;
		// 除暴
		chara.chubao = 1;
		chara.baibangmang = 0;
		// 师门
		chara.shimencishu = 1;
		chara.fabaorenwu = 0;
		chara.diyu_cishu = 0;
		chara.totalCheckpoint = 0;

		chara.cengshu = 1;
		chara.molongIndex = 0;
		chara.diyu_ceng = 1;
		chara.molongCount = 0;
		chara.zhuxian_ceng = 1;
		chara.zhuxian_cishu = 0;
		chara.diyushenyuanNum = 0;
		// 修行次数
		chara.xiuxingcishu = 1;
		// 悬赏
		chara.xuanshangcishu = 0;
		//掌门
		chara.zhangmentiaozhan = 0;
		//八仙
		chara.baxiantiaozhan = 0;
		//副本
		chara.fb_num = 0;
		//挑战地图守护神的次数
		chara.mapguardcishu = 0;
		// 挑战证道殿的次数
		chara.zhengdaodiancishu = 0;
		// 挑战英雄会次数
		chara.heropubcishu = 0;
		// 攻城BOSS刷的次数
		chara.gongchengcishu = 0;
		// 战神的次数
		chara.zhanshencishu = 0;
		// 海盗的次数
		chara.haidaocishu = 0;
		// 上古的次数
		chara.shanggucishu = 0;
		// 万年的次数
		chara.wanniancishu = 0;
		// 修法任务次数
		chara.xiufacishu = 0;
		// 通天塔
		chara.tongttcishu = 0;
		//超级boss
		chara.superBossNum = 0;
		//帮派日常挑战
		chara.partyFightNum = 0;
		//帮派任务
		chara.partyNum = 0;
		// 清除普通任务
//		chara.commonTaskMap.clear();
		//年兽
		chara.newYearBeastNum = 0;
		//天地星
		chara.tiandixingNum = 0;
		//重置今日
		chara.dayInfo = new DAILY_STATS_INFO();
		chara.sendGivingCount = 0;
		chara.getGivingCount = 0;
		chara.isGetChargeFuLi = 0;
		chara.fixedTeamPoint = 0;
		chara.ctCount = 0;
		//如果每次输出化小于0
		if(chara.ctDataScore<0) {
			chara.ctDataScore = 0;
		}
		chara.qishaCount = 0;
	}

//	public static void openNpcDialogue(int npc_id, String content) {
//        Npc npc = GameData.that.baseNpcService.findById(npc_id);
//        final Vo_8247_0 vo_8247_3 = new Vo_8247_0();
//        vo_8247_3.id = npc_id;
//        vo_8247_3.portrait = npc.getIcon();
//        vo_8247_3.pic_no = 1;
//        vo_8247_3.content = content;
//        vo_8247_3.secret_key = "";
//        vo_8247_3.name = npc.getName();
//        vo_8247_3.attrib = 1;
//        GameObjectChar.send(new M8247_0_MSG_MENU_LIST(), vo_8247_3);
//    }

	public static void openNpcDialogue(int npc_id, String content) {
//		Npc npc = GameData.that.baseNpcService.findById(npc_id);
//		final Vo_8247_0 vo_8247_3 = new Vo_8247_0();
//		vo_8247_3.id = npc_id;
//		vo_8247_3.portrait = npc.getIcon();
//		vo_8247_3.pic_no = 1;
//		vo_8247_3.content = content;
//		vo_8247_3.secret_key = "";
//		vo_8247_3.name = npc.getName();
//		vo_8247_3.attrib = 1;
//		GameObjectChar.sendThreadLocal(new M8247_0_MSG_MENU_LIST(), vo_8247_3);
	}

	public static void resetRenwuByChara(SaveChara chara) {
		chara.isGet = 0;
		chara.isCanSgin = 1;
		chara.online_time = 0L;
		// add:e
		for (int i = 0; i < chara.shenmiliwu.size(); ++i) {
			chara.shenmiliwu.get(i).online_time = 0;
			chara.shenmiliwu.get(i).name = "";
			chara.shenmiliwu.get(i).brate = 0;
		}
		// 紫气鸿蒙金钱购买次数
		chara.ziqihongmengMoneyNum = 0;
		// 宠风散金钱购买次数
		chara.chongfengsanMoneyNum = 0;


		// 刷道
		chara.shuadao = 1;
		// 除暴
		chara.chubao = 1;
		chara.baibangmang = 0;
		// 师门
		chara.shimencishu = 1;
		chara.fabaorenwu = 0;
		// 修行次数
		chara.xiuxingcishu = 1;
		// 悬赏
		chara.xuanshangcishu = 0;
		//掌门
		chara.zhangmentiaozhan = 0;
		//八仙
		chara.baxiantiaozhan = 0;
		//副本
		chara.fb_num = 0;
		//挑战地图守护神的次数
		chara.mapguardcishu = 0;
		// 挑战证道殿的次数
		chara.zhengdaodiancishu = 0;
		// 挑战英雄会次数
		chara.heropubcishu = 0;
		// 攻城BOSS刷的次数
		chara.gongchengcishu = 0;
		// 战神的次数
		chara.zhanshencishu = 0;
		// 海盗的次数
		chara.haidaocishu = 0;
		// 上古的次数
		chara.shanggucishu = 0;
		// 万年的次数
		chara.wanniancishu = 0;
		// 修法任务次数
		chara.xiufacishu = 0;
		// 通天塔
		chara.tongttcishu = 0;
		//超级boss
		chara.superBossNum = 0;
		//帮派日常挑战
		chara.partyFightNum = 0;
		//帮派任务
		chara.partyNum = 0;
		// 清除普通任务
//		chara.commonTaskMap.clear();
		//年兽
		chara.newYearBeastNum = 0;
		//天地星
		chara.tiandixingNum = 0;
		//重置今日
		chara.dayInfo = new DAILY_STATS_INFO();
		chara.sendGivingCount = 0;
		chara.getGivingCount = 0;
		chara.isGetChargeFuLi = 0;
		chara.fixedTeamPoint = 0;
		chara.ctCount = 0;
		chara.qishaCount = 0;

		chara.totalCheckpoint = 0;
		chara.diyu_cishu = 0;
		chara.cengshu = 1;
		chara.molongIndex = 0;
		chara.diyu_ceng = 1;
		chara.molongCount = 0;
		chara.diyushenyuanNum = 0;
			chara.zhuxian_ceng = 1;
		chara.zhuxian_cishu = 0;
	}

	// 判断每个队员的挑战守护神次数是否用完了
	public static boolean judeMapGuardCishu(Chara chara, GameObjectChar session) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).mapguardcishu >= GameConfig.config.getBaseConfig().getDitushouhuNum()) {
				hasyes = false;
			}
		}
		return hasyes;
	}

	/**
	 *
	 * @param polar
	 * @param sex    1:男，2：女
	 * @return
	 */
	public static int getCharWaiGuan2(int polar, int sex) {
		if ((polar == 1) && (sex == 1)) {
			return 6001;
		}
		if ((polar == 2) && (sex == 1)) {
			return 7002;
		}
		if ((polar == 3) && (sex == 1)) {
			return 7003;
		}
		if ((polar == 4) && (sex == 1)) {
			return 6004;
		}
		if ((polar == 5) && (sex == 1)) {
			return 6005;
		}
		if ((polar == 1) && (sex == 2)) {
			return 7001;
		}
		if ((polar == 2) && (sex == 2)) {
			return 6002;
		}
		if ((polar == 3) && (sex == 2)) {
			return 6003;
		}
		if ((polar == 4) && (sex == 2)) {
			return 7004;
		}
		if ((polar == 5) && (sex == 2)) {
			return 7005;
		}
		throw new UnsupportedOperationException();
	}

	public static void notifyNpcDisappear(Npc npc, List<GameObjectChar> sessionList) {
		for (GameObjectChar gameObjectChar : sessionList) {
			if (gameObjectChar.chara == null) {
				continue;
			}
			if (gameObjectChar.gameMap.id == npc.getMapId()) {
				GameObjectChar.getGameObjectChar().sendOne(new MSG_DISAPPEAR(), npc.getId());
			}
		}
	}

	public static void notifyNpcAppear(Npc npc, List<GameObjectChar> sessionList) {
		for (GameObjectChar gameObjectChar : sessionList) {
			if (!gameObjectChar.isOnline()) {
				continue;
			}
			if (gameObjectChar.gameMap.id == npc.getMapId()) {
				gameObjectChar.sendOne(new M65529_npc(), npc);
			}
		}
	}

	/**
	 * 将两个int16通过位运算转换为有序拼接的int32
	 *
	 * @param x
	 * @param y
	 * @return
	 */
	public static int comTwoInt16(short x, short y) {

		int rt = ((int) x) << 16;

		rt = rt | y;

		return rt;

	}

	/**
	 * 通知打开面板
	 * 
	 * @param npc
	 * @param content
	 */
	public static void notifyOpenMenu(Npc npc, String content) {
		GameObjectChar.send(new MSG_MENU_LIST(), GameUtil.MSG_MENU_LIST(npc, content));
	}

	/**
	 * MSG_MENU_LIST
	 */
	public static Vo_MENU_LIST MSG_MENU_LIST(Npc npc, String content) {
		Vo_MENU_LIST menu_list_vo = new Vo_MENU_LIST();
		menu_list_vo.id = npc.getId();
		menu_list_vo.portrait = npc.getIcon();
		menu_list_vo.pic_no = 1;
		menu_list_vo.content = content.replace("\\", "");
		menu_list_vo.secret_key = "";
		menu_list_vo.name = npc.getName();
		menu_list_vo.attrib = 0;
		return menu_list_vo;
	}

	// 根据掌门名字来获取掌门id
	public static int getPolar(String zhangMenName) {
		for (int i = 0; i < ZHANG_MEN.length; ++i) {
			if (ZHANG_MEN[i].equals(zhangMenName)) {
				return i + 1;
			}
		}
		throw new UnsupportedOperationException();
	}

	// 返回证道殿的角色名字[证道殿_1_0表示男70级]
	public static String getZddJuese(int polar, int sex, int level) {
		String name = ZhengDaoDianService.NPC_NAME;
		if (level >= 70 && level < 80)
			name = name + "_" + polar + "_" + sex + "_" + 0;
		if (level >= 80 && level < 90)
			name = name + "_" + polar + "_" + sex + "_" + 1;
		if (level >= 90 && level < 100)
			name = name + "_" + polar + "_" + sex + "_" + 2;
		if (level >= 100 && level < 110)
			name = name + "_" + polar + "_" + sex + "_" + 3;
		if (level >= 110 && level < 120)
			name = name + "_" + polar + "_" + sex + "_" + 4;
		if (level >= 120 && level < 129)
			name = name + "_" + polar + "_" + sex + "_" + 5;
		return name;
	}

	// 返回英雄会英雄的名字[英雄会评议员_0表示70级]
	public static String getYingxiong(int level) {
		String name = HeroPubService.DEFAULT_PET_NAME;
		if (level >= 70 && level < 80)
			name = name + "_" + 0;
		if (level >= 80 && level < 90)
			name = name + "_" + 1;
		if (level >= 90 && level < 100)
			name = name + "_" + 2;
		if (level >= 100 && level < 110)
			name = name + "_" + 3;
		if (level >= 110 && level < 120)
			name = name + "_" + 4;
		if (level >= 120 && level < 129)
			name = name + "_" + 5;
		if (level >= 130 && level < 139)
			name = name + "_" + 6;
		return name;
	}

	// 获取门派取对应掌门的名字
	public static String getZhangMenName(int polar) {
		return ZHANG_MEN[polar - 1];
	}

	// 障碍技能中根据道行、抗异常、忽视抗异常来计算
	public static boolean zaActiveJudge(FightObject attackObject, FightObject victimObject) {
		int aDaohang = attackObject.friend;
		int bDaohang = victimObject.friend;
		//抗异常
		double kangYichang = 0;
		//忽视抗异常
		double hushiYichang = 0;
		//强力异常
		double qiangLi = 0;
		//所有抗异常
		double allYichang = 0;
		if (attackObject.polar == 1) {
			kangYichang = victimObject.getAttribute(FightAttribtueType.RESIST_FORGOTTEN);
			hushiYichang = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_FORGOTTEN);
			qiangLi = victimObject.getAttribute(FightAttribtueType.SUPER_FORGOTTEN);
		} else if (attackObject.polar == 2) {
			kangYichang = victimObject.getAttribute(FightAttribtueType.RESIST_POISON);
			hushiYichang = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_POISON);
			qiangLi = victimObject.getAttribute(FightAttribtueType.SUPER_POISON);
		} else if (attackObject.polar == 3) {
			kangYichang = victimObject.getAttribute(FightAttribtueType.RESIST_FROZEN);
			hushiYichang = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_FROZEN);
			qiangLi = victimObject.getAttribute(FightAttribtueType.SUPER_FROZEN);
		} else if (attackObject.polar == 4) {
			kangYichang = victimObject.getAttribute(FightAttribtueType.RESIST_SLEEP);
			hushiYichang = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_SLEEP);
			qiangLi = victimObject.getAttribute(FightAttribtueType.SUPER_SLEEP);
		} else if (attackObject.polar == 5) {
			kangYichang = victimObject.getAttribute(FightAttribtueType.RESIST_CONFUSION);
			hushiYichang = attackObject.getAttribute(FightAttribtueType.IGNORE_RESIST_CONFUSION);
			qiangLi = victimObject.getAttribute(FightAttribtueType.SUPER_CONFUSION);
		}
		//所有抗异常
		allYichang = victimObject.getAttribute(FightAttribtueType.ALL_RESIST_EXCEPT);
		
		kangYichang = (kangYichang - 80) > 0.0 ? 80 : kangYichang;
		hushiYichang = (hushiYichang - 80) > 0.0 ? 80 : hushiYichang;
		allYichang = (allYichang - 80) > 0.0 ? 80 : allYichang;
		qiangLi = (qiangLi - 80) > 0.0 ? 80 : qiangLi;
		int addKyc = (int) (hushiYichang - kangYichang-allYichang);
		int num = (int) (50 + (aDaohang - bDaohang) / 20 + (addKyc+qiangLi));
		boolean isSuccessExcption = GameUtil.getChance(num);
		return isSuccessExcption;
	}

	// 返回对手成员
	public static Vo_ADD_FRIEND_OPPONENT vo_65017_0(FightObject fightObject) {
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
		vo_65017_0.dex = fightObject.max_mofa;
		vo_65017_0.max_mana = fightObject.max_mofa;
		vo_65017_0.max_life = fightObject.max_shengming;
		vo_65017_0.def = fightObject.max_shengming;
		vo_65017_0.org_icon = fightObject.org_icon;
		vo_65017_0.suit_icon = fightObject.suit_icon;
		vo_65017_0.suit_light_effect = fightObject.suit_light_effect;
		vo_65017_0.special_icon = fightObject.special_icon;
		vo_65017_0.customIcon = fightObject.customIcon;
		vo_65017_0.zhenlingLevel = fightObject.zhenlingLevel;
		vo_65017_0.zhenlingType = fightObject.zhenlingType;
		return vo_65017_0;
	}

	// 这里可以弹出必杀特效
	public static void showImg(FightContainer fightContainer, int id, int effectNo, String imgName) {
		Vo_12028_0 vo_12028_0 = new Vo_12028_0();
		vo_12028_0.id = id;
		vo_12028_0.effect_no = effectNo;
		vo_12028_0.type = 4;
		vo_12028_0.name = imgName;
		FightManager.send(fightContainer, new M12028_0(), vo_12028_0);
	}

	// add tzhang 获得等级对应的基本道行。这里是道行对应的总天书，1年=360天
	public static int baseDH(int level) {
		return (int) (level * level * level * 0.29);
	}

	// 判断两个对象的障碍技能，是否a能封住b
	public static boolean ZA(int a, int b) {
		if (a < b) {
			return a * 1.2 > b && getChance(100);
		}
		if (a > b) {
			return getChance((int) ((a - b) * 1.0 / b * 100.0));
		}
		return getChance(10);
	}

	// 添加守护
	public static void addshouhu(Chara chara) {
		for (int i = 0; i < chara.listshouhu.size(); ++i) {
			ShouHu shouHu = chara.listshouhu.get(i);
			ShouHuShuXing shouHuShuXing = chara.listshouhu.get(i).listShouHuShuXing.get(0);
			Hashtable<String, int[]> stringHashtable = PetAttributesUtils.helpPet(shouHuShuXing.penetrate,
					shouHuShuXing.metal, chara.level);
			int[] attributes = stringHashtable.get("attribute");
			int[] polars = stringHashtable.get("polars");
			shouHuShuXing.life = attributes[0];
			shouHuShuXing.mag_power = attributes[1];
			shouHuShuXing.phy_power = attributes[2];
			shouHuShuXing.speed = attributes[3];
			shouHuShuXing.wood = polars[0];
			shouHuShuXing.water = polars[1];
			shouHuShuXing.fire = polars[2];
			shouHuShuXing.earth = polars[3];
			shouHuShuXing.resist_metal = polars[4];
			shouHuShuXing.skill = chara.level;
			shouHuShuXing.shape = 0;
			int[] ints = BasicAttributesUtils.calculationHelpAttributes(chara.level, attributes[0], attributes[1],
					attributes[2], attributes[3], polars[0], polars[1], polars[2], polars[3], polars[4],
					shouHuShuXing.metal);
			shouHuShuXing.max_life = ints[0];
			shouHuShuXing.def = ints[0];
			shouHuShuXing.accurate = ints[2];
			shouHuShuXing.mana = ints[3];
			shouHuShuXing.parry = ints[4];
			shouHuShuXing.wiz = ints[5];
			shouHuShuXing.salary = 0;
			List<ShouHu> list = new ArrayList<>();
			list.add(shouHu);
			GameObjectChar.send(new M12016_0(), list, chara.id);
			dujineng(2, shouHuShuXing.metal, shouHuShuXing.skill, true, shouHu.id, chara, null);
		}
	}

	public static void addfabaojingyan(Chara chara1, int jingyan, String source) {
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			if(openGlobalDouble.indexOf(source) != -1) {
				jingyan*=2;
			}
		}
		boolean has = fabaojingyan(chara1, jingyan);
		if (has) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你的法宝获得了#R" + jingyan + "#n经验";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_0);
		}
	}

	public static Boolean fabaojingyan(Chara chara1, int jingyan) {
		if (jingyan < 0)
			return false;
		Boolean has = false;
		int i = 0;
		
		while (i < chara1.otherGoods.size()) {
			Goods fabao = chara1.otherGoods.get(i);
			if (fabao.pos == 9) {
				if (fabao.goodsInfo.skill >= 24) {
					Vo_20481_0 vo_20481_9 = new Vo_20481_0();
					vo_20481_9.msg = "你的法宝已满级，无法获得法宝道法！";
					vo_20481_9.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_9);
					return has;
				}
				ListVo_65527_0 listVo_65527_0 = a65527(chara1);
				GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M65527_0(), listVo_65527_0);
				GoodsInfo goodsInfo = fabao.goodsInfo;
				goodsInfo.pot += jingyan;
				List<Goods> list = new ArrayList<Goods>();
				list.add(fabao);
				GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M65525_0(), list);
				if (fabao.goodsInfo.pot >= fabao.goodsInfo.resist_poison) {
					GoodsInfo goodsInfo2 = fabao.goodsInfo;
					++goodsInfo2.skill;
					fabao.goodsInfo.pot = 0;
					jingyan -= fabao.goodsInfo.resist_poison;
					ExperienceTreasure et = GameData.that.baseExperienceTreasureService.findOneByAttrib(fabao.goodsInfo.skill);
					if(et != null) {
						fabao.goodsInfo.resist_poison = et.getMaxLevel();
					}
					fabaojingyan(chara1, jingyan);
				}
				has = true;
				break;
			} else {
				++i;
			}
		}
		return has;
	}

	public static Goods beibaowupin(Chara chara, int pos) {
		for (int i = 0; i < chara.backpack.size(); ++i) {
			if (chara.backpack.get(i).pos == pos) {
				return chara.backpack.get(i);
			}
		}
		return null;
	}

	public static boolean belongCalendar() {
		Date nowTime = null;
		Date beginTime = null;
		Date endTime = null;
		SimpleDateFormat df = new SimpleDateFormat("mm");
		try {
			nowTime = df.parse(df.format(new Date()));
			beginTime = df.parse("29");
			endTime = df.parse("40");
		} catch (ParseException e) {
			log.error("{}", e);
		}
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);
		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);
		Calendar end = Calendar.getInstance();
		end.setTime(endTime);
		return date.after(begin) && date.before(end);
	}

	// 给刷星相关奖励
	public static void shuaxingOver(Chara chara1, Chara duiyuan, int level, String replace) {
		Random random = new Random();
		log.info("杀星结束,给奖励--星等级{}", level);
		duiyuan.tiandixingNum++;
		if (replace.equals("天星")) {
			huodejingyan(duiyuan, 100000 + 3125 * duiyuan.level, "天星");
			adddaohang(duiyuan, duiyuan.level * 45 * 1440,"天星");
			addQianNeng(duiyuan, duiyuan.level * 3700, "天星");
			// 获取装备
			GameCommonUtil.getRandomEquipByLevel(duiyuan, level);
			//宠物武学
			Petbeibao pet = GameCommonUtil.getCurrentPet(duiyuan);
			if(pet != null) {
				PetShuXing petShuXing = pet.petShuXing.get(0);
				int base_pet_dh = (int) (0.25 * petShuXing.skill * petShuXing.skill * petShuXing.skill);
				int intimacy = 33 * level
						/ ((petShuXing.intimacy > base_pet_dh) ? (petShuXing.intimacy / base_pet_dh) : 1);
				
				GameCommonUtil.addWuXue(duiyuan, intimacy, pet, "天星");
				
			}
		} else if (replace.equals("地星")) {
			int j = Math.abs(duiyuan.level - level) / 5;
			if (j == 0) {
				j = 1;
			}
			int jingyan2 = 1298 * level / j;
			int jingyan3 = random.nextInt(100);
			jingyan2 = (int) (jingyan2 * (1000 - jingyan3) * 0.001);
			if (jingyan2 < 1) {
				jingyan2 = 1;
			}
			jingyan2 = shuangbei(duiyuan, jingyan2);
			if (duiyuan.level - level > 29) {
				jingyan2 = 1;
			}
			//宠物武学
			Petbeibao pet = GameCommonUtil.getCurrentPet(duiyuan);
			if(pet != null) {
				PetShuXing petShuXing = pet.petShuXing.get(0);
				int base_pet_dh = (int) (0.29 * petShuXing.skill * petShuXing.skill * petShuXing.skill);
				int intimacy = 33 * level
						/ ((petShuXing.intimacy > base_pet_dh) ? (petShuXing.intimacy / base_pet_dh) : 1);
				
				GameCommonUtil.addWuXue(duiyuan, intimacy, pet, "地星");
			}
			// 获取装备
			for (int i = 0; i < ThreadLocalRandom.current().nextInt(2) + 1; i++) {
				GameCommonUtil.getRandomEquipByLevel(duiyuan, level);
			}
			huodejingyan(duiyuan, 100000 + 3125 * duiyuan.level, "地星");
			addQianNeng(duiyuan, duiyuan.level * 3700, "地星");
			adddaohang(duiyuan, duiyuan.level * 45 * 1440,"地星");

		}
		GameObjectChar.send(new M65527_0(), a65527(duiyuan), duiyuan.id);
	}

	// 这里是巡逻给经验的地方，chara1是队长，duiyuan是队员
	public static void shuayeguai(Chara chara1, Chara duiyuan, int level) {
		Random random = new Random();
		int i = Math.abs(duiyuan.level - level) / 5;
		if (i == 0) {
			i = 1;
		}

		int jingyan = 20 * level / i;
		int i2 = random.nextInt(100);
		jingyan = (int) (jingyan * (1000 - i2) * 0.001);

		if (jingyan < 1) {
			jingyan = 1;
		}

		// 这里传入的是队员的角色，不是队长的
		jingyan = shuangbei(duiyuan, jingyan);
		huodejingyan(duiyuan, jingyan, "野怪");

		ListVo_65527_0 listVo_65527_0 = a65527(duiyuan);
		GameObjectCharMng.getGameObjectChar(duiyuan.id).sendOne(new M65527_0(), listVo_65527_0);
		i = random.nextInt(1000);
		if (i < 5 && level >= 60) {
			weijianding(duiyuan);
		}
	}

	// 给试道经验
	public static void shidaojingyan(Chara chara1, Chara duiyuan, int id) {
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_DISAPPEAR(), id);

		int base_dh = (int) (0.29 * duiyuan.level * duiyuan.level * duiyuan.level);
		int owner_name = 12272 * duiyuan.level / ((duiyuan.tao > base_dh) ? (duiyuan.tao / base_dh) : 1);
		adddaohang(duiyuan, owner_name, "试道");
		//宠物武学
		GameCommonUtil.addWuXue(duiyuan, 0, "试道");
	}

	public static void nextxuanshang(Chara chara1, Chara duiyuan, int xuanshangId) {
		GameObjectChar.sendduiwu(new MSG_DISAPPEAR(), xuanshangId, chara1.id);
		if(!GameActiveUtil.fightVictoryInfo(duiyuan, "悬赏任务")) {
			int base_dh = (int) (0.29 * duiyuan.level * duiyuan.level * duiyuan.level);
			int owner_name = 17800 * duiyuan.level / ((duiyuan.tao > base_dh) ? (duiyuan.tao / base_dh) : 1);
			adddaohang(duiyuan, owner_name,"悬赏");
			int cash = 18936 * duiyuan.level;
			duiyuan.pot += cash;
			//宠物武学
			GameCommonUtil.addWuXue(duiyuan, 0, "悬赏");
		}
		Vo_61553_0 task = chara1.taskMap.get("悬赏任务");
		if (task != null) {
			task.task_prompt = "任务已完成速回#P仙界神捕|M=领取奖励#P#n处领取奖励";
			task.task_state = "finish";
			GameUtilRenWu.createTask(task, chara1);
		}
		GameShuaGuai.xuanshang.remove(xuanshangId);
	}

	public static boolean duiwudengji(Chara chara, GameObjectChar session) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size() - 1; ++i) {
			if (Math.abs(session.gameTeam.duiwu.get(i).level - session.gameTeam.duiwu.get(i + 1).level) > 10) {
				hasyes = false;
			}
		}
		return hasyes;
	}

	public static boolean duiwudengji120(Chara chara, GameObjectChar session) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level < 120) {
				hasyes = false;
			}
		}
		return hasyes;
	}

	public static boolean judgeDuiyuanLevel(Chara chara, GameObjectChar session, int level) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level < level) {
				hasyes = false;
				break;
			}
		}
		return hasyes;
	}

	public static boolean duiwudengji40(Chara chara, GameObjectChar session) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level < 40) {
				hasyes = false;
			}
		}
		return hasyes;
	}

	public static boolean duiwudengji80(Chara chara, GameObjectChar session) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level < 80) {
				hasyes = false;
			}
		}
		return hasyes;
	}

	public static boolean duiwudengji100(Chara chara, GameObjectChar session) {
		boolean hasyes = true;
		for (int i = 0; i < session.gameTeam.duiwu.size(); ++i) {
			if (session.gameTeam.duiwu.get(i).level < 100) {
				hasyes = false;
			}
		}
		return hasyes;
	}

	// 法宝给亲密
	public static void addFabaoQinmi(Chara chara, int value, String source) {
		for (int l = 0; l < chara.otherGoods.size(); ++l) {
			Goods goods = chara.otherGoods.get(l);
			if (goods.pos == 9) {
				String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
				if(openGlobalDouble != null) {
					if(openGlobalDouble.indexOf(source) != -1) {
						value*=2;
					}
				}
				goods.goodsInfo.shape += value;
				Vo_20481_0 vo_20481_9 = new Vo_20481_0();
				vo_20481_9.msg = "你的法宝#Y" + goods.goodsInfo.str + "#n获得了#R" + value + "#n点亲密";
				vo_20481_9.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_9);
				break;
			}
		}
	}

	// 法宝给道法
	public static void addFabaoDaofa(Chara chara, int value, String source) {
		addfabaojingyan(chara, value, source);
	}

	// 角色获得元宝
	public static void addJinYuanBao(GameObjectChar gameObjectChar, int yb, String... source) {
		Chara chara = gameObjectChar.chara;
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			if(source.length> 0 && openGlobalDouble.indexOf(source[0]) != -1) {
				yb*=2;
			}
		}
		long macYuanBao = chara.goldCoin + yb;
		if(macYuanBao > 2000000000) {
			chara.goldCoin = 2000000000;
			GameCommonUtil.sendTips("金元宝超出上限，多出部分充公！", chara.id);
		}else {
			chara.goldCoin += yb;
		}
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得#R" + yb+"#n金元宝";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
		//刷新
		java.util.Map<String,Object> data = new HashMap<>();
		data.put("gold_coin", chara.goldCoin);
		gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
		GameCommonUtil.addCharaTrail(chara, "金元宝", yb, source);
	}
	
	/**
	 * a获得金币
	 * @param gameObjectChar
	 * @param cash
	 */
	public static void addCash(GameObjectChar gameObjectChar, int cash, String... source) {
		Chara chara = gameObjectChar.chara;
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			if(source.length> 0 && openGlobalDouble.indexOf(source[0]) != -1) {
				cash*=2;
			}
		}
		long max = chara.cash + cash;
		if(max > 2000000000) {
			chara.cash = 2000000000;
			GameCommonUtil.sendTips("金币超出上限，多出部分充公！", gameObjectChar);
		}else {
			chara.cash += cash;
		}
		GameCommonUtil.sendTips("获得"+GameCommonUtil.getMoneyDes(cash)+"金钱", gameObjectChar);
		
		//刷新
		java.util.Map<String,Object> data = new HashMap<>();
		data.put("cash", chara.cash);
		gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
//		GameCommonUtil.addCharaTrail(chara, "金币", cash, source);
	}
	
	/**
	 * a添加充值积分
	 * @param gameObjectChar
	 * @param score
	 */
	public static void addchargeScore(GameObjectChar gameObjectChar, int score, String... source) {
		Chara chara = gameObjectChar.chara;
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			if(source.length> 0 && openGlobalDouble.indexOf(source[0]) != -1) {
				score*=2;
			}
		}
		if(chara.chargeScore + score > 2000000000) {
			chara.chargeScore = 2000000000;
			GameCommonUtil.sendTips("积分超出上限，多出部分充公！", gameObjectChar);
		} else {
			chara.chargeScore += score;
		}
		if(source != null && source.length>1) {
			GameCommonUtil.sendTips(source[1], gameObjectChar);
		}else {
			GameCommonUtil.sendTips("获得#R"+score+"#n充值积分", gameObjectChar);
		}
		GameCommonUtil.addCharaTrail(chara, "充值积分", score, source);
	}
	
	/**
	 * 获得抽奖次数
	 * @param gameObjectChar
	 * @param score
	 * @param source
	 */
	public static void addLotteryTimes(GameObjectChar gameObjectChar, int score, String... source) {
		Chara chara = gameObjectChar.chara;
		if(chara.shadow_self + score > 2000000000) {
			chara.shadow_self = 2000000000;
			GameCommonUtil.sendTips("抽奖次数超出上限，多出部分充公！", gameObjectChar);
		} else {
			chara.shadow_self += score;
		}
		if(source != null && source.length>1) {
			GameCommonUtil.sendTips(source[1], gameObjectChar);
		}else {
			GameCommonUtil.sendTips("获得#R"+score+"#n抽奖次数", gameObjectChar);
		}
		GameCommonUtil.addCharaTrail(chara, "积分抽奖", score, source);
	}
	
	/**
	 * a添加银元宝
	 * @param gameObjectChar
	 * @param score
	 */
	public static void addYinYuanBao(GameObjectChar gameObjectChar, int num, String... source) {
		Chara chara = gameObjectChar.chara;
		long max = chara.silverCoin + num;
		if(max > 2000000000) {
			chara.silverCoin = 2000000000;
			GameCommonUtil.sendTips("银元宝超出上限，多出部分充公！", gameObjectChar);
		} else {
			chara.silverCoin += num;
		}
		if(source != null && source.length>1) {
			GameCommonUtil.sendTips(source[1], gameObjectChar);
		}else {
			GameCommonUtil.sendTips("获得#R"+num+"#n银元宝", gameObjectChar);
		}
		//刷新
		java.util.Map<String,Object> data = new HashMap<>();
		data.put("silver_coin", chara.silverCoin);
		gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
		GameCommonUtil.addCharaTrail(chara, "银元宝", num, source);
	}

	// 角色获得道行
	public static void adddaohang(Chara chara, int daohangdian, String... source) {
		if(chara.mapName.equals("试道场")) {
			chara.shidaoTao+=daohangdian;
		}else {
			// 判断 A的现有道行>标准道行X10 则A获得的道行为原属性的10% 判断A现有道行>标准道行X5 则A获得的道行为原数值的50% 否则 数值正常
			int baseDH = GameUtil.baseDH(chara.level);
			int tao = (chara.tao+chara.taoPoint);
			if (tao > baseDH * 20) {
				daohangdian = (int) (daohangdian * 0.05);
			}else if (tao > baseDH * 19) {
				daohangdian = (int) (daohangdian * 0.1);
			} else if (tao > baseDH * 18) {
				daohangdian = (int) (daohangdian * 0.15);
			} else if (tao > baseDH * 17) {
				daohangdian = (int) (daohangdian * 0.2);
			} else if (tao > baseDH * 16) {
				daohangdian = (int) (daohangdian * 0.25);
			} else if (tao > baseDH * 15) {
				daohangdian = (int) (daohangdian * 0.3);
			} else if (tao > baseDH * 14) {
				daohangdian = (int) (daohangdian * 0.35);
			} else if (tao > baseDH * 13) {
				daohangdian = (int) (daohangdian * 0.4);
			} else if (tao > baseDH * 12) {
				daohangdian = (int) (daohangdian * 0.45);
			} else if (tao > baseDH * 11) {
				daohangdian = (int) (daohangdian * 0.5);
			} else if (tao > baseDH * 10) {
				daohangdian = (int) (daohangdian * 0.55);
			} else if (tao > baseDH * 9) {
				daohangdian = (int) (daohangdian * 0.6);
			} else if (tao > baseDH * 8) {
				daohangdian = (int) (daohangdian * 0.65);
			} else if (tao > baseDH * 7) {
				daohangdian = (int) (daohangdian * 0.7);
			} else if (tao > baseDH * 6) {
				daohangdian = (int) (daohangdian * 0.75);
			} else if (tao > baseDH * 5) {
				daohangdian = (int) (daohangdian * 0.8);
			} else if (tao > baseDH * 4) {
				daohangdian = (int) (daohangdian * 0.85);
			} else if (tao > baseDH * 3) {
				daohangdian = (int) (daohangdian * 0.9);
			} else if (tao > baseDH * 2) {
				daohangdian = (int) (daohangdian * 0.95);
			}
			chara.taoPoint += daohangdian<=0?0:daohangdian;
			int num = chara.taoPoint / 1440;
			String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
			if(openGlobalDouble != null) {
				if(source.length> 0 && openGlobalDouble.indexOf(source[0]) != -1) {
					num*=2;
				}
			}
			chara.tao += num<=0?0:num;
			chara.taoPoint %= 1440;
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "获得道行#R" + fmtDh(daohangdian);
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			chara.monthTao+=num;
			chara.dayInfo.setToDayTotalTao(chara.dayInfo.getToDayTotalTao()+num);
		}
		GameCommonUtil.addCharaTrail(chara, "道行", daohangdian, source);
		
	}

	// 角色获得金币
	public static void addJinbi(Chara chara, int jinbi, String... source) {
		if(chara.cash + jinbi > 2000000000) {
			chara.cash = 2000000000;
			GameCommonUtil.sendTips("金币超出上限，多出部分充公！", chara.id);
		}else {
			chara.cash += jinbi;
		}
		GameCommonUtil.sendTips("获得"+GameCommonUtil.getMoneyDes(jinbi)+"金钱", chara.id);
//		GameCommonUtil.addCharaTrail(chara, "金币", jinbi, source);
	}

	public static void addLuoshuJinYan(Chara chara, int jingyan, String... source) {
//		chara.luoshuExp = NumberUtil.addNumber(chara.luoshuExp, jingyan);
		if (chara.luoshuExp + jingyan > 2000000000)
			GameCommonUtil.sendTips("洛书超出上限，多出部分充公！", chara.getId());
		chara.luoshuExp += jingyan;
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得洛书经验#R" + jingyan;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//		GameObjectChar.send((BaseWrite) new MSG_NOTIFY_MISC_EX(), vo_20481_0, chara.getId());
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
//		 GameObjectCharMng.save(gameObjectChar);
		GameCommonUtil.addCharaTrail(chara, "洛书经验", Integer.valueOf(jingyan), source);
	}

	/**
	 * 角色获得潜能
	 * @param chara
	 * @param qianneng
	 * @param source
	 */
	public static void addQianNeng(Chara chara, int qianneng, String... source) {
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			if(source.length> 0 && openGlobalDouble.indexOf(source[0]) != -1) {
				qianneng*=2;
			}
		}
		long maxCash = chara.pot+qianneng;
		if (maxCash > 2000000000 || chara.pot < 0) {
			chara.pot = 200000000;
		}else {
			chara.pot+=qianneng;
		}
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得潜能#R" + qianneng;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
		
//		GameCommonUtil.addCharaTrail(chara, "潜能", qianneng, source);
	}

	// 格式化道行
	public static String fmtDh(int daohangdian) {
		int residue = daohangdian % 525600; // 一年为525600，一天为1440
		int days = residue / 1440;
		int years = (daohangdian - residue) / 525600;
		if (years <= 0)
			return days + "天";
		return years + "年" + days + "天";
	}

	// 给双倍经验
	public static int shuangbei(Chara chara1, int jingyan) {
		if (chara1.charashuangbei == 1 && chara1.enable_double_points > 0) {
			jingyan *= 2;
			chara1.enable_double_points -= 4;
		}
		if (chara1.enable_double_points <= 0) {
			chara1.enable_double_points = 0;
		}
		return jingyan;
	}

	// 这里是对修法进行结算
	public static void nextxiufa(Chara chara1, Chara duiyuan) {
		duiyuan.xiufacishu += 1;
		if (duiyuan.xiufacishu <= 4) {
			addFabaoQinmi(duiyuan, 10000, "修法");
			addFabaoDaofa(duiyuan, 10000 + chara1.level * 1000, "修法");
		}

		if (duiyuan.xiufacishu >= 4) {
			// 移除修法的任务框,并将修法npc置空
			duiyuan.xiufaNpcName = "";
			GameUtilRenWu.createTask("修法", "", "", chara1);
			Vo_20481_0 vo_20481_0 = new com.fengshen.server.data.vo.Vo_20481_0();
			vo_20481_0.msg = ("您已完成今日的所有修法任务！");
			vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		}

		String[] npces = GameUtil.XIU_FA_NPC;
		int i = duiyuan.xiufacishu;
		chara1.xiufaNpcName = npces[i];
		String task_prompt = "";
		String show_name = "";
		task_prompt = "挑战神兽#P" + npces[i] + "| M=【修法】我是来消灭你的#P";
		show_name = ("【修法】挑战" + npces[i]);

		if (duiyuan.xiufacishu >= 4) {
			task_prompt = "";
			show_name = "";
			chara1.xiufaNpcName = "";
		}
		GameUtilRenWu.createTask("修法", task_prompt, show_name, chara1);
		GameObjectChar.sendduiwu(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task_prompt), chara1.id);
	}

	/**
	 * 十绝阵结束后给奖励
	 * @param chara1 队长
	 * @param charas
	 */
	public static void nextzhenzhu(Chara teamLeader, List<Chara> charas) {
		String task_prompt = "";
		String show_name = "";
		String[] npces = { "金光阵主", "风吼阵主", "落魄阵主", "化血阵主", "红水阵主", "寒冰阵主", "烈焰阵主", "地烈阵主", "天阙阵主", "红砂阵主" };
		teamLeader.xiuxingcishu+=1;
		String fightName = npces[(teamLeader.xiuxingcishu + 9) % 10];
		task_prompt = "讨教#P" + fightName + "| M=【十绝阵】讨教#P";
		boolean isLeaderFinishTask = false;
		for(Chara duiyuan:charas) {
			if(duiyuan.level<100) {
				GameCommonUtil.sendTips("等级不足100级，无法获得奖励！", duiyuan.id);
				continue;
			}else if(duiyuan.taskMap.get("十绝阵") == null) {
				GameCommonUtil.sendTips("你还未领取任务，无法获得奖励！", duiyuan.id);
				continue;
			}
			if(duiyuan.id != teamLeader.id) {
				duiyuan.xiuxingcishu += 1;
			}
			if (duiyuan.xiuxingcishu <= GameConfig.config.getBaseConfig().getXiuxingcishuNum()) {
				int jingyan = 20000 + duiyuan.level * 1800 + duiyuan.xiuxingcishu * 10000;
				jingyan = shuangbei(duiyuan, jingyan);
				huodejingyan(duiyuan, jingyan, "十绝");
				adddaohang(duiyuan, duiyuan.level * 50 * 230, "十绝");
				addQianNeng(duiyuan, duiyuan.level * 500 + (duiyuan.xiuxingcishu + 1) * 5000, "十绝");
				ListVo_65527_0 listVo_65527_0 = a65527(duiyuan);
				GameObjectCharMng.getGameObjectChar(duiyuan.id).sendOne(new M65527_0(), listVo_65527_0);
				if (getChance(5) == true) {
					weijianding(duiyuan);
				}
			}
			int i = (duiyuan.xiuxingcishu + 9) % 10;
			duiyuan.xiuxingNpcname = fightName;
			show_name = "【十绝阵】讨教(" + (i + 1) + "/10)";
			//如果是队长完成一轮任务,重新到玉泉真人处领取任务.
			if (i == 0 && duiyuan.id == teamLeader.id && duiyuan.xiuxingcishu<GameConfig.config.getBaseConfig().getXiuxingcishuNum()+1) {
				isLeaderFinishTask = true;
			}
			//次数小于这个才会创建任务
			if(duiyuan.xiuxingcishu<GameConfig.config.getBaseConfig().getXiuxingcishuNum()+1) {
				//如果为0表示完成一轮任务
				if(i == 0) {
					duiyuan.xiuxingNpcname = "";
					show_name = "领取十绝阵任务";
					task_prompt = "去#P无名小镇|玉泉真人|M=【十绝阵】我欲挑战十绝阵#P玉泉真人处领取任务";
					GameUtilRenWu.createTask("十绝阵", task_prompt, show_name, duiyuan);
				}
			}else {
				duiyuan.xiuxingNpcname = "";
				//任务完成了
				GameCommonUtil.sendTips("你已完成今日十绝阵！",duiyuan.id);
				GameUtilRenWu.removeTask("十绝阵", duiyuan);
			}
		}
		//如果队长没有完成一轮的话则继续创建任务.
		if(!isLeaderFinishTask) {
			for(Chara duiyuan:charas) {
				if(duiyuan.xiuxingcishu < GameConfig.config.getBaseConfig().getXiuxingcishuNum()+1) {
					GameUtilRenWu.createTask("十绝阵", task_prompt, show_name, duiyuan);
				}
			}
		}
		if(!"".equals(task_prompt)) {
			GameUtil.sendMeTips("任务领取成功,快去完成吧！");
			GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task_prompt), teamLeader.id);
		}
	}

	/**
	 * 修行结束
	 * @param teamLeader 队长
	 * @param teams 
	 */
	public static void nextxiuxing(Chara teamLeader, List<Chara> teams) {
		String nextNpc = "雷神";
		if("雷神".equals(teamLeader.xiuxingNpcname)) {
			nextNpc = "花神";
		}else if("花神".equals(teamLeader.xiuxingNpcname)) {
			nextNpc = "龙神";
		}else if("龙神".equals(teamLeader.xiuxingNpcname)) {
			nextNpc = "炎神";
		}else if("炎神".equals(teamLeader.xiuxingNpcname)) {
			nextNpc = "山神";
		}else if("山神".equals(teamLeader.xiuxingNpcname)) {
			nextNpc = "雷神";
		}
		String task_prompt = "拜访#P" + nextNpc + "| M=【修行】请仙人赐教#P";
		for(Chara team:teams) {
			if(team.taskMap.get("修炼") == null) {
				GameCommonUtil.sendTips("你还未领取任务，无法获得奖励！", team.id);
				continue;
			}else if("".equals(team.xiuxingNpcname)) {
				continue;
			}
			++team.xiuxingcishu;
			int baseNum = ((team.xiuxingcishu - 1) % 10 == 0) ? 10 : (team.xiuxingcishu - 1) % 10;
			//如果任务小于最大限制
			int jingyan = 20000 + team.level * 1500 + baseNum * 10000;
			jingyan = shuangbei(team, jingyan);
			huodejingyan(team, jingyan, "修行");
			addJinbi(team, 50000);
			ListVo_65527_0 listVo_65527_0 = a65527(team);
			GameObjectCharMng.getGameObjectChar(team.id).sendOne(new M65527_0(), listVo_65527_0);
			if (getChance(5) == true) { // 修行爆装备的几率为5%
				weijianding(team);
			}
			if(team.xiuxingcishu >= GameConfig.config.getBaseConfig().getXiuxingcishuNum()+1) {
				team.xiuxingNpcname = "";
				//任务完成了
				GameCommonUtil.sendTips("你已完成今日修行",team.id);
				GameUtilRenWu.removeTask("修炼", team);
				//如果是队长完成了提示队员
				if(team.id == teamLeader.id) {
					task_prompt = "";
					if(team.id != teamLeader.id) {
						GameCommonUtil.sendTips("队长完成了今日的修行,无法继续自动修行！",team.id);
					}
				}
				continue;
			}
			//开始分配新的任务
			team.xiuxingNpcname = nextNpc;
			String show_name = "【修炼】修行(" + team.xiuxingcishu + "/" + GameConfig.config.getBaseConfig().getXiuxingcishuNum()
					+ ")";
			GameUtilRenWu.createTask("修炼", task_prompt, show_name, team);
		}
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task_prompt), teamLeader.id);
	}

	/**
	 * 刷道,飞仙渡邪、伏魔、降妖
	 * 
	 * @param leaderTeam
	 * @param team
	 * @param bossNum
	 */
	public static void singleshuadao(Chara leaderTeam, List<Chara> team, int bossNum) {
		Vo_APPEAR info = leaderTeam.shudao.get(leaderTeam.zhandouId);
		if(info == null) {
			return;
		}
		// 让怪物消失
		GameObjectChar.sendduiwu(new MSG_DISAPPEAR(), leaderTeam.zhandouId, leaderTeam.id);
		// 队长次数
		leaderTeam.shuadao += 1;
		int shuadao = leaderTeam.shuadao;
		double beishu = 1.0;
		if (info.leixing == 3) {
			beishu = 1.5;
		}
		if (info.leixing == 4) {
			beishu = 3.0;
		}
		String task_prompt = "";
		String show_name = "";
		String type = "";
		
		List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
				.findByType(info.leixing);
		Iterator<RenwuMonster> rmIterator = renwuMonsters.iterator();
		while(rmIterator.hasNext()) {
			RenwuMonster next = rmIterator.next();
			if(next.getMapName().equals(leaderTeam.mapName)) {
				rmIterator.remove();
			}
		}
		RenwuMonster renwuMonster2 = renwuMonsters.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
		String name3 = renwuMonster2.getName();
		com.fengshen.db.domain.Map map = GameData.that.baseMapService
				.findOneByName(renwuMonster2.getMapName());
		if (map == null) {
			return;
		}
		
		Vo_APPEAR npc = new Vo_APPEAR();
		npc.mapid = map.getMapId();
		npc.id = GameCommonUtil.generateBossId();
		npc.x = renwuMonster2.getX();
		npc.y = renwuMonster2.getY();
		npc.icon = renwuMonster2.getIcon();
		npc.type = 2;
		npc.org_icon = renwuMonster2.getIcon();
		npc.portrait = renwuMonster2.getIcon();
		npc.name = name3;
		npc.level = leaderTeam.level;
		npc.leixing = info.leixing;
		for (Chara chara : team) {
			chara.shudao.remove(leaderTeam.zhandouId);
			//如果和队长相差10级
			if(leaderTeam.level-chara.level>20) {
				GameCommonUtil.sendTips("你和队长等级相差20级，无法获得奖励！", chara.id);
				continue;
			}
			//降妖
			if (info.leixing == 2) {
				if(chara.level > 80 || chara.level < 45) {
					GameCommonUtil.sendTips("等级不符合，无法获得奖励！", chara.id);
					continue;
				}
			}
			//伏魔
			if (info.leixing == 3) {
				//如果大于120级则不给奖励
				if(chara.level > 120 || chara.level < 80) {
					GameCommonUtil.sendTips("等级不符合，无法获得奖励！", chara.id);
					continue;
				}
			}
			//飞仙
			if (info.leixing == 4) {
				//如果小于120级不符合要求
				if(chara.level<120) {
					GameCommonUtil.sendTips("等级不符合，无法获得奖励！", chara.id);
					continue;
				}
			}
			// 全局对象
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
			if (gameObjectChar == null) {
				continue;
			}
			// 把队长的次数给所有队员
			chara.shuadao = shuadao;
			// 如果开启了紫气鸿蒙给level*0.5的道法，没有开给level*0.2的道法
			if (chara.ziqihongmengState == 1 && chara.ziqihongmeng > 0) {
				chara.ziqihongmeng -= 4;
				if (chara.ziqihongmeng <= 0) {
					chara.ziqihongmeng = 0;
				}
				addFabaoQinmi(chara, 10, "刷道");
				addFabaoDaofa(chara, (int) (chara.level * 0.5), "刷道");
			} else if (chara.ziqihongmengState == 0) { // 没有开启紫气鸿蒙，给level*0.2法宝道法
				addFabaoQinmi(chara, 10, "刷道");
				addFabaoDaofa(chara, (int) (chara.level * 0.2), "刷道");
			}

			if (info.leixing == 2) {
				addQianNeng(chara, chara.level * 500 + (shuadao + 1) * 2000, "刷道");
				adddaohang(chara, (shuadao + 1) * 120 * 1440, "刷道");
			} else if (info.leixing == 3) {
				addQianNeng(chara, chara.level * 700 + (shuadao + 1) * 4000, "刷道");
				adddaohang(chara, (shuadao + 1) * 145 * 1440, "刷道");
			} else if (info.leixing == 4) {
				addQianNeng(chara, chara.level * 1050 + (shuadao + 1) * 6000, "刷道");
				adddaohang(chara, (shuadao + 1) * 165 * 1440, "刷道");
			}
			//宠物武学
			Petbeibao pet = GameCommonUtil.getCurrentPet(chara);
			if(pet != null) {
				PetShuXing petShuXing = pet.petShuXing.get(0);
				
				int base_pet_dh = (int) (0.29 * petShuXing.skill * petShuXing.skill * petShuXing.skill) + 1;
				int intimacy = (int) (12 * petShuXing.skill * (1.0 + 0.2 * shuadao)
						/ ((petShuXing.intimacy > base_pet_dh) ? (petShuXing.intimacy / base_pet_dh) : 1) * beishu);
				if (chara.chongfengsan == 1 && chara.shuadaochongfeng_san > 0) {
					intimacy *= 1;
					chara.shuadaochongfeng_san -= 4;
					if (chara.shuadaochongfeng_san <= 0) {
						chara.shuadaochongfeng_san = 0;
					}
				}
				GameCommonUtil.addWuXue(chara, intimacy, pet, "刷道");
			}
			int use_money_type = (int) (159 * chara.level * (1.0 + 0.2 * shuadao) * beishu);
			chara.use_money_type += use_money_type;
			Vo_20481_0 vo_20481_2 = new Vo_20481_0();
			vo_20481_2.msg = "获得代金券#R" + use_money_type;
			vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
			gameObjectChar.sendOne(new M20481_0(), vo_20481_2);

			int cash = (int) ((int) (673 * chara.level * (1.0 + 0.2 * shuadao)) * beishu);
			chara.pot += cash;
			ListVo_65527_0 listVo_65527_0 = a65527(chara);
			gameObjectChar.sendOne(new M65527_0(), listVo_65527_0);
			// 降妖
			if (info.leixing == 2 && shuadao < 11) {
				chara.shudao.put(npc.id,npc);
				Vo_61553_0 vo_61553_0 = new Vo_61553_0();
				vo_61553_0.count = 1;
				vo_61553_0.task_type = "降妖";
				vo_61553_0.task_desc = "";
				vo_61553_0.task_prompt = "降妖#P" + name3 + "|" + renwuMonster2.getMapName() + "(" + renwuMonster2.getX()
						+ "," + renwuMonster2.getY() + ")|M=今天我要为民除害|$0#P";
				vo_61553_0.refresh = 1;
				vo_61553_0.task_end_time = 1567909190;
				vo_61553_0.attrib = 1;
				vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
				vo_61553_0.show_name = "降妖(" + shuadao + "/10)";
				vo_61553_0.task_extra_para = "";
				vo_61553_0.task_state = "chuyao";
				GameUtilRenWu.createTask(vo_61553_0, chara);
				Vo_45092_0 vo_45092_2 = new Vo_45092_0();
				vo_45092_2.task_name = "降妖";
				vo_45092_2.check_point = 40;
				gameObjectChar.sendOne(new M45092_0(), vo_45092_2);
				type = vo_61553_0.task_type;
				task_prompt = vo_61553_0.task_prompt;
			} else if (info.leixing == 3 && shuadao < 11) {
				chara.shudao.put(npc.id,npc);
				Vo_61553_0 vo_61553_0 = new Vo_61553_0();
				vo_61553_0.count = 1;
				vo_61553_0.task_type = "伏魔";
				vo_61553_0.task_desc = "";
				vo_61553_0.task_prompt = "降伏#P" + name3 + "|" + renwuMonster2.getMapName() + "(" + renwuMonster2.getX()
						+ "," + renwuMonster2.getY() + ")|M=今天我要为民除害|$0#P";
				vo_61553_0.refresh = 1;
				vo_61553_0.task_end_time = 1567909190;
				vo_61553_0.attrib = 1;
				vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
				vo_61553_0.show_name = "伏魔(" + shuadao + "/10)";
				vo_61553_0.task_extra_para = "";
				vo_61553_0.task_state = "fumo";
				GameUtilRenWu.createTask(vo_61553_0, chara);
				Vo_45092_0 vo_45092_0 = new Vo_45092_0();
				vo_45092_0.task_name = "伏魔";
				vo_45092_0.check_point = 40;
				gameObjectChar.sendOne(new M45092_0(), vo_45092_0);
				type = vo_61553_0.task_type;
				task_prompt = vo_61553_0.task_prompt;
			} else if (info.leixing == 4 && shuadao < 11) {
				chara.shudao.put(npc.id,npc);
				Vo_61553_0 vo_61553_0 = new Vo_61553_0();
				vo_61553_0.count = 1;
				vo_61553_0.task_type = "飞仙渡邪";
				vo_61553_0.task_desc = "";
				vo_61553_0.task_prompt = "渡邪#P" + name3 + "|" + renwuMonster2.getMapName() + "(" + renwuMonster2.getX()
						+ "," + renwuMonster2.getY() + ")|M=今天我要为民除害|$0#P";
				vo_61553_0.refresh = 1;
				vo_61553_0.task_end_time = 1567909190;
				vo_61553_0.attrib = 1;
				vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
				vo_61553_0.show_name = "飞仙渡邪(" + shuadao + "/10)";
				vo_61553_0.task_extra_para = "";
				vo_61553_0.task_state = "feixian";
				GameUtilRenWu.createTask(vo_61553_0, chara);
				Vo_45092_0 vo_45092_0 = new Vo_45092_0();
				vo_45092_0.task_name = "飞仙渡邪";
				vo_45092_0.check_point = 40;
				gameObjectChar.sendOne(new M45092_0(), vo_45092_0);
				type = vo_61553_0.task_type;
				task_prompt = vo_61553_0.task_prompt;
			} else {
				Iterator<Entry<Integer, Vo_APPEAR>> iterator = chara.shudao.entrySet().iterator();
				if (info.leixing == 2) {
					type = "降妖";
					task_prompt = "找#P通灵道人|M=【降妖】降拿妖怪#P领取降妖任务";
					show_name = "降妖";
					chara.shuadao = 1;
					if(iterator.hasNext()) {
						Entry<Integer, Vo_APPEAR> next = iterator.next();
						if(next.getValue().leixing == 2) {
							iterator.remove();
						}
					}
					//刷道轮数
//					GameCommonUtil.addCharaTrail(chara, "刷道轮次", 1, "降妖");
				}
				if (info.leixing == 3) {
					type = "伏魔";
					task_prompt = "找#P陆压真人|M=【伏魔】我这就去#P领取任务";
					show_name = "伏魔";
					chara.shuadao = 1;
					if(iterator.hasNext()) {
						Entry<Integer, Vo_APPEAR> next = iterator.next();
						if(next.getValue().leixing == 3) {
							iterator.remove();
						}
					}
					//刷道轮数
//					GameCommonUtil.addCharaTrail(chara, "刷道轮次", 1, "伏魔");
				}
				if (info.leixing == 4) {
					type = "飞仙渡邪";
					task_prompt = "找#P清微真人|M=【飞仙渡邪】我这就去#P领取任务";
					show_name = "飞仙渡邪";
					chara.shuadao = 1;
					if(iterator.hasNext()) {
						Entry<Integer, Vo_APPEAR> next = iterator.next();
						if(next.getValue().leixing == 4) {
							iterator.remove();
						}
					}
					//刷道轮数
//					GameCommonUtil.addCharaTrail(chara, "刷道轮次", 1, "飞仙");
				}
				GameUtilRenWu.createTask(type, task_prompt, show_name, chara);
			}
			// 刷道积分
			chara.shuadaoScore += bossNum;
		}
		//如果在当前地图刷新
		if(leaderTeam.mapName.equals(renwuMonster2.getMapName())) {
			//刷道
			GameObjectChar.sendduiwu(new MSG_APPEAR_MONSTER(), npc, leaderTeam.id);
		}
		// 只有队长才能自动寻路
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task_prompt,type), leaderTeam.id);

	}

	/**
	 * 除暴任务结算
	 * 
	 * @param leaderTeam
	 * @param teams
	 */
	public static void chubaorenwu(Chara leaderTeam, List<Chara> teams) {
		// 让怪物消失
		GameObjectChar.sendduiwu(new MSG_DISAPPEAR(), leaderTeam.npcchubao.get(0).id, leaderTeam.id);
		
		//随机取出一个和当前地图不一样的坐标
		RenwuMonster renwuMonster = GameData.that.baseRenwuMonsterService
				.getOneRandomRenwuMonsterByTypeNotReplace(new RenwuMonster(leaderTeam.mapName, 1));
		String name = renwuMonster.getName();
		Map map = GameData.that.baseMapService.findOneByName(renwuMonster.getMapName());

		// 添加新的除暴任务
		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.mapid = ((Map) map).getMapId();
		vo_65529_0.id = GameCommonUtil.generateBossId();
		vo_65529_0.x = renwuMonster.getX();
		vo_65529_0.y = renwuMonster.getY();
		vo_65529_0.icon = renwuMonster.getIcon();
		vo_65529_0.org_icon = renwuMonster.getIcon();
		vo_65529_0.portrait = renwuMonster.getIcon();
		vo_65529_0.name = name;
		vo_65529_0.level = leaderTeam.level;
		
		String task_prompt = "捉拿#P" + name + "|" + renwuMonster.getMapName() + "(" + renwuMonster.getX() + ","
				+ renwuMonster.getY() + ")|M=就是来抓你的|$0#P";
		
		for (Chara chara : teams) {
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
			if (gameObjectChar == null) {
				continue;
			}
			//连任务都没有就别提奖励了
			if(chara.taskMap.get("为民除暴") == null) {
				continue;
			}else {
				//限定的次数要比正常大
				if(chara.chubao>GameConfig.config.getBaseConfig().getChubaoNum()+1) {
					GameUtil.sendMeTips("你已完成今日除暴任务,暂无奖励。");
					continue;
				}
			}
			// 分配除暴道行
			int base_dh = (int) (0.29 * chara.level * chara.level * chara.level);
			// 这里的除暴次数必须是每个队员自己的除暴次数
			int owner_name = (int) (39 * chara.level * (1.0 + 0.2 * chara.chubao)
					/ ((chara.tao > base_dh) ? (chara.tao / base_dh) : 1));
			
			adddaohang(chara, owner_name, "刷道");
			//宠物武学
			GameCommonUtil.addWuXue(chara, 0, "刷道");
			int cash = chara.level * 2400;
			addQianNeng(chara, cash, "除暴");
			addJinbi(chara, 50000, "除暴");
			// 除暴给经验算法
			int jingyan = 3000 + chara.level * 1400 + (chara.chubao) * 2000;
			jingyan = shuangbei(chara, jingyan);
			huodejingyan(chara, jingyan, "刷道");
			ListVo_65527_0 listVo_65527_0 = a65527(chara);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65527_0(), listVo_65527_0);
			//删除任务
			chara.npcchubao = new ArrayList<Vo_APPEAR>();
			if(chara.chubao<GameConfig.config.getBaseConfig().getChubaoNum()) {
				chara.npcchubao.add(vo_65529_0);
				//正常玩家才要累计次数
				if(gameObjectChar.characters.getXiaozi() != null &&
						gameObjectChar.characters.getXiaozi() == 0) {
					chara.chubao += 1;
				}
				//创建任务
				String show_name2 = "为民除暴(" + chara.chubao + "/" + GameConfig.config.getBaseConfig().getChubaoNum() + ")";
				GameUtilRenWu.createTask("为民除暴", task_prompt, show_name2, chara);
			}else {
				GameUtilRenWu.removeTask("为民除暴",  chara);
				GameCommonUtil.sendTips("你已完成今日除暴", chara.id);
			}
		}
		//如果在当前地图刷新
		if(leaderTeam.mapName.equals(renwuMonster.getMapName())) {
			for (int i = 0; i < leaderTeam.npcchubao.size(); ++i) {
				if (leaderTeam.mapid == leaderTeam.npcchubao.get(i).mapid) {
					GameObjectChar.sendduiwu(new MSG_APPEAR_MONSTER(), leaderTeam.npcchubao.get(i), leaderTeam.id);
				}
			}
		}
		if(leaderTeam.taskMap.get("为民除暴") != null) {
			log.info("除暴任务开始自动寻路:{}",task_prompt);
			GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(task_prompt), leaderTeam.id);
		}
	}

	// 设置角色的称号消息
	public static void chenghaoxiaoxi(Chara chara, String key, String value) {
		//开始发送
		final ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
		final Chengwei newChengwei = cs.getChengweiByName(value);
		if (!StringUtils.isNullOrEmpty(key) && !StringUtils.isNullOrEmpty(value)) {
			chara.chenghao.put(key, value);
			CharaChengWei charaChengWei = new CharaChengWei();
			charaChengWei.setName(key);
			charaChengWei.setValue(value);
			if(newChengwei == null){
				charaChengWei.setTime(-1L);
			}else{
				charaChengWei.setTime((long)newChengwei.getTime());
			}

			charaChengWei.setCreateTime(new Date());
			chara.charaChengWeis.add(charaChengWei);
		}
		refreshChengHao(chara);
//		List<Vo_62209_0> list = new LinkedList<Vo_62209_0>();
//		Vo_62209_0 vo_62209_0 = new Vo_62209_0();
//		vo_62209_0.stringformat = "无显示";
//		vo_62209_0.title = "";
//		vo_62209_0.titled_left_time = 0;
//		list.add(vo_62209_0);

//		GameCommonUtil.refreshAppellAtion(chara);
//		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
//		gameObjectChar.sendOne(new M62209_0(), list);
//		GameUtil.a65511(gameObjectChar);
	}
	public static void refreshChengHao(Chara chara){
		List<Vo_62209_0> list = new LinkedList<Vo_62209_0>();
		Vo_62209_0 vo_62209_0 = new Vo_62209_0();
		vo_62209_0.stringformat = "无显示";
		vo_62209_0.title = "";
		vo_62209_0.titled_left_time = 0;
		list.add(vo_62209_0);
		List<CharaChengWei> charaChengWeis = chara.charaChengWeis;
		for (java.util.Map.Entry<String, String> entry : chara.chenghao.entrySet()) {
			vo_62209_0 = new Vo_62209_0();
			vo_62209_0.stringformat = entry.getKey();
			vo_62209_0.title = entry.getValue();
			for (CharaChengWei charaChengWei : charaChengWeis) {
				if(charaChengWei.getValue().equals(entry.getValue())){
					if(charaChengWei.getTime()>0){
						vo_62209_0.titled_left_time = (int) (charaChengWei.getCreateTime().getTime()/1000+60*charaChengWei.getTime());
					}
					break;
				}
			}
			list.add(vo_62209_0);
		}
		GameCommonUtil.refreshAppellAtion(chara);
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		gameObjectChar.sendOne(new M62209_0(), list);
		GameUtil.a65511(gameObjectChar);
	}
	
	/**
	 * 称号消息
	 * @param chara 玩家
	 * @param key 称号
	 * @param value 值
	 * @param time 时间
	 */
	public static void chenghaoxiaoxi(Chara chara, String key, String value, int time) {
		if (!StringUtils.isNullOrEmpty(key) && !StringUtils.isNullOrEmpty(value)) {
			chara.chenghao.put(key, value);
			CharaChengWei charaChengWei = new CharaChengWei();
			charaChengWei.setName(key);
			charaChengWei.setValue(value);
			charaChengWei.setTime((long)time);
			charaChengWei.setCreateTime(new Date());
			chara.charaChengWeis.add(charaChengWei);
		}

		List<Vo_62209_0> list = new LinkedList<Vo_62209_0>();
		Vo_62209_0 vo_62209_0 = new Vo_62209_0();
		vo_62209_0.stringformat = "无显示";
		vo_62209_0.title = "";
		vo_62209_0.titled_left_time = 0;
		list.add(vo_62209_0);
		List<CharaChengWei> charaChengWeis = chara.charaChengWeis;
		for (CharaChengWei charaChengWei : charaChengWeis) {
			vo_62209_0 = new Vo_62209_0();
			vo_62209_0.stringformat =charaChengWei.getName();
			vo_62209_0.title = charaChengWei.getValue();
			if(charaChengWei.getTime()>0){
				vo_62209_0.titled_left_time = (int) (System.currentTimeMillis()/1000L+60*charaChengWei.getTime());
			}
			list.add(vo_62209_0);
		}
		GameCommonUtil.refreshAppellAtion(chara);
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		gameObjectChar.sendOne(new M62209_0(), list);
		GameUtil.a65511(gameObjectChar);
	}

	// 判断角色的法宝栏是否穿戴法宝
	public static List<Object> zhandouisyoufabao(Chara chara) {
		List<Object> fabao = new LinkedList<>();
		for (int i = 0; i < chara.otherGoods.size(); ++i) {
			Goods goods = chara.otherGoods.get(i);
			if (goods.pos == 9) {
				fabao.add(goods.goodsInfo.str);
				fabao.add(goods.goodsInfo.skill);
				fabao.add(goods.goodsInfo.shape);
				break;
			}
		}
		return fabao;
	}

	// 获得法宝，指定法宝的名字和等级
	public static void huodefabao(Chara chara, String fabao, int fabaoLevel, String source, int... xiangxin) {
		int pos = packPoint(chara);
		if (pos == -1) {
			return;
		}
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(fabao);
		if (info == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不存在法宝：" + fabao;
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		}
		Goods goods = new Goods();
		goods.pos = pos;
		goods.goodsInfo = new GoodsInfo();
		if (info.getQuality() != null) {
			goods.goodsInfo.quality = info.getQuality();
		}
		if (info.getSilverCoin() != null) {
			goods.goodsInfo.silver_coin = info.getSilverCoin();
		}
		goods.goodsInfo.type = info.getType();
		goods.goodsInfo.attrib = 0;
		goods.goodsInfo.shape = 0;
		goods.goodsInfo.str = info.getName();
		goods.goodsInfo.nick = 0;
		goods.goodsInfo.recognize_recognized = info.getRecognizeRecognized();
		goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
		goods.goodsInfo.total_score = info.getTotalScore();
		goods.goodsInfo.rebuild_level = 50000;
		goods.goodsInfo.value = info.getValue();
		goods.goodsInfo.degree_32 = 0; // 【重要】法宝也是已鉴定
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.pot = 1;
		goods.goodsInfo.damage_sel_rate = 400976;
		goods.goodsInfo.diandqk_frozen_round = 3;
		goods.goodsInfo.skill = fabaoLevel;
		goods.goodsInfo.amount = 9;
		goods.goodsInfo.resist_poison = 1830;


		int xiang1= 0;
		if (fabao.equals("番天印"))
		{
			xiang1 = 0;
		}
		else if (fabao.equals("定海珠"))
		{
			xiang1 = 3;
		}
		else if (fabao.equals("混元金斗"))
		{
			xiang1 = 4;
		}
		

		goods.goodsInfo.shuadao_ziqihongmeng = xiangxin == null || xiangxin.length == 0 ?xiang1 + 1
				: xiangxin[0];
		chara.backpack.add(goods);
		GameObjectChar.send(new M65525_0(), chara.backpack, chara.id);
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 1;
		vo_40964_0.name = fabao;
		vo_40964_0.param = "20691134";
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0, chara.id);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得#R" + fabao;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0, chara.id);
		
		GameCommonUtil.addCharaTrail(chara, "法宝", fabao+","+ fabaoLevel, source);
	}

	 public static void MSG_UPDATE_ALL_a65511(final GameObjectChar gameObjectChar) {
        final Chara chara = gameObjectChar.chara;
        zhuangbeiValue(gameObjectChar);
        //装备属性
        gameObjectChar.sendOne(new M65511_0(), chara.zbAttribute);
        //角色属性信息
        final ListVo_65527_0 vo_65527_0 = GameUtil.buildCharaInfo_a65527(chara);
        gameObjectChar.sendOne(new M65527_0(), vo_65527_0);
        //更新外观
        final Vo_61661_0 vo_61661_0 = MSG_UPDATE_APPEARANCE_a61661(chara);
        gameObjectChar.sendOne(new M61661_0_MSG_UPDATE_APPEARANCE(), vo_61661_0);
        //更新角色技能信息
        final List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(chara);
        gameObjectChar.sendOne(new M32747_0(), vo_32747_0List);
    }

	/**
	 * 角色属性信息
	 */
	public static ListVo_65527_0 buildCharaInfo_a65527(final Chara chara, final String... event) {
		final ListVo_65527_0 vo_65527_0 = new ListVo_65527_0();
		BasicAttributesUtils.shuxing(chara);
//        log.info("属性信息:{}",JSON.toJSONString(chara));
	//	FightManager.autoAddLifeAndMana(chara);
		vo_65527_0.id = chara.id;
		vo_65527_0.vo_65527_0.str = chara.name;
		vo_65527_0.vo_65527_0.phy_power = chara.phy_power;
		vo_65527_0.vo_65527_0.accurate = chara.accurate;
		vo_65527_0.vo_65527_0.life = chara.life;
		vo_65527_0.vo_65527_0.max_life = chara.max_life;
		vo_65527_0.vo_65527_0.def = chara.def;
		vo_65527_0.vo_65527_0.wiz = chara.wiz;
		vo_65527_0.vo_65527_0.mag_power = chara.mag_power;
		vo_65527_0.vo_65527_0.mana = chara.mana;
		vo_65527_0.vo_65527_0.max_mana = chara.max_mana;
		vo_65527_0.vo_65527_0.dex = chara.dex;
		vo_65527_0.vo_65527_0.speed = chara.speed;
		vo_65527_0.vo_65527_0.parry = chara.parry;
		vo_65527_0.vo_65527_0.wood = chara.metal;
		vo_65527_0.vo_65527_0.water = chara.wood;
		vo_65527_0.vo_65527_0.fire = chara.water;
		vo_65527_0.vo_65527_0.earth = chara.fire;
		vo_65527_0.vo_65527_0.resist_metal = chara.earth;
		vo_65527_0.vo_65527_0.polar_point = chara.attribPoint;
		vo_65527_0.vo_65527_0.stamina = chara.polarPoint;
		vo_65527_0.vo_65527_0.friend = chara.tao;
		vo_65527_0.vo_65527_0.owner_name = chara.taoPoint;
		vo_65527_0.vo_65527_0.attrib_point = 0;
		vo_65527_0.vo_65527_0.metal = chara.polar;
		vo_65527_0.vo_65527_0.resist_wood = 0;
		vo_65527_0.vo_65527_0.resist_water = 0;
		vo_65527_0.vo_65527_0.resist_fire = 0;
		vo_65527_0.vo_65527_0.resist_earth = 0;
		vo_65527_0.vo_65527_0.exp_to_next_level = 0;
		//vo_65527_0.vo_65527_0.exp_to_next_level = 0;
		vo_65527_0.vo_65527_0.max_stamina = 1000;
		vo_65527_0.vo_65527_0.tao = 105;
		vo_65527_0.vo_65527_0.mon_tao_ex = 0;
		vo_65527_0.vo_65527_0.last_mon_tao = 0;
		vo_65527_0.vo_65527_0.last_mon_tao_ex = 0;
		vo_65527_0.vo_65527_0.mon_martial = 0;
		vo_65527_0.vo_65527_0.degree = 0;
		vo_65527_0.vo_65527_0.exp = chara.exp;
		vo_65527_0.vo_65527_0.pot = chara.pot;
		vo_65527_0.vo_65527_0.cash = chara.cash;
		vo_65527_0.vo_65527_0.balance = 0;
		vo_65527_0.vo_65527_0.gender = chara.sex;
		vo_65527_0.vo_65527_0.max_balance = 2000000000;
		vo_65527_0.vo_65527_0.ignore_resist_metal = 2000000000;
		//vo_65527_0.vo_65527_0.gender_n = chara.sex;
		//vo_65527_0.vo_65527_0.master_n = "";
		vo_65527_0.vo_65527_0.status_daofa_wubian = "";
		vo_65527_0.vo_65527_0.nick = 0;
		vo_65527_0.vo_65527_0.family_title = "";
		vo_65527_0.vo_65527_0.title = "";
		vo_65527_0.vo_65527_0.nice = chara.chenhao;
		vo_65527_0.vo_65527_0.reputation = 0;
		//vo_65527_0.vo_65527_0.couple = chara.arenaInfo.getShengWang() == 0 ? 0 : chara.arenaInfo.getShengWang();
		//声望;;
		vo_65527_0.vo_65527_0.icon = "";
		vo_65527_0.vo_65527_0.type = chara.waiguan;
		vo_65527_0.vo_65527_0.resist_poison = chara.expToNextLevel;
		vo_65527_0.vo_65527_0.item_unique = 0;
		vo_65527_0.vo_65527_0.passive_mode = chara.waiguan;
		vo_65527_0.vo_65527_0.req_str = chara.chenhao;
		vo_65527_0.vo_65527_0.locked = 0;
		vo_65527_0.vo_65527_0.extra_desc = 0;
		vo_65527_0.vo_65527_0.silverCoin = chara.silverCoin;
		vo_65527_0.vo_65527_0.extra_life = chara.goldCoin;
		vo_65527_0.vo_65527_0.extra_mana = chara.extra_mana;
		vo_65527_0.vo_65527_0.have_coin_pwd = chara.have_coin_pwd;
		vo_65527_0.vo_65527_0.max_req_level = 0;
		//vo_65527_0.vo_65527_0.max_req_level = 0;
		vo_65527_0.vo_65527_0.use_skill_d = chara.use_skill_d;
		vo_65527_0.vo_65527_0.double_points = chara.charashuangbei;
		vo_65527_0.vo_65527_0.enable_double_points = chara.enable_double_points;
		vo_65527_0.vo_65527_0.can_buy_dp_times = chara.charashuangbei;
		vo_65527_0.vo_65527_0.enable_shenmu_points = chara.enable_shenmu_points;
		vo_65527_0.vo_65527_0.gift_key = chara.shenmoding;
		vo_65527_0.vo_65527_0.online = 0;
		vo_65527_0.vo_65527_0.free_rename = ((chara.autofight_select != 0) ? 1 : 0);
	//	vo_65527_0.vo_65527_0.voucher = chara.free_rename;
	//	vo_65527_0.vo_65527_0.use_money_type = chara.voucher;
		vo_65527_0.vo_65527_0.lock_exp = chara.use_money_type;//;
		vo_65527_0.vo_65527_0.shuadaojiji_rulvling = chara.lock_exp;
		vo_65527_0.vo_65527_0.party_name = ((chara.getPartyName() == null) ? "" : chara.getPartyName());
		vo_65527_0.vo_65527_0.partyJob = chara.getPartyJob();
		vo_65527_0.vo_65527_0.party_contrib = chara.getContrib();
		vo_65527_0.vo_65527_0.shuadaochongfeng_san = chara.shuadaochongfeng_san;
		vo_65527_0.vo_65527_0.equip_identify = 0;
		vo_65527_0.vo_65527_0.reputation = 0;
		vo_65527_0.vo_65527_0.recharge = 10;
		vo_65527_0.vo_65527_0.shadow_self = chara.getShadow_self();
		vo_65527_0.vo_65527_0.extra_life_effect = 0;
		vo_65527_0.vo_65527_0.desc = 0;
		vo_65527_0.vo_65527_0.enchant = 0;
		vo_65527_0.vo_65527_0.higest_feixdx = 0;
		//vo_65527_0.vo_65527_0.ct_datascore = 1559291151;

		vo_65527_0.vo_65527_0.marriagemarry_id = chara.marriageMarryId;
	//	vo_65527_0.vo_65527_0.shuadao_jiji_rulvling = chara.jijirulvling;
	//	vo_65527_0.vo_65527_0.shuadao_ziqihongmeng = chara.ziqihongmeng;
	//	vo_65527_0.vo_65527_0.shuadao_ruyi_point = chara.ruyishuadao;

		vo_65527_0.vo_65527_0.settingrefuse_stranger_level = chara.settingrefuse_stranger_level;
		vo_65527_0.vo_65527_0.settingauto_reply_msg = ((chara.settingauto_reply_msg == null) ? "" : chara.settingauto_reply_msg);
		vo_65527_0.vo_65527_0.setting_refuse_be_add_level = chara.setting_refuse_be_add_level;
		vo_65527_0.vo_65527_0.mount_attrib_end_time = 20;
	//	vo_65527_0.vo_65527_0.ct_data_top_rank = chara.leitaiScore;
	//	vo_65527_0.vo_65527_0.real_desc = chara.leitaiRank;

		vo_65527_0.vo_65527_0.bully_kill_num = 0;
		vo_65527_0.vo_65527_0.police_kill_num = 0;
		vo_65527_0.vo_65527_0.gm_attribsmax_life = 0;
		vo_65527_0.vo_65527_0.gm_attribsmax_mana = 0;
		vo_65527_0.vo_65527_0.gm_attribsphy_power = 0;
		vo_65527_0.vo_65527_0.gm_attribsmag_power = 0;
		vo_65527_0.vo_65527_0.gm_attribsdef = 0;
		vo_65527_0.vo_65527_0.gm_attribsspeed = 0;
		//vo_65527_0.vo_65527_0.brother_appellation = "";
		vo_65527_0.vo_65527_0.artifact_upgraded_enabled = 0;
		vo_65527_0.vo_65527_0.house_house_class = "";
		vo_65527_0.vo_65527_0.plant_level = 0;
		//vo_65527_0.vo_65527_0.plant_level = 0;
		vo_65527_0.vo_65527_0.phy_power_without_intimacy = 0;
		vo_65527_0.vo_65527_0.plant_exp = 0;
		vo_65527_0.vo_65527_0.marriage_couple_gid = "";
		vo_65527_0.vo_65527_0.strengthen_jewelry_num = "";
		vo_65527_0.vo_65527_0.soul_state = 0;
		vo_65527_0.vo_65527_0.transform_num = 0;
		vo_65527_0.vo_65527_0.fasion_effect_disable = 0;
		//vo_65527_0.vo_65527_0.strengthen_level = 4;
		vo_65527_0.vo_65527_0.strengthen_level = 0;
		vo_65527_0.vo_65527_0.status_diliebo_flag = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_lock_time = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_exp_ware = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_fetch_times = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_today_fetch_times = 0;
		vo_65527_0.vo_65527_0.level = chara.upgrade_state == 0 ? chara.level : chara.realLevel;
		vo_65527_0.vo_65527_0.marriage_book_id = "AA15545545125";
		vo_65527_0.vo_65527_0.marriage_start_time = (int) (chara.marriageTime / 1000L);
		if (event.length > 0) {
			final String type = event[0];
			if (type.equals("yuanyingAddPoint")) {
				vo_65527_0.vo_65527_0.level = chara.upgrade_level;
			} else if ("openUserTab_yuanying".equals(type)) {
				vo_65527_0.vo_65527_0.level = chara.realLevel;
			}
		}
//        vo_65527_0.vo_65527_0.has_upgraded = 1;
		vo_65527_0.vo_65527_0.upgrade_level = chara.upgrade_level;
		vo_65527_0.vo_65527_0.upgrade_type = chara.upgrade_type;
		vo_65527_0.vo_65527_0.upgrade_exp = chara.upgrade_exp;
		vo_65527_0.vo_65527_0.upgrade_exp_to_next_level = chara.upgrade_exp_to_next_level;
		//元婴/真身状态
		vo_65527_0.vo_65527_0.upgrade_state = chara.upgrade_state;
		vo_65527_0.vo_65527_0.upgrade_max_polar_extra = chara.upgrade_max_polar_extra;
		vo_65527_0.vo_65527_0.upgradeImmortal = chara.upgrade_immortal;
		vo_65527_0.vo_65527_0.upgrade_magic = chara.upgrade_magic;
		vo_65527_0.vo_65527_0.upgrade_total = chara.upgrade_total;

		if (chara.shenHunDataSate == 0) {
			chara.shenHunDataSate = 1;
		}
		vo_65527_0.vo_65527_0.shenHunDataSate = chara.shenHunDataSate;
		vo_65527_0.vo_65527_0.shenHunDataLayer = chara.shenHunDataLaye;
		//vo_65527_0.vo_65527_0.shenHunDataExp = chara.shenHunDataExp;
		vo_65527_0.vo_65527_0.shenHunDataExpToNextLevel = GameConfig.shenHunConfig.getData().get(String.valueOf(chara.shenHunDataSate)).getIntValue("jifen");
		//vo_65527_0.vo_65527_0.not_check_bw = 1;//chara.danDataState;

		vo_65527_0.vo_65527_0.dan_data_state = chara.danDataState;
		vo_65527_0.vo_65527_0.dan_data_stage = chara.danDataStage;
		vo_65527_0.vo_65527_0.dan_data_exp = chara.danDataExp;
		vo_65527_0.vo_65527_0.dan_data_exp_to_next_level = chara.danDataExpToNextLevel = chara.danDataState * chara.danDataStage * 1000;
	//	int danAttribPoint = GameConfig.config.getBaseConfig().getDanAttribPoint();
		//danPolarPoint = GameConfig.config.getBaseConfig().getDanPolarPoint();
	//	vo_65527_0.vo_65527_0.dan_data_attrib_point = chara.danDataAttribPoint = chara.danDataState * chara.danDataStage * danAttribPoint;
	//	vo_65527_0.vo_65527_0.dan_data_polar_point = chara.danDataPolarPoint = chara.danDataState * chara.danDataStage * danPolarPoint;
	//	vo_65527_0.vo_65527_0.dan_data_today_exp = chara.danDataExp;//1000 - chara.danDataExp
		//todo 神魂突破加成
//        if (StrUtil.containsAny(chara.mapName, FightUtil.difuMap)) {
		vo_65527_0.vo_65527_0.mana += chara.shenHunMagPower;
		vo_65527_0.vo_65527_0.accurate += chara.shenHunPhyPower;
		vo_65527_0.vo_65527_0.wiz += chara.shenHunDef;
		vo_65527_0.vo_65527_0.parry += chara.shenHunSpeed;
		vo_65527_0.vo_65527_0.def += chara.shenHunmaxLife;
//            vo_65527_0.vo_65527_0.max_life += chara.shenHunmaxLife;
//        }
		//变身卡效果
		final VoChangeCard changeCardInfo = chara.getChangeCardInfo();
		if (changeCardInfo != null) {
			final List<ChangeCardAttr> attrs = changeCardInfo.getAttr();
			if (attrs != null && !attrs.isEmpty()) {
				for (final ChangeCardAttr a : attrs) {
					final String field;
					switch ((field = a.getField()).hashCode()) {
						case -1162364729: {
							if (!field.equals("phy_power")) {
								continue;
							}
							final Vo_65527_0 vo_65527_8 = vo_65527_0.vo_65527_0;
							vo_65527_8.accurate += vo_65527_0.vo_65527_0.accurate * a.getValue() / 100;
							continue;
						}
						case 99333: {
							if (!field.equals("def")) {
								continue;
							}
							final Vo_65527_0 vo_65527_9 = vo_65527_0.vo_65527_0;
							vo_65527_9.wiz += vo_65527_0.vo_65527_0.wiz * a.getValue() / 100;
							continue;
						}
						case 109641799: {
							if (!field.equals("speed")) {
								continue;
							}
							final Vo_65527_0 vo_65527_10 = vo_65527_0.vo_65527_0;
							vo_65527_10.parry += vo_65527_0.vo_65527_0.parry * a.getValue() / 100;
							continue;
						}
						case 407863543: {
							if (!field.equals("max_life")) {
								continue;
							}
							final Vo_65527_0 vo_65527_11 = vo_65527_0.vo_65527_0;
							vo_65527_11.def += vo_65527_0.vo_65527_0.def * a.getValue() / 100;
							continue;
						}
						case 407885890: {
							if (!field.equals("max_mana")) {
								continue;
							}
							final Vo_65527_0 vo_65527_12 = vo_65527_0.vo_65527_0;
							vo_65527_12.dex += vo_65527_0.vo_65527_0.dex * a.getValue() / 100;
							continue;
						}
						case 1855063833: {
							if (!field.equals("mag_power")) {
								continue;
							}
							final Vo_65527_0 vo_65527_13 = vo_65527_0.vo_65527_0;
							vo_65527_13.mana += vo_65527_0.vo_65527_0.mana * a.getValue() / 100;
							continue;
						}
						default: {
							continue;
						}
					}
				}
			}
		}
		vo_65527_0.vo_65527_0.jewelry_essence = chara.jewelry_essence;
		vo_65527_0.vo_65527_0.equipPage = chara.equipPage;
		vo_65527_0.vo_65527_0.chengwei = chara.chenhao;
		vo_65527_0.vo_65527_0.zhenlingLevel = chara.zhenlingLevel;
		vo_65527_0.vo_65527_0.zhenlingType = chara.zhenlingType;
		vo_65527_0.vo_65527_0.accurate += chara.zhenlingPhy;
		vo_65527_0.vo_65527_0.mana += chara.zhenlingMag;
		vo_65527_0.vo_65527_0.parry += chara.zhenlingSpeed;
		vo_65527_0.vo_65527_0.wiz += chara.zhenlingDef;
		if (chara.zhenlingType == 1) {
			final int mana = (int) (vo_65527_0.vo_65527_0.mana * (GameConfig.spiritInfoConfig.get((chara.qinglongZhenlingLevel - 1 < 0) ? 0 : (chara.qinglongZhenlingLevel - 1))).getAtt()[0] / 100.0);
			final Vo_65527_0 vo_65527_18 = vo_65527_0.vo_65527_0;
			vo_65527_18.mana += mana;
			final Vo_65527_0 vo_65527_19 = vo_65527_0.vo_65527_0;
			vo_65527_19.accurate += (vo_65527_0.vo_65527_0.accurate * (GameConfig.spiritInfoConfig.get((chara.baihuhenlingLevel - 1 < 0) ? 0 : (chara.baihuhenlingLevel - 1))).getAtt()[1] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_20 = vo_65527_0.vo_65527_0;
			vo_65527_20.parry += (vo_65527_0.vo_65527_0.parry * (GameConfig.spiritInfoConfig.get((chara.zhuqueZhenlingLevel - 1 < 0) ? 0 : (chara.zhuqueZhenlingLevel - 1))).getAtt()[2] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_21 = vo_65527_0.vo_65527_0;
			vo_65527_21.wiz += (vo_65527_0.vo_65527_0.wiz * (GameConfig.spiritInfoConfig.get((chara.xuanwuZhenlingLevel - 1 < 0) ? 0 : (chara.xuanwuZhenlingLevel - 1))).getAtt()[3] / 100.0 / 2.0);
		} else if (chara.zhenlingType == 2) {
			final Vo_65527_0 vo_65527_22 = vo_65527_0.vo_65527_0;
			vo_65527_22.accurate += (vo_65527_0.vo_65527_0.accurate * (GameConfig.spiritInfoConfig.get((chara.baihuhenlingLevel - 1 < 0) ? 0 : (chara.baihuhenlingLevel - 1))).getAtt()[1] / 100.0);
			final Vo_65527_0 vo_65527_23 = vo_65527_0.vo_65527_0;
			vo_65527_23.mana += (vo_65527_0.vo_65527_0.mana * (GameConfig.spiritInfoConfig.get((chara.qinglongZhenlingLevel - 1 < 0) ? 0 : (chara.qinglongZhenlingLevel - 1))).getAtt()[0] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_24 = vo_65527_0.vo_65527_0;
			vo_65527_24.parry += (vo_65527_0.vo_65527_0.parry * (GameConfig.spiritInfoConfig.get((chara.zhuqueZhenlingLevel - 1 < 0) ? 0 : (chara.zhuqueZhenlingLevel - 1))).getAtt()[2] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_25 = vo_65527_0.vo_65527_0;
			vo_65527_25.wiz += (vo_65527_0.vo_65527_0.wiz * (GameConfig.spiritInfoConfig.get((chara.xuanwuZhenlingLevel - 1 < 0) ? 0 : (chara.xuanwuZhenlingLevel - 1))).getAtt()[3] / 100.0 / 2.0);
		} else if (chara.zhenlingType == 3) {
			final Vo_65527_0 vo_65527_26 = vo_65527_0.vo_65527_0;
			vo_65527_26.parry += (vo_65527_0.vo_65527_0.parry * (GameConfig.spiritInfoConfig.get((chara.zhuqueZhenlingLevel - 1 < 0) ? 0 : (chara.zhuqueZhenlingLevel - 1))).getAtt()[2] / 100.0);
			final Vo_65527_0 vo_65527_27 = vo_65527_0.vo_65527_0;
			vo_65527_27.accurate += (vo_65527_0.vo_65527_0.accurate * (GameConfig.spiritInfoConfig.get((chara.baihuhenlingLevel - 1 < 0) ? 0 : (chara.baihuhenlingLevel - 1))).getAtt()[1] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_28 = vo_65527_0.vo_65527_0;
			vo_65527_28.mana += (vo_65527_0.vo_65527_0.mana * (GameConfig.spiritInfoConfig.get((chara.qinglongZhenlingLevel - 1 < 0) ? 0 : (chara.qinglongZhenlingLevel - 1))).getAtt()[0] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_29 = vo_65527_0.vo_65527_0;
			vo_65527_29.wiz += (vo_65527_0.vo_65527_0.wiz * (GameConfig.spiritInfoConfig.get((chara.xuanwuZhenlingLevel - 1 < 0) ? 0 : (chara.xuanwuZhenlingLevel - 1))).getAtt()[3] / 100.0 / 2.0);
		} else if (chara.zhenlingType == 4) {
			final Vo_65527_0 vo_65527_30 = vo_65527_0.vo_65527_0;
			vo_65527_30.wiz += (vo_65527_0.vo_65527_0.wiz * (GameConfig.spiritInfoConfig.get((chara.xuanwuZhenlingLevel - 1 < 0) ? 0 : (chara.xuanwuZhenlingLevel - 1))).getAtt()[3] / 100.0);
			final Vo_65527_0 vo_65527_31 = vo_65527_0.vo_65527_0;
			vo_65527_31.accurate += (vo_65527_0.vo_65527_0.accurate * (GameConfig.spiritInfoConfig.get((chara.baihuhenlingLevel - 1 < 0) ? 0 : (chara.baihuhenlingLevel - 1))).getAtt()[1] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_32 = vo_65527_0.vo_65527_0;
			vo_65527_32.mana += (vo_65527_0.vo_65527_0.mana * (GameConfig.spiritInfoConfig.get((chara.qinglongZhenlingLevel - 1 < 0) ? 0 : (chara.qinglongZhenlingLevel - 1))).getAtt()[0] / 100.0 / 2.0);
			final Vo_65527_0 vo_65527_33 = vo_65527_0.vo_65527_0;
			vo_65527_33.parry += (vo_65527_0.vo_65527_0.parry * (GameConfig.spiritInfoConfig.get((chara.zhuqueZhenlingLevel - 1 < 0) ? 0 : (chara.zhuqueZhenlingLevel - 1))).getAtt()[2] / 100.0 / 2.0);
		}
		return vo_65527_0;
	}

	public static Vo_61661_0 MSG_UPDATE_APPEARANCE_a61661(final Chara chara, final String... event) {
		final Vo_61661_0 vo_61661_0 = new Vo_61661_0();
		vo_61661_0.id = chara.id;
		vo_61661_0.x = chara.x;
		vo_61661_0.y = chara.y;
		vo_61661_0.dir = chara.dir;
		vo_61661_0.icon = chara.waiguan;
		vo_61661_0.weapon_icon = chara.weapon_icon;
		vo_61661_0.type = 1;
		vo_61661_0.sub_type = 0;
		vo_61661_0.owner_id = 0;
		vo_61661_0.leader_id = 0;
		vo_61661_0.name = chara.name;
		vo_61661_0.level = chara.level;
		vo_61661_0.title = chara.chenhao;
		vo_61661_0.family = chara.chenhao;
		vo_61661_0.partyname = chara.getPartyName();
		vo_61661_0.status = 0;
		vo_61661_0.special_icon = chara.special_icon;
		vo_61661_0.org_icon = getWaiguan(chara.polar, chara.sex, chara);
		vo_61661_0.suit_icon = chara.suit_icon;
		if (chara.effectIcons == null) {
			chara.effectIcons = new HashMap<String, Integer>();
		}
		if (!StringUtils.isNullOrEmpty(chara.chenhao)) {
			Chengwei chengweiByName = GameData.that.chengweiService.getChengweiByName(chara.chenhao);
			if (chengweiByName != null && chengweiByName.getIcon() != null) {
				chara.effectIcons.put("chengweiEffectIcon", chengweiByName.getIcon());
			}else {
				chara.effectIcons.remove("chengweiEffectIcon");
			}
		}else {
			chara.effectIcons.remove("chengweiEffectIcon");
		}
		chara.effectIcons.remove("fasionEffectIcon");
		vo_61661_0.suit_light_effect = chara.suit_light_effect;
		vo_61661_0.mount_icon = chara.zuowaiguan;
		vo_61661_0.guard_icon = 0;
		vo_61661_0.pet_icon = chara.zuoqiwaiguan;
		vo_61661_0.shadow_icon = 0;
		vo_61661_0.shelter_icon = 0;
		vo_61661_0.alicename = "";
		vo_61661_0.gid = chara.uuid;
		vo_61661_0.camp = "";
		vo_61661_0.vip_type = chara.vipType;
		final GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		vo_61661_0.isHide = ((gameObjectChar == null) ? 0 : gameObjectChar.isHide);
		vo_61661_0.opacity = 0;
		vo_61661_0.moveSpeedPercent = chara.yidongsudu;
		vo_61661_0.score = 0;
		vo_61661_0.masquerade = 0;
		vo_61661_0.obstacle = 0;
		vo_61661_0.upgradestate = chara.upgrade_state;
		vo_61661_0.upgradetype = chara.upgrade_type;
		vo_61661_0.effect = chara.effectIcons;
		vo_61661_0.partyTitle = "default006.png";
		final VoChangeCard changeCardInfo = chara.changeCardInfo;
		if (changeCardInfo != null && vo_61661_0.special_icon == 0) {
			vo_61661_0.special_icon = changeCardInfo.getIcon();
		} else if (chara.upgrade_state != 0 && vo_61661_0.special_icon == 0) {
			if (chara.upgrade_type == 1 || chara.upgrade_type == 3) {
				vo_61661_0.special_icon = 7008;
			} else {
				vo_61661_0.special_icon = 7009;
			}
		} else {
			vo_61661_0.special_icon = chara.special_icon;
		}
		if (event != null && event.length > 0) {
			if (chara.upgrade_state != 0) {
				vo_61661_0.level = chara.upgrade_level;
			}
		} else if (chara.upgrade_state != 0) {
			vo_61661_0.level = chara.upgrade_level;
		}
		vo_61661_0.customIcon = chara.customIcon;
		vo_61661_0.teamIcon = chara.teamIcon;

		vo_61661_0.extra_scale = 0;
		vo_61661_0.gather_suit_icons = new ArrayList<>();
		vo_61661_0.ban_rule = "";
		vo_61661_0.title_ban_rule = "";
		vo_61661_0.x_offset = 0;
		vo_61661_0.y_offset = 0;
//		vo_61661_0.moveType = chara.getMoveType();
//		vo_61661_0.flyType = chara.getFlyType();
//		vo_61661_0.moveIds = chara.getMoveIds();
		return vo_61661_0;
	}

	// 获得法宝，指定法宝的名字和等级，分配指定相性
	public static void jifenhuodefabao(Chara chara, String fabao, int fabaoLevel, String source, int xiangxing) {
		int pos = packPoint(chara);
		if (pos == -1) {
			return;
		}

		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(fabao);
		if (info == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不存在法宝：" + fabao;
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		}
		Goods goods = new Goods();
		goods.pos = pos;
		goods.goodsInfo = new GoodsInfo();
		if (info.getQuality() != null) {
			goods.goodsInfo.quality = info.getQuality();
		}
		if (info.getSilverCoin() != null) {
			goods.goodsInfo.silver_coin = info.getSilverCoin();
		}
		goods.goodsInfo.type = info.getType();
		goods.goodsInfo.attrib = 0;
		goods.goodsInfo.shape = 0;
		goods.goodsInfo.str = info.getName();
		goods.goodsInfo.nick = 0;
		goods.goodsInfo.recognize_recognized = info.getRecognizeRecognized();
		goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
		goods.goodsInfo.total_score = info.getTotalScore();
		goods.goodsInfo.rebuild_level = 50000;
		goods.goodsInfo.value = info.getValue();
		goods.goodsInfo.degree_32 = 0; // 【重要】法宝也是已鉴定
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.pot = 1;
		goods.goodsInfo.damage_sel_rate = pos;
		goods.goodsInfo.diandqk_frozen_round = 3;
		goods.goodsInfo.skill = fabaoLevel;
		goods.goodsInfo.amount = 9;
		goods.goodsInfo.resist_poison = 1830;
		goods.goodsInfo.shuadao_ziqihongmeng = xiangxing;
		chara.backpack.add(goods);
		GameObjectChar.send(new M65525_0(), chara.backpack, chara.id);
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 1;
		vo_40964_0.name = fabao;
		vo_40964_0.param = "20691134";
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0, chara.id);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得#R" + fabao;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0, chara.id);
		GameCommonUtil.addCharaTrail(chara, "法宝", fabao+","+fabaoLevel, source);
	}

	// 刷法宝，龙王换法宝的地方
	public static void shuafabao(Chara chara, String source) {
		int pos = packPoint(chara);
		if (pos == -1) {
			return;
		}
		String[] fb = { "番天印", "定海珠", "混元金斗", "阴阳镜", "九龙神火罩", "卸甲金葫" };
		Random random = new Random();
		int i = random.nextInt(5);
		String fabao = fb[random.nextInt(fb.length)];
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(fabao);
		Goods goods = new Goods();
		goods.pos = pos;
		goods.goodsInfo = new GoodsInfo();
		if (info.getQuality() != null) {
			goods.goodsInfo.quality = info.getQuality();
		}
		if (info.getSilverCoin() != null) {
			goods.goodsInfo.silver_coin = info.getSilverCoin();
		}
		goods.goodsInfo.type = info.getType();
		goods.goodsInfo.attrib = 0;
		goods.goodsInfo.shape = 0;
		goods.goodsInfo.str = info.getName();
		goods.goodsInfo.nick = 0;
		goods.goodsInfo.recognize_recognized = info.getRecognizeRecognized();
		goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
		goods.goodsInfo.total_score = info.getTotalScore();
		goods.goodsInfo.rebuild_level = 50000;
		goods.goodsInfo.value = info.getValue();
		goods.goodsInfo.degree_32 = 0; // 【重要】法宝也是已鉴定
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.pot = 1;
		goods.goodsInfo.damage_sel_rate = pos;
		goods.goodsInfo.diandqk_frozen_round = 3;
		goods.goodsInfo.skill = 1;
		goods.goodsInfo.amount = 9;
		goods.goodsInfo.resist_poison = 1830;
		goods.goodsInfo.shuadao_ziqihongmeng = i + 1;
		chara.backpack.add(goods);
		GameObjectChar.send(new M65525_0(), chara.backpack);
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 1;
		vo_40964_0.name = fabao;
		vo_40964_0.param = "20691134";
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得#R" + fabao;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0);
		
		GameCommonUtil.addCharaTrail(chara, "法宝", fabao+","+goods.goodsInfo.skill, source);
	}

	// 积分获得未鉴定
	public static void jifenweijianding(GameObjectChar gameObjectChar, int type, int count) {
		Chara chara = gameObjectChar.chara;
		Random random = new Random();
		// 四件装备的类型
		int[] eqType = { 1, 2, 10, 3 };
		int leixing = eqType[random.nextInt(4)];
		String zhuangbname = zhuangbname(chara, leixing);
		ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		jifenhuodezhuangbei(gameObjectChar, zhuangbeiInfo, type, count);
	}

	// 获得未鉴定装备
	public static void weijianding(Chara chara) {
		Random random = new Random();
		// 四件装备的类型
		int[] eqType = { 1, 2, 10, 3 };
		int leixing = eqType[random.nextInt(4)];
		String zhuangbname = zhuangbname(chara, leixing);
		ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(zhuangbname);
		huodezhuangbei(chara, zhuangbeiInfo, 1, 1);
	}

	// 这里是给主线任务的任务奖励
	public static void renwujiangli(Chara chara) {
		Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
		String reward = renwu.getReward(); // 获得任务奖励
		String[] split = reward.split("\\,");
		for (int i = 0; i < split.length; ++i) {
			String[] jiangli = split[i].split("\\#");
			huodechoujiang(jiangli, GameObjectCharMng.getGameObjectChar(chara.id), "主线");
		}
	}

	public static String nextrenw(String str) {
		String substring = str.substring(9, str.length());
		int next = Integer.valueOf(substring) + 1;
		String substring2 = str.substring(0, 9); // 值为"主线—浮生若梦_s"
		String renwu = substring2 + next; // 这里是带上任务序号了
		Renwu serviceOneByCurrentTask = GameData.that.baseRenwuService.findOneByCurrentTask(renwu);
		if (renwu.equals("主线—浮生若梦_s23")) { // 这里目前做到主线—浮生若梦_s22就结束了
			return "";
		}
		if (serviceOneByCurrentTask.getNpcName() != null && serviceOneByCurrentTask.getNpcName().equals("跳")) {
			return nextrenw(renwu);
		}
		return renwu;
	}

	public static void removemoney(Chara chara, int monet) {
		if (chara.lock_exp == 0) {
			chara.cash -= monet;
		} else {
			chara.cash -= monet;
		}
		ListVo_65527_0 listVo_65527_0 = a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0);
	}

	public static void addVip(Chara chara) {
		if (chara.vipTimeShengYu > 0) {
			int time = chara.vipTimeShengYu;
			time -= (int)System.currentTimeMillis()/1000L;
			if(time<=0) {
				chara.vipTimeShengYu = 0;
				//取消vip
				chara.vipType = 0;
			}
		}
		Vo_53257_0 vo_53257_0 = new Vo_53257_0();
		vo_53257_0.vipType = chara.vipType;
		vo_53257_0.leftTime = 0;
		vo_53257_0.curTime = chara.vipTimeShengYu;
		vo_53257_0.isGet = chara.isGet;
		vo_53257_0.tempInsider = 0;
		GameObjectChar.send(new M53257_0(), vo_53257_0);
	}

	public static Vo_APPEAR followPet(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.x = chara.x;
		vo_65529_0.y = chara.y;
		vo_65529_0.dir = 5;
		vo_65529_0.type = 0x8000;
		vo_65529_0.sub_type = 2;
		vo_65529_0.owner_id = chara.id;
		vo_65529_0.name = "";
		vo_65529_0.id = chara.genchong_icon;
		vo_65529_0.icon = chara.genchong_icon;
		vo_65529_0.org_icon = chara.genchong_icon;
		vo_65529_0.portrait = chara.genchong_icon;
		if(gameObjectChar.flyType > 0) {
			return null;
		}
		return vo_65529_0; 
	}
	
	public static void genchongfei(GameObjectChar gameObjectChar) {
		if (gameObjectChar != null && gameObjectChar.chara.genchong_icon != 0) {
			Vo_APPEAR followPet = followPet(gameObjectChar);
			if(followPet != null) {
				gameObjectChar.gameMap.send(new M65529_0(), followPet);
			}
		}
	}

	// 给宠物增加经验
	public static void addpetjingyan(Petbeibao petbeibao, int jingyan, Chara chara) {

		PetShuXing petShuXing = petbeibao.petShuXing.get(0);
		petShuXing.pot += jingyan;

		// 判断宠物是否大于角色15级
		if (petShuXing.skill - 15 >= chara.level) {
			Vo_16383_0 vo_16383_2 = GameUtil.a16383(chara, String.join("", "你的宠物#Y", petShuXing.str, "#n大于你15无法升级。"),
					0);
			GameObjectChar.send(new M16383_0(), vo_16383_2, chara.id);
			return;
		}
		// 宠物飞升之前最高升级到115级
		if (petShuXing.pot >= petShuXing.resist_poison && petShuXing.skill < 115 && petShuXing.limit_use_time == 0) {
			petShuXing.pot -= petShuXing.resist_poison;
			++petShuXing.skill;
			// 增加宠物亲密度，每给一次经验就给10点亲密
			petShuXing.shape += 10;
			Experience oneByMaxLevel = GameData.that.baseExperienceService.findOneByAttrib(petShuXing.skill);
			petShuXing.resist_poison = oneByMaxLevel.getMaxLevel() / 2;
			Vo_4323_0 vo_4323_0 = new Vo_4323_0();
			vo_4323_0.id = petbeibao.id;
			vo_4323_0.level = 1;
			GameObjectChar.send(new M4323_0(), vo_4323_0, chara.id);
			++petShuXing.phy_power;
			++petShuXing.life;
			++petShuXing.speed;
			++petShuXing.mag_power;
			petShuXing.polar_point += 4;
			if (petShuXing.skill < 60 && petShuXing.skill % 2 != 0) {
				++petShuXing.stamina;
			} else if (petShuXing.skill > 60) {
				++petShuXing.stamina;
			}
			//抗性点,60级以前包含60每升2级给一点
			if (petShuXing.skill <= 60 && petShuXing.skill % 2 == 0) {
				++petShuXing.resist_point2;
			} else if (petShuXing.skill > 60) {
				++petShuXing.resist_point2;
			}
			
			if (petShuXing.pot >= petShuXing.resist_poison) {
				addpetjingyan(petbeibao, 0, chara);
			}
			// 宠物自动加点功能
			java.util.Map<String, Object> petAutoAddPoint = chara.getPetAutoAddPoint();
			if (petAutoAddPoint != null) {
				int autoAdd = MapUtils.getIntValue(petAutoAddPoint, "auto_add");
				// 开启了自动加点
				if (autoAdd == 1) {
					// 体制
					int con2 = MapUtils.getIntValue(petAutoAddPoint, "con");
					// 灵力
					int wiz = MapUtils.getIntValue(petAutoAddPoint, "wiz");
					// 力量
					int str = MapUtils.getIntValue(petAutoAddPoint, "str");
					// 敏捷
					int dex = MapUtils.getIntValue(petAutoAddPoint, "dex");
					java.util.Map<String, Integer> map = GameCommonUtil.autoCalculationProportion(con2, wiz, str, dex,
							petShuXing.polar_point);
					petShuXing.mag_power += map.get("lingli");
					petShuXing.life += map.get("tizhi");
					petShuXing.phy_power += map.get("liliang");
					petShuXing.speed += map.get("minjie");
					petShuXing.polar_point -= (map.get("liliang") + map.get("tizhi") + map.get("lingli")
							+ map.get("minjie"));
				}
			}

			BasicAttributesUtils.petshuxing(petShuXing, petbeibao);
			// 这里是计算妖石伤害
			for (PetShuXing yaoshi : petbeibao.petShuXing) {
				// 在宠物的基础信息里面操作
				if (yaoshi.no >= 12 && yaoshi.no <= 15) {
					petShuXing.wiz += yaoshi.wiz;
					petShuXing.parry += yaoshi.parry;
					petShuXing.def += yaoshi.def;
					petShuXing.dex += yaoshi.dex;
					petShuXing.mana += yaoshi.mana;
					petShuXing.accurate += yaoshi.accurate;
				}
			}
			petShuXing.max_life = petShuXing.def;
			petShuXing.max_mana = petShuXing.dex;
			if (petbeibao.petShuXing.get(0).suit_light_effect != 0) {
				for (int i = 0; i < petbeibao.petShuXing.size(); ++i) {
					if (petbeibao.petShuXing.get(i).no == 23) {
						petbeibao.petShuXing.get(i).accurate = 4 * (petbeibao.petShuXing.get(0).hide_mount - 1)
								* petbeibao.petShuXing.get(0).skill;
						petbeibao.petShuXing.get(i).mana = 4 * (petbeibao.petShuXing.get(0).hide_mount - 1)
								* petbeibao.petShuXing.get(0).skill;
						petbeibao.petShuXing.get(i).wiz = 3 * (petbeibao.petShuXing.get(0).hide_mount - 1)
								* petbeibao.petShuXing.get(0).skill;
					}
				}
			}
			List<Petbeibao> list = new ArrayList<>();
			boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
			dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong, petbeibao.id,
					chara, petbeibao);
			list.add(petbeibao);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_UPDATE_PETS(), list);
		} else if (petShuXing.skill == 115 && petShuXing.limit_use_time == 0) {
			petShuXing.pot = 0;
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = petShuXing.str + "完成#R宠物飞升#n后才能继续升级！";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		}

		// 这里是宠物飞升完成之后的升级逻辑
		if (petShuXing.pot >= petShuXing.resist_poison && petShuXing.skill < 194 && petShuXing.limit_use_time == 1) {
			petShuXing.pot -= petShuXing.resist_poison;
			++petShuXing.skill;
			// 飞升之后每升级一直加200亲密
			petShuXing.shape += 200;
			Experience oneByMaxLevel = GameData.that.baseExperienceService.findOneByAttrib(petShuXing.skill);
			if (oneByMaxLevel == null) {
				return;
			}
			petShuXing.resist_poison = oneByMaxLevel.getMaxLevel() / 2;
			Vo_4323_0 vo_4323_0 = new Vo_4323_0();
			vo_4323_0.id = petbeibao.id;
			vo_4323_0.level = 1;
			GameObjectChar.send(new M4323_0(), vo_4323_0, chara.id);
			++petShuXing.phy_power;
			++petShuXing.life;
			++petShuXing.speed;
			++petShuXing.mag_power;
			petShuXing.polar_point += 4;
			if (petShuXing.skill < 60 && petShuXing.skill % 2 != 0) {
				++petShuXing.stamina;
			} else if (petShuXing.skill > 60) {
				++petShuXing.stamina;
			}
			if (petShuXing.skill <= 60 && petShuXing.skill % 2 == 0) {
				++petShuXing.resist_point2;
			} else if (petShuXing.skill > 60) {
				++petShuXing.resist_point2;
			}
			if (petShuXing.pot >= petShuXing.resist_poison) {
				addpetjingyan(petbeibao, 0, chara);
			}
			// 宠物自动加点功能
			java.util.Map<String, Object> petAutoAddPoint = chara.getPetAutoAddPoint();
			if (petAutoAddPoint != null) {
				int autoAdd = MapUtils.getIntValue(petAutoAddPoint, "auto_add");
				// 开启了自动加点
				if (autoAdd == 1) {
					// 体制
					int con2 = MapUtils.getIntValue(petAutoAddPoint, "con");
					// 灵力
					int wiz = MapUtils.getIntValue(petAutoAddPoint, "wiz");
					// 力量
					int str = MapUtils.getIntValue(petAutoAddPoint, "str");
					// 敏捷
					int dex = MapUtils.getIntValue(petAutoAddPoint, "dex");
					java.util.Map<String, Integer> map = GameCommonUtil.autoCalculationProportion(con2, wiz, str, dex,
							petShuXing.polar_point);
					petShuXing.mag_power += map.get("lingli");
					petShuXing.life += map.get("tizhi");
					petShuXing.phy_power += map.get("liliang");
					petShuXing.speed += map.get("minjie");
					petShuXing.polar_point -= (map.get("liliang") + map.get("tizhi") + map.get("lingli")
							+ map.get("minjie"));
				}
			}
			BasicAttributesUtils.petshuxing(petShuXing, petbeibao);
			if (petbeibao.petShuXing.get(0).suit_light_effect != 0) {
				for (int i = 0; i < petbeibao.petShuXing.size(); ++i) {
					if (petbeibao.petShuXing.get(i).no == 23) {
						petbeibao.petShuXing.get(i).accurate = 4 * (petbeibao.petShuXing.get(0).hide_mount - 1)
								* petbeibao.petShuXing.get(0).skill;
						petbeibao.petShuXing.get(i).mana = 4 * (petbeibao.petShuXing.get(0).hide_mount - 1)
								* petbeibao.petShuXing.get(0).skill;
						petbeibao.petShuXing.get(i).wiz = 3 * (petbeibao.petShuXing.get(0).hide_mount - 1)
								* petbeibao.petShuXing.get(0).skill;
					}
				}
			}
			List<Petbeibao> list = new ArrayList<>();
			boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
			dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong, petbeibao.id,
					chara, petbeibao);
			list.add(petbeibao);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_UPDATE_PETS(), list);
		}

		List<Petbeibao> list2 = new ArrayList<>();
		list2.add(petbeibao);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_UPDATE_PETS(), list2);
		if (jingyan != 0) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你的宠物#Y"+petShuXing.str+"#n获得#R" + jingyan + "#n经验";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
		}
	}

	// 宠物和角色都获得经验
	public static void huodejingyan(Chara chara, int jingyan, String... source) {
		String openGlobalDouble = GameActiveUtil.isOpenGlobalDouble();
		if(openGlobalDouble != null) {
			if(source.length> 0 && openGlobalDouble.indexOf(source[0]) != -1) {
				jingyan*=2;
			}
		}
		if (chara.lock_exp == 1) {
			GameCommonUtil.dialogOk("你已经#R锁定#n了经验，无法获得经验！", chara.id);
			// 如果人物锁定了经验就给宠物经验
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				if (petbeibao.id == chara.chongwuchanzhanId) {
					// 宠物和角色获得的经验相同
					addpetjingyan(petbeibao, jingyan, chara);
					break;
				}
			}
		} else {
			boolean isAdd = true;
			// 如果等级等于115级,判断是否完成了飞升人物
//			if (chara.level == 115) {
//				if (chara.isFeisheng == 0) {
//					GameCommonUtil.dialogOk("请完成飞升引路人任务！", chara.id);
//					isAdd = false;
//					boolean b = chara.taskMap.containsKey("飞升引路人");
//					if (!b && !chara.taskMap.containsKey("飞升—引路人")) {
//						String task_prompt = "前往#P天机老人|天墉城(194,52)|M=【飞升引路人】人物飞升|#P";
//						GameUtilRenWu.createTask("飞升引路人", task_prompt, "飞升引路人", chara, "完成飞升任务，才能突破115级");
//					}
//				}
//			}
			if (chara.isFeisheng == 0) {
				if (chara.level >= 110) {
					if (chara.level == 115) {
						GameCommonUtil.dialogOk("请完成飞升引路人任务！", chara.id);
						isAdd = false;
					}
					boolean b = chara.taskMap.containsKey("飞升引路人");
					if (!b && !chara.taskMap.containsKey("飞升—引路人")) {
						String task_prompt = "前往#P天机老人|天墉城(194,52)|M=【飞升引路人】人物飞升|#P";
						GameUtilRenWu.createTask("飞升引路人", task_prompt, "飞升引路人", chara, "完成飞升任务，才能突破115级");
					}
				}
			}
			if(chara.mapName.equals("试道场")) {
				chara.shidaoExp+=jingyan;
//				GameCommonUtil.addCharaTrail(chara, "经验", jingyan, "试道元魔");
				chara.dayInfo.setToDayTotalExp(chara.dayInfo.getToDayTotalExp()+jingyan);
				return;
			}
			if (isAdd) {
				boolean addjingyan = addjingyan(chara, jingyan);
				if(addjingyan) {
					Vo_20481_0 vo_20481_2 = new Vo_20481_0();
					vo_20481_2.msg = "你获得了#R" + jingyan + "#n经验";
					vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_2);
					//添加轨迹
//					GameCommonUtil.addCharaTrail(chara, "经验", jingyan, source);
					chara.dayInfo.setToDayTotalExp(chara.dayInfo.getToDayTotalExp()+jingyan);
				}
			}
			for (int i = 0; i < chara.pets.size(); ++i) {
				Petbeibao petbeibao = chara.pets.get(i);
				//如果设置有共通的话优先共通的宠物
				if(chara.awardSupplyPetId != 0) {
					if (petbeibao.id == chara.awardSupplyPetId) {
						// 宠物和角色获得的经验相同
						addpetjingyan(petbeibao, jingyan, chara);
						break;
					}
				}else if (petbeibao.id == chara.chongwuchanzhanId) {
					// 宠物和角色获得的经验相同
					addpetjingyan(petbeibao, jingyan, chara);
					break;
				}
			}
		}
	}

	// 只有角色获得经验
	public static void zhilingjingyan(Chara chara, int jingyan) {
		addjingyan(chara, jingyan);
		Vo_20481_0 vo_20481_2 = new Vo_20481_0();
		vo_20481_2.msg = "你获得了#R" + jingyan + "#n经验";
		vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_2);
	}

	// 角色获得神兽
	public static int huodeshenshou(Chara chara, String name, String source) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		if (pet == null) {
			GameUtil.sendMeTips("没有找到神兽#R" + name);
			return 0;
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, 4, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "恭喜获得神兽#R" + name;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
		
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 2;
		vo_40964_0.name = name;
		vo_40964_0.param = String.valueOf(pet.getIcon());
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		
		return petbeibao.id;
	}

	// 角色获得神兽
	public static void huodechaoshenshou(Chara chara, String name, boolean isFuzhan, String source) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		Petbeibao petbeibao = new Petbeibao();
		double[] chaoRate = new double[] { 1.1, 1.1, 1.1, 1.1, 1.1 };
		double[] fuzhanRate = new double[] { 1.1, 1.1, 1.1, 1.1, 1.1 };
		if ("朱雀".equals(name)) {
			chaoRate[4] = 1.2;
			fuzhanRate[4] = 1.2;
		}
		if ("九尾狐".equals(name)) {
			chaoRate[2] = 1.2;
			fuzhanRate[2] = 1.2;
		}
		if ("疆良".equals(name)) {
			chaoRate[3] = 1.2;
			fuzhanRate[3] = 1.2;
		}
		if ("玄武".equals(name)) {
			chaoRate[0] = 1.2;
			fuzhanRate[0] = 1.2;
		}
		if (isFuzhan)
			petbeibao.ChaoPetCreate(pet, chara, 0, 4, chaoRate, fuzhanRate, source);
		else
			petbeibao.ChaoPetCreate(pet, chara, 0, 4, chaoRate, new double[] { 1.0, 1.0, 1.0, 1.0, 1.0 }, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		if (isFuzhan)
			vo_20481_0.msg = "恭喜获得服战级神兽#R" + name;
		else
			vo_20481_0.msg = "恭喜获得超神兽#R" + name;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
	}

	// 获得坐骑
	public static void huodezuoji(Chara chara1, String goodsName, String source) {
		int jieshu = 6;
		Pet pet = GameData.that.basePetService.findOneByName(goodsName);
		if (pet == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不存在坐骑：" + goodsName;
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		} else {
			jieshu = stageMounts(goodsName);
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara1, 0, 2, source);
		List<Petbeibao> list5 = new ArrayList<Petbeibao>();
		chara1.pets.add(petbeibao);
		list5.add(petbeibao);
		petbeibao.petShuXing.get(0).enchant_nimbus = 0;
		petbeibao.petShuXing.get(0).max_enchant_nimbus = 0;
		petbeibao.petShuXing.get(0).suit_light_effect = 1;
		petbeibao.petShuXing.get(0).hide_mount = jieshu;

		PetShuXing shuXing = new PetShuXing();
		shuXing.no = 23;
		shuXing.type1 = 2;
		shuXing.accurate = 4 * (jieshu - 1);
		shuXing.mana = 4 * (jieshu - 1);
		shuXing.wiz = 3 * (jieshu - 1);
		shuXing.all_polar = 0;
		shuXing.upgrade_magic = 0;
		shuXing.upgrade_total = 0;
		petbeibao.petShuXing.add(shuXing);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list5, chara1.id);

		Vo_20481_0 vo_20481_4 = new Vo_20481_0();
		vo_20481_4.msg = "恭喜获得" + jieshu + "阶坐骑#R" + goodsName;
		vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_4);
	}

	// 角色获得坐骑
	public static int huodezuoqi(Chara chara, String name, String source) {
		int jieshu = 8;
		Pet pet = GameData.that.basePetService.findOneByName(name);
		if (pet == null) {
			GameUtil.sendMeTips("没有找到坐骑#R" + name);
			return 0;
		}
		String jie = pet.getPetType();
		if(!StringUtils.isNullOrEmpty(jie)){
			jieshu = Integer.parseInt(jie);
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, 2, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		petbeibao.petShuXing.get(0).enchant_nimbus = 0;
		petbeibao.petShuXing.get(0).max_enchant_nimbus = 0;
		petbeibao.petShuXing.get(0).suit_light_effect = 1;
		petbeibao.petShuXing.get(0).hide_mount = jieshu;
		PetShuXing shuXing = new PetShuXing();
		shuXing.no = 23;
		shuXing.type1 = 2;
		shuXing.accurate = 4 * (jieshu - 1);
		shuXing.mana = 4 * (jieshu - 1);
		shuXing.wiz = 3 * (jieshu - 1);
		shuXing.all_polar = 0;
		shuXing.upgrade_magic = 0;
		shuXing.upgrade_total = 0;
		petbeibao.petShuXing.add(shuXing);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "恭喜获得#R" + name;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0);
		
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 2;
		vo_40964_0.name = name;
		vo_40964_0.param = String.valueOf(pet.getIcon());
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		
		return petbeibao.id;
	}

	// 角色获得坐骑
	public static void huodezuoqi(Chara chara, String name, int speed, String source) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		if (pet == null) {
			GameUtil.sendMeTips("没有找到坐骑#R" + name);
			return;
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, 2, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		petbeibao.petShuXing.get(0).enchant_nimbus = 0;
		petbeibao.petShuXing.get(0).max_enchant_nimbus = 0;
		petbeibao.petShuXing.get(0).suit_light_effect = 1;
		petbeibao.petShuXing.get(0).hide_mount = speed;
		PetShuXing shuXing = new PetShuXing();
		shuXing.no = 23;
		shuXing.type1 = 2;
		shuXing.accurate = 4 * (speed - 1);
		shuXing.mana = 4 * (speed - 1);
		shuXing.wiz = 3 * (speed - 1);
		shuXing.all_polar = 0;
		shuXing.upgrade_magic = 0;
		shuXing.upgrade_total = 0;
		petbeibao.petShuXing.add(shuXing);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "恭喜获得#R" + name;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
	}


	// 只有宠物获得经验
	public static void chongwujingyan(Chara chara, int jingyan) {
		for (int i = 0; i < chara.pets.size(); ++i) {
			if (chara.pets.get(i).id == chara.chongwuchanzhanId) {
				PetShuXing petShuXing2 = chara.pets.get(i).petShuXing.get(0);
				petShuXing2.shape += 10;
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				addpetjingyan(chara.pets.get(i), jingyan, chara);
				vo_20481_0.msg = "宠物获得#R" + jingyan + "#n经验";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
				break;
			}
		}
	}

	// 获得妖石,pos是位置2-7的范围，amount是数量，level是妖石等级
	public static void huodeyaoshi(GameObjectChar gameObjectChar, int pos, int amount, int level) {
		Chara chara = gameObjectChar.chara;
		GroceriesShop groceriesShop = GameData.that.baseGroceriesShopService.findOneByGoodsNo(pos);
		StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName(groceriesShop.getName());
		if (pos < 2) {
			GameUtil.huodedaoju(gameObjectChar, storeInfo, amount);
		} else {
			int pos2 = packPoint(chara);
			if (pos2 == -1) {
				return;
			}
			Goods goods = new Goods();
			goods.pos = pos2;
			goods.goodsInfo = new GoodsInfo();
			goods.goodsDaoju(storeInfo);
			goods.goodsInfo.degree_32 = 0;
			goods.goodsInfo.skill = level;
			goods.goodsInfo.owner_id = amount;
			goods.goodsInfo.damage_sel_rate = 400976;
			goods.goodsInfo.silver_coin = 6000;
			goods.goodsInfo.degree_32 = 0; // 【重要】妖石也是已鉴定
			goods.goodsLanSe = new GoodsLanSe();
			if (pos == 2) {
				switch (level) {
				case 3:
					goods.goodsLanSe.wiz = 270;
					break;
				case 4:
					goods.goodsLanSe.wiz = 380 + new Random().nextInt(171);
					break;
				case 5:
					goods.goodsLanSe.wiz = 600 + new Random().nextInt(301);
					break;
				case 6:
					goods.goodsLanSe.wiz = 850 + new Random().nextInt(351);
					break;
				case 7:
					goods.goodsLanSe.wiz = 1100 + new Random().nextInt(601);
					break;
				case 8:
					goods.goodsLanSe.wiz = 1500 + new Random().nextInt(701);
					break;
				}
			}
			if (pos == 3) {
				switch (level) {
				case 3:
					goods.goodsLanSe.accurate = 594;
					break;
				case 4:
					goods.goodsLanSe.accurate = 800 + new Random().nextInt(401);
					break;
				case 5:
					goods.goodsLanSe.accurate = 1300 + new Random().nextInt(601);
					break;
				case 6:
					goods.goodsLanSe.accurate = 1900 + new Random().nextInt(901);
					break;
				case 7:
					goods.goodsLanSe.accurate = 2400 + new Random().nextInt(1401);
					break;
				case 8:
					goods.goodsLanSe.accurate = 3200 + new Random().nextInt(1801);
					break;
				}
			}
			if (pos == 4) {
				switch (level) {
				case 3:
					goods.goodsLanSe.mana = 392;
					break;
				case 4:
					goods.goodsLanSe.mana = 800 + new Random().nextInt(401);
					break;
				case 5:
					goods.goodsLanSe.mana = 1300 + new Random().nextInt(601);
					break;
				case 6:
					goods.goodsLanSe.mana = 1900 + new Random().nextInt(901);
					break;
				case 7:
					goods.goodsLanSe.mana = 2400 + new Random().nextInt(1401);
					break;
				case 8:
					goods.goodsLanSe.mana = 3200 + new Random().nextInt(1801);
					break;
				}
			}
			if (pos == 5) {
				switch (level) {
				case 3:
					goods.goodsLanSe.def = 900;
					break;
				case 4:
					goods.goodsLanSe.def = 1200 + new Random().nextInt(701);
					break;
				case 5:
					goods.goodsLanSe.def = 2000 + new Random().nextInt(1001);
					break;
				case 6:
					goods.goodsLanSe.def = 2800 + new Random().nextInt(1401);
					break;
				case 7:
					goods.goodsLanSe.def = 3800 + new Random().nextInt(1701);
					break;
				case 8:
					goods.goodsLanSe.def = 5000 + new Random().nextInt(2501);
					break;
				}
			}
			if (pos == 6) {
				switch (level) {
				case 3:
					goods.goodsLanSe.parry = 96;
					break;
				case 4:
					goods.goodsLanSe.parry = 100 + new Random().nextInt(51);
					break;
				case 5:
					goods.goodsLanSe.parry = 120 + new Random().nextInt(71);
					break;
				case 6:
					goods.goodsLanSe.parry = 150 + new Random().nextInt(71);
					break;
				case 7:
					goods.goodsLanSe.parry = 170 + new Random().nextInt(91);
					break;
				case 8:
					goods.goodsLanSe.parry = 200 + new Random().nextInt(101);
					break;
				}
			}
			if (pos == 7) {
				switch (level) {
				case 3:
					goods.goodsLanSe.dex = 594;
					break;
				case 4:
					goods.goodsLanSe.dex = 800 + new Random().nextInt(401);
					break;
				case 5:
					goods.goodsLanSe.dex = 1300 + new Random().nextInt(601);
					break;
				case 6:
					goods.goodsLanSe.dex = 1900 + new Random().nextInt(901);
					break;
				case 7:
					goods.goodsLanSe.dex = 2400 + new Random().nextInt(1401);
					break;
				case 8:
					goods.goodsLanSe.dex = 3200 + new Random().nextInt(1801);
					break;
				}
			}
			GameUtil.addwupin(goods, chara);
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
		}
		GameCommonUtil.sendTips("你获得了" + "#R" + level + "级妖石#n#Y" + storeInfo.getName() + "#n", chara.id);
		
	}

	// 角色添加经验
	private static boolean addjingyan(Chara chara, int jingyan) {
		if (chara.lock_exp == 1) {
			GameCommonUtil.dialogOk("你已经#R锁定#n了经验，无法获得经验！", chara.id);
			return false;
		}
		// 如果等级等于115级,判断是否完成了飞升人物
		if (chara.level == 115 && chara.isFeisheng == 0) {
			GameCommonUtil.dialogOk("请完成飞升引路人任务！",chara.id);
			return false;
		}
		addFabaoQinmi(chara, 10, "");
		// 获得角色经验
		if (chara.upgrade_state == 0) {
			// 真身
			chara.exp += jingyan;
			if (chara.exp >= chara.expToNextLevel) {
				//判断等级是否到达顶级
				if(chara.level+1 > GameConfig.config.getBaseConfig().getRealMaxLevel()) {
					GameUtil.sendMeTips("等级已达顶级，暂时无法获得经验");
					//扣去获取的经验
					chara.exp = 0;
					return false;
				}
				chara.exp -= chara.expToNextLevel;
				++chara.level;
				chara.realLevel = chara.level;
				Experience oneByMaxLevel = GameData.that.baseExperienceService.findOneByAttrib(chara.level);
				if (oneByMaxLevel == null) {
					return false;
				}
				//主线任务
				if(chara.level >= 20 && chara.taskMap.get("主线—拜入师门") != null 
						&& "主线—拜入师门s30_1".equals(chara.current_task)) {
					GameUtil.renwujiangli(chara);
					//领取宠物
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("主线—拜入师门s30_2");
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					//创建主线任务
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//弹出新手礼包
					GameUtil.openDlg("RookieGiftDlg");
				}
				//创建妖魔道任务
				if(chara.level >= 20 && chara.taskMap.get("妖魔道") == null) {
					chara.current_task = "妖魔道—勇擒鱼怪s1";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("妖魔道—勇擒鱼怪s1");
					//初始化妖魔道
					GameUtilRenWu.createYaoMoDaoYongQingYingYuGuaiTask(chara,"勇擒鱼怪", renwu);
				}
				chara.expToNextLevel = oneByMaxLevel.getMaxLevel();
				Vo_4323_0 vo_4323_0 = new Vo_4323_0();
				vo_4323_0.id = chara.id;
				vo_4323_0.level = 1;
				GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
				gameObjectChar.gameMap.send(new M4323_0(), vo_4323_0);
				++chara.phy_power;
				++chara.life;
				++chara.speed;
				++chara.mag_power;
				chara.attribPoint += 4;
				if (chara.level < 60 && chara.level % 2 != 0) {
					++chara.polarPoint;
				} else if (chara.level > 60) {
					++chara.polarPoint;
				}
				//仙魔道点，并且已经飞升
				if(chara.upgrade_type > 2 && chara.level>119) {
					chara.upgrade_magic+=1;
					chara.upgrade_immortal+=1;
					//如果开启了自动分配
					if(chara.upgradeIsOpen == 1) {
						if(chara.upgradeAddType == 1) {
							//仙道点
							chara.upgrade_immortal+=1;
						}else if(chara.upgradeAddType == 2) {
							chara.upgrade_magic+=1;
						}
					}else {
						//给他分配点数
						chara.upgrade_total+=1;
					}
				}
				if (chara.exp >= chara.expToNextLevel) {
					addjingyan(chara, 0);
				}
				// 获取用户是否开启自动加点
				java.util.Map<String, Object> userAutoAddPoint = chara.getUserAutoAddPoint();
				if (userAutoAddPoint != null) {
					int autoAdd = MapUtils.getIntValue(userAutoAddPoint, "auto_add");
					// 开启了自动加点
					if (autoAdd == 1) {
						// 体制
						int con2 = MapUtils.getIntValue(userAutoAddPoint, "con");
						// 灵力
						int wiz = MapUtils.getIntValue(userAutoAddPoint, "wiz");
						// 力量
						int str = MapUtils.getIntValue(userAutoAddPoint, "str");
						// 敏捷
						int dex = MapUtils.getIntValue(userAutoAddPoint, "dex");
						log.info("------人物可用属性点:{}", chara.attribPoint);
						java.util.Map<String, Integer> map = GameCommonUtil.autoCalculationProportion(con2, wiz, str,
								dex, chara.attribPoint);
						chara.phy_power += map.get("liliang");
						chara.life += map.get("tizhi");
						chara.mag_power += map.get("lingli");
						chara.speed += map.get("minjie");
						chara.attribPoint -= (map.get("liliang") + map.get("tizhi") + map.get("lingli")
								+ map.get("minjie"));
					}
				}
				BasicAttributesUtils.shuxing(chara);
				chara.max_life = chara.def + chara.zbAttribute.def;
				chara.max_mana = chara.dex + chara.zbAttribute.dex;
				addshouhu(chara);
			}
		} else {
			// 真身
			chara.upgrade_exp += jingyan;
			// 切换元婴状态了
			if(chara.upgrade_exp >= chara.upgrade_exp_to_next_level) {
				//判断等级是否到达顶级
				if(chara.upgrade_level+1 > GameConfig.config.getBaseConfig().getUpgradeMaxLevel()) {
					GameUtil.sendMeTips("等级已达顶级，暂时无法获得经验");
					chara.upgrade_exp = 0;
					return false;
				}else {
					chara.upgrade_exp -= chara.upgrade_exp_to_next_level;
					++chara.level;
					chara.upgrade_level = chara.level;
					Experience oneByMaxLevel = GameData.that.baseExperienceService.findOneByAttrib(chara.upgrade_level);
					if (oneByMaxLevel == null) {
						return false;
					}
					// 每升10级就给真身加两点自由分配属性
					if (chara.upgrade_level % 10 == 0) {
						chara.charaRealInfo.attribPoint += 2;
					}
					chara.upgrade_exp_to_next_level = (int) (oneByMaxLevel.getMaxLevel() * 0.8);
					// 发送升级动画
					GameObjectCharMng.getGameObjectChar(chara.id).gameMap.send(new MSG_UPGRADE_LEVEL_UP(),
							new Object[] { chara.id, 1 });
					++chara.phy_power;
					++chara.life;
					++chara.speed;
					++chara.mag_power;
					chara.attribPoint += 4;
					if (chara.level < 60 && chara.level % 2 != 0) {
						++chara.polarPoint;
					} else if (chara.level > 60) {
						++chara.polarPoint;
					}
					if (chara.upgrade_exp >= chara.upgrade_exp_to_next_level) {
						addjingyan(chara, 0);
					}
					// 获取用户是否开启自动加点
					java.util.Map<String, Object> userAutoAddPoint = chara.getUserAutoAddPoint();
					if (userAutoAddPoint != null) {
						int autoAdd = MapUtils.getIntValue(userAutoAddPoint, "auto_add");
						// 开启了自动加点
						if (autoAdd == 1) {
							// 体制
							int con2 = MapUtils.getIntValue(userAutoAddPoint, "con");
							// 灵力
							int wiz = MapUtils.getIntValue(userAutoAddPoint, "wiz");
							// 力量
							int str = MapUtils.getIntValue(userAutoAddPoint, "str");
							// 敏捷
							int dex = MapUtils.getIntValue(userAutoAddPoint, "dex");
							log.info("------人物可用属性点:{}", chara.attribPoint);
							java.util.Map<String, Integer> map = GameCommonUtil.autoCalculationProportion(con2, wiz, str,
									dex, chara.attribPoint);
							chara.phy_power += map.get("liliang");
							chara.life += map.get("tizhi");
							chara.mag_power += map.get("lingli");
							chara.speed += map.get("minjie");
							chara.attribPoint -= (map.get("liliang") + map.get("tizhi") + map.get("lingli")
									+ map.get("minjie"));
						}
					}
					BasicAttributesUtils.shuxing(chara);
					chara.max_life = chara.def + chara.zbAttribute.def;
					chara.max_mana = chara.dex + chara.zbAttribute.dex;
					addshouhu(chara);
				
				}
			}
		}
		if (GameObjectCharMng.getGameObjectChar(chara.id).gameTeam != null) {
			// 更新队伍信息.
			updateRightTeamInfos(chara);
		}
		sendUpdate(chara);
		return true;
	}

	// 添加经验,后台专用
	public static void addjingyanToManage(Chara chara, int jingyan) {
		huodejingyan(chara, jingyan, "GM后台");
		// 更新队伍信息.
		updateRightTeamInfosToManage(chara);
	}

	/**
	 * 更新右侧组队队伍信息.
	 * 
	 * @param chara
	 */
	public static void updateRightTeamInfosToManage(Chara chara) {

		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);

		if (null != gameObjectChar.gameTeam) {
			List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
			GameUtil.MSG_UPDATE_TEAM_LIST(duiwu);
			for (Vo_4121_0 vo_4121_0 : gameObjectChar.gameTeam.zhanliduiyuan) {
				if (vo_4121_0.id == chara.id) {
					vo_4121_0.skill = chara.level;
				}
			}
			GameUtil.MSG_UPDATE_TEAM_LIST_EX(gameObjectChar.gameTeam.zhanliduiyuan);
		}
	}


	// 添加物品到背包
	public static void addwupin(Goods goods, Chara chara) {
		boolean has = true;
		int munber = GameCommonUtil.getMaxSuperPosition(goods);
		if(munber>1) {
			goods.goodsInfo.damage_sel_rate = 4971;
		}
		for (int i = 0; i < chara.backpack.size(); ++i) {
			// add tzhang 修复集市显示普通物品为未鉴定的bug
			boolean isJiandingZhuangbei = false;
			ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(goods.goodsInfo.str);
			if (zhuangbeiInfo != null && goods.goodsInfo.degree_32 == 0)
				isJiandingZhuangbei = true;
			// add:e
			Goods goods2 = chara.backpack.get(i);
			java.util.Map<Object, Object> objectMapGoodxin = UtilObjMapshuxing.GoodsInfo(goods.goodsInfo);
			java.util.Map<Object, Object> objectMapGoodxin2 = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
			objectMapGoodxin.remove("auto_fight");
			objectMapGoodxin.remove("owner_id");
			java.util.Map<Object, Object> objectMapGoodjold = UtilObjMapshuxing.GoodsInfo(goods2.goodsInfo);
			java.util.Map<Object, Object> objectMapGoodjold2 = UtilObjMapshuxing.GoodsLanSe(goods2.goodsLanSe);
			objectMapGoodjold.remove("auto_fight");
			objectMapGoodjold.remove("owner_id");
			if (objectMapGoodjold.toString().equals(objectMapGoodxin.toString())
					&& objectMapGoodxin2.toString().equals(objectMapGoodjold2.toString()) && !isJiandingZhuangbei
					&& goods2.goodsInfo.owner_id < munber) {
				int owner = goods2.goodsInfo.owner_id;
				GoodsInfo goodsInfo = goods2.goodsInfo;
				goodsInfo.owner_id += goods.goodsInfo.owner_id;
				if (goods2.goodsInfo.owner_id >= munber) {
					goods2.goodsInfo.owner_id = munber;
					goods.goodsInfo.owner_id = goods.goodsInfo.owner_id - munber + owner;
				} else {
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
					has = false;
				}
			}
		}
		if (has) {
			List<Goods> list = new ArrayList<>();
			if (goods.goodsInfo.owner_id > munber) {
				int len = goods.goodsInfo.owner_id / munber;
				int last = goods.goodsInfo.owner_id % munber;
				for (int j = 0; j < len; ++j) {
					java.util.Map<Object, Object> objectMapGoodxin3 = UtilObjMapshuxing.Goods(goods);
					Goods goodsxin = com.alibaba.fastjson.JSONObject.parseObject(
							com.alibaba.fastjson.JSONObject.toJSONString((Object) objectMapGoodxin3), Goods.class);
					int pos2 = packPoint(chara);
					if (pos2 == -1) {
						return;
					}
					goodsxin.pos = pos2;
					goodsxin.goodsInfo.owner_id = munber;
					chara.backpack.add(goodsxin);
					list = new ArrayList<>();
					list.add(goodsxin);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
				}
				if (last != 0) {
					java.util.Map<Object, Object> objectMapGoodxin4 = UtilObjMapshuxing.Goods(goods);
					Goods goodsxin2 = (Goods) com.alibaba.fastjson.JSONObject
							.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(objectMapGoodxin4), Goods.class);
					int pos2 = packPoint(chara);
					if (pos2 == -1) {
						return;
					}
					goodsxin2.pos = pos2;
					goodsxin2.goodsInfo.owner_id = last;
					chara.backpack.add(goodsxin2);
					list = new ArrayList<>();
					list.add(goodsxin2);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
				}
			} else if (goods.goodsInfo.owner_id != 0) {
				int pos2 = packPoint(chara);
				if (pos2 == -1) {
					return;
				}
				goods.pos = pos2;
				chara.backpack.add(goods);
				list = new ArrayList<>();
				list.add(goods);
				GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
			}
			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
		}
	}

	public static Vo_49179_0 a49179(List<SaleGood> saleGoodList, Chara chara) {
		Vo_49179_0 vo_49179_0 = new Vo_49179_0();
		vo_49179_0.dealNum = 0;
		vo_49179_0.sellCash = String.valueOf(chara.jishou_coin);
		vo_49179_0.stallTotalNum = 4;
		vo_49179_0.record_count_max = 30;
		for (int i = 0; i < saleGoodList.size(); ++i) {
			SaleGood saleGood = saleGoodList.get(i);
			Vo_49179 vo_49179 = new Vo_49179();
			vo_49179.name = saleGood.getName();
			if (saleGood.getName().contains("超级黑水晶·")) {
				String goods = saleGood.getGoods();
				Goods goods2 = com.alibaba.fastjson.JSONObject.parseObject(goods, Goods.class);
				java.util.Map<Object, Object> goodsFenSe1 = UtilObjMapshuxing.GoodsLanSe(goods2.goodsLanSe);
				int value = 0;
				for (java.util.Map.Entry<Object, Object> entry : goodsFenSe1.entrySet()) {
					if (!entry.getKey().equals("groupNo") && !entry.getKey().equals("groupType")) {
						if (entry.getValue().toString().equals("0")) {
							continue;
						}
						value = (int) entry.getValue();
						break;
					}
				}
				vo_49179.name = saleGood.getName() + "|" + value + "|1";
			}
			vo_49179.id = saleGood.getGoodsId();
			vo_49179.price = saleGood.getPrice();
			vo_49179.status = saleGood.getStatus();
			vo_49179.startTime = saleGood.getStartTime();
			vo_49179.endTime = saleGood.getEndTime();
			vo_49179.level = saleGood.getLevel();
			vo_49179.unidentified = saleGood.getUnidentified();
			vo_49179.amount = saleGood.getReqLevel();
			vo_49179.req_level = saleGood.getReqLevel();
			vo_49179.extra = saleGood.getExtra();
			vo_49179.item_polar = saleGood.getItemPolar();
			vo_49179.cg_price_count = saleGood.getCgPriceCount();
			vo_49179.init_price = saleGood.getPrice();
			vo_49179_0.vo_49179s.add(vo_49179);
		}
		return vo_49179_0;
	}

	public static void MSG_OPEN_WELFARE(Chara chara) {

		Vo_OPEN_WELFARE vo_49159_3 = new Vo_OPEN_WELFARE();
		int isCanSign = 0;
		for (int j = 0; j < chara.shenmiliwu.size(); ++j) {
			if (!chara.shenmiliwu.get(j).name.equals("")) {
				Vo_OPEN_WELFARE vo_49159_5 = new Vo_OPEN_WELFARE();
				vo_49159_5.leftTime += chara.shenmiliwu.get(j).time;
			}
		}
		for (int i = 0; i < chara.shenmiliwu.size(); ++i) {
			// name为""表示为可开次数
			if (chara.shenmiliwu.get(i).name.equals("")) {
				int times = chara.shenmiliwu.get(i).time;
				vo_49159_3.leftTime = (int) (times - chara.online_time / 1000L);
				Vo_OPEN_WELFARE vo_49159_6 = vo_49159_3;
				vo_49159_6.leftTime -= (int) ((System.currentTimeMillis() - chara.uptime) / 1000L);
				if (vo_49159_3.leftTime > 0) {
					break;
				}
			}
		}
		for (int i = 0; i < chara.shenmiliwu.size(); ++i) {
			// name为""表示为可开次数
			if (chara.shenmiliwu.get(i).name.equals("")) {
				isCanSign++;
			}
		}
		// 剩余次数
		vo_49159_3.leftTimes = isCanSign;
		vo_49159_3.times = 0;
		vo_49159_3.isCanSign = 0;
		vo_49159_3.isCanGetNewPalyerGift = 0;
		vo_49159_3.firstChargeState = 2;
		vo_49159_3.cumulativeReward = 255;
		vo_49159_3.loginGiftState = 2;
		vo_49159_3.activeCount = 255;
		vo_49159_3.holidayCount = 255;
		vo_49159_3.isCanReplenishSign = 255;
		vo_49159_3.chargePointFlag = 1;
		vo_49159_3.consumePointFlag = -1;
		vo_49159_3.isShowHuiGui = 0;
		vo_49159_3.canGetZXQYHuoYue = 0;
		vo_49159_3.canGetZXQYSevenLogin = 0;
		vo_49159_3.isShowZhaohui = 0;
		vo_49159_3.activeVIPFlag = 0;
		vo_49159_3.rename_discount_time = 0;
		vo_49159_3.summerSF2017 = 0;
		vo_49159_3.zaohua = 255;
		vo_49159_3.welcomeDrawStatue = 255;
		vo_49159_3.activeLoginStatue = 255;
		vo_49159_3.xundcf = 255;
		vo_49159_3.mergeLoginStatus = 255;
		vo_49159_3.mergeLoginActiveStatus = 255;
		vo_49159_3.reentryAsktaoRecharge = 255;
		vo_49159_3.expStoreStatus = 0;
		vo_49159_3.isShowXYFL = 255;
		vo_49159_3.isShowXFSD = 0;
		vo_49159_3.newServeAddNum = -1;
		vo_49159_3.double_lottery = -1;
		vo_49159_3.qmpkDrawTimes = -1;
		vo_49159_3.new_year_bless_flag = -1;
		vo_49159_3.fixed_team_welfare_flag = -1;
		log.info("可用次数-------:{}", vo_49159_3.leftTimes);
		GameObjectChar.send(new MSG_OPEN_WELFARE(), vo_49159_3, chara.id);
	}

	// 判断传过来的日期是否是今天
	public static boolean isNow(Date date) {
		Date now = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
		String nowDay = sf.format(now);
		String day = sf.format(date);
		return day.equals(nowDay);
	}

	public static List<JiNeng> dujineng(int leixing, int pos, int level, boolean isMagic, int id, Chara chara, Petbeibao
			petbeibao) {
		List<JiNeng> jiNengList = new ArrayList<JiNeng>();
		List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getNomelSkills(leixing, pos, level, true);
		List<Integer> defaultFsId = new ArrayList<>();
		for (int i = 0; i < nomelSkills.size(); ++i) {
			JiNeng jiNeng = new JiNeng();
			JSONObject jsonObject = nomelSkills.get(i);
			jiNeng.id = id;
			jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
			JSONObject jsonObject2 = PetAndHelpSkillUtils.jsonArray(jiNeng.skill_no);
			jiNeng.skill_attrib1 = Integer.parseInt((String) jsonObject2.get("skill_attrib"));
			jiNeng.skill_attrib = (int) jsonObject.get("skillLevel");
			jiNeng.skill_level = (int) jsonObject.get("skillLevel");
			jiNeng.skillRound = jsonObject.optInt("skillRound");
			jiNeng.level_improved = 1;
			jiNeng.skill_mana_cost = (int) jsonObject.get("skillBlue");
			jiNeng.skill_nimbus = 42949672;
			jiNeng.skill_disabled = 0;
			jiNeng.range = (int) jsonObject.get("skillNum");
			jiNeng.max_range = (int) jsonObject.get("skillNum");
			jiNengList.add(jiNeng);
			if(jsonObject2.getString("skillType").equals("FS")) {
				defaultFsId.add(i);
			}
		}
		//如果不等于空
		if(petbeibao != null && petbeibao.tianji != null && !petbeibao.tianji.isEmpty()) {
			int customFsNum = 0;
			for(JiNeng tinaji:petbeibao.tianji) {
				JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(tinaji.skill_no);
				if(jsonObject.getString("skillType").equals("FS")) {
					customFsNum++;
				}
			}
			if(defaultFsId.size()>=3) {
				if(customFsNum>3) {
					//直接清除
					jiNengList.clear();
				}else {
					//表示法伤技能已经超限了，则删除默认技能
					for(int i=0;i<customFsNum;i++) {
						jiNengList.remove(0);
					}
				}
			}
			jiNengList.addAll(petbeibao.tianji);
		}
		List<Vo_32747_0> vo_32747_0List = a32747(jiNengList);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M32747_0(), vo_32747_0List);
		return jiNengList;
	}

	public static int getCard(Chara chara) {
		// 从redis获取一个自增的宠物id
		int petId = GameData.that.redisUtils.getIncr("petId");
		List<Integer> petIds = new ArrayList<>();
		for (Petbeibao p : chara.pets) {
			petIds.add(p.id);
		}
		if (petIds.contains(petId)) {
			log.info("宠物Id重复，继续生成====================={}", petId);
			getCard(chara);
		}
		log.info("生成宠物id{}", petId);
		return petId;
	}

	public static int getNo(Chara chara) {
		// 初始化的nos
		List<Integer> nos = new ArrayList<>();
		for (int i = 10; i < 50; i++) {
			nos.add(i);
		}
		for (int j = 0; j < chara.pets.size(); ++j) {
			if (nos.contains(chara.getPets().get(j).no)) {
				nos.remove(Integer.valueOf(chara.getPets().get(j).no));
				log.info("删除宠物的no={}", chara.getPets().get(j).no);
			}
		}
		log.info("生成宠物no={}", nos.get(0));
		return nos.get(0);
	}

	public static List<Vo_32747_0> MSG_UPDATE_SKILLS(List<JiNeng> jiNengs) {
		List<Vo_32747_0> vo_32747_0List = new ArrayList<>();
		for (JiNeng jiNeng : jiNengs) {
			Vo_32747_0 vo_32747_0 = new Vo_32747_0();
			vo_32747_0.id = jiNeng.id;
			vo_32747_0.skill_no = jiNeng.skill_no;
			vo_32747_0.skill_attrib = jiNeng.skill_attrib;
			vo_32747_0.skill_level = jiNeng.skill_level + jiNeng.level_improved;
			vo_32747_0.level_improved = jiNeng.level_improved;//
			vo_32747_0.skill_mana_cost = jiNeng.skill_mana_cost;
			vo_32747_0.skill_nimbus = jiNeng.skill_nimbus;
			vo_32747_0.skill_disabled = jiNeng.skill_disabled;
			vo_32747_0.range = jiNeng.range;
			vo_32747_0.max_range = jiNeng.max_range;
			vo_32747_0.count1 = jiNeng.count1;
			vo_32747_0.s1 = jiNeng.s1;
			vo_32747_0.s2 = jiNeng.s2;
			vo_32747_0.isTempSkill = jiNeng.isTempSkill;
			vo_32747_0List.add(vo_32747_0);
		}
		return vo_32747_0List;
	}

	// 返回角色技能
	public static List<Vo_32747_0> a32747(List<JiNeng> jiNengs) {
		List<Vo_32747_0> vo_32747_0List = new ArrayList<Vo_32747_0>();
		for (JiNeng jiNeng : jiNengs) {
			Vo_32747_0 vo_32747_0 = new Vo_32747_0();
			vo_32747_0.id = jiNeng.id;
			vo_32747_0.skill_no = jiNeng.skill_no;
			vo_32747_0.skill_attrib = jiNeng.skill_attrib;
			vo_32747_0.skill_attrib1 = jiNeng.skill_attrib1;
			vo_32747_0.skill_level = jiNeng.skill_level;
			vo_32747_0.level_improved = jiNeng.level_improved;
			vo_32747_0.skill_mana_cost = jiNeng.skill_mana_cost;
			vo_32747_0.skill_nimbus = jiNeng.skill_nimbus;
			vo_32747_0.skill_disabled = jiNeng.skill_disabled;
			vo_32747_0.range = jiNeng.range;
			vo_32747_0.max_range = jiNeng.max_range;
			vo_32747_0.count1 = jiNeng.count1;
			vo_32747_0.s1 = jiNeng.s1;
			vo_32747_0.s2 = jiNeng.s2;
			vo_32747_0.skillCost = jiNeng.skillCost;
			vo_32747_0.isTempSkill = 0;
			vo_32747_0List.add(vo_32747_0);
		}
		return vo_32747_0List;
	}

	public static List<Vo_32747_0> a32747(Chara chara) {
		List<Vo_32747_0> vo_32747_0List = new ArrayList<Vo_32747_0>();
		if (chara.jiNengList != null) {
			// 如果飞升仙魔自动添加这两个技能
			Vo_32747_0 xianmo = new Vo_32747_0();
			xianmo.id = chara.id;
			xianmo.skill_attrib = 1;
			xianmo.skill_attrib1 = 1;
			xianmo.skill_level = 1;
			xianmo.skill_disabled = 1;
			xianmo.range = 1;
			xianmo.max_range = 1;
			if (chara.upgrade_type == 3) {
				// 仙
				xianmo.skill_no = 303;
				vo_32747_0List.add(xianmo);
			} else if (chara.upgrade_type == 4) {
				xianmo.skill_no = 304;
				vo_32747_0List.add(xianmo);
			}
			Iterator<JiNeng> iterator = chara.jiNengList.iterator();
			while(iterator.hasNext()) {
				JiNeng jiNeng = iterator.next();
				//如果no为0删除
				if(jiNeng.skill_no == 0) {
					iterator.remove();
					continue;
				}
				Vo_32747_0 vo_32747_0 = new Vo_32747_0();
				vo_32747_0.id = chara.id;
				vo_32747_0.skill_no = jiNeng.skill_no;
				vo_32747_0.skill_attrib = jiNeng.skill_attrib;
				vo_32747_0.skill_attrib1 = jiNeng.skill_attrib1;
				vo_32747_0.skill_level = jiNeng.skill_level+jiNeng.level_improved;
				vo_32747_0.level_improved = jiNeng.level_improved;
				vo_32747_0.skill_mana_cost = jiNeng.skill_mana_cost;
				vo_32747_0.skill_nimbus = jiNeng.skill_nimbus;
				vo_32747_0.skill_disabled = jiNeng.skill_disabled;
				vo_32747_0.range = jiNeng.range;
				vo_32747_0.max_range = jiNeng.max_range;
				vo_32747_0.count1 = jiNeng.count1;
				vo_32747_0.s1 = jiNeng.s1;
				vo_32747_0.s2 = jiNeng.s2;
				vo_32747_0.isTempSkill = 0;
				vo_32747_0List.add(vo_32747_0);
			}
		}
		return vo_32747_0List;
	}

	public static List<Vo_FRIEND_ADD_CHAR> a61545(Chara chara) {
		List<Vo_FRIEND_ADD_CHAR> vo_61545_0List = new ArrayList<Vo_FRIEND_ADD_CHAR>();
		Vo_FRIEND_ADD_CHAR vo_61545_0 = new Vo_FRIEND_ADD_CHAR();
		vo_61545_0.groupBuf = "6";
		vo_61545_0.charBuf = chara.name;
		vo_61545_0.blocked = 0;
		vo_61545_0.online = 1;
		vo_61545_0.server_name1 = GameConfig.lineName;
		vo_61545_0.insider_level = chara.vipType;
		vo_61545_0.user_state = 0;
		vo_61545_0.auto_reply = 0;
		vo_61545_0.gid = chara.uuid;
		vo_61545_0.placed_amount = 0;
		vo_61545_0.tao_effect = chara.waiguan;
		vo_61545_0.skill = chara.level;
		vo_61545_0.type = chara.waiguan;
		vo_61545_0.server_name = GameConfig.lineName;
		vo_61545_0.suit_icon = chara.weapon_icon;
		vo_61545_0.party_contrib = chara.getPartyName();
		vo_61545_0.character_harmony = "";
		vo_61545_0.evolve_level = 0;
		vo_61545_0.nice = "";
		vo_61545_0.req_str = "";
		vo_61545_0.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
		vo_61545_0.iid_str = chara.uuid;
		vo_61545_0.balance = chara.cash;
		vo_61545_0.arena_rank = 1;
		vo_61545_0List.add(vo_61545_0);
		return vo_61545_0List;
	}

	public static Vo_24505_0 a24505(Chara chara) {
		Vo_24505_0 vo_24505_0 = new Vo_24505_0();
		vo_24505_0.update_type = 2;
		vo_24505_0.groupBuf = "6";
		vo_24505_0.charBuf = chara.name;
		vo_24505_0.user_state = 0;
		vo_24505_0.auto_reply = 0;
		vo_24505_0.placed_amount = 0;
		vo_24505_0.gid = "";
		vo_24505_0.tao_effect = chara.waiguan; // 角色外观
		vo_24505_0.skill = chara.level; // 角色技能
		vo_24505_0.type = chara.waiguan; // 角色外观
		vo_24505_0.server_name = GameConfig.lineName;
		vo_24505_0.suit_icon = chara.weapon_icon; // 武器外观
		vo_24505_0.party_contrib = chara.getPartyName();
		vo_24505_0.character_harmony = "";
		vo_24505_0.evolve_level = 0;
		vo_24505_0.nice = "";
		vo_24505_0.req_str = "";
		vo_24505_0.org_icon = 0;
		vo_24505_0.iid_str = chara.uuid;
		vo_24505_0.balance = chara.cash; // 角色金额
		vo_24505_0.arena_rank = 1;
		return vo_24505_0;
	}

	/**
	 * 添加邀请人员列表
	 * 
	 * @param chara
	 * @param id
	 * @param ask_type
	 */
	public static void addInvitationChara(Chara chara, int id, String ask_type) {
//		GameTeam gameTeam = new GameTeam();
//		if (GameObjectCharMng.getGameObjectChar(id).gameTeam == null) {
//			GameObjectCharMng.getGameObjectChar(id).creator(gameTeam);
//		}
		GameObjectChar team = GameObjectCharMng.getGameObjectChar(id);
		if(team != null) {
			if (team.invitationCharas == null) {
				team.invitationCharas = new HashMap<>();
			}
			team.invitationCharas.put(chara.id, chara);
			Vo_20467_0 vo_20467_0 = new Vo_20467_0();
			vo_20467_0.caption = "";
			vo_20467_0.content = "";
			vo_20467_0.peer_name = chara.name;
			vo_20467_0.ask_type = "invite_join";
			vo_20467_0.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
			vo_20467_0.iid_str = chara.uuid;
			vo_20467_0.skill = chara.level;
			vo_20467_0.str = chara.name;
			vo_20467_0.master = chara.sex;
			vo_20467_0.metal = chara.polar;
			vo_20467_0.req_str = "";
			vo_20467_0.passive_mode = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
			vo_20467_0.party_contrib = chara.getPartyName();
			vo_20467_0.teamMembersCount = 0;
			vo_20467_0.comeback_flag = 0;
			team.sendOne(new M20467_0(), vo_20467_0);
		}
	}

	public static Vo_4121_0 add4121(Chara chara, int memberteam_status) {
		Vo_4121_0 vo_4121_0 = new Vo_4121_0();
		vo_4121_0.id = chara.id;
		vo_4121_0.gid = chara.uuid;
		vo_4121_0.suit_icon = chara.suit_icon;
		vo_4121_0.weapon_icon = chara.weapon_icon;
		vo_4121_0.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
		vo_4121_0.skill = chara.level;
		vo_4121_0.str = chara.name;
		vo_4121_0.master = chara.sex;
		vo_4121_0.metal = chara.polar;
		vo_4121_0.passive_mode = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
		vo_4121_0.req_str = "";
		vo_4121_0.durability = 1;
		vo_4121_0.party_contrib = chara.getPartyName();
		vo_4121_0.upgrade_level = chara.upgrade_level;
		vo_4121_0.memberpos_x = chara.x;
		vo_4121_0.memberpos_y = chara.y;
		vo_4121_0.membermap_id = chara.mapid;
		vo_4121_0.memberteam_status = memberteam_status;
		vo_4121_0.membercard_name = "";
		vo_4121_0.membercomeback_flag = 0;
		vo_4121_0.memberlight_effect_count = chara.suit_light_effect;
		vo_4121_0.upgrade_state = chara.upgrade_state;
		return vo_4121_0;
	}

	// MSG_UPDATE_TEAM_LIST_EX --右侧队伍信息
	public static void a4121(List<Vo_4121_0> charaList) {
		List<Vo_4121_0> vo_4121_0List = new ArrayList<Vo_4121_0>();
		for (Vo_4121_0 vo41210 : charaList) {
			Vo_4121_0 vo_4121_0 = new Vo_4121_0();
			vo_4121_0.id = vo41210.id;
			vo_4121_0.gid = vo41210.gid;
			vo_4121_0.suit_icon = vo41210.suit_icon;
			vo_4121_0.weapon_icon = vo41210.weapon_icon;
			vo_4121_0.org_icon = vo41210.org_icon;
			vo_4121_0.skill = vo41210.skill;
			vo_4121_0.str = vo41210.str;
			vo_4121_0.master = vo41210.master;
			vo_4121_0.metal = vo41210.metal;
			vo_4121_0.passive_mode = vo41210.passive_mode;
			vo_4121_0.req_str = "";
			vo_4121_0.durability = 1;
			vo_4121_0.party_contrib = vo41210.party_contrib;
			vo_4121_0.upgrade_level = vo41210.upgrade_level;
			vo_4121_0.memberpos_x = vo41210.memberpos_x;
			vo_4121_0.memberpos_y = vo41210.memberpos_y;
			vo_4121_0.membermap_id = vo41210.membermap_id;
			vo_4121_0.memberteam_status = vo41210.memberteam_status;
			vo_4121_0.membercard_name = "";
			GameObjectChar team = GameObjectCharMng.getGameObjectChar(vo41210.id);
			if(team != null) {
				VoChangeCard changeCardInfo = team.chara.changeCardInfo;
				if (changeCardInfo != null) {
					vo_4121_0.membercard_name = changeCardInfo.getName();
				}
			}
			vo_4121_0.membercomeback_flag = vo41210.membercomeback_flag;
			vo_4121_0.memberlight_effect_count = 0;
			vo_4121_0List.add(vo_4121_0);
		
		}
		for (Vo_4121_0 vo41210 : vo_4121_0List) {
			GameObjectChar.send(new M4121_0(), vo_4121_0List, vo41210.id);
		}
	}

	public static void a4119(List<Chara> charaList) {
		List<Vo_4119_0> vo_4119_0List = new ArrayList<Vo_4119_0>();
		for (Chara chara : charaList) {
			Vo_4119_0 vo_4119_0 = new Vo_4119_0();
			vo_4119_0.id = chara.id;
			vo_4119_0.gid = chara.uuid;
			vo_4119_0.suit_icon = chara.suit_icon;
			vo_4119_0.weapon_icon = chara.weapon_icon;
			vo_4119_0.org_icon = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
			vo_4119_0.skill = chara.level;
			vo_4119_0.str = chara.name;
			vo_4119_0.master = chara.sex;
			vo_4119_0.metal = chara.polar;
			vo_4119_0.passive_mode = GameUtil.getWaiguan(chara.polar, chara.sex, chara);
			vo_4119_0.req_str = "";
			vo_4119_0.party_contrib = chara.getPartyName();
			vo_4119_0.upgrade_level = 0;
			vo_4119_0.membercard_name = "";
			VoChangeCard changeCardInfo = chara.changeCardInfo;
			if (changeCardInfo != null) {
				vo_4119_0.membercard_name = changeCardInfo.getName();
			}
			vo_4119_0.memberlight_effect_count = 0;
			vo_4119_0List.add(vo_4119_0);
		}
		List<Vo_45074_0> list = new ArrayList<Vo_45074_0>();
		Chara chara2 = charaList.get(0);
		for (int i = 0; i < chara2.listshouhu.size(); ++i) {
			if (chara2.listshouhu.get(i).listShouHuShuXing.get(0).nil != 0) {
				Vo_45074_0 vo_45074_0 = new Vo_45074_0();
				vo_45074_0.guardName = chara2.listshouhu.get(i).listShouHuShuXing.get(0).str;
				vo_45074_0.guardLevel = chara2.level;
				vo_45074_0.guardIcon = chara2.listshouhu.get(i).listShouHuShuXing.get(0).type;
				vo_45074_0.guardOrder = chara2.listshouhu.get(i).listShouHuShuXing.get(0).salary;
				vo_45074_0.guardId = chara2.listshouhu.get(i).id;
				list.add(vo_45074_0);
			}
		}
		for (Chara chara3 : charaList) {
			GameObjectChar team = GameObjectCharMng.getGameObjectChar(chara3.id);
			if(team != null) {
				team.sendOne(new M45074_0(), list);
				team.sendOne(new M4119_0(), vo_4119_0List);
			}
		}
	}

	// 获得标配宠物
	public static int huodechongwu(Chara chara1, String goodsName, int type, String source) {
		Pet pet = GameData.that.basePetService.findOneByName(goodsName);
		if (pet == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不存在宠物：" + goodsName;
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0, chara1.id);
			return 0;
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara1, 0, type, source);

		List<Petbeibao> list = new ArrayList<>();
		chara1.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list, chara1.id);

		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "恭喜获得#R" + goodsName;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_0,chara1.id);
		
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 2;
		vo_40964_0.name = goodsName;
		vo_40964_0.param = String.valueOf(pet.getIcon());
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0, chara1.id);
		return petbeibao.id;
	}

	// 获得满属性宠物
	public static void huodemanchongwu(Chara chara1, String goodsName, int type, String source) {
		Pet pet = GameData.that.basePetService.findOneByName(goodsName);
		if (pet == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不存在宠物：" + goodsName;
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_0);
			return;
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.FullPetCreate(pet, chara1, 0, type, source);

		List<Petbeibao> list = new ArrayList<>();
		chara1.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);

		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "恭喜获满成长#R" + goodsName;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara1.id).sendOne(new M20481_0(), vo_20481_0);
	}

	public static void huodecaifen(Chara chara, StoreInfo wupin, int owner_id, int leve, int value, String name,
			Goods goods, int add_pet_exp) {
		List<Goods> list = new ArrayList<Goods>();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsBasics = new GoodsBasics();
		goods.goodsDaoju(wupin);
		goods.goodsInfo.degree_32 = 0;
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.damage_sel_rate = 400976;
		goods.goodsInfo.attrib = leve;
		goods.goodsInfo.skill = leve;
		goods.goodsInfo.total_score = 6;
		goods.goodsInfo.damage_sel_rate = 156945;
		goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
		goods.goodsInfo.str = goods.goodsInfo.str + "·" + name;
		goods.goodsInfo.value = 8388608;
		goods.goodsInfo.damage_sel_rate = 156945;
		goods.goodsInfo.rebuild_level = 0;
		goods.goodsInfo.recognize_recognized = 274096;
		goods.goodsInfo.add_pet_exp = add_pet_exp;
		goods.goodsInfo.durability = 8;
		chara.backpack.add(goods);
		list.add(goods);
		GameObjectChar.send(new M65525_0(), list);
	}

	/**
	 * 删除某个物品
	 * @param chara
	 * @param str
	 * @param count
	 * @return
	 */
	public static int removemunber(Chara chara, String str, int count, boolean... force) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		// 如果是gm不删除物品
		if (gameObjectChar != null &&
				gameObjectChar.privilege != 0 && force.length == 0) {
			return 0;
		}
		//问道小子不扣除物品
		if(gameObjectChar != null && gameObjectChar.characters.getXiaozi() == 1) {
			return 0;
		}
		List<Goods> list1 = new ArrayList<Goods>();
		Iterator<Goods> iterator = chara.backpack.iterator();
		while(iterator.hasNext()) {
			Goods goods = iterator.next();
			if (str.equals(goods.goodsInfo.str)) {
				if (goods.goodsInfo.owner_id >= count) {
					GoodsInfo goodsInfo = goods.goodsInfo;
					goodsInfo.owner_id -= count;
					count = 0;
				} else {
					count -= goods.goodsInfo.owner_id;
					goods.goodsInfo.owner_id = 0;
				}
				if (goods.goodsInfo.owner_id == 0) {
					list1.add(goods);
				}
				List<Goods> list2 = new ArrayList<Goods>();
				list2.add(goods);
				GameObjectChar.send(new M65525_0(), list2, chara.id);
				if (count == 0) {
					break;
				}
			}
		}
		for (int i = 0; i < list1.size(); ++i) {
			chara.backpack.remove(list1.get(i));
		}
		GameObjectChar.send(new M65525_0(), chara.backpack, chara.id);
		
		return list1.size();
	}

	public static int removemunber(Chara chara, Goods goods1, int count, boolean... force) {
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		// 如果是gm不删除物品
		if (gameObjectChar.privilege != 0 && force.length == 0) {
			return 0;
		}
		//问道小子不扣除物品
		if(gameObjectChar.characters.getXiaozi() != null &&
				gameObjectChar.characters.getXiaozi() == 1) {
			return 0;
		}
		Goods removeGoods = null;
		Iterator<Goods> iterator = chara.backpack.iterator();
		while(iterator.hasNext()) {
			Goods goods2 = iterator.next();
			if (goods1 == goods2) {
				if (goods2.goodsInfo.owner_id == 1) {
					removeGoods = goods2;
				} else {
					goods2.goodsInfo.owner_id--;
					// 刷新物品
					gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(goods2));
				}
				break;
			}
		}
		if (removeGoods != null) {
			// 让物品消失
			Goods goods = new Goods();
			goods.goodsInfo = null;
			goods.goodsBasics = null;
			goods.pos = removeGoods.pos;
			gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(goods));
			chara.backpack.remove(removeGoods);
		}
		gameObjectChar.sendOne(new M65525_0(), chara.backpack);
		return removeGoods==null?0:1;
	}
	
	/**
	 * 删除物品
	 * @param gameObjectChar
	 * @param goods1
	 * @param count
	 * @return
	 */
	public static int removemunber(GameObjectChar gameObjectChar, Goods goods1, int count) {
		Chara chara = gameObjectChar.chara;
		// 如果是gm不删除物品
		if (gameObjectChar.privilege != 0) {
			return 0;
		}
		//问道小子不扣除物品
		if(gameObjectChar.characters.getXiaozi() != null &&
				gameObjectChar.characters.getXiaozi() == 1) {
			return 0;
		}
		Goods removeGoods = null;
		Iterator<Goods> iterator = chara.backpack.iterator();
		while(iterator.hasNext()) {
			Goods goods2 = iterator.next();
			if (goods1 == goods2) {
				if (goods2.goodsInfo.owner_id == 1) {
					removeGoods = goods2;
				} else {
					goods2.goodsInfo.owner_id--;
					// 刷新物品
					gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(goods2));
				}
				break;
			}
		}
		if (removeGoods != null) {
			// 让物品消失
			Goods goods = new Goods();
			goods.goodsInfo = null;
			goods.goodsBasics = null;
			goods.pos = removeGoods.pos;
			gameObjectChar.sendOne(new M65525_0(), Lists.newArrayList(goods));
			chara.backpack.remove(removeGoods);
		}
		gameObjectChar.sendOne(new M65525_0(), chara.backpack);
		return removeGoods==null?0:1;
	}

	// 制定相性几的首饰
	public static void xiangxingdengjishoushi(Chara chara, String[] levelShoushi) {
		Random r = new Random();
		String shoushiname = levelShoushi[r.nextInt(levelShoushi.length)];
		ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		huodezhuangbeixiangwu(chara, oneByStr, 0, 1);
	}

	// 随机爆对应等级的首饰
	public static void dengjishoushi(Chara chara, String[] levelShoushi) {
		Random r = new Random();
		String shoushiname = levelShoushi[r.nextInt(levelShoushi.length)];
		ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		huodezhuangbei(chara, oneByStr, 0, 1);
	}

	// （积分商城中买的未鉴定，需要自动鉴定好给玩家）鉴定装备的满属性，type=2、3、4表示鉴定蓝、粉、黄属性，count为满属性的条数
	// 目前仅允许蓝属性为满
	public static GoodsLanSe jifenjiandingmanshuxing(GoodsLanSe goodsLanse, int amount, int attrib, int type,
			int count) {
		List<Hashtable<String, Integer>> hashtables5 = ForgingEquipmentUtils.appraisalEquipment(amount, attrib, 2);
		// 将它的满属性设置为满
		if (hashtables5.get(0) != null && type == 2 && count > 0) {
			Hashtable<String, Integer> maps2 = hashtables5.get(0);
			for (String key1 : maps2.keySet()) {
				if (!key1.equals("groupNo")) {
					if (key1.equals("groupType")) {
						continue;
					}
					if (maps2.get(key1).toString().equals("0")) {
						continue;
					}
					if (count > 0) {
						hashtables5.get(0).put(key1, ForgingEquipmentUtils.getMaxValueByChineseName(
								ForgingEquipmentUtils.getEquipmentKeyByName(key1, false), attrib, amount == 3, false));
						count--;
					} else {
						break;
					}

				}
			}
		}
		// add:e
		java.util.Map<Object, Object> goodsLanSe8 = UtilObjMapshuxing.GoodsLanSe(goodsLanse);
		for (Hashtable<String, Integer> maps4 : hashtables5) {
			int groupNo2 = maps4.get("groupNo");
			int groupNolanse2 = (int) goodsLanSe8.get("groupNo"); // 蓝色2
			if (groupNolanse2 == groupNo2) {
				for (java.util.Map.Entry<String, Integer> entry8 : maps4.entrySet()) {
					goodsLanSe8.put(entry8.getKey(), entry8.getValue());
				}
			}
		}
		GoodsLanSe goodsLanSeObj6 = (GoodsLanSe) com.alibaba.fastjson.JSONObject
				.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(goodsLanSe8), GoodsLanSe.class);
		return goodsLanSeObj6;
	}
	
	/**
	 * 获得随机满属性的粉色装备
	 * @param goodsFanse
	 * @param amount
	 * @param attrib
	 * @param isAll 是否满属性
	 * @return
	 */
	public static GoodsFenSe getRandomAttrFenSe(GoodsFenSe goodsFense, int amount, int attrib, boolean isAll) {
		List<Hashtable<String, Integer>> hashtables5 = ForgingEquipmentUtils.appraisalEquipment(amount, attrib, 3);
		// 将它的满属性设置为满
		if (hashtables5.get(0) != null && isAll) {
			Hashtable<String, Integer> maps2 = hashtables5.get(0);
			for (String key1 : maps2.keySet()) {
				if (!key1.equals("groupNo") && !key1.equals("groupType")) {
					if (maps2.get(key1).toString().equals("0")) {
						continue;
					}
					hashtables5.get(0).put(key1, ForgingEquipmentUtils.getMaxValueByChineseName(
								ForgingEquipmentUtils.getEquipmentKeyByName(key1, false), attrib, amount == 3, false));
				}
			}
		}
		java.util.Map<Object, Object> goodsLanSe8 = UtilObjMapshuxing.GoodsFenSe(goodsFense);
		for (Hashtable<String, Integer> maps4 : hashtables5) {
			int groupNo2 = maps4.get("groupNo");
			int groupNolanse2 = (int) goodsLanSe8.get("groupNo");
			if (groupNolanse2 == groupNo2) {
				for (java.util.Map.Entry<String, Integer> entry8 : maps4.entrySet()) {
					goodsLanSe8.put(entry8.getKey(), entry8.getValue());
				}
			}
		}
		GoodsFenSe goodsFenSe2 = (GoodsFenSe) com.alibaba.fastjson.JSONObject
				.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(goodsLanSe8), GoodsFenSe.class);
		return goodsFenSe2;
	}
	
	/**
	 * 返回满属性的黄色装备
	 * @param goodsFanse
	 * @param amount 类型
	 * @param attrib 等级
	 * @param isAll 是否满属性
	 * @return
	 */
	public static GoodsHuangSe getRandomAttrHuangSe(GoodsHuangSe goodsHangse, int amount, int attrib, boolean isAll) {
		List<Hashtable<String, Integer>> hashtables5 = ForgingEquipmentUtils.appraisalEquipment(amount, attrib, 4);
		// 将它的满属性设置为满
		if (hashtables5.get(0) != null && isAll) {
			Hashtable<String, Integer> maps2 = hashtables5.get(0);
			for (String key1 : maps2.keySet()) {
				if (!key1.equals("groupNo") && !key1.equals("groupType")) {
					if (maps2.get(key1).toString().equals("0")) {
						continue;
					}
					hashtables5.get(0).put(key1, ForgingEquipmentUtils.getMaxValueByChineseName(
								ForgingEquipmentUtils.getEquipmentKeyByName(key1, false), attrib, amount == 3, false));
				}
			}
		}
		java.util.Map<Object, Object> goodsHuangSe8 = UtilObjMapshuxing.GoodsHuangSe(goodsHangse);
		for (Hashtable<String, Integer> maps4 : hashtables5) {
			int groupNo2 = maps4.get("groupNo");
			int groupNolanse2 = (int) goodsHuangSe8.get("groupNo");
			if (groupNolanse2 == groupNo2) {
				for (java.util.Map.Entry<String, Integer> entry8 : maps4.entrySet()) {
					goodsHuangSe8.put(entry8.getKey(), entry8.getValue());
				}
			}
		}
		GoodsHuangSe goodsHuangSe2 = (GoodsHuangSe) com.alibaba.fastjson.JSONObject
				.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(goodsHuangSe8), GoodsHuangSe.class);
		return goodsHuangSe2;
	}
	
	/**
	 *  生成绿色属性
	 * @param goods 产品
	 * @param isAll 是否满属性
	 */
	public static void getRandomGreenAttr(Goods goods, boolean isAll) {
		//绿色属性
		List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils.appraisalGreenEquipment(goods.goodsInfo.amount, goods.goodsInfo.attrib, goods.goodsInfo.suit_enabled);
       
		// 将它的满属性设置为满
		if (hashtables.get(0) != null && isAll) {
			Hashtable<String, Integer> maps2 = hashtables.get(0);
			for (String key1 : maps2.keySet()) {
				if (!key1.equals("groupNo") && !key1.equals("groupType")) {
					if (maps2.get(key1).toString().equals("0")) {
						continue;
					}
					hashtables.get(0).put(key1, ForgingEquipmentUtils.getMaxValueByChineseName(
								ForgingEquipmentUtils.getEquipmentKeyByName(key1, false), goods.goodsInfo.attrib, goods.goodsInfo.amount == 3, true));
				}
			}
		}
		//设置绿色共鸣
		if (hashtables.get(1) != null && isAll) {
			Hashtable<String, Integer> maps2 = hashtables.get(1);
			for (String key1 : maps2.keySet()) {
				if (!key1.equals("groupNo") && !key1.equals("groupType")) {
					if (maps2.get(key1).toString().equals("0")) {
						continue;
					}
					hashtables.get(1).put(key1, ForgingEquipmentUtils.getMaxValueByChineseName(
								ForgingEquipmentUtils.getEquipmentKeyByName(key1, false), goods.goodsInfo.attrib, goods.goodsInfo.amount == 3, true));
				}
			}
		}
		for (Hashtable<String, Integer> maps4 : hashtables) {
           if (maps4.get("groupNo") == 12) {
               maps4.put("groupType", 2);
               GoodsLvSe goodsLvSe = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps4), GoodsLvSe.class);
               if (goodsLvSe == null) {
                   goodsLvSe = new GoodsLvSe();
               }
               goods.goodsLvSe = goodsLvSe;
           }
           if (maps4.get("groupNo") == 8) {
               maps4.put("groupType", 2);
               GoodsLvSeGongMing goodsLvSeGongMing2 = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps4), GoodsLvSeGongMing.class);
               goods.goodsLvSeGongMing = goodsLvSeGongMing2;
           }
        }
	}
	
	/**
	 * 获得满属性共鸣
	 * @param goodsGaiZaoGongMing
	 * @param amount
	 * @param attrib
	 * @param isAll
	 * @return
	 */
	public static GoodsGaiZaoGongMing getRandomAttrGoodsGaiZaoGongMing(GoodsGaiZaoGongMing goodsGaiZaoGongMing, int amount, int attrib, boolean isAll) {
		
		List<Hashtable<String, Integer>> hashtables5 = ForgingEquipmentUtils.appraisalEquipment(amount, attrib, 8);
		// 将它的满属性设置为满
		if (hashtables5.get(0) != null && isAll) {
			Hashtable<String, Integer> maps2 = hashtables5.get(0);
			for (String key1 : maps2.keySet()) {
				if (!key1.equals("groupNo") && !key1.equals("groupType")) {
					if (maps2.get(key1).toString().equals("0")) {
						continue;
					}
					hashtables5.get(0).put(key1, ForgingEquipmentUtils.getMaxValueByChineseName(
								ForgingEquipmentUtils.getEquipmentKeyByName(key1, false), attrib, amount == 3, false));
				}
			}
		}
		java.util.Map<Object, Object> goodsGaiZaoGongMing8 = UtilObjMapshuxing.GoodsGaiZaoGongMing(goodsGaiZaoGongMing);
		for (Hashtable<String, Integer> maps4 : hashtables5) {
			int groupNo2 = maps4.get("groupNo");
			int groupNolanse2 = (int) goodsGaiZaoGongMing8.get("groupNo");
			if (groupNolanse2 == groupNo2) {
				for (java.util.Map.Entry<String, Integer> entry8 : maps4.entrySet()) {
					goodsGaiZaoGongMing8.put(entry8.getKey(), entry8.getValue());
				}
			}
		}
		GoodsGaiZaoGongMing goodsGaiZaoGongMing2 = (GoodsGaiZaoGongMing) com.alibaba.fastjson.JSONObject
				.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(goodsGaiZaoGongMing8), GoodsGaiZaoGongMing.class);
		return goodsGaiZaoGongMing2;
	}
	

	// 积分商城给首饰
	@SuppressWarnings("unchecked")
	public static void jifendengjishoushi(Chara chara, String[] levelShoushi) {
		Random r = new Random();
		String shoushiname = levelShoushi[r.nextInt(levelShoushi.length)];
		ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		// 小妖等于70级，没有蓝属性S
		if (zhuangbeiInfo2.getAttrib() <= 70) {
			GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1);
		} else {
			// 80级、120级定制首饰，80级直接给所有相性5
			List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils
					.appraisalMaxALLEquipment(zhuangbeiInfo2.getAmount(), 80, null);
			GoodsLanSe gooodsLanSe2 = null;
			if (hashtables2.size() >= 0) {
				for (Hashtable<String, Integer> maps2 : hashtables2) {
					if (maps2.get("groupNo") == 2) {
						maps2.put("groupType", 2);
						gooodsLanSe2 = new GoodsLanSe();
						gooodsLanSe2.all_resist_polar = 5;
						if (zhuangbeiInfo2.getAttrib() == 90) {
							String chineseName = ForgingEquipmentUtils.getEquipmentKeyByName("all_polar", false);
							int dst_max = ForgingEquipmentUtils.getMaxValueByChineseName(chineseName, 90, false, false);
							gooodsLanSe2.all_polar = dst_max;
						}
						if (zhuangbeiInfo2.getAttrib() == 80 || zhuangbeiInfo2.getAttrib() == 90) {
							GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, gooodsLanSe2);
							return;
						}
					}
				}
			}
			// end:80级

			// 90级到120级逐次加一条蓝属性
			java.util.Map<Object, Object> goodsLanSe = null;
			for (int i = 90; i <= 170; i += 10) {
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
				hashtables2 = ForgingEquipmentUtils.appraisalMaxALLEquipment(zhuangbeiInfo2.getAmount(), i, hashMap);
				if (hashtables2.size() >= 0) {
					for (Hashtable<String, Integer> maps2 : hashtables2) {
						if (maps2.get("groupNo") == 2) {
							maps2.put("groupType", 2);
							gooodsLanSe2 = (GoodsLanSe) com.alibaba.fastjson.JSONObject
									.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps2), GoodsLanSe.class);
							if (zhuangbeiInfo2.getAttrib() == i) {
								GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, gooodsLanSe2);
								return;
							}
						}
					}
				}
			}
		}
	}
	
	

	/**
	 * 获得满属性的首饰
	 * 
	 * @param chara
	 * @param shoushiname
	 */
	@SuppressWarnings("unchecked")
	public static void getShouShiAllAttr(Chara chara, String shoushiname) {
		GameUtil.sendMeTips("获得首饰#R" + shoushiname);
		ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		if (zhuangbeiInfo2 == null) {
			GameUtil.sendMeTips("没有找到该首饰");
			return;
		}
		// 小妖等于70级，没有蓝属性S
		if (zhuangbeiInfo2.getAttrib() <= 70) {
			GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1);
		} else {
			// 80级、120级定制首饰，80级直接给所有相性5
			List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils
					.appraisalMaxALLEquipment(zhuangbeiInfo2.getAmount(), 80, null);
			GoodsLanSe gooodsLanSe2 = null;
			if (hashtables2.size() >= 0) {
				for (Hashtable<String, Integer> maps2 : hashtables2) {
					if (maps2.get("groupNo") == 2) {
						maps2.put("groupType", 2);
						gooodsLanSe2 = new GoodsLanSe();
						gooodsLanSe2.all_resist_polar = 5;
						if (zhuangbeiInfo2.getAttrib() == 90) {
							String chineseName = ForgingEquipmentUtils.getEquipmentKeyByName("all_polar", false);
							int dst_max = ForgingEquipmentUtils.getMaxValueByChineseName(chineseName, 90, false, false);
							gooodsLanSe2.all_polar = dst_max;
						}
						if (zhuangbeiInfo2.getAttrib() == 80 || zhuangbeiInfo2.getAttrib() == 90) {
							GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, gooodsLanSe2);
							return;
						}
					}
				}
			}
			// end:80级

			// 90级到120级逐次加一条蓝属性
			java.util.Map<Object, Object> goodsLanSe = null;
			for (int i = 90; i <= 170; i += 10) {
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
				hashtables2 = ForgingEquipmentUtils.appraisalMaxALLEquipment(zhuangbeiInfo2.getAmount(), i, hashMap);
				if (hashtables2.size() >= 0) {
					for (Hashtable<String, Integer> maps2 : hashtables2) {
						if (maps2.get("groupNo") == 2) {
							maps2.put("groupType", 2);
							gooodsLanSe2 = (GoodsLanSe) com.alibaba.fastjson.JSONObject
									.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps2), GoodsLanSe.class);
							if (zhuangbeiInfo2.getAttrib() == i) {
								GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, gooodsLanSe2);
								return;
							}
						}
					}
				}
			}
		}
	}

	// 爆低级首饰
	public static void baoshoushi(Chara chara) {
		String[] shoushi = { "金刚玉镯", "七星手链", "凤舞环", "龙鳞手镯", "法文手轮", "纹龙佩", "温玉玦", "血心石", "八角晶牌", "蟠螭结", "青珑挂珠", "紫晶坠子",
				"三才项圈", "幻彩项链", "雪魂丝链" };
		Random r = new Random();
		String shoushiname = shoushi[r.nextInt(shoushi.length)];
		ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		huodezhuangbeixiangwu(chara, oneByStr, 0, 1);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "哇，爆出了首饰#Y" + shoushiname;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
	}

	// 爆高级首饰
	public static void baogjshoushi(Chara chara) {
		String[] shoushi = { "闭月双环", "三清手镯", "天星奇光", "碎梦涵光", "九天霜华", "七龙珠", "金蝉宝囊", "通灵宝玉", "寒玉龙勾", "五蕴悯光", "天机锁链",
				"秘魔灵珠", "金碧莲花", "流光绝影", "八宝如意" };
		Random r = new Random();
		String shoushiname = shoushi[r.nextInt(shoushi.length)];
		ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(shoushiname);
		huodezhuangbeixiangwu(chara, oneByStr, 0, 1);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "哇，爆出了首饰#Y" + shoushiname;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
	}

	// 爆神兽
	public static void baoshenshou(Chara chara, String source) {
		String[] shenshou = { "疆良", "东山神灵", "玄武", "朱雀", "九尾狐", "白矖" };
		Random r = new Random();
		String shenshouname = shenshou[r.nextInt(shenshou.length)];
		Pet pet = GameData.that.basePetService.findOneByName(shenshouname);
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, 4, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "哇，爆出了一只神兽#Y" + shenshouname;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
	}

	// 随机爆变异
	public static String baobianyi(Chara chara, String source) {
		String[] bianyi = { "伶俐鼠", "笨笨牛", "威威虎", "跳跳兔", "酷酷龙", "花花蛇", "溜溜马", "咩咩羊", "帅帅猴", "蛋蛋鸡", "乖乖狗", "招财猪" };
		Random r = new Random();
		String bianyiname = bianyi[r.nextInt(bianyi.length)];
		Pet pet = GameData.that.basePetService.findOneByName(bianyiname);
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, 3, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "哇，爆出了一只变异宠物#Y" + bianyiname;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
		return bianyiname;
	}

	// 给变异
	public static int huodebianyi(Chara chara, String name, String source) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		if(pet == null) {
			GameUtil.sendMeTips("不存在该变异");
			return 0;
		}
		Petbeibao petbeibao = new Petbeibao();
		petbeibao.PetCreate(pet, chara, 0, 3, source);
		List<Petbeibao> list = new ArrayList<Petbeibao>();
		chara.pets.add(petbeibao);
		list.add(petbeibao);
		GameObjectChar.send(new MSG_UPDATE_PETS(), list);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得#Y" + name;
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
		
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 2;
		vo_40964_0.name = name;
		vo_40964_0.param = String.valueOf(pet.getIcon());
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		return petbeibao.id;
	}

	// 获得道具
	public static void huodedaoju(GameObjectChar gameObjectChar, StoreInfo wupin, int owner_id) {
		if (wupin == null) {
			sendMeTips("该物品不存在");
			return;
		}
		Chara chara = gameObjectChar.chara;
		int flag = -1;
		// 如果购买的是变身卡
		if (wupin.getName().startsWith("超级") && wupin.getName().endsWith("卡")) {
			Goods goods = new Goods();
			goods.goodsDaoju(wupin);
			goods.goodsInfo.owner_id = owner_id;
			goods.goodsInfo.damage_sel_rate = 400976;
			goods.goodsInfo.degree_32 = 0; // 【重要】 道具也是已鉴定
			goods.goodsInfo.amount = 0;
			flag = GameCommonUtil.addCard(goods, chara, "变身卡已自动放入#R卡套");
			if (flag == 0) {
				Vo_61677_0 vo_61677_0 = new Vo_61677_0("card_store");
				vo_61677_0.list = chara.cardStore;
				GameObjectChar.send(new M61677_0(), vo_61677_0, chara.id);
			}
		}
		// 卡套位置不足添加到背包
		if (flag == -1) {
			Goods goods = new Goods();
			goods.goodsDaoju(wupin);
			goods.goodsInfo.owner_id = 1;
			goods.goodsInfo.damage_sel_rate = 400976;
			goods.goodsInfo.degree_32 = 0; // 【重要】 道具也是已鉴定
			goods.goodsInfo.amount = 0;
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar, owner_id);
		}
	}

	// 获得道具
	public static void huodetianshu(GameObjectChar gameObjectChar, StoreInfo wupin, int owner_id,String type) {
		if (wupin == null) {
			sendMeTips("该物品不存在");
			return;
		}
		Chara chara = gameObjectChar.chara;
		Goods goods = new Goods();
		goods.pos = 0;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsDaoju(wupin);
		goods.goodsInfo.degree_32 = 0;
		goods.goodsInfo.skill = 1;
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.damage_sel_rate = 400976;
		goods.goodsInfo.silver_coin = 6000;
		goods.goodsInfo.degree_32 = 0; // 【重要】妖石也是已鉴定
		goods.goodsInfo.amount = 0;
		goods.goodsLanSe = new GoodsLanSe();
//		log.info("GameConfig.tianshuConfig：   "+GameConfig.tianshuConfig.toString());
//		log.info("GameConfig.tianshuConfig.qixue：   "+GameConfig.tianshuConfig.qixue);
		int qixueMin = Integer.parseInt(GameConfig.tianshuConfig.qixue.split(",")[0]);
		int qixueMax = Integer.parseInt(GameConfig.tianshuConfig.qixue.split(",")[1]);

		int faliMin = Integer.parseInt(GameConfig.tianshuConfig.fali.split(",")[0]);
		int faliMax = Integer.parseInt(GameConfig.tianshuConfig.fali.split(",")[1]);

		int wushangMin = Integer.parseInt(GameConfig.tianshuConfig.wushang.split(",")[0]);
		int wushangMax = Integer.parseInt(GameConfig.tianshuConfig.wushang.split(",")[1]);

		int fashangMin = Integer.parseInt(GameConfig.tianshuConfig.fashang.split(",")[0]);
		int fashangMax = Integer.parseInt(GameConfig.tianshuConfig.fashang.split(",")[1]);
		int suduMin = Integer.parseInt(GameConfig.tianshuConfig.sudu.split(",")[0]);
		int suduMax = Integer.parseInt(GameConfig.tianshuConfig.sudu.split(",")[1]);
		int fangyuMin = Integer.parseInt(GameConfig.tianshuConfig.fangyu.split(",")[0]);
		int fangyuMax = Integer.parseInt(GameConfig.tianshuConfig.fangyu.split(",")[1]);
		/**
		 * 		hashtable.put("法伤", "mana");
		 * 		hashtable.put("气血", "def");
		 * 		hashtable.put("防御", "wiz");
		 * 		hashtable.put("速度", "parry");
		 * 		hashtable.put("物伤", "accurate");
		 * 		hashtable.put("法力", "dex");
		 */
		if("man".equals(type)){
			goods.goodsLanSe.def = qixueMax;
			goods.goodsLanSe.dex = faliMax;
			goods.goodsLanSe.accurate = wushangMax;
			goods.goodsLanSe.mana = fashangMax;
			goods.goodsLanSe.parry = suduMax;
			goods.goodsLanSe.wiz = fangyuMax;
		}else{
			goods.goodsLanSe.def = qixueMin + new Random().nextInt(qixueMax-qixueMin);
			goods.goodsLanSe.dex = faliMin + new Random().nextInt(faliMax-faliMin);
			goods.goodsLanSe.accurate = wushangMin + new Random().nextInt(wushangMax-wushangMin);
			goods.goodsLanSe.mana = fashangMin + new Random().nextInt(fashangMax-fashangMin);
			goods.goodsLanSe.parry = suduMin + new Random().nextInt(suduMax-suduMin);
			goods.goodsLanSe.wiz = fangyuMin + new Random().nextInt(fangyuMax-fangyuMin);
		}
		GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar, owner_id);

//		if("超级魔引 超级狂暴 超级怒击 超级破天 超级反击 超级降魔斩 超级修罗术 超级云体 超级仙风 超级尽忠 超级惊雷 超级青木 超级寒冰 超级烈炎 超级碎石".contains(wupin.getName())){
//			goods.pos = 0;
//			goods.goodsInfo = new GoodsInfo();
//			goods.goodsDaoju(wupin);
//			goods.goodsInfo.degree_32 = 0;
//			goods.goodsInfo.skill = 1;
//			goods.goodsInfo.owner_id = 1;
//			goods.goodsInfo.damage_sel_rate = 400976;
//			goods.goodsInfo.silver_coin = 6000;
//			goods.goodsInfo.degree_32 = 0; // 【重要】妖石也是已鉴定
//			goods.goodsInfo.amount = 0;
//			goods.goodsLanSe = new GoodsLanSe();
//			goods.goodsLanSe.wiz = 380 + new Random().nextInt(171);
//			goods.goodsLanSe.accurate = 380 + new Random().nextInt(171);
//			goods.goodsLanSe.mana = 380 + new Random().nextInt(171);
//			goods.goodsLanSe.def = 380 + new Random().nextInt(171);
//			GameUtil.addwupin(goods, chara);
//			GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
//		}else{
//			goods.goodsDaoju(wupin);
//			goods.goodsInfo.owner_id = 1;
//			goods.goodsInfo.damage_sel_rate = 400976;
//			goods.goodsInfo.degree_32 = 0; // 【重要】 道具也是已鉴定
//			goods.goodsInfo.amount = 0;
//			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar, owner_id);
//		}


	}


	// 获得道具
	public static boolean huodedaoju(Chara chara, String name, int owner_id) {
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
		if(info == null) {
			GameCommonUtil.sendTips("未找到该道具#R"+name+"！",chara.id);
			return false;
		}
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			log.info("包裹数量：{},玩家名字:{}", pos2, chara.name);
			return false;
		}
		goods.pos = pos2;
		goods.goodsDaoju(info);
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.damage_sel_rate = goods.pos;
		goods.goodsInfo.degree_32 = 0; // 【重要】 道具也是已鉴定
		addwupin(goods, chara);
		return true;
	}
	
	public static String huodedaojuGetGoods(Chara chara, String name, int owner_id) {
		StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
		if(info == null) {
			GameCommonUtil.sendTips("未找到该道具#R"+name+"！",chara.id);
			return null;
		}
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			log.info("包裹数量：{},玩家名字:{}", pos2, chara.name);
			return null;
		}
		goods.pos = pos2;
		goods.goodsDaoju(info);
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.damage_sel_rate = goods.pos;
		goods.goodsInfo.degree_32 = 0; // 【重要】 道具也是已鉴定
		addwupin(goods, chara);
		return goods.goodsInfo.auto_fight;
	}

	// 获得装备
	public static void huodezhuangbei(Chara chara, ZhuangbeiInfo zhuangb, int degree_32, Goods goods) {
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsInfo = new GoodsInfo();
		goods.goodsBasics = new GoodsBasics();
		goods.goodsLanSe = new GoodsLanSe();
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.degree_32 = degree_32;
		chara.backpack.add(goods);
		GameObjectChar.send(new M65525_0(), chara.backpack, chara.id);
	}

	public static void huodezhuangbei(Chara chara, ZhuangbeiInfo zhuangb, int degree_32, int owner_id,
			GoodsLanSe goodsLanSe) {
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsLanSe = goodsLanSe;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.degree_32 = degree_32;
		chara.backpack.add(goods);
		GameObjectChar.send(new M65525_0(), chara.backpack, chara.id);
	}

	// 获得相5的首饰
	public static void huodezhuangbeixiangwu(Chara chara, ZhuangbeiInfo zhuangb, int degree_32, int owner_id) {
		if (zhuangb == null) {
			GameUtil.sendMeTips("没有找到这个首饰");
			return;
		}
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.degree_32 = degree_32;
		goods.goodsLanSe.all_resist_polar = 5;
		addwupin(goods, chara);
		GameObjectChar.send(new M65525_0(), chara.backpack);

		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得相5首饰#R" + goods.goodsInfo.str + "";
		vo_20481_0.time = 1562987118;
		GameObjectChar.send(new M20481_0(), vo_20481_0);
	}

	// 从积分商城用积分获得装备的
	public static void jifenhuodezhuangbei(GameObjectChar gameObjectChar, ZhuangbeiInfo zhuangb, int type, int count) {
		Chara chara = gameObjectChar.chara;
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = 0;
		goods.goodsLanSe = jifenjiandingmanshuxing(goods.goodsLanSe, goods.goodsInfo.amount, goods.goodsInfo.attrib,
				type, count);
		GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar, 1);
		gameObjectChar.sendOne(new M65525_0(), chara.backpack);

		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得了#R" + goods.goodsInfo.str + "";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
	}
	
	/**
	 * 获取满属性的装备
	 * @param chara
	 * @param zhuangb
	 * @param type
	 * @param count
	 */
	public static void getAllEquip(Chara chara, ZhuangbeiInfo zhuangb, int type, int count) {
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = 0;
		goods.goodsLanSe = jifenjiandingmanshuxing(goods.goodsLanSe, goods.goodsInfo.amount, goods.goodsInfo.attrib,
				type, count);
		addwupin(goods, chara);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);

		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得了#R" + goods.goodsInfo.str + "";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
	}
	

	public static void huodezhuangbei(Chara chara, ZhuangbeiInfo zhuangb, int degree_32, int owner_id) {
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.degree_32 = degree_32;
		addwupin(goods, chara);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得了#R" + goods.goodsInfo.str + "";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//        GameObjectChar.getGameObjectChar();
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);
//        GameObjectChar.send(new M20481_0(), vo_20481_0);
	}
	
	/**
	 * 获取太阴之气
	 * @param gameObjectChar
	 * @param zhuangb
	 */
	public static void geiTaiYinZhiQi(GameObjectChar gameObjectChar) {
		ZhuangbeiInfo zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr("太阴之气");
		Chara chara = gameObjectChar.chara;
		Goods goods = new Goods();
		List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
		int pos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
		if (pos == -1) {
			return;
		}
		goods.pos = pos;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.total_score = 31;
		goods.goodsInfo.quality = "金色";
		goods.goodsInfo.type = 2138;
		//唯一码
		goods.goodsInfo.damage_sel_rate = pos;
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = 0;
		chara.tyzqStore.add(goods);
		//刷新仓库
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.list = chara.tyzqStore;
		vo_61677_0.store_type = "tyzq_store";
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = "获得了#R" + goods.goodsInfo.str + "";
		vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
		gameObjectChar.sendOne(new M20481_0(), vo_20481_0);
	}
	
	/**
	 * 获取太阴之气
	 * @param gameObjectChar
	 * @param zhuangb
	 */
	public static void addTaiYinZhiQi(GameObjectChar gameObjectChar, Goods goods) {
		Chara chara = gameObjectChar.chara;
		if(goods != null) {
			List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
			int pos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
			if (pos == -1) {
				return;
			}
			goods.pos = pos;
			//唯一码
			goods.goodsInfo.damage_sel_rate = pos;
			chara.tyzqStore.add(goods);
			//刷新仓库
			Vo_61677_0 vo_61677_0 = new Vo_61677_0();
			vo_61677_0.list = chara.tyzqStore;
			vo_61677_0.store_type = "tyzq_store";
			GameObjectChar.send(new M61677_0(), vo_61677_0);
		}
	}
	
	/**
	 * 获取随机属性的太阴之气
	 * @param gameObjectChar
	 */
	public static void getTyzqRandomAttr(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
		int pos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
		if (pos == -1) {
			GameUtil.sendMeTips("背包不足");
			return;
		}
		ZhuangbeiInfo zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr("太阴之气");
		Goods goods = new Goods();
		goods.pos = pos;
		goods.goodsCreate(zhuangb);
		GoodsLanSe lanse = goods.goodsLanSe;
		Class<? extends GoodsLanSe> lanClass = lanse.getClass();
		goods.goodsInfo.total_score = 31;
		String[] colors = {"蓝色","粉色","金色"};
		goods.goodsInfo.quality = colors[ThreadLocalRandom.current().nextInt(3)];
		try {
			List<String> attrNames = new ArrayList<>();
			attrNames.add("phy_power");
			attrNames.add("mag_power");
			attrNames.add("max_life");
			attrNames.add("def");
			attrNames.add("speed");
			attrNames.add("str");
			attrNames.add("wiz");
			attrNames.add("dex");
			attrNames.add("con");
			attrNames.add("double_hit_rate");
			attrNames.add("ignore_all_resist_except");
			attrNames.add("all_resist_polar");
			goods.goodsInfo.type = 2136;
			
			if(goods.goodsInfo.quality.equals("粉色")) {
				//随机两条属性
				goods.goodsInfo.type = 2137;
				String attrName1 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no1 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName1,false));
				TyzqAttribVo no1t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName1);
				int maxValNo1t = no1t.getPropMaxValue();
				no1.set(lanse, ThreadLocalRandom.current().nextInt(maxValNo1t)+1);
				
				String attrName2 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no2 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName2,false));
				TyzqAttribVo no2t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName2);
				int maxValNo2t = no2t.getPropMaxValue();
				no2.set(lanse, ThreadLocalRandom.current().nextInt(maxValNo2t)+1);
				
			}else if(goods.goodsInfo.quality.equals("金色")){
				//随机三条属性
				goods.goodsInfo.type = 2138;
				String attrName1 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no1 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName1,false));
				TyzqAttribVo no1t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName1);
				int maxValNo1t = no1t.getPropMaxValue();
				no1.set(lanse, ThreadLocalRandom.current().nextInt(maxValNo1t)+1);
				
				String attrName2 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no2 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName2,false));
				TyzqAttribVo no2t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName2);
				int maxValNo2t = no2t.getPropMaxValue();
				no2.set(lanse, ThreadLocalRandom.current().nextInt(maxValNo2t)+1);
				
				String attrName3 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no3 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName3,false));
				TyzqAttribVo no3t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName3);
				int maxValNo3t = no3t.getPropMaxValue();
				no3.set(lanse, ThreadLocalRandom.current().nextInt(maxValNo3t)+1);
			}else {
				String attrName1 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no1 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName1,false));
				TyzqAttribVo no1t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName1);
				int maxValNo1t = no1t.getPropMaxValue();
				no1.set(lanse, ThreadLocalRandom.current().nextInt(maxValNo1t)+1);
			}
		} catch (Exception e) {
			log.error("{}",e);
		}
		//唯一码
		goods.goodsInfo.damage_sel_rate = pos;
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = 0;
		addTaiYinZhiQi(gameObjectChar, goods);
	}
	
	/**
	 * 获取随机属性的太阴之气满值
	 * @param gameObjectChar
	 */
	public static void getTyzqRandomAttrFullVal(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		List<Integer> allPos = Stream.iterate(5401, item->item+1).limit(99).collect(Collectors.toList());
		int pos = GameCommonUtil.getAvaliablePos(chara.tyzqStore, allPos);
		if (pos == -1) {
			GameUtil.sendMeTips("背包不足");
			return;
		}
		ZhuangbeiInfo zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr("太阴之气");
		Goods goods = new Goods();
		goods.pos = pos;
		goods.goodsCreate(zhuangb);
		GoodsLanSe lanse = goods.goodsLanSe;
		Class<? extends GoodsLanSe> lanClass = lanse.getClass();
		goods.goodsInfo.total_score = 31;
		String[] colors = {"蓝色","粉色","金色"};
		goods.goodsInfo.quality = colors[ThreadLocalRandom.current().nextInt(3)];
		try {
			List<String> attrNames = new ArrayList<>();
			attrNames.add("phy_power");
			attrNames.add("mag_power");
			attrNames.add("max_life");
			attrNames.add("def");
			attrNames.add("speed");
			attrNames.add("str");
			attrNames.add("wiz");
			attrNames.add("dex");
			attrNames.add("con");
			attrNames.add("double_hit_rate");
			attrNames.add("ignore_all_resist_except");
			attrNames.add("all_resist_polar");
			goods.goodsInfo.type = 2136;
			
			if(goods.goodsInfo.quality.equals("粉色")) {
				//随机两条属性
				goods.goodsInfo.type = 2137;
				String attrName1 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no1 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName1,false));
				TyzqAttribVo no1t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName1);
				int maxValNo1t = no1t.getPropMaxValue();
				no1.set(lanse, maxValNo1t);
				
				String attrName2 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no2 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName2,false));
				TyzqAttribVo no2t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName2);
				int maxValNo2t = no2t.getPropMaxValue();
				no2.set(lanse, maxValNo2t);
				
			}else if(goods.goodsInfo.quality.equals("金色")){
				//随机三条属性
				goods.goodsInfo.type = 2138;
				String attrName1 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no1 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName1,false));
				TyzqAttribVo no1t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName1);
				int maxValNo1t = no1t.getPropMaxValue();
				no1.set(lanse, maxValNo1t);
				
				String attrName2 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no2 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName2,false));
				TyzqAttribVo no2t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName2);
				int maxValNo2t = no2t.getPropMaxValue();
				no2.set(lanse, maxValNo2t);
				
				String attrName3 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no3 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName3,false));
				TyzqAttribVo no3t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName3);
				int maxValNo3t = no3t.getPropMaxValue();
				no3.set(lanse, maxValNo3t);
			}else {
				String attrName1 = attrNames.remove(ThreadLocalRandom.current().nextInt(attrNames.size()));
				Field no1 = lanClass.getField(ForgingEquipmentUtils.getErrorFieldByOriginField(attrName1,false));
				TyzqAttribVo no1t = GameConfig.tyzqAttribConfig.getTyzqArryibs().get(attrName1);
				int maxValNo1t = no1t.getPropMaxValue();
				no1.set(lanse, maxValNo1t);
			}
		} catch (Exception e) {
			log.error("{}",e);
		}
		//唯一码
		goods.goodsInfo.damage_sel_rate = pos;
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.degree_32 = 0;
		addTaiYinZhiQi(gameObjectChar, goods);
	}

	public static void huodezhuangbei(Chara chara, ZhuangbeiInfo zhuangb, int degree_32) {
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.degree_32 = degree_32;
		chara.backpack.add(goods);
//        GameObjectChar.send(new M65525_0(), chara.backpack);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
	}

	// 返回下一个可用的背包位置
	public static int packPoint(Chara chara) {
		int avaliablePos = GameCommonUtil.getAvaliablePos(chara.backpack, GameCommonUtil.getBackpackPos(chara));
		if (avaliablePos == -1) {
			GameCommonUtil.sendTips("#R包裹不足，#W请及时清理。", chara.id);
		}
		return avaliablePos;
	}



	// 通过角色穿戴的装备来更新角色的所有装备属性
	public static void zhuangbeiValue(GameObjectChar gameObjectChar) {
		try {

			Chara chara = gameObjectChar.chara;
			chara.zbAttribute = null;
			chara.zbAttribute = new ZbAttribute();
			chara.zbAttribute.accurate = 0;
			chara.zbAttribute.def = 0;
			chara.zbAttribute.dex = 0;
			chara.zbAttribute.mana = 0;
			chara.zbAttribute.parry = 0;
			chara.zbAttribute.wiz = 0;
			int taozhuang = 0;
			int qianghua = 0;
			int dengji1 = 0;
			int dengji2 = 0;
			int dengji3 = 0;
			int dengji4 = 0;
			int tao1 = 0;
			int tao2 = 0;
			int tao3 = 0;
			int tao4 = 0;
			int color = 20;
			int jinhua1 = 0;
			int jinhua2 = 0;
			int jinhua3 = 0;
			int jinhua10 = 0;
			List<Goods> otherGoods = chara.otherGoods;
			for (int i = 0; i < otherGoods.size(); ++i) {
				Goods goods = chara.otherGoods.get(i);
				GameCommonUtil.setGoodsDefaultValue(goods, true);
				if (goods.pos == 1 || goods.pos == 2 || goods.pos == 3
						|| goods.pos == 10) {
					java.util.Map<Object, Object> map = UtilObjMapshuxing
							.GoodsLvSeGongMing(goods.goodsLvSeGongMing);
					Iterator<java.util.Map.Entry<Object, Object>> it = map.entrySet().iterator();
					while (it.hasNext()) {
						java.util.Map.Entry<Object, Object> entry = it.next();
						if(entry.getValue() == null) {
							it.remove();
						}
						else if (entry.getValue().equals(0)) {
							it.remove();
						}
					}
					if (map.size() >= 3) {
						++taozhuang;
					}

					// 改造等级、强化数量
					map = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
					it = map.entrySet().iterator();
					while (it.hasNext()) {
						java.util.Map.Entry<Object, Object> entry = it.next();
						if(entry.getValue() == null) {
							it.remove();
						}
						else if (entry.getValue().equals(0)) {
							it.remove();
						}
					}
					if (map.size() >= 3) {
						if (goods.goodsInfo.color < color) {
							color = goods.goodsInfo.color;
						}
						++qianghua;
					}
					if (goods.pos == 1) {
						dengji1 = goods.goodsInfo.attrib;
						tao1 = goods.goodsInfo.suit_enabled;
						jinhua1 = goods.goodsInfo.mailing_item_times;
					}
					if (goods.pos == 2) {
						dengji2 = goods.goodsInfo.attrib;
						tao2 = goods.goodsInfo.suit_enabled;
						jinhua2 = goods.goodsInfo.mailing_item_times;
					}
					if (goods.pos == 3) {
						dengji3 = goods.goodsInfo.attrib;
						tao3 = goods.goodsInfo.suit_enabled;
						jinhua3 = goods.goodsInfo.mailing_item_times;
					}
					if (goods.pos == 10) {
						dengji4 = goods.goodsInfo.attrib;
						tao4 = goods.goodsInfo.suit_enabled;
						jinhua10 = goods.goodsInfo.mailing_item_times;
					}
				}
			}
			int l1 = dengji1-jinhua1;
			int l2 = dengji2-jinhua2;
			int l3 = dengji3-jinhua3;
			int l4 = dengji4-jinhua10;
			if (taozhuang == 4 && l1 == l2 && l2 == l3 && l3 == l4
					&& tao1 == tao2 && tao2 == tao3 && tao3 == tao4) {
				int e1 = Math.abs(l2+jinhua2-dengji3);
				int e2 = Math.abs(l2+jinhua2-dengji3);
				int e3 = Math.abs(l3+jinhua3-dengji4);
				int e4 = Math.abs(l4+jinhua10-dengji1);
				int toudingchenghao = 0;
				if(e1 < 3 && e2 < 3 
						&& e3 < 3 && e4 < 3) {
					int[] suit = SuitEffectUtils.suit(chara.sex - 1, dengji4, chara.polar, tao1);
					chara.suit_icon = suit[0];
					if(GameConfig.config.getTouDingChengHao().getChenghao()!=null && !"".equals(GameConfig.config.getTouDingChengHao().getChenghao())){
						String chenghao[] = GameConfig.config.getTouDingChengHao().getChenghao();
						for(int i=0;i<chenghao.length;i++)
						{
							String[] strArr = chenghao[i].split(":");
							for (int y = 0; y < strArr.length; ++y){
								if(chara.chenhao.equals(strArr[0])){
									toudingchenghao = Integer.parseInt(strArr[1]);
								}
							}
						}
					}
					chara.suit_light_effect =suit[1];
					for (int j = 0; j < chara.otherGoods.size(); ++j) {
						Goods goods = chara.otherGoods.get(j);
						if (goods.pos == 1 || goods.pos == 2 || goods.pos == 3
								|| goods.pos == 10) {
							goods.goodsInfo.gift = 1;
						}
					}
				}
			} else {
				chara.suit_icon = 0;
				chara.suit_light_effect = 0;
				for (int i = 0; i < chara.otherGoods.size(); ++i) {
					if (chara.otherGoods.get(i).pos == 1 || chara.otherGoods.get(i).pos == 2 || chara.otherGoods.get(i).pos == 3
							|| chara.otherGoods.get(i).pos == 10) {
						chara.otherGoods.get(i).goodsInfo.gift = 0;
					}
				}
			}

			// 改造共鸣
			if (qianghua == 4 && dengji1 == dengji2 && dengji2 == dengji3 && dengji3 == dengji4) {
				for (int i = 0; i < otherGoods.size(); ++i) {
					Goods goods = chara.otherGoods.get(i);
					if (goods.pos == 1 || goods.pos == 2 || goods.pos == 3
							|| chara.otherGoods.get(i).pos == 10) {
						java.util.Map<Object, Object> map = UtilObjMapshuxing
								.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
						goods.goodsGaiZaoGongMingChengGong = (GoodsGaiZaoGongMingChengGong) com.alibaba.fastjson.JSONObject
										.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(map),
												GoodsGaiZaoGongMingChengGong.class);
						goods.goodsGaiZaoGongMingChengGong.groupNo = 29;
						goods.goodsGaiZaoGongMingChengGong.color = color;
					}
				}
			} else {
				for (int i = 0; i < chara.otherGoods.size(); ++i) {
					chara.otherGoods.get(i).goodsGaiZaoGongMingChengGong = new GoodsGaiZaoGongMingChengGong();
				}
			}
			gameObjectChar.sendOne(new M65525_0(), chara.otherGoods);

			//所有技能上升
			int extUpSkillAllLevel = 0;
			
			// 遍历背包
			for (int i = 0; i < otherGoods.size(); ++i) {
				Goods good = chara.otherGoods.get(i);
				// 装备属性
				if (good.pos > 0 && good.pos <= 10) {
					ZbAttribute zbAttribute1 = chara.zbAttribute;
					// 物品基础属性
					zbAttribute1.accurate += good.goodsBasics.accurate;
					zbAttribute1.def += good.goodsBasics.def;
					zbAttribute1.dex += good.goodsBasics.dex;
					zbAttribute1.mana += good.goodsBasics.mana;
					zbAttribute1.parry += good.goodsBasics.parry;
					zbAttribute1.wiz += good.goodsBasics.wiz;
					
					
					/*----------------------------蓝色属性-------------------------------s*/
					chara.zbAttribute.phy_power = chara.zbAttribute.phy_power + good.goodsLanSe.phy_power
							+ good.goodsLanSe.all_polar;
					chara.zbAttribute.mag_power = chara.zbAttribute.mag_power + good.goodsLanSe.mag_power
							+ good.goodsLanSe.all_polar;
					chara.zbAttribute.speed = chara.zbAttribute.speed + good.goodsLanSe.speed + good.goodsLanSe.all_polar;
					chara.zbAttribute.life = chara.zbAttribute.life + good.goodsLanSe.life + good.goodsLanSe.all_polar;
					zbAttribute1.skill_low_cost += good.goodsLanSe.skill_low_cost;

					zbAttribute1.mstunt_rate += good.goodsLanSe.mstunt_rate;
					// 人物相性
					chara.zbAttribute.wood = chara.zbAttribute.wood + good.goodsLanSe.wood
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.water = chara.zbAttribute.water + good.goodsLanSe.water
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.fire = chara.zbAttribute.fire + good.goodsLanSe.fire
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.earth = chara.zbAttribute.earth + good.goodsLanSe.earth
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.resist_metal = chara.zbAttribute.resist_metal + good.goodsLanSe.resist_metal
							+ good.goodsLanSe.all_resist_polar;
					

					zbAttribute1.damage_sel += good.goodsLanSe.damage_sel;
					zbAttribute1.stunt_rate += good.goodsLanSe.stunt_rate;
					zbAttribute1.double_hit_rate += good.goodsLanSe.double_hit_rate;
					zbAttribute1.release_forgotten += good.goodsLanSe.release_forgotten;
					zbAttribute1.ignore_all_resist_except += good.goodsLanSe.ignore_all_resist_except;
					zbAttribute1.stunt += good.goodsLanSe.stunt;
					zbAttribute1.def += good.goodsLanSe.def;
					zbAttribute1.dex += good.goodsLanSe.dex;
					zbAttribute1.wiz += good.goodsLanSe.wiz;
					zbAttribute1.family += good.goodsLanSe.family;
					zbAttribute1.life_recover += good.goodsLanSe.life_recover;
					zbAttribute1.all_skill += good.goodsLanSe.all_skill;
					zbAttribute1.portrait += good.goodsLanSe.portrait;
					zbAttribute1.resist_frozen += good.goodsLanSe.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsLanSe.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsLanSe.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsLanSe.resist_confusion;
					zbAttribute1.longevity += good.goodsLanSe.longevity;
					zbAttribute1.resist_wood += good.goodsLanSe.resist_wood;
					zbAttribute1.resist_water += good.goodsLanSe.resist_water;
					zbAttribute1.resist_fire += good.goodsLanSe.resist_fire;
					zbAttribute1.resist_earth += good.goodsLanSe.resist_earth;
					zbAttribute1.exp_to_next_level += good.goodsLanSe.exp_to_next_level;
					zbAttribute1.all_resist_except += good.goodsLanSe.all_resist_except;
					zbAttribute1.accurate += good.goodsLanSe.accurate;
					zbAttribute1.mana += good.goodsLanSe.mana;
					zbAttribute1.parry += good.goodsLanSe.parry;
					zbAttribute1.ignore_resist_wood += good.goodsLanSe.ignore_resist_wood;
					zbAttribute1.ignore_resist_water += good.goodsLanSe.ignore_resist_water;
					zbAttribute1.ignore_resist_fire += good.goodsLanSe.ignore_resist_fire;
					zbAttribute1.ignore_resist_earth += good.goodsLanSe.ignore_resist_earth;
					zbAttribute1.ignore_resist_forgotten += good.goodsLanSe.ignore_resist_forgotten;
					zbAttribute1.ignore_resist_frozen += good.goodsLanSe.ignore_resist_frozen;
					zbAttribute1.ignore_resist_sleep += good.goodsLanSe.ignore_resist_sleep;
					zbAttribute1.ignore_resist_confusion += good.goodsLanSe.ignore_resist_confusion;
					zbAttribute1.super_excluse_metal += good.goodsLanSe.super_excluse_metal;
					zbAttribute1.ignore_resist_poison += good.goodsLanSe.ignore_resist_poison;
					/*----------------------------蓝色属性-------------------------------e*/
					

					/*----------------------------黄色属性-------------------------------s*/
					chara.zbAttribute.phy_power = chara.zbAttribute.phy_power + good.goodsHuangSe.phy_power
							+ good.goodsHuangSe.all_polar;
					chara.zbAttribute.mag_power = chara.zbAttribute.mag_power + good.goodsHuangSe.mag_power
							+ good.goodsHuangSe.all_polar;
					chara.zbAttribute.speed = chara.zbAttribute.speed + good.goodsHuangSe.speed
							+ good.goodsHuangSe.all_polar;
					chara.zbAttribute.life = chara.zbAttribute.life + good.goodsHuangSe.life + good.goodsHuangSe.all_polar;
					zbAttribute1.skill_low_cost += good.goodsHuangSe.skill_low_cost;
					zbAttribute1.mstunt_rate += good.goodsHuangSe.mstunt_rate;
					chara.zbAttribute.wood = chara.zbAttribute.wood + good.goodsHuangSe.wood
							+ good.goodsHuangSe.all_resist_polar;
					chara.zbAttribute.water = chara.zbAttribute.water + good.goodsHuangSe.water
							+ good.goodsHuangSe.all_resist_polar;
					chara.zbAttribute.fire = chara.zbAttribute.fire + good.goodsHuangSe.fire
							+ good.goodsHuangSe.all_resist_polar;
					chara.zbAttribute.earth = chara.zbAttribute.earth + good.goodsHuangSe.earth
							+ good.goodsHuangSe.all_resist_polar;
					chara.zbAttribute.resist_metal = chara.zbAttribute.resist_metal + good.goodsHuangSe.resist_metal
							+ good.goodsHuangSe.all_resist_polar;
					
					zbAttribute1.damage_sel += good.goodsHuangSe.damage_sel;
					zbAttribute1.stunt_rate += good.goodsHuangSe.stunt_rate;
					zbAttribute1.double_hit_rate += good.goodsHuangSe.double_hit_rate;
					zbAttribute1.release_forgotten += good.goodsHuangSe.release_forgotten;
					zbAttribute1.ignore_all_resist_except += good.goodsHuangSe.ignore_all_resist_except;
					zbAttribute1.stunt += good.goodsHuangSe.stunt;
					zbAttribute1.def += good.goodsHuangSe.def;
					zbAttribute1.dex += good.goodsHuangSe.dex;
					zbAttribute1.wiz += good.goodsHuangSe.wiz;
					zbAttribute1.family += good.goodsHuangSe.family;
					zbAttribute1.life_recover += good.goodsHuangSe.life_recover;
					zbAttribute1.all_skill += good.goodsHuangSe.all_skill;
					zbAttribute1.portrait += good.goodsHuangSe.portrait;
					zbAttribute1.resist_frozen += good.goodsHuangSe.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsHuangSe.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsHuangSe.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsHuangSe.resist_confusion;
					zbAttribute1.longevity += good.goodsHuangSe.longevity;
					zbAttribute1.resist_wood += good.goodsHuangSe.resist_wood;
					zbAttribute1.resist_water += good.goodsHuangSe.resist_water;
					zbAttribute1.resist_fire += good.goodsHuangSe.resist_fire;
					zbAttribute1.resist_earth += good.goodsHuangSe.resist_earth;
					zbAttribute1.exp_to_next_level += good.goodsHuangSe.exp_to_next_level;
					zbAttribute1.all_resist_except += good.goodsHuangSe.all_resist_except;
					zbAttribute1.accurate += good.goodsHuangSe.accurate;
					zbAttribute1.mana += good.goodsHuangSe.mana;
					zbAttribute1.parry += good.goodsHuangSe.parry;
					zbAttribute1.ignore_resist_wood += good.goodsHuangSe.ignore_resist_wood;
					zbAttribute1.ignore_resist_water += good.goodsHuangSe.ignore_resist_water;
					zbAttribute1.ignore_resist_fire += good.goodsHuangSe.ignore_resist_fire;
					zbAttribute1.ignore_resist_earth += good.goodsHuangSe.ignore_resist_earth;
					zbAttribute1.ignore_resist_forgotten += good.goodsHuangSe.ignore_resist_forgotten;
					zbAttribute1.ignore_resist_frozen += good.goodsHuangSe.ignore_resist_frozen;
					zbAttribute1.ignore_resist_sleep += good.goodsHuangSe.ignore_resist_sleep;
					zbAttribute1.ignore_resist_confusion += good.goodsHuangSe.ignore_resist_confusion;
					zbAttribute1.super_excluse_metal += good.goodsHuangSe.super_excluse_metal;
					zbAttribute1.ignore_resist_poison += good.goodsHuangSe.ignore_resist_poison;
					/*----------------------------黄色属性-------------------------------e*/
					

					/*----------------------------粉色属性-------------------------------s*/
					chara.zbAttribute.phy_power = chara.zbAttribute.phy_power + good.goodsFenSe.phy_power
							+ good.goodsFenSe.all_polar;
					chara.zbAttribute.mag_power = chara.zbAttribute.mag_power + good.goodsFenSe.mag_power
							+ good.goodsFenSe.all_polar;
					chara.zbAttribute.speed = chara.zbAttribute.speed + good.goodsFenSe.speed + good.goodsFenSe.all_polar;
					chara.zbAttribute.life = chara.zbAttribute.life + good.goodsFenSe.life + good.goodsFenSe.all_polar;
					zbAttribute1.skill_low_cost += good.goodsFenSe.skill_low_cost;
					zbAttribute1.mstunt_rate += good.goodsFenSe.mstunt_rate;

					chara.zbAttribute.wood = chara.zbAttribute.wood + good.goodsFenSe.wood
							+ good.goodsFenSe.all_resist_polar;
					chara.zbAttribute.water = chara.zbAttribute.water + good.goodsFenSe.water
							+ good.goodsFenSe.all_resist_polar;
					chara.zbAttribute.fire = chara.zbAttribute.fire + good.goodsFenSe.fire
							+ good.goodsFenSe.all_resist_polar;
					chara.zbAttribute.earth = chara.zbAttribute.earth + good.goodsFenSe.earth
							+ good.goodsFenSe.all_resist_polar;
					chara.zbAttribute.resist_metal = chara.zbAttribute.resist_metal + good.goodsFenSe.resist_metal
							+ good.goodsFenSe.all_resist_polar;
					
					zbAttribute1.damage_sel += good.goodsFenSe.damage_sel;
					zbAttribute1.stunt_rate += good.goodsFenSe.stunt_rate;
					zbAttribute1.double_hit_rate += good.goodsFenSe.double_hit_rate;
					zbAttribute1.release_forgotten += good.goodsFenSe.release_forgotten;
					zbAttribute1.ignore_all_resist_except += good.goodsFenSe.ignore_all_resist_except;
					zbAttribute1.stunt += good.goodsFenSe.stunt;
					zbAttribute1.def += good.goodsFenSe.def;
					zbAttribute1.dex += good.goodsFenSe.dex;
					zbAttribute1.wiz += good.goodsFenSe.wiz;
					zbAttribute1.family += good.goodsFenSe.family;
					zbAttribute1.life_recover += good.goodsFenSe.life_recover;
					zbAttribute1.all_skill += good.goodsFenSe.all_skill;
					zbAttribute1.portrait += good.goodsFenSe.portrait;
					zbAttribute1.resist_frozen += good.goodsFenSe.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsFenSe.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsFenSe.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsFenSe.resist_confusion;
					zbAttribute1.longevity += good.goodsFenSe.longevity;
					zbAttribute1.resist_wood += good.goodsFenSe.resist_wood;
					zbAttribute1.resist_water += good.goodsFenSe.resist_water;
					zbAttribute1.resist_fire += good.goodsFenSe.resist_fire;
					zbAttribute1.resist_earth += good.goodsFenSe.resist_earth;
					zbAttribute1.exp_to_next_level += good.goodsFenSe.exp_to_next_level;
					zbAttribute1.all_resist_except += good.goodsFenSe.all_resist_except;
					zbAttribute1.accurate += good.goodsFenSe.accurate;
					zbAttribute1.mana += good.goodsFenSe.mana;
					zbAttribute1.parry += good.goodsFenSe.parry;
					zbAttribute1.ignore_resist_wood += good.goodsFenSe.ignore_resist_wood;
					zbAttribute1.ignore_resist_water += good.goodsFenSe.ignore_resist_water;
					zbAttribute1.ignore_resist_fire += good.goodsFenSe.ignore_resist_fire;
					zbAttribute1.ignore_resist_earth += good.goodsFenSe.ignore_resist_earth;
					zbAttribute1.ignore_resist_forgotten += good.goodsFenSe.ignore_resist_forgotten;
					zbAttribute1.ignore_resist_frozen += good.goodsFenSe.ignore_resist_frozen;
					zbAttribute1.ignore_resist_sleep += good.goodsFenSe.ignore_resist_sleep;
					zbAttribute1.ignore_resist_confusion += good.goodsFenSe.ignore_resist_confusion;
					zbAttribute1.super_excluse_metal += good.goodsFenSe.super_excluse_metal;
					zbAttribute1.ignore_resist_poison += good.goodsFenSe.ignore_resist_poison;
					/*----------------------------粉色属性-------------------------------e*/

					/*----------------------------绿色属性-------------------------------s*/
					zbAttribute1.accurate += good.goodsLvSe.accurate;
					zbAttribute1.resist_frozen += good.goodsLvSe.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsLvSe.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsLvSe.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsLvSe.resist_confusion;
					zbAttribute1.longevity += good.goodsLvSe.longevity;
					zbAttribute1.super_excluse_wood += good.goodsLvSe.super_excluse_wood;
					zbAttribute1.super_excluse_water += good.goodsLvSe.super_excluse_water;
					zbAttribute1.super_excluse_fire += good.goodsLvSe.super_excluse_fire;
					zbAttribute1.super_excluse_earth += good.goodsLvSe.super_excluse_earth;
					zbAttribute1.B_skill_low_cost += good.goodsLvSe.B_skill_low_cost;
					zbAttribute1.enhanced_wood += good.goodsLvSe.enhanced_wood;
					zbAttribute1.enhanced_water += good.goodsLvSe.enhanced_water;
					zbAttribute1.enhanced_fire += good.goodsLvSe.enhanced_fire;
					zbAttribute1.enhanced_earth += good.goodsLvSe.enhanced_earth;
					zbAttribute1.mag_dodge += good.goodsLvSe.mag_dodge;
					zbAttribute1.ignore_mag_dodge += good.goodsLvSe.ignore_mag_dodge;
					zbAttribute1.jinguang_zhaxian_counter_att_rate += good.goodsLvSe.jinguang_zhaxian_counter_att_rate;
					zbAttribute1.C_skill_low_cost += good.goodsLvSe.C_skill_low_cost;
					zbAttribute1.D_skill_low_cost += good.goodsLvSe.D_skill_low_cost;
					zbAttribute1.super_poison += good.goodsLvSe.super_poison;
					zbAttribute1.ignore_resist_wood += good.goodsLvSe.ignore_resist_wood;
					zbAttribute1.ignore_resist_water += good.goodsLvSe.ignore_resist_water;
					zbAttribute1.ignore_resist_fire += good.goodsLvSe.ignore_resist_fire;
					zbAttribute1.ignore_resist_earth += good.goodsLvSe.ignore_resist_earth;
					zbAttribute1.ignore_resist_forgotten += good.goodsLvSe.ignore_resist_forgotten;
					zbAttribute1.release_forgotten += good.goodsLvSe.release_forgotten;
					zbAttribute1.ignore_all_resist_except += good.goodsLvSe.ignore_all_resist_except;
					zbAttribute1.super_confusion += good.goodsLvSe.super_confusion;
					zbAttribute1.super_sleep += good.goodsLvSe.super_sleep;
					zbAttribute1.enhanced_metal += good.goodsLvSe.enhanced_metal;
					zbAttribute1.super_forgotten += good.goodsLvSe.super_forgotten;
					zbAttribute1.super_frozen += good.goodsLvSe.super_frozen;
					zbAttribute1.ignore_resist_frozen += good.goodsLvSe.ignore_resist_frozen;
					zbAttribute1.ignore_resist_sleep += good.goodsLvSe.ignore_resist_sleep;
					zbAttribute1.ignore_resist_confusion += good.goodsLvSe.ignore_resist_confusion;
					zbAttribute1.super_excluse_metal += good.goodsLvSe.super_excluse_metal;
					zbAttribute1.ignore_resist_poison += good.goodsLvSe.ignore_resist_poison;
					zbAttribute1.tao_ex += good.goodsLvSe.tao_ex;
					zbAttribute1.release_confusion += good.goodsLvSe.release_confusion;
					zbAttribute1.release_sleep += good.goodsLvSe.release_sleep;
					zbAttribute1.release_frozen += good.goodsLvSe.release_frozen;
					zbAttribute1.release_poison += good.goodsLvSe.release_poison;
					//躲避忽视攻击
					zbAttribute1.ignore_mag_dodge2 += good.goodsLvSe.ignore_mag_dodge2;
					/*----------------------------绿色属性-------------------------------e*/
					
					/*----------------------------改造属性-------------------------------s*/
					zbAttribute1.accurate += good.goodsGaiZao.accurate;
					zbAttribute1.wiz += good.goodsGaiZao.wiz;
					zbAttribute1.def += good.goodsGaiZao.def;
					zbAttribute1.mana += good.goodsGaiZao.mana;
					zbAttribute1.phy_power += good.goodsGaiZao.all_polar;
					zbAttribute1.mag_power += good.goodsGaiZao.all_polar;
					zbAttribute1.speed += good.goodsGaiZao.all_polar;
					zbAttribute1.life += good.goodsGaiZao.all_polar;
					/*----------------------------改造属性-------------------------------e*/
					

					/*----------------------------改造共鸣成功属性-------------------------------s*/
					zbAttribute1.damage_sel += good.goodsGaiZaoGongMingChengGong.damage_sel;
					zbAttribute1.accurate += good.goodsGaiZaoGongMingChengGong.accurate;
					zbAttribute1.mana += good.goodsGaiZaoGongMingChengGong.mana;
					zbAttribute1.def += good.goodsGaiZaoGongMingChengGong.def;
					zbAttribute1.wiz += good.goodsGaiZaoGongMingChengGong.wiz;
					zbAttribute1.parry += good.goodsGaiZaoGongMingChengGong.parry;
					zbAttribute1.phy_power += good.goodsGaiZaoGongMingChengGong.phy_power;
					zbAttribute1.mag_power += good.goodsGaiZaoGongMingChengGong.mag_power;
					zbAttribute1.speed += good.goodsGaiZaoGongMingChengGong.speed;
					zbAttribute1.life += good.goodsGaiZaoGongMingChengGong.life;
					zbAttribute1.resist_frozen += good.goodsGaiZaoGongMingChengGong.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsGaiZaoGongMingChengGong.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsGaiZaoGongMingChengGong.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsGaiZaoGongMingChengGong.resist_confusion;
					zbAttribute1.longevity += good.goodsGaiZaoGongMingChengGong.longevity;
					zbAttribute1.resist_wood += good.goodsGaiZaoGongMingChengGong.resist_wood;
					zbAttribute1.resist_water += good.goodsGaiZaoGongMingChengGong.resist_water;
					zbAttribute1.resist_fire += good.goodsGaiZaoGongMingChengGong.resist_fire;
					zbAttribute1.resist_earth += good.goodsGaiZaoGongMingChengGong.resist_earth;
					zbAttribute1.exp_to_next_level += good.goodsGaiZaoGongMingChengGong.exp_to_next_level;
					zbAttribute1.stunt_rate += good.goodsGaiZaoGongMingChengGong.stunt_rate;
					zbAttribute1.double_hit_rate += good.goodsGaiZaoGongMingChengGong.double_hit_rate;
					zbAttribute1.super_excluse_wood += good.goodsGaiZaoGongMingChengGong.super_excluse_wood;
					zbAttribute1.super_excluse_water += good.goodsGaiZaoGongMingChengGong.super_excluse_water;
					zbAttribute1.super_excluse_fire += good.goodsGaiZaoGongMingChengGong.super_excluse_fire;
					zbAttribute1.super_excluse_earth += good.goodsGaiZaoGongMingChengGong.super_excluse_earth;
					zbAttribute1.B_skill_low_cost += good.goodsGaiZaoGongMingChengGong.B_skill_low_cost;
					zbAttribute1.life_recover += good.goodsGaiZaoGongMingChengGong.life_recover;
					zbAttribute1.family += good.goodsGaiZaoGongMingChengGong.family;
					zbAttribute1.portrait += good.goodsGaiZaoGongMingChengGong.portrait;
					zbAttribute1.tao_ex += good.goodsGaiZaoGongMingChengGong.tao_ex;
					zbAttribute1.release_confusion += good.goodsGaiZaoGongMingChengGong.release_confusion;
					zbAttribute1.release_sleep += good.goodsGaiZaoGongMingChengGong.release_sleep;
					zbAttribute1.release_frozen += good.goodsGaiZaoGongMingChengGong.release_frozen;
					zbAttribute1.release_poison += good.goodsGaiZaoGongMingChengGong.release_poison;
					zbAttribute1.C_skill_low_cost += good.goodsGaiZaoGongMingChengGong.C_skill_low_cost;
					zbAttribute1.D_skill_low_cost += good.goodsGaiZaoGongMingChengGong.D_skill_low_cost;
					zbAttribute1.super_poison += good.goodsGaiZaoGongMingChengGong.super_poison;
					/*----------------------------改造共鸣成功属性-------------------------------e*/
					
					/*----------------------------改造共鸣属性-------------------------------s*/
					zbAttribute1.damage_sel += good.goodsGaiZaoGongMing.damage_sel;
					zbAttribute1.accurate += good.goodsGaiZaoGongMing.accurate;
					zbAttribute1.mana += good.goodsGaiZaoGongMing.mana;
					zbAttribute1.def += good.goodsGaiZaoGongMing.def;
					zbAttribute1.wiz += good.goodsGaiZaoGongMing.wiz;
					zbAttribute1.parry += good.goodsGaiZaoGongMing.parry;
					zbAttribute1.phy_power += good.goodsGaiZaoGongMing.phy_power;
					zbAttribute1.mag_power += good.goodsGaiZaoGongMing.mag_power;
					zbAttribute1.speed += good.goodsGaiZaoGongMing.speed;
					zbAttribute1.life += good.goodsGaiZaoGongMing.life;
					zbAttribute1.resist_frozen += good.goodsGaiZaoGongMing.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsGaiZaoGongMing.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsGaiZaoGongMing.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsGaiZaoGongMing.resist_confusion;
					zbAttribute1.longevity += good.goodsGaiZaoGongMing.longevity;
					zbAttribute1.resist_wood += good.goodsGaiZaoGongMing.resist_wood;
					zbAttribute1.resist_water += good.goodsGaiZaoGongMing.resist_water;
					zbAttribute1.resist_fire += good.goodsGaiZaoGongMing.resist_fire;
					zbAttribute1.resist_earth += good.goodsGaiZaoGongMing.resist_earth;
					zbAttribute1.exp_to_next_level += good.goodsGaiZaoGongMing.exp_to_next_level;
					zbAttribute1.mstunt_rate += good.goodsGaiZaoGongMing.mstunt_rate;
					zbAttribute1.stunt_rate += good.goodsGaiZaoGongMing.stunt_rate;
					zbAttribute1.double_hit_rate += good.goodsGaiZaoGongMing.double_hit_rate;
					zbAttribute1.super_excluse_wood += good.goodsGaiZaoGongMing.super_excluse_wood;
					zbAttribute1.super_excluse_water += good.goodsGaiZaoGongMing.super_excluse_water;
					zbAttribute1.super_excluse_fire += good.goodsGaiZaoGongMing.super_excluse_fire;
					zbAttribute1.super_excluse_earth += good.goodsGaiZaoGongMing.super_excluse_earth;
					zbAttribute1.B_skill_low_cost += good.goodsGaiZaoGongMing.B_skill_low_cost;
					zbAttribute1.life_recover += good.goodsGaiZaoGongMing.life_recover;
					zbAttribute1.family += good.goodsGaiZaoGongMing.family;
					zbAttribute1.portrait += good.goodsGaiZaoGongMing.portrait;
					zbAttribute1.tao_ex += good.goodsGaiZaoGongMing.tao_ex;
					zbAttribute1.release_confusion += good.goodsGaiZaoGongMing.release_confusion;
					zbAttribute1.release_sleep += good.goodsGaiZaoGongMing.release_sleep;
					zbAttribute1.release_frozen += good.goodsGaiZaoGongMing.release_frozen;
					zbAttribute1.release_poison += good.goodsGaiZaoGongMing.release_poison;
					zbAttribute1.C_skill_low_cost += good.goodsGaiZaoGongMing.C_skill_low_cost;
					zbAttribute1.D_skill_low_cost += good.goodsGaiZaoGongMing.D_skill_low_cost;
					zbAttribute1.super_poison += good.goodsGaiZaoGongMing.super_poison;
					// 法术必杀
					zbAttribute1.mstunt_rate2 += good.goodsGaiZaoGongMing.mstunt_rate_225;
					/*----------------------------改造共鸣属性-------------------------------e*/
					
					
					/*---------------------------套装属性-------------------------------s*/
					if (chara.suit_icon != 0) {
						zbAttribute1.mana += good.goodsLvSeGongMing.mana;
						zbAttribute1.def += good.goodsLvSeGongMing.def;
						zbAttribute1.wiz += good.goodsLvSeGongMing.wiz;
						zbAttribute1.parry += good.goodsLvSeGongMing.parry;
						zbAttribute1.accurate += good.goodsLvSeGongMing.accurate;
					}
					/*---------------------------套装属性-------------------------------s*/
					
					
					chara.zbAttribute.accurate += good.goodsLanSe.skill_low_cost;
					chara.zbAttribute.accurate += good.goodsHuangSe.skill_low_cost;
					chara.zbAttribute.accurate += good.goodsFenSe.skill_low_cost;
					chara.zbAttribute.mana += good.goodsLanSe.skill_low_cost;
					chara.zbAttribute.mana += good.goodsHuangSe.skill_low_cost;
					chara.zbAttribute.mana += good.goodsFenSe.skill_low_cost;

					// 反震率
					chara.zbAttribute.portrait += good.goodsLanSe.portrait;
					chara.zbAttribute.portrait += good.goodsHuangSe.portrait;
					chara.zbAttribute.portrait += good.goodsFenSe.portrait;

					// 反震度
					chara.zbAttribute.family += good.goodsLanSe.family;
					chara.zbAttribute.family += good.goodsHuangSe.family;
					chara.zbAttribute.family += good.goodsFenSe.family;
					
					
					//所有技能上升
					extUpSkillAllLevel += good.goodsLanSe.mstunt_rate;
					extUpSkillAllLevel += good.goodsFenSe.mstunt_rate;
					extUpSkillAllLevel += good.goodsHuangSe.mstunt_rate;
					
					//魂器
					GoodsHunqi hunqi = good.goodsHunQi;
					if (hunqi != null) {
						List<Hashtable<String, Object>> zongShuxing = hunqi.zongShuxing;
						if (zongShuxing != null && !zongShuxing.isEmpty()) {
							int count = 0;
							for (Hashtable<String, Object> ls : zongShuxing) {
								if (count == chara.shenHunDataSate) {
									log.info("人物阶数:{}", count);
									break;
								}
								String yangKey = (String) ls.get("yang_prop");
								Integer yangValue = (Integer) ls.get("yang_prop_value");
								String yinKey = (String) ls.get("yin_prop");
								Integer yinValue = (Integer) ls.get("yin_prop_value");
								// 处理阳属性
								if ("phy_power".equals(yangKey)) {
									zbAttribute1.accurate += yangValue;
								}
								if ("mag_power".equals(yangKey)) {
									zbAttribute1.mana += yangValue;
								}
								if ("str".equals(yangKey)) {
									zbAttribute1.phy_power += yangValue;
								}
								if ("speed".equals(yangKey)) {
									zbAttribute1.parry += yangValue;
								}
								if ("wiz".equals(yangKey)) {
									zbAttribute1.mag_power += yangValue;
								}
								if ("dex".equals(yangKey)) {
									zbAttribute1.speed += yangValue;
								}
								if ("double_hit".equals(yangKey)) {
									zbAttribute1.stunt += yangValue;
								}
								//反击率
								if ("double_hit_rate".equals(yangKey)) {
									zbAttribute1.stunt_rate += yangValue;
								}
								//忽视金
								if ("ignore_resist_metal".equals(yangKey)) {
									zbAttribute1.ignore_resist_wood += yangValue;
								}
								//忽视木
								if ("ignore_resist_wood".equals(yangKey)) {
									zbAttribute1.ignore_resist_water += yangValue;
								}
								//忽视水
								if ("ignore_resist_water".equals(yangKey)) {
									zbAttribute1.ignore_resist_fire += yangValue;
								}
								//忽视火
								if ("ignore_resist_fire".equals(yangKey)) {
									zbAttribute1.ignore_resist_earth += yangValue;
								}
								//忽视土
								if ("ignore_resist_earth".equals(yangKey)) {
									zbAttribute1.ignore_resist_forgotten += yangValue;
								}
								if ("ignore_all_resist_polar".equals(yangKey)) {
									zbAttribute1.ignore_all_resist_except += yangValue;
								}
								if ("ignore_all_resist_except".equals(yangKey)) {
									zbAttribute1.release_forgotten += yangValue;
								}
								if ("ignore_resist_forgotten".equals(yangKey)) {
									zbAttribute1.ignore_resist_poison += yangValue;
								}
								if ("ignore_resist_poison".equals(yangKey)) {
									zbAttribute1.ignore_resist_frozen += yangValue;
								}
								if ("ignore_resist_frozen".equals(yangKey)) {
									zbAttribute1.ignore_resist_sleep += yangValue;
								}
								if ("ignore_resist_sleep".equals(yangKey)) {
									zbAttribute1.ignore_resist_confusion += yangValue;
								}
								if ("ignore_resist_confusion".equals(yangKey)) {
									zbAttribute1.super_excluse_metal += yangValue;
								}
								
								// 阴属性-防御
								if ("def".equals(yinKey)) {
									zbAttribute1.wiz += yinValue;
								}
								// 最大气血
								if ("max_life".equals(yinKey)) {
									zbAttribute1.def += yinValue;
								}
								// 最大法力
								if ("max_mana".equals(yinKey)) {
									zbAttribute1.dex += yinValue;
								}
								// 体质
								if ("con".equals(yinKey)) {
									zbAttribute1.life += yinValue;
								}
								if ("damage_sel".equals(yinKey)) {
									zbAttribute1.family += yinValue;
								}
								if ("damage_sel_rate".equals(yinKey)) {
									zbAttribute1.portrait += yinValue;
								}
								if ("counter_attack".equals(yinKey)) {
									zbAttribute1.life_recover += yinValue;
								}
								if ("counter_attack_rate".equals(yinKey)) {
									zbAttribute1.double_hit_rate += yinValue;
								}
								if ("resist_metal".equals(yinKey)) {
									zbAttribute1.resist_wood += yinValue;
								}
								if ("resist_wood".equals(yinKey)) {
									zbAttribute1.resist_water += yinValue;
								}
								if ("resist_water".equals(yinKey)) {
									zbAttribute1.resist_fire += yinValue;
								}
								if ("resist_fire".equals(yinKey)) {
									zbAttribute1.resist_earth += yinValue;
								}
								if ("resist_earth".equals(yinKey)) {
									zbAttribute1.exp_to_next_level += yinValue;
								}
								if ("all_resist_polar".equals(yinKey)) {
									zbAttribute1.all_resist_except += yinValue;
								}
								if ("all_resist_except".equals(yinKey)) {
									zbAttribute1.all_skill += yinValue;
								}
								if ("resist_forgotten".equals(yinKey)) {
									zbAttribute1.resist_confusion += yinValue;
								}
								if ("resist_poison".equals(yinKey)) {
									zbAttribute1.resist_frozen += yinValue;
								}
								if ("resist_frozen".equals(yinKey)) {
									zbAttribute1.resist_sleep += yinValue;
								}
								if ("resist_sleep".equals(yinKey)) {
									zbAttribute1.resist_forgotten += yinValue;
								}
								if ("resist_confusion".equals(yinKey)) {
									zbAttribute1.longevity += yinValue;
								}
								count++;
							}
						}
					}
				}
			}

			int isDie = GameConfig.config.getBaseConfig().getIsChenghaodie();
			if (isDie == 1){
				if (chara.chenghao != null && !chara.chenghao.isEmpty()) {
					for (Entry<String, String> enty : chara.chenghao.entrySet()) {
						final ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
						final Chengwei newChengwei = cs.getChengweiByName(enty.getKey());
						GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
					}
            	}
			}else {
					// //当前称谓
				ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
				Chengwei newChengwei = cs.getChengweiByName(chara.getChenhao());
				GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);	
			}
			// //当前称谓
			// ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
			// Chengwei newChengwei = cs.getChengweiByName(chara.getChenhao());
			// GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
			
			//累计计算称谓属性
//        int chenWeiSuperposition = GameConfig.config.getBaseConfig().getChenWeiSuperposition();
//        if (chenWeiSuperposition == 1) {
//            final ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
//            final Chengwei newChengwei = cs.getChengweiByName(chara.getChenhao());
//            GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
//        } else {
            
//        }
			
			// 当前骑宠
			for (int i = 0; i < chara.pets.size(); ++i) {
				if (chara.pets.get(i) == null) {
					continue;
				}
				if (chara.pets.get(i).id == chara.zuoqiId) {
					for (int k = 0; k < chara.pets.get(i).petShuXing.size(); ++k) {
						if (chara.pets.get(i).petShuXing.get(k).no == 23) {
							ZbAttribute zbAttribute = chara.zbAttribute;
							zbAttribute.mana += chara.pets.get(i).petShuXing.get(k).mana;
							zbAttribute.accurate += chara.pets.get(i).petShuXing.get(k).accurate;
							zbAttribute.wiz += chara.pets.get(i).petShuXing.get(k).wiz;
						}
					}
				}
			}

			// 技能
			for (int i = 0; i < chara.jiNengList.size(); ++i) {
				JiNeng jiNeng = chara.jiNengList.get(i);
				if (jiNeng.skill_no == 301) { // 神术护体
					ZbAttribute zbAttribute439 = chara.zbAttribute;
					zbAttribute439.def += (500 * jiNeng.skill_level);
				}else if (jiNeng.skill_no == 302) { // 修道法术
					ZbAttribute zbAttribute440 = chara.zbAttribute;
					zbAttribute440.dex += (200 * jiNeng.skill_level);
				}else {
					//师门技能需要加上所有技能上升
					jiNeng.level_improved = extUpSkillAllLevel;
					//获取技能最大范围
					JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(jiNeng.skill_no);
					jiNeng.skill_attrib1 = Integer.parseInt((String) jsonObject.get("skill_attrib"));
					jiNeng.range = PetAndHelpSkillUtils.skillNummax(jiNeng.skill_no, jiNeng.skill_level+jiNeng.level_improved);
					int[] ints2 = PetAndHelpSkillUtils.skillNum(jsonObject, jiNeng.skill_level+jiNeng.level_improved);
					jiNeng.skillRound = ints2[1];
					int[] blueAndPointsLan2 = PetAndHelpSkillUtils.getBlueAndPointsLan(jiNeng.skill_no, jiNeng.skill_level+jiNeng.level_improved);
					jiNeng.skill_mana_cost = blueAndPointsLan2[0];
					jiNeng.s2 = blueAndPointsLan2[1];
				}
			}

			// 法宝
			for (int i = 0; i < otherGoods.size(); ++i) {
				Goods goods = chara.otherGoods.get(i);
				if (goods.pos == 9) {
					if (goods.goodsInfo.shuadao_ziqihongmeng == 4) {
						ZbAttribute zbAttribute441 = chara.zbAttribute;
						zbAttribute441.parry += (int) ((chara.zbAttribute.parry + chara.parry) * 0.015);
					}
					if (goods.goodsInfo.shuadao_ziqihongmeng == 1) {
						ZbAttribute zbAttribute442 = chara.zbAttribute;
						zbAttribute442.mana += (int) ((chara.zbAttribute.mana + chara.mana) * 0.015);
					}
					if (goods.goodsInfo.shuadao_ziqihongmeng == 2) {
						ZbAttribute zbAttribute443 = chara.zbAttribute;
						zbAttribute443.def += (int) ((chara.zbAttribute.def + chara.def) * 0.025);
						ZbAttribute zbAttribute444 = chara.zbAttribute;
						zbAttribute444.dex += (int) ((chara.zbAttribute.dex + chara.dex) * 0.025);
					}
					if (goods.goodsInfo.shuadao_ziqihongmeng == 3) {
						ZbAttribute zbAttribute445 = chara.zbAttribute;
						zbAttribute445.wiz += (int) ((chara.zbAttribute.wiz + chara.wiz) * 0.03);
					}
					if (goods.goodsInfo.shuadao_ziqihongmeng == 5) {
						ZbAttribute zbAttribute446 = chara.zbAttribute;
						zbAttribute446.accurate += (int) ((chara.zbAttribute.accurate + chara.accurate) * 0.015);
					}
					break;
				}
			}

			if (chara.zbAttribute.all_resist_except != 0) {
				chara.zbAttribute.resist_wood += chara.zbAttribute.all_resist_except;
				chara.zbAttribute.resist_water += chara.zbAttribute.all_resist_except;
				chara.zbAttribute.resist_fire += chara.zbAttribute.all_resist_except;
				chara.zbAttribute.resist_earth += chara.zbAttribute.all_resist_except;
				chara.zbAttribute.exp_to_next_level += chara.zbAttribute.all_resist_except;
			}
			if (chara.zbAttribute.ignore_all_resist_except != 0) {
				chara.zbAttribute.ignore_resist_wood += chara.zbAttribute.ignore_all_resist_except;
				chara.zbAttribute.ignore_resist_water += chara.zbAttribute.ignore_all_resist_except;
				chara.zbAttribute.ignore_resist_fire += chara.zbAttribute.ignore_all_resist_except;
				chara.zbAttribute.ignore_resist_earth += chara.zbAttribute.ignore_all_resist_except;
				chara.zbAttribute.ignore_resist_forgotten += chara.zbAttribute.ignore_all_resist_except;
			}
			if (chara.zbAttribute.release_forgotten != 0) {
				chara.zbAttribute.ignore_resist_frozen += chara.zbAttribute.release_forgotten;
				chara.zbAttribute.ignore_resist_sleep += chara.zbAttribute.release_forgotten;
				chara.zbAttribute.ignore_resist_confusion += chara.zbAttribute.release_forgotten;
				chara.zbAttribute.super_excluse_metal += chara.zbAttribute.release_forgotten;
				chara.zbAttribute.ignore_resist_poison += chara.zbAttribute.release_forgotten;
			}
			
			//太阴之气-魂窍
			for (int i = 0; i < otherGoods.size(); ++i) {
				Goods good = chara.otherGoods.get(i);
				if(good.pos>=21 && good.pos<=25) {
					ZbAttribute zbAttribute1 = chara.zbAttribute;
					// 物品基础属性
					zbAttribute1.accurate += good.goodsBasics.accurate;
					zbAttribute1.def += good.goodsBasics.def;
					zbAttribute1.dex += good.goodsBasics.dex;
					zbAttribute1.mana += good.goodsBasics.mana;
					zbAttribute1.parry += good.goodsBasics.parry;
					zbAttribute1.wiz += good.goodsBasics.wiz;
					
					
					// 装备蓝色属性
					chara.zbAttribute.phy_power = chara.zbAttribute.phy_power + good.goodsLanSe.phy_power
							+ good.goodsLanSe.all_polar;
					chara.zbAttribute.mag_power = chara.zbAttribute.mag_power + good.goodsLanSe.mag_power
							+ good.goodsLanSe.all_polar;
					chara.zbAttribute.speed = chara.zbAttribute.speed + good.goodsLanSe.speed + good.goodsLanSe.all_polar;
					chara.zbAttribute.life = chara.zbAttribute.life + good.goodsLanSe.life + good.goodsLanSe.all_polar;
					zbAttribute1.skill_low_cost += good.goodsLanSe.skill_low_cost;

					zbAttribute1.mstunt_rate += good.goodsLanSe.mstunt_rate;
					// 人物相性
					chara.zbAttribute.wood = chara.zbAttribute.wood + good.goodsLanSe.wood
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.water = chara.zbAttribute.water + good.goodsLanSe.water
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.fire = chara.zbAttribute.fire + good.goodsLanSe.fire
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.earth = chara.zbAttribute.earth + good.goodsLanSe.earth
							+ good.goodsLanSe.all_resist_polar;
					chara.zbAttribute.resist_metal = chara.zbAttribute.resist_metal + good.goodsLanSe.resist_metal
							+ good.goodsLanSe.all_resist_polar;

					zbAttribute1.damage_sel += good.goodsLanSe.damage_sel;
					zbAttribute1.stunt_rate += good.goodsLanSe.stunt_rate;
					zbAttribute1.double_hit_rate += good.goodsLanSe.double_hit_rate;
					zbAttribute1.release_forgotten += good.goodsLanSe.release_forgotten;
					zbAttribute1.ignore_all_resist_except += good.goodsLanSe.ignore_all_resist_except;
					zbAttribute1.stunt += good.goodsLanSe.stunt;
					zbAttribute1.def += good.goodsLanSe.def;
					zbAttribute1.dex += good.goodsLanSe.dex;
					zbAttribute1.wiz += good.goodsLanSe.wiz;
					zbAttribute1.family += good.goodsLanSe.family;
					zbAttribute1.life_recover += good.goodsLanSe.life_recover;
					zbAttribute1.all_skill += good.goodsLanSe.all_skill;
					zbAttribute1.portrait += good.goodsLanSe.portrait;
					zbAttribute1.resist_frozen += good.goodsLanSe.resist_frozen;
					zbAttribute1.resist_sleep += good.goodsLanSe.resist_sleep;
					zbAttribute1.resist_forgotten += good.goodsLanSe.resist_forgotten;
					zbAttribute1.resist_confusion += good.goodsLanSe.resist_confusion;
					zbAttribute1.longevity += good.goodsLanSe.longevity;
					zbAttribute1.resist_wood += good.goodsLanSe.resist_wood;
					zbAttribute1.resist_water += good.goodsLanSe.resist_water;
					zbAttribute1.resist_fire += good.goodsLanSe.resist_fire;
					zbAttribute1.resist_earth += good.goodsLanSe.resist_earth;
					zbAttribute1.exp_to_next_level += good.goodsLanSe.exp_to_next_level;
					zbAttribute1.all_resist_except += good.goodsLanSe.all_resist_except;
					zbAttribute1.accurate += good.goodsLanSe.accurate;
					zbAttribute1.mana += good.goodsLanSe.mana;
					zbAttribute1.parry += good.goodsLanSe.parry;
					zbAttribute1.ignore_resist_wood += good.goodsLanSe.ignore_resist_wood;
					zbAttribute1.ignore_resist_water += good.goodsLanSe.ignore_resist_water;
					zbAttribute1.ignore_resist_fire += good.goodsLanSe.ignore_resist_fire;
					zbAttribute1.ignore_resist_earth += good.goodsLanSe.ignore_resist_earth;
					zbAttribute1.ignore_resist_forgotten += good.goodsLanSe.ignore_resist_forgotten;
					zbAttribute1.ignore_resist_frozen += good.goodsLanSe.ignore_resist_frozen;
					zbAttribute1.ignore_resist_sleep += good.goodsLanSe.ignore_resist_sleep;
					zbAttribute1.ignore_resist_confusion += good.goodsLanSe.ignore_resist_confusion;
					zbAttribute1.super_excluse_metal += good.goodsLanSe.super_excluse_metal;
					zbAttribute1.ignore_resist_poison += good.goodsLanSe.ignore_resist_poison;
				}
			}
		} catch (Exception e) {
			log.error("{}",e);
		}
	}

	public static List<com.fengshen.server.data.vo.Vo_32747_0> MSG_UPDATE_SKILLS(Chara chara) {
		return MSG_UPDATE_SKILLS(chara.jiNengList);
	}

	public static void notifyAllBagGoodsInfo(Chara chara) {
		int listSize = chara.backpack.size();
		int perSize = 100;
		for (int beginIndex = 0; beginIndex < listSize; beginIndex += perSize) {
			int endIndex = Math.min(beginIndex + perSize, listSize);
			GameObjectChar.send(new M65525_0(), chara.backpack.subList(beginIndex, endIndex));
		}
	}

	public static Vo_16383_0 a16383(Chara chara, String msg, int channel, Chara chara1) {
		Vo_16383_0 vo_16383_0 = new Vo_16383_0();
		vo_16383_0.channel = channel;
		vo_16383_0.id = chara.id;
		vo_16383_0.name = chara.name;
		vo_16383_0.msg = msg;
		long times = System.currentTimeMillis() / 1000L;
		vo_16383_0.time = (int) times;
		vo_16383_0.privilege = 0;
		vo_16383_0.server_name = GameConfig.lineName;
		vo_16383_0.show_extra = 1;
		vo_16383_0.compress = 0;
		vo_16383_0.orgLength = 65535;
		vo_16383_0.cardCount = 0;
		vo_16383_0.voiceTime = 0;
		vo_16383_0.token = "";
		vo_16383_0.checksum = 0;
		vo_16383_0.iid_str = chara.uuid;
		vo_16383_0.has_break_lv_limit = 0;
		vo_16383_0.skill = chara.level;
		vo_16383_0.type = chara.waiguan;
		vo_16383_0.suit_level = chara1.uuid;
		return vo_16383_0;
	}

	public static String getSubUtil(String soap, String rgex) {
		Pattern pattern = Pattern.compile(rgex);
		Matcher m = pattern.matcher(soap);
		if (m.find()) {
			return m.group(1);
		}
		return "";
	}

	// 当前、世界、队伍喊话都会在这里初始化信息，然后发送到前端
	public static Vo_16383_0 a16383(Chara chara, String msg, int channel) {
		Vo_16383_0 vo_16383_0 = new Vo_16383_0();
		vo_16383_0.channel = channel;
		vo_16383_0.id = chara.id;
		vo_16383_0.name = chara.name;
		vo_16383_0.msg = msg;
		long times = System.currentTimeMillis() / 1000L;
		int time = (int) times;
		vo_16383_0.time = time;
		vo_16383_0.privilege = 0;
		vo_16383_0.server_name = GameCore.getGameLine(chara.line).lineNum + "线";
		vo_16383_0.show_extra = 1;
		vo_16383_0.compress = 0;
		vo_16383_0.orgLength = 65535;
		vo_16383_0.cardCount = 0;
		vo_16383_0.voiceTime = 0;
		vo_16383_0.token = "";
		vo_16383_0.checksum = 0;
		vo_16383_0.iid_str = chara.uuid;
		vo_16383_0.has_break_lv_limit = 0;
		vo_16383_0.skill = chara.level;
		vo_16383_0.type = chara.waiguan;
		vo_16383_0.chatFloor = chara.useChatFloor;
		vo_16383_0.chatHead = chara.useChatHead;
		return vo_16383_0;
	}
	
	

	/**
	 * 7系统, 19公告
	 * @param channel
	 * @param msg
	 */
	public static void sendSystemMessage(Integer channel, String msg) {
		Vo_MESSAGE v = new Vo_MESSAGE();
		v.channel = channel;
		v.id = 0;
		v.name = "";
		v.msg = msg;
		v.time = (int) (System.currentTimeMillis()/1000L);
		v.privilege = 0;
		v.server_name = GameConfig.lineName + "1线";
		v.show_extra = 0;
		v.show_time = (int) (System.currentTimeMillis()/1000L);
		v.icon = 0;
		GameObjectCharMng.sendAll(new MSG_MESSAGE(), v);
	}

	public static Vo_45056_0 a45056(Chara chara) {
		Vo_45056_0 vo_45056_0 = new Vo_45056_0();
		vo_45056_0.id = chara.id;
		vo_45056_0.name = chara.name;
		vo_45056_0.portrait = chara.waiguan;
		vo_45056_0.pic_no = 0;
		vo_45056_0.content = "";
		vo_45056_0.isComplete = 1;
		vo_45056_0.isInCombat = 0;
		vo_45056_0.playTime = 20;
		vo_45056_0.task_type = "主线—浮生若梦";
		return vo_45056_0;
	}
	
	/**
	 * a获取剧本对象
	 * @param chara
	 * @param npcDialogue
	 * @return
	 */
	public static Vo_45056_0 getPlayScenariod(Chara chara, NpcDialogue npcDialogue) {
		Vo_45056_0 vo_45056_0 = new Vo_45056_0();
		if("玩家".equals(npcDialogue.getName())) {
			vo_45056_0.id = chara.id;
			vo_45056_0.name = chara.name;
			vo_45056_0.portrait = chara.waiguan;
			vo_45056_0.pic_no = chara.waiguan;
		}else {
			vo_45056_0.id = npcDialogue.getId();
			vo_45056_0.name = npcDialogue.getName();
			vo_45056_0.portrait = npcDialogue.getPortranit();
			vo_45056_0.pic_no = npcDialogue.getPicNo();
		}
		vo_45056_0.content = npcDialogue.getContent();
		vo_45056_0.isComplete = npcDialogue.getIsconmlete();
		vo_45056_0.isInCombat = npcDialogue.getIsincombat();
		vo_45056_0.playTime = npcDialogue.getPalytime();
		vo_45056_0.task_type = npcDialogue.getTaskType();
		return vo_45056_0;
	}

	

	// 角色发送对话框
	public static Vo_45056_0 a45056(Chara chara, String content, String task_type) {
		Vo_45056_0 vo_45056_0 = new Vo_45056_0();
		vo_45056_0.id = chara.id;
		vo_45056_0.name = chara.name;
		vo_45056_0.portrait = chara.waiguan;
		vo_45056_0.pic_no = chara.waiguan;
		vo_45056_0.content = content;
		vo_45056_0.isComplete = 0;
		vo_45056_0.isInCombat = 0;
		vo_45056_0.playTime = 20;
		vo_45056_0.task_type = task_type;
		return vo_45056_0;
	}

	// npc发送对话框
	public static Vo_45056_0 a45056(Chara chara, String content, String task_type, int icon, String name) {
		Vo_45056_0 vo_45056_0 = new Vo_45056_0();
		vo_45056_0.id = chara.id;
		vo_45056_0.name = name;
		vo_45056_0.portrait = icon;
		vo_45056_0.pic_no = icon;
		vo_45056_0.content = content;
		vo_45056_0.isComplete = 0;
		vo_45056_0.isInCombat = 0;
		vo_45056_0.playTime = 20;
		vo_45056_0.task_type = task_type;
		return vo_45056_0;
	}

	public static void thread(Long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			log.error("{}", e);
		}
	}

	// 随机生成中文名称
	public static String getRandomJianHan() {
		Random random = new Random();
		int len = random.nextInt(2) + 3;
		String ret = "";
		for (int i = 0; i < len; ++i) {
			String str = null;
			int hightPos = 176 + Math.abs(random.nextInt(39));
			int lowPos = 161 + Math.abs(random.nextInt(93));
			byte[] b = { (byte) (hightPos), (byte) (lowPos) };
			try {
				str = new String(b, "GBK");
			} catch (UnsupportedEncodingException ex) {
				log.error("{}", ex);
			}
			ret += str;
		}
		return ret;
	}

	// 指定角色的下一个主线任务
	public static Vo_61553_0 a61553(Renwu tasks, Chara chara) {
		if (tasks == null) {
			Vo_61553_0 vo_61553_0 = new Vo_61553_0();
			vo_61553_0.count = 1;
			for (int i = 0; i < vo_61553_0.count; ++i) {
				vo_61553_0.task_type = "";
				vo_61553_0.task_desc = "1-9级主线任务，该等级段任务不可组队同步完成。";
				vo_61553_0.task_prompt = "";
				vo_61553_0.refresh = 0;
				vo_61553_0.task_end_time = 1563252508; // 2019/7/16 12:48:28
				vo_61553_0.attrib = 0;
				vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I代金券|代金券#I";
				vo_61553_0.show_name = "";
			}
			vo_61553_0.task_extra_para = "";
			vo_61553_0.task_state = "1";
			return vo_61553_0;
		}
		if (tasks.getCurrentTask().equals("主线—浮生若梦_s21")) {
			String[] str = { "前往#Z五龙山#Z拜师", "前往#Z终南山#Z拜师", "前往#Z凤凰山#Z拜师", "前往#Z乾元山#Z拜师", "前往#Z骷髅山#Z拜师" };
			tasks.setTaskPrompt(str[chara.polar - 1]);
		}
		if (tasks.getCurrentTask().equals("主线—浮生若梦_s22")) {
			String[] str = { "向#P云霄童子|E=【主线】慕名而来#P拜师", "向#P碧玉童子|E=【主线】慕名而来#P拜师", "向#P水灵童子|E=【主线】慕名而来#P拜师",
					"向#P赤霞童子|E=【主线】慕名而来#P拜师", "向#P彩云童子|E=【主线】慕名而来#P拜师" };
			tasks.setTaskPrompt(str[chara.polar - 1]);
		}

		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		for (int i = 0; i < vo_61553_0.count; ++i) {
			vo_61553_0.task_type = "";
			vo_61553_0.task_desc = "1-9级主线任务，该等级段任务不可组队同步完成。";
			vo_61553_0.task_prompt = tasks.getTaskPrompt(); // 任务提示，会自动导航
			vo_61553_0.refresh = 0;
			vo_61553_0.task_end_time = 1563252508;
			vo_61553_0.attrib = 0;
			vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I代金券|代金券#I";
			vo_61553_0.show_name = tasks.getShowName(); // 显示在前端APP的任务名称
		}
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "1"; // task_state等于1表示当前任务是最后一个，它之后会重新开启一个新的任务
		return vo_61553_0;
	}

	// 创建在某个NPC上添加内容列表的对象
	public static Vo_MENU_LIST a8247_1(Npc npc, String content) {
		Vo_MENU_LIST vo_8247_0 = new Vo_MENU_LIST();
		vo_8247_0.id = npc.getId();
		vo_8247_0.portrait = npc.getIcon();
		vo_8247_0.pic_no = 1;
		vo_8247_0.content = content.replace("\\", "");
		vo_8247_0.secret_key = "";
		vo_8247_0.name = npc.getName();
		vo_8247_0.attrib = 0;
		return vo_8247_0;
	}

	public static Vo_8247_0 a8247(final Npc npc, final String content) {
        final Vo_8247_0 vo_8247_0 = new Vo_8247_0();
        vo_8247_0.id = npc.getId();
        vo_8247_0.portrait = npc.getIcon();
        vo_8247_0.pic_no = 1;
        vo_8247_0.content = content.replace("\\", "");
        vo_8247_0.secret_key = "";
        vo_8247_0.name = npc.getName();
        vo_8247_0.attrib = 0;
        return vo_8247_0;
    }

	/**
	 * MSG_APPEAR 更新角色左侧面板
	 * 
	 * @param chara
	 * @return
	 */
	public static Vo_APPEAR a65529(Chara chara) {
		Vo_APPEAR vo_65529_0 = new Vo_APPEAR();
		vo_65529_0.id = chara.id;
		vo_65529_0.x = chara.x;
		vo_65529_0.y = chara.y;
		vo_65529_0.dir = chara.dir;
		vo_65529_0.icon = chara.waiguan;
		vo_65529_0.weapon_icon = chara.weapon_icon;
		vo_65529_0.type = 1;
		vo_65529_0.sub_type = 0;
		vo_65529_0.owner_id = 0;
		vo_65529_0.leader_id = 0;
		vo_65529_0.name = chara.name;
		vo_65529_0.level = chara.level;
		vo_65529_0.title = chara.chenhao;
		vo_65529_0.family = chara.chenhao;
		vo_65529_0.party = chara.getPartyName() == null ? "" : chara.getPartyName();
		vo_65529_0.status = 0;
		vo_65529_0.special_icon = chara.special_icon;
		vo_65529_0.org_icon = getWaiguan(chara.polar, chara.sex, chara);
		vo_65529_0.suit_icon = chara.suit_icon;
		vo_65529_0.suit_light_effect = chara.suit_light_effect;
		vo_65529_0.guard_icon = 0;
		vo_65529_0.pet_icon = chara.zuoqiwaiguan;
		vo_65529_0.shadow_icon = 0;
		vo_65529_0.shelter_icon = 0;
		vo_65529_0.mount_icon = chara.zuowaiguan;
		vo_65529_0.alicename = "";
		vo_65529_0.gid = chara.uuid;
		vo_65529_0.camp = "";
		vo_65529_0.vip_type = chara.vipType;
		vo_65529_0.isHide = 0;
		vo_65529_0.moveSpeedPercent = chara.yidongsudu;
		vo_65529_0.score = 0;
		vo_65529_0.masquerade = 0;
		vo_65529_0.upgradestate = chara.upgrade_state;
		vo_65529_0.upgradetype = chara.upgrade_type;
		vo_65529_0.obstacle = 0;
		vo_65529_0.effectIcons = chara.effectIcons;
		vo_65529_0.share_mount_icon = 0;
		vo_65529_0.share_mount_leader_id = 0;
		vo_65529_0.gather_count = 0;
		vo_65529_0.gather_name_num = 0;
		vo_65529_0.portrait = getWaiguan(chara.polar, chara.sex, chara);
		vo_65529_0.customIcon = chara.customIcon;
		VoChangeCard changeCardInfo = chara.changeCardInfo;
		if (changeCardInfo != null && vo_65529_0.special_icon == 0) {
			vo_65529_0.special_icon = changeCardInfo.getIcon();
		} else if (chara.upgrade_state != 0 && vo_65529_0.special_icon == 0) {
			if (chara.upgrade_type == 1 || chara.upgrade_type == 3) {
				// 元婴
				vo_65529_0.special_icon = 7008;
			} else if (chara.upgrade_type == 2) {
				vo_65529_0.special_icon = 7009;
			}
		} else {
			vo_65529_0.special_icon = chara.special_icon;
		}
		vo_65529_0.teamIcon = chara.teamIcon;
		//如果队伍不为空
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		if(gameObjectChar != null) {
			vo_65529_0.opacity = gameObjectChar.isHide == 0?0:30;
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
				vo_65529_0.moveSpeedPercent = gameObjectChar.gameTeam.duiwu.get(0).yidongsudu;
			}
			vo_65529_0.flyType = gameObjectChar.flyType;
			vo_65529_0.moveType = gameObjectChar.moveType;
			vo_65529_0.moveIds = gameObjectChar.moveIds;
		}
		if(vo_65529_0.flyType == 2) {
			vo_65529_0.moveSpeedPercent+=1;
		}else if(vo_65529_0.flyType == 3) {
			vo_65529_0.moveSpeedPercent+=1;
		}else if(vo_65529_0.flyType == 4) {
			vo_65529_0.moveSpeedPercent+=2;
		}
		return vo_65529_0;
	}

	/**
	 * MSG_UPDATE_IMPROVEMENT 更新背包装备栏数据
	 * 
	 * @param chara
	 * @return
	 */
	public static void a65511(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		zhuangbeiValue(gameObjectChar);
		chara.zbAttribute.id = chara.id;
		gameObjectChar.sendOne(new M65511_0(), chara.zbAttribute);

		ListVo_65527_0 vo_65527_0 = a65527(chara);
		gameObjectChar.sendOne(new M65527_0(), vo_65527_0);

		Vo_UPDATE_APPEARANCE vo_61661_0 = a61661(chara);
		gameObjectChar.sendOne(new MSG_UPDATE_APPEARANCE(), vo_61661_0);
		
		//重新刷新技能信息
		List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(chara);
		gameObjectChar.sendOne(new M32747_0(), vo_32747_0List);
	}
	

	// 将角色信息封装成netty能够发送的对象，发送到前端页面进行更新
	// 当更改角色的属性时，会创建这个对象并发送到前端
	// 用户头像改变
	public static ListVo_65527_0 a65527(Chara chara, String... event) {
		ListVo_65527_0 vo_65527_0 = new ListVo_65527_0();
		BasicAttributesUtils.shuxing(chara);
//		if (chara.max_mana < chara.dex + chara.zbAttribute.dex && chara.have_coin_pwd > 0) {
			int pwd = chara.dex + chara.zbAttribute.def - chara.max_mana;
			if (chara.have_coin_pwd < pwd) {
				pwd += chara.have_coin_pwd;
				chara.have_coin_pwd = 0;
			} else {
				chara.have_coin_pwd -= pwd;
			}
			chara.max_mana += pwd;
//		}
//		if (chara.max_life < chara.def + chara.zbAttribute.def && chara.extra_mana > 0) {
			int life = chara.def + chara.zbAttribute.def - chara.max_life;
			if (chara.extra_mana < life) {
				life += chara.extra_mana;
				chara.extra_mana = 0;
			} else {
				chara.extra_mana -= life;
			}
			chara.max_life += life;
//		}
		vo_65527_0.id = chara.id;
		vo_65527_0.vo_65527_0.str = chara.name;

		// 角色基础伤害信息
		vo_65527_0.vo_65527_0.phy_power = chara.phy_power;
		vo_65527_0.vo_65527_0.accurate = chara.accurate;
		vo_65527_0.vo_65527_0.life = chara.life;
		vo_65527_0.vo_65527_0.max_life = chara.max_life;
		vo_65527_0.vo_65527_0.def = chara.def;
		vo_65527_0.vo_65527_0.wiz = chara.wiz;
		vo_65527_0.vo_65527_0.mag_power = chara.mag_power;
		vo_65527_0.vo_65527_0.mana = chara.mana;
		vo_65527_0.vo_65527_0.max_mana = chara.max_mana;
		vo_65527_0.vo_65527_0.dex = chara.dex;
		vo_65527_0.vo_65527_0.speed = chara.speed;
		vo_65527_0.vo_65527_0.parry = chara.parry;
		
		
		vo_65527_0.vo_65527_0.wood = chara.metal;
		vo_65527_0.vo_65527_0.water = chara.wood;
		vo_65527_0.vo_65527_0.fire = chara.water;
		vo_65527_0.vo_65527_0.earth = chara.fire;
		vo_65527_0.vo_65527_0.resist_metal = chara.earth;
		vo_65527_0.vo_65527_0.stamina = chara.polarPoint;

		
		vo_65527_0.vo_65527_0.polar_point = chara.attribPoint;
		
		vo_65527_0.vo_65527_0.friend = chara.tao;
		vo_65527_0.vo_65527_0.owner_name = chara.taoPoint;

		vo_65527_0.vo_65527_0.attrib_point = 0;
		vo_65527_0.vo_65527_0.metal = chara.polar;
		vo_65527_0.vo_65527_0.resist_wood = 0;
		vo_65527_0.vo_65527_0.resist_water = 0;
		vo_65527_0.vo_65527_0.resist_fire = 0;
		vo_65527_0.vo_65527_0.resist_earth = 0;
		vo_65527_0.vo_65527_0.exp_to_next_level = 0;
		vo_65527_0.vo_65527_0.max_stamina = 1000;
		vo_65527_0.vo_65527_0.tao = 105;
		vo_65527_0.vo_65527_0.mon_tao_ex = chara.tao;
		vo_65527_0.vo_65527_0.last_mon_tao = chara.taoPoint;
		vo_65527_0.vo_65527_0.last_mon_tao_ex =  chara.tao;
		vo_65527_0.vo_65527_0.mon_martial = chara.taoPoint;
		vo_65527_0.vo_65527_0.degree = 0;
		vo_65527_0.vo_65527_0.exp = 0;
		vo_65527_0.vo_65527_0.pot = chara.exp;
		vo_65527_0.vo_65527_0.cash = chara.pot;
		vo_65527_0.vo_65527_0.balance = chara.cash;
		vo_65527_0.vo_65527_0.gender = chara.sex;
		vo_65527_0.vo_65527_0.max_balance = 2000000000;
		vo_65527_0.vo_65527_0.ignore_resist_metal = 2000000000;
		vo_65527_0.vo_65527_0.master = chara.sex;
		//vo_65527_0.vo_65527_0.level = 0;
		vo_65527_0.vo_65527_0.level = chara.upgrade_state == 0 ? chara.level : chara.realLevel;
		vo_65527_0.vo_65527_0.party_contrib = chara.contrib;
		vo_65527_0.vo_65527_0.status_daofa_wubian = "";
		vo_65527_0.vo_65527_0.nick = 0;
		vo_65527_0.vo_65527_0.family_title = "";
		vo_65527_0.vo_65527_0.title = "";
		vo_65527_0.vo_65527_0.nice = chara.chenhao;
		vo_65527_0.vo_65527_0.reputation = 0;
		vo_65527_0.vo_65527_0.couple = 0;
		vo_65527_0.vo_65527_0.icon = "";
		vo_65527_0.vo_65527_0.type = chara.waiguan;
		vo_65527_0.vo_65527_0.resist_poison = chara.expToNextLevel;
		vo_65527_0.vo_65527_0.item_unique = 0;
		vo_65527_0.vo_65527_0.passive_mode = chara.waiguan;
		vo_65527_0.vo_65527_0.req_str = chara.chenhao;
		vo_65527_0.vo_65527_0.locked = 0;
		vo_65527_0.vo_65527_0.extra_desc = 0;
		vo_65527_0.vo_65527_0.silverCoin = chara.silverCoin;
		vo_65527_0.vo_65527_0.extra_life = chara.goldCoin;
		vo_65527_0.vo_65527_0.extra_mana = chara.extra_mana;
		vo_65527_0.vo_65527_0.have_coin_pwd = chara.have_coin_pwd;
		vo_65527_0.vo_65527_0.max_req_level = 0;
		vo_65527_0.vo_65527_0.use_skill_d = chara.use_skill_d;
		vo_65527_0.vo_65527_0.double_points = chara.charashuangbei;// 开启双倍
		vo_65527_0.vo_65527_0.enable_double_points = chara.enable_double_points; // 双倍点数;
		vo_65527_0.vo_65527_0.can_buy_dp_times = chara.charashuangbei;
		vo_65527_0.vo_65527_0.enable_shenmu_points = chara.enable_shenmu_points;// 神木点点数
		vo_65527_0.vo_65527_0.gift_key = chara.shenmoding;// 神木鼎开启状态
		vo_65527_0.vo_65527_0.online = 0;
		vo_65527_0.vo_65527_0.voucher = 0;
		vo_65527_0.vo_65527_0.party_name = chara.getPartyName() == null ? "" : chara.getPartyName();
		vo_65527_0.vo_65527_0.partyJob = chara.getPartyJob();
		vo_65527_0.vo_65527_0.party_contrib = chara.getContrib();
		//代金券
		vo_65527_0.vo_65527_0.use_money_type = 0;
		vo_65527_0.vo_65527_0.lock_exp = chara.lock_exp;
		vo_65527_0.vo_65527_0.shuadaochongfeng_san = chara.shuadaochongfeng_san;
		vo_65527_0.vo_65527_0.equip_identify = 0;
		vo_65527_0.vo_65527_0.reputation = 0;
		vo_65527_0.vo_65527_0.recharge = 10;
		vo_65527_0.vo_65527_0.shadow_self = chara.shadow_self;
		vo_65527_0.vo_65527_0.extra_life_effect = 0;
		vo_65527_0.vo_65527_0.desc = 0;
		vo_65527_0.vo_65527_0.enchant = 0;
		vo_65527_0.vo_65527_0.higest_feixdx = 0;
		vo_65527_0.vo_65527_0.createTime = 1559291151;
		vo_65527_0.vo_65527_0.marriagemarry_id = chara.marriageMarryId;
		// 急急如律令点数
		vo_65527_0.vo_65527_0.fetch_nice = chara.jijirulvling;
		// 紫气鸿蒙点数
		vo_65527_0.vo_65527_0.extra_skill = chara.ziqihongmeng;
		// 如意刷道令点数
		vo_65527_0.vo_65527_0.chushi_ex = chara.ruyishuadao;
		// 拒绝xxx等级陌生人的消息
		vo_65527_0.vo_65527_0.settingrefuse_stranger_level = chara.settingrefuse_stranger_level;
		// 自动回复消息
		vo_65527_0.vo_65527_0.settingauto_reply_msg = chara.settingauto_reply_msg == null ? ""
				: chara.settingauto_reply_msg;
		// 拒绝xx等级的好友申请
		vo_65527_0.vo_65527_0.setting_refuse_be_add_level = chara.setting_refuse_be_add_level;
		vo_65527_0.vo_65527_0.mount_attrib_end_time = 20;
		//擂台排名
		vo_65527_0.vo_65527_0.ct_data_top_rank = chara.ctDataTopRank;
		//擂台积分
		vo_65527_0.vo_65527_0.ct_data_score = chara.ctDataScore;
		
		vo_65527_0.vo_65527_0.bully_kill_num = chara.ctDataScoreCost;
		vo_65527_0.vo_65527_0.police_kill_num = 0;
		vo_65527_0.vo_65527_0.gm_attribsmax_life = 0;
		vo_65527_0.vo_65527_0.gm_attribsmax_mana = 0;
		//用作于充值积分
		vo_65527_0.vo_65527_0.gm_attribsphy_power = 0;
		vo_65527_0.vo_65527_0.gm_attribsmag_power = 0;
		vo_65527_0.vo_65527_0.gm_attribsdef = 0;
		vo_65527_0.vo_65527_0.gm_attribsspeed = 0;
		vo_65527_0.vo_65527_0.shuadao_ruyi_point = "";
		vo_65527_0.vo_65527_0.artifact_upgraded_enabled = 0;
		vo_65527_0.vo_65527_0.house_house_class = "";
		vo_65527_0.vo_65527_0.plant_level = 0;
		vo_65527_0.vo_65527_0.phy_power_without_intimacy = 0;
		vo_65527_0.vo_65527_0.plant_exp = 0;
		vo_65527_0.vo_65527_0.marriage_couple_gid = "";
		vo_65527_0.vo_65527_0.strengthen_jewelry_num = "";
		vo_65527_0.vo_65527_0.soul_state = 0;
		vo_65527_0.vo_65527_0.dan_data_today_exp = 0;
		vo_65527_0.vo_65527_0.transform_num = 0;
		vo_65527_0.vo_65527_0.fasion_effect_disable = 0;
		vo_65527_0.vo_65527_0.strengthen_level = 0;
		vo_65527_0.vo_65527_0.status_diliebo_flag = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_lock_time = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_exp_ware = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_fetch_times = 0;
		vo_65527_0.vo_65527_0.exp_ware_data_today_fetch_times = 0;
		vo_65527_0.vo_65527_0.free_rename = ((chara.autofight_select != 0) ? 1 : 0);
		vo_65527_0.vo_65527_0.skill = chara.level;
		//结婚
		vo_65527_0.vo_65527_0.marriage_book_id = "AA15545545125";
		vo_65527_0.vo_65527_0.marriage_start_time = (int) (chara.marriageTime/1000L); 
		// 其他人的视野
		if (event.length > 0) {
			String type = event[0];
			// 元婴加点
			if (type.equals("yuanyingAddPoint")) {
				vo_65527_0.vo_65527_0.skill = chara.upgrade_level;
			} else if ("openUserTab_yuanying".equals(type)) {
				vo_65527_0.vo_65527_0.skill = chara.realLevel;
			}
		}
		// 飞升
		vo_65527_0.vo_65527_0.upgrade_level = chara.upgrade_level;
		vo_65527_0.vo_65527_0.upgrade_type = chara.upgrade_type;
		vo_65527_0.vo_65527_0.upgrade_exp = chara.upgrade_exp;
		vo_65527_0.vo_65527_0.upgrade_exp_to_next_level = chara.upgrade_exp_to_next_level;
		vo_65527_0.vo_65527_0.upgrade_state = chara.upgrade_state;
		vo_65527_0.vo_65527_0.upgrade_max_polar_extra = chara.upgrade_max_polar_extra;
		vo_65527_0.vo_65527_0.upgradeImmortal = chara.upgrade_immortal;
		vo_65527_0.vo_65527_0.upgrade_magic = chara.upgrade_magic;
		vo_65527_0.vo_65527_0.upgrade_total = chara.upgrade_total;
		/* 魂魄开始结束----- */
		// 内丹阶数
		vo_65527_0.vo_65527_0.shenHunDataSate = chara.shenHunDataSate;
		// 当前阶数层级
		vo_65527_0.vo_65527_0.shenHunDataLayer = chara.shenHunDataLaye;
		// 拥有的阴德
		vo_65527_0.vo_65527_0.shenHunDataExp = chara.chargeScore;
		if (chara.shenHunDataSate == 0) {
			chara.shenHunDataSate = 1;
		}
		vo_65527_0.vo_65527_0.shenHunDataExpToNextLevel = GameConfig.shenHunConfig.getData()
				.get(String.valueOf(chara.shenHunDataSate)).getIntValue("jifen");
		// 内丹数据
		vo_65527_0.vo_65527_0.dan_data_state = chara.danDataState;
		vo_65527_0.vo_65527_0.dan_data_stage = chara.danDataStage;
		vo_65527_0.vo_65527_0.dan_data_exp = chara.chargeScore;
		vo_65527_0.vo_65527_0.dan_data_exp_to_next_level = chara.danDataExpToNextLevel;
		vo_65527_0.vo_65527_0.dan_data_attrib_point = chara.danDataAttribPoint;
		vo_65527_0.vo_65527_0.dan_data_polar_point = chara.danDataPolarPoint;
		//内丹数据
		
		// 神魂数据
		vo_65527_0.vo_65527_0.mana += chara.shenHunMagPower;
		vo_65527_0.vo_65527_0.accurate += chara.shenHunPhyPower;
		vo_65527_0.vo_65527_0.wiz += chara.shenHunDef;
		vo_65527_0.vo_65527_0.parry += chara.shenHunSpeed;
		vo_65527_0.vo_65527_0.def += chara.shenHunmaxLife;
		vo_65527_0.vo_65527_0.max_life += chara.shenHunmaxLife;
		/* 神魂数据结束----- */

		//  洛书数据
		vo_65527_0.vo_65527_0.mana += chara.luoshuMagpower;
		vo_65527_0.vo_65527_0.accurate += chara.luoshumPhypower;
		vo_65527_0.vo_65527_0.wiz += chara.luoshuDefense;
		vo_65527_0.vo_65527_0.parry += chara.luoshuSpeed;
		/* 洛书结束 */
		
		
		
		// 变身卡数据
		VoChangeCard changeCardInfo = chara.getChangeCardInfo();
		if (changeCardInfo != null) {
			List<ChangeCardAttr> attrs = changeCardInfo.getAttr();
			if (attrs != null && !attrs.isEmpty()) {
				for (ChangeCardAttr a : attrs) {
					switch (a.getField()) {
					case "mag_power":
						vo_65527_0.vo_65527_0.mana += (vo_65527_0.vo_65527_0.mana * a.getValue() / 100);
						break;
					case "max_life":
						vo_65527_0.vo_65527_0.def += (vo_65527_0.vo_65527_0.def * a.getValue() / 100);
						break;
					case "max_mana":
						vo_65527_0.vo_65527_0.dex += (vo_65527_0.vo_65527_0.dex * a.getValue() / 100);
						break;
					case "phy_power":
						vo_65527_0.vo_65527_0.accurate += (vo_65527_0.vo_65527_0.accurate * a.getValue() / 100);
						break;
					case "speed":
						vo_65527_0.vo_65527_0.parry += (vo_65527_0.vo_65527_0.parry * a.getValue() / 100);
						break;
					case "def":
						vo_65527_0.vo_65527_0.wiz += (vo_65527_0.vo_65527_0.wiz * a.getValue() / 100);
						break;
					}
				}
			}
		}
		// 首饰精华
		vo_65527_0.vo_65527_0.jewelry_essence = chara.jewelry_essence;
		//装备页面
		vo_65527_0.vo_65527_0.equipPage = chara.equipPage;
		vo_65527_0.vo_65527_0.chengwei = chara.chenhao;
		//附灵
		vo_65527_0.vo_65527_0.zhenlingLevel = chara.zhenlingLevel;
		vo_65527_0.vo_65527_0.zhenlingType = chara.zhenlingType;
		//附灵伤害信息
		vo_65527_0.vo_65527_0.accurate+=chara.zhenlingPhy;
		vo_65527_0.vo_65527_0.mana+=chara.zhenlingMag;
		vo_65527_0.vo_65527_0.parry+=chara.zhenlingSpeed;
		vo_65527_0.vo_65527_0.wiz+=chara.zhenlingDef;
		//附灵附身
		if(chara.zhenlingType == 1) {
			//法伤10%
			int mana = (int) (vo_65527_0.vo_65527_0.mana*GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel-1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]/100);
			vo_65527_0.vo_65527_0.mana+=mana;
			//其他5%
			vo_65527_0.vo_65527_0.accurate+=(vo_65527_0.vo_65527_0.accurate*GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel-1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]/100/2);
			vo_65527_0.vo_65527_0.parry+=(vo_65527_0.vo_65527_0.parry*GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel-1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2]/100/2);
			vo_65527_0.vo_65527_0.wiz+=(vo_65527_0.vo_65527_0.wiz*GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel-1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]/100/2);
		}else if(chara.zhenlingType == 2) {
			//物伤10%
			vo_65527_0.vo_65527_0.accurate+=(vo_65527_0.vo_65527_0.accurate*GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel-1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]/100);
			//其他5%
			vo_65527_0.vo_65527_0.mana+=(vo_65527_0.vo_65527_0.mana*GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel-1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]/100/2);
			vo_65527_0.vo_65527_0.parry+=(vo_65527_0.vo_65527_0.parry*GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel-1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2]/100/2);
			vo_65527_0.vo_65527_0.wiz+=(vo_65527_0.vo_65527_0.wiz*GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel-1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]/100/2);
			
		}else if(chara.zhenlingType == 3) {
			//速度10%
			vo_65527_0.vo_65527_0.parry+=(vo_65527_0.vo_65527_0.parry*GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel-1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2]/100);
			//其他5%
			vo_65527_0.vo_65527_0.accurate+=(vo_65527_0.vo_65527_0.accurate*GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel-1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]/100/2);
			vo_65527_0.vo_65527_0.mana+=(vo_65527_0.vo_65527_0.mana*GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel-1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]/100/2);
			vo_65527_0.vo_65527_0.wiz+=(vo_65527_0.vo_65527_0.wiz*GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel-1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]/100/2);
		}else if(chara.zhenlingType == 4) {
			//防御1%
			vo_65527_0.vo_65527_0.wiz+=(vo_65527_0.vo_65527_0.wiz*GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel-1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]/100);
			//其他5%
			vo_65527_0.vo_65527_0.accurate+=(vo_65527_0.vo_65527_0.accurate*GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel-1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]/100/2);
			vo_65527_0.vo_65527_0.mana+=(vo_65527_0.vo_65527_0.mana*GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel-1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]/100/2);
			vo_65527_0.vo_65527_0.parry+=(vo_65527_0.vo_65527_0.parry*GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel-1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2]/100/2);
		}
		return vo_65527_0;
	}

	/**
	 * MSG_ENTER_ROOM
	 * 
	 * @param chara
	 * @return
	 */
	public static Vo_65505_0 a65505(Chara chara) {
		int x = chara.x;
        int y = chara.y;
        Vo_65505_0 vo_65505_1 = new Vo_65505_0();
        vo_65505_1.map_name = chara.mapName;
        vo_65505_1.zeros=0;
        vo_65505_1.zerob=0;
        vo_65505_1.map_id = chara.mapid^(x*256+y);
        vo_65505_1.x1=0x33;
        vo_65505_1.x2=x^0x33;
        vo_65505_1.y1=0;
        vo_65505_1.y2=x^y;
        vo_65505_1.is_safe_zone = 0;
        vo_65505_1.is_task_walk = 0;
        vo_65505_1.wall_index = 1;
        vo_65505_1.enter_effect_index = 0;
		return vo_65505_1;
	}


	public static Vo_12285_0 a12285() {
		Vo_12285_0 vo_12285_0 = new Vo_12285_0();
		vo_12285_0.id = 16105;
		vo_12285_0.type = 4;
		return vo_12285_0;
	}

	/**
	 * 
	 * 外观
	 * @param chara
     * @param event 动作,是否为真身或者是元血婴界面
	 * @return
	 */
	public static Vo_UPDATE_APPEARANCE a61661(Chara chara, String... event) {
		Vo_UPDATE_APPEARANCE vo_61661_0 = new Vo_UPDATE_APPEARANCE();
		vo_61661_0.id = chara.id;
		vo_61661_0.x = chara.x;
		vo_61661_0.y = chara.y;
		vo_61661_0.dir = chara.dir;
		vo_61661_0.icon = chara.waiguan;
		vo_61661_0.weapon_icon = chara.weapon_icon;
		vo_61661_0.type = 1;
		vo_61661_0.sub_type = 0;
		vo_61661_0.owner_id = 0;
		vo_61661_0.leader_id = 0;
		vo_61661_0.name = chara.name;
		vo_61661_0.level = chara.level;
		vo_61661_0.title = chara.chenhao;
		vo_61661_0.family = chara.chenhao;
		vo_61661_0.partyname = chara.getPartyName();
		vo_61661_0.status = 0;
		vo_61661_0.special_icon = chara.special_icon;
		// 人物头像图标
		vo_61661_0.org_icon = getWaiguan(chara.polar, chara.sex, chara);
		vo_61661_0.suit_icon = chara.suit_icon;
		vo_61661_0.suit_light_effect = chara.suit_light_effect;
		vo_61661_0.mount_icon = chara.zuowaiguan;
		vo_61661_0.guard_icon = 0;
		vo_61661_0.pet_icon = chara.zuoqiwaiguan;
		vo_61661_0.shadow_icon = 0;
		vo_61661_0.shelter_icon = 0;
		vo_61661_0.alicename = "";
		vo_61661_0.gid = chara.uuid;
		vo_61661_0.camp = "";
		vo_61661_0.vip_type = chara.vipType;
		vo_61661_0.isHide = 0;
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		if(gameObjectChar != null) {
			vo_61661_0.opacity = gameObjectChar.isHide == 0?0:30;
			vo_61661_0.flyType = gameObjectChar.flyType;
			vo_61661_0.moveType = gameObjectChar.moveType;
			vo_61661_0.moveIds = gameObjectChar.moveIds;
		}
		vo_61661_0.moveSpeedPercent = chara.yidongsudu;
		vo_61661_0.score = 0;
		vo_61661_0.masquerade = 0;
		vo_61661_0.obstacle = 0;
		vo_61661_0.upgradestate = chara.upgrade_state;
		vo_61661_0.upgradetype = chara.upgrade_type;
		//显示的队标

		if (chara.effectIcons == null) {
			chara.effectIcons = new HashMap<String, Integer>();
		}
		if (!StringUtils.isNullOrEmpty(chara.chenhao)) {
			Chengwei chengweiByName = GameData.that.chengweiService.getChengweiByName(chara.chenhao);
			if (chengweiByName != null && chengweiByName.getIcon() != null) {
				log.info("chengweiByName.getIcon():{}", chengweiByName.getIcon());
				chara.effectIcons.put("chengweiEffectIcon", chengweiByName.getIcon());
			}else {
				chara.effectIcons.remove("chengweiEffectIcon");
			}
		}else {
			chara.effectIcons.remove("chengweiEffectIcon");
		}
		chara.effectIcons.remove("fasionEffectIcon");

		vo_61661_0.effect = chara.effectIcons;
		vo_61661_0.partyTitle = "default006.png";
		VoChangeCard changeCardInfo = chara.changeCardInfo;
		if (changeCardInfo != null && vo_61661_0.special_icon == 0) {
			// 如果没有穿时装就显示变身卡
			vo_61661_0.special_icon = changeCardInfo.getIcon();
		} else if (chara.upgrade_state != 0 && vo_61661_0.special_icon == 0) {
			if (chara.upgrade_type == 1 || chara.upgrade_type == 3) {
				// 元婴
				vo_61661_0.special_icon = 7008;
			} else {
				vo_61661_0.special_icon = 7009;
			}
		} else {
			vo_61661_0.special_icon = chara.special_icon;
		}
		if (event != null && event.length > 0) {
			if (chara.upgrade_state != 0) {
				vo_61661_0.level = chara.upgrade_level;
			}
		} else {
			if (chara.upgrade_state != 0) {
				vo_61661_0.level = chara.upgrade_level;
			}
		}
		if(gameObjectChar != null && "marry".equals(gameObjectChar.flag)) {
			vo_61661_0.isHide = 1;
		}
		vo_61661_0.customIcon = chara.customIcon;
		vo_61661_0.teamIcon = chara.teamIcon;
		if(gameObjectChar != null && GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
			vo_61661_0.moveSpeedPercent = gameObjectChar.gameTeam.duiwu.get(0).yidongsudu;
		}
		if(vo_61661_0.flyType == 2) {
			vo_61661_0.moveSpeedPercent+=1;
		}else if(vo_61661_0.flyType == 3) {
			vo_61661_0.moveSpeedPercent+=1;
		}else if(vo_61661_0.flyType == 4) {
			vo_61661_0.moveSpeedPercent+=2;
		}
		return vo_61661_0;
	}

	public static Vo_61589_0 a61589() {
		Vo_61589_0 vo_61589_0 = new Vo_61589_0();
		vo_61589_0.key0 = "autoplay_party_voice";
		vo_61589_0.settingkey0 = 1;
		vo_61589_0.key1 = "total_switch";
		vo_61589_0.settingkey1 = 0;
		vo_61589_0.key2 = "push_world_boss";
		vo_61589_0.settingkey2 = 0;
		vo_61589_0.key3 = "ft_dun_yb";
		vo_61589_0.settingkey3 = 1;
		vo_61589_0.key4 = "refuse_shock";
		vo_61589_0.settingkey4 = 0;
		vo_61589_0.key5 = "refuse_be_joint";
		vo_61589_0.settingkey5 = 0;
		vo_61589_0.key6 = "refuse_rumor_msg";
		vo_61589_0.settingkey6 = 0;
		vo_61589_0.key7 = "apply_apprentice_mail";
		vo_61589_0.settingkey7 = 1;
		vo_61589_0.key8 = "refuse_lookon_msg";
		vo_61589_0.settingkey8 = 0;
		vo_61589_0.key9 = "hide_world_msg";
		vo_61589_0.settingkey9 = 0;
		vo_61589_0.key10 = "hide_rumor_msg";
		vo_61589_0.settingkey10 = 0;
		vo_61589_0.key11 = "friend_msg_bubble";
		vo_61589_0.settingkey11 = 1;
		vo_61589_0.key12 = "hide_team_msg";
		vo_61589_0.settingkey12 = 0;
		vo_61589_0.key13 = "refuse_all_msg";
		vo_61589_0.settingkey13 = 0;
		vo_61589_0.key14 = "sight_scope";
		vo_61589_0.settingkey14 = 1;
		vo_61589_0.key15 = "verify_be_added";
		vo_61589_0.settingkey15 = 0;
		vo_61589_0.key16 = "hide_party_msg";
		vo_61589_0.settingkey16 = 0;
		vo_61589_0.key17 = "refuse_stranger_msg";
		vo_61589_0.settingkey17 = 1;
		vo_61589_0.key18 = "refuse_family_msg";
		vo_61589_0.settingkey18 = 0;
		vo_61589_0.key19 = "refuse_world_msg";
		vo_61589_0.settingkey19 = 0;
		vo_61589_0.key20 = "refuse_be_added";
		vo_61589_0.settingkey20 = 0;
		vo_61589_0.key21 = "refuse_look_equip";
		vo_61589_0.settingkey21 = 0;
		vo_61589_0.key22 = "ft_lead_team";
		vo_61589_0.settingkey22 = 1;
		vo_61589_0.key23 = "push_chanchu_yaowang";
		vo_61589_0.settingkey23 = 0;
		vo_61589_0.key24 = "ft_recruit";
		vo_61589_0.settingkey24 = 1;
		vo_61589_0.key25 = "visit_house";
		vo_61589_0.settingkey25 = 0;
		vo_61589_0.key26 = "push_super_boss";
		vo_61589_0.settingkey26 = 0;
		vo_61589_0.key27 = "ft_inv_team";
		vo_61589_0.settingkey27 = 1;
		vo_61589_0.key28 = "music_value";
		vo_61589_0.settingkey28 = 127;
		vo_61589_0.key29 = "auto_reply_msg";
		vo_61589_0.settingkey29 = 0;
		vo_61589_0.key30 = "refuse_team_msg";
		vo_61589_0.settingkey30 = 0;
		vo_61589_0.key31 = "refuse_party_image";
		vo_61589_0.settingkey31 = 0;
		vo_61589_0.key32 = "award_supply_artifact";
		vo_61589_0.settingkey32 = 0;
		vo_61589_0.key33 = "refuse_wedding_msg";
		vo_61589_0.settingkey33 = 0;
		vo_61589_0.key34 = "forbidden_play_voice";
		vo_61589_0.settingkey34 = 0;
		vo_61589_0.key35 = "refuse_request_party";
		vo_61589_0.settingkey35 = 0;
		vo_61589_0.key36 = "push_shidao_dahui";
		vo_61589_0.settingkey36 = 1;
		vo_61589_0.key37 = "refuse_fight";
		vo_61589_0.settingkey37 = 0;
		vo_61589_0.key38 = "ft_req_team";
		vo_61589_0.settingkey38 = 1;
		vo_61589_0.key39 = "push_haidao_ruqin";
		vo_61589_0.settingkey39 = 1;
		vo_61589_0.key40 = "music_effect";
		vo_61589_0.settingkey40 = 127;
		vo_61589_0.key41 = "refuse_exchange";
		vo_61589_0.settingkey41 = 0;
		vo_61589_0.key42 = "touch_furniture_lock";
		vo_61589_0.settingkey42 = 0;
		vo_61589_0.key43 = "ft_use_item";
		vo_61589_0.settingkey43 = 1;
		vo_61589_0.key44 = "refuse_raid_msg";
		vo_61589_0.settingkey44 = 0;
		vo_61589_0.key45 = "combat_auto_talk";
		vo_61589_0.settingkey45 = 0;
		vo_61589_0.key46 = "autoplay_team_voice";
		vo_61589_0.settingkey46 = 1;
		vo_61589_0.key47 = "refuse_party_msg";
		vo_61589_0.settingkey47 = 0;
		vo_61589_0.key48 = "push_biaoxing_wanli";
		vo_61589_0.settingkey48 = 1;
		vo_61589_0.key49 = "refuse_tell_msg";
		vo_61589_0.settingkey49 = 0;
		vo_61589_0.key50 = "hide_system_msg";
		vo_61589_0.settingkey50 = 0;
		vo_61589_0.key51 = "award_supply_pet";
		vo_61589_0.settingkey51 = 0;
		vo_61589_0.key52 = "hide_current_msg";
		vo_61589_0.settingkey52 = 0;
		vo_61589_0.key53 = "refuse_warcraft";
		vo_61589_0.settingkey53 = 0;
		vo_61589_0.key54 = "push_shuadao_double";
		vo_61589_0.settingkey54 = 1;
		vo_61589_0.key55 = "refuse_friend_msg";
		vo_61589_0.settingkey55 = 0;
		vo_61589_0.key56 = "push_week_act";
		vo_61589_0.settingkey56 = 0;
		vo_61589_0.key57 = "window_mode";
		vo_61589_0.settingkey57 = 1;
		vo_61589_0.key58 = "ft_change_look";
		vo_61589_0.settingkey58 = 1;
		vo_61589_0.key59 = "ft_change_team_seq";
		vo_61589_0.settingkey59 = 1;
		vo_61589_0.key60 = "refuse_cs_msg";
		vo_61589_0.settingkey60 = 0;
		return vo_61589_0;
	}

	public static void a49171(Chara chara) {
		List<Vo_49171_0> list = new LinkedList<>();
		List<String[]> strings = NoviceGiftBagUtils.giftBag(chara.sex, chara.polar);
		for (int i = 0; i < strings.size(); ++i) {
			Vo_49171_0 vo_49171_0 = new Vo_49171_0();
			vo_49171_0.isGot = chara.levelUpReward[i];
			vo_49171_0.limitLevel = (i + 1) * 10;
			for (int j = 0; j < strings.get(i).length; ++j) {
				Vo_49171_0 vo = new Vo_49171_0();
				String s = strings.get(i)[j];
				String[] split = s.split("\\#");
				vo.name = split[0];
				if (split[0].equals("代金券")) {
					vo.number = Integer.parseInt(split[1]);
				} else {
					vo.number = 1;
				}
				vo.limitLevel = 429496729;
				vo_49171_0.vo491710s.add(vo);
			}
			list.add(vo_49171_0);
		}
		GameObjectChar.send(new M49171_0(), list, chara.id);
	}

	public static void main(String[] args) {
		double daohang = 4687700.0;
		System.out.println(daohang);
		System.out.println(fmtDh((int) daohang));
	}

	// 当打败了地图守护神之后
	public static void mapguard(Chara chara, FightContainer fightContainer, String npcName) {
		int jingyan = chara.level / 10 * 6815 * 5;
		if (jingyan < 1) {
			jingyan = 1;
		}
		huodejingyan(chara, jingyan, "地图守护神");
		double daohang = 1.674E7;
		adddaohang(chara, (int)daohang, "地图守护神");

		Npc oldNpc = GameData.that.baseNpcService.findOneByName(npcName);
		for (int i = 0; i < fightContainer.attCharaStatueList.size(); ++i) {
			CharaStatue charaStatue = fightContainer.attCharaStatueList.get(i);
			if (i == 0)
				charaStatue.id = oldNpc.getId();
			charaStatue.copyChengHao(npcName);
			CharaStatueService.saveCharaStature(npcName + "_" + i, charaStatue);
		}
		GameUtil.notifyNpcDisappear(MapGuardianService.configMap.get(npcName).npc,
				GameObjectChar.getGameObjectChar().gameMap.sessionList);
		
		MapGuardianService.notifyNpcApprear(MapGuardianService.configMap.get(npcName),
				fightContainer.attCharaStatueList, GameObjectChar.getGameObjectChar().gameMap.sessionList);
		//发送谣言
		StringBuilder teamMsg = new StringBuilder("");
		for(Chara team:GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
			teamMsg.append("#Y").append(team.name).append("#n,");
		}
		String msg = teamMsg.substring(0, teamMsg.length() - 1);
		teamMsg = new StringBuilder();
		teamMsg.append(msg).append("成功挑战#R").append(npcName).append("#n真是实力超群啊，各位可到各个地图中挑战#R地图守护神#n奖励丰厚哦");
		GameUtil.sendYaoYan(teamMsg.toString());
	}

	// 当角色挑战证道殿角色成功后，在这里处理他的称号，并实时替换
	public static void zhengdaodian(Chara chara, FightContainer fightContainer) {
		int jingyan = chara.level / 10 * 6815 * 5;
		if (jingyan < 1) {
			jingyan = 1;
		}
		huodejingyan(chara, jingyan, "证道殿");
//         double daohang = 1.674E7;
		double daohang = chara.level * 60;
		adddaohang(chara, (int)daohang, "证道殿");

		int stage = (chara.level - 70) / 10;
		String title = "";
		switch (stage) {
		case 0:
			title = ZhengDaoDianService.titles[0];
			break;
		case 1:
			title = ZhengDaoDianService.titles[1];
			break;
		case 2:
			title = ZhengDaoDianService.titles[2];
			break;
		case 3:
			title = ZhengDaoDianService.titles[3];
			break;
		case 4:
			title = ZhengDaoDianService.titles[4];
			break;
		case 5:
			title = ZhengDaoDianService.titles[5];
			break;
		}

		int npcId = ZhengDaoDianService.getNpcId(chara.sex, chara.level);
		// 调整称谓
//        Npc npc = GameData.that.baseNpcService.findOneByName(zddJuese);
		CharaStatue charaStatue = HeroPubService.getCharStaure(npcId);
		if (charaStatue != null) {
			// 称号
			if (charaStatue.id > 0 && charaStatue.id != chara.id) {
				// 撤销旧的称号
				TitleService.removeUserTitle(charaStatue.id, TitleConst.TITLE_EVENT_SHI_DAO_DIAN);
				TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_SHI_DAO_DIAN, title);
			} else {
				TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_SHI_DAO_DIAN, title);
			}
		} else {
			TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_HERO_CHALLENGE, title);
		}
		fightContainer.charaStatue.copyChengHao(title);
		// add:e

		String zddJuese = getZddJuese(chara.polar, chara.sex, chara.level);
		Npc npc = new Npc();
		npc.setId(npcId);
		npc.setIcon(GameUtil.getWaiguan(chara.polar, chara.sex, chara));
		// 雕像
		CharaStatueService.saveCharaStature(zddJuese, fightContainer.charaStatue);
		// 刷新npc视野信息
		npc.setName(zddJuese);
		npc.setMapId(ZhengDaoDianService.MAP_ID);
		Integer[] pos = null;
		if (chara.sex == 1) {
			pos = ZhengDaoDianService.manPos[(chara.level - 70) / 10];
		} else {
			pos = ZhengDaoDianService.womanPos[(chara.level - 70) / 10];
		}
		npc.setX(pos[0]);
		npc.setY(pos[1]);
		for (GameObjectChar gameSession : GameObjectCharMng.getGameObjectCharList()) {
			// 让当前地图的人都能实时看到实时刷新的npc
			if (gameSession.gameMap.id == GameObjectChar.getGameObjectChar().gameMap.id
					&& gameSession.chara.polar == GameObjectChar.getGameObjectChar().chara.polar)
				gameSession.sendOne(new M65529_npc(), npc);
		}

	}

	// 当角色挑战英雄评议员成功后，在这里处理他的称号，并实时替换
	public static void yingxiong(Chara chara, FightContainer fightContainer) {
		int jingyan = chara.level / 10 * 6815 * 5;
		if (jingyan < 1) {
			jingyan = 1;
		}
		huodejingyan(chara, jingyan, "英雄会");
		double daohang = 1.674E7;
		adddaohang(chara, (int)daohang, "英雄会");
		String yingxiong = getYingxiong(chara.level);

		String title = "";
		switch (yingxiong) {
		case "英雄会评议员_0":
			title = HeroPubService.titles[0];
			break;
		case "英雄会评议员_1":
			title = HeroPubService.titles[1];
			break;
		case "英雄会评议员_2":
			title = HeroPubService.titles[2];
			break;
		case "英雄会评议员_3":
			title = HeroPubService.titles[3];
			break;
		case "英雄会评议员_4":
			title = HeroPubService.titles[4];
			break;
		case "英雄会评议员_5":
			title = HeroPubService.titles[5];
			break;
		case "英雄会评议员_6":
			title = HeroPubService.titles[6];
			break;
		}

		// 调整称谓
		Npc npc = GameData.that.baseNpcService.findOneByName(yingxiong);
		CharaStatue charaStatue = HeroPubService.getCharStaure(npc.getId());
		if (charaStatue != null) {
			if (charaStatue.id > 0 && charaStatue.id != chara.id) {
				// 撤销旧的称号
				TitleService.removeUserTitle(charaStatue.id, TitleConst.TITLE_EVENT_HERO_CHALLENGE);
				TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_HERO_CHALLENGE,
						title);
			} else {
				TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_HERO_CHALLENGE,
						title);
			}
		} else {
			TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_HERO_CHALLENGE, title);
		}
		fightContainer.charaStatue.dir = npc.getExt();
		fightContainer.charaStatue.copyChengHao(title);
		// 雕像
		CharaStatueService.saveCharaStature(yingxiong, fightContainer.charaStatue);
		for (GameObjectChar gameSession : GameObjectCharMng.getGameObjectCharList()) {
			if (gameSession.gameMap.id == GameObjectChar.getGameObjectChar().gameMap.id)
				gameSession.sendOne(new M65529_npc(), npc);
		}
		GameUtil.sendYaoYan("恭喜玩家#R" + chara.name + "#n挑战英雄会！暂时接管了#R官府#Y" + title + "#n之位”");
	}

	// 当角色挑战掌门成功后，在这里处理他的称号
	public static void zhangmen(Chara chara, FightContainer fightContainer) {
		int jingyan = chara.level / 10 * 6815 * 5;
		if (jingyan < 1) {
			jingyan = 1;
		}
		huodejingyan(chara, jingyan, "掌门");
		double daohang = 1.674E7;
		adddaohang(chara, (int)daohang, "掌门");
		String type = "金系掌门";
		if (chara.polar == 1) {
			type = "金系掌门";
		} else if (chara.polar == 2) {
			type = "木系掌门";
		} else if (chara.polar == 3) {
			type = "水系掌门";
		} else if (chara.polar == 4) {
			type = "火系掌门";
		} else if (chara.polar == 5) {
			type = "土系掌门";
		}
		for (FightObject fobj : fightContainer.teamList.get(0).fightObjectList) {
			fobj.str = type + "-" + fobj.str;
		}

		String zhangMenName = type;

		// 称号
		CharaStatue charaStatue = CharaStatueService.getCharStaure(zhangMenName);
		if (null != charaStatue) {
			if (charaStatue.id != chara.id) {
				// 撤销旧的称号
				TitleService.removeUserTitle(charaStatue.id, TitleConst.TITLE_EVENT_CHALLENGE_LEADER);
				TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_CHALLENGE_LEADER,
						zhangMenName);
			} else {
				TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_CHALLENGE_LEADER,
						zhangMenName);
			}
		} else {
			TitleService.grantTitle(GameObjectChar.getGameObjectChar(), TitleConst.TITLE_EVENT_CHALLENGE_LEADER,
					zhangMenName);
		}
		fightContainer.charaStatue.copyChengHao(type);
//        chara.chenghao.put("挑战掌门", type);
//        chara.chenhao = type;
//        chenghaoxiaoxi(chara);

//        FightManager.zmMap.put(type, fightContainer.teamList.get(0).fightObjectList);
		// add tzhang 增加动态掌门
		// 雕像
		CharaStatueService.saveCharaStature(GameUtil.ZHANG_MEN[chara.polar - 1], fightContainer.charaStatue);
		// 刷新npc视野信息
		Npc npc = GameData.that.baseNpcService.findOneByName(GameUtil.ZHANG_MEN[chara.polar - 1]);
		for (GameObjectChar gameSession : GameObjectCharMng.getGameObjectCharList()) {
			// 让当前地图的人都能实时看到刷新的掌门
			if (gameSession.gameMap.id == GameObjectChar.getGameObjectChar().gameMap.id)
				gameSession.sendOne(new M65529_npc(), npc);
		}
//		if (chara.polar == 1) {
//			shanmen = "五龙山";
//		} else if (chara.polar == 2) {
//			shanmen = "终南山";
//			shifu = "云中子";
//		} else if (chara.polar == 3) {
//			shanmen = "凤凰山";
//			shifu = "龙吉公主";
//		} else if (chara.polar == 4) {
//			shanmen = "乾元山";
//			shifu = "太乙真人";
//		} else if (chara.polar == 5) {
//			shanmen = "骷髅山";
//			shifu = "石矶娘娘";
//		}
		String upname = "";
		if (charaStatue != null) {
			upname = charaStatue.name;
		}
		StringBuilder msg = new StringBuilder();
		msg.append("[#Y").append(GameCommonUtil.shimen_shizun[chara.polar-1]).append("]#W").append("经过激烈角逐，#Y").append(chara.name).append("#W力挫上任掌门#Y")
				.append(upname).append("#W").append("，一时其他弟子无人能敌，为师深感欣慰。现告知天下正式赐予#Y").append(chara.name)
				.append("#W本派掌门称谓，同喜同贺");
		GameUtil.sendYaoYan(msg.toString());
	}

	// 获得奖励，strings的格式为{200，"经验"}.第一个位置为数值，第二个位置为内容。代金券是特例在第一个位置
	// 目前的奖励内容有：宝宝、经验
	public static void huodechoujiang(String[] strings, GameObjectChar gameObejctChar, 
			String source) {
		Chara chara = gameObejctChar.chara;
		if (strings[1].equals("宝宝")) {
			Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
			Petbeibao petbeibao = new Petbeibao();
			petbeibao.PetCreate(pet, chara, 0, 2, source);
			List<Petbeibao> list = new ArrayList<Petbeibao>();
			chara.pets.add(petbeibao);
			list.add(petbeibao);
			GameObjectChar.send(new MSG_UPDATE_PETS(), list);
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了宝宝#R" + pet.getName() + "#n";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObejctChar.sendOne(new M20481_0(), vo_20481_0);
		}

		if (strings[1].equals("经验")) {
			huodejingyan(chara, Integer.valueOf(strings[0]), source);
		}

		// 坐骑就是精怪
		if (strings[1].equals("精怪")) {
			int jieshu = stageMounts(strings[0]);
			Pet pet2 = GameData.that.basePetService.findOneByName(strings[0]);
			Petbeibao petbeibao2 = new Petbeibao();
			petbeibao2.PetCreate(pet2, chara, 0, 2, source);

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
			gameObejctChar.sendOne(new MSG_UPDATE_PETS(), list2);

			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了精怪#R" + strings[0] + "#n";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObejctChar.sendOne(new M20481_0(), vo_20481_0);

		}

		if (strings[1].equals("变异")) {
			Pet pet = GameData.that.basePetService.findOneByName(strings[0]);
			Petbeibao petbeibao = new Petbeibao();
			petbeibao.PetCreate(pet, chara, 0, 3, source);
			List<Petbeibao> list = new ArrayList<Petbeibao>();
			chara.pets.add(petbeibao);
			list.add(petbeibao);
			gameObejctChar.sendOne(new MSG_UPDATE_PETS(), list);

			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了变异#R" + strings[0] + "#n";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObejctChar.sendOne(new M20481_0(), vo_20481_0);
		}

		// 物品中如果有神兽
		if (strings[1].equals("神兽")) {
			huodeshenshou(chara, strings[0], source);
		}

		if (strings[1].equals("物品")) {
			StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(strings[0]);
			huodedaoju(gameObejctChar, info, 1);
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了物品#R" + strings[0] + "#n";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObejctChar.sendOne(new M20481_0(), vo_20481_0);
		}
		if (strings[1].equals("首饰")) {
			ZhuangbeiInfo oneByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(strings[0]);
			huodezhuangbei(chara, oneByStr, 0, 1);
			strings[0] = "60级首饰";
		}
		if (strings[0].equals("代金券")) {
			chara.use_money_type += Integer.valueOf(strings[1]);
			ListVo_65527_0 listVo_65527_0 = a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);

			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了#R" + strings[1] + "#n代金券";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObejctChar.sendOne(new M20481_0(), vo_20481_0);
		}
		if (strings[1].equals("金币")) {
			addCash(gameObejctChar, Integer.valueOf(strings[0]));
			ListVo_65527_0 listVo_65527_0 = a65527(chara);
			gameObejctChar.sendOne(new M65527_0(), listVo_65527_0);

			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了#R" + strings[0] + "#n金币";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			gameObejctChar.sendOne(new M20481_0(), vo_20481_0);
		}

		if (strings[1].equals("装备")) {
			ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(strings[0]);
			List<Hashtable<String, Integer>> hashtables = NoviceGiftBagUtils
					.equipmentGiftBags(zhuangbeiInfo.getAmount(), zhuangbeiInfo.getAttrib());
			if (hashtables.size() > 0) {
				GoodsLanSe gooodsLanSe = new GoodsLanSe();
				GoodsGaiZao goodsGaiZao = new GoodsGaiZao();
				GoodsFenSe goodsFenSe = new GoodsFenSe();
				GoodsHuangSe goodsHuangSe = new GoodsHuangSe();
				int gaizao = 0;
				for (Hashtable<String, Integer> maps : hashtables) {
					if (maps.get("groupNo") == 2) {
						maps.put("groupType", 2);
						gooodsLanSe = (GoodsLanSe) com.alibaba.fastjson.JSONObject.parseObject(
								com.alibaba.fastjson.JSONObject.toJSONString(maps),GoodsLanSe.class);
					}
					if (maps.get("groupNo") == 3) {
						maps.put("groupType", 2);
						goodsFenSe = (GoodsFenSe) com.alibaba.fastjson.JSONObject.parseObject(
								com.alibaba.fastjson.JSONObject.toJSONString(maps),GoodsFenSe.class);
					}
					if (maps.get("groupNo") == 4) {
						maps.put("groupType", 2);
						goodsHuangSe = (GoodsHuangSe) com.alibaba.fastjson.JSONObject.parseObject(
								com.alibaba.fastjson.JSONObject.toJSONString(maps),
								GoodsHuangSe.class);
					}
					if (maps.get("groupNo") == 10) {
						gaizao = maps.get("changeNum");
						maps.remove("changeNum");
						maps.put("groupType", 2);
						goodsGaiZao = (GoodsGaiZao) com.alibaba.fastjson.JSONObject.parseObject(
								com.alibaba.fastjson.JSONObject.toJSONString(maps), GoodsGaiZao.class);
					}
				}
				Goods goods = new Goods();
				int pos2 = packPoint(chara);
				if (pos2 == -1) {
					return;
				}
				goods.pos = pos2;
				goods.goodsLanSe = gooodsLanSe;
				goods.goodsGaiZao = goodsGaiZao;
				goods.goodsFenSe = goodsFenSe;
				goods.goodsHuangSe = goodsHuangSe;
				goods.goodsCreate(zhuangbeiInfo);
				goods.goodsInfo.owner_id = 1;
				goods.goodsInfo.degree_32 = 0;
				goods.goodsInfo.color = gaizao;
				chara.backpack.add(goods);
				gameObejctChar.sendOne(new M65525_0(), chara.backpack);

				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "你获得了装备#R" + strings[0] + "#n";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				gameObejctChar.sendOne(new M20481_0(), vo_20481_0);
			}
		}
	}

	public static int stageMounts(String name) {

		Pet pet = GameData.that.basePetService.findOneByName(name);
		int mounts_stage = 0;
		if(pet != null){
			try{
				mounts_stage = Integer.parseInt(pet.getPetType());
			}catch(Exception e){
				log.info("请求出错：{}",e);
			}

		}else{
			return 1;
		}
		//2022坐骑阶数
//		int[] mounts_stage = { 2, 3, 4, 4, 5, 5, 5,5, 6, 6, 6, 6, 8, 8, 10,12,14,16};
//		String[] mounts_name = { "仙阳剑", "凌岩豹", "幻鹿", "赤焰葫芦", "玉豹", "翠灵剑", "仙葫芦", "无极熊", "岳麓剑", "古鹿", "北极熊", "筋斗云", "太极熊",
//				"墨麒麟","北斗天蓬","九尾仙狐","小老虎","梦幻天马"};
//		for (int i = 0; i < mounts_name.length; ++i) {
//			if (mounts_name[i].equalsIgnoreCase(name)) {
//				return mounts_stage[i];
//			}
//		}
		return mounts_stage;
	}

	public static List<Hashtable<String, Integer>> equipmentLuckDraw(int eq_attrib, int leixing) {
		if (eq_attrib < 70) {
			eq_attrib = 70;
		} else {
			eq_attrib = eq_attrib / 10 * 10;
		}
		List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils.appraisalEquipment(leixing, eq_attrib, 10);
		String[] rareAttributes = { "all_resist_except", "all_resist_polar", "all_polar", "all_skill",
				"ignore_all_resist_except", "mstunt_rate", "release_forgotten" };
		for (Hashtable<String, Integer> hashtable : hashtables) {
			for (String key : rareAttributes) {
				if (hashtable.contains(key)) {
					Random random = new Random();
					String[] replaceAttributes = { "mag_power", "phy_power", "speed", "life" };
					List<Hashtable<String, Integer>> appraisalList = new ArrayList<Hashtable<String, Integer>>();
					Hashtable<String, Integer> key_vlaue_tab = new Hashtable<String, Integer>();
					key_vlaue_tab.put("groupNo", 2);
					key_vlaue_tab.put(replaceAttributes[random.nextInt(4)], eq_attrib / 4);
					appraisalList.add(key_vlaue_tab);
					return appraisalList;
				}
			}
		}
		return hashtables;
	}

	// 返回对应任务等级，以及对应类型的装备名称
	public static String zhuangbname(Chara chara, int leixing) {
		int eq_attrib = 0;
		if (chara.level < 70) {
			eq_attrib = 70;
		} else {
			eq_attrib = chara.level / 10 * 10; // 获取对应等级的装备,如128就返回120的装备
		}
		List<ZhuangbeiInfo> byAttrib = GameData.that.baseZhuangbeiInfoService.findByAttrib(eq_attrib);
		for (int j = 0; j < byAttrib.size(); ++j) {
			if (leixing == 1 && byAttrib.get(j).getMetal() == chara.polar && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if ((leixing == 2 || leixing == 3) && byAttrib.get(j).getMaster() == chara.sex
					&& byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if (leixing == 10 && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
		}
		return "";
	}

	/**
	 * 根据等级获取随机装备
	 * 
	 * @param level
	 * @param leixing
	 * @return
	 */
	public static String getRandomZbNameByLevel(int level) {
		int eq_attrib = level / 10 * 10;
		List<ZhuangbeiInfo> byAttrib = GameData.that.baseZhuangbeiInfoService.findByAttrib(eq_attrib);
		int[] type = new int[] { 1, 2, 3, 10 };
		int[] polor = new int[] { 1, 2, 3, 4, 5 };
		int leixing = type[(int) (Math.random() * type.length)];
		int polar = polor[(int) (Math.random() * polor.length)];
		int sex = new Random().nextInt(2) + 1;
		for (int j = 0; j < byAttrib.size(); ++j) {
			if (leixing == 1 && byAttrib.get(j).getMetal() == polar && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if ((leixing == 2 || leixing == 3) && byAttrib.get(j).getMaster() == sex
					&& byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if (leixing == 10 && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
		}
		return "";
	}
	
	/**
	 * 根据等级获取随机装备
	 * 
	 * @param level
	 * @param leixing
	 * @return
	 */
	public static String getZbNameByLevel(Chara chara, int level, int leixing) {
		int eq_attrib = level / 10 * 10;
		List<ZhuangbeiInfo> byAttrib = GameData.that.baseZhuangbeiInfoService.findByAttrib(eq_attrib);
		int polar = chara.polar;
		int sex = chara.sex;
		for (int j = 0; j < byAttrib.size(); ++j) {
			if (leixing == 1 && byAttrib.get(j).getMetal() == polar && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if ((leixing == 2 || leixing == 3) && byAttrib.get(j).getMaster() == sex
					&& byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
			if (leixing == 10 && byAttrib.get(j).getAmount() == leixing) {
				return byAttrib.get(j).getStr();
			}
		}
		return "";
	}

	public static void pointMap(Chara chara1, String mapName) {
		Map map = GameData.that.baseMapService.findOneByName(mapName);
		if (map == null) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "不符合条件";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			return;
		}
		chara1.y = map.getY();
		chara1.x = map.getX();
		GameLine.getGameMapname(chara1.line, map.getName()).join(GameObjectCharMng.getGameObjectChar(chara1.id));
		Vo_SHIDAO_TASK_INFO vo_49177_0 = new Vo_SHIDAO_TASK_INFO();
		vo_49177_0.isPK = 3;
		vo_49177_0.stageId = 3;
		vo_49177_0.monsterPoint = 10;
		vo_49177_0.pkValue = 2;
		vo_49177_0.totalScore = 45;
		vo_49177_0.startTime = 1567343400;
		vo_49177_0.stage1_duration_time = 1800;
		vo_49177_0.stage2_duration_time = 6600;
		vo_49177_0.rank = 0;
		GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(), vo_49177_0);
	}

	public static void jiBaiYaoChiXianZi(Chara chara1) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "八仙梦境";
		vo_61553_0.task_desc = "八仙梦境-吕洞宾";
		vo_61553_0.task_prompt = "向#P牡丹仙子|E=【八仙】万事俱备|$0#P复命";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = (int) (System.currentTimeMillis() / 1000L);
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "";
		vo_61553_0.show_name = "八仙梦境-吕洞宾";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "5";
		GameUtilRenWu.createTask(vo_61553_0, chara1);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
	}

	public static void jiBaiShouZhiTianBing(Chara chara1) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "八仙梦境";
		vo_61553_0.task_desc = "八仙梦境-吕洞宾";
		vo_61553_0.task_prompt = "前往收服作乱的#P穿山甲|E=【八仙】铲除妖孽|$0#P";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = (int) (System.currentTimeMillis() / 1000L);
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "";
		vo_61553_0.show_name = "八仙梦境-吕洞宾";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "7";
		GameUtilRenWu.createTask(vo_61553_0, chara1);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
	}

	public static void jiBaiChuanShanJia(Chara chara1) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "八仙梦境";
		vo_61553_0.task_desc = "八仙梦境-吕洞宾";
		vo_61553_0.task_prompt = "将定海神针交给#P西王母|E=【八仙】物归原主|$0#P";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = (int) (System.currentTimeMillis() / 1000L);
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "";
		vo_61553_0.show_name = "八仙梦境-吕洞宾";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "8";
		GameUtilRenWu.createTask(vo_61553_0, chara1);
		GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
	}

	public static void a45060(Chara chara) {
		Vo_45060_0 vo_45060_0 = new Vo_45060_0();
		vo_45060_0.hasBonus = 0;
		vo_45060_0.xy_higest = 649;
		vo_45060_0.fm_higest = 496;
		vo_45060_0.fx_higest = 0;
		vo_45060_0.off_line_time = 2276;
		vo_45060_0.buy_one = 50;
		vo_45060_0.buy_five = 350;
		vo_45060_0.buy_time = 0;
		vo_45060_0.max_buy_time = 4;
		vo_45060_0.offlineStatus = 0;
		vo_45060_0.max_turn = 0;
		vo_45060_0.lastTaskName = "降妖";
		vo_45060_0.max_double = 16000;
		vo_45060_0.max_jiji = 16000;
		vo_45060_0.jijiStatus = chara.jijirulvlingState;
		// 宠风散金钱购买
		vo_45060_0.chongfengsan_time = chara.chongfengsanMoneyNum;
		// 宠风散金钱购买最大次数
		vo_45060_0.max_chongfengsan_time = 3;
		// 紫气鸿蒙金钱购买次数
		vo_45060_0.ziqihongmeng_time = chara.ziqihongmengMoneyNum;
		// 紫气鸿蒙金钱最大购买次数
		vo_45060_0.max_ziqihongmeng_time = 1;
		vo_45060_0.max_chongfengsan = 16000;
		vo_45060_0.chongfengsan_status = chara.chongfengsan;
		vo_45060_0.max_ziqihongmeng = 16000;
		vo_45060_0.ziqihongmeng_status = chara.ziqihongmengState;
		vo_45060_0.hasDaofaBonus = 0;
		vo_45060_0.count = 3;
		vo_45060_0.taskName = "降妖";
		vo_45060_0.taskTime = 9;
		vo_45060_0.taskName1 = "伏魔";
		vo_45060_0.taskTime1 = 3;
		vo_45060_0.taskName2 = "飞仙渡邪";
		vo_45060_0.taskTime2 = 1;
		GameObjectChar.send(new M45060_0(), vo_45060_0);
	}

	// 删号
	public static void shanhao(Chara aChar) {
		Characters oneByName = GameData.that.baseCharactersService.findOneByName(aChar.name);
		GameData.that.baseCharactersService.deleteById(oneByName.getId());
		GameData.that.baseAccountsService.deleteById(oneByName.getAccountId());
		List<GameObjectChar> all = GameObjectCharMng.getAll();
		for (GameObjectChar objectChar : all) {
			if (objectChar.chara.equals(aChar)) {
				GameObjectCharMng.del(objectChar);
			}
		}
		GameUtil.log.info("删号处理：" + aChar.name);
	}

	// 解封
	public static void jiefeng(String name) {
		Characters oneByName = GameData.that.baseCharactersService.findOneByName(name);
		Accounts accounts = GameData.that.baseAccountsService.findById((int) oneByName.getAccountId());
		oneByName.setDeleted(false);
		accounts.setDeleted(false);
		GameData.that.baseCharactersService.updateById(oneByName);
		GameData.that.baseAccountsService.updateById(accounts);
		GameUtil.log.info("解封成功：" + name);
	}

	// 根据传入的复活解决决定宠物是否能复活
	public static boolean getChance(int fhRate) {
		if (fhRate <= 0)
			return false;
		int nextInt;
		return (nextInt = new Random().nextInt(99)) >= 0 && nextInt < fhRate;
	}

	// 根据宠物的亲密度决定能否复活
	public static boolean fh(int type, int shape) {
		if (shape < 100000) {
			return false;
		}
		if (shape > 5000000) {
			if(type == 2) {
				return getChance(60);
			}else if(type == 3) {
				return getChance(70);
			}
			return getChance(80);
			
 		}
		if (shape > 2000000) {
			if(type == 2) {
				return getChance(40);
			}else if(type == 3) {
				return getChance(45);
			}
			return getChance(50);
		}
		if (shape > 1000000) {
			if(type == 2) {
				return getChance(30);
			}else if(type == 3) {
				return getChance(35);
			}
			return getChance(40);
		}
		if (shape > 500000) {
			if(type == 2) {
				return getChance(25);
			}else if(type == 3) {
				return getChance(30);
			}
			return getChance(35);
		}
		if (shape > 300000) {
			if(type == 2) {
				return getChance(10);
			}else if(type == 3) {
				return getChance(15);
			}
			return getChance(20);
		}
		if (shape > 200000) {
			if(type == 2) {
				return getChance(10);
			}else if(type == 3) {
				return getChance(15);
			}
			return getChance(20);
		}
		return shape > 100000 && getChance(5);
	}


	static {
		tongttXj = new String[] { "玉衡星君", "天权星君", "天玑星君", "天璇星君", "天枢星君", "摇光星君", "开阳星君" };
		tongttcw = new String[] { "朱雀", "疆良", "玄武", "东山神灵" };
		originGuardPet = new String[] { "朱雀", "疆良", "玄武", "东山神灵" };
		log = LoggerFactory.getLogger(GameUtil.class);
	}

	public static void openDlg(String dlgName) {
		sendNotify(ClientButtonIdConst.NOTIFY_OPEN_DLG, dlgName);
	}

	public static void closeDlg(String dlgName) {
		sendNotify(ClientButtonIdConst.NOTIFY_CLOSE_DLG, dlgName);
	}

	/**
	 * 发送通知
	 *
	 * @param notify 通知号
	 * @param para   面板ID
	 */
	public static void sendNotify(int notify, String para) {
		Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
		vo_9129_2.notify = notify;
		vo_9129_2.para = para;
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2);
	}
	
	/**
	 * 发送通知
	 *
	 * @param notify 通知号
	 * @param para   面板ID
	 * @param gameObjectChar 游戏对象
	 */
	public static void sendNotify(int notify, String para, GameObjectChar gameObjectChar) {
		Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
		vo_9129_2.notify = notify;
		vo_9129_2.para = para;
		gameObjectChar.sendOne(new MSG_GENERAL_NOTIFY(), vo_9129_2);
	}

	// 进入副本
	public static void enterDugeno(Chara chara, String name) {
		DugenoCfg cfgMgr = (DugenoCfg) XLSConfigMgr.getCfg("dugeno");
		DugenoItem cfg = cfgMgr.getByName(name);
		if (cfg == null) {
			cfg = cfgMgr.getByMapName(name);
		}
		com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName(cfg.map_name);
		if (map != null) {
			chara.y = map.getY().intValue();
			chara.x = map.getX().intValue();
			GameZone gameZone = GameLine.createGameZone(chara.line, map.getMapId());
			gameZone.initGameDugeon(cfg.name);
			gameZone.join(GameObjectCharMng.getGameObjectChar(chara.id));
			gameZone.gameDugeon.enter(chara);
		}
	}

	/**
	 * 弹出 TIPS
	 *
	 * @param msg
	 */
	public static void sendMeTips(String msg) {
		Vo_20481_0 vo_20481_0 = new Vo_20481_0();
		vo_20481_0.msg = msg;
		vo_20481_0.time = ((int) (System.currentTimeMillis() / 1000L));
		GameObjectChar.send(new M20481_0(), vo_20481_0);
	}

	public static boolean isTeamLeader(Chara chara) {
		GameTeam gameTeam = GameObjectCharMng.getGameObjectChar(chara.id).gameTeam;
		if (gameTeam == null)
			return false;
		return (gameTeam.duiwu.get(0).id == chara.id);
	}

	/**
	 * 播放下一个NPC对话剧本
	 */
	public static void playNextNpcDialogueJuBen(Chara chara) {
		if (chara.currentJuBens != null) {
			playNpcDialogueJuBen(chara, Integer.valueOf(chara.currentJuBens[chara.nextJuBen]));
			chara.nextJuBen += 1;
			if (chara.nextJuBen >= chara.currentJuBens.length) {
				chara.nextJuBen = 0;
				chara.currentJuBens = null;
				chara.jubenAllTeam = false;
				// 如果剧本结束的话,关闭所有队伍中的剧本显示.
				Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
				vo_9129_0.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
				vo_9129_0.para = "DramaDlg";
				GameObjectChar.sendduiwu(new MSG_GENERAL_NOTIFY(), vo_9129_0, chara.id);

			}
		}

	}

	/**
	 * 剧本对话
	 */
	public static void playNpcDialogueJuBen(Chara chara, int nJuBenID) {
		NpcDialogue npcDialogue = GameData.that.baseNpcDialogueService.findById(nJuBenID);
		if (npcDialogue == null)
			return;

		Vo_45056_0 MSGPLAYSCENARIODVO = new Vo_45056_0();
		if ("玩家".equals(npcDialogue.getName())) {
			MSGPLAYSCENARIODVO.name = chara.name;
			MSGPLAYSCENARIODVO.portrait = chara.waiguan;
		} else {
			MSGPLAYSCENARIODVO.name = npcDialogue.getName();
			MSGPLAYSCENARIODVO.portrait = npcDialogue.getPortranit();
		}
		MSGPLAYSCENARIODVO.id = npcDialogue.getId();
		MSGPLAYSCENARIODVO.pic_no = npcDialogue.getPicNo();
		MSGPLAYSCENARIODVO.content = npcDialogue.getContent();
		MSGPLAYSCENARIODVO.isComplete = npcDialogue.getIsconmlete();
		MSGPLAYSCENARIODVO.playTime = npcDialogue.getPalytime();
		MSGPLAYSCENARIODVO.task_type = npcDialogue.getTaskType();
		if (chara.jubenAllTeam)
			GameObjectChar.sendduiwu(new M45056_0(), MSGPLAYSCENARIODVO, chara.id);
		else
			GameObjectChar.send(new M45056_0(), MSGPLAYSCENARIODVO);
	}

	/**
	 * 根据相性文字转化成int
	 *
	 * @param polar
	 * @return
	 */
	public static int getMetal(String polar) {
		if (polar.equals("金")) {
			return 1;
		}
		if (polar.equals("木")) {
			return 2;
		}
		if (polar.equals("水")) {
			return 3;
		}
		if (polar.equals("火")) {
			return 4;
		}
		if (polar.equals("土")) {
			return 5;
		}
		return 0;
	}

	public static List<Chara> checkTeam(int manCount) {
		if (GameObjectChar.getGameObjectChar().gameTeam == null) {
			GameUtil.sendMeTips("请组队!");
			return null;
		}

		List<Chara> duiwu = GameObjectChar.getGameObjectChar().gameTeam.duiwu;

		if (duiwu == null) {
			GameUtil.sendMeTips("请组队!");
			return null;
		}
		if (duiwu.size() < manCount) {
			GameUtil.sendMeTips("人数不足" + manCount + "人！");
			return null;
		}
		return duiwu;
	}

	/**
	 * 更新右侧组队队伍信息.
	 * 
	 * @param chara
	 */
	public static void updateRightTeamInfos(Chara chara) {

		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		if (null != gameObjectChar.gameTeam) {
			List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
			GameUtil.MSG_UPDATE_TEAM_LIST(duiwu);
			for (Vo_4121_0 vo_4121_0 : gameObjectChar.gameTeam.zhanliduiyuan) {
				if (vo_4121_0.id == chara.id) {
					if (chara.upgrade_state != 0) {
						vo_4121_0.skill = chara.upgrade_level;
					} else {
						vo_4121_0.skill = chara.level;
					}
				}
			}
			GameUtil.MSG_UPDATE_TEAM_LIST_EX(gameObjectChar.gameTeam.zhanliduiyuan);
		}

	}

	public static void MSG_UPDATE_TEAM_LIST_EX(List<Vo_4121_0> charaList) {
		List<Vo_4121_0> vo_4121_0List = new ArrayList<>();
		for (Vo_4121_0 vo41210 : charaList) {
			Vo_4121_0 vo_4121_0 = new Vo_4121_0();
			vo_4121_0.id = vo41210.id;
			vo_4121_0.gid = vo41210.gid;
			vo_4121_0.suit_icon = vo41210.suit_icon;
			vo_4121_0.weapon_icon = vo41210.weapon_icon;
			vo_4121_0.org_icon = vo41210.org_icon;
			vo_4121_0.skill = vo41210.skill;
			vo_4121_0.str = vo41210.str;
			vo_4121_0.master = vo41210.master;
			vo_4121_0.metal = vo41210.metal;
			vo_4121_0.passive_mode = vo41210.passive_mode;
			vo_4121_0.req_str = "";
			vo_4121_0.durability = 1;
			vo_4121_0.party_contrib = vo41210.party_contrib;
			vo_4121_0.upgrade_level = vo41210.upgrade_level;
			vo_4121_0.memberpos_x = vo41210.memberpos_x;
			vo_4121_0.memberpos_y = vo41210.memberpos_y;
			vo_4121_0.membermap_id = vo41210.membermap_id;
			vo_4121_0.memberteam_status = vo41210.memberteam_status;
			vo_4121_0.membercard_name = "";
			vo_4121_0.membercomeback_flag = vo41210.membercomeback_flag;
			vo_4121_0.memberlight_effect_count = 0;
			vo_4121_0List.add(vo_4121_0);
		}
		for (Vo_4121_0 vo41210 : charaList) {
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(vo41210.id);
			if(gameObjectChar != null) {
				gameObjectChar.sendOne(new M4121_0(), vo_4121_0List);
			}
		}
	}

	public static void MSG_UPDATE_TEAM_LIST(List<Chara> charaList) {
		List<Vo_4119_0> vo_4119_0List = new ArrayList<>();
		for (Chara chara : charaList) {
			Vo_4119_0 vo_4119_0 = new Vo_4119_0();
			vo_4119_0.id = chara.id;
			vo_4119_0.gid = chara.uuid;
			vo_4119_0.suit_icon = chara.suit_icon;
			vo_4119_0.weapon_icon = chara.weapon_icon;
			vo_4119_0.org_icon = chara.waiguan;
			vo_4119_0.skill = chara.level;
			vo_4119_0.str = chara.name;
			vo_4119_0.master = chara.sex;
			vo_4119_0.metal = chara.polar;
			vo_4119_0.passive_mode = chara.waiguan;
			vo_4119_0.req_str = "";
			vo_4119_0.party_contrib = chara.chenhao;
			vo_4119_0.upgrade_level = chara.upgrade_level;
			vo_4119_0.membercard_name = "";
			vo_4119_0.memberlight_effect_count = 0;
			vo_4119_0List.add(vo_4119_0);
		}
		List<Vo_45074_0> list = new ArrayList<>();
		if (charaList != null && !charaList.isEmpty() && charaList.get(0) != null) {
			Chara chara1 = charaList.get(0);
			for (int i = 0; i < chara1.listshouhu.size(); i++) {
				if (chara1.listshouhu.get(i).listShouHuShuXing.get(0).nil != 0) {
					Vo_45074_0 vo_45074_0 = new Vo_45074_0();
					vo_45074_0.guardName = chara1.listshouhu.get(i).listShouHuShuXing.get(0).str;
					vo_45074_0.guardLevel = chara1.level;
					vo_45074_0.guardIcon = chara1.listshouhu.get(i).listShouHuShuXing.get(0).type;
					vo_45074_0.guardOrder = chara1.listshouhu.get(i).listShouHuShuXing.get(0).salary;
					vo_45074_0.guardId = chara1.listshouhu.get(i).id;
					list.add(vo_45074_0);
				}
			}
		}
		for (Chara chara : charaList) {
			GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
			if (gameObjectChar != null) {
				gameObjectChar.sendOne(new M45074_0(), list);
				gameObjectChar.sendOne(new M4119_0(), vo_4119_0List);
			}
		}
	}

	// 更新角色数据
	public static void sendUpdate(Chara chara, String... event) {

		ListVo_65527_0 listVo_65527_0 = a65527(chara, event);
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		if (gameObjectChar != null) {
			gameObjectChar.sendOne(new M65527_0(), listVo_65527_0);
		}
	}

	// 改变npc对话
	public static void changeNpcSession(Integer id, Integer icon, String name, String content) {
		Vo_MENU_LIST vo_8247_0 = new Vo_MENU_LIST();
		vo_8247_0.id = id;
		vo_8247_0.portrait = icon;
		vo_8247_0.pic_no = 1;
		vo_8247_0.content = content.replace("\\", "");
		vo_8247_0.secret_key = "";
		vo_8247_0.name = name;
		vo_8247_0.attrib = 1;
		GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_0);
	}

	// npc会话
	public static void changeNpcSession(Npc npc, String content) {
		if(npc == null) {
			return;
		}
		Vo_MENU_LIST vo_8247_0 = new Vo_MENU_LIST();
		vo_8247_0.id = npc.getId();
		vo_8247_0.portrait = npc.getIcon();
		vo_8247_0.pic_no = 1;
		vo_8247_0.content = content.replace("\\", "");
		vo_8247_0.secret_key = "";
		vo_8247_0.name = npc.getName();
		vo_8247_0.attrib = 1;
		GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_0);
	}

	public static void clearBackPackage(String menu_item, GameObjectChar gameObjectChar) {
		//校验安全密码
		if(gameObjectChar == null) {
			return;
		}
		if(GameCommonUtil.isValidateSafePwd(gameObjectChar)) {
			return;
		}
 		Chara chara = gameObjectChar.chara;
		Iterator<Goods> goodsIt = chara.backpack.iterator();
		List<Integer> filterPos = new ArrayList<>();
		if (menu_item.equals("清理第一页")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if(goods.goodsInfo.str.equals("结婚纪念册")) {
					filterPos.add(goods.pos);
					continue;
				}
				if (goods.pos >=41 && goods.pos <= 65) {
					goodsIt.remove();
				}
			}
			//无论有没有都要通知客户端删除
			for (int i = 41; i < 66; i++) {
				if(filterPos.contains(Integer.valueOf(i))) {
					continue;
				}
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), i);
			}
		} else if (menu_item.equals("清理第二页")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if(goods.goodsInfo.str.equals("结婚纪念册")) {
					filterPos.add(goods.pos);
					continue;
				}
				if (goods.pos >=66 && goods.pos <= 90) {
					GameObjectChar.send(new MSG_INVENTORY_REMOVE(), goods.pos);
					goodsIt.remove();
				}
			}
			//无论有没有都要通知客户端删除
			for (int i = 66; i < 91; i++) {
				if(filterPos.contains(Integer.valueOf(i))) {
					continue;
				}
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), i);
			}
		} else if (menu_item.equals("清理第三页")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if(goods.goodsInfo.str.equals("结婚纪念册")) {
					filterPos.add(goods.pos);
					continue;
				}
				if (goods.pos >=91 && goods.pos <= 115) {
					goodsIt.remove();
				}
			}
			//无论有没有都要通知客户端删除
			for (int i = 91; i < 116; i++) {
				if(filterPos.contains(Integer.valueOf(i))) {
					continue;
				}
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), i);
			}
		} else if (menu_item.equals("清理第四页")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if(goods.goodsInfo.str.equals("结婚纪念册")) {
					filterPos.add(goods.pos);
					continue;
				}
				if (goods.pos >=116 && goods.pos <= 140) {
					goodsIt.remove();
				}
			}
			
			//无论有没有都要通知客户端删除
			for (int i = 116; i < 141; i++) {
				if(filterPos.contains(Integer.valueOf(i))) {
					continue;
				}
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), i);
			}
		} else if (menu_item.equals("清理第五页")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if(goods.goodsInfo.str.equals("结婚纪念册")) {
					filterPos.add(goods.pos);
					continue;
				}
				if (goods.pos >=141 && goods.pos <= 165) {
					goodsIt.remove();
				}
			}
			//无论有没有都要通知客户端删除
			for (int i = 141; i < 166; i++) {
				if(filterPos.contains(Integer.valueOf(i))) {
					continue;
				}
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), i);
			}
		} else if (menu_item.equals("清理背包")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if(goods.goodsInfo.str.equals("结婚纪念册")) {
					filterPos.add(goods.pos);
					continue;
				}
				if (goods.pos >=41 && goods.pos <= 165) {
					goodsIt.remove();
				}
			}
			//无论有没有都要通知客户端删除
			for (int i = 41; i < 166; i++) {
				if(filterPos.contains(Integer.valueOf(i))) {
					continue;
				}
				GameObjectChar.send(new MSG_INVENTORY_REMOVE(), i);
			}
		}
		//重新缓存一次
		GameUtil.sendMeTips("清理成功！");
	}
	
	/**
	 * a清理仓库
	 * @param menu_item
	 * @param chara
	 */
	public static void clearStorePackage(String menu_item, GameObjectChar gameObjectChar) {
		if(gameObjectChar == null) {
			return;
		}
		//打开设置密码
		if(GameCommonUtil.isValidateSafePwd(gameObjectChar)) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		Iterator<Goods> goodsIt = chara.cangku.iterator();
		if (menu_item.equals("clearStoreS1")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if (goods.pos >=201 && goods.pos <= 225 && !goods.goodsInfo.str.equals("结婚纪念册")) {
					goodsIt.remove();
				}
			}
		} else if (menu_item.equals("clearStoreS2")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if (goods.pos >=226 && goods.pos <= 250) {
					goodsIt.remove();
				}
			}
		} else if (menu_item.equals("clearStoreS3")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if (goods.pos >=256 && goods.pos <= 275 && !goods.goodsInfo.str.equals("结婚纪念册")) {
					goodsIt.remove();
				}
			}
		} else if (menu_item.equals("clearStoreS4")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if (goods.pos >=286 && goods.pos <= 300 && !goods.goodsInfo.str.equals("结婚纪念册")) {
					goodsIt.remove();
				}
			}
		} else if (menu_item.equals("clearStore")) {
			while(goodsIt.hasNext()) {
				Goods goods = goodsIt.next();
				if (goods.pos >=201 && goods.pos <= 300 && !goods.goodsInfo.str.equals("结婚纪念册")) {
					goodsIt.remove();
				}
			}
		}
		//刷新仓库
		Vo_61677_0 vo_61677_0 = new Vo_61677_0();
		vo_61677_0.list = chara.cangku;
		GameObjectChar.send(new M61677_0(), vo_61677_0);
		GameUtil.sendMeTips("清理成功！");
	}

	/**
	 * 发送谣言
	 * 
	 * @param msg 消息
	 */
	public static void sendYaoYan(String msg) {
		Vo_MESSAGE v = new Vo_MESSAGE();
		v.channel = 6;
		v.id = 0;
		v.name = "";
		v.msg = msg;
		v.time = (int) (System.currentTimeMillis()/1000L);
		v.privilege = 0;
		v.server_name = GameConfig.lineName + "1线";
		v.show_extra = 0;
		v.show_time = (int) (System.currentTimeMillis()/1000L);
		v.icon = 0;
		GameObjectCharMng.sendAll(new MSG_MESSAGE(), v);
	}

	// 随机门派技能.
	public static String getRandomSkills(int polar) {
		String skillJson = PetAndHelpSkillUtils.skillJson;
		if (randomSkills == null) {
			randomSkills = com.alibaba.fastjson.JSONArray.parseArray(skillJson);
		}

		int i = new Random().nextInt(10) + 1;
		int count = 0;
		StringBuffer skillsBuf = new StringBuffer();
		for (int j = 0; j < randomSkills.size(); j++) {
			if (count == i) {
				break;
			}
			com.alibaba.fastjson.JSONObject json = randomSkills.getJSONObject(j);
			if (json.getInteger("metal").equals(polar)) {
				skillsBuf.append(json.getString("skillName"));
				skillsBuf.append(",");
				count++;
			}
		}
		String string = skillsBuf.toString();
		if (!StringUtils.isNullOrEmpty(string)) {
			return skillsBuf.deleteCharAt(skillsBuf.length() - 1).toString();
		} else {
			return "";
		}
	}

	/**
	 * 自动解散队伍.
	 * 
	 * @param gameTeam
	 */
	public static void dissolutionTeam(Chara chara) {
		GameTeam gameTeam = GameObjectCharMng.getGameObjectChar(chara.id).gameTeam;
		if (gameTeam != null && gameTeam.duiwu != null && !gameTeam.duiwu.isEmpty()
				&& chara.id == gameTeam.duiwu.get(0).id) {
			for (int i = 0; i < gameTeam.zhanliduiyuan.size(); ++i) {
				List<Vo_4119_0> object1 = new ArrayList<Vo_4119_0>();
				GameObjectCharMng.getGameObjectChar(gameTeam.zhanliduiyuan.get(i).id).sendOne(new M4119_0(), object1);

				List<Vo_4121_0> vo_4121_0List = new ArrayList<Vo_4121_0>();
				GameObjectCharMng.getGameObjectChar(gameTeam.zhanliduiyuan.get(i).id).sendOne(new M4121_0(),
						vo_4121_0List);

				Vo_20480_0 vo_20480_0 = new Vo_20480_0();
				vo_20480_0.msg = "队伍解散了。";
				vo_20480_0.time = (int) (System.currentTimeMillis() / 1000);
				GameObjectCharMng.getGameObjectChar(gameTeam.zhanliduiyuan.get(i).id).sendOne(new M20480_0(),
						vo_20480_0);

				Vo_TITLE vo_61671_0 = new Vo_TITLE();
				vo_61671_0.id = gameTeam.zhanliduiyuan.get(i).id;
				vo_61671_0.list.add(2);
				GameObjectChar.getGameObjectChar().gameMap.send(new MSG_TITLE(), vo_61671_0);
			}
			for (int i = 0; i < GameObjectChar.getGameObjectChar().gameTeam.zhanliduiyuan.size() - 1; ++i) {
				GameObjectCharMng.getGameObjectChar(
						GameObjectChar.getGameObjectChar().gameTeam.zhanliduiyuan.get(i + 1).id).gameTeam = null;
			}
			Vo_61593_0 vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "request_join";
			GameObjectChar.send(new M61593_0(), vo_61593_0);

			vo_61593_0 = new Vo_61593_0();
			vo_61593_0.ask_type = "request_team_leader";
			GameObjectChar.send(new M61593_0(), vo_61593_0);

			List<Vo_4121_0> vo_4121_0List2 = new ArrayList<Vo_4121_0>();
			GameObjectChar.send(new M4121_0(), vo_4121_0List2);
			GameObjectChar.getGameObjectChar().gameTeam = null;
		}
	}

	/**
	 * 带参数的确认框
	 * 
	 * @param chara
	 * @param tips  提示文字
	 * @param type  类型. 当前操作的类型,用于判断
	 */
	public static void confirm(Chara chara, String tips, String type) {
		Vo_CONFIRM vo_45240_0 = new Vo_CONFIRM();
		vo_45240_0.tips = tips;
		vo_45240_0.down_count = 0;
		vo_45240_0.only_confirm = 3;
		vo_45240_0.confirm_type = "";
		vo_45240_0.confirmText = "";
		vo_45240_0.cancelText = "";
		vo_45240_0.show_dlg_mode = 1;
		vo_45240_0.countDownTips = "";
		vo_45240_0.para_str = "{}";
		chara.currentConfirmItem = type;
		GameObjectChar.send(new MSG_CONFIRM(), vo_45240_0, chara.id);
	}
	
	/**
	 * 带参数的确认框
	 * 
	 * @param chara
	 * @param tips  提示文字
	 * @param type  类型. 当前操作的类型,用于判断
	 */
	public static void confirm(Chara chara, String tips, String type, int downCount) {
		Vo_CONFIRM vo_45240_0 = new Vo_CONFIRM();
		vo_45240_0.tips = tips;
		vo_45240_0.down_count = downCount;
		vo_45240_0.only_confirm = 3;
		vo_45240_0.confirm_type = "";
		vo_45240_0.confirmText = "";
		vo_45240_0.cancelText = "";
		vo_45240_0.show_dlg_mode = 1;
		vo_45240_0.countDownTips = "";
		vo_45240_0.para_str = "{}";
		chara.currentConfirmItem = type;
		GameObjectChar.send(new MSG_CONFIRM(), vo_45240_0, chara.id);
	}
	
	public static void confirm(Chara chara, String tips, String confirmText, String cancelText, String type) {
		Vo_CONFIRM Vo_CONFIRM = new Vo_CONFIRM();
		Vo_CONFIRM.tips = tips;
		Vo_CONFIRM.down_count = 0;
		Vo_CONFIRM.only_confirm = 3;
		Vo_CONFIRM.confirm_type = "";
		Vo_CONFIRM.confirmText = confirmText;
		Vo_CONFIRM.cancelText = cancelText;
		Vo_CONFIRM.show_dlg_mode = 1;
		Vo_CONFIRM.countDownTips = "";
		Vo_CONFIRM.para_str = "{}";
		chara.currentConfirmItem = type;
		GameObjectChar.send(new MSG_CONFIRM(), Vo_CONFIRM);
	}
	
	public static void confirm(Chara chara, String tips, String confirmText, String cancelText, String type, int down_count) {
		Vo_CONFIRM Vo_CONFIRM = new Vo_CONFIRM();
		Vo_CONFIRM.tips = tips;
		Vo_CONFIRM.down_count = down_count;
		Vo_CONFIRM.only_confirm = 3;
		Vo_CONFIRM.confirm_type = "";
		Vo_CONFIRM.confirmText = confirmText;
		Vo_CONFIRM.cancelText = cancelText;
		Vo_CONFIRM.show_dlg_mode = 1;
		Vo_CONFIRM.countDownTips = "";
		Vo_CONFIRM.para_str = "{}";
		chara.currentConfirmItem = type;
		GameObjectChar.send(new MSG_CONFIRM(), Vo_CONFIRM);
	}

	/**
	 * 根据门派和性别获取人物外观
	 * 
	 * @param polar 门派
	 * @param sex    性别
	 * @return
	 */
	public static int getWaiguan(int polar, int sex, Chara chara) {
		int waiguan = 0;
		if (polar == 1 && sex == 1) {
			waiguan = 6001;
		} else if (polar == 2 && sex == 1) {
			waiguan = 7002;
		} else if (polar == 3 && sex == 1) {
			waiguan = 7003;
		} else if (polar == 4 && sex == 1) {
			waiguan = 6004;
		} else if (polar == 5 && sex == 1) {
			waiguan = 6005;
		} else if (polar == 1 && sex == 2) {
			waiguan = 7001;
		} else if (polar == 2 && sex == 2) {
			waiguan = 6002;
		} else if (polar == 3 && sex == 2) {
			waiguan = 6003;
		} else if (polar == 4 && sex == 2) {
			waiguan = 7004;
		} else if (polar == 5 && sex == 2) {
			waiguan = 7005;
		}
		return waiguan;
	}
	
	/**
	 * 没登录的情况发送对话框
	 * @param gameObjectChar
	 * @param tips
	 * @param type
	 */
	public static void confirm(GameObjectChar gameObjectChar, String tips, String type) {
		Vo_CONFIRM vo_45240_0 = new Vo_CONFIRM();
		vo_45240_0.tips = tips;
		vo_45240_0.down_count = 0;
		vo_45240_0.only_confirm = 3;
		vo_45240_0.confirm_type = "";
		vo_45240_0.confirmText = "";
		vo_45240_0.cancelText = "";
		vo_45240_0.show_dlg_mode = 1;
		vo_45240_0.countDownTips = "";
		vo_45240_0.para_str = "{}";
		gameObjectChar.currentConfirmItem = type;
		gameObjectChar.ctx.writeAndFlush(new MSG_CONFIRM().write(vo_45240_0));
	}
	
	/**
	 * 根据等级获取一组首饰
	 * @param level
	 * @return
	 */
	public static String[] getShowShiNameArrByLevel(int level) {
		String[] nameArr = null;
		if(level == 35) {
			nameArr = SHOU_SHI_35;
		}else if(level == 50) {
			nameArr = SHOU_SHI_50;
		}else if(level == 60) {
			nameArr = SHOU_SHI_60;
		}else if(level == 70) {
			nameArr = SHOU_SHI_70;
		}else if(level == 80) {
			nameArr = SHOU_SHI_80;
		}else if(level == 90) {
			nameArr = SHOU_SHI_90;
		}else if(level == 100) {
			nameArr = SHOU_SHI_100;
		}else if(level == 110) {
			nameArr = SHOU_SHI_110;
		}else if(level == 120) {
			nameArr = SHOU_SHI_120;
		}else if(level == 130) {
			nameArr = SHOU_SHI_130;
		}else if(level == 140) {
			nameArr = SHOU_SHI_140;
		}else if(level == 150) {
			nameArr = SHOU_SHI_150;
		}else if(level == 160) {
			nameArr = SHOU_SHI_160;
		}else if(level == 170) {
			nameArr = SHOU_SHI_170;
		}
		return nameArr;
	}
	
	/**
	 * 获得蓝色装备
	 * @param chara 玩家
	 * @param zhuangb 装备
	 * @param degree_32 是否未鉴定
	 * @param owner_id 数量
	 * @param goodsLanSe 蓝色属性
	 */
	public static void getBlueEquipGoods(Chara chara, ZhuangbeiInfo zhuangb, int degree_32, int owner_id,
			GoodsLanSe goodsLanSe) {
		Goods goods = new Goods();
		int pos2 = packPoint(chara);
		if (pos2 == -1) {
			return;
		}
		goods.pos = pos2;
		goods.goodsLanSe = goodsLanSe;
		goods.goodsCreate(zhuangb);
		goods.goodsInfo.owner_id = owner_id;
		goods.goodsInfo.degree_32 = degree_32;
		addwupin(goods, chara);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
	}
	
	/**
	 * 获取技能回合数
	 * @param differTao 相差的道行(年)
	 * @param maxRound 最大回合数
	 * @return
	 */
	public static int getRemoveRound(int differTao, int maxRound) {
		int removeRound = 1;
		//100年到200年之间2个回合
		if(differTao>=100 && differTao<=200) {
			removeRound = 2;
		}else if(differTao>=201 && differTao<=500) {
			removeRound = 3;
		}else if(differTao>=501 && differTao<=1000) {
			removeRound = 4;
		}else if(differTao>1000) {
			removeRound = 5;
		}
		//如果大于当前回合数.那就让他变成最大回合数
		if(removeRound>maxRound) {
			removeRound = maxRound;
		}
		return removeRound;
	}
}