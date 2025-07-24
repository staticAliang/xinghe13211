package com.fengshen.server.data.vo.rank;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_TOP_USER {

	private int type; // 排行类型

	private int requestType; // 请求类型 1: normal, 2: level
	
	private int minLevel; // 最小等级
	
	private int maxLevel; // 最大等级
	
	private int cookie; // 记录上一次最后次数
	
	private List<Map<Object,Object>> data;
}
