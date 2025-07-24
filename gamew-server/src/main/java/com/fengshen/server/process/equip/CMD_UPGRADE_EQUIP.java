package com.fengshen.server.process.equip;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.fengshen.server.game.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.UtilObjMapshuxing;
import com.fengshen.server.data.constant.ClientButtonIdConst;
import com.fengshen.server.data.game.ConsumeMoneyUtils;
import com.fengshen.server.data.game.ForgingEquipmentUtils;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_41191_0;
import com.fengshen.server.data.vo.equip.Vo_PLAY_LYFH_ANIMAATE;
import com.fengshen.server.data.vo.equip.Vo_UPGRADE_INHERIT_PREVIEW;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.vo.user.Vo_UPDATE_DYNAMIC;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.data.write.M16383_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M32775_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M41191_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.equip.MSG_PLAY_LYFH_ANIMAATE;
import com.fengshen.server.data.write.equip.MSG_PLAY_YDHD_ANIMAATE;
import com.fengshen.server.data.write.equip.MSG_TRANSFORM_JEWELRY_COMPLETE;
import com.fengshen.server.data.write.equip.MSG_UPGRADE_INHERIT_PREVIEW;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.data.write.user.MSG_UPDATE_DYNAMIC;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsBasics;
import com.fengshen.server.domain.GoodsFenSe;
import com.fengshen.server.domain.GoodsGaiZao;
import com.fengshen.server.domain.GoodsGaiZaoGongMing;
import com.fengshen.server.domain.GoodsHuangSe;
import com.fengshen.server.domain.GoodsHunqi;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.domain.GoodsLanSe;
import com.fengshen.server.domain.GoodsLvSe;
import com.fengshen.server.domain.GoodsLvSeGongMing;
import com.fengshen.server.domain.config.EquipGaiZaoConfig;
import com.fengshen.server.util.BeanUtils;
import com.fengshen.server.util.GameConfig;
import com.google.common.collect.Lists;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.extern.slf4j.Slf4j;

