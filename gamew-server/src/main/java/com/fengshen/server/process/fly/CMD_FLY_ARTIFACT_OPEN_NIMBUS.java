package com.fengshen.server.process.fly;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.M65525_0;
import com.fengshen.server.data.write.fly.MSG_FLY_ARTIFACT_MAKE;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.google.common.collect.Lists;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_FLY_ARTIFACT_OPEN_NIMBUS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		//需要启灵的法宝
		int artifact_pos = GameReadTool.readShort(buff);
		//消耗品
		int material_pos = GameReadTool.readShort(buff);
		log.info("artifact_pos={},material_pos={}" ,artifact_pos,material_pos);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		//消耗商品
		Goods materialGoods = GameCommonUtil.getBackpackGoodsByPos(chara, material_pos);
		//启灵法宝
		Goods artifactGoods = GameCommonUtil.getBackpackGoodsByPos(chara, artifact_pos);
		if(materialGoods == null || artifactGoods == null) {
			GameUtil.sendMeTips("材料不足！");
			return;
		}
		//如果消耗品不是启灵的御天梭
		if(!materialGoods.goodsInfo.str.equals("御天梭") || materialGoods.goodsInfo.open_nimbus != 1) {
			GameUtil.sendMeTips("请提交正确的材料！");
			return;
		}
		//如果启灵的不是未启灵的飞行器
		if(artifactGoods.goodsInfo.open_nimbus != 0) {
			GameUtil.sendMeTips("请提交正确的未启灵飞行法宝！");
			return;
		}
		//删除消耗品
		GameUtil.removemunber(gameObjectChar, materialGoods, 1);
		//通知客户端刷新商品
		artifactGoods.goodsInfo.open_nimbus = 1;
		artifactGoods.goodsInfo.amount = 21;
		GameObjectChar.send(new M65525_0(), Lists.newArrayList(artifactGoods));
		GameObjectChar.send(new MSG_FLY_ARTIFACT_MAKE(), buff);
		GameUtil.sendMeTips("成功启灵！");
	}

	@Override
	public int cmd() {
		return 33576;
	}

}
