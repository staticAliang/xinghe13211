package com.fengshen.server.data.write.shidao;

import java.util.List;

import com.fengshen.server.data.GameWriteTool;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY_SCORE_INFO;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY_SCORE_INFO.Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM;
import com.fengshen.server.data.vo.shidao.Vo_SHIDAO_HISTORY_SCORE_INFO.Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM.Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS;
import com.fengshen.server.netty.BaseWrite;

import io.netty.buffer.ByteBuf;

/**
 * 试道积分历史记录
 * 
 *
 */
public class MSG_SHIDAO_HISTORY_SCORE_INFO extends BaseWrite<Vo_SHIDAO_HISTORY_SCORE_INFO> {

	@Override
	protected void writeO(ByteBuf buff, Vo_SHIDAO_HISTORY_SCORE_INFO object) {
		
		GameWriteTool.writeInt(buff, object.getTime());
		GameWriteTool.writeShort(buff, object.getLevel());
		List<Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM> teams = object.getTeams();
		GameWriteTool.writeByte(buff, teams.size());
		//列表
		for(Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM team:teams) {
			
			GameWriteTool.writeByte(buff, team.getRank());
			GameWriteTool.writeInt(buff, team.getScore());
			GameWriteTool.writeInt(buff, team.getTotalTao());
			
			List<Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS> details = team.getDetails();
			GameWriteTool.writeByte(buff, details.size());
			//队员详情
			for(Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS detail:details) {
				GameWriteTool.writeByte(buff, detail.getIsLeader());
				GameWriteTool.writeString(buff, detail.getName());
				GameWriteTool.writeShort(buff, detail.getLevel());
				GameWriteTool.writeByte(buff, detail.getFamily());
				GameWriteTool.writeString(buff, detail.getGid());
				GameWriteTool.writeInt(buff, detail.getIcon());
			}
		}
	}

	@Override
	public int cmd() {
		return 0xD2DF;
	}

}
