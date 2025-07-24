package com.fengshen.server.process.xiaozi;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.fengshen.core.util.ResponseView;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.game.GameCore;
import com.fengshen.server.game.GameHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 问道小子上线成功！
 * @author weilian
 *
 */
@Service
@Slf4j
public class CMD_XIAOZI_LOGIN_SUCCESS implements GameHandler {
	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String uuid = GameReadTool.readString(buff);
		String name = GameReadTool.readString(buff);
		//设置登录信息
		Map<String, Map<String, Object>> xiaoziClientInfo = GameCore.xiaoziClientInfo;
		Map<String, Object> clientInfo = xiaoziClientInfo.get(uuid);
		if(clientInfo == null) {
			ResponseView.fail("客户端不在线！");
		}
		clientInfo.put("online", 1);
		clientInfo.put("name", name);
		//给客户端发通知
		Map<String,Object> object = new LinkedHashMap<String, Object>();
		object.put("name:str", name);
		log.info("问道小子登录成功：{},{}",uuid,name);
	}

	@Override
	public int cmd() {
		return 9998;
	}

}
