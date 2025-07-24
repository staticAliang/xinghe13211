package com.fengshen.server.process.jiutian;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightContainer;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.*;
//import com.fengshen.server.game.mng.AbstractAfterFightCallback;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.fengshen.server.util.GameConfig;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author zhengzihang
 * @description: 请求挑战九天真君
 */
@Service
public class CMD_JIUTIAN_ZHENJUN implements GameHandler {
    private static final Logger log;

    static {
        log = LoggerFactory.getLogger(CMD_JIUTIAN_ZHENJUN.class);
    }

    static String[] boss = {
            "钧天君",
            "变天君",
            "玄天君",
            "幽天君",
            "成天君",
            "朱天君",
            "赤天君",
            "阳天君",
            "昊天君",
    };

    /**
     * 6418 钧天君
     * 6417 变天君
     * 6416 玄天君
     * 6415 幽天君
     * 6414 成天君
     * 6413 朱天君
     * 6412 赤天君
     * 6411 阳天君
     * 6410 昊天君
     */
    @Override
    public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
        int isTaskWalk = GameReadTool.readByte(buff);
        log.info("请求挑战九天真君, isTaskWalk={}", isTaskWalk);
        final GameObjectChar session = GameObjectChar.getGameObjectChar();
        final Chara chara = session.chara;
        if (checkFightNum(chara)) {
            return;
        }
        if (chara.curCheckpoint > 9) {
            chara.curCheckpoint = 9;
        } else if (chara.curCheckpoint < 0) {
            chara.curCheckpoint = 0;
        }
        if (isTaskWalk > 9) {
            GameUtil.sendTips("你已完成所有关卡，现在进行随机关卡指点。");
           // isTaskWalk = RandomUtil.randomInt(0, 9);
            Random random = new Random();
            isTaskWalk = random.nextInt(9);
        }
        if (isTaskWalk > chara.curCheckpoint) {
            GameUtil.sendTips("修道必须循序渐进，请完成前面关卡。");
            return;
        }
        // if (chara.totalCheckpoint < chara.curCheckpoint) {
        //     chara.totalCheckpoint = chara.curCheckpoint;
        // }
       // chara.totalCheckpoint++;
        final ArrayList<String> monsterList2 = new ArrayList<String>();
        final String monsterName = boss[8 - isTaskWalk];// "昊天君";
        monsterList2.add(monsterName);
        monsterList2.add("琼阵天将");
        monsterList2.add("琼阵天将");
        monsterList2.add("琼阵天将");
        monsterList2.add("琼阵天将");
        monsterList2.add("御阵天兵");
        monsterList2.add("御阵天兵");
        monsterList2.add("御阵天兵");
        monsterList2.add("御阵天兵");
        monsterList2.add("御阵天兵");
        FightManager.activeBoosGoFight(chara, monsterList2, false);
//        FightManager.activeBoosGoFight(chara, monsterList2, new AbstractAfterFightCallback() {
//
//            @Override
//            public void doFailed(Chara chara) {
//
//            }
//
//            @Override
//            public void doRun(Chara chara) {
//
//            }
//
//            @Override
//            public String getFightTypeName() {
//                return "九天试炼" + (chara.curCheckpoint + 1) + "关";
//            }
//
//            @Override
//            public String getMsg() {
//                return "完成九天真君的指点";
//            }
//
//            @Override
//            public void doSuccess(Chara chara, FightContainer fightContainer) {
//                super.doSuccess(chara, fightContainer);
//                if (chara.curCheckpoint < 9) {
//                    chara.curCheckpoint++;
//                }
//            }
//        });
    }

    public static void FightEnd(Chara chara) 
    {
        if (chara.curCheckpoint < 9) {
            chara.curCheckpoint++;
        }
    }

    private boolean checkFightNum(Chara chara) {
        if (chara.totalCheckpoint + 1 > GameConfig.config.getBaseConfig().getTotalCheckpoint()) {
            GameUtil.sendMeTips("你已完成今日的指点！");
            return true;
        }

        if (GameCommonUtil.isNotGameTeam(GameObjectCharMng.getGameObjectChar(chara.id).gameTeam)) {
            final StringBuilder msg = new StringBuilder();
            msg.append("队伍中[");
            boolean flag = false;
            for (final Chara duiwu : GameObjectChar.getGameObjectChar().gameTeam.duiwu) {
                if (duiwu.totalCheckpoint + 1 > GameConfig.config.getBaseConfig().getTotalCheckpoint() && duiwu.id != chara.id) {
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
//                ++chara2.totalCheckpoint;
//            }
        } else {
          //  ++chara.totalCheckpoint;
        }
        return false;
    }

    public static void main(String[] args) {
        int a= 0;
        System.out.println(a++);
       // System.out.println(++a);
        System.out.println(a);
        System.out.println(++a);
        System.out.println(a);
    }
    @Override
    public int cmd() {
        return 33320;
    }
}
