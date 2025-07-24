package com.fengshen.server.data.vo.tongtianta;

import lombok.Getter;
import lombok.Setter;

/**
 * 通天塔飞升
 * 
 *
 */
@Getter
@Setter
public class Vo_TONGTIANTA_JUMP {
	//消耗类型
	private int costType;
	//消耗数量
	private int costCount;
	//跳了多少层
	private int jumpCount;
}
