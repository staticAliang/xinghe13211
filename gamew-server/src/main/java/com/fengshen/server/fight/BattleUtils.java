package com.fengshen.server.fight;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;


public class BattleUtils {
	public static List<Integer> SKILL_INDEX_LIST;
	public static List<Integer> SKILL_PERCENT_POINT_LIST;

	public static Hashtable<String, Integer>[] battlePosition(List<Hashtable<String, String>> list,
			List<String> monsters) {
		ConcurrentLinkedQueue<Integer> mineQueueFront = new ConcurrentLinkedQueue<Integer>();
		mineQueueFront.offer(3);
		mineQueueFront.offer(2);
		mineQueueFront.offer(4);
		mineQueueFront.offer(1);
		mineQueueFront.offer(5);
		ConcurrentLinkedQueue<Integer> mineQueueBehind = new ConcurrentLinkedQueue<Integer>();
		mineQueueBehind.offer(8);
		mineQueueBehind.offer(7);
		mineQueueBehind.offer(9);
		mineQueueBehind.offer(6);
		mineQueueBehind.offer(10);
		ConcurrentLinkedQueue<Integer> monsterQueueFront = new ConcurrentLinkedQueue<Integer>();
		monsterQueueFront.offer(8);
		monsterQueueFront.offer(7);
		monsterQueueFront.offer(9);
		monsterQueueFront.offer(6);
		monsterQueueFront.offer(10);
		ConcurrentLinkedQueue<Integer> monsterQueueBehind = new ConcurrentLinkedQueue<Integer>();
		monsterQueueBehind.offer(3);
		monsterQueueBehind.offer(2);
		monsterQueueBehind.offer(4);
		monsterQueueBehind.offer(1);
		monsterQueueBehind.offer(5);
		Hashtable<String, Integer> minePositionTab = new Hashtable<String, Integer>();
		if (!list.isEmpty()) {
			for (Hashtable<String, String> hs : list) {
				String personValue = hs.get("person");
				String petValue = hs.get("pet");
				minePositionTab.put(personValue, mineQueueBehind.poll());
				int petPosition = mineQueueFront.poll();
				if (null != petValue && !petValue.isEmpty()) {
					minePositionTab.put(petValue, petPosition);
				}
			}
		}
		Hashtable<String, Integer> monsterPositionTab = new Hashtable<String, Integer>();
		if (!monsters.isEmpty()) {
			for (String monster : monsters) {
				if (monsterQueueBehind.isEmpty()) {
					monsterPositionTab.put(monster, monsterQueueFront.poll());
				} else {
					monsterPositionTab.put(monster, monsterQueueBehind.poll());
				}
			}
		}
		@SuppressWarnings("unchecked")
		Hashtable<String, Integer>[] hashtables = new Hashtable[] { minePositionTab,
				monsterPositionTab };
		return hashtables;
	}

