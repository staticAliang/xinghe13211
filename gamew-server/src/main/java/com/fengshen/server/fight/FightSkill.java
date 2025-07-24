package com.fengshen.server.fight;

import java.util.List;

import com.fengshen.server.domain.JiNeng;
import com.fengshen.server.fight.horcrux.FightHorcruxBoMu;
import com.fengshen.server.fight.tj.FightSheMingYiJi;
import com.fengshen.server.fight.tj.FightSheShenQuYi;

public interface FightSkill {
	List<FightResult> doSkill(final FightContainer fightContainer, final FightRequest fightRequest,
							  final JiNeng jiNeng);

	int getStateType();

	static FightFabaoSkill getFabaoSkill(final String name) {
		switch (name) {
			case "番天印": {
				return new FantianyingSkill();
			}
			case "定海珠": {
				return new DinghaizhuSkill();
			}
			case "混元金斗": {
				return new HunyuanjindouSkill();
			}
			case "阴阳镜": {
				return new YinyangjingSkill();
			}
			case "卸甲金葫": {
				return new XiejiajinhuSkill();
			}
			default: {
				return null;
			}
		}
	}

	// 对技能类型进行分类
	// 如果不是辅助技能，就返回true，是辅助技能就返回false
	static boolean isOpSkill(final int para) {
		switch (para) {
			case 31:
			case 32:
			case 33:
			case 34:
			case 35:
			case 81:
			case 82:
			case 83:
			case 84:
			case 85:
			case 131:
			case 132:
			case 133:
			case 134:
			case 135:
			case 181:
			case 182:
			case 183:
			case 184:
			case 185:
			case 231:
			case 232:
			case 233:
			case 234:
			case 254: // 如意圈
			case 259: // 乾坤罩
			case 260: // 神龙圈
			case 235: {
				return false;
			}
			default: {
				return true;
			}
		}
	}

