package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import com.fengshen.db.domain.FightObjectInfo;
import com.fengshen.db.domain.Pet;
import com.fengshen.db.domain.SkillMonster;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.game.BasicAttributesUtils;
import com.fengshen.server.data.game.ChangeCardAttr;
import com.fengshen.server.data.game.PetAndHelpSkillUtils;
import com.fengshen.server.data.game.SuitEffectUtils;
import com.fengshen.server.data.vo.Vo_11757_0;
import com.fengshen.server.data.vo.Vo_7667_0;
import com.fengshen.server.data.vo.Vo_APPEAR;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.data.vo.fight.Vo_C_ACTION;
import com.fengshen.server.data.vo.fight.Vo_C_END_ACTION;
import com.fengshen.server.data.write.M11757_0;
import com.fengshen.server.data.write.M64981_Fight_Blood;
import com.fengshen.server.data.write.M64981_Fight_Mana;
import com.fengshen.server.data.write.M7667_0;
import com.fengshen.server.data.write.fight.c.MSG_C_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_END_ACTION;
import com.fengshen.server.data.write.fight.c.MSG_C_SET_CUSTOM_MSG;
import com.fengshen.server.domain.AutoTalkVo;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.CharaStatue;
import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.domain.ShouHu;
import com.fengshen.server.exception.FightException;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameLine;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameShuaGuai;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.service.DynamicAttributesService;
import com.fengshen.server.util.GameConfig;
import com.qcloud.cos.utils.StringUtils;

