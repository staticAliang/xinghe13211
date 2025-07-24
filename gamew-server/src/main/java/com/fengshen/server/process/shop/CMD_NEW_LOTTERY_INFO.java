package com.fengshen.server.process.shop;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Choujiang;
import com.fengshen.server.data.vo.luck.Vo_NEW_LOTTERY_INFO;
import com.fengshen.server.data.vo.luck.Vo_NEW_LOTTERY_INFO.Item;
import com.fengshen.server.data.write.M45381_0;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端新充值好礼的奖励数据
 * 
 *
 */
@Service
@Slf4j
public class CMD_NEW_LOTTERY_INFO implements GameHandler {
	@Override
	public void process(final ChannelHandlerContext ctx, final ByteBuf buff) {
		log.info("客户端新充值好礼的奖励数据");
		Vo_NEW_LOTTERY_INFO vo = new Vo_NEW_LOTTERY_INFO();
		vo.setStart_time(1566766800);
		vo.setEnd_time(1567371599);
		List<Item> items = new ArrayList<>();
		// 数据库查询出商品
		List<Choujiang> findAll = GameData.that.baseChoujiangService.findAll();
		for (Choujiang c : findAll) {
			items.add(new Item(c.getNo(), c.getName(), c.getDesc(), c.getLevel()));
		}
		vo.setItems(items);
		GameObjectChar.send(new M45381_0(), vo);
	}

	@Override
	public int cmd() {
		return 45384;
	}
}