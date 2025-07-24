package com.fengshen.server.data.vo.fixedteam;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FIXED_TEAM_DATA {

	private String name;
	
	private Integer level;
	
	private Integer intimacy;
	
	private Integer maxIntimacy;
	
	private List<Member> members;	
	
	
	
	public Vo_FIXED_TEAM_DATA() {
		this.members = new ArrayList<>();
	}



	@Getter
	@Setter
	public static class Member {
		
		private String gid;
		
		private String name;
		
		private Integer level;
		
		private Integer icon;
		
		private Integer tao;
		
		private Integer lastLogoutTime;
		
		private Integer joinTime;
	}
	
}