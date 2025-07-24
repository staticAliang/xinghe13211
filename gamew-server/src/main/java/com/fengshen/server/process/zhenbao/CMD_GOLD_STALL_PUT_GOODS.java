package com.fengshen.server.process.zhenbao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.SpringBeanUtils;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.domain.StallRecord;
import com.fengshen.db.service.zhenbao.GoldStallNineGoodsService;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.constant.StallStatus;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_MINE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.PetShuXing;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.fengshen.server.util.GameConfig;
import com.mysql.jdbc.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 珍宝请求上架
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_PUT_GOODS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		int inventoryPos = GameReadTool.readInt(buff);
		int price = GameReadTool.readInt(buff);
		GameReadTool.readShort(buff);
		//0道具、金钱 1宠物、
		int type = GameReadTool.readShort(buff);
		//指定买家gid
		String appointee = GameReadTool.readString(buff);
		//0 普通或指定、5 拍卖
		int sell_type = GameReadTool.readByte(buff);
		
		Integer zhenbaoStatus = GameConfig.config.getMarketConfig().getZhenbaoStatus();
		if(zhenbaoStatus == 1) {
			GameCommonUtil.dialogOk("gm关闭了珍宝。");
			return;
		}
		if(sell_type ==5) {
			return;
		}
		
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		//计算摊位费
		int stallMoney = price*100;
		if(stallMoney > 5000000) {
			stallMoney = 5000000;
		}else if(stallMoney < 500000 && type != 3) {
			stallMoney =  500000;
		}
		
		String goodsId = GameCommonUtil.UUID();
		//珍宝数据信息
		GoldStallNineGoods gold = new GoldStallNineGoods();
		gold.setGid(chara.uuid);
		gold.setAddTime(new Date());
		gold.setMaster(chara.name);
		//指定人购买
		if(!StringUtils.isNullOrEmpty(appointee)) {
			GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectCharByUUid(appointee);
			String name = "";
			if(gameObjectCharByUUid == null) {
				//不在线
				Characters c = GameData.that.baseCharactersService.findOneByGidSelectProperties(appointee,"id","name");
				name = c.getName();
			}else {
				name = gameObjectCharByUUid.chara.name;
			}
			gold.setAppointeeName(name+";"+appointee);
			//设置一口价
			int buyoutPrice = getGoldYkj(price)+price;
			gold.setBuyoutPrice(buyoutPrice);
			sell_type = 1;
		}
		gold.setGoodsId(goodsId);
		gold.setPrice(price);
		gold.setInitPrice(price);
		gold.setFlagNum(0);
		StringBuilder key = new StringBuilder();
		Petbeibao pet = null;
		Goods goods = null;
		if (type == 1) {
			for (int i = 0; i < chara.backpack.size(); ++i) {
				if (chara.backpack.get(i).pos == inventoryPos) {
					goods = chara.backpack.get(i);
					break;
				}
			}
			if(goods == null) {
				GameUtil.sendMeTips("该商品不存在。");
				return;
			}
			gold.setName(goods.goodsInfo.str);
			int attrib = goods.goodsInfo.attrib;
			if(goods.goodsInfo.amount == 1){
				//全部武器
				key.append("装备_全部武器_").append(attrib);
			}else if(goods.goodsInfo.amount == 2 || goods.goodsInfo.amount == 3 || goods.goodsInfo.amount == 10){
				//全部防具
				key.append("装备_全部防具_").append(attrib);
			}else if(goods.goodsInfo.amount == 4){
				//项链
				key.append("高级首饰_项链_").append(attrib);
			}else if(goods.goodsInfo.amount == 5){
				//玉佩
				key.append("高级首饰_玉佩_").append(attrib);
			}else if(goods.goodsInfo.amount == 6){
				//手镯
				key.append("高级首饰_手镯_").append(attrib);
			}else if(goods.goodsInfo.amount == 8) {
				//魂器
			}else if(goods.goodsInfo.amount == 9) {
				//法宝
				String fabao = "";
				if(goods.goodsInfo.type == 1409) {
					fabao = "定海珠";
				}else if(goods.goodsInfo.type == 1412) {
					fabao = "阴阳镜";
				}else if(goods.goodsInfo.type == 1410) {
					fabao = "混元金斗";
				}else if(goods.goodsInfo.type == 1411) {
					fabao = "金蛟剪";
				}else if(goods.goodsInfo.type == 1413) {
					fabao = "卸甲金葫";
				}else if(goods.goodsInfo.type == 1414) {
					fabao = "九龙神火罩";
				}else if(goods.goodsInfo.type == 1415) {
					fabao = "番天印";
				}
				key.append("法宝_").append(fabao);
			}
			type = 0;
			//设置拓展信息
			Map<String,Object> extraJson = new HashMap<>();
			//支付定金.
			extraJson.put("deposit_state", 0);
			gold.setExtra(JSONObject.toJSONString(extraJson));
			gold.setGoods(JSONObject.toJSONString(goods));
		}else if(type == 2) {
			//宠物
			for (int i = 0; i < chara.pets.size(); i++) {
				if (chara.pets.get(i).id == inventoryPos) {
					pet = chara.pets.get(i);
					break;
				}
			}
			if(pet == null) {
				GameUtil.sendMeTips("该宠物不存在。");
				return;
			}
			PetShuXing petShuXing = pet.petShuXing.get(0);
			//设置名称
			gold.setName(petShuXing.suit_polar);
			int petType = petShuXing.penetrate;
			key.append("宠物_");
			//宝宝
			if(petType == 2) {
				//判断是坐骑还是精怪
				if(petShuXing.suit_light_effect == 1 || petShuXing.suit_light_effect == 2) {
					//精怪、御灵
					key.append("精怪/御灵");
				}else {
					key.append("普通");
				}
			}else if(petType == 3) {
				//变异
				key.append("变异");
			}else if(petType == 4) {
				//神兽
				key.append("神兽");
			}else if(petType == 6 || petType == 7 || petType == 8) {
				//鬼宠--鬼卒、鬼将、鬼仙
				key.append("鬼宠");
			}
			//设置拓展信息
			Map<String,Object> extraJson = new HashMap<>();
			//宠物类型
			extraJson.put("rank", petShuXing.penetrate);
			//精怪御灵
			extraJson.put("mount_type", petShuXing.suit_light_effect);
			extraJson.put("enchant", petShuXing.enchant_nimbus);
			extraJson.put("rebuild_level", petShuXing.skill-15<0?0:petShuXing.skill-15);
			extraJson.put("eclosion",  petShuXing.eclosion_nimbus);
			//支付定金.
			extraJson.put("deposit_state", 0);
			gold.setExtra(JSONObject.toJSONString(extraJson));
			gold.setGoods(JSONObject.toJSONString(pet));
			gold.setLevel(pet.petShuXing.get(0).skill);
		}else if(type == 3) {
			//金钱
//			key.append("金钱");
//			gold.setName("金钱");
//			goods = new Goods();
//			goods.goodsInfo = new GoodsInfo();
//			goods.goodsInfo.type = 5;
//			goods.goodsInfo.str = "金钱";
//			goods.goodsInfo.recognize_recognized = 0;
//			goods.goodsInfo.auto_fight = UUID.randomUUID().toString();
//			goods.goodsInfo.total_score = 10000000;
//			goods.goodsInfo.rebuild_level = 
//			goods.goodsInfo.value = 10000000;
//			goods.goodsInfo.degree_32 = 0; // 【重要】道具也是已鉴定
//			goods.goodsInfo.owner_id = chara.id;
//			goods.goodsInfo.damage_sel_rate = 400976;
//			Map<String,Object> extraJson = new HashMap<>();
//			//支付定金.
//			extraJson.put("deposit_state", 0);
//			gold.setExtra(JSONObject.toJSONString(extraJson));
//			gold.setGoods(JSONObject.toJSONString(goods));
			return;
		}
		
		//指定
		gold.setSellType(sell_type);
		gold.setStallItemType(type);
		gold.setAlias(key.toString());
		gold.setStartTime((int) (System.currentTimeMillis()/1000L));
		if(GameConfig.config.getMarketConfig().getZhenbaoPublicTimes() > 0) {
			gold.setStatus(1);
			int publicTimes = GameConfig.config.getMarketConfig().getZhenbaoPublicTimes()*60;//单位是分钟
			gold.setEndTime((int) (System.currentTimeMillis()/1000L)+publicTimes);
			//创建定时器
			GameData.that.redisUtils.set(DefinedConst.GOLD_STALL_PREFIX+";"+goodsId+";"+gold.getStatus(), "", publicTimes);
		}else {
			//如果系统开启了审核
			ConfigInfo orderStatus = GameData.that.configInfoService.getOneByKeyName("zhenbao_cost_order_status");
			if(orderStatus != null && "开启".equals(orderStatus.getData())) {
				gold.setStatus(StallStatus.getValue("审核中"));
				//审核记录-主人
				StallRecord builderGoldStallRecord = GameCommonUtil.builderGoldStallRecord(chara, null, chara, gold, 
						gold.getPrice());
				GameData.that.stallRecordService.insertSelective(builderGoldStallRecord);
				String msg = "亲爱的#Y" + chara.name + "#n玩家你在珍宝上架的#R" + gold.getName()
				+ "#n商品待系统审核通过后即可上架";
				GameUtil.sendMeTips(msg);
			}else {
				//没有公示期直接上架
				gold.setStatus(StallStatus.getValue("出售中"));
				//设置下架时间
				GameData.that.redisUtils.set(DefinedConst.GOLD_STALL_PREFIX+";"+goodsId+";"+2, 
						"", GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes()*60);
				gold.setEndTime((int) (System.currentTimeMillis()/1000L)+GameConfig.config.getMarketConfig().getZhenbaoDownGoodTimes()*60);
			}
		}
		GoldStallNineGoodsService gs = SpringBeanUtils.getBean(GoldStallNineGoodsService.class);
		gs.insertSelective(gold);
		chara.cash -= stallMoney;
		final ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0);
		GameUtil.sendMeTips("摆摊成功花费了摊位费" + GameCommonUtil.getMoneyDes(stallMoney) + "#n文钱#n");
		//删除上架的商品
		if(goods != null) {
			GameUtil.removemunber(chara, goods, 1);
		}else if(pet != null) {
			chara.pets.remove(pet);
			final Vo_12269_0 vo_12269_0 = new Vo_12269_0();
			vo_12269_0.id = pet.id;
			vo_12269_0.owner_id = 0;
			GameObjectChar.send(new M12269_0(), vo_12269_0);
			//删除宠物
			GameData.that.charaPetService.deleteByPrimaryKey(pet.id);
		}
		GameObjectChar.send(new MSG_GOLD_STALL_MINE(), GameCommonUtil.refreshMarketGold(chara));
		log.info("珍宝请求上架:-----一口价={},type={},sell_type={},appointee={},搜索key={}",type,sell_type,appointee,key);
	}
	
	/**
	 * 一口价计算
	 * @param price
	 * @return
	 */
	private int getGoldYkj(int price) {
		if(price <= 10000) {
			return (int) Math.floor(price*1);
		}else if(price <=25000) {
			return (int) (getGoldYkj(10000) + Math.floor((price-10000)*0.7));
		}else if(price <=250000) {
			return (int) (getGoldYkj(25000) + Math.floor((price-25000)*0.5));
		}else if(price <=500000) {
			return (int) (getGoldYkj(250000) + Math.floor((price- 250000)*0.25));
		}else if(price <=100000000) {
			return (int) (getGoldYkj(500000) + Math.floor((price- 500000)*0.1));
		}
		return getGoldYkj(price)+price;
	}

	@Override
	public int cmd() {
		return 0x8104;
	}

}
