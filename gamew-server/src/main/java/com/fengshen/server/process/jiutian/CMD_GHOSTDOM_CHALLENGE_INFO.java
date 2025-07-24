package com.fengshen.server.process.jiutian;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.*;
//import com.fengshen.server.game.mng.AbstractAfterFightCallback;
import com.fengshen.server.util.GameConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * @author zhengzihang
 * @description: 请求挑战地狱深渊
 */
@Service
public class CMD_GHOSTDOM_CHALLENGE_INFO implements GameHandler {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(CMD_GHOSTDOM_CHALLENGE_INFO.class);
    }

    @Override
    public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
        final int cengshu = GameReadTool.readByte(buff);
        log.info("请求挑战地狱深渊, cengshu={}", cengshu);
        final GameObjectChar session = GameObjectChar.getGameObjectChar();
        final Chara chara = session.chara;

        if (chara.cengshu <  cengshu)
        {
            GameUtil.sendMeTips("先挑战上面的关卡");
            return;
        }

//        final Accounts accounts = GameData.that.baseAccountsService.findById(GameObjectChar.getGameObjectChar().accountid);
        if (checkFightNum(chara)) {
            return;
        }
//        GameUtil.sendTips("地狱恶魔魔气值#R" + chara.diyushenyuanNum * 2);
        final ArrayList<String> monsterList2 = new ArrayList<String>();
        monsterList2.add(cengshu+"层"+"炼狱恶魔");
        monsterList2.add("骷髅战士");
        monsterList2.add("骷髅战士");
        monsterList2.add("骷髅战士");
        monsterList2.add("骷髅战士");
        monsterList2.add("魔獒");
        monsterList2.add("魔獒");
        monsterList2.add("魔獒");
        monsterList2.add("魔獒");
        monsterList2.add("魔獒");
        FightManager.activeBoosGoFight(chara, monsterList2, false);
    }

    private boolean checkFightNum(Chara chara) {
        if (chara.diyushenyuanNum + 1 > GameConfig.config.getBaseConfig().getDiyushenyuanNum()) {
            GameUtil.sendMeTips("你已完成今日的挑战！");
            return true;
        }
        if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
            final StringBuilder msg = new StringBuilder();
            msg.append("队伍中[");
            boolean flag = false;
            for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                if (duiwu.diyushenyuanNum + 1 > GameConfig.config.getBaseConfig().getDiyushenyuanNum() && duiwu.id != chara.id) {
                    msg.append("#Y").append(duiwu.name).append(",");
                    flag = true;
                }
            }
            if (flag) {
                msg.append("#n]已完成今日的挑战！");
                msg.replace(msg.lastIndexOf(","), msg.lastIndexOf(",") + 1, "");
                GameUtil.sendMeTips(msg.toString());
                return true;
            }
//            for (final Chara chara2 : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
//                ++chara2.diyushenyuanNum;
//            }
        } else {
          //  ++chara.diyushenyuanNum;
        }
        return false;
    }

    public static void FightEnd(Chara chara) 
    {
        if (chara.cengshu < 9) {
            chara.cengshu++;
        }
    }

    @Override
    public int cmd() {
        return 53952;
    }
}
