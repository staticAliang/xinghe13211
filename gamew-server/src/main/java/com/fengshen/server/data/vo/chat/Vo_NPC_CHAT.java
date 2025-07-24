package com.fengshen.server.data.vo.chat;

/**
 * @author aaa
 *
 */
public class Vo_NPC_CHAT {

	private Integer id;

	private String text;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Vo_NPC_CHAT(Integer id, String text) {
		this.id = id;
		this.text = text;
	}
}