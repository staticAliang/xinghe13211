package com.fengshen.server.data.vo.party;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
public class Vo_PARTY_INFO {

	private String partyId;
	private String partyName;
	private String partyBaseInfo;
	private String partyAnnounce;
	private Integer rights;
	private Integer construct;
	private Integer money;
	private Integer createTime;
	private Integer salary;
	private Integer autoAcceptLevel;
	private String creator;
	
	
	//技能信息
	private List<Skill> skills;
	@Getter
	@Setter
	public class Skill{
		private String name;
		private Integer no;
		private Integer level;
		private Integer currentSocre;
		private Integer levelupScore;
	}
	
	//其他信息
	private Integer population;
	private Integer onLineCount;
	private Integer partyLevel;
	private Integer partyMap;
	private String heir;
	private Integer lastAutoJoinTime;
	private String icon_md5;
	private String review_icon_md5;
	
	//领导
	private List<Leader> leaders;
	@Getter
	@Setter
	@ToString
	public static class Leader{
		private String job;
		private String name;
		private String gid;
	}
	
}
