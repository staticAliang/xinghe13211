package com.fengshen.server.data.vo.identity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_UPDATE_ANTIADDICTION_STATUS {

	private Integer is_startup;
	private Integer total_online;
	private Integer last_online;
	private Integer adult_status;
	private Integer player_age;
	private Integer is_guest;
	private Integer age1;
	private Integer age2;
	private Integer young_coin_cost_limit;
	private Integer max_online_time;
	private Integer limit_day;
	
	public Vo_UPDATE_ANTIADDICTION_STATUS() {
		
		this.is_startup = 1;
		this.total_online = (int) (System.currentTimeMillis()/1000L);
		this.last_online = (int) (System.currentTimeMillis()/1000L);
		this.adult_status = -1;
		this.player_age = -1;
		this.is_guest = 0;
		this.age1 = 8;
		this.age2 = 16;
		this.young_coin_cost_limit = 1000000000;
		this.max_online_time = 86400;
		this.limit_day = 1;
	}
	
	
}
