package com.fengshen.server.game;

import java.text.ParseException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fengshen.core.util.Utils;
import com.fengshen.db.domain.ChargePoint;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.db.service.base.BaseChargePointService;
import com.fengshen.server.data.vo.Vo_53477_0;
import com.fengshen.server.data.write.M53447_0;
import com.fengshen.server.domain.Chara;

import lombok.extern.slf4j.Slf4j;

/**
 * 充值积分兑换管理
 */
@Component
@Slf4j
public class ChargePointMng {
	@Autowired
	private BaseChargePointService chargePointService;

	/** 默认的日期格式 */
	public static String DEFAULT_TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
	/** 活动开始时间 */
	public int startTime;
	/** 活动结束时间 */
	public int endTime;
	/** 兑换截止时间 */
	public int deadline;

	public ChargePointMng() {
		try {
			load();
		} catch (Exception e) {
			log.error("{}", e);
			e.printStackTrace();
		}
	}

	/** 发送该玩家的充值积分兑换数据 */
	public void sendChargePointGoods(GameObjectChar gameObjectChar) {
		Chara chara = gameObjectChar.chara;
		Vo_53477_0 vo = new Vo_53477_0();
		vo.startTime = startTime;
		vo.endTime = endTime;
		vo.deadline = deadline;
		vo.ownPoint = chara.chargeScore < 0 ? 0 : chara.chargeScore;
		vo.totalPoint = chara.chargeScore < 0 ? 0 : chara.chargeScore;
		vo.items = chargePointService.findAll();
		if (gameObjectChar != null) {
			gameObjectChar.sendOne(new M53447_0(), vo);
		}

		GameUtilRenWu.refshPointTask(chara);
	}

