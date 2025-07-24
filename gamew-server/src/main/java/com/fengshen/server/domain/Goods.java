package com.fengshen.server.domain;

import java.util.UUID;

import com.fengshen.db.domain.StoreInfo;
import com.fengshen.db.domain.ZhuangbeiInfo;

import lombok.Getter;
import lombok.Setter;

// 用于写到数据库存为json格式的商品类
@Getter
@Setter
public class Goods  {
	public int pos;
	public GoodsInfo goodsInfo; // 商品信息
	public GoodsBasics goodsBasics; // 基本信息
	public GoodsLanSe goodsLanSe; // 蓝色属性，最多3个
	public GoodsFenSe goodsFenSe; // 粉色属性，最多1个
	public GoodsHuangSe goodsHuangSe; // 黄色属性，最多1个
	public GoodsLvSe goodsLvSe; // 绿色属性（默认的一条绿色）最多1个
	public GoodsGaiZao goodsGaiZao; // 改造属性，（两条蓝色的改造属性）
	public GoodsGaiZaoGongMing goodsGaiZaoGongMing; // 改造暗色的，例如改12，这种
	public GoodsGaiZaoGongMingChengGong goodsGaiZaoGongMingChengGong;
	public GoodsLvSeGongMing goodsLvSeGongMing; // 共鸣的绿色属性，在默认的属性下面（就是绿色下面的那个暗属性）
	public GoodsHunqi goodsHunQi;
	
	
	public Goods() {
		this.pos = 0;
		this.goodsInfo = new GoodsInfo();
		this.goodsBasics = new GoodsBasics();
		this.goodsHunQi = new GoodsHunqi();
		this.goodsLanSe = new GoodsLanSe();
		this.goodsFenSe = new GoodsFenSe();
		this.goodsHuangSe = new GoodsHuangSe();
		this.goodsLvSe = new GoodsLvSe();
		this.goodsGaiZao = new GoodsGaiZao();
		this.goodsGaiZaoGongMing = new GoodsGaiZaoGongMing();
		this.goodsGaiZaoGongMingChengGong = new GoodsGaiZaoGongMingChengGong();
		this.goodsLvSeGongMing = new GoodsLvSeGongMing();
	}
	
	
	//这是创建道具
//	public Goods(String type) {
//		this.pos = 0;
//		this.goodsInfo = new GoodsInfo();
//		this.goodsBasics = new GoodsBasics();
//	}

	public void goodsDaoju(StoreInfo info) {
		if (info.getQuality() != null) {
			this.goodsInfo.quality = info.getQuality();
		}
		if (info.getSilverCoin() != null) {
			this.goodsInfo.silver_coin = info.getSilverCoin();
		}
		if (info.getName().equals("血玲珑")) {
			this.goodsBasics.max_life = 4000000;
			this.goodsInfo.phy_rebuild_level = "剩余血量：4,000,000";
		}
		if (info.getName().equals("法玲珑")) {
			this.goodsBasics.max_mana = 4000000;
			this.goodsInfo.phy_rebuild_level = "剩余法力：4,000,000";
		} 
		if (info.getName().equals("中级血玲珑")) {
			this.goodsBasics.max_life = 10000000;
			this.goodsInfo.phy_rebuild_level = "剩余血量：10,000,000";
		}
		if (info.getName().equals("中级法玲珑")) {
			this.goodsBasics.max_mana = 10000000;
			this.goodsInfo.phy_rebuild_level = "剩余法力：10,000,000";
		}
		if (info.getName().equals("高级法玲珑")) {
			this.goodsBasics.max_mana = 20000000;
			this.goodsInfo.phy_rebuild_level = "剩余法力：20,000,000";
		}
		if (info.getName().equals("高级血玲珑")) {
			this.goodsBasics.max_life = 20000000;
			this.goodsInfo.phy_rebuild_level = "剩余血量：20,000,000";
		}
		if (info.getName().equals("火眼金睛")) {
			this.goodsInfo.max_durability2 = 10;
		}
		this.goodsInfo.type = info.getType();
		this.goodsInfo.str = info.getName();
		this.goodsInfo.recognize_recognized = 0;
		this.goodsInfo.auto_fight = UUID.randomUUID().toString();
		this.goodsInfo.total_score = info.getTotalScore();
		this.goodsInfo.rebuild_level = info.getRebuildLevel();
		this.goodsInfo.value = info.getValue();
		this.goodsInfo.degree_32 = 0;
	}

	public void goodsCreate(final ZhuangbeiInfo info) {
		this.goodsInfo.amount = info.getAmount();
		this.goodsInfo.auto_fight = UUID.randomUUID().toString();
		this.goodsInfo.master = info.getMaster();
		this.goodsInfo.type = info.getType();
		this.goodsInfo.str = info.getStr();
		this.goodsInfo.metal = info.getMetal();
		this.goodsInfo.quality = info.getQuality();
		this.goodsInfo.attrib = info.getAttrib();
		this.goodsInfo.total_score = 1;
		this.goodsInfo.rebuild_level = 1500;
		this.goodsInfo.recognize_recognized = 1;
		this.goodsInfo.damage_sel_rate = 1000;
		this.goodsInfo.owner_id = 1;
		this.goodsInfo.dunwu_times = 0;
		this.goodsInfo.gift = 0;
		this.goodsInfo.nick = 0;
		this.goodsInfo.power = 0;
		this.goodsInfo.wrestlescore = 0;
		this.goodsInfo.skill = 0;
		this.goodsInfo.store_exp = 0;
		this.goodsInfo.suit_degree = 0;
		this.goodsInfo.party_stage_party_name = 0;
		this.goodsInfo.mailing_item_times = 0;
		this.goodsInfo.suit_enabled = 0;
		this.goodsInfo.degree_32 = 0;
		this.goodsInfo.value = 8388608;
		this.goodsInfo.color = 0;
		this.goodsBasics.accurate = info.getAccurate();
		this.goodsBasics.def = info.getDef();
		this.goodsBasics.dex = info.getDex();
		this.goodsBasics.mana = info.getMana();
		this.goodsBasics.parry = info.getParry();
		this.goodsBasics.wiz = info.getWiz();
	}
}