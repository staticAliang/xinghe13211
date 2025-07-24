package com.fengshen.server.process.market;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.SaleClassifyGood;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_49179_0;
import com.fengshen.server.data.vo.Vo_8165_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.M8165_0;
import com.fengshen.server.data.write.market.M49179_0;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 集市摆摊
 * 
 * 
 * 
 *         商品类型_type 1装备道具 2宠物
 */
@Service
@Slf4j
public class CMD_SET_STALL_GOODS implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int inventoryPos = GameReadTool.readInt(buff);
		int price = GameReadTool.readInt(buff);
		int pos = GameReadTool.readShort(buff);
		// 类型
		int type = GameReadTool.readShort(buff);
		// 数量
		int amount = GameReadTool.readShort(buff);
		log.info("集市摆摊, pos={},type={},amount={}",pos,type,amount);
		Chara chara = GameObjectChar.getGameObjectChar().chara;

		if (GameConfig.config.getMarketConfig().getStatus() != 0) {
			GameCommonUtil.dialogOk("集市已关闭");
			return;
		}
		String str = null;
		// 12小时到期,秒为单位
		int downGoodTimes = GameConfig.config.getMarketConfig().getDownGoodTimes()*60*60;
		int coin = price / 100;
		//如果摊位费小于0那就
		if (coin <= 0) {
			coin = 100000;
		}
		if (price < 0) {
			GameUtil.sendMeTips("价格有误");
			return;
		}
		if (price > 2000000000) {
			return;
		}
		if (type == 1) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (chara.backpack.get(i).pos == inventoryPos) {
					Goods goods = chara.backpack.get(i);
					log.info("摆摊对象名字：{}", goods.goodsInfo.str);
					SaleClassifyGood saleClassifyGood = null;
					if (goods.goodsInfo.str.contains("超级黑水晶·")) {
						Example example = new Example(SaleClassifyGood.class);
						example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", goods.goodsInfo.str);
						List<SaleClassifyGood> classifyGoodList = GameData.that.baseSaleClassifyGoodService
								.selectByExample(example);
						for (int j = 0; j < classifyGoodList.size(); ++j) {
							// 等级
							String[] split = classifyGoodList.get(j).getCompose().split("_");
							// 武器类型名称
							String zbTypeStr = split[1];
							// 等级
							int attrib = Integer.valueOf(split[2]);
							// 武器类型数字
							int zbType = 1;
							if (zbTypeStr.indexOf("鞋") != -1) {
								zbType = 10;
							} else if (zbTypeStr.indexOf("衣") != -1) {
								zbType = 3;
							} else if (zbTypeStr.indexOf("帽") != -1) {
								zbType = 2;
							}
							if (attrib == goods.goodsInfo.attrib && zbType == goods.goodsInfo.add_pet_exp) {
								saleClassifyGood = classifyGoodList.get(j);
								break;
							}
						} // 如果是装备的话
					} else if (goods.goodsInfo.amount == 1 || goods.goodsInfo.amount == 2 || goods.goodsInfo.amount == 3
							|| goods.goodsInfo.amount == 10) {
						Example example = new Example(SaleClassifyGood.class);
						example.createCriteria().andEqualTo("deleted", false).andEqualTo("name", goods.goodsInfo.str);
						List<SaleClassifyGood> classifyGoodList = GameData.that.baseSaleClassifyGoodService
								.selectByExample(example);
						for (SaleClassifyGood sc : classifyGoodList) {
							// 判断是否未鉴定
							if (goods.goodsInfo.degree_32 == 1) {
								if (sc.getCompose().indexOf("未鉴定") != -1) {
									saleClassifyGood = sc;
									break;
								}
							} else {
								saleClassifyGood = sc;
								break;
							}
						}
					} else {
						saleClassifyGood = GameData.that.baseSaleClassifyGoodService.findOneByStr(goods.goodsInfo.str);
					}
					if (saleClassifyGood == null) {
						return;
					}
					str = saleClassifyGood.getCompose();
					SaleGood saleGood = new SaleGood();
					if (goods.goodsInfo.degree_32 == 1) {
						if (coin < 1000) {
							coin = 1000;
						}
						if (goods.goodsInfo.degree_32 == 1) {
							str = "未鉴定" + saleClassifyGood.getCompose();
							saleGood.setUnidentified(1);
						}
					}
					// 商品唯一id
					String goodsId = UUID.randomUUID().toString().replace("-", "");
					int time = (int) (System.currentTimeMillis() / 1000L);
					saleGood.setStartTime(time);
					int endTime = setStatus(saleClassifyGood, downGoodTimes, goodsId, saleGood);
					saleGood.setEndTime(endTime);
					saleGood.setGoodsId(goodsId);
					saleGood.setName(goods.goodsInfo.str);
					saleGood.setPrice(price);
					saleGood.setReqLevel(0);
					saleGood.setLevel(goods.goodsInfo.attrib);
					saleGood.setGid(chara.uuid);
					saleGood.setAlias(str);
					saleGood.setType(1);
					saleGood.setGoods(JSONObject.toJSONString(goods));
					saleGood.setIcon(goods.goodsInfo.type);
					saleGood.setAddTime(new Date());
					saleGood.setItemPolar(goods.goodsInfo.shuadao_ziqihongmeng);
					saleGood.setSgId(saleClassifyGood.getId());
					GameData.that.saleGoodService.insertSelective(saleGood);

					chara.cash -= coin;
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);
					GameUtil.removemunber(chara, chara.backpack.get(i), 1);

					Vo_20480_0 vo_20480_0 = new Vo_20480_0();
					vo_20480_0.msg = "摆摊成功";
					vo_20480_0.time = (int) System.currentTimeMillis() / 1000;
					GameObjectChar.send(new M20480_0(), vo_20480_0);

					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = "花费了摊位费" + coin + "#n文钱#n";
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);
					break;
				}
			}
		}
		if (type == 2) {
			if (chara.cash < coin) {
				return;
			}
			chara.cash -= coin;
			for (int k = 0; k < chara.pets.size(); ++k) {
				if (chara.pets.get(k).id == inventoryPos) {
					Petbeibao pet = chara.pets.get(k);
					SaleClassifyGood saleClassifyGood = GameData.that.baseSaleClassifyGoodService
							.findOneByStr(pet.petShuXing.get(0).suit_polar);
					if (saleClassifyGood == null) {
						GameUtil.sendMeTips("未找到该配置信息，请联系GM!");
						return;
					}
					str = saleClassifyGood.getCompose();
					SaleGood saleGood = new SaleGood();
					// 商品唯一id
					String goodsId = UUID.randomUUID().toString().replace("-", "");
					int time = (int) (System.currentTimeMillis() / 1000L);
					saleGood.setStartTime(time);
					int endTime = setStatus(saleClassifyGood, downGoodTimes, goodsId, saleGood);
					saleGood.setEndTime(endTime);
					saleGood.setGoodsId(goodsId);
					saleGood.setName(saleClassifyGood.getName());
					saleGood.setPrice(price);
					// 等级要求
					saleGood.setReqLevel(pet.petShuXing.get(0).skill - 15 < 1 ? 1 : pet.petShuXing.get(0).skill - 15);
					saleGood.setGid(chara.uuid);
					saleGood.setAlias(str);
					saleGood.setGoods(JSONObject.toJSONString(pet));
					saleGood.setType(2);
					saleGood.setIcon(pet.petShuXing.get(0).type);
					saleGood.setLevel(pet.petShuXing.get(0).skill);
					// 设置拓展信息
					Map<String, Object> extraJson = new HashMap<>();
					// 宠物类型
					extraJson.put("rank", pet.petShuXing.get(0).penetrate);
					// 精怪御灵
					extraJson.put("mount_type", pet.petShuXing.get(0).suit_light_effect);
					extraJson.put("enchant", pet.petShuXing.get(0).enchant_nimbus);
					extraJson.put("rebuild_level", pet.petShuXing.get(0).skill - 15);
					extraJson.put("eclosion", pet.petShuXing.get(0).eclosion_nimbus);
					saleGood.setExtra(JSONObject.toJSONString(extraJson));
					saleGood.setAddTime(new Date());
					saleGood.setSgId(saleClassifyGood.getId());
					GameData.that.saleGoodService.insertSelective(saleGood);

					Vo_12269_0 vo_12269_0 = new Vo_12269_0();
					vo_12269_0.id = pet.id;
					vo_12269_0.owner_id = 0;
					GameObjectChar.send(new M12269_0(), vo_12269_0);
					str = saleClassifyGood.getCompose();
					Vo_20480_0 vo_20480_0 = new Vo_20480_0();
					vo_20480_0.msg = "摆摊成功";
					vo_20480_0.time = (int) System.currentTimeMillis() / 1000;
					GameObjectChar.send(new M20480_0(), vo_20480_0);

					chara.cash -= coin;
					ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
					GameObjectChar.send(new M65527_0(), listVo_65527_0);

					Vo_8165_0 vo_8165_0 = new Vo_8165_0();
					vo_8165_0.msg = "花费了摊位费" + coin + "#n文钱#n";
					vo_8165_0.active = 0;
					GameObjectChar.send(new M8165_0(), vo_8165_0);
					chara.pets.remove(pet);
					// 删除宠物
					GameData.that.charaPetService.deleteByPrimaryKey(pet.id);
					break;
				}
			}
		}
		List<SaleGood> saleGoodList = (List<SaleGood>) GameData.that.saleGoodService.findByOwnerUuid(chara.uuid);
		Vo_49179_0 vo_49179_0 = GameUtil.a49179(saleGoodList, chara);
		GameObjectChar.send(new M49179_0(), vo_49179_0);
	}

	private int setStatus(SaleClassifyGood saleClassifyGood, int downGoodTimes, String goodsId, SaleGood saleGood) {
		int endTime = 0;
		// 如果需要公示
		if (saleClassifyGood.getPublicityTime() != null && saleClassifyGood.getPublicityTime() > 0) {
			log.info("需要公示-------商品名称={},商品id={}", saleGood.getName(), goodsId);
			saleGood.setStatus(1);
			// 公示需要的秒数
			int publicEndTimes = saleClassifyGood.getPublicityTime() * 60;
			endTime = (int) (System.currentTimeMillis() / 1000L) + publicEndTimes;
			//公示结束后调用此方法,把物品修改为出售状态.
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 设置为出售状态,并且初始化开始时间和到期时间
					SaleGood up = new SaleGood();
					up.setStatus(2);
					up.setStartTime((int) (System.currentTimeMillis() / 1000L));
					up.setEndTime((int) (System.currentTimeMillis() / 1000L) + downGoodTimes);
					Example example = new Example(SaleGood.class);
					example.createCriteria().andEqualTo("goodsId", goodsId);
					GameData.that.saleGoodService.updateByExampleSelective(up, example);
					log.info("公示到期更新为出售状态-------商品名称={},商品id={}", saleGood.getName(), goodsId);
					// 货物只保存12小时
					new Timer().schedule(new TimerTask() {
						@Override
						public void run() {
							// 货物到期设置为到期
							SaleGood up = new SaleGood();
							up.setStatus(3);
							Example example = new Example(SaleGood.class);
							example.createCriteria().andEqualTo("goodsId", goodsId);
							GameData.that.saleGoodService.updateByExampleSelective(up, example);
							log.info("货物到期，下架-------商品名称={},商品id={}", saleGood.getName(), goodsId);
						}
					}, downGoodTimes * 1000);
				}
			}, publicEndTimes * 1000);
		} else {
			// 无需公示直接上架
			saleGood.setStatus(2);
			endTime = (int) (System.currentTimeMillis() / 1000L) + downGoodTimes;
			log.info("无需公示-------商品名称={},商品id={}", saleGood.getName(), goodsId);
			// 货物只保存12小时
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					// 货物时间到期,设置状态为到期
					SaleGood up = new SaleGood();
					up.setStatus(3);
					Example example = new Example(SaleGood.class);
					example.createCriteria().andEqualTo("goodsId", goodsId);
					GameData.that.saleGoodService.updateByExampleSelective(up, example);
					log.info("货物到期下架-------商品名称={},商品id={}", saleGood.getName(), goodsId);
				}
			}, downGoodTimes * 1000);
		}
		return endTime;
	}

	@Override
	public int cmd() {
		return 0x40c6;
	}
}