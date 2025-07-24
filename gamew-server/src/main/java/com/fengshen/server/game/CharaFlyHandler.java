package com.fengshen.server.game;

import com.fengshen.server.data.vo.Vo_61553_0;
import com.fengshen.server.domain.Chara;

/**
 * 任务飞升任务
 * 
 *
 */
public class CharaFlyHandler {

	
	public static void flyStepNo2(Chara chara1) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
	    vo_61553_0.count = 1;
	    vo_61553_0.task_type = "飞升—引路人";
	    vo_61553_0.task_desc = "修行已久、道行已深；寻找引路人，凝结成婴，踏上飞升之路。";
	    vo_61553_0.task_prompt = "前往#P冰晶龙鳞兽王|昆仑云海(10,22)|M=【飞升】那只有得罪了！|$0#P挑战#Y冰晶龙鳞兽王#n完成飞升第二步";
	    vo_61553_0.refresh = 1;
	    vo_61553_0.task_end_time = 1567909190;
	    vo_61553_0.attrib = 0;
	    vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I#I等级上限|突破115级限制#I";
	    vo_61553_0.show_name = "飞升—引路人";
	    vo_61553_0.task_extra_para = "";
	    vo_61553_0.task_state = "2";
	    GameUtilRenWu.createTask(vo_61553_0, chara1);
	}
	
	public static void flyStepNo3(Chara chara1) {
		Vo_61553_0 vo_61553_0 = new Vo_61553_0();
	    vo_61553_0.count = 1;
	    vo_61553_0.task_type = "飞升—引路人";
	    vo_61553_0.task_desc = "修行已久、道行已深；寻找引路人，凝结成婴，踏上飞升之路。";
	    vo_61553_0.task_prompt = "前往#P雪狐王|雪域冰原(10,22)|M=【飞升】那只有得罪了！|$0#P挑战#Y雪狐王#n完成飞升第三步";
	    vo_61553_0.refresh = 1;
	    vo_61553_0.task_end_time = 1567909190;
	    vo_61553_0.attrib = 0;
	    vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I#I等级上限|突破115级限制#I";
	    vo_61553_0.show_name = "飞升—引路人";
	    vo_61553_0.task_extra_para = "";
	    vo_61553_0.task_state = "3";
	    GameUtilRenWu.createTask(vo_61553_0, chara1);
	}
	public static void flyStepNo4(Chara chara) {
		final Vo_61553_0 vo_61553_0 = new Vo_61553_0();
		vo_61553_0.count = 1;
		vo_61553_0.task_type = "飞升—引路人";
		vo_61553_0.task_desc = "修行已久、道行已深；寻找引路人，凝结成婴，踏上飞升之路。";
		vo_61553_0.task_prompt = "请前往#R无名小镇#n找#P南华真人|@P南华真人|M=【飞升】结婴之路#P#n凝结成婴";
		vo_61553_0.refresh = 1;
		vo_61553_0.task_end_time = 1567909190;
		vo_61553_0.attrib = 0;
		vo_61553_0.reward = "#I经验|人物经验宠物经验#I#I道行|道行#I#I潜能|潜能#I#I武学|武学#I#I金钱|金钱#I#I等级上限|突破115级限制#I";
		vo_61553_0.show_name = "飞升—引路人";
		vo_61553_0.task_extra_para = "1";
		vo_61553_0.task_state = "4";
		GameUtilRenWu.createTask(vo_61553_0, chara);
	}
}
