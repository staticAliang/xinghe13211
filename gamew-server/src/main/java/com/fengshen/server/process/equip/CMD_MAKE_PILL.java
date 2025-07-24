package com.fengshen.server.process.equip;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.game.PackUtils;
import com.fengshen.server.data.vo.Vo_20480_0;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.vo.system.Vo_GENERAL_NOTIFY;
import com.fengshen.server.data.write.M20480_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.system.MSG_GENERAL_NOTIFY;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsInfo;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 炼丹
 * 
 *
 */
@Service
@Slf4j
public class CMD_MAKE_PILL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int index = GameReadTool.readInt(buff);
		// 合成方式（1表示单次合成非绑定玩具，2表示单次合成绑定玩具，3表示全部合成非绑定玩具，4表示全部合成绑定玩具）
		int type = GameReadTool.readByte(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		int pos = GameUtil.packPoint(chara);
		if (pos == -1) {
			return;
		}
		int createGoodsNum = 0;
		//凝香幻彩
		if (index / 10 == 10) {
			int level = index % 10;
			createGoodsNum = removeGoods(chara, level, "凝香幻彩", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("凝香幻彩数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "凝香幻彩");
			goods.goodsLanSe.def = PackUtils.demonStoneValue(goods.goodsInfo.skill, "凝香幻彩");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("凝香幻彩", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		//凝香幻彩 11-13
		if(index / 10 == 11) {
			int level = index%11+10;
			if(level > 13) {
				GameUtil.sendMeTips("最高13级");
				return;
			}
			createGoodsNum = removeGoods(chara, index, "凝香幻彩", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("凝香幻彩数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "凝香幻彩");
			goods.goodsLanSe.def = PackUtils.demonStoneValue(goods.goodsInfo.skill, "凝香幻彩");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("凝香幻彩", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		//炫影霜星1-10
		if (index / 10 == 12) {
			int level = index % 10;
			createGoodsNum = removeGoods(chara, level, "炫影霜星", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("炫影霜星数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "炫影霜星");
			goods.goodsLanSe.parry = PackUtils.demonStoneValue(goods.goodsInfo.skill, "炫影霜星");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("炫影霜星", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		//炫影霜星11-13
		if (index / 10 == 13) {
			int level = index%13+10;
			if(level > 13) {
				GameUtil.sendMeTips("最高13级");
				return;
			}
			createGoodsNum = removeGoods(chara, level, "炫影霜星", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("炫影霜星数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "炫影霜星");
			goods.goodsLanSe.parry = PackUtils.demonStoneValue(goods.goodsInfo.skill, "炫影霜星");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("炫影霜星", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		
		//风寂云清1-10
		if (index / 10 == 14) {
			int level = index % 10;
			createGoodsNum = removeGoods(chara, level, "风寂云清", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("风寂云清数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "风寂云清");
			goods.goodsLanSe.wiz = PackUtils.demonStoneValue(goods.goodsInfo.skill, "风寂云清");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("风寂云清", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		//风寂云清11-13
		if (index / 10 == 15) {
			int level = index%15+10;
			if(level > 13) {
				GameUtil.sendMeTips("最高13级");
				return;
			}
			createGoodsNum = removeGoods(chara, level, "风寂云清", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("风寂云清数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "风寂云清");
			goods.goodsLanSe.wiz = PackUtils.demonStoneValue(goods.goodsInfo.skill, "风寂云清");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("风寂云清", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		
		//1-10枯月流魂
		if (index / 10 == 16) {
			int level = index % 10;
			createGoodsNum = removeGoods(chara, level, "枯月流魂", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("枯月流魂数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "枯月流魂");
			goods.goodsLanSe.accurate = PackUtils.demonStoneValue(goods.goodsInfo.skill, "枯月流魂");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("枯月流魂", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		//11-13枯月流魂
		if (index / 10 == 17) {
			int level = index%17+10;
			if(level > 13) {
				GameUtil.sendMeTips("最高13级");
				return;
			}
			createGoodsNum = removeGoods(chara, level, "枯月流魂", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("枯月流魂数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "枯月流魂");
			goods.goodsLanSe.accurate = PackUtils.demonStoneValue(goods.goodsInfo.skill, "枯月流魂");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("枯月流魂", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		
		//1-10 雷极弧光
		if (index / 10 == 18) {
			int level = index % 10;
			createGoodsNum = removeGoods(chara, level, "雷极弧光", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("雷极弧光数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "雷极弧光");
			goods.goodsLanSe.mana = PackUtils.demonStoneValue(goods.goodsInfo.skill, "雷极弧光");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("雷极弧光", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		//11-13 雷极弧光
		if (index / 10 == 19) {
			int level = index%19+10;
			if(level > 13) {
				GameUtil.sendMeTips("最高13级");
				return;
			}
			createGoodsNum = removeGoods(chara, level, "雷极弧光", type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("雷极弧光数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, "雷极弧光");
			goods.goodsLanSe.mana = PackUtils.demonStoneValue(goods.goodsInfo.skill, "雷极弧光");
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success("雷极弧光", createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		
		//冰落残阳 1-10
		if (index / 10 == 20) {
			int level = index % 10;
			String name = "冰落残阳";
			createGoodsNum = removeGoods(chara, level, name, type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("冰落残阳数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, name);
			goods.goodsLanSe.dex = PackUtils.demonStoneValue(goods.goodsInfo.skill, name);
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success(name, createGoodsNum, goods.goodsInfo.skill);
			return;
		}
		
		//冰落残阳 11-13
		if (index / 10 == 21) {
			int level = index%21+10;
			if(level > 13) {
				GameUtil.sendMeTips("最高13级");
				return;
			}
			String name = "冰落残阳";
			createGoodsNum = removeGoods(chara, level, name, type);
			if(createGoodsNum == -1) {
				GameUtil.sendMeTips("冰落残阳数量不足,无法合成！");
				return;
			}
			Goods goods = createGoods(gameObjectChar,level, createGoodsNum, name);
			goods.goodsLanSe.dex = PackUtils.demonStoneValue(goods.goodsInfo.skill, name);
			GameCommonUtil.addGoodsToBackpack(goods, gameObjectChar);
			success(name, createGoodsNum, goods.goodsInfo.skill);
			return;
		}
	}

	/**
	 * 创建
	 * @param gameObjectChar
	 * @param level
	 * @param createGoodsNum
	 * @param name
	 * @return
	 */
	private Goods createGoods(GameObjectChar gameObjectChar, int level, int createGoodsNum, String name) {
		StoreInfo storeInfo = GameData.that.baseStoreInfoService.findOneByName(name);
		Goods goods = new Goods();
		goods.goodsInfo = new GoodsInfo();
		goods.goodsDaoju(storeInfo);
		goods.goodsInfo.degree_32 = 0;
		goods.goodsInfo.skill = level + 1;
		goods.goodsInfo.owner_id = createGoodsNum;
		goods.goodsInfo.damage_sel_rate = 400976;
		goods.goodsInfo.silver_coin = 6000;
		return goods;
	}
	
	/**
	 * 删除某个商品
	 * @param chara 玩家
	 * @param index 索引
	 * @param str 名字
	 * @param count 删除的数量
	 */
	public int removeGoods(Chara chara, int index, String str, int type) {
		int count = 0;
		//如果GM则无视
		if(GameObjectChar.getGameObjectChar().privilege == 1000) {
			return 30;
		}
		for (int i = 0; i < chara.backpack.size(); ++i) {
			Goods goods = chara.backpack.get(i);
			if (goods.goodsInfo.str.equals(str) && goods.goodsInfo.skill == index) {
				//找到这个商品
				count+=goods.goodsInfo.owner_id;
			}
		}
		if(count == 0) {
			//没有找到这个物品
			return -1;
		}else if(count < 3) {
			//材料不足无法炼化
			return -1;
		}
		//单个合成的话就修改数量
		if(type==1 || type ==2) {
			//单个合成
			count = 3;
		}
		//获取道具的数量
		int createGoodsNum = count/3;
		//减去余数
		count-=count%3;
		List<Goods> list = new ArrayList<Goods>();
		for (int i = 0; i < chara.backpack.size(); ++i) {
			Goods goods = chara.backpack.get(i);
			if (goods.goodsInfo.str.equals(str) && goods.goodsInfo.skill == index) {
				if (goods.goodsInfo.owner_id >= count) {
					goods.goodsInfo.owner_id -= count;
					count = 0;
				} else {
					count -= goods.goodsInfo.owner_id;
					goods.goodsInfo.owner_id = 0;
				}
				if (goods.goodsInfo.owner_id == 0) {
					list.add(goods);
				}
				List<Goods> listbeibao = new ArrayList<Goods>();
				Goods goods2 = new Goods();
				goods2.goodsBasics = null;
				goods2.goodsInfo = null;
				goods2.goodsLanSe = null;
				goods2.pos = goods.pos;
				listbeibao.add(goods2);
				GameObjectChar.send(new M65525_0(), listbeibao);
				if (count == 0) {
					break;
				}
			}
		}
		for (int j = 0; j < list.size(); ++j) {
			chara.backpack.remove(list.get(j));
			GameObjectChar.send(new M65525_0(), chara.backpack);
		}
		GameObjectChar.send(new M65525_0(), chara.backpack);
		//返回删除的数量
		return createGoodsNum;
	}

	public void success(String str, int count, int level) {
		
		Vo_20480_0 vo_20480_0 = new Vo_20480_0();
		vo_20480_0.msg = "炼制成功";
		vo_20480_0.time = (int) (System.currentTimeMillis() / 1000L);
		GameObjectChar.send(new M20480_0(), vo_20480_0);
		Vo_40964_0 vo_40964_0 = new Vo_40964_0();
		vo_40964_0.type = 1;
		vo_40964_0.name = str;
		vo_40964_0.param = "394250";
		vo_40964_0.rightNow = 0;
		GameObjectChar.send(new M40964_0(), vo_40964_0);
		Vo_GENERAL_NOTIFY vo_9129_0 = new Vo_GENERAL_NOTIFY();
		vo_9129_0.notify = 46;
		vo_9129_0.para = "1";
		GameObjectChar.send(new MSG_GENERAL_NOTIFY(), vo_9129_0);
		GameUtil.sendMeTips("恭喜你成功合成了#R"+count+"#n个#R"+level+"#n级#Y"+str);
		log.info("合成:{}",str);
	}
	
	@Override
	public int cmd() {
		return 28840;
	}
}