/**
 * 装备升级系列
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_UPGRADE_EQUIP implements GameHandler {

	// 首饰转换精华消耗
	public static final int[] JINGHUA_COST = new int[] { 60, 90, 150, 240, 360, 510, 710, 960, 1260, 1610, 1610 };
	// 重铸消耗
	public static final int[] JEWELRY_REFINE_COST_ESSENCE = new int[] { 70, 120, 180 };

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int pos = GameReadTool.readShort(buff); // 值为装备在仓库中的位置，1是武器，10是鞋子，意思类推
		int type = GameReadTool.readByte(buff); // 操作类型，3是改造
		String para = GameReadTool.readString(buff); // 操作的相关参数
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		log.info("装备操作,pos={},type={},para={}", pos, type, para);
		if (GameCommonUtil.rejectRequestTimeOutForSecond(chara, "equip_upgrade", null, 300)) {
			GameUtil.sendMeTips("操作频繁");
			return;
		}
		if (pos < 0) {
			pos = 129 + pos + 127;
		}
		//魂器技能升级
		if(type == 33) {
			//para副装备para=53|52
			String[] itemArr = para.split("\\|");
			if(itemArr.length < 2) {
				GameUtil.sendMeTips("请提交副魂器材料");
				return;
			}
			Goods oGoods1 = null;
			Goods oGoods2 = null;
			for(Goods goods:chara.backpack) {
				if(goods.pos == Integer.valueOf(itemArr[0])) {
					//如果不是魂器则直接返回
					if(goods.goodsHunQi == null || goods.goodsHunQi.zongShuxing == null || goods.goodsHunQi.zongShuxing.isEmpty()) {
						GameUtil.sendMeTips("请提交正确的材料");
						return;
					}
					oGoods1 = goods;
				}
				if(goods.pos == Integer.valueOf(itemArr[1])) {
					//如果不是魂器则直接返回
					if(goods.goodsHunQi == null || goods.goodsHunQi.zongShuxing == null || goods.goodsHunQi.zongShuxing.isEmpty()) {
						GameUtil.sendMeTips("请提交正确的材料");
						return;
					}
					oGoods2 = goods;
				}
				
			}
			if(oGoods1 != null && oGoods2 != null) {
				
			}
			return;
		}

		// 首饰转换
		if (type == 28) {
			// 获取到字段
			para = para.split("/")[1].trim();
			// 旧的属性名称
			String fieldName = ForgingEquipmentUtils.getErrorFieldByOriginField(para, false);
			// 找到这个装备
			if (!StringUtils.isNullOrEmpty(fieldName)) {
				List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
				for (Goods goods : goodss) {
					if (goods.pos == pos) {
						try {
							if (goods.goodsInfo.transform_num + 1 > 10) {
								GameUtil.sendMeTips("转换次数已达极限！");
								return;
							}
							int goodsAvaliableNum = GameCommonUtil.getGoodsAvaliableNum(chara.backpack, "超级女娲石");
							if (goodsAvaliableNum < 2) {
								GameUtil.sendMeTips("超级女娲石不足！");
								return;
							} else if (chara.getJewelry_essence() < JINGHUA_COST[goods.goodsInfo.transform_num]) {
								GameUtil.sendMeTips("首饰精华不足！");
								return;
							}
							// 上一个属性最大值
							Map<String, String> preFieldInfo = ForgingEquipmentUtils
									.getShouShiRandomFieldByChineseToMap(fieldName, goods.goodsInfo.amount);
							Field preField = goods.goodsLanSe.getClass().getField(preFieldInfo.get("en"));
							int preMaxValue = ForgingEquipmentUtils.getMaxValueByChineseName(preFieldInfo.get("ch"),
									goods.goodsInfo.attrib, false, false);
							int preValue = (int) preField.get(goods.goodsLanSe);
							if (preValue >= preMaxValue) {
								// 已是最大值
								GameUtil.sendMeTips("当前属性已是最大值！");
								return;
							}
							// 装备原始的字段
							List<String> oldFields = new ArrayList<>();
							// 属性集合
							List<String> attrNameBuff = new ArrayList<>();
							// 首饰的名字集合
							Hashtable<String, String> shouShiFields = ForgingEquipmentUtils
									.getShouShiFields(goods.goodsInfo.amount);
							// 获取装备原始的字段值
							for (Field or : goods.goodsLanSe.getClass().getFields()) {
								if (or.getName().equals("groupNo") || or.getName().equals("groupType")) {
									continue;
								}
								if ((int)or.get(goods.goodsLanSe) != 0) {
									oldFields.add(or.getName());
									log.info("原始key:{}--原始value:{}", or.getName(), or.get(goods.goodsLanSe));
								}
							}
							//所有相性、所有技能上升、所有抗异常属性最多一条,这里统计下
//							Map<String, Long> group = attrNameBuff.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
//							for (Entry<String, Long> g:group.entrySet()) {
//								if(g.getKey().equals("all_resist_polar")) {
//									if()
//								}
//							}
							for (Entry<String, String> hash : shouShiFields.entrySet()) {
								//随机字段不应包含原来的值
								if (oldFields.contains(hash.getValue())) {
									log.info("过滤值en:{}--过滤值ch:{}", hash.getValue(), hash.getKey());
									continue;
								}
								attrNameBuff.add(hash.getValue());
							}
							// 随机选出一条属性
							int randonIndex = ThreadLocalRandom.current().nextInt(attrNameBuff.size());
							// 新的名字
							String newFieldName = attrNameBuff.get(randonIndex);
							Map<String, String> newFieldInfo = ForgingEquipmentUtils
									.getShouShiRandomFieldByChineseToMap(newFieldName, goods.goodsInfo.amount);
							// 中文名
							String chinese = newFieldInfo.get("ch");
							// 最大值
							int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chinese,
									goods.goodsInfo.attrib, false, false);
							if (preField != null) {
								int newMaxVlaue = (int) (maxValue * 0.6);
								int newValue = ThreadLocalRandom.current().nextInt(newMaxVlaue) + 1;
								log.info("旧的字段:{}、旧的值:{}", preFieldInfo.get("en"), preValue);
								log.info("新的字段:{}、新的值:{}", newFieldName, newValue);
								if (gameObjectChar.privilege == 0) {
									// 扣除女娲和精华
									GameUtil.removemunber(chara, "超级女娲石", 2);
									chara.setJewelry_essence(
											chara.getJewelry_essence() - JINGHUA_COST[goods.goodsInfo.transform_num]);
									GameObjectChar.send(new M16383_0(),
											GameUtil.a16383(
													chara, "你消耗了#R2#n个超级女娲石和#R"
															+ JINGHUA_COST[goods.goodsInfo.transform_num] + "#n点首饰精华。",
													0));
									// 测试期间冷却10秒
									goods.goodsInfo.transform_cool_ti = (int) (System.currentTimeMillis() / 1000L + 10);
								}
								// 冷却和次数
								goods.goodsInfo.transform_num += 1;
								Map<String, Object> obj = new HashMap<>();
								obj.put("jewelry_essence", chara.getJewelry_essence());
								GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, obj));
								GoodsLanSe clone = goods.goodsLanSe;
								// 删除原来需要转换的属性
								goods.goodsLanSe.getClass().getField(preFieldInfo.get("en")).set(clone, 0);
								for (Field or : clone.getClass().getFields()) {
									if (or.getName().equals("groupNo") || or.getName().equals("groupType")) {
										continue;
									}
									if ((int)or.get(clone) != 0) {
										log.info("删除之后key:{}--删除之后value:{}", or.getName(), or.get(clone));
									}
								}
								// 设置新属性
								Field newField = goods.goodsLanSe.getClass().getField(newFieldName);
								newField.set(clone, newValue);
								for (Field or : clone.getClass().getFields()) {
									if (or.getName().equals("groupNo") || or.getName().equals("groupType")) {
										continue;
									}
									if ((int)or.get(clone) != 0) {
										log.info("重新生成key:{}--重新生成value:{}", or.getName(), or.get(clone));
									}
								}
								// 刷新首饰
								GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
								// 转换成功
								GameObjectChar.send(new MSG_TRANSFORM_JEWELRY_COMPLETE(), pos);
								// 如果穿戴在身上则刷新
								if (goods.pos <= 10) {
									GameUtil.a65511(gameObjectChar);
								}
								log.info("==============================首饰转换==============================");
							}
						} catch (Exception e) {
							log.error("{}", e);
							GameUtil.sendMeTips("转换失败,系统异常！");
						}
						break;
					}
				}
			}
			return;
		}
		// 装备继承确定,把两个装备的属性调换一下
		if (30 == type) {
			// pos=副装备,type=30,para=主装备|1:金元宝0:银元宝
			Goods mEquip = null;
			Goods oEquip = null;
			String[] split = para.split("\\|");
			int pos2 = Integer.valueOf(split[0]);
			// 找出主装备和副装备信息
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos2)) {
				if (goods.pos == Integer.valueOf(split[0])) {
					// 主装备
					mEquip = goods;
					break;
				}
			}
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					// 副装备
					oEquip = goods;
					break;
				}
			}
			if (mEquip != null && oEquip != null) {
//				if(mEquip.goodsInfo.amount != oEquip.goodsInfo.amount) {
//					GameUtil.sendMeTips("不同类型装备无法继承");
//					return;
//				}
				// 要扣除的元宝
				int coin = (int) (1200 * mEquip.goodsInfo.color * 15.2);
				coin += 1200;
				if (chara.goldCoin < coin) {
					GameUtil.sendMeTips("元宝不足。");
					return;
				}
				int money = 1200 * mEquip.goodsInfo.color * 100;
				if (chara.cash < money) {
					GameUtil.sendMeTips("金钱不足。");
					return;
				}
				// 开始继承属性，复制一个主装备对象出来.待会使用
				GoodsGaiZao cloneGoodsGaiZao = BeanUtils.clone(mEquip.goodsGaiZao);
				GoodsGaiZaoGongMing cloneGoodsGaiZaoGongMing = BeanUtils.clone(mEquip.goodsGaiZaoGongMing);
//				GoodsGaiZaoGongMingChengGong cloneGoodsGaiZaoGongMingChengGong = BeanUtils
//						.clone(mEquip.goodsGaiZaoGongMingChengGong);
				//主装备改造等级
				int mEquipRebuildLevel = mEquip.goodsInfo.color;
				//副装备改造等级
				int oEquipRebuildLevel = oEquip.goodsInfo.color;
				//主装备改造进度
				int mEquipExp = mEquip.goodsInfo.store_exp;
				//副装备改造进度
				int oEquipExp = oEquip.goodsInfo.store_exp;
				//改造等级和改造经验
				mEquip.goodsInfo.color = oEquipRebuildLevel;
				mEquip.goodsInfo.store_exp = oEquipExp;
				oEquip.goodsInfo.color = mEquipRebuildLevel;
				oEquip.goodsInfo.store_exp = mEquipExp;
				
				//重新计算改造属性和共鸣属性
				mEquip.goodsGaiZao = oEquip.goodsGaiZao;
				mEquip.goodsGaiZaoGongMing = oEquip.goodsGaiZaoGongMing;
//				mEquip.goodsGaiZaoGongMingChengGong = oEquip.goodsGaiZaoGongMingChengGong;
				
				if(mEquip.goodsInfo.attrib<70 || mEquip.goodsInfo.color<4) {
					//取消共鸣属性
					mEquip.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
				}else {
					//重新计算
					ForgingEquipmentUtils.rebuildLevelInfo(mEquip);
				}
				oEquip.goodsGaiZao = cloneGoodsGaiZao;
				oEquip.goodsGaiZaoGongMing = cloneGoodsGaiZaoGongMing;
				if(oEquip.goodsInfo.attrib<70 || oEquip.goodsInfo.color<4) {
					//取消共鸣属性
					mEquip.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
				}else {
					//重新计算
					ForgingEquipmentUtils.rebuildLevelInfo(oEquip);
				}
//				oEquip.goodsGaiZaoGongMingChengGong = cloneGoodsGaiZaoGongMingChengGong;
				
				// 扣除元宝和金钱
				chara.goldCoin -= coin;
				chara.cash -= money;
				GameUtil.a65511(gameObjectChar);
				// 继承成功
				Vo_GENERAL_NOTIFY vo_9129_3 = new Vo_GENERAL_NOTIFY();
				vo_9129_3.notify = ClientButtonIdConst.NOTIFY_EQUIP_UPGRADE_INHERIT_OK;
				vo_9129_3.para = "";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_3);
				// 刷新两个装备的信息
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(mEquip));
				GameObjectChar.send(new M65525_0(), Lists.newArrayList(oEquip));
				GameUtil.sendMeTips("继承成功，副装备改造属性已转移到主装备");
			}
			return;
		}
		// 装备继承预览数据
		if (31 == type) {
			// pos=69,type=31,para=66
			Goods mEquip = null;
			Goods oEquip = null;
			// 找出主装备和副装备信息
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, Integer.valueOf(para))) {
				if (goods.pos == Integer.valueOf(para)) {
					// 主装备
					mEquip = goods;
					break;
				}
			}
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					// 副装备
					oEquip = goods;
					break;
				}
			}
			if (mEquip != null && oEquip != null) {
				int defaltCoin = (int) (1200 * mEquip.goodsInfo.color * 15.2);
				defaltCoin += 1200;
				// 设置装备信息
				Vo_UPGRADE_INHERIT_PREVIEW vo = new Vo_UPGRADE_INHERIT_PREVIEW();
				vo.setCoin(defaltCoin);
				vo.setFlag(1);
				vo.setPara(para);
				vo.setMoney(1200 * mEquip.goodsInfo.color * 100);
				vo.setPos(pos);
				vo.setOEquip(oEquip);
				vo.setMEquip(mEquip);
				GameObjectChar.send(new MSG_UPGRADE_INHERIT_PREVIEW(), vo);
			}
			return;
		}

		// 魂器-进化
		if (34 == type) {
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					if (Integer.valueOf(para) > 179) {
						GameUtil.sendMeTips("目前最高支持179");
						return;
					}
					if (Integer.valueOf(para) > chara.level) {
						GameUtil.sendMeTips("无法超过自身等级");
						return;
					}
					// 升级
					goods.goodsInfo.attrib = Integer.valueOf(para);
					GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
					GameUtil.sendMeTips("恭喜你进化到#R" + goods.goodsInfo.attrib + "#n级。");
					break;
				}
			}
			return;
		}

		if (36 == type) {
			// 魂器-两仪分化
			Integer index = Integer.valueOf(para);
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					GoodsHunqi goodsHunQi = goods.goodsHunQi;
					List<Hashtable<String, Object>> list = goodsHunQi.zongShuxing;
					Hashtable<String, Object> hashtable = list.get(index - 1);
					if((int)hashtable.get("yang_prop_value") > 0) {
						GameUtil.sendMeTips("请先引动混沌");
						return;
					}
					if((int)hashtable.get("chaos_value") == 0 && (int)hashtable.get("yang_prop_value") == 0) {
						GameUtil.sendMeTips("请先引动混沌");
						return;
					}
					Vo_PLAY_LYFH_ANIMAATE lyfh = new Vo_PLAY_LYFH_ANIMAATE();
					lyfh.setIndex(index);
					lyfh.setPos(pos);
					lyfh.setYangPercent(60);
					lyfh.setYinPercent(50);
					GameObjectChar.send(new MSG_PLAY_LYFH_ANIMAATE(), lyfh);
					// 阳属性
					int yang_percent = new Random().nextInt(100) + 1;
					// 找出原来的阳属性,不可重复
					HashSet<String> exitsAttrNameSetYang = new HashSet<String>();
					// 允许重复
					List<String> exutsArrtNameListYang = new ArrayList<>();
					// 找出原来的阳属性,不可重复
					HashSet<String> exitsAttrNameSetYin = new HashSet<String>();
					// 允许重复
					List<String> exutsArrtNameListYin = new ArrayList<>();
					for (Hashtable<String, Object> name : list) {
						String object = (String) name.get("yang_prop");
						if (!StringUtils.isNullOrEmpty(object)) {
							exitsAttrNameSetYang.add(object);
							exutsArrtNameListYang.add(object);
							exitsAttrNameSetYin.add(object);
							exutsArrtNameListYin.add(object);
						}
					}
					// 阳属性
					Collection yangRs = CollectionUtils.disjunction(exutsArrtNameListYang, exitsAttrNameSetYang);
					Object[] yangArray = yangRs.toArray();
					String[] yangStrArray = Arrays.stream(yangArray).toArray(String[]::new);
					String key = HunqiUtils.horcrux_yang(yangStrArray);
					log.info("魂器阳属性重复:{}", Arrays.toString(yangStrArray));
					int value = HunqiUtils.jisuanYang((Integer) hashtable.get("chaos_value"), yang_percent, key,
							goods.goodsInfo.attrib);
					// 阴属性
					Collection yinRs = CollectionUtils.disjunction(exutsArrtNameListYin, exitsAttrNameSetYin);
					Object[] yinArray = yinRs.toArray();
					String[] yinStrArr = Arrays.stream(yinArray).toArray(String[]::new);
					log.info("魂器阴属性重复:{}", Arrays.toString(yinStrArr));
					String keyYin = HunqiUtils.horcrux_yin(yinStrArr);
					int valueYin = HunqiUtils.jisuanYin((Integer) hashtable.get("chaos_value"), yang_percent, keyYin,
							goods.goodsInfo.attrib);
					log.info("两仪分化yang-----key={},value={}", key, value);
					log.info("两仪分化yin-----key={},value={}", keyYin, valueYin);
					hashtable.put("yang_percent", yang_percent);
					hashtable.put("yang_prop", key);
					hashtable.put("yang_prop_value", value);
					hashtable.put("yin_prop", keyYin);
					hashtable.put("yin_prop_value", valueYin);
					GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
					break;
				}
			}
			return;
		}
		// 引动混沌
		if (35 == type) {
			try {
				String[] split = para.split("\\|");
				if(GameCommonUtil.getGoodsNum(chara, "天倾石") <1 && gameObjectChar.privilege == 0) {
					GameUtil.sendMeTips("天倾石不足");
					return;
				}
				for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
					if (goods.pos == pos) {
						GoodsHunqi goodsHunQi = goods.goodsHunQi;
						List<Hashtable<String, Object>> list = goodsHunQi.zongShuxing;
						Hashtable<String, Object> hashtable = list.get(Integer.valueOf(split[0]) - 1);
						hashtable.put("chaos_value", new Random().nextInt(100) + 1);
						hashtable.put("yang_percent", 0);
						hashtable.put("yang_prop", "");
						hashtable.put("yang_prop_value", 0);
						hashtable.put("yin_prop", "");
						hashtable.put("yin_prop_value", 0);
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
						// 删除物品
						GameUtil.removemunber(chara, "天倾石", 1);
						break;
					}
				}
				log.info("魂器引动混沌.");
				GameObjectChar.send(new MSG_PLAY_YDHD_ANIMAATE(),
						new Vo_PLAY_LYFH_ANIMAATE(pos, Integer.valueOf(split[0])));
			} finally {
				Vo_GENERAL_NOTIFY obj = new Vo_GENERAL_NOTIFY();
				obj.notify = 50034;
				obj.para = "35";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), obj);
			}
			return;
		}

		// 装备进化
		if (14 == type) {
			// 查找包裹是否有天星石
			int maxCostNum = 0;
			for (Goods g : chara.backpack) {
				if (g.goodsInfo.str.equals("天星石")) {
					maxCostNum += g.goodsInfo.owner_id;
				}
			}
			for (Goods goodsByPos : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goodsByPos.pos == pos) {
					int costNum = (int) Math.max(1, Math.floor(goodsByPos.goodsInfo.attrib / 10) - 7);
					if (maxCostNum < costNum) {
						GameUtil.sendMeTips("天星石不足。");
						return;
					}
					if (goodsByPos.goodsInfo.mailing_item_times+1 > 9) {
						GameUtil.sendMeTips("进化最多为9级");
						return;
					}
					if(goodsByPos.goodsInfo.attrib+1>135) {
						GameUtil.sendMeTips("装备进化到135级");
						return;
					}
					if(goodsByPos.goodsInfo.attrib+1>chara.level) {
						GameUtil.sendMeTips("装备超过人物等级无法进化");
						return;
					}
					goodsByPos.goodsInfo.attrib += 1;
					goodsByPos.goodsInfo.mailing_item_times += 1;
					// 更新完美度
					goodsByPos.goodsInfo.dunwu_times = (int) ((goodsByPos.goodsInfo.mailing_item_times * 4.6) * 100);
					if (goodsByPos.goodsInfo.dunwu_times >= 100) {
						goodsByPos.goodsInfo.dunwu_times = (int) (99.99 * 100);
					}
					GameObjectChar.send(new M65525_0(), Lists.newArrayList(goodsByPos));

					Vo_GENERAL_NOTIFY obj = new Vo_GENERAL_NOTIFY();
					obj.notify = 20027;
					obj.para = "";
					GameObjectChar.send(new MSG_GENERAL_NOTIFY(), obj);
					GameUtil.sendMeTips("进化成功。");
					// 扣去金钱
					chara.cash -= goodsByPos.goodsInfo.attrib * 10000;
					// 减去天星石
					GameUtil.removemunber(chara, "天星石", costNum);
					GameUtil.sendUpdate(chara);
					break;
				}
			}
			return;
		}
		// 首饰重铸
		if (19 == type) {
			for (Goods goods : GameCommonUtil.switchGoodsLocation(chara, pos)) {
				if (goods.pos == pos) {
					// 重铸消耗
					int cost = 70;
					int moneyCost = goods.goodsInfo.attrib * 10000;
					int randomNum = 1;
					if (goods.goodsInfo.attrib >= 90 && goods.goodsInfo.attrib <= 99) {
						randomNum = 2;
						cost = JEWELRY_REFINE_COST_ESSENCE[1];
					} else if (goods.goodsInfo.attrib >= 100 && goods.goodsInfo.attrib <= 109) {
						randomNum = 3;
						cost = JEWELRY_REFINE_COST_ESSENCE[2];
					}
					if (gameObjectChar.privilege == 0) {
						if (chara.getJewelry_essence() < cost) {
							GameUtil.sendMeTips("首饰精华不足！");
							return;
						} else if (chara.getCash() < moneyCost) {
							GameUtil.sendMeTips("金钱不足！");
							return;
						}
						// 扣除金钱和首饰精华
						chara.setJewelry_essence(chara.getJewelry_essence() - cost);
						chara.cash -= moneyCost;
						// 刷新
						Map<String, Object> obj = new HashMap<>();
						obj.put("jewelry_essence", chara.getJewelry_essence());
						obj.put("cash", chara.getCash());
						GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, obj));
					}
					List<String> attrNameBuff = new ArrayList<>();
					// 首饰的名字集合
					Hashtable<String, String> shouShiFields = ForgingEquipmentUtils
							.getShouShiFields(goods.goodsInfo.amount);
					for (String getKey : shouShiFields.keySet()) {
						attrNameBuff.add(getKey);
					}
					Map<String, Integer> newShoushiValueMap = new HashMap<>();
					// 属性条数
					for (int i = 0; i < randomNum; i++) {
						// 随机选出对应的属性
						int nextInt = ThreadLocalRandom.current().nextInt(attrNameBuff.size());
						// 中文字段
						String chineseName = attrNameBuff.get(nextInt);
						// 英文字段
						String englishName = ForgingEquipmentUtils.getEquipmentKeyByName(chineseName);
						// 最大值
						int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chineseName,
								goods.goodsInfo.attrib, false, false);
						// 把选出的删除了去
						attrNameBuff.remove(nextInt);
						newShoushiValueMap.put(englishName, ThreadLocalRandom.current().nextInt(maxValue) + 1);
					}
					if (!newShoushiValueMap.isEmpty()) {
						// 属性初始化
						goods.goodsLanSe = new GoodsLanSe();
						for (Entry<String, Integer> data : newShoushiValueMap.entrySet()) {
							try {
								// 重新生成值
								goods.goodsLanSe.getClass().getField(data.getKey()).set(goods.goodsLanSe,
										data.getValue());
							} catch (Exception e) {
								log.error("{}", e);
							}
						}
						// 生成id
						goods.goodsInfo.damage_sel_rate = (int) (System.currentTimeMillis() / 1000L);
						// 刷新装备
						GameObjectChar.send(new M65525_0(), Lists.newArrayList(goods));
						Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
						vo_9129_0.notify = 20034;
						vo_9129_0.para = String.valueOf(goods.goodsInfo.damage_sel_rate);
						GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
						// 如果穿戴在身上则刷新
						if (goods.pos <= 10) {
							GameUtil.a65511(gameObjectChar);
						}
					}
					log.info("首饰重铸");
					break;
				}
			}
			return;
		}
		// 首饰分解
		if (27 == type) {
			String[] split = para.split("\\|");
			int essence = 0;
			for (String ids : split) {
				for (int i = 0; i < chara.backpack.size(); ++i) {
					if (chara.backpack.get(i).pos == Integer.valueOf(ids)) {
						switch (chara.backpack.get(i).goodsInfo.attrib) {
						case 70:
							essence += 2;
							break;
						case 80:
							essence += 35;
							break;
						case 90:
							essence += 60;
							break;
						case 100:
							essence += 90;
							break;
						case 110:
							essence += 120;
							break;
						case 120:
							essence += 150;
							break;
						default:
							essence += 180;
							break;
						}
					}
				}
			}
			if (chara.cash - (essence * 10000) < 0) {
				GameUtil.sendMeTips("金钱不足");
				return;
			}
			for (String ids : split) {
				for (int i = 0; i < chara.backpack.size(); ++i) {
					if (chara.backpack.get(i).pos == Integer.valueOf(ids)) {
						GameUtil.removemunber(chara, chara.backpack.get(i), 1);
					}
				}
			}
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你获得了#R" + essence + "#n个首饰精华！";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			chara.jewelry_essence += essence;
			// 减去金钱
			chara.cash -= essence * 10000;
			// 刷新
			Map<String, Object> obj = new HashMap<>();
			obj.put("jewelry_essence", chara.getJewelry_essence());
			obj.put("cash", chara.getCash());
			GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, obj));
			// 首饰分解成功
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("type", (byte) 1);
			map.put("tip", "分解成功");
			GameObjectChar.send(new CommonWrite(0xB1D3), map);
			return;
		}

		if (13 == type) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				Goods goods = chara.backpack.get(i);
				if (goods.pos == pos) {
					int attrib = goods.goodsInfo.attrib + 10;
					String current = "";
					List<ZhuangbeiInfo> infoList = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService
							.findByAttrib(attrib);
					for (int j = 0; j < infoList.size(); ++j) {
						if (infoList.get(j).getAmount() == goods.goodsInfo.amount) {
							current = infoList.get(j).getStr();
						}
					}
					Hashtable hashMap = new Hashtable();
					Map<Object, Object> goodsLanSe = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
					for (Map.Entry<Object, Object> entry : goodsLanSe.entrySet()) {
						if (!entry.getKey().equals("groupNo")) {
							if (entry.getKey().equals("groupType")) {
								continue;
							}
							if (entry.getValue().toString().equals("0")) {
								continue;
							}
							hashMap.put(entry.getKey(), entry.getValue());
						}
					}
					List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils
							.appraisalALLEquipment(goods.goodsInfo.amount, goods.goodsInfo.attrib, hashMap);
					if (hashtables.size() > 0) {
						ZhuangbeiInfo zhuangbeiInfo = GameData.that.baseZhuangbeiInfoService.findOneByStr(current);
						for (Hashtable<String, Integer> maps : hashtables) {
							if (maps.get("groupNo") == 2) {
								maps.put("groupType", 2);
								GoodsLanSe gooodsLanSe = JSONObject.parseObject(JSONObject.toJSONString(maps),
										GoodsLanSe.class);
								GameUtil.huodezhuangbei(chara, zhuangbeiInfo, 0, 1, gooodsLanSe);
							}
						}
						GameUtil.removemunber(chara, goods, 1);
						Vo_40964_0 vo_40964_0 = new Vo_40964_0();
						vo_40964_0.type = 1;
						vo_40964_0.name = zhuangbeiInfo.getStr();
						vo_40964_0.param = "20691134";
						vo_40964_0.rightNow = 0;
						GameObjectChar.send(new M40964_0(), vo_40964_0);
						Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
						vo_9129_0.notify = 10000;
						vo_9129_0.para = "20691134";
						GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
						vo_20481_0.msg = "你成功合成了1个#R" + zhuangbeiInfo.getStr() + "#n。";
						vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_0);
					} else {
						Vo_20481_0 vo_20481_2 = new Vo_20481_0();
						vo_20481_2.msg = "合成失败!";
						vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_2);
					}
					int coin = ConsumeMoneyUtils.appraisalMoney(attrib);
					chara.cash -= coin;
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					if (goods.goodsInfo.attrib >= 100) {
						infoList = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService.findByAttrib(70);
						for (int k = 0; k < infoList.size(); ++k) {
							if (infoList.get(k).getAmount() == goods.goodsInfo.amount) {
								current = infoList.get(k).getStr();
							}
						}
						GameUtil.removemunber(chara, current, 2);
					} else {
						GameUtil.removemunber(chara, "超级女娲石", 2);
					}
				}
			}
		}
		// 首饰合成
		if (6 == type) {
			String[] split = para.split("\\_");
			String pos2 = split[0];
			int pos4 = Integer.parseInt(split[2]);
			int ClassCurrent = 0;
			String goodsName = "";
			ZhuangbeiInfo zhuangbeiInfo2 = GameData.that.baseZhuangbeiInfoService.findOneByStr(pos2);
			if (zhuangbeiInfo2.getAttrib() <= 50) {
				ClassCurrent = zhuangbeiInfo2.getAttrib() - 15;
			} else {
				ClassCurrent = zhuangbeiInfo2.getAttrib() - 10;
			}
			List<ZhuangbeiInfo> infoList2 = (List<ZhuangbeiInfo>) GameData.that.baseZhuangbeiInfoService
					.findByAttrib(ClassCurrent);
			for (int l = 0; l < infoList2.size(); ++l) {
				if (infoList2.get(l).getAmount() == zhuangbeiInfo2.getAmount()) {
					goodsName = infoList2.get(l).getStr();
				}
			}
			// 一键合成
			if (pos4 == 1) {
				int currentcount = 0;
				for (int m = 0; m < chara.backpack.size(); ++m) {
					Goods goods2 = chara.backpack.get(m);
					if (goodsName.equals(goods2.goodsInfo.str)) {
						currentcount += goods2.goodsInfo.owner_id;
					}
				}
				if (currentcount < 3) {
					GameUtil.sendMeTips("材料不足无法合成！");
					return;
				}
				int owner_id = currentcount / 3;
				GameUtil.removemunber(chara, goodsName, owner_id * 3);
				GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, owner_id);

				Vo_20481_0 vo_20481_0 = new Vo_20481_0();
				vo_20481_0.msg = "你成功合成了1个#R" + pos2 + "#n。";
				vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
				for (int i2 = 0; i2 < owner_id; ++i2) {
					GameObjectChar.send(new M20481_0(), vo_20481_0);
				}
				int coin2 = ConsumeMoneyUtils.appraisalMoney(zhuangbeiInfo2.getAttrib());
				chara.cash -= coin2 * owner_id;
				ListVo_65527_0 listVo_65527_2 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_2);
			} else {
				// 判断数量材料是否充足
				int goodsAvaliableNum = GameCommonUtil.getGoodsAvaliableNum(chara.backpack, goodsName);
				String msg = "你成功合成了1个#R" + pos2 + "#n。";
				if (zhuangbeiInfo2.getAttrib() <= 70) {
					if (goodsAvaliableNum < 3) {
						GameUtil.sendMeTips("材料不足无法合成！");
						return;
					}
					GameUtil.removemunber(chara, goodsName, 3);
					GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1);
				} else {
					if (goodsAvaliableNum < 1) {
						GameUtil.sendMeTips("材料不足无法合成！");
						return;
					} else if (GameCommonUtil.getGoodsAvaliableNum(chara.backpack, "超级女娲石") < 2) {
						GameUtil.sendMeTips("超级女娲石不足无法合成！");
						return;
					}
					List<Hashtable<String, Integer>> hashtables2 = null;
					hashtables2 = ForgingEquipmentUtils.appraisalALLEquipment(zhuangbeiInfo2.getAmount(),
							zhuangbeiInfo2.getAttrib(), null);
					GameUtil.removemunber(chara, goodsName, 1);
					GameUtil.removemunber(chara, "超级女娲石", 2);
					if (hashtables2 != null && hashtables2.size() >= 0) {
						for (Hashtable<String, Integer> maps2 : hashtables2) {
							if (maps2.get("groupNo") == 2) {
								maps2.put("groupType", 2);
								GoodsLanSe gooodsLanSe2 = JSONObject.parseObject(JSONObject.toJSONString(maps2),
										GoodsLanSe.class);
								GameUtil.huodezhuangbei(chara, zhuangbeiInfo2, 0, 1, gooodsLanSe2);
							}
						}
					}
				}
				Vo_20481_0 vo_20481_3 = new Vo_20481_0();
				vo_20481_3.msg = msg;
				vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_3);

				int coin3 = ConsumeMoneyUtils.appraisalMoney(zhuangbeiInfo2.getAttrib());
				chara.cash -= coin3;
				ListVo_65527_0 listVo_65527_3 = GameUtil.a65527(chara);
				GameObjectChar.send(new M65527_0(), listVo_65527_3);
			}
			Vo_GENERAL_NOTIFY vo_9129_2 = new Vo_GENERAL_NOTIFY();
			vo_9129_2.notify = 10000;
			vo_9129_2.para = "20643387";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_2);
		}

		// 改造共鸣
		if (24 == type) {
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i = 0; i < goodss.size(); ++i) {
				String[] split2 = para.split("\\|");
				int pos5 = Integer.parseInt(split2[0]);
				Goods goods = goodss.get(i);
				boolean has = true;
				if (pos == goods.pos) {
					Map<Object, Object> goodsGaiZaoGongMing = UtilObjMapshuxing
							.GoodsGaiZaoGongMing(goods.goodsGaiZaoGongMing);
					for (Map.Entry<Object, Object> entry : goodsGaiZaoGongMing.entrySet()) {
						if (!entry.getKey().equals("groupNo")) {
							if (entry.getKey().equals("groupType")) {
								continue;
							}
							if (entry.getValue().toString().equals("0")) {
								continue;
							}
							has = false;
						}
					}

					List<Hashtable<String, Integer>> hashtables = ForgingEquipmentUtils
							.resonanceEquipMent(goods.goodsInfo.attrib, goods.goodsInfo.color, pos5, has);

					if (hashtables.size() > 0) {
						for (Hashtable<String, Integer> maps3 : hashtables) {
							if (maps3.get("groupNo") == 27) {
								maps3.put("groupType", 2);
								GoodsGaiZaoGongMing goodsLvSeGongMing = JSONObject
										.parseObject(JSONObject.toJSONString(maps3), GoodsGaiZaoGongMing.class);
								goods.goodsGaiZaoGongMing = goodsLvSeGongMing;
							}
						}
						List<Goods> list = new ArrayList<Goods>();
						list.add(goods);
						GameObjectChar.send(new M65525_0(), list);
						Vo_41191_0 vo_41191_0 = new Vo_41191_0();
						vo_41191_0.flag = 1;
						vo_41191_0.opType = "";
						GameObjectChar.send(new M41191_0(), vo_41191_0);
						Vo_20481_0 vo_20481_4 = new Vo_20481_0();
						vo_20481_4.msg = "恭喜你，炼化成功!";
						vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_4);
						GameUtil.a65511(gameObjectChar);
					} else {
						Vo_41191_0 vo_41191_2 = new Vo_41191_0();
						vo_41191_2.flag = 0;
						vo_41191_2.opType = "";
						GameObjectChar.send(new M41191_0(), vo_41191_2);
						Vo_20481_0 vo_20481_3 = new Vo_20481_0();
						vo_20481_3.msg = "炼化失败，请继续努力";
						vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_3);
					}
					int coin = ConsumeMoneyUtils.remakeMoney(goods.goodsInfo.attrib);
					chara.cash -= coin;
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, "装备共鸣石", pos5);
				}
			}
		}

		// 改造装备
		// para：单次改造表示超级灵石的数量，一键改造则为0
		if (3 == type) {
			String[] split = para.split("\\_");
			// 这里是一键改造的逻辑
			if (split.length == 1) {
				int iswuqi = 0;
				List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
				for (int i3 = 0; i3 < goodss.size(); ++i3) {
					Goods goods4 = goodss.get(i3);
					if (pos == goods4.pos) {
						if (goods4.goodsInfo.color >= 12) {
							GameUtil.sendMeTips("装备已到达最高改造等级");
							return;
						}
						int isSuccess = 0;
						iswuqi = goods4.goodsInfo.amount;
						int money = ConsumeMoneyUtils.remakeMoney(goods4.goodsInfo.attrib);
						if (chara.cash < money) {
							GameUtil.sendMeTips("金钱不足。");
							Vo_GENERAL_NOTIFY vo_9129_3 = new Vo_GENERAL_NOTIFY();
							vo_9129_3.notify = 48;
							vo_9129_3.para = pos + "_" + 0;
							GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_3);
							return;
						}
						int subCoin = 0;
						int num = goods4.goodsInfo.color;
						if (goods4.goodsInfo.color == 0) {
							num = 1;
						}
						// 改造配置
						EquipGaiZaoConfig config = GameConfig.equipGaiZaoConfig;
						if (GameConfig.equipGaiZao.get("n" + num).equals("积分")) {
							// 武器
							if (type == 1) {
								subCoin = config.equipPrice;
							} else {
								subCoin = config.defPrice;
							}
							if (chara.chargeScore < subCoin) {
								GameUtil.sendMeTips("充值积分不足,无法改造");
								return;
							}
							chara.chargeScore -= subCoin;
							GameUtilRenWu.refshPointTask(chara);

							GameUtil.sendMeTips("你消耗了#R" + subCoin + "点#n充值积分用于改造装备");
						} else {
							if (iswuqi == 1) {
								// 武器自动减去的元宝
								subCoin = 2388;
							} else {
								// 如果是防具的话
								subCoin = 648;
							}
							if ("1".equals(para)) {
								// 采用银元宝
								if (chara.silverCoin < subCoin) {
									GameUtil.sendMeTips("银元宝不足,无法改造");
									return;
								} else {
									// 减去银元宝
									chara.silverCoin -= subCoin;
								}
							} else {
								if (chara.goldCoin < subCoin) {
									GameUtil.sendMeTips("金元宝不足,无法改造");
									return;
								} else {
									// 减去元宝
									chara.goldCoin -= subCoin;
								}
							}
						}
						// 扣除金钱
						chara.cash -= money;
						GameObjectChar.send(new M65525_0(), chara.backpack);
						String str = null;
						Map<Object, Object> goodsGaiZai = UtilObjMapshuxing
								.GoodsGaiZaoGongMing(goods4.goodsGaiZaoGongMing);
						// 获取改造的值
						int[] ints = ForgingEquipmentUtils.remakeAttrib(goods4.goodsInfo.color,
								goods4.goodsInfo.store_exp, 6, goods4.goodsInfo.amount);
						if (ints[0] != goods4.goodsInfo.color) {
							goods4.goodsInfo.store_exp = 0;
							for (Map.Entry<Object, Object> entry2 : goodsGaiZai.entrySet()) {
								if (!entry2.getKey().equals("groupNo")) {
									if (entry2.getKey().equals("groupType")) {
										continue;
									}
									if (entry2.getValue().toString().equals("0")) {
										continue;
									}
									str = (String) entry2.getKey();
								}
							}
							//改造共鸣中文名
							String equipmentKeyByName = ForgingEquipmentUtils.getEquipmentKeyByName(str, false);
							List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils
									.appraisalRemakeEquipment(equipmentKeyByName, goods4.goodsInfo.amount,
											goods4.goodsInfo.attrib, goods4.goodsInfo.color + 1);
							for (Hashtable<String, Integer> maps2 : hashtables2) {
								if (equipmentKeyByName != null) {
									if (maps2.get("groupNo") == 27) {
										maps2.put("groupType", 2);
										GoodsGaiZaoGongMing goodsGaiZaoGongMing2 = JSONObject
												.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZaoGongMing.class);
										goods4.goodsGaiZaoGongMing = goodsGaiZaoGongMing2;
									}
								}
								if (maps2.get("groupNo") == 10) {
									maps2.put("groupType", 2);
									GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2),
											GoodsGaiZao.class);
									goods4.goodsGaiZao = goodsGaiZao;
								}
							}
							GoodsInfo goodsInfo = goods4.goodsInfo;
							++goodsInfo.color;
							GameObjectChar.send(new M32775_0(), goods4);
							List<Goods> listgood = new ArrayList<Goods>();
							listgood.add(goods4);
							// 增加完美度
							goods4.goodsInfo.dunwu_times = (int) (goodsInfo.color * 4.3 * 100);
							if (goods4.goodsInfo.dunwu_times >= (100 * 100)) {
								goods4.goodsInfo.dunwu_times = (int) (99.99 * 100);
							}
							GameObjectChar.send(new M65525_0(), listgood);

							Vo_20481_0 vo_20481_0 = new Vo_20481_0();
							vo_20481_0.msg = "恭喜你，改造成功！装备的改造等级提升到1级";
							vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
							GameObjectChar.send(new M20481_0(), vo_20481_0);

							GameUtil.a65511(gameObjectChar);
							Vo_41191_0 vo_41191_4 = new Vo_41191_0();
							vo_41191_4.flag = 1;
							vo_41191_4.opType = "equip_upgrade";
							GameObjectChar.send(new M41191_0(), vo_41191_4);
							isSuccess = 1;
						} else {
							goods4.goodsInfo.store_exp = ints[1];
							List<Goods> listgood2 = new ArrayList<Goods>();
							listgood2.add(goods4);
							GameObjectChar.send(new M65525_0(), listgood2);
							Vo_20481_0 vo_20481_4 = new Vo_20481_0();
							vo_20481_4.msg = "改造失敗，再接再厉";
							vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
							GameObjectChar.send(new M20481_0(), vo_20481_4);
							Vo_41191_0 vo_41191_5 = new Vo_41191_0();
							vo_41191_5.flag = 0;
							vo_41191_5.opType = "equip_upgrade";
							GameObjectChar.send(new M41191_0(), vo_41191_5);
							isSuccess = 0;
						}
						ListVo_65527_0 listVo_65527_4 = GameUtil.a65527(chara);
						GameObjectChar.send(new M65527_0(), listVo_65527_4);
						
						// 通知
						Vo_GENERAL_NOTIFY vo_9129_3 = new Vo_GENERAL_NOTIFY();
						vo_9129_3.notify = goods4.pos;
						vo_9129_3.para = pos+"_"+isSuccess;
						GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_3);
						break;
					}
				}
				return;
			}
			int pos7 = Integer.parseInt(split[0]); // 使用的超级晶石的个数
			int iswuqi = 0;
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i3 = 0; i3 < goodss.size(); ++i3) {
				Goods goods4 = goodss.get(i3);
				if (pos == goods4.pos) {
					if (goods4.goodsInfo.color >= 12) {
						GameUtil.sendMeTips("装备已到达最高改造等级");
						return;
					}
					iswuqi = goods4.goodsInfo.amount;
					int goodsNum = GameCommonUtil.getGoodsNum(chara, iswuqi == 1 ? "超级灵石" : "超级晶石");
					if (goodsNum < pos7) {
						GameUtil.sendMeTips(iswuqi == 1 ? "#R超级灵石" : "#R超级晶石" + "#n不足。");
						return;
					}
					int coin5 = ConsumeMoneyUtils.remakeMoney(goods4.goodsInfo.attrib);
					if (chara.cash < coin5) {
						GameUtil.sendMeTips("金钱不足。");
						return;
					}
					// 改造配置
					EquipGaiZaoConfig config = GameConfig.equipGaiZaoConfig;
					int num = goods4.goodsInfo.color;
					if (goods4.goodsInfo.color == 0) {
						num = 1;
					}
					if (GameConfig.equipGaiZao.get("n" + num).equals("积分")) {
						int subCoin = 0;
						// 武器
						if (type == 1) {
							subCoin = config.equipPrice;
						} else {
							subCoin = config.defPrice;
						}
						if (chara.chargeScore < subCoin) {
							GameUtil.sendMeTips("充值积分不足,无法改造");
							return;
						}
						chara.chargeScore -= subCoin;
						GameUtilRenWu.refshPointTask(chara);

						GameUtil.sendMeTips("你消耗了#R" + subCoin + "点#n充值积分用于改造装备");
					} else {
						// 扣除道具
						if (iswuqi == 1) {
							GameUtil.removemunber(chara, "超级灵石", pos7);
						} else {
							GameUtil.removemunber(chara, "超级晶石", pos7);
						}
						GameObjectChar.send(new M65525_0(), chara.backpack);
					}
					String str = null;
					Map<Object, Object> goodsGaiZai = UtilObjMapshuxing.GoodsGaiZaoGongMing(goods4.goodsGaiZaoGongMing);
					// 获取改造的值
					int[] ints = ForgingEquipmentUtils.remakeAttrib(goods4.goodsInfo.color, goods4.goodsInfo.store_exp,
							pos7, goods4.goodsInfo.amount);
					if (ints[0] != goods4.goodsInfo.color) {
						goods4.goodsInfo.store_exp = 0;
						for (Map.Entry<Object, Object> entry2 : goodsGaiZai.entrySet()) {
							if (!entry2.getKey().equals("groupNo")) {
								if (entry2.getKey().equals("groupType")) {
									continue;
								}
								if (entry2.getValue().toString().equals("0")) {
									continue;
								}
								str = (String) entry2.getKey();
							}
						}
						// 改造共鸣中文名
						String equipmentKeyByName = ForgingEquipmentUtils.getEquipmentKeyByName(str, false);
						List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils.appraisalRemakeEquipment(
								equipmentKeyByName, goods4.goodsInfo.amount, goods4.goodsInfo.attrib,
								goods4.goodsInfo.color + 1);
						for (Hashtable<String, Integer> maps2 : hashtables2) {
							if (equipmentKeyByName != null) {
								if (maps2.get("groupNo") == 27) {
									maps2.put("groupType", 2);
									GoodsGaiZaoGongMing goodsGaiZaoGongMing2 = JSONObject
											.parseObject(JSONObject.toJSONString(maps2), GoodsGaiZaoGongMing.class);
									goods4.goodsGaiZaoGongMing = goodsGaiZaoGongMing2;
								}
							}
							if (maps2.get("groupNo") == 10) {
								maps2.put("groupType", 2);
								GoodsGaiZao goodsGaiZao = JSONObject.parseObject(JSONObject.toJSONString(maps2),
										GoodsGaiZao.class);
								goods4.goodsGaiZao = goodsGaiZao;
							}
						}
						GoodsInfo goodsInfo = goods4.goodsInfo;
						++goodsInfo.color;
						GameObjectChar.send(new M32775_0(), goods4);
						List<Goods> listgood = new ArrayList<Goods>();
						listgood.add(goods4);
						GameObjectChar.send(new M65525_0(), listgood);

						// 增加完美度
						goods4.goodsInfo.dunwu_times = (int) (goodsInfo.color * 4.3 * 100);
						if (goods4.goodsInfo.dunwu_times >= (100 * 100)) {
							goods4.goodsInfo.dunwu_times = (int) (99.99 * 100);
						}
						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
						vo_20481_0.msg = "恭喜你，改造成功！装备的改造等级提升到1级";
						vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_0);

						GameUtil.a65511(gameObjectChar);
						Vo_41191_0 vo_41191_4 = new Vo_41191_0();
						vo_41191_4.flag = 1;
						vo_41191_4.opType = "equip_upgrade";
						GameObjectChar.send(new M41191_0(), vo_41191_4);
					} else {
						goods4.goodsInfo.store_exp = ints[1];
						List<Goods> listgood2 = new ArrayList<Goods>();
						listgood2.add(goods4);
						GameObjectChar.send(new M65525_0(), listgood2);
						Vo_20481_0 vo_20481_4 = new Vo_20481_0();
						vo_20481_4.msg = "改造失敗，再接再厉";
						vo_20481_4.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_4);
						Vo_41191_0 vo_41191_5 = new Vo_41191_0();
						vo_41191_5.flag = 0;
						vo_41191_5.opType = "equip_upgrade";
						GameObjectChar.send(new M41191_0(), vo_41191_5);
					}
					// 通知
					Vo_GENERAL_NOTIFY vo_9129_3 = new Vo_GENERAL_NOTIFY();
					vo_9129_3.notify = 54;
					vo_9129_3.para = pos + "";
					GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_3);

					// 扣除金钱
					chara.cash -= coin5;
					ListVo_65527_0 listVo_65527_4 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_4);
					return;
				}
			}
		}
		// 粉水晶炼化
		if (7 == type) {
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i = 0; i < goodss.size(); ++i) {
				Goods goods = goodss.get(i);
				if (goods.pos == pos) {
					// 如果不是套装，修改粉化后的装备颜色为粉色。或者还没有黄属性了
					if (goods.goodsInfo.suit_enabled == 0 && !goods.goodsInfo.quality.equals("金色"))
						goods.goodsInfo.quality = "粉色";
					
					if(GameCommonUtil.getGoodsNum(chara, "超级粉水晶")<=0) {
						GameUtil.sendMeTips("超级粉水晶不足。");
						return;
					}
					Map<Object, Object> goodsHuangSe2 = UtilObjMapshuxing.GoodsHuangSe(goods.goodsHuangSe);
					Map<Object, Object> goodsLanSe3 = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
					Map<Object, Object> goodsFenSe2 = UtilObjMapshuxing.GoodsFenSe(goods.goodsFenSe);
					HashSet set2 = new HashSet(); // 不能放重复的属性进去
					List a2 = new ArrayList(); // 可以放重复的属性名
					for (Map.Entry<Object, Object> entry : goodsHuangSe2.entrySet()) {
						if (!entry.getKey().equals("groupNo")) {
							if (entry.getKey().equals("groupType")) {
								continue;
							}
							if (entry.getValue().toString().equals("0")) {
								continue;
							}
							set2.add(entry.getKey());
							a2.add(entry.getKey());
						}
					}
					for (Map.Entry<Object, Object> entry : goodsLanSe3.entrySet()) {
						if (!entry.getKey().equals("groupNo")) {
							if (entry.getKey().equals("groupType")) {
								continue;
							}
							if (entry.getValue().toString().equals("0")) {
								continue;
							}
							set2.add(entry.getKey());
							a2.add(entry.getKey());
						}
					}
					for (Map.Entry<Object, Object> entry : goodsFenSe2.entrySet()) {
						if (!entry.getKey().equals("groupNo")) {
							if (entry.getKey().equals("groupType")) {
								continue;
							}
							if (entry.getValue().toString().equals("0")) {
								continue;
							}
							set2.add(entry.getKey());
							a2.add(entry.getKey());
						}
					}
					Collection rs2 = CollectionUtils.disjunction(a2, set2);
					set2 = new HashSet();
					Object[] objects2 = rs2.toArray();
					for (int j3 = 0; j3 < objects2.length; ++j3) {
						set2.add(objects2[j3]);
					}

					// 是属性名和属性值的k,v
					List<Hashtable<String, Integer>> hashtables2 = ForgingEquipmentUtils
							.appraisalEquipment(goods.goodsInfo.amount, goods.goodsInfo.attrib, 3, set2);
					for (Hashtable<String, Integer> maps2 : hashtables2) {
						maps2.put("groupType", 2);
						GoodsFenSe goodsLanSeObj2 = JSONObject.parseObject(JSONObject.toJSONString(maps2),
								GoodsFenSe.class);
						goods.goodsFenSe = goodsLanSeObj2;
						GameUtil.a65511(gameObjectChar);
						GameObjectChar.send(new M65525_0(), chara.backpack);

						int coin7 = ConsumeMoneyUtils.pinkMoney(goods.goodsInfo.attrib);
						chara.cash -= coin7; // 扣除粉化所需的钱
						ListVo_65527_0 listVo_65527_6 = GameUtil.a65527(chara);
						GameObjectChar.send(new M65527_0(), listVo_65527_6);

						Vo_41191_0 vo_41191_7 = new Vo_41191_0();
						vo_41191_7.flag = 1;
						vo_41191_7.opType = "gold_refine";
						GameObjectChar.send(new M41191_0(), vo_41191_7);

						Vo_GENERAL_NOTIFY vo_9129_4 = new Vo_GENERAL_NOTIFY();
						vo_9129_4.notify = 50;
						vo_9129_4.para = "39563320";
						GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_4);
					}
					GameUtil.removemunber(chara, "超级粉水晶", 1);
				}
			}
			return;
		}
		// 黄水晶炼化
		if (8 == type) {
			String[] split = para.split("\\|");
			int pos7 = Integer.parseInt(split[0]);
			int pos3 = Integer.parseInt(split[1]);
			if(GameCommonUtil.getGoodsNum(chara, "黄水晶")<pos3) {
				GameUtil.sendMeTips("黄水晶不足。");
				return;
			}
			
			@SuppressWarnings("unused")
			int pos4 = Integer.parseInt(split[2]);
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i3 = 0; i3 < goodss.size(); ++i3) {
				Goods goods4 = goodss.get(i3);
				if (goods4.pos == pos) {
					// 如果装备不是黄色和粉色则无法进行黄属性
					if (!goods4.goodsInfo.quality.equals("粉色") 
							&& !goods4.goodsInfo.quality.equals("金色") && !goods4.goodsInfo.quality.equals("绿色")) {
						GameUtil.sendMeTips("请先炼化粉属性");
						return;
					}
					// 如果不是套装，修改黄化后的装备颜色为金色
					if (goods4.goodsInfo.suit_enabled == 0)
						goods4.goodsInfo.quality = "金色";
					Map<Object, Object> goodsHuangSe = UtilObjMapshuxing.GoodsHuangSe(goods4.goodsHuangSe);
					Map<Object, Object> goodsLanSe2 = UtilObjMapshuxing.GoodsLanSe(goods4.goodsLanSe);
					Map<Object, Object> goodsFenSe = UtilObjMapshuxing.GoodsFenSe(goods4.goodsFenSe);
					HashSet set = new HashSet();
					List a = new ArrayList();
					for (Map.Entry<Object, Object> entry3 : goodsHuangSe.entrySet()) {
						if (!entry3.getKey().equals("groupNo")) {
							if (entry3.getKey().equals("groupType")) {
								continue;
							}
							if (entry3.getValue().toString().equals("0")) {
								continue;
							}
							a.add(entry3.getKey());
							set.add(entry3.getKey());
						}
					}
					for (Map.Entry<Object, Object> entry3 : goodsLanSe2.entrySet()) {
						if (!entry3.getKey().equals("groupNo")) {
							if (entry3.getKey().equals("groupType")) {
								continue;
							}
							if (entry3.getValue().toString().equals("0")) {
								continue;
							}
							a.add(entry3.getKey());
							set.add(entry3.getKey());
						}
					}
					for (Map.Entry<Object, Object> entry3 : goodsFenSe.entrySet()) {
						if (!entry3.getKey().equals("groupNo")) {
							if (entry3.getKey().equals("groupType")) {
								continue;
							}
							if (entry3.getValue().toString().equals("0")) {
								continue;
							}
							a.add(entry3.getKey());
							set.add(entry3.getKey());
						}
					}
					Collection rs = CollectionUtils.disjunction(a, set);
					set = new HashSet();
					Object[] objects = rs.toArray();
					for (int j2 = 0; j2 < objects.length; ++j2) {
						set.add(objects[j2]);
					}
					List<Hashtable<String, Integer>> hashtables4 = ForgingEquipmentUtils
							.appraisalYellowEquipment(goods4.goodsInfo.amount, goods4.goodsInfo.attrib, 4, set, pos7);
					if (hashtables4.size() > 0) {
						for (Hashtable<String, Integer> maps5 : hashtables4) {
							maps5.put("groupType", 2);
							GoodsHuangSe goodsLanSeObj = JSONObject.parseObject(JSONObject.toJSONString(maps5),
									GoodsHuangSe.class);
							goods4.goodsHuangSe = goodsLanSeObj;
							GameUtil.a65511(gameObjectChar);
							List list2 = new ArrayList();
							list2.add(goods4);
							GameObjectChar.send(new M65525_0(), list2);
							int coin6 = ConsumeMoneyUtils.yellowMoney(goods4.goodsInfo.attrib);
							chara.cash -= coin6;
							ListVo_65527_0 listVo_65527_5 = GameUtil.a65527(chara);
							GameObjectChar.send(new M65527_0(), listVo_65527_5);

							Vo_41191_0 vo_41191_6 = new Vo_41191_0();
							vo_41191_6.flag = 1;
							vo_41191_6.opType = "gold_refine";
							GameObjectChar.send(new M41191_0(), vo_41191_6);

							Vo_GENERAL_NOTIFY vo_9129_3 = new Vo_GENERAL_NOTIFY();
							vo_9129_3.notify = 50;
							vo_9129_3.para = "39563320";
							GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_3);
							GameUtil.removemunber(chara, "黄水晶", pos3);
						}
					} else {
						Vo_41191_0 vo_41191_6 = new Vo_41191_0();
						vo_41191_6.flag = 0;
						vo_41191_6.opType = "gold_refine";
						GameObjectChar.send(new M41191_0(), vo_41191_6);
						Vo_20481_0 vo_20481_5 = new Vo_20481_0();
						vo_20481_5.msg = "炼化失败，请继续努力！";
						vo_20481_5.time = (int) (System.currentTimeMillis() / 1000L);
						GameObjectChar.send(new M20481_0(), vo_20481_5);
					}
				}
			}
			return;
		}
		// 炼化套装，就是将goodsInfo.suit_enable置为1即可, 绿属性
		if (5 == type) {
			String[] split = para.split("\\|");
			int pos3 = Integer.parseInt(split[1]);
			if(GameCommonUtil.getGoodsNum(chara, "超级绿水晶") < 1 && gameObjectChar.privilege == 0) {
				GameUtil.sendMeTips("绿水晶不足");
				return;
			}
			List<Goods> goods = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i3 = 0; i3 < goods.size(); ++i3) {
				Goods goods4 = goods.get(i3);
				if (pos == goods4.pos) {
					// 如果装备不是黄色则无法进行绿化
					if (!goods4.goodsInfo.quality.equals("金色") && !goods4.goodsInfo.quality.equals("绿色")) {
						GameUtil.sendMeTips("请先炼化黄属性");
						return;
					}
					goods4.goodsInfo.suit_enabled = pos3;
					// 将装备的颜色变为绿色
					goods4.goodsInfo.quality = "绿色";
					List<Hashtable<String, Integer>> hashtables3 = ForgingEquipmentUtils
							.appraisalGreenEquipment(goods4.goodsInfo.amount, goods4.goodsInfo.attrib, pos3, chara);
					for (Hashtable<String, Integer> maps4 : hashtables3) {
						if (maps4.get("groupNo") == 12) {
							maps4.put("groupType", 2);
							GoodsLvSe goodsLvSe = (GoodsLvSe) JSONObject.parseObject(JSONObject.toJSONString(maps4),
									GoodsLvSe.class);
							if (goodsLvSe == null) {
								goodsLvSe = new GoodsLvSe();
							}
							goods4.goodsLvSe = goodsLvSe;
						}
						if (maps4.get("groupNo") == 8) {
							maps4.put("groupType", 2);
							GoodsLvSeGongMing goodsLvSeGongMing2 = (GoodsLvSeGongMing) JSONObject
									.parseObject(JSONObject.toJSONString(maps4), GoodsLvSeGongMing.class);
							goods4.goodsLvSeGongMing = goodsLvSeGongMing2;
						}
					}
					int coin4 = ConsumeMoneyUtils.appendEqMoney(goods4.goodsInfo.attrib);
					chara.cash -= coin4;
					GameUtil.a65511(gameObjectChar);
					List<Goods> list = new ArrayList<Goods>();
					list.add(goods4);
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65525_0(), list);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					
					Vo_41191_0 vo_41191_6 = new Vo_41191_0();
					vo_41191_6.flag = 1;
					vo_41191_6.opType = "gold_refine";
					GameObjectChar.send(new M41191_0(), vo_41191_6);
					
					// 通知客户端刷新信息.
					Vo_GENERAL_NOTIFY vo_9129_3 = new Vo_GENERAL_NOTIFY();
					vo_9129_3.notify = 50;
					vo_9129_3.para = "39563320";
					GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_3);

					GameUtil.removemunber(chara, "超级绿水晶", 1);
				}
			}
			return;
		}
		//强化粉
		if (10 == type) {
			String[] split = para.split("\\|");
			int pos7 = Integer.parseInt(split[0]);
			String pos8 = split[1];
			int count2 = 0;
			String attrCnName = "";
			for (int i4 = 0; i4 < chara.backpack.size(); i4++) {
				Goods goods = chara.backpack.get(i4);
				if (goods.pos == pos7) {
					Map<Object, Object> lanse = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
					if(goods.goodsInfo.str.startsWith("超级黑水晶")) {
						for (Map.Entry<Object, Object> map : lanse.entrySet()) {
							if(map.getKey().equals("groupNo") || map.getKey().equals("groupType")) {
								continue;
							}
							if((int)map.getValue()>0) {
								attrCnName = (String) map.getKey();
								++count2;
								break;
							}
						}
					}
					break;
				}
				
			}
			if (count2 == 0) {
				Vo_20481_0 vo_20481_6 = new Vo_20481_0();
				vo_20481_6.msg = "请放入超级黑水晶！";
				vo_20481_6.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_6);
				return;
			}
			//判断是否有超级圣水晶
			if(GameCommonUtil.getGoodsNum(chara, "超级圣水晶") <= 0) {
				GameUtil.sendMeTips("超级圣水晶不足.");
				return;
			}
			int leve = 0;
			Boolean has2 = false;
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i5 = 0; i5 < goodss.size(); ++i5) {
				Goods goods5 = goodss.get(i5);
				if (goods5.pos == pos) {
					leve = goods5.goodsInfo.attrib;
					Map<Object, Object> goodsLanSe4 = UtilObjMapshuxing.GoodsFenSe(goods5.goodsFenSe);
					for (Map.Entry<Object, Object> entry3 : goodsLanSe4.entrySet()) {
						String name = ForgingEquipmentUtils.getErrorFieldByOriginField(pos8, false);
						//要强化的属性和黑水晶里面的属性不对应
						if(!attrCnName.equals(name)) {
							GameUtil.sendMeTips("请放入正确的黑水晶");
							return;
						}
						//获取到当前装备黄属性名称
						for(Map.Entry<Object, Object> field:goodsLanSe4.entrySet()) {
							if(field.getKey().equals("groupNo") || field.getKey().equals("groupType")) {
								continue;
							}
							if((int)field.getValue()>0) {
								String orginName = (String) field.getKey();
								if(!orginName.equals(attrCnName) || !orginName.equals(name)) {
									GameUtil.sendMeTips("请放入正确的黑水晶");
									return;
								}
								break;
							}
						}
						if (entry3.getKey().equals(name)) {
							int[] equipmentKeyByNames = ForgingEquipmentUtils.appendAttrib(name,
									(Integer) entry3.getValue(), goods5.goodsInfo.attrib, goods5.goodsInfo.amount);
							int value = equipmentKeyByNames[0];
							if ((Integer) entry3.getValue() < value) {
								has2 = true;
							}
							goodsLanSe4.put(entry3.getKey(), value);
							GoodsFenSe goodsHuangSeObj1 = JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe4),
									GoodsFenSe.class);
							goods5.goodsFenSe = goodsHuangSeObj1;
							List list2 = new ArrayList();
							list2.add(goods5);
							GameObjectChar.send(new M65525_0(), list2);
						}
					}
					GameUtil.removemunber(chara, "超级圣水晶", 1);
				}
			}
			if (has2) {
				for (int i5 = 0; i5 < chara.backpack.size(); ++i5) {
					Goods goods5 = chara.backpack.get(i5);
					if (goods5.pos == pos7) {
						if (gameObjectChar.privilege == 0) {
							GameObjectChar.send(new MSG_INVENTORY_REMOVE(), pos7);
							chara.backpack.remove(chara.backpack.get(i5));
							GameObjectChar.send(new M65525_0(), chara.backpack);
						}
						break;
					}
				}
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "强化成功，请再接再厉！";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
				Vo_41191_0 vo_41191_0 = new Vo_41191_0();
				vo_41191_0.flag = 1;
				vo_41191_0.opType = "";
				GameObjectChar.send(new M41191_0(), vo_41191_0);
			} else {
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "强化失败!";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
				Vo_41191_0 vo_41191_0 = new Vo_41191_0();
				vo_41191_0.flag = 0;
				vo_41191_0.opType = "";
				GameObjectChar.send(new M41191_0(), vo_41191_0);
			}
			Vo_GENERAL_NOTIFY vo_9129_5 = new Vo_GENERAL_NOTIFY();
			vo_9129_5.notify = 51;
			vo_9129_5.para = "33927504";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_5);
			int coin5 = ConsumeMoneyUtils.appendEqMoney(leve);
			chara.cash -= coin5;
			ListVo_65527_0 listVo_65527_4 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_4);
			return;
		}
		// 强化黄属性
		if (11 == type) {
			String[] split = para.split("\\|");
			int pos7 = Integer.parseInt(split[0]);
			String pos8 = split[1];
			int count2 = 0;
			String attrCnName = "";
			for (int i4 = 0; i4 < chara.backpack.size(); i4++) {
				Goods goods = chara.backpack.get(i4);
				if (goods.pos == pos7) {
					Map<Object, Object> lanse = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
					if(goods.goodsInfo.str.startsWith("超级黑水晶")) {
						for (Map.Entry<Object, Object> map : lanse.entrySet()) {
							if(map.getKey().equals("groupNo") || map.getKey().equals("groupType")) {
								continue;
							}
							if((int)map.getValue()>0) {
								attrCnName = (String) map.getKey();
								++count2;
								break;
							}
						}
					}
					break;
				}
			}
			if (count2 == 0) {
				Vo_20481_0 vo_20481_6 = new Vo_20481_0();
				vo_20481_6.msg = "请放入超级黑水晶！";
				vo_20481_6.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_6);
				return;
			}
			//判断是否有超级圣水晶
			if(GameCommonUtil.getGoodsNum(chara, "超级圣水晶") <= 0) {
				GameUtil.sendMeTips("超级圣水晶不足.");
				return;
			}
			int leve = 0;
			Boolean has2 = false;
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i5 = 0; i5 < goodss.size(); ++i5) {
				Goods goods5 = goodss.get(i5);
				if (goods5.pos == pos) {
					leve = goods5.goodsInfo.attrib;
					Map<Object, Object> goodsLanSe4 = UtilObjMapshuxing.GoodsHuangSe(goods5.goodsHuangSe);
					for (Map.Entry<Object, Object> entry3 : goodsLanSe4.entrySet()) {
						String name = ForgingEquipmentUtils.getErrorFieldByOriginField(pos8, false);
						//要强化的属性和黑水晶里面的属性不对应
						if(!attrCnName.equals(name)) {
							GameUtil.sendMeTips("请放入正确的黑水晶");
							return;
						}
						//获取到当前装备黄属性名称
						for(Map.Entry<Object, Object> field:goodsLanSe4.entrySet()) {
							if(field.getKey().equals("groupNo") || field.getKey().equals("groupType")) {
								continue;
							}
							if((int)field.getValue()>0) {
								String orginName = (String) field.getKey();
								if(!orginName.equals(attrCnName) || !orginName.equals(name)) {
									GameUtil.sendMeTips("请放入正确的黑水晶");
									return;
								}
								break;
							}
						}
						if (entry3.getKey().equals(name)) {
							int[] equipmentKeyByNames = ForgingEquipmentUtils.appendAttrib(name,
									(Integer) entry3.getValue(), goods5.goodsInfo.attrib, goods5.goodsInfo.amount);
							int value = equipmentKeyByNames[0];
							if ((Integer) entry3.getValue() < value) {
								has2 = true;
							}
							goodsLanSe4.put(entry3.getKey(), value);
							GoodsHuangSe goodsHuangSeObj2 = JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe4),
									GoodsHuangSe.class);
							goods5.goodsHuangSe = goodsHuangSeObj2;
							List list2 = new ArrayList();
							list2.add(goods5);
							GameObjectChar.send(new M65525_0(), list2);
						}
					}
					GameUtil.removemunber(chara, "超级圣水晶", 1);
				}
			}
			if (has2) {
				for (int i5 = 0; i5 < chara.backpack.size(); ++i5) {
					Goods goods5 = chara.backpack.get(i5);
					if (goods5.pos == pos7) {
						if (gameObjectChar.privilege == 0) {
							GameObjectChar.send(new MSG_INVENTORY_REMOVE(), pos7);
							chara.backpack.remove(chara.backpack.get(i5));
							GameObjectChar.send(new M65525_0(), chara.backpack);
						}
						break;
					}
				}
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "强化成功，请再接再厉！";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
				Vo_41191_0 vo_41191_0 = new Vo_41191_0();
				vo_41191_0.flag = 1;
				vo_41191_0.opType = "";
				GameObjectChar.send(new M41191_0(), vo_41191_0);
			} else {
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "强化失败!";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
				Vo_41191_0 vo_41191_0 = new Vo_41191_0();
				vo_41191_0.flag = 0;
				vo_41191_0.opType = "";
				GameObjectChar.send(new M41191_0(), vo_41191_0);
			}
			Vo_GENERAL_NOTIFY vo_9129_5 = new Vo_GENERAL_NOTIFY();
			vo_9129_5.notify = 51;
			vo_9129_5.para = "33927504";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_5);
			int coin5 = ConsumeMoneyUtils.appendEqMoney(leve);
			chara.cash -= coin5;
			ListVo_65527_0 listVo_65527_4 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_4);
			return;
		}
		// 强化蓝
		if (9 == type) {
			String[] split = para.split("\\|");
			int pos7 = Integer.parseInt(split[0]);
			String pos8 = split[1];
			int count2 = 0;
			String attrCnName = "";
			for (int i4 = 0; i4 < chara.backpack.size(); i4++) {
				Goods goods = chara.backpack.get(i4);
				if (goods.pos == pos7) {
					Map<Object, Object> lanse = UtilObjMapshuxing.GoodsLanSe(goods.goodsLanSe);
					if(goods.goodsInfo.str.startsWith("超级黑水晶")) {
						for (Map.Entry<Object, Object> map : lanse.entrySet()) {
							if(map.getKey().equals("groupNo") || map.getKey().equals("groupType")) {
								continue;
							}
							if((int)map.getValue()>0) {
								attrCnName = (String) map.getKey();
								++count2;
								break;
							}
						}
					}
					break;
				}
			}
			if (count2 == 0) {
				Vo_20481_0 vo_20481_6 = new Vo_20481_0();
				vo_20481_6.msg = "请放入超级黑水晶！";
				vo_20481_6.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_6);
				return;
			}
			//判断是否有超级圣水晶
			if(GameCommonUtil.getGoodsNum(chara, "超级圣水晶") <= 0) {
				GameUtil.sendMeTips("超级圣水晶不足.");
				return;
			}
			int leve = 0;
			Boolean has2 = false;
			List<Goods> goodss = GameCommonUtil.switchGoodsLocation(chara, pos);
			for (int i5 = 0; i5 < goodss.size(); ++i5) {
				Goods goods5 = goodss.get(i5);
				if (goods5.pos == pos) {
					leve = goods5.goodsInfo.attrib;
					Map<Object, Object> goodsLanSe4 = UtilObjMapshuxing.GoodsLanSe(goods5.goodsLanSe);
					for (Map.Entry<Object, Object> entry3 : goodsLanSe4.entrySet()) {
						String name = ForgingEquipmentUtils.getErrorFieldByOriginField(pos8, false);
						//要强化的属性和黑水晶里面的属性不对应
						if(!attrCnName.equals(name)) {
							GameUtil.sendMeTips("请放入正确的黑水晶");
							return;
						}
						Map<String,Integer> existField = new HashMap<>();
						//获取到当前装备黄属性名称
						for(Map.Entry<Object, Object> field:goodsLanSe4.entrySet()) {
							if(field.getKey().equals("groupNo") || field.getKey().equals("groupType")) {
								continue;
							}
							if((int)field.getValue()>0) {
								existField.put((String)field.getKey(), (int)field.getValue());
							}
						}
						//如果没有找到这类水晶
						if(existField.get(name) == null) {
							GameUtil.sendMeTips("请放入正确的黑水晶");
							return;
						}
						if (entry3.getKey().equals(name)) {
							int[] equipmentKeyByNames = ForgingEquipmentUtils.appendAttrib(name,
									(Integer) entry3.getValue(), goods5.goodsInfo.attrib, goods5.goodsInfo.amount);
							int value = equipmentKeyByNames[0];
							if ((Integer) entry3.getValue() < value) {
								has2 = true;
							}
							goodsLanSe4.put(entry3.getKey(), value);
							GoodsLanSe goodsHuangSeObj3 = JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe4),
									GoodsLanSe.class);
							goods5.goodsLanSe = goodsHuangSeObj3;
							List list2 = new ArrayList();
							list2.add(goods5);
							GameObjectChar.send(new M65525_0(), list2);
						}
					}
					GameUtil.removemunber(chara, "超级圣水晶", 1);
				}
			}
			if (has2) {
				for (int i5 = 0; i5 < chara.backpack.size(); ++i5) {
					Goods goods5 = chara.backpack.get(i5);
					if (goods5.pos == pos7) {
						if (gameObjectChar.privilege == 0) {
							GameObjectChar.send(new MSG_INVENTORY_REMOVE(), pos7);
							chara.backpack.remove(chara.backpack.get(i5));
							GameObjectChar.send(new M65525_0(), chara.backpack);
						}
						break;
					}
				}
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "强化成功，请再接再厉！";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
				Vo_41191_0 vo_41191_0 = new Vo_41191_0();
				vo_41191_0.flag = 1;
				vo_41191_0.opType = "";
				GameObjectChar.send(new M41191_0(), vo_41191_0);
				Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
				vo_9129_0.notify = 51;
				vo_9129_0.para = "33927504";
				GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
			} else {
				Vo_20481_0 vo_20481_2 = new Vo_20481_0();
				vo_20481_2.msg = "强化失败!";
				vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
				GameObjectChar.send(new M20481_0(), vo_20481_2);
				Vo_41191_0 vo_41191_0 = new Vo_41191_0();
				vo_41191_0.flag = 0;
				vo_41191_0.opType = "";
				GameObjectChar.send(new M41191_0(), vo_41191_0);
			}
			int coin = ConsumeMoneyUtils.appendEqMoney(leve);
			chara.cash -= coin;
			ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_0);
			return;
		}
		// 重组装备
		if (4 == type) {
			ZhuangbeiInfo zhuangbeiInfo3 = GameData.that.baseZhuangbeiInfoService.findOneByType(pos);
			int coin8 = ConsumeMoneyUtils.createMoney(zhuangbeiInfo3.getAttrib());
			chara.cash -= coin8;
			ListVo_65527_0 listVo_65527_7 = GameUtil.a65527(chara);
			GameObjectChar.send(new M65527_0(), listVo_65527_7);
			String[] split3 = para.split("\\|");
			int pos9 = Integer.parseInt(split3[0]);
			int pos10 = Integer.parseInt(split3[1]);
			int pos11 = Integer.parseInt(split3[2]);
			Goods goods7 = new Goods();
			Map<Object, Object> goodsLanSe5 = UtilObjMapshuxing.GoodsLanSe(goods7.goodsLanSe);
			Goods backpack2 = null;
			Goods backpack3 = null;
			Goods backpack4 = null;
			for (int i2 = 0; i2 < chara.backpack.size(); ++i2) {
				Goods goods8 = null;
				if (chara.backpack.get(i2).pos == pos9) {
					goods8 = chara.backpack.get(i2);
					Map<Object, Object> goodsLanSe6 = UtilObjMapshuxing.GoodsLanSe(goods8.goodsLanSe);
					for (Map.Entry<Object, Object> entry4 : goodsLanSe6.entrySet()) {
						if (!entry4.getKey().equals("groupNo")) {
							if (entry4.getKey().equals("groupType")) {
								continue;
							}
							if (entry4.getValue().toString().equals("0")) {
								continue;
							}
							goodsLanSe5.put(entry4.getKey(), entry4.getValue());
						}
					}
					List<Goods> listbeibao2 = new ArrayList<Goods>();
					Goods goods9 = new Goods();
					goods9.goodsBasics = null;
					goods9.goodsInfo = null;
					goods9.goodsLanSe = null;
					goods9.pos = pos9;
					listbeibao2.add(goods9);
					backpack2 = chara.backpack.get(i2);
					GameObjectChar.send(new M65525_0(), listbeibao2);
				}
				if (chara.backpack.get(i2).pos == pos10) {
					goods8 = chara.backpack.get(i2);
					Map<Object, Object> goodsLanSe6 = UtilObjMapshuxing.GoodsLanSe(goods8.goodsLanSe);
					for (Map.Entry<Object, Object> entry4 : goodsLanSe6.entrySet()) {
						if (!entry4.getKey().equals("groupNo")) {
							if (entry4.getKey().equals("groupType")) {
								continue;
							}
							if (entry4.getValue().toString().equals("0")) {
								continue;
							}
							goodsLanSe5.put(entry4.getKey(), entry4.getValue());
						}
					}
					List<Goods> listbeibao2 = new ArrayList<Goods>();
					Goods goods9 = new Goods();
					goods9.goodsBasics = null;
					goods9.goodsInfo = null;
					goods9.goodsLanSe = null;
					goods9.pos = pos10;
					listbeibao2.add(goods9);
					backpack3 = chara.backpack.get(i2);
					GameObjectChar.send(new M65525_0(), listbeibao2);
				}
				if (chara.backpack.get(i2).pos == pos11) {
					goods8 = chara.backpack.get(i2);
					Map<Object, Object> goodsLanSe6 = UtilObjMapshuxing.GoodsLanSe(goods8.goodsLanSe);
					for (Map.Entry<Object, Object> entry4 : goodsLanSe6.entrySet()) {
						if (!entry4.getKey().equals("groupNo")) {
							if (entry4.getKey().equals("groupType")) {
								continue;
							}
							if (entry4.getValue().toString().equals("0")) {
								continue;
							}
							goodsLanSe5.put(entry4.getKey(), entry4.getValue());
						}
					}
					List<Goods> listbeibao2 = new ArrayList<Goods>();
					Goods goods9 = new Goods();
					goods9.goodsBasics = null;
					goods9.goodsInfo = null;
					goods9.goodsLanSe = null;
					goods9.pos = pos11;
					listbeibao2.add(goods9);
					backpack4 = chara.backpack.get(i2);
					GameObjectChar.send(new M65525_0(), listbeibao2);
				}
			}
			Vo_40964_0 vo_40964_2 = new Vo_40964_0();
			vo_40964_2.type = 1;
			vo_40964_2.name = zhuangbeiInfo3.getStr();
			vo_40964_2.param = "32271173";
			vo_40964_2.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_2);
			chara.backpack.remove(backpack2);
			chara.backpack.remove(backpack3);
			chara.backpack.remove(backpack4);
			GameUtil.huodezhuangbei(chara, zhuangbeiInfo3, 0, goods7);
			GoodsLanSe goodsHuangSeObj4 = JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe5),
					GoodsLanSe.class);
			goods7.goodsLanSe = goodsHuangSeObj4;
			List<Goods> listbeibao3 = new ArrayList<Goods>();
			listbeibao3.add(goods7);
			GameObjectChar.send(new M65525_0(), listbeibao3);
			Vo_GENERAL_NOTIFY vo_9129_6 = new Vo_GENERAL_NOTIFY();
			vo_9129_6.notify = 49;
			vo_9129_6.para = "32271173";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_6);
		}
		// 拆分装备
		if (type == 2) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (chara.backpack.get(i).pos == pos) {
					int coin9 = ConsumeMoneyUtils.removeMoney(chara.backpack.get(i).goodsInfo.attrib);
					chara.cash -= coin9;
					ListVo_65527_0 listVo_65527_8 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_8);
					Random random = new Random();
					Goods goods4 = chara.backpack.get(i);
					Map<Object, Object> goodsLanSe = UtilObjMapshuxing.GoodsLanSe(goods4.goodsLanSe);
					Map<Object, Object> goodsHuangSe3 = UtilObjMapshuxing.GoodsHuangSe(goods4.goodsHuangSe);
					Map<Object, Object> goodsFenSe = UtilObjMapshuxing.GoodsFenSe(goods4.goodsFenSe);
					String name2 = "";
					int cont = random.nextInt(10);
					if (para.equals("3")) {
						cont = 2;
					}
					int jilv = 2;
					for (Map.Entry<Object, Object> entry5 : goodsLanSe.entrySet()) {
						if ((Integer) entry5.getValue() != 0 && cont <= jilv) {
							if (entry5.getKey().equals("groupNo")) {
								continue;
							}
							if (entry5.getKey().equals("groupType")) {
								continue;
							}
							Goods good = new Goods();
							Map<Object, Object> goodsLanSe7 = UtilObjMapshuxing.GoodsLanSe(good.goodsLanSe);
							goodsLanSe7.put(entry5.getKey(), entry5.getValue());
							name2 = ForgingEquipmentUtils.getEquipmentKeyByName((String) entry5.getKey(), false);
							if (name2.contentEquals("伤害_最低伤害")) {
								name2 = "伤害";
							}
							StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName("超级黑水晶");
							GoodsLanSe goodsLanSeObj3 = JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe7),
									GoodsLanSe.class);
							good.goodsLanSe = goodsLanSeObj3;
							GameUtil.huodecaifen(chara, storeInfo, 1, goods4.goodsInfo.attrib,
									(Integer) entry5.getValue(), name2, good, goods4.goodsInfo.amount);
							goodsLanSe.remove(entry5.getKey());
							GoodsLanSe goodsLanSeObj4 = JSONObject.parseObject(JSONObject.toJSONString(goodsLanSe),
									GoodsLanSe.class);
							goods4.goodsLanSe = goodsLanSeObj4;
							List list3 = new ArrayList();
							list3.add(goods4);
							GameObjectChar.send(new M65525_0(), list3);
							jilv = 0;
							break;
						}
					}
					for (Map.Entry<Object, Object> entry5 : goodsHuangSe3.entrySet()) {
						if ((Integer) entry5.getValue() != 0 && cont <= jilv) {
							if (entry5.getKey().equals("groupNo")) {
								continue;
							}
							if (entry5.getKey().equals("groupType")) {
								continue;
							}
							Goods good = new Goods();
							Map<Object, Object> goodsHuangSe4 = UtilObjMapshuxing.GoodsLanSe(good.goodsLanSe);
							goodsHuangSe4.put(entry5.getKey(), entry5.getValue());
							name2 = ForgingEquipmentUtils.getEquipmentKeyByName((String) entry5.getKey(), false);
							if (name2.contentEquals("伤害_最低伤害")) {
								name2 = "伤害";
							}
							StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName("超级黑水晶");
							GoodsLanSe goodsHuangSeObj5 = JSONObject.parseObject(JSONObject.toJSONString(goodsHuangSe4),
									GoodsLanSe.class);
							good.goodsLanSe = goodsHuangSeObj5;
							GameUtil.huodecaifen(chara, storeInfo, 1, goods4.goodsInfo.attrib,
									(Integer) entry5.getValue(), name2, good, goods4.goodsInfo.amount);
							goodsHuangSe3.remove(entry5.getKey());
							GoodsHuangSe goodsHuangSeObj6 = JSONObject
									.parseObject(JSONObject.toJSONString(goodsHuangSe3), GoodsHuangSe.class);
							goods4.goodsHuangSe = goodsHuangSeObj6;
							List list3 = new ArrayList();
							list3.add(goods4);
							GameObjectChar.send(new M65525_0(), list3);
							jilv = 0;
							break;
						}
					}
					for (Map.Entry<Object, Object> entry5 : goodsFenSe.entrySet()) {
						if (!entry5.getKey().equals("groupNo")) {
							if (entry5.getKey().equals("groupType")) {
								continue;
							}
							if ((Integer) entry5.getValue() != 0 && cont <= jilv) {
								Goods good = new Goods();
								Map<Object, Object> goodsFenSe3 = UtilObjMapshuxing.GoodsLanSe(good.goodsLanSe);
								goodsFenSe3.put(entry5.getKey(), entry5.getValue());
								name2 = ForgingEquipmentUtils.getEquipmentKeyByName((String) entry5.getKey(), false);
								if (name2.contentEquals("伤害_最低伤害")) {
									name2 = "伤害";
								}
								StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName("超级黑水晶");
								GoodsLanSe goodsFenSeObj = JSONObject.parseObject(JSONObject.toJSONString(goodsFenSe3),
										GoodsLanSe.class);
								good.goodsLanSe = goodsFenSeObj;
								GameUtil.huodecaifen(chara, storeInfo, 1, goods4.goodsInfo.attrib,
										(Integer) entry5.getValue(), name2, good, goods4.goodsInfo.amount);
								goodsFenSe.remove(entry5.getKey());
								GoodsFenSe goodsFenSeObj2 = JSONObject.parseObject(JSONObject.toJSONString(goodsFenSe),
										GoodsFenSe.class);
								goods4.goodsFenSe = goodsFenSeObj2;
								List list3 = new ArrayList();
								list3.add(goods4);
								GameObjectChar.send(new M65525_0(), list3);
								jilv = 0;
								break;
							}
							continue;
						}
					}
					int number = 0;
					for (Map.Entry<Object, Object> entry6 : goodsLanSe.entrySet()) {
						if (!entry6.getKey().equals("groupNo")) {
							if (entry6.getKey().equals("groupType")) {
								continue;
							}
							number += (Integer) entry6.getValue();
						}
					}
					for (Map.Entry<Object, Object> entry6 : goodsHuangSe3.entrySet()) {
						if (!entry6.getKey().equals("groupNo")) {
							if (entry6.getKey().equals("groupType")) {
								continue;
							}
							number += (Integer) entry6.getValue();
						}
					}
					for (Map.Entry<Object, Object> entry6 : goodsFenSe.entrySet()) {
						if (!entry6.getKey().equals("groupNo")) {
							if (entry6.getKey().equals("groupType")) {
								continue;
							}
							number += (Integer) entry6.getValue();
						}
					}
					if (number == 0) {
						List<Goods> listbeibao4 = new ArrayList<Goods>();
						Goods goods10 = new Goods();
						goods10.goodsBasics = null;
						goods10.goodsInfo = null;
						goods10.goodsLanSe = null;
						goods10.pos = pos;
						listbeibao4.add(goods10);
						chara.backpack.remove(chara.backpack.get(i));
						GameObjectChar.send(new M65525_0(), listbeibao4);
					}
					Vo_20481_0 vo_20481_7 = new Vo_20481_0();
					if (name2.equals("")) {
						vo_20481_7.msg = "拆分失败，请继续努力";
					} else {
						vo_20481_7.msg = "你成功拆分出了属性#R" + name2 + "#n";
					}
					vo_20481_7.time = (int) (System.currentTimeMillis() / 1000L);
					GameObjectChar.send(new M20481_0(), vo_20481_7);
					GameUtil.removemunber(chara, "超级黑水晶", 1);
					if (para.equals("3")) {
						GameUtil.removemunber(chara, "混沌玉", 1);
					}
				}
			}
		}
		// 鉴定装备，1是普通鉴定，12是精致鉴定
		if (1 == type || 12 == type) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (chara.backpack.get(i).pos == pos) {
					Goods goods = chara.backpack.get(i);
					if (goods.goodsInfo.degree_32 == 0) {
						GameUtil.sendMeTips("该装备已鉴定！");
						return;
					}
					ZhuangbeiInfo zhuangb = GameData.that.baseZhuangbeiInfoService.findOneByStr(goods.goodsInfo.str);
					Goods newGoods = new Goods();
					int newPos = GameUtil.packPoint(chara);
					if (newPos == -1) {
						return;
					}
					newGoods.pos = newPos;
					newGoods.goodsInfo = new GoodsInfo();
					newGoods.goodsBasics = new GoodsBasics();
					newGoods.goodsCreate(zhuangb);
					newGoods.goodsInfo.owner_id = 1;
					newGoods.goodsInfo.degree_32 = 0;
					newGoods.goodsLanSe = ForgingEquipmentUtils.randomCount3BlueAttribute(goods);
					GameObjectCharMng.getGameObjectChar(chara.id).sendOne(new M65525_0(), chara.backpack);
					int coin10 = ConsumeMoneyUtils.appraisalMoney(goods.goodsInfo.attrib);
					chara.cash -= coin10;
					ListVo_65527_0 listVo_65527_9 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_9);
					GameUtil.addwupin(newGoods, chara);
					// 让物品消失
					GameUtil.removemunber(chara, goods, 1);
					Vo_40964_0 vo_40964_0 = new Vo_40964_0();
					vo_40964_0.type = 1;
					vo_40964_0.name = newGoods.goodsInfo.str;
					vo_40964_0.param = "20691134";
					vo_40964_0.rightNow = 0;
					GameObjectChar.send(new M40964_0(), vo_40964_0, chara.id);
					break;
				}
			}
			Vo_20481_0 vo_20481_8 = new Vo_20481_0();
			vo_20481_8.msg = "鉴定成功！";
			vo_20481_8.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_8);
			Vo_GENERAL_NOTIFY vo_9129_7 = new Vo_GENERAL_NOTIFY();
			vo_9129_7.notify = 20022;
			vo_9129_7.para = "11516529|1";
			GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_7);
		}
		// 首饰强化
		if (32 == type) {
//			for (int i = 0; i < chara.backpack.size(); ++i) {
//				if (chara.backpack.get(i).pos == pos) {
//					Goods goods0 = chara.backpack.get(i);
//					if (chara.shoushiQianghuacishu <= 3 || GameObjectChar.getGameObjectChar().privilege != 0) {
//						if (chara.cash < 2000000 || chara.pot < 5000000) {
//							Vo_20481_0 vo_20481_0 = new Vo_20481_0();
//							vo_20481_0.msg = "你当前的金币不足或者经验不足！#R无法强化#n首饰";
//							vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//							GameObjectChar.send(new M20481_0(), vo_20481_0);
//							return;
//						}
//						if(goods0.goodsInfo.strengthen_level+1>20) {
//							GameUtil.sendMeTips("当前首饰强化等级已达上限。");
//							return;
//						}
//						//增加首饰强化次数、扣除金币和经验
//						if(GameObjectChar.getGameObjectChar().privilege == 0) {
//							chara.shoushiQianghuacishu++;
//							chara.cash -= 2000000;
//							chara.pot -= 5000000;
//							Map<String,Object> data = new HashMap<>();
//							data.put("exp", chara.pot);
//							data.put("cash", chara.cash);
//							GameObjectChar.send(new MSG_UPDATE_DYNAMIC(), new Vo_UPDATE_DYNAMIC(chara.id, data));
//						}
//						goods0.goodsBasics.accurate = (int) (goods0.goodsBasics.accurate * 1.05);
//						goods0.goodsBasics.def = (int) (goods0.goodsBasics.def * 1.05);
//						goods0.goodsBasics.dex = (int) (goods0.goodsBasics.dex * 1.05);
//						goods0.goodsBasics.mana = (int) (goods0.goodsBasics.mana * 1.05);
//						goods0.goodsBasics.parry = (int) (goods0.goodsBasics.parry * 1.05);
//						goods0.goodsBasics.wiz = (int) (goods0.goodsBasics.wiz * 1.05);
//
//						GoodsLanSe tmp = new GoodsLanSe();
//						copyLanse(tmp, goods0.goodsLanSe);
//
//						// 存储蓝色属性的非空值
//						HashMap<String, Integer> notNullKv = new HashMap<>();
//
//						Map<Object, Object> map = new HashMap<Object, Object>();
//						map = UtilObjMapshuxing.GoodsLanSe(goods0.goodsLanSe);
//						Iterator<Map.Entry<Object, Object>> it = map.entrySet().iterator();
//						while (it.hasNext()) {
//							Map.Entry<Object, Object> entry = it.next();
//							if (!entry.getKey().equals("groupType") && !entry.getKey().equals("groupNo")
//									&& !entry.getValue().equals(0)) {
//								// 将非空的属性放到map中保存
//								notNullKv.put((String) entry.getKey(), (Integer) entry.getValue());
//							}
//						}
//						int isSuccess = 0;
//						if (notNullKv.size() > 0) {
//							ZhuangbeiInfo zhuangb = GameData.that.baseZhuangbeiInfoService
//									.findOneByStr(goods0.goodsInfo.str);
//							int eq_attrib = zhuangb.getAttrib();
//							String enName = (String) (notNullKv.keySet().toArray()[new Random()
//									.nextInt(notNullKv.size())]);
//							Integer value = notNullKv.get(enName);
//							String chnName = ForgingEquipmentUtils.getEquipmentKeyByName(enName, false);
//							int maxValue = ForgingEquipmentUtils.getMaxValueByChineseName(chnName, eq_attrib, false);
//							if (value == maxValue) {
//								Vo_20481_0 vo_20481_0 = new Vo_20481_0();
//								vo_20481_0.msg = "你强化了已达到满属性的#R" + chnName + "#n！";
//								vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//								GameObjectChar.send(new M20481_0(), vo_20481_0);
//								return;
//							}
//							if ((int) (Math.round(value * 1.1)) > maxValue) {
//								value = maxValue;
//							} else {
//								value = (int) (Math.round(value * 1.1));
//							}
//							//如果大于旧的值
//							if(value > notNullKv.get(enName)) {
//								isSuccess = 1;
//							}
//							updateAttribute(goods0.goodsLanSe, enName, value);
//						}
//						//临时设置
//						goods0.goodsInfo.damage_sel_rate = (int) (System.currentTimeMillis()/1000L);
//						goods0.goodsInfo.strengthen_level+=1;
//						GameUtil.a65511(chara);
//						GameObjectChar.send(new M65525_0(), chara.backpack); // 这句是更新背包
//						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
//						vo_20481_0.msg = "你成功强化了首饰#R" + goods0.goodsInfo.str + "#n。";
//						vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//						GameObjectChar.send(new M20481_0(), vo_20481_0);
//						//首饰强化成功
//						GameObjectChar.send(new MSG_STRENGTHEN_JEWELRY_SUCC(), new Integer[] {goods0.goodsInfo.damage_sel_rate, isSuccess});
//						break;
//					} else {
//						Vo_20481_0 vo_20481_0 = new Vo_20481_0();
//						vo_20481_0.msg = "您本日已经进行过3次首饰强化了，请明日再来。";
//						vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
//						GameObjectChar.send(new M20481_0(), vo_20481_0);
//						break;
//					}
//				}
//			}
		}
	}

	public void updateAttribute(GoodsLanSe ls1, String name, int value) {
		switch (name) {
		case "groupNo":
			ls1.groupNo = value;
			break;
		case "groupType":
			ls1.groupType = value;
			break;
		case "phy_power":
			ls1.phy_power = value;
			break;
		case "mag_power":
			ls1.mag_power = value;
			break;
		case "speed":
			ls1.speed = value;
			break;
		case "life":
			ls1.life = value;
			break;
		case "skill_low_cost":
			ls1.skill_low_cost = value;
			break;
		case "mstunt_rate":
			ls1.mstunt_rate = value;
			break;
		case "all_polar":
			ls1.all_polar = value;
			break;
		case "all_resist_polar":
			ls1.all_resist_polar = value;
			break;
		case "wood":
			ls1.wood = value;
			break;
		case "water":
			ls1.water = value;
			break;
		case "fire":
			ls1.fire = value;
			break;
		case "earth":
			ls1.earth = value;
			break;
		case "resist_metal":
			ls1.resist_metal = value;
			break;
		case "damage_sel":
			ls1.damage_sel = value;
			break; // 物理必杀率
		case "stunt_rate":
			ls1.stunt_rate = value;
			break;
		case "double_hit_rate":
			ls1.double_hit_rate = value;
			break;
		case "release_forgotten":
			ls1.release_forgotten = value;
			break;
		case "ignore_all_resist_except":
			ls1.ignore_all_resist_except = value;
			break;
		case "stunt":
			ls1.stunt = value;
			break;
		case "def":
			ls1.def = value;
			break;
		case "dex":
			ls1.dex = value;
			break;
		case "wiz":
			ls1.wiz = value;
			break;
		case "family":
			ls1.family = value;
			break;
		case "life_recover":
			ls1.life_recover = value;
			break;
		case "all_skill":
			ls1.all_skill = value;
			break;
		case "portrait":
			ls1.portrait = value;
			break;
		case "resist_frozen":
			ls1.resist_frozen = value;
			break;
		case "resist_sleep":
			ls1.resist_sleep = value;
			break;
		case "resist_forgotten":
			ls1.resist_forgotten = value;
			break;
		case "resist_confusion":
			ls1.resist_confusion = value;
			break;
		case "longevity":
			ls1.longevity = value;
			break;
		case "resist_wood":
			ls1.resist_wood = value;
			break;
		case "resist_water":
			ls1.resist_water = value;
			break;
		case "resist_fire":
			ls1.resist_fire = value;
			break;
		case "resist_earth":
			ls1.resist_earth = value;
			break;
		case "exp_to_next_level":
			ls1.exp_to_next_level = value;
			break;
		case "all_resist_except":
			ls1.all_resist_except = value;
			break;
		case "accurate":
			ls1.accurate = value;
			break;
		case "mana":
			ls1.mana = value;
			break;
		case "parry":
			ls1.parry = value;
			break;
		case "ignore_resist_wood":
			ls1.ignore_resist_wood = value;
			break;
		case "ignore_resist_water":
			ls1.ignore_resist_water = value;
			break;
		case "ignore_resist_fire":
			ls1.ignore_resist_fire = value;
			break;
		case "ignore_resist_earth":
			ls1.ignore_resist_earth = value;
			break;
		case "ignore_resist_forgotten":
			ls1.ignore_resist_forgotten = value;
			break;
		case "ignore_resist_frozen":
			ls1.ignore_resist_frozen = value;
			break;
		case "ignore_resist_sleep":
			ls1.ignore_resist_sleep = value;
			break;
		case "ignore_resist_confusion":
			ls1.ignore_resist_confusion = value;
			break;
		case "super_excluse_metal":
			ls1.super_excluse_metal = value;
			break;
		case "ignore_resist_poison":
			ls1.ignore_resist_poison = value;
			break;
		}
	}

	// add tzhang 逐个添加蓝属性的值
	public void copyLanse(GoodsLanSe ls1, GoodsLanSe ls2) {
		ls1.groupNo = ls2.groupNo;
		ls1.groupType = ls2.groupNo;
		ls1.phy_power = ls2.phy_power;
		ls1.mag_power = ls2.mag_power;
		ls1.speed = ls2.speed;
		ls1.life = ls2.life;
		ls1.skill_low_cost = ls2.skill_low_cost;
		ls1.mstunt_rate = ls2.mstunt_rate;
		ls1.all_polar = ls2.all_polar;
		ls1.all_resist_polar = ls2.all_resist_polar;
		ls1.wood = ls2.wood;
		ls1.water = ls2.water;
		ls1.fire = ls2.fire;
		ls1.earth = ls2.earth;
		ls1.resist_metal = ls2.resist_metal;
		ls1.damage_sel = ls2.damage_sel; // 物理必杀率
		ls1.stunt_rate = ls2.stunt_rate;
		ls1.double_hit_rate = ls2.double_hit_rate;
		ls1.release_forgotten = ls2.release_forgotten;
		ls1.ignore_all_resist_except = ls2.ignore_all_resist_except;
		ls1.stunt = ls2.stunt;
		ls1.def = ls2.def;
		ls1.dex = ls2.dex;
		ls1.wiz = ls2.wiz;
		ls1.family = ls2.family;
		ls1.life_recover = ls2.life_recover;
		ls1.all_skill = ls2.all_skill;
		ls1.portrait = ls2.portrait;
		ls1.resist_frozen = ls2.resist_frozen;
		ls1.resist_sleep = ls2.resist_sleep;
		ls1.resist_forgotten = ls2.resist_forgotten;
		ls1.resist_confusion = ls2.resist_confusion;
		ls1.longevity = ls2.longevity;
		ls1.resist_wood = ls2.resist_wood;
		ls1.resist_water = ls2.resist_water;
		ls1.resist_fire = ls2.resist_fire;
		ls1.resist_earth = ls2.resist_earth;
		ls1.exp_to_next_level = ls2.exp_to_next_level;
		ls1.all_resist_except = ls2.all_resist_except;
		ls1.accurate = ls2.accurate;
		ls1.mana = ls2.mana;
		ls1.parry = ls2.parry;
		ls1.ignore_resist_wood = ls2.ignore_resist_wood;
		ls1.ignore_resist_water = ls2.ignore_resist_water;
		ls1.ignore_resist_fire = ls2.ignore_resist_fire;
		ls1.ignore_resist_earth = ls2.ignore_resist_earth;
		ls1.ignore_resist_forgotten = ls2.ignore_resist_forgotten;
		ls1.ignore_resist_frozen = ls2.ignore_resist_frozen;
		ls1.ignore_resist_sleep = ls2.ignore_resist_sleep;
		ls1.ignore_resist_confusion = ls2.ignore_resist_confusion;
		ls1.super_excluse_metal = ls2.super_excluse_metal;
		ls1.ignore_resist_poison = ls2.ignore_resist_poison;
	}
	// add:e

	@Override
	public int cmd() {
		return 32776;
	}

	public static Goods shuxing(Goods goods, Goods good) {
		return good;
	}
}