package com.fengshen.server.process.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.StoreGoods;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.M65499_0;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 在线商城
 * 
 *
 */
@Service
@Slf4j
public class CMD_OPEN_ONLINE_MALL implements GameHandler {
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff); // 角色名称
		String para = GameReadTool.readString(buff); // 默认传递的参数为空
		if (para.equals("")) {
			// 查询数据库中所有商品
			List<StoreGoods> all = GameData.that.baseStoreGoodsService.findAll();
			GameObjectChar.send(new M65499_0(), all);
		}
		log.info("在线商城, name={}, para={}",name,para);
	}

	@Override
	public int cmd() {
		return 216;
	}
}
