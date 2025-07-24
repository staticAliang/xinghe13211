package com.fengshen.server.data.vo.party;

import java.util.List;

import com.fengshen.db.domain.PartyMember;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_PARTY_MEMBER {

	private Integer page;
	private Integer tail;
	
	private List<PartyMembers> partyMembers;
	
	@Getter
	@Setter
	public static class PartyMembers {
		private Integer level;
		private Integer contrib;
		private Integer gender;
		private Integer tao;
		private Integer warTimes;
		private Integer curWarTimes;
		private Integer online;
		private Integer portrait;
		private String family;
		private PartyMember partyMember;
		private Integer flag;
	}
}
