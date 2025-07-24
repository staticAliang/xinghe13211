package com.fengshen.server.process.fashion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FasionCustomInfo;
import com.fengshen.db.domain.PackModification;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.Vo_20481_0;
import com.fengshen.server.data.vo.Vo_41505_0;
import com.fengshen.server.data.vo.Vo_61677_0;
import com.fengshen.server.data.write.M20481_0;
import com.fengshen.server.data.write.M41505_0;
import com.fengshen.server.data.write.M61677_0;
import com.fengshen.server.data.write.inventory.MSG_INVENTORY_REMOVE;
import com.fengshen.server.data.write.store.MSG_STORE_REMOVE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义时装 - 批量穿戴
 FASION_START     = 31, -- 时装开始位置
    FASHION_SUIT     = 31, -- 时装套装(旧代码，兼容)
    FASHION_JEWELRY  = 32, -- 时装首饰(旧代码，兼容)
    FASION_DRESS     = 31, -- 时装礼服
    FASION_BALDRIC   = 32, -- 时装玉佩
    FASION_HAIR      = 33, -- 自定义外观 - 发型 (新增)
    FASION_UPPER     = 34, -- 自定义外观 - 上身 (新增)
    FASION_LOWER     = 35, -- 自定义外观 - 下身 (新增)
    FASION_ARMS      = 36, -- 自定义外观 - 武器 (新增)
    EQUIP_FOLLOW_PET = 37, -- 跟随宠
    FASION_BACK      = 38, -- 自定义外观 - 背饰 (新增)
    FASION_TEAM_ICON = 39, -- 自定义外观 - 对标
    FASIONG_END      = 39, -- 时装结束位置
 * 
 *
 */
