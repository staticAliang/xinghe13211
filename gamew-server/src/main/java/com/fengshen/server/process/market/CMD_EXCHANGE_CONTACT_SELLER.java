package com.fengshen.server.process.market;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.Characters;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.db.domain.SaleGood;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.market.Vo_EXCHANGE_CONTACT_SELLER;
import com.fengshen.server.data.write.market.MSG_EXCHANGE_CONTACT_SELLER;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

@Service
@Slf4j
public class CMD_EXCHANGE_CONTACT_SELLER implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//类型
		String type = GameReadTool.readString(buff);
		//商品gid
		String goodsGid = GameReadTool.readString(buff);
		//参数 3|宠物_神兽|1;2;1;price   3|装备_全部武器_120|1;2;1;price
		String para = GameReadTool.readString(buff);
		Vo_EXCHANGE_CONTACT_SELLER v = new Vo_EXCHANGE_CONTACT_SELLER();
		//查询商品信息
		Object goods = GameData.that.saleGoodService.findOneByGoodsId(goodsGid);
		Map<String,Object> goodsMap = new HashMap<>();
		if(goods == null) {
			//去珍宝中查询
			Example example = new Example(GoldStallNineGoods.class);
			example.createCriteria().andEqualTo("goodsId", goodsGid);
			goods = GameData.that.zhenbao.selectOneByExample(example);
			GoldStallNineGoods gold = (GoldStallNineGoods) goods;
			goodsMap.put("gid", gold.getGid());
			goodsMap.put("name", gold.getName());
			goodsMap.put("extra", gold.getExtra());
		}else {
			SaleGood saleGood = (SaleGood) goods;
			goodsMap.put("gid", saleGood.getGid());
			goodsMap.put("name", saleGood.getName());
			goodsMap.put("extra", saleGood.getExtra());
		}
		if(goods != null) {
			int online = 2;
			GameObjectChar gameObject = GameObjectCharMng.getGameObjectCharByUUid((String) goodsMap.get("gid"));
			Chara toChara = null;
			if(gameObject == null) {
				//数据库查询
				Characters findOneByGid2 = GameData.that.baseCharactersService.findOneByGid2((String) goodsMap.get("gid"));
				online = 1;
				toChara = JSONObject.parseObject(findOneByGid2.getData(),Chara.class);
			}else {
				toChara = gameObject.chara;
			}
			String goodsName = "";
			//宠物
			if(MapUtils.getIntValue(goodsMap, "type") == 2) {
				JSONObject parseObject = JSONObject.parseObject((String) goodsMap.get("extra"));
				String type1 = "精怪";
				if(parseObject.getInteger("mount_type") != 0) {
					if(parseObject.getInteger("mount_type") == 2) {
						type1 = "御灵";
					}
				}else {
					type1 = GameCommonUtil.getPetTypeStr(parseObject.getIntValue("rank"));
				}
				goodsName = (String) goodsMap.get("name")+"("+type1+")";
				
			}else {
				//装备道具等
				goodsName =  (String) goodsMap.get("name");
			}
			v.setGoodsName(goodsName);
			v.setGid( (String) goodsMap.get("gid"));
			v.setGoodGid(goodsGid);
			v.setPara(para);
			v.setIcon(toChara.waiguan);
			v.setIsFriend(0);
			v.setIsOnline(online);
			v.setLevel(toChara.level);
			v.setType(type);
			v.setName(toChara.name);
			GameObjectChar.send(new MSG_EXCHANGE_CONTACT_SELLER(), v);
		}
		
		
		log.info("客户端请求连续交易系统的卖家---类型={}，goodsGid={},para={}",
				type, goodsGid, para);
	}

	@Override
	public int cmd() {
		return 0x80BC;
	}

}
