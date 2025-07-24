package com.fengshen.server.data.vo.fixedteam;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.data.vo.fixedteam.Vo_FIXED_TEAM_CHECK_DATA.Member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FIXED_TEAM_FINISH_DATA {

	private String teamName;
	
	private List<Member> members;
	
	public Vo_FIXED_TEAM_FINISH_DATA() {
		this.members = new ArrayList<>();
	}
}