package com.fengshen.server.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.CustomPetSkill;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.service.pet.CustomPetSkillService;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_32747_0;
import com.fengshen.server.data.write.M32747_0;
import com.fengshen.server.exception.PetPackOverflowException;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

// 表示一个宠物类
@JsonIgnoreProperties(ignoreUnknown = true)
public class Petbeibao {
	public int no;
	public int id;
	@JSONField(serialzeFeatures = SerializerFeature.DisableCircularReferenceDetect)
	public List<PetShuXing> petShuXing; // 宠物属性
	public List<Vo_12023_0> tianshu; // 宠物天书
	//自动补充
	public int autofight_supplement;
	public int autofight_select; // 是否自动战斗
	public int autofight_skillaction; // 自动战斗的行为，普攻，物攻，法功
	public int autofight_skillno; // 技能编号
	public int base; // add tzhang
	// 宠物天生技能
	public List<JiNeng> tianji;
	//自动喊话
	public List<AutoTalkVo> autoTalk;
	public Petbeibao() {
		this.petShuXing = new LinkedList<PetShuXing>();
		this.tianshu = new LinkedList<Vo_12023_0>();
		// 默认不让宠物自动战斗
		this.autofight_select = 0;
		this.autofight_skillaction = 1;
		this.autofight_skillno = 0;
		this.tianji = new ArrayList<>();
	}

	/**
	 * 判断是否有可容纳的位置
	 * 
	 * @param chara
	 * @param penetrate
	 * @return
	 */
	public static int isAddPet(Chara chara, int penetrate) {
		int minjieNum = 0;
		int yangjianNum = 0;
		List<Petbeibao> pets = chara.pets;
		for (Petbeibao p : pets) {
			PetShuXing sx = p.petShuXing.get(0);
			if (sx.penetrate == 6 || sx.penetrate == 7 || sx.penetrate == 8) {
				minjieNum++;
			} else {
				yangjianNum++;
			}
		}
		if (penetrate == 6 || penetrate == 7 || penetrate == 8) {
			// 判断冥界的宠物是否超过限制
			if (minjieNum >= 8) {
				GameCommonUtil.sendTips("宠物栏满了",chara.id);
				throw new PetPackOverflowException("宠物栏满了");
			}
		} else {
			if (yangjianNum >= 8) {
				GameCommonUtil.sendTips("宠物栏满了",chara.id);
				throw new PetPackOverflowException("宠物栏满了");
			}
		}
		return 0;
	}

	// add tzhang 创建满属性宠物
	public void FullPetCreate(Pet pet, Chara chara, int suiji, int penetrate, String source) {
		if (isAddPet(chara, penetrate) == -1) {
			return;
		}
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = 1;
		this.no = GameUtil.getNo(chara);
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 15000;
		shuXing.double_hit = 100;
		shuXing.suit_polar = pet.getName();
		shuXing.auto_fight = GameCommonUtil.UUID();

		if (pet.getPolar().equals("无")) {
			shuXing.metal = 0;
		}
		if (pet.getPolar().equals("金")) {
			shuXing.metal = 1;
		}
		if (pet.getPolar().equals("木")) {
			shuXing.metal = 2;
		}
		if (pet.getPolar().equals("水")) {
			shuXing.metal = 3;
		}
		if (pet.getPolar().equals("火")) {
			shuXing.metal = 4;
		}
		if (pet.getPolar().equals("土")) {
			shuXing.metal = 5;
		}
		shuXing.mana_effect = pet.getLife() - 40 - subtraction(suiji);
		shuXing.attack_effect = pet.getMana() - 40 - subtraction(suiji);
		shuXing.mag_effect = pet.getPhyAttack() - 40 - subtraction(suiji);
		shuXing.phy_absorb = pet.getMagAttack() - 40 - subtraction(suiji);
		shuXing.phy_effect = pet.getSpeed() - 40 - subtraction(suiji);
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		shuXing.penetrate = penetrate;
		shuXing.polar_point = 4;
		shuXing.enchant_nimbus = 0;
		shuXing.max_enchant_nimbus = 0;
		shuXing.suit_light_effect = 0;
		shuXing.hide_mount = 0;
		shuXing.phy_power = 1;
		shuXing.mag_power = 1;
		shuXing.life = 1;
		shuXing.speed = 1;
		shuXing.skillRange = pet.getSkillRange();
		BasicAttributesUtils.petshuxing(shuXing, this);
		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;

		// add tzhang 添加满属性资质
		shuXing.pet_mana_shape = 90;
		shuXing.pet_speed_shape = 50;
		shuXing.pet_phy_shape = 60;
		shuXing.pet_mag_shape = 100;
		shuXing.rank = 10;
		shuXing.resist_point = 310;
		// add:e
		this.petShuXing.add(shuXing);
		createPet(chara, this, source);

	}
	// add:e

