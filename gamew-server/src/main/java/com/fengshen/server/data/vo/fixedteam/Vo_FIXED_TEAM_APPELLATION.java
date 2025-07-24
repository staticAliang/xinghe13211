package com.fengshen.server.data.vo.fixedteam;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FIXED_TEAM_APPELLATION {

	private Integer type;
	
	private String teamName;
	
	private Integer costGold;

	public Vo_FIXED_TEAM_APPELLATION(Integer type, String teamName, Integer costGold) {
		this.type = type;
		this.teamName = teamName;
		this.costGold = costGold;
	}

	public Vo_FIXED_TEAM_APPELLATION() {
	}
	
}