@Service
@Slf4j
public class CMD_FASION_CUSTOM_EQUIP_EX implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//is_buy
		GameReadTool.readByte(buff);
		String item_names = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		String[] split = item_names.split("\\|");
		if(split.length>1) {
			//自定义批量
			List<String> itemNames = Arrays.asList(split);
			List<FasionCustomInfo> fcs = GameData.that.fasionCustomInfoService.findByInStrs(itemNames);
			if(fcs != null && !fcs.isEmpty()) {
				Map<String,Integer> exists = new HashMap<>();
				for(Goods goods:chara.customShizhuang) {
					exists.put(goods.goodsInfo.str, 1);
				}
 				//过滤重复
				if(!exists.isEmpty()) {
					Iterator<FasionCustomInfo> iterator = fcs.iterator();
					while(iterator.hasNext()) {
						FasionCustomInfo next = iterator.next();
						if(exists.get(next.getName()) != null) {
							iterator.remove();
						}
					}
				}
				int sum = 0;
				if(!fcs.isEmpty()) {
					//计算需要消耗的积分
					sum = fcs.stream().mapToInt(FasionCustomInfo::getGoodsPrice).sum();
					if(chara.chargeScore<sum) {
						GameUtil.sendMeTips("积分不足。");
						return;
					}
					StringBuilder names = new StringBuilder();
					for(FasionCustomInfo f:fcs) {
						names.append("#R").append(f.getName()).append("#R").append("#n消费#R").append(f.getGoodsPrice()).append("#n积分\n");
					}
					Map<String,Object> data = new HashMap<String,Object>();
					data.put("data", fcs);
					data.put("sum", sum);
					data.put("names", split);
					gameObjectChar.confirmData = data;
					//弹出购买窗口
					GameUtil.confirm(chara, "你确认花费#R"+sum+"#n积分进行换装吗？\n包含:\n"+names.toString(), "shopCustomFasion");
					return;
				}
				//穿戴自定义时装
				GameCommonUtil.getFasionCustomEquipEx(chara, split);
				//这里要删除旧的时装
				Iterator<Goods> iterator = chara.otherGoods.iterator();
				while(iterator.hasNext()) {
					Goods next = iterator.next();
					if(next.pos ==  31) {
						iterator.remove();
						gameObjectChar.sendOne(new MSG_INVENTORY_REMOVE(), next.pos);
						break;
					}
				}
				Vo_61677_0 vo_61677_0 = new Vo_61677_0();
				vo_61677_0.store_type = "custom_store";
				vo_61677_0.npcID = 0;
				vo_61677_0.list = chara.customShizhuang;
				vo_61677_0.count = chara.customShizhuang.size();
				GameObjectChar.send(new M61677_0(), vo_61677_0);
				
				Vo_41505_0 vo_41505_0 = new Vo_41505_0();
				vo_41505_0.type = "equip_fasion";
				GameObjectChar.send(new M41505_0(), vo_41505_0);
				GameUtil.sendMeTips("穿戴成功。");
				GameObjectChar.send(new MSG_STORE_REMOVE(), new Vo_61677_0("custom_store",38));
			}
		}else {
			PackModification packModification = GameData.that.basePackModificationService.findOneByAlias(item_names);
			if(packModification == null) {
				return;
			}
			if(chara.goldCoin<packModification.getGoodsPrice()) {
				GameUtil.sendMeTips("元宝不足");
				return;
			}
			for(Goods goods:chara.otherGoods) {
				if(goods.goodsInfo.str.equals(packModification.getStr())) {
					GameUtil.sendMeTips("你已购买过#Y"+packModification.getStr());
					return;
				}
			}
			
			chara.goldCoin -= packModification.getGoodsPrice();
			Goods goods = createFasion(packModification,chara,item_names, 16);
			chara.shizhuang.add(goods);
			
			Vo_61677_0 vo_61677_0 = new Vo_61677_0();
			vo_61677_0.store_type = "fasion_store";
			vo_61677_0.npcID = 0;
			vo_61677_0.list = chara.shizhuang;
			vo_61677_0.count = chara.shizhuang.size();
			GameObjectChar.send(new M61677_0(), vo_61677_0);
			Vo_20481_0 vo_20481_0 = new Vo_20481_0();
			vo_20481_0.msg = "你花费了" + packModification.getGoodsPrice() + "#n个金元宝购买了#Y" + item_names + "#n。";
			vo_20481_0.time = (int) (System.currentTimeMillis() / 1000L);
			GameObjectChar.send(new M20481_0(), vo_20481_0);
			Vo_41505_0 vo_41505_0 = new Vo_41505_0();
			vo_41505_0.type = "equip_fasion";
			GameObjectChar.send(new M41505_0(), vo_41505_0);
			for (int i = 0; i < chara.otherGoods.size(); ++i) {
				if (chara.otherGoods.get(i).pos == 31) {
					packModification = GameData.that.basePackModificationService
							.findOneByStr(chara.otherGoods.get(i).goodsInfo.str);
					Vo_61677_0 vo_61677_2 = new Vo_61677_0();
					vo_61677_2.store_type = "fasion_store";
					vo_61677_2.npcID = 0;
					vo_61677_2.count = 1;
					vo_61677_2.isGoon = 0;
					vo_61677_2.pos = packModification.getPosition();
					GameObjectChar.send(new MSG_STORE_REMOVE(), vo_61677_2);
				}
			}
		}
		log.info("批量穿戴时装");
	}

	/**
	 * 创建时装商品
	 * @param packModification
	 * @param chara
	 * @param name
	 * @return
	 */
	public Goods createFasion(PackModification packModification, Chara chara, String name, int amount) {
		Goods goods = new Goods();
		goods.goodsInfo.owner_id = 1;
		goods.goodsInfo.value = 2097924;
		goods.goodsInfo.quality = "金色";
		goods.goodsInfo.alias = name;
		goods.goodsInfo.amount = amount;
		goods.pos = packModification.getPosition();
		goods.goodsInfo.food_num = 2;
		goods.goodsInfo.master = chara.sex;
		goods.goodsInfo.recognize_recognized = 0;
		goods.goodsInfo.type = Integer.valueOf(packModification.getType());
		goods.goodsInfo.total_score = 25;
		goods.goodsInfo.damage_sel_rate = 1842075;
		goods.goodsInfo.str = packModification.getStr();
		goods.goodsInfo.metal = chara.polar;
		goods.goodsInfo.durability = 8;
		goods.goodsInfo.rebuild_level = 500;
		goods.goodsInfo.auto_fight = GameCommonUtil.UUID().toLowerCase();
		return goods;
	}
	
	@Override
	public int cmd() {
		return 33318;
	}
}