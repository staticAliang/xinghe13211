package com.fengshen.server.process.shidao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fengshen.db.domain.ShidaoHistory;
import com.fengshen.db.domain.ShidaoHistoryteam;
import com.fengshen.server.data.GameReadTool;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY_SCORE_INFO;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY_SCORE_INFO.Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY_SCORE_INFO.Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM.Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS;
import com.fengshen.server.data.write.shidao.MSG_SHIDAO_HISTORY_SCORE_INFO;
import com.fengshen.server.game.GameData;
import com.fengshen.server.game.GameHandler;
import com.fengshen.server.game.GameObjectChar;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import tk.mybatis.mapper.entity.Example;

/**
 * 查询试道积分信息
 * 
 *
 */
@Service
public class CMD_SHIDAO_HISTORY_SCORE_INFO implements GameHandler {

	@Override
	public void process(ChannelHandlerContext ctx, ByteBuf buff) {
		int time = GameReadTool.readInt(buff);
		int level = GameReadTool.readShort(buff);
		
		Example example = new Example(ShidaoHistory.class);
		example.orderBy("rank").orderBy("score").desc().orderBy("totalTao").desc();
		example.createCriteria().andEqualTo("shidaoTime", time).andEqualTo("level", level);
		List<ShidaoHistory> shidaoHistorys = GameData.that.shidaoHistoryService.selectByExample(example);
		if(shidaoHistorys != null && !shidaoHistorys.isEmpty()) {
			Vo_SHIDAO_HISTORY_SCORE_INFO vo = new Vo_SHIDAO_HISTORY_SCORE_INFO();
			vo.setLevel(level);
			vo.setTime(time);
			List<Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM> teams = new ArrayList<>();
			for(ShidaoHistory s:shidaoHistorys) {
				Example example2 = new Example(ShidaoHistoryteam.class);
				example2.createCriteria().andEqualTo("shidaoHistoryId", s.getId());
				//成员详情
				List<ShidaoHistoryteam> members = GameData.that.shidaoHistoryteamService.selectByExample(example2);
			
				Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM team = new Vo_SHIDAO_HISTORY_SCORE_INFO().new Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM();
				team.setRank(s.getRank());
				team.setScore(s.getScore());
				team.setTotalTao(s.getTotalTao());
				List<Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS> vo_members = new ArrayList<>();
				//成员详情
				for(ShidaoHistoryteam member:members) {
					Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS vo_member = new Vo_SHIDAO_HISTORY_SCORE_INFO().new Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM().new  Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS();
					vo_member.setFamily(member.getFamily());
					vo_member.setGid(member.getGid());
					vo_member.setIcon(member.getIcon());
					if(s.getLeaderUuid().equals(member.getGid())) {
						vo_member.setIsLeader(1);
					}else {
						vo_member.setIsLeader(0);
					}
					vo_member.setLevel(member.getLevel());
					vo_member.setName(member.getName());
					vo_members.add(vo_member);
				}
				team.setDetails(vo_members);
				teams.add(team);
			}
			vo.setTeams(teams);
			GameObjectChar.send(new MSG_SHIDAO_HISTORY_SCORE_INFO(), vo);
		}
	}

	@Override
	public int cmd() {
		return 0xD2E0;
	}

}
