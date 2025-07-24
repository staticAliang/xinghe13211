package com.fengshen.server.data.vo.wdrd;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_WD_RB_RECV_REDBAG {

	private Integer type;
	
	private Integer coin;
	
	private String redbagGid;
	
	private Integer totalCoin;
	
	private Integer senderLevel;
	
	private Integer senderIcon;
	
	private String senderName;
	
	private String msg;
	
	private Integer sendTime;
	
	private Integer count;
	
	private Integer state;
	
	private Integer isSender;
	
	private Integer isRecv;
	
	private List<Info> infos;
	
	
	
	public Vo_WD_RB_RECV_REDBAG() {
		this.infos = new ArrayList<>();
	}



	@Getter
	@Setter
	public static class Info {
		private String name;
		
		private Integer coin;
		
		private Integer time;

		public Info(String name, Integer coin, Integer time) {
			this.name = name;
			this.coin = coin;
			this.time = time;
		}
	}
}