	public static Hashtable<String, Integer>[] battlePkPosition(List<Hashtable<String, String>> list,
			List<Hashtable<String, String>> monsterList) {
		ConcurrentLinkedQueue<Integer> mineQueueFront = new ConcurrentLinkedQueue<Integer>();
		mineQueueFront.offer(3);
		mineQueueFront.offer(2);
		mineQueueFront.offer(4);
		mineQueueFront.offer(1);
		mineQueueFront.offer(5);
		ConcurrentLinkedQueue<Integer> mineQueueBehind = new ConcurrentLinkedQueue<Integer>();
		mineQueueBehind.offer(8);
		mineQueueBehind.offer(7);
		mineQueueBehind.offer(9);
		mineQueueBehind.offer(6);
		mineQueueBehind.offer(10);
		ConcurrentLinkedQueue<Integer> monsterQueueFront = new ConcurrentLinkedQueue<Integer>();
		monsterQueueFront.offer(8);
		monsterQueueFront.offer(7);
		monsterQueueFront.offer(9);
		monsterQueueFront.offer(6);
		monsterQueueFront.offer(10);
		ConcurrentLinkedQueue<Integer> monsterQueueBehind = new ConcurrentLinkedQueue<Integer>();
		monsterQueueBehind.offer(3);
		monsterQueueBehind.offer(2);
		monsterQueueBehind.offer(4);
		monsterQueueBehind.offer(1);
		monsterQueueBehind.offer(5);
		Hashtable<String, Integer> minePositionTab = new Hashtable<String, Integer>();
		if (!list.isEmpty()) {
			for (Hashtable<String, String> hs : list) {
				String personValue = hs.get("person");
				String petValue = hs.get("pet");
				minePositionTab.put(personValue, mineQueueBehind.poll());
				int petPosition = mineQueueFront.poll();
				if (null != petValue && !petValue.isEmpty()) {
					minePositionTab.put(petValue, petPosition);
				}
			}
		}
		Hashtable<String, Integer> monsterPositionTab = new Hashtable<String, Integer>();
		if (!monsterList.isEmpty()) {
			for (Hashtable<String, String> hs2 : monsterList) {
				String personValue2 = hs2.get("person");
				String petValue2 = hs2.get("pet");
				monsterPositionTab.put(personValue2, monsterQueueBehind.poll());
				int petPosition2 = monsterQueueFront.poll();
				if (null != petValue2 && !petValue2.isEmpty()) {
					monsterPositionTab.put(petValue2, petPosition2);
				}
			}
		}
		@SuppressWarnings("unchecked")
		Hashtable<String, Integer>[] hashtables = new Hashtable[] { minePositionTab, monsterPositionTab };
		return hashtables;
	}

	/**
	 * 法术加成基础伤害
	 * 
	 * @param skillLevel
	 * @return
	 */
	public static int getSkillBaseAttack(int skillLevel) {
		int skillAttack = 0;
		if (skillLevel >= 0 && skillLevel <= 30) {
			skillAttack = 15;
		} else if (skillLevel >= 31 && skillLevel <= 50) {
			skillAttack = 25;
		} else if (skillLevel >= 51 && skillLevel <= 70) {
			skillAttack = 35;
		} else if (skillLevel >= 71 && skillLevel <= 90) {
			skillAttack = 45;
		} else if (skillLevel >= 91 && skillLevel <= 110) {
			skillAttack = 55;
		} else if (skillLevel >= 111 && skillLevel <= 130) {
			skillAttack = 65;
		} else if (skillLevel >= 131 && skillLevel <= 150) {
			skillAttack = 75;
		} else if (skillLevel >= 151 && skillLevel <= 170) {
			skillAttack = 95;
		} else if (skillLevel >= 171 && skillLevel <= 190) {
			skillAttack = 95;
		} else if (skillLevel >= 191 && skillLevel <= 206) {
			skillAttack = 105;
		} else if (skillLevel > 206) {
			skillAttack = 115;
		}
		return skillAttack;
	}

	public static void main(String[] args) {

	}

	public static int produceRandomNumber(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}

	public static int monsterCount(int attrib, int teamMember, int monsterAttrib) {
		if (monsterAttrib <= 15 || attrib < 10) {
			return produceRandomNumber(1, 2);
		}
		if (monsterAttrib <= 35 || attrib < 25) {
			return produceRandomNumber(2, 3);
		}
		if (monsterAttrib <= 50 || attrib < 35) {
			return produceRandomNumber(3, 5);
		}
		return produceRandomNumber(teamMember, (teamMember + 3 < 10) ? (teamMember + 3) : 10);
	}

	// 计算攻击的伤害
	public static int battle(int baseAttack, int skillAttack, int monsterDefense) {
//		double jp = monsterDefense * 0.9 / (baseAttack + skillAttack+monsterDefense);
		int hurt = baseAttack + skillAttack;
//        if (hurt != 0) {
//        	if(hurt>1000) {
//        		int rfudong = FightManager.RANDOM.nextInt(1000);
//        		hurt += rfudong;
//        	}
//        }
        //这里扣除百分之55的防御值
        int subHurt = (int) (monsterDefense*0.55);
        hurt-=subHurt;
		return hurt<=0?1:hurt;
	}

	/**
	 * 计算伤害
	 * 
	 * @param baseAttack     基础伤害
	 * @param skillAttack    技能加成
	 * @param monsterDefense 防御
	 * @param fightObject 攻击发起者
	 * @param oppentFightObject 被打者
	 * @return
	 */

