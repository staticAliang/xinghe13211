package com.fengshen.server.data.vo.account;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_L_ACCOUNT_CHARS {

	private String distName;

	private List<Role> roleList;

	@Getter
	@Setter
	public static class Role {
		
		private String name;
		
		private Integer icon;
		
		private Integer level;
		
		private Integer deleteTime;
	}

	public Vo_L_ACCOUNT_CHARS() {
		this.roleList = new ArrayList<>();
	}
	
}