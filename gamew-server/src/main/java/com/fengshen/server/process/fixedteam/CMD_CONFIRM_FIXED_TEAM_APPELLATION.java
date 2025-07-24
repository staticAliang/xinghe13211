package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA.Member;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_CHECK_DATA;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameUtil;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * 确认固定队称谓
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_CONFIRM_FIXED_TEAM_APPELLATION implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("确认固定队称谓");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		String name = (String) gameObjectChar.confirmData;
		if(name != null) {
			if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam) && gameObjectChar.gameTeam.duiwu.size()>1) {
				if(gameObjectChar.gameTeam.duiwu.get(0).id != chara.id) {
					GameUtil.sendMeTips("只有队长才可操作！");
					return;
				}
				if(!StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
					GameUtil.sendMeTips("你已有固定队伍！");
					return;
				}
				//开始确定
				Vo_FIXED_TEAM_CHECK_DATA data = new Vo_FIXED_TEAM_CHECK_DATA();
				data.setAction(1);
				data.setTeanName(name);
				for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
					if(!StringUtils.isNullOrEmpty(teamChara.fixedTeamName)) {
						GameUtil.sendMeTips("#Y"+teamChara.name+"#n已有固定队伍！");
						return;
					}
					Member member = new Member();
					member.setGid(teamChara.uuid);
					//如果是队长直接设置成确定
					if(teamChara.id == chara.id) {
						member.setHasConfirm(1);
					}else {
						member.setHasConfirm(0);
					}
					member.setIcon(teamChara.waiguan);
					member.setName(teamChara.name);
					data.getMembers().add(member);
				}
				gameObjectChar.confirmData = data;
				GameObjectChar.sendduiwu(new MSG_FIXED_TEAM_CHECK_DATA(), data, chara.id);
			}
		}
	}

	@Override
	public int cmd() {
		return 0xD200;
	}

}
