package com.fengshen.server.data.vo.hunpo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_REFRESH_NEIDAN_DATA {

	//是否为顶层0:是 1:否
	private int isTop;
	
	//下一阶段状态
	private int nextState;
	private int nextStage;
	
	//下一阶段消耗点
	private int nextAttributePoint;
	private int nextPolarPoint;
	
}