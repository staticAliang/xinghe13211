package com.fengshen.server.data.vo.system;

public class Vo_REMOVE_ANIMATE {

	private Integer id;
	
	private Integer type;
	
	private Integer effectNo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getEffectNo() {
		return effectNo;
	}

	public void setEffectNo(Integer effectNo) {
		this.effectNo = effectNo;
	}

	public Vo_REMOVE_ANIMATE(Integer id, Integer type, Integer effectNo) {
		super();
		this.id = id;
		this.type = type;
		this.effectNo = effectNo;
	}

	public Vo_REMOVE_ANIMATE() {
		super();
	}
	
}