import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class FightObject {
	public int id;
	public int cid; // 宠物所属的角色id
	public int fid; //战斗id
	public String str; // 战斗对象的名字
	public int leader;
	public int type; // 1表示角色(和死亡不消失的怪物)，2是宠物，3是守护，4是怪物, 5死亡后不消失的
	public int pos;
	public int weapon_icon;
	public int guaiwulevel;
	public int mofa;
	public int shengming;
	public int max_mofa;
	public int max_shengming;
	public int fangyu;
	public int accurate;
	public int fashang;
	public int parry;
	public int fangyu_ext;
	public int accurate_ext;
	public int fashang_ext;
	public int parry_ext;
	public int durability;
	public int org_icon; // 角色外观
	public int suit_icon;
	public int suit_light_effect;
	public int special_icon;
	public AtomicInteger state = new AtomicInteger(1); // 1为存活，2或3为死亡
	private List<Integer> buffState;
	public List<JiNeng> skillsList;
	public FightRequest fightRequest;
	private List<FightSkill> fightSkillList;
	public int autofight_supplement; // 自动消耗开关，0关1开
	public int autofight_select; // 自动战斗开关，0关1开
	public int autofight_skillaction; // 自动战斗的类型
	public int autofight_skillno; // 战斗技能的编号
	public int friend; // 道行的总天数，一年=360天
	public int rank;
	public int godbook;
	public boolean run;
	int[] xiangxing;
	// 宠物最大复活次数
	public int maxReviveTimes;
	// 宠物当前的复活次数
	public int currentReviveTimes;
	// 添加宠物的亲密度
	public int shape;
	// 是否触发了掠阵(不是让他上阵的意思，是表示它已经死亡并且开启了掠阵，让其他掠阵宠物上)
	public boolean isLueZhen = false;
	// 设置门派
	public int polar;
	// boosId
	public int bossid;
	// 死亡的宠物id
	public List<Integer> overPets = new ArrayList<>();
	// 怪物死亡后是否立马消失,0消失.1:不消失.并且还会自动拉
	public int isGuaiWuHide;
	public int upgrade_level;
	public int upgrade_type;
	public int upgrade_state;
	//仙道点
	public int upgrade_immortal;
	//魔道点
	public int upgrade_magic;
	//自定义宠物图标
	public int petCustomIcon;
	// 技能攻击范围
	public int skillRange;
	// 宠物类型
	public int petType;
	// 自定义外观
	public String customIcon = "";
	// 闪避
	public int magDodgeExt;

	/**
	 * 战斗属性
	 */
	private FightAttribute fightAttribute = new FightAttribute();
	// 是否让木心消失
	public boolean isHideLifeEffect = false;
	// 附灵
	public int zhenlingLevel;
	public int zhenlingType;
	//超时次数，如果超过一次则自动设置为自动
	public int timeOutValue;
	//唯一标识
	public String uid;
	//自动喊话
	public List<AutoTalkVo> autoTalk;
	//是否开启喊话
	public int combatAutoTalk;
	//战斗类型
	public String fightType = "";
	//请求喊话是否执行
	public boolean isSos;
	//是否复活
	public boolean isRevive;
	//喊话技能
	public boolean isTalk;
	//vip
	public int vipType;

	public float getAttribute(FightAttribtueType type) {
		return fightAttribute.getAttribute(type);
	}

	// 新建角色的战斗对象
	public FightObject(Chara chara) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		this.id = chara.id;
		this.str = chara.name;
		this.guaiwulevel = chara.level;
		this.weapon_icon = chara.weapon_icon;
		this.shengming = chara.def+chara.zbAttribute.def;
		this.max_shengming = chara.def+chara.zbAttribute.def;
		this.mofa =  chara.dex + chara.zbAttribute.dex;
		this.max_mofa = chara.dex + chara.zbAttribute.dex;
		this.fashang = chara.mana + chara.zbAttribute.mana;
		this.parry = chara.parry + chara.zbAttribute.parry;
		this.accurate = chara.accurate + chara.zbAttribute.accurate; // 战斗对象的物攻为角色本身物攻+装备物攻
		this.fangyu = chara.wiz + chara.zbAttribute.wiz;
		this.suit_icon = chara.suit_icon;
		this.suit_light_effect = chara.suit_light_effect;
		this.org_icon = chara.waiguan;
		this.special_icon = chara.special_icon;
		this.friend = chara.tao;
		this.skillsList = chara.jiNengList;
		this.type = 1;
		this.autofight_supplement = chara.autofight_supplement;
		this.autofight_skillno = chara.autofight_skillno;
		this.autofight_select = chara.autofight_select;
		this.autofight_skillaction = chara.autofight_skillaction;
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(chara);
		this.polar = chara.polar;
		VoChangeCard changeCardInfo = chara.getChangeCardInfo();
		if (chara.special_icon != 0) {
			this.special_icon = chara.special_icon;
			// 如果是龙凤呈祥在战斗中是不显示的
			if (chara.special_icon == 42101 || chara.special_icon == 42102) {
				this.special_icon = 0;
			}
		} else {
			// 变身卡为空则显示默认状态
			if (changeCardInfo == null) {
				if (chara.upgrade_state != 0) {
					if (chara.upgrade_state == 1) {
						// 元婴
						this.special_icon = 7008;
					} else {
						this.special_icon = 7009;
					}
				}
			} else {
				// 取出变身卡效果
				this.special_icon = changeCardInfo.getIcon();
			}
		}
		this.upgrade_type = chara.upgrade_type;
		this.upgrade_level = chara.upgrade_level;
		this.upgrade_immortal = chara.upgrade_immortal;
		this.upgrade_magic = chara.upgrade_magic;
		// 神魂数据
		this.fashang += chara.shenHunMagPower;
		this.accurate += chara.shenHunPhyPower;
		this.fangyu += chara.shenHunDef;
		this.parry += chara.shenHunSpeed;
		this.shengming += chara.shenHunmaxLife;
		this.max_shengming += chara.shenHunmaxLife;
		//洛书数据
		this.fashang += chara.luoshuMagpower;
		this.accurate += chara.luoshumPhypower;
		this.fangyu += chara.luoshuDefense;
		this.parry += chara.luoshuSpeed;
		// 判断是否有变身卡.并加成
		if (changeCardInfo != null) {
			List<ChangeCardAttr> attrs = changeCardInfo.getAttr();
			if (attrs != null && !attrs.isEmpty()) {
				for (ChangeCardAttr a : attrs) {
					switch (a.getField()) {
					case "mag_power":
						this.fashang += (this.fashang * a.getValue() / 100);
						break;
					case "max_life":
						this.shengming += (this.shengming * a.getValue() / 100);
						this.max_shengming += (this.max_shengming * a.getValue() / 100);
						break;
					case "max_mana":
						this.max_mofa += (this.max_mofa * a.getValue() / 100);
						break;
					case "phy_power":
						this.accurate += (this.accurate * a.getValue() / 100);
						break;
					case "speed":
						this.parry += (this.parry * a.getValue() / 100);
						break;
					case "def":
						this.fangyu += (this.fangyu * a.getValue() / 100);
						break;
					}
				}
			}
		}
		this.customIcon = chara.customIcon;
		if (this.mofa > this.max_mofa) {
			this.mofa = this.max_mofa;
		}
		// 附灵
		this.zhenlingType = chara.zhenlingType;
		// 附灵加成
		fulingAttr(chara);
		GameObjectChar gameObjectChar = GameObjectCharMng.getGameObjectChar(chara.id);
		if(gameObjectChar != null) {
//			gameObjectChar.isBack.set(false);
			gameObjectChar.isEndRound.set(false);
		}
		this.autoTalk = chara.autoTalk;
		if(chara.getSettings().get("combat_auto_talk") != null) {
			this.combatAutoTalk = chara.getSettings().get("combat_auto_talk");
		}
		this.durability = 1;
		this.vipType = chara.vipType;
	}
	
	/**
	 * 宠物战斗对象
	 * @param pet 宠物
	 * @param chara 主人
	 */
	public FightObject(Petbeibao pet, Chara chara) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		int life = pet.petShuXing.get(0).def;
		if (pet.petShuXing.get(0).max_life > pet.petShuXing.get(0).def) {
			life = pet.petShuXing.get(0).max_life;
		}
		this.str = pet.petShuXing.get(0).str;
		this.shengming = life;
		this.mofa = pet.petShuXing.get(0).dex;
		this.max_mofa = pet.petShuXing.get(0).max_mana;
		this.max_shengming = life;
		this.fashang = pet.petShuXing.get(0).mana;
		this.parry = pet.petShuXing.get(0).parry;
		this.accurate = pet.petShuXing.get(0).accurate;
		this.fangyu = pet.petShuXing.get(0).wiz;
		this.org_icon = pet.petShuXing.get(0).type;
		boolean isfagong = pet.petShuXing.get(0).rank > pet.petShuXing.get(0).pet_mag_shape;
		this.skillsList = dujineng(1, pet.petShuXing.get(0).metal, pet.petShuXing.get(0).skill, isfagong, pet.id, "",
				pet);
		this.type = 2;
		this.autofight_supplement = pet.autofight_supplement;
		this.autofight_skillno = pet.autofight_skillno;
		this.autofight_select = pet.autofight_select;
		this.autofight_skillaction = pet.autofight_skillaction;
		this.friend = pet.petShuXing.get(0).intimacy / 2;
		this.shape = pet.petShuXing.get(0).shape;
		this.durability = 32768;
		this.rank = 2;
		// add tzhang 添加宠物的最大复活次数以及初始化当前复活次数
		this.currentReviveTimes = 0;
		this.maxReviveTimes = pet.petShuXing.get(0).maxReviveTimes;
		this.special_icon = pet.petShuXing.get(0).getFasion_id();
		this.petCustomIcon = pet.petShuXing.get(0).getFasion_id();
		// 当宠物时装没有穿的情况下.则显示变色的.
		if (pet.petShuXing.get(0).getFasion_id() == 0 && pet.petShuXing.get(0).dye_icon != 0) {
			this.petCustomIcon = pet.petShuXing.get(0).dye_icon;
			this.special_icon = pet.petShuXing.get(0).dye_icon;
		}
		// 自定义宠物技能范围
		this.skillRange = pet.petShuXing.get(0).skillRange;
		
		if(chara != null) {
//			this.fashang += chara.luoshuMagpower;
//			this.accurate += chara.luoshumPhypower;
//			this.fangyu += chara.luoshuDefense;
//			this.parry += chara.luoshuSpeed;
			this.accurate += chara.zhenlingPhy;
			this.fashang += chara.zhenlingMag;
			this.parry += chara.zhenlingSpeed;
			this.fangyu += chara.zhenlingDef;
			// 附灵
			this.zhenlingType = pet.petShuXing.get(0).zhenlingType;
			this.zhenlingLevel = pet.petShuXing.get(0).zhenlingLevel;
			// 附灵附身
			if (pet.petShuXing.get(0).zhenlingType == 1) {
				// 法伤10%
				int mana = (int) (this.fashang
						* GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0] / 100);
				this.fashang += mana;
				// 其他5%
				this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
						/ 100 / 2);
				this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2] / 100
						/ 2);
				this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
						/ 100 / 2);
			} else if (pet.petShuXing.get(0).zhenlingType == 2) {
				// 物伤10%
				this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
						/ 100);
				// 其他5%
				this.fashang += (this.fashang * GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]
						/ 100 / 2);
				this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2] / 100
						/ 2);
				this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
						/ 100 / 2);
			} else if (pet.petShuXing.get(0).zhenlingType == 3) {
				// 速度10%
				int zhuque = chara.zhuqueZhenlingLevel - 1;
				this.parry += (this.parry * GameConfig.spiritInfoConfig.get(zhuque<0?0:zhuque).getAtt()[2]
						/ 100);
				// 其他5%
				this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
						/ 100 / 2);
				this.fashang += (this.fashang * GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]
						/ 100 / 2);
				this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
						/ 100 / 2);
			} else if (pet.petShuXing.get(0).zhenlingType == 4) {
				// 防御1%
				this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
						/ 100);
				// 其他5%
				this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
						/ 100 / 2);
				this.fashang += (this.fashang * GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]
						/ 100 / 2);
				this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2] / 100
						/ 2);
			}
		}
		//宠物抗性
		this.fightAttribute = DynamicAttributesService.fightAttribute(pet.petShuXing.get(0));
		this.autoTalk = pet.autoTalk;
		if(chara != null && chara.getSettings().get("combat_auto_talk") != null) {
			this.combatAutoTalk = chara.getSettings().get("combat_auto_talk");
		}
	}

	// 从快照的角色雕像中创建战斗对象
	public FightObject(CharaStatue charaStatue) {
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.str = charaStatue.name;
		this.weapon_icon = charaStatue.weapon_icon;
		this.shengming = charaStatue.shengming;
		this.mofa = charaStatue.mofa;
		this.max_shengming = charaStatue.max_shengming;
		this.max_mofa = charaStatue.max_mofa;
		this.fashang = charaStatue.fashang;
		this.parry = charaStatue.speed;
		this.accurate = charaStatue.phy_power;
		this.fangyu = charaStatue.fangyu;
		this.suit_icon = charaStatue.suit_icon;
		this.suit_light_effect = charaStatue.suit_light_effect;
		this.org_icon = charaStatue.waiguan;
		this.friend = charaStatue.tao;
		this.skillsList = charaStatue.jiNengList;
		this.type = 4;
		this.autofight_skillno = charaStatue.autofight_skillno;
		this.autofight_select = charaStatue.autofight_select;
		this.autofight_skillaction = charaStatue.autofight_skillaction;

	}

	// 新增
	public FightObject(FightObjectInfo fightObjectInfo, int ttt_level) {
		ttt_level = ttt_level - 36;
		int metal = GameUtil.getMetal(fightObjectInfo.getPolar());
		this.fightSkillList = new ArrayList<FightSkill>();
		this.str = fightObjectInfo.getName();
		this.guaiwulevel = ttt_level;
		this.shengming = (int) (fightObjectInfo.getLife() * (1 + ttt_level * 0.1));
		this.max_shengming = (int) (fightObjectInfo.getLife() * (1 + ttt_level * 0.1));
		this.mofa = (int) (fightObjectInfo.getMana() * (1 + ttt_level * 0.1));
		this.max_mofa = (int) (fightObjectInfo.getMana() * (1 + ttt_level * 0.1));
		this.fashang = (int) (fightObjectInfo.getMagAttack() * (1 + ttt_level * 0.1));
		this.parry = (int) (fightObjectInfo.getSpeed() * (1 + ttt_level * 0.1));
		this.accurate = (int) (fightObjectInfo.getPhyAttack() * (1 + ttt_level * 0.1));
		this.fangyu = (int) (fightObjectInfo.getDef() * (1 + ttt_level * 0.1));
		this.org_icon = fightObjectInfo.getIcon();
		this.friend = (int) (fightObjectInfo.getDaohang() * (1 + ttt_level * 0.5));// 道行武学上涨百分之五十
		if (!StringUtils.isNullOrEmpty(fightObjectInfo.getSkill())) {
			String[] split = fightObjectInfo.getSkill().split("\\#");
			// 随机从里面选一个职业技能
			this.skillsList = getJiNengListByName(metal, ttt_level, 123456,
					split[ThreadLocalRandom.current().nextInt(split.length)]);
		}
		this.type = 4;
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
	}

	public FightObject(FightObjectInfo fightObjectInfo) {
		int metal = GameUtil.getMetal(fightObjectInfo.getPolar());
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.str = fightObjectInfo.getName();
		this.guaiwulevel = fightObjectInfo.getLevel();
		this.shengming = fightObjectInfo.getLife();
		this.max_shengming = fightObjectInfo.getLife();
		this.mofa = fightObjectInfo.getMana();
		this.max_mofa = fightObjectInfo.getMana();
		this.fashang = fightObjectInfo.getMagAttack();
		this.parry = fightObjectInfo.getSpeed();
		this.accurate = fightObjectInfo.getPhyAttack();
		this.fangyu = fightObjectInfo.getDef();
		this.org_icon = fightObjectInfo.getIcon();
		this.friend = fightObjectInfo.getDaohang();
		if (!StringUtils.isNullOrEmpty(fightObjectInfo.getSkill())) {
			String[] split = fightObjectInfo.getSkill().split("\\#");
			// 随机从里面选一个职业技能
			this.skillsList = getJiNengListByName(metal, 1, 123456,
					split[ThreadLocalRandom.current().nextInt(split.length)]);
		}
		this.type = 4;
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
	}

	/**
	 * 自定义技能和相性
	 * 
	 * @param FightObjectInfo
	 * @param polar
	 * @param skillsList
	 */
	public FightObject(FightObjectInfo fightObjectInfo, Boolean isRandomSkill, int polar) {
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.str = fightObjectInfo.getShowName();
		this.guaiwulevel = fightObjectInfo.getLevel();
		this.shengming = fightObjectInfo.getLife();
		this.max_shengming = fightObjectInfo.getLife();
		this.mofa = fightObjectInfo.getMana();
		this.max_mofa = fightObjectInfo.getMana();
		this.fashang = fightObjectInfo.getMagAttack();
		this.parry = fightObjectInfo.getSpeed();
		this.accurate = fightObjectInfo.getPhyAttack();
		this.fangyu = fightObjectInfo.getDef();
		this.org_icon = fightObjectInfo.getIcon();
		this.friend = fightObjectInfo.getDaohang();
		if (fightObjectInfo.getSkill() != null && !fightObjectInfo.getSkill().trim().equals("")) {
			String[] split = fightObjectInfo.getSkill().split("\\#");
			// 随机从里面选一个职业技能
			this.skillsList = getJiNengListByName(this.guaiwulevel, 123456,
					split[ThreadLocalRandom.current().nextInt(split.length)]);
		} else {
			if (isRandomSkill) {
				// 随机门派技能.
				this.skillsList = getJiNengListByName(polar, this.guaiwulevel, 123456, GameUtil.getRandomSkills(polar));
			}
		}
		List<JiNeng> skillsList = this.skillsList;
		this.type = 4;
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
	}

	public FightObject(FightObjectInfo fightObjectInfo, Integer id) {
		int metal = GameUtil.getMetal(fightObjectInfo.getPolar());
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.bossid = id;
		this.str = fightObjectInfo.getName();
		this.guaiwulevel = fightObjectInfo.getLevel();
		this.shengming = fightObjectInfo.getLife();
		this.max_shengming = fightObjectInfo.getLife();
		this.mofa = fightObjectInfo.getMana();
		this.max_mofa = fightObjectInfo.getMana();
		this.fashang = fightObjectInfo.getMagAttack();
		this.parry = fightObjectInfo.getSpeed();
		this.accurate = fightObjectInfo.getPhyAttack();
		this.fangyu = fightObjectInfo.getDef();
		this.org_icon = fightObjectInfo.getIcon();
		this.friend = fightObjectInfo.getDaohang();
		if (!StringUtils.isNullOrEmpty(fightObjectInfo.getSkill())) {
			String[] split = fightObjectInfo.getSkill().split("\\#");
			// 随机从里面选一个职业技能
			this.skillsList = getJiNengListByName(metal, 1, 123456,
					split[ThreadLocalRandom.current().nextInt(split.length)]);
		}
		this.type = 4;
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
	}

	/**
	 * 技能名字是逗号分隔
	 *
	 * @param metal           门派
	 * @param skillNames      技能名称
	 * @param filterSkillType 过滤指定类型技能
	 * @return
	 */
	public static List<JiNeng> getJiNengListByName(int metal, int level, int id, String skillNames,
			String... filterSkillType) {
		List<JiNeng> jiNengList = new ArrayList<>();
		List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getSkills(metal, level, skillNames);

		for (int i = 0; i < nomelSkills.size(); ++i) {
			JiNeng jiNeng = new JiNeng();
			JSONObject jsonObject = nomelSkills.get(i);
//			log.info("星星："+jsonObject.toString());
			jiNeng.id = id;
			jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
			int skill_no = jiNeng.skill_no;
			// 如果这个人小于100级是不会出现百级法术的.
			if (level < 100) {
				if (skill_no == 15 || skill_no == 25 || skill_no == 35 || skill_no == 65 || skill_no == 75
						|| skill_no == 85 || skill_no == 114 || skill_no == 125 || skill_no == 135 || skill_no == 165
						|| skill_no == 175 || skill_no == 185 || skill_no == 214 || skill_no == 225
						|| skill_no == 235) {
					continue;
				}
			}
			// 过滤指定类型的技能
			boolean isFindFilterType = false;
			for (String skillType : filterSkillType) {
				if (skillType.equals(jsonObject.getString("skillType"))) {
					isFindFilterType = true;
					break;
				}
			}
			if (isFindFilterType) {
				continue;
			}
			jiNeng.skill_attrib = (Integer) jsonObject.get("skillLevel");
			jiNeng.skill_level = (Integer) jsonObject.get("skillLevel");
			jiNeng.skillRound = jsonObject.optInt("skillRound");
			jiNeng.level_improved = 0;
			jiNeng.skill_mana_cost = (Integer) jsonObject.get("skillBlue");
			jiNeng.skill_nimbus = 42949672;
			jiNeng.skill_disabled = 0;
			jiNeng.range = (Integer) jsonObject.get("skillNum");
			jiNeng.max_range = (Integer) jsonObject.get("skillNum");
			jiNengList.add(jiNeng);
		}
//		for (JiNeng jiNeng : jiNengList) {
//			log.info("jineng:"+ JSON.toJSON(jiNeng));
//		}
		return jiNengList;
	}

	public static List<JiNeng> getJiNengListByName(int level, int id, String skillNames) {
		List<JiNeng> jiNengList = new ArrayList<>();
		List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getSkills(level, skillNames);

		for (int i = 0; i < nomelSkills.size(); ++i) {
			JiNeng jiNeng = new JiNeng();
			JSONObject jsonObject = nomelSkills.get(i);
			jiNeng.id = id;
			jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
			jiNeng.skill_attrib = (Integer) jsonObject.get("skillLevel");
			jiNeng.skill_level = (Integer) jsonObject.get("skillLevel");
			jiNeng.skillRound = jsonObject.optInt("skillRound");
			jiNeng.level_improved = 0;
			jiNeng.skill_mana_cost = (Integer) jsonObject.get("skillBlue");
			jiNeng.skill_nimbus = 42949672;
			jiNeng.skill_disabled = 0;
			jiNeng.range = (Integer) jsonObject.get("skillNum");
			jiNeng.max_range = (Integer) jsonObject.get("skillNum");
			jiNengList.add(jiNeng);
		}
		return jiNengList;
	}

	public static List<JiNeng> getFightObjectJiNengListByName(int level, int id, String skillNames) {
		List<JiNeng> jiNengList = new ArrayList<>();
		List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getFightObjectSkills(level, skillNames);
		for (int i = 0; i < nomelSkills.size(); ++i) {
			JiNeng jiNeng = new JiNeng();
			JSONObject jsonObject = nomelSkills.get(i);
			jiNeng.id = id;
			jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
			jiNeng.skill_attrib = (Integer) jsonObject.get("skillLevel");
			jiNeng.skill_level = (Integer) jsonObject.get("skillLevel");
			jiNeng.skillRound = jsonObject.optInt("skillRound");
			jiNeng.level_improved = 0;
			jiNeng.skill_mana_cost = (Integer) jsonObject.get("skillBlue");
			jiNeng.skill_nimbus = 42949672;
			jiNeng.skill_disabled = 0;
			jiNeng.range = (Integer) jsonObject.get("skillNum");
			jiNeng.max_range = (Integer) jsonObject.get("skillNum");
			jiNengList.add(jiNeng);
		}
		return jiNengList;
	}

	// 新增

	public boolean isDead() {
		return this.state.get() == 2 || this.state.get() == 3;
	}

	// 如果状态是1,2,3则返回false，如果是6,7则是true
	public boolean doDead() {
		if (this.state.get() == 6) {
			this.state.set(2);
		} else {
			if (this.state.get() != 7) {
				return false;
			}
			this.state.set(3);
		}
		List<FightRoundSkill> roundSkill = this.getRoundSkill();
		FightContainer fightContainer = FightManager.getFightContainer(this.fid);
		if (fightContainer != null) {
			for (FightRoundSkill fightRoundSkill : roundSkill) {
				if (fightRoundSkill.getStateType() != 528128) { // 528128木的复活效果
					this.removeBuffState(fightContainer, fightRoundSkill.getStateType());
					this.fightSkillList.remove(fightRoundSkill);
				}
			}
		}
		return true;
	}

	// 获取战斗回合技能
	public List<FightRoundSkill> getRoundSkill() {
		List<FightRoundSkill> list = new ArrayList<FightRoundSkill>();
		for (FightSkill fightSkill : this.fightSkillList) {
			if (fightSkill instanceof FightRoundSkill) {
				list.add((FightRoundSkill) fightSkill);
			}
		}
		return list;
	}

	// 获取法宝技能，只有一个法宝技能
	public FightFabaoSkill getFabaoSkill() {
		if (this.fightSkillList == null)
			return null;
		for (FightSkill fightSkill : this.fightSkillList) {
			if (fightSkill instanceof FightFabaoSkill) {
				return (FightFabaoSkill) fightSkill;
			}
		}
		return null;
	}

	// add tzhang 从当前战斗对象中随机选择一个天书
	public int getRandomTianshuType(FightContainer fc) {
		ArrayList<FightTianshuSkill> list = new ArrayList<>();
		for (FightSkill fightSkill : this.fightSkillList) {
			if (fightSkill instanceof FightTianshuSkill) {
				FightTianshuSkill tianshu = (FightTianshuSkill) fightSkill;
				list.add(tianshu);
			}
		}
		if (list.size() == 0)
			return 0;
		return list.get(new Random().nextInt(list.size())).getStateType();
	}
	// add:e

	public FightTianshuSkill isActiveTianshu(FightContainer fc, int state) {
		// 添加前置判断，如果战斗对象为空，或者战斗对象被打死了，直接返回false
		if ((null != this && this.isDead()) || null == this) {
			return null;
		}
		// add:e

		for (FightSkill fightSkill : this.fightSkillList) {
			if (fightSkill instanceof FightTianshuSkill) {
				FightTianshuSkill fts = (FightTianshuSkill) fightSkill;
				if (fts.getStateType() == state) {
					boolean active = fts.isActive();
					if (active) {
						return fts;
					} else {
						return null;
					}
				}
				continue;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		List<JiNeng> jiNengListByName = getJiNengListByName(5, 125, 123456, GameUtil.getRandomSkills(5));

		for (JiNeng j : jiNengListByName) {
			System.out.println(com.alibaba.fastjson.JSONObject.toJSONString(j));
		}
	}

	/**
	 * 通天塔
	 * 
	 * @param chara   玩家
	 * @param name    名字
	 * @param perType 类型
	 */
	public FightObject(Chara chara, String name, String perType) {
		// 当前通天塔倍数
		double base = chara.tongtiantaTask.getCurLayer();
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		double beishu = 1;
		// 如果是自我突破的境界
		if (chara.tongtiantaTask.getChallengeCount() > 0) {
			beishu = 1.5;
		}
		// 如果是宠物的话就1.5倍
		if ("pet".equals(perType)) {
			beishu *= 1.5;
			this.type = 2;
			this.fightType = "TTT_TYPE";
		} else {
			this.type = 4;
			this.isGuaiWuHide = 1;
		}
		// 获取队长阶段
		int level = GameCommonUtil.getZbLevel(chara.level);
		// 给所有的对象加上等级
		name = name + "(" + level + ")";
		List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByNameForType(name, "通天塔");
		if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
			log.error("找不到该怪物的配置:{}", name);
			return;
		}
		int random = ThreadLocalRandom.current().nextInt(fightObjectInfos.size());

		FightObjectInfo fightObjectInfo = fightObjectInfos.get(random);
		this.str = fightObjectInfo.getShowName();
		this.guaiwulevel = (int) (fightObjectInfo.getLevel() * base * beishu);
		this.shengming = (int) (fightObjectInfo.getLife() * base * beishu);
		this.max_shengming = (int) (fightObjectInfo.getLife() * base * beishu);
		this.mofa = (int) (fightObjectInfo.getMana() * base * beishu);
		this.max_mofa = (int) (fightObjectInfo.getMana() * base * beishu);
		this.fashang = (int) (fightObjectInfo.getMagAttack() * base * beishu);
		this.parry = (int) (fightObjectInfo.getSpeed() * base * beishu);
		this.accurate = (int) (fightObjectInfo.getPhyAttack() * base * beishu);
		this.fangyu = fightObjectInfo.getDef();
		this.org_icon = fightObjectInfo.getIcon();
		this.friend = (int) (fightObjectInfo.getDaohang() * base * beishu);
		if (!StringUtils.isNullOrEmpty(fightObjectInfo.getSkill())) {
			this.skillsList = getFightObjectJiNengListByName(fightObjectInfo.getLevel(), 1, fightObjectInfo.getSkill());
		} else {
			this.skillsList = getJiNengListByName(ThreadLocalRandom.current().nextInt(5) + 1,
					fightObjectInfo.getLevel(), 123456,
					GameUtil.getRandomSkills(ThreadLocalRandom.current().nextInt(5) + 1));
		}
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
	}

	// 这里是生成掌门对象或者掌门宠物
	public FightObject(Chara chara, String name, int base) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		Petbeibao petbeibao = new Petbeibao();
		petbeibao = this.petCreate(name, chara.level);
		List<SkillMonster> monsters = (List<SkillMonster>) GameData.that.baseSkillMonsterService.findByName(name);
		String skills = "";
		if (monsters != null && monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); ++i) {
				if (monsters.get(i).getType() == 1) {
					skills = monsters.get(i).getSkills();
				}
			}
		}
		this.str = name;
		this.guaiwulevel = petbeibao.petShuXing.get(0).skill;
		this.shengming = (int) (petbeibao.petShuXing.get(0).max_life * 0.5) * base;
		this.mofa = (int) (petbeibao.petShuXing.get(0).max_mana * 0.5 * base) * base;
		this.max_mofa = (int) (petbeibao.petShuXing.get(0).dex * 0.5);
		this.max_shengming = (int) (petbeibao.petShuXing.get(0).def * 0.5 * base) * base;
		this.fashang = (int) (petbeibao.petShuXing.get(0).mana * 0.5 * base) * base;
		this.parry = (int) (petbeibao.petShuXing.get(0).parry * 0.5) * base;
		this.accurate = (int) (petbeibao.petShuXing.get(0).accurate * 0.5 * base) * base;
		this.fangyu = (int) (petbeibao.petShuXing.get(0).wiz * 0.5 * base) * base;
		this.org_icon = petbeibao.petShuXing.get(0).type;
		if (base == 1) {
			this.org_icon = GameCommonUtil.chara_icon[chara.polar - 1];
		} else {
			this.org_icon = petbeibao.petShuXing.get(0).type;
		}
		boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
		this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
				123456, skills, null);
		this.type = 10;
	}

	/**
	 * 守护神
	 * 
	 * @param chara
	 * @param name
	 * @param isPet
	 */
	public FightObject(Chara chara, String name, boolean isPet) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		FightObjectInfo fightObjectInfo = GameData.that.baseFightObjectService.findOneByName(name);
		if (fightObjectInfo == null) {
			GameUtil.sendMeTips("未找到该配置");
			throw new FightException();
		}
		this.str = name;
		this.guaiwulevel = fightObjectInfo.getLevel();
		this.shengming = fightObjectInfo.getLife();
		this.mofa = fightObjectInfo.getMana();
		this.max_mofa = fightObjectInfo.getMana();
		this.max_shengming = fightObjectInfo.getLife();
		this.fashang = fightObjectInfo.getMagAttack();
		this.parry = fightObjectInfo.getSpeed();
		this.accurate = fightObjectInfo.getPhyAttack();
		this.fangyu = fightObjectInfo.getDef();
		this.org_icon = fightObjectInfo.getIcon();
		this.type = 10;
		int leixing = 4;
		if (!isPet) {
			leixing = 1;
		}
		if (!StringUtils.isNullOrEmpty(fightObjectInfo.getSkill())) {
			String[] split = fightObjectInfo.getSkill().split("\\#");
			String skillStr = "";
			if (leixing > split.length) {
				skillStr = split[split.length - 1];
			} else {
				skillStr = split[leixing - 1];
			}
			if (!StringUtils.isNullOrEmpty(skillStr)) {
				// 随机从里面选一个职业技能
				this.skillsList = getFightObjectJiNengListByName(chara.level, 123456, skillStr);
			} else {
				this.skillsList = getJiNengListByName(leixing, chara.level, 123456, GameUtil.getRandomSkills(leixing));
			}
		} else {
			this.skillsList = getJiNengListByName(leixing, chara.level, 123456, GameUtil.getRandomSkills(leixing),
					"ZA");
		}
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
	}

	public FightObject() {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
	}

	// 这里生成攻城BOSS或者海盗、战神
	public FightObject(String name, Chara chara) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		Petbeibao petbeibao = new Petbeibao();
		petbeibao = this.petCreateRamMetal(name, chara.level);

		List<SkillMonster> monsters = (List<SkillMonster>) GameData.that.baseSkillMonsterService.findByName(name);
		String skills = "";
		if (monsters != null && monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); ++i) {
				if (monsters.get(i).getType() == 1) {
					skills = monsters.get(i).getSkills();
				}
			}
		}
		this.bossid = id;
		this.str = name;
		this.guaiwulevel = petbeibao.petShuXing.get(0).skill;
		this.shengming = (int) (petbeibao.petShuXing.get(0).max_life * 0.8);
		this.mofa = (int) (petbeibao.petShuXing.get(0).max_mana * 0.8);
		this.max_mofa = (int) (petbeibao.petShuXing.get(0).dex * 0.8);
		this.max_shengming = (int) (petbeibao.petShuXing.get(0).def * 0.8);
		this.fashang = (int) (petbeibao.petShuXing.get(0).mana * 0.8);
		this.parry = (int) (petbeibao.petShuXing.get(0).parry * 0.8);
		this.accurate = (int) (petbeibao.petShuXing.get(0).accurate * 0.8);
		this.fangyu = (int) (petbeibao.petShuXing.get(0).wiz * 0.8);
		this.org_icon = petbeibao.petShuXing.get(0).type;
		boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
		this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
				123456, skills, null);
		this.type = 4;
		// add tzhang 添加BOSS、海盗、战神的道行
		if (this.str.contains("海盗"))
			this.friend = (int) (0.29 * chara.level * chara.level * chara.level * (1));
		else if (this.str.contains("战"))
			this.friend = (int) (0.29 * chara.level * chara.level * chara.level * (1.5));
		// 判断是否是攻城BOSS
		boolean isGongchengBoss = false;
		for (Vo_APPEAR vo : GameLine.gameGongCheng.shuaGuai) {
			if (vo.name.equals(this.str)) {
				isGongchengBoss = true;
				break;
			}
		}
		if (this.str.contains("攻城") || isGongchengBoss)
			this.friend = (int) (0.29 * chara.level * chara.level * chara.level * (3));
		// add:e
	}

	// 初始化和角色战斗的怪物，name为怪物名称，怪物的自动战斗开关是关闭的
	public FightObject(Chara chara, String name) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		String strname = name;
		Petbeibao petbeibao = new Petbeibao();
		if (name.contains("土匪")) {
			strname = "土匪";
		}
		if (name.contains("强盗")) {
			strname = "强盗";
		}
		if (name.contains("狐狸妖")) {
			strname = "狐狸妖";
		}
		if (name.contains("鱼妖")) {
			strname = "鱼妖";
		}
		if (name.contains("蓝精")) {
			strname = "蓝精";
		}
		if (name.contains("黄怪")) {
			strname = "黄怪";
		}
		if (name.contains("疯魑")) {
			strname = "疯魑";
		}
		if (name.contains("狂魍")) {
			strname = "狂魍";
		}
		if (name.contains("蟒怪")) {
			strname = "蟒怪";
		}
		if (name.contains("鸟精")) {
			strname = "鸟精";
		}
		if (name.contains("琵琶妖")) {
			strname = "琵琶妖";
		}
		if (name.contains("蟒妖")) {
			strname = "蟒妖";
		}
		// 添加飞仙渡邪的怪物
		if (name.contains("绿毛怪")) {
			strname = "绿毛怪";
		}
		if (name.contains("骷髅")) {
			strname = "骷髅";
		}
		if (name.contains("丑鬼")) {
			strname = "丑鬼";
		}
		if (name.contains("伏地魔")) {
			strname = "伏地魔";
		}
		// add:e
		if (name.contains("怪王狂狮")) {
			strname = "怪王狂狮";
		}
		if (name.contains("鬼王黑熊")) {
			strname = "鬼王黑熊";
		}
		if (name.contains("鬼王悍猪")) {
			strname = "鬼王悍猪";
		}
		if (name.contains("混天巨象")) {
			strname = "混天巨象";
		}
		if (name.contains("兑灵")) {
			strname = "兑灵";
		}
		if (name.contains("艮灵")) {
			strname = "艮灵";
		}
		if (name.contains("坎灵")) {
			strname = "坎灵";
		}
		if (name.contains("离灵")) {
			strname = "离灵";
		}
		if (name.contains("狂灵")) {
			strname = "狂灵";
		}
		if (name.contains("疯灵")) {
			strname = "疯灵";
		}
		if (name.contains("山神")) {
			strname = "山神";
		}
		if (name.contains("炎神")) {
			strname = "炎神";
		}
		if (name.contains("雷神")) {
			strname = "雷神";
		}
		if (name.contains("花神")) {
			strname = "花神";
		}
		if (name.contains("龙神")) {
			strname = "龙神";
		}
		// 仙界叛逆的怪物本来就很难封印
		if (name.contains("仙界叛逆")) {
			strname = "仙界叛逆";
			Vo_APPEAR vo_APPEAR = GameShuaGuai.xuanshang.get(chara.zhandouId);
			if (vo_APPEAR != null) {
				strname = vo_APPEAR.alicename;
			}
			this.friend = (int) (0.29 * chara.level * chara.level * chara.level
					* (0.29 * chara.level * chara.level * chara.level));
		}
		if ("北斗神将玉衡星君天权星君天玑星君天璇星君天枢星君摇光星君开阳星君".indexOf(name) != -1) {
			strname = chara.tongtiantaTask.getNpc();
			petbeibao = this.petCreate(strname, chara.tongtiantaTask.getCurLayer());
		}
		if (name.equals("金系掌门")) {
			petbeibao = this.petCreate(strname, chara.level);
		}
		if (name.equals("帮凶") || name.equals("喽啰") || strname.equals("土匪") || strname.equals("土匪")
				|| "土匪#强盗#狐狸妖#鱼妖#蓝精#黄怪#疯魑#狂魍#**怪#鸟精#琵琶妖**妖#怪王狂狮#鬼王黑熊#鬼王悍猪#混天巨象#兑灵#艮灵#坎灵#离灵#狂灵#疯灵#山神#炎神#雷神#花神#龙神#刀斧手#火扇儒生#红衣剑客#试道元魔"
						.contains(strname)) {
			petbeibao = this.petCreate(strname, chara.level);
		}
		if (name.equals("瑶池仙子")) {
			petbeibao = this.petCreateRamMetal(strname, chara.level);
		}
		if (name.equals("穿山甲")) {
			petbeibao = this.petCreateRamMetal(strname, chara.level);
		}
		if (name.equals("守护神")) {
			petbeibao = this.petCreate(strname, chara.level);
		} else {
			petbeibao = this.petCreate(strname);
		}
		List<SkillMonster> monsters = GameData.that.baseSkillMonsterService.findByName(strname);
		String skills = "";
		if (monsters != null && monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); ++i) {
				if (monsters.get(i).getType() == 1) {
					skills = monsters.get(i).getSkills();
				}
			}
		}
		this.str = name;
		this.guaiwulevel = petbeibao.petShuXing.get(0).skill;
		this.shengming = (int) (petbeibao.petShuXing.get(0).max_life * 0.8);
		this.mofa = (int) (petbeibao.petShuXing.get(0).max_mana * 0.8);
		this.max_mofa = (int) (petbeibao.petShuXing.get(0).dex * 0.8);
		this.max_shengming = (int) (petbeibao.petShuXing.get(0).def * 0.8);
		this.fashang = (int) (petbeibao.petShuXing.get(0).mana * 0.8);
		this.parry = (int) (petbeibao.petShuXing.get(0).parry * 0.8);
		this.accurate = (int) (petbeibao.petShuXing.get(0).accurate * 0.8);
		this.fangyu = (int) (petbeibao.petShuXing.get(0).wiz * 0.8);
		this.org_icon = petbeibao.petShuXing.get(0).type;
		boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
		this.shape = petbeibao.petShuXing.get(0).shape;
		this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
				123456, skills, null);
		this.type = 4;

		// 给帮凶和喽啰的伤害改调一点
