package com.fengshen.server.data.vo.shidao;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 试道大会记录
 * 
 *
 */
@Getter
@Setter
public class Vo_SHIDAO_HISTORY {

	//左侧等级菜单
	private Integer levelBuff;
	
	//试道大会详情
	private List<Vo_SHIDAO_HISTORY_TIMES> items;
	
	/**
	 * 某届试道大会详情
	 * 
	 *
	 */
	@Getter
	@Setter
	public class Vo_SHIDAO_HISTORY_TIMES {
		
		private Integer time;
		
		private Integer isMonth;
		
		private List<Vo_SHIDAO_HISTORY_TIMES_MEMBERS> members;
		
		//成员
		@Getter
		@Setter
		public class Vo_SHIDAO_HISTORY_TIMES_MEMBERS {
			private Integer isLeader;
			private String memberName;
			private Integer level;
			private Integer family;
			private String gid;
			private Integer icon;
		}
	}
}
