package com.fengshen.server.service;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Npc;
import com.fengshen.server.data.vo.Vo_61613_0;
import com.fengshen.server.data.write.MSG_MASTER_INFO;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 挑战掌门
 */
@Service
public class ChallengeLeaderService {
    /**
     * key:polar
     */
    private static Map<Integer, Vo_61613_0> default_ZhangmenMap = new HashMap<>();
    /**
     * 通知掌门信息
     * @param polar
     */
    public static void notifyLeaderInfo(int polar){
        String zhangMen = GameUtil.getZhangMenName(polar);
        CharaStatue charaStatue = CharaStatueService.getCharStaure(zhangMen);
        if(null == charaStatue){
            GameObjectChar.send(new MSG_MASTER_INFO(), getDefaultInfo(polar));
        }else{
            GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();

            Vo_61613_0 vo_61613_0 = new Vo_61613_0();
            vo_61613_0.polar = polar;
            vo_61613_0.isLeader = gameObjectChar.chara.id == charaStatue.id?1:0;
            vo_61613_0.name = charaStatue.name;
            vo_61613_0.title = zhangMen;
            vo_61613_0.level = ""+charaStatue.level;
            vo_61613_0.party_name = charaStatue.partyName;
            /**
             * 套装icon
             */
            vo_61613_0.suit_icon = charaStatue.suit_icon;
            vo_61613_0.weapon_icon = charaStatue.weapon_icon;
            vo_61613_0.icon = charaStatue.waiguan;
            //仙魔光效
            vo_61613_0.xianmo = 0;
            //掌门留言
            GameObjectChar leader = GameObjectCharMng.getGameObjectChar(charaStatue.id);
            if(null!=leader&&null!=leader.chara.leaderNotice){
                vo_61613_0.signature = leader.chara.leaderNotice;
            }else{
                vo_61613_0.signature = String.format("大家好，我是新一任%s", zhangMen);
            }
            vo_61613_0.vipLevel = 0;
            vo_61613_0.gender = charaStatue.sex;
            GameObjectChar.send(new MSG_MASTER_INFO(), vo_61613_0);
        }
    }

    private static Vo_61613_0 getDefaultInfo(int polar){
        if(default_ZhangmenMap.containsKey(polar)){
            return default_ZhangmenMap.get(polar);
        }

        String name = GameUtil.getZhangMenName(polar);
        Npc npc = GameData.that.baseNpcService.findOneByName(name);
        Vo_61613_0 vo_61613_0 = new Vo_61613_0();
        vo_61613_0.polar = polar;
        vo_61613_0.isLeader = 0;
        vo_61613_0.name = name;
        vo_61613_0.title = name;
        vo_61613_0.level = "50";
        vo_61613_0.party_name = "";
        /**
         * 套装icon
         */
        vo_61613_0.suit_icon = 0;
        vo_61613_0.weapon_icon = 0;
        vo_61613_0.icon = npc.getIcon();
        //仙魔光效
        vo_61613_0.xianmo = 0;
        //掌门留言
        vo_61613_0.signature = String.format("大家好，我是新一任%s", name);
        vo_61613_0.vipLevel = 0;
        vo_61613_0.gender = 0;

        default_ZhangmenMap.put(polar, vo_61613_0);//放入缓存
        return vo_61613_0;
    }

//    public static void challengeLeader(Chara chara) {
//        String zhangMen = GameUtil.getZhangMenName(chara.polar);
//        CharaStatue charaStatue = CharaStatueService.getCharStaure(zhangMen);
//        if(null == charaStatue){
//            FightManager.goFightChallengeCharaStatue(chara, chara.polar);
//        }else{
//            FightManager.goFightChallengeCharaStatue(chara, charaStatue, BattleType.CHALLENGE_LEADER);
//        }
//        chara.leaderTodayFailNum++;
//    }

    /**
     * 修改掌门留言
     * @param polar
     * @param msg
     */
    public static void changeMsg(int polar, String msg){
        String zhangMen = GameUtil.getZhangMenName(polar);
        CharaStatue charaStatue = CharaStatueService.getCharStaure(zhangMen);
        GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
        if(gameObjectChar.chara.id!=charaStatue.id){
            return;
        }
        gameObjectChar.chara.leaderNotice = msg;
    }
}
