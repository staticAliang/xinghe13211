package com.fengshen.server.process.fuling;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.fuling.Vo_SOUL_FULINGZHEN_DATA;
import com.fengshen.server.data.write.fuling.MSG_SOUL_FULINGZHEN_DATA;
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
 * a真灵升级
 * 
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SOUL_IMPROVE_ZHENLING_LEVEL implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(chara.zhenlingLevel == 0) {
			GameUtil.sendMeTips("请先激活附灵阵");
		}
		// 升级信息
		int realLevel = chara.zhenlingStage == 0 ? 0 : (chara.zhenlingStage - 1) * 10 + chara.zhenlingLevel;
		if (realLevel > GameConfig.spiritInfoConfig.size() - 1) {
			realLevel = GameConfig.spiritInfoConfig.size() - 1;
		}
		// 消耗道具数量
		int costNum = 1;
		// 最大等级
		int maxLevel = chara.zhenlingStage * 10;
		String msgType = "青龙真灵";
		int level = 0;
		if (type == 1) {
			// 青龙
			if (chara.qinglongZhenlingLevel + 1 > maxLevel) {
				GameUtil.sendMeTips("该真灵已达最高等级");
				return;
			}
			SpiritInfoConfig spiritInfoConfig = GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel + 1 > GameConfig.spiritInfoConfig.size() - 1
					? GameConfig.spiritInfoConfig.size()-1
					: chara.qinglongZhenlingLevel + 1);
			// 判断当前道具余额是否足够
			if (GameCommonUtil.getGoodsNum(chara, "真灵精粹") < spiritInfoConfig.getCost()) {
				GameUtil.sendMeTips("真灵精粹不足无法升级！");
				return;
			}
			costNum = spiritInfoConfig.getCost();
			// 等级+1
			chara.qinglongZhenlingLevel += 1;
			level = chara.qinglongZhenlingLevel;
		} else if (type == 2) {
			// 白虎
			if (chara.baihuhenlingLevel + 1 > maxLevel) {
				GameUtil.sendMeTips("该真灵已达最高等级");
				return;
			}
			SpiritInfoConfig spiritInfoConfig = GameConfig.spiritInfoConfig
					.get(chara.baihuhenlingLevel + 1 > GameConfig.spiritInfoConfig.size() - 1
							? GameConfig.spiritInfoConfig.size()-1
							: chara.baihuhenlingLevel + 1);
			// 判断当前道具余额是否足够
			if (GameCommonUtil.getGoodsNum(chara, "真灵精粹") < spiritInfoConfig.getCost()) {
				GameUtil.sendMeTips("真灵精粹不足无法升级！");
				return;
			}
			costNum = spiritInfoConfig.getCost();
			// 升级
			chara.baihuhenlingLevel += 1;
			msgType = "白虎真灵";
			level = chara.baihuhenlingLevel;
		} else if (type == 3) {
			// 朱雀
			if (chara.zhuqueZhenlingLevel + 1 > maxLevel) {
				GameUtil.sendMeTips("该真灵已达最高等级");
				return;
			}
			SpiritInfoConfig spiritInfoConfig = GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel + 1 > GameConfig.spiritInfoConfig.size() - 1
					? GameConfig.spiritInfoConfig.size()-1
					: chara.zhuqueZhenlingLevel + 1);
			// 判断当前道具余额是否足够
			if (GameCommonUtil.getGoodsNum(chara, "真灵精粹") < spiritInfoConfig.getCost()) {
				GameUtil.sendMeTips("真灵精粹不足无法升级！");
				return;
			}
			costNum = spiritInfoConfig.getCost();
			// 升级
			chara.zhuqueZhenlingLevel += 1;
			msgType = "朱雀真灵";
			level = chara.zhuqueZhenlingLevel;
		} else {
			// 玄武
			if (chara.xuanwuZhenlingLevel + 1 > maxLevel) {
				GameUtil.sendMeTips("该真灵已达最高等级");
				return;
			}
			SpiritInfoConfig spiritInfoConfig = GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel + 1 > GameConfig.spiritInfoConfig.size() - 1
					? GameConfig.spiritInfoConfig.size()-1
					: chara.xuanwuZhenlingLevel + 1);
			// 判断当前道具余额是否足够
			if (GameCommonUtil.getGoodsNum(chara, "真灵精粹") < spiritInfoConfig.getCost()) {
				GameUtil.sendMeTips("真灵精粹不足无法升级！");
				return;
			}
			costNum = spiritInfoConfig.getCost();
			// 升级
			chara.xuanwuZhenlingLevel += 1;
			msgType = "玄武真灵";
			level = chara.xuanwuZhenlingLevel;
		}
		// 扣除道具
		GameUtil.removemunber(chara, "真灵精粹", costNum);
		// 通知客户端弹出提示信息
		GameUtil.sendMeTips("成功将#Y" + msgType + "#n提升至#R" + level + "级");
		// 附灵阵信息
		gameObjectChar.sendOne(new MSG_SOUL_FULINGZHEN_DATA(), new Vo_SOUL_FULINGZHEN_DATA(chara, 1));
		GameUtil.sendUpdate(chara);
		log.info("真灵升级，type={}、最高等级={}", type,maxLevel);
	}

	@Override
	public int cmd() {
		return 0xD372;
	}

}
