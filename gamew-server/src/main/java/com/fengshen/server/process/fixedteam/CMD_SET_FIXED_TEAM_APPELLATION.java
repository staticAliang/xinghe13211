package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_APPELLATION;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_APPELLATION;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 设置固定队称谓
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_SET_FIXED_TEAM_APPELLATION implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("设置固定队称谓");
		String name = GameReadTool.readString(buff);
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		gameObjectChar.confirmData = null;
		Chara chara = gameObjectChar.chara;
		if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam) && gameObjectChar.gameTeam.duiwu.size()>1) {
			if(gameObjectChar.gameTeam.duiwu.get(0).id != chara.id) {
				GameUtil.sendMeTips("只有队长才可设定专属固定队伍名称！");
				return;
			}
			//只允许使用汉字
			char[] ch = name.toCharArray();
		    for (char c : ch) {
		        if (c < 0x4E00 || c > 0x9FA5) {
		        	GameUtil.sendMeTips("队伍名称只允许输入汉字！");
		        	return;
		        }
		    }
		    if(name.length()<2 || name.length()>5) {
		    	GameUtil.sendMeTips("队伍名称只能在2-5字之间！");
		    	return;
		    }
		    gameObjectChar.confirmData = name;
		    Vo_FIXED_TEAM_APPELLATION data = new Vo_FIXED_TEAM_APPELLATION();
			data.setType(1);
			data.setTeamName(name);
			data.setCostGold(0);
			for(Chara team:gameObjectChar.gameTeam.duiwu) {
				if(team.id != chara.id) {
					GameObjectCharMng.getGameObjectChar(team.id).sendOne(new MSG_FIXED_TEAM_APPELLATION(), data);
				}
			}
		}
	}

	@Override
	public int cmd() {
		return 0xD1FE;
	}

}
