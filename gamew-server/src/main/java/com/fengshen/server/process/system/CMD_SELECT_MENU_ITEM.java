package com.fengshen.server.process.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.write.jiutian.MSG_GHOSTDOM_CHALLENGE_INFO;
import com.fengshen.server.data.write.jiutian.Vo_53951;
import com.fengshen.server.game.*;
import com.fengshen.server.process.common.CMD_GENERAL_NOTIFY;
import com.fengshen.server.util.GameConfig;
import com.fengshen.db.domain.*;
import com.fengshen.server.data.vo.*;
import com.fengshen.server.data.write.*;
import com.fengshen.server.data.write.jiutian.MSG_JIUTIAN_ZHENJUN;
import com.fengshen.server.data.write.jiutian.Vo_33321;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.core.util.Utils;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.db.service.chara.WeddingListService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.constant.BonusType;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.vo.baxian.Vo_BAXIAN_MENGJING_INFO;
import com.fengshen.server.data.vo.dungeon.Vo_DUNGEON_LIST;
import com.fengshen.server.data.vo.dungeon.Vo_DUNGEON_LIST.DugeonsInfo;
import com.fengshen.server.data.vo.jiehun.Vo_OPEN_TIQIN_DLG;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_TASK_INFO;
import com.fengshen.server.data.vo.shop.Vo_RARE_SHOP_ITEMS_INFO;
import com.fengshen.server.data.vo.system.Vo_DESTROY_VALUABLE_LIST;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.vo.zuolao.Vo_ZUOLAO_INFO;
import com.fengshen.server.data.write.baxian.MSG_BAXIAN_MENGJING_INFO;
import com.fengshen.server.data.write.chat.MSG_MESSAGE;
import com.fengshen.server.data.write.dungeon.MSG_DUNGEON_LIST;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_START_DATA;
import com.fengshen.server.data.write.jiehun.MSG_OPEN_TIQIN_DLG;
import com.fengshen.server.data.write.leitai.MSG_COMPETE_TOURNAMENT_TARGETS;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_TASK_INFO;
import com.fengshen.server.data.write.shop.MSG_RARE_SHOP_ITEMS_INFO;
import com.fengshen.server.data.write.system.MSG_DESTROY_VALUABLE_LIST;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.zuolao.MSG_ZUOLAO_INFO;
import com.fengshen.server.data.write.zuolao.MSG_ZUOLAO_INFO_FINISH;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsGaiZao;
import com.fengshen.server.domain.GoodsGaiZaoGongMing;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.config.VipChargeConfig;
import com.fengshen.server.domain.config.VipChargeConfig.Reward;
import com.fengshen.server.domain.config.VipChargeConfig.Reward.RewardInfo;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.fight.FightTeam;
import com.fengshen.server.service.ChallengeLeaderService;
import com.fengshen.server.service.CharaStatueService;
import com.fengshen.server.service.HeroPubService;
import com.fengshen.server.service.MapGuardianService;
import com.fengshen.server.service.ZhengDaoDianService;
import com.fengshen.server.util.CompeteTournamentUtils;
import com.fengshen.server.util.GameActiveUtil;
import com.fengshen.server.util.MsgUtil;
import com.fengshen.server.util.NpcIds;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 所有任务NPC弹出框对应的操作
 */
@Service
@Slf4j
public class CMD_SELECT_MENU_ITEM implements GameHandler {
    public int[] jiage;
    public int[] coins;
    String[] tongttXj; // 通天塔里面的新君
    String[] shanggu; // 上古的类型
    String[] tongttcw; // 通天塔和星君一起战斗的宠物
    private static Map<Integer, List<RenwuMonster>> type2; // [暂时没用]表示降妖的刷道怪物
    //改造价格表
    private int[] fastUpgradeEquip = {0, 0, 0, 0, 0, 0, 0, 25000000, 75000000, 200000000, 375000000};
    //改造标识
    private String[] fastUpgradeEquipStr = {"", "", "", "", "", "", "", "2500万元宝", "7500万元宝", "2亿元宝", "3.75亿元宝"};

    public CMD_SELECT_MENU_ITEM() {
        this.jiage = new int[]{6, 30, 100, 200, 328, 500, 648, 1000, 2000, 3000, 5000};
        this.tongttXj = new String[]{"玉衡星君", "天权星君", "天玑星君", "天璇星君", "天枢星君", "摇光星君", "开阳星君"};
        this.shanggu = new String[]{"将夜·琵琶精", "将夜·骷髅战将", "将夜·千面妖", "将夜·狐狸精"};
        this.tongttcw = new String[]{"通天塔朱雀", "通天塔疆良", "通天塔玄武"};
    }

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 妖塔对应id
     */
    private static final String[] yaotas = new String[]{"妖塔一层", "妖塔二层", "妖塔三层", "妖塔四层", "妖塔五层",
            "妖塔六层", "妖塔七层", "妖塔八层", "妖塔九层"};

    public static int getIdxForYaotaName(String mapName) {
        for (int i = 0; i < yaotas.length; i++) {
            if (yaotas[i].equals(mapName)) {
                return i;
            }
        }
        return 0;
    }


    private boolean checkFightNum(Chara chara) {

        if (chara.molongCount + 1 > GameConfig.config.getBaseConfig().getMolongCount()) {
            GameUtil.sendMeTips("你已完成今日的指点！");
            return true;
        }

        if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
            final StringBuilder msg = new StringBuilder();
            msg.append("队伍中[");
            boolean flag = false;
            for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                if (duiwu.molongCount + 1 > GameConfig.config.getBaseConfig().getMolongCount() && duiwu.id != chara.id) {
                    msg.append("#Y").append(duiwu.name).append(",");
                    flag = true;
                }
            }
            if (flag) {
                msg.append("#n]已完成今日的指点！");
                msg.replace(msg.lastIndexOf(","), msg.lastIndexOf(",") + 1, "");
                GameUtil.sendMeTips(msg.toString());
                return true;
            }
//            for (final Chara chara2 : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
//                ++chara2.molongCount;
//            }
        } else {
//            ++chara.molongCount;
        }
        return false;
    }

    public static void FightEnd(Chara chara) {
        // if (chara.curCheckpoint < 9) {
        //     chara.curCheckpoint++;
        // }
    }

    @Override
    public void process(ChannelHandlerContext ctx, ByteBuf buff) {
        int id = GameReadTool.readInt(buff); // npc_id

        Npc npc = GameData.that.baseNpcService.findById(id);
        String menu_item = GameReadTool.readString(buff);
        String para = GameReadTool.readString(buff);
        GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
        Chara chara = gameObjectChar.chara;
        GameMap gameMap = gameObjectChar.gameMap;
        GameObjectChar.send(new M4155_0(), id);
        log.info("点击菜单，id={},menu_item={},para={}", id, menu_item, para);
        if (chara.taskMap.get("坐牢") != null) {
            GameUtil.sendMeTips("正在坐牢，不允许操作");
            return;
        }

//		// 判断是否非法请求
//		if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
//			//判断是否暂离队伍
//			boolean isLeave = true;
//			for(Chara team:gameObjectChar.gameTeam.duiwu) {
//				if(team.id == chara.id) {
//					isLeave =  false;
//				}
//			}
//			if(!isLeave) {
//				// 如果有队伍，点击菜单的必须是队长
//				if (chara.id != gameObjectChar.gameTeam.duiwu.get(0).id) {
//					GameUtil.sendMeTips("既然加入队伍了，就不要非法请求了要老老实实听队长指挥哦！");
//					return;
//				}
//			}
//		}

        if (menu_item.contains("地狱深渊")) {
//                GameUtil.openDlg("DiYuShenYuanDlg");
            Vo_53951 vo_53951 = new Vo_53951();
            vo_53951.heightestLevel = 1;
            vo_53951.cengshu = chara.cengshu;
            vo_53951.icon = 6510;
            //
            vo_53951.rewardInfo = Lists.newArrayList("#I物品|阴德礼盒#I", "#I物品|冥海霞光#I", "#I物品|铸灵石#I", "#I物品|真灵精粹#I", "#I物品|蝎后血精#I", "#I物品|魔猪血精#I", "#I物品|黑熊血精#I", "#I物品|鬼猿血精#I", "#I物品|冥灵石#I");
            vo_53951.bonus_flag = 1;
            gameObjectChar.sendOne(new MSG_GHOSTDOM_CHALLENGE_INFO(), vo_53951);
            return;
        }

        if ("moLongToFight1".equals(menu_item)) {//魔龙之尾进入战斗

            if (checkFightNum(chara)) {
                return;
            }


            ArrayList<String> l = new ArrayList<>();
            l.add("魔龙之尾");
            l.add("魔魂(金)");
            l.add("魔魂(木)");
            l.add("魔魂(水)");
            l.add("魔魂(火)");
            l.add("魔魂(土)");
            FightManager.activeBoosGoFight(chara, l, false);
            return;
        } else if ("moLongToFight2".equals(menu_item)) { //魔龙之爪进入战斗

            if (chara.molongIndex < 1) {
                GameUtil.sendMeTips("请先挑战#Y魔龙之尾。");
                return;
            }

            if (checkFightNum(chara)) {
                return;
            }

            ArrayList<String> l = new ArrayList<>();
            l.add("魔龙之爪");
            l.add("魔魂(金)");
            l.add("魔魂(木)");
            l.add("魔魂(水)");
            l.add("魔魂(火)");
            l.add("魔魂(土)");
            FightManager.activeBoosGoFight(chara, l, false);
            return;
        } else if ("moLongToFight3".equals(menu_item)) { //魔龙之首进入战斗
            if (chara.molongIndex < 2) {
                GameUtil.sendMeTips("请先挑战#Y魔龙之爪。");
                return;
            }


            if (checkFightNum(chara)) {
                return;
            }

            ArrayList<String> l = new ArrayList<>();
            l.add("魔龙之首");
            l.add("魔魂(金)");
            l.add("魔魂(木)");
            l.add("魔魂(水)");
            l.add("魔魂(火)");
            l.add("魔魂(土)");
            FightManager.activeBoosGoFight(chara, l, false);
            return;
        } else if ("moLongToFight4".equals(menu_item)) { //魔龙吞天进入战斗
            if (checkFightNum(chara)) {
                return;
            }

            ArrayList<String> l = new ArrayList<>();
            l.add("魔龙吞天");
            l.add("魔魂(金)");
            l.add("魔魂(木)");
            l.add("魔魂(水)");
            l.add("魔魂(火)");
            l.add("魔魂(土)");
            FightManager.activeBoosGoFight(chara, l, false);

            return;
        } else if ("moLongToFight5".equals(menu_item)) { //魔龙吞天·怒进入战斗
            if (chara.molongIndex < 3) {
                GameUtil.sendMeTips("请先挑战#Y魔龙之首。");
                return;
            }


            if (checkFightNum(chara)) {
                return;
            }

            ArrayList<String> l = new ArrayList<>();
            l.add("魔龙吞天·怒");
            l.add("魔魂(金)");
            l.add("魔魂(木)");
            l.add("魔魂(水)");
            l.add("魔魂(火)");
            l.add("魔魂(土)");
            FightManager.activeBoosGoFight(chara, l, false);

            return;
        }


        if (id == 1041 && "jiutian_fight".equals(menu_item)) {
            Vo_33321 vo_33321 = new Vo_33321();
            vo_33321.is_open = 1;
            vo_33321.curCheckpoint = chara.curCheckpoint;
            vo_33321.openMax = 9;
            gameObjectChar.sendOne(new MSG_JIUTIAN_ZHENJUN(), vo_33321);
            return;
        }


        if (menu_item.contains("十八层地狱")) {
            GameMap gameMapname = GameLine.getGameMapname(chara.line, "十八层地狱");
            chara.x = gameMapname.x;
            chara.y = gameMapname.y;
            gameMapname.join(gameObjectChar);
            return;
        }


        if (menu_item.equals("zhuxian1") || menu_item.equals("zhuxian2") || menu_item.equals("zhuxian3") ||
                        menu_item.equals("zhuxian4") || menu_item.equals("zhuxian5") || menu_item.equals("zhuxian6") ||
                        menu_item.equals("zhuxian7") || menu_item.equals("zhuxian8") || menu_item.equals("zhuxian9")) {
            String k = menu_item.substring(7, 8);
            int kindex = new Integer(k).intValue();
            int ci = GameConfig.config.getBaseConfig().zhuxianCishu;
            if (chara.zhuxian_cishu >= ci) {
                GameUtil.sendMeTips("每天只能挑战#R" + ci + "#W次诛仙台");
                return;
            }
            //
            String[] m = {"孔宣", "雷震子", "哪咤", "九天玄女", "李靖", "孙悟空", "杨戬", "王母娘娘", "玉皇大帝"};
            if (kindex != chara.zhuxian_ceng) {
                GameUtil.sendMeTips("你还未诛杀#Y" + (m[chara.zhuxian_ceng - 1]) + ",#W还没有资格挑战本尊！");
                return;
            }
             if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                 for (final Chara duiwu : gameObjectChar.gameTeam.duiwu) {
                     if (duiwu.zhuxian_cishu >= ci) {
                         GameCommonUtil.sendTips("您每天挑战次数已满，本次战斗无法获得奖励！",duiwu.id);
                     }
                 }
             }
            int index = new Integer(k).intValue() - 1;
            ArrayList<String> l = new ArrayList<>();
            for (int t = 1; t <= 10; ++t) {
                l.add(m[index]);
            }
            log.info("开始战斗诛仙台");
            FightManager.activeBoosGoFight(chara, l, false);
            return;

        }

        if (menu_item.equals("jianji")) {
            chara.realLevel = 70;
            chara.level = 70;
            chara.jianji(70);
            //重新计算角色信息
            //GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
            //重新计算伤害

            GameUtil.a65511(gameObjectChar);
            GameUtil.zhuangbeiValue(gameObjectChar);

            GameUtil.sendMeTips("降级成功!");
            GameUtil.sendUpdate(chara);
            return;
        }
        String[] mList = {"游魂", "厉鬼", "达摩", "雪妖", "风灵", "炎魔", "炼狱魔", "阴阳师", "魅灵", "狱獒", "范无赦", "牛头狱卒", "谢必安", "马面罗刹", "冥炎之灵", "梦姑", "谛听", "阎魔"};
        if (
                menu_item.equals("1") || menu_item.equals("2") || menu_item.equals("3") ||
                        menu_item.equals("4") || menu_item.equals("5") || menu_item.equals("6") ||

                        menu_item.equals("7") || menu_item.equals("8") || menu_item.equals("9") ||
                        menu_item.equals("10") || menu_item.equals("11") || menu_item.equals("12") ||

                        menu_item.equals("13") || menu_item.equals("14") || menu_item.equals("15") ||
                        menu_item.equals("16") || menu_item.equals("17") || menu_item.equals("18")

        ) {

            int kindex = new Integer(menu_item).intValue();
            if (kindex != chara.diyu_ceng) {
                GameUtil.sendMeTips("你当前挑战的是第#Y" + (chara.diyu_ceng) + "#W层地狱,麻烦挑战对应层的地狱！");
                return;
            }

            int ci = GameConfig.config.getBaseConfig().getDiyuZongcishu();
            if (chara.diyu_cishu >= ci) {
                GameUtil.sendMeTips("#Y每天只能挑战#R" + ci + "#y次地狱");
                return;
            }


            // List<CharaPet> petsByCid = GameData.that.charaPetService.getPetsByCid(chara.id);
            // for (int i=0; i<petsByCid.size(); ++i)
            // {
            // 	CharaPet pet = petsByCid.get(i);
            // 	int t = pet.getId().intValue();
            // 	boolean isGui = true;

            // 	for (Petbeibao petbeibao : chara.pets) {
            // 		if (petbeibao.id < 437 || petbeibao.id > 448 ) {
            // 			isGui = false;
            // 		}
            // 	}

            // 	if (isGui == false)
            // 	{
            // 		GameUtil.sendMeTips("只能携带鬼宠参战");
            // 		return;
            // 	}
            // }

            if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                GameUtil.sendMeTips("不能组队挑战18层地狱");
                return;
            }


            chara.diyu_cishu = chara.diyu_cishu + 1;

            int index = new Integer(menu_item).intValue() - 1;
            ArrayList<String> l = new ArrayList<>();
            for (int t = 1; t <= 5; ++t) {
                l.add(mList[index]);
            }
            FightManager.activeBoosGoFight(chara, l, false);
            return;
        }
        if (menu_item.equals("juanshen")) {
            int goodsNum = GameCommonUtil.getGoodsNum(chara, "神魂卷轴残页");
            if (goodsNum < 100) {
                GameUtil.sendTips("您的#Y神魂卷轴残页#W不足#R100页");
                return;
            }
            GameUtil.removemunber(chara, "神魂卷轴残页", 100);
            GameUtil.huodedaoju(chara, "神魂卷轴", 1);
            GameCommonUtil.sendTips("您获得了#R神魂卷轴#n。", gameObjectChar);
            return;
        }
        if (menu_item.equals("helingshi")) {
            int goodsNum = GameCommonUtil.getGoodsNum(chara, "转世灵符碎片");
            if (goodsNum < 100) {
                GameUtil.sendTips("您的#Y转世灵符碎片#W不足#R100个");
                return;
            }
            GameUtil.removemunber(chara, "转世灵符碎片", 100);
            GameUtil.huodedaoju(chara, "转世灵符", 1);
            GameCommonUtil.sendTips("您获得了#R转世灵符#n。", gameObjectChar);
            return;
        }

        if (menu_item.equals("heguidan")) {
            int goodsNum = GameCommonUtil.getGoodsNum(chara, "鬼丹残魄");
            if (goodsNum < 100) {
                GameUtil.sendTips("您的#Y鬼丹残魄#W不足#R100个");
                return;
            }
            GameUtil.removemunber(chara, "鬼丹残魄", 100);
            GameUtil.huodedaoju(chara, "鬼丹", 1);
            GameCommonUtil.sendTips("您获得了#R鬼丹#n。", gameObjectChar);
            return;
        }

        if (menu_item.equals("heguiwang")) {
            int goodsNum = GameCommonUtil.getGoodsNum(chara, "鬼王内丹残魄");
            if (goodsNum < 100) {
                GameUtil.sendTips("您的#Y鬼王内丹残魄#W不足#R100个");
                return;
            }
            GameUtil.removemunber(chara, "鬼王内丹残魄", 100);
            GameUtil.huodedaoju(chara, "鬼王内丹", 1);
            GameCommonUtil.sendTips("您获得了#R鬼王内丹#n。", gameObjectChar);
            return;
        }


        if (menu_item.equals("shishi")) {
            GameUtil.MSG_UPDATE_ALL_a65511(GameObjectChar.getGameObjectChar());
            return;
        }

        if (menu_item.equals("jinboss")) {
            GameMap gameMapname = GameLine.getGameMapname(chara.line, "埋骨之地");
            chara.x = gameMapname.x;
            chara.y = gameMapname.y;
            gameMapname.join(gameObjectChar);
            return;
        }

        // if (1181 == id) {
        //宠物心法
        if (menu_item.contains("canwu_pet_")) {
            Petbeibao petbeibao = null;
            for (final Petbeibao pet : chara.pets) {
                if (pet.id == chara.chongwuchanzhanId) {
                    petbeibao = pet;
                    break;
                }
            }
            if (petbeibao != null) {
                PetShuXing petShuXing = petbeibao.petShuXing.get(0);
                Map<String, Integer> xinFa = petShuXing.getXinFa();
                if (xinFa == null) {
                    xinFa = new HashMap<>();
                }
                String str = menu_item.replace("canwu_pet_", "");
                String[] split = str.split(":");
                int count = Integer.valueOf(split[0]);
                if (count != 1 && count != 10 && count != 20) {
                    //			CMD_GENERAL_NOTIFY.doCharaBlockAndBlankList(gameObjectChar, "wpe刷宠物心法菜单");
                    return;
                }
                String xilianPet = split[1];
                Integer xilian_pet_ = xinFa.get(xilianPet);
                if (xilian_pet_ == null) {
                    xilian_pet_ = count;
                } else {
                    xilian_pet_ += count;
                }
                int petXinfaMaxLevel = GameConfig.config.getBaseConfig().getPetXinfaMaxLevel();
                if (xilian_pet_ > petXinfaMaxLevel) {
                    GameUtil.sendTips("心法已经是最高层");
                    return;
                }
                int petXinfaGold = GameConfig.config.getBaseConfig().getPetXinfaGold();
                if (chara.goldCoin < petXinfaGold * count) {
                    GameUtil.sendTips("金元宝不足");
                    return;
                }
                int petXinfaSilver = GameConfig.config.getBaseConfig().getPetXinfaSilver();
                if (chara.silverCoin < petXinfaSilver * count) {
                    GameUtil.sendTips("银元宝不足");
                    return;
                }
                int petXinfaPoint = GameConfig.config.getBaseConfig().getPetXinfaPoint();
                if (chara.getChargeScore() < petXinfaPoint * count) {
                    GameUtil.sendTips("积分不足");
                    return;
                }
                int petXinfaBaoDian = GameConfig.config.getBaseConfig().getPetXinfaBaoDian();
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "心法宝典");
                if (goodsNum < petXinfaBaoDian * count) {
                    GameUtil.sendTips("#Y心法宝典#n不足");
                    return;
                }
                GameUtil.removemunber(chara, "心法宝典", petXinfaBaoDian * count);
                chara.addGoldCoin(-petXinfaGold * count);
                chara.addSilverCoin(-petXinfaSilver * count);
                if(petXinfaPoint>0){
                    chara.subChargeScore(petXinfaPoint * count, "宠物心法参悟");
                }
                xinFa.put(xilianPet, xilian_pet_);
                petShuXing.setXinFa(xinFa);
                BasicAttributesUtils.petshuxing(petShuXing,petbeibao);
                final List<Petbeibao> list = new ArrayList<Petbeibao>();
                list.add(petbeibao);
                GameObjectChar.send(new MSG_UPDATE_PETS(), list);
                GameUtilRenWu.petXinfaTask(chara, petShuXing);
                GameUtil.sendTips("宠物心法参悟成功");
                gameObjectChar.sendOne(new M65527_6(), chara);
                menu_item = "xilian_pet_" + xilianPet;
            }
        }
        //宠物心法
        if (menu_item.contains("xilian_pet_")) {
            Petbeibao canZhanPet = chara.getCanZhanPet();
            if (canZhanPet == null) {
                GameUtil.sendTips("请设置参战宠物");
                return;
            }
            String xilian_pet = menu_item.replace("xilian_pet_", "");
            PetShuXing petShuXing = canZhanPet.petShuXing.get(0);
            final Vo_8247_0 vo_8247_5 = GameUtil.a8247(npc, "请选择#R" + petShuXing.str + "#n参悟的属性[#R参悟1层/canwu_pet_1:" + xilian_pet + "][#R参悟10层/canwu_pet_10:" + xilian_pet + "][#R参悟20层/canwu_pet_20:" + xilian_pet + "][离开]");
            GameObjectChar.send(new M8247_0_MSG_MENU_LIST(), vo_8247_5);
            return;
        } else if (menu_item.equals("pet_xinfa")) {
            Petbeibao canZhanPet = chara.getCanZhanPet();
            if (canZhanPet == null) {
                GameUtil.sendTips("请设置参战宠物");
                return;
            }
            PetShuXing petShuXing = canZhanPet.petShuXing.get(0);
            final Vo_8247_0 vo_8247_5 = GameUtil.a8247(npc, "请选择#R" + petShuXing.str + "#n参悟的属性[#R参悟气血/xilian_pet_def][#R参悟法力/xilian_pet_dex][#R参悟物攻/xilian_pet_accurate][#R参悟法攻/xilian_pet_mana][#R参悟速度/xilian_pet_parry][#R参悟防御/xilian_pet_wiz][离开]");
            GameObjectChar.send(new M8247_0_MSG_MENU_LIST(), vo_8247_5);
            return;
        } else if (menu_item.equals("chose_pet_feiSheng")) {
            if (chara.taskMap.get("宠物飞升") != null) {
                GameUtilRenWu.removeTask("引路-宠物飞升", chara);
                GameUtil.changeNpcSession(1181, 6041, "灵兽异人", "当前已有宠物飞升任务,请去完成[离开]");
                return;
            }
            final Vo_MSG_SUBMIT_PET vo = new Vo_MSG_SUBMIT_PET();
            vo.type = 2;
            GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M_MSG_SUBMIT_PET(), vo);
        } else if (menu_item.equals("飞升")) {
            PetFlyMgr.sendPetUpgradedInfo(chara);
            return;
        }
        // }


        if (menu_item.contains("确定精修")) {
            final int subJiFen = GameConfig.shenHunConfig.getData().get(String.valueOf(chara.shenHunDataSate)).getIntValue("jifen");
            int shenHunCostPoint = GameConfig.config.getBaseConfig().getShenHunCostPoint();
            int shenHunCostSilverCoin = GameConfig.config.getBaseConfig().getShenHunCostSilverCoin();
            int shenHunUpMaxLevel = GameConfig.config.getBaseConfig().getShenHunUpMaxLevel();
            if (chara.getChargeScore() < shenHunCostPoint) {
                GameUtil.sendTips("积分不足");
                return;
            }
//            if (chara.getSilverCoin() < shenHunCostSilverCoin) {
//                GameUtil.sendTips("银元宝不足");
//                return ;
//            }
//            if (chara.shenHunDataExp - subJiFen < 0) {
//                GameUtil.sendMeTips("阴德不足无法突破");
//                return ;
//            }
            int goodsNum = GameCommonUtil.getGoodsNum(chara, "神魂卷轴");
            if (goodsNum < 1) {
                GameUtil.sendTips("你未拥有#Y神魂卷轴");
                return;
            }
            if (chara.shenhunUpLevel >= shenHunUpMaxLevel) {
                GameUtil.sendTips("已经是最大精修等级");
                return;
            }

            if (chara.shenHunDataLaye < 10) {
                GameUtil.sendTips("还没达到精炼条件");
                return;
            }

//            chara.shenHunDataExp -= subJiFen;

            chara.subChargeScore(shenHunCostPoint);
//            chara.addSilverCoin(-shenHunCostSilverCoin);
            GameUtil.removemunber(chara, "神魂卷轴", 1);
            Random r = new Random();

            int i = r.nextInt(100);
            int i1 = 100 - chara.shenhunUpLevel * 3;
            i1 = i1 < 0 ? 1 : i1;
            int shenHunUpType = GameConfig.config.getBaseConfig().getShenHunUpType();
            if (shenHunUpType == 1 || i < i1) {
                chara.shenhunUpLevel += 1;
                GameUtil.sendMeTips("恭喜你精修成功，当前精修等级#R" + chara.shenhunUpLevel + "#n级");
                Vo_MENU_LIST vo_8247_3 = new Vo_MENU_LIST();
                vo_8247_3.id = 1716;
                vo_8247_3.portrait = 6412;
                vo_8247_3.pic_no = 1;
                vo_8247_3.content = "消耗神魂卷轴一张" + (shenHunCostPoint > 0 ? ",花费#R" + shenHunCostPoint + "#n积分" : "") + (shenHunCostSilverCoin > 0 ? "#R" + shenHunCostSilverCoin + "#n金元宝" : "") + "，可以进行一次神魂精修(精修有概率失败,等级越高失败几率越大)。每级神魂加成#R1%#n[#L确定精修][离开]";
                vo_8247_3.secret_key = "";
                vo_8247_3.name = "神魂尊者";
                vo_8247_3.attrib = 0;
                GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_3);

                // GameUtil.changeNpcSession(npc, "你的邀请码是"+code);

                GameUtilRenWu.createTask("神魂精修", "当前#Y神魂精修#n等级：#R" + chara.shenhunUpLevel + "#Y级", "神魂精修", chara,
                        "当前神魂精修：所有神魂属性#R+" + chara.shenhunUpLevel + "%#n", "神魂精修：神魂基础属性加成");
                GameCommonUtil.resetShenHunData(chara, gameObjectChar);
                GameUtil.sendUpdate(chara);
            } else {
                GameUtil.sendMeTips("很遗憾，精修失败！请再接再厉。");
            }
            return;
        }


        if (id == 1730) {
            if (menu_item.equals("兑换鬼丹")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "鬼丹");
                if (goodsNum < 10) {
                    GameUtil.sendMeTips("10个鬼丹才可兑换");
                    return;
                }

                // for (int i=0; i<goodsNum; ++i)
                // {
                // 	int value = GameConfig.config.getBaseConfig().getGuidanCount();
                // 	chara.life += value;
                // 	chara.mag_power += value;
                // 	chara.phy_power += value;
                // 	chara.speed += value;
                // 	GameUtil.removemunber(chara, "鬼丹", 1);
                // }
                int c = (int) Math.floor(goodsNum / 10);

                if (c > 0) {
                    GameUtil.removemunber(chara, "鬼丹", 10);
                    int value = GameConfig.config.getBaseConfig().getGuidanCount();
                    //修改鬼丹 加成未分配属性点
                    chara.attribPoint +=value;
                    GameUtil.sendMeTips("兑换成功#R未分配属性点+"+value+"#n");
//                    chara.life += value;
//                    chara.mag_power += value;
//                    chara.phy_power += value;
//                    chara.speed += value;
                }


                GameUtil.sendUpdate(chara);

            }

            if (menu_item.equals("兑换鬼王内丹")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "鬼王内丹");
                if (goodsNum < 10) {
                    GameUtil.sendMeTips("10个鬼王内丹才可兑换");
                    return;
                }

                int c = (int) Math.floor(goodsNum / 10);

                if (c > 0) {
                    GameUtil.removemunber(chara, "鬼王内丹", 10);
                    int value = GameConfig.config.getBaseConfig().getGuiwangneidanCount();
                    //修改鬼王内丹 增加未分配相性 点
                    chara.polarPoint += value;
                    GameUtil.sendMeTips("兑换成功#R未分配相性点+"+value+"#n");
//                    chara.wood += value;
//                    chara.water += value;
//                    chara.fire += value;
//                    chara.earth += value;
//                    chara.metal += value;
                }

                GameUtil.sendUpdate(chara);
            }
            return;
        }

        if (id == 1041 && "jiutian_fight".equals(menu_item)) {
            Vo_33321 vo_33321 = new Vo_33321();
            vo_33321.is_open = 1;
            vo_33321.curCheckpoint = chara.curCheckpoint;
            vo_33321.openMax = 9;
            gameObjectChar.sendOne(new MSG_JIUTIAN_ZHENJUN(), vo_33321);
            return;
        }

