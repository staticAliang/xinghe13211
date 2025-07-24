package com.fengshen.server.data.vo.equip;

import com.fengshen.server.domain.Goods;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_UPGRADE_INHERIT_PREVIEW {

	private Integer pos;
	
	private String para;
	
	private Integer flag;
	
	private Integer money;
	
	private Integer coin;
	
	private Goods mEquip;
	
	private Goods oEquip;
	
}