package com.fengshen.server.data.vo.friend;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FRIEND_MOVE_CHAR {
	private String fromId;
	private String toId;
	private List<String> gids;
}
