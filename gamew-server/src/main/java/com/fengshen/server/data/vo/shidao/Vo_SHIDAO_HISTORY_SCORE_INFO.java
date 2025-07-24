package com.fengshen.server.data.vo.shidao;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SHIDAO_HISTORY_SCORE_INFO {

	private Integer time;
	
	private Integer level;

	private List<Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM> teams;
	
	@Getter
	@Setter
	public class Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM {
		
		private Integer rank;
		
		private Integer score;
		
		private Integer totalTao;
		
		private List<Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS> details;
		
		@Getter
		@Setter
		public class Vo_SHIDAO_HISTORY_SCORE_INFO_TEAM_DETAILS {
			
			private Integer isLeader;
			
			private String name;
			
			private Integer level;
			
			private Integer family;
			
			private String gid;
			
			private Integer icon;
		}
	}
}
