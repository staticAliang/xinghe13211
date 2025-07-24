package com.fengshen.server.domain.config;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VipChargeConfig {

	private Integer minMoney;
	
	private Integer maxMoney;

	private String uuid;
	
	private Reward reward;
	
	@Getter
	@Setter
	public static class Reward {
		
		private List<RewardInfo> task;
		
		private List<RewardInfo> value;
		
		@Getter
		@Setter
		public static class RewardInfo {
			public String name;
			
			private Integer num;
		}
	}
}
