package com.fengshen.server.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fengshen.db.domain.Npc;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.Renwu;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.game.PetAttributesUtils;
import com.fengshen.server.data.vo.VO_MSG_PET_UPGRADE_PRE_INFO;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.data.vo.task.Vo_AUTO_WALK;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M_MSG_PET_UPGRADE_PRE_INFO;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.task.MSG_AUTO_WALK;
import com.fengshen.server.data.write.task.MSG_TASK_PROMPT;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.fight.FightManager;

public class PetFlyMgr {
	public static final int MAX_FLY = 3;
	public static final String zhanDouNpcName = "米兰仙子";
	public static final String YinLuNpcName = "灵兽异人";
	public static final List<String> guaiwuNames = Arrays.asList(new String[] { "武学道宠", "物攻道宠", "法攻道宠" });
	public static final String itemStr = "2阶骑宠灵魄#3,驯兽诀#1,萦香丸#20,聚灵丹#20";

	public enum UPGRAD_TYPE {
		UPGRAD_TYPE_NO, UPGRAD_TYPE_UPING, UPGRAD_TYPE_FINSH,
	};

	public static boolean checkEnough(Chara chara, List<Goods> goodsList) {
		for (Goods goods : goodsList) {
			if (!GameUtil.checkGoods(chara, goods.goodsInfo.str, 1)) {
				return false;
			}
		}
		return true;
	}

	// 判断是否当前飞升的宠物已经通过考验
	public static boolean isTongGuoKaoYan(Chara chara) {
		if (chara.flyPetID == 0)
			return false;
		// 如果飞升宠物ID和当前参战宠物不一样，就返回false
		if (chara.flyPetID != chara.chongwuchanzhanId)
			return false;
		Petbeibao pet = chara.getPetByID(chara.flyPetID);
		if (null == pet)
			return false;
		if (pet.petShuXing.get(0).currentFlyFightIndex >= MAX_FLY)
			return true;
		return false;
	}

	// 计算属性
	public static int[] calcShuXing(Petbeibao pet) {
		PetShuXing shuXing = pet.petShuXing.get(0);
		int[] result = new int[5];
		int rank = shuXing.pet_mag_shape;
		int pet_life_shape = 100;
		int pet_mag_shape = 100;
		int pet_mana_shape = 100;
		int pet_phy_shape = 100;
		int pet_speed_shape = 100;

		if (rank == DefinedConst.PET_RANK.PET_RANK_ELITE.ordinal()) { // 变异
			pet_life_shape = (int) (shuXing.pet_mana_shape * (0.25D));
			pet_mana_shape = (int) (shuXing.pet_speed_shape * (0.25D));
			pet_speed_shape = (int) (shuXing.pet_phy_shape * (0.20D));
			pet_mag_shape = (int) (shuXing.pet_mag_shape * (0.25D)) + 30;
			pet_phy_shape = (int) (shuXing.rank * (0.25D)) + 30;

		} else if (rank == DefinedConst.PET_RANK.PET_RANK_EPIC.ordinal()) {// 神兽
			pet_life_shape = (int) (shuXing.pet_mana_shape * (0.3D));
			pet_mana_shape = (int) (shuXing.pet_speed_shape * (0.3D));
			pet_speed_shape = (int) (shuXing.pet_phy_shape * (0.25D));
			pet_mag_shape = (int) (shuXing.pet_mag_shape * (0.3D)) + 40;
			pet_phy_shape = (int) (shuXing.rank * (0.3D)) + 40;
		} else {
			pet_life_shape = (int) (shuXing.pet_mana_shape * (0.2D));
			pet_mana_shape = (int) (shuXing.pet_speed_shape * (0.2D));
			pet_speed_shape = (int) (shuXing.pet_phy_shape * (0.15D));
			pet_mag_shape = (int) (shuXing.pet_mag_shape * (0.2D)) + 20;
			pet_phy_shape = (int) (shuXing.rank * (0.2D)) + 20;
		}

		result[0] = pet_life_shape;
		result[1] = pet_mana_shape;
		result[2] = pet_speed_shape;
		result[3] = pet_mag_shape;
		result[4] = pet_phy_shape;
		return result;
	}

