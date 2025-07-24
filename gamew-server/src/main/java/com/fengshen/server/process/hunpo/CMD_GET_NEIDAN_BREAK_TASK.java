package com.fengshen.server.process.hunpo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.write.hunpo.MSG_GET_NEIDAN_BREAK_TASK_SUCC;
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

/**
 * 请求获得内丹突破任务
 * 
 *
 */
@Service
@Slf4j
public class CMD_GET_NEIDAN_BREAK_TASK implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("请求获得内丹突破任务");
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
		if(chara.isNeiDanTop == 1) {
			GameUtil.sendMeTips("已达到极限");
			return;
		}
		//精气不足无法突破
		if(chara.chargeScore<chara.danDataExpToNextLevel) {
			GameUtil.sendMeTips("积分不足无法突破！");
			return;
		}
		if(chara.taskMap.get("内丹修炼") != null) {
			GameUtil.sendMeTips("请完成突破！");
			return;
		}
		chara.chargeScore-=chara.danDataExpToNextLevel;
			GameUtilRenWu.refshPointTask(chara);

		GameUtil.sendMeTips("你消耗了#R"+chara.danDataExpToNextLevel+"#n积分，提升了一个阶段");
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
				}
			}
		}
		
		//阶段
		chara.danDataStage+=1;
		log.info("内丹层数:{}",chara.danDataStage);
		if(chara.danDataStage == 4 && chara.danDataState == 5) {
			//完成最后的突破
			GameUtilRenWu.createTask("内丹修炼","内丹修炼","内丹修炼",chara);
			GameUtil.sendUpdate(chara);
			GameCommonUtil.refreshNeidan(gameObjectChar);
			chara.danDataStage+=1;
		}else {
			if(chara.danDataStage == 5) {
				//创建突破任务
				GameUtilRenWu.createTask("内丹修炼","内丹修炼","内丹修炼",chara);
			}
		}
		//刷新信息
		GameUtil.sendUpdate(chara);
		//刷新内丹信息
		GameCommonUtil.refreshNeidan(gameObjectChar);
		GameObjectChar.send(new MSG_GET_NEIDAN_BREAK_TASK_SUCC(), null);
	}

	@Override
	public int cmd() {
		return 0xB181;
	}

}
