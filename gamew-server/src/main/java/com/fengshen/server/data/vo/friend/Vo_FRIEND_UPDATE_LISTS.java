package com.fengshen.server.data.vo.friend;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 好友列表信息.
 * 
 *
 */
@Getter
@Setter
public class Vo_FRIEND_UPDATE_LISTS {

	private Vo_FRIEND_ADD_GROUP group;

	private List<Vo_FRIEND_ADD_CHAR> friends;

}
