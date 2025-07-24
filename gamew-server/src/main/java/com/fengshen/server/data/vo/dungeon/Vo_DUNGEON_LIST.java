package com.fengshen.server.data.vo.dungeon;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_DUNGEON_LIST {

	private Integer bonus;
	
	private String hardName;
	
	private List<DugeonsInfo> dugeonsInfo;
	
	
	
	public Vo_DUNGEON_LIST(Integer bonus, String hardName, List<DugeonsInfo> dugeonsInfo) {
		this.bonus = bonus;
		this.hardName = hardName;
		this.dugeonsInfo = dugeonsInfo;
	}



	@Getter
	@Setter
	public static class DugeonsInfo {
		
		private Integer level;
		
		private String dungeonName;

		public DugeonsInfo(Integer level, String dungeonName) {
			this.level = level;
			this.dungeonName = dungeonName;
		}
	}
}