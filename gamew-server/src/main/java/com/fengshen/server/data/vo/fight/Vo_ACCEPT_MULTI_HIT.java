package com.fengshen.server.data.vo.fight;

import java.util.ArrayList;
import java.util.List;

import com.fengshen.server.data.vo.fight.Vo_ACCEPT_MAGIC_HIT.Info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_ACCEPT_MULTI_HIT {

	//攻击者id
	private Integer hitterId;
	
	//受击者id
	private Integer mainVictimId;
	
	//伤害类型
	private Integer damageType;
	
	private List<Info> infos;
	
	public Vo_ACCEPT_MULTI_HIT() {
		this.infos = new ArrayList<>();
	}
	
}