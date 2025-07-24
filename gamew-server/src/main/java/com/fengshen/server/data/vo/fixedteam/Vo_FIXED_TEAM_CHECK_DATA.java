package com.fengshen.server.data.vo.fixedteam;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FIXED_TEAM_CHECK_DATA {

	private Integer action;
	
	private String teanName;
	
	private List<Member> members;
	
	
	
	public Vo_FIXED_TEAM_CHECK_DATA() {
		this.members = new ArrayList<>();
	}



	@Getter
	@Setter
	public static class Member {
		
		private String gid;
		
		private String name;
		
		private Integer icon;
		
		private Integer hasConfirm;
	}
}