//        if (name.contains("强盗") || name.contains("帮凶") || name.contains("喽啰") || name.contains("土匪")) {
//            this.shengming = new Random().nextInt(500) + 2500;
//            this.fashang = new Random().nextInt(500) + 1500;
//            this.accurate = new Random().nextInt(500) + 1500;
//        }
	}

	// 创建道宠会调用这里
	public FightObject(Chara chara, String name, int type, int level) {
		double n = 1.0;
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		double n3 = n;
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		Petbeibao petbeibao = new Petbeibao();
		petbeibao = this.petCreate(name, 110);
		List<SkillMonster> monsters = GameData.that.baseSkillMonsterService.findByName(name);
		String skills = "";
		if (monsters != null && monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); ++i) {
				if (monsters.get(i).getType() == 1) {
					skills = monsters.get(i).getSkills();
				}
			}
		}
		this.str = name;
		this.guaiwulevel = petbeibao.petShuXing.get(0).skill;
		this.shengming = (int) ((petbeibao.petShuXing.get(0).max_life * 0.5) * n3);
		this.mofa = (int) ((petbeibao.petShuXing.get(0).max_mana * 0.5 * n3) * n3);
		this.max_mofa = (int) (petbeibao.petShuXing.get(0).dex * 0.5);
		this.max_shengming = (int) ((petbeibao.petShuXing.get(0).def * 0.5 * n3) * n3);
		this.fashang = (int) ((petbeibao.petShuXing.get(0).mana * 0.5 * n3) * n3);
		this.parry = (int) ((petbeibao.petShuXing.get(0).parry * 0.5) * n3);
		this.accurate = (int) ((petbeibao.petShuXing.get(0).accurate * 0.5 * n3) * n3);
		this.fangyu = (int) ((petbeibao.petShuXing.get(0).wiz * 0.5 * n3) * n3);
		this.org_icon = petbeibao.petShuXing.get(0).type;
		boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
		if (type == 1) {
			this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill,
					isfagong, 123456, skills, null);
		} else {
			this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill,
					isfagong, 123456, skills, null);
		}
		this.type = type;

		if (this.str.contains("武学"))
			this.friend = GameUtil.baseDH(110) * 2;
		else
			this.friend = GameUtil.baseDH(110);
	}

	public boolean canAtta() {
		boolean canbe = true;
		if (this.state.get() == 2 || this.state.get() == 3) {
			canbe = false;
		}
		if (this.hasBuffState(3844) || this.hasBuffState(3856)) {
			canbe = false;
		}
		return canbe;
	}

	public boolean canbeVictim() {
		boolean canbe = true;
		if (this.state.get() == 2 || this.state.get() == 3) {
			canbe = false;
		}
		return canbe;
	}

	public FightObject(String name) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		this.str = name;
		Petbeibao petbeibao = this.petCreate(name);
		this.shengming = petbeibao.petShuXing.get(0).max_life;
		this.mofa = petbeibao.petShuXing.get(0).max_mana;
		this.max_mofa = petbeibao.petShuXing.get(0).dex;
		this.max_shengming = petbeibao.petShuXing.get(0).def;
		this.fashang = petbeibao.petShuXing.get(0).mana;
		this.parry = petbeibao.petShuXing.get(0).parry;
		this.accurate = petbeibao.petShuXing.get(0).accurate;
		this.fangyu = petbeibao.petShuXing.get(0).wiz;
		this.org_icon = petbeibao.petShuXing.get(0).type;
		boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
		this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
				123456, "", null);
		this.type = 4;
	}

	// 给守护创建为战斗对象
	public FightObject(ShouHu shouHu) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		this.str = shouHu.listShouHuShuXing.get(0).str;
		this.shengming = shouHu.listShouHuShuXing.get(0).max_life;
		this.max_shengming = shouHu.listShouHuShuXing.get(0).def;
		this.fashang = shouHu.listShouHuShuXing.get(0).mana;
		this.parry = shouHu.listShouHuShuXing.get(0).parry;
		this.accurate = shouHu.listShouHuShuXing.get(0).accurate;
		this.fangyu = shouHu.listShouHuShuXing.get(0).wiz;
		this.org_icon = shouHu.listShouHuShuXing.get(0).type;
		List<SkillMonster> monsters = (List<SkillMonster>) GameData.that.baseSkillMonsterService.findByName(this.str);
		String skills = "";
		if (monsters != null && monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); ++i) {
				if (monsters.get(i).getType() == 2) {
					skills = monsters.get(i).getSkills();
				}
			}
		}
		String[] split = skills.split("##");
		if (shouHu.listShouHuShuXing.get(0).max_degree == 0) {
			skills = split[0];
		} else {
			skills = split[1];
		}
		this.skillsList = dujineng(2, shouHu.listShouHuShuXing.get(0).metal, shouHu.listShouHuShuXing.get(0).skill,
				true, shouHu.id, skills, null);
		this.type = 3;
		this.durability = 32;
		this.rank = 2;
	}

	public Petbeibao petCreate(String name) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		Petbeibao petbeibao = new Petbeibao();
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = 1;
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 10000;
		shuXing.suit_polar = pet.getName();
		StringBuilder sb = new StringBuilder();
		PetShuXing petShuXing = shuXing;
		petShuXing.auto_fight = sb.append(petShuXing.auto_fight).append(this.id).toString();
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
		shuXing.mana_effect = pet.getLife() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.attack_effect = pet.getMana() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.mag_effect = pet.getPhyAttack() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.phy_absorb = pet.getMagAttack() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.phy_effect = pet.getSpeed() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		shuXing.skill = pet.getLevelReq();
		shuXing.attrib = pet.getLevelReq();
		int polar_point = shuXing.skill * 4;
		int addpoint = FightManager.RANDOM.nextInt(polar_point - shuXing.skill * 3);
		polar_point -= addpoint;
		shuXing.life = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.mag_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.phy_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.speed = shuXing.skill + addpoint;
		petbeibao.petShuXing.add(shuXing);
		BasicAttributesUtils.petshuxing(shuXing, petbeibao);
		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;
		petbeibao.petShuXing.add(shuXing);
		return petbeibao;
	}

	public Petbeibao petCreate(String name, int level, int type) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		Petbeibao petbeibao = new Petbeibao();
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = level;
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 10000;
		shuXing.suit_polar = pet.getName();
		StringBuilder sb = new StringBuilder();
		PetShuXing petShuXing = shuXing;
		petShuXing.auto_fight = sb.append(petShuXing.auto_fight).append(this.id).toString();
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
		shuXing.mana_effect = pet.getLife() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.attack_effect = pet.getMana() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.mag_effect = pet.getPhyAttack() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.phy_absorb = pet.getMagAttack() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.phy_effect = pet.getSpeed() - 40 - FightManager.RANDOM.nextInt(20) - 10;
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		int polar_point = shuXing.skill * 4;
		int addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.life = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.mag_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.phy_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.speed = shuXing.skill + addpoint;
		petbeibao.petShuXing.add(shuXing);
		BasicAttributesUtils.petshuxing(shuXing,petbeibao);
		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;
		petbeibao.petShuXing.add(shuXing);
		return petbeibao;
	}

	// 创建指定了名字和等级的宠物（或者怪物）
	public Petbeibao petCreate(String name, int level) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		Petbeibao petbeibao = new Petbeibao();
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = pet.getLevelReq();
		shuXing.str = pet.getName();
		shuXing.skill = level;
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 10000;
		shuXing.suit_polar = pet.getName();
		StringBuilder sb = new StringBuilder();
		PetShuXing petShuXing = shuXing;
		petShuXing.auto_fight = sb.append(petShuXing.auto_fight).append(this.id).toString();
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
		shuXing.mana_effect = pet.getLife();
		shuXing.attack_effect = pet.getMana();
		shuXing.mag_effect = pet.getPhyAttack();
		shuXing.phy_absorb = pet.getMagAttack();
		shuXing.phy_effect = pet.getSpeed();
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		int polar_point = shuXing.skill * 4;

		int addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.life = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.mag_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.phy_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.speed = shuXing.skill + addpoint;
		petbeibao.petShuXing.add(shuXing);

		BasicAttributesUtils.petshuxing(shuXing,petbeibao);

		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;
		petbeibao.petShuXing.add(shuXing);
		return petbeibao;
	}

	public Petbeibao petCreateRamMetal(String name, int level) {
		Pet pet = GameData.that.basePetService.findOneByName(name);
		Petbeibao petbeibao = new Petbeibao();
		PetShuXing shuXing = new PetShuXing();
		shuXing.type = pet.getIcon();
		shuXing.passive_mode = pet.getIcon();
		shuXing.attrib = level;
		shuXing.str = pet.getName();
		shuXing.skill = level;
		shuXing.pot = 0;
		shuXing.resist_poison = 258;
		shuXing.martial = 10000;
		shuXing.suit_polar = pet.getName();
		StringBuilder sb = new StringBuilder();
		PetShuXing petShuXing = shuXing;
		petShuXing.auto_fight = sb.append(petShuXing.auto_fight).append(this.id).toString();
		Random random = new Random();
		shuXing.metal = this.xiangxing[random.nextInt(this.xiangxing.length)];
		shuXing.mana_effect = pet.getLife();
		shuXing.attack_effect = pet.getMana();
		shuXing.mag_effect = pet.getPhyAttack();
		shuXing.phy_absorb = pet.getMagAttack();
		shuXing.phy_effect = pet.getSpeed();
		shuXing.pet_mana_shape = shuXing.mana_effect + 40;
		shuXing.pet_speed_shape = shuXing.attack_effect + 40;
		shuXing.pet_phy_shape = shuXing.phy_effect + 40;
		shuXing.pet_mag_shape = shuXing.mag_effect + 40;
		shuXing.rank = shuXing.phy_absorb + 40;
		shuXing.resist_point = shuXing.pet_mana_shape + shuXing.pet_speed_shape + shuXing.pet_phy_shape
				+ shuXing.pet_mag_shape + shuXing.rank;
		int polar_point = shuXing.skill * 4;
		int addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.life = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.mag_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.phy_power = shuXing.skill + addpoint;
		addpoint = FightManager.RANDOM.nextInt(polar_point);
		polar_point -= addpoint;
		shuXing.speed = shuXing.skill + addpoint;
		petbeibao.petShuXing.add(shuXing);
		BasicAttributesUtils.petshuxing(shuXing,petbeibao);
		shuXing.max_life = shuXing.def;
		shuXing.max_mana = shuXing.dex;
		petbeibao.petShuXing.add(shuXing);
		return petbeibao;
	}

	public static List<JiNeng> dujineng(int leixing, int pos, int level, boolean isMagic, int id, String skills,
			Petbeibao petbeibao) {
		List<JiNeng> jiNengList = new ArrayList<JiNeng>();
		List<JSONObject> nomelSkills = PetAndHelpSkillUtils.getNomelSkills(leixing, pos, level, true, skills);
		List<Integer> defaultFsId = new ArrayList<>();
		for (int i = 0; i < nomelSkills.size(); ++i) {
			JiNeng jiNeng = new JiNeng();
			JSONObject jsonObject = nomelSkills.get(i);
			jiNeng.id = id;
			jiNeng.skill_no = Integer.parseInt((String) jsonObject.get("skillNo"));
			jiNeng.skill_attrib = (int) jsonObject.get("skillLevel");
			jiNeng.skill_level = (int) jsonObject.get("skillLevel");
			jiNeng.skillRound = jsonObject.optInt("skillRound");
			jiNeng.level_improved = 0;
			jiNeng.skill_mana_cost = (int) jsonObject.get("skillBlue");
			jiNeng.skill_nimbus = 100;
			jiNeng.skill_disabled = 0;
			jiNeng.range = (int) jsonObject.get("skillNum");
			jiNeng.max_range = (int) jsonObject.get("skillNum");
			jiNengList.add(jiNeng);
			if (jsonObject.getString("skillType").equals("FS")) {
				defaultFsId.add(i);
			}
		}
		// 如果不等于空
		if (petbeibao != null && petbeibao.tianji != null && !petbeibao.tianji.isEmpty()) {
			int customFsNum = 0;
			for (JiNeng tinaji : petbeibao.tianji) {
				JSONObject jsonObject = PetAndHelpSkillUtils.jsonArray(tinaji.skill_no);
				if (jsonObject.getString("skillType").equals("FS")) {
					customFsNum++;
				}
			}
			if (defaultFsId.size() >= 3) {
				if (customFsNum > 3) {
					// 直接清除
					jiNengList.clear();
				} else {
					// 表示法伤技能已经超限了，则删除默认技能
					for (int i = 0; i < customFsNum; i++) {
						jiNengList.remove(0);
					}
				}
			}
			jiNengList.addAll(petbeibao.tianji);
		}
		return jiNengList;
	}

	public void updateState(FightContainer fightContainer, int state, int type) {
		Vo_11757_0 vo_11757_0 = new Vo_11757_0();
		vo_11757_0.id = this.fid;
		vo_11757_0.list.add(state);
		vo_11757_0.list.add(type);
		FightManager.send(fightContainer, new M11757_0(), vo_11757_0);
	}

	public void updateState(FightContainer fightContainer) {
		Vo_11757_0 vo_11757_0 = new Vo_11757_0();
		vo_11757_0.id = this.fid;
		if (this.buffState.isEmpty()) {
			vo_11757_0.list.add(0);
			vo_11757_0.list.add(0);
		} else {
			int value = 0;
			for (Integer integer : this.buffState) {
				value += integer;
			}
			vo_11757_0.list.add(value);
			vo_11757_0.list.add(32);
			
		}
		FightManager.send(fightContainer, new M11757_0(), vo_11757_0);
	}

	public void update(FightContainer fightContainer) {
		ArrayList<Integer> objects = new ArrayList<Integer>();
		objects.add(this.fid);
		objects.add(this.shengming);
		FightManager.send(fightContainer, new M64981_Fight_Blood(), objects);
		// add tzhang 更新右上角法力，暂时注释
		ArrayList<Integer> objects2 = new ArrayList<Integer>();
		objects2.add(this.fid);
		objects2.add(this.mofa);
		FightManager.send(fightContainer, new M64981_Fight_Mana(), objects2);
		// add:e
	}

	// 计算法宝的伤害，降低当前参战对象的生命值
	public int reduceShengming(int reduce, boolean fabao) {
		// 如果不能被攻击，则不减少生命值
		if (!this.canbeHurt()) {
			return 0;
		}
		FightContainer fightContainer = FightManager.getFightContainer(this.fid);
		if (this.hasBuffState(3844)) {
			this.removeBuffSK(fightContainer, 3844);
		}
		if (fabao) {
			FightFabaoSkill fabaoSkill = this.getFabaoSkill();
			// 定海珠
			if (fabaoSkill != null && fabaoSkill.getStateType() == 8015 && fabaoSkill.isActive()) {
				fabaoSkill.sendEffect(fightContainer);
				reduce = 0;
			}
		}

		if (this.shengming <= reduce) {
			reduce = this.shengming;
			this.shengming = 0;
			if (this.type == 1 || this.type == 3) {
				this.state.set(6);
				return reduce;
			} else if (this.canbeRevive() && this.type == 1) {
				this.state.set(6);
				return reduce;
			} else {
				this.state.set(7);
			}
			// 为宠物的时候
			if (this.type == 2) {
				if (GameUtil.fh(this.petType, this.shape) && this.currentReviveTimes <= 9) {
					Vo_C_ACTION vo_19959_0 = new Vo_C_ACTION();
					vo_19959_0.round = fightContainer.round;
					vo_19959_0.aid = this.fid;
					vo_19959_0.action = 41;
					vo_19959_0.vid = this.fid;
					vo_19959_0.para = 0;
					FightManager.send(fightContainer, new MSG_C_ACTION(), vo_19959_0);
					String[] reviceMsg = {"都说猫有九条命，可是我有十条命哟！他们是打不死我的！","还好没打中我的要害，看来我要把压箱底的功夫使出来！","主人不要为我担心，凭他们的三脚猫的功夫，是杀不死我的！"};
					FightManager.send(fightContainer, new MSG_C_SET_CUSTOM_MSG(), GameCommonUtil.getAutoTalkObj(this.fid, reviceMsg[ThreadLocalRandom.current().nextInt(reviceMsg.length)], 1));
					++this.currentReviveTimes; // 增加当前的复活次数
					this.state.set(1);
					this.revive(fightContainer);
					this.addShengming(this.max_shengming);
					FightManager.send(fightContainer, new MSG_C_END_ACTION(), new Vo_C_END_ACTION(this.fid));
					this.isRevive = true;
					log.info("--{},{}=复活了",this.str,this.fid);
					return reduce;
				}
			}
		} else {
			this.shengming -= reduce;
		}
		return reduce <= 0 ? 1 : reduce;
	}

	public int addShengming(int reduce) {
		if (this.shengming + reduce <= this.max_shengming) {
			this.shengming += reduce;
		} else {
			reduce = this.max_shengming;
			this.shengming = this.max_shengming;
		}
		return reduce;
	}
	
	/**
	 * 添加魔法
	 * @param reduce
	 * @return
	 */
	public int addMoFa(int reduce) {
		if (this.mofa + reduce <= this.max_mofa) {
			this.mofa += reduce;
		} else {
			reduce = this.max_mofa - this.mofa;
			this.mofa = this.max_mofa;
		}
		//log.info("加魔法--------------------------------------={}",reduce);
		return reduce;
	}

	// 再给对象添加新的障碍技能时需要先移除其他的障碍技能
	public void addBuffState(FightContainer fightContainer, Integer state) {
		if (this.isBingdong()) {
			return;
		}
		if (!this.buffState.contains(state)) {
			this.buffState.add(state);
			this.updateState(fightContainer);
		}
		switch (state) {
		case 20224: {
			this.removeBuffSK(fightContainer, 12032); // 覆盖火的辅助技能
			break;
		}
		case 12032: {
			this.removeBuffSK(fightContainer, 20224);
			break;
		}
		case 134912: {
			this.removeBuffSK(fightContainer, 265984);
			break;
		}
		case 265984: {
			this.removeBuffSK(fightContainer, 134912);
			break;
		}
		case 3848: {
			this.removeBuffSK(fightContainer, 3856); // 冰冻
			this.removeBuffSK(fightContainer, 3844); // 昏睡
			this.removeBuffSK(fightContainer, 3872); // 混乱
			break;
		}
		case 3842: {
			// 冰冻
			this.removeBuffSK(fightContainer, 3856);
			// 昏睡
			this.removeBuffSK(fightContainer, 3844);
			// 混乱
			this.removeBuffSK(fightContainer, 12032);
			// 辅助金
			this.removeBuffSK(fightContainer, 20224);
			// 辅助土
			this.removeBuffSK(fightContainer, 134912);
			// 辅助水
			this.removeBuffSK(fightContainer, 265984);
			// 辅助火
			this.removeBuffSK(fightContainer, 12032);
			break;
		}
		case 3856: {
			this.removeBuffSK(fightContainer, 3848);
			this.removeBuffSK(fightContainer, 3844);
			this.removeBuffSK(fightContainer, 3872);
			this.removeBuffSK(fightContainer, 3842);
			break;
		}
		case 3844: {
			this.removeBuffSK(fightContainer, 3848);
			this.removeBuffSK(fightContainer, 3856);
			this.removeBuffSK(fightContainer, 3872);
//			this.removeBuffSK(fightContainer, 3842);//木毒
			break;
		}
		case 3872: {
			this.removeBuffSK(fightContainer, 3848);
			this.removeBuffSK(fightContainer, 3856);
			this.removeBuffSK(fightContainer, 3844);
			break;
		}
		case 0x80000000: {
			this.removeBuffSK(fightContainer, 0x40000000);
			this.removeBuffSK(fightContainer, 0x09000000);
			break;
		}
		case 0x40000000: {
			this.removeBuffSK(fightContainer, 0x80000000);
			this.removeBuffSK(fightContainer, 0x09000000);
			break;
		}
		case 0x09000000: {
			this.removeBuffSK(fightContainer, 0x80000000);
			this.removeBuffSK(fightContainer, 0x40000000);
			break;
		}
		}
	}

	public void removeBuffSK(FightContainer fightContainer, Integer state) {
		this.removeBuffState(fightContainer, state);
		this.removeFightSkill(state);
	}

	public void removeBuffState(FightContainer fightContainer, Integer state) {
		if (this.buffState.remove(state)) {
			this.updateState(fightContainer);
		}
	}

	// 添加角色的战斗技能，法宝技能会加到这里
	// 添加障碍技能也会到这里
	// 添加防御也在这里
	public void addSkill(FightSkill fightSkill) {
		List<FightRoundSkill> roundSkill = this.getRoundSkill();
		for (FightRoundSkill skill : roundSkill) {
			if (skill.getStateType() == fightSkill.getStateType()) {
				this.removeSkill(skill);
			}
		}
		this.fightSkillList.add(fightSkill);
	}

	public void removeSkill(FightSkill fightSkill) {
		this.fightSkillList.remove(fightSkill);
	}

	public void removeFightSkill(int buffstate) {
		List<FightRoundSkill> roundSkill = this.getRoundSkill();
		for (FightRoundSkill fightRoundSkill : roundSkill) {
			if (fightRoundSkill.getStateType() == buffstate) {
				this.fightSkillList.remove(fightRoundSkill);
			}
		}
	}

	// 将战斗容器中的角色复活
	public void revive(FightContainer fightContainer) {
		
		this.update(fightContainer);
		
		FightResult fightResult = new FightResult();
		fightResult.id = this.fid;
		fightResult.vid = this.fid;
		fightResult.point = this.shengming;
		fightResult.effect_no = 10005;
		fightResult.damage_type = 0;
		FightManager.send_LIFE_DELTA(fightContainer, fightResult);
		
		Vo_7667_0 vo_7667_0 = new Vo_7667_0();
		vo_7667_0.id = this.fid;
		FightManager.send(fightContainer, new M7667_0(), vo_7667_0);
		this.update(fightContainer);
	}
	

	// 判断是否有木的复活效果
	public boolean canbeRevive() {
		return this.hasBuffState(528128);
	}

	//3848:金系  3872:土
	public boolean canbeSkill() {
		
		return !this.hasBuffState(3848) && !this.hasBuffState(3872);
	}

	public boolean canbeHurt() {
		return !this.hasBuffState(3856);
	}

	public boolean isHunluan() {
		return this.hasBuffState(3872);
	}

	public boolean isZhongdu() {
		return this.hasBuffState(3842);
	}

	public boolean isYiwang() {
		return this.hasBuffState(3848);
	}

	public boolean isBingdong() {
		return this.hasBuffState(3856);
	}

	public boolean isRun() {
		return this.run;
	}

	public boolean hasBuffState(int buff) {
		if (this.buffState == null)
			return false;
		for (Integer integer : this.buffState) {
			if (integer == buff) {
				return true;
			}
		}
		return false;
	}

	public FightObject getBuffValue(int buff) {
		if (this.buffState == null)
			return null;
		for (Integer integer : this.buffState) {
			if (integer == buff) {
				return this;
			}
		}
		return null;
	}

	/**
	 * 天地星
	 * 
	 * @param chara
	 * @param name
	 * @param vo_65529_0
	 */
	public FightObject(Chara chara, String name, Vo_APPEAR vo_65529_0) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		int suit_iconlinshi = 0;
		int suit_light_effectlinshi = 0;
		int icon = 0;
		FightObjectInfo fightObjectInfo = null;
		int leixing = ThreadLocalRandom.current().nextInt(5) + 1;
		int sex = ThreadLocalRandom.current().nextInt(2) + 1;
		icon = GameUtil.getWaiguan(leixing, sex, null);
		vo_65529_0.org_icon = vo_65529_0.icon;
		if (vo_65529_0.level >= 70) {
			int[] suit = SuitEffectUtils.suit(sex - 1, vo_65529_0.level, leixing,
					ThreadLocalRandom.current().nextInt(5) + 1);
			suit_iconlinshi = suit[0];
			suit_light_effectlinshi = suit[1];
		}
		List<FightObjectInfo> fightObjectInfos = GameData.that.baseFightObjectService.findByName(name);
		if (fightObjectInfos == null || fightObjectInfos.isEmpty()) {
			log.error("找不到该怪物的配置:{}", name);
			throw new FightException();
		}
		fightObjectInfo = fightObjectInfos.get(ThreadLocalRandom.current().nextInt(fightObjectInfos.size()));
		int weapon_iconlingshi = vo_65529_0.weapon_icon;
		List<ZhuangbeiInfo> infoList = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService
				.findByAttrib(vo_65529_0.level / 10 * 10);
		for (ZhuangbeiInfo zhuangbeiInfo : infoList) {
			if (zhuangbeiInfo.getAmount() == 1 && zhuangbeiInfo.getMetal() == leixing) {
				weapon_iconlingshi = zhuangbeiInfo.getType();
				break;
			}
		}
		if("星宿".equals(fightObjectInfo.getType())){
			//这里是星宿。 所有的属性 以70级  为基准， 每级提高百分之5
			int lv = vo_65529_0.level;
			try{
				if(lv>0){
					Double lvcha = (lv -70)*0.05;
					this.shengming = (int)Math.round(fightObjectInfo.getLife()*(1+lvcha));//fightObjectInfo.getLife();
					this.max_shengming = (int)Math.round(fightObjectInfo.getLife()*(1+lvcha));//fightObjectInfo.getLife();
					this.mofa = (int)Math.round(fightObjectInfo.getMana()*(1+lvcha));//fightObjectInfo.getLife();
					this.max_mofa = (int)Math.round(fightObjectInfo.getMana()*(1+lvcha));//fightObjectInfo.getLife();
					this.fashang = (int)Math.round(fightObjectInfo.getMagAttack()*(1+lvcha));//fightObjectInfo.getLife();
					this.parry = (int)Math.round(fightObjectInfo.getSpeed()*(1+lvcha));//fightObjectInfo.getLife();
					this.accurate = (int)Math.round(fightObjectInfo.getPhyAttack()*(1+lvcha));//fightObjectInfo.getLife();
					this.fangyu = (int)Math.round(fightObjectInfo.getDef()*(1+lvcha));//fightObjectInfo.getLife();
				}
			}catch(Exception e){
				log.info("星宿出错，{}",e);
			}
		}else{
			this.shengming = fightObjectInfo.getLife();
			this.max_shengming = fightObjectInfo.getLife();
			this.mofa = fightObjectInfo.getMana();
			this.max_mofa = fightObjectInfo.getMana();
			this.fashang = fightObjectInfo.getMagAttack();
			this.parry = fightObjectInfo.getSpeed();
			this.accurate = fightObjectInfo.getPhyAttack();
			this.fangyu = fightObjectInfo.getDef();

		}
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.str = vo_65529_0.name;
		this.guaiwulevel = vo_65529_0.level;
		this.org_icon = icon;
		this.weapon_icon = weapon_iconlingshi;
		this.friend = fightObjectInfo.getDaohang();
		this.suit_icon = suit_iconlinshi;
		this.suit_light_effect = suit_light_effectlinshi;
		int level = 0;
	//	log.info("vo_65529_0.name:"+vo_65529_0.name+"  vo_65529_0.level："+vo_65529_0.level);
		if(vo_65529_0.level>=100){
			 level = (int)(vo_65529_0.level*1.6);
		}else{
			level = vo_65529_0.level;
		}
		if (!StringUtils.isNullOrEmpty(fightObjectInfo.getSkill())) {
			String[] split = fightObjectInfo.getSkill().split("\\#");
			String skillStr = "";
			if (leixing > split.length) {
				skillStr = split[split.length - 1];
			} else {
				skillStr = split[leixing - 1];
			}
			if (!StringUtils.isNullOrEmpty(skillStr)) {
				// 随机从里面选一个职业技能
				this.skillsList = getFightObjectJiNengListByName(level, 123456, skillStr);
			} else {
				this.skillsList = getJiNengListByName(leixing,level, 123456,
						GameUtil.getRandomSkills(leixing));
			}
		} else {
			this.skillsList = getJiNengListByName(leixing, level, 123456, GameUtil.getRandomSkills(leixing),
					"ZA");
		}
		this.type = 4;
		this.isGuaiWuHide = 1;
		// 战斗属性
		this.fightAttribute = DynamicAttributesService.fightAttribute(fightObjectInfo);
		this.uid = "天地星";
		this.fightType = "天地星";
	}

	@SuppressWarnings("unused")
	// 这里是创建上古妖王及其小怪、星的战斗对象
	public FightObject(Chara chara, String name, Vo_APPEAR vo_65529_0, Object obj) {
		this.xiangxing = new int[] { 1, 2, 3, 4, 5 };
		this.buffState = new ArrayList<Integer>();
		this.fightSkillList = new ArrayList<FightSkill>();
		this.autofight_select = 0;
		Random random = new Random();
		String strname = name;
		new Petbeibao();
		String replace = "";
		if (name.length() > 1) {
			String substring = name.substring(1, 2);
			replace = name.replace(substring, "");
		}

		int suit_iconlinshi = 0;
		int suit_light_effectlinshi = 0;
		int icon = 0;
		if ((replace.equals("天星") || replace.equals("地星")) && name.equals(vo_65529_0.name)) {
			suit_iconlinshi = vo_65529_0.suit_icon;
			suit_light_effectlinshi = vo_65529_0.suit_light_effect;
			icon = vo_65529_0.icon;
			if (vo_65529_0.leixing == 1) {
				strname = "金星";
			}
			if (vo_65529_0.leixing == 2) {
				strname = "木星";
			}
			if (vo_65529_0.leixing == 3) {
				strname = "水星";
			}
			if (vo_65529_0.leixing == 4) {
				strname = "火星";
			}
			if (vo_65529_0.leixing == 5) {
				strname = "土星";
			}
		}
		if (name.equals("星")) {
			int leixing = random.nextInt(5) + 1;
			if (leixing == 1) {
				strname = "金星";
			}
			if (leixing == 2) {
				strname = "木星";
			}
			if (leixing == 3) {
				strname = "水星";
			}
			if (leixing == 4) {
				strname = "火星";
			}
			if (leixing == 5) {
				strname = "土星";
			}
			int sex = random.nextInt(1) + 1;
			icon = GameShuaGuai.waiguan(leixing, sex);
			int[] suit = SuitEffectUtils.suit(sex - 1, vo_65529_0.level, leixing, random.nextInt(5) + 1);
			vo_65529_0.org_icon = vo_65529_0.icon;
			suit_iconlinshi = suit[0];
			suit_light_effectlinshi = suit[1];
			name = vo_65529_0.name;
		}

		Petbeibao petbeibao = this.petCreate(strname, vo_65529_0.level);
		List<SkillMonster> monsters = GameData.that.baseSkillMonsterService.findByName(strname);
		String skills = "";
		if (monsters != null && monsters.size() > 0) {
			for (int i = 0; i < monsters.size(); ++i) {
				if (monsters.get(i).getType() == 1) {
					skills = monsters.get(i).getSkills();
				}
			}
		}

		int i = 0;
		List<ZhuangbeiInfo> infoList = GameData.that.baseZhuangbeiInfoService.findByAttrib(vo_65529_0.level / 10 * 10);
		for (ZhuangbeiInfo zhuangbeiInfo : infoList) {
			if (zhuangbeiInfo.getAmount() == 1 && zhuangbeiInfo.getMetal() == petbeibao.petShuXing.get(0).metal) {
				i = zhuangbeiInfo.getType();
			}
		}
		if ("将夜·琵琶精将夜·骷髅怪将夜·千面怪将夜·狐狸精上古妖王".contains(name)) {
			icon = petbeibao.petShuXing.get(0).type;
		}
		this.str = name;
		this.guaiwulevel = vo_65529_0.level;
		this.shengming = (int) (petbeibao.petShuXing.get(0).max_life * 0.8);
		this.mofa = (int) (petbeibao.petShuXing.get(0).max_mana * 0.8);
		this.max_mofa = (int) (petbeibao.petShuXing.get(0).dex * 0.8);
		this.max_shengming = (int) (petbeibao.petShuXing.get(0).def * 0.8);
		this.fashang = (int) (petbeibao.petShuXing.get(0).mana * 0.8);
		this.parry = (int) (petbeibao.petShuXing.get(0).parry * 0.8);
		this.accurate = (int) (petbeibao.petShuXing.get(0).accurate * 0.8);
		this.fangyu = (int) (petbeibao.petShuXing.get(0).wiz * 0.8);
		this.org_icon = petbeibao.petShuXing.get(0).type;
		this.suit_light_effect = suit_light_effectlinshi;
		this.friend = (int) (0.29 * vo_65529_0.level * vo_65529_0.level * vo_65529_0.level * 0.29 * vo_65529_0.level
				* vo_65529_0.level * vo_65529_0.level);
		boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
		this.skillsList = dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
				123456, skills, null);
		this.type = 4;
	}

	@Override
	public String toString() {
		return "FightObject [fid=" + fid + ", str=" + str + ", shengming=" + shengming + ", fangyu=" + fangyu
				+ ", accurate=" + accurate + ", fashang=" + fashang + ", parry=" + parry + "]";
	}

	private void fulingAttr(Chara chara) {
		this.accurate += chara.zhenlingPhy;
		this.fashang += chara.zhenlingMag;
		this.parry += chara.zhenlingSpeed;
		this.fangyu += chara.zhenlingDef;
		// 附灵附身
		if (chara.zhenlingType == 1) {
			// 法伤10%
			int mana = (int) (this.fashang
					* GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0] / 100);
			this.fashang += mana;
			// 其他5%
			this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
					/ 100 / 2);
			this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2] / 100
					/ 2);
			this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
					/ 100 / 2);
			this.zhenlingLevel = chara.qinglongZhenlingLevel;
		} else if (chara.zhenlingType == 2) {
			// 物伤10%
			this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
					/ 100);
			// 其他5%
			this.fashang += (this.fashang * GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]
					/ 100 / 2);
			this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2] / 100
					/ 2);
			this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
					/ 100 / 2);
			this.zhenlingLevel = chara.baihuhenlingLevel;
		} else if (chara.zhenlingType == 3) {
			// 速度10%
			this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2]
					/ 100);
			// 其他5%
			this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
					/ 100 / 2);
			this.fashang += (this.fashang * GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]
					/ 100 / 2);
			this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
					/ 100 / 2);
			this.zhenlingLevel = chara.zhuqueZhenlingLevel;
		} else if (chara.zhenlingType == 4) {
			// 防御1%
			this.fangyu += (this.fangyu * GameConfig.spiritInfoConfig.get(chara.xuanwuZhenlingLevel - 1<0?0:chara.xuanwuZhenlingLevel-1).getAtt()[3]
					/ 100);
			// 其他5%
			this.accurate += (this.accurate * GameConfig.spiritInfoConfig.get(chara.baihuhenlingLevel - 1<0?0:chara.baihuhenlingLevel-1).getAtt()[1]
					/ 100 / 2);
			this.fashang += (this.fashang * GameConfig.spiritInfoConfig.get(chara.qinglongZhenlingLevel - 1<0?0:chara.qinglongZhenlingLevel-1).getAtt()[0]
					/ 100 / 2);
			this.parry += (this.parry * GameConfig.spiritInfoConfig.get(chara.zhuqueZhenlingLevel - 1<0?0:chara.zhuqueZhenlingLevel-1).getAtt()[2] / 100
					/ 2);
			this.zhenlingLevel = chara.xuanwuZhenlingLevel;
		}
	}
}