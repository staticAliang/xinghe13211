package com.fengshen.server.data.vo.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_OTHER_LOGIN {

	/**
	 * 结果
	 * 0 登录失败
	 * 1 顶号操作
	 * 2 封号断开
	 * 3 重连
	 */
	private Integer result;
	
	private Integer code;
	
	private String msg;
}
