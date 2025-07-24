package com.fengshen.server.process.gm;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.Characters;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.write.MSG_KICK_OFF;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CMD_ADMIN_KICKOFF implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		String name = GameReadTool.readString(buff);
		if(GameObjectChar.getGameObjectChar().privilege == 1000) {
			for(GameObjectChar g:GameObjectCharMng.getAll()) {
				if(g.chara.name.equals(name)) {
					//如果要封对方是GM则在游戏内无法操作，必须后台才能操作
					if(g.privilege != 0) {
						GameUtil.sendMeTips("对不起，您无权操作！");
						return;
					}
					//找到目标
					g.sendOne(new MSG_KICK_OFF(), "对不起您已被强制下线.");
					//先提示5秒钟后强制下线.
	            	g.offline();
	                Characters c = new Characters();
					c.setId(g.characters.getId());
					c.setUpdateTime(new Date());
					c.setOnline(0);
					GameData.that.baseCharactersService.updateById(c);
					break;
				}
			}
		}
		GameUtil.sendMeTips("操作成功");
		log.info("gm强踢下线,名字为:{}",name);
	}

	@Override
	public int cmd() {
		return 0x1AEC;
	}

}
