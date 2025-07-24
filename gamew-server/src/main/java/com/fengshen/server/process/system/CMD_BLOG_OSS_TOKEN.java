package com.fengshen.server.process.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.fengshen.core.util.DateUtil;
import com.fengshen.db.domain.ConfigInfo;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.system.Vo_BLOG_OSS_TOKEN;
import com.fengshen.server.data.write.system.MSG_BLOG_OSS_TOKEN;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端请求 oss token
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_BLOG_OSS_TOKEN implements GameHandler {

	
	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int flag = GameReadTool.readByte(buff);
		log.info("客户端请求 oss token, flag={}",flag);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		if(gameObjectChar != null) {
			ConfigInfo ossConfig = GameData.that.configInfoService.getOneByUuid("oss_config");
			if(ossConfig != null) {
				String[] auths = ossConfig.getData().split(",");
				if(auths != null && auths.length > 1) {
					Map<String,Object> data = new HashMap<>();
					data.put("AccessKeyId", auths[0]);
					data.put("AccessKeySecret", auths[1]);
					String format = DateUtil.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd'T'HH:mm:ss'Z'");
					data.put("Expiration", format);
					gameObjectChar.sendOne(new MSG_BLOG_OSS_TOKEN(), new Vo_BLOG_OSS_TOKEN(flag, JSONObject.toJSONString(data)));
				}
			}
			
		}
	}

	@Override
	public int cmd() {
		return 0x80D4;
	}

}
