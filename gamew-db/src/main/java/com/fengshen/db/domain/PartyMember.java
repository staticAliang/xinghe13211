package com.fengshen.db.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="party_member")
public class PartyMember {
	
	@Id
	@GeneratedValue(generator = "JDBC")
    private Integer id;

    private Integer charaId;

    private String charaGid;
    
    private String partyId;

    private String name;

    private Integer polar;

    private String job;

    private Integer lastWeekActive;

    private Integer currWeekActive;

    private Integer active;

    private Date logoutTime;

    private Date createTime;

    private String info;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCharaId() {
        return charaId;
    }

    public void setCharaId(Integer charaId) {
        this.charaId = charaId;
    }
    
    public String getCharaGid() {
		return charaGid;
	}

	public void setCharaGid(String charaGid) {
		this.charaGid = charaGid;
	}

	public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPolar() {
        return polar;
    }

    public void setPolar(Integer polar) {
        this.polar = polar;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public Integer getLastWeekActive() {
        return lastWeekActive;
    }

    public void setLastWeekActive(Integer lastWeekActive) {
        this.lastWeekActive = lastWeekActive;
    }

    public Integer getCurrWeekActive() {
        return currWeekActive;
    }

    public void setCurrWeekActive(Integer currWeekActive) {
        this.currWeekActive = currWeekActive;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Date getLogoutTime() {
        return logoutTime;
    }

    public void setLogoutTime(Date logoutTime) {
        this.logoutTime = logoutTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}