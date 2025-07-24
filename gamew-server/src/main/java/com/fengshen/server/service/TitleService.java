package com.fengshen.server.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

@Service
public class TitleService {


    /**
     * 授予玩家称谓
     * @param gameObjectChar GameObjectChar
     * @param event 称谓来源
     * @param title 称谓内容
     */
    public static void grantTitle(GameObjectChar gameObjectChar, String event, String title) {
        if (gameObjectChar.chara.chenghao.containsKey(event) && gameObjectChar.chara.chenghao.get(event).equals(title)) {
            return;
        }
        GameUtil.chenghaoxiaoxi(gameObjectChar.chara, event, title);
        Vo_20481_0 vo_20481_9 = new Vo_20481_0();
        vo_20481_9.msg = String.format("你获得了#R%s#n的称谓。", title);
        vo_20481_9.time = (int)(System.currentTimeMillis() / 1000);
        GameObjectChar.send(new M20481_0(), vo_20481_9);
    }



    /**
     * 撤销用户称谓
     * @param uid uid
     * @param event 称谓来源
     */
    public static void removeUserTitle(Integer uid, String event) {
        if (uid == null) {
            return;
        }
        // 如果之前的掌门或者英雄在线，则直接在游戏管理器中移除，否则就从数据库读出他的信息然后修改
        GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(uid);
        if (gameObjectChar != null) {
            Chara chara = gameObjectChar.chara;
            chara.chenghao.remove(event);
            //发送称号消息并提示掌门被人顶下来
            if(chara.chenhao.indexOf("掌门") != -1) {
            	chara.chenhao = "";
            }
            GameCommonUtil.sendTips("道友你的#R掌门位置#n已被人挑战下来了,失去了#R掌门位置", uid);
            GameUtil.refreshChengHao(chara);
            gameObjectChar.gameMap.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(chara));
        } else {
            Characters characters = GameData.that.baseCharactersService.findById(uid);
            if(characters == null) {
            	return;
            }
            Chara chara = JSONObject.parseObject(characters.getData(), Chara.class);
            if(chara.chenhao.indexOf("掌门") != -1) {
            	chara.chenhao = "";
            }
            chara.chenghao.remove(event);
            characters.setData(JSONObject.toJSONString(chara));
            GameData.that.characterService.updateById(characters);
        }
    }
}
