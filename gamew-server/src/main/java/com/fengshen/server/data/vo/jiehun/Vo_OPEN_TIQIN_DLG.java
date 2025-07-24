package com.fengshen.server.data.vo.jiehun;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_OPEN_TIQIN_DLG {

	private Integer gender;
	
	private Integer orgIcon;
	
	private Integer weaponIcon;
	
	private Integer suitIcon;
	
	private Integer upgrageType;
	
	private String name;
	
	private List<Integer> lightEffects;

	public Vo_OPEN_TIQIN_DLG() {
		this.lightEffects = new ArrayList<>();
	}
	
	
}