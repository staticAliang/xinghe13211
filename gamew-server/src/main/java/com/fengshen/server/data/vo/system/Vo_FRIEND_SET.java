package com.fengshen.server.data.vo.system;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_FRIEND_SET {

	//拒绝陌生人消息等级
	private int rejectStrangerLevel;
	//自动回复好友消息
	private String autoReplyFriendMessage;
	//拒绝xx级以下好友申请
	private int rejectLowerLevelApply;
}
