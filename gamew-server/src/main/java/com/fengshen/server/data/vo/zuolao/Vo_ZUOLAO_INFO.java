package com.fengshen.server.data.vo.zuolao;

import com.fengshen.server.domain.Chara;

public class Vo_ZUOLAO_INFO {

	private String gid;
	
	private String name;
	
	private Integer level;
	
	private String family;
	
	private Integer polar;
	
	private String serverName;
	
	private Integer time;

	public Vo_ZUOLAO_INFO(Chara chara) {
		this.gid = chara.uuid;
		this.name = chara.name;
		this.polar = chara.polar;
		this.level = chara.level;
		this.family = "";
		this.time = (int)chara.crimeTime;
	}

	public Vo_ZUOLAO_INFO() {
		
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getFamily() {
		return family;
	}

	public void setFamily(String family) {
		this.family = family;
	}

	public Integer getPolar() {
		return polar;
	}

	public void setPolar(Integer polar) {
		this.polar = polar;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
}