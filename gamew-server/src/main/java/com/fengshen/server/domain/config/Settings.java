package com.fengshen.server.domain.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Settings {

	private String filterText;
	
	//过滤昵称
	private String filterNickText = "张起灵、吴邪、王胖子";


}