// 743		青铜卡
// 744		白银卡
// 745		黄金卡
// 746		铂金卡
// 747		钻石卡
// 748		星耀卡
// 749		王者卡

        if (1750 == id) {

            Charge charge = new Charge();
            int ling = 0;
            final Accounts account = GameData.that.baseAccountsService.findById(gameObjectChar.accountid);


            if (menu_item.equals("兑换青铜卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "青铜卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R青铜卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().qingtongChongzhi;
                GameUtil.removemunber(chara, "青铜卡", 1);
            } else if (menu_item.equals("兑换白银卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "白银卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R白银卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().baiyinChongzhi;
                GameUtil.removemunber(chara, "白银卡", 1);
            } else if (menu_item.equals("兑换黄金卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "黄金卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R黄金卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().huangjinChongzhi;
                GameUtil.removemunber(chara, "黄金卡", 1);

            } else if (menu_item.equals("兑换铂金卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "铂金卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R铂金卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().bojinChongzhi;
                GameUtil.removemunber(chara, "铂金卡", 1);
            } else if (menu_item.equals("兑换钻石卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "钻石卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R钻石卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().zuanshiChongzhi;
                GameUtil.removemunber(chara, "钻石卡", 1);
            } else if (menu_item.equals("兑换星耀卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "星耀卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R星耀卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().xingyaoChongzhi;
                GameUtil.removemunber(chara, "星耀卡", 1);
            } else if (menu_item.equals("兑换王者卡")) {
                int goodsNum = GameCommonUtil.getGoodsNum(chara, "王者卡");
                if (goodsNum < 1) {
                    GameUtil.sendMeTips("道友你的包裹内没有#R王者卡");
                    return;
                }

                ling = GameConfig.config.getBaseConfig().wangzheChongzhi;
                GameUtil.removemunber(chara, "王者卡", 1);
            }

            GameUtil.sendMeTips("恭喜你兑换了#R" + ling + "#n元充值,请到#B一叶知秋#n那里领取！");
            charge.setAccountname(account.name);
            charge.setCoin(ling);
            charge.setMoney(ling);
            charge.setCode("");
            charge.setState(0);
            GameData.that.baseChargeService.add(charge);
        }


        if (id == 1724) {
            String code = "ZZZ" + gameObjectChar.accountid;
            if (menu_item.equals("查询邀请码")) {
                GameUtil.changeNpcSession(npc, "你的邀请码是：#B" + code);

                Daili one = GameData.that.baseDailiService.findOneByCode(code);
                if (one == null) {
                    Daili daili = new Daili();
                    daili.setAccount(code);
                    daili.setCode(code);
                    daili.setPasswd(code + "321");
                    GameData.that.baseDailiService.add(daili);
                }

                return;
            }

            String remark = "222";

            if (menu_item.equals("查询邀请金额")) {
                List<Charge> list = GameData.that.baseChargeService.findByCode(code);

                int coin = 0;
                for (int i = 0; i < list.size(); ++i) {
                    Charge item = list.get(i);
                    //	if (item.deleted != deleted  )
                    {
                        coin = coin + item.money;
                    }
                }
                GameUtil.changeNpcSession(npc, "您的邀请总额为：#R" + coin);

                return;
            }

            if (menu_item.equals("领取邀请奖励")) {
                List<Charge> list = GameData.that.baseChargeService.findByCode(code);

                int coin = 0;

                String s = "";
                for (int i = 0; i < list.size(); ++i) {
                    Charge item = list.get(i);

                    if (item.remark == null || item.remark.equals(remark) == false) {
                        coin = coin + item.money;
                        //item.setState(t);
                        item.remark = remark;
                        GameData.that.baseChargeService.updateById(item);
                    }

                }

                String s1 = String.valueOf(GameConfig.config.getBaseConfig().getYaoqingbili());

                //GameUtil.changeNpcSession(npc, "没奖励可领取"+s);
                if (coin > 0) {

                    int ling = (int) (coin * GameConfig.config.getBaseConfig().getYaoqingbili() / 100);
                    GameUtil.changeNpcSession(npc, "您未领取的邀请奖励为：#R" + ling);
                    Charge charge = new Charge();
                    final Accounts account = GameData.that.baseAccountsService.findById(gameObjectChar.accountid);
                    charge.setAccountname(account.name);
                    charge.setCoin(ling);
                    charge.setMoney(ling);
                    //  charge.setAccountname(name);

                    charge.setCode("");
                    charge.setState(0);
                    //charge.setType(1);
                    //charge.setState(t);
                    GameData.that.baseChargeService.add(charge);
                } else {
                    GameUtil.changeNpcSession(npc, "已经查询的奖励，请于一叶知秋处领取");
                }


                return;
            }

        }

        if (menu_item.equals("九层妖塔")) {
//			if (!GameAutoShuaguaiMng.belongCalendarFlush()) {
//				GameUtil.sendTips("活动时间未开放,开放时间为:#R" + GameAutoShuaguaiMng.getTime());
//				return;
//			}
            StringBuffer sb = new StringBuffer();
            sb.append("九层妖塔凶险层层,各位道友务必小心！\n");
            sb.append("请选择想要去的层数\n");
            sb.append("[九层妖塔一层/妖塔:0]");
            sb.append("[九层妖塔二层/妖塔:1]");
            sb.append("[九层妖塔三层/妖塔:2]");
            sb.append("[九层妖塔四层/妖塔:3]");
            sb.append("[九层妖塔五层/妖塔:4]");
            sb.append("[九层妖塔六层/妖塔:5]");
            sb.append("[九层妖塔七层/妖塔:6]");
            sb.append("[九层妖塔八层/妖塔:7]");
            sb.append("[九层妖塔九层/妖塔:8]");
            sb.append("[太恐怖了,还是不去了/离开]");
            //GameUtil.openNpcDialogue(1714, 6412, "秘境使者", sb.toString());

            Vo_MENU_LIST vo_8247_3 = new Vo_MENU_LIST();
            vo_8247_3.id = 1714;
            vo_8247_3.portrait = 6412;
            vo_8247_3.pic_no = 1;
            vo_8247_3.content = sb.toString();
            vo_8247_3.secret_key = "";
            vo_8247_3.name = "秘境使者";
            vo_8247_3.attrib = 0;
            GameObjectChar.send(new MSG_MENU_LIST(), vo_8247_3);

//			Npc npc = GameData.that.baseNpcService.findById(npc_id);
//			final Vo_8247_0 vo_8247_3 = new Vo_8247_0();
//			vo_8247_3.id = npc_id;
//			vo_8247_3.portrait = npc.getIcon();
//			vo_8247_3.pic_no = 1;
//			vo_8247_3.content = content;
//			vo_8247_3.secret_key = "";
//			vo_8247_3.name = npc.getName();
//			vo_8247_3.attrib = 1;
//			GameObjectChar.send(new M8247_0_MSG_MENU_LIST(), vo_8247_3);

            return;
        } else if (menu_item.indexOf("妖塔") != -1) {
            int yaotaI = Integer.parseInt(menu_item.split(":")[1]);
            String mapName = yaotas[yaotaI];
            int idx = getIdxForYaotaName(chara.mapName);
            if (idx - yaotaI > 1) {
                GameUtil.sendTips("不支持塔内跨层传送，请出塔后操作。");
                return;
            }
            GameMap gameMapname = GameLine.getGameMapname(chara.line, mapName);
            chara.x = gameMapname.x;
            chara.y = gameMapname.y;
            gameMapname.join(gameObjectChar);
            return;
        }

        //摘桃子
        if ("getTaoZi".equals(menu_item)) {
            //如果没有任务的话
            Vo_61553_0 taoziTask = chara.taskMap.get("萝卜桃子大收集");
            if (taoziTask == null) {
                GameUtil.sendMeTips("你还未参加萝卜桃子活动，请到#R天墉城#n处#Y千面怪#n领取任务！");
                return;
            }
            if (taoziTask.task_state.equals("success")) {
                GameUtil.sendMeTips("你已完成任务，快去千面怪处交任务吧！");
                return;
            }
            if (!taoziTask.currentTask.equals(chara.mapName)) {
                GameUtil.sendMeTips("请到正确地点摘桃子");
                return;
            }
            Vo_APPEAR taozi = GameCore.otherBoosMonster.remove(id);
            if (taozi != null && taozi.name.equals("桃树")) {
                if (chara.level < 70) {
                    GameUtil.sendMeTips("等级太低无法参与活动");
                    return;
                }
                taoziTask.task_prompt = "前往#P千面怪|E=提交萝卜桃子任务#P提交任务，第#R" + gameObjectChar.lbtzTaskCount + "#n次任务";
                taoziTask.task_state = "success";
                GameUtilRenWu.createTask(taoziTask, chara);
                //消失
                GameObjectCharMng.sendAllmap(new MSG_DISAPPEAR(), taozi.id, taozi.mapid);
                GameUtil.sendMeTips("成功摘取桃子，快去提交任务吧！");
            }

            int nextInt = ThreadLocalRandom.current().nextInt(100);
            String name = "";
            if (nextInt > 98) {
                // 大桃子和萝卜
                if (ThreadLocalRandom.current().nextBoolean()) {
                    GameUtil.sendMeTips("运气真好获得了1个大桃子");
                    name = "大桃子";
                } else {
                    GameUtil.sendMeTips("运气真好获得了1个大萝卜");
                    name = "大萝卜";
                }
            } else {
                // 小桃子和萝卜
                if (ThreadLocalRandom.current().nextBoolean()) {
                    GameUtil.sendMeTips("你获得了1个桃子");
                    name = "桃子";
                } else {
                    GameUtil.sendMeTips("你获得了1个萝卜");
                    name = "萝卜";
                }
            }
            GameUtil.huodedaoju(chara, name, 1);
            Vo_40964_0 vo_40964_21 = new Vo_40964_0();
            vo_40964_21.type = 1;
            vo_40964_21.name = name;
            vo_40964_21.param = "";
            vo_40964_21.rightNow = 0;
            GameObjectChar.send(new M40964_0(), vo_40964_21);
        }

        //桃子萝卜
        if ("taoziluo_kill_out".equals(menu_item)) {
            if (GameCore.fightObject.get(id) != null) {
                GameUtil.sendMeTips("没看见我正忙吗？");
                return;
            }
            Vo_APPEAR lieren = GameCore.otherBoosMonster.get(id);
            if (lieren != null && lieren.name.equals("猎人头领")) {
                if (chara.level < 70) {
                    GameUtil.sendMeTips("你低于70级，无法挑战");
                    return;
                }
                if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                    if (!GameCommonUtil.levelLessThanorEqualto(gameObjectChar, 70)) {
                        GameUtil.sendMeTips("队伍有成员低于70级，无法挑战");
                        return;
                    }
                }
                gameObjectChar.action = "luobotaozi";
                FightManager.goFightDynamicLevelByType(chara, Lists.newArrayList("猎人头领", "猎人喽喽", "猎人喽喽", "猎人喽喽", "猎人喽喽", "猎人喽喽", "猎人喽喽", "猎人喽喽", "猎人喽喽", "猎人喽喽"), "桃子萝卜", id);
            }

            return;
        }


        if (id == 961) {
            if ("【介绍】镖行万里".equals(menu_item)) {
                GameUtil.openDlg("TransportDartRuleDlg");
                return;
            }
        }

        //自定义弹窗
        if (menu_item.startsWith("ABOUT")) {
            String[] tipsArr = menu_item.split("\\$");
            String uid = tipsArr[1];
            ConfigInfo oneByUuid = GameData.that.configInfoService.getOneByUuid(uid);
            if (oneByUuid != null) {
                Vo_CONFIRM vo_45240_0 = new Vo_CONFIRM();
                vo_45240_0.tips = oneByUuid.getData().replace("#BR", "\n");
                vo_45240_0.down_count = 0;
                vo_45240_0.only_confirm = 0;
                vo_45240_0.confirm_type = "";
                vo_45240_0.confirmText = "知晓了";
                vo_45240_0.cancelText = "关闭";
                vo_45240_0.show_dlg_mode = 0;
                vo_45240_0.countDownTips = "";
                vo_45240_0.para_str = "{}";
                chara.currentConfirmItem = "";
                GameObjectChar.send(new MSG_CONFIRM(), vo_45240_0);
            }
            return;
        }

        //劫狱求情
        if ("jieyu_qiuqing".equals(menu_item)) {
            //如果没有任务
            Vo_61553_0 task = chara.taskMap.get("将功补过");
            if (task == null) {
                GameUtil.sendMeTips("我和你往日无怨近日无仇，为何找我呢？");
                return;
            }
            //查看是否和他的任务匹配的
            if (!task.flag.equals(chara.uuid)) {
                GameUtil.sendMeTips("虽然我长的帅，但也不能总点错啊");
                return;
            }
            GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(task.task_extra_para);
            if (gameObjectCharByUUid == null) {
                GameUtil.sendMeTips("被救人不在线");
                return;
            }
            List<String> names = new ArrayList<>();
            String[] nameArr = {"土匪", "强盗"};
            for (int i = 0; i < 10; i++) {
                names.add(nameArr[ThreadLocalRandom.current().nextInt(nameArr.length)]);
            }
            List<FightObject> fightObjects = FightManager.getFightDynamicLevelByType(chara, gameObjectCharByUUid.chara.level, names, "劫狱",
                    id);
            //这里随机给名字
            for (FightObject fight : fightObjects) {
                fight.str = Utils.getRandomName();
                fight.uid = "jieyu_qiuqing";
            }
            gameObjectChar.flag = "jieyu_qiuqing";
            FightManager.activeBoosGoFight(chara, fightObjects, false);
            return;
        }
        // 年兽活动
        if (id == 99999 && "fromUseFireworks".equals(gameObjectChar.flag)) {
            if ("exp".equals(menu_item)) {
                // 经验奖励
                FightManager.goFightDynamicLevelByType(chara, Lists.newArrayList("年兽", "年兽", "年兽", "年兽", "年兽"), "年兽活动",
                        id);
                gameObjectChar.flag = "newYearBeastExp";
            } else if ("tao".equals(menu_item)) {
                // 道行奖励
                FightManager.goFightDynamicLevelByType(chara, Lists.newArrayList("年兽", "年兽", "年兽", "年兽", "年兽"), "年兽活动",
                        id);
                gameObjectChar.flag = "newYearBeastTao";
            }
            return;
        }

        if (npc != null) {
            //千面怪
            if (id == 982) {
                if ("changePolar".equals(menu_item)) {
                    if (chara.level < 70) {
                        GameUtil.sendMeTips("等级低于70无法转换");
                        return;
                    }
                    if (chara.taskMap.get("门派转换") == null) {
                        GameUtil.sendMeTips("你还未领取门派转换任务");
                        return;
                    }
                    //判断是否脱下武器
                    for (Goods goods : chara.getOtherGoods()) {
                        if (goods.pos == 1) {
                            GameUtil.sendMeTips("门派转换不允许携带武器");
                            return;
                        }
                    }
                    List<String> polar = Lists.newArrayList("[五龙山云霄洞/changeSwitchPolar_1]", "[终南山玉柱洞/changeSwitchPolar_2]", "[凤凰山斗阙宫/changeSwitchPolar_3]", "[乾元山金光洞/changeSwitchPolar_4]", "[骷髅山白骨洞/changeSwitchPolar_5]");
                    //原门派
                    int oldPolar = chara.polar;
                    polar.remove(oldPolar - 1);
                    StringBuilder m = new StringBuilder();
                    for (String p : polar) {
                        m.append(p);
                    }
                    GameUtil.changeNpcSession(npc, "请选择需要转换门派，转换后门派技能会清0，需重新学习新门派技能" + m.toString());
                    return;
                } else if (menu_item.startsWith("changeSwitchPolar")) {//确定选择门派
                    if (chara.upgrade_state != 0) {
                        GameUtil.sendMeTips("请切换真身在转门派");
                        return;
                    }
                    if (chara.level < 70) {
                        GameUtil.sendMeTips("等级低于70无法转换");
                        return;
                    }
                    if (chara.taskMap.get("门派转换") == null) {
                        GameUtil.sendMeTips("你还未领取门派转换任务");
                        return;
                    }
                    //判断是否脱下武器
                    for (Goods goods : chara.getOtherGoods()) {
                        if (goods.pos == 1) {
                            GameUtil.sendMeTips("门派转换不允许携带武器");
                            return;
                        }
                    }
                    //判断娃娃是否携带武器
                    if (chara.charaYuanyingInfo.equip.get(1) != null
                            || chara.charaYuanyingInfo.equip.get(11) != null) {
                        GameUtil.sendMeTips("门派转换不允许娃娃携带武器");
                        return;
                    }
                    //新的门派
                    int newPolar = Integer.valueOf(menu_item.split("_")[1]);
                    if (newPolar == chara.polar) {
                        GameUtil.sendMeTips("不能选择原门派");
                        return;
                    }
                    List<String> polar = Lists.newArrayList("五龙山云霄洞", "终南山玉柱洞", "凤凰山斗阙宫", "乾元山金光洞", "骷髅山白骨洞");
                    //弹出提示
                    gameObjectChar.confirmData = newPolar;
                    GameUtil.confirm(chara, org.apache.commons.lang3.StringUtils.join("转换成功会重新上下线，确定消耗#R2张#Y改后换面卡#n转换到新门派#R", polar.get(newPolar - 1), "#n吗？"), "acceptChangePolar");
                    return;
                } else if ("萝卜桃子大收集_tijlb".equals(menu_item)) {
                    int count = 0;
                    count += GameCommonUtil.getGoodsNum(chara, "大萝卜");
                    count += GameCommonUtil.getGoodsNum(chara, "萝卜");
                    if (count <= 0) {
                        GameUtil.sendMeTips("暂未发现萝卜");
                        return;
                    }
                    gameObjectChar.confirmData = count;
                    GameUtil.confirm(chara, org.apache.commons.lang3.StringUtils.join("你目前尚有#R", count, "#n棵萝卜确定提交吗?"), "luobo");
                    return;
                } else if ("萝卜桃子大收集_tijtz".equals(menu_item)) {
                    int count = 0;
                    count += GameCommonUtil.getGoodsNum(chara, "大桃子");
                    count += GameCommonUtil.getGoodsNum(chara, "桃子");
                    if (count <= 0) {
                        GameUtil.sendMeTips("暂未发现桃子");
                        return;
                    }
                    gameObjectChar.confirmData = count;
                    GameUtil.confirm(chara, org.apache.commons.lang3.StringUtils.join("你目前尚有#R", count, "#n个桃子确定提交吗?"), "taozi");
                    return;
                } else if ("getLuoBoTaiZiTask".equals(menu_item) || "submitLuoBoTaiZiTask".equals(menu_item)) {
                    if (GameConfig.taoziLuoboStatus == 1) {
                        if ("getLuoBoTaiZiTask".equals(menu_item)) {
                            //领取任务
                            Vo_61553_0 taoziTask = chara.taskMap.get("萝卜桃子大收集");
                            //如果任务存在是无法领取的
                            if (taoziTask != null) {
                                return;
                            }
                            GameUtil.sendMeTips("成功摘取桃子，快去提交任务吧！");
                        } else if ("submitLuoBoTaiZiTask".equals(menu_item)) {
                            //提交任务
                            Vo_61553_0 taoziTask = chara.taskMap.get("萝卜桃子大收集");
                            //如果任务为空或者是状态不对
                            if (taoziTask == null || !taoziTask.task_state.equals("success")) {
                                return;
                            }
                            gameObjectChar.lbtzTaskCount += 1;
                            gameObjectChar.lbtzTaskTime = System.currentTimeMillis();
                            GameUtil.sendMeTips("任务提交成功");
                        }
                        //创建任务
                        Renwu renwu = new Renwu();
                        renwu.setShowName("萝卜桃子大收集");
                        renwu.setAttrib(1);
                        renwu.setTaskDesc("又一批桃子和萝卜成熟了，#Y千面怪#n开办了萝卜桃子活动，在#R天墉城#Y千面怪#n即可参加活动，活动期间会不定时刷新猎人，道友们可组队前往#R桃柳林#n或#R官道北#n驱赶，驱赶成功会随机得到大桃子(道行)和大萝卜(经验)");
                        renwu.setTaskState("start");
                        renwu.setShowReward("#I道行|道行#I#I经验|经验#I#I物品|桃子#I#I物品|萝卜#I");
                        renwu.setTaskEndTime((int) (System.currentTimeMillis() / 1000L));
                        renwu.setTaskPrompt("前往#Z%s#Z摘取桃子，第#R" + gameObjectChar.lbtzTaskCount + "#n次任务");
                        if (ThreadLocalRandom.current().nextBoolean()) {
                            renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), "桃柳林"));
                            renwu.setCurrentTask("桃柳林");
                        } else {
                            renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), "官道北"));
                            renwu.setCurrentTask("官道北");
                        }
                        GameUtilRenWu.createTask(chara, renwu);
                        //把参与的人添加到这里面
                        GameCore.luoboTaoziCids.add(chara.id);
                    }
                    return;
                }
            }
            //VIP系统
            if (id == 1678) {
                if ("vipChargeFuLi".equals(menu_item)) {
                    if (chara.isGetChargeFuLi > 0) {
                        GameUtil.changeNpcSession(npc, "你今日已领取福利,请明日再来。[离开]");
                        return;
                    }
                    //判断充值
                    List<Charge> chargeList = GameData.that.baseChargeService
                            .findByAccountname(gameObjectChar.account.getName());
                    int sum = chargeList.stream().mapToInt(Charge::getMoney).sum();
                    //数据库查询
                    ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("VIP_FULI_CONFIG");
                    List<VipChargeConfig> vipConfigs = com.alibaba.fastjson.JSONObject.parseArray(configInfo.getData(), VipChargeConfig.class);
                    vipConfigs.sort(new Comparator<VipChargeConfig>() {
                        @Override
                        public int compare(VipChargeConfig o1, VipChargeConfig o2) {
                            return o1.getMinMoney().compareTo(o2.getMinMoney());
                        }
                    });
                    for (VipChargeConfig vipConfig : vipConfigs) {
                        //如果满足要求
                        if (sum >= vipConfig.getMinMoney() && sum <= vipConfig.getMaxMoney()) {
                            //解析数据
                            Reward reward = vipConfig.getReward();
                            //任务次数
                            List<RewardInfo> tasks = reward.getTask();
                            StringBuffer msg = new StringBuffer();
                            msg.append("成功领取福利,");
                            for (RewardInfo task : tasks) {
                                if ("shanggucishu".equals(task.getName())) {
                                    chara.shanggucishu -= task.getNum();
                                    msg.append("上古次数上限增加").append("#R").append(task.getNum()).append("#n次,");
                                } else if ("tiandixingNum".equals(task.getName())) {
                                    chara.tiandixingNum -= task.getNum();
                                    msg.append("天地星次数上限增加").append("#R").append(task.getNum()).append("#n次,");
                                }
                                CMD_SELECT_MENU_ITEM.refreshTask(chara);
                            }
                            //奖励类型
                            List<RewardInfo> values = reward.getValue();
                            for (RewardInfo task : values) {
                                if ("积分".equals(task.getName())) {
                                    GameUtil.addchargeScore(gameObjectChar, task.getNum(), "VIP系统");
                                    msg.append("获得了#R").append("#R").append(task.getNum()).append("#n充值积分");
                                } else if ("金元宝".equals(task.getName())) {
                                    chara.goldCoin += task.getNum();
                                } else if ("充值好礼".equals(task.getName())) {
                                    chara.shadow_self += task.getNum();
                                }
                            }
                            chara.isGetChargeFuLi += 1;
                            GameUtil.sendMeTips(msg.toString());
                            GameUtil.sendUpdate(chara);
                            return;
                        }
                    }
                    GameUtil.sendMeTips("条件不满足");
                }
                return;
            }
            //固定队
            if (id == 868) {
                if ("fixedTeam".equals(menu_item)) {
                    //如果没有队伍
                    if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                        GameUtil.changeNpcSession(npc, "施主找谁结成固定队，请带他们来见贫僧好吗？[离开]");
                        return;
                    } else if (gameObjectChar.gameTeam.duiwu.size() < 2) {
                        GameUtil.changeNpcSession(npc, "施主找谁结成固定队，请带他们来见贫僧好吗？[离开]");
                        return;
                    } else {
                        if (!StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
                            Example example = new Example(FixedTeam.class);
                            example.createCriteria().andEqualTo("uid", chara.fixedTeamName);
                            FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
                            if (!fixedTeam.getLeaderUid().equals(chara.uuid)) {
                                GameUtil.sendMeTips("只有固定队长才可邀请人员加入固定队！");
                                return;
                            }
                        }
                        //打开固定队伍界面
                        for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
                            if (teamChara.id != chara.id) {
                                //如果某个人有固定队
                                if (!StringUtils.isNullOrEmpty(teamChara.fixedTeamName)) {
                                    GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("#Y", teamChara.name, "#n已有固定队"));
                                    return;
                                }
                            }
                        }
                        for (Chara teamChara : gameObjectChar.gameTeam.duiwu) {
                            GameObjectCharMng.getGameObjectChar(teamChara.id).sendOne(new MSG_FIXED_TEAM_START_DATA(), null);
                        }
                    }
                } else if ("quitFixedTeam".equals(menu_item)) {
                    //退出固定队
                    if (StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
                        GameUtil.changeNpcSession(npc, "施主你还没有结缔固定队呢？[离开]");
                        return;
                    }
                    //如果是队长
                    Example example = new Example(FixedTeam.class);
                    example.createCriteria().andEqualTo("uid", chara.fixedTeamName);
                    FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
                    if (fixedTeam != null && fixedTeam.getLeaderUid().equals(chara.uuid)) {
                        //队长退出固定队
                        gameObjectChar.confirmData = fixedTeam;
                        GameUtil.confirm(chara, "施主，队长退出固定队后整个队伍会解散，确定吗？", "leaderQuitFixedTeam");
                    } else {
                        //弹出确认框
                        gameObjectChar.confirmData = fixedTeam;
                        GameUtil.confirm(chara, "施主，是否确定退出固定队吗？", "quitFixedTeam");
                    }
                }
                return;
            }
            //监狱牢头
            if (id == 1015) {
                if ("zuolao_info".equals(menu_item)) {
                    List<Vo_ZUOLAO_INFO> infos = new ArrayList<>();
                    for (GameObjectChar all : GameObjectCharMng.getAll()) {
                        if (all.chara.crimeTime > 0 && all.chara.taskMap.get("坐牢") != null) {
                            Vo_ZUOLAO_INFO vo_ZUOLAO_INFO = new Vo_ZUOLAO_INFO(all.chara);
                            vo_ZUOLAO_INFO.setServerName(GameConfig.lineName + 1 + "线");
                            infos.add(vo_ZUOLAO_INFO);
                        }
                    }
                    GameUtil.openDlg("PrisonDlg");
                    gameObjectChar.sendOne(new MSG_ZUOLAO_INFO(), infos);
                    gameObjectChar.sendOne(new MSG_ZUOLAO_INFO_FINISH(), null);
                }
                return;
            }
            //管神工
            if (id == 968) {
                if ("upgrade_equip".equals(menu_item)) {
                    StringBuilder content = new StringBuilder("请选择要改造的装备");
                    for (Goods goods : chara.otherGoods) {
                        if (goods.pos >= 1 && goods.pos <= 3 && goods.goodsInfo.color >= 8 && goods.goodsInfo.color < 12) {
                            content.append("[").append(goods.goodsInfo.str).append("(").append(goods.goodsInfo.quality).append(":").append(goods.goodsInfo.color).append(")/open_switch_upgrade_level_").append(goods.goodsInfo.auto_fight).append("]");
                        }
                        if (goods.pos == 10 && goods.goodsInfo.color >= 8 && goods.goodsInfo.color < 12) {
                            content.append("[").append(goods.goodsInfo.str).append("(").append(goods.goodsInfo.quality).append(":").append(goods.goodsInfo.color).append(")/open_switch_upgrade_level_").append(goods.goodsInfo.auto_fight).append("]");
                        }
                    }
                    content.append("[离开]");
                    GameUtil.changeNpcSession(npc, content.toString());
                    gameObjectChar.flag = "step2";
                    return;
                } else if (menu_item.startsWith("open_switch_upgrade_level_") &&
                        "step2".equals(gameObjectChar.flag)) {
                    String iid = menu_item.replace("open_switch_upgrade_level_", "");
                    //加载菜单
                    StringBuilder content = new StringBuilder("只能一级级升上去无法进行跳级，请选择等级，当前选中改造(");
                    for (Goods goods : chara.otherGoods) {
                        if (goods.goodsInfo.auto_fight.equals(iid)) {
                            content.append(goods.goodsInfo.str).append(":").append(goods.goodsInfo.color).append(")");
                        }
                    }
                    content.append("[").append(8).append("-").append(9)
                            .append("(").append(fastUpgradeEquipStr[8 - 1]).append(")/equip_upgrade_tolevel8]");

                    content.append("[").append(9).append("-").append(10)
                            .append("(").append(fastUpgradeEquipStr[9 - 1]).append(")/equip_upgrade_tolevel9]");

                    content.append("[").append(10).append("-").append(11)
                            .append("(").append(fastUpgradeEquipStr[10 - 1]).append(")/equip_upgrade_tolevel10]");

                    content.append("[").append(11).append("-").append(12)
                            .append("(").append(fastUpgradeEquipStr[11 - 1]).append(")/equip_upgrade_tolevel11]");
                    gameObjectChar.flag = "step3";
                    gameObjectChar.confirmData = iid;
                    GameUtil.changeNpcSession(npc, content.toString());
                    return;
                } else if (menu_item.startsWith("equip_upgrade_tolevel") &&
                        "step3".equals(gameObjectChar.flag)) {
                    String iid = (String) gameObjectChar.confirmData;
                    //需要升级到哪个等级
                    int equipLevel = Integer.valueOf(menu_item.replace("equip_upgrade_tolevel", ""));
                    for (Goods goods : chara.otherGoods) {
                        if (goods.goodsInfo.auto_fight.equals(iid)) {
                            //如果装备等于12级就直接返回
                            if (goods.goodsInfo.color >= 12) {
                                GameUtil.sendMeTips("装备改造等级已达极限");
                                return;
                            }
                            //判断是否满足条件
                            if (equipLevel != goods.goodsInfo.color) {
                                GameUtil.sendMeTips("请选择正确的升级等级");
                                return;
                            }
                            //判断元宝是否充足
                            int goldCoin = fastUpgradeEquip[goods.goodsInfo.color - 1];
                            int price = goldCoin;
                            if (chara.goldCoin < price) {
                                GameUtil.sendMeTips("金元宝不足无法完成升级");
                                return;
                            }
                            //开始对装备进行升级
                            String str = null;
                            Map<Object, Object> goodsGaiZai = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
                            // 获取改造的值
                            goods.goodsInfo.store_exp = 0;
                            for (Map.Entry<Object, Object> entry2 : goodsGaiZai.entrySet()) {
                                if (!entry2.getKey().equals("groupNo")) {
                                    if (entry2.getKey().equals("groupType")) {
                                        continue;
                                    }
                                    if (entry2.getValue().toString().equals("0")) {
                                        continue;
                                    }
                                    str = (String) entry2.getKey();
                                }
                            }
                            // 改造共鸣中文名
                            String equipmentKeyByName = ForgingEquipmentUtils.getEquipmentKeyByName(str, false);
                            List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment(
                                    equipmentKeyByName, goods.goodsInfo.amount, goods.goodsInfo.attrib,
                                    goods.goodsInfo.color + 1);
                            for (Hashtable<String, Integer> maps2 : hashtables2) {
                                if (equipmentKeyByName != null) {
                                    if (maps2.get("groupNo") == 27) {
                                        maps2.put("groupType", 2);
                                        GoodsGaiZaoGongMing goodsGaiZaoGongMing2 = com.alibaba.fastjson.JSONObject
                                                .parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps2), GoodsGaiZaoGongMing.class);
                                        goods.goodsGaiZaoGongMing = goodsGaiZaoGongMing2;
                                    }
                                }
                                if (maps2.get("groupNo") == 10) {
                                    maps2.put("groupType", 2);
                                    GoodsGaiZao goodsGaiZao = com.alibaba.fastjson.JSONObject.parseObject(com.alibaba.fastjson.JSONObject.toJSONString(maps2),
                                            GoodsGaiZao.class);
                                    goods.goodsGaiZao = goodsGaiZao;
                                }
                            }
                            GoodsInfo goodsInfo = goods.goodsInfo;
                            ++goodsInfo.color;
                            GameObjectChar.send(new M32775_0(), goods);
                            List<Goods> listgood = new ArrayList<Goods>();
                            listgood.add(goods);
                            GameObjectChar.send(new M65525_0(), listgood);

                            // 增加完美度
                            goods.goodsInfo.dunwu_times = (int) (goodsInfo.color * 4.3 * 100);
                            if (goods.goodsInfo.dunwu_times >= (100 * 100)) {
                                goods.goodsInfo.dunwu_times = (int) (99.99 * 100);
                            }
                            //扣除元宝
                            chara.goldCoin -= goldCoin;
                            GameUtil.a65511(gameObjectChar);
                            GameUtil.sendMeTips("你消费了#R" + goldCoin + "金元宝#n将#Y" + goods.goodsInfo.str + "#n一键提升到了#R" + goodsInfo.color + "#n级");
                            break;
                        }
                    }
                    return;
                }
            }
            // 提亲-月老
            if (id == 890) {
                if ("请您为我们举办豪华婚礼吧".equals(menu_item)) {
                    Vo_61553_0 tiqin = chara.taskMap.get("提亲");
                    if (tiqin != null && tiqin.currentTask.equals("提亲s1")) {
                        GameObjectChar womenGameObjectChar = GameObjectCharMng
                                .getGameObjectCharByUUid(tiqin.task_extra_para);
                        if (womenGameObjectChar == null) {
                            GameUtil.sendMeTips("对方不在线！");
                        } else {
                            if (tiqin == null || chara.sex != 1
                                    || !GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                                    || gameObjectChar.gameTeam.duiwu.size() != 2
                                    || !gameObjectChar.gameTeam.duiwu.get(1).uuid.equals(tiqin.task_extra_para)) {
                                // 增加提亲菜单
                                GameUtil.changeNpcSession(id, 6060, "月老", "前往#R天墉城#n找#Y红娘#n完成提亲任务再来找我吧！[离开]");
                                return;
                            }
                            GameUtil.openDlg("WeddingListChoseDlg");
                            // 如果没结婚,结了婚只是购买一些特效
                            if (chara.marriageMarryId == 0) {
                                MarryUtil.jiehun(gameObjectChar, id);
                            }
                        }
                    } else {
                        // 已经结婚了,那就打开礼单选择
                        if (chara.marriageMarryId != 0) {
                            if (chara.sex == 2) {
                                GameUtil.changeNpcSession(id, 6060, "月老", "举行婚礼这种事情，还是由男方来操办，较为妥当！[离开]");
                                return;
                            } else if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                                    && gameObjectChar.gameTeam.duiwu.size() != 2) {
                                GameUtil.changeNpcSession(id, 6060, "月老", "举行婚礼这种事情，必须得成双成对男女搭配！[离开]");
                                return;
                            } else if (chara.sex != 1 || !GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                                    || gameObjectChar.gameTeam.duiwu.size() != 2
                                    || gameObjectChar.gameTeam.duiwu.get(1).marriageMarryId != chara.id) {
                                GameUtil.changeNpcSession(id, 6060, "月老", "请带上你的另一半在来举行婚礼哦！[离开]");
                                return;
                            } else {
                                // 打开礼单
                                GameUtil.openDlg("WeddingListChoseDlg");
                            }
                        } else {
                            // 没有结婚
                            if (chara.sex == 2) {
                                GameUtil.changeNpcSession(id, 6060, "月老", "举行婚礼这种事情，还是由男方来操办，较为妥当！[离开]");
                                return;
                            } else if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                                    && gameObjectChar.gameTeam.duiwu.size() != 2) {
                                GameUtil.changeNpcSession(id, 6060, "月老", "举行婚礼这种事情，必须得成双成对男女搭配！[离开]");
                                return;
                            } else if (chara.sex != 1 || !GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                                    || gameObjectChar.gameTeam.duiwu.size() != 2
                                    || gameObjectChar.gameTeam.duiwu.get(1).marriageMarryId != chara.id) {
                                GameUtil.changeNpcSession(id, 6060, "月老", "请带上你的另一半在来举行婚礼哦！[离开]");
                                return;
                            } else {
                                // 打开礼单
                                GameUtil.openDlg("WeddingListChoseDlg");
                            }
                        }
                    }
                } else if ("我们不想太招摇".equals(menu_item)) {
                    // 直接结婚
                    if (chara.sex == 2) {
                        GameUtil.changeNpcSession(id, 6060, "月老", "举行婚礼这种事情，还是由男方来操办，较为妥当！[离开]");
                        return;
                    } else if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                            && gameObjectChar.gameTeam.duiwu.size() != 2) {
                        GameUtil.changeNpcSession(id, 6060, "月老", "举行婚礼这种事情，必须得成双成对男女搭配！[离开]");
                        return;
                    } else {
                        if (chara.marriageMarryId == 0) {
                            MarryUtil.jiehun(gameObjectChar, id);
                        } else {
                            GameUtil.changeNpcSession(id, 6060, "月老", "你俩已经结婚了，如想补办婚礼请选择#R举办豪华婚礼#n[离开]");
                            return;
                        }
                    }
                } else if ("我们是来举办婚礼的".equals(menu_item)) {
                    GameUtil.changeNpcSession(id, 6060, "月老",
                            "恭喜两位，贺喜两位，千里姻缘一线牵，两位情定于此，实在是天作之合呀！[请您为我们举办豪华婚礼吧][我们不想太招摇][我们再考虑考虑吧]");
                } else if ("预定婚礼时间".equals(menu_item)) {
                    GameUtil.changeNpcSession(id, 6060, "月老",
                            "每对新人都会在我这里预定举办婚礼的时间，每场婚礼大概需要#R4分钟#n，如果当前有人正在举办婚礼，则只能预约当天下一场婚礼[预定举办婚礼的时间][我们再考虑考虑吧]");
                } else if ("预定举办婚礼的时间".equals(menu_item)) {
                    if (chara.sex != 1 || !GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                            || gameObjectChar.gameTeam.duiwu.size() != 2
                            || gameObjectChar.gameTeam.duiwu.get(1).marriageMarryId != chara.id) {
                        GameUtil.changeNpcSession(id, 6060, "月老", "请带上你的另一半在来预定婚礼哦！[离开]");
                        return;
                    } else {
                        if (!MarryUtil.teams.isEmpty()) {
                            GameUtil.changeNpcSession(id, 6060, "月老", "当前已有人在举行婚礼，请稍后[离开]");
                        } else if (chara.taskMap.get("预定婚礼") == null) {
                            GameUtil.changeNpcSession(id, 6060, "月老", "你还未预定呢？[离开]");
                        } else {
                            String weddinglist = chara.taskMap.get("预定婚礼").task_state;
                            String[] split = weddinglist.split(";");
                            // 查询出价格
                            Example example = new Example(WeddingList.class);
                            example.createCriteria().andIn("name", Lists.newArrayList(split));
                            WeddingListService weddingListService = SpringBeanUtils.getBean(WeddingListService.class);
                            List<WeddingList> selectByExample = weddingListService.selectByExample(example);
                            // 开始婚礼
                            GameUtil.sendMeTips("婚礼正式开始。");
                            new MarryUtil(selectByExample, System.currentTimeMillis()).startMarry(gameObjectChar);
                        }
                    }
                } else if ("lh".equals(menu_item)) {
                    if (chara.marriageMarryId == 0) {
                        GameUtil.changeNpcSession(id, 6060, "月老", "你的另一半在哪里呢？[离开]");
                        return;
                    } else if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                            && gameObjectChar.gameTeam.duiwu.size() != 2) {
                        GameUtil.changeNpcSession(id, 6060, "月老", "离婚需要双方在场！[离开]");
                        return;
                    } else if (gameObjectChar.gameTeam.duiwu.size() != 2
                            || gameObjectChar.gameTeam.duiwu.get(1).marriageMarryId != chara.id) {
                        GameUtil.changeNpcSession(id, 6060, "月老", "请带上你的另一半[离开]");
                        return;
                    }
                    GameUtil.confirm(chara, "你确定离婚吗？", "lh", 30);
                    //满足条件
                } else if ("qzlh".equals(menu_item)) {
                    //强制离婚需要扣除好友度
                    GameUtil.confirm(chara, "强制离婚后双方好友度将变为#R0#n，确定还要强制离婚吗？", "qzlh", 30);
                }
                return;
            }
            // 提亲-红娘
            if (id == 986) {
                // 点击提亲按钮
                if ("tiqin_dispatch".equals(menu_item)) {
                    if (gameObjectChar.chara.sex != 1) {
                        GameUtil.changeNpcSession(npc, "提亲这种事情，女孩子怎么好太过主动呢？[离开]");
                    } else if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                        GameUtil.changeNpcSession(npc, "想提亲吗？想的话得把你的心上人一起带来。[离开]");
                    } else if (gameObjectChar.gameTeam.duiwu.size() > 2) {
                        GameUtil.changeNpcSession(npc, "想提亲吗？必须成双成对。[离开]");
                    } else if (gameObjectChar.gameTeam.duiwu.size() == 1) {
                        GameUtil.changeNpcSession(npc, "想提亲吗？想的话得把你的心上人一起带来。[离开]");
                    } else if (gameObjectChar.gameTeam.duiwu.get(1).sex != 2) {
                        GameUtil.changeNpcSession(npc, "想提亲吗？必须成双成对。[离开]");
                    } else if (!GameUtil.duiwudengji40(chara, gameObjectChar)) {
                        GameUtil.changeNpcSession(npc, "道友还是先提升下等级吧，结婚最低40级。[离开]");
                    } else if (chara.taskMap.get("提亲") != null) {
                        GameUtil.changeNpcSession(npc, "你已有提亲任务，请勿重复领取！[离开]");
                    } else {
                        if (chara.getMarriageMarryId() != 0) {
                            GameUtil.changeNpcSession(npc, "你已婚了！[离开]");
                            return;
                        }
                        /**
                         * 好友度是否达标
                         */
                        // 女方
                        Example example = new Example(Friend.class);
                        example.createCriteria().andEqualTo("friendGid", gameObjectChar.gameTeam.duiwu.get(1).uuid)
                                .andEqualTo("gid", chara.uuid);
                        Friend women = GameData.that.friendService.selectOneByExample(example);
                        // 男方
                        example = new Example(Friend.class);
                        example.createCriteria().andEqualTo("friendGid", chara.uuid).andEqualTo("gid",
                                gameObjectChar.gameTeam.duiwu.get(1).uuid);
                        Friend man = GameData.that.friendService.selectOneByExample(example);
                        if (women == null || man == null) {
                            GameUtil.changeNpcSession(npc, "双方还不是好友！[离开]");
                        } else if (women.getFriendScore() < 5000 || man.getFriendScore() < 5000) {
                            GameUtil.changeNpcSession(npc, "双方友好度不足5000，可以做任务或者赠送友好度道具来增加友好度。[离开]");
                        } else {
                            // 打开提亲界面
                            GameObjectChar womenGameObjectChar = GameObjectCharMng
                                    .getGameObjectChar(gameObjectChar.gameTeam.duiwu.get(1).id);
                            Chara womenChara = womenGameObjectChar.chara;
                            Vo_OPEN_TIQIN_DLG womenInfo = new Vo_OPEN_TIQIN_DLG();
                            womenInfo.setGender(2);
                            womenInfo.setName(womenChara.name);
                            womenInfo.setOrgIcon(womenChara.waiguan);
                            womenInfo.setWeaponIcon(womenChara.weapon_icon);
                            womenInfo.setUpgrageType(womenChara.upgrade_type);
                            womenInfo.setLightEffects(new ArrayList<Integer>(womenChara.getEffectIcons().values()));
                            womenInfo.setSuitIcon(womenChara.suit_icon);
                            Vo_OPEN_TIQIN_DLG manInfo = new Vo_OPEN_TIQIN_DLG();
                            manInfo.setGender(1);
                            manInfo.setName(chara.name);
                            manInfo.setOrgIcon(chara.waiguan);
                            manInfo.setWeaponIcon(chara.weapon_icon);
                            manInfo.setUpgrageType(chara.upgrade_type);
                            manInfo.setLightEffects(new ArrayList<Integer>(chara.getEffectIcons().values()));
                            manInfo.setSuitIcon(chara.suit_icon);
                            // 发送结婚消息
                            gameObjectChar.sendOne(new MSG_OPEN_TIQIN_DLG(), Lists.newArrayList(womenInfo, manInfo));
                            womenGameObjectChar.sendOne(new MSG_OPEN_TIQIN_DLG(),
                                    Lists.newArrayList(womenInfo, manInfo));
                            // 设置信息
                            gameObjectChar.receiverId = womenChara.id;
                            womenGameObjectChar.receiverId = chara.id;
                        }
                    }
                }
                return;
            }

            // 擂台管理员
            if (id == 987) {
                // 开启挑战列表
                if ("open_search_targets_dialog".equals(menu_item)) {
                    List<GameObjectChar> all = GameObjectCharMng.getAll();
                    List<GameObjectChar> onLeiTai = new ArrayList<>();
                    for (GameObjectChar g : all) {
                        if (CompeteTournamentUtils.onLeitai(g.chara.x, g.chara.y)) {
                            onLeiTai.add(g);
                        }
                    }
                    Map<String, Object> data = new HashMap<>();
                    data.put("ct_data/top_rank", chara.ctDataTopRank);
                    data.put("ct_data/score", chara.ctDataScore);
                    gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));

                    gameObjectChar.sendOne(new MSG_COMPETE_TOURNAMENT_TARGETS(), onLeiTai);
                    return;
                }
                // 擂台信息
                if ("open_compete_tournament_dialog".equals(menu_item)) {
                    return;
                }

                if ("leitai_shop".equals(menu_item)) {
                    List<RareShopItem> rareShopItems = GameData.that.rareShopItemService.selectAll();
                    List<Vo_RARE_SHOP_ITEMS_INFO> vos = new ArrayList<>();
                    for (RareShopItem r : rareShopItems) {
                        vos.add(new Vo_RARE_SHOP_ITEMS_INFO(r.getBarcode(), r.getName(), r.getCost(), r.getNum()));
                    }
                    gameObjectChar.sendOne(new MSG_RARE_SHOP_ITEMS_INFO(), vos);
                }
            }

            // 升级奖励大使礼包
            if (id == 1669) {
//				// 获取70级极品法套
//				if ("getJpMetal70".equals(menu_item)) {
//					if (chara.levelUpReward[0] == 1) {
//						GameUtil.sendMeTips("你已领取过该奖励！");
//						return;
//					}
//					chara.levelUpReward[0] = 1;
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipMetalWuQi(chara, 70, 7), gameObjectChar);
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipMetalOther(chara, 70, 7, 2),
//							gameObjectChar);
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipMetalOther(chara, 70, 7, 3),
//							gameObjectChar);
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipMetalOther(chara, 70, 7, 10),
//							gameObjectChar);
//					GameUtil.sendMeTips("恭喜你获得极品法伤套装一套");
//					return;
//				} else if ("getJpEarth70".equals(menu_item)) {
//					if (chara.levelUpReward[0] == 1) {
//						GameUtil.sendMeTips("你已领取过该奖励！");
//						return;
//					}
//					chara.levelUpReward[0] = 1;
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipEarthWuQi(chara, 70, 7), gameObjectChar);
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipEarthOther(chara, 70, 7, 2),
//							gameObjectChar);
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipEarthOther(chara, 70, 7, 3),
//							gameObjectChar);
//					GameCommonUtil.addGoodsToBackpack(GameCommonUtil.getJpEquipEarthOther(chara, 70, 7, 10),
//							gameObjectChar);
//					GameUtil.sendMeTips("恭喜你获得极品物伤套装一套");
//					return;
//				} else {
//					if(menu_item.equals("离开")) {
//						return;
//					}
//					// 查询等级是否达到要求
//					if (chara.level < Integer.valueOf(menu_item)) {
//						GameUtil.sendMeTips("还未达到等级要求！");
//						return;
//					} else {
//						// 70级满属性装备
//						if ("70".equals(menu_item)) {
//							// 弹出领取第二页菜单
//							GameUtil.changeNpcSession(npc,
//									"亲爱的道友我这里可以领取升级奖励哦，每个奖励只能领取一次。[法术伤害套装/getJpMetal70][物理伤害套装/getJpEarth70]");
//							return;
//						} else if ("80".equals(menu_item)) {
//							if (chara.levelUpReward[1] == 1) {
//								GameUtil.sendMeTips("你已领取过该奖励！");
//								return;
//							}
//							chara.levelUpReward[1] = 1;
//							// 领取所有相5收拾
//							GameUtil.jifendengjishoushi(chara, new String[] { "七龙珠" });
//							GameUtil.sendMeTips("恭喜你获得所有相五属性手镯#R七龙珠");
//							return;
//						} else if ("90".equals(menu_item)) {
//							if (chara.levelUpReward[2] == 1) {
//								GameUtil.sendMeTips("你已领取过该奖励！");
//								return;
//							}
//							chara.levelUpReward[2] = 1;
//							Goods goods = new Goods();
//							goods.goodsInfo = new GoodsInfo();
//							goods.goodsInfo.str = "魂器·鬼步";
//							goods.goodsInfo.attrib = 75;
//							GameCommonUtil.getRandomAllAttrHunQi(gameObjectChar, goods);
//							return;
//						} else if ("100".equals(menu_item)) {
//							if (chara.levelUpReward[3] == 1) {
//								GameUtil.sendMeTips("你已领取过该奖励！");
//								return;
//							}
//							chara.levelUpReward[3] = 1;
//							// 6介北极熊
//							GameUtil.huodezuoqi(chara, "北极熊", 6, "升级奖励大使");
//							return;
//						} else if ("110".equals(menu_item)) {
//							if (chara.levelUpReward[4] == 1) {
//								GameUtil.sendMeTips("你已领取过该奖励！");
//								return;
//							}
//							chara.levelUpReward[4] = 1;
//							GameUtil.huodefabao(chara, "定海珠", 12, "升级奖励大使");
//							return;
//						} else if ("120".equals(menu_item)) {
//							if (chara.levelUpReward[5] == 1) {
//								GameUtil.sendMeTips("你已领取过该奖励！");
//								return;
//							}
//							chara.levelUpReward[5] = 1;
//							GameUtil.getShouShiAllAttr(chara, "九天霜华");
//							GameUtil.getShouShiAllAttr(chara, "九天霜华");
//							GameUtil.getShouShiAllAttr(chara, "五蕴悯光");
//							GameUtil.getShouShiAllAttr(chara, "八宝如意");
//							return;
//						} else if ("130".equals(menu_item)) {
//							if (chara.levelUpReward[6] == 1) {
//								GameUtil.sendMeTips("你已领取过该奖励！");
//								return;
//							}
//							chara.levelUpReward[6] = 1;
//							GameUtil.addchargeScore(gameObjectChar, 1000, "升级奖励大使");
//							return;
//						}
//					}
//				}
                if (menu_item.equals("离开")) {
                    return;
                }

                ConfigInfo infoData = GameData.that.configInfoService.getOneByUuid("8afc361ba57649b093c2b480a00897b1");
                if (infoData != null) {
                    JSONObject parseObject = JSONObject.parseObject(infoData.getData());
                    JSONObject jsonObject = parseObject.getJSONObject(menu_item);
                    if (jsonObject != null) {
                        Integer no = jsonObject.getInteger("no");
                        int level = jsonObject.getIntValue("level");
                        if (chara.level < level) {
                            GameUtil.sendMeTips("请升至#R" + level + "#n级在来领取奖励！");
                            return;
                        }
                        String reward = jsonObject.getString("reward");
                        if (no != null && !StringUtils.isNullOrEmpty(reward)) {
                            if (chara.levelUpReward[no] == 1) {
                                GameUtil.changeNpcSession(npc, "该升级奖励已领[离开]");
                                return;
                            }
                            List<String[]> parseRewardStr = GameCommonUtil.parseRewardStr(reward);
                            if (parseRewardStr.isEmpty()) {
                                GameUtil.changeNpcSession(npc, "未设置该奖励[离开]");
                                return;
                            }
                            chara.levelUpReward[no] = 1;
                            for (String[] str : parseRewardStr) {
                                GameCommonUtil.getReward(gameObjectChar, str, "升级奖励大使");
                            }
                        }
                    }
                }
            }

            // 帮派书童捐钱
            if (id == 1004) {
                // 帮派捐款
                if (menu_item.contains("bpstjk|")) {
                    String[] paras = menu_item.split("\\|");
                    int money = Integer.valueOf(paras[1]);
                    if (money > 0) {
                        GameUtil.confirm(chara, "是否确认为本帮捐款" + GameCommonUtil.getMoneyDes(money) + "#n文钱?", menu_item);
                    }
                    return;
                }
            }
            // 活动大使处挑战超级boss
            if (id == 963) {
                if ("【一阶】挑战超级BOSS".equals(menu_item)) {
                    GameActiveUtil.gotoSuperBossFight(chara, false);
                    return;
                }
                if ("【二阶】挑战超级BOSS".equals(menu_item)) {
                    GameActiveUtil.gotoSuperBossFight(chara, true);
                    return;
                } else if ("getSuperBoosNum".equals(menu_item)) {
                    int overSuperBossNum = GameConfig.config.getBaseConfig().getSuperBossNum() - chara.superBossNum;
                    GameUtil.sendMeTips("今日还剩余#R" + (overSuperBossNum < 0 ? 0 : overSuperBossNum) + "#n次挑战次数。");
                    return;
                }
            }


            //单笔


            // 单笔奖励
            if (id == 9663 && npc != null) {
                // 如果是这个开头的话.
                if (menu_item.toUpperCase().startsWith("LJCZ")) {
                    //查询到该菜单信息
                    NpcDialogueFrame npcMenu = GameData.that.baseNpcDialogueFrameService.findOneByName(npc.getName());
                    if (npcMenu == null) {
                        GameCommonUtil.fuckBastard(gameObjectChar);
                        return;
                    }
                    String content = npcMenu.getUncontent();
                    boolean isFindMenu = false;
                    if (!StringUtils.isNullOrEmpty(content)) {
                        content = content.replace("[", "");
                        String[] split = content.split("]");
                        for (String sp : split) {
                            String[] smenu = sp.split("/");
                            if (smenu.length > 1 && menu_item.equals(smenu[1])) {
                                isFindMenu = true;
                                break;
                            }
                        }
                    }
                    if (!isFindMenu) {
                        GameCommonUtil.fuckBastard(gameObjectChar);
                        return;
                    }
                    // 解析参数-- LJCZ|法宝|翻天印|24, 0:类型 1:名称(如果要随机直接就填随机)2:等级 ,LJCZ|积分|1000
                    String menuItem = menu_item.replace("LJCZ|", "");
                    String[] params = menu_item.replace("LJCZ|", "").split("\\|");
                    int money = -1;
                    try {
                        money = Integer.parseInt(params[0]);
                    } catch (Exception e) {
                        log.error("{}", e);
                        GameUtil.sendMeTips("格式错误.");
                        return;
                    }

                    if (money != -1) {
                        // 查询是否符合条件
                        Accounts accounts = GameData.that.baseAccountsService.findById(gameObjectChar.getAccountid());
                        Charge c = GameData.that.baseChargeService.findOneByAccountMoney(accounts.getName(), money);
                        if (c == null) {
                            GameUtil.sendMeTips("暂无该单笔充值数据");
                            return;
                        }
                        for (String s : menuItem.split(",")) {
                            params = s.split("\\|");
                            try {
                                String type = params[1];
                                String name = params[2];
                                int level = 0;
                                if (params.length > 3) {
                                    try {
                                        level = Integer.valueOf(params[3]);
                                    } catch (Exception e) {
                                        log.error("{}", e);
                                        GameUtil.sendMeTips("格式错误.");
                                        return;
                                    }
                                }
                                // 开始解析参数
                                if ("积分".equals(type)) {
                                    GameUtil.addchargeScore(gameObjectChar, Integer.valueOf(params[2]), "累计充值");
                                } else if ("法宝".equals(type)) {
                                    if ("随机".equals(name)) {
                                        String[] fabaoName = {"定海珠", "阴阳镜", "混元金斗", "金蛟剪", "卸甲金葫", "九龙神火罩", "番天印"};
                                        GameUtil.huodefabao(chara, fabaoName[new Random().nextInt(fabaoName.length)],
                                                level == 0 ? 1 : level, "");
                                    } else {
                                        // 指定
                                        GameUtil.huodefabao(chara, name, level == 0 ? 1 : level, "累计充值");
                                    }
                                } else if ("变异".equals(type)) {
                                    if ("随机".equals(name)) {
                                        GameUtil.baobianyi(chara, "累计充值");
                                    } else {
                                        // 指定
                                        GameUtil.huodebianyi(chara, name, "累计充值");
                                    }
                                } else if ("神兽".equals(type)) {
                                    if ("随机".equals(name)) {
                                        GameUtil.baoshenshou(chara, "累计充值");
                                    } else {
                                        // 指定
                                        GameUtil.huodeshenshou(chara, name, "累计充值");
                                    }
                                } else if ("坐骑".equals(type)) {
                                    if ("随机".equals(name)) {
                                        // 判断等级
                                        if (level == 6) {
                                            String zuoqi = GameCommonUtil.speedNo6[new Random()
                                                    .nextInt(GameCommonUtil.speedNo6.length)];
                                            GameUtil.huodezuoqi(chara, zuoqi, 6, "累计充值");
                                        } else if (level == 8) {
                                            String zuoqi = GameCommonUtil.speedNo8[new Random()
                                                    .nextInt(GameCommonUtil.speedNo8.length)];
                                            GameUtil.huodezuoqi(chara, zuoqi, 8, "累计充值");
                                        }
                                    } else {
                                        // 指定
                                        GameUtil.huodezuoqi(chara, name, level == 0 ? 2 : level, "累计充值");
                                    }
                                } else if ("首饰".equals(type)) {
                                    if ("全套".equals(name)) {
                                        String[] showshi = new String[3];
                                        if (level == 80) {
                                            showshi = GameUtil.SHOU_SHI_80;
                                        } else if (level == 90) {
                                            showshi = GameUtil.SHOU_SHI_90;
                                        } else if (level == 100) {
                                            showshi = GameUtil.SHOU_SHI_100;
                                        } else if (level == 110) {
                                            showshi = GameUtil.SHOU_SHI_110;
                                        } else if (level == 120) {
                                            showshi = GameUtil.SHOU_SHI_120;
                                        } else if (level == 130) {
                                            showshi = GameUtil.SHOU_SHI_130;
                                        } else if (level == 140) {
                                            showshi = GameUtil.SHOU_SHI_140;
                                        } else if (level == 150) {
                                            showshi = GameUtil.SHOU_SHI_150;
                                        } else if (level == 160) {
                                            showshi = GameUtil.SHOU_SHI_160;
                                        } else if (level == 170) {
                                            showshi = GameUtil.SHOU_SHI_170;
                                        }
                                        // 获得全套首饰
                                        GameUtil.getShouShiAllAttr(chara, showshi[0]);
                                        GameUtil.getShouShiAllAttr(chara, showshi[1]);
                                        GameUtil.getShouShiAllAttr(chara, showshi[2]);
                                        GameUtil.getShouShiAllAttr(chara, showshi[2]);
                                    } else if ("随机".equals(name)) {
                                        String[] showshi = new String[3];
                                        if (level == 80) {
                                            showshi = GameUtil.SHOU_SHI_80;
                                        } else if (level == 90) {
                                            showshi = GameUtil.SHOU_SHI_90;
                                        } else if (level == 100) {
                                            showshi = GameUtil.SHOU_SHI_100;
                                        } else if (level == 110) {
                                            showshi = GameUtil.SHOU_SHI_110;
                                        } else if (level == 120) {
                                            showshi = GameUtil.SHOU_SHI_120;
                                        } else if (level == 130) {
                                            showshi = GameUtil.SHOU_SHI_130;
                                        } else if (level == 140) {
                                            showshi = GameUtil.SHOU_SHI_140;
                                        } else if (level == 150) {
                                            showshi = GameUtil.SHOU_SHI_150;
                                        } else if (level == 160) {
                                            showshi = GameUtil.SHOU_SHI_160;
                                        } else if (level == 170) {
                                            showshi = GameUtil.SHOU_SHI_170;
                                        }
                                        GameUtil.jifendengjishoushi(chara, showshi);
                                    } else {
                                        // 指定
                                        ZhuangbeiInfo f1 = GameData.that.baseZhuangbeiInfoService.findOneByStr(name);
                                        GameUtil.huodezhuangbeixiangwu(chara, f1, 1, 1);
                                    }
                                } else if ("物品".equals(type)) {
                                    int num = 0;
                                    if (params.length > 3) {
                                        try {
                                            num = Integer.valueOf(params[3]);
                                        } catch (Exception e) {
                                            log.error("{}", e);
                                            GameUtil.sendMeTips("格式错误.");
                                            return;
                                        }
                                    }
                                    GameUtil.huodedaoju(chara, name, num);
                                    GameUtil.sendMeTips("获得物品#R" + name);
                                }
                                if (!s.contains("(")) {
                                    continue;
                                }
                                ChengweiService chengweiService = SpringBeanUtils.getBean(ChengweiService.class);
                                String chengweiName = menu_item.substring(menu_item.indexOf("(") + 1, (menu_item.indexOf(")")));
                                Chengwei chengwei = chengweiService.getChengweiByName(chengweiName);
                                if (chengwei != null) {
                                    if (chara.getChenghao().get(chengwei.getName()) != null) {
                                        // 称谓已经获取了,无需再次获取
                                        break;
                                    }
                                    GameUtil.chenghaoxiaoxi(chara, chengwei.getName(), chengwei.getName());
                                    GameUtil.sendMeTips("恭喜你获得#R" + chengwei.getName() + "#n称谓。");
                                    GameUtil.sendSystemMessage(19, "热烈恭喜#Y" + chara.name + "#n玩家单笔充值达到了#R"
                                            + chengwei.getMoney() + "元#n获得了系统赠送的#R" + chengwei.getName() + "#n称谓，真是可喜可贺。");
                                }
//								ChargeGetRecord chargeGetRecord = new ChargeGetRecord();
//								chargeGetRecord.setCreateTime(new Date());
//								chargeGetRecord.setAccount(accounts.getName());
//								chargeGetRecord.setMoney(money);
//								chargeGetRecord.setName(chara.name);
//								GameData.that.chargeGetRecordService.insertSelective(chargeGetRecord);
                            } catch (Exception e) {
                                log.error("领取出现异常,{}", "{}");
                            }
                        }
                        // 插入记录
                        c.setStatus(1);
                        GameData.that.baseChargeService.updateById(c);
                    }
                    return;
                }
            }


            // 累计充值奖励
            if (id == 1663 && npc != null) {
                // 如果是这个开头的话.
                if (menu_item.toUpperCase().startsWith("LJCZ")) {
                    //查询到该菜单信息
                    NpcDialogueFrame npcMenu = GameData.that.baseNpcDialogueFrameService.findOneByName(npc.getName());
                    if (npcMenu == null) {
                        GameCommonUtil.fuckBastard(gameObjectChar);
                        return;
                    }
                    String content = npcMenu.getUncontent();
                    boolean isFindMenu = false;
                    if (!StringUtils.isNullOrEmpty(content)) {
                        content = content.replace("[", "");
                        String[] split = content.split("]");
                        for (String sp : split) {
                            String[] smenu = sp.split("/");
                            if (smenu.length > 1 && menu_item.equals(smenu[1])) {
                                isFindMenu = true;
                                break;
                            }
                        }
                    }
                    if (!isFindMenu) {
                        GameCommonUtil.fuckBastard(gameObjectChar);
                        return;
                    }
                    // 解析参数-- LJCZ|法宝|翻天印|24, 0:类型 1:名称(如果要随机直接就填随机)2:等级 ,LJCZ|积分|1000
                    String menuItem = menu_item.replace("LJCZ|", "");
                    String[] params = menu_item.replace("LJCZ|", "").split("\\|");
                    int money = -1;
                    try {
                        money = Integer.parseInt(params[0]);
                    } catch (Exception e) {
                        log.error("{}", e);
                        GameUtil.sendMeTips("格式错误.");
                        return;
                    }
                    if (money != -1) {
                        // 查询是否符合条件
                        Accounts accounts = GameData.that.baseAccountsService.findById(gameObjectChar.getAccountid());
                        // 查询用户是否已领取
                        int isGet = GameData.that.chargeGetRecordService.getUserChargeGetRecords(accounts.getName(),
                                money);
                        if (isGet > 0) {
                            GameUtil.sendMeTips("请勿重复领取！");
                            return;
                        }
                        List<Charge> chargeList = (List<Charge>) GameData.that.baseChargeService
                                .findByAccountname(accounts.getName());
                        int sum = chargeList.stream().mapToInt(Charge::getMoney).sum();
                        if (sum < money) {
                            GameUtil.sendMeTips("不符合条件");
                            return;
                        }
                        for (String s : menuItem.split(",")) {
                            params = s.split("\\|");
                            try {

                                String type = params[1];
                                String name = params[2];
                                int level = 0;
                                if (params.length > 3) {
                                    try {
                                        level = Integer.valueOf(params[3]);
                                    } catch (Exception e) {
                                        log.error("{}", e);
                                        GameUtil.sendMeTips("格式错误.");
                                        return;
                                    }
                                }
                                // 开始解析参数
                                if ("积分".equals(type)) {
                                    GameUtil.addchargeScore(gameObjectChar, Integer.valueOf(params[2]), "累计充值");
                                } else if ("法宝".equals(type)) {
                                    if ("随机".equals(name)) {
                                        String[] fabaoName = {"定海珠", "阴阳镜", "混元金斗", "金蛟剪", "卸甲金葫", "九龙神火罩", "番天印"};
                                        GameUtil.huodefabao(chara, fabaoName[new Random().nextInt(fabaoName.length)],
                                                level == 0 ? 1 : level, "");
                                    } else {
                                        // 指定
                                        GameUtil.huodefabao(chara, name, level == 0 ? 1 : level, "累计充值");
                                    }
                                } else if ("变异".equals(type)) {
                                    if ("随机".equals(name)) {
                                        GameUtil.baobianyi(chara, "累计充值");
                                    } else {
                                        // 指定
                                        GameUtil.huodebianyi(chara, name, "累计充值");
                                    }
                                } else if ("神兽".equals(type)) {
                                    if ("随机".equals(name)) {
                                        GameUtil.baoshenshou(chara, "累计充值");
                                    } else {
                                        // 指定
                                        GameUtil.huodeshenshou(chara, name, "累计充值");
                                    }
                                } else if ("坐骑".equals(type)) {
                                    if ("随机".equals(name)) {
                                        // 判断等级
                                        if (level == 6) {
                                            String zuoqi = GameCommonUtil.speedNo6[new Random()
                                                    .nextInt(GameCommonUtil.speedNo6.length)];
                                            GameUtil.huodezuoqi(chara, zuoqi, 6, "累计充值");
                                        } else if (level == 8) {
                                            String zuoqi = GameCommonUtil.speedNo8[new Random()
                                                    .nextInt(GameCommonUtil.speedNo8.length)];
                                            GameUtil.huodezuoqi(chara, zuoqi, 8, "累计充值");
                                        }
                                    } else {
                                        // 指定
                                        GameUtil.huodezuoqi(chara, name, level == 0 ? 2 : level, "累计充值");
                                    }
                                } else if ("首饰".equals(type)) {
                                    if ("全套".equals(name)) {
                                        String[] showshi = new String[3];
                                        if (level == 80) {
                                            showshi = GameUtil.SHOU_SHI_80;
                                        } else if (level == 90) {
                                            showshi = GameUtil.SHOU_SHI_90;
                                        } else if (level == 100) {
                                            showshi = GameUtil.SHOU_SHI_100;
                                        } else if (level == 110) {
                                            showshi = GameUtil.SHOU_SHI_110;
                                        } else if (level == 120) {
                                            showshi = GameUtil.SHOU_SHI_120;
                                        } else if (level == 130) {
                                            showshi = GameUtil.SHOU_SHI_130;
                                        } else if (level == 140) {
                                            showshi = GameUtil.SHOU_SHI_140;
                                        } else if (level == 150) {
                                            showshi = GameUtil.SHOU_SHI_150;
                                        } else if (level == 160) {
                                            showshi = GameUtil.SHOU_SHI_160;
                                        } else if (level == 170) {
                                            showshi = GameUtil.SHOU_SHI_170;
                                        }
                                        // 获得全套首饰
                                        GameUtil.getShouShiAllAttr(chara, showshi[0]);
                                        GameUtil.getShouShiAllAttr(chara, showshi[1]);
                                        GameUtil.getShouShiAllAttr(chara, showshi[2]);
                                        GameUtil.getShouShiAllAttr(chara, showshi[2]);
                                    } else if ("随机".equals(name)) {
                                        String[] showshi = new String[3];
                                        if (level == 80) {
                                            showshi = GameUtil.SHOU_SHI_80;
                                        } else if (level == 90) {
                                            showshi = GameUtil.SHOU_SHI_90;
                                        } else if (level == 100) {
                                            showshi = GameUtil.SHOU_SHI_100;
                                        } else if (level == 110) {
                                            showshi = GameUtil.SHOU_SHI_110;
                                        } else if (level == 120) {
                                            showshi = GameUtil.SHOU_SHI_120;
                                        } else if (level == 130) {
                                            showshi = GameUtil.SHOU_SHI_130;
                                        } else if (level == 140) {
                                            showshi = GameUtil.SHOU_SHI_140;
                                        } else if (level == 150) {
                                            showshi = GameUtil.SHOU_SHI_150;
                                        } else if (level == 160) {
                                            showshi = GameUtil.SHOU_SHI_160;
                                        } else if (level == 170) {
                                            showshi = GameUtil.SHOU_SHI_170;
                                        }
                                        GameUtil.jifendengjishoushi(chara, showshi);
                                    } else {
                                        // 指定
                                        ZhuangbeiInfo f1 = GameData.that.baseZhuangbeiInfoService.findOneByStr(name);
                                        GameUtil.huodezhuangbeixiangwu(chara, f1, 1, 1);
                                    }
                                } else if ("物品".equals(type)) {
                                    int num = 0;
                                    if (params.length > 3) {
                                        try {
                                            num = Integer.valueOf(params[3]);
                                        } catch (Exception e) {
                                            log.error("{}", e);
                                            GameUtil.sendMeTips("格式错误.");
                                            return;
                                        }
                                    }
                                    GameUtil.huodedaoju(chara, name, num);
                                    GameUtil.sendMeTips("获得物品#R" + name);
                                }
                            } catch (Exception e) {
                                log.error("领取出现异常,{}", "{}");
                            }
                        }
                        // 插入记录
                        ChargeGetRecord chargeGetRecord = new ChargeGetRecord();
                        chargeGetRecord.setCreateTime(new Date());
                        chargeGetRecord.setAccount(accounts.getName());
                        chargeGetRecord.setMoney(money);
                        chargeGetRecord.setName(chara.name);
                        GameData.that.chargeGetRecordService.insertSelective(chargeGetRecord);
                    }
                    return;
                }
            }
            // 解散帮派
            if (id == 1044) {
                if ("【帮派管理】我想解散帮派".equals(menu_item)) {
                    GameUtil.confirm(chara, "你确定要解散#Y" + chara.getPartyName() + "#n帮派？解散后将失去帮派称谓和一切福利", "removeParty");
                    log.info("解散帮派");
                    return;
                }
            }
            // 神兽令牌兑换积分
            if (id == 1662) {
                if ("回收神兽令牌".equals(menu_item)) {
                    int num = 0;
                    for (Goods g : chara.backpack) {
                        if ("召唤令·上古神兽".equals(g.goodsInfo.str)) {
                            num += g.goodsInfo.owner_id;
                        }
                    }
                    if (num == 0) {
                        GameUtil.changeNpcSession(npc, "道友你的包裹未发现#R召唤令·上古神兽#n请去做任务赚取吧！[离开]");
                    } else {
                        GameUtil.confirm(chara,
                                "你确定用#Y" + num + "#n个召唤令·上古神兽兑换#R" + GameConfig.jifenhuishou * num + "#n积分吗?",
                                "recoveryShangGuLingPai-" + num);
                    }
                    return;
                }
            }
            // 帮派任务和日常挑战
            if (id == 1006) {
                String content = "你当前已有任务快去完成吧";
                if ("bprw".equals(menu_item)) {
                    if (chara.partyNum > GameConfig.config.getBaseConfig().getPartyNum()) {
                        GameUtil.sendMeTips("你今日已完成帮派任务");
                        return;
                    }
                    Vo_61553_0 task = chara.taskMap.get("帮派任务");
                    if (task != null) {
                        if (task.task_extra_para.equals("finish")) {
                            try {
                                GamePartyUtil.endPartyTask(chara, 0);
                            } finally {
                                // 移除任务.
                                GameUtilRenWu.removeTask("帮派任务", chara);
                                //完成任务
                                if (chara.partyNum >= 10) {
                                    // 任务比实际多一次
                                    chara.partyNum++;
                                    return;
                                }
                            }
                        } else {
                            GameUtil.changeNpcSession(npc, content + "[离开]");
                            return;
                        }
                    }
                    GamePartyUtil.randomPartyTask(chara);
                    GameCommonUtil.dialogOk("领取帮派任务成功，快去完成吧！");
                    return;
                } else if ("bprwsd".equals(menu_item)) {
                    if (chara.partyNum > GameConfig.config.getBaseConfig().getPartyNum()) {
                        GameUtil.sendMeTips("你今日已完成帮派任务");
                        return;
                    }
                    // 帮派任务扫荡
                    if (chara.vipType == 0) {
                        GameUtil.changeNpcSession(npc, "你还不是会员。" + "[离开]");
                        return;
                    }
                    try {
                        // 移除任务.
                        GameUtilRenWu.removeTask("帮派任务", chara);
                        // 如果扫荡的话奖励就翻倍
                        chara.partyNum = ((GameConfig.config.getBaseConfig().getPartyNum() - chara.partyNum) * 3) * 4;
                        GamePartyUtil.endPartyTask(chara, 0);
                    } finally {
                        chara.partyNum = GameConfig.config.getBaseConfig().getPartyNum() + 1;
                    }
                    return;
                } else if ("bprctz".equals(menu_item)) {
                    if (chara.partyFightNum > GameConfig.config.getBaseConfig().getPartyFightNum()) {
                        GameUtil.sendMeTips("你今日已完成帮派日常挑战");
                        return;
                    }
                    // 帮派任务挑战
                    Vo_61553_0 task = chara.taskMap.get("帮派日常挑战");
                    if (task != null) {
                        GameUtil.changeNpcSession(npc, content + "[离开]");
                        return;
                    }
                    chara.partyFightNum = GameConfig.config.getBaseConfig().getPartyFightNum() + 1;
                    GameCommonUtil.dialogOk("领取帮派日常挑战成功，快去完成吧！");
                    GamePartyUtil.nextPartyTiaozhanTask(chara);
                    return;
                } else if ("bprctzsd".equals(menu_item)) {
                    if (chara.partyFightNum > GameConfig.config.getBaseConfig().getPartyFightNum()) {
                        GameUtil.sendMeTips("你今日已完成帮派日常挑战");
                        return;
                    }
                    // 帮派任务挑战扫荡
                    if (chara.vipType == 0) {
                        GameUtil.changeNpcSession(npc, "你还不是会员。" + "[离开]");
                        return;
                    }
                    // 移除任务.
                    GameUtilRenWu.removeTask("帮派日常挑战", chara);
                    chara.partyFightNum = GameConfig.config.getBaseConfig().getPartyFightNum() + 1;
                    // 如果扫荡的话奖励就翻倍
                    GamePartyUtil.endPartyTask(chara, 100);
                    return;
                }
            }
            // 人物飞升 开始
            if (id == 955) {
                if ("getFsrw".equals(menu_item)) {
                    GameUtilRenWu.removeTask("飞升引路人", chara);
                    if (chara.isFeisheng != 0) {
                        GameUtil.changeNpcSession(npc, "道友你已完成飞升！[离开]");
                        return;
                    } else if (chara.taskMap.get("飞升—引路人") != null) {
                        GameUtil.changeNpcSession(npc, "你已领取飞升—引路人任务请去完成！[离开]");
                        return;
                    }
                    // 领取飞升任务.
                    Vo_61553_0 vo_61553_0 = new Vo_61553_0();
                    vo_61553_0.count = 1;
                    vo_61553_0.task_type = "飞升—引路人";
                    vo_61553_0.task_desc = "修行已久、道行已深；寻找引路人，凝结成婴，踏上飞升之路。";
                    vo_61553_0.task_prompt = "请前往#R无名小镇#n找#P南华真人|@P南华真人|M=【飞升】结婴之路#P#n进行飞升";
                    vo_61553_0.refresh = 1;
                    vo_61553_0.task_end_time = 1567909190;
                    vo_61553_0.attrib = 0;
                    vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I#I#I等级上限|突破115级限制#I";
                    vo_61553_0.show_name = "飞升—引路人";
                    vo_61553_0.task_extra_para = "1";
                    vo_61553_0.task_state = "1";
                    GameUtilRenWu.createTask(vo_61553_0, chara);
                    GameObjectChar.send(new MSG_AUTO_WALK(),
                            new Vo_AUTO_WALK("请前往#R无名小镇#n找#P南华真人|@P南华真人|M=【飞升】结婴之路#P#n进行飞升"));
                    return;
                } else if ("七杀试炼".equals(menu_item)) {
                    String openMenu = "";
                    if (gameObjectChar.isOpenQiShaFlag > 0) {
                        openMenu = "[参悟七杀试炼/canwu_qisha]";
                    }
                    GameUtil.changeNpcSession(npc, "七杀星高悬于空，坐镇东南，若能参悟其中玄机，道行修为必将突飞猛进。[了解七杀试炼/read_qisha][开启七杀试炼/open_qisha]" + openMenu + "[离开]");
                    return;
                } else if ("open_qisha".equals(menu_item)) {
                    GameUtil.openDlg("QiShaDlg");
                } else if ("canwu_qisha".equals(menu_item)) {
                    //如果没有开启七杀
                    if (gameObjectChar.isOpenQiShaFlag < 1) {
                        GameUtil.changeNpcSession(npc, "七杀星高悬于空，坐镇东南，若能参悟其中玄机，道行修为必将突飞猛进。[了解七杀试炼/read_qisha][开启七杀试炼/open_qisha][参悟七杀试炼/canwu_qisha][离开]");
                        GameUtil.sendMeTips("你还未开启七杀！");
                        return;
                    }
                    if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                        GameUtil.sendMeTips("七杀试炼难度较大，请组队！");
                        return;
                    }
                    gameObjectChar.isOpenQiShaFlag = 0;
                    for (Chara team : gameObjectChar.gameTeam.duiwu) {
                        if (team.level < 100) {
                            GameUtil.sendMeTips("队伍中有低于100级玩家，无法进入战斗！");
                            return;
                        }
                        team.qishaCount += 1;
                    }
                    List<FightObject> fightObjects = FightManager.getFightDynamicLevelByType(chara, Arrays.asList("七杀星君", "双龙护卫", "双龙护卫", "四象镇邪", "四象镇邪", "四象镇邪", "四象镇邪", "四象镇邪", "四象镇邪", "四象镇邪"), "七杀试炼");
                    fightObjects.get(0).uid = "七杀试炼";
                    FightManager.activeBoosGoFight(chara, fightObjects, false);
                    return;
                } else if ("qisha_count".equals(menu_item)) {
                    int count = chara.qishaCount + 1 > GameConfig.config.getBaseConfig().getQishaCount() ? GameConfig.config.getBaseConfig().getQishaCount() : chara.qishaCount;
                    GameUtil.sendMeTips("今日还剩余:" + (GameConfig.config.getBaseConfig().getQishaCount() - count) + "次");
                    return;
                }
            }
            if (1196 == id) {
                if ("结婴之路".equals(menu_item)) {
                    GameUtil.changeNpcSession(npc, "事情有点麻烦了，怎么办？\n[挑战真人灵兽]\n[以后再说]\n");
                    return;
                } else if ("挑战真人灵兽".equals(menu_item)) {
                    List<String> monsterList = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        monsterList.add("真人灵兽");
                    }
                    FightManager.activeBoosGoFight(chara, monsterList, false);
                    return;
                } else if ("结婴".equals(menu_item)) {
                    chara.currentConfirmItem = "switchFly";
                    GameUtil.openDlg("CoagulationChildDlg");
                    return;
                } else if ("重新结婴".equals(menu_item)) {
                    chara.currentConfirmItem = "reSwitchFly";
                    GameUtil.openDlg("CoagulationChildDlg");
                    return;
                } else if ("飞升仙魔".equals(menu_item)) {
                    chara.currentConfirmItem = "openSwitchXianMo";
                    GameUtil.openDlg("UserUpgradeDlg");
                    return;
                } else if ("仙魔转换".equals(menu_item)) {
                    chara.currentConfirmItem = "reSwitchXianMo";
                    GameUtil.openDlg("UserChangeUpgradeDlg");
                    return;
                }
            }
            if (id == 1344) {
                if ("【飞升】那只有得罪了！".equals(menu_item)) {
                    List<String> list = new ArrayList<>();
                    list.add("冰晶龙鳞兽王");
                    for (int i = 0; i < 9; i++) {
                        list.add("冰晶龙鳞兽");
                    }
                    FightManager.activeBoosGoFight(chara, list, false);
                    return;
                } else if ("帮派求助".equals(menu_item)) {
                    return;
                }
            }
            if (id == 1345) {
                if ("【飞升】那只有得罪了！".equals(menu_item)) {
                    List<String> list = new ArrayList<>();
                    list.add("雪狐王");
                    for (int i = 0; i < 9; i++) {
                        list.add("雪狐");
                    }
                    FightManager.activeBoosGoFight(chara, list, false);
                    return;
                } else if ("帮派求助".equals(menu_item)) {
                    return;
                }
            }
            // 人物飞升 结束

            // 抽奖大使
            if ("抽奖大使".equals(npc.getName())) {
                //如果背包满或者是宠物栏满了
                if (chara.backpack.size() >= GameCommonUtil.getBackpackPos(chara).size()) {
                    GameUtil.sendMeTips("包裹满了，无法抽奖");
                    return;
                } else if (chara.pets.size() >= 8) {
                    GameUtil.sendMeTips("宠物栏满了，无法抽奖");
                    return;
                }
                int chargeNpcOnePrice = GameConfig.config.getBaseConfig().getChargeNpcOnePrice();
                if ("one".equals(menu_item)) {
                    GameUtil.confirm(chara, "确定消耗#R" + chargeNpcOnePrice + "#n积分抽奖1次?", "chargeLuckOne");
                    return;
                } else if ("many".equals(menu_item)) {
                    GameUtil.confirm(chara, "确定消耗#R" + chargeNpcOnePrice * 10 + "#n积分抽奖10次?", "chargeLuckMany");
                    return;
                }
            }

            if (id == 1649) {
                if (menu_item.equals("杀我")) {
                    ArrayList<String> l = new ArrayList<>();
                    l.add("测试海盗");
                    l.add("测试海盗");
                    l.add("测试海盗");
                    l.add("测试海盗");
                    l.add("测试海盗");
                    gameObjectChar.flag = "测试海盗";
                    FightManager.activeBoosGoFight(chara, l, true);
                    return;
                }
            }
            // 修法任务
            if (id == 907) {
                if (menu_item.equals("修法任务")) {
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "修法任务只能单人完成！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (chara.level < 70) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "你未满70级，无法继续进行！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    boolean hasFabao = false;
                    for (int l = 0; l < chara.otherGoods.size(); ++l) {
                        if (chara.otherGoods.get(l).pos == 9) {
                            hasFabao = true;
                            break;
                        }
                    }
                    if (!hasFabao) {
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "请#R穿戴法宝#n后再来领取修法任务！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (chara.xiufacishu >= 4) {
                        chara.xiufaNpcName = "";
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "你今日已做过修法任务了！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }

                    String[] npces = GameUtil.XIU_FA_NPC;
                    int i = 0; // 顺序做修法任务
                    String shenshouName = (chara.xiufaNpcName != null && !chara.xiufaNpcName.equals(""))
                            ? chara.xiufaNpcName
                            : npces[i];
                    chara.xiufaNpcName = (chara.xiufaNpcName != null && !chara.xiufaNpcName.equals(""))
                            ? chara.xiufaNpcName
                            : npces[i];
                    Vo_61553_0 vo_61553_10 = new Vo_61553_0();
                    vo_61553_10.count = 1;
                    vo_61553_10.task_type = "修法";
                    vo_61553_10.task_desc = "替多宝道人去讨伐怪物，完成后会有诸多奖励。";
                    vo_61553_10.task_prompt = ("挑战神兽#P" + shenshouName + "|M=【修法】我是来消灭你的#P");
                    vo_61553_10.refresh = 0;
                    vo_61553_10.task_end_time = 1567932239;
                    vo_61553_10.attrib = 1;
                    vo_61553_10.reward = "#I经验|人物经验宠物经验#I#I金钱|金钱#I";
                    vo_61553_10.show_name = ("【修法】挑战" + shenshouName);
                    vo_61553_10.task_extra_para = "";
                    vo_61553_10.task_state = "1";
                    GameUtilRenWu.createTask(vo_61553_10, chara);
                    GameUtil.sendMeTips("领取修法任务成功");
                    return;

                } else if (menu_item.contains("扫荡修法任务")) {
                    if (chara.xiufacishu >= 4) {
                        chara.xiufaNpcName = "";
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "你今日已做过修法任务了！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    GameUtil.confirm(chara, "是否确定消耗#R10#n积分扫荡修法?", "saodangxiufu");
                    return;
                }
            }

            if (id == 1193 && "地府".equals(menu_item)) {
                com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName("鬼门关");
                chara.y = 31;
                chara.x = 38;
                GameLine.getGameMapname(chara.line, map.getName()).join(gameObjectChar);
                return;
            }
            if (id == 1300 && "阳间".equals(menu_item)) {
                com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName("无名小镇");
                chara.y = 85;
                chara.x = 15;
                GameLine.getGameMapname(chara.line, map.getName()).join(gameObjectChar);
                return;
            }
            if (id == 906) {
                if ("open_dlg".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队进入吕洞宾梦境";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    if (chara.level < 70) {
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "请先把等级升到70级再来挑战！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (chara.taskMap.get("八仙梦境") != null) {
                        GameCommonUtil.enterDynamicMap("桐柏山", chara);
                    } else {
                        Vo_BAXIAN_MENGJING_INFO object1 = new Vo_BAXIAN_MENGJING_INFO();
                        object1.times = chara.baxiantiaozhan;
                        object1.curCheckpoint = 0;
                        object1.openMax = 1;
                        object1.mainState = 1;
                        object1.isOpenDlg = 1;
                        gameObjectChar.sendOne(new MSG_BAXIAN_MENGJING_INFO(), object1);
                    }
                    return;
                }
            }
            if (id == 948) {
                if ("问路".equals(menu_item) && "0".equals(chara.taskMap.get("八仙梦境").task_state)
                        && chara.taskMap.get("八仙梦境").show_name.equals("八仙梦境-吕洞宾")) {
                    Vo_61553_0 vo_61553_2 = new Vo_61553_0();
                    vo_61553_2.count = 1;
                    vo_61553_2.task_type = "八仙梦境";
                    vo_61553_2.task_desc = "八仙梦境-吕洞宾";
                    vo_61553_2.task_prompt = "去找#P太白金星|E=【八仙】请求帮助|$0#P寻求帮助";
                    vo_61553_2.refresh = 1;
                    vo_61553_2.task_end_time = (int) (System.currentTimeMillis() / 1000L);
                    vo_61553_2.attrib = 1;
                    vo_61553_2.reward = "";
                    vo_61553_2.show_name = "八仙梦境-吕洞宾";
                    vo_61553_2.task_extra_para = "";
                    vo_61553_2.task_state = "1";
                    GameUtilRenWu.createTask(vo_61553_2, chara);
                    GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_2.task_prompt));
                    return;
                }
            }
            if (id == 949) {
                if ("帮助".equals(menu_item) && "1".equals(chara.taskMap.get("八仙梦境").task_state)
                        && chara.taskMap.get("八仙梦境").show_name.equals("八仙梦境-吕洞宾")) {
                    Vo_61553_0 vo_61553_2 = new Vo_61553_0();
                    vo_61553_2.count = 1;
                    vo_61553_2.task_type = "八仙梦境";
                    vo_61553_2.task_desc = "八仙梦境-吕洞宾";
                    vo_61553_2.task_prompt = "前去瑶池找#P牡丹仙子|E=【八仙】凡尘美景|$0#P";
                    vo_61553_2.refresh = 1;
                    vo_61553_2.task_end_time = (int) (System.currentTimeMillis() / 1000L);
                    vo_61553_2.attrib = 1;
                    vo_61553_2.reward = "";
                    vo_61553_2.show_name = "八仙梦境-吕洞宾";
                    vo_61553_2.task_extra_para = "";
                    vo_61553_2.task_state = "2";
                    GameUtilRenWu.createTask(vo_61553_2, chara);
                    GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_2.task_prompt));

                } else if ("找牡丹仙子".equals(menu_item)) {
                    GameCommonUtil.enterDynamicMap("瑶池", chara);
                    return;
                }
            }
            if (id == 1024) {
                if ("去桐柏山".equals(menu_item)) {
                    if ("6".equals(chara.taskMap.get("八仙梦境").task_state)
                            && chara.taskMap.get("八仙梦境").show_name.equals("八仙梦境-吕洞宾")) {
                        List<String> list2 = new ArrayList<String>();
                        list2.add("守值天兵");
                        list2.add("守值天兵");
                        list2.add("守值天兵");
                        FightManager.activeBoosGoFight(chara, list2, false);
                    } else {
                        GameCommonUtil.enterDynamicMap("桐柏山", chara);
                    }
                    return;
                }
            }
            if (id == 1022) {
                if ("牡丹仙子领取".equals(menu_item) && "2".equals(chara.taskMap.get("八仙梦境").task_state)
                        && chara.taskMap.get("八仙梦境").show_name.equals("八仙梦境-吕洞宾")) {
                    Vo_61553_0 vo_61553_2 = new Vo_61553_0();
                    vo_61553_2.count = 1;
                    vo_61553_2.task_type = "八仙梦境";
                    vo_61553_2.task_desc = "八仙梦境-吕洞宾";
                    vo_61553_2.task_prompt = "找#P瑶池仙子|瑶池(10,18)|M=【八仙】玄玉冰|$0#P获得#R玄玉冰";
                    vo_61553_2.refresh = 1;
                    vo_61553_2.task_end_time = (int) (System.currentTimeMillis() / 1000L);
                    vo_61553_2.attrib = 1;
                    vo_61553_2.reward = "";
                    vo_61553_2.show_name = "八仙梦境-吕洞宾";
                    vo_61553_2.task_extra_para = "";
                    vo_61553_2.task_state = "3";
                    GameUtilRenWu.createTask(vo_61553_2, chara);
                    GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_2.task_prompt));
                    return;
                }
                if ("万事俱备".equals(menu_item) && "5".equals(chara.taskMap.get("八仙梦境").task_state)
                        && chara.taskMap.get("八仙梦境").show_name.equals("八仙梦境-吕洞宾")) {
                    Vo_61553_0 vo_61553_2 = new Vo_61553_0();
                    vo_61553_2.count = 1;
                    vo_61553_2.task_type = "八仙梦境";
                    vo_61553_2.task_desc = "八仙梦境-吕洞宾";
                    vo_61553_2.task_prompt = "从#P守值天兵|$0#P处离开#Y瑶池";
                    vo_61553_2.refresh = 1;
                    vo_61553_2.task_end_time = (int) (System.currentTimeMillis() / 1000L);
                    vo_61553_2.attrib = 1;
                    vo_61553_2.reward = "";
                    vo_61553_2.show_name = "八仙梦境-吕洞宾";
                    vo_61553_2.task_extra_para = "";
                    vo_61553_2.task_state = "6";
                    GameUtilRenWu.createTask(vo_61553_2, chara);
                    GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_2.task_prompt));
                    return;
                }
            }
            if (id == 1208) {
                if ("玄玉冰".equals(menu_item)) {
                    List<String> list2 = new ArrayList<>();
                    list2.add("瑶池仙子");
                    list2.add("瑶池仙子");
                    list2.add("瑶池仙子");
                    FightManager.activeBoosGoFight(chara, list2, false);
                    return;
                }
            }
            if (id == 950) {
                if ("铲除妖孽".equals(menu_item)) {
                    List<String> list2 = new ArrayList<String>();
                    list2.add("穿山甲");
                    FightManager.activeBoosGoFight(chara, list2, false);
                    return;
                }
            }
            if (id == 1023) {
                if ("物归原主".equals(menu_item)) {
                    if (chara.baxiantiaozhan + 1 > GameConfig.config.getBaseConfig().getBaxianNum()
                            || chara.taskMap.get("八仙梦境") == null) {
                        GameCommonUtil.fuckBastard(gameObjectChar);
                        GameCommonUtil.addCharaTrail(chara, "非法请求", "八仙-吕洞宾", "八仙-吕洞宾");
                    } else {
                        GameUtil.pointMap(chara, "蓬莱岛");
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "一股神秘的力量将你送出了吕洞宾的梦境";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        if (!GameActiveUtil.fightVictoryInfo(chara, "八仙-吕洞宾")) {
                            int tao = (chara.level / 10 * 1440 * 325 * 3) * 5;
                            GameUtil.adddaohang(chara, tao, "八仙-吕洞宾");
                            GameCommonUtil.addWuXue(chara, 0, "八仙-吕洞宾");
                        }

                        GameUtilRenWu.removeTask("八仙梦境", chara);
                        ++chara.baxiantiaozhan;
                    }
                    return;
                }
            }
            if (id == 959) {
                if (!"离开".equals(menu_item)) {
                    // 1, 获取队伍信息,判断是否满足三人或三人以上的人数
                    if (gameObjectChar.gameTeam == null) {
                        GameUtil.changeNpcSession(id, 6223, "赤灵尊神", "你不是队长，不能创建副本。#R(至少需要3人组队才能进入副本)。[离开]");
                        return;
                    }
                    if (chara.fb_num >= GameConfig.config.getFb().getFbNumber()) {
                        GameUtil.changeNpcSession(id, 6223, "赤灵尊神", "今日已经挑战完了，道友明天再来。[离开]");
                        return;
                    }
                    // 2，弹出创建副本窗口
                    GameUtil.openDlg("DugeonCreateDlg");
                    int num = GameConfig.config.getFb().getFbNumber() - chara.fb_num;
                    int bonus = num < 0 ? 0 : num;
                    GameObjectChar.send(new MSG_DUNGEON_LIST(), new Vo_DUNGEON_LIST(bonus, "超级困难", Lists.newArrayList(new DugeonsInfo(35, "黑风洞")
                            , new DugeonsInfo(75, "兰若寺"), new DugeonsInfo(90, "烈火涧"), new DugeonsInfo(110, "飘渺仙府"))));

                    return;
                }
            }
            // 北斗星使
            if (id == 960) {
                if ("通天塔".equals(menu_item)) {
                    if (chara.level < 50) {
                        GameUtil.changeNpcSession(npc, "请先把等级升到50级再来挑战！[离开]");
                        return;
                    }
                    if (chara.tongttcishu >= GameConfig.config.getBaseConfig().getTongtiantaNum()
                            && chara.tongtiantaTask == null) {
                        GameUtil.changeNpcSession(npc, "今日已经挑战完了，道友明天再来！[离开]");
                        return;
                    }
                    if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                        GameUtil.changeNpcSession(npc, "暂未开放组队！[离开]");
                        return;
                    }
                    // 判断队伍等级是否相差10级
//					if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
//						if(!GameCommonUtil.levelAndLeaderDiffer(gameObjectChar, 10)) {
//							GameUtil.changeNpcSession(npc, "队员和队长级别相差10级，无法进入！[离开]");
//							return;
//						}
//					}else {
//						StringBuilder msg = new StringBuilder();
//						msg.append("队伍中,[#Y");
//						//判断队伍是否有任务并且层数一样
//						for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
//							if(teamChara.id == chara.id) {
//								continue;
//							}
//							if(teamChara.tongtiantaTask == null) {
//								msg.append(teamChara.name).append("#n,");
//							}
//						}
//						msg.append("]还未领取任务#n");
//					}

                    if (chara.tongtiantaTask == null) {
                        // 进入塔内、弹出经验和道行选择
                        GameUtil.changeNpcSession(npc, "道友请选择对应的奖励。 [经验/tttenter-exp][道行/tttenter-tao][离开]");
                    } else {
                        // 直接进入塔内
                        GameActiveUtil.pointTtt(chara);
                        GameActiveUtil.enterTongtianta(chara, null, true);
                    }
                    return;
                } else if (menu_item.indexOf("tttenter") != -1) {
                    if (chara.tongttcishu >= GameConfig.config.getBaseConfig().getTongtiantaNum()
                            && chara.tongtiantaTask == null) {
                        GameUtil.changeNpcSession(npc, "今日已经挑战完了，道友明天再来！[离开]");
                        return;
                    }
                    String bonusType = BonusType.TAO.type;
                    if (menu_item.indexOf("exp") != -1) {
                        // 选择经验奖励
                        bonusType = BonusType.EXP.type;
                    }
                    GameActiveUtil.pointTtt(chara);
                    GameActiveUtil.enterTongtianta(chara, bonusType, true);
                } else if ("更换奖励类型".equals(menu_item)) {
                    // 如果人物是空的,则让玩家领取任务
                    if (chara.tongtiantaTask == null) {
                        GameUtil.changeNpcSession(npc, "道友你还未领取任务，请速去领取任务。[离开]");
                        return;
                    } else if (chara.tongtiantaTask.getCurLayer() > chara.level) {
                        GameUtil.changeNpcSession(npc, "你的实力不俗，已经到达了突破挑战阶段，此阶段不可轻易更改奖励类型[离开]");
                        return;
                    }
                    GameUtil.changeNpcSession(npc, "道友请选择对应的奖励！ [经验/change-exp][道行/change-tao][离开]");
                    return;
                } else if (menu_item.indexOf("change") != -1) {
                    // 如果人物是空的,则让玩家领取任务
                    if (chara.tongtiantaTask == null) {
                        GameUtil.changeNpcSession(npc, "道友你还未领取任务，请速去领取任务。[离开]");
                        return;
                    } else if (chara.tongtiantaTask.getCurLayer() > chara.level) {
                        GameUtil.changeNpcSession(npc, "你的实力不俗，已经到达了突破挑战阶段，此阶段不可轻易更改奖励类型[离开]");
                        return;
                    }
                    String bonusType = BonusType.TAO.type;
                    if (menu_item.indexOf("exp") != -1) {
                        // 选择经验奖励
                        bonusType = BonusType.EXP.type;
                    }
                    chara.tongtiantaTask.setBonusType(bonusType);
                    // 选择完直接进入通天塔
                    GameActiveUtil.pointTtt(chara);
                    GameActiveUtil.enterTongtianta(chara, null, true);
                } else if (menu_item.equals("restTtt")) {
                    if (chara.tongttcishu < 1) {
                        GameUtil.changeNpcSession(npc, "道友你还有次数无需提交[离开]");
                    } else {
                        // 提交通天令牌
                        boolean find = false;
                        for (Goods g : chara.backpack) {
                            if (g.goodsInfo.str.equals("通天令牌")) {
                                find = true;
                                break;
                            }
                        }
                        if (find) {
                            chara.tongttcishu = 0;
                            GameUtil.removemunber(chara, "通天令牌", 1);
                            GameUtil.changeNpcSession(npc, "通天塔任务已重置快去挑战把![离开]");
                        } else {
                            GameUtil.changeNpcSession(npc, "道友你的背包没有#R通天令牌呢[离开]");
                        }
                    }
                    return;
                }
            }


            if (id == 1672 || id == 1673 || id == 1674) {
                if (menu_item.startsWith("助人为乐—扶危救困") && chara.taskMap.get("助人为乐—扶危救困") != null) {
                    GameUtil.changeNpcSession(npc,
                            "好心人呐，能不能打发我#R3#n积分？我已经三天没吃饭了。[这么可怜，就给他吧/donationScoreBtn][你是来敲诈的吧，看我收拾你/toFight][先看看再说]");
                    return;
                } else if (menu_item.equals("donationScoreBtn") && chara.taskMap.get("助人为乐—扶危救困") != null) {
                    GameUtil.changeNpcSession(npc, "你真的要把#R10#n积分捐给我吗？[是的，收下吧/donation10Score][我开玩笑的]");
                    return;
                } else if (menu_item.equals("donation10Score") && chara.taskMap.get("助人为乐—扶危救困") != null) {
                    if (chara.chargeScore < 3) {
                        GameUtil.sendMeTips("积分不足,无法捐助！");
                        return;
                    }
                    chara.chargeScore -= 3;
                    GameUtilRenWu.refshPointTask(chara);

                    GameUtil.sendMeTips("你给予" + npc.getName() + "#R3#n积分。");
                    if (chara.taskMap.get("助人为乐—扶危救困").currentTask.equals("助人为乐—扶危救困s1")) {
                        // 下个任务
                        GameUtilRenWu.createTask(chara, "助人为乐—扶危救困s2");
                    } else if (chara.taskMap.get("助人为乐—扶危救困").currentTask.equals("助人为乐—扶危救困s2")) {
                        // 下个任务
                        GameUtilRenWu.createTask(chara, "助人为乐—扶危救困s3");
                    } else if (chara.taskMap.get("助人为乐—扶危救困").currentTask.equals("助人为乐—扶危救困s3")) {
                        // 完成
                        GameUtilRenWu.removeTask("助人为乐—扶危救困", chara);
                        GameUtilRenWu.createTask(chara, "助人为乐—领取犒赏");
                    }
                    return;
                } else if (menu_item.equals("toFight") && chara.taskMap.get("助人为乐—扶危救困") != null) {
                    if (!chara.isFight) {
                        chara.taskMap.get("助人为乐—扶危救困").task_extra_para = "0";
                        gameObjectChar.flag = chara.taskMap.get("助人为乐—扶危救困").currentTask;
                        if (gameObjectChar.flag.equals("助人为乐—扶危救困s3")) {
                            FightManager.goFightDynamicLevel(chara,
                                    Lists.newArrayList("扶危救困劫匪", "扶危救困劫匪", "扶危救困劫匪", "扶危救困劫匪", "扶危救困劫匪"), false);
                            FightContainer fightContainer = FightManager.getFightContainer();
                            // 随机选一个说话
                            if (fightContainer != null) {
                                FightTeam fightTeamDM = FightManager.getFightTeamDM(fightContainer, chara.id);
                                int fid = fightTeamDM.fightObjectList.get(0).fid;
                                gameObjectChar.sendOne(new MSG_MESSAGE(),
                                        GameCommonUtil.npcMessage("劫匪", "哼，化妆成叫花子真麻烦，还是直接抢好了。", fid, 6202, 1));
                            }
                        } else {
                            FightManager.goFightDynamicLevel(chara,
                                    Lists.newArrayList("扶危救困百变飞贼", "扶危救困百变飞贼", "扶危救困百变飞贼", "扶危救困百变飞贼", "扶危救困百变飞贼"),
                                    false);
                        }
                        return;
                    }
                }
            }
            // 妙手道人清理背包
            if (id == 978) {
            }
            // 找米兰仙子做宠物飞升的任务
            if (id == 910) {
                PetFlyMgr.onPetFly(chara, menu_item);
            }
            // 找灵兽异人做宠物飞升任务
            if (1181 == id) {
                if (menu_item.equals("chose_pet_feiSheng")) {
                    if (chara.taskMap.get("宠物飞升") == null) {
                        Vo_MSG_SUBMIT_PET vo = new Vo_MSG_SUBMIT_PET();
                        vo.type = 2;
                        gameObjectChar.sendOne(new M_MSG_SUBMIT_PET(), vo);
                    } else {
                        GameUtil.changeNpcSession(1181, 6041, "灵兽异人", "当前已有宠物飞升任务,请去完成[离开]");
                        return;
                    }
                } else if (menu_item.equals("飞升")) {
                    PetFlyMgr.sendPetUpgradedInfo(chara);
                    return;
                }
            }

            // 在李总兵接受除暴任务
            if (id == 956) {
                if (menu_item.equals("dispatch_chubao")) {
                    if ((chara.chubao + 1) > GameConfig.config.getBaseConfig().getChubaoNum()) {
                        // 正常用户才判断
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "你今日已经完成，除暴任务了";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (gameObjectChar.gameTeam == null || gameObjectChar.gameTeam.duiwu == null
                            || gameObjectChar.gameTeam.duiwu.size() <= 0 || chara.level < 20) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "请至少组队1人，且等级大于20级以上才能除暴！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.getGameObjectChar();
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    } else if (chara.taskMap.get("为民除暴") != null) {
                        GameUtil.changeNpcSession(npc, "你已领取了除暴任务。[离开]");
                        return;
                    }
                    List<RenwuMonster> all3 = GameData.that.baseRenwuMonsterService.findByType(1);
                    Random random = new Random();
                    int i = random.nextInt(all3.size());
                    RenwuMonster renwuMonster3 = all3.get(i);
                    String name2 = renwuMonster3.getName();
                    com.fengshen.db.domain.Map map4 = GameData.that.baseMapService
                            .findOneByName(renwuMonster3.getMapName());
                    chara.npcchubao = new ArrayList<Vo_APPEAR>();
                    Vo_APPEAR vo_65529_2 = new Vo_APPEAR();
                    vo_65529_2.mapid = map4.getMapId();
                    vo_65529_2.id = GameCommonUtil.generateBossId();
                    vo_65529_2.x = renwuMonster3.getX();
                    vo_65529_2.y = renwuMonster3.getY();
                    vo_65529_2.icon = renwuMonster3.getIcon();
                    vo_65529_2.type = 2;
                    vo_65529_2.org_icon = renwuMonster3.getIcon();
                    vo_65529_2.portrait = renwuMonster3.getIcon();
                    vo_65529_2.name = name2;
                    vo_65529_2.level = chara.level;
                    vo_65529_2.leixing = 1;
                    chara.npcchubao.add(vo_65529_2);
                    Vo_61553_0 vo_61553_5 = new Vo_61553_0();
                    vo_61553_5.count = 1;
                    vo_61553_5.task_type = "为民除暴";
                    vo_61553_5.task_desc = "当前第" + chara.chubao % 10 + "轮任务：前往#R" + renwuMonster3.getMapName()
                            + "#n附近捉拿#Y#P" + name2 + "|" + renwuMonster3.getMapName() + "(" + renwuMonster3.getX() + ","
                            + renwuMonster3.getY()
                            + ")|M=就是来抓你的|$0#P#n等人。领取任务15分钟后未完成将会失败，当前剩余#R15分钟#n。（本任务队员离队、暂离、换线、下线或转移队长时会消失，任务轮次不会清除，每天只可获得20次奖励）";
                    vo_61553_5.task_prompt = "捉拿#P" + name2 + "|" + renwuMonster3.getMapName() + "("
                            + renwuMonster3.getX() + "," + renwuMonster3.getY() + ")|M=就是来抓你的|$0#P";
                    vo_61553_5.refresh = 1;
                    vo_61553_5.task_end_time = (int) (System.currentTimeMillis() / 1000L);
                    vo_61553_5.attrib = 1;
                    vo_61553_5.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
                    vo_61553_5.task_extra_para = "";
                    vo_61553_5.task_state = "1";
                    for (Chara team : gameObjectChar.gameTeam.duiwu) {
                        vo_61553_5.show_name = "为民除暴(" + team.chubao + "/"
                                + GameConfig.config.getBaseConfig().getChubaoNum() + ")";
                        if (team.chubao + 1 > GameConfig.config.getBaseConfig().getChubaoNum()) {
                            GameCommonUtil.sendTips("你今日已经完成，除暴任务了", team.id);
                            continue;
                        }
                        GameUtilRenWu.createTask(vo_61553_5, team);
                    }
                    Vo_45092_0 vo_45092_3 = new Vo_45092_0();
                    vo_45092_3.task_name = "为民除暴";
                    vo_45092_3.check_point = 40;
                    GameObjectChar.sendduiwu(new M45092_0(), vo_45092_3, chara.id);
                    GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_5.task_prompt, "为民除暴"));
                    return;
                }
            }
            if (id == 985) {
                if (menu_item.equals("五行生肖乐")) {
                    ConfigInfo wuxingConfig = GameData.that.configInfoService.getOneByKeyName("wuxing_config");
                    com.alibaba.fastjson.JSONObject parseObject = com.alibaba.fastjson.JSONObject.parseObject(wuxingConfig.getData());
                    if (parseObject == null || parseObject.getIntValue("status") == 0) {
                        GameUtil.sendMeTips("GM关闭了五行竞猜");
                        return;
                    }
                    GameUtil.sendMeTips("请注意五行竞猜消耗的是#R" + parseObject.getString("type"));

                    Vo_40995_0 vo_40995_0 = new Vo_40995_0();
                    vo_40995_0.flag = 0;
                    vo_40995_0.money = 0;
                    vo_40995_0.surlus = String.valueOf(chara.wuxingBalance);
                    vo_40995_0.overflow = "0";
                    vo_40995_0.amount = 1000;
                    vo_40995_0.choice = 0;
                    vo_40995_0.prize = 0;
                    vo_40995_0.leftCount = parseObject.getIntValue("maxCount") - chara.wuxingCount;
                    GameObjectChar.send(new M40995_0(), vo_40995_0);
                    return;
                }
            }
            if (id == 973) {
                if (menu_item.equals("我要兑换变异宠物")) {
                    Vo_53249_0 vo_53249_0 = new Vo_53249_0();
                    vo_53249_0.type = 1;
                    vo_53249_0.count = 12;
                    vo_53249_0.name0 = "伶俐鼠";
                    vo_53249_0.price0 = 100;
                    vo_53249_0.name1 = "笨笨牛";
                    vo_53249_0.price1 = 100;
                    vo_53249_0.name2 = "威威虎";
                    vo_53249_0.price2 = 100;
                    vo_53249_0.name3 = "跳跳兔";
                    vo_53249_0.price3 = 100;
                    vo_53249_0.name4 = "酷酷龙";
                    vo_53249_0.price4 = 100;
                    vo_53249_0.name5 = "花花蛇";
                    vo_53249_0.price5 = 100;
                    vo_53249_0.name6 = "溜溜马";
                    vo_53249_0.price6 = 100;
                    vo_53249_0.name7 = "咩咩羊";
                    vo_53249_0.price7 = 100;
                    vo_53249_0.name8 = "帅帅猴";
                    vo_53249_0.price8 = 100;
                    vo_53249_0.name9 = "蛋蛋鸡";
                    vo_53249_0.price9 = 100;
                    vo_53249_0.name10 = "乖乖狗";
                    vo_53249_0.price10 = 100;
                    vo_53249_0.name11 = "招财猪";
                    vo_53249_0.price11 = 100;
                    GameObjectChar.send(new M53249_0(), vo_53249_0);
                    return;
                }
                if (menu_item.equals("我要兑换神兽宠物")) {
                    Vo_53249_1 vo_53249_2 = new Vo_53249_1();
                    vo_53249_2.type = 2;
                    vo_53249_2.items = new ArrayList<>();
                    vo_53249_2.items.add(new Vo_53249_1().new Items("疆良", 100));
                    vo_53249_2.items.add(new Vo_53249_1().new Items("东山神灵", 100));
                    vo_53249_2.items.add(new Vo_53249_1().new Items("玄武", 100));
                    vo_53249_2.items.add(new Vo_53249_1().new Items("朱雀", 100));
                    vo_53249_2.items.add(new Vo_53249_1().new Items("九尾狐", 100));
                    vo_53249_2.items.add(new Vo_53249_1().new Items("白矖", 100));
                    GameObjectChar.send(new M53249_1(), vo_53249_2);
                    return;
                }
            }

            if (id == 1180 && menu_item.equals("召唤精怪")) {
                Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
                vo_9129_0.notify = 97;
                vo_9129_0.para = "PetCallDlg";
                GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
                return;
            }

            if (id == 1180 && menu_item.equals("驯化精怪")) {
                Vo_41041_0 vo_41041_0 = new Vo_41041_0();
                vo_41041_0.type = 2;
                vo_41041_0.limitNum = 0;
                vo_41041_0.count = 0;
                GameObjectChar.send(new M41041_0(), vo_41041_0);
                GameObjectChar.send(new M4155_0(), 0);
                return;
            }

            if (id == 1195) {
                if (menu_item.equals("我想领取悬赏经验")) {
                    if (chara.taskMap.get("悬赏任务") != null &&
                            chara.taskMap.get("悬赏任务").task_state.equals("finish")) {
                        int jingyan = 7975 * chara.level;
                        GameUtil.huodejingyan(chara, jingyan, "悬赏");
                        GameUtilRenWu.removeTask("悬赏任务", chara);
                        return;
                    }
                }
                if (menu_item.equals("我想领取悬赏道行")) {
                    if (chara.taskMap.get("悬赏任务") != null &&
                            chara.taskMap.get("悬赏任务").task_state.equals("finish")) {
                        GameUtil.sendMeTips("任务都没有哪来的奖励");
                        int base_pet_dh = (int) (0.29 * chara.level * chara.level * chara.level);
                        int owner_name = 34800 * chara.level
                                / ((chara.tao > base_pet_dh) ? (chara.tao / base_pet_dh) : 1);
                        GameUtil.adddaohang(chara, owner_name, "悬赏");
                        for (int i = 0; i < chara.pets.size(); ++i) {
                            if (chara.pets.get(i).id == chara.chongwuchanzhanId) {
                                PetShuXing petShuXing = chara.pets.get(i).petShuXing.get(0);
                                base_pet_dh = (int) (0.29 * petShuXing.skill * petShuXing.skill * petShuXing.skill);
                                int intimacy = 878 * petShuXing.skill
                                        / ((petShuXing.intimacy > base_pet_dh) ? (petShuXing.intimacy / base_pet_dh) : 1);
                                PetShuXing petShuXing2 = petShuXing;
                                petShuXing2.intimacy += intimacy;
                                Vo_20481_0 vo_20481_11 = new Vo_20481_0();
                                vo_20481_11.msg = "宠物获得武学#R" + intimacy;
                                vo_20481_11.time = (int) (System.currentTimeMillis() / 1000L);
                                GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_11);
                            }
                        }
                        ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
                        GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65527_0(), listVo_65527_2);
                        GameUtilRenWu.removeTask("悬赏任务", chara);
                    }
                    return;
                }
                if (menu_item.equals("领取悬赏任务")) {
                    Vo_61553_0 task = chara.taskMap.get("悬赏任务");
                    if (task != null) {
                        GameCommonUtil.dialogOk("你已经#R领取过#n悬赏任务了，赶快去完成吧。");
                        return;
                    }
                    boolean b = GameUtil.belongCalendar();
                    if (!b && gameObjectChar.privilege == 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不在任务时间段";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    if ((chara.xuanshangcishu + 1) > GameConfig.config.getBaseConfig().getXuanshangcishu()) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "今日已没有次数！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    chara.xuanshangcishu++;
                    List<RenwuMonster> findByType = GameData.that.baseRenwuMonsterService.findByType(5);
                    RenwuMonster accMap = findByType.get(new Random().nextInt(findByType.size()));
                    com.fengshen.db.domain.Map map3 = GameData.that.baseMapService.findOneByName(accMap.getMapName());
                    Vo_APPEAR Vo_APPEAR = new Vo_APPEAR();
                    Vo_APPEAR.mapid = map3.getMapId();
                    Vo_APPEAR.id = GameCommonUtil.generateBossId();
                    Vo_APPEAR.x = accMap.getX();
                    Vo_APPEAR.y = accMap.getY();
                    Vo_APPEAR.icon = accMap.getIcon();
                    Vo_APPEAR.type = 2;
                    Vo_APPEAR.org_icon = accMap.getIcon();
                    Vo_APPEAR.portrait = accMap.getIcon();
                    Vo_APPEAR.name = accMap.getName();
                    Vo_APPEAR.level = chara.level;
                    Vo_APPEAR.leixing = 4;
                    Vo_APPEAR.owner_id = chara.id;
                    Vo_APPEAR.alicename = chara.name + "的仙界叛逆";
                    GameShuaGuai.xuanshang.put(Vo_APPEAR.id, Vo_APPEAR);
                    String task_type = "悬赏任务";
                    String task_prompt = "捉拿逃窜的#P" + Vo_APPEAR.name + "|" + accMap.getMapName() + "(" + accMap.getX()
                            + "," + accMap.getY() + ")|M=追拿通缉犯|$0#P（建议组队）";
                    String show_name = "悬赏任务";

                    Vo_61553_0 vo_61553_0 = new Vo_61553_0();
                    vo_61553_0.count = 1;
                    vo_61553_0.task_type = task_type;
                    vo_61553_0.task_desc = "协助#R无名小镇#n的#Y仙界神捕#n追拿仙界逃亡人间的#R衔接叛逆#n，领取任务后2小时后未完成将会失败，当前剩余#RTIME_LEFT#n,今日已获得#R"
                            + chara.xuanshangcishu + "#n次战斗奖励。";
                    vo_61553_0.task_prompt = task_prompt;
                    vo_61553_0.refresh = 1;
                    int endTime = (int) ((System.currentTimeMillis() / 1000L + 20 * 60) - 60);
                    vo_61553_0.task_end_time = endTime;
                    vo_61553_0.attrib = 1;
                    vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I";
                    vo_61553_0.show_name = show_name;
                    vo_61553_0.task_extra_para = "";
                    vo_61553_0.task_state = "getTask";
                    GameUtilRenWu.createTask(vo_61553_0, chara);
                    GameUtil.sendMeTips("你已成功领取悬赏任务，快去完成把");
                    // 两个小时之后让任务小时
                    GameData.that.redisUtils.set("XUANSHANG;" + chara.uuid + ";" + Vo_APPEAR.id, "", 20 * 60);
                    return;
                }
            }
            // npc结束
        }

        // 如果是副本的话,就处理副本.
        if (gameMap.isDugeno()) {
            GameZone gameZone = (GameZone) gameMap;
            gameZone.gameDugeon.selectNpc(chara, id, menu_item, para);
            return;
        }
        // 帮派挑战
        if (chara.taskMap.get("帮派日常挑战") != null && menu_item.equals("【日常挑战】帮派日常挑战")) {
            int fightObjectNum = new Random().nextInt(4) + 2;
            List<String> fig = new ArrayList<>();
            while (fightObjectNum-- > 0) {
                fig.add("帮派日常陪练");
            }
            FightManager.activeBoosGoFight(chara, fig, false);
            FightContainer fc = FightManager.getFightContainer(chara.id);
            List<FightTeam> teamList = fc.teamList;
            List<FightObject> hanhua = new ArrayList<>();
            for (FightTeam f : teamList) {
                List<FightObject> fightObjectList = f.fightObjectList;
                if (fightObjectList.get(0).type == 4) {
                    hanhua.add(fightObjectList.get(0));
                    hanhua.add(fightObjectList.get(1));
                }
            }
            GameObjectChar.send(new MSG_MESSAGE(), GameCommonUtil.npcMessage("帮派日常陪练", "#R战斗结束时死亡的角色会受到惩罚。",
                    hanhua.get(0).fid, hanhua.get(0).org_icon, 1));
            GameObjectChar.send(new MSG_MESSAGE(),
                    GameCommonUtil.npcMessage("帮派日常陪练", "战胜我们就算挑战成功！", hanhua.get(1).fid, hanhua.get(1).org_icon, 1));
            log.info("帮派日常挑战");
            return;
        }

        // 帮派任务之护送天山雪莲
        if (GamePartyUtil.isIngParty(chara)) {
            if ("tsxl".equals(menu_item)) {
                GamePartyUtil.playTsxlScenariod("tsxlStep1", npc, chara);
                return;
            }
        }

        // 地府
        if ("difu".equals(menu_item)) {
            chara.x = 115;
            chara.y = 46;
            GameLine.getGameMap(chara.line, 60000).join(GameObjectCharMng.getGameObjectChar(chara.id));
            return;
        }
        // 鬼门关回天墉城
        if ("tianyong".equals(menu_item)) {
            chara.x = 95;
            chara.y = 64;
            GameLine.getGameMap(chara.line, 5000).join(GameObjectCharMng.getGameObjectChar(chara.id));
            return;
        }
        if ("wuming".equals(menu_item)) {
            chara.x = 21;
            chara.y = 83;
            GameLine.getGameMap(chara.line, 23000).join(GameObjectCharMng.getGameObjectChar(chara.id));
            return;
        }

        // 先处理证道殿、英雄会的逻辑
        // 证道殿
        if (NpcIds.isZhengDaoDianNpc(id)) {
            if (menu_item.equals(MsgUtil.WO_YAO_TIAO_ZHAN_70)) {// 挑战70
                ZhengDaoDianService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_TIAO_ZHAN_80)) {// 挑战80
                ZhengDaoDianService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_TIAO_ZHAN_90)) {// 挑战90
                ZhengDaoDianService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_TIAO_ZHAN_100)) {// 挑战100
                ZhengDaoDianService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_TIAO_ZHAN_110)) {// 挑战110
                ZhengDaoDianService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_TIAO_ZHAN_120)) {// 挑战120
                ZhengDaoDianService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_YI_DU_HU_FA)) {// 查看面板
                ZhengDaoDianService.notifyPanel(chara, id);
                GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M4155_0(), Integer.valueOf(id));
            } else {// 离开
                GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M4155_0(), Integer.valueOf(id));
            }
            return;
        }

        // 英雄会
        if (NpcIds.isHeroPubNpc(id)) {
            if (menu_item.equals(MsgUtil.WO_XIANG_SHI_70)) {// 挑战70
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_XIANG_SHI_80)) {// 挑战80
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_XIANG_SHI_90)) {// 挑战90
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_XIANG_SHI_100)) {// 挑战100
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_XIANG_SHI_110)) {// 挑战110
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_XIANG_SHI_120)) {// 挑战120
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_XIANG_SHI_130)) {// 挑战130
                HeroPubService.challenge(chara, id);
            } else if (menu_item.equals(MsgUtil.WO_YAO_YI_DU_YING_XIONG)) {// 查看面板
                HeroPubService.notifyPanel(chara, id);
                GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M4155_0(), Integer.valueOf(id));
            } else {// 离开
                GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M4155_0(), Integer.valueOf(id));
            }

            return;
        }

        // 地图守护神
        if (NpcIds.isMapGuardianNpc(id)) {
            if (menu_item.contains("看看你们的实力")) {
                MapGuardianService.challenge(chara, id);
            } else {
                gameObjectChar.sendOne(new M4155_0(), Integer.valueOf(id));
            }
            return;
        }

        if (menu_item.equals("消灭修法神兽")) {
            ArrayList<String> monsterList = new ArrayList<>();
            String monsterName = npc.getName();
            monsterList.add(monsterName);
            monsterList.add(monsterName + "分身");
            monsterList.add(monsterName + "分身");
            monsterList.add(monsterName + "分身");
            monsterList.add(monsterName + "分身");
            FightManager.activeBoosGoFight(chara, monsterList, false);
        }
        Vo_APPEAR shanggu = GameShuaGuai.shanggu.get(id);
        if ("挑战上古妖王".equals(menu_item) && shanggu != null) {
            if (GameCore.fightObject.get(id) != null) {
                GameUtil.changeNpcSession(id, 6239, "上古妖王", "急什么！吃了他们就轮到你了！[离开]");
                return;
            }
            String bossWait = GameData.that.redisUtils.get("BOSS_WAIT_" + id);
            if (bossWait != null) {
                String[] data = bossWait.split(":");
                if (!chara.name.equals(data[1])) {
                    GameUtil.changeNpcSession(id, 6239, "上古妖王", "我受#Y" + data[1] + "#n召唤而来，如果"
                            + ((Long.valueOf(data[0]) - System.currentTimeMillis()) / 1000) + "秒他还未挑战,你等方可对我发起挑战[离开]");
                    return;
                }
            }
            if (chara.level < 70) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "您低于70级，无法挑战上古妖王！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }

            if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam) && !GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 70)) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "有低于70级的成员，无法挑战上古妖王！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }

            if (GameConfig.config.getBaseConfig().getShangguNum() < chara.shanggucishu) {
                GameUtil.sendMeTips("上古妖王次数已满");
                return;
            }


            List<String> list = new ArrayList<String>();
            Random random = new Random();
            list.add("上古妖王");
            for (int j = 0; j < 9; ++j) {
                list.add(this.shanggu[random.nextInt(this.shanggu.length)]);
            }
            if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                for (final Chara duiwu : gameObjectChar.gameTeam.duiwu) {
                    duiwu.shanggucishu = duiwu.shanggucishu + 1;
                    CMD_SELECT_MENU_ITEM.refreshTask(chara);
                }
            }else{
                chara.shanggucishu = chara.shanggucishu + 1;
                CMD_SELECT_MENU_ITEM.refreshTask(chara);
            }

            FightManager.goFightDynamicLevel(chara, shanggu.level, list, false, id);
            return;
        }

        Vo_APPEAR wannian = GameShuaGuai.wannian.get(id);
        if ("挑战万年妖王".equals(menu_item) && wannian != null) {
            // 判断万年是否被人挑战
            if (GameCore.fightObject.get(id) != null) {
                GameUtil.changeNpcSession(id, 6258, "万年妖王", "急什么！吃了他们就轮到你了！[离开]");
                return;
            }
            String bossWait = GameData.that.redisUtils.get("BOSS_WAIT_" + id);
            if (bossWait != null) {
                String[] data = bossWait.split(":");
                if (!data[1].equals(chara.name)) {
                    GameUtil.changeNpcSession(id, 6258, "万年妖王", "我受#Y" + data[1] + "#n召唤而来，如果"
                            + ((Long.valueOf(data[0]) - System.currentTimeMillis()) / 1000) + "秒他还未挑战,你等方可对我发起挑战[离开]");
                    return;
                }
            }
            if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "请至少组队3人才能挑战万年妖王！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }
            if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 70)) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "有低于70级的成员，无法挑战万年妖王！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }

            if (GameConfig.config.getBaseConfig().getWannianNum() < chara.wanniancishu) {
                GameUtil.sendMeTips("万年妖王次数已满");
                return;
            }
            List<String> list = new ArrayList<String>();
            Random random = new Random();
            list.add("万年妖王");
            String[] fightName = new String[]{"万年·琵琶精", "万年·骷髅战将", "万年·千面妖", "万年·狐狸精"};
            for (int j = 0; j < 9; ++j) {
                list.add(fightName[random.nextInt(fightName.length)]);
            }
            if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                for (final Chara duiwu : gameObjectChar.gameTeam.duiwu) {
                    duiwu.wanniancishu = duiwu.wanniancishu + 1;
                    CMD_SELECT_MENU_ITEM.refreshTask(chara);
                }
            }else{
                chara.wanniancishu = chara.wanniancishu + 1;
                CMD_SELECT_MENU_ITEM.refreshTask(chara);
            }
            FightManager.goFightDynamicLevel(chara, wannian.level, list, false, id);
            return;
        }

        Vo_APPEAR guiguai = GameShuaGuai.guiguai.get(id);
        if ("挑战鬼怪".equals(menu_item) && guiguai != null) {
            if (GameCore.fightObject.get(id) != null) {
                GameUtil.changeNpcSession(id, 6113, "鬼怪", "急什么！吃了他们就轮到你了！[离开]");
                return;
            }
            String bossWait = GameData.that.redisUtils.get("BOSS_WAIT_" + id);
            if (bossWait != null) {
                String[] data = bossWait.split(":");
                if (!data[1].equals(chara.name)) {
                    GameUtil.changeNpcSession(id, 6113, "鬼怪", "我受#Y" + data[1] + "#n召唤而来，如果"
                            + ((Long.valueOf(data[0]) - System.currentTimeMillis()) / 1000) + "秒他还未挑战,你等方可对我发起挑战[离开]");
                    return;
                }
            }
            if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "请至少组队3人才能挑战鬼怪！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }
            if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 70)) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "有低于70级的成员，无法挑战鬼怪！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }
            List<String> list = new ArrayList<String>();
            Random random = new Random();
            list.add("宝图·僵尸王");
            String[] fightName = new String[]{"宝图·僵尸王", "宝图·凶魂"};
            for (int j = 0; j < 4; ++j) {
                list.add(fightName[random.nextInt(fightName.length)]);
            }

            FightManager.goFightDynamicLevel(chara, guiguai.level, list, false, id);
            return;
        }

        Vo_61553_0 zhuxian1 = chara.taskMap.get("主线—浮生若梦");
        // 多闻道人的第一个任务
        if (id == 1140 && menu_item.equals("主线—浮生若梦_s1") && zhuxian1 != null
                && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(387));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }

        // 王老板的第二个任务
        if (id == 1145 && "主线—浮生若梦_s3".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "公子真是好福缘啊，那日被救回来的时候浑身都是伤，你看看这才几天就生龙活虎的了。正好这丹药也炼好了，快服下吧。",
                    "主线—浮生若梦", 6011, "王老板");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 黄仨儿
        if (id == 1141 && "主线—浮生若梦_s4".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(402));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 与宠物一起战斗
        if (id == 1141 && "主线—浮生若梦_s5".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            zhuxian1.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(405));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 请教莲花姑娘
        if (id == 1142 && "主线—浮生若梦_s6".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(412));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 赵老板
        if (id == 1143 && "主线—浮生若梦_s7".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s7";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(417));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 张老板
        if (id == 1144 && "主线—浮生若梦_s9".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s9";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(424));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if (id == 1143 && "主线—浮生若梦_s10".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s10";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(662));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if (id == 1142 && "主线—浮生若梦_s11".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s11";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(379));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 官道南找强盗
        if (id == 55555555 && "主线—浮生若梦_s12".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            zhuxian1.task_extra_para = "0";
            chara.current_task = "主线—浮生若梦_s12";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(288));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if (id == 1085 && "主线—浮生若梦_s14".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s14";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(299));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 神龙真人
        if (id == 1086 && "主线—浮生若梦_s15".equals(menu_item) && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s15";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(304));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if (id == 961 && "主线—浮生若梦_s20".equals(menu_item) && zhuxian1 != null
                && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s20";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(330));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 拜师学艺
        if ((id == 832 || id == 944 || id == 1020 || id == 1069 || id == 1108) && "主线—浮生若梦_s22".equals(menu_item)
                && zhuxian1 != null && zhuxian1.currentTask.equals(menu_item)) {
            zhuxian1.task_state = "1";
            chara.current_task = "主线—浮生若梦_s22";
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(341));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 主线拜师
        Vo_61553_0 shimenTask = chara.taskMap.get("主线—拜入师门");
        if ((id == 832 || id == 944 || id == 1020 || id == 1069 || id == 1108) && shimenTask != null) {
            int icon = GameCommonUtil.shimen_tongzi_icon[chara.polar - 1];
            if ("主线—拜入师门s1".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
                shimenTask.task_state = "1";
                chara.current_task = "主线—拜入师门s1";
                Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我欲授你本门道法玄妙，不过在这之前你需要先了解一下不同的战斗偏向，它将极大的影响你未来的前景！",
                        "主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar - 1]);
                GameObjectChar.send(new M45056_0(), vo_45056_2);
                return;
            }
            if ("主线—拜入师门s2".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
                chara.current_task = menu_item;
                // 直接打开界面
                GameUtil.openDlg("SkillDlg");
                int step = Integer.valueOf(shimenTask.task_state);
                String[] skill = new String[]{"金光乍现", "摘叶飞花", "滴水穿石", "举火焚天", "落土飞岩"};
                if (step == 1) {
                    GameUtil.sendMeTips("请切换到#R物攻#n选项,将#Y力破千钧#n提升至#n16级");
                } else if (step == 2) {
                    GameUtil.sendMeTips("请切换到#R法功#n选项,将#Y" + skill[chara.polar - 1] + "#n提升至#n16级");
                } else {
                    GameUtil.sendMeTips("请切换到#R法攻#n选项或#R物攻#n选项,将#Y力破千钧#n提升至#n16级");
                }
                return;
            }
            // 技能学完复命
            if ("主线—拜入师门s3".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
                shimenTask.task_state = "1";
                shimenTask.task_extra_para = "0";
                chara.current_task = "主线—拜入师门s3";
                Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "很好，你已经成功掌握了学习技能的方法。随着你等级的提高，你还可以随时在#R角色技能#n界面学习新的技能。",
                        "主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar - 1]);
                GameObjectChar.send(new M45056_0(), vo_45056_2);
                return;
            }
            // 接下来有何安排
            if ("主线—拜入师门s4".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
                shimenTask.task_state = "1";
                chara.current_task = "主线—拜入师门s4";
                Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不错，还是很有天赋嘛。现在正好有一件要紧之事交给你。", "主线—拜入师门", icon,
                        GameCommonUtil.shimen_tongzi[chara.polar - 1]);
                GameObjectChar.send(new M45056_0(), vo_45056_2);
                return;
            }
        }
        if ("主线—拜入师门s5".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(373));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s6".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(373));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 孩子不要跑
        if ("主线—拜入师门s7".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
                    GameData.that.baseNpcDialogueService.findById(665));
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 蒙面人
        if ("主线—拜入师门s8".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            shimenTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你是何人，快把这孩子放开！！", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }

        if ("主线—拜入师门s9".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子带一走失的孩子回家，却不料那孩子被一神秘蒙面人劫持，弟子担心他受伤，只能任由那人逃了，请师尊责罚！",
                    "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s10".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            String[] polar = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞"};
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这" + polar[chara.polar - 1] + "也是一派大家气象，却不想气量如此狭小。",
                    "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s11".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不知今日是否有贵派的外门弟子去了天墉城，请如实相告。", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s12".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞"};
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
                    "那孩子确是被" + att_name[chara.polar - 1] + "门下的弟子抓去了，是因门派邀斗结下了私仇，那人气不过，所以拐了人相邀再战。", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s13".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
                    "我欲前往营救被绑架的孩子，为保诸事妥当，" + GameCommonUtil.shimen_tongzi[chara.polar - 1] + "让我先来向你学习召唤守护之术。",
                    "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s14".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
                    "召唤守护之术果然深通，这下肯定万无一失了！多谢" + GameCommonUtil.shimen_zhanglao[chara.polar - 1] + "！", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s15".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            shimenTask.task_extra_para = "0";
            chara.current_task = menu_item;
            int[] att_icon = new int[]{6004, 6001, 7002, 7003, 7005};
            String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞"};
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "什么，你们竟然来了这么多人，想以多欺少么？", "主线—拜入师门",
                    att_icon[chara.polar - 1], att_name[chara.polar - 1]);
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s16".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "被绑的孩子已平安归来，凶手我也捆回来了，不知该如何处理？", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s17".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不知师父接下来有何安排？", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s19".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "干的不错，现有一重要任何安排你，前些日子我们在天墉城段铁心那里定制了一批武器，想来已经打制完毕，需有人押运回来。",
                    "主线—拜入师门", GameCommonUtil.shimen_tongzi_icon[chara.polar - 1],
                    GameCommonUtil.shimen_tongzi[chara.polar - 1]);
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s20".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            shimenTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "实不相瞒，铁匠铺后头院子里有妖异之物，弄得人心惶惶，伙计皆逃回家中，这武器，怕是无法按时交付了。",
                    "主线—拜入师门", npc.getIcon(), npc.getName());
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s22".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            shimenTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "原来是只不入流的鸟怪。", "主线—拜入师门", 6211, "赤羽鸟怪");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s23".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            shimenTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "还真是不知天高地厚的家伙，兄弟们，给我上！", "主线—拜入师门", 6211, "赤羽鸟怪");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s24".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "老人家，你砍柴时可有发现妖怪？", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s25".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道友来的却不是时候，前些日子太乙真人派人取走了所有的现形符，我云游四方昨日才归，尚未来得及绘制。",
                    "主线—拜入师门", 6053, "玉真子");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s26".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "降妖除魔，我辈本分应该，这照妖镜，就借给你了。", "主线—拜入师门", 6042, "柳如尘");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 使用照妖镜
        if ("主线—拜入师门s27".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            // 特效
            Map<String, Object> map2 = new LinkedHashMap<String, Object>();
            map2.put("id", 66666666);
            map2.put("effect_no", 6049);
            map2.put("order", 0);
            map2.put("post", (byte) 1);
            map2.put("x", 14);
            map2.put("y", 64);
            map2.put("loops", 0);
            map2.put("interval", 0);
            map2.put("during", 0);
            GameObjectCharMng.getGameObjectChar(chara.id).gameMap.send(new CommonWrite(0xB073), map2);
            // 创建npc
            Vo_APPEAR vo_APPEAR = new Vo_APPEAR();
            vo_APPEAR.mapid = 11000;
            vo_APPEAR.id = 66666666;
            vo_APPEAR.x = 14;
            vo_APPEAR.y = 64;
            vo_APPEAR.icon = 6206;
            vo_APPEAR.type = 2;
            vo_APPEAR.org_icon = 6206;
            vo_APPEAR.portrait = 6206;
            vo_APPEAR.name = "蟒精";
            vo_APPEAR.dir = 4;
            gameObjectChar.sendOne(new M65529_0(), vo_APPEAR);
            // 创建下个任务
            GameUtil.renwujiangli(chara);
            chara.current_task = "主线—拜入师门s28";
            Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
            // 创建主线任务
            GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
            // 对话
            gameObjectChar.sendOne(new MSG_MESSAGE(),
                    GameCommonUtil.npcMessage("蟒精", "啊！好刺眼的亮光，我的易容术怎么失效了！？", 66666666, 6206, 1));
            return;
        }
        if ("主线—拜入师门s28".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            shimenTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "可恶，你这小子，竟敢坏我好事！", "主线—拜入师门", 6206, "蟒精");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s29".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那蟒精在桃柳林聚集妖孽，弟子正要拷问，却突然口吐鲜血，爆体而亡！", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        if ("主线—拜入师门s30_2".equals(menu_item) && shimenTask.currentTask.equals(menu_item)) {
            shimenTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我来领取新宠物。", "主线—拜入师门");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 主线—山雨欲来
        Vo_61553_0 shanyuTask = chara.taskMap.get("主线—山雨欲来");
        if ("主线—山雨欲来s1".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "此次下山修行，命你在世间行走，观世间百态，修世间百态，修世而砺心，惩恶扬善。", "主线—山雨欲来",
                    GameCommonUtil.shimen_tongzi_icon[chara.polar - 1],
                    GameCommonUtil.shimen_tongzi[chara.polar - 1]);
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s2".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "冯掌柜因何事如此苦闷？", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s3".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "老头，我想请您帮忙找个人。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s4".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "老头，我想请您帮忙找个人。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s5".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "老头，报酬取回来了，不知那恶霸......", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s6".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "真是踏破铁鞋无觅处，得来全不费工夫，你们就是在冯喜来那里白吃白喝的家伙吧！", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s7".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小道士，休要多管闲事。", "主线—山雨欲来", 6140, "妖风");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s8".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "前辈，附近可有什么妖异出没？", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s9".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "刚刚吃个饱，就又有送上门的美味啦。", "主线—山雨欲来", 6204, "琵琶精");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s10".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "多谢真人救命之恩。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s11".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "前辈，那琵琶精已被陆压真人除去。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s12".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
                    "弟子于轩辕坟中为妖孽所害，幸得陆压真人相助才得以解脱，真人怀疑此事定有幕后主使，命弟子剿灭轩辕坟中残余妖孽，弟子不敢托大，特前来禀告。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s13".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子奉命于世间修行，发现轩辕坟中妖孽横生，故弟子将此事禀告师尊，师尊特命弟子前来听候差遣。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s14".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（找了这么久什么都没有发现，不如先问问那个渔夫吧）#n这位朋友，最近这一带可有什么诡异之事？",
                    "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s15".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那渔夫说的就是这了吧，这亮光是？", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s16".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小道士，此乃老龙修行之地，老龙可一向潜心修道，与民无犯，不知你来此何事啊？", "主线—山雨欲来", 6117,
                    "北海乌龙");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s17".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "定魂香我已给你找回来了，快用了它轮回去吧。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s18".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小道士，怎么又回来了？难道是定魂香不够用吗？", "主线—山雨欲来", 6117, "北海乌龙");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s20".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "冯掌柜，那恶霸已经被我教训了一顿，以后再也不敢来了，你就放心吧。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s21".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "听闻千面怪你神通广大，有千变万化之能，小道特来拜会。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        } else if ("主线—山雨欲来s22".equals(menu_item) && shanyuTask != null && shanyuTask.currentTask.equals(menu_item)) {
            shanyuTask.task_state = "1";
            shanyuTask.task_extra_para = "0";
            chara.current_task = menu_item;
            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你这虎妖竟敢来天墉城作乱，看我来收拾你。", "主线—山雨欲来");
            GameObjectChar.send(new M45056_0(), vo_45056_2);
            return;
        }
        // 妖魔道
        Vo_61553_0 yaomodao = chara.taskMap.get("妖魔道");
        if ("妖魔道—勇擒鱼怪s1".equals(menu_item) && yaomodao != null) {
//			yaomodao.task_state = "1";
//			chara.current_task = menu_item;
//			Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "前些日子我采购了一批药材，恰巧最近镇外又来了一批妖魔，为首是一直鱼怪，这批药还没见着影就被他们劫去了。本来也不是什么值钱的东西，但我担心这些妖魔弄出什么花样来。",
//					"妖魔道—勇擒鱼怪",6011,"王老板");
//			GameObjectChar.send(new M45056_0(), vo_45056_2);
            GameUtil.sendMeTips("即将更新！！！");
            chara.current_task = menu_item;
            return;
        }

        if (menu_item.equals("zhuan")) {
            if (chara.realLevel < 179) {
                GameUtil.sendMeTips("道友等级未达到#Y179级");
                return;
            }
            int goodsNum = GameCommonUtil.getGoodsNum(chara, "转世灵符");
            if (goodsNum < 1) {
                GameUtil.sendMeTips("你的#Y转世灵符#W不足#R1个");
                return;
            }

            if (chara.zhuan >= 9) {
                GameUtil.sendMeTips("#Y你已达到最大转世等级!");
                return;
            }

            //
            ArrayList<String> l = new ArrayList<>();
           // for (int t = 1; t <= 10; ++t) {
            GameUtil.removemunber(chara, "转世灵符", 1);
            l.add("转世灵兽");
            //}
            FightManager.activeBoosGoFight(chara, l, false);

            // 	chara.zhuan =  chara.zhuan + 1;
            // 	String chenghao = "";
            // 	if (chara.zhuan == 1)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao1;
            // 	}
            // 	else if (chara.zhuan == 2)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao2;
            // 	}
            // 	else if (chara.zhuan == 3)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao3;
            // 	}
            // 	else if (chara.zhuan == 4)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao4;
            // 	}
            // 	else if (chara.zhuan == 5)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao5;
            // 	}
            // 	else if (chara.zhuan == 6)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao6;
            // 	}
            // 	else if (chara.zhuan == 7)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao7;
            // 	}
            // 	else if (chara.zhuan == 8)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao8;
            // 	}
            // 	else if (chara.zhuan == 9)
            // 	{
            // 		chenghao = GameConfig.config.getBaseConfig().hao9;
            // 	}
            // 	chara.chenhao = chenghao;
            // 	chara.realLevel = 70;
            // 	chara.level = 70;


            // 	ChengweiService cs = SpringBeanUtils.getBean(ChengweiService.class);
            // 	Chengwei newChengwei = cs.getChengweiByName(chenghao);
            // //重新计算角色信息
            // 	//GameCommonUtil.computeDeltaChengwei(chara, newChengwei, false);
            // //重新计算伤害
            // 	chara.chenghao.put(chenghao, chenghao);

            //  	GameUtil.chenghaoxiaoxi(chara, newChengwei.getName(), newChengwei.getName());
            // 	 GameUtil.a65511(gameObjectChar);
            // 	GameUtil.zhuangbeiValue(gameObjectChar);

            // 	GameUtil.sendMeTips(chara.zhuan+"转成功!");
            // 	GameUtil.sendUpdate(chara);

            return;
        }


        // 处理挑战掌门
        if (menu_item.equals("挑战掌门")) {
            if (chara.level < 70) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "你未满70级，无法继续进行！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.getGameObjectChar();
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }
            if (chara.zhangmentiaozhan >= GameConfig.config.getBaseConfig().getZmcs()) {
                Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                vo_20481_2.msg = "你今天已经挑战过" + GameConfig.config.getBaseConfig().getZmcs() + "次了！";
                vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_2);
                return;
            }
            String[] baseInfo = new String[]{"朱雀", "疆良", "玄武"};
            List<String> list2 = new ArrayList<String>();
            int polar = chara.polar;
            if (polar == 4 && id == 1105) {
                String name = "火系掌门";
                CharaStatue charStaure = CharaStatueService.getCharStaure(name);
                if (charStaure != null) {
                    if (charStaure.level - chara.level >= 19) {
                        GameUtil.changeNpcSession(id, 6004, name, "道友还是前去修炼，在来与我挑战。[离开]");
                        return;
                    } else if (GameConfig.canzhanBoos.get("挑战掌门[火系掌门]_" + id) != null) {
                        GameUtil.changeNpcSession(id, 6004, name, "岂能被车轮战，等其他人挑战完再来。[离开]");
                        return;
                    } else {
                        GameConfig.canzhanBoos.put("挑战掌门[火系掌门]_" + id, id);
                        chara.zhandouInfo = "挑战掌门[火系掌门]_" + id;
                    }
                }
                list2.add("火系掌门");
                list2.add(baseInfo[new Random().nextInt(baseInfo.length)]);
                FightManager.goFightZhangMen(chara, list2, "火系掌门", id);
            } else if (polar == 5 && id == 941) {
                CharaStatue charStaure = CharaStatueService.getCharStaure("土系掌门");
                if (charStaure != null) {
                    if (charStaure.level - chara.level >= 19) {
                        GameUtil.changeNpcSession(id, 6005, "土系掌门", "道友还是前去修炼，在来与我挑战。[离开]");
                        return;
                    } else if (GameConfig.canzhanBoos.get("挑战掌门[土系掌门]_" + id) != null) {
                        GameUtil.changeNpcSession(id, 6005, "土系掌门", "岂能被车轮战，等其他人挑战完再来。[离开]");
                        return;
                    } else {
                        GameConfig.canzhanBoos.put("挑战掌门[土系掌门]_" + id, id);
                        chara.zhandouInfo = "挑战掌门[土系掌门]_" + id;
                    }
                }
                list2.add("土系掌门");
                list2.add(baseInfo[new Random().nextInt(baseInfo.length)]);
                FightManager.goFightZhangMen(chara, list2, "土系掌门", id);
            } else if (polar == 3 && id == 1017) {
                CharaStatue charStaure = CharaStatueService.getCharStaure("水系掌门");
                if (charStaure != null) {
                    if (charStaure.level - chara.level >= 19) {
                        GameUtil.changeNpcSession(id, 6003, "水系掌门", "道友还是前去修炼，在来与我挑战。[离开]");
                        return;
                    } else if (GameConfig.canzhanBoos.get("挑战掌门[水系掌门]_" + id) != null) {
                        GameUtil.changeNpcSession(id, 6003, "水系掌门", "岂能被车轮战，等其他人挑战完再来。[离开]");
                        return;
                    } else {
                        GameConfig.canzhanBoos.put("挑战掌门[水系掌门]_" + id, id);
                        chara.zhandouInfo = "挑战掌门[水系掌门]_" + id;
                    }
                }
                list2.add("水系掌门");
                list2.add(baseInfo[new Random().nextInt(baseInfo.length)]);
                FightManager.goFightZhangMen(chara, list2, "水系掌门", id);
            } else if (polar == 1 && id == 829) {
                CharaStatue charStaure = CharaStatueService.getCharStaure("金系掌门");
                if (charStaure != null) {
                    if (charStaure.level - chara.level >= 119) {
                        GameUtil.changeNpcSession(id, 6001, "金系掌门", "道友还是前去修炼，在来与我挑战。[离开]");
                        return;
                    } else if (GameConfig.canzhanBoos.get("挑战掌门[金系掌门]_" + id) != null) {
                        GameUtil.changeNpcSession(id, 6001, "金系掌门", "岂能被车轮战，等其他人挑战完再来。[离开]");
                        return;
                    } else {
                        GameConfig.canzhanBoos.put("挑战掌门[金系掌门]_" + id, id);
                        chara.zhandouInfo = "挑战掌门[金系掌门]_" + id;
                    }
                }
                list2.add("金系掌门");
                list2.add(baseInfo[new Random().nextInt(baseInfo.length)]);
                FightManager.goFightZhangMen(chara, list2, "金系掌门", id);
            } else if (polar == 2 && id == 1066) {
                CharaStatue charStaure = CharaStatueService.getCharStaure("木系掌门");
                if (charStaure != null) {
                    if (charStaure.level - chara.level >= 19) {
                        GameUtil.changeNpcSession(id, 6002, "木系掌门", "道友还是前去修炼，在来与我挑战。[离开]");
                        return;
                    } else if (GameConfig.canzhanBoos.get("挑战掌门[木系掌门]_" + id) != null) {
                        GameUtil.changeNpcSession(id, 6002, "木系掌门", "岂能被车轮战，等其他人挑战完再来。[离开]");
                        return;
                    } else {
                        GameConfig.canzhanBoos.put("挑战掌门[木系掌门]_" + id, id);
                        chara.zhandouInfo = "挑战掌门[木系掌门]_" + id;
                    }
                }
                list2.add("木系掌门");
                list2.add(baseInfo[new Random().nextInt(baseInfo.length)]);
                FightManager.goFightZhangMen(chara, list2, "木系掌门", id);
            } else {
                String name = "";
                Integer i = 0;
                if (id == 1105) {
                    // 木
                    name = "火系掌门";
                    i = 4;
                } else if (id == 829) {
                    // 金
                    name = "金系掌门";
                    i = 1;
                } else if (id == 1017) {
                    // 水
                    name = "水系掌门";
                    i = 3;
                } else if (id == 941) {
                    // 土
                    name = "土系掌门";
                    i = 5;
                } else {
                    name = "木系掌门";
                    i = 2;
                }
                CharaStatue charStaure = CharaStatueService.getCharStaure(name);
                int icon = GameUtil.getWaiguan(i, charStaure.sex, null);
                GameUtil.changeNpcSession(id, icon, name, "你不是我门派弟子,怎么能挑战我呢！[离开]");
                return;
            }
        }
        // 添加查看掌门信息
        else if (menu_item.equals("我要一睹掌门风采")) {
            ChallengeLeaderService.notifyLeaderInfo(GameUtil.getPolar(npc.getName()));
        }
        // 进入证道殿
        else if (menu_item.equals(MsgUtil.JIN_RU_ZHENG_DAO_DIAN)) {// 进入证道殿
            if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                    && gameObjectChar.gameTeam.duiwu.size() > 0) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "不能组队进入证道殿";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }
            if (chara.level < 70) {
                Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                vo_20481_10.msg = "低于70级的角色不能进入证道殿！";
                vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.getGameObjectChar();
                GameObjectChar.send(new M20481_0(), vo_20481_10);
                return;
            }
            if ((chara.polar == 4 && id == 1105) || (chara.polar == 5 && id == 941)
                    || (chara.polar == 3 && id == 1017) || (chara.polar == 1 && id == 829)
                    || (chara.polar == 2 && id == 1066)) {
                com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName("证道殿");
                chara.y = map.getY().intValue();
                chara.x = map.getX().intValue();
                GameLine.getGameMapname(chara.line, "证道殿").join(GameObjectCharMng.getGameObjectChar(chara.id));
            } else {
                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                vo_20481_0.msg = "你不是我门派弟子,怎么能进入我门派证道殿呢！";
                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_0);
                return;
            }
        } else {
            if (npc != null) {
                // 北斗神将通天塔
                if (id == 1200) {
                    if ("传送".equals(menu_item)) {
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        // 未完成
                        if (chara.getTongtiantaTask().getCurType() == 1) {
                            vo_20481_2.msg = "修行悟道须讲究循环渐进，你尚未通过第#R" + chara.getTongtiantaTask().getCurLayer()
                                    + "#n层的调整，无法进阶至更高的塔层。";
                            vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.send(new M20481_0(), vo_20481_2);
                            return;
                        } else {
                            GameActiveUtil.tongtiantaGoNextLayer();
                        }

                    }
                    if ("出塔".equals(menu_item)) {
                        com.fengshen.db.domain.Map map = GameData.that.baseMapService.findOneByName("天墉城");
                        chara.y = 16;
                        chara.x = 114;
                        GameLine.getGameMapname(chara.line, map.getName())
                                .join(GameObjectCharMng.getGameObjectChar(chara.id));
                    }
                }
                if (id == 1201 && "挑战玉衡星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }

                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"玉衡星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("玉衡星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
                if (id == 1202 && "挑战天权星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }

                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"天权星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("天权星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
                if (id == 1203 && "挑战天玑星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }

                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"天玑星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("天玑星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
                if (id == 1204 && "挑战天璇星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }

                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"天璇星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("天璇星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
                if (id == 1205 && "挑战天枢星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"天枢星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("天枢星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
                if (id == 1206 && "挑战摇光星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"摇光星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("摇光星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
                if (id == 1207 && "挑战开阳星君".equals(menu_item)) {
                    if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                            && gameObjectChar.gameTeam.duiwu.size() > 0) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "不能组队挑战星君！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    if (chara.tongtiantaTask.getCurType() == 2) {
                        vo_20481_2.msg = "汝已参透此处玄机，佩服，佩服。快去找#Y北斗神将#n传送到更高层把！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    if (!"开阳星君".equals(chara.tongtiantaTask.getNpc().substring(0,
                            chara.tongtiantaTask.getNpc().indexOf("(") == -1 ? chara.tongtiantaTask.getNpc().length()
                                    : chara.tongtiantaTask.getNpc().indexOf("(")))) {
                        vo_20481_2.msg = "本层由#Y" + chara.tongtiantaTask.getNpc().substring(0,
                                chara.tongtiantaTask.getNpc().indexOf("(") == -1
                                        ? chara.tongtiantaTask.getNpc().length()
                                        : chara.tongtiantaTask.getNpc().indexOf("("))
                                + "#n负责把守。";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Random random = new Random();
                    String cw = this.tongttcw[random.nextInt(this.tongttcw.length)];
                    List<String> list3 = new ArrayList<String>();
                    list3.add("开阳星君");
                    List<String> list4 = new ArrayList<String>();
                    list4.add(cw);
                    FightManager.goFightTtt(chara, list3, list4);
                }
            }
            // 充值积分比例， 充值领取
            if (id == 1151 || "赠送元宝".equals(menu_item)) {
                if (menu_item.equals("赠送元宝")) {
                    Characters characters = GameData.that.characterService.findById(chara.id);
                    Accounts accounts = GameData.that.baseAccountsService.findById(characters.getAccountId());
                    List<Charge> chargeList = (List<Charge>) GameData.that.baseChargeService
                            .findByAccountname(accounts.getName());
                    long goldCoinSumL = 0;
                    int goldCoinSum = 0;
                    if (chargeList != null && !chargeList.isEmpty()) {
                        // 元宝
                        goldCoinSumL = chargeList.stream().filter(c -> c.getState().equals(0)).mapToLong(Charge::getCoin).sum();
                        if (goldCoinSumL > 0) {
                            if (goldCoinSumL > 2000000000) {
                                goldCoinSum = 2000000000;
                            } else {
                                goldCoinSum = (int) goldCoinSumL;
                            }
                            // 添加金元宝
                            GameUtil.addJinYuanBao(gameObjectChar, goldCoinSum, "充值奖励");
                            int jifen = goldCoinSum * GameConfig.config.getBaseConfig().getChongzhibili();
                            chara.shadow_self += jifen;
                            // 添加积分
                            GameUtil.addchargeScore(gameObjectChar, jifen, "充值奖励");

                            Map<String, Object> dataMap = new HashMap<>();
                            dataMap.put("lottery_times", chara.shadow_self);
                            dataMap.put("gold_coin", chara.goldCoin);
                            GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, dataMap));

                            Vo_20481_0 vo_20481_6 = new Vo_20481_0();
                            vo_20481_6.msg = "领取元宝成功";
                            vo_20481_6.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_6);
                            // 如果累计充值多少
                            int sum = chargeList.stream().mapToInt(Charge::getMoney).sum();
                            // 获取称谓
                            ChengweiService chengweiService = SpringBeanUtils.getBean(ChengweiService.class);
                            List<Chengwei> chengweis = chengweiService.getChengweiMoney(sum,"累计");
                            if (chengweis != null && !chengweis.isEmpty()) {
                                Iterator<Chengwei> iterator = chengweis.iterator();
                                while (iterator.hasNext()) {
                                    Chengwei chengwei = iterator.next();
                                    if (chara.getChenghao().get(chengwei.getName()) != null) {
                                        // 称谓已经获取了,无需再次获取
                                        iterator.remove();
                                    }
                                }
                                for (Chengwei chengwei : chengweis) {
                                    // 称号
                                    GameUtil.chenghaoxiaoxi(chara, chengwei.getName(), chengwei.getName());
                                    GameUtil.sendMeTips("恭喜你获得#R" + chengwei.getName() + "#n称谓。");
                                    GameUtil.sendSystemMessage(19, "热烈恭喜#Y" + chara.name + "#n玩家累计充值达到了#R"
                                            + chengwei.getMoney() + "元#n获得了系统赠送的#R" + chengwei.getName() + "#n称谓，真是可喜可贺。");
                                }
                            }
                            //更新状态
                            for (Charge charge : chargeList) {
                                // 根据比例计算元宝
                                charge.setState(1);
                                charge.setCode(accounts.getRegisterCode());
                                GameData.that.baseChargeService.updateById(charge);
                            }
                        }
                    }
                    if (goldCoinSum <= 0) {
                        Vo_20481_0 vo_20481_7 = new Vo_20481_0();
                        vo_20481_7.msg = "暂无可领取的元宝";
                        vo_20481_7.time = (int) (System.currentTimeMillis() / 1000L);
                        gameObjectChar.sendOne(new M20481_0(), vo_20481_7);
                    }
                }
                return;
            }
            if (id == 1170) {
                if (menu_item.equals("离开战场")) {
                    GameUtil.confirm(chara, "确定离开试道场内吗？离开后如若试道开始则无法再次进入。", "leaveShiDaoMap");
                } else if ("gmEnterSd".equals(menu_item)) {
                    // 查询该角色是否为GM
                    if (gameObjectChar.privilege == 1000) {
                        StringBuilder menus = new StringBuilder();
                        menus.append("亲爱的#RGM#n请选择你要进入的阶段试道场[70-79/gmsd-70]").append("[80-89/gmsd-80][90-99/gmsd-90]")
                                .append("[100-109/gmsd-100][110-119/gmsd-110][120-129/gmsd-120]")
                                .append("[130-139/gmsd-130]");
                        GameUtil.changeNpcSession(npc, menus.toString());
                        return;
                    }
                } else if (menu_item.startsWith("gmsd-") && gameObjectChar.privilege == 1000) {
                    //获取动态试道地图
                    String level = menu_item.split("-")[1];
                    String shiDaoJieDuan = GameShiDao.getShiDaoJieDuan(Integer.valueOf(level));
                    List<GameZone> list = GameShiDao.maps.get(shiDaoJieDuan);
                    StringBuilder msg = new StringBuilder();
                    msg.append("该阶段分为#R").append(list.size()).append("#n个子地图，请选择需要进入的地图");
                    for (GameZone gz : list) {
                        int num = gz.sessionList.size();
                        if (num > 0) {
                            msg.append("[").append(num).append("人").append("/gmen-").append(level).append("-").append(gz.uid).append("]");
                        }
                    }
                    msg.append("[返回/gmEnterSd]");
                    GameUtil.changeNpcSession(npc, msg.toString());
                } else if (menu_item.startsWith("gmen-") && gameObjectChar.privilege == 1000) {
                    String level = menu_item.split("-")[1];
                    String uid = menu_item.split("-")[2];
                    String shiDaoJieDuan = GameShiDao.getShiDaoJieDuan(Integer.valueOf(level));
                    List<GameZone> list = GameShiDao.maps.get(shiDaoJieDuan);
                    for (GameZone gz : list) {
                        if (gz.uid.equals(uid)) {
                            chara.x = 31;
                            chara.y = 47;
                            gz.join(gameObjectChar);
                            GameUtil.sendMeTips("亲爱的#RGM#n,欢迎光临#R" + level + "#n阶段试道场");
                            gameObjectChar.useGmAuth = "enterGmAuthShiDao";
                            break;
                        }
                    }
                }
                return;
            }

            // 试道相关
            if (menu_item.equals("开始战斗")) {
                if (gameObjectChar.gameTeam == null) {
                    GameUtil.sendMeTips("请组队。");
                    return;
                }
                if (chara.mapName.equals("试道场") && GameShiDao.statzhuangtai == 2) {
                    for (int l = 0; l < gameObjectChar.gameMap.gameShiDao.shidaoyuanmo.size(); ++l) {
                        if (id == gameObjectChar.gameMap.gameShiDao.shidaoyuanmo.get(l).id) {
                            List<String> list = new ArrayList<String>();
                            int mon = 2;
                            if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null) {
                                mon = mon * gameObjectChar.gameTeam.duiwu.size();
                            }
                            for (int m = 0; m < mon; ++m) {
                                list.add("试道元魔");
                            }
                            FightManager.activeBoosGoFight(chara, list, false);
                            gameObjectChar.gameMap.send(new MSG_DISAPPEAR(), id);
                            gameObjectChar.gameMap.gameShiDao.shidaoyuanmo
                                    .remove(gameObjectChar.gameMap.gameShiDao.shidaoyuanmo.get(l));
                            break;
                        }
                    }
                } else {
                    GameCommonUtil.fuckBastard(gameObjectChar);
                }
                return;
            }
            if (id == 962) {
                String shidaoname = GameUtilRenWu.shidaolevel(chara);
                // 领取奖励
                if ("shidaoReward".equals(menu_item)) {
                    if (chara.shidaoExp > 0) {
                        try {
                            GameUtil.huodejingyan(chara, chara.shidaoExp, "试道");
                        } finally {
                            chara.shidaoExp = 0;
                        }
                    }
                    if (chara.shidaoTao > 0) {
                        try {
                            GameUtil.adddaohang(chara, chara.shidaoTao);
                        } finally {
                            chara.shidaoTao = 0;
                        }
                    }
                    if (chara.shidaoMartial > 0) {
                        try {
                            GameCommonUtil.addWuXue(chara, chara.shidaoMartial);
                        } finally {
                            chara.shidaoMartial = 0;
                        }
                    }
                } else if ("世道进入".equals(menu_item)) {
                    if (shidaoname.equals("notStart")) {
                        String[] times = GameConfig.config.getShidao().getTimes();
                        String week = Arrays.toString(GameConfig.config.getShidao().getWeek());
                        week = week.substring(1, week.length() - 1);
                        GameUtil.changeNpcSession(npc, "现在还不是报名时间,试道大会下一场将于#R" + times[0] + "#W开始报名。[离开]");
                        return;
                    } else if (shidaoname.equals("wzd")) {
                        GameUtil.changeNpcSession(npc, "请组队[离开]");
                        return;
                    } else if (shidaoname.equals("djbt")) {
                        GameUtil.changeNpcSession(npc, "队伍等级不符合条件！[离开]");
                        return;
                    } else {
                        StringBuilder msg = new StringBuilder("队伍中:");
                        for (int i = 0; i < gameObjectChar.gameTeam.duiwu.size(); ++i) {
                            Chara team = gameObjectChar.gameTeam.duiwu.get(i);
                            if (team.upgrade_state != 0) {
                                msg.append("#Y").append(team.name).append("#n,");
                            }
                        }
                        if (msg.length() > 4) {
                            msg.append("不是真身状态无法进入试道！");
                            GameUtil.sendMeTips(msg.toString());
                            return;
                        }
                        // 判断队伍是否符合要求
                        String minOneTeamNum = GameConfig.config.getShidao().getMinOneTeamNum();
                        if (!StringUtils.isNullOrEmpty(minOneTeamNum)) {
                            String[] split = minOneTeamNum.split("-");
                            int min = Integer.valueOf(split[0]);
                            int max = Integer.valueOf(split[1]);
                            int size = gameObjectChar.gameTeam.duiwu.size();
                            if (size >= min && size <= max) {
                                // 判断这个阶段的试道是否开启
                                Map<String, Integer> openProject = GameConfig.config.getShidao().getOpenProject();
                                // 0:关闭 1:开放
                                if (openProject.get(GameShiDao.getShiDaoJieDuan(chara.level)) != 1) {
                                    GameUtil.changeNpcSession(npc, "该阶段试道暂未开放[离开]");
                                    return;
                                }
                                if (chara.level < 70) {
                                    GameUtil.sendMeTips("等级不符合要求，最低70级才能参加试道");
                                    return;
                                }
                                GameZone gameZone = GameShiDao.enterShiDaoMap(chara.level);
                                if (gameZone == null) {
                                    GameUtil.sendMeTips("未找到地图信息！");
                                    return;
                                }
                                // 进入试道,把队伍放到缓存中.
                                List<Chara> charas = gameObjectChar.gameTeam.duiwu;
                                for (Chara c : charas) {
                                    c.x = 18;
                                    c.y = 36;
                                    gameZone.join(GameObjectCharMng.getGameObjectCharByUUid(c.uuid));
                                }
                                if (chara != null && !charas.isEmpty()) {
                                    for (Chara c : charas) {
                                        c.shidaodaguaijifen = 0;
                                        c.shidaoPkSocre = 4;
                                        c.shidaoOutTime = 0;
                                        Vo_SHIDAO_TASK_INFO vo_49177_0 = GameCommonUtil.shidaoTaskInfoNo1();
                                        GameObjectChar.send(new MSG_SHIDAO_TASK_INFO(), vo_49177_0, c.id);
                                        // 设置试道状态
                                        GameObjectChar teamGame = GameObjectCharMng.getGameObjectChar(c.id);
                                        if (teamGame.privilege == 1000) {
                                            //系正常进入
                                            teamGame.useGmAuth = "";
                                        }
                                        teamGame.shiDaoFlag.set(true);
                                        teamGame.shiDaoGetReward = false;
                                    }
                                    if (GameShiDao.shidaoMapChara.get(charas.get(0).uuid) == null) {
                                        GameShiDao.getShiDaoSession(chara.level).add(charas);
                                        GameShiDao.shidaoMapChara.put(charas.get(0).uuid, charas);
                                    }
                                }
                            } else {
                                GameUtil.changeNpcSession(npc, "队伍不符合要求,人数应在#R" + min + "#n~#R" + max + "#n人[离开]");
                            }
                        }
                    }
                } else if ("gmEnterSd".equals(menu_item)) {
                    // 查询该角色是否为GM
                    if (gameObjectChar.privilege == 1000) {
                        if (GameShiDao.statzhuangtai == 0) {
                            GameUtil.changeNpcSession(npc, "暂无试道大会[离开]");
                            return;
                        }
//						StringBuilder menus = new StringBuilder();
//						menus.append("亲爱的#RGM#n请选择你要进入的阶段试道场[70-79/gmsd-70]").append("[80-89/gmsd-80][90-99/gmsd-90]")
//								.append("[100-109/gmsd-100][110-119/gmsd-110][120-129/gmsd-120]")
//								.append("[130-139/gmsd-130]");
//						GameUtil.changeNpcSession(npc, menus.toString());
                        GameUtil.changeNpcSession(npc, "暂未开放");
                        return;
                    }
                } else if (menu_item.startsWith("gmsd-") && gameObjectChar.privilege == 1000) {
                    //获取动态试道地图
                    String level = menu_item.split("-")[1];
                    String shiDaoJieDuan = GameShiDao.getShiDaoJieDuan(Integer.valueOf(level));
                    List<GameZone> list = GameShiDao.maps.get(shiDaoJieDuan);
                    StringBuilder msg = new StringBuilder();
                    msg.append("该阶段分为#R").append(list.size()).append("#n个子地图，请选择需要进入的地图");
                    for (GameZone gz : list) {
                        int num = gz.sessionList.size();
                        if (num > 0) {
                            msg.append("[").append(num).append("人").append("/gmen-").append(level).append("-").append(gz.uid).append("]");
                        }
                    }
                    msg.append("[返回/gmEnterSd]");
                    GameUtil.changeNpcSession(npc, msg.toString());
                } else if (menu_item.startsWith("gmen-") && gameObjectChar.privilege == 1000) {
                    String level = menu_item.split("-")[1];
                    String uid = menu_item.split("-")[2];
                    String shiDaoJieDuan = GameShiDao.getShiDaoJieDuan(Integer.valueOf(level));
                    List<GameZone> list = GameShiDao.maps.get(shiDaoJieDuan);
                    for (GameZone gz : list) {
                        if (gz.uid.equals(uid)) {
                            chara.x = 31;
                            chara.y = 47;
                            gz.join(gameObjectChar);
                            GameUtil.sendMeTips("亲爱的#RGM#n,欢迎光临#R" + level + "#n阶段试道场");
                            gameObjectChar.useGmAuth = "enterGmAuthShiDao";
                            break;
                        }
                    }
                    // 使用gm进入试道地图
                } else if (menu_item.equals("new_shidao_dahui_instruction")) {
                    // 试道大会说明
//					GameUtil.openDlg("ShidwzDlg.lua");
                } else if (menu_item.equals("世道查询")) {
                    GameShiDao.getShiDaoWzHistorys();
                }
                return;
            }
            if (id == 928 && menu_item.equals("法宝亲密丹")) {
                Boolean has = false;
                for (int l = 0; l < chara.otherGoods.size(); ++l) {
                    Goods fabao = chara.otherGoods.get(l);
                    if (fabao.pos == 9 && chara.goldCoin > 500) {
                        chara.goldCoin -= 500;
                        ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
                        gameObjectChar.sendOne(new M65527_0(), listVo_65527_2);
                        fabao.goodsInfo.shape += 1000;
                        Vo_20481_0 vo_20481_9 = new Vo_20481_0();
                        vo_20481_9.msg = "你的法宝#Y" + fabao.goodsInfo.str + "#n获得了#R1000#n亲密";
                        vo_20481_9.time = (int) (System.currentTimeMillis() / 1000L);
                        gameObjectChar.sendOne(new M20481_0(), vo_20481_9);
                        has = true;
                    }
                }
                if (!has) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "你身上没有法宝！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                }
            }

            // 战神的战斗
            if (GameLine.gameGongCheng.zhanshenGuaiwu.get(id) != null && menu_item.equals("消灭战神")) {
                if (GameCore.fightObject.get(id) != null) {
                    GameUtil.sendMeTips("没看见我正忙吗?");
                    return;
                }
                if (gameObjectChar.gameTeam == null || gameObjectChar.gameTeam.duiwu == null
                        || gameObjectChar.gameTeam.duiwu.size() < 1) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "请至少组队3人后再来挑战！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 70)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "有低于70级的成员，无法挑战！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.getGameObjectChar();
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                List<String> list7 = new ArrayList<String>();
                list7.add("战神");
                for (int i = 0; i < 9; ++i) {
                    list7.add("战将");
                }
                FightManager.goFightDynamicLevel(chara, list7, false, id);
                return;
            }

            // 海盗的战斗
            if (GameLine.gameGongCheng.haidaoGuaiwu.get(id) != null && menu_item.equals("消灭海盗")) {
                if (gameObjectChar.gameTeam == null || gameObjectChar.gameTeam.duiwu == null
                        || gameObjectChar.gameTeam.duiwu.size() < GameConfig.config.getHaidao().getTeamNumber()) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "请至少组队" + GameConfig.config.getHaidao().getTeamNumber() + "人后再来挑战！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 70)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "有低于70级的成员，无法挑战！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.getGameObjectChar();
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                Vo_APPEAR haidao = GameLine.gameGongCheng.haidaoGuaiwu.get(id);
                if (GameCore.fightObject.get(id) != null) {
                    GameUtil.changeNpcSession(haidao.id, haidao.icon, "海盗", "想死也得一个个来，急什么！[离开]");
                    return;
                }
                if (haidao != null) {
                    FightManager.goFightDynamicLevel(chara, Lists.newArrayList("海盗", "海盗", "海盗", "海盗", "海盗"), false,
                            id);
                }
                return;
            }

            // 怪物攻城的战斗
            Vo_APPEAR gongcheng = GameLine.gameGongCheng.gongchengBoss.get(id);
            if (gongcheng != null && menu_item.equals("休要废话，妖孽受死吧")) {
                // 判断是否被挑战
                if (GameCore.fightObject.get(id) != null) {
                    GameUtil.sendMeTips("想死也得一个个来，急什么！");
                    return;
                }
                if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)
                        || gameObjectChar.gameTeam.duiwu.size() < 1) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "请至少组队3人后再来挑战！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 60)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "有低于60级的成员，无法挑战BOSS！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                List<String> list7 = new ArrayList<String>();
                String name = gongcheng.name;
                list7.add(name);
                if ("孔雀妖姬".indexOf(name) != -1) {
                    list7.add("孔雀妖姬(牛魔王)");
                } else {
                    list7.add("攻城小妖(" + name + ")");
                }
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                list7.add("攻城小妖(" + name + ")");
                if ("罗刹王".indexOf(name) != -1) {
                    list7 = new ArrayList<String>();
                    list7.add(name);
                    list7.add("木之妖灵");
                    list7.add("火之妖灵");
                    list7.add("水之妖灵");
                    list7.add("土之妖灵");
                }
                FightManager.activeBoosGoFight(chara, list7, "攻城BOSS", false, id);
                return;
            }
            // 刷星
            if (GameBossTianDiXing.xing.get(id) != null) {
                Vo_APPEAR xing = GameBossTianDiXing.xing.get(id);
                if ("lookFight".equals(menu_item)) {
                    //观战
                    GameUtil.sendMeTips("耐心等待吧");
                    return;
                }
                if (menu_item.equals("我是来向你挑战的")) {
                    if(chara.isFight){
                        GameCommonUtil.sendTips("战斗中无法挑战", gameObjectChar);
                        return;
                    }
                    if (gameObjectChar.gameTeam == null || gameObjectChar.gameTeam.duiwu == null
                            || gameObjectChar.gameTeam.duiwu.size() < 1) {
                        GameUtil.changeNpcSession(xing.id, xing.icon, xing.name, "试炼之路独行乃大忌，还是凑齐3人再来挑战吧。[离开]");
                        return;
                    }
                    // 判断星星是否有人已经在挑战
                    if (GameCore.fightObject.get(id) != null) {
                        GameUtil.changeNpcSession(xing.id, xing.icon, xing.name, "我可不接受车轮战哦！[离开]");
                        return;
                    }
                    if (chara.level < GameBossTianDiXing.xing.get(id).level - 29) {
                        GameUtil.changeNpcSession(xing.id, xing.icon, xing.name, "道友修为尚浅，暂时还无法和我战斗[离开]");
                        return;
                    }
                    // 判断当前玩家是否被选中
                    String switchTdx = GameData.that.redisUtils.get("randomSwitchChara_" + id);
                    if (!com.mysql.jdbc.StringUtils.isNullOrEmpty(switchTdx)) {
                        String[] data = switchTdx.split(":");
                        // 如果这个人在队伍中,那无需换队长即可杀
                        boolean thisTeamFindChara = false;
                        for (Chara duiwu : gameObjectChar.gameTeam.duiwu) {
                            if (duiwu.getName().equals(data[1])) {
                                // 找到这个选中的人.
                                thisTeamFindChara = true;
                                break;
                            }
                        }
                        if (!thisTeamFindChara) {
                            if (GameBossTianDiXing.xing.get(id) != null) {
                                GameUtil.changeNpcSession(xing.id, xing.icon, xing.name,
                                        "我和#Y" + data[1] + "#n约定好，如果"
                                                + ((Long.valueOf(data[0]) - System.currentTimeMillis()) / 1000)
                                                + "秒他还未挑战,你等方可对我发起挑战[离开]");
                            } else {
                                GameUtil.sendMeTips("我和#Y" + data[1] + "#n约定好，如果"
                                        + ((Long.valueOf(data[0]) - System.currentTimeMillis()) / 1000)
                                        + "秒他还未挑战,你等方可对我发起挑战[离开]");
                            }
                            return;
                        }
                    }
                    if(xing.name.contains("鬼差")){
                        String[] m = {"勾魂谛听", "引灵火冥炎", "锁魂孟姑", "锁灵狱獒", "索命阴阳师"};
                        ArrayList<String> l = new ArrayList<>();
                        l.add(xing.name+"#"+xing.level);
                        for (int t = 1; t < 10; t++) {
                            l.add(m[t%5]);
                        }
                        // 设置用户战斗信息
                        FightManager.activeBoosGoFight(chara, l, false,xing.id);
                        return;
                    }else{
                        List<String> list7 = new ArrayList<String>();
                        for (int j2 = 0; j2 < 10; ++j2) {
                            if(xing.score==11){
                                list7.add(xing.name);
                            }else{
                                list7.add(xing.name);
                            }
                        }
                        // 设置用户战斗信息
                        chara.zhandouId = id;
                        GameCore.fightObject.put(id, id);
                        // 进入战斗
                        FightManager.goFightTianDiXing(chara, list7, xing);
                    }

                    return;
                }
            } else if (GameBossTianDiXing.xing.get(id) != null && menu_item.equals("离开")) {
                GameObjectChar.send(new M4155_0(), id);
                return;
            }
            // 仙界叛逆
            if (menu_item.equals("追拿通缉犯")) {
                Vo_APPEAR xuanshang = GameShuaGuai.xuanshang.get(id);
                if (xuanshang != null) {
                    if (xuanshang.owner_id == chara.id) {
                        List<String> list7 = new ArrayList<String>();
                        for (int j2 = 0; j2 < 5; ++j2) {
                            list7.add(xuanshang.name);
                        }
                        FightManager.goFightDynamicLevelByType(chara, list7, "悬赏", id);
                        return;
                    } else {
                        GameUtil.sendMeTips("你搞错了，我可不是你的通缉犯");
                    }
                    return;
                }
            }
            // 十绝阵，在玉泉真人那里初次领取十绝阵任务时
            if ((id == 1184) && (menu_item.equals("十绝阵_s0"))) {
                if (gameObjectChar.gameTeam == null || gameObjectChar.gameTeam.duiwu == null
                        || gameObjectChar.gameTeam.duiwu.size() == 0) {
                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    vo_20481_2.msg = "请组队后再来修行！";
                    vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_2);
                    return;
                }

                List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
                if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 100)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "不满100级的角色！无法继续进行！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                // 如果队长都完成了,那就直接结束
                if (chara.xiuxingcishu > GameConfig.config.getBaseConfig().getXiuxingcishuNum()) {
                    GameUtil.sendMeTips("你已完成今日十绝阵！");
                    return;
                }
                StringBuilder finishTaskName = new StringBuilder();
                for (int i = 0; i < duiwu.size(); i++) {
                    if ((duiwu.get(i).xiuxingcishu) > GameConfig.config.getBaseConfig().getXiuxingcishuNum()) {
                        finishTaskName.append("#Y").append(duiwu.get(i).name).append("#n,");
                    }
                }
                if (finishTaskName.toString().length() > 0) {
                    // 有人已经完成任务了
                    String str = finishTaskName.toString();
                    str = str.substring(0, str.length() - 1);
                    GameUtil.sendMeTips(str + "已完成今日十绝阵！");
                    return;
                }
                String[] npces = {"金光阵主", "风吼阵主", "落魄阵主", "化血阵主", "红水阵主", "寒冰阵主", "烈焰阵主", "地烈阵主", "天阙阵主", "红砂阵主"};
                Vo_61553_0 vo_61553_10 = new Vo_61553_0();
                vo_61553_10.count = 1;
                vo_61553_10.task_type = "十绝阵";
                vo_61553_10.task_desc = "天法道、道法自然，此乃道义根本。十位上古仙神演自然玄机，终成十绝之阵。";
                vo_61553_10.refresh = 0;
                vo_61553_10.task_end_time = 1567932239;
                vo_61553_10.attrib = 1;
                vo_61553_10.reward = "#I经验|人物经验宠物经验#I#I金钱|金钱#I";
                vo_61553_10.task_extra_para = "";
                vo_61553_10.task_state = "1";
                int count = (chara.xiuxingcishu + 9) % 10;
                vo_61553_10.task_prompt = ("拜访#P" + npces[count] + "|M=【十绝阵】讨教#P");
                for (int i = 0; i < duiwu.size(); i++) {
                    Chara team = duiwu.get(i);
                    team.xiuxingNpcname = npces[count];
                    vo_61553_10.show_name = ("【十绝阵】修行(" + ((team.xiuxingcishu + 9) % 10 + 1) + "/10)");
                    GameUtilRenWu.createTask(vo_61553_10, team);
                }
                GameUtil.sendMeTips("任务领取成功,快去完成吧！");
                GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_10.task_prompt), chara.id);
            }

            // 这里对应各个阵主的挑战动作
            if (menu_item.equals("十绝阵_s1")) {
                npc = GameData.that.baseNpcService.findOneByName(chara.xiuxingNpcname);
                if (npc == null) {
                    return;
                }
                if (npc.getId().intValue() == id) {
                    Random random = new Random();
                    List<String> list = new ArrayList<>();
                    list.add(chara.xiuxingNpcname);
                    for (int j = 0; j < 9; j++) {
                        int i1 = random.nextInt(6);
                        if (i1 == 0) {
                            list.add("兑灵");
                        }
                        if (i1 == 1) {
                            list.add("艮灵");
                        }
                        if (i1 == 2) {
                            list.add("坎灵");
                        }
                        if (i1 == 3) {
                            list.add("离灵");
                        }
                        if (i1 == 4) {
                            list.add("狂灵");
                        }
                        if (i1 == 5) {
                            list.add("疯灵");
                        }
                    }
                    FightManager.goFight(chara, list, false);
                }
            }
            if (id == 1174 && menu_item.equals("修行_s0")) {
                if (!GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
                    GameUtil.sendMeTips("请组队！");
                    return;
                }
                List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
                if (duiwu.size() < GameConfig.config.getBaseConfig().getXiuxingDuiwuNum()) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "人数不足" + GameConfig.config.getBaseConfig().getXiuxingDuiwuNum() + "人！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                StringBuilder finishTaskName = new StringBuilder();
                for (int i = 0; i < duiwu.size(); i++) {
                    if ((duiwu.get(i).xiuxingcishu) > GameConfig.config.getBaseConfig().getXiuxingcishuNum()) {
                        finishTaskName.append("#Y").append(duiwu.get(i).name).append("#n,");
                    }
                }
                // 如果队长都完成了,那就直接结束
                if (chara.xiuxingcishu > GameConfig.config.getBaseConfig().getXiuxingcishuNum()) {
                    GameUtil.sendMeTips("你已完成今日修行！");
                    return;
                }
                if (finishTaskName.toString().length() > 0) {
                    // 有人已经完成任务了
                    String str = finishTaskName.toString();
                    str = str.substring(0, str.length() - 1);
                    GameUtil.sendMeTips(str + "已完成今日修行！");
                    return;
                }
                if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 60)) {
                    GameUtil.sendMeTips("队伍中有低于60级的队友！");
                    return;
                }
                if (chara.taskMap.get("修炼") != null) {
                    GameUtil.changeNpcSession(npc, "你已领取任务，快去完成吧！[离开]");
                    return;
                }
                String nextNpc = "雷神";
                if ("雷神".equals(chara.xiuxingNpcname)) {
                    nextNpc = "花神";
                } else if ("花神".equals(chara.xiuxingNpcname)) {
                    nextNpc = "龙神";
                } else if ("龙神".equals(chara.xiuxingNpcname)) {
                    nextNpc = "炎神";
                } else if ("炎神".equals(chara.xiuxingNpcname)) {
                    nextNpc = "山神";
                } else if ("山神".equals(chara.xiuxingNpcname)) {
                    nextNpc = "雷神";
                }
                Vo_61553_0 vo_61553_4 = new Vo_61553_0();
                vo_61553_4.count = 1;
                vo_61553_4.task_type = "修炼";
                vo_61553_4.task_desc = "接受门派师尊交办的一些事情，完成后会获得嘉奖。";
                vo_61553_4.task_prompt = "拜访#P" + nextNpc + "|M=【修行】请仙人赐教#P";
                vo_61553_4.refresh = 0;
                vo_61553_4.task_end_time = 1567932239;
                vo_61553_4.attrib = 1;
                vo_61553_4.reward = "#I经验|人物经验宠物经验#I#I金钱|金钱#I";
                vo_61553_4.task_extra_para = "";
                vo_61553_4.task_state = "1";
                for (Chara team : gameObjectChar.gameTeam.duiwu) {
                    team.xiuxingNpcname = nextNpc;
                    vo_61553_4.show_name = "【修炼】修行(" + team.xiuxingcishu + "/"
                            + GameConfig.config.getBaseConfig().getXiuxingcishuNum() + ")";
                    GameUtilRenWu.createTask(vo_61553_4, team);
                }
                GameCommonUtil.dialogOk("修行任务领取成功，快去完成把！");
            }

            // 这里是对应几个神的修行任务
            if (menu_item.equals("修行_s1")) {
                npc = GameData.that.baseNpcService.findOneByName(chara.xiuxingNpcname);
                if (npc == null) {
                    return;
                }
                if (npc.getId() == id) {
                    Random random = new Random();
                    List<String> list8 = new ArrayList<String>();
                    list8.add(chara.xiuxingNpcname);
                    for (int j3 = 0; j3 < 4; ++j3) {
                        int i4 = random.nextInt(6);
                        if (i4 == 0) {
                            list8.add("兑灵");
                        }
                        if (i4 == 1) {
                            list8.add("艮灵");
                        }
                        if (i4 == 2) {
                            list8.add("坎灵");
                        }
                        if (i4 == 3) {
                            list8.add("离灵");
                        }
                        if (i4 == 4) {
                            list8.add("狂灵");
                        }
                        if (i4 == 5) {
                            list8.add("疯灵");
                        }
                    }
                    FightManager.goFightDynamicLevelByType(chara, list8, "修山", id);
                }
            }

            // 在陆压真人领取伏魔任务
            else if (id == 866 && menu_item.equals("领取任务")) {
                if (gameObjectChar.gameTeam == null) {
                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    vo_20481_2.msg = "请组队" + GameConfig.LY_SHUADAO_NUM + "人！";
                    vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_2);
                    return;
                }
                List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
                if (duiwu.size() < GameConfig.LY_SHUADAO_NUM) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "人数不足" + GameConfig.LY_SHUADAO_NUM + "人！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (GameUtil.duiwudengji120(chara, gameObjectChar)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "队伍中有120级及以上成员，无法继续进行！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.duiwudengji80(chara, gameObjectChar)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "不满80级的成员，无法继续进行！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.duiwudengji(chara, gameObjectChar)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "人物等级相差10级，不能接任务！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }

                List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
                        .findByType(3);
                RenwuMonster renwuMonster2 = renwuMonsters.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
                String name3 = renwuMonster2.getName();
                com.fengshen.db.domain.Map map4 = GameData.that.baseMapService
                        .findOneByName(renwuMonster2.getMapName());
                if (map4 == null) {
                    return;
                }
                Vo_APPEAR vo_65529_2 = new Vo_APPEAR();
                vo_65529_2.mapid = map4.getMapId();
                vo_65529_2.id = GameCommonUtil.generateBossId();
                vo_65529_2.x = renwuMonster2.getX();
                vo_65529_2.y = renwuMonster2.getY();
                vo_65529_2.icon = renwuMonster2.getIcon();
                vo_65529_2.type = 2;
                vo_65529_2.org_icon = renwuMonster2.getIcon();
                vo_65529_2.portrait = renwuMonster2.getIcon();
                vo_65529_2.name = name3;
                vo_65529_2.level = chara.level;
                vo_65529_2.leixing = 3;
                vo_65529_2.owner_id = chara.id;
                chara.shudao.put(vo_65529_2.id, vo_65529_2);

                Vo_61553_0 vo_61553_5 = new Vo_61553_0();
                vo_61553_5.count = 1;
                vo_61553_5.task_type = "伏魔";
                vo_61553_5.task_desc = "";
                vo_61553_5.task_prompt = "降伏#P" + name3 + "|" + renwuMonster2.getMapName() + "(" + renwuMonster2.getX()
                        + "," + renwuMonster2.getY() + ")|M=今天我要为民除害|$0#P";
                vo_61553_5.refresh = 1;
                vo_61553_5.task_end_time = 1567909190;
                vo_61553_5.attrib = 1;
                vo_61553_5.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
                vo_61553_5.show_name = "伏魔(" + chara.shuadao + "/10)";
                vo_61553_5.task_extra_para = "";
                vo_61553_5.task_state = "1";
                GameObjectChar.sendduiwu(new MSG_TASK_PROMPT(), vo_61553_5, chara.id);
                GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_5.task_prompt));
                Vo_45092_0 vo_45092_0 = new Vo_45092_0();
                vo_45092_0.task_name = "伏魔";
                vo_45092_0.check_point = 40;
                GameObjectChar.sendduiwu(new M45092_0(), vo_45092_0, chara.id);

                if (chara.mapid == vo_65529_2.mapid) {
                    GameObjectChar.sendduiwu(new M65529_0(), vo_65529_2, chara.id);
                }
            }

            // 清微真人领取飞仙渡劫任务
            else if ((id == 1185) && (menu_item.equals("飞仙渡邪_dispatch"))) {
                if (gameObjectChar.gameTeam == null) {
                    Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                    vo_20481_2.msg = "请组队" + GameConfig.LY_SHUADAO_NUM + "人！";
                    vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_2);
                    return;
                }
                List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
                if (duiwu.size() < GameConfig.LY_SHUADAO_NUM) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "人数不足" + GameConfig.LY_SHUADAO_NUM + "人！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.duiwudengji120(chara, gameObjectChar)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "不满120级的成员，无法继续进行！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                if (!GameUtil.duiwudengji(chara, gameObjectChar)) {
                    Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                    vo_20481_10.msg = "人物等级相差10级，不能接任务！";
                    vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_10);
                    return;
                }
                List<RenwuMonster> renwuMonsters = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService
                        .findByType(4);
                RenwuMonster renwuMonster2 = renwuMonsters.get(ThreadLocalRandom.current().nextInt(renwuMonsters.size()));
                String name3 = renwuMonster2.getName();
                com.fengshen.db.domain.Map map4 = GameData.that.baseMapService
                        .findOneByName(renwuMonster2.getMapName());
                if (map4 == null) {
                    return;
                }

                Vo_APPEAR vo_65529_2 = new Vo_APPEAR();
                vo_65529_2.mapid = map4.getMapId();
                vo_65529_2.id = GameCommonUtil.generateBossId();
                vo_65529_2.x = renwuMonster2.getX();
                vo_65529_2.y = renwuMonster2.getY();
                vo_65529_2.icon = renwuMonster2.getIcon();
                vo_65529_2.type = 2;
                vo_65529_2.org_icon = renwuMonster2.getIcon();
                vo_65529_2.portrait = renwuMonster2.getIcon();
                vo_65529_2.name = name3;
                vo_65529_2.level = chara.level;
                vo_65529_2.leixing = 4;
                vo_65529_2.owner_id = chara.id;
                chara.shudao.put(vo_65529_2.id, vo_65529_2);

                Vo_61553_0 vo_61553_5 = new Vo_61553_0();
                vo_61553_5.count = 1;
                vo_61553_5.task_type = "飞仙渡邪";
                vo_61553_5.task_desc = "";
                vo_61553_5.task_prompt = "渡邪#P" + name3 + "|" + renwuMonster2.getMapName() + "(" + renwuMonster2.getX()
                        + "," + renwuMonster2.getY() + ")|M=今天我要为民除害|#P";
                vo_61553_5.refresh = 1;
                vo_61553_5.task_end_time = 1567909190;
                vo_61553_5.attrib = 1;
                vo_61553_5.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
                vo_61553_5.show_name = "飞仙渡邪(" + chara.shuadao + "/10)";
                vo_61553_5.task_extra_para = "";
                vo_61553_5.task_state = "1";
                GameUtilRenWu.createTaskTeam(vo_61553_5, chara);
                GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_5.task_prompt));

                Vo_45092_0 vo_45092_0 = new Vo_45092_0();
                vo_45092_0.task_name = "飞仙渡邪";
                vo_45092_0.check_point = 40;
                GameObjectChar.sendduiwu(new M45092_0(), vo_45092_0, chara.id);
                if (chara.mapid == vo_65529_2.mapid) {
                    GameObjectChar.sendduiwu(new M65529_0(), vo_65529_2, chara.id);
                }
            } else {
                // 在通灵道人领取降妖任务
                if (id == 957) {
                    if (menu_item.equals("dispatch_xiangy")) {
                        if (gameObjectChar.gameTeam == null) {
                            Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                            vo_20481_2.msg = "请创建队伍";
                            vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.send(new M20481_0(), vo_20481_2);
                            return;
                        }
                        List<Chara> duiwu = gameObjectChar.gameTeam.duiwu;
                        if (duiwu.size() < GameConfig.XIANGYAO_NUM) {
                            Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                            vo_20481_10.msg = "人数不足" + GameConfig.XIANGYAO_NUM + "人！";
                            vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.send(new M20481_0(), vo_20481_10);
                            return;
                        }
                        if (!GameUtil.judgeDuiyuanLevel(chara, gameObjectChar, 40)) {
                            Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                            vo_20481_10.msg = "队伍中有不足40级的成员，无法继续进行！";
                            vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.send(new M20481_0(), vo_20481_10);
                            return;
                        }
                        if (!GameCommonUtil.levelGreaterThanorEqualto(gameObjectChar, 80)) {
                            Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                            vo_20481_10.msg = "队伍中有80级及以上成员，无法继续进行！";
                            vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.send(new M20481_0(), vo_20481_10);
                            return;
                        }

                        List<RenwuMonster> all2 = new ArrayList<RenwuMonster>();
                        if (CMD_SELECT_MENU_ITEM.type2.get(2) == null
                                || CMD_SELECT_MENU_ITEM.type2.get(2).size() == 0) {
                            all2 = (List<RenwuMonster>) GameData.that.baseRenwuMonsterService.findByType(2);
                        } else {
                            all2 = CMD_SELECT_MENU_ITEM.type2.get(2);
                        }
                        Random random4 = new Random();
                        int i3 = random4.nextInt(all2.size());
                        RenwuMonster renwuMonster2 = all2.get(i3);
                        String name3 = renwuMonster2.getName();
                        com.fengshen.db.domain.Map map5 = GameData.that.baseMapService
                                .findOneByName(renwuMonster2.getMapName());
                        if (map5 == null) {
                            return;
                        }
                        Vo_APPEAR vo_65529_3 = new Vo_APPEAR();
                        vo_65529_3.mapid = map5.getMapId();
                        vo_65529_3.id = GameCommonUtil.generateBossId();
                        vo_65529_3.x = renwuMonster2.getX();
                        vo_65529_3.y = renwuMonster2.getY();
                        vo_65529_3.icon = renwuMonster2.getIcon();
                        vo_65529_3.type = 2;
                        vo_65529_3.org_icon = renwuMonster2.getIcon();
                        vo_65529_3.portrait = renwuMonster2.getIcon();
                        vo_65529_3.name = name3;
                        vo_65529_3.level = chara.level;
                        vo_65529_3.leixing = 2;
                        vo_65529_3.owner_id = chara.id;
                        chara.shudao.put(vo_65529_3.id, vo_65529_3);
                        Vo_61553_0 vo_61553_0 = new Vo_61553_0();
                        vo_61553_0.count = 1;
                        vo_61553_0.task_type = "降妖";
                        vo_61553_0.task_desc = "";
                        vo_61553_0.task_prompt = "降妖#P" + name3 + "|" + renwuMonster2.getMapName() + "("
                                + renwuMonster2.getX() + "," + renwuMonster2.getY() + ")|M=今天我要为民除害|#P";
                        vo_61553_0.refresh = 1;
                        vo_61553_0.task_end_time = 1567909190;
                        vo_61553_0.attrib = 1;
                        vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I";
                        vo_61553_0.show_name = "降妖(" + chara.shuadao + "/10)";
                        vo_61553_0.task_extra_para = "";
                        vo_61553_0.task_state = "1";
                        GameUtilRenWu.createTaskTeam(vo_61553_0, chara);
                        GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_0.task_prompt));
                        Vo_45092_0 vo_45092_2 = new Vo_45092_0();
                        vo_45092_2.task_name = "降妖";
                        vo_45092_2.check_point = 40;
                        GameObjectChar.sendduiwu(new M45092_0(), vo_45092_2, chara.id);
                        if (chara.mapid == vo_65529_3.mapid) {
                            GameObjectChar.sendduiwu(new M65529_0(), vo_65529_3, chara.id);
                        }
                    }
                    return;
                }

                // 找龙王换取东西
                if (id == 928 && menu_item.equals("【领取法宝】提交#R蟠螭结、雪魂丝链#n")) {
                    if (chara.taskMap.get("法宝任务") == null) {
                        GameUtil.sendMeTips("你还未领取任务呢？");
                        return;
                    }
                    boolean banlijie = false;
                    boolean xuehunsilian = false;
                    for (int i = 0; i < chara.backpack.size(); ++i) {
                        if (chara.backpack.get(i).goodsInfo.str.equals("蟠螭结")) {
                            banlijie = true;
                        }
                        if (chara.backpack.get(i).goodsInfo.str.equals("雪魂丝链")) {
                            xuehunsilian = true;
                        }
                    }
                    if (banlijie && xuehunsilian) {
                        GameUtil.shuafabao(chara, "龙王");
                        GameUtil.removemunber(chara, "蟠螭结", 1);
                        GameUtil.removemunber(chara, "雪魂丝链", 1);
                        GameUtilRenWu.removeTask("法宝任务", chara);
                        ++chara.fabaorenwu;
                    } else {
                        Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                        vo_20481_0.msg = "首饰不足！";
                        vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_0);
                    }
                    return;
                }

                if (id == 976 && menu_item.equals("大日金乌")) {
                    gameObjectChar.chara.x = 36;
                    gameObjectChar.chara.y = 37;
                    GameLine.getGameMap(gameObjectChar.chara.line, 20003).join(gameObjectChar);
                    return;
                }

                if (id == 843 && menu_item.equals("火焰之灵")) {
                    if (Long.parseLong(redisUtils.get("dari_life_str")) <= 0) {
                        GameUtil.sendMeTips("#L大日金乌血量已经见底啦，系统正在努力得结算中，耐心等待！#n");
                        return;
                    }
                    ArrayList<String> monsterList = new ArrayList<>();
                    monsterList.add("火焰之灵");
                    monsterList.add("火焰之灵");
                    monsterList.add("火焰之灵");
                    monsterList.add("火焰之灵");
                    monsterList.add("火焰之灵");
                    FightManager.activeBoosGoFight(chara, monsterList, "大日金乌", false, id);
                    return;
                }

                if (id == 842 && menu_item.equals("火狮兽")) {
                    if (Long.parseLong(redisUtils.get("dari_life_str")) <= 0) {
                        GameUtil.sendMeTips("#L大日金乌血量已经见底啦，系统正在努力得结算中，耐心等待！#n");
                        return;
                    }
                    ArrayList<String> monsterList = new ArrayList<>();
                    monsterList.add("火狮兽");
                    monsterList.add("火狮兽");
                    monsterList.add("火狮兽");
                    monsterList.add("火狮兽");
                    monsterList.add("火狮兽");
                    FightManager.activeBoosGoFight(chara, monsterList, "大日金乌", false, id);
                    return;
                }

                if (id == 841 && menu_item.equals("金乌之灵")) {
                    if (Long.parseLong(redisUtils.get("dari_life_str")) <= 0) {
                        GameUtil.sendMeTips("#L大日金乌血量已经见底啦，系统正在努力得结算中，耐心等待！#n");
                        return;
                    }
                    ArrayList<String> monsterList = new ArrayList<>();
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    FightManager.activeBoosGoFight(chara, monsterList, "大日金乌", false, id);
                    return;
                }

                if (id == 840 && menu_item.equals("大日金乌")) {
                    if (Long.parseLong(redisUtils.get("dari_life_str")) <= 0) {
                        GameUtil.sendMeTips("#L大日金乌血量已经见底啦，系统正在努力得结算中，耐心等待！#n");
                        return;
                    }
                    ArrayList<String> monsterList = new ArrayList<>();
                    monsterList.add("大日金乌");
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    monsterList.add("金乌之灵");
                    FightManager.activeBoosGoFight(chara, monsterList, "大日金乌", false, id);
                    return;
                }


                if (id == 976 && menu_item.equals("【法宝任务】我对法宝感兴趣")) {
                    if (chara.fabaorenwu >= 10) {
                        Vo_20481_0 vo_20481_2 = new Vo_20481_0();
                        vo_20481_2.msg = "今天已经领取任务了！";
                        vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_2);
                        return;
                    }
                    Vo_61553_0 vo_61553_7 = new Vo_61553_0();
                    vo_61553_7.count = 1;
                    vo_61553_7.task_type = "法宝任务";
                    vo_61553_7.task_desc = "为获得强大的法宝而接受重重考验的任务。";
                    vo_61553_7.task_prompt = "找#P龙王#P求取法宝";
                    vo_61553_7.refresh = 0;
                    vo_61553_7.task_end_time = 1567932239;
                    vo_61553_7.attrib = 0;
                    vo_61553_7.reward = "#I法宝|随机法宝=F$1$6#I";
                    vo_61553_7.show_name = "法宝任务";
                    vo_61553_7.task_extra_para = "";
                    vo_61553_7.task_state = "0";
                    GameUtilRenWu.createTask(vo_61553_7, chara);
                    ++chara.fabaorenwu;
                }
                // 师门任务
                if (menu_item.equals("sm-002_s1")) {
                    if (chara.level < 15) {
                        Vo_20481_0 vo_20481_7 = new Vo_20481_0();
                        vo_20481_7.msg = "等级不足15级无法做师门任务！";
                        vo_20481_7.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.getGameObjectChar();
                        GameObjectChar.send(new M20481_0(), vo_20481_7);
                        return;
                    }
                    if (chara.taskMap.get("师门任务") == null) {
                        GameUtil.sendMeTips("请领取任务.");
                        return;
                    }
                    String[] npces2 = {"李总兵", "杨镖头", "董老头", "五行生肖大使", "逍遥仙", "陆压真人", "无名武器店老板", "清微真人", "龙王", "杜卜思",
                            "屠娇娇", "管神工", "天机老人"};
                    Random random = new Random();
                    int i = random.nextInt(npces2.length);
                    ++chara.shimencishu;
                    Vo_61553_0 vo_61553_8 = new Vo_61553_0();
                    vo_61553_8.count = 1;
                    vo_61553_8.task_type = "师门任务";
                    vo_61553_8.task_desc = "接受门派师尊交办的一些事情，完成后会获得嘉奖。";
                    vo_61553_8.task_prompt = "拜访#P" + npces2[i] + "|M=【师门】入世#P";
                    vo_61553_8.refresh = 0;
                    vo_61553_8.task_end_time = 1567909190;
                    vo_61553_8.attrib = 1;
                    vo_61553_8.reward = "#I经验|人物经验宠物经验#I#I金钱|金钱#I";
                    vo_61553_8.show_name = "师门—入世("
                            + ((chara.shimencishu % GameConfig.config.getBaseConfig().getShimenNum() == 0)
                            ? GameConfig.config.getBaseConfig().getShimenNum()
                            : (chara.shimencishu % GameConfig.config.getBaseConfig().getShimenNum()))
                            + "/" + GameConfig.config.getBaseConfig().getShimenNum() + ")";
                    vo_61553_8.task_extra_para = npces2[i];
                    vo_61553_8.task_state = "1";
                    GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_8);
                    // 师门任务获得经验
                    GameUtil.huodejingyan(chara, (int) (30000 + chara.level * 1400
                            + 2000 * ((chara.shimencishu % 10 == 0) ? 10 : (chara.shimencishu % 10))), "师门");
                    ListVo_65527_0 listVo_65527_3 = GameUtil.a65527(chara);
                    GameObjectChar.send(new M65527_0(), listVo_65527_3);
                    // 完成任务
                    if (chara.shimencishu > GameConfig.config.getBaseConfig().getShimenNum()) {
                        GameUtilRenWu.removeTask("师门任务", chara);
                        return;
                    } else {
                        GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_8.task_prompt));
                        chara.taskMap.put(vo_61553_8.task_type, vo_61553_8);
                    }
                }

                if (id == 831 || id == 1068 || id == 1019 || id == 1107 || id == 943) {
                    int[] polar2 = {831, 1068, 1019, 1107, 943}; // 五大门派ID
                    if (polar2[chara.polar - 1] != id) {
                        Vo_20481_0 vo_20481_10 = new Vo_20481_0();
                        vo_20481_10.msg = "来错门派了！";
                        vo_20481_10.time = (int) (System.currentTimeMillis() / 1000L);
                        GameObjectChar.send(new M20481_0(), vo_20481_10);
                        return;
                    }
                    String[] npces = {"李总兵", "杨镖头", "董老头", "逍遥仙", "陆压真人", "五行生肖大使", "无名武器店老板", "清微真人", "龙王", "杜卜思",
                            "屠娇娇", "管神工", "天机老人"};
                    Random random4 = new Random();
                    int i3 = random4.nextInt(npces.length);
                    if (menu_item.equals("师门任务_s0")) {
                        chara.current_task = "主线—拜入师门s18";
                        if (chara.shimencishu > GameConfig.config.getBaseConfig().getShimenNum()) {
                            Vo_20481_0 vo_20481_7 = new Vo_20481_0();
                            vo_20481_7.msg = "今天已完成任务";
                            vo_20481_7.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.send(new M20481_0(), vo_20481_7);
                            return;
                        }
                        if ("主线—拜入师门s18".equals(chara.current_task) && chara.taskMap.get("主线—拜入师门") != null
                                && chara.taskMap.get("主线—拜入师门").task_extra_para.equals("主线—拜入师门s18")) {
                            shimenTask.task_state = "1";
                            chara.current_task = "主线—拜入师门s18";
                            Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子奉命前来师尊处领取师门任务，还请师尊指点。", "主线—拜入师门");
                            GameObjectChar.send(new M45056_0(), vo_45056_2);
                            return;
                        }
                        Vo_61553_0 vo_61553_4 = new Vo_61553_0();
                        vo_61553_4.count = 1;
                        vo_61553_4.task_type = "师门任务";
                        vo_61553_4.task_desc = "接受门派师尊交办的一些事情，完成后会获得嘉奖。";
                        vo_61553_4.task_prompt = "拜访#P" + npces[i3] + "|M=【师门】入世#P";
                        vo_61553_4.refresh = 0;
                        vo_61553_4.task_end_time = 1567932239;
                        vo_61553_4.attrib = 1;
                        vo_61553_4.reward = "#I经验|人物经验宠物经验#I#I金钱|金钱#I";
                        vo_61553_4.show_name = "师门—入世("
                                + ((chara.shimencishu % GameConfig.config.getBaseConfig().getShimenNum() == 0)
                                ? GameConfig.config.getBaseConfig().getShimenNum()
                                : (chara.shimencishu % GameConfig.config.getBaseConfig().getShimenNum()))
                                + "/" + GameConfig.config.getBaseConfig().getShimenNum() + ")";
                        vo_61553_4.task_extra_para = npces[i3];
                        vo_61553_4.task_state = "1";
                        GameObjectChar.send(new MSG_TASK_PROMPT(), vo_61553_4);
                        GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK(vo_61553_4.task_prompt));
                        chara.taskMap.put(vo_61553_4.task_type, vo_61553_4);
                    } else if ("百级拜师任务".equals(menu_item)) {
                        if (chara.level >= 100) {
                            Vo_41023_0 vo_41023_0 = new Vo_41023_0();
                            vo_41023_0.taskName = "拜师任务";
                            vo_41023_0.status = 1;
                            GameObjectChar.send(new M41023_0(), vo_41023_0);
                            GameUtil.sendMeTips("恭喜你已领悟更多的技能法术。");
                            chara.isFinish100Task = 1;
                        }
                    }
                }
                // 助人为乐的任务
                if (id == 958) {
                    if (menu_item.equals("助人为乐_s0")) {
                        if (chara.level < 40) {
                            GameUtil.changeNpcSession(id, 6010, "白邦芒", "请升到40级再来找我。[离开]");
                            return;
                        }
                        if (chara.baibangmang >= GameConfig.config.getBaseConfig().getBaibangmangNum()) {
                            GameUtil.changeNpcSession(id, 6010, "白邦芒", "你今天已经帮了我大忙了，还是先休息休息吧。[离开]");
                            return;
                        }
                        GameUtil.changeNpcSession(npc,
                                "据我所知，目前#Y冯喜来#n、#Y乞丐#n有事需要帮助，你去看看能帮上谁的忙吧。[帮助冯来喜/助人为乐—打抱不平s0][帮助乞丐/助人为乐—扶危救困s0][离开]");
                        return;
                    } else if (menu_item.equals("助人为乐—打抱不平s0")) {
                        if (chara.taskMap.get("助人为乐—打抱不平") != null || chara.taskMap.get("助人为乐—扶危救困") != null) {
                            GameCommonUtil.dialogOk("你已#R领取任务#n了，快去完成吧！");
                            return;
                        } else if (chara.taskMap.get("助人为乐") != null) {
                            GameCommonUtil.dialogOk("你已完成#R任务#n，请领取犒赏！");
                            return;
                        }
                        GameUtilRenWu.createTask(chara,
                                GameData.that.baseRenwuService.findOneByCurrentTask("助人为乐—打抱不平s1"));
                        GameCommonUtil.dialogOk("你领取了#R助人为乐—打抱不平#n任务，快去完成吧。");
                    } else if (menu_item.equals("助人为乐—扶危救困s0")) {
                        if (chara.taskMap.get("助人为乐—扶危救困") != null || chara.taskMap.get("助人为乐—打抱不平") != null) {
                            GameCommonUtil.dialogOk("你已#R领取任务#n了，快去完成吧！");
                            return;
                        } else if (chara.taskMap.get("助人为乐") != null) {
                            GameCommonUtil.dialogOk("你已完成#R任务#n，请领取犒赏！");
                            return;
                        }
                        GameUtilRenWu.createTask(chara,
                                GameData.that.baseRenwuService.findOneByCurrentTask("助人为乐—扶危救困s1"));
                        GameCommonUtil.dialogOk("你领取了#R助人为乐—扶危救困#n任务，快去完成吧。");
                    } else if (menu_item.equals("助人为乐_exp")
                            && chara.baibangmang < GameConfig.config.getBaseConfig().getBaibangmangNum()) {
                        GameUtilRenWu.removeTask("助人为乐", chara);
                        GameUtil.huodejingyan(chara, 500000 + (chara.level - 40) * 10000, "白邦芒");
                        // 获取未鉴定的装备
                        GameUtil.weijianding(chara);
                        ListVo_65527_0 listVo_65527_4 = GameUtil.a65527(chara);
                        GameObjectChar.send(new M65527_0(), listVo_65527_4);
                        chara.baibangmang++;
                        return;
                    } else {
                        if (menu_item.equals("助人为乐_tao")
                                && chara.baibangmang < GameConfig.config.getBaseConfig().getBaibangmangNum()) {
                            int base_dh = (int) (0.29 * chara.level * chara.level * chara.level);
                            int owner_name = 3 * 365 * 1440
                                    + 3392 * chara.level / ((chara.tao > base_dh) ? (chara.tao / base_dh) : 1);
                            GameUtil.adddaohang(chara, owner_name, "白邦芒");

                            for (int i = 0; i < chara.pets.size(); ++i) {
                                if (chara.pets.get(i).id == chara.chongwuchanzhanId) {
                                    PetShuXing petShuXing3 = chara.pets.get(i).petShuXing.get(0);
                                    petShuXing3.intimacy += 76 * chara.pets.get(i).petShuXing.get(0).skill;
                                    List<Petbeibao> list9 = new ArrayList<Petbeibao>();
                                    list9.add(chara.pets.get(i));
                                    GameObjectChar.send(new MSG_UPDATE_PETS(), list9);
                                    break;
                                }
                            }
                            ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
                            GameObjectChar.send(new M65527_0(), listVo_65527_5);
                            GameUtil.weijianding(chara);
                            chara.baibangmang++;
                            GameUtilRenWu.removeTask("助人为乐", chara);
                        }
                        if (menu_item.equals("助人为乐_pot")
                                && chara.baibangmang < GameConfig.config.getBaseConfig().getBaibangmangNum()) {
                            Chara chara18 = chara;
                            chara18.pot += (chara.level * 50000);
                            Vo_20481_0 vo_20481_2 = new Vo_20481_0();

                            vo_20481_2.msg = "获得潜能#R" + (chara.level * 50000);
                            vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
                            GameObjectChar.getGameObjectChar();
                            GameObjectChar.send(new M20481_0(), vo_20481_2);
                            ListVo_65527_0 listVo_65527_6 = GameUtil.a65527(chara);
                            GameObjectChar.send(new M65527_0(), listVo_65527_6);
                            GameUtil.weijianding(chara);
                            chara.baibangmang++;
                            GameUtilRenWu.removeTask("助人为乐", chara);
                        }
                    }
                }
                int i2 = 0;
                for (i2 = 0; i2 < chara.npcchubao.size(); ++i2) {
                    if (chara.npcchubao.get(i2).id == id && menu_item.equals("就是来抓你的")) {
                        Random random = new Random();
                        List<String> list8 = new ArrayList<String>();
                        list8.add(chara.npcchubao.get(0).name);
                        // 如果是单人，就两个怪物
                        if (gameObjectChar.gameTeam == null || gameObjectChar.gameTeam.duiwu.size() == 1) {
                            int i4 = random.nextInt(2);
                            if (i4 == 1) {
                                list8.add("帮凶");
                            } else {
                                list8.add("喽啰");
                            }
                        } else if (gameObjectChar.gameTeam != null && gameObjectChar.gameTeam.duiwu != null
                                && gameObjectChar.gameTeam.duiwu.size() > 1) {
                            for (int j = 0; j < gameObjectChar.gameTeam.duiwu.size(); ++j) {
                                int i4 = random.nextInt(2);
                                if (i4 == 1) {
                                    list8.add("帮凶");
                                } else {
                                    list8.add("喽啰");
                                }
                                i4 = random.nextInt(2);
                                if (i4 == 1) {
                                    list8.add("帮凶");
                                } else {
                                    list8.add("喽啰");
                                }
                            }
                            list8.remove(list8.size() - 1); // 因为多加入了一个怪物，所以要删除
                        }
                        FightManager.goFightDynamicLevelByType(chara, list8, "刷道", id);
                    }
                }

                Vo_APPEAR shudao = chara.shudao.get(id);
                if (shudao != null && menu_item.equals("今天我要为民除害")) {
                    Random random = new Random();
                    List<String> list8 = new ArrayList<String>();
                    if (chara.isFight) {
                        GameCommonUtil.sendTips("战斗中无法挑战", gameObjectChar);
                        return;
                    }
                    if (shudao.leixing == 2) {
                        // 降妖
                        if (chara.level >= 80 || chara.level < 45) {
                            GameUtil.sendMeTips("降妖等级大于#R45级#n并且小于#R80级#n！");
                            return;
                        }
                        list8.add(shudao.name);
                        for (int j3 = 0; j3 < random.nextInt(3) + 6; ++j3) {
                            int i4 = random.nextInt(4);
                            if (i4 == 0) {
                                list8.add("疯魑");
                            }
                            if (i4 == 1) {
                                list8.add("狂魍");
                            }
                            if (i4 == 2) {
                                list8.add("黄怪");
                            }
                            if (i4 == 3) {
                                list8.add("蓝精");
                            }
                        }
                        FightManager.goFightDynamicLevelByType(chara, list8, "刷道", id);
                    }
                    if (shudao.leixing == 3 || shudao.leixing == 4) {
                        if (shudao.leixing == 3) {
                            if (chara.level >= 120 || chara.level < 80) {
                                GameUtil.sendMeTips("伏魔等级需大于#R80级#n并且小于#R120#n级方可进行！");
                                return;
                            }
                        } else if (shudao.leixing == 4) {
                            if (chara.level < 120) {
                                GameUtil.sendMeTips("飞仙等级需大于#R120级#n方可进行！");
                                return;
                            }
                        }
                        list8.add(shudao.name);
                        list8.add(shudao.name);
                        list8.add(shudao.name);
                        for (int j3 = 0; j3 < random.nextInt(3) + 4; ++j3) {
                            int i4 = random.nextInt(6);
                            if (i4 == 0) {
                                list8.add("兑灵");
                            }
                            if (i4 == 1) {
                                list8.add("艮灵");
                            }
                            if (i4 == 2) {
                                list8.add("坎灵");
                            }
                            if (i4 == 3) {
                                list8.add("离灵");
                            }
                            if (i4 == 4) {
                                list8.add("狂灵");
                            }
                            if (i4 == 5) {
                                list8.add("疯灵");
                            }
                        }
                        FightManager.goFightDynamicLevelByType(chara, list8, "刷道", id);
                    }
                }
                // 妙音清理
                if (id == 972 || menu_item.indexOf("清理") != -1 || menu_item.indexOf("删除宠物") != -1
                        || menu_item.indexOf("妙音返回") != -1) {
                    if (menu_item.equals("离开")) {
                        return;
                    }
                    if (menu_item.indexOf("清理背包") != -1) {
                        GameUtil.changeNpcSession(id, 20072, "妙音仙子",
                                "请选择背包页码。[清理第一页][清理第二页][清理第三页][清理第四页][清理第五页][清理全部][返回/妙音返回]");
                        return;
                    } else if (menu_item.indexOf("清理宠物") != -1) {
                        StringBuilder sb = new StringBuilder();
                        for (int l = 0; l < chara.pets.size(); ++l) {
                            if (chara.pets.get(l).id != chara.flyPetID) {
                                sb.append(chara.pets.get(l).id).append("|");
                            }
                        }
                        Vo_DESTROY_VALUABLE_LIST info = new Vo_DESTROY_VALUABLE_LIST();
                        info.setId_str(sb.toString());
                        info.setType(1);
                        GameObjectChar.send(new MSG_DESTROY_VALUABLE_LIST(), info);
                        return;
                    } else if (menu_item.indexOf("返回") != -1) {
                        GameUtil.changeNpcSession(id, 20072, "妙音仙子",
                                "此生仅有一愿，聆尽世间天籁之音。[#L清理背包/妙音仙子清理背包][#O清理仓库/openClearStore][#B销毁宠物/妙音仙子清理宠物][#R装备回收/openSubmitEquipDlg][离开]");
                        return;
                    } else if (menu_item.indexOf("删除宠物") != -1) {
                        if (GameCommonUtil.isValidateSafePwd(gameObjectChar)) {
                            return;
                        }
                        String idStr = menu_item.split(",")[0];
                        int petId = Integer.parseInt(idStr);
                        for (int l = 0; l < chara.pets.size(); ++l) {
                            Petbeibao petbeibao = chara.pets.get(l);
                            if (petId == petbeibao.id) {
                                Vo_12269_0 vo_12269_0 = new Vo_12269_0();
                                vo_12269_0.id = petId;
                                vo_12269_0.owner_id = 0;
                                GameObjectChar.send(new M12269_0(), vo_12269_0);
                                chara.pets.remove(petbeibao);
                                GameData.that.charaPetService.deleteByPrimaryKey(petId);
                                if (petbeibao.petShuXing.get(0).penetrate == 3) {
                                    Integer recoveryBianYiScore = GameConfig.config.getBaseConfig().getRecoveryBianYiScore();
                                    if (recoveryBianYiScore > 0) {
                                        GameUtil.addchargeScore(gameObjectChar, recoveryBianYiScore, "回收大使");
                                    }
                                } else if (petbeibao.petShuXing.get(0).penetrate == 4) {
                                    Integer recoveryShenShouScore = GameConfig.config.getBaseConfig().getRecoveryShenShouScore();
                                    if (recoveryShenShouScore > 0) {
                                        GameUtil.addchargeScore(gameObjectChar, recoveryShenShouScore, "回收大使");
                                    }
                                }
                                break;
                            }
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("此生仅有一愿，聆尽世间天籁之音。");
                        for (int l = 0; l < chara.pets.size(); ++l) {
                            if (chara.pets.get(l).id != chara.chongwuchanzhanId && chara.pets.get(l).id != chara.zuoqiId
                                    && chara.pets.get(l).id != chara.flyPetID
                                    && chara.pets.get(l).id != chara.chongwuluezhenId) {
                                sb.append("[销毁#R" + chara.pets.get(l).petShuXing.get(0).str + "#n\\/"
                                        + chara.pets.get(l).id + ",删除宠物]");
                            }
                        }
                        sb.append("[返回/妙音返回]");
                        GameUtil.changeNpcSession(id, 20072, "妙音仙子", sb.toString());
                        return;
                    } else if ("openClearStore".equals(menu_item)) {
                        // 清理仓库
                        GameUtil.changeNpcSession(id, 20072, "妙音仙子",
                                "请选择仓库页码。[清理第一页/clearStoreS1][清理第二页/clearStoreS2][清理第三页/clearStoreS3][清理第四页/clearStoreS4][清理全部/clearStore][返回/妙音返回]");
                        return;
                    } else if (menu_item.startsWith("clearStore")) {
                        // 清理背包
                        GameUtil.clearStorePackage(menu_item, gameObjectChar);
                        return;
                    } else if (menu_item.startsWith("openSubmitEquipDlg")) {
                        //装备回收这里只显示改12
                        StringBuilder sb = new StringBuilder();
                        for (int l = 0; l < chara.backpack.size(); ++l) {
                            if (chara.backpack.get(l).goodsInfo.color == 12) {
                                sb.append(chara.backpack.get(l).pos).append("|");
                            }
                        }
                        if (sb.toString().isEmpty()) {
                            GameUtil.sendMeTips("未发现有可回收的装备");
                            return;
                        }
                        Vo_DESTROY_VALUABLE_LIST info = new Vo_DESTROY_VALUABLE_LIST();
                        info.setId_str(sb.toString());
                        info.setType(2);
                        GameObjectChar.send(new MSG_DESTROY_VALUABLE_LIST(), info);
                        return;
                    } else if ("about_v".equals(menu_item)) {
                        GameUtil.confirm(chara, "当前版本号:" + GameCommonUtil.gameVersion, "showVersion");
                        return;
                    } else {
                        // 判断是否清理宠物
                        if ("清理全部".equals(menu_item) || menu_item.indexOf("清理全部") != -1) {
                            menu_item = "清理背包";
                        }
                        // 清理背包
                        GameUtil.clearBackPackage(menu_item, gameObjectChar);
                    }
                }

                if (menu_item.equals("我要购买野生宠物")) {
                    List<CreepsStore> creepsStoreList = (List<CreepsStore>) GameData.that.baseCreepsStoreService
                            .findAll();
                    GameObjectChar.send(new M40967_0(), creepsStoreList);
                    return;
                }
                if (menu_item.equals("买卖")) {
                    List<MedicineShop> medicineShopList = (List<MedicineShop>) GameData.that.baseMedicineShopService
                            .findAll();
                    GameObjectChar.send(new M65503_0(), medicineShopList);
                    return;
                }
                if (menu_item.equals("我要做买卖")) {
                    List<GroceriesShop> groceriesShopList = (List<GroceriesShop>) GameData.that.baseGroceriesShopService
                            .findAll();
                    GameObjectChar.send(new M65503_0(), groceriesShopList);
                    return;
                }
                Vo_61553_0 zurenTask = chara.taskMap.get("助人为乐—打抱不平");
                if (menu_item.equals("助人为乐—打抱不平s1") && zurenTask != null && zurenTask.currentTask.equals("助人为乐—打抱不平s1")) {
                    chara.taskMap.get("助人为乐—打抱不平").task_state = "1";
                    chara.taskMap.get("助人为乐—打抱不平").task_extra_para = "0";
                    chara.current_task = menu_item;
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "唉，店里又被这些家伙给弄得乱七八糟。", "助人为乐—打抱不平", 6016, "冯喜来");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                } else if (menu_item.equals("助人为乐—打抱不平s2") && chara.taskMap.get("助人为乐—打抱不平") != null && zurenTask.currentTask.equals("助人为乐—打抱不平s2")) {
                    chara.taskMap.get("助人为乐—打抱不平").task_state = "1";
                    chara.taskMap.get("助人为乐—打抱不平").task_extra_para = "0";
                    chara.current_task = menu_item;
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#Y无名剑客#n，你竟然纵容你兄弟酗酒闹事，还说要教训我？", "助人为乐—打抱不平");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                } else if (menu_item.equals("助人为乐—打抱不平s3") && chara.taskMap.get("助人为乐—打抱不平") != null && zurenTask.currentTask.equals("助人为乐—打抱不平s3")) {
                    chara.taskMap.get("助人为乐—打抱不平").task_state = "1";
                    chara.current_task = menu_item;
                    Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "掌柜的，刚才那个恶徒还在没？", "助人为乐—打抱不平");
                    GameObjectChar.send(new M45056_0(), vo_45056_2);
                    return;
                }

                Chara chara2 = gameObjectChar.chara;
                if (npc == null) {
                    return;
                }
                GameObjectChar.send(new M4155_0(), id);
                // 地图守护神
                if (MapGuardianService.isProtector(npc.getName())) {
                    MapGuardianService.openMenu(chara2, npc);
                    return;
                }
                Vo_45056_0 vo_45056_3 = GameUtil.a45056(chara2);
                GameObjectChar.send(new M45056_0(), vo_45056_3);
                ListVo_65527_0 vo_65527_2 = GameUtil.a65527(chara2);
                GameObjectChar.send(new M65527_0(), vo_65527_2);
            }
        }
    }

    @Override
    public int cmd() {
        return 12344;
    }

    // 玩家从多闻道人那里获取装备
    public void geizhuangb(Chara chara) {
        ZhuangbeiInfo zhuangb = new ZhuangbeiInfo();
        List<ZhuangbeiInfo> byAttrib = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService.findByAttrib(1);
        for (int i = 0; i < byAttrib.size(); ++i) {
            if (byAttrib.get(i).getMetal() == chara.polar && byAttrib.get(i).getAmount() == 1) {
                zhuangb = byAttrib.get(i);
                GameUtil.huodezhuangbei(chara, zhuangb, 0);

                Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                vo_20481_0.msg = "你获得了1把#R" + zhuangb.getStr() + "#n。";
                vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20481_0(), vo_20481_0);

                Vo_20480_0 vo_20480_0 = new Vo_20480_0();
                vo_20480_0.msg = "你获得了#R260#n点经验";
                vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
                GameObjectChar.send(new M20480_0(), vo_20480_0);

                Vo_8165_0 vo_8165_0 = new Vo_8165_0();
                vo_8165_0.msg = "你获得了#R260#n经验、1把#R" + zhuangb.getStr() + "#n。";
                vo_8165_0.active = 0;
                GameObjectChar.send(new M8165_0(), vo_8165_0);

                Vo_40964_0 vo_40964_0 = new Vo_40964_0();
                vo_40964_0.type = 1;
                vo_40964_0.name = zhuangb.getStr().toString();
                vo_40964_0.param = "98107";
                vo_40964_0.rightNow = 1;
                GameObjectChar.send(new M40964_0(), vo_40964_0);

                Vo_40965_0 vo_40965_0 = new Vo_40965_0();
                vo_40965_0.guideId = 19;
                GameObjectChar.send(new M40965_0(), vo_40965_0);
            }
        }
    }

    static {
        CMD_SELECT_MENU_ITEM.type2 = new HashMap<Integer, List<RenwuMonster>>();
    }
    public static void refreshTask(Chara chara){
        Integer tiandixingCount = GameConfig.config.getBaseConfig().getTiandixingNum();
        Integer tiandixing = chara.tiandixingNum;
        tiandixingCount = tiandixingCount - tiandixing;

        Integer shangguCount = GameConfig.config.getBaseConfig().getShangguNum();
        Integer shanggu = chara.shanggucishu;
        shangguCount = shangguCount -shanggu;
        Integer wannianCount = GameConfig.config.getBaseConfig().getWannianNum();
        Integer wannian = chara.wanniancishu;
        Integer zhanshenCount = GameConfig.config.getBaseConfig().getZhanshenNum();
        Integer zhanshen = chara.zhanshencishu;
        Integer nianshouCount = GameConfig.config.getBaseConfig().getNewYearBeastNum();
        Integer nianshou = chara.newYearBeastNum;
        Integer zhuxianCount = GameConfig.config.getBaseConfig().getZhuxianCishu();
        Integer zhuxian = chara.zhuxian_cishu;
        Integer qishaCount = GameConfig.config.getBaseConfig().getQishaCount();
        Integer qisha = chara.qishaCount;
        Integer molongCount = GameConfig.config.getBaseConfig().getMolongCount();
        Integer molong = chara.molongCount;


        Integer shenyuanCount = GameConfig.config.getBaseConfig().getDiyushenyuanNum();
        Integer shenyuan = chara.diyushenyuanNum;
        Integer superBossCount = GameConfig.config.getBaseConfig().getSuperBossNum();
        Integer superBoss = chara.superBossNum;
        Integer bossCount = GameConfig.config.getBaseConfig().getBossNum();
        Integer boss = chara.gongchengcishu;

        Integer jiutianCount = GameConfig.config.getBaseConfig().getTotalCheckpoint();
        Integer jiutian = chara.totalCheckpoint;
        StringBuffer jiangli = new StringBuffer();
        jiangli.append(" 战神次数：#R"+zhanshen+"/"+zhanshenCount +"#n            | 诛仙次数：#R"+zhuxian+"/"+zhuxianCount+"#n\n");
        jiangli.append(" 魔龙次数：#R"+molong+"/"+molongCount +"#n               | 年兽次数：#R"+nianshou+"/"+nianshouCount+"#n\n"   );
        jiangli.append(" 天罡地煞次数：#R"+tiandixingCount +"#n        | 地狱深渊次数：#R"+shenyuan+"/"+shenyuanCount+"#n\n" );
        jiangli.append(" 上古妖王次数：#R"+shangguCount +"#n        | 万年妖王次数：#R"+wannian+"/"+wannianCount+"#n\n");
        jiangli.append(" 七杀试炼次数：#R"+qisha+"/"+qishaCount +"#n       | 九天真君次数：#R"+jiutian+"/"+jiutianCount+"#n\n");
        jiangli.append(" 攻城BOSS次数：#R"+boss+"/"+bossCount +"#n   | 超级BOSS次数：#R"+superBoss+"/"+superBossCount+"#n\n");


//        StringBuffer jiangli = new StringBuffer();
//        jiangli.append("#Y当前击杀BOSS剩余次数：#n\n");
//        jiangli.append("	剩余战神次数：#R"+(zhanshenCount-zhanshen) +"#n\n");
//        jiangli.append("	剩余天地星次数：#R"+(tiandixingCount-tiandixing) +"#n\n");
//        jiangli.append("	剩余上古妖王次数：#R"+(shangguCount-shanggu) +"#n\n");
//        jiangli.append("	剩余万年妖王次数：#R"+(wannianCount-wannian) +"#n\n");

        GameUtilRenWu.createTask("怪物次数", 0, "#Y当前BOSS击杀次数#n", "BOSS击杀次数", chara,
                "每日BOSS挑战任务凌晨五点更新重置", jiangli.toString());
    }
}