package com.fengshen.server.data.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

// 这里是每个月的签到奖励类，可以自定义。共有31天
public class Vo_49169_0 {
	public int monthDays;
	public int signDays;
	public int isCanSgin;
	public int isCanReplenishSign;
	
	public List<SignDaysItem> items;

	@Getter
	@Setter
	public static class SignDaysItem{
		private String name;
		private int num;
		public SignDaysItem(String name, int num) {
			super();
			this.name = name;
			this.num = num;
		}
		public SignDaysItem() {
			
		}
	}
}
