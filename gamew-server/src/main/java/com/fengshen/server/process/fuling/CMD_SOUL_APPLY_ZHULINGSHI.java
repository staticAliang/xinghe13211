package com.fengshen.server.process.fuling;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.fuling.MSG_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.SpiritInfoConfig;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * a使用铸灵石
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SOUL_APPLY_ZHULINGSHI implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.zhenlingLevel == 0) {
			GameUtil.sendMeTips("请先激活附灵阵");
		}
		if(chara.zhenlingStage>=5 && chara.zhenlingLevel>=10) {
			GameUtil.sendMeTips("已达极限无法在升级");
			return;
		}
		//附灵阵信息
		int num = GameCommonUtil.getGoodsNum(chara, "铸灵石");
		if(num < 1) {
			GameUtil.sendMeTips("铸灵石不足无法升级！");
			return;
		}
		GameUtil.removemunber(chara, "铸灵石", 1);
		num-=1;
		//当前顺序等级
		int realLevel = chara.zhenlingStage == 0 ?0 :(chara.zhenlingStage - 1) * 10 + chara.zhenlingLevel;
		if(realLevel>GameConfig.spiritInfoConfig.size()-1) {
			realLevel = GameConfig.spiritInfoConfig.size()-1;
		}
		SpiritInfoConfig spiritInfoConfig = GameConfig.spiritInfoConfig.get(realLevel);
		//获取当前升级经验
		int nextMaxExp = 240;
		if(spiritInfoConfig != null) {
			nextMaxExp = spiritInfoConfig.getMaxExp();
		}
		chara.zhenlingExp+=200;
		//当前经验大于最大升级经验.则直接升级
		if(chara.zhenlingExp>=nextMaxExp) {
			chara.zhenlingLevel+=1;
			//如果当前等级大于该阶段最高级
			if(realLevel%10==0) {
				//开始下一阶段
				chara.zhenlingStage+=1;
				chara.zhenlingLevel = 1;
			}
			//恢复初始化
			chara.zhenlingExp = 0;
			//属性升级
			chara.zhenlingPhy += 11;
			chara.zhenlingMag += 6;
			chara.zhenlingSpeed += 2;
			chara.zhenlingDef += 22;
			//更新角色信息
			GameUtil.sendUpdate(chara);
		}
		//刷新
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("zhenling/level", chara.zhenlingLevel);
		gameObjectChar.sendOne(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, dataMap));
		gameObjectChar.sendOne(new MSG_SOUL_FULINGZHEN_DATA(), new Vo_SOUL_FULINGZHEN_DATA(chara,num));
		//扣除铸灵石
		log.info("使用铸灵石升级，下一级最高经验：{}, 顺序等级：{}",nextMaxExp, realLevel);
	}

	@Override
	public int cmd() {
		return 0xD370;
	}

}
