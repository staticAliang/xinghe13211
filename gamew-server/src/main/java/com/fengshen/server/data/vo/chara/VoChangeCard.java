package com.fengshen.server.data.vo.chara;

import java.util.List;

import com.fengshen.server.data.game.ChangeCardAttr;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VoChangeCard {

	private int icon;
	
	private int type;
	
	private int level;
	
	private String name;
	
	private long startTime;
	
	private int endTime;
	
	private int hour;
	
	private List<ChangeCardAttr> attr;
}