	// 通过战斗action和参数，返回对应的技能
	static FightSkill getFightSkill(final int action, final int para) {
		switch (action) {
			case 1: { // 防御技能
				return new DefenseSkill();
			}
			case 2: { // 物攻
				return new NormalAttackSkill();
			}
			case 3: { // 攻击技能
				switch (para) {
					case 11: // 金光乍现 ：金
					case 12: // 刀光剑影
					case 13: // 金虹贯日
					case 14: // 流光异彩
					case 15: // 逆天残刃
					case 61: // 摘叶飞花 ：木
					case 62: // 飞柳仙矢
					case 63: // 盘根错节
					case 64: // 落叶缤纷
					case 65: // 鬼舞枯藤
					case 110: // 滴水穿石 ：水
					case 111: // 雨恨云愁
					case 112: // 悬河泻水
					case 113: // 怒波狂涛
					case 114: // 搅海翻江
					case 161: // 举火焚天 ：火
					case 162: // 星火燎原
					case 163: // 焰天火雨
					case 164: // 焦金砾石
					case 165: // 炼狱火海
					case 210: // 落土飞岩 ：土
					case 211: // 土没尘埋
					case 212: // 山崩地裂
					case 213: // 天塌地陷
					case 214: // 石破天惊
					{
						// 全部是法术攻击
						return new FightMagPowerSkill();
					}
					case 900: // 妖皇天怒
					case 904: // 雷霆万钧
					case 702:
					case 703:
					case 704:
					case 710:
					case 926:
					case 927:
					case 906: // 威压
					case 910: // 聚煞
					case 905: // 地裂波
					case 907: // 摩柯无量
					case 933: // 天地雷法
					case 911: // 千钧伏魔
					case 913: // 九幽冥火
					case 914: // 九幽火
					case 915: // 离魂之火
					case 917: // 真武魔印
					case 920: // 邪气斩
					case 909: // 日月同辉
					case 919: // 腥风毒雨
					case 701: // 妖皇天怒
					case 928:
					case 930:
					case 922:
					case 924:{
						return new FightBsSkill();
					}
					case 501: {
						// 力破千钧
						return new FightPhyPowerSkill();
					}
					case 181: // 十万火急
					case 182: // 先声夺人
					case 183: // 疾风迅雷
					case 184: // 风驰电掣
					case 185: // 兵贵神速
					{
						// 全部是火系辅助技能，加速度
						return new FuzhuHuo181Skill();
					}
					case 81: // 拔苗助长
					case 82: // 火上烧油
					case 83: // 水涨船高
					case 84: // 红花绿色
					case 85: // 锦上添花
					{
						// 全部是木系辅助技能，加气血值和复活效果
						return new FuzhuMu81Skill();
					}
					case 131: // 防微杜渐
					case 132: // 铁骨铮铮
					case 133: // 兵来将挡
					case 134: // 铜墙铁壁
					case 135: // 天地浑元
					{
						// 全部是水系辅助技能，加防御和抗障碍能力
						return new FuzhuShui131Skill();
					}
					case 231: // 鞭长莫及
					case 232: // 望风扑影
					case 233: // 化险为夷
					case 234: // 避实就虚
					case 235: // 移形换影
					{
						// 全部是土系辅助技能，加躲闪率和解除障碍状态几率
						return new FuzhuTu231Skill();
					}
					case 31: // 天生神力
					case 32: // 气冲斗牛
					case 33: // 九牛二虎
					case 34: // 如虎添翼
					case 35: // 力挽狂澜
					{
						// 全部是金系辅助技能，加攻击力
						return new FuzhuJin31Skill();
					}
					case 171: // 心醉神迷
					case 172: // 神魂颠倒
					case 173: // 魂不守舍
					case 174: // 魂牵梦萦
					case 175: // 魂不附体
					{
						// 障碍火，昏睡
						return new ZhangaiHuo171Skill();
					}
					case 21: // 流连忘返
					case 22: // 得意忘形
					case 23: // 如痴如醉
					case 24: // 如梦初醒
					case 25: // 恍若隔世
					{
						// 障碍金，遗忘
						return new ZhangAiJin21Skill();
					}
					case 71: // 见血封喉
					case 72: // 蛇口蜂针
					case 73: // 鹤顶红粉
					case 74: // 蝎尾蛇涎
					case 75: // 万蚂噬心
					{
						// 障碍木，中毒
						return new ZhangaiMu71Skill();
					}
					case 121: // 三九严寒
					case 122: // 天寒地冻
					case 123: // 冰冻三尺
					case 124: // 极地冰寒
					case 125: // 包罗万象
					{
						// 障碍水，冰冻
						return new ZhangaiShui121Skill();
					}
					case 221: // 有心无力
					case 222: // 顾此失彼
					case 223: // 六神无主
					case 224: // 地束七魂
					case 225: // 天定三魂
					{
						// 障碍土，混乱
						return new ZhangAiTu221Skill();
					}
					case 254: // 如意圈
					case 259: // 乾坤罩
					case 260: // 神龙圈
					{
						return new TianJiSqrSkill();
					}
					case 1424:{//魂器薄暮
						return new FightHorcruxBoMu();
					}
					case 264: {
						return new FightWuSeGuangHuan();
					}
					case 258:{
						return new FightSheMingYiJi();
					}
					case 265:{
						return new FightSheShenQuYi();
					}
					default: {
						return new ZhaohuiSkill();
					}
				}
//                break;
			}
			case 14: { // 召回技能
				return new ZhaohuiSkill();
			}
			case 9: { // 捕捉宠物
				return new CatchPetSkill();
			}
			case 8: { // 召唤宠物
				return new ZhaoChuSkill();
			}
			case 7: { // 逃跑技能
				return new FleeSkill();
			}
			case 4: { // 用户在战斗中使用了道具
				return new UseItemSkill();
			}
			default: { // 如果不在上述所有技能中，则是一个不存在的技能，则返回空。
				return null;
			}
		}
	}

	/**
	 * 计算仙道点数值
	 * @param victimObject 被攻击人
	 * @param hurt 当前伤害
	 * @return
	 */
	public default int getUpgradeImmortalScore(FightObject fightObject, int hurt) {
		//被攻击者仙道点
		int subHurt = 0;
		if(fightObject.type ==1 && fightObject.upgrade_type>2) {
			//已经飞升了，计算仙道点减伤
			double upgradeImmortalScore = fightObject.upgrade_immortal*0.04/100;
			subHurt = (int) (hurt*upgradeImmortalScore);
		}
		return subHurt;
	}

	/**
	 * 计算魔道点数值
	 * @param fightObject 战斗对象
	 * @param hurt 当前伤害
	 * @return
	 */
	public default int getUpgradeMagicScore(FightObject fightObject, int hurt) {
		//被攻击者魔道点加成
		int addHurt = 0;
		if(fightObject.type ==1 && fightObject.upgrade_type>2) {
			//已经飞升了，计算魔道点加成
			double upgradeMagicScore = (fightObject.upgrade_magic*0.06/100);
			addHurt = (int) (hurt*upgradeMagicScore);
		}
		return addHurt;
	}
}