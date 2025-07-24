package com.fengshen.server.process.user;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fengshen.db.domain.ChangeCard;
import com.fengshen.server.data.constant.DefinedConst;
import com.fengshen.server.data.vo.chara.VoChangeCard;
import com.fengshen.server.data.vo.user.Vo_UPDATE_APPEARANCE;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.data.write.user.MSG_UPDATE_APPEARANCE;
import com.fengshen.server.game.*;
import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.domain.Goods;
import com.fengshen.server.domain.GoodsInfo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 使用变身卡
 *
 */
@Service
@Slf4j
public class CMD_APPLY_CARDa implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int pos = GameReadTool.readInt(buff);
		int b = GameReadTool.readShort(buff);
		int c = GameReadTool.readShort(buff);
		int d = GameReadTool.readShort(buff);

		 GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		 Chara chara = gameObjectChar.chara;
		String i= null;
//4200  2425  2420   2664  2412  2156 2664 2938  2152  2403
		if (b == 4200)
		{
			i = "";
		}
		else if (b == 2425)
		{
			i = "夜行人";
		}
		else if (b == 2420)
		{
			i = "铁拐李";
		}
		else if (b == 2664 && d == 31336)
		{
			i= "汉钟离";
		}
		else if (b == 2412)
		{
			i = "吕洞宾";
		}
		else if (b == 2156)
		{
			i = "蓝采和";
		}
		else if (b == 2664 && d == 30825)
		{
			i = "韩湘子";
		}
		else if (b == 2938)
		{
			i = "张果老";
		}
		else if (b == 2152)
		{
			i = "何仙姑";
		}
		else if (b == 2403)
		{
			i = "曹国舅";
		}

		VoChangeCard voCard = new VoChangeCard();

		if (i.equals("") == false)
		{
					Example example = new Example(ChangeCard.class);
				example.createCriteria().andEqualTo("name", i);
				ChangeCard changeCard = GameData.that.changeCardService.selectOneByExample(example);
				if (changeCard == null) {
					GameUtil.sendMeTips("未找到#Y" + i + "#n配置信息。");
					return;
				}
				// 先删除之前的定时器
				Calendar calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - changeCard.getTime());
				voCard.setIcon(changeCard.getIcon());
				voCard.setType(changeCard.getType());
				voCard.setLevel(changeCard.getLeve());
				
				voCard.setName(changeCard.getName());
				// 开始时间
				voCard.setStartTime(System.currentTimeMillis());
				int endTime = (int) ((System.currentTimeMillis() / 1000L + changeCard.getTime() * 60 * 60) - 60);
				voCard.setEndTime(endTime);
				// 小时
				voCard.setHour(changeCard.getTime());
		}
		else
		{
			voCard = null;
		}
		chara.changeCardInfo = voCard;

		// 刷新地图数据--让所有人都能看到
		Vo_UPDATE_APPEARANCE vo_61661_0 = GameUtil.a61661(chara);
		GameObjectChar.getGameObjectChar().gameMap.send(new MSG_UPDATE_APPEARANCE(), vo_61661_0);

		// 播放使用变身卡声音.
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		map.put("sound", "bianshen");
		GameObjectChar.getGameObjectChar().gameMap.send(new CommonWrite(0xD043), map);
		// 播放动画效果
		GameCommonUtil.charaPlay(GameObjectChar.getGameObjectChar(), 1261, 1);
		GameUtil.sendUpdate(chara);
		// int pos = GameReadTool.readShort(buff);
		// int id = GameReadTool.readInt(buff);
		// log.info("pos:{},id:{}", pos, id);
		// GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		// Chara chara = gameObjectChar.chara;
		// List<Goods> backpack = chara.backpack;
		// GoodsInfo goodsInfo = null;
		// if(pos>=2001 && pos<=2501) {
		// 	//变身卡仓库
		// 	for (int i = 0; i < chara.cardStore.size(); i++) {
		// 		Goods goods2 = chara.cardStore.get(i);
		// 		if (goods2.pos == pos) {
		// 			goodsInfo = goods2.goodsInfo;
		// 			break;
		// 		}
		// 	}
		// }else {
		// 	for (Goods g : backpack) {
		// 		if (g.pos == pos) {
		// 			// 获取背包商品信息
		// 			goodsInfo = g.goodsInfo;
		// 			break;
		// 		}
		// 	}
		// }
		// if(goodsInfo == null) {
		// 	GameUtil.sendMeTips("未找到#Y这个商品信息。");
		// 	return;
		// }
		// GameUtil.confirm(GameObjectChar.getGameObjectChar().chara, 
		// 		"你确定要使用#R"+goodsInfo.str+"#n吗?", "applyCard-"+pos);
	}

	@Override
	public int cmd() {
		return 45316;
	}

}
