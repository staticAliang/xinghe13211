package com.fengshen.server.process.common;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FightObjectInfo;
import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.Renwu;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_40965_0;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.data.write.M12016_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M40965_0;
import com.fengshen.server.data.write.M4155_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.M65529_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.MSG_DISAPPEAR;
import com.fengshen.server.data.write.achieve.MSG_ACHIEVE_FINISHED;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.user.MSG_PLAY_INSTRUCTION;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.ShouHu;
import com.fengshen.server.fight.FightManager;
import com.fengshen.server.fight.FightObject;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameMap;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GamePartyUtil;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.fengshen.server.game.GameZone;
import com.fengshen.server.process.CommonCmd;
import com.google.common.collect.Lists;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;


/**
 * 操作对话场景
 * 
 *
 */
@Service
@Slf4j
public class CMD_OPER_SCENARIOD implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		// 1直接跳过 2点击下一步
		int type = GameReadTool.readShort(buff);
		String para = GameReadTool.readString(buff);
		log.info("操作对话场景, type={}, para={}",type,para);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar == null) {
			return;
		}
		Chara chara = gameObjectChar.chara;
		if(chara == null) {
			return;
		}
		// 帮派任务
		if (GamePartyUtil.isIngParty(chara)) {
			Npc npc = GamePartyUtil.getPartyJuBenCurrNpc(id);
			if (npc != null) {
				if (type == 1) {
					chara.taskMap.get("帮派任务").task_state = "tsxlEnd";
				}
				GamePartyUtil.playTsxlScenariod(chara.taskMap.get("帮派任务").task_state, npc, chara);
				return;
			}
		}
		if (id == 1006) {
			// 帮派总管
			return;
		}
		// 副本
		GameMap gameMap = gameObjectChar.gameMap;
		if (gameMap != null && gameMap.isDugeno()) {
			GameZone gz = (GameZone) gameMap;
			if (type == 2) {
				GameUtil.playNextNpcDialogueJuBen(chara);
				return;
			} else if (type == 1) {
				// 如果剧本结束的话,关闭所有队伍中的剧本显示.
				Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
				vo_9129_0.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
				vo_9129_0.para = "DramaDlg";
				GameObjectChar.sendduiwu(new MSG_GENERAL_NOTIFY(), vo_9129_0, chara.id);
				if (chara.currentJuBens != null) {
					try {
						if(chara.currentJuBens.length>0) {
							int index = Integer.valueOf(chara.currentJuBens[chara.currentJuBens.length - 1]);
							gz.gameDugeon.OnJuBenEnd(chara, index);
						}
					} catch (Exception e) {
						log.error("{}", e);
					}
					return;
				}
			}
			gz.gameDugeon.OnJuBenEnd(chara, id);
			return;
		}
		Vo_61553_0 task = chara.taskMap.get("主线—浮生若梦");
		Vo_61553_0 shimenTask = chara.taskMap.get("主线—拜入师门");
		if (type == 2) { // 2表示任务正在进行中，而且不是最后一个任务环节
			//如梦初醒
			if ("主线—浮生若梦_s0".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(657));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(658));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(659));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(660));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if (step == 5) {
					task.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(661));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					//下一个任务
					chara.current_task = "主线—浮生若梦_s1";
					closeAndCreateTask(chara);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 333333333);
				}
				return;
			}
			//感谢多多
			if ("主线—浮生若梦_s1".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step== 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(396));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(397));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(398));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(399));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 5) {
					task.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(400));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 6) {
					GameUtil.renwujiangli(chara);
					this.geizhuangb(chara);
					chara.current_task = "主线—浮生若梦_s2";
					closeAndCreateTask(chara);
				}
				return;
			}
			// 第三个任务
			if ("主线—浮生若梦_s3".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "有劳王老板了。#B(咕噜一声,吞下丹药。)#n哇！这丹药真有用，感觉全身都是劲！",
							"主线—浮生若梦");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "在我这也甚是无趣，正好#Y黄仨儿#n回来了,你不妨去他那看看,他养了些小动物，没准公子你会感兴趣。",
							"主线—浮生若梦", 6011, "王老板");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "多谢王老板关心，我这就去看看", "主线—浮生若梦");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara,
							"我这里正好有一个血池和一个灵池,使用后可在战斗结束时自动补充气血和法力，也一并给你吧。日后世间行走,还要多加小心才是。", "主线—浮生若梦", 6011, "王老板");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				else if (step == 5) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s4";
					closeAndCreateTask(chara);
				}
				return;
			}
			if ("主线—浮生若梦_s4".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(403));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				//这里开始领取宠物
				else if (step == 2) {
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					if(renwu != null && !StringUtils.isNullOrEmpty(renwu.getReward())) {
						String reward = renwu.getReward();
						String[] split = reward.split("\\#");
						//打开界面领取
						Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
						vo_9129_2.notify = ClientButtonIdConst.NOTICE_FETCH_BONUS;
						vo_9129_2.para = "#I1|"+split[0]+"("+split[1]+")$1#I";
						GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2);
					}
					task.task_state = "3";
					GameObjectChar.send(new M4155_0(), 0);
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
				}
				return;
			}
			if ("主线—浮生若梦_s5".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(406));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				//和宠物一起战斗
				 else if (step == 2) {
					if(!chara.isFight) {
						gameObjectChar.chara.zhandouId = 8888888;
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("新手兔子"), false);
						gameObjectChar.sendOne(new MSG_PLAY_INSTRUCTION(), 17);
						gameObjectChar.flag = "newCombatFightS4";
						GameObjectChar.send(new M4155_0(), 0);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
					}
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(409));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					GameObjectChar.send(new M4155_0(), 0);
				}
				else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(410));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					GameObjectChar.send(new M4155_0(), 0);
				}else if(step == 5) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s6";
					closeAndCreateTask(chara);
				}
				return;
			}
			//莲花姑娘
			if ("主线—浮生若梦_s6".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "另外，还要多派遣它战斗，这样你们才更有默契，心有灵犀。", "主线—浮生若梦", 6019,
							"莲花姑娘");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我明白了莲花姑娘，真是帮了大忙了！", "主线—浮生若梦");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#Y赵老板#n好像遇到了麻烦，可我有事脱不了身，如果公子能帮到他，那就太好", "主线—浮生若梦",
							6019, "莲花姑娘");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 4) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s7";
					closeAndCreateTask(chara);
				}
				return;
			}
			//赵老板
			if ("主线—浮生若梦_s7".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(418));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(419));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(420));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 4) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s8";
					closeAndCreateTask(chara);
				}
				return;
			}
			//揽仙镇外寻找玉佩
			if ("主线—浮生若梦_s8".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if(step == 1) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s9";
					closeAndCreateTask(chara);
				}
				return;
			}
			//把玉佩还给张老板
			if ("主线—浮生若梦_s9".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(425));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(426));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s10";
					closeAndCreateTask(chara);
				}
				return;
			}
			//玉佩-告知赵老板
			if ("主线—浮生若梦_s10".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(663));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s11";
					closeAndCreateTask(chara);
				}
				return;
			}
			//莲花姑娘找我
			if ("主线—浮生若梦_s11".equals(chara.current_task) && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道长真是有心了，奈何我拜师无门，又瞧不得那些旁门左道， 不知何处去。", "主线—浮生若梦");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					String[] polar = { "五龙山云霄洞", "终南山玉柱洞", "凤凰山斗阙宫", "乾元山金光洞", "骷髅山白骨洞" };
					Vo_45056_0 vo_45056_3 = GameUtil.a45056(chara,
							"道长说了,最近五大门派正广招弟子，若是能拜入#R" + polar[chara.polar - 1] + "#n下就再好不过了，修行定会事半功倍。", "主线—浮生若梦",
							6019, "莲花姑娘");
					GameObjectChar.send(new M45056_0(), vo_45056_3);
				}
				else if (step == 3) {
					task.task_state = "4";
					String[] polar = { "五龙山云霄洞", "终南山玉柱洞", "凤凰山斗阙宫", "乾元山金光洞", "骷髅山白骨洞" };
					Vo_45056_0 vo_45056_3 = GameUtil.a45056(chara,
							"我也听说#R" + polar[chara.polar - 1] + "#n道法了得,此去定不负道长期望。", "主线—浮生若梦");
					GameObjectChar.send(new M45056_0(), vo_45056_3);
				}
				else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(383));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				
				else if (step == 5) {
					task.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(384));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 6) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s12";
					closeAndCreateTask(chara);
				}
				return;
			}
			//官道南找强盗
			if ("主线—浮生若梦_s12".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(289));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(290));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					if(!chara.isFight) {
						List<String> list = new ArrayList<>();
						list.add("新手强盗");
						list.add("新手强盗");
						FightManager.activeBoosGoFight(chara, list, false);
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}
				else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(293));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 5) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s13";
					closeAndCreateTask(chara);
					//删除npc
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 55555555);
				}
				return;
			}
			//继续赶路
			if ("主线—浮生若梦_s13".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(296));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(297));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				//去卧龙坡找强盗
				else if (step == 3) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s14";
					closeAndCreateTask(chara);
				}
				return;
			}
			//卧龙追查线索
			if ("主线—浮生若梦_s14".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(300));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(301));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(302));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 4) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s15";
					closeAndCreateTask(chara);
				}
				return;
			}
			//找神龙真人
			if ("主线—浮生若梦_s15".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if (step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(305));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(306));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(307));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(308));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if (step == 5) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s16";
					closeAndCreateTask(chara);
				}
				return;
			}
			//卧龙坡追击强盗老巢
			if ("主线—浮生若梦_s16".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				//播放剧情
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(311));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(312));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(313));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 4) {
					if(!chara.isFight) {
						//开始打架
						List<String> list = new ArrayList<>();
						list.add("新手强盗");
						list.add("新手强盗");
						FightManager.activeBoosGoFight(chara, list, false);
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}
				else if(step == 5) {
					task.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(316));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 6) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s17";
					closeAndCreateTask(chara);
				}
				return;
			}
			
			if ("主线—浮生若梦_s17".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara, GameData.that.baseNpcDialogueService.findById(319));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s18";
					closeAndCreateTask(chara);
				}
				return;
			}
			if ("主线—浮生若梦_s18".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(322));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(323));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(324));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(325));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s19";
					closeAndCreateTask(chara);
				}
				return;
			}
			if ("主线—浮生若梦_s19".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(328));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s20";
					closeAndCreateTask(chara);
				}
				return;
			}
			if ("主线—浮生若梦_s20".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(331));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(332));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(333));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(334));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 5) {
					task.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(335));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 6) {
					task.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(336));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				else if(step == 7) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s21";
					closeAndCreateTask(chara);
				}
				return;
			}
			
			//师门拜师
			if ("主线—浮生若梦_s21".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(339));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s22";
					closeAndCreateTask(chara);
					//打开领取礼包
					GameUtil.openDlg("RookieGiftDlg");
					//成就
					GameObjectChar.send(new MSG_ACHIEVE_FINISHED(), new Object[] {501000,"浮生若梦"});
				}
				return;
			}
			//学习道法
			if ("主线—浮生若梦_s22".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				int step = Integer.valueOf(task.task_state);
				int icon = GameCommonUtil.shimen_tongzi_icon[chara.polar-1];
				if(step == 1) {
					task.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你运气不错，正逢我门广收门徒。不过这收与不收，还需看你资质如何。我且问你，何为道心？",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					task.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(343));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					task.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不过夸夸奇谈，市井小民皆会这一套，我再问你，大道为何，虚实为何，本心又为何。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					task.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(345));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					task.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "好，确有几分见解。我观你根骨尚佳，乃可塑之才，从今往后你就是我门三代弟子了。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					task.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既入我山门，自然也该习我道法，你先下去准备准备，一切妥当再来找我。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					//下一个任务
					GameUtilRenWu.removeTask("主线—浮生若梦", chara);
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s1";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt("向#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|E=【主线】我已准备妥当#P学习道法");
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//获得称号
					String[] chenghao = { "五龙山云霄洞第三代弟子", "终南山玉柱洞第三代弟子", "凤凰山斗阙宫第三代弟子", "乾元山金光洞第三代弟子",
					"骷髅山白骨洞第三代弟子" };
					String chenhao = chenghao[chara.polar - 1];
					GameUtil.chenghaoxiaoxi(chara, "拜师任务", chenhao);
					Vo_20481_0 vo_20481_3 = new Vo_20481_0();
					vo_20481_3.msg = "你获得了#R" + chenhao + "#n的称谓。";
					vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_3);
					//装备称谓
					chara.chenhao = chenhao;
					GameObjectChar.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(chara));
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			//第二个剧情
			if ("主线—拜入师门s1".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				int icon = GameCommonUtil.shimen_tongzi_icon[chara.polar-1];
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(350));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不同的战斗偏向会带来效果自然不同。#R物理攻击#n如猛虎下山，重力重势。#R法术攻击#n如蛟龙布雨，重意重形。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "当然，两种攻击手段各有千秋却殊途同归，皆是破敌制胜的手段。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					//打开力法选择
					GameUtil.openDlg("ChoseAtkDlg");
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			if ("主线—拜入师门s2".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1 || step == 2) {
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else if(step == 3) {
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					//创建任务
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("主线—拜入师门s2_1");
					String[] skill = new String[] {"金光乍现","摘叶飞花","滴水穿石","举火焚天","落土飞岩"};
					renwu.setTaskPrompt("找#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|M=【主线】学习道法#P将#R力破千钧#n或#R"+skill[chara.polar-1]+"#n提升至16级");
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				}
				return;
			}
			//复命
			if ("主线—拜入师门s3".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				int icon = GameCommonUtil.shimen_tongzi_icon[chara.polar-1];
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "除了技能等级以外#R属性点#n与#R相性点#n同样会左右技能伤害，你目前道行尚浅，贪多嚼不烂，待你日后道法精进，自会明白的。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子明白了。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "接下来你在战斗中试试本门道法的威力吧。",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					if(!chara.isFight) {
						//进入战斗
						List<String> monsterList = Lists.newArrayList("新手木桩","新手木桩");
						FightManager.activeBoosGoFight(chara, monsterList, false);
						if("phyPower".equals(shimenTask.flag)) {
							//选择了物攻
							GameObjectChar.send(new MSG_PLAY_INSTRUCTION(), 43);
						}else if("magPower".equals(shimenTask.flag)) {
							//选择了法攻
							GameObjectChar.send(new MSG_PLAY_INSTRUCTION(), 42);
						}
						//关闭对话框
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}
				return;
			}
			if("主线—拜入师门s4".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				int icon = GameCommonUtil.shimen_tongzi_icon[chara.polar-1];
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "山中发现一走失的孩子，现命你将其送回家去，一路上你且好生照顾，莫要损了门派的声誉！",
							"主线—拜入师门", icon, GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子定不辱使命！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(364));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(365));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(366));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s5";
					closeAndCreateTaskShiMen(chara);
				}
				return;
			}
			//去城里玩耍
			if("主线—拜入师门s5".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(369));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(370));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(371));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s6";
					closeAndCreateTaskShiMen(chara);
				}
				return;
			}
			//买包子
			if("主线—拜入师门s6".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(374));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(375));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(376));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.getPlayScenariod(chara,
							GameData.that.baseNpcDialogueService.findById(377));
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5){
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s7";
					closeAndCreateTaskShiMen(chara);
					//走失的孩子
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 5000;
					npc.id = 55555555;
					npc.x = 105;
					npc.y = 136;
					npc.icon = 6018;
					npc.type = 2;
					npc.org_icon = 6018;
					npc.portrait = 6018;
					npc.name = "走失的孩子";
					npc.dir = 3;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			//河边
			if("主线—拜入师门s7".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我的小祖宗哎，别跑这么快啊，小心掉河里了。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "咦，那边的桥上有个人穿的好生有趣。走，去看看！#B（说完那孩子就跑过去了。）",
							"主线—拜入师门", 6018, "走失的孩子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "喂喂喂........都说了别跑那么快了........#B（他要是有个闪失，我回去可不好交代啊。）",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s8";
					closeAndCreateTaskShiMen(chara);
					//消失
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 55555555);
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 5000;
					npc.id = 66666666;
					npc.x = 66;
					npc.y = 134;
					npc.icon = 6213;
					npc.type = 2;
					npc.org_icon = 6213;
					npc.portrait = 6213;
					npc.name = "神秘蒙面人";
					npc.dir = 3;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			if("主线—拜入师门s8".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "坏人你敢打我！呜呜呜，我要咬死你！",
							"主线—拜入师门", 6018, "走失的孩子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "嗷呜！你属狗的啊！老实点，不然要你的小命！",
							"主线—拜入师门", 6213, "神秘蒙面人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "竟欺负一个无辜的孩子！看打！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					if(!chara.isFight) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else {
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("新手蒙面"), false);
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不陪你玩了！这小娃儿我就带走了！想要找回这孩子，就乖乖等消息吧！",
							"主线—拜入师门", 6213, "神秘蒙面人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shimenTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "糟了，让他给溜了。此事非同一般，得赶紧回禀掌门！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s9";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					//npc消失
					GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}
			
			if("主线—拜入师门s9".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				String shimen_shizun = GameCommonUtil.shimen_shizun[chara.polar-1];
				int shimen_shizun_icon = GameCommonUtil.shimen_shizun_icon[chara.polar-1];
				String[] types = new String[] {"金光洞:火光","云霄洞:金光","玉柱洞:木光","斗阙宫:水光","白骨洞:土光"};
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "什么？！岂有此理！你可看清那人有何特征？",
							"主线—拜入师门", shimen_shizun_icon, shimen_shizun);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "并不能看清面容，但他使用遁术逃走时，脚下有#R"+types[chara.polar-1].split(":")[1]+"#n闪现。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "看来是#R"+types[chara.polar-1].split(":")[0]+"#n的人。应该是前段时日门派邀斗输了，就暗地找我派弟子麻烦！损我门派清誉！",
							"主线—拜入师门", shimen_shizun_icon, shimen_shizun);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我这就去#R"+types[chara.polar-1].split(":")[0]+"#n打探打探，定将那孩子完好无损的救回来！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					String[] zhanglao = new String[] {"金光长老","云霄长老","玉柱长老","斗阙长老","白骨长老"};
					String[] polar = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s10";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), polar[chara.polar-1],zhanglao[chara.polar-1]));
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			if("主线—拜入师门s10".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				int[] zhanglao_icon = new int[] {20036,20033,20034,20035,20037};
				String[] zhanglao = new String[] {"金光长老","云霄长老","玉柱长老","斗阙长老","白骨长老"};
				String[] dongfu = { "云霄洞", "玉柱洞", "斗阙宫", "金光洞", "白骨洞" };
				String[] skill = { "火遁术", "金遁术", "木遁术", "水遁术", "土遁术" };
				String[] tongzi = {"赤霞童子","云霄童子", "碧玉童子", "水灵童子", "彩云童子"};
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "何人胆敢擅闯我门派领地？",
							"主线—拜入师门", zhanglao_icon[chara.polar-1], zhanglao[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我乃"+dongfu[chara.polar-1]+"门下弟子，早先护送一名走失的孩子回家，不料却被人从天墉城河边掳走，我怀疑是贵派弟子所谓。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哪里来的狂妄小儿，我派弟子岂会做出此等卑鄙之事！",
							"主线—拜入师门", zhanglao_icon[chara.polar-1], zhanglao[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那人使出了你们门派的#R"+skill[chara.polar-1]+"#n，你们休想抵赖！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "竟有此事！#Y"+tongzi[chara.polar-1]+"#n负责天墉城一带事务，想必他能给你一个交代。",
							"主线—拜入师门", zhanglao_icon[chara.polar-1], zhanglao[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s11";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), tongzi[chara.polar-1]));
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			
			if("主线—拜入师门s11".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				int[] tongzi_icon = new int[] {6023,6020,6021,6022,6024};
				int[] att_icon = new int[] {6004,6001,6003,6004,6005};
				String[] tongzi = {"赤霞童子","云霄童子", "碧玉童子", "水灵童子", "彩云童子"};
				String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我派确有弟子数以万计，每日皆有弟子前往天墉城修行历练，道友气势汹汹而来，不知是何缘故？",
							"主线—拜入师门", tongzi_icon[chara.polar-1], tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哈！还敢装傻！果然就是你们绑架了那个孩子！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "还请道友慎言！我以门派名誉担保，绝无此事！",
							"主线—拜入师门", tongzi_icon[chara.polar-1], tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "是我干的！那小娃儿在我手上，怎么样！！",
							"主线—拜入师门", att_icon[chara.polar-1], att_name[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "什么？！孽障！竟敢败坏门派威名！",
							"主线—拜入师门", tongzi_icon[chara.polar-1], tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shimenTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "是他们欺人太甚！前些日子门派邀斗，我一时不敌，他们竟下狠手，打断了我一根肋骨！",
							"主线—拜入师门", att_icon[chara.polar-1], att_name[chara.polar-1]+"门外弟子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					shimenTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "一人做事一人当，此事和门派没有任何干系，想要回这娃儿也行，叫伤我之人到#R官道北#n等着，我定要出了这口恶气！",
							"主线—拜入师门", att_icon[chara.polar-1], att_name[chara.polar-1]+"门外弟子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8) {
					shimenTask.task_state = "9";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（虽听说"+att_name[chara.polar-1]+"一向公正，但难免会有人护短，不如先答应下来，那孩子在这里定然不会有危险。）#n好！一言为定！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 9) {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s12";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			if("主线—拜入师门s12".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step ==1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "真是荒唐！那弟子因恶意伤人已被师尊训斥并安排外出苦修了，如何与他再战。先救人才是当务之急，此事就交给你了。",
							"主线—拜入师门", GameCommonUtil.shimen_tongzi_icon[chara.polar-1], GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你一人前去太过冒险，为保证万无一失，你先去找#Y"+GameCommonUtil.shimen_zhanglao[chara.polar-1]+"#n学习召唤守护之书，壮大队伍实力后再去。",
							"主线—拜入师门", GameCommonUtil.shimen_tongzi_icon[chara.polar-1], GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s13";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_zhanglao[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			//守护召唤
			if("主线—拜入师门s13".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "此术可为你召唤守护，当队伍人数不足时，助阵杀敌，不在话下。你且试试。",
							"主线—拜入师门", GameCommonUtil.shimen_zhanglao_icon[chara.polar-1], GameCommonUtil.shimen_zhanglao[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					//得到守护
					Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
					vo_9129_0.notify = 20002;
					vo_9129_0.para = "FFFFFFFFFF0FFF0FFFFFF0F0";
					GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
					//图标
					gameObjectChar.sendOne(new MSG_PLAY_INSTRUCTION(), 9);
					//如果存在守护的话就直接下一个任务
					if(chara.listshouhu != null && !chara.listshouhu.isEmpty()) {
						ShouHu shouHu = chara.listshouhu.get(0);
						shouHu.listShouHuShuXing.get(0).salary = chara.canzhanshouhunumber;
						++chara.canzhanshouhunumber;
						shouHu.listShouHuShuXing.get(0).nil = 1;
						GameObjectChar.send(new M12016_0(), Lists.newArrayList(shouHu));
						//设置下一个任务
						GameUtil.renwujiangli(chara);
						chara.current_task = "主线—拜入师门s14";
						Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
						renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_zhanglao[chara.polar-1]));
						closeAndCreateTaskShiMen(chara, renwu);
					}
				}
				return;
			}
			if("主线—拜入师门s14".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你我同门同宗，相互扶持，本是分内之事，况事关我派声誉，自当全力协助，无需客气。",
							"主线—拜入师门", GameCommonUtil.shimen_zhanglao_icon[chara.polar-1], GameCommonUtil.shimen_zhanglao[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
					//应约挑战
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s15";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), att_name[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s15".equals(chara.current_task)  && shimenTask != null 
					&& Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "懒得与你废话，大家一起上！揍他！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					//进入战斗
					if(!chara.isFight) {
						FightObjectInfo fight = GameData.that.baseFightObjectService.
								findOneByName("新手外门弟子");
						int[] att_icon = new int[] {6004,6001,7002,7003,7005};
						int[] wea_icon = new int[] {1102,1135,1146,1124,1113};
						String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
						FightObject fightObject = new FightObject(fight, false, 4);
						fightObject.setStr(att_name[chara.polar-1]+"外门弟子");
						fightObject.setOrg_icon(att_icon[chara.polar-1]);
						fightObject.setWeapon_icon(wea_icon[chara.polar-1]);
						fightObject.setGuaiwulevel(20);
						fightObject.setFid(88888888);
						List<FightObject> fightObjects = new ArrayList<>();
						fightObjects.add(fightObject);
						fightObject = new FightObject(fight, false, 4);
						fightObject.setStr(att_name[chara.polar-1]+"外门弟子");
						fightObject.setOrg_icon(att_icon[chara.polar-1]);
						fightObject.setWeapon_icon(wea_icon[chara.polar-1]);
						fightObject.setGuaiwulevel(13);
						fightObject.setFid(99999999);
						fightObjects.add(fightObject);
						FightManager.activeBoosGoFight(chara, fightObjects, true);
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小朋友，你受惊了，这就送你回家。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					//任务奖励
					GameUtil.renwujiangli(chara);
					//弹出守护召唤
					Map<String,Object> obj = new LinkedHashMap<String, Object>();
					obj.put("id:int", 6002);
					GameObjectChar.send(new CommonCmd(0xA0A4), obj);
					chara.current_task = "主线—拜入师门s16";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s16".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我自有安排，此人已被"+att_name[chara.polar-1]+"除名，这孩子看起来也并未受伤，此事也就此了结吧。",
							"主线—拜入师门",GameCommonUtil.shimen_shizun_icon[chara.polar-1],GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2){
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你初入门派，让你担此重任确也有些唐突，这孩子我会安排其他弟子送他回家，你且去#Y"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"#n那里听候差遣吧。",
							"主线—拜入师门",GameCommonUtil.shimen_shizun_icon[chara.polar-1],GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s17";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s17".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "眼下门派日益壮大，事务繁忙，你可以到师尊#Y"+GameCommonUtil.shimen_shizun[chara.polar-1]+"#n处了解并领取师门任务再来向我复命。",
							"主线—拜入师门",GameCommonUtil.shimen_tongzi_icon[chara.polar-1],GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子这就去！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3){
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s18";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
					GameObjectChar.send(new MSG_PLAY_INSTRUCTION(), 16);
				}
				return;
			}
			if("主线—拜入师门s18".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "师门任务乃是弟子修行的检验方法，弟子们需根据师尊的指示处理门派日常事物，达到修行的目的。 ",
							"主线—拜入师门",GameCommonUtil.shimen_shizun_icon[chara.polar-1],GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "每日可以完成的师门任务是有次数限制的，记得每天都要完成，这样修行速度才会大大加快！",
							"主线—拜入师门",GameCommonUtil.shimen_shizun_icon[chara.polar-1],GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子谨记，一定坚持每日完成师门任务。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s19";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s19".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你办事一向稳重，此要务就交予你了。",
							"主线—拜入师门",GameCommonUtil.shimen_tongzi_icon[chara.polar-1],GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s20";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s20".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我倒要看看是何妖孽作祟！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					if(!chara.isFight) {
						shimenTask.task_state = "3";
						FightObject fightObject = new FightObject(GameData.that.baseFightObjectService.
								findOneByName("主线桃精"));
						fightObject.str = "桃精";
						fightObject.fid = 88888888;
						FightObject fightObject2 = new FightObject(GameData.that.baseFightObjectService.
								findOneByName("主线柳鬼"));
						fightObject2.fid = 99999999;
						fightObject2.str = "柳鬼";
						FightManager.activeBoosGoFight(chara, Lists.newArrayList(fightObject,fightObject2), true);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
						GameCommonUtil.npcMessage("桃精", "我等也是迫不得已，道长何必苦苦相逼！", 88888888, 6106, 1);
					}
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "为何到天墉城中惊扰百姓？",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我本身栖身桃柳林，前些日子突然来了大波妖怪，我打不过他们抢不到地盘就被赶出来了。", "主线—拜入师门", 6106, "桃精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shimenTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "岂能凭你一口之言便轻易相信你，可敢随我前去桃柳林一看究竟。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					shimenTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道长若能赶走那些妖怪，我自然愿意为您指路。", "主线—拜入师门", 6106, "桃精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8) {
					shimenTask.task_state = "9";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这桃精所言不知是否属实，我且去探查一番。段老板请安心赶制我派所需武器。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 9) {
					shimenTask.task_state = "10";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "放心吧，我这就开炉", "主线—拜入师门", 6012, "段铁心");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 10) {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s21";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			
			if("主线—拜入师门s21".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这里果然妖气凝聚，泄气冲天。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "远处的八卦台上方妖气浓郁不散，隐约结成阵法，不如去探查一下。",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					//创建npc
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 6000;
					npc.id = 66666666;
					npc.x = 26;
					npc.y = 34;
					npc.icon = 6211;
					npc.type = 2;
					npc.org_icon = 6211;
					npc.portrait = 6211;
					npc.name = "赤羽鸟怪";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s22";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s22".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我奉命在此修建阵法，识相的就乖乖滚开！",
							"主线—拜入师门",6211,"赤羽鸟怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "妖怪休要猖狂，容我破你阵法，有什么招，尽管使来！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					//进入战斗
					shimenTask.task_state = "4";
					if(!chara.isFight) {
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪"), true);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
						gameObjectChar.flag = "主线赤羽鸟怪";
					}
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "有何不敢！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s23";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 6000;
					npc.id = 66666666;
					npc.x = 32;
					npc.y = 12;
					npc.icon = 6211;
					npc.type = 2;
					npc.org_icon = 6211;
					npc.portrait = 6211;
					npc.name = "赤羽鸟怪";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			if("主线—拜入师门s23".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哈，就你们这些妖怪，我何时俱过！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					if(!chara.isFight) {
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪"), true);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
						gameObjectChar.flag = "主线赤羽鸟怪s23";
					}
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "蟒精命我在此修建阵法，吞食桃柳林天地之气，引诱四方妖邪来此。", "主线—拜入师门", 6211, "赤羽鸟怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shimenTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那蟒精现在何处？",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shimenTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "他已前往东海渔村，说是有要紧之事。", "主线—拜入师门", 6211, "赤羽鸟怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s24";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}
			if("主线—拜入师门s24".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你这娃子病得不轻吧，什么妖怪，有妖怪早把我老头子吃了！", "主线—拜入师门", 6035, "樵夫");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（不对，这樵夫说起话来怪腔怪调的，难道他就是那妖怪的化身！！）", "主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（一时也无法确定，若是误伤岂不是罪孽。对了！我有办法！）", "主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s25";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
					//弹出召唤
					Map<String,Object> obj = new LinkedHashMap<String, Object>();
					obj.put("id:int", 6022);
					GameObjectChar.send(new CommonCmd(0xA0A4), obj);
				}
				return;
			}
			if("主线—拜入师门s25".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这该如何是好，若是延误太久会让那妖怪察觉遁逃了。", "主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "柳如尘有一法宝名照妖镜，能使妖物无所遁形，你可去借来一用。", "主线—拜入师门",6053,"玉真子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					//下一任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s26";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s26".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道长高义，我这就去除了那蟒精", "主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					//下一任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s27";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
					//樵夫
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 11000;
					npc.id = 66666666;
					npc.x = 14;
					npc.y = 64;
					npc.icon = 6035;
					npc.type = 2;
					npc.org_icon = 6035;
					npc.portrait = 6035;
					npc.name = "樵夫";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			//铲除蟒精
			if("主线—拜入师门s28".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "果然你就是蟒精！虽然你藏的很深，但也架不住机智的我！看打！！", "主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					//进入战斗
					if("0".equals(shimenTask.task_extra_para)) {
						if(!chara.isFight) {
							shimenTask.task_state = "3";
							gameObjectChar.flag = "主线蟒精s28";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线蟒精","主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪"), true);
							Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
							GameObjectChar.send(new M45056_0(), vo_45056_4);
							GameObjectChar.send(new M4155_0(), 0);
						}
					}
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "都是因为......#B(蟒精正要坦白从宽，却突然口吐鲜血，爆体而亡！)",
							"主线—拜入师门", 6206, "蟒精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
					//下一任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s29";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			
			if("主线—拜入师门s29".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "若如你所见，恐怕事情就没这么简单，我这就派出门下探子调查事情真相。", "主线—拜入师门", GameCommonUtil.shimen_shizun_icon[chara.polar-1], 
							GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shimenTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这段时间你进益颇多，在众第三代弟子中脱颖而出。最近师门驯养的海龟成年了，我打算是赏予你一只。", "主线—拜入师门", GameCommonUtil.shimen_shizun_icon[chara.polar-1], 
							GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shimenTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不过以你现在的修为无法驾驭，你修炼到20级以后再去找#Y"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"#n领取吧。", "主线—拜入师门", GameCommonUtil.shimen_shizun_icon[chara.polar-1], 
							GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shimenTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "多谢掌门赏识！弟子日后定当勤加修炼！",
							"主线—拜入师门");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					//创建任务
					GameUtil.renwujiangli(chara);
					if(chara.level>=20) {
						chara.current_task = "主线—拜入师门s30_2";
					}else {
						chara.current_task = "主线—拜入师门s30_1";
					}
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					if(chara.level>=20) {
						renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
						//弹出新手礼包
						GameUtil.openDlg("RookieGiftDlg");
					}
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			
			if("主线—拜入师门s30_2".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1) {
					shimenTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这只海龟品种优良，你可要多加善待才是。", "主线—拜入师门", GameCommonUtil.shimen_tongzi_icon[chara.polar-1], 
							GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					
				}else if(step == 2) {
					//查询本次任务
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					boolean isGetPet = false;
					if(renwu.getReward().indexOf("#宝宝") != -1 || renwu.getReward().indexOf("#神兽") != -1 
							|| renwu.getReward().indexOf("#变异") != -1) {
						String[] split = renwu.getReward().split(",");
						for(String sp:split) {
							String[] info = sp.split("\\#");
							if("宝宝".equals(info[1]) || "神兽".equals(info[1]) || "变异".equals(info[1])) {
								//领取宠物
								Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
								vo_9129_2.notify = ClientButtonIdConst.NOTICE_FETCH_BONUS;
								vo_9129_2.para = "#I1|"+info[0]+"("+info[1]+")$1#I";
								GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2);
								isGetPet = true;
								break;
							}
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					if(!isGetPet) {
						//如果没有宠物领取的话就直接下个任务
						GameUtil.renwujiangli(chara);
						chara.current_task = "主线—山雨欲来s1";
						renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
						renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
						closeAndCreateTaskShanYu(chara, renwu);
						GameUtilRenWu.removeTask("主线—拜入师门", chara);
					}
				}
				return;
			}
			
			//主线—山雨欲来
			Vo_61553_0 shanyuTask = chara.taskMap.get("主线—山雨欲来");
			if("主线—山雨欲来s1".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "但你根基尚浅，凡事切莫一意孤行，回来与我禀告再做决断，亦不可逡巡不前，畏首畏尾，损了门派声誉。",
							"主线—山雨欲来",GameCommonUtil.shimen_tongzi_icon[chara.polar-1],GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子定不负师父的期望。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "为师还有一事需托你代办：近来#R天墉城#n的#Y冯喜来#n似乎遇到了些麻烦，你一探究竟，为其排忧解难。",
							"主线—山雨欲来",GameCommonUtil.shimen_tongzi_icon[chara.polar-1],GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那我这就出发！#B（此行也正好去看看杨镖头，定要将那日之事解释清楚才是。）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s2";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s2".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哎，确有一事烦心，你没发现今天都没什么客人么。",
							"主线—山雨欲来", 6016, "冯喜来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（你环视四周，发现确实没有几个人，生意比以前差了很多。）#n究竟发生了什么，客人们都不见了。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "还不是那几个可恶的家伙，在这里吃霸王餐不说，还四处捣乱弄得客人们都不能好好吃饭，客人都被吓走了，所以我正发愁呢。",
							"主线—山雨欲来", 6016, "冯喜来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "还有这种事，不知那帮恶霸现在何处，我替你好好教训教训他们！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这个我就不清楚了，我哪敢跟着他们啊。#52m",
							"主线—山雨欲来", 6016, "冯喜来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shanyuTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（去问问#Y神算子#n吧，他一定能知道。）#n放心好了，这事就交给我了！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s3";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s3".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你来的不巧，我正打算出门一趟，那天#Y灵兽异人#n托我帮他算算风水，我正打算去告诉他。",
							"主线—山雨欲来", 6091, "神算子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（灵兽异人不就在旁边么！不用一盏茶的时间就能到，你糊弄谁呢！！）#n不如前辈把要说的告诉我，我帮您转告他，老头你快帮我算算在冯喜来那吃霸王餐的家伙在哪吧。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这年纪大了，也不愿意走动，你就帮我跑一趟把这封信给他吧。对了，记得问他要报酬！",
							"主线—山雨欲来", 6091, "神算子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（原来你是为了这个啊......）#n我这就去！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s4";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}
			else if("主线—山雨欲来s4".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这是神算子托我交给你的信。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我都听到啦，本来说乘着今儿个风和日丽心情不错过去问问他结果，没想到听到这事，弄得我窝了我一肚子火！",
							"主线—山雨欲来",6041,"神兽异人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不就是几百文钱么，白当那么多年邻居了，给你给你，真是的。",
							"主线—山雨欲来",6041,"神兽异人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（又不是我收你的钱，你和我较什么劲！懒得和你计较，赶紧回去要紧。）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s5";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}
			else if("主线—山雨欲来s5".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "此等小事自是手到擒来，你可前去#R轩辕庙#n附近，他们就在那里。",
							"主线—山雨欲来",6091,"神算子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那晚辈这就去，告辞！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s6";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}
			else if("主线—山雨欲来s6".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "白痴？小兔崽子你居然敢骂我，找茬找到爷爷我头上来了，真是不知好歹！",
							"主线—山雨欲来",6202,"恶霸");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你还敢恶人先告状，看来非得好好揍你一顿不可！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(!chara.isFight) {
							if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
								GameUtil.sendMeTips("不可组队完成！");
								return;
							}
							gameObjectChar.flag = "主线—山雨欲来s6";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线恶霸","主线恶霸","主线恶霸","主线恶霸","主线恶霸"), true);
						}
						//正在战斗则把他关闭了战斗
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "今儿个出师不利啊，咱后会有期......有鬼啊！！",
							"主线—山雨欲来",6202,"恶霸");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "切，想乘我不备溜走？就这点小伎俩我可不上你的当。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shanyuTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "就是，我怎么可能是鬼嘛，我可是妖。",
							"主线—山雨欲来",6140,"妖风");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					shanyuTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你这么想跑，那我来带你一程啊。#B（说着有一阵风卷走了恶霸。）",
							"主线—山雨欲来",6140,"妖风");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8) {
					shanyuTask.task_state = "9";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "大胆妖孽，竟敢在我面前行凶！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 9) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s7";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					//妖风
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6140;
					npc.mapid = 8000;
					npc.id = 66666666;
					npc.x = 39;
					npc.y = 22;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "妖风";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}else if("主线—山雨欲来s7".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我岂能坐视不管，如此与妖无异！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "好，我倒要看看你有多大的本事！",
							"主线—山雨欲来",6140,"妖风");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==3) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else if(!chara.isFight) {
							//进入战斗
							gameObjectChar.flag = "主线—山雨欲来s7";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线妖风","主线妖风","主线妖风","主线妖风","主线妖风"), true);
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "啊啊啊，道长救我，我知错了，救我！！！",
							"主线—山雨欲来",6202,"恶霸");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "可恶，这妖风诡异异常，完全使不上力气，得赶紧回禀师父才是。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shanyuTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不行不行，不可见死不救，这妖怪应该就在这附近，不如去找#Y无想僧#n前辈问问。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s8";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}else if("主线—山雨欲来s8".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "近来轩辕坟附近妖气横生，但尚且不知是何方妖怪，道友匆匆而来打听此事，所为何事？",
							"主线—山雨欲来",6089,"无想僧");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "有妖怪擒走了几个作恶的人，虽说他们坏事做尽，却不能见死不救，任其由妖怪摆布。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "竟有此事！道友切莫心急，小心为上，最好先回禀你师父想想对策吧。",
							"主线—山雨欲来",6089,"无想僧");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "多谢晚辈，晚辈明白。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（不行，回去报信时间来不及了，不如先去#R轩辕坟一层#n打探一番）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s9";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s9".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这声音，你是那个妖怪，他们人呢！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "别成天妖怪妖怪的，我们是妖，哪里怪啦，你问我他们啊，当然是被我吃了咯。#1f",
							"主线—山雨欲来",6204,"琵琶精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你！妖怪你敢......我定要除了你！！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成");
						}else if(!chara.isFight) {
							gameObjectChar.flag = "主线—山雨欲来s9";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线琵琶精","主线妖风","主线妖风","主线妖风","主线妖风"), true);
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哟哟哟，瞧瞧这脾气，要是本事能有脾气一半大，也不会落到这般田地啊，还是乖乖的做我的晚餐吧。",
							"主线—山雨欲来",6204,"琵琶精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shanyuTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "大胆妖孽，那日我本念你修行不易，留你一条生路，今日你却变本加厉！",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					shanyuTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哦？又有不知好歹的家伙来了，还真是好事成双啊。",
							"主线—山雨欲来",6204,"琵琶精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8) {
					shanyuTask.task_state = "9";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "区区一个琵琶精，也敢在我面前放肆！",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					//播放特效
					Map<String, Object> map2 = new LinkedHashMap<String, Object>();
					map2.put("id", 66666666);
					map2.put("effect_no", 3018);
					map2.put("order", 0);
					map2.put("post", (byte) 2);
					map2.put("y", 49);
					map2.put("x", 42);
					map2.put("loops", 0);
					map2.put("interval", 0);
					map2.put("during", 0);
					gameObjectChar.sendOne(new CommonWrite(0xB073), map2);
				}else if(step == 9) {
					shanyuTask.task_state = "10";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你是......真人，真人饶命啊，我再也不敢了！啊......#B（只看见一道闪光，琵琶精便化作一道黑烟被除掉了。）",
							"主线—山雨欲来",6204,"琵琶精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 10) {
					shanyuTask.task_state = "11";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（真人看向你）#n小子，随我来，我有事与你交代。",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 11) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s10";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s10".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "修道之人，怎能意气用事。道基受损还尚可挽救，若是身死道消，岂不惜哉。",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "真人教训的是，是弟子冲动了。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "且说千百年来无数的精怪妖魔飞升得道，与人相安无事。那日我心存此念，故有今日一劫，错皆在我。",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "真人无需自责，错在弟子才是。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "轩辕坟内，尚有此妖党羽，而我似乎嗅到了幕后更大的阴谋，此事非同一般啊。",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					shanyuTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你既入此劫，自然无法置身事外，老道要去看看究竟是谁在搅浑这水，这些小妖，就交由你如何。",
							"主线—山雨欲来",6086,"陆压真人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 7) {
					shanyuTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子岂敢不从，自然全力为之！#B（此事干系重大，得告诉无想僧前辈小心才是。）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8){
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s11";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}
			else if("主线—山雨欲来s11".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哦？真人已经很久不理这些琐事，看来事情远没有那么简单啊。",
							"主线—山雨欲来",6089,"无想僧");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "正是，真人已前去调查幕后主使，轩辕坟内还有其他妖怪，前辈也要万事小心。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哈哈，我自会小心，道友尽管放心。",
							"主线—山雨欲来",6089,"无想僧");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那晚辈这就回去禀告师尊。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s12";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s12".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既是陆压真人之命，我等定当全力配合，但我需要先派人调查一番，你且先去#Y"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"#n处听候差遣。",
							"主线—山雨欲来", GameCommonUtil.shimen_shizun_icon[chara.polar-1], GameCommonUtil.shimen_shizun[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "弟子遵命。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s13";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}
			else if("主线—山雨欲来s13".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既是师尊之命，自然有师尊的打算，正好有外门弟子禀告近来有妖孽在#R北海海滩#n作恶，此地距轩辕坟极近，你去调查一番，其中兴许有什么关联。",
							"主线—山雨欲来", GameCommonUtil.shimen_tongzi_icon[chara.polar-1], GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我这就去！#B（定是那妖孽的党羽又在害人，要快点赶过去才是。）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "若有情况可随时寄书信予我，你还是以下山修行为重，有要事我自会命人通知你。",
							"主线—山雨欲来", GameCommonUtil.shimen_tongzi_icon[chara.polar-1], GameCommonUtil.shimen_tongzi[chara.polar-1]);
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s14";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}
			else if("主线—山雨欲来s14".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "确有些稀奇事，夜里在#R沙滩#n那边总有奇怪的光闪烁，走近却什么都没有。",
							"主线—山雨欲来", 6044, "渔夫");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "竟有此事，我这就去看看。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s15";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					//显示冤魂
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6141;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 14;
					npc.y = 42;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "恶霸怨魂";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			else if("主线—山雨欲来s15".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道士，你害的我好苦啊！",
							"主线—山雨欲来", 6141, "恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "是你？你怎么成了这副模样。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "都是你害的！我被那妖怪变成这样都是你害的！",
							"主线—山雨欲来", 6141, "恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（看他这副模样，还是先稳住他吧。）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==5) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else if(!chara.isFight) {
							gameObjectChar.flag = "主线—山雨欲来s15";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂"), true);
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 6) {
					shanyuTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你别激动，我不想伤害你，你现在肉身已失，再不前往六道轮回，就来不及了！",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step ==7) {
					shanyuTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哼，我死于妖怪之手，肉身不得安息，若寻不到定魂香，是无法进入轮回的。",
							"主线—山雨欲来", 6141, "恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8) {
					shanyuTask.task_state = "9";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（这是不能不管，那妖怪也未现身，不如先帮他寻找再打探消息吧。）#n我来帮你找#Y定魂香#n，但你要答应我，万万不可伤人。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 9) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s16";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					//北海乌龙
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6117;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 13;
					npc.y = 16;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "北海乌龙";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			else if("主线—山雨欲来s16".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小道无心冒犯，还请宽恕，只是有人因我而死，入不得轮回，听闻此地有定魂香，故特地来寻。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我当何事，区区定魂香而已，老龙多得是，便予你一些又何妨。",
							"主线—山雨欲来",6117, "北海乌龙");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那么就有劳你了。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "老龙我半世修行，飞升在即，能结一桩善缘，也未尝不是一件好事，救人要紧，赶紧去吧。",
							"主线—山雨欲来",6117, "北海乌龙");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s17";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6141;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 14;
					npc.y = 42;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "恶霸怨魂";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}else if("主线—山雨欲来s17".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哎，你也是有心了，只可惜太迟了。",
							"主线—山雨欲来",6141, "恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这......又怎么了。#45m",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我在阳界的时间太长，定魂香已经不起作用，如果能找到#Y定魂珠#n也许会有用。",
							"主线—山雨欲来",6141, "恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "好，我这就去帮你找。#B（也不知那老龙有没有定魂珠，索性先去问问吧。）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s18";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6117;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 13;
					npc.y = 16;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "北海乌龙";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}else if("主线—山雨欲来s18".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那人为妖怪所害，定魂香并不能起什么作用，您是否有定魂珠，那应该能帮到他。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道士不要欺人太甚！枉我一片好心，原来你一开始就在打我定魂珠的主意！",
							"主线—山雨欲来",6117, "北海乌龙");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不是这样的，你听我说......#63m",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(!chara.isFight) {
							if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
								GameUtil.sendMeTips("不可组队完成！");
							}else {
								gameObjectChar.flag = "主线—山雨欲来s18";
								FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线北海乌龙"), true);
							}
						}
						//正在战斗的话就关闭对话
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（这老龙心地不坏，想必说的都是真的，难道那家伙在骗我？）#n当真如此？我定回去查个清楚，打搅了。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s19";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6175;
					npc.mapid = 9000;
					npc.id = 77777777;
					npc.x = 18;
					npc.y = 44;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "雉鸡精";
					npc.dir = 2;
					gameObjectChar.sendOne(new M65529_0(), npc);
					npc = new Vo_APPEAR();
					icon = 6141;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 14;
					npc.y = 42;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "恶霸怨魂";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}else if("主线—山雨欲来s19".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "一切都如您所料，那家伙已经去找定魂珠了，估计已经和那老龙打起来了。",
							"主线—山雨欲来",6141,"恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "干的不错，等我拿到了定魂珠就重塑你的肉身，到时候跟着我少不了你好处。", "主线—山雨欲来", 6175, "雉鸡精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你居然敢骗我，亏我还好心帮你寻找定魂珠！", "主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哼，堕入轮回哪有跳出六道自在，我变成这幅模样，还不是拜你所赐！",
							"主线—山雨欲来",6141,"恶霸怨魂");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					shanyuTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "既然被发现了，那就只好先除了你，再去找那条龙的麻烦了！", "主线—山雨欲来", 6175, "雉鸡精");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 6) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else {
							if(!chara.isFight) {
								gameObjectChar.flag = "主线—山雨欲来s19";
								FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线雉鸡精","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂"), true);
							}
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 7) {
					shanyuTask.task_state = "8";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "发生了这么多事，得赶紧给师父寄封书信说明一下。对了，还得告诉#Y冯喜来#n事情已经解决了呢。","主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 8) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s20";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
					GameObjectChar.send(new MSG_DISAPPEAR(), 77777777);
				}
				return;
			}else if("主线—山雨欲来s20".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "真是感激不尽啊，今后若是有什么我帮得上的，尽管来找我！",
							"主线—山雨欲来",6016,"冯喜来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "您就别客气了，都是我应该做的。#1m",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "#B（时候尚早，不如再四处走走。对了！之前一直听说千面怪有千变万化的本事，不如前去一探究竟，长长见识也好！）",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s21";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s21".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "道友言重了，这变化之术并非晦涩难懂，一道简单的变身咒，即可完成一次简单的变化。",
							"主线—山雨欲来",6049,"千面怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					shanyuTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哦，此话怎讲。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哈哈，我传授道友一道变身咒语，只要在#R当前频道#n喊出该咒语，就可使周围的人短暂变身，道友不妨前去一试。",
							"主线—山雨欲来",6049,"千面怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					shanyuTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那我可真要去试试！#56m",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 5) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s22";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					//虎妖
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6121;
					npc.mapid = 5000;
					npc.id = 66666666;
					npc.x = 50;
					npc.y = 38;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "虎妖";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			else if("主线—山雨欲来s22".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				int step = Integer.valueOf(shanyuTask.task_state);
				if(step == 1) {
					shanyuTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小道士可别口出狂言，爷爷我可不是吃素的！",
							"主线—山雨欲来",6121,"虎妖");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					if("0".equals(shanyuTask.task_extra_para)) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else {
							if(!chara.isFight) {
								gameObjectChar.flag = "主线—山雨欲来s22";
								FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线虎妖","主线虎妖","主线虎妖","主线虎妖","主线虎妖"), true);
							}
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else if(step == 3) {
					shanyuTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不过千面怪这变身之术还真是令人叹为观止啊。时候也不早了，不如去杨镖头那看看，将当日之事解释清楚吧。",
							"主线—山雨欲来");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					//到这里新手任务就结束了
					GameUtil.renwujiangli(chara);
					chara.current_task = "";
					GameUtilRenWu.removeTask("主线—山雨欲来", chara);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			//妖魔道
			Vo_61553_0 yaomodao = chara.taskMap.get("妖魔道");
			if("妖魔道—勇擒鱼怪s1".equals(chara.current_task) && yaomodao != null) {
				int step = Integer.valueOf(yaomodao.task_state);
				if(step == 1) {
					yaomodao.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "原来竟是这些小事，我这就去将这些小妖擒下夺回药材。",
							"妖魔道—勇擒鱼怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 2) {
					yaomodao.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "公子可要小心，我听人说这些妖魔本事大着呢！",
							"妖魔道—勇擒鱼怪",6011,"王老板");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 3) {
					yaomodao.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你就放心吧，这段时间师父可是教了我不少道法，正好拿他们练手。",
							"妖魔道—勇擒鱼怪");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}else if(step == 4) {
					yaomodao.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "如此说来倒是我多虑了。",
							"妖魔道—勇擒鱼怪",6011,"王老板");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
				}
				return;
			}
			Vo_61553_0 petFeiShengTask = chara.getTaskMap().get("宠物飞升");
			if (petFeiShengTask != null && Integer.valueOf(petFeiShengTask.task_state)>0) {
				int step = Integer.valueOf(petFeiShengTask.task_state);
				if (step == 1) {
					petFeiShengTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那应该如何引导呢？", "宠物飞升");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 2) {
					petFeiShengTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "蓬莱岛的米兰仙子，善于驭兽之道，你可带你的宠物前去寻求指点", "宠物飞升", 6041,
							"灵兽异人");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 3) {
					petFeiShengTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "多谢异人。", "宠物飞升");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 4) {
					Vo_61553_0 a35 = new Vo_61553_0();
					a35.count = 1;
					a35.task_type = "宠物飞升";
					a35.task_desc = "异类修道，难成正果，但经过主人的引导，宠物可以飞升而达到新的境界，突破等级限制。当前正在引导#Y" + petFeiShengTask.task_extra_para
							+ "#n进行飞升";
					a35.task_prompt = "向#P米兰仙子#P求教";
					a35.refresh = 1;
					a35.task_end_time = (int) (System.currentTimeMillis() / 1000L);
					a35.attrib = 1;
					a35.reward = "";
					a35.show_name = "宠物飞升";
					a35.task_extra_para = "飞升阶段";
					a35.task_state = "1";
					// 这时候放置的是任务对象
					GameUtilRenWu.createTaskTeam(a35, chara);
					GameObjectChar.send(new M4155_0(), 0);
					return;
				}
			}

			// 米兰仙子处理飞升
			if (petFeiShengTask != null && "飞升阶段".equals(petFeiShengTask.task_extra_para)) {
				int step = Integer.valueOf(petFeiShengTask.task_state);
				if (step == 1) {
					petFeiShengTask.task_state = "2";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "嗯，异人是我的朋友，而且我也非常喜欢宠物，我这就将宠物飞升的办法告诉你", "宠物飞升", 6002,
							"米兰仙子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 2) {
					petFeiShengTask.task_state = "3";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "宠物飞升像人一样，需要经历一些考验。其中包括#R武学考验、攻击考验、法术考验#n，而且是连续进行的",
							"宠物飞升", 6002, "米兰仙子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 3) {
					petFeiShengTask.task_state = "4";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那如何才能接受这些考验呢？", "宠物飞升");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 4) {
					petFeiShengTask.task_state = "5";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "这些考验本有鸿钧道人主持，后来道人将法器交出，这个事情就落在我身上了。", "宠物飞升", 6002,
							"米兰仙子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 5) {
					petFeiShengTask.task_state = "6";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那太好了！请仙子施法！", "宠物飞升");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 6) {
					petFeiShengTask.task_state = "7";
					Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "事不宜迟，现在就开始吧！", "宠物飞升", 6002, "米兰仙子");
					GameObjectChar.send(new M45056_0(), vo_45056_2);
					return;
				}
				if (step == 7) {
					ArrayList<String> wuxueDaochong = new ArrayList<>();
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					wuxueDaochong.add("武学道宠");
					FightManager.goFightfssc(chara, wuxueDaochong);
					return;
				}
			}
			
			//白帮忙-冯喜来
			Vo_61553_0 zuRenFxl = chara.getTaskMap().get("助人为乐—打抱不平");
			if(zuRenFxl != null) {
				if("助人为乐—打抱不平s1".equals(zuRenFxl.currentTask)) {
					int step = Integer.valueOf(zuRenFxl.task_state);
					if (step == 1) {
						zuRenFxl.task_state = "2";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "掌柜的为何唉声叹气？",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 2) {
						zuRenFxl.task_state = "3";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你有所不知啊，近来常有一批暴徒来我店里喝酒，他们不但不给钱，还老乱砸东西，我实在是拿他们没办法啊。",
								"助人为乐—打抱不平", 6016, "冯喜来");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 3) {
						zuRenFxl.task_state = "4";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "竟然还有这样的狂徒！碰到的话我定要会他一会。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 4) {
						zuRenFxl.task_state = "5";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "谁在说要跟本大爷会会的？！",
								"助人为乐—打抱不平", 6201,"暴徒");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 5) {
						zuRenFxl.task_state = "6";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "我就是来教训你的。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 6) {
						if("0".equals(zuRenFxl.task_extra_para)) {
							if(!chara.isFight) {
								GameCommonUtil.dialogOk("#R本场战斗会有死亡惩罚，请小心！");
								gameObjectChar.flag = "助人为乐—打抱不平s1";
								FightManager.goFightDynamicLevel(chara, Lists.newArrayList("助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒"), false);
							}
							Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
							GameObjectChar.send(new M45056_0(), vo_45056_4);
							GameObjectChar.send(new M4155_0(), 0);
						}
					}else if(step == 7) {
						zuRenFxl.task_state = "8";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "哼，我才不服，有本事你去跟我大哥比试比试，我大哥说他早就想教训你了。",
								"助人为乐—打抱不平", 6201,"暴徒");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 8) {
						zuRenFxl.task_state = "9";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你大哥是谁？",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 9) {
						zuRenFxl.task_state = "10";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "大名鼎鼎的#Y无名剑客#n就是我大哥。",
								"助人为乐—打抱不平", 6201,"暴徒");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 10) {
						zuRenFxl.task_state = "11";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "啊？他居然跟你这样的人称兄道弟？待我去与他过过招。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 11) {
						GameUtilRenWu.createTask(chara, "助人为乐—打抱不平s2");
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}
				if("助人为乐—打抱不平s2".equals(zuRenFxl.currentTask)) {
					int step = Integer.valueOf(zuRenFxl.task_state);
					if (step == 1) {
						zuRenFxl.task_state = "2";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你说的是我哪个兄弟？",
								"助人为乐—打抱不平",6231,"无名剑客");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 2) {
						zuRenFxl.task_state = "3";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "你狐朋狗友还真不少啊，我也懒得慢慢说了，咱们切磋切磋，手底下见真章吧。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 3) {
						if("0".equals(zuRenFxl.task_extra_para)) {
							if(!chara.isFight) {
								GameCommonUtil.dialogOk("#R本场战斗会有死亡惩罚，请小心！");
								gameObjectChar.flag = "助人为乐—打抱不平s2";
								FightManager.goFightDynamicLevel(chara, Lists.newArrayList("助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客"), false);
							}
							Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
							GameObjectChar.send(new M45056_0(), vo_45056_4);
							GameObjectChar.send(new M4155_0(), 0);
						}
					}else if(step == 4) {
						zuRenFxl.task_state = "5";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "去客栈问你那醉酒的兄弟去。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 5) {
						zuRenFxl.task_state = "6";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "醉酒的兄弟？我自远方而来，没有兄弟在本地啊。",
								"助人为乐—打抱不平",6231,"无名剑客");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 6) {
						zuRenFxl.task_state = "7";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "啊？难道是那个家伙在骗我？待我去问问清楚。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 7) {
						GameUtilRenWu.createTask(chara, "助人为乐—打抱不平s3");
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}
				
				if("助人为乐—打抱不平s3".equals(zuRenFxl.currentTask)) {
					int step = Integer.valueOf(zuRenFxl.task_state);
					if (step == 1) {
						zuRenFxl.task_state = "2";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "那个醉酒闹事的客人刚才见你一出门，就一溜烟跑掉了，还说“没想到这么容易就上当了”。",
								"助人为乐—打抱不平", 6016, "冯喜来");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 2) {
						zuRenFxl.task_state = "3";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "可恶，果然是被他骗了。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 3) {
						zuRenFxl.task_state = "4";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "不管怎么样这次多亏有你来，太谢谢了。",
								"助人为乐—打抱不平", 6016, "冯喜来");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 4) {
						zuRenFxl.task_state = "5";
						Vo_45056_0 vo_45056_2 = GameUtil.a45056(chara, "小事一桩，不用客气。",
								"助人为乐—打抱不平");
						GameObjectChar.send(new M45056_0(), vo_45056_2);
					}else if(step == 5) {
						GameUtilRenWu.removeTask("助人为乐—打抱不平", chara);
						GameCommonUtil.dialogOk("已帮助了#R天墉城#n中的居民，回去找#Y白邦芒#n领取犒赏。");
						GameUtilRenWu.createTask(chara, "助人为乐—领取犒赏");
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}
			}
			
			//白帮忙-乞丐
			Vo_61553_0 zuRenFpjk = chara.getTaskMap().get("助人为乐—扶危救困");
			if(zuRenFpjk != null) {
				if("助人为乐—扶危救困s1".equals(zuRenFpjk.currentTask)) {
					int step = Integer.valueOf(zuRenFpjk.task_state);
					if (step == 2) {
						GameUtilRenWu.createTask(chara, "助人为乐—扶危救困s2");
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}else if("助人为乐—扶危救困s2".equals(zuRenFpjk.currentTask)) {
					int step = Integer.valueOf(zuRenFpjk.task_state);
					if (step == 2) {
						GameUtilRenWu.createTask(chara, "助人为乐—扶危救困s3");
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}else if("助人为乐—扶危救困s3".equals(zuRenFpjk.currentTask)) {
					int step = Integer.valueOf(zuRenFpjk.task_state);
					if (step == 2) {
						GameUtilRenWu.removeTask("助人为乐—扶危救困", chara);
						GameCommonUtil.dialogOk("已帮助了#R天墉城#n中的居民，回去找#Y白邦芒#n领取犒赏。");
						GameUtilRenWu.createTask(chara, "助人为乐—领取犒赏");
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}
			}
			
		} else if (1 == type) {
			if ("主线—浮生若梦_s0".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				chara.current_task = "主线—浮生若梦_s1";
				closeAndCreateTask(chara);
				gameObjectChar.sendOne(new MSG_DISAPPEAR(), 333333333);
				return;
			}
			if ("主线—浮生若梦_s1".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				this.geizhuangb(chara);
				chara.current_task = "主线—浮生若梦_s2";
				closeAndCreateTask(chara);
				return;
			}
			//王老板
			if ("主线—浮生若梦_s3".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s4";
				closeAndCreateTask(chara);
				return;
			}
			if ("主线—浮生若梦_s4".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//这里开始领取宠物
				task.task_state = "3";
				Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
				vo_9129_0.notify = ClientButtonIdConst.NOTIFY_CLOSE_DLG;
				vo_9129_0.para = "DramaDlg";
				gameObjectChar.sendOne(new MSG_GENERAL_NOTIFY(), vo_9129_0);
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				if(renwu != null && !StringUtils.isNullOrEmpty(renwu.getReward())) {
					String reward = renwu.getReward();
					String[] split = reward.split("\\#");
					//打开界面领取
					Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
					vo_9129_2.notify = ClientButtonIdConst.NOTICE_FETCH_BONUS;
					vo_9129_2.para = "#I1|"+split[0]+"("+split[1]+")$1#I";
					GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2);
				}
				return;
			}

			//新手黄仨儿战斗
			if ("主线—浮生若梦_s5".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				String com = task.task_extra_para;
				if(com != null && com.equals("1")) {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s6";
					closeAndCreateTask(chara);
				}else {
					if(!chara.isFight) {
						gameObjectChar.chara.zhandouId = 8888888;
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("新手兔子"), false);
						gameObjectChar.sendOne(new MSG_PLAY_INSTRUCTION(), 17);
						gameObjectChar.flag = "newCombatFightS4";
						GameObjectChar.send(new M4155_0(), 0);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
					}
				}
				return;
			}
			//莲花姑娘
			if ("主线—浮生若梦_s6".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s7";
				closeAndCreateTask(chara);
				return;
			}
			//赵老板
			if ("主线—浮生若梦_s7".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s8";
				closeAndCreateTask(chara);
				return;
			}
			//寻找玉佩结束
			if ("主线—浮生若梦_s8".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s9";
				closeAndCreateTask(chara);
				return;
			}
			//张老板
			if ("主线—浮生若梦_s9".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s10";
				closeAndCreateTask(chara);
				return;
			}
			//玉佩告知赵老板
			if ("主线—浮生若梦_s10".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s11";
				closeAndCreateTask(chara);
				return;
			}
			//莲花姑娘找我
			if ("主线—浮生若梦_s11".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s12";
				closeAndCreateTask(chara);
				return;
			}
			//官道南打强盗
			if ("主线—浮生若梦_s12".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				if("0".equals(task.task_extra_para)) {
					if(!chara.isFight) {
						List<String> list = new ArrayList<>();
						list.add("新手强盗");
						list.add("新手强盗");
						FightManager.activeBoosGoFight(chara, list, false);
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s13";
					closeAndCreateTask(chara);
					//删除npc
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 55555555);
				}
				return;
			}
			//继续赶路
			if ("主线—浮生若梦_s13".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s14";
				closeAndCreateTask(chara);
				return;
			}
			if ("主线—浮生若梦_s14".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s15";
				closeAndCreateTask(chara);
				return;
			}
			if ("主线—浮生若梦_s15".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s16";
				closeAndCreateTask(chara);
				return;
			}
			//教训强盗
			if ("主线—浮生若梦_s16".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				if("0".equals(task.task_extra_para)) {
					if(!chara.isFight) {
						//开始打架
						List<String> list = new ArrayList<>();
						list.add("新手强盗");
						list.add("新手强盗");
						FightManager.activeBoosGoFight(chara, list, false);
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—浮生若梦_s17";
					closeAndCreateTask(chara);
				}
				return;
			}
			//继续追击官道南
			if ("主线—浮生若梦_s17".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s18";
				closeAndCreateTask(chara);
				return;
			}
			//前方好像有狗叼
			if ("主线—浮生若梦_s18".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s19";
				closeAndCreateTask(chara);
				return;
			}
			//赶往天墉城
			if ("主线—浮生若梦_s19".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s20";
				closeAndCreateTask(chara);
				return;
			}
			//包裹给杨
			if ("主线—浮生若梦_s20".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s21";
				closeAndCreateTask(chara);
				return;
			}
			//拜师
			if ("主线—浮生若梦_s21".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—浮生若梦_s22";
				closeAndCreateTask(chara);
				//打开领取礼包
				GameUtil.openDlg("RookieGiftDlg");
				//完成浮生若梦成就
				GameObjectChar.send(new MSG_ACHIEVE_FINISHED(), new Object[] {501000,"浮生若梦"});
				//新手礼包
//				GameUtil.a49171(chara);
				return;
			}
			if ("主线—浮生若梦_s22".equals(chara.current_task) && task != null && Integer.valueOf(task.task_state)>0) {
				//下一个任务
				GameUtilRenWu.removeTask("主线—浮生若梦", chara);
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s1";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt("向#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|E=【主线】我已准备妥当#P学习道法");
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				//获得称号
				String[] chenghao = { "五龙山云霄洞第三代弟子", "终南山玉柱洞第三代弟子", "凤凰山斗阙宫第三代弟子", "乾元山金光洞第三代弟子",
				"骷髅山白骨洞第三代弟子" };
				String chenhao = chenghao[chara.polar - 1];
				GameUtil.chenghaoxiaoxi(chara, "拜师任务", chenhao);
				Vo_20481_0 vo_20481_3 = new Vo_20481_0();
				vo_20481_3.msg = "你获得了#R" + chenhao + "#n的称谓。";
				vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_3);
				//装备称谓
				chara.chenhao = chenhao;
				GameObjectChar.send(new MSG_UPDATE_APPEARANCE(), GameUtil.a61661(chara));
				//剧情对话消失
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			//第二个剧情
			if ("主线—拜入师门s1".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//打开力法选择
				GameUtil.openDlg("ChoseAtkDlg");
				//剧情对话消失
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			if ("主线—拜入师门s2".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				int step = Integer.valueOf(shimenTask.task_state);
				if(step == 1 || step == 2) {
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else if(step == 3) {
					GameUtil.renwujiangli(chara);
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					//创建任务
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask("主线—拜入师门s2_1");
					String[] skill = new String[] {"金光乍现","摘叶飞花","滴水穿石","举火焚天","落土飞岩"};
					renwu.setTaskPrompt("找#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|M=【主线】学习道法#P将#R力破千钧#n或#R"+skill[chara.polar-1]+"#n提升至16级");
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				}
				return;
			}
			if ("主线—拜入师门s3".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//进入战斗
				List<String> monsterList = Lists.newArrayList("新手木桩","新手木桩");
				FightManager.activeBoosGoFight(chara, monsterList, false);
				if("phyPower".equals(shimenTask.flag)) {
					//选择了物攻
					GameObjectChar.send(new MSG_PLAY_INSTRUCTION(), 43);
				}else if("magPower".equals(shimenTask.flag)) {
					//选择了法攻
					GameObjectChar.send(new MSG_PLAY_INSTRUCTION(), 42);
				}
				//关闭对话框
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			if("主线—拜入师门s4".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s5";
				closeAndCreateTaskShiMen(chara);
				return;
			}
			if("主线—拜入师门s5".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s6";
				closeAndCreateTaskShiMen(chara);
				return;
			}
			if("主线—拜入师门s6".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s7";
				closeAndCreateTaskShiMen(chara);
				//走失的孩子
				Vo_APPEAR npc = new Vo_APPEAR();
				npc.mapid = 5000;
				npc.id = 55555555;
				npc.x = 105;
				npc.y = 136;
				npc.icon = 6018;
				npc.type = 2;
				npc.org_icon = 6201;
				npc.portrait = 6201;
				npc.name = "走失的孩子";
				npc.dir = 3;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}
			if("主线—拜入师门s7".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s8";
				closeAndCreateTaskShiMen(chara);
				gameObjectChar.sendOne(new MSG_DISAPPEAR(), 55555555);
				Vo_APPEAR npc = new Vo_APPEAR();
				npc.mapid = 5000;
				npc.id = 66666666;
				npc.x = 65;
				npc.y = 134;
				npc.icon = 6213;
				npc.type = 2;
				npc.org_icon = 6213;
				npc.portrait = 6213;
				npc.name = "神秘蒙面人";
				npc.dir = 3;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}
			if("主线—拜入师门s8".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				if(shimenTask.task_extra_para.equals("0")) {
					if(!chara.isFight) {
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("新手蒙面"), false);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s9";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					//npc消失
					GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}
			
			if("主线—拜入师门s9".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				String[] zhanglao = new String[] {"金光长老","云霄长老","玉柱长老","斗阙长老","白骨长老"};
				String[] polar = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s10";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), polar[chara.polar-1],zhanglao[chara.polar-1]));
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				//剧情对话消失
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			if("主线—拜入师门s10".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				String[] tongzi = {"赤霞童子","云霄童子", "碧玉童子", "水灵童子", "彩云童子"};
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s11";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), tongzi[chara.polar-1]));
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				//剧情对话消失
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			if("主线—拜入师门s11".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s12";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				//剧情对话消失
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			if("主线—拜入师门s12".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s13";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_zhanglao[chara.polar-1]));
				GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
				//剧情对话消失
				Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
				GameObjectChar.send(new M45056_0(), vo_45056_4);
				GameObjectChar.send(new M4155_0(), 0);
				return;
			}
			if("主线—拜入师门s13".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				shimenTask.task_state = "3";
				//得到守护
				Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
				vo_9129_0.notify = 20002;
				vo_9129_0.para = "000FFFFFFF0FFF0FF0000F0";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
				//图标
				gameObjectChar.sendOne(new MSG_PLAY_INSTRUCTION(), 9);
				//如果存在守护的话就直接下一个任务
				if(chara.listshouhu != null && !chara.listshouhu.isEmpty()) {
					ShouHu shouHu = chara.listshouhu.get(0);
					shouHu.listShouHuShuXing.get(0).salary = chara.canzhanshouhunumber;
					++chara.canzhanshouhunumber;
					shouHu.listShouHuShuXing.get(0).nil = 1;
					GameObjectChar.send(new M12016_0(), Lists.newArrayList(shouHu));
					//设置下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s14";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_zhanglao[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
				}else {
					//打开守护指引
					gameObjectChar.sendOne(new MSG_PLAY_INSTRUCTION(), 25);
				}
				return;
			}
			if("主线—拜入师门s14".equals(chara.current_task) && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
				//应约挑战
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s15";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), att_name[chara.polar-1]));
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			if("主线—拜入师门s15".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				shimenTask.task_state = "3";
				if("0".equals(shimenTask.task_extra_para)) {
					//进入战斗
					if(!chara.isFight) {
						shimenTask.task_extra_para = "1";
						FightObjectInfo fight = GameData.that.baseFightObjectService.
								findOneByName("新手外门弟子");
						int[] att_icon = new int[] {6004,6001,7002,7003,7005};
						int[] wea_icon = new int[] {1102,1135,1146,1124,1113};
						String[] att_name = {"金光洞", "云霄洞", "玉柱洞", "斗阙宫", "白骨洞" };
						FightObject fightObject = new FightObject(fight, false, 4);
						fightObject.setStr(att_name[chara.polar-1]+"外门弟子");
						fightObject.setOrg_icon(att_icon[chara.polar-1]);
						fightObject.setWeapon_icon(1102);
						fightObject.setGuaiwulevel(20);
						fightObject.setFid(88888888);
						List<FightObject> fightObjects = new ArrayList<>();
						fightObjects.add(fightObject);
						fightObject = new FightObject(fight, false, 4);
						fightObject.setStr(att_name[chara.polar-1]+"外门弟子");
						fightObject.setOrg_icon(att_icon[chara.polar-1]);
						fightObject.setWeapon_icon(wea_icon[chara.polar-1]);
						fightObject.setGuaiwulevel(13);
						fightObject.setFid(99999999);
						fightObjects.add(fightObject);
						FightManager.activeBoosGoFight(chara, fightObjects, true);
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else {
					//任务奖励
					GameUtil.renwujiangli(chara);
					//弹出召唤
					Map<String,Object> obj = new LinkedHashMap<String, Object>();
					obj.put("id:int", 6002);
					GameObjectChar.send(new CommonCmd(0xA0A4), obj);
					chara.current_task = "主线—拜入师门s16";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
					
				}
				return;
			}
			if("主线—拜入师门s16".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//任务奖励
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s17";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			if("主线—拜入师门s17".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//任务奖励
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s18";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShiMen(chara, renwu);
				GameObjectChar.send(new MSG_PLAY_INSTRUCTION(), 16);
				return;
			}
			if("主线—拜入师门s18".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//任务奖励
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s19";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			if("主线—拜入师门s19".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s20";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			if("主线—拜入师门s20".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				if("0".equals(shimenTask.task_extra_para)) {
					if(!chara.isFight) {
						shimenTask.task_state = "3";
						FightObject fightObject = new FightObject(GameData.that.baseFightObjectService.
								findOneByName("主线桃精"));
						fightObject.fid = 88888888;
						FightObject fightObject2 = new FightObject(GameData.that.baseFightObjectService.
								findOneByName("主线柳鬼"));
						fightObject2.fid = 99999999;
						FightManager.activeBoosGoFight(chara, Lists.newArrayList(fightObject,fightObject2), true);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else {
					//完成挑战
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s21";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
				}
				return;
			}
			if("主线—拜入师门s21".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//创建npc
				Vo_APPEAR npc = new Vo_APPEAR();
				npc.mapid = 6000;
				npc.id = 66666666;
				npc.x = 26;
				npc.y = 35;
				npc.icon = 6211;
				npc.type = 2;
				npc.org_icon = 6211;
				npc.portrait = 6211;
				npc.name = "赤羽鸟怪";
				npc.dir = 4;
				gameObjectChar.sendOne(new M65529_0(), npc);
				//任务奖励
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s22";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			if("主线—拜入师门s22".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//进入战斗
				shimenTask.task_state = "4";
				if("0".equals(shimenTask.task_extra_para)) {
					if(!chara.isFight) {
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪"), true);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
						gameObjectChar.flag = "主线赤羽鸟怪";
					}
				}else {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s23";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
					Vo_APPEAR npc = new Vo_APPEAR();
					npc.mapid = 6000;
					npc.id = 66666666;
					npc.x = 32;
					npc.y = 12;
					npc.icon = 6211;
					npc.type = 2;
					npc.org_icon = 6211;
					npc.portrait = 6211;
					npc.name = "赤羽鸟怪";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			if("主线—拜入师门s23".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//进入战斗
				shimenTask.task_state = "3";
				if("0".equals(shimenTask.task_extra_para)) {
					if(!chara.isFight) {
						gameObjectChar.flag = "主线赤羽鸟怪s23";
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪"), true);
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
				}else {
					//任务奖励
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s24";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShiMen(chara, renwu);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}
			if("主线—拜入师门s24".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s25";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShiMen(chara, renwu);
				gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
				//弹出召唤
				Map<String,Object> obj = new LinkedHashMap<String, Object>();
				obj.put("id:int", 6022);
				GameObjectChar.send(new CommonCmd(0xA0A4), obj);
				return;
			}
			if("主线—拜入师门s25".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s26";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			if("主线—拜入师门s26".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//下一任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—拜入师门s27";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShiMen(chara, renwu);
				//樵夫
				Vo_APPEAR npc = new Vo_APPEAR();
				npc.mapid = 11000;
				npc.id = 66666666;
				npc.x = 14;
				npc.y = 64;
				npc.icon = 6035;
				npc.type = 2;
				npc.org_icon = 6035;
				npc.portrait = 6035;
				npc.name = "樵夫";
				npc.dir = 4;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}
			//铲除蟒精
			if("主线—拜入师门s28".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//进入战斗
				if("0".equals(shimenTask.task_extra_para)) {
					if(!chara.isFight) {
						shimenTask.task_state = "3";
						gameObjectChar.flag = "主线蟒精s28";
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线蟒精","主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪","主线赤羽鸟怪"), true);
					}
				}else {
					//下一个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—拜入师门s29";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
					closeAndCreateTaskShiMen(chara, renwu);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}
			if("主线—拜入师门s29".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//创建任务
				GameUtil.renwujiangli(chara);
				if(chara.level>=20) {
					chara.current_task = "主线—拜入师门s30_2";
				}else {
					chara.current_task = "主线—拜入师门s30_1";
				}
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				if(chara.level>=20) {
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					//弹出新手礼包
					GameUtil.openDlg("RookieGiftDlg");
				}
				closeAndCreateTaskShiMen(chara, renwu);
				return;
			}
			
			if("主线—拜入师门s30_2".equals(chara.current_task)  && shimenTask != null && Integer.valueOf(shimenTask.task_state)>0) {
				//查询本次任务
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				boolean isGetPet = false;
				if(renwu.getReward().indexOf("#宝宝") != -1 || renwu.getReward().indexOf("#神兽") != -1 
						|| renwu.getReward().indexOf("#变异") != -1) {
					String[] split = renwu.getReward().split(",");
					for(String sp:split) {
						String[] info = sp.split("\\#");
						if("宝宝".equals(info[1]) || "神兽".equals(info[1]) || "变异".equals(info[1])) {
							//领取宠物
							Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
							vo_9129_2.notify = ClientButtonIdConst.NOTICE_FETCH_BONUS;
							vo_9129_2.para = "#I1|"+info[0]+"("+info[1]+")$1#I";
							GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2);
							isGetPet = true;
							break;
						}
					}
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				if(!isGetPet) {
					//如果没有宠物领取的话就直接下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s1";
					renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
					closeAndCreateTaskShanYu(chara, renwu);
					GameUtilRenWu.removeTask("主线—拜入师门", chara);
				}
				return;
			}
			//主线—山雨欲来
			Vo_61553_0 shanyuTask = chara.taskMap.get("主线—山雨欲来");
			if("主线—山雨欲来s1".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s2";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}else if("主线—山雨欲来s2".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s3";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}
			else if("主线—山雨欲来s3".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s4";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}
			else if("主线—山雨欲来s4".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s5";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}
			else if("主线—山雨欲来s5".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s6";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}
			else if("主线—山雨欲来s6".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(!chara.isFight) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else{
							gameObjectChar.flag = "主线—山雨欲来s6";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线恶霸","主线恶霸","主线恶霸","主线恶霸","主线恶霸"), true);
						}
					}
					//正在战斗的话就关闭对话
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s7";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					//妖风
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6140;
					npc.mapid = 8000;
					npc.id = 66666666;
					npc.x = 39;
					npc.y = 22;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "妖风";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			else if("主线—山雨欲来s7".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
						GameUtil.sendMeTips("不可组队完成！");
					}else if(!chara.isFight) {
						//进入战斗
						gameObjectChar.flag = "主线—山雨欲来s7";
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线妖风","主线妖风","主线妖风","主线妖风","主线妖风"), true);
					}
					//正在战斗则把他关闭了战斗
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s8";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
				}
				return;
			}else if("主线—山雨欲来s8".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s9";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}
			else if("主线—山雨欲来s9".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
						GameUtil.sendMeTips("不可组队完成");
						return;
					}
					if(!chara.isFight) {
						gameObjectChar.flag = "主线—山雨欲来s9";
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线琵琶精","主线妖风","主线妖风","主线妖风","主线妖风"), true);
					}
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s10";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
				}
				return;
			}else if("主线—山雨欲来s10".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s11";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}else if("主线—山雨欲来s11".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s12";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_shizun[chara.polar-1]));
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}else if("主线—山雨欲来s12".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s13";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				renwu.setTaskPrompt(String.format(renwu.getTaskPrompt(), GameCommonUtil.shimen_tongzi[chara.polar-1]));
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}
			else if("主线—山雨欲来s13".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s14";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}else if("主线—山雨欲来s14".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				//下个任务
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s15";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				//显示冤魂
				Vo_APPEAR npc = new Vo_APPEAR();
				int icon = 6141;
				npc.mapid = 9000;
				npc.id = 66666666;
				npc.x = 14;
				npc.y = 42;
				npc.icon = icon;
				npc.type = 2;
				npc.org_icon = icon;
				npc.portrait = icon;
				npc.name = "恶霸怨魂";
				npc.dir = 4;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}
			else if("主线—山雨欲来s15".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
						GameUtil.sendMeTips("不可组队完成！");
					}else if(!chara.isFight) {
						gameObjectChar.flag = "主线—山雨欲来s15";
						FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂"), true);
					}
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					//下个任务
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s16";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					//北海乌龙
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6117;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 13;
					npc.y = 16;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "北海乌龙";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}else if("主线—山雨欲来s16".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s17";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				Vo_APPEAR npc = new Vo_APPEAR();
				int icon = 6141;
				npc.mapid = 9000;
				npc.id = 66666666;
				npc.x = 14;
				npc.y = 42;
				npc.icon = icon;
				npc.type = 2;
				npc.org_icon = icon;
				npc.portrait = icon;
				npc.name = "恶霸怨魂";
				npc.dir = 4;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}else if("主线—山雨欲来s17".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s18";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				Vo_APPEAR npc = new Vo_APPEAR();
				int icon = 6117;
				npc.mapid = 9000;
				npc.id = 66666666;
				npc.x = 13;
				npc.y = 16;
				npc.icon = icon;
				npc.type = 2;
				npc.org_icon = icon;
				npc.portrait = icon;
				npc.name = "北海乌龙";
				npc.dir = 4;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}
			else if("主线—山雨欲来s18".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(!chara.isFight) {
						if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
							GameUtil.sendMeTips("不可组队完成！");
						}else {
							gameObjectChar.flag = "主线—山雨欲来s18";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线北海乌龙"), true);
						}
					}
					//正在战斗的话就关闭对话
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s19";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					Vo_APPEAR npc = new Vo_APPEAR();
					int icon = 6175;
					npc.mapid = 9000;
					npc.id = 77777777;
					npc.x = 18;
					npc.y = 44;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "雉鸡精";
					npc.dir = 2;
					gameObjectChar.sendOne(new M65529_0(), npc);
					npc = new Vo_APPEAR();
					icon = 6141;
					npc.mapid = 9000;
					npc.id = 66666666;
					npc.x = 14;
					npc.y = 42;
					npc.icon = icon;
					npc.type = 2;
					npc.org_icon = icon;
					npc.portrait = icon;
					npc.name = "恶霸怨魂";
					npc.dir = 4;
					gameObjectChar.sendOne(new M65529_0(), npc);
				}
				return;
			}
			else if("主线—山雨欲来s19".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
						GameUtil.sendMeTips("不可组队完成！");
					}else {
						if(!chara.isFight) {
							gameObjectChar.flag = "主线—山雨欲来s19";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线雉鸡精","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂","主线恶霸怨魂"), true);
						}
					}
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					GameUtil.renwujiangli(chara);
					chara.current_task = "主线—山雨欲来s20";
					Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
					closeAndCreateTaskShanYu(chara, renwu);
					GameObjectChar.send(new MSG_DISAPPEAR(), 66666666);
					GameObjectChar.send(new MSG_DISAPPEAR(), 77777777);
				}
				return;
			}
			else if("主线—山雨欲来s20".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s21";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				return;
			}else if("主线—山雨欲来s21".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				GameUtil.renwujiangli(chara);
				chara.current_task = "主线—山雨欲来s22";
				Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
				closeAndCreateTaskShanYu(chara, renwu);
				//虎妖
				Vo_APPEAR npc = new Vo_APPEAR();
				int icon = 6121;
				npc.mapid = 5000;
				npc.id = 66666666;
				npc.x = 50;
				npc.y = 38;
				npc.icon = icon;
				npc.type = 2;
				npc.org_icon = icon;
				npc.portrait = icon;
				npc.name = "虎妖";
				npc.dir = 4;
				gameObjectChar.sendOne(new M65529_0(), npc);
				return;
			}else if("主线—山雨欲来s22".equals(chara.current_task)  && shanyuTask != null && Integer.valueOf(shanyuTask.task_state)>0) {
				if("0".equals(shanyuTask.task_extra_para)) {
					if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam)) {
						GameUtil.sendMeTips("不可组队完成！");
					}else {
						if(!chara.isFight) {
							gameObjectChar.flag = "主线—山雨欲来s22";
							FightManager.activeBoosGoFight(chara, Lists.newArrayList("主线虎妖","主线虎妖","主线虎妖","主线虎妖","主线虎妖"), true);
						}
					}
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}else {
					//到这里新手任务就结束了
					GameUtil.renwujiangli(chara);
					chara.current_task = "";
					GameUtilRenWu.removeTask("主线—山雨欲来", chara);
					gameObjectChar.sendOne(new MSG_DISAPPEAR(), 66666666);
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
				}
				return;
			}
			Vo_61553_0 zuRenFxl = chara.getTaskMap().get("助人为乐—打抱不平");
			if(zuRenFxl != null) {
				if("助人为乐—打抱不平s1".equals(zuRenFxl.currentTask)) {
					if("0".equals(zuRenFxl.task_extra_para)) {
						if(!chara.isFight) {
							GameCommonUtil.dialogOk("#R本场战斗会有死亡惩罚，请小心！");
							gameObjectChar.flag = "助人为乐—打抱不平s1";
							FightManager.goFightDynamicLevel(chara, Lists.newArrayList("助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒","助人为乐打抱不平:醉酒暴徒"), false);
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}else if("1".equals(zuRenFxl.task_extra_para)) {
						GameUtilRenWu.createTask(chara, "助人为乐—打抱不平s2");
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}else if("助人为乐—打抱不平s2".equals(zuRenFxl.currentTask)) {
					if("0".equals(zuRenFxl.task_extra_para)) {
						if(!chara.isFight) {
							GameCommonUtil.dialogOk("#R本场战斗会有死亡惩罚，请小心！");
							gameObjectChar.flag = "助人为乐—打抱不平s2";
							FightManager.goFightDynamicLevel(chara, Lists.newArrayList("助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客","助人为乐打抱不平:无名剑客"), false);
						}
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}else if("1".equals(zuRenFxl.task_extra_para)){
						GameUtilRenWu.createTask(chara, "助人为乐—打抱不平s3");
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
					}
					return;
				}else if("助人为乐—打抱不平s3".equals(zuRenFxl.currentTask)) {
					GameUtilRenWu.removeTask("助人为乐—打抱不平", chara);
					GameCommonUtil.dialogOk("已帮助了#R天墉城#n中的居民，回去找#Y白邦芒#n领取犒赏。");
					GameUtilRenWu.createTask(chara, "助人为乐—领取犒赏");
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					return;
				}
			}
			
			//白帮忙-乞丐
			Vo_61553_0 zuRenFpjk = chara.getTaskMap().get("助人为乐—扶危救困");
			if(zuRenFpjk != null) {
				if("助人为乐—扶危救困s1".equals(zuRenFpjk.currentTask)) {
					GameUtilRenWu.createTask(chara, "助人为乐—扶危救困s2");
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					return;
				}else if("助人为乐—扶危救困s2".equals(zuRenFpjk.currentTask)) {
					GameUtilRenWu.createTask(chara, "助人为乐—扶危救困s3");
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					return;
				}else if("助人为乐—扶危救困s3".equals(zuRenFpjk.currentTask)) {
					GameUtilRenWu.removeTask("助人为乐—扶危救困", chara);
					GameCommonUtil.dialogOk("已帮助了#R天墉城#n中的居民，回去找#Y白邦芒#n领取犒赏。");
					GameUtilRenWu.createTask(chara, "助人为乐—领取犒赏");
					//剧情对话消失
					Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
					GameObjectChar.send(new M45056_0(), vo_45056_4);
					GameObjectChar.send(new M4155_0(), 0);
					return;
				}
			}
		} else if (3 == type) {
			return;
		}
		Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
		GameObjectChar.send(new M45056_0(), vo_45056_4);
	}

	// 这里是分配一级时候的装备
	public void geizhuangb(Chara chara) {
		ZhuangbeiInfo zhuangb = new ZhuangbeiInfo();
		List<ZhuangbeiInfo> byAttrib = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService.findByAttrib(1);
		for (int i = 0; i < byAttrib.size(); ++i) {
			// 根据门派分配武器
			if (byAttrib.get(i).getMetal() == chara.polar && byAttrib.get(i).getAmount() == 1) {
				zhuangb = byAttrib.get(i);
				GameUtil.huodezhuangbei(chara, zhuangb, 0);
				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "你获得了1把#R" + zhuangb.getStr() + "#n。";
				vo_20481_0.time = 1562987118;
				GameObjectChar.send(new M20481_0(), vo_20481_0);
				Vo_20480_0 vo_20480_0 = new Vo_20480_0();
				vo_20480_0.msg = "你获得了#R260#n点经验。";
				vo_20480_0.time = 1562593376;
				GameObjectChar.send(new M20480_0(), vo_20480_0);
				Vo_8165_0 vo_8165_0 = new Vo_8165_0();
				vo_8165_0.msg = "你获得了#R260#n经验、1把#R" + zhuangb.getStr() + "#n。";
				vo_8165_0.active = 0;
				GameObjectChar.send(new M8165_0(), vo_8165_0);
				Vo_40964_0 vo_40964_0 = new Vo_40964_0();
				vo_40964_0.type = 1;
				vo_40964_0.name = zhuangb.getStr().toString();
				vo_40964_0.param = "98107";
				vo_40964_0.rightNow = 1;
				GameObjectChar.send(new M40964_0(), vo_40964_0);
				Vo_40965_0 vo_40965_0 = new Vo_40965_0();
				vo_40965_0.guideId = 19;
				GameObjectChar.send(new M40965_0(), vo_40965_0);
			}
		}
	}
	
	
	private void closeAndCreateTask(Chara chara) {
		//创建主线任务
		GameUtilRenWu.createZhuXianFuShengRuoMengTask(chara, 
				GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task));
		//剧情对话消失
		Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
		GameObjectChar.send(new M45056_0(), vo_45056_4);
		GameObjectChar.send(new M4155_0(), 0);
	}
	
	/**
	 * a主线拜入师门
	 * @param chara
	 */
	private void closeAndCreateTaskShiMen(Chara chara) {
		//创建主线任务
		GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, 
				GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task));
		//剧情对话消失
		Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
		GameObjectChar.send(new M45056_0(), vo_45056_4);
		GameObjectChar.send(new M4155_0(), 0);
	}
	
	private void closeAndCreateTaskShiMen(Chara chara, Renwu renwu) {
		//创建主线任务
		GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
		//剧情对话消失
		Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
		GameObjectChar.send(new M45056_0(), vo_45056_4);
		GameObjectChar.send(new M4155_0(), 0);
	}
	
	private void closeAndCreateTaskShanYu(Chara chara, Renwu renwu) {
		//创建主线任务
		GameUtilRenWu.createZhuXianShanYuYuLaiTask(chara, renwu);
		//剧情对话消失
		Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
		GameObjectChar.send(new M45056_0(), vo_45056_4);
		GameObjectChar.send(new M4155_0(), 0);
	}
	
	
	@Override
	public int cmd() {
		return 45057;
	}
}