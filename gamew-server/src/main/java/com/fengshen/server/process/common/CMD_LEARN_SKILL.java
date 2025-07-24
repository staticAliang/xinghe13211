package com.fengshen.server.process.common;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Renwu;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.data.vo.Vo_45056_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M32747_0;
import com.fengshen.server.data.write.M4155_0;
import com.fengshen.server.data.write.M45056_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.game.GameUtilRenWu;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 升级技能
 * 
 *
 */
@Service
@Slf4j
public class CMD_LEARN_SKILL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int id = GameReadTool.readInt(buff);
		int skill_no = GameReadTool.readShort(buff);
		int up_level = GameReadTool.readShort(buff);
		log.info("升级技能， skill_no={},up_level={}",skill_no,up_level);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		// 判断等级是否可以学习五法
		if (skill_no == 15 || skill_no == 25 || skill_no == 35 || skill_no == 65 || skill_no == 75 || skill_no == 85
				|| skill_no == 114 || skill_no == 125 || skill_no == 135 || skill_no == 165 || skill_no == 175
				|| skill_no == 185 || skill_no == 214 || skill_no == 225 || skill_no == 235) {
			if (chara.isFinish100Task == 0) {
				GameUtil.sendTips("请完成百级拜师");
				return;
			}
		}
		//如果要升级的等级不等于1和10，那就直接返回
		if(up_level != 1 && up_level != 10) {
			GameUtil.sendMeTips("单次升级只能是1或者是10");
			return;
		}
		Petbeibao petbeibao = null;
		for (Petbeibao pet : chara.pets) {
			if (pet.id == id) {
				petbeibao = pet;
			}
		}
		boolean isPet = false;
		if (petbeibao != null) {
			if(skill_no == 254 || skill_no == 260 || skill_no == 259 || skill_no == 31 || 
					skill_no == 81 || skill_no == 131
					|| skill_no == 181 || skill_no == 231) {
				isPet = true;
				//学习宠物学习技能
				String name = "如意圈";
				JiNeng tianji = null;
				if (petbeibao.tianji == null) {
					petbeibao.tianji = new ArrayList<>();
				}
				for (JiNeng jn : petbeibao.tianji) {
					if (jn.getSkill_no() == skill_no) {
						// 找到该技能
						tianji = jn;
						break;
					}
				}
				if (tianji == null) {
					GameUtil.sendMeTips("宠物还尚未学习该技能！");
					return;
				} else if (StringUtils.isNullOrEmpty(chara.getPartyName())) {
					GameUtil.sendMeTips("你还未加入帮派！");
					return;
				}
				if (skill_no == 259) {
					name = "乾坤罩";
				} else if (skill_no == 260) {
					name = "神龙罩";
				} else if (skill_no == 31) {
					name = "天生神力";
				} else if (skill_no == 81) {
					name = "拔苗助长";
				} else if (skill_no == 131) {
					name = "防微杜渐";
				} else if (skill_no == 181) {
					name = "十万火急";
				} else if (skill_no == 231) {
					name = "鞭长莫及";
				}
				int[] prePetPartySkillCost = GameCommonUtil.getPetPartySkillCost(tianji.skill_level);
				// 弹出确认框
				GameUtil.confirm(chara,
						"是否确定消耗" + GameCommonUtil.getMoneyDes(prePetPartySkillCost[0] * up_level) + "文钱和#R"
								+ prePetPartySkillCost[1] * up_level + "帮贡#n为#R" + name + "#n提升#R" + up_level + "#n级？",
						"upPetPartySkillLevel_" + id + "_" + skill_no + "_" + up_level + "_"
								+ prePetPartySkillCost[0] * up_level + "_" + prePetPartySkillCost[1] * up_level);
			} else {
				GameUtil.sendMeTips("客官，技能正在研发中！");
				return;
			}
		}
		//如果不是宠物学习
		if(!isPet) {
			// 如果已经学过这个技能了，就直接用
			JiNeng sjjiNeng = new JiNeng();
			for (JiNeng jiNeng : chara.jiNengList) {
				if (jiNeng.skill_no == skill_no) {
					sjjiNeng = jiNeng;
				}
			}
			if(chara.taskMap.get("主线—拜入师门") != null) {
				if(chara.taskMap.get("主线—拜入师门").currentTask.equals("主线—拜入师门s2_1")
					|| chara.taskMap.get("主线—拜入师门").currentTask.equals("主线—拜入师门s2")) {
					int step = Integer.valueOf(chara.taskMap.get("主线—拜入师门").task_state);
					int[] skill = new int[] {11,61,110,161,210};
					//这里强制设置
					int minLevel = 10;
					String flag = "";
					if(step == 1) {
						if(sjjiNeng.skill_no == 0) {
							skill_no = 501;
						}
						flag = "phyPower";
					}else if(step == 2) {
						//法术攻击
						if(sjjiNeng.skill_no == 0) {
							skill_no = skill[chara.polar-1];
						}
						flag = "magPower";
					}else {
						minLevel = 16;
					}
					if(sjjiNeng.skill_level >= minLevel) {
						GameUtil.renwujiangli(chara);
						chara.current_task = "主线—拜入师门s3";
						//创建主线任务
						Renwu renwu = GameData.that.baseRenwuService.findOneByCurrentTask(chara.current_task);
						renwu.setTaskPrompt("向#P"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"|E=【主线】我已习得技能#P复命");
						GameUtilRenWu.createZhuXianBaiRuShiMenTask(chara, renwu);
						chara.taskMap.get("主线—拜入师门").flag = flag;
						//剧情对话消失
						Vo_45056_0 vo_45056_4 = GameUtil.a45056(chara);
						GameObjectChar.send(new M45056_0(), vo_45056_4);
						GameObjectChar.send(new M4155_0(), 0);
						GameUtil.sendMeTips("你已完成道法学习,请速向#R"+GameCommonUtil.shimen_tongzi[chara.polar-1]+"#n回复吧！");
					}
				}
			}
			// 意味着第一次学技能
			if (sjjiNeng.skill_no == 0) {
				JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(skill_no);
				if (jsonObject == null) {
					return;
				}
				if(!GameCommonUtil.isAvaliableLearnSkill(gameObjectChar,jsonObject,skill_no, sjjiNeng)) {
					return;
				}
				int levelUp = up_level;
				// 学习响应等级共需要花费的潜能
				int cash = 0;
				if (sjjiNeng.skill_level + up_level > sjjiNeng.skill_attrib) {
					up_level = sjjiNeng.skill_attrib - sjjiNeng.skill_level;
				}
				for (int i = 0; i < levelUp; ++i) {
					int[] blueAndPointsLan = PetAndHelpSkillUtils.getBlueAndPointsLan(skill_no,
							sjjiNeng.skill_level + i);
					cash += blueAndPointsLan[1];
				}
				if (cash > chara.pot && skill_no != 302 && skill_no != 301) {
					GameUtil.sendMeTips("潜能不足，无法学习该技能");
					return;
				}
				// 设置技能编号
				sjjiNeng.skill_no = skill_no;
				sjjiNeng.skill_attrib1 = Integer.parseInt((String) jsonObject.get("skill_attrib"));
				int maxSkill = PetAndHelpSkillUtils.getMaxSkill(chara.level);
				sjjiNeng.skill_attrib = maxSkill;
				sjjiNeng.skill_level = 0 + levelUp;
				int[] blueAndPointsLan = PetAndHelpSkillUtils.getBlueAndPointsLan(skill_no, sjjiNeng.skill_level);
				sjjiNeng.level_improved = 0;
				sjjiNeng.skill_mana_cost = blueAndPointsLan[0];
				sjjiNeng.skill_nimbus = 42949672;
				sjjiNeng.skill_disabled = 0;
				sjjiNeng.range = PetAndHelpSkillUtils.skillNummax(skill_no, sjjiNeng.skill_level);
				sjjiNeng.max_range = PetAndHelpSkillUtils.skillNummax(skill_no, sjjiNeng.skill_attrib);
				int[] ints = PetAndHelpSkillUtils.skillNum(jsonObject, sjjiNeng.skill_level);
				sjjiNeng.skillRound = ints[1];
				sjjiNeng.count1 = 1;
				sjjiNeng.s1 = "pot";
				sjjiNeng.s2 = blueAndPointsLan[1];
				sjjiNeng.isTempSkill = 0;
				chara.jiNengList.add(sjjiNeng);
				if (skill_no == 301 || skill_no == 302) {
					GameUtil.a65511(gameObjectChar);
					sjjiNeng.s1 = "voucher_or_cash";
					if (chara.use_money_type < cash) {
						chara.cash -= cash;
					} else {
						chara.use_money_type -= cash;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
				} else {
					Chara chara2 = chara;
					chara2.pot -= cash;
				}
				ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), vo_65527_0);
				List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(chara);
				GameObjectChar.send(new M32747_0(), vo_32747_0List);
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "#Y"+jsonObject.getString("skillName")+"#n技能等级提升到了#R" + sjjiNeng.skill_level + "#n级！";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
			} else {
				JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(skill_no);
				if (jsonObject == null) {
					return;
				}
				if(!GameCommonUtil.isAvaliableLearnSkill(gameObjectChar,jsonObject,skill_no, sjjiNeng)) {
					return;
				}
				int attrib = PetAndHelpSkillUtils.getMaxSkill(chara.level);
				sjjiNeng.skill_attrib = attrib;
				int levelUp2 = up_level;
				int cash2 = 0;
				if (sjjiNeng.skill_level + up_level > sjjiNeng.skill_attrib) {
					up_level = sjjiNeng.skill_attrib - sjjiNeng.skill_level;
				}
				for (int i = 0; i < levelUp2; ++i) {
					int[] blueAndPointsLan = PetAndHelpSkillUtils.getBlueAndPointsLan(skill_no,
							sjjiNeng.skill_level + i);
					cash2 += blueAndPointsLan[1];
				}
				if (cash2 > chara.pot && skill_no != 302 && skill_no != 301) {
					GameUtil.sendMeTips("潜能不足，无法学习该技能");
					return;
				}
				if (skill_no == 301 || skill_no == 302) {
					if (chara.cash < cash2) {
						GameUtil.sendMeTips("金钱不足。");
						return;
					}
				}
				sjjiNeng.skill_attrib1 = Integer.parseInt((String) jsonObject.get("skill_attrib"));
				sjjiNeng.skill_level += up_level;
				//这里要加上额外技能
				int skillLevel = sjjiNeng.skill_level+sjjiNeng.level_improved;
				sjjiNeng.range = PetAndHelpSkillUtils.skillNummax(skill_no, skillLevel);
				int[] ints2 = PetAndHelpSkillUtils.skillNum(jsonObject, skillLevel);
				sjjiNeng.skillRound = ints2[1];
				int[] blueAndPointsLan2 = PetAndHelpSkillUtils.getBlueAndPointsLan(skill_no, skillLevel);
				sjjiNeng.skill_mana_cost = blueAndPointsLan2[0];
				sjjiNeng.s2 = blueAndPointsLan2[1];
				if (skill_no == 301 || skill_no == 302) {
					GameUtil.a65511(gameObjectChar);
					sjjiNeng.s1 = "voucher_or_cash";
					if (chara.use_money_type < cash2) {
						chara.cash -= cash2;
					} else {
						chara.use_money_type -= cash2;
					}
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
				} else {
					chara.pot -= cash2;
				}
				ListVo_65527_0 vo_65527_0 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), vo_65527_0);
				List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(chara);
				GameObjectChar.send(new M32747_0(), vo_32747_0List);
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "#Y"+jsonObject.getString("skillName")+"#n技能等级提升到了#R" + skillLevel + "#n级！";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
			}
		}
		ListVo_65527_0 vo_65527_2 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), vo_65527_2);
	}

	@Override
	public int cmd() {
		return 8308;
	}
}