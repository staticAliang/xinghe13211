package com.fengshen.server.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.MailboxRefresh;
import com.fengshen.db.util.RedisUtils;
import com.fengshen.server.data.constant.RedisKeyConstant;
import com.fengshen.server.data.vo.Vo_62209_0;
import com.fengshen.server.data.vo.Vo_MAILBOX_REFRESH;
import com.fengshen.server.data.write.M62209_0;
import com.fengshen.server.data.write.system.MSG_MAILBOX_REFRESH;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaChengWei;
import com.fengshen.server.game.*;
import com.fengshen.server.process.dari.rank_role;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharaTimes {

    private static final Logger log = LoggerFactory.getLogger(CharaTimes.class);

    @Autowired
    private RedisUtils redisUtils;
    @Scheduled(cron = "0 0/10 * * * ?")
    public void checkChengWei(){
        List<GameObjectChar> all = GameObjectCharMng.getAll();
        for (GameObjectChar gameSession : all) {
            String flag = "";
            Chara chara = gameSession.chara;
            List<CharaChengWei> charaChengWeis = chara.charaChengWeis;
            Iterator<CharaChengWei> iterator = charaChengWeis.iterator();
            while (iterator.hasNext()) {
                CharaChengWei charaChengWei = iterator.next();
                if(-1 != charaChengWei.getTime()){
                    Date nowTime = new Date();
                    Date createTime = charaChengWei.getCreateTime();
                    long interval = (nowTime.getTime() -createTime.getTime() )/1000/60;
                    if(interval >= charaChengWei.getTime()){
                        flag = "1";
                        chara.chenghao.remove(charaChengWei.getName());
                        iterator.remove();
                    }
                }

            }
           if("1".equals(flag)){
              GameUtil.refreshChengHao(chara);
            }
        }
    }

    @Scheduled(
            cron = "0 */10 * * * ?"
            //cron = "0 */4 * * * ?"
    )
    public void refreshteshuziduan() {
        String dari_life_str = redisUtils.get("dari_life_str");//当前血量
        if(Long.parseLong(dari_life_str)<=0) {
            //重置大日金乌血量(注意这里如果修改了max最大得值，那么需要更新)
            redisUtils.set("dari_life_str", GameConfig.config.getDari().getDari_life_str());
            redisUtils.set("dari_max_life_str", GameConfig.config.getDari().getDari_max_life_str());
            RedisUtils redisUtils = GameData.that.redisUtils;
            String rankString = redisUtils.get(RedisKeyConstant.RANK_LEFT);
            List<rank_role> rankList = Lists.newArrayList();
            Map<String, JSONObject> mapRank = (Map) JSON.parseObject(rankString, Map.class);
            if (mapRank == null) {
                mapRank = Maps.newHashMap();
            }
            Iterator var65 = ((Map) mapRank).values().iterator();

            while (var65.hasNext()) {
                JSONObject jsonObject = (JSONObject) var65.next();
                rank_role rankRole = (rank_role) JSON.parseObject(jsonObject.toJSONString(), rank_role.class);
                rankList.add(rankRole);
            }
            rankList.sort(Comparator.comparingInt(rank_role::getRank).reversed());

            List<Characters> selectAll = GameData.that.characterService.findAll();
            if (rankList.size() > 0) {
                String darijinwujiangli = null;
                for (int r = 0; r < rankList.size(); r++) {
                    if (rankList.get(r).getRank() == 1) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_1_reward();
                    } else if (rankList.get(r).getRank() == 2) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_2_reward();
                    } else if (rankList.get(r).getRank() == 3) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_3_reward();
                    } else if (rankList.get(r).getRank() == 4) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_4_reward();
                    } else if (rankList.get(r).getRank() == 5) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_5_reward();
                    } else if (rankList.get(r).getRank() == 6) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_6_reward();
                    } else if (rankList.get(r).getRank() == 7) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_7_reward();
                    } else if (rankList.get(r).getRank() == 8) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_8_reward();
                    } else if (rankList.get(r).getRank() == 9) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_9_reward();
                    } else if (rankList.get(r).getRank() == 10) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_10_reward();
                    } else if (rankList.get(r).getRank() == 11) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_11_reward();
                    } else if (rankList.get(r).getRank() == 12) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_12_reward();
                    } else if (rankList.get(r).getRank() == 13) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_13_reward();
                    } else if (rankList.get(r).getRank() == 14) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_14_reward();
                    } else if (rankList.get(r).getRank() == 15) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_15_reward();
                    } else if (rankList.get(r).getRank() == 16) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_16_reward();
                    } else if (rankList.get(r).getRank() == 17) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_17_reward();
                    } else if (rankList.get(r).getRank() == 18) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_18_reward();
                    } else if (rankList.get(r).getRank() == 19) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_19_reward();
                    } else if (rankList.get(r).getRank() == 20) {
                        darijinwujiangli = GameConfig.config.getDari().getDari_1_20_reward();
                    } else {
                        darijinwujiangli = GameConfig.config.getDari().getDari_20_30002_reward();
                    }

                    for (Characters characters : selectAll) {
                        if (characters.getName().equals(rankList.get(r).getName())) {
                            //发送邮件奖励
                            darimailbox(characters, rankList.get(r).getRank(),darijinwujiangli);
                        }
                    }
                }
            }
            //重置所有排名
            Map<String, rank_role> map = JSON.parseObject(rankString, Map.class);
            for(rank_role rank:rankList){
                rank.setRank((short) 30002);
                rank.setName(rank.getName());
                rank.setDamage(0);
                map.put(rank.getName(), rank);
                redisUtils.set(RedisKeyConstant.RANK_LEFT, map);
            }
            GameUtil.sendSystemMessage(19, "#Y大日金乌#n活动已经开始了，请各位道友前往#Y逍遥仙#n选择#R#Z大日金乌#Z#n一起消灭大日金乌，该活动奖励丰富,请积极参与！");
            return;
        }else{
            GameUtil.sendSystemMessage(19, "#Y大日金乌#n活动已经开始了，请各位道友前往#Y逍遥仙#n选择#R#Z大日金乌#Z#n一起消灭大日金乌，该活动奖励丰富,请积极参与！");
            return;
        }
    }


    public static void darimailbox(Characters chara, int paimimg,String jiangli){
        List<String[]> zhenshijiangli = GameCommonUtil.parseRewardStr(jiangli);
        String zhenshijianglis = "";
        for(String[] result:zhenshijiangli) {
            zhenshijianglis +=result[0]+"-"+result[1]+",";
        }
        String yuans = "";
        String[] ls = jiangli.split(",");
        for(int i=0;i<ls.length;i++){
            yuans = ls[0];
        }
        Vo_MAILBOX_REFRESH vo = new Vo_MAILBOX_REFRESH();
        vo.id = GameCommonUtil.UUID();
        vo.type = 0;
        vo.sender = jiangli; //
        vo.title = "大日金乌活动";//邮件名称
        vo.msg = "亲爱的#Y" + chara.getName() + "#n你在大日金乌活动排名第#R" + paimimg +"#n名，由于您出色的表现获得以下奖励：#L"+zhenshijianglis+"#n";//邮件文本
        vo.create_time = (int)(System.currentTimeMillis() / 1000L);//附件开始时间
        vo.expired_time = (int)(System.currentTimeMillis() / 1000L + 43200L);//附件结束时间
        vo.status = 0;//默认没有浏览的话会标红
        vo.attachment = yuans; //附件奖励
        vo.toGid = chara.getGid();
        MailboxRefresh mail = GameCommonUtil.convertMail(vo);
        GameData.that.mailboxRefreshService.insertSelective(mail);
        GameObjectChar.send(new MSG_MAILBOX_REFRESH(), Lists.newArrayList(new Vo_MAILBOX_REFRESH[]{vo}), chara.getId());
    }
}
