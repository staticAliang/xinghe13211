package com.fengshen.server.process.xiaozi;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 问道小子和服务端成功建立连接
 * 
 *
 */
@Service
@Slf4j
public class CMD_XIAOZI_CONNECTION_SUCCESS implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {

		String uuid = GameReadTool.readString(buff);
		log.info("uuid:"+uuid);
		log.info("buff:"+buff);
		String name = GameReadTool.readString(buff);
		log.info("问道小子和服务端成功建立连接:{}",uuid);
		Map<String,Object> info = new HashMap<>();
		info.put("online", 0);
		info.put("ctx", ctx);
		info.put("name", name);
		GameCore.xiaoziClientInfo.put(uuid, info);
	}

	@Override
	public int cmd() {
		return 9111;
	}

}