	// （物伤×2-防)/2
	public static int battleWuGong(int baseAttack, int skillAttack, int monsterDefense, FightObject fightObject, 
			FightObject oppentFightObject) {
		//防御百分之10
		int subMonsterDefense = (int) (monsterDefense*0.1);
		int hurt =  (int) ((baseAttack + skillAttack) - subMonsterDefense);
//		 double jp = monsterDefense * 1.0 / (baseAttack + skillAttack + monsterDefense);
//		int hurt = (int) ((baseAttack + skillAttack) * (1.0 - jp * jp) * 0.87);
		return hurt < 0 ? 1 : hurt;
	}

	public static int skillAttack(int baseAttack, int skillLeave, String skillType, int skillPara) {
		if (skillType.contentEquals("WS")) {
			int addAttack = (int) (getSkillBaseAttack(skillLeave) + baseAttack * 0.2);
			return addAttack;
		}
		int skillIndex = getSkillIndex(skillPara);
		double[] fs = { 0.0, 7.98084596967E-4, 0.002594253790902, 0.003575099760575, 0.003088507581804 };
		if (skillType.contentEquals("FS")) {
			if (skillPara == 900 || skillPara == 904 || skillPara == 702 || skillPara == 710 || skillPara == 703
					|| skillPara == 704) {
				return (int) (fs[0] * skillLeave * baseAttack * 0.8);
			}
			double fsValue = fs[skillIndex];
			int hurt = (int) (fsValue * skillLeave * baseAttack * 0.8);
			return hurt;
		}else if(skillType.contentEquals("BS")) {//超级技能 
			return (int) (fs[0] * skillLeave * baseAttack * 1.2);
		}
		return 0;
	}

	public static double extAdd(int skillLeave, int skillPara) {
		int skillIndex = getSkillIndex(skillPara);
		int[] base_pro = { 10, 17, 25, 50, 40 };
		double append = skillLeave * 1.0 / 208 * 0.3;
		return base_pro[skillIndex] * (1.0 + append);
	}

	public static void init() {
		BattleUtils.SKILL_INDEX_LIST.add(11);
		BattleUtils.SKILL_INDEX_LIST.add(21);
		BattleUtils.SKILL_INDEX_LIST.add(31);
		BattleUtils.SKILL_INDEX_LIST.add(61);
		BattleUtils.SKILL_INDEX_LIST.add(71);
		BattleUtils.SKILL_INDEX_LIST.add(81);
		BattleUtils.SKILL_INDEX_LIST.add(110);
		BattleUtils.SKILL_INDEX_LIST.add(121);
		BattleUtils.SKILL_INDEX_LIST.add(131);
		BattleUtils.SKILL_INDEX_LIST.add(161);
		BattleUtils.SKILL_INDEX_LIST.add(171);
		BattleUtils.SKILL_INDEX_LIST.add(181);
		BattleUtils.SKILL_INDEX_LIST.add(210);
		BattleUtils.SKILL_INDEX_LIST.add(221);
		BattleUtils.SKILL_INDEX_LIST.add(231);
		BattleUtils.SKILL_INDEX_LIST.add(900);
		Collections.reverse(BattleUtils.SKILL_INDEX_LIST);
		BattleUtils.SKILL_PERCENT_POINT_LIST.add(15);
		BattleUtils.SKILL_PERCENT_POINT_LIST.add(20);
		BattleUtils.SKILL_PERCENT_POINT_LIST.add(30);
		BattleUtils.SKILL_PERCENT_POINT_LIST.add(65);
		BattleUtils.SKILL_PERCENT_POINT_LIST.add(50);
	}

	public static int getSkillIndex(int para) {
		for (Integer index : BattleUtils.SKILL_INDEX_LIST) {
			if (index <= para) {
				return para - index;
			}
		}
		return 0;
	}

	static {
		SKILL_INDEX_LIST = new ArrayList<Integer>();
		SKILL_PERCENT_POINT_LIST = new ArrayList<Integer>();
	}
}