package com.fengshen.server.process.xiaozi;

import java.util.LinkedHashMap;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Accounts;
import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.CommonWrite;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.netty.ServerHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import lombok.extern.slf4j.Slf4j;

/*
 * 问道小子登录
 */
@Service
@Slf4j
public class CMD_XIAOZI_LOGIN implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		final String uuid = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		log.info("登录的问道小子名字为："+name);
		GameObjectChar gameObjectCharByUUid = GameObjectCharMng.getGameObjectChar(name);
		if(gameObjectCharByUUid != null ) {
			//该角色已经上线
			ctx.writeAndFlush(new CommonWrite(9997).write(null));
		}else {
			//查询用户是否存在
			Characters characters = GameData.that.baseCharactersService.findOneBlobByName(name);
			log.info("根据名称查询"+characters);
			if(characters == null) {
				//清注册
				ctx.writeAndFlush(new CommonWrite(9996).write(null));
			}else {
				Accounts account = new Accounts();
				account.setId(characters.getAccountId());
				account.setToken(GameCommonUtil.UUID());
				GameObjectChar gameSession = new GameObjectChar(account, ctx);
				Attribute<GameObjectChar> attr = ctx.channel()
						.attr(ServerHandler.akey);
				attr.set(gameSession);
				log.info("存在该用户.开始进入到登录环节"+account);
				//存在该用户.开始进入到登录环节
				LinkedHashMap<String, Object> gameMap = new LinkedHashMap<String, Object>();
				gameMap.put("name", characters.getName());
				ctx.writeAndFlush(new CommonWrite(9998).write(gameMap));
			}
		}
		log.info("问道小子请求登录{}",name);
	}

	@Override
	public int cmd() {
		return 9999;
	}

}
