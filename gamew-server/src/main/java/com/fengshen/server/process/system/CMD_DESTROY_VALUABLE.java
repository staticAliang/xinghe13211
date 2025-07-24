package com.fengshen.server.process.system;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.system.Vo_DESTROY_VALUABLE;
import com.fengshen.server.data.write.system.MSG_DESTROY_VALUABLE;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 请求销毁贵重道具或者宠物
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_DESTROY_VALUABLE implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int type = GameReadTool.readByte(buff);
		int id = GameReadTool.readInt(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		
		//随机4位数验证码
		int code = ThreadLocalRandom.current().nextInt(8999)+1000;
		Map<String,Integer> data = new HashMap<>();
		data.put("id", id);
		data.put("code", code);
		data.put("type", type);
		gameObjectChar.confirmData = data;
		gameObjectChar.currentConfirmItem = "destory_valuable";
		GameObjectChar.send(new MSG_DESTROY_VALUABLE(), new Vo_DESTROY_VALUABLE(type,id,code));
		log.info("请求销毁贵重道具或者宠物,type={},id={}",type,id);
	}

	@Override
	public int cmd() {
		return 0x8094;
	}

}
