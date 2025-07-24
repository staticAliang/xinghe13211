package com.fengshen.server.data.vo.task;

public class Vo_AUTO_WALK {
	public String task_prompt;
	public String task_type;

	public Vo_AUTO_WALK(String task_prompt) {
		this.task_prompt = task_prompt;
		this.task_type = "";
	}
	public Vo_AUTO_WALK(String task_prompt, String task_type) {
		this.task_prompt = task_prompt;
		this.task_type = task_type;
	}

	public Vo_AUTO_WALK() {
		super();
	}

}
