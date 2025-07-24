package com.fengshen.server.data.vo.party;

import java.util.List;

import com.fengshen.db.domain.Party;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_PARTY_LIST_EX {

	private String type;
	
	private List<Party> partys;
}
