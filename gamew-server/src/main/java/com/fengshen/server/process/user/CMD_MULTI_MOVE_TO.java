package com.fengshen.server.process.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_16431_0;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.write.M16431_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightMove;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 多人移动到某个坐标--队长
 */
@Service
@Slf4j
public class CMD_MULTI_MOVE_TO implements GameHandler {

	public static ConcurrentHashMap<String, Long> timeOutChara = new ConcurrentHashMap<String, Long>();

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff); // 角色ID
		int map_id = GameReadTool.readInt(buff);
		GameReadTool.readInt(buff);// map_index
		int count = GameReadTool.readShort(buff); // 这里应该算的是走几步
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(id);
		if (gameObjectChar == null) {
			return;
		}
		if(GameCommonUtil.addSpeedHandler(gameObjectChar)) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		int x = 0;
		int y = 0;
		// 以最后一步为准
		for (int i = 0; i < count; ++i) {
			x = GameReadTool.readShort(buff);
			y = GameReadTool.readShort(buff);
		}
		log.info("当前地图id为:{},角色:{},坐标为:{}:{}", map_id, chara.name, x, y);
		int dir = GameReadTool.readShort(buff);
		GameReadTool.readInt(buff);//send_time
		chara.x = x;
		chara.y = y;
		chara.dir = dir;
		Vo_16431_0 vo_16431_0 = new Vo_16431_0();
		vo_16431_0.id = id;
		vo_16431_0.x = x;
		vo_16431_0.y = y;
		vo_16431_0.dir = dir;
		if(gameObjectChar.flyType>2) {
			if (GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam, chara)) {
	            for (int i = 0; i < GameObjectChar.getGameObjectChar().gameTeam.duiwu.size(); i++) {
	            	Chara teamChara = GameObjectChar.getGameObjectChar().gameTeam.duiwu.get(i);
            		teamChara.x = x;
            		teamChara.y = y;
            		vo_16431_0.id = teamChara.id;
            		GameObjectChar teamGameObjectChar = GameObjectCharMng.getGameObjectChar(teamChara.id);
            		teamGameObjectChar.gameMap.send(new M16431_0(), vo_16431_0);
	            }
	        }else {
	        	gameObjectChar.gameMap.send(new M16431_0(), vo_16431_0);
	        }
		}else {
			gameObjectChar.gameMap.send(new M16431_0(), vo_16431_0);
		}
		
		//超过视线外隐藏该人
