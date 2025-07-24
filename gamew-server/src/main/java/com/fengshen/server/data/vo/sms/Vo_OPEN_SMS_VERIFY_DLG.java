package com.fengshen.server.data.vo.sms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_OPEN_SMS_VERIFY_DLG {

	private String fuzzyPhone;
	
	private Integer lastLakeCodeTime;

	public Vo_OPEN_SMS_VERIFY_DLG(String fuzzyPhone, Integer lastLakeCodeTime) {
		super();
		this.fuzzyPhone = fuzzyPhone;
		this.lastLakeCodeTime = lastLakeCodeTime;
	}
}
