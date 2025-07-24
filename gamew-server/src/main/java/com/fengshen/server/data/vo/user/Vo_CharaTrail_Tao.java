package com.fengshen.server.data.vo.user;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_CharaTrail_Tao {
	
	private Integer id;

	private Integer data;

	private String remarks;

	private String source;

	private String charaName;

	private Integer cid;

	private Date addTime;
}
