package com.fengshen.server.process.fixedteam;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.FixedTeam;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_APPELLATION;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA;
import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA.Member;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_APPELLATION;
import com.fengshen.server.data.write.fixedteam.MSG_FIXED_TEAM_CHECK_DATA;
import com.fengshen.server.domain.Chara;
import com.fengshen.server.game.GameCommonUtil;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;
import com.fengshen.server.game.GameObjectCharMng;
import com.fengshen.server.game.GameUtil;
import com.qcloud.cos.utils.StringUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.mapper.entity.Example;

/**
 * 确认开始缔结固定队
 * @author aaa
 *
 */
@Service
@Slf4j
public class CMD_CONFIRM_START_BUILD_FIXED_TEAM implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		log.info("确认开始缔结固定队");
		GameObjectChar gameObjectChar = GameObjectChar.getGameObjectChar();
		Chara chara = gameObjectChar.chara;
		if(GameCommonUtil.isNotGameTeam(gameObjectChar.gameTeam) && gameObjectChar.gameTeam.duiwu.size()>1) {
			if(gameObjectChar.gameTeam.duiwu.get(0).id != chara.id) {
				GameUtil.sendMeTips("只有队长才可操作！");
				return;
			}
			//新组成的固定队
			Vo_FIXED_TEAM_APPELLATION data = new Vo_FIXED_TEAM_APPELLATION();
			data.setType(1);
			data.setTeamName("");
			data.setCostGold(0);
			for(Chara team:gameObjectChar.gameTeam.duiwu) {
				GameObjectCharMng.getGameObjectChar(team.id).sendOne(new MSG_FIXED_TEAM_APPELLATION(), data);
			}
			//如果有固定队伍的话直接下个界面
			if(!StringUtils.isNullOrEmpty(chara.fixedTeamName)) {
				Example example = new Example(FixedTeam.class);
				example.createCriteria().andEqualTo("uid", chara.fixedTeamName);
				FixedTeam fixedTeam = GameData.that.fixedTeamService.selectOneByExample(example);
				if(!fixedTeam.getLeaderUid().equals(chara.uuid)) {
					GameUtil.sendMeTips("只有固定队队长才可邀请人员加入固定队！");
					return;
				}
				//直接跳转到结交界面
				Vo_FIXED_TEAM_CHECK_DATA ftd = new Vo_FIXED_TEAM_CHECK_DATA();
				ftd.setAction(1);
				ftd.setTeanName(fixedTeam.getName());
				for(Chara teamChara:gameObjectChar.gameTeam.duiwu) {
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
					ftd.getMembers().add(member);
				}
				gameObjectChar.confirmData = ftd;
				GameObjectChar.sendduiwu(new MSG_FIXED_TEAM_CHECK_DATA(), ftd, chara.id);
			}
		}
		
	}

	@Override
	public int cmd() {
		return 0xD1FC;
	}

}
