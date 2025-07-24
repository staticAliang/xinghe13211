package com.fengshen.server.process.user;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.config.ForcePkConfig;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameShiDao;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.CompeteTournamentUtils;
import com.fengshen.server.util.GameConfig;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * 发起PK
 * 
 *
 */
@Service
@Slf4j
public class CMD_KILL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int victim_id = GameReadTool.readInt(buff);
		int flag = GameReadTool.readShort(buff);
		String gid = GameReadTool.readString(buff);
		log.info("发起切磋，victim_id={}，flg={}",victim_id,flag);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara; // 切磋发起者
		GameObjectChar toKillGameObject = GameObjectCharMng.getGameObjectCharByUUid(gid);
		if (toKillGameObject == null) {
			return;
		}
		Chara chara2 = toKillGameObject.chara;
		if (toKillGameObject.characters.getXiaozi() == 1) {
			String msg = GameCommonUtil.xiao_info[ThreadLocalRandom.current().nextInt(GameCommonUtil.xiao_info.length)];
			// 问道小子不允许pk
			if (ThreadLocalRandom.current().nextBoolean()) {
				GameUtil.sendMeTips(msg);
			}
			return;
		}
		if(chara.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("正在坐牢，无法发起PK");
			return;
		}
		if(chara2.taskMap.get("坐牢") != null) {
			GameUtil.sendMeTips("对方正在坐牢，无法发起PK");
			return;
		}
		//表示不在自己视线范围
		if(Math.abs(chara.x-chara2.x)>50 || Math.abs(chara.y-chara2.y) > 25) {
			GameUtil.sendMeTips("你和对方距离太远");
			return;
		}
		if (flag == 1) {
			// 没有领取杀气腾腾任务
			ForcePkConfig config = GameConfig.forcePkConfig;
			if (!StringUtils.isNullOrEmpty(config.getPkMoney())) {
				// 解析参数
				String pkInfo = config.getPkMoney();
				String[] pkInfoArr = pkInfo.split(":");
				if (pkInfoArr.length > 1) {
					String type = pkInfoArr[0];
					if (chara.taskMap.get("杀气腾腾") == null) {
						GameUtil.confirm(chara,
								"你尚未领取#R杀气腾腾#n任务，需要花费#O" + Integer.valueOf(pkInfoArr[1]) + "#n" + type
										+ "才能PK，你确认要#OPK#n玩家#Y" + chara2.name + "#n吗?",
								"notGetTaskForcePkChara_" + chara2.uuid);
					} else {
						GameUtil.confirm(chara, "是否确定对#Y" + chara2.name + "#n发起强制PK", "forcePkChara_" + chara2.uuid);
					}
				}
			} else {
				GameUtil.confirm(chara, "你尚未领取#R杀气腾腾#n任务，你确认要#OPK#n玩家#Y" + chara2.name + "#n吗?",
						"notGetTaskForcePkChara_" + chara2.uuid);
			}
			return;
		}
		// 不在试道场内，才会做这个判断
		if (chara.mapid != 38004) {
			// 如果自己是GM并且对方关闭了PK
			if (gameObjectChar.privilege != 0 && chara2.getSettings().get("refuse_fight") != null
					&& chara2.getSettings().get("refuse_fight") == 1) {
				GameUtil.confirm(chara, "对方已关闭了PK，GM大大是否要对#Y" + chara2.name + "#n进行强制切磋", "forcePkGm_" + chara2.uuid);
				return;
			}
			// gm是否关闭pk
			if (GameConfig.config.getPkLock() != 0) {
				GameCommonUtil.dialogOk("gm关闭了pk");
				return;
			}
			// 自己是否关闭pk
			if (chara.getSettings().get("refuse_fight") != null && chara.getSettings().get("refuse_fight") == 1) {
				GameCommonUtil.dialogOk("你的切磋开关没有打开。");
				return;
			} else if (chara2.getSettings().get("refuse_fight") != null
					&& chara2.getSettings().get("refuse_fight") == 1) {
				GameCommonUtil.dialogOk("对方关闭了pk");
				return;
			}
		}

		// 如果用户在试道战场
		if (chara.mapid == 38004) {
			// 在试道场内不能对GM发起PK
			if (GameShiDao.statzhuangtai == 2) {
				GameCommonUtil.dialogOk("当前阶段不支持队伍切磋、请击杀元魔获取积分");
				return;
			} else if (toKillGameObject.privilege == 1000
					&& "enterGmAuthShiDao".equals(toKillGameObject.useGmAuth)) {
				GameUtil.confirm(chara2, "GM大大,#Y" + chara.name + "#n对你发起了PK,接受或者拒绝。", "PKGM_" + chara.uuid);
				return;
			} else if (GameConfig.config.getShidao().getIsCloseActivePk() == 0) {
				GameUtil.sendMeTips("请耐心等待系统分配.");
				return;
			}
		}
		// 如果切磋发起者和被挑战者不在同一地图
		if (chara.mapid != chara2.mapid) {
			GameCommonUtil.dialogOk("不在同一地图，无法切磋！");
			return;
		}
		// 被打的人
		if (chara2 != null&& chara2.isFight() && FightManager.getFightContainer(chara2.id) != null) {
			GameCommonUtil.dialogOk("对方正忙");
			return;
		}
		//被打的人如果在观战
		if(toKillGameObject.isLook ==1) {
			GameCommonUtil.dialogOk("对方正忙");
			return;
		}
		//如果满足条件
		if(chara.mapid == 5000 && chara2.mapid == 5000 && GameCore.ctConfig != null) {
			//如果两人都在擂台
			if(CompeteTournamentUtils.onLeitai(chara.x, chara.y) && 
					CompeteTournamentUtils.onLeitai(chara2.x, chara2.y)) {
				//设置标识
				toKillGameObject.action = "ctPk";
				gameObjectChar.action = "ctPk";
			}
		}
		// 如果切磋对象和自己在一个队伍里
		if (toKillGameObject.gameTeam != null
				&& gameObjectChar.gameTeam != null
				&&toKillGameObject.gameTeam.duiwu != null
				&& gameObjectChar.gameTeam.duiwu != null
				&& toKillGameObject.gameTeam.duiwu.size() > 0
				&& gameObjectChar.gameTeam.duiwu.size() > 0
				&& (toKillGameObject.gameTeam.duiwu.get(0).id == gameObjectChar.gameTeam.duiwu.get(0).id)) {
			GameCommonUtil.dialogOk("你不能和自己的队员切磋！");
			return;
		}
		// 移除当前这个人的状态
		GameCommonUtil.dialogOk("你已进入切磋战斗中！");
		FightManager.goFight(chara, chara2);
	}

	@Override
	public int cmd() {
		return 4114;
	}
}