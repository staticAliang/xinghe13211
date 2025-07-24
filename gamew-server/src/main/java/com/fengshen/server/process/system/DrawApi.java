package com.fengshen.server.process.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.*;
import com.fengshen.db.service.chara.ChengweiService;
import com.fengshen.server.data.game.LuckDrawUtils;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.FudaiConfig;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.config.ChoujiangConfig;
import com.fengshen.server.exception.PackOverflowException;
import com.fengshen.server.game.*;
import com.fengshen.server.util.GameConfig;
import com.mysql.jdbc.StringUtils;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
@Slf4j
public class DrawApi {

    public static void fuDai(GameObjectChar gameObjectChar) {
        String[] luckInfo = DrawApi.fuDaiDraw();
        LuckDrawUtils.fuDaiChouJiang(luckInfo, gameObjectChar, "福袋");
        if(luckInfo[1].equals("道行")) {
            GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你获得了#R" , luckInfo[3],"#n天道行"));
        }else if(luckInfo[1].equals("潜能")) {
            GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你获得了#R" , luckInfo[3],"#n点潜能"));
        }else if(luckInfo[1].equals("经验")){
            GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你获得了#R" , luckInfo[3],"#n点经验"));
        }else if(luckInfo[1].equals("积分")){
            GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你获得了#R" , luckInfo[3],"#n点积分"));
        }else if(luckInfo[1].equals("洛书经验")){
        }else if(luckInfo[1].equals("武学")){
        }else if(luckInfo[1].equals("充值")){
        }else {
            GameUtil.sendMeTips(org.apache.commons.lang3.StringUtils.join("你获得了#R",luckInfo[0]));
        }
        log.info("福袋获得物品:{}", Arrays.toString(luckInfo));
    };