	public void buyGoods(GameObjectChar gameObjectChar, int no, int num) {
		//商城
		Chara chara = gameObjectChar.chara;
		ChargePoint chargePoint = chargePointService.findByNo(no);
		boolean isEnd = false;
		try {
			if (num > 100) {
				GameUtil.notifyPrompt(chara.id, "一次最多兑换100个");
				return;
			}
			if (chargePoint.getLeftNum() < num) {
				GameUtil.notifyPrompt(chara.id, "剩余数量不足！");
				return;
			}
			if (gameObjectChar.privilege != 1000) {
				if (chara.chargeScore < num * chargePoint.getPoint()) {
					GameUtil.notifyPrompt(chara.id, "积分不足！");
					return;
				}
			}
			
			// 检查包裹是否充足
            int availableNum = GameCommonUtil.getAvaliablePos(chara.backpack, GameCommonUtil.getBackpackPos(chara));
            if(availableNum == -1) {
            	GameUtil.notifyPrompt(chara.id, "包裹位置不足。");
            	return;
            }
			// 获得奖励
			String awardStr = chargePoint.getAwardstr();
			awardStr = awardStr.substring(2, awardStr.length() - 2);
			String[] award = awardStr.split("\\|");

			if ("物品".equals(award[0])) {
				String item = award[1];
				StoreInfo info = GameData.that.baseStoreInfoService.findOneByName(item);
				GameUtil.huodedaoju(gameObjectChar, info, num);
				GameUtil.sendTips("恭喜你获得" + num + "个#R" + item + "#n");
			} else {
				// 分配指定数量的商品
				for (int i = 1; i <= num; ++i) {
					if (isEnd) {
						break;
					}
					if ("未鉴定".equals(award[0])) {
						if (award[1].contains("$1")) {
							GameUtil.jifenweijianding(gameObjectChar, 2, 1);
						} else if (award[1].contains("$3")) {
							GameUtil.jifenweijianding(gameObjectChar, 2, 3);
						}
					} else if ("装备".equals(award[0])) {
						// 整套130暴力$鞋子$力$130
						String[] info = award[1].split("\\$");
						if (info != null && info.length > 4) {
							// 指定装备类型,是整套还是特定
							String equipType = info[1];
							// 指定装备属性,是力还是法的
							String polar = info[2];
							if (!Utils.isNumber(info[3]) || !Utils.isNumber(info[4])) {
								GameUtil.sendMeTips("积分装备格式错误，请联系GM！");
								return;
							}
							// 等级
							Integer level = Integer.valueOf(info[3]);
							// 改造等级
							Integer rebuildLevel = Integer.valueOf(info[4]);
							if ("整套".equals(equipType)) {
								// 获取整套
								if ("力".equals(polar)) {
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipEarthWuQi(chara, level, rebuildLevel),
											gameObjectChar);
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 2),
											gameObjectChar);
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 3),
											gameObjectChar);
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 10),
											gameObjectChar);
									GameUtil.sendMeTips("恭喜你获得整套#R" + level + "#n级极品力套");
								} else if ("法".equals(polar)) {
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipMetalWuQi(chara, level, rebuildLevel),
											gameObjectChar);
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 2),
											gameObjectChar);
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 3),
											gameObjectChar);
									GameCommonUtil.addGoodsToBackpack(
											GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 10),
											gameObjectChar);
									GameUtil.sendMeTips("恭喜你获得整套#R" + level + "#n级极品法套");
								}
							} else {
								if ("武器".equals(equipType)) {
									if ("力".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipEarthWuQi(chara, level, rebuildLevel),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力武器");
									} else if ("法".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipMetalWuQi(chara, level, rebuildLevel),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法武器");
									}
								} else if ("帽子".equals(equipType)) {
									if ("力".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 2),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力帽子");
									} else if ("法".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 2),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法帽子");
									}
								} else if ("衣服".equals(equipType)) {
									if ("力".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 3),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力衣服");
									} else if ("法".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 3),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法衣服");
									}
								} else if ("鞋子".equals(equipType)) {
									if ("力".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipEarthOther(chara, level, rebuildLevel, 10),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴力鞋子");
									} else if ("法".equals(polar)) {
										GameCommonUtil.addGoodsToBackpack(
												GameCommonUtil.getJpEquipMetalOther(chara, level, rebuildLevel, 10),
												gameObjectChar);
										GameUtil.sendMeTips("恭喜你获得#R" + level + "#n级暴法鞋子");
									}
								}
							}
						}
					} else if ("首饰".equals(award[0])) {
						if (award[1].contains("随机70级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_70);
						} else if (award[1].contains("随机80级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_80);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R80级首饰#n");
						} else if (award[1].contains("随机90级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_90);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R90级首饰#n");
						} else if (award[1].contains("随机100级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_100);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R100级首饰#n");
						} else if (award[1].contains("随机110级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_110);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R110级首饰#n");
						} else if (award[1].contains("随机120级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_120);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R120级首饰#n");
						} else if (award[1].contains("随机35级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_35);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R35级首饰#n");
						} else if (award[1].contains("随机50级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_50);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R50级首饰#n");
						} else if (award[1].contains("随机60级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_60);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R60级首饰#n");
						} else if (award[1].contains("随机130级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_130);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R130级首饰#n");
						} else if (award[1].contains("随机140级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_140);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R140级首饰#n");
						} else if (award[1].contains("随机150级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_150);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R150级首饰#n");
						} else if (award[1].contains("随机160级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_160);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R160级首饰#n");
						} else if (award[1].contains("随机170级")) {
							GameUtil.jifendengjishoushi(chara, GameUtil.SHOU_SHI_170);
							GameUtil.notifyPrompt(chara.id, "恭喜获得#R170级首饰#n");
						} else if (award[1].startsWith("整套")) {
							Pattern p = Pattern.compile("[^0-9]");
							Matcher m = p.matcher(award[1].split("\\$")[0]);
							String trim = m.replaceAll("").trim();
							Integer level = Integer.valueOf(trim);
							String[] nameArr = GameUtil.getShowShiNameArrByLevel(level);
							if (nameArr == null) {
								GameUtil.sendMeTips("没有找到该等级的首饰！");
								return;
							}
							GameUtil.getShouShiAllAttr(chara, nameArr[0]);
							GameUtil.getShouShiAllAttr(chara, nameArr[1]);
							GameUtil.getShouShiAllAttr(chara, nameArr[2]);
							GameUtil.getShouShiAllAttr(chara, nameArr[2]);
						}
					} else if ("法宝".equals(award[0])) {
						String fabao = award[1].substring(0, award[1].indexOf("$"));
						int fabaoLevel = 1;
						int xinagxing = 1;

						//增加6级  12级 18级 20级
						if (award[1].contains("$24")) {
							fabaoLevel = 24;
						} else if (award[1].contains("$6")) {
							fabaoLevel = 6;
						} else if (award[1].contains("$12")) {
							fabaoLevel = 12;
						} else if (award[1].contains("$18")) {
							fabaoLevel = 18;
						} else if (award[1].contains("$20")) {
							fabaoLevel = 20;
						}else if (award[1].contains("$1")) {
							fabaoLevel = 1;
						}
						switch (fabao) {
							case "番天印":
								xinagxing = 1;
								break;
							case "混元金斗":
								xinagxing = 5;
								break;
							case "定海珠":
								xinagxing = 4;
								break;
							case "九龙神火罩":
								xinagxing = 4;
								break;
						}
						GameUtil.jifenhuodefabao(chara, fabao, fabaoLevel, "积分商城", xinagxing);
					} else if ("宠物".equals(award[0])) {
						if (award[1].contains("太极熊")) {
							GameUtil.huodezuoji(chara, "太极熊", "积分商城");
						} else if (award[1].contains("墨麒麟")) {
							GameUtil.huodezuoji(chara, "墨麒麟", "积分商城");
						} else if (award[1].contains("随机神兽")) {
							String[] mounts_name = { "朱雀", "玄武", "九尾狐", "疆良" };
							Random random3 = new Random();
							String name2 = mounts_name[random3.nextInt(mounts_name.length)];
							GameUtil.huodeshenshou(chara, name2, "积分商城");
						} else if (award[1].contains("随机超神兽")) {
							String[] mounts_name = { "火精灵", "火凤凰", "火麒麟", "血魔" };
							Random random3 = new Random();
							String name2 = mounts_name[random3.nextInt(mounts_name.length)];
							GameUtil.huodeshenshou(chara, name2, "积分商城");
						} else if (award[1].contains("随机服战神兽")) {
							String[] mounts_name = { "普法道尊", "后裔星神", "巨斧大帝", "盾甲仙君" };
							Random random3 = new Random();
							String name2 = mounts_name[random3.nextInt(mounts_name.length)];
							GameUtil.huodeshenshou(chara, name2, "积分商城");
						} else if (award[1].contains("超神兽") || award[1].contains("服战神兽")) {
							GameUtil.huodeshenshou(chara, award[1].substring(0, award[1].indexOf("(")), "积分商城");
						} else {
							String nameAndType = award[1].split("\\$")[0];
							String[] str = nameAndType.split("\\(");
							String name = str[0]; // 宠物名字
							String typeall = str[1];
							String type = typeall.substring(0, typeall.length() - 1); // 宠物类型
							if (type.equals("变异")) {
								GameUtil.huodebianyi(chara, name, "积分商城");
							} else if (type.equals("神兽")) {
								GameUtil.huodeshenshou(chara, name, "积分商城");
							} else if (type.equals("精怪")) {
								GameUtil.huodezuoji(chara, name, "积分商城");
							} else if ("鬼仙".equals(type)) {
								GameUtil.huodechongwu(chara, name, 8, "积分商城");
							}else if("宝宝".equals(type)) {
								GameUtil.huodemanchongwu(chara, name, 2, "积分商城");
							}
						}
					} else if ("魂器".indexOf(award[0]) != -1) {
						ZhuangbeiInfo findByStr = GameData.that.baseZhuangbeiInfoService.findOneByStr(award[1]);
						if (findByStr != null) {
							GameCommonUtil.integral_horcrux(chara, award[1], 75, null);
							GameUtil.notifyPrompt(chara.id, "获得#Y" + award[1]);
						}
					} else if("金钱".equals(award[0])) {
						//获得金钱
						GameUtil.addJinbi(chara, Integer.valueOf(award[1]), "积分商城");
						GameUtil.sendUpdate(chara);
					} else if("经验".equals(award[0])) {
						//获得经验
						if (award[1].contains("洛书经验")) {
							String substring = award[1].replace("洛书经验","").substring(1, award[1].replace("洛书经验","").length() - 1);
							GameUtil.addLuoshuJinYan(chara, Integer.valueOf(substring), "积分商城");
						}
						GameUtil.sendUpdate(chara);
					}else if("魂窍".equals(award[0])) {
						String type = award[1].split("\\$")[1];
						if("1".equals(type)) {
							//默认随机属性数值
							GameUtil.getTyzqRandomAttr(gameObjectChar);
							GameUtil.sendMeTips("获得了1个随机属性#R太阴之气");
						}else {
							//随机属性满值
							GameUtil.getTyzqRandomAttrFullVal(gameObjectChar);
							GameUtil.sendMeTips("获得了1个随机满属性#R太阴之气");
						}
					}
				}
			}

		} catch (RuntimeException e) {
			if (e instanceof DataIntegrityViolationException) {
				log.error("{}", e);
				// 库存不足.
				GameUtil.notifyPrompt(chara.id, "剩余数量不足哦....");
			}
			num--;
			isEnd = true;
			e.printStackTrace();
		}
		if (gameObjectChar.privilege != 1000) {
			chara.chargeScore -= chargePoint.getPoint() * num;
			chargePoint.setLeftNum(chargePoint.getLeftNum() - num);
			chargePointService.update(chargePoint);
			// 刷新积分商城页面
			sendChargePointGoods(gameObjectChar);
			
		}

		GameUtilRenWu.refshPointTask(chara);

	}

	public void load() throws ParseException {
		startTime = (int) (DateUtils.parseDate("2020/02/13 00:00:00", DEFAULT_TIME_FORMAT).getTime() / 1000);
		endTime = startTime + 2*365 * 24 * 60 * 60;// 3 * 60 * 60
		deadline = endTime + 2*365 * 24 * 60 * 60;// 7 * 24 * 60 * 60
	}

	public static void main(String[] args) {
		String a = "100|神兽|朱雀|1|,|物品|鬼丹|200|(诛仙孙悟空)";
		for (String s : a.split(",")) {
			System.out.println(s);
		}

	}
}