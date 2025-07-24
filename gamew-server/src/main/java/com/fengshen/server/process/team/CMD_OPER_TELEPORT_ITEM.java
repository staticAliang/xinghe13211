package com.fengshen.server.process.team;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_4121_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhengzihang
 * @description: 一键召集队员
 * @date 2021/2/10 12:51
 */
@Slf4j
@Service
public class CMD_OPER_TELEPORT_ITEM implements GameHandler {
    @Override
    public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
        final int id = GameReadTool.readInt(buff);
        final int type = GameReadTool.readShort(buff);
        final int ids = GameReadTool.readShort(buff);
        //当前角色
        GameObjectChar currentGameObjectChar = GameObjectChar.getGameObjectChar();
        final Chara charaduizhang = currentGameObjectChar.chara;
        if (charaduizhang.mapid == 38004 || charaduizhang.mapName.equals("试道场")) {
            GameUtil.sendMeTips("当前地图不支持此操作.");
            return;
        }
        log.info("召集队员 chara 当前角色 {}", charaduizhang);
        List<Integer> liduiIds = new ArrayList<>();
        List<Vo_4121_0> gameTemZhanliduiyuan = currentGameObjectChar.gameTeam.zhanliduiyuan;
        if (id == 0) {
            for (Vo_4121_0 vo_4121_0 : gameTemZhanliduiyuan) {
                if (vo_4121_0.memberteam_status == 2) {
                    liduiIds.add(vo_4121_0.id);
                }
            }
        } else {
            liduiIds.add(id);
        }
        for (Integer liduiId : liduiIds) {
            //目标角色
            final GameObjectChar formGameObjectChar = GameObjectCharMng.getGameObjectChar(liduiId);
            //加入队伍
            if (formGameObjectChar != null) {
                Chara formChara = formGameObjectChar.chara;
                log.info("召集队员 chara 被传送角色 {}", formChara.toString());
                if (formChara.isFight) {
                    final Vo_20481_0 vo_20481_0 = new Vo_20481_0();
                    vo_20481_0.msg = "对方现在正忙";
                    vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
                    GameObjectChar.send(new M20481_0(), vo_20481_0);
                    continue;
                }
                formChara.x = charaduizhang.x;
                formChara.y = charaduizhang.y;
                formChara.dir = charaduizhang.dir;
                formChara.mapid = charaduizhang.mapid;
                formChara.mapName = charaduizhang.mapName;
                //加入队伍
                currentGameObjectChar.gameTeam.duiwu.add(formChara);
                //currentGameObjectChar.addGameTeamChara(formChara);
                //更新所有人的队伍信息
                for (int i = 0; i < gameTemZhanliduiyuan.size(); ++i) {
                    final GameObjectChar session2 = GameObjectCharMng.getGameObjectChar(gameTemZhanliduiyuan.get(i).id);
                    session2.setGameTeam(currentGameObjectChar.getGameTeam());
                }
                currentGameObjectChar.gameMap.joinduiyuan(formGameObjectChar, charaduizhang);

                C28_0.goBackTeam(currentGameObjectChar,formChara,charaduizhang);
            }
        }
    }

    @Override
    public int cmd() {
        return 16590;
    }
}
