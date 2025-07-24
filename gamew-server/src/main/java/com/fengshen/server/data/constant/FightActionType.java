package com.fengshen.server.data.constant;

public enum FightActionType {

	DEFENSE("防御",1),
	PHYSICAL_ATTACK("物理攻击 ",2),
	CAST_MAGIC("施展魔法",3),
	APPLY_ITEM("使用道具",4),
	USE_ARTIFACT("使用宝物",5),
	USE_STUNT("施展绝技 ",6),
	FLEE("逃跑",7),
	SELECT_PET("选择宠物出战 ",8),
	CATCH_PET("捕捉宠物",9),
	GUARD("保护 ",10),
	JOINT_ATTACK("合击 ",11),
	DOUBLE_HIT("连击",12),
	LEECH_MANA("吸魔",13),
	CALLBACK_PET("召回宠物",14),
	ACTION_USE_ARTIFACT_EXTRA_SKILL("使用法宝特殊技能 ",16),
	DIE("死亡",40),
	REVIVE("重生",41),
	HEAL("治疗",42),
	CHECK_STATUS("检查状态",43),
	COUNTER_ATTACK("反击 ",44),
	SELECT_MENU("选择菜单",45),
	DISAPPEAR("直接消失",46),
	DEADLY_KISS("死亡缠绵",47),
	DOUBLE_MAGIC_HIT("法术攻击双击",48),
	CANCEL("取消输入",50),
	SPECIAL("特殊动作",98),
	NULL("不做动作",99);
	
	private String action;
	private Integer no;

	private FightActionType(String action, Integer no) {
		this.action = action;
		this.no = no;
	}
	
	public static String getValueByKey(int no) {
		for (FightActionType p : FightActionType.values()) {
			if (p.getNo().equals(no)) {
				return p.getAction();
			}
		}
		return "";
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}
}