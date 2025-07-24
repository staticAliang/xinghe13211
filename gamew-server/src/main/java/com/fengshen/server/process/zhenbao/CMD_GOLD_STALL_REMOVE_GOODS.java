package com.fengshen.server.process.zhenbao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.db.domain.CharaPet;
import com.fengshen.db.domain.GoldStallNineGoods;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.constant.StallStatus;
import com.fengshen.server.data.vo.Vo_40964_0;
import com.fengshen.server.data.write.M40964_0;
import com.fengshen.server.data.write.pet.MSG_UPDATE_PETS;
import com.fengshen.server.data.write.zhenbao.MSG_GOLD_STALL_MINE;
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
import tk.mybatis.mapper.entity.Example;

/**
 * 珍宝交易下架商品
 * 
 *
 */
@Service
public class CMD_GOLD_STALL_REMOVE_GOODS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String goodsId = GameReadTool.readString(buff);
		Chara chara = GameObjectChar.getGameObjectChar().chara;
		//撤摊
		Example example = new Example(GoldStallNineGoods.class);
		example.createCriteria().andEqualTo("goodsId", goodsId).andEqualTo("gid", chara.uuid);
		GoldStallNineGoods goldStallNineGoods = GameData.that.zhenbao.selectOneByExample(example);
		if(goldStallNineGoods == null) {
			GameUtil.sendMeTips("该商品已失效。");
			return;
		}
		JSONObject extra = JSONObject.parseObject(goldStallNineGoods.getExtra());
		if(extra.getIntValue("deposit_state") == 1) {
			//买家已支付定金
			GameCommonUtil.dialogOk("指定交易类型类商品支付定金后无法撤摊。");
			return;
		}else if(goldStallNineGoods.getStatus() == StallStatus.getValue("冻结中")) {
			GameCommonUtil.dialogOk("商品已被冻结");
			return;
		}
		if(goldStallNineGoods.getStallItemType() == 2) {
			//宠物
			Petbeibao pet = JSONObject.parseObject(goldStallNineGoods.getGoods(),Petbeibao.class);
			Petbeibao.isAddPet(chara, pet.petShuXing.get(0).penetrate);
			//添加宠物
			CharaPet charaPet = new CharaPet();
			charaPet.setAddTime(new Date());
			charaPet.setCid(chara.id);
			charaPet.setUuid(chara.uuid);
			charaPet.setPet(goldStallNineGoods.getGoods());
			charaPet.setOwnerName(chara.name);
			charaPet.setPetName(pet.petShuXing.get(0).str);
			GameData.that.charaPetService.insertSelective(charaPet);
			pet.id = charaPet.getId();
			pet.no = GameUtil.getNo(chara);
			chara.pets.add(pet);
			
			//播放动画
			final Vo_40964_0 vo_40964_21 = new Vo_40964_0();
			vo_40964_21.type = 2;
			vo_40964_21.name = "";
			vo_40964_21.param = String.valueOf(pet.petShuXing.get(0).type);
			vo_40964_21.rightNow = 0;
			GameObjectChar.send(new M40964_0(), vo_40964_21);
			//发送数据
			final List<Petbeibao> pets = new ArrayList<>();
			pets.add(pet);
			GameObjectChar.send(new MSG_UPDATE_PETS(), pets);
			GameUtil.sendMeTips("你成功将#R" + pet.petShuXing.get(0).str + "#n撤摊了");
			
		}else if(goldStallNineGoods.getStallItemType() == 0) {
			//装备道具
			int pos = GameUtil.packPoint(chara);
			if (pos != -1) {
				Goods goodsInfo = JSONObject.parseObject(goldStallNineGoods.getGoods(), Goods.class);
				// 重新设置信息.
				goodsInfo.goodsInfo.auto_fight = GameCommonUtil.UUID();
				goodsInfo.pos = pos;
				GameUtil.addwupin(goodsInfo, chara);
				
				//播放动画
				final Vo_40964_0 vo_40964_21 = new Vo_40964_0();
				vo_40964_21.type = 2;
				vo_40964_21.name = goldStallNineGoods.getName();
				vo_40964_21.param = "32271173";
				vo_40964_21.rightNow = 0;
				GameObjectChar.send(new M40964_0(), vo_40964_21);
				GameUtil.sendMeTips("你成功将#R" + goodsInfo.goodsInfo.str + "#n撤摊了");
			}
		}
		//删除商品信息
		Example delExample = new Example(GoldStallNineGoods.class);
		delExample.createCriteria().andEqualTo("gid", chara.uuid).andEqualTo("goodsId", goldStallNineGoods.getGoodsId());
		GameData.that.zhenbao.deleteByExample(delExample);
		//清空redis缓存
		GameData.that.redisUtils.delete(DefinedConst.GOLD_STALL_PREFIX+";"+goldStallNineGoods.getGoodsId()+";"+goldStallNineGoods.getStatus());
		//刷新当前这个人的摊位
		GameObjectChar.send(new MSG_GOLD_STALL_MINE(), GameCommonUtil.refreshMarketGold(chara));
	}

	@Override
	public int cmd() {
		return 0x8108;
	}

}
