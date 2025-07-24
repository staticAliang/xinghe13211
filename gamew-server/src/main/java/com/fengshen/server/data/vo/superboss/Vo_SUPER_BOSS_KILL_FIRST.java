package com.fengshen.server.data.vo.superboss;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SUPER_BOSS_KILL_FIRST {

	private Integer flag;
	
	private List<Monster> monster;

	public Vo_SUPER_BOSS_KILL_FIRST() {
		this.monster = new ArrayList<>();
	}

	@Getter
	@Setter
	public static class Monster {
		
		private String name;
		
		private Integer killTime;
		
		private List<Player> players;
		public Monster() {
			this.players = new ArrayList<>();
		}
		
		public Monster(String name, Integer killTime) {
			this.name = name;
			this.killTime = killTime;
			this.players = new ArrayList<>();
		}


		//玩家
		@Getter
		@Setter
		public static class Player {
			
			private String gid;
			
			private String name;
			
			private Integer level;
			
			private Integer icon;

			public Player(String gid, String name, Integer level, Integer icon) {
				this.gid = gid;
				this.name = name;
				this.level = level;
				this.icon = icon;
			}
		}
	}
}
