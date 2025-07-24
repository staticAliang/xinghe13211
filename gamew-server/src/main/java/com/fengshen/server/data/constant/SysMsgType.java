package com.fengshen.server.data.constant;

public enum SysMsgType {
	SYSTEM(0),
	FRIEND_CHECK(1),
	ARENA(2),
	RED_DOT(3),
	TYPE_MAIL_CHATGROUP(4),
	TYPE_MAIL_MATERIAL(5),
	TYPE_MAIL_ACTIVITY(6),
	TYPE_MAIL_FRIEND(7),
	;
	private int value;
	
	private SysMsgType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

}
