package com.fengshen.server.data.vo.safelock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SAFE_LOCK_OPEN_BAN {

	private Integer banTime;
	
	private Integer errorCountMax;
	
	private Integer errorCount;
}
