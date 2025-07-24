package com.fengshen.server.data.vo.identity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 实名认证
 * 
 *
 */
public class Vo_FUZZY_IDENTITY {

	private int isBindName;
	private int isBindPhone;
	private String bindName;
	private String bindId;
	private String bindPhone;
}
