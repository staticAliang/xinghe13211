package com.fengshen.server.process.hunpo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.hunpo.MSG_NEIDAN_BREAK_TASK_SUCC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.NeiDanConfig;
import com.fengshen.server.domain.config.NeiDanVo;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_NEIDAN_BREAK_TASK implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求完成突破任务");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.upgrade_state != 0) {
			GameUtil.sendMeTips("只允许在真身下操作！");
			return;
		}
		if(chara.upgrade_level<120) {
			GameUtil.sendMeTips("元婴120后才能开启内丹！");
			return;
		}
		if(chara.taskMap.get("内丹修炼") != null) {
			try {
				if(chara.isNeiDanTop == 1) {
					GameUtil.sendMeTips("已达到极限");
					return;
				}
				
				//级数
				chara.danDataState+=1;
				//如果级数大于5的话那就表示是最高级了
				if(chara.danDataState>5) {
					chara.danDataState = 5;
					chara.isNeiDanTop = 1;
				}else {
					//阶段
					chara.danDataStage=1;
				}
				NeiDanConfig neiDanConfig = GameConfig.neiDanConfig;
				if(neiDanConfig != null) {
					String no = "no"+chara.danDataState;
					List<NeiDanVo> nextNeiDanConfigs = neiDanConfig.getInfo().get(no);
					if(nextNeiDanConfigs != null) {
						NeiDanVo nextNeiDanConfig = nextNeiDanConfigs.get(chara.danDataStage-1);
						if(nextNeiDanConfig != null) {
							//属性和相性点加成
							chara.attribPoint += nextNeiDanConfig.getAttribPoint();
							chara.polarPoint+= nextNeiDanConfig.getPolarPoint();
							
							chara.danDataPolarPoint+=nextNeiDanConfig.getPolarPoint();
							chara.danDataAttribPoint+=nextNeiDanConfig.getAttribPoint();
							//下一级分数
							chara.danDataExpToNextLevel = nextNeiDanConfig.getNextExp();
						}
					}
				}
			} finally {
				GameUtilRenWu.removeTask("内丹修炼", chara);
				//刷新信息
				GameUtil.sendUpdate(chara);
				//刷新内丹信息
				GameCommonUtil.refreshNeidan(gameObjectChar);
				GameObjectChar.send(new MSG_NEIDAN_BREAK_TASK_SUCC(), null);
				GameUtil.sendMeTips("恭喜你完成一个阶层的突破");
			}
		}
	}

	@Override
	public int cmd() {
		return 0xB183;
	}

}