	// 创建普通宠物，// penetrate为宠物的类型：1野生，2宝宝，3变异，4神兽，5守护
	public void PetCreate(Pet pet, Chara chara, int suiji, int penetrate, String source) {
		if (isAddPet(chara, penetrate) == -1) {
			return;
		}
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = 1;
		this.no = GameUtil.getNo(chara);
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 15000;
		shuXing.double_hit = 100;
		shuXing.suit_polar = pet.getName();
		shuXing.auto_fight = GameCommonUtil.UUID();
		if (pet.getPolar().equals("金")) {
			shuXing.metal = 1;
		}
		if (pet.getPolar().equals("木")) {
			shuXing.metal = 2;
		}
		if (pet.getPolar().equals("水")) {
			shuXing.metal = 3;
		}
		if (pet.getPolar().equals("火")) {
			shuXing.metal = 4;
		}
		if (pet.getPolar().equals("土")) {
			shuXing.metal = 5;
		}
		shuXing.mana_effect = pet.getLife() - 40 - subtraction(suiji);
		shuXing.attack_effect = pet.getMana() - 40 - subtraction(suiji);
		shuXing.mag_effect = pet.getPhyAttack() - 40 - subtraction(suiji);
		shuXing.phy_absorb = pet.getMagAttack() - 40 - subtraction(suiji);
		shuXing.phy_effect = pet.getSpeed() - 40 - subtraction(suiji);
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		shuXing.penetrate = penetrate;
		shuXing.polar_point = 4;
		shuXing.enchant_nimbus = 0;
		shuXing.max_enchant_nimbus = 0;
		shuXing.suit_light_effect = 0;
		shuXing.hide_mount = 0;
		shuXing.phy_power = 1;
		shuXing.mag_power = 1;
		shuXing.life = 1;
		shuXing.speed = 1;
		shuXing.skillRange = pet.getSkillRange();
		BasicAttributesUtils.petshuxing(shuXing, this);
		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;
		this.petShuXing.add(shuXing);
		createPet(chara, this, source);
	}

	// 获得超神兽
	public void ChaoPetCreate(Pet pet, Chara chara, int suiji, int penetrate, double[] chaoRate, double[] fuzhanRate, 
			String source) {
		if (isAddPet(chara, penetrate) == -1) {
			return;
		}
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = 1;
		this.no = GameUtil.getNo(chara);
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 15000;
		shuXing.double_hit = 100;
		shuXing.suit_polar = pet.getName();
		shuXing.auto_fight = GameCommonUtil.UUID();
		if (pet.getPolar().equals("金")) {
			shuXing.metal = 1;
		}
		if (pet.getPolar().equals("木")) {
			shuXing.metal = 2;
		}
		if (pet.getPolar().equals("水")) {
			shuXing.metal = 3;
		}
		if (pet.getPolar().equals("火")) {
			shuXing.metal = 4;
		}
		if (pet.getPolar().equals("土")) {
			shuXing.metal = 5;
		}
		shuXing.mana_effect = pet.getLife() - 40 - subtraction(suiji);
		shuXing.attack_effect = pet.getMana() - 40 - subtraction(suiji);
		shuXing.mag_effect = pet.getPhyAttack() - 40 - subtraction(suiji);
		shuXing.phy_absorb = pet.getMagAttack() - 40 - subtraction(suiji);
		shuXing.phy_effect = pet.getSpeed() - 40 - subtraction(suiji);
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		// add tzhang 超神兽对基础属性加倍
		if (chaoRate.length == 5) {
			shuXing.pet_mana_shape = (int) Math.round(shuXing.pet_mana_shape * chaoRate[0]);
			shuXing.pet_speed_shape = (int) Math.round(shuXing.pet_speed_shape * chaoRate[1]);
			shuXing.pet_phy_shape = (int) Math.round(shuXing.pet_phy_shape * chaoRate[2]);
			shuXing.pet_mag_shape = (int) Math.round(shuXing.pet_mag_shape * chaoRate[3]);
			shuXing.rank = (int) Math.round(shuXing.rank * chaoRate[4]);
		}
		// add:e
		// 服战神兽再加倍
		if (fuzhanRate.length == 5) {
			shuXing.pet_mana_shape = (int) Math.round(shuXing.pet_mana_shape * fuzhanRate[0]);
			shuXing.pet_speed_shape = (int) Math.round(shuXing.pet_speed_shape * fuzhanRate[1]);
			shuXing.pet_phy_shape = (int) Math.round(shuXing.pet_phy_shape * fuzhanRate[2]);
			shuXing.pet_mag_shape = (int) Math.round(shuXing.pet_mag_shape * fuzhanRate[3]);
			shuXing.rank = (int) Math.round(shuXing.rank * fuzhanRate[4]);
		}
		shuXing.mana_effect = shuXing.pet_mana_shape - 40;
		shuXing.attack_effect = shuXing.pet_speed_shape - 40;
		shuXing.mag_effect = shuXing.pet_phy_shape - 40;
		shuXing.phy_absorb = shuXing.pet_mag_shape - 40;
		shuXing.phy_effect = shuXing.rank - 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		shuXing.penetrate = penetrate;
		shuXing.polar_point = 4;
		shuXing.enchant_nimbus = 0;
		shuXing.max_enchant_nimbus = 0;
		shuXing.suit_light_effect = 0;
		shuXing.hide_mount = 0;
		shuXing.phy_power = 1;
		shuXing.mag_power = 1;
		shuXing.life = 1;
		shuXing.speed = 1;
		shuXing.skillRange = pet.getSkillRange();
		BasicAttributesUtils.petshuxing(shuXing, this);
		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;
		this.petShuXing.add(shuXing);
		createPet(chara, this, source);
	}

