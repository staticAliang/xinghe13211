package com.fengshen.server.data.vo;

// 这个类是创建新任务的
public class Vo_61553_0 {
	public int count;
	public String task_type; // 任务类型
	public String task_desc; // 任务描述，显示在详细的任务介绍中
	//task_prompt中有 "TIME_LEFT"，则需要一直刷新
	public String task_prompt; // 任务提示，到某个指定的NPC处
	public int refresh;
	public int task_end_time;
	public int attrib;
	public String reward;
	public String show_name;
	public String task_extra_para;
	public String task_state;
	public String flag;
	public String currentTask;
	//自定义数据
	public Object cdata;
}