    /**
     * 福袋抽奖
     *
     * @return
     */
    public static String[] fuDaiDraw() {
        String[] result = null;
        // 根据几率选出几等奖
        Example example = new Example(ConfigInfo.class);
        example.selectProperties("data");
        example.createCriteria().andEqualTo("keyName", "福袋抽奖");
        ConfigInfo ci = GameData.that.configInfoService.selectOneByExample(example);
        FudaiConfig config = JSONObject.parseObject(ci.getData(), FudaiConfig.class);
        int baseNumber = config.getBaseNumber();
        double randomLevel = ThreadLocalRandom.current().nextDouble(baseNumber == 0 ? 100 : baseNumber);
        Double maxNo0 = config.getNo0();
        Double maxNo1 = config.getNo1()+maxNo0;
        Double maxNo2 = config.getNo2()+maxNo1;
        Double maxNo3 = config.getNo3()+maxNo2;
        Double maxNo4 = config.getNo4()+maxNo3;

        int level = 5;
        if(randomLevel < maxNo0){
            level = 0;
        }else if(maxNo0 <= randomLevel && randomLevel < maxNo1){
            level = 1;
        }else if(maxNo1 <= randomLevel && randomLevel < maxNo2){
            level = 2;
        }else if(maxNo2 <= randomLevel && randomLevel < maxNo3){
            level = 3;
        }else if(maxNo3 <= randomLevel && randomLevel <=maxNo4){
            level = 4;
        }
        // 查询出当前等级的数量
        List<LuckDrawItem> findByLevel = GameData.that.luckDrawItemService.getLuckByLevel(level,"fudai");
        // 随机取出一条数据
        log.info("LuckDrawItem:"+ JSON.toJSON(findByLevel));
        LuckDrawItem choujiang = findByLevel.get(ThreadLocalRandom.current().nextInt(findByLevel.size()));
        String awardStr = choujiang.getItem();
        awardStr = awardStr.substring(2, awardStr.length() - 2);
        String[] award = awardStr.split("\\|");
        String name = award[0];
        if ("物品".equals(name)) {
            String item = award[1];
            String wpName = item.split("#")[0];
            result = new String[] { wpName, "物品", awardStr, String.valueOf(level)};
        } else if ("宠物".equals(name)) {
            String nameAndType = award[1].split("\\$")[0];
            String[] str = nameAndType.split("\\(");
            String petName = str[0]; // 宠物名字
            String petType = str[1].replace(")", ""); // 宠物名字
            result = new String[] { petName, petType, choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        } else if ("首饰".equals(award[0])) {
            // #I首饰|七星手链$指定$35#I
            result = new String[] { award[1].split("\\$")[0], "首饰", choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        } else if ("装备".equals(award[0])) {
            String[] equipType = award[1].split("\\$");
            result = new String[] { equipType[0], "装备", choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }else if("经验".equals(award[0]) || "潜能".equals(award[0]) || "道行".equals(award[0])) {
            String[] equipType = award[1].split("\\$");
            result = new String[] { equipType[0], award[0], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }else if("积分".equals(award[0])) {
            //#|积分|2000#I
            result = new String[] { name, award[1], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }else if("法宝".equals(award[0])) {
            //#I法宝|番天印$24$24#I
            String[] equipType = award[1].split("\\$");
            result = new String[] { equipType[0], award[0], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }else if("洛书经验".equals(name)) {
            // 新增 福袋奖品，
            //#I洛书经验|40000#I
            result = new String[] { name, award[1], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }else if("充值".equals(name)) {
            result = new String[] { name, award[1], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }else if("武学".equals(name)) {
            result = new String[] { name, award[1], choujiang.getItem(), String.valueOf(choujiang.getLevel())};
        }

        return result;
    }
    public static void  huodechongzhi(Chara chara, String money, String accountName){
        log.info("检测到福袋抽奖，开始充值："+chara.getName());
        Charge charge = new Charge();
        charge.setAccountname(accountName);
        charge.setCoin(Integer.parseInt(money));
        charge.setMoney(Integer.parseInt(money));
        charge.setCode("10087");
        charge.setState(0);
        charge.type = 1;
        charge.remark = "福袋抽奖充值";
        GameData.that.baseChargeService.add(charge);
        Vo_20481_0 vo_20481_0 = new Vo_20481_0();
        vo_20481_0.msg = "获得充值#R" + money;
        vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
        GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20481_0(), vo_20481_0);

    }

    public static void fudaicishu(Integer cishu,GameObjectChar gameObjectChar){
        // 查询出当前等级的数量
        Chara chara= gameObjectChar.chara;
        List<FuDaiChengwei> findBycishu = GameData.that.fuDaiChengweiService.getAllReChargeChengwei();
        StringBuffer sb = new StringBuffer();
        log.info("当前人："+chara.name+" 当前次数："+cishu+"");
        sb.append("#Y开福袋赢大奖#n\n");
        for (FuDaiChengwei fuDaiChengwei : findBycishu) {
            sb.append("#R开启福袋"+fuDaiChengwei.getCishu()+"个奖励："+fuDaiChengwei.getRemake()+"#n\n");
            if(fuDaiChengwei.getCishu().intValue() == cishu.intValue()){
                String type = fuDaiChengwei.getType();
                String[] peizhi = fuDaiChengwei.getName().split(",");
                // 按照逗号分割， 第一个 名称， 第二个数量，第三个 等级  ,第四个相性，或者宠物类型
                // 1野生，2宝宝，3变异，4神兽，5守护
                // 1金，2木，3水，4火，5土
                String name = peizhi[0];
                String num = peizhi[1];
                String lv = peizhi[2];
                String wupinType = peizhi[3];
                if ("物品".equals(type)) {
                    StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(name);
                    GameUtil.huodedaoju(gameObjectChar, info, Integer.parseInt(num));
                }else if ("宠物".equals(type)) {
                    log.info("获得宠物。。。。。");
                    try {
                        Pet pet = GameData.that.basePetService.findOneByName(name);
                        log.info("获得宠物。。。。。2"+JSON.toJSON(pet));
                        if(pet != null) {
                            Petbeibao petbeibao = new Petbeibao();
                            if("6".equals(wupinType)){
                                GameUtil.huodezuoqi(chara, name, "福袋次数");
                            }else{
                                petbeibao.PetCreate(pet, chara, 0, Integer.parseInt(wupinType), "福袋次数");
                                log.info("获得宠物。。。。。3"+JSON.toJSON(petbeibao));
                                List<Petbeibao> list = new ArrayList<Petbeibao>();
                                chara.pets.add(petbeibao);
                                list.add(petbeibao);
                                log.info("获得宠物。。。。。4"+JSON.toJSON(list));
                                GameObjectChar.send(new MSG_UPDATE_PETS(), list);
                            }

                        }
                    } catch (Exception e) {
                    }
                }else if("积分".equals(type)) {
                    GameUtil.addchargeScore(gameObjectChar,  Integer.parseInt(num), "福袋次数");
                }else if("法宝".equals(type)) {
                    GameUtil.jifenhuodefabao(chara, name, Integer.parseInt(lv), "福袋次数", Integer.parseInt(wupinType));
                }else if("充值".equals(type)) {
                    String accountName =gameObjectChar.account.getName();
                    DrawApi.huodechongzhi(chara, num,accountName);
                }else if("称号".equals(type)) {
                    ChengweiService chengweiService = SpringBeanUtils.getBean(ChengweiService.class);
                    Chengwei chengwei = chengweiService.getChengweiByName(name);
                    if (chengwei != null) {
                        if (chara.getChenghao().get(chengwei.getName()) != null) {
                            // 称谓已经获取了,无需再次获取
                            return;
                        }
                        GameUtil.chenghaoxiaoxi(chara, chengwei.getName(), chengwei.getName());
                    }
                }

                GameUtil.sendMeTips("恭喜你开启福袋次数到达#R"+ cishu+"#n次获得"+type+"#R" + name + "#n。");

            }
        }

        GameUtilRenWu.createTask("福袋次数", 0, "#Y当前开启福袋次数：#n#R"+cishu, "福袋任务", chara,
                "#Y"+chara.name+"#n当前开启福袋次数是：#R"+cishu, sb.toString());
    }

    public static void main(String[] args) {
        System.out.println("满属性".matches("^[-\\+]?[\\d]*$"));
    }
}
