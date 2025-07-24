package com.fengshen.server.data.vo.friend;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_OPEN_GIVING_WINDOW {

	private String giverName;
	
	private Integer giverIcon;
	
	private Integer giverUpgradeType;
	
	private Integer girverLeftTimes;
	
	private List<Integer> girverLightEffects;
	
	private String receiverName;
	
	private Integer receiverIcon;
	
	private Integer receiverUpgradeType;
	
	private Integer receiverLeftTimes;
	
	private List<Integer> receiverLightEffects;

	public Vo_OPEN_GIVING_WINDOW() {
		this.girverLightEffects = new ArrayList<>();
		this.receiverLightEffects = new ArrayList<>();
	}
	
}