package com.fengshen.server.data.vo.friend;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FRIEND_ADD_GROUP {
	private String groupId;
	private String name;
	public Vo_FRIEND_ADD_GROUP(String groupId, String name) {
		super();
		this.groupId = groupId;
		this.name = name;
	}
	
	public Vo_FRIEND_ADD_GROUP() {
		super();
	}
}