	public static void removeFlyTask(Chara chara) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "宠物飞升";
		vo_61553_0.task_desc = "";
		vo_61553_0.task_prompt = "";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = "";
		vo_61553_0.show_name = "";
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "1";
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_TASK_PROMPT(), vo_61553_0);
	}

	public static void onNotifyTask(Chara chara, int taskID) {
		Renwu renwu = GameData.that.baseRenwuService.findById(taskID);
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_prompt = renwu.getTaskPrompt();
		vo_61553_0.task_type = "宠物飞升";
		vo_61553_0.task_desc = renwu.getUncontent();
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = (int) (System.currentTimeMillis() / 1000) + 2 * 60 * 60;
		vo_61553_0.attrib = 1;
		vo_61553_0.reward = renwu.getReward();
		vo_61553_0.show_name = renwu.getShowName();
		vo_61553_0.task_extra_para = "";
		vo_61553_0.task_state = "";
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_TASK_PROMPT(), vo_61553_0);
		// 添加飞升任务
		chara.taskMap.put("宠物飞升", vo_61553_0);

	}

	public static boolean onChosePetFly(Chara chara, int petID) {
		Petbeibao pet = chara.getPetByID(petID);
		if (pet == null) {
			GameUtil.sendMeTips("当前飞升的宠物不存在！");
			return false;
		}
		if(pet.petShuXing.get(0).skill<110) {
			GameUtil.sendMeTips("宠物等级达到110才能飞升");
			return false;
		}
		if (pet.petShuXing.get(0).enchant_nimbus != 2) {
			GameUtil.sendMeTips("请完成宠物点化再来找我");
			return false;
		}
		if (pet.petShuXing.get(0).eclosion_nimbus != 2) {
			GameUtil.sendMeTips("请完成宠物羽化再来找我");
			return false;
		}
		if (pet.petShuXing.get(0).shape < 30000) {
			GameUtil.sendMeTips("宠物亲密度不足30000");
			return false;
		}
		if (pet.petShuXing.get(0).limit_use_time == 1) {
			GameUtil.sendMeTips("你提交的宠物已完成飞升！");
			return false;
		}
		chara.flyPetID = petID;
		pet.petShuXing.get(0).upgrade_level = UPGRAD_TYPE.UPGRAD_TYPE_UPING.ordinal();
		
		pet.petShuXing.get(0).currentFlyFightIndex = 1;
		List<Petbeibao> list = new ArrayList<>();
		list.add(pet);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new MSG_UPDATE_PETS(), list);

		Npc npc = GameData.that.baseNpcService.findOneByName(zhanDouNpcName);
		com.fengshen.db.domain.Map map = (com.fengshen.db.domain.Map) GameData.that.baseMapService
				.findOneByMapId(npc.getMapId());
		String msg = "#Y" + chara.name + "#n正在去找#R" + npc.getName() + "#n, " + "在地图#Z" + map.getName() + "|" + map.getName()
				+ "(" + npc.getX() + "," + npc.getY() + ")#Z上进行宠物飞升!";

		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = msg;
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M20480_0(), vo_20480_0);
		onNotifyTask(chara, 71);
		GameCommonUtil.sendTips("成功领取宠物飞升任务，快去完成吧！", chara.id);
		return true;
	}

	public static void onFight(Chara chara) {
		List<String> list = new ArrayList<>();
		Petbeibao pet = chara.getPetByID(chara.flyPetID);
		for (int i = 0; i < 10; i++) {
			list.add(guaiwuNames.get(pet.petShuXing.get(0).currentFlyFightIndex - 1));
		}
		FightManager.activeBoosGoFight(chara, list, false);
	}

	public static boolean onPetFly(Chara chara, String menu_item) {
		Petbeibao pet = chara.getPetByID(chara.flyPetID);
		if (pet == null && !menu_item.equals("离开")) {
			GameUtil.sendTips("当前没有要飞升的宠物！");
			return false;
		}
		//如果当前飞升的宠物和参战的宠物不一样
		if(chara.flyPetID != chara.chongwuchanzhanId) {
			GameUtil.sendTips("宠物飞升必须要让飞升的宠物参战");
			return false;
		}
		if (menu_item.equals("宠物飞升")) {
			int step = pet.petShuXing.get(0).currentFlyFightIndex;
			if(step>3) {
				GameUtil.sendMeTips("你已完成宠物飞升，请去提交任务！");
				return false;
			}
			onFight(chara);
		} else if (menu_item.equals("帮派求助")) {

		}
		return true;
	}

	public static void sendPetUpgradedInfo(Chara chara) {
		Petbeibao pet = chara.getPetByID(chara.flyPetID);
		if(pet == null) {
			GameUtil.sendMeTips("没有找到这个宠物的配置信息！");
			return;
		}
		final Pet petInfoByName = GameData.that.basePetService.findOneByName(pet.petShuXing.get(0).suit_polar);
		if(petInfoByName == null) {
			GameUtil.sendMeTips("没有找到这个宠物的配置信息！");
			return;
		}
		final int[] a49 = new int[5];
		a49[0] = petInfoByName.getLife();
		a49[1] = petInfoByName.getMana();
		a49[2] = petInfoByName.getPhyAttack();
		a49[3] = petInfoByName.getMagAttack();
		a49[4] = petInfoByName.getSpeed();

		// 0气血，1法力，2物攻，3法攻，4速度
		final int[] result = PetAttributesUtils.flyPet(pet.petShuXing.get(0).penetrate, a49);
		VO_MSG_PET_UPGRADE_PRE_INFO vo = new VO_MSG_PET_UPGRADE_PRE_INFO();
		vo.id = pet.id;
		// 气血
		vo.pet_life_shape = result[0];
		// 法力
		vo.pet_mana_shape = result[1];
		// 物攻
		vo.pet_mag_shape = result[2];
		// 法攻
		vo.pet_phy_shape = result[3];
		// 速度
		vo.pet_speed_shape = result[4];

		GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M_MSG_PET_UPGRADE_PRE_INFO(), vo);
	}

	public static boolean onFightSuccess(Chara chara) {
		Petbeibao pet = chara.getPetByID(chara.flyPetID);
		pet.petShuXing.get(0).currentFlyFightIndex+=1;
		int currentFlyFightIndex = pet.petShuXing.get(0).currentFlyFightIndex;
		if (currentFlyFightIndex <= MAX_FLY) {
			GameObjectChar.send(new MSG_AUTO_WALK(), new Vo_AUTO_WALK("#P米兰仙子|M=宠物飞升#P","宠物飞升"));
		} else {
			onNotifyTask(chara, 72);
			Npc npc = GameData.that.baseNpcService.findOneByName(YinLuNpcName);
			com.fengshen.db.domain.Map map = (com.fengshen.db.domain.Map) GameData.that.baseMapService
					.findOneByMapId(npc.getMapId());
			String msg = "请赶快去找#R" + npc.getName() + "#n, " + "在地图#Z" + map.getName() + "|" + map.getName() + "(" + npc.getX()
					+ "," + npc.getY() + ")#Z上进行宠物飞升吧!";
			GameUtil.sendMeTips(msg);
		}
		return true;
	}

	public static boolean onFightFail(Chara chara) {
//		Petbeibao pet = chara.getPetByID(chara.flyPetID);
//		pet.petShuXing.get(0).currentFlyFightIndex-=1;
		return true;
	}

	public static boolean isPetFeiSheng(String name) {
		if(name != null) {
			for (String guiWuname : guaiwuNames) {
				if (guiWuname.contains(name))
					return true;
			}
		}
		return false;
	}
}
