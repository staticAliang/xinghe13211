package com.fengshen.server.domain.config;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * 内丹配置
 * @author aaa
 *
 */
@Getter
@Setter
public class NeiDanConfig {

	
	public Map<String,List<NeiDanVo>> info;
	
}