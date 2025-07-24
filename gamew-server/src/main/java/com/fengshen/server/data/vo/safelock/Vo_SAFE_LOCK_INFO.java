package com.fengshen.server.data.vo.safelock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_SAFE_LOCK_INFO {

	//是否设置过密码,0:未,1:设置过
	private Integer hasPwd;
	//当前是否验证,0,1
	private Integer isRelleaseLock;
	//
	private Integer resetStart;
	
	private Integer resetEnd;
	
	private Integer resetDays;
	
}