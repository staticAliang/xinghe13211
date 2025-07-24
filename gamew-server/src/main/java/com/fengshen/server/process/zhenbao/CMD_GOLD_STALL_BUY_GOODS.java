package com.fengshen.server.process.zhenbao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.zhenbao.Vo_GOLD_STALL_BUY_RESUL;
import com.fengshen.server.data.write.M12023_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_BUY_RESULT;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 金元宝交易购买商品
 * 
 * 
 *
 */
@Service
@Slf4j
public class CMD_GOLD_STALL_BUY_GOODS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String id = GameReadTool.readString(buff);
		String key = GameReadTool.readString(buff);
		String pageStr = GameReadTool.readString(buff);
		int price = GameReadTool.readInt(buff);
		int type = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		// 后台也要校验一次元宝
		// 查询出配置
		ConfigInfo configInfo = GameData.that.configInfoService.getOneByKeyName("zhenbao_cost_type");
		// 默认为金元宝
		if (configInfo == null) {
			if (chara.goldCoin < price) {
				GameUtil.sendMeTips("金元宝不足。");
				return;
			}
		} else {
			if ("积分".equals(configInfo.getData())) {
				if (chara.chargeScore < price) {
					GameUtil.sendMeTips("积分不足。");
					return;
				}
			} else if ("银元宝".equals(configInfo.getData())) {
				if (chara.silverCoin < price) {
					GameUtil.sendMeTips("银元宝不足。");
					return;
				}
			} else if (chara.goldCoin < price) {
				GameUtil.sendMeTips("金元宝不足。");
				return;
			}
		}

		// 先核对商品是否存在或者是过期了.
		String value = GameData.that.redisUtils
				.get(DefinedConst.GOLD_STALL_PREFIX + ";" + id + ";" + pageStr.split(";")[1]);
		if (value == null) {
			GameUtil.sendMeTips("对不起，商品已失效。");
			return;
		}
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("goodsId", id);
		GoldStallNineGoods shopGoods = GameData.that.zhenbao.selectOneByExample(example);
		if (shopGoods.getAppointeeName() == null && shopGoods.getAppointeeName().equals("")) {
			if (price != shopGoods.getPrice()) {
				GameCommonUtil.dialogOk("商品数据发生变化，请刷新。");
				return;
			}
		}
		// 不为空并且正常出售
		if (shopGoods != null && shopGoods.getStatus() == 2) {
			//定金
			int earnestMoney = (int) (shopGoods.getPrice() * 0.1);
			// 把物品放到购买者的背包
			if (shopGoods.getStallItemType() == 2) {
				// 宠物
				Petbeibao pet = JSONObject.parseObject(shopGoods.getGoods(), Petbeibao.class);
				Petbeibao.isAddPet(chara, pet.petShuXing.get(0).penetrate);
				pet.petShuXing.get(0).str = pet.petShuXing.get(0).suit_polar;
				// 储存到数据库
				CharaPet charaPet = new CharaPet();
				charaPet.setAddTime(new Date());
				charaPet.setOwnerName(chara.name);
				charaPet.setPetName(pet.petShuXing.get(0).str);
				charaPet.setUuid(chara.uuid);
				charaPet.setCid(chara.id);
				charaPet.setPet(shopGoods.getGoods());
				GameData.that.charaPetService.insertSelective(charaPet);
				pet.id = charaPet.getId();
				pet.no = GameUtil.getNo(chara);
				//宠物天书
				for(Vo_12023_0 book:pet.tianshu) {
					book.id = pet.id;
					book.owner_id = chara.id;
				}
				// 亲密置为0
				pet.petShuXing.get(0).shape = 0;
				chara.pets.add(pet);
				GameCommonUtil.addCharaTrail(chara, "宠物", pet.petShuXing.get(0).str, "珍宝");
				// 播放动画
				Vo_40964_0 vo_40964_21 = new Vo_40964_0();
				vo_40964_21.type = 2;
				vo_40964_21.name = "";
				vo_40964_21.param = String.valueOf(pet.petShuXing.get(0).type);
				vo_40964_21.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_21);
				// 发送数据
				List<Petbeibao> pets = new ArrayList<>();
				pets.add(pet);
				GameObjectChar.send(new MSG_UPDATE_PETS(), pets);
				GameObjectChar.send(new M12023_0(), pet.tianshu);
				GameCommonUtil.goldStallShopGoods(shopGoods, configInfo, chara, price, earnestMoney);
			} else if (shopGoods.getStallItemType() == 0) {
				// 播放动画
				Vo_40964_0 vo_40964_21 = new Vo_40964_0();
				vo_40964_21.type = 1;
				vo_40964_21.name = shopGoods.getName();
				vo_40964_21.param = "";
				vo_40964_21.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_21);
				int pos = GameUtil.packPoint(chara);
				Goods goodsInfo = JSONObject.parseObject(shopGoods.getGoods(), Goods.class);
				if (pos != -1) {
					// 重新设置信息.
					goodsInfo.goodsInfo.auto_fight = GameCommonUtil.UUID();
					goodsInfo.pos = pos;
					GameUtil.addwupin(goodsInfo, chara);
				}
				GameCommonUtil.goldStallShopGoods(shopGoods, configInfo, chara, price, earnestMoney);
			}
			//刷新
			GameCommonUtil.openStallGold(chara, key, pageStr);
			//结果
			String costType = "金元宝";
			if (configInfo != null && "积分".equals(configInfo.getData())) {
				costType = "积分";
			} else if (configInfo != null && "银元宝".equals(configInfo.getData())) {
				costType = "银元宝";
			}
			Vo_GOLD_STALL_BUY_RESUL buyResul = new Vo_GOLD_STALL_BUY_RESUL();
			buyResul.setGoods_gid(id);
			buyResul.setResult(1);
			buyResul.setTips("你在珍宝花费了#R" + price + "#n" + costType + "购买了#Y" + shopGoods.getName());
			buyResul.setType(type);
			GameObjectChar.send(new MSG_GOLD_STALL_BUY_RESULT(), buyResul);
		} else {
			GameUtil.sendMeTips("对不起，商品已失效。");
		}
		log.info("金元宝购买商品,价格=={}", price);
	}

	

	@Override
	public int cmd() {
		return 0x810A;
	}

}
