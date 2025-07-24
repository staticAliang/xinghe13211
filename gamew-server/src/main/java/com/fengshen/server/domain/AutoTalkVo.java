package com.fengshen.server.domain;

public class AutoTalkVo {

	private Integer type;
	
	private String msg;
	
	//0没有变化 1增加 2修改 3删除
	private Integer op_type;
	
	private Integer para;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Integer getOp_type() {
		return op_type;
	}

	public void setOp_type(Integer op_type) {
		this.op_type = op_type;
	}

	public Integer getPara() {
		return para;
	}

	public void setPara(Integer para) {
		this.para = para;
	}
	
}