	public int petCreate(Pet pet, Chara chara, int suiji, String source, boolean... isThrow) {
		if (isThrow == null || isThrow.length == 0) {
			if (isAddPet(chara, 20) == -1) {
				return 0;
			}
		}
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = 1;
		this.no = GameUtil.getNo(chara);
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 15000;
		shuXing.double_hit = 100;
		shuXing.suit_polar = pet.getName();
		shuXing.auto_fight = GameCommonUtil.UUID();
		if (pet.getPolar().equals("金")) {
			shuXing.metal = 1;
		}
		if (pet.getPolar().equals("木")) {
			shuXing.metal = 2;
		}
		if (pet.getPolar().equals("水")) {
			shuXing.metal = 3;
		}
		if (pet.getPolar().equals("火")) {
			shuXing.metal = 4;
		}
		if (pet.getPolar().equals("土")) {
			shuXing.metal = 5;
		}
		shuXing.skillRange = pet.getSkillRange();
		shuXing.mana_effect = pet.getLife() - 40 - subtraction(suiji);
		shuXing.attack_effect = pet.getMana() - 40 - subtraction(suiji);
		shuXing.mag_effect = pet.getPhyAttack() - 40 - subtraction(suiji);
		shuXing.phy_absorb = pet.getMagAttack() - 40 - subtraction(suiji);
		shuXing.phy_effect = pet.getSpeed() - 40 - subtraction(suiji);
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		this.petShuXing.add(shuXing);
		createPet(chara, this, source);
		return 0;
	}

	/**
	 * 创建pet
	 * 
	 * @param chara
	 * @param pet
	 */
	private void createPet(Chara chara, Petbeibao pet, String source) {
		// 插入数据
		CharaPet charaPet = new CharaPet();
		charaPet.setCid(chara.id);
		charaPet.setUuid(chara.uuid);
		charaPet.setOwnerName(chara.name);
		charaPet.setPetName(pet.petShuXing.get(0).str);
		charaPet.setPet(JSONObject.toJSONString(pet));
		GameData.that.charaPetService.createPet(charaPet);
		pet.id = charaPet.getId();
		// 查询是否有自定义技能
		CustomPetSkillService customPetSkillService = SpringBeanUtils.getBean(CustomPetSkillService.class);
		List<CustomPetSkill> customPetSkillByPetName = customPetSkillService
				.getCustomPetSkillByPetName(pet.petShuXing.get(0).str);
		if (customPetSkillByPetName != null && !customPetSkillByPetName.isEmpty()) {
			if (pet.tianji == null) {
				pet.tianji = new ArrayList<>();
			}
			for (CustomPetSkill cp : customPetSkillByPetName) {
				List<org.json.JSONObject> nomelSkills = PetAndHelpSkillUtils.getSkills(1, cp.getSkillName());
				for (int i = 0; i < nomelSkills.size();) {
					org.json.JSONObject jsonObject1 = nomelSkills.get(i);
					JiNeng tianji = new JiNeng();
					tianji.id = charaPet.getId();
					tianji.skill_no = Integer.parseInt((String) jsonObject1.get("skillNo"));
					tianji.skill_attrib1 = Integer.parseInt((String) jsonObject1.get("skill_attrib"));
					tianji.skill_attrib = cp.getSkillLevel();
					tianji.skill_level = cp.getSkillLevel();
					tianji.level_improved = 0;
					tianji.skill_mana_cost = 200;
					tianji.skill_nimbus = 59000;
					tianji.skill_disabled = 0;
					tianji.range = cp.getSkillRange();
					tianji.max_range = cp.getSkillRange();
					tianji.skillRound = cp.getSkillRound();
					tianji.count1 = 0;
					// 消耗信息
					int[] petPartySkillCost = GameCommonUtil.getPetPartySkillCost(tianji.skill_level);
					tianji.skillCost.add(new SkillCost("cash", petPartySkillCost[0]));
					tianji.skillCost.add(new SkillCost("party/contrib", petPartySkillCost[1]));
					tianji.isTempSkill = 0;
					pet.tianji.add(tianji);
					break;
				}
			}
			// 过一秒钟执行
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 刷新技能信息
					List<Vo_32747_0> vo_32747_0List = GameUtil.a32747(pet.tianji);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M32747_0(), vo_32747_0List);
				}
			}, 1000);
		}
		GameCommonUtil.addCharaTrail(chara, "宠物", pet.petShuXing.get(0).str, source);
	}

	// 随机生成0-i之间的整数，0则返回0
	public static int subtraction(int i) {
		Random r = new Random();
		if (i == 0) {
			return 0;
		}
		return r.nextInt(i);
	}
}