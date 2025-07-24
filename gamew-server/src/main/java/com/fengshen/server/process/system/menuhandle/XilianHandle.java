package com.fengshen.server.process.system.menuhandle;

//import cn.hutool.core.util.StrUtil;
import com.fengshen.db.domain.Npc;
import com.fengshen.server.data.vo.Vo_8247_0;
import com.fengshen.server.data.write.M8247_0_MSG_MENU_LIST;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.util.GameConfig;

import java.util.Map;

public class XilianHandle {

    public static boolean processXilianMenu(String menu_item, GameObjectChar gameObjectChar, Chara chara, Npc npc) {
        if ( menu_item.equals("do_shiZhaungXiLian") || menu_item.equals("do_faBaoXiLian") || menu_item.equals("do_peiShiXiLian") || menu_item.equals("do_zuoQiXiLian") ) {
            int xilianOpen = GameConfig.config.getBaseConfig().getXilianOpen();
            if (xilianOpen != 1) {
                GameUtil.sendMeTips("洗炼功能未开放");
                return true;
            }
            int maxValue = GameConfig.config.getBaseConfig().getXiLianMaxValue();
            if (maxValue == 0) {
                maxValue = 100;
            }
            int lianPoint = GameConfig.config.getBaseConfig().getXiLianPoint();
            if (chara.getChargeScore()< lianPoint) {
                GameUtil.sendMeTips("积分不足");
                return true;
            }
            chara.subChargeScore(lianPoint,"心法洗炼");
            int randomAttr = 0;
            Map<String, Integer> xiLianInfoMap = chara.getXiLianInfoMap();




            if (menu_item.equals("do_shiZhaungXiLian")) {
                Integer shiZhaungXiLian = xiLianInfoMap.get("shiZhaungXiLian");
                if (shiZhaungXiLian != null && shiZhaungXiLian == maxValue) {
                    GameUtil.sendMeTips("已经是最大等级。");
                    return true;
                }
                randomAttr = XiLianUtil.getRandomAttr();
                xiLianInfoMap.put("shiZhaungXiLian", randomAttr);
            }
            if (menu_item.equals("do_faBaoXiLian")) {
                Integer faBaoXiLian = xiLianInfoMap.get("faBaoXiLian");
                if (faBaoXiLian != null && faBaoXiLian == maxValue) {
                    GameUtil.sendMeTips("已经是最大等级。");
                    return true;
                }
                randomAttr = XiLianUtil.getRandomAttr();
                xiLianInfoMap.put("faBaoXiLian", randomAttr);
            }
            if (menu_item.equals("do_peiShiXiLian")) {
                Integer peiShiXiLian = xiLianInfoMap.get("peiShiXiLian");
                if (peiShiXiLian != null && peiShiXiLian == maxValue) {
                    GameUtil.sendMeTips("已经是最大等级。");
                    return true;
                }
                randomAttr = XiLianUtil.getRandomAttr();
                xiLianInfoMap.put("peiShiXiLian", randomAttr);
            }
            if (menu_item.equals("do_zuoQiXiLian")) {
                Integer zuoQiXiLian = xiLianInfoMap.get("zuoQiXiLian");
                if (zuoQiXiLian != null && zuoQiXiLian == maxValue) {
                    GameUtil.sendMeTips("已经是最大等级。");
                    return true;
                }
                randomAttr = XiLianUtil.getRandomAttr();
                xiLianInfoMap.put("zuoQiXiLian", randomAttr);
            }
            GameUtil.sendMeTips("洗炼成功,所有基础属性#R+" + randomAttr);
            GameUtilRenWu.xiLianTask(chara);
            GameUtil.MSG_UPDATE_ALL_a65511(gameObjectChar);
            menu_item = menu_item.replace("do_", "");
        }
        if (menu_item.equals("shiZhaungXiLian")|| menu_item.equals("faBaoXiLian") || menu_item.equals("peiShiXiLian") || menu_item.equals("zuoQiXiLian") ) {
            String typeStr = "";
            if ("shiZhaungXiLian".equals(menu_item)) {
                typeStr = "时装";
            }
            if ("faBaoXiLian".equals(menu_item)) {
                typeStr = "法宝";
            }
            if ("peiShiXiLian".equals(menu_item)) {
                typeStr = "配饰";
            }
            if ("zuoQiXiLian".equals(menu_item)) {
                typeStr = "坐骑";
            }
            String content = "洗炼#Y"+typeStr+"#n需要消耗#R"+GameConfig.config.getBaseConfig().getXiLianPoint()+"#n积分[开始"+typeStr+"洗炼/do_"+ menu_item +"][离开/离开]";
            final Vo_8247_0 vo_8247_3 = new Vo_8247_0();
            vo_8247_3.id = 1699;
            vo_8247_3.portrait = npc.getIcon();
            vo_8247_3.pic_no = 1;
            vo_8247_3.content = content;
            vo_8247_3.secret_key = "";
            vo_8247_3.name = npc.getName();
            vo_8247_3.attrib = 1;
            GameObjectChar.send(new M8247_0_MSG_MENU_LIST(), vo_8247_3);
            return true;
        }
        return false;
    }

}