//		for(GameObjectChar all:GameObjectCharMng.getAll()) {
//			if(all == gameObjectChar) {
//				continue;
//			}
//			Vo_APPEAR hideAppear = GameUtil.a65529(all.chara);
//			//不在自己视线范围隐藏
//			if(Math.abs(chara.x-all.chara.x)>50 || Math.abs(chara.y-all.chara.y) > 25) {
//				hideAppear.isHide = 1;
//			}else {
//				hideAppear.isHide = 0;
//			}
//			GameObjectChar.send(new M65529_0(), hideAppear);
//		}
		
		

		// 在帮派总坛的时候
		if (GamePartyUtil.isPartyMap(chara)) {
			// 判断是否有帮派任务
			Vo_61553_0 task = chara.getTaskMap().get("帮派任务");
			if (task != null) {
				String ext = task.task_extra_para;
				if (!ext.equals("finish") && ext.indexOf("fight") != -1) {
					if (!chara.isFight) {
						// 消灭怪物
						int n = new Random().nextInt(100);
						if (n < 30) {
							List<String> fightObj = new ArrayList<>();
							for (int i = 0; i < 6; i++) {
								if (new Random().nextBoolean()) {
									fightObj.add("花纹蛇");
								} else {
									fightObj.add("灵睛鼠");
								}
							}
							FightManager.activeBoosGoFight(chara, fightObj, true);
						}
					}
				}
			}
			return;
		}

		// 如果角色走到了挖藏宝图的位置
		if (chara.getTaskMap().get("超级宝藏") != null) {
			if (GameCommonUtil.treasureMapTask(gameObjectChar, "超级宝藏", y)) {
				gameObjectChar.setGatherType("chaoji_goon");
				return;
			}
		}
		if (chara.getTaskMap().get("特级宝藏") != null) {
			if (GameCommonUtil.treasureMapTask(gameObjectChar, "特级宝藏", y)) {
				gameObjectChar.setGatherType("teji_goon");
				return;
			}
		}

		Vo_61553_0 zhuxian1 = chara.taskMap.get("主线—浮生若梦");
		if (zhuxian1 != null) {
			// 离开揽仙镇
			if (zhuxian1 != null && "主线—浮生若梦_s12".equals(zhuxian1.currentTask) && chara.mapid == 4000) {
				return;
			}
			// 到卧龙追查线索
			if (zhuxian1 != null
					&& ("主线—浮生若梦_s14".equals(zhuxian1.currentTask) || ("主线—浮生若梦_s15".equals(zhuxian1.currentTask)))
					&& chara.mapid == 3000) {
				return;
			}
			// 继续赶路
			if (zhuxian1 != null && "主线—浮生若梦_s13".equals(zhuxian1.currentTask) && chara.mapid == 4000) {
				if (chara.x == 20 && chara.y == 12) {
					zhuxian1.task_state = "1";
					zhuxian1.task_extra_para = "0";
					chara.current_task = "主线—浮生若梦_s13";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(295));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				return;
			}
			if (zhuxian1 != null && "主线—浮生若梦_s16".equals(zhuxian1.currentTask) && chara.mapid == 3000) {
				if (ThreadLocalRandom.current().nextInt(100) > 60) {
					zhuxian1.task_extra_para = "0";
					zhuxian1.task_state = "1";
					chara.current_task = "主线—浮生若梦_s16";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(310));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				return;
			}
			if (zhuxian1 != null && "主线—浮生若梦_s17".equals(zhuxian1.currentTask) && chara.mapid == 4000) {
				if (chara.x == 27 && chara.y == 33) {
					zhuxian1.task_state = "1";
					chara.current_task = "主线—浮生若梦_s17";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(318));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				return;
			}
			if (zhuxian1 != null && "主线—浮生若梦_s18".equals(zhuxian1.currentTask) && chara.mapid == 4000) {
				if (ThreadLocalRandom.current().nextInt(100) > 50) {
					zhuxian1.task_state = "1";
					chara.current_task = "主线—浮生若梦_s18";
					zhuxian1.task_extra_para = "0";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(321));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				return;
			}
			// 赶往天墉城
			if (zhuxian1 != null && "主线—浮生若梦_s19".equals(zhuxian1.currentTask) && chara.mapid == 5000 && chara.x == 77
					&& chara.y == 51) {
				zhuxian1.task_state = "1";
				chara.current_task = "主线—浮生若梦_s19";
				Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
						GameData.that.baseNpcDialogueService.findById(327));
				GameObjectChar.send(new M45056_0(), vo_45056_2);
				return;
			}
		}
		// 师门
		Vo_61553_0 shimen = chara.taskMap.get("主线—拜入师门");
		if (shimen != null) {
			if ("主线—拜入师门s15".equals(chara.taskMap.get("主线—拜入师门").currentTask) && chara.mapid == 24000) {
				return;
			}
			if ("主线—拜入师门s22".equals(shimen.currentTask) || "主线—拜入师门s23".equals(shimen.currentTask)) {
				if (chara.mapid == 6000) {
					return;
				}
			}
			if (shimen.currentTask.equals("主线—拜入师门s5") && chara.mapid == 5000 && chara.x == 115 && chara.y == 79) {
				chara.current_task = "主线—拜入师门s5";
				shimen.task_state = "1";
				Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
						GameData.that.baseNpcDialogueService.findById(368));
				GameObjectChar.send(new M45056_0(), vo_45056_2);
				return;
			} else if ("主线—拜入师门s21".equals(shimen.currentTask) && chara.mapid == 6000 && chara.x == 54
					&& chara.y == 43) {
				shimen.task_state = "1";
				chara.current_task = "主线—拜入师门s21";
				Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那妖怪便在桃林深处，道长可前去查探。", "主线—拜入师门", 6106, "桃精");
				GameObjectChar.send(new M45056_0(), vo_45056_2);
				return;
			}
		}

		Vo_61553_0 shanyu = chara.taskMap.get("主线—山雨欲来");
		if (shanyu != null) {
			if (("主线—山雨欲来s14".equals(shanyu.currentTask) || "主线—山雨欲来s15".equals(shanyu.currentTask)
					|| "主线—山雨欲来s16".equals(shanyu.currentTask) || "主线—山雨欲来s17".equals(shanyu.currentTask)
					|| "主线—山雨欲来s18".equals(shanyu.currentTask))) {
				if ("北海沙滩".equals(chara.mapName)) {
					return;
				}
			}
			if (("主线—山雨欲来s7".equals(shanyu.currentTask) || "主线—山雨欲来s6".equals(shanyu.currentTask)
					|| "主线—山雨欲来s8".equals(shanyu.currentTask))) {
				if ("轩辕庙".equals(chara.mapName)) {
					return;
				}
			}
			if (shanyu != null && ("主线—山雨欲来s19".equals(shanyu.currentTask)) && "北海沙滩".equals(chara.mapName)) {
				if (chara.x == 10 && chara.y == 36) {
					chara.current_task = "主线—山雨欲来s19";
					shanyu.task_state = "1";
					shanyu.task_extra_para = "0";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "情况如何，那人可曾信了你？", "主线—山雨欲来", 6175, "雉鸡精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				return;
			}
		}

		// 驱魔香为0表示关闭，为1表示打开
		if (chara.qumoxiang != 1 && FightMove.move(chara.id) && !chara.isFight) {
			FightManager.goFight(gameObjectChar.chara, gameObjectChar.chara.mapName);
			return;
		}
	}

	@Override
	public int cmd() {
		return 61634;
	}
}