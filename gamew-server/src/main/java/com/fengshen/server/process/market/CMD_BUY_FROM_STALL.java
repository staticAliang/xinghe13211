package com.fengshen.server.process.market;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.ListVo_65527_0;
import com.fengshen.server.data.vo.Vo_12023_0;
import com.fengshen.server.data.vo.Vo_12269_0;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_33049_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.Vo_49183;
import com.fengshen.server.data.vo.Vo_49183_0;
import com.fengshen.server.data.write.M12023_0;
import com.fengshen.server.data.write.M12269_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M33049_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M49183_0;
import com.fengshen.server.data.write.M65527_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.Petbeibao;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

// 这里是集市的类
@Service
@Slf4j
public class CMD_BUY_FROM_STALL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String id = GameReadTool.readString(buff);
		String key = GameReadTool.readString(buff);
		String pageStr = GameReadTool.readString(buff);
		int price = GameReadTool.readInt(buff);
		int type = GameReadTool.readByte(buff);
		int amount = GameReadTool.readShort(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		SaleGood saleGood = GameData.that.saleGoodService.findOneByGoodsId(id);
		if (saleGood == null) {
			GameUtil.sendMeTips("该商品不存在或被购买");
			return;
		}
		if (saleGood != null && saleGood.getGid().equals(chara.uuid)) {
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "道友,这是你自己出售的商品哦。";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			return;
		}
		// 是否是宠物，1不是宠物（其他）
		int pos = GameUtil.packPoint(chara);
		if (pos == -1) {
			return;
		}
		if (saleGood.getType() == 1) {
			String goods = saleGood.getGoods();
			Goods goods2 = JSONObject.parseObject(goods, Goods.class);
			goods2.pos = pos;
			goods2.goodsInfo.owner_id = 1;
			GameUtil.addwupin(goods2, chara);
			Vo_40964_0 vo_40964_9 = new Vo_40964_0();
			vo_40964_9.type = 1;
			vo_40964_9.name = saleGood.getName();
			vo_40964_9.param = "-1";
			vo_40964_9.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_9);
		} else {
			String goods = saleGood.getGoods();
			Petbeibao petbeibao = JSONObject.parseObject(goods, Petbeibao.class);
			petbeibao.petShuXing.get(0).shape = 0;
			GameCommonUtil.addCharaTrail(chara, "宠物", petbeibao.petShuXing.get(0).str, "集市");
			// 添加宠物
			CharaPet charaPet = new CharaPet();
			GameCommonUtil.addCharaPet(charaPet, chara, petbeibao);
			petbeibao.id = charaPet.getId();
			//宠物天书
			for(Vo_12023_0 book:petbeibao.tianshu) {
				book.id = petbeibao.id;
				book.owner_id = chara.id;
			}
			Vo_12269_0 vo_12269_0 = new Vo_12269_0();
			vo_12269_0.id = petbeibao.id;
			vo_12269_0.owner_id = chara.id;
			GameObjectChar.send(new M12269_0(), vo_12269_0);
			Vo_40964_0 vo_40964_10 = new Vo_40964_0();
			vo_40964_10.type = 2;
			vo_40964_10.name = "";
			vo_40964_10.param = String.valueOf(petbeibao.petShuXing.get(0).type);
			vo_40964_10.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_10);
			Vo_20481_0 vo_20481_2 = new Vo_20481_0();
			vo_20481_2.msg = "你成功将#R" + saleGood.getName() + "#n购买了";
			vo_20481_2.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_2);
			List<Petbeibao> list2 = new ArrayList<>();
			list2.add(petbeibao);
			petbeibao.id = petbeibao.id;
			GameObjectChar.send(new MSG_UPDATE_PETS(), list2);
			//刷新天书
			GameObjectChar.send(new M12023_0(), petbeibao.tianshu);
			boolean isfagong = petbeibao.petShuXing.get(0).rank > petbeibao.petShuXing.get(0).pet_mag_shape;
			GameUtil.dujineng(1, petbeibao.petShuXing.get(0).metal, petbeibao.petShuXing.get(0).skill, isfagong,
					petbeibao.id, chara, petbeibao);
			chara.pets.add(petbeibao);
			GameData.that.saleGoodService.deleteById((int) saleGood.getId());
		}
		Vo_33049_0 vo_33049_0 = new Vo_33049_0();
		vo_33049_0.goods_gid = id;
		vo_33049_0.type = 0;
		vo_33049_0.result = 1;
		vo_33049_0.tips = "";
		GameObjectChar.send(new M33049_0(), vo_33049_0);
		Vo_20481_0 vo_20481_3 = new Vo_20481_0();
		vo_20481_3.msg = "购买了#R" + saleGood.getName() + "#n。";
		vo_20481_3.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20481_0(), vo_20481_3);
		chara.cash -= price;
		ListVo_65527_0 listVo_65527_0 = GameUtil.a65527(chara);
		GameObjectChar.send(new M65527_0(), listVo_65527_0);
		GameData.that.saleGoodService.deleteById((int) saleGood.getId());
		String[] split = pageStr.split("\\;");
		int pos1 = Integer.parseInt(split[0]);
		List<SaleGood> saleGoodList = (List<SaleGood>) GameData.that.saleGoodService.findByStr(key);
		Vo_49183_0 vo_49183_0 = new Vo_49183_0();
		vo_49183_0.totalPage = saleGoodList.size() / 3;
		vo_49183_0.cur_page = pos1;
		int weizhi = (pos1 - 1) * 8;
		int size = saleGoodList.size() - (pos1 - 1) * 8;
		if (size > 8) {
			size = 8;
		}
		for (int i = 0; i < size; ++i) {
			Vo_49183 vo_49183 = new Vo_49183();
			vo_49183.name = saleGoodList.get(i + weizhi).getName();
			vo_49183.is_my_goods = 0;
			vo_49183.id = saleGoodList.get(i + weizhi).getGoodsId();
			vo_49183.price = saleGoodList.get(i + weizhi).getPrice();
			vo_49183.status = 2;
			vo_49183.startTime = saleGoodList.get(i + weizhi).getStartTime();
			vo_49183.endTime = saleGoodList.get(i + weizhi).getEndTime();
			vo_49183.level = saleGoodList.get(i + weizhi).getLevel();
			vo_49183.unidentified = ((saleGoodList.get(i + weizhi).getLevel() > 0) ? 1 : 0);
			if (saleGoodList.get(i + weizhi).getType() == 2) {
				vo_49183.unidentified = 0;
			}
			vo_49183.amount = 1;
			vo_49183.req_level = saleGoodList.get(i + weizhi).getReqLevel();
			vo_49183.extra = "\"{\"rank\":2,\"enchant\":0,\"mount_type\":0,\"rebuild_level\":1,\"eclosion\":0}\"";
			vo_49183.item_polar = 0;
			vo_49183_0.vo_49183s.add(vo_49183);
		}
		vo_49183_0.path_str = key;
		vo_49183_0.select_gid = "";
		vo_49183_0.sell_stage = 2;
		vo_49183_0.sort_key = "price";
		vo_49183_0.is_descending = 0;
		GameObjectChar.send(new M49183_0(), vo_49183_0);
		Characters characters = GameData.that.baseCharactersService.findOneByGid2(saleGood.getGid());
		Chara chara2 = JSONObject.parseObject(characters.getData(), Chara.class);
		GameObjectChar session = GameObjectCharMng.getGameObjectChar(chara2.id);
		if (session != null) {
			Chara chara3 = session.chara;
			long maxPrice = chara3.jishou_coin+price;
			if(maxPrice>=2000000000) {
				chara3.jishou_coin = 2000000000;
			}else {
				chara3.jishou_coin += price;
			}
		} else {
			long maxPrice = chara2.jishou_coin+price;
			if(maxPrice>=2000000000) {
				chara2.jishou_coin = 2000000000;
			}else {
				chara2.jishou_coin += price;
			}
			characters.setData(JSONObject.toJSONString((Object) chara2));
			GameData.that.characterService.updateById(characters);
		}
		log.info("集市购买，type={},amount={}",type,amount);
	}

	@Override
	public int cmd() {
		return 12490;